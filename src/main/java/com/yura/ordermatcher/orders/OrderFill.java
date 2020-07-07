package com.yura.ordermatcher.orders;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class OrderFill {
    @Id
    private long id;
    private LocalDateTime time;
    private long orderId;
    private double price;
    private long size;

}
