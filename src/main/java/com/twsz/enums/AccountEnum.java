package com.twsz.enums;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/19 11:56
 */
public enum AccountEnum {

    ACCOUNT_QB(1, "球币账户"),
    ACCOUNT_RMB(2, "现金账户");

    private AccountEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
