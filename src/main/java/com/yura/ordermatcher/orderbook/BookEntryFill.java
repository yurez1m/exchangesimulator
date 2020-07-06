package com.yura.ordermatcher.orderbook;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookEntryFill {
    private LocalDateTime time;
    private final double price;
    private final long entryId;
    private final long size;
    private final String execId;
}
