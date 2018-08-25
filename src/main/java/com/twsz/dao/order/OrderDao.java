package com.twsz.dao.order;

import com.twsz.entity.bo.OrderBo;
import com.twsz.entity.po.order.OrderPo;

public interface OrderDao {
    OrderPo selectByThirdOrderId(OrderBo orderBo);
    Integer insertOrder(OrderBo orderBo);
    Long insertOrderSeq();
    Integer updateOrderStatusById(OrderBo orderBo);
    OrderPo selectByOrderId(OrderBo orderBo);
}
