package com.andersenlab.shop.facade.implementation;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.OrderDto;
import com.andersenlab.shop.facade.OrderFacade;
import com.andersenlab.shop.model.Order;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    OrderService orderService;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<OrderDto> getAll() {
        return modelMapper.mapList(orderService.getAll(), OrderDto.class);
    }

    @Logging
    @Override
    public List<OrderDto> getAllByUserId(Long id) {
        return modelMapper.mapList(orderService.getAllByUserId(id), OrderDto.class);
    }

    @Logging
    @Override
    public OrderDto getById(Long userId, Long id) {
        return modelMapper.map(orderService.getById(userId, id), OrderDto.class);
    }

    @Logging
    @Override
    public OrderDto create(OrderDto orderDto) {
        return modelMapper.map(orderService.create(modelMapper.map(orderDto, Order.class)), OrderDto.class);
    }

    @Logging
    @Override
    public OrderDto edit(OrderDto orderDto) {
        return modelMapper.map(orderService.edit(modelMapper.map(orderDto, Order.class)), OrderDto.class);
    }

    @Logging
    @Override
    public void delete(Long id) {
        orderService.delete(id);
    }
}
