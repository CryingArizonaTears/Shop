package com.andersenlab.shop.service.implementation;

import com.andersenlab.shop.model.Product;
import com.andersenlab.shop.model.ProductType;
import com.andersenlab.shop.model.Warehouse;
import com.andersenlab.shop.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    ProductRepository productRepository;

    final Product productForTesting = new Product();

    @BeforeEach
    void beforeTests() {
        Warehouse warehouseForTesting = new Warehouse();
        warehouseForTesting.setAddress("testAddress");

        productForTesting.setWarehouse(warehouseForTesting);
        productForTesting.setProductType(ProductType.FOOD);
        productForTesting.setPrice(BigDecimal.valueOf(1));
        productForTesting.setName("testProduct");
        productForTesting.setExpDate(1);
    }

    @Test
    void testGetAll_Successful() {
        List<Product> products = List.of(productForTesting);
        when(productRepository.findAll())
                .thenReturn(products);
        List<Product> expectingProducts = productService.getAll();
        assertEquals(expectingProducts, products);
    }

    @Test
    void testGetById_Successful() {
        when(productRepository.findById(any()))
                .thenReturn(Optional.of(productForTesting));
        Product expectingProduct = productService.getById(1L);
        assertEquals(expectingProduct, productForTesting);
    }

    @Test
    void testCreate_Successful() {
        productService.create(productForTesting);
        verify(productRepository).save(argThat(productForSave ->
                productForSave.getName().equals("testProduct") &&
                        productForSave.getProductType().equals(ProductType.FOOD) &&
                        productForSave.getPrice().equals(BigDecimal.valueOf(1)) &&
                        productForSave.getExpDate().equals(1) &&
                        productForSave.getWarehouse().getAddress().equals("testAddress")));
    }

    @Test
    void testEdit_Successful() {
        Product productWithEdits = new Product();
        Warehouse warehouseForEdit = new Warehouse();
        warehouseForEdit.setAddress("editAddress");
        productWithEdits.setProductType(ProductType.NON_FOOD);
        productWithEdits.setExpDate(20);
        productWithEdits.setPrice(BigDecimal.valueOf(3));
        productWithEdits.setName("editName");
        productWithEdits.setWarehouse(warehouseForEdit);
        when(productRepository.findById(any()))
                .thenReturn(Optional.of(productForTesting));
        productService.edit(productWithEdits);
        verify(productRepository).save(argThat(productForSave ->
                productForSave.getProductType().equals(ProductType.NON_FOOD) &&
                        productForSave.getPrice().equals(BigDecimal.valueOf(3)) &&
                        productForSave.getWarehouse().getAddress().equals("editAddress") &&
                        productForSave.getExpDate().equals(20) &&
                        productForSave.getName().equals("editName")));
    }

    @Test
    void testDelete_Successful() {
        when(productRepository.findById(any()))
                .thenReturn(Optional.of(productForTesting));
        productService.delete(1L);
        verify(productRepository).delete(productForTesting);
    }
}