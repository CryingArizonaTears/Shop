package com.andersenlab.shop.service;

import com.andersenlab.shop.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product getById(Long id);

    Product create(Product product);

    Product edit(Product product);

    void delete(Long id);
}
