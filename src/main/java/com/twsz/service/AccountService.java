package com.twsz.service;

import com.twsz.entity.Response;
import com.twsz.entity.bo.AccountBo;
import com.twsz.entity.dto.order.OrderDto;

public interface AccountService {
    Response reCharge(OrderDto orderDto);
    void createAccount(String userId);
    void updateAccount(String orderId, int payStatus, int payWay);
}
