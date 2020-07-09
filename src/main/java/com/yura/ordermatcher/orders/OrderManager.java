package com.yura.ordermatcher.orders;

import com.yura.ordermatcher.orderbook.BookEntry;
import com.yura.ordermatcher.orderbook.OrderBookManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderManager {

    private final Logger logger = LogManager.getLogger(OrderManager.class);
    private final OrderBookManager orderBookManager;
    private final OrderTradingRegister orderTradingRegister;
    private final OrderStateMachine orderStateMachine;

    private final Map<String, Order> ordersByClientOrderId = new ConcurrentHashMap<>();

    public OrderManager(OrderBookManager orderBookManager, OrderTradingRegister orderTradingRegister, OrderStateMachine orderStateMachine) {
        this.orderBookManager = orderBookManager;
        this.orderTradingRegister = orderTradingRegister;
        this.orderStateMachine = orderStateMachine;
    }

    public void placeOrder(Order order) {
        logger.info("On submit new order {} ", order);
        ordersByClientOrderId.put(order.getClientOrderId(), order);
        BookEntry bookEntry = convertToBookEntry(order);
        orderStateMachine.onSubmit(order);
        orderBookManager.putEntry(bookEntry, order.getSymbol());
    }

    private BookEntry convertToBookEntry(Order order) {
        return new BookEntry(order.getExchangeOrderId(), order.getPrice(), order.getLeftSize(), order.isBid()) {
            @Override
            public void onMatch(double price, long size) {
                orderTradingRegister.onOrderTraded(order, size, price);
                orderStateMachine.onFill(order, size);
            }
        };
    }

    public void cancelOrder(String clientOrderId) {
        logger.info("On cancel order {}", clientOrderId);
        Order order = ordersByClientOrderId.get(clientOrderId);
        if (order != null) {
            orderBookManager.removeEntry(order.getSymbol(), order.getExchangeOrderId());
            orderStateMachine.onCancelled(order);
            logger.info("Order {} removed", order);
        } else {
            logger.info("Cannot find order by id {} for cancel", clientOrderId);
        }
    }

    public Order getOrder(String clientOrderId) {
        return ordersByClientOrderId.get(clientOrderId);
    }

    public Collection<Order> getAllOrders() {
        return ordersByClientOrderId.values();
    }
}
