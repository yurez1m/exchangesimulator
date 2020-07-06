package com.yura.ordermatcher.services;

import com.yura.ordermatcher.api.Side;
import com.yura.ordermatcher.model.Order;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class OrderBook {
    private final NavigableMap<Long, NavigableMap<Long, Order>> buyOrders = new TreeMap<>(Comparator.comparingLong(o -> o));
    private final NavigableMap<Long, NavigableMap<Long, Order>> sellOrders = new TreeMap<>((o1, o2) -> Long.compare(o2, o1));

    private final Map<Long, Order> orders = new HashMap<>();

    public void placeOrder(Order order) {
        NavigableMap<Long, NavigableMap<Long, Order>> oppositeOrders = bookBySide(order.getSide());
        long priceKey = NumberUtils.toLong(order.getPrice());
        oppositeOrders.computeIfAbsent(priceKey, aLong -> new TreeMap<>(Comparator.comparingLong(o -> o))).put(order.getExchangeOrderId(), order);
        orders.put(order.getExchangeOrderId(), order);
    }

    public Order removeOrder(long orderId) {
        Order internalOrder = orders.get(orderId);
        if (internalOrder != null) {
            long priceKey = NumberUtils.toLong(internalOrder.getPrice());
            SortedMap<Long, NavigableMap<Long, Order>> oppositeOrders = bookBySide(internalOrder.getSide());
            oppositeOrders.get(priceKey).remove(orderId);
            if (oppositeOrders.get(priceKey).isEmpty()) {
                oppositeOrders.remove(priceKey);
            }
        }
        return internalOrder;
    }

    private NavigableMap<Long, NavigableMap<Long, Order>> bookBySide(Side side) {
        return side == Side.BUY ? buyOrders : sellOrders;
    }

    public Order getBestMatching(Order order) {
        if (orderMatchesOrderbook(order)) {
            return bookBySide(order.getSide().getOpposite()).lastEntry().getValue().firstEntry().getValue();
        }
        return null;
    }

    private boolean matchable(long priceKey, Side side, NavigableMap<Long, NavigableMap<Long, Order>> orderBook) {
        return !orderBook.isEmpty() && (side == Side.BUY && priceKey >= orderBook.firstKey() ||
                side == Side.SELL && priceKey <= orderBook.lastKey());
    }

    public boolean orderMatchesOrderbook(Order order) {
        NavigableMap<Long, NavigableMap<Long, Order>> oppositeOrders = bookBySide(order.getSide().getOpposite());
        long priceKey = NumberUtils.toLong(order.getPrice());
        return matchable(priceKey, order.getSide(), oppositeOrders);
    }
}
