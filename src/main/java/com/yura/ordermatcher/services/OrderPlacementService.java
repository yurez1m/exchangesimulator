package com.yura.ordermatcher.services;

import com.yura.ordermatcher.api.in.SubmitNewOrder;
import com.yura.ordermatcher.model.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderPlacementService {
    private final OrderFactory orderFactory;
    private final MatchingService matchingService;
    private Map<String, OrderBook> orderBookBySymbol = new HashMap<>();

    public OrderPlacementService(OrderFactory orderFactory, MatchingService matchingService) {
        this.orderFactory = orderFactory;
        this.matchingService = matchingService;
    }

    public void placeOrder(SubmitNewOrder submitNewOrder) {
        Order order = orderFactory.fromSubmit(submitNewOrder);
        String symbol = order.getSymbol();
        OrderBook orderBook = orderBookBySymbol.computeIfAbsent(symbol, s -> new OrderBook());
        while (orderBook.orderMatchesOrderbook(order)) {
            matchingService.matchWithBook(order, orderBook);
        }
    }
}

