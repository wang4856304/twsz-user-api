package com.twsz.enums.error;

public enum ErrorEnum {
    PARAM_VALID_ERROR("2001", "参数校验错误"),
    TOKEN_EXPIRED("2002", "token令牌失效"),
    VERIFY_CODE_ERROR("2003", "获取验证码失败"),
    SIGN_ERROR("2004", "验签失败");

    private ErrorEnum(String code, String msg) {
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
