package com.twsz.controller;

import com.twsz.entity.Response;
import com.twsz.entity.dto.user.UserChangePwdDto;
import com.twsz.entity.dto.user.UserDto;
import com.twsz.entity.dto.user.UserLoginDto;
import com.twsz.entity.dto.user.UserResetPwdDto;
import com.twsz.service.UserService;
import com.twsz.utils.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/16 21:41
 */

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param userDto
     * @param bindingResult
     * @return
     */
    @RequestMapping("/register")
    public Object register(@RequestBody @Valid UserDto userDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return buildValidErrorJson(bindingResult);
        }
        String remoteIp = NetUtil.getIpAddress(request);
        String deviceId = request.getHeader("deviceId");
        String channelId = request.getHeader("channelId");
        userDto.setDeviceId(deviceId);
        userDto.setChannelId(channelId);
        userDto.setRemoteIp(remoteIp);

        Response response = userService.registerUser(userDto);
        return buildResponse(response);
    }

    /**
     * 登录
     * @param userLoginDto
     * @param bindingResult
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public Object login(@RequestBody @Valid UserLoginDto userLoginDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return buildValidErrorJson(bindingResult);
        }
        String remoteIp = NetUtil.getIpAddress(request);
        userLoginDto.setRemoteIp(remoteIp);
        Response response  = userService.login(userLoginDto);
        return buildResponse(response);
    }

    /**
     * 登出
     * @param token
     * @return
     */
    @RequestMapping("/loginOut")
    public Object loginOut(@RequestParam String token) {
        Response response = userService.loginOut(token);
        return buildResponse(response);
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @RequestMapping("/getUserInfo")
    public Object getUserInfo(@RequestParam String token) {
        Response response = userService.getUserInfo(token);
        return buildResponse(response);
    }


    /**
     * 修改密码
     * @param userChangePwdDto
     * @param bindingResult
     * @return
     */
    @RequestMapping("/changeUserPassword")
    public Object changeUserPassword(@Valid @RequestBody UserChangePwdDto userChangePwdDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return buildValidErrorJson(bindingResult);
        }
        Response response = userService.changeUserPassword(userChangePwdDto);
        return buildResponse(response);
    }

    /**
     * 修改密码
     * @param userResetPwdDto
     * @param bindingResult
     * @return
     */
    @RequestMapping("/resetUserPassword")
    public Object resetUserPassword(@Valid @RequestBody UserResetPwdDto userResetPwdDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return buildValidErrorJson(bindingResult);
        }
        Response response = userService.resetUserPassword(userResetPwdDto);
        return buildResponse(response);
    }
}
