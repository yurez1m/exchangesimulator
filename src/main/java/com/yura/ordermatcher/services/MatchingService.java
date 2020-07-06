package com.yura.ordermatcher.services;

import com.yura.ordermatcher.model.Fill;
import com.yura.ordermatcher.model.Order;
import com.yura.ordermatcher.repositories.FillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatchingService {

    private final FillRepository fillRepository;
    private final IdGenerator idGenerator;
    private final OrderStateMachine orderStateMachine;

    public MatchingService(FillRepository fillRepository, IdGenerator idGenerator, OrderStateMachine orderStateMachine) {
        this.fillRepository = fillRepository;
        this.idGenerator = idGenerator;
        this.orderStateMachine = orderStateMachine;
    }

    public void matchWithBook(Order order, OrderBook orderBook) {
        Order oppositeOrder;
        while (order.getLeftSize() > 0 && (oppositeOrder = orderBook.getBestMatching(order)) != null) {
            match(order, oppositeOrder);
            orderStateMachine.onFill(order, orderBook);
            orderStateMachine.onFill(oppositeOrder, orderBook);
        }
    }

    private void match(Order incomingOrder, Order bookOrder) {
        double price = bookOrder.getPrice();
        long size = Math.min(incomingOrder.getLeftSize(), incomingOrder.getLeftSize());
        if (size > 0) {
            incomingOrder.addExecSize(size);
            bookOrder.addExecSize(size);
            Fill incomingOrderFill = new Fill(LocalDateTime.now(), idGenerator.getNextId(), incomingOrder.getExchangeOrderId(), price, size);
            Fill bookOrderFill = new Fill(LocalDateTime.now(), idGenerator.getNextId(), bookOrder.getExchangeOrderId(), price, size);
            fillRepository.save(bookOrderFill);
            fillRepository.save(incomingOrderFill);
        }
    }
}
