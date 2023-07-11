package by.shop.facade;

import by.shop.dto.OrderDto;

import java.util.List;

public interface OrderFacade {

    List<OrderDto> getAll();

    List<OrderDto> getAllByUserId(Long id);

    OrderDto getById(Long userId, Long id);

    OrderDto create(OrderDto orderDto);

    OrderDto edit(OrderDto orderDto);

    void delete(Long id);
}
