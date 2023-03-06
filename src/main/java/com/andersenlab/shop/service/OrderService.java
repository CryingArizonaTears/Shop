package com.andersenlab.shop.service;

import com.andersenlab.shop.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAll();
    List<OrderDto> getAllByUserId(Long id);
    OrderDto getById(Long id);
    void create(OrderDto orderDto);
    void edit(OrderDto orderDto);
    void delete(OrderDto orderDto);

}
