package com.twsz.enums.error;

public enum OrderErrorEnum {
    ORDER_EXISTS("3001", "订单已存在");
    private OrderErrorEnum(String code, String msg) {
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
