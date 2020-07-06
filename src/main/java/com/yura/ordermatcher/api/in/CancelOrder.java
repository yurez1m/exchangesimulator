package com.yura.ordermatcher.api.in;

import lombok.Data;

@Data
public class CancelOrder {
    private String clientOrerId;
    private String requestId;
}
