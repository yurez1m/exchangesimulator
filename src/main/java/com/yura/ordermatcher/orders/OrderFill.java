package com.yura.ordermatcher.orders;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class OrderFill {
    @Id
    private long id;
    private LocalDateTime time;
    private long orderId;
    private double price;
    private long size;

}
