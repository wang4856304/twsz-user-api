package com.twsz.enums.error;

/**
 * @author wangjun
 * @date 18-2-23 下午2:29
 * @description
 * @modified by
 */
public enum ResultEnum {

    SUCCESS("0", "成功"), FAIL("-1", "系统错误");

    private ResultEnum(String code, String msg) {
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
