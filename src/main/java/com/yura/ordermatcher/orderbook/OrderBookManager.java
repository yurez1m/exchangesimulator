package com.yura.ordermatcher.orderbook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderBookManager {

    private final Logger logger = LogManager.getLogger(OrderBookManager.class);

    public final Map<String, OrderBook> bookBySymbol = new HashMap<>();

    private final MatchingService matchingService;

    public OrderBookManager(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    public synchronized void putEntry(BookEntry bookEntry, String symbol) {
        logger.info("Add new entry {} to {}", bookEntry, symbol);
        OrderBook orderBook = orderBook(symbol);
        matchWithBook(bookEntry, orderBook);
        addToBook(bookEntry, orderBook);
    }

    private void addToBook(BookEntry bookEntry, OrderBook orderBook) {
        if (bookEntry.getLeftSize() > 0) {
            orderBook.addEntry(bookEntry);
        }
    }

    private void matchWithBook(BookEntry bookEntry, OrderBook orderBook) {
        BookEntry matchingEntry;
        while ((matchingEntry = orderBook.getBestMatching(bookEntry)) != null && bookEntry.getLeftSize() > 0) {
            matchingService.match(matchingEntry, bookEntry);
            if (matchingEntry.getLeftSize() == 0) {
                orderBook.removeEntry(matchingEntry.getId());
            }
        }
    }

    public synchronized void removeEntry(String symbol, long orderId) {
        BookEntry bookEntry = orderBook(symbol).removeEntry(orderId);
        logger.info("Order {} removed", bookEntry);
    }

    private OrderBook orderBook(String symbol) {
        return bookBySymbol.computeIfAbsent(symbol, s -> new OrderBook());
    }
}
