package com.yura.ordermatcher.api.orders;

import lombok.Data;

@Data
public class SubmitNewOrder {
    private String clientOrderId;
    private String symbol;
    private double price;
    private int size;
    private OrderType orderType;
    private boolean bid;


}
