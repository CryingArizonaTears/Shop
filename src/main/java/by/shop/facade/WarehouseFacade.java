package by.shop.facade;

import by.shop.dto.WarehouseDto;

import java.util.List;

public interface WarehouseFacade {
    List<WarehouseDto> getAll();

    WarehouseDto getById(Long id);

    WarehouseDto create(WarehouseDto warehouseDto);

    WarehouseDto edit(WarehouseDto warehouseDto);

    void delete(Long id);
}
