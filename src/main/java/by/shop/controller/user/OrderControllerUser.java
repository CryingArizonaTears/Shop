package by.shop.controller.user;

import by.shop.annotation.Logging;
import by.shop.dto.OrderDto;
import by.shop.dto.UserProfileDto;
import by.shop.facade.OrderFacade;
import by.shop.facade.UserAuthFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/orders")
public class OrderControllerUser {

    OrderFacade orderFacade;
    UserAuthFacade userAuthFacade;

    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllByUserId() {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        return ResponseEntity.ok(orderFacade.getAllByUserId(currentUser.getId()));
    }

    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        return ResponseEntity.ok(orderFacade.getById(currentUser.getId(), id));
    }

    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto orderDto) {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        orderDto.setUserProfile(currentUser);
        orderDto.setProcessed(false);
        orderDto.setDate(LocalDate.now());
        orderDto.setId(null);
        return new ResponseEntity<>(orderFacade.create(orderDto), HttpStatus.CREATED);
    }
}
