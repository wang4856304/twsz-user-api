package com.twsz.entity.bo;

import com.twsz.entity.dto.user.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 9:47
 */
@Data
@NoArgsConstructor
public class UserBo extends UserDto {

    /**
     * 令牌
     */
    private String userToken;

    /**
     * 令牌有效期
     */
    private long tokenExpire;

    /**
     * 令牌是否有效
     */
    private int tokenValid;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户code
     */
    private String userCode;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String userIcon;

    /**
     * 是否登录
     */
    private int isLogin;

    /**
     * 是否推送
     */
    private String pushMsg;

    /**
     * 最后登录时间
     */
    private String lastLoginTime;

    /**
     * 最后登录ip
     */
    private String lastLoginIp;

    /**
     * 是否首冲
     */
    private int isRecharge;

    /**
     * 账号状态
     */
    private int status;

    /**
     * 是否显示内容
     */
    private int display;

    /**
     * 用户等级
     */
    private int userLevel;

    /**
     * 原始密码
     */
    private String oldPassword;
}
