package com.twsz.service;

import com.twsz.entity.Response;
import com.twsz.entity.dto.user.UserChangePwdDto;
import com.twsz.entity.dto.user.UserDto;
import com.twsz.entity.dto.user.UserLoginDto;
import com.twsz.entity.dto.user.UserResetPwdDto;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 11:44
 */
public interface UserService {
    Response registerUser(UserDto userDto);
    String createUserId();
    Response login(UserLoginDto userLoginDto);
    Response loginOut(String token);
    Boolean checkToken(String token);
    Response getUserInfo(String token);
    Response changeUserPassword(UserChangePwdDto userChangePwdDto);
    Response resetUserPassword(UserResetPwdDto userResetPwdDto);
}
