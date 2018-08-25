package com.twsz.enums.error;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 14:05
 */
public enum UserEnum {

    MOBILE_EXISTS("1002", "手机号已注册"),
    USER_NAME_PWD_ERROR("1003", "用户名或密码错误"),
    ALREDY_LOGIN_ERROR("1004", "你已登录"),
    NOT_REGISDTER("1005", "请先注册"),
    INVALID_VERIFY_CODE("1006", "验证码错误"),
    CONFIRM_PWD_ERROR("1007", "两次输入密码不一致"),
    NICK_NAME_EXISTS("1008", "昵称已存在");

    private UserEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
