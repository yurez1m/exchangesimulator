package com.yura.ordermatcher.api.orders;

import lombok.Data;

@Data
public class CancelOrder {
    private String clientOrderId;
}
