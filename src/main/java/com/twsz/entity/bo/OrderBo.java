package com.twsz.entity.bo;

import com.twsz.entity.dto.order.OrderDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderBo extends OrderDto {
    private String orderId;
    private int orderType;
    private int customType;
    private String operatorTime;
    private String userId;
    private int orderStatus;
}
