package com.yura.ordermatcher.repositories;

import com.yura.ordermatcher.orders.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Long> {
}
