package com.andersenlab.shop.controller.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.OrderDto;
import com.andersenlab.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/orders")
public class OrderControllerAdmin {

    @Autowired
    public OrderControllerAdmin(@Qualifier("orderServiceAdmin") OrderService orderService) {
        this.orderService = orderService;
    }

    private final OrderService orderService;

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderDto>> getAllByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getAllByUserId(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderDto orderDto) {
        orderService.create(orderDto);
        return ResponseEntity.ok().build();
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping()
    public ResponseEntity<Void> edit(@RequestBody OrderDto orderDto) {
        orderService.edit(orderDto);
        return ResponseEntity.ok().build();
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderService.delete(orderDto);
        return ResponseEntity.ok().build();
    }
}
