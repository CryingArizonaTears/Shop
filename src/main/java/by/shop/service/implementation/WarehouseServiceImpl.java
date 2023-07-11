package by.shop.service.implementation;

import by.shop.annotation.Logging;
import by.shop.model.Warehouse;
import by.shop.repository.WarehouseRepository;
import by.shop.service.WarehouseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseServiceImpl implements WarehouseService {

    WarehouseRepository warehouseRepository;

    @Logging
    @Override
    public List<Warehouse> getAll() {
        return (List<Warehouse>) warehouseRepository.findAll();
    }

    @Logging
    @Override
    public Warehouse getById(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Warehouse with id " + id + " not found"));
    }

    @Logging
    @Override
    public Warehouse create(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Logging
    @Override
    public Warehouse edit(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Logging
    @Override
    public void delete(Long id) {
        Warehouse existingWarehouse = getById(id);
        warehouseRepository.delete(existingWarehouse);
    }
}
