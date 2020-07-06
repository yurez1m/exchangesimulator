package com.yura.ordermatcher.orderbook;

import lombok.Data;

@Data
public abstract class BookEntry {
    private final double price;
    private final long size;
    private long tradedSize;
    private final boolean bid;
    private final long id;

    public BookEntry(long id, double price, long size, boolean bid) {
        this.id = id;
        this.price = price;
        this.size = size;
        this.bid = bid;
    }

    public void addTradedSize(long tradedSize) {
        this.tradedSize += tradedSize;
    }

    public long getLeftSize() {
        return size - tradedSize;
    }

    public abstract void onMatch(double price, long
            size);
}
