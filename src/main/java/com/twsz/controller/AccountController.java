package com.twsz.controller;

import com.twsz.entity.Response;
import com.twsz.entity.dto.order.OrderDto;
import com.twsz.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 账户
 * date 2018.08.18
 */

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/recharge")
    public Object recharge(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return buildValidErrorJson(bindingResult);
        }
        Response response = accountService.reCharge(orderDto);
        return buildResponse(response);
    }

    /**
     * 支付宝回调
     * @param request
     * @return
     */
    @RequestMapping("/aliPayCallback")
    public Object aliPayCallback(HttpServletRequest request) {
        String orderId = request.getParameter("orderId");
        accountService.updateAccount(orderId, 0, 1);
        Response response = new Response();
        return buildResponse(response);
    }

    /**
     * 微信回调
     * @param request
     * @return
     */
    @RequestMapping("/weChatPayCallback")
    public Object weChatPayCallback(HttpServletRequest request) {
        String orderId = request.getParameter("orderId");
        accountService.updateAccount(orderId, 0, 2);
        Response response = new Response();
        return buildResponse(response);
    }
}
