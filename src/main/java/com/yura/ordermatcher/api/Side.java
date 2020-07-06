package com.yura.ordermatcher.api;

public enum Side {
    BUY, SELL;

    public Side getOpposite() {
        return this == BUY ? SELL : BUY;
    }
}
