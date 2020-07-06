package com.yura.ordermatcher.services;

import com.yura.ordermatcher.api.in.SubmitNewOrder;
import com.yura.ordermatcher.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderFactory {
    private long idGenerator = 0;

    public Order fromSubmit(SubmitNewOrder submitNewOrder) {
        Order order = new Order(submitNewOrder.getClientOrderId(), ++idGenerator, submitNewOrder.getSymbol());
        order.setPrice(submitNewOrder.getPrice());
        order.setSide(submitNewOrder.getSide());
        order.setInitQty(submitNewOrder.getSize());
        order.setOrderPriceType(submitNewOrder.getOrderPriceType());
        return order;
    }
}
