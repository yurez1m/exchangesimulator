package com.yura.ordermatcher.model;

import com.yura.ordermatcher.api.OrderPriceType;
import com.yura.ordermatcher.api.Side;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private LocalDateTime time;
    private final String clientOrderId;
    private final long exchangeOrderId;
    private final String symbol;
    private OrderStatus orderStatus;
    private double price;
    private long initQty;
    private long cumQty;
    private Side side;
    private OrderPriceType orderPriceType;
    private long leftSize;

    public void addExecSize(long execSize) {
        this.cumQty += execSize;
        this.leftSize = this.initQty - this.cumQty;
    }
}
