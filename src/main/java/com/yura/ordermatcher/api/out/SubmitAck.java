package com.yura.ordermatcher.api.out;

import lombok.Data;

@Data
public class SubmitAck {
    private long exchangeOrderId;
    private String clientOrderId;
}
