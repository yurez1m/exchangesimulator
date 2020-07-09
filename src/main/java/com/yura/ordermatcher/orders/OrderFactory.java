package com.yura.ordermatcher.orders;

import com.yura.ordermatcher.api.orders.SubmitNewOrder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderFactory {
    private final IdGenerator idGenerator;

    public OrderFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Order fromSubmit(SubmitNewOrder submitNewOrder) {
        Order order = new Order(idGenerator.getNextId(), submitNewOrder.getClientOrderId(), submitNewOrder.getSymbol());
        order.setPrice(submitNewOrder.getPrice());
        order.setBid(submitNewOrder.isBid());
        order.setInitQty(submitNewOrder.getSize());
        order.setOrderType(submitNewOrder.getOrderType());
        order.setLeftSize(submitNewOrder.getSize());
        order.setOrderStatus(OrderStatus.CREATED);
//        order.setTime(LocalDateTime.now());
        return order;
    }
}
