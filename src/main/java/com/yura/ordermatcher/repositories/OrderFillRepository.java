package com.yura.ordermatcher.repositories;

import com.yura.ordermatcher.orders.OrderFill;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderFillRepository extends MongoRepository<OrderFill, Long> {
}
