package com.twsz.dao.order.impl;

import com.twsz.dao.BaseDao;
import com.twsz.dao.order.OrderDao;
import com.twsz.entity.bo.OrderBo;
import com.twsz.entity.bo.OrderSeqBo;
import com.twsz.entity.po.order.OrderPo;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public OrderPo selectByThirdOrderId(OrderBo orderBo) {
        return masterSqlSessionTemplate.selectOne("Order.selectByThirdOrderId", orderBo);
    }

    @Override
    public Integer insertOrder(OrderBo orderBo) {
        return masterSqlSessionTemplate.insert("Order.insertOrder", orderBo);
    }

    @Override
    public Long insertOrderSeq() {
        OrderSeqBo orderSeqBo = new OrderSeqBo();
        orderSeqBo.setStub("a");
        masterSqlSessionTemplate.insert("Order.insertOrderSeq", orderSeqBo);
        return orderSeqBo.getOrderSeq();
    }

    @Override
    public Integer updateOrderStatusById(OrderBo orderBo) {
        return masterSqlSessionTemplate.update("Order.updateOrderStatusById", orderBo);
    }

    @Override
    public OrderPo selectByOrderId(OrderBo orderBo) {
        return masterSqlSessionTemplate.selectOne("Order.selectByOrderId", orderBo);
    }
}
