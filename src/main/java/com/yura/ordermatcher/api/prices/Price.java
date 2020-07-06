package com.yura.ordermatcher.api.prices;

import lombok.Data;

@Data
public class Price {
    private final String symbol;
    private final double bidPrice;
    private final long bidSize;
    private final double askPrice;
    private final long askSize;

    public double getPriceForSide(boolean bid) {
        return bid ? bidPrice : askPrice;
    }

    public long getSizeForSide(boolean bid) {
        return bid ? bidSize : askSize;
    }
}
