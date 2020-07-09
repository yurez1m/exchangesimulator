package com.yura.ordermatcher.api.prices;

import lombok.Data;

@Data
public class Price {
    private String symbol;
    private double bidPrice;
    private long bidSize;
    private double askPrice;
    private long askSize;

    public Price() {
    }

    public Price(String symbol, double bidPrice, long bidSize, double askPrice, long askSize) {
        this.symbol = symbol;
        this.bidPrice = bidPrice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
    }

    public double getPriceForSide(boolean bid) {
        return bid ? bidPrice : askPrice;
    }

    public long getSizeForSide(boolean bid) {
        return bid ? bidSize : askSize;
    }
}
