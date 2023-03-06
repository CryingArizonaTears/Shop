package com.andersenlab.shop.service.user;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dao.IBucketDao;
import com.andersenlab.shop.dao.ICurrencyDao;
import com.andersenlab.shop.dao.IOrderDao;
import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.OrderDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.model.Currency;
import com.andersenlab.shop.model.Order;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.IOrderService;
import com.andersenlab.shop.service.IUserAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IOrderService {

    IOrderDao orderDao;
    ExtendedModelMapper modelMapper;
    IBucketDao bucketDao;
    ICurrencyDao currencyDao;
    IUserAuthenticationService userAuthenticationService;

    @Logging
    @Override
    public List<OrderDto> getAll() {
        return null;
    }

    @Logging
    @Override
    public List<OrderDto> getAllByUserId(Long id) {
        UserProfileDto currentUser = userAuthenticationService.getCurrent();
        List<Order> orders = orderDao.getAllByUserProfileId(currentUser.getId());
        return modelMapper.mapList(orders, OrderDto.class);
    }

    @Logging
    @SneakyThrows
    @Override
    public OrderDto getById(Long id) {
        UserProfileDto currentUser = userAuthenticationService.getCurrent();
        List<Order> orders = orderDao.getAllByUserProfileId(currentUser.getId());
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(id)) {
                return modelMapper.map(orders.get(i), OrderDto.class);
            }
        }
        throw new Exception("Order not found");
    }

    @Logging
    @Override
    public void create(OrderDto orderDto) {
        UserProfileDto currentUser = userAuthenticationService.getCurrent();
        BucketDto userBucket = currentUser.getBucketDto();
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (int i = 0; i < userBucket.getProducts().size(); i++) {
            totalPrice = totalPrice.add(userBucket.getProducts().get(i).getPrice());
        }
        if (orderDto.getCurrencyDto() != null) {
            Optional<Currency> currency = currencyDao.findById(orderDto.getCurrencyDto().getId());
            totalPrice = totalPrice.multiply(currency.get().getMultiplier());
        }
        orderDto.setTotalPrice(totalPrice);
        orderDto.setProcessed(false);
        orderDto.setDate(LocalDate.now());
        orderDto.setId(null);
        orderDao.save(modelMapper.map(orderDto, Order.class));
    }

    @Logging
    @Override
    public void edit(OrderDto orderDto) {

    }

    @Logging
    @Override
    public void delete(OrderDto orderDto) {

    }
}
