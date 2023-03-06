package com.andersenlab.shop.controller.user;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.OrderDto;
import com.andersenlab.shop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user/order")
public class OrderControllerUser {

    @Autowired
    public OrderControllerUser(@Qualifier("orderService") IOrderService orderService) {
        this.orderService = orderService;
    }

    private final IOrderService orderService;

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllByUserId() {
        return ResponseEntity.ok(orderService.getAllByUserId(null));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderDto orderDto) {
        orderService.create(orderDto);
        return ResponseEntity.ok().build();
    }
}
