package com.yura.ordermatcher.services;

import com.yura.ordermatcher.model.Order;
import com.yura.ordermatcher.model.OrderStatus;
import com.yura.ordermatcher.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderStateMachine {

    private final OrderRepository orderRepository;

    public OrderStateMachine(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void onFill(Order order, OrderBook orderBook) {
        if (order.getLeftSize() == 0) {
            order.setOrderStatus(OrderStatus.FILLED);
            orderRepository.save(order);
            orderBook.removeOrder(order.getExchangeOrderId());
        } else if (order.getOrderStatus() == OrderStatus.NEW) {
            order.setOrderStatus(OrderStatus.PARTIALLY_FILLED);
            orderRepository.save(order);
        }
    }

    public void onNew(Order order) {
        order.setOrderStatus(OrderStatus.NEW);
        orderRepository.save(order);
    }

    public void onCancel(Order order, OrderBook orderBook) {
        if (order.getOrderStatus() == OrderStatus.NEW || order.getOrderStatus() == OrderStatus.PARTIALLY_FILLED) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            orderBook.removeOrder(order.getExchangeOrderId());
        }
    }
}
