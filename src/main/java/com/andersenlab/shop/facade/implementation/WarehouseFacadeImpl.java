package com.andersenlab.shop.facade.implementation;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.WarehouseDto;
import com.andersenlab.shop.facade.WarehouseFacade;
import com.andersenlab.shop.model.Warehouse;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.WarehouseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WarehouseFacadeImpl implements WarehouseFacade {

    WarehouseService warehouseService;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<WarehouseDto> getAll() {
        return modelMapper.mapList(warehouseService.getAll(), WarehouseDto.class);
    }

    @Logging
    @Override
    public WarehouseDto getById(Long id) {
        return modelMapper.map(warehouseService.getById(id), WarehouseDto.class);
    }

    @Logging
    @Override
    public WarehouseDto create(WarehouseDto warehouseDto) {
        return modelMapper.map(warehouseService.create(modelMapper.map(warehouseDto, Warehouse.class)), WarehouseDto.class);
    }

    @Logging
    @Override
    public WarehouseDto edit(WarehouseDto warehouseDto) {
        return modelMapper.map(warehouseService.edit(modelMapper.map(warehouseDto, Warehouse.class)), WarehouseDto.class);
    }

    @Logging
    @Override
    public void delete(Long id) {
        warehouseService.delete(id);
    }
}
