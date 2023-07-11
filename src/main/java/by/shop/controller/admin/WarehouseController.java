package by.shop.controller.admin;

import by.shop.annotation.Logging;
import by.shop.dto.WarehouseDto;
import by.shop.facade.WarehouseFacade;
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
@RequestMapping(value = "/admin/warehouses")
public class WarehouseController {

    WarehouseFacade warehouseFacade;

    @GetMapping
    public ResponseEntity<List<WarehouseDto>> getAll() {
        return ResponseEntity.ok(warehouseFacade.getAll());
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseFacade.getById(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<WarehouseDto> create(@RequestBody WarehouseDto warehouseDto) {
        return new ResponseEntity<>(warehouseFacade.create(warehouseDto), HttpStatus.CREATED);
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping()
    public ResponseEntity<WarehouseDto> edit(@RequestBody WarehouseDto warehouseDto) {
        return ResponseEntity.ok(warehouseFacade.edit(warehouseDto));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        warehouseFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
