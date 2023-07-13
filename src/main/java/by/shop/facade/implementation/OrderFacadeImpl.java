package by.shop.facade.implementation;

import by.shop.annotation.Logging;
import by.shop.dto.OrderDto;
import by.shop.facade.OrderFacade;
import by.shop.model.Order;
import by.shop.modelMapperMethods.ExtendedModelMapper;
import by.shop.service.BucketService;
import by.shop.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    OrderService orderService;
    BucketService bucketService;
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
    @Transactional
    public OrderDto createAsAdmin(OrderDto orderDto) {
        OrderDto retOrderDto = modelMapper.map(orderService.createAsAdmin(modelMapper.map(orderDto, Order.class)), OrderDto.class);
        bucketService.clearBucket(orderDto.getUserProfile().getBucket().getId());
        return retOrderDto;
    }

    @Logging
    @Override
    @Transactional
    public OrderDto createAsUser(OrderDto orderDto) {
        OrderDto retOrderDto = modelMapper.map(orderService.createAsAdmin(modelMapper.map(orderDto, Order.class)), OrderDto.class);
        bucketService.clearBucket(orderDto.getUserProfile().getBucket().getId());
        return retOrderDto;
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
