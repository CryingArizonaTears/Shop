package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.repository.BucketRepository;
import com.andersenlab.shop.repository.CurrencyRepository;
import com.andersenlab.shop.repository.OrderRepository;
import com.andersenlab.shop.dto.OrderDto;
import com.andersenlab.shop.model.Bucket;
import com.andersenlab.shop.model.Currency;
import com.andersenlab.shop.model.Order;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceAdminImpl implements OrderService {

    OrderRepository orderRepository;
    ExtendedModelMapper modelMapper;
    BucketRepository bucketRepository;
    CurrencyRepository currencyRepository;

    @Logging
    @Override
    public List<OrderDto> getAll() {
        List<Order> orders = (List<Order>) orderRepository.findAll();
        return modelMapper.mapList(orders, OrderDto.class);
    }

    @Logging
    @Override
    public List<OrderDto> getAllByUserId(Long id) {
        List<Order> orders = orderRepository.getAllByUserProfileId(id);
        return modelMapper.mapList(orders, OrderDto.class);
    }

    @Logging
    @Override
    public OrderDto getById(Long id) {
        return modelMapper.map(orderRepository.findById(id), OrderDto.class);
    }

    @Logging
    @SneakyThrows
    @Override
    public void create(OrderDto orderDto) {
        Optional<Bucket> bucket = bucketRepository.findById(orderDto.getUserProfileDto().getId());
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (int i = 0; i < bucket.get().getProducts().size(); i++) {
            totalPrice = totalPrice.add(bucket.get().getProducts().get(i).getPrice());
        }
        if (orderDto.getCurrencyDto() != null) {
            Optional<Currency> currency = currencyRepository.findById(orderDto.getCurrencyDto().getId());
            totalPrice = totalPrice.multiply(currency.get().getMultiplier());
        }
        orderDto.setTotalPrice(totalPrice);
        orderRepository.save(modelMapper.map(orderDto, Order.class));
    }

    @Logging
    @Override
    public void edit(OrderDto orderDto) {
        OrderDto order = getById(orderDto.getId());
        if (orderDto.getUserProfileDto() != null) {
            order.setUserProfileDto(orderDto.getUserProfileDto());
        }
        if (orderDto.getProducts() != null) {
            order.setProducts(orderDto.getProducts());
        }
        if (orderDto.getCurrencyDto() != null) {
            Optional<Currency> currency = currencyRepository.findById(orderDto.getCurrencyDto().getId());
            order.setCurrencyDto(orderDto.getCurrencyDto());
            order.setTotalPrice(currency.get().getMultiplier().multiply(order.getTotalPrice()));
        }
        if (orderDto.getDate() != null) {
            order.setDate(orderDto.getDate());
        }
        if (orderDto.getProcessed() != null) {
            order.setProcessed(orderDto.getProcessed());
        }
        if (orderDto.getTotalPrice() != null) {
            order.setTotalPrice(orderDto.getTotalPrice());
        }
        orderRepository.save(modelMapper.map(order, Order.class));
    }

    @Logging
    @Override
    public void delete(OrderDto orderDto) {
        orderRepository.delete(modelMapper.map(orderDto, Order.class));
    }
}
