package com.yura.ordermatcher.api.in;

import com.yura.ordermatcher.api.OrderPriceType;
import com.yura.ordermatcher.api.Side;
import lombok.Data;

@Data
public class SubmitNewOrder {
    private String clientOrderId;
    private String symbol;
    private double price;
    private int size;
    private OrderPriceType orderPriceType;
    private Side side;
}
