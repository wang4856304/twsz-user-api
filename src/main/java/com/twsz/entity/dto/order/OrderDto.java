package com.twsz.entity.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class OrderDto {

    /**
     * 第三方订单id
     */
    @NotBlank(message = "业务订单号为空")
    private String thirdOrderId;

    /**
     * 令牌
     */
    @NotBlank(message = "token为空")
    private String token;

    /**
     * 消费方式
     */
    @NotNull(message = "消费方式为空")
    private int customWay;

    /**
     * 金额
     */
    @NotNull(message = "球币金额为空")
    private Double qbAmount;
    /**
     * 金额
     */
    @NotNull(message = "现金金额为空")
    private Double rmbAmount;


    /**
     * 时间戳
     */
    private Long time;

    @NotNull(message = "签名为空")
    private String sign;
}
