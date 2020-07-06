package com.yura.ordermatcher.repositories;

import com.yura.ordermatcher.model.Fill;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface FillRepository extends CrudRepository<Fill, Long> {
}
