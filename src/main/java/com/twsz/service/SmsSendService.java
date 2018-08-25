package com.twsz.service;

import com.twsz.entity.Response;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/18 11:46
 */
public interface SmsSendService {
    Response sendVerifyCode(String mobile);
}
