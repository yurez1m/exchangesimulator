package com.yura.ordermatcher.orderbook;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class OrderBook {
    private final NavigableMap<Long, NavigableMap<Long, BookEntry>> buyOrders = new TreeMap<>(Comparator.comparingLong(o -> o));
    private final NavigableMap<Long, NavigableMap<Long, BookEntry>> sellOrders = new TreeMap<>((o1, o2) -> Long.compare(o2, o1));

    private final Map<Long, BookEntry> orders = new HashMap<>();

    public void addEntry(BookEntry bookEntry) {
        NavigableMap<Long, NavigableMap<Long, BookEntry>> oppositeOrders = bookBySide(bookEntry.isBid());
        long priceKey = NumberUtils.toLong(bookEntry.getPrice());
        oppositeOrders.computeIfAbsent(priceKey, aLong -> new TreeMap<>(Comparator.comparingLong(o -> o))).put(bookEntry.getId(), bookEntry);
        orders.put(bookEntry.getId(), bookEntry);
    }

    public BookEntry removeEntry(long orderId) {
        BookEntry bookEntry = orders.get(orderId);
        if (bookEntry != null) {
            long priceKey = NumberUtils.toLong(bookEntry.getPrice());
            SortedMap<Long, NavigableMap<Long, BookEntry>> oppositeOrders = bookBySide(bookEntry.isBid());
            oppositeOrders.get(priceKey).remove(orderId);
            if (oppositeOrders.get(priceKey).isEmpty()) {
                oppositeOrders.remove(priceKey);
            }
        }
        return bookEntry;
    }

    private NavigableMap<Long, NavigableMap<Long, BookEntry>> bookBySide(boolean bid) {
        return bid ? buyOrders : sellOrders;
    }

    public BookEntry getBestMatching(BookEntry bookEntry) {
        if (orderMatchesOrderBook(bookEntry.isBid(), bookEntry.getPrice())) {
            return bookBySide(!bookEntry.isBid()).lastEntry().getValue().firstEntry().getValue();
        }
        return null;
    }

    private boolean matchable(long priceKey, boolean bid, NavigableMap<Long, NavigableMap<Long, BookEntry>> orderBook) {
        return !orderBook.isEmpty() && (bid && priceKey >= orderBook.firstKey() ||
                !bid && priceKey <= orderBook.lastKey());
    }

    private boolean orderMatchesOrderBook(boolean bid, double price) {
        NavigableMap<Long, NavigableMap<Long, BookEntry>> oppositeOrders = bookBySide(!bid);
        long priceKey = NumberUtils.toLong(price);
        return matchable(priceKey, bid, oppositeOrders);
    }
}
