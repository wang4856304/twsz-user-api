package com.twsz.entity.po.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderPo {
    private String orderId;
    private String thirdOrderId;
    private String userId;
    private Integer orderType;
    private Integer customType;
    private Integer customWay;
    private Double qbAmount;
    private Double rmbAmount;
    private int orderStatus;
    private String operatorTime;
}
