package com.yura.ordermatcher.orders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderStateMachine {

    private final Logger logger = LogManager.getLogger(OrderStateMachine.class);

    private final CrudRepository<Order, Long> orderLongCrudRepository;

    public OrderStateMachine(CrudRepository<Order, Long> orderLongCrudRepository) {
        this.orderLongCrudRepository = orderLongCrudRepository;
    }

    public void onFill(Order order, long size) {
        order.setLeftSize(order.getLeftSize() - size);
        if (order.getLeftSize() == 0) {
            moveStatus(order, OrderStatus.FILLED);
            orderLongCrudRepository.save(order);
        } else if (order.getOrderStatus() == OrderStatus.NEW) {
            moveStatus(order, OrderStatus.PARTIALLY_FILLED);
            orderLongCrudRepository.save(order);
        }
    }

    public void onSubmit(Order order) {
        moveStatus(order, OrderStatus.NEW);
        orderLongCrudRepository.save(order);
    }

    private void moveStatus(Order order, OrderStatus aNew) {
        logger.info("{} : {} -> {}", order.getExchangeOrderId(), order.getOrderStatus(), aNew);
        order.setOrderStatus(aNew);
    }

    public void onCancelled(Order order) {
        moveStatus(order, OrderStatus.CANCELLED);
        orderLongCrudRepository.save(order);
    }
}
