package by.shop.service;

import by.shop.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAll();

    List<Order> getAllByUserId(Long id);

    Order getById(Long userId, Long id);

    Order create(Order order);

    Order edit(Order order);

    void delete(Long id);

}
