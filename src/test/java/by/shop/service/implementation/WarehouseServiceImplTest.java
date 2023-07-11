package by.shop.service.implementation;

import by.shop.model.Warehouse;
import by.shop.repository.WarehouseRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class WarehouseServiceImplTest {


    @InjectMocks
    WarehouseServiceImpl warehouseService;
    @Mock
    WarehouseRepository warehouseRepository;
    final Warehouse warehouseForTesting = new Warehouse();


    @BeforeEach
    void beforeTests() {
        warehouseForTesting.setAddress("testAddress");
    }

    @Test
    void testGetAll_Successful() {
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouseForTesting);
        when(warehouseRepository.findAll())
                .thenReturn(warehouses);
        List<Warehouse> expectingWarehouses = warehouseService.getAll();
        assertEquals(warehouses, expectingWarehouses);
    }

    @Test
    void testGetById_Successful() {
        when(warehouseRepository.findById(any()))
                .thenReturn(Optional.of(warehouseForTesting));
        Warehouse expectingUserProfile = warehouseService.getById(1L);
        assertEquals(warehouseForTesting, expectingUserProfile);
    }

    @Test
    void testCreate_Successful() {
        warehouseService.create(warehouseForTesting);
        verify(warehouseRepository).save(argThat(warehouseForSaving ->
                warehouseForSaving.getAddress().equals("testAddress")));
    }

    @Test
    void testEdit_Successful() {
        warehouseService.edit(warehouseForTesting);
        verify(warehouseRepository).save(argThat(warehouseForSaving ->
                warehouseForSaving.getAddress().equals("testAddress")));
    }

    @Test
    void testDelete_Successful() {
        when(warehouseRepository.findById(any()))
                .thenReturn(Optional.of(warehouseForTesting));
        warehouseService.delete(1L);
        verify(warehouseRepository).delete(warehouseForTesting);
    }
}