package com.twsz.dao.user;

import com.twsz.entity.bo.UserBo;
import com.twsz.entity.po.user.UserInfoPo;
import com.twsz.entity.po.user.UserPo;
import org.apache.ibatis.annotations.Param;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 13:50
 */
public interface UserDao {
    Integer insert(UserBo userBo);
    Integer delete(UserBo userBo);
    UserPo selectByMobile(UserBo userBo);
    UserPo selectByMobileAndPwd(UserBo userBo);
    Integer updateLastIpAndTime(UserBo userBo);
    String selectUserIdByToken(UserBo userBo);
    Integer updateLoginByToken(UserBo userBo);
    UserInfoPo selectUserInfoByToken(UserBo userBo);

    Integer updatePwd(UserBo userBo);
    Integer updateResetPwd(UserBo userBo);

    UserPo selectByNickName(UserBo userBo);
    String selectMobileByToken(@Param("token") String token);
}
