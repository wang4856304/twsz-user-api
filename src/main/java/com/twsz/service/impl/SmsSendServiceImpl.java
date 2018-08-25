package com.twsz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.twsz.constant.RedisKeyConstant;
import com.twsz.entity.Response;
import com.twsz.entity.dto.sms.SmsSendDto;
import com.twsz.entity.po.sms.SmsSendPo;
import com.twsz.enums.error.ErrorEnum;
import com.twsz.service.BaseService;
import com.twsz.service.SmsSendService;
import com.twsz.service.redis.RedisService;
import com.twsz.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/18 11:47
 */
@Service
public class SmsSendServiceImpl extends BaseService implements SmsSendService {

    @Value("${sms.account}")
    private String account;

    @Value("${sms.password}")
    private String password;

    @Value("${sms.url}")
    private String url;

    /** 短信发送成功 */
    public static final String SUCCESS="0";

    @Autowired
    private RedisService redisService;

    @Override
    public Response sendVerifyCode(String mobile) {
        StringBuffer sb = new StringBuffer();
        sb.append("【球胜】尊敬的用户，您的验证码为:");
        StringBuffer validCode = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int code = random.nextInt(9) + 1;
            validCode.append(code);
        }
        String msg = sb.append(validCode).append("，5分钟内有效").
                append("。请勿将此验证码告知他人。如非本人操作，请联系我们或忽略。").toString();


        SmsSendDto smsSendDto = SmsSendDto.builder().account(account)
                .password(password)
                .msg(msg)
                .phone(mobile)
                .report("true").build();
        String smsSendJson = JSONObject.toJSONString(smsSendDto);
        String smsResponse = HttpClientUtil.httpPostRequest(url, smsSendJson);
        SmsSendPo smsSendPo = JSONObject.parseObject(smsResponse, SmsSendPo.class);
        if (!SUCCESS.equals(smsSendPo.getCode())) {
            return buildErrorResponse(ErrorEnum.VERIFY_CODE_ERROR.getCode(), ErrorEnum.VERIFY_CODE_ERROR.getMsg());
        }
        redisService.set(RedisKeyConstant.VERIFY_CODE + mobile, validCode.toString(), 60*5);
        return buildSuccesResponse();
    }
}
