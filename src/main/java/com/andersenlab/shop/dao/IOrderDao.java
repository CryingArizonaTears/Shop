package com.andersenlab.shop.dao;

import com.andersenlab.shop.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDao extends CrudRepository<Order, Long> {
    List<Order> getAllByUserProfileId(Long id);
}
