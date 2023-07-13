package by.shop.controller.admin;

import by.shop.annotation.Logging;
import by.shop.dto.OrderDto;
import by.shop.facade.OrderFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/admin/orders")
public class OrderControllerAdmin {

    OrderFacade orderFacade;

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        return ResponseEntity.ok(orderFacade.getAll());
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderDto>> getAllByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(orderFacade.getAllByUserId(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderFacade.getById(null, id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderFacade.createAsAdmin(orderDto), HttpStatus.CREATED);
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping()
    public ResponseEntity<OrderDto> edit(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderFacade.edit(orderDto));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
