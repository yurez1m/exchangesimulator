package com.yura.ordermatcher.orders;

import com.yura.ordermatcher.api.orders.OrderPriceType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Order {
    @Id
    private final long exchangeOrderId;
    private LocalDateTime time;
    private final String clientOrderId;
    private final String symbol;
    private OrderStatus orderStatus;
    private double price;
    private long initQty;
    private long cumQty;
    private boolean bid;
    private OrderPriceType orderPriceType;
    private long leftSize;
}
