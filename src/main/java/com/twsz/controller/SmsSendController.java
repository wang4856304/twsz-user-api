package com.twsz.controller;

import com.twsz.entity.Response;
import com.twsz.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/18 11:44
 */
@RestController
@RequestMapping("/smsSend")
@Validated
public class SmsSendController extends BaseController {

    @Autowired
    private SmsSendService smsSendService;

    @RequestMapping("/sendVerifyCode")
    public Object sendVerifyCode(@RequestParam @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "请输入正确的电话号码") String mobile) {

        Response response = smsSendService.sendVerifyCode(mobile);
        return buildResponse(response);
    }
}
