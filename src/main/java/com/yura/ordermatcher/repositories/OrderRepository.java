package com.yura.ordermatcher.repositories;

import com.yura.ordermatcher.model.Fill;
import com.yura.ordermatcher.orders.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
