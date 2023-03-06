package com.andersenlab.shop.dao;

import com.andersenlab.shop.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductDao extends CrudRepository<Product, Long> {
}
