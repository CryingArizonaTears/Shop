package by.shop.service.implementation;

import by.shop.model.Bucket;
import by.shop.model.Product;
import by.shop.model.ProductType;
import by.shop.model.Warehouse;
import by.shop.repository.BucketRepository;
import by.shop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BucketServiceImplTest {

    @InjectMocks
    BucketServiceImpl bucketService;
    @Mock
    BucketRepository bucketRepository;
    @Mock
    ProductService productService;
    final Bucket bucketForTesting = new Bucket();
    final Product productForTesting = new Product();
    final Warehouse warehouseForTesting = new Warehouse();


    @BeforeEach
    void beforeTests() {
        warehouseForTesting.setId(1L);
        warehouseForTesting.setAddress("testAddress");
        productForTesting.setWarehouse(warehouseForTesting);
        productForTesting.setProductType(ProductType.FOOD);
        productForTesting.setPrice(BigDecimal.valueOf(1));
        productForTesting.setName("testProduct");
        productForTesting.setExpDate(1);
        bucketForTesting.setProducts(List.of(productForTesting));
        bucketForTesting.setTotalPrice(BigDecimal.valueOf(1));
        when(bucketRepository.findById(any()))
                .thenReturn(Optional.of(bucketForTesting));
    }

    @Test
    void testGetById_Successful() {
        Bucket expectingBucket = bucketService.getById(1L);
        assertEquals(bucketForTesting, expectingBucket);
    }

    @Test
    void testAddProductToBucket_Successful() {
        when(productService.getById(any()))
                .thenReturn(productForTesting);
        Bucket expectingBucket = new Bucket();
        expectingBucket.setProducts(bucketForTesting.getProducts());
        List<Product> productsInExpectingBucket = new ArrayList<>(expectingBucket.getProducts());
        productsInExpectingBucket.add(productForTesting);
        expectingBucket.setProducts(productsInExpectingBucket);
        expectingBucket.setTotalPrice(BigDecimal.valueOf(2));
        bucketService.addProductToBucket(1L, productForTesting);
        verify(bucketRepository).save(argThat(bucketForSaving ->
                bucketForSaving.equals(expectingBucket)));
    }

    @Test
    void testDeleteProductFromBucket_Successful() {
        when(productService.getById(any()))
                .thenReturn(productForTesting);
        Bucket expectingBucket = new Bucket();
        expectingBucket.setProducts(bucketForTesting.getProducts());
        List<Product> productsInExpectingBucket = new ArrayList<>(expectingBucket.getProducts());
        productsInExpectingBucket.remove(productForTesting);
        expectingBucket.setProducts(productsInExpectingBucket);
        expectingBucket.setTotalPrice(BigDecimal.valueOf(0));
        bucketService.deleteProductFromBucket(1L, productForTesting);
        verify(bucketRepository).save(argThat(bucketForSaving ->
                bucketForSaving.equals(expectingBucket)));
    }

    @Test
    void testDeleteProductFromBucket_Unsuccessful() {
        when(productService.getById(any()))
                .thenReturn(productForTesting);
        bucketForTesting.setProducts(List.of());
        EntityNotFoundException expectingException = assertThrows(EntityNotFoundException.class,
                () -> bucketService.deleteProductFromBucket(1L, productForTesting));
        assertEquals(expectingException.getMessage(), "Bucket with " + 1L + " does not contain product with id null");
    }

    @Test
    void testClearBucket_Successful() {
        when(bucketRepository.findById(any()))
                .thenReturn(Optional.of(bucketForTesting));
        bucketService.clearBucket(1L);
        verify(bucketRepository).save(argThat(clearBucket ->
                clearBucket.getTotalPrice().equals(BigDecimal.valueOf(0)) &&
                        clearBucket.getProducts().equals(new ArrayList<>())));
    }
}