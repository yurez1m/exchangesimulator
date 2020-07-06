package com.yura.ordermatcher.orderbook;

import org.springframework.stereotype.Service;

@Service
class MatchingService {
    public void match(BookEntry entry1, BookEntry entry2) {
        long size = Math.min(entry1.getLeftSize(), entry2.getLeftSize());
        double price = entry1.getPrice();
        entry1.onMatch(price, size);
        entry2.onMatch(price, size);
        entry1.addTradedSize(size);
        entry2.addTradedSize(size);
    }
}
