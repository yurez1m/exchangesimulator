package com.yura.ordermatcher.core;

import com.yura.ordermatcher.api.OrderPriceType;
import com.yura.ordermatcher.api.Side;
import com.yura.ordermatcher.model.Order;
import com.yura.ordermatcher.services.OrderBook;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class OrderBookTest {

    @Test
    public void testFindMatchableOrders() {
        OrderBook orderBook = new OrderBook();
        Order order1 = createOrder(2d, 1000, Side.BUY);
        orderBook.placeOrder(order1);

        assertTrue(orderBook.getBestMatching(createOrder(2.0001, 50, Side.SELL)) == null);
        assertTrue(orderBook.getBestMatching(createOrder(2.0001, 50, Side.BUY)) == null);
        assertTrue(orderBook.getBestMatching(createOrder(1, 50, Side.BUY)) == null);

        assertTrue(orderBook.getBestMatching(createOrder(2, 1, Side.SELL)) != null);
        assertTrue(orderBook.getBestMatching(createOrder(1, 100000, Side.SELL)) != null);
    }

    private long orderId = 0;

    private Order createOrder(double price, int size, Side side) {
        orderId++;
        Order order = new Order(String.valueOf(orderId), orderId, "USD/RUB");
        order.setPrice(price);
        order.setInitQty(size);
        order.setSide(side);
        order.setOrderPriceType(Double.isNaN(price) ? OrderPriceType.MARKET : OrderPriceType.LIMIT);
        return order;
    }
}