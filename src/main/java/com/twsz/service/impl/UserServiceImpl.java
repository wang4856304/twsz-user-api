package com.twsz.service.impl;

import com.twsz.constant.Constant;
import com.twsz.constant.RedisKeyConstant;
import com.twsz.dao.user.UserDao;
import com.twsz.dao.user.UserSeqDao;
import com.twsz.entity.Response;
import com.twsz.entity.bo.UserBo;
import com.twsz.entity.dto.user.UserChangePwdDto;
import com.twsz.entity.dto.user.UserDto;
import com.twsz.entity.dto.user.UserLoginDto;
import com.twsz.entity.dto.user.UserResetPwdDto;
import com.twsz.entity.po.user.UserInfoPo;
import com.twsz.entity.po.user.UserPo;
import com.twsz.enums.error.ErrorEnum;
import com.twsz.enums.error.UserEnum;
import com.twsz.service.AccountService;
import com.twsz.service.BaseService;
import com.twsz.service.UserService;
import com.twsz.service.redis.RedisService;
import com.twsz.utils.DateUtil;
import com.twsz.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 11:48
 */

@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserSeqDao userSeqDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AccountService accountService;

    /**
     * 序列模取值
     */
    private static final int mod = 100;

    private static final String defaultUserIconUrl="https://zea8.oss-cn-beijing.aliyuncs.com/QMBK_7B74F00C1B0548738E89AB5A7B9A2A45.png";

    /**
     * 已登录
     */
    private static final Integer LOGIN_IN = 1;

    /**
     * token有效期
     */
    private static final int TOKEN_EXPIRE = 30;

    /**
     * token有效
     */
    private static final int TOKEN_VALID = 1;

    @Transactional
    @Override
    public Response registerUser(UserDto userDto) {

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return buildErrorResponse(UserEnum.CONFIRM_PWD_ERROR.getCode(), UserEnum.CONFIRM_PWD_ERROR.getMsg());
        }
        String mobile = userDto.getMobile();
        String userVerifyCode = redisService.get(RedisKeyConstant.VERIFY_CODE + mobile);
        if (!userDto.getVerifyCode().equals(userVerifyCode)) {
            return buildErrorResponse(UserEnum.INVALID_VERIFY_CODE.getCode(), UserEnum.INVALID_VERIFY_CODE.getMsg());
        }
        UserBo userBo = new UserBo();
        userBo.setMobile(mobile);
        UserPo userPo = userDao.selectByMobile(userBo);
        if (userPo != null) {
            return buildErrorResponse(UserEnum.MOBILE_EXISTS.getCode(), UserEnum.MOBILE_EXISTS.getMsg());
        }
        userBo.setNickName(userDto.getNickName());
        UserPo userPo1 = userDao.selectByNickName(userBo);
        if (userPo1 != null) {
            return buildErrorResponse(UserEnum.NICK_NAME_EXISTS.getCode(), UserEnum.NICK_NAME_EXISTS.getMsg());
        }
        String userId = createUserId();
        userBo.setUserId(userId);

        userBo.setPassword(userDto.getPassword());
        userBo.setUserIcon(defaultUserIconUrl);
        userBo.setLastLoginTime(DateUtil.formartDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        //userBo.setLastLoginIp(userDto.getRemoteIp());
        //userBo.setDeviceId("");
        //userBo.setChannelId("360");
        //userBo.setLoginType(userDto.getLoginType());
        //userBo.setLoginSource(0);

        userDao.insert(userBo);

        userPo = new UserPo();
        userPo.setMobile(mobile);
        userPo.setUserId(userId);
        userPo.setNickName(userDto.getNickName());
        userPo.setUserIcon(defaultUserIconUrl);

        accountService.createAccount(userId);

        return buildSuccesResponse(userPo);
    }

    /**
     * 生成唯一的userId
     * @return
     */
    @Override
    public String createUserId() {
        Long userSeq = userSeqDao.insertUserSeq();
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String value = decimalFormat.format(userSeq%mod);
        StringBuffer sb = new StringBuffer();
        sb.append(Constant.USER_ID_PREFIX);
        DecimalFormat seqBaseDecimalFormat = new DecimalFormat("00000000");
        String seqBaseValue = seqBaseDecimalFormat.format(userSeq%Constant.SEQ_BASE);
        sb.append(seqBaseValue);
        sb.append(DateUtil.formartDate(new Date(), DateUtil.YYYYMMDD));
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(10));
        }
        sb.append(value);
        return sb.toString();
    }

    @Transactional
    @Override
    public Response login(UserLoginDto userLoginDto) {
        UserBo userBo = new UserBo();
        String mobile = userLoginDto.getMobile();
        userBo.setMobile(mobile);
        UserPo userPo = userDao.selectByMobile(userBo);
        if (userPo == null) {
            return buildErrorResponse(UserEnum.NOT_REGISDTER.getCode(), UserEnum.NOT_REGISDTER.getMsg());
        }
        userBo.setPassword(userLoginDto.getPassword());
        userPo = userDao.selectByMobileAndPwd(userBo);
        if (userPo == null) {
            return buildErrorResponse(UserEnum.USER_NAME_PWD_ERROR.getCode(), UserEnum.USER_NAME_PWD_ERROR.getMsg());
        }
        if (LOGIN_IN.equals(userPo.getIsLogin())) {
            return buildErrorResponse(UserEnum.ALREDY_LOGIN_ERROR.getCode(), UserEnum.ALREDY_LOGIN_ERROR.getMsg());
        }

        String token = TokenUtil.createToken(mobile);
        userBo.setUserToken(token);
        Long tokenExpire = DateUtil.delayTime(TOKEN_EXPIRE);
        userBo.setTokenExpire(tokenExpire);
        redisService.set(RedisKeyConstant.TOKEN + token, mobile, 60*60*24*TOKEN_EXPIRE);

        userBo.setLastLoginTime(DateUtil.formartDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        userBo.setLastLoginIp(userLoginDto.getRemoteIp());
        userBo.setIsLogin(LOGIN_IN);
        userBo.setTokenValid(TOKEN_VALID);
        userBo.setUserToken(token);
        userDao.updateLastIpAndTime(userBo);
        userPo.setMobile(mobile);
        userPo.setUserToken(token);
        userPo.setIsLogin(LOGIN_IN);
        return buildSuccesResponse(userPo);
    }

    @Override
    public Response loginOut(String token) {
        if (!checkToken(token)) {
            return buildErrorResponse(ErrorEnum.TOKEN_EXPIRED.getCode(), ErrorEnum.TOKEN_EXPIRED.getMsg());
        }
        String mobile = userDao.selectMobileByToken(token);
        redisService.del(RedisKeyConstant.TOKEN + mobile);
        UserBo userBo = new UserBo();
        userBo.setUserToken(token);
        userDao.updateLoginByToken(userBo);
        return buildSuccesResponse();
    }

    @Override
    public Boolean checkToken(String token) {
        boolean isRes = redisService.exists(RedisKeyConstant.TOKEN + token);
        if (!isRes) {
            return false;
        }
        return true;
    }

    @Override
    public Response getUserInfo(String token) {
        if (!checkToken(token)) {
            return buildErrorResponse(ErrorEnum.TOKEN_EXPIRED.getCode(), ErrorEnum.TOKEN_EXPIRED.getMsg());
        }
        UserBo userBo = new UserBo();
        userBo.setUserToken(token);
        UserInfoPo userInfoPo = userDao.selectUserInfoByToken(userBo);
        return buildSuccesResponse(userInfoPo);
    }

    @Override
    public Response changeUserPassword(UserChangePwdDto userChangePwdDto) {
        if (!userChangePwdDto.getPassword().equals(userChangePwdDto.getConfirmPassword())) {
            return buildErrorResponse(UserEnum.CONFIRM_PWD_ERROR.getCode(), UserEnum.CONFIRM_PWD_ERROR.getMsg());
        }
        String token = userChangePwdDto.getToken();
        if (!checkToken(token)) {
            return buildErrorResponse(ErrorEnum.TOKEN_EXPIRED.getCode(), ErrorEnum.TOKEN_EXPIRED.getMsg());
        }
        /*String mobile = userChangePwdDto.getMobile();
        String userVerifyCode = redisService.get(RedisKeyConstant.VERIFY_CODE + mobile);
        if (!userChangePwdDto.getVerifyCode().equals(userVerifyCode)) {
            return buildErrorResponse(UserEnum.INVALID_VERIFY_CODE.getCode(), UserEnum.INVALID_VERIFY_CODE.getMsg());
        }*/
        UserBo userBo = new UserBo();
        userBo.setUserToken(token);
        userBo.setOldPassword(userChangePwdDto.getOldPassword());
        userBo.setPassword(userChangePwdDto.getPassword());
        userDao.updatePwd(userBo);
        String mobile = userDao.selectMobileByToken(token);
        redisService.del(RedisKeyConstant.TOKEN + mobile);
        return buildSuccesResponse();
    }

    @Override
    public Response resetUserPassword(UserResetPwdDto userResetPwdDto) {
        if (!userResetPwdDto.getPassword().equals(userResetPwdDto.getConfirmPassword())) {
            return buildErrorResponse(UserEnum.CONFIRM_PWD_ERROR.getCode(), UserEnum.CONFIRM_PWD_ERROR.getMsg());
        }
        /*String token = userResetPwdDto.getToken();
        if (!checkToken(token)) {
            return buildErrorResponse(ErrorEnum.TOKEN_EXPIRED.getCode(), ErrorEnum.TOKEN_EXPIRED.getMsg());
        }*/
        String mobile = userResetPwdDto.getMobile();
        String userVerifyCode = redisService.get(RedisKeyConstant.VERIFY_CODE + mobile);
        if (!userResetPwdDto.getVerifyCode().equals(userVerifyCode)) {
            return buildErrorResponse(UserEnum.INVALID_VERIFY_CODE.getCode(), UserEnum.INVALID_VERIFY_CODE.getMsg());
        }
        UserBo userBo = new UserBo();
        userBo.setMobile(mobile);
        userBo.setPassword(userResetPwdDto.getPassword());
        userDao.updateResetPwd(userBo);
        UserPo userPo = userDao.selectByMobile(userBo);
        String token = userPo.getUserToken();
        redisService.del(RedisKeyConstant.TOKEN + token);
        return buildSuccesResponse();
    }
}
