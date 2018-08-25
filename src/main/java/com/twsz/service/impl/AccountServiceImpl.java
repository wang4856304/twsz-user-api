package com.twsz.service.impl;

import com.twsz.constant.Constant;
import com.twsz.constant.OrderConstant;
import com.twsz.constant.RedisKeyConstant;
import com.twsz.dao.account.AccountDao;
import com.twsz.dao.order.OrderDao;
import com.twsz.entity.Response;
import com.twsz.entity.bo.AccountBo;
import com.twsz.entity.bo.OrderBo;
import com.twsz.entity.dto.order.OrderDto;
import com.twsz.entity.po.account.AccountPo;
import com.twsz.entity.po.order.OrderPo;
import com.twsz.enums.AccountEnum;
import com.twsz.enums.error.ErrorEnum;
import com.twsz.enums.error.OrderErrorEnum;
import com.twsz.service.AccountService;
import com.twsz.service.BaseService;
import com.twsz.service.UserService;
import com.twsz.service.redis.RedisService;
import com.twsz.utils.DateUtil;
import com.twsz.utils.SignUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

@Service
public class AccountServiceImpl extends BaseService implements AccountService {

    private static Log log = LogFactory.getLog(AccountServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Value("${rechargeSecret}")
    private String rechargeSecret;

    private static final String DELIMITER = "-";


    @Transactional
    @Override
    public Response reCharge(OrderDto orderDto) {
        String token = orderDto.getToken();
        if (!userService.checkToken(token)) {
            return buildErrorResponse(ErrorEnum.TOKEN_EXPIRED.getCode(), ErrorEnum.TOKEN_EXPIRED.getMsg());
        }
        String sign = orderDto.getSign();
        String thirdOrderId = orderDto.getThirdOrderId();

        int customWay = orderDto.getCustomWay();
        double qbAmount = orderDto.getQbAmount();
        double rmbAmount = orderDto.getRmbAmount();
        long time = orderDto.getTime();
        StringBuffer sb = new StringBuffer();
        sb.append(thirdOrderId).append(DELIMITER)
                .append(token).append(DELIMITER)
                .append(customWay).append(DELIMITER)
                .append(qbAmount).append(DELIMITER)
                .append(rmbAmount).append(DELIMITER)
                .append(time).append(DELIMITER)
                .append(rechargeSecret);
        String signStr = SignUtil.getMD5Sign(sb.toString());
        if (!signStr.equalsIgnoreCase(sign)) {
            return buildErrorResponse(ErrorEnum.SIGN_ERROR.getCode(), ErrorEnum.SIGN_ERROR.getMsg());
        }
        OrderBo orderBo = new OrderBo();
        orderBo.setThirdOrderId(thirdOrderId);
        OrderPo orderPo = orderDao.selectByThirdOrderId(orderBo);
        if (orderPo != null) {
            if (OrderConstant.ORDER_STATUS_START != orderPo.getOrderStatus()) {
                return buildErrorResponse(OrderErrorEnum.ORDER_EXISTS.getCode(), OrderErrorEnum.ORDER_EXISTS.getMsg());
            }
        }
        String userId = redisService.get(RedisKeyConstant.TOKEN + token);
        String orderId = createOrderId(userId);
        orderBo.setOrderId(orderId);
        orderBo.setUserId(userId);
        orderBo.setOrderType(OrderConstant.ORDER_TYPE_IN);
        orderBo.setCustomType(OrderConstant.CUSTOM_TYPE_QB);
        orderBo.setCustomWay(customWay);
        orderBo.setQbAmount(qbAmount);
        orderBo.setRmbAmount(rmbAmount);
        boolean isSuccess = true;
        try {
            orderDao.insertOrder(orderBo);
        }
        catch (DuplicateKeyException e) {
            log.warn(e.getLocalizedMessage(), e);
            isSuccess = false;
        }
        if (isSuccess) {
            //去支付
        }

        return buildSuccesResponse();
    }

    @Transactional
    @Override
    public void createAccount(String userId) {
        AccountBo accountBo = new AccountBo();
        accountBo.setUserId(userId);
        accountBo.setUserAccountType(AccountEnum.ACCOUNT_QB.getType());
        String userAccountId = createAccountId(userId);
        accountBo.setUserAccountId(userAccountId);
        accountBo.setUserAccountName(AccountEnum.ACCOUNT_QB.getName());

        AccountBo accountBoRmb = new AccountBo();
        accountBoRmb.setUserId(userId);
        accountBoRmb.setUserAccountType(AccountEnum.ACCOUNT_RMB.getType());
        String RMBUserAccountId = createAccountId(userId);
        accountBoRmb.setUserAccountId(RMBUserAccountId);
        accountBoRmb.setUserAccountName(AccountEnum.ACCOUNT_RMB.getName());

        try {
            accountDao.insertAccount(accountBo);
            accountDao.insertAccount(accountBoRmb);
        }
        catch (DuplicateKeyException e) {
            log.warn(e.getLocalizedMessage(), e);
        }
    }

    @Transactional
    @Override
    public void updateAccount(String orderId, int payStatus, int payWay) {
        OrderBo orderBo = new OrderBo();
        orderBo.setOrderId(orderId);
        OrderPo orderPo = orderDao.selectByOrderId(orderBo);
        boolean isSuccess = false;

        if (OrderConstant.ORDER_STATUS_START == orderPo.getOrderStatus()) {
            if (1 == payWay) {//支付宝
                if (0 == payStatus) {
                    orderBo.setOrderStatus(OrderConstant.ORDER_STATUS_SUCCESS);
                    isSuccess = true;
                }
                else {
                    orderBo.setOrderStatus(OrderConstant.ORDER_STATUS_FAIL);
                }
            }
            else if (2 == payWay) {//微信
                if (0 == payStatus) {
                    orderBo.setOrderStatus(OrderConstant.ORDER_STATUS_SUCCESS);
                    isSuccess = true;
                }
                else {
                    orderBo.setOrderStatus(OrderConstant.ORDER_STATUS_FAIL);
                }
            }
            orderDao.updateOrderStatusById(orderBo);
            if (isSuccess) {
                AccountBo accountBo = new AccountBo();
                accountBo.setUserId(orderPo.getUserId());
                accountBo.setUserAccountType(AccountEnum.ACCOUNT_QB.getType());
                AccountPo accountPo = accountDao.selectAccountByUserIdAndType(accountBo);
                double newQBAmount = accountPo.getAmount() + orderPo.getQbAmount();
                accountBo.setAmount(newQBAmount);
                accountDao.updateAccountAmount(accountBo);
            }
        }
    }

    private String createOrderId(String userId) {
        Long orderSeq = orderDao.insertOrderSeq();
        StringBuffer sb = new StringBuffer();
        sb.append(Constant.ORDER_ID_PREFIX);
        DecimalFormat seqBaseDecimalFormat = new DecimalFormat("00000000");
        String seqBaseValue = seqBaseDecimalFormat.format(orderSeq%Constant.SEQ_BASE);
        sb.append(seqBaseValue);
        sb.append(DateUtil.formartDate(new Date(), DateUtil.YYYYMMDD));
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(10));
        }
        String value = userId.substring(userId.length()-2);
        sb.append(value);
        return sb.toString();
    }

    private String createAccountId(String userId) {
        Long orderSeq = accountDao.insertAccountSeq();
        StringBuffer sb = new StringBuffer();
        sb.append(Constant.ACCOUNT_ID_PREFIX);
        DecimalFormat seqBaseDecimalFormat = new DecimalFormat("00000000");
        String seqBaseValue = seqBaseDecimalFormat.format(orderSeq%Constant.SEQ_BASE);
        sb.append(seqBaseValue);
        sb.append(DateUtil.formartDate(new Date(), DateUtil.YYYYMMDD));
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(10));
        }
        String value = userId.substring(userId.length()-2);
        sb.append(value);
        return sb.toString();
    }
}
