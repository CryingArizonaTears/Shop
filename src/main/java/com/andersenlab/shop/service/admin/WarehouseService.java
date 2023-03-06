package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dao.IWarehouseDao;
import com.andersenlab.shop.dto.WarehouseDto;
import com.andersenlab.shop.model.Warehouse;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.IWarehouseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseService implements IWarehouseService {

    IWarehouseDao warehouseDao;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<WarehouseDto> getAll() {
        List<Warehouse> warehouses = (List<Warehouse>) warehouseDao.findAll();
        return modelMapper.mapList(warehouses, WarehouseDto.class);
    }

    @Logging
    @Override
    public WarehouseDto getById(Long id) {
        return modelMapper.map(warehouseDao.findById(id), WarehouseDto.class);
    }

    @Logging
    @Override
    public void create(WarehouseDto warehouseDto) {
        warehouseDao.save(modelMapper.map(warehouseDto, Warehouse.class));
    }

    @Logging
    @Override
    public void edit(WarehouseDto warehouseDto) {
        warehouseDao.save(modelMapper.map(warehouseDto, Warehouse.class));
    }

    @Logging
    @Override
    public void delete(WarehouseDto warehouseDto) {
        warehouseDao.delete(modelMapper.map(warehouseDto, Warehouse.class));
    }
}
