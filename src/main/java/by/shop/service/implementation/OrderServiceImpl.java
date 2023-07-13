package by.shop.service.implementation;

import by.shop.annotation.Logging;
import by.shop.model.Currency;
import by.shop.model.Order;
import by.shop.model.Product;
import by.shop.repository.OrderRepository;
import by.shop.service.CurrencyService;
import by.shop.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    CurrencyService currencyService;

    @Logging
    @Override
    public List<Order> getAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Logging
    @Override
    public List<Order> getAllByUserId(Long id) {
        return (List<Order>) orderRepository.getAllByUserProfileId(id);
    }

    @Logging
    @Override
    public Order getById(Long userId, Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            if (userId == null) {
                return order.get();
            }
            if (order.get().getUserProfile().getId().equals(userId)) {
                return order.get();
            }
        }
        throw new EntityNotFoundException("Order with id " + id + " not found");
    }

    @Logging
    @Override
    public Order createAsAdmin(Order order) {
        Order orderForSave = fillOrderData(order);
        return orderRepository.save(orderForSave);
    }

    @Logging
    @Override
    public Order createAsUser(Order order) {
        order.setProcessed(false);
        order.setDate(LocalDate.now());
        order.setId(null);
        Order orderForSave = fillOrderData(order);
        return orderRepository.save(orderForSave);
    }

    @Logging
    @Override
    public Order edit(Order order) {
        Order orderFromRepo = getById(null, order.getId());
        if (order.getUserProfile() != null) {
            orderFromRepo.setUserProfile(order.getUserProfile());
        }
        if (order.getProducts() != null) {
            orderFromRepo.setProducts(order.getProducts());
        }
        if (order.getCurrency() != null) {
            Currency currencyFromRepo = currencyService.getById(order.getCurrency().getId());
            orderFromRepo.setTotalPrice(orderFromRepo.getTotalPrice().divide(orderFromRepo.getCurrency().getMultiplier()));
            orderFromRepo.setCurrency(currencyFromRepo);
            orderFromRepo.setTotalPrice(currencyFromRepo.getMultiplier().multiply(orderFromRepo.getTotalPrice()));
        }
        if (order.getDate() != null) {
            orderFromRepo.setDate(order.getDate());
        }
        if (order.getProcessed() != null) {
            orderFromRepo.setProcessed(order.getProcessed());
        }
        if (order.getTotalPrice() != null) {
            orderFromRepo.setTotalPrice(order.getTotalPrice());
        }
        return orderRepository.save(orderFromRepo);
    }

    @Logging
    @Override
    public void delete(Long id) {
        Order existingOrder = getById(null, id);
        orderRepository.delete(existingOrder);
    }

    private Order fillOrderData(Order order) {
        BigDecimal totalPrice = order.getUserProfile().getBucket().getTotalPrice();
        List<Product> products = order.getUserProfile().getBucket().getProducts();
        if (order.getCurrency() != null) {
            Currency currency = currencyService.getById(order.getCurrency().getId());
            totalPrice = totalPrice.multiply(currency.getMultiplier());
        }
        order.setTotalPrice(totalPrice);
        order.setProducts(products);
        return order;
    }
}
