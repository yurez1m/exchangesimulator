package com.yura.ordermatcher.orders;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.yura.ordermatcher.api.orders.OrderType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Order {
    @Id
    private long exchangeOrderId;
    private LocalDateTime time;
    private String clientOrderId;
    private String symbol;
    private OrderStatus orderStatus;
    private double price;
    private long initQty;
    private long cumQty;
    private boolean bid;
    private OrderType orderType;
    private long leftSize;

    public Order() {
    }

    public Order(long exchangeOrderId, String clientOrderId, String symbol) {
        this.exchangeOrderId = exchangeOrderId;
        this.clientOrderId = clientOrderId;
        this.symbol = symbol;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getTime() {
        return time;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public long getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(long exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getInitQty() {
        return initQty;
    }

    public void setInitQty(long initQty) {
        this.initQty = initQty;
    }

    public long getCumQty() {
        return cumQty;
    }

    public void setCumQty(long cumQty) {
        this.cumQty = cumQty;
    }

    public boolean isBid() {
        return bid;
    }

    public void setBid(boolean bid) {
        this.bid = bid;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public long getLeftSize() {
        return leftSize;
    }

    public void setLeftSize(long leftSize) {
        this.leftSize = leftSize;
    }
}
