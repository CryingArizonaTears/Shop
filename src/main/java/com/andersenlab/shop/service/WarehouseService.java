package com.andersenlab.shop.service;

import com.andersenlab.shop.dto.WarehouseDto;

import java.util.List;

public interface WarehouseService {

    List<WarehouseDto> getAll();
    WarehouseDto getById(Long id);

    void create(WarehouseDto warehouseDto);

    void edit(WarehouseDto warehouseDto);

    void delete(WarehouseDto warehouseDto);
}
