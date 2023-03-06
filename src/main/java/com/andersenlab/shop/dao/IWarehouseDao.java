package com.andersenlab.shop.dao;

import com.andersenlab.shop.model.Warehouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWarehouseDao extends CrudRepository<Warehouse, Long> {
}
