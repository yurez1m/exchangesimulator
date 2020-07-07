package com.yura.ordermatcher.repositories;

import com.yura.ordermatcher.orders.OrderFill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderFillRepository extends CrudRepository<OrderFill, Long> {
}
