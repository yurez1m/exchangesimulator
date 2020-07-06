package com.yura.ordermatcher.api.orders;

import lombok.Data;

@Data
public class SubmitAck {
    private final long exchangeOrderId;
    private final String clientOrderId;
}
