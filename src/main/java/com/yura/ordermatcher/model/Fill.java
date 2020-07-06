package com.yura.ordermatcher.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Fill {
    private final LocalDateTime time;
    private final long executionId;
    private final long orderId;
    private final double price;
    private final long size;
}
