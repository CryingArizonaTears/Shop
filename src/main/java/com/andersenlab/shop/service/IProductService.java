package com.andersenlab.shop.service;

import com.andersenlab.shop.dto.ProductDto;

import java.util.List;

public interface IProductService {

    List<ProductDto> getAll();
    ProductDto getById(Long id);

    void create(ProductDto productDto);

    void edit(ProductDto productDto);

    void delete(ProductDto productDto);
}
