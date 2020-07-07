package com.yura.ordermatcher.orders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderTradingRegister {

    private final Logger logger = LogManager.getLogger(OrderTradingRegister.class);

    private final CrudRepository<OrderFill, Long> orderFillRepository;
    private final IdGenerator idGenerator;

    public OrderTradingRegister(CrudRepository<OrderFill, Long> orderFillRepository, IdGenerator idGenerator) {
        this.orderFillRepository = orderFillRepository;
        this.idGenerator = idGenerator;
    }

    public void onOrderTraded(Order order, long size, double price) {
        OrderFill orderFill = new OrderFill();
        orderFill.setPrice(price);
        orderFill.setSize(size);
        orderFill.setTime(LocalDateTime.now());
        orderFill.setId(idGenerator.getNextId());
        orderFill.setOrderId(order.getExchangeOrderId());
        orderFillRepository.save(orderFill);
        logger.info("Saved order fill {}", orderFill);
    }
}
