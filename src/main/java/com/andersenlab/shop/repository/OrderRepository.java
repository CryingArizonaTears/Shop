package com.andersenlab.shop.repository;

import com.andersenlab.shop.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> getAllByUserProfileId(Long id);
}
