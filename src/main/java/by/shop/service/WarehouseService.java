package by.shop.service;

import by.shop.model.Warehouse;

import java.util.List;

public interface WarehouseService {

    List<Warehouse> getAll();

    Warehouse getById(Long id);

    Warehouse create(Warehouse warehouse);

    Warehouse edit(Warehouse warehouse);

    void delete(Long id);
}
