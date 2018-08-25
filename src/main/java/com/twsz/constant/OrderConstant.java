package com.twsz.constant;

public class OrderConstant {
    public static final int ORDER_TYPE_IN = 1;//入账
    public static final int ORDER_TYPE_OUT = 2;//出账
    public static final int CUSTOM_TYPE_QB = 2;//球币
    public static final int ORDER_TYPE_RMB = 1;//现金

    public static final int ORDER_STATUS_START = 0;//待确认
    public static final int ORDER_STATUS_SUCCESS = 1;//成功
    public static final int ORDER_STATUS_FAIL = 2;//失败
    public static final int ORDER_STATUS_REFUND = 3;//待退款
    public static final int ORDER_STATUS_REFUNDED = 4;//已退款
    public static final int ORDER_STATUS_CANCEL = 5;//已取消
}
