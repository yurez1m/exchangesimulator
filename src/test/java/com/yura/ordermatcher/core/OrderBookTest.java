package com.yura.ordermatcher.core;

import com.yura.ordermatcher.orderbook.BookEntry;
import com.yura.ordermatcher.orderbook.OrderBook;
import com.yura.ordermatcher.orders.Order;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class OrderBookTest {


    @Test
    public void testFindMatchableOrders() {
        OrderBook orderBook = new OrderBook();
        BookEntry order1 = createOrder(2d, 1000, true);
        orderBook.addEntry(order1);

        assertNull(orderBook.getBestMatching(createOrder(2.0001, 50, false)));
        assertNull(orderBook.getBestMatching(createOrder(2.0001, 50, true)));
        assertNull(orderBook.getBestMatching(createOrder(1, 50, true)));

        assertNotNull(orderBook.getBestMatching(createOrder(2, 1, false)));
        assertNotNull(orderBook.getBestMatching(createOrder(1, 100000, false)));
    }

    private long orderId = 0;

    private BookEntry createOrder(double price, int size, boolean bid) {
        return new BookEntry(orderId++, price, size, bid) {
            @Override
            public void onMatch(double price, long size) {

            }
        };
    }
}