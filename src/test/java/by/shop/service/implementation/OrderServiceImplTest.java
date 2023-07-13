package by.shop.service.implementation;

import by.shop.model.*;
import by.shop.repository.OrderRepository;
import by.shop.service.CurrencyService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    CurrencyService currencyService;
    List<Order> orderListForTesting;
    final Order orderForTesting = new Order();
    final Currency currencyForTesting = new Currency();
    final Product productForTesting = new Product();

    @BeforeEach
    void beforeTests() {
        UserProfile userProfileForTesting = new UserProfile();
        UserCredentials userCredentialsForTesting = new UserCredentials();
        userCredentialsForTesting.setId(1L);
        userCredentialsForTesting.setPassword("testPassword");
        userCredentialsForTesting.setUsername("testUsername");
        Warehouse warehouseForTesting = new Warehouse();
        warehouseForTesting.setId(1L);
        warehouseForTesting.setAddress("testAddress");
        productForTesting.setId(1L);
        productForTesting.setWarehouse(warehouseForTesting);
        productForTesting.setProductType(ProductType.FOOD);
        productForTesting.setPrice(BigDecimal.valueOf(1));
        productForTesting.setName("testProduct");
        productForTesting.setExpDate(1);
        Bucket bucketForeTesting = new Bucket();
        bucketForeTesting.setId(1L);
        bucketForeTesting.setProducts(List.of(productForTesting));
        bucketForeTesting.setTotalPrice(BigDecimal.valueOf(2));
        Role roleForTesting = new Role();
        roleForTesting.setId(1L);
        roleForTesting.setName("ROLE_USER");
        userProfileForTesting.setUserCredentials(userCredentialsForTesting);
        userProfileForTesting.setRole(roleForTesting);
        userProfileForTesting.setBucket(bucketForeTesting);
        userProfileForTesting.setPhone("testPhone");
        userProfileForTesting.setAddress("testAddress");
        userProfileForTesting.setEmail("testEmail");
        userProfileForTesting.setId(1L);
        currencyForTesting.setId(1L);
        currencyForTesting.setMultiplier(BigDecimal.valueOf(2));
        currencyForTesting.setName("testCurrency");
        orderForTesting.setCurrency(currencyForTesting);
        orderForTesting.setUserProfile(userProfileForTesting);
        orderForTesting.setDate(LocalDate.of(2023, 07, 03));
        orderForTesting.setProcessed(false);
        orderForTesting.setProducts(List.of(productForTesting));
        orderForTesting.setTotalPrice(BigDecimal.valueOf(4));
        orderListForTesting = List.of(orderForTesting);
    }

    @Test
    void testGetAll_Successful() {
        when(orderRepository.findAll())
                .thenReturn(orderListForTesting);
        List<Order> expectingOrders = orderService.getAll();
        assertEquals(expectingOrders, orderListForTesting);
    }

    @Test
    void testGetAllByUserId_Successful() {
        when(orderRepository.getAllByUserProfileId(any()))
                .thenReturn(orderListForTesting);
        List<Order> expectingOrders = orderService.getAllByUserId(1L);
        assertEquals(expectingOrders, orderListForTesting);
    }

    @Test
    void testGetById_Successful() {
        when(orderRepository.findById(any()))
                .thenReturn(Optional.of(orderForTesting));
        Order expectingOrder = orderService.getById(1L, 1L);
        assertEquals(expectingOrder, orderForTesting);
    }

    @Test
    void testGetById_Unsuccessful() {
        when(orderRepository.findById(any()))
                .thenReturn(Optional.of(orderForTesting));
        EntityNotFoundException expectingException = assertThrows(EntityNotFoundException.class,
                () -> orderService.getById(2L, 1L));
        assertEquals(expectingException.getMessage(), "Order with id " + 1L + " not found");
    }

    @Test
    void testCreateAsAdmin_Successful() {
        List<Product> expectingProducts = new ArrayList<>(orderForTesting.getProducts());
        when(currencyService.getById(any()))
                .thenReturn(currencyForTesting);
        orderService.createAsAdmin(orderForTesting);
        verify(orderRepository).save(argThat(orderForSave ->
                orderForSave.getProducts().equals(expectingProducts) &&
                        orderForSave.getCurrency().getMultiplier().equals(BigDecimal.valueOf(2)) &&
                        orderForSave.getDate().equals(LocalDate.of(2023, 07, 03)) &&
                        orderForSave.getTotalPrice().equals(BigDecimal.valueOf(4))));
    }


    @Test
    void testCreateAsUser_Successful() {
        List<Product> expectingProducts = new ArrayList<>(orderForTesting.getProducts());
        when(currencyService.getById(any()))
                .thenReturn(currencyForTesting);
        orderService.createAsUser(orderForTesting);
        verify(orderRepository).save(argThat(orderForSave ->
                orderForSave.getProducts().equals(expectingProducts) &&
                        orderForSave.getCurrency().getMultiplier().equals(BigDecimal.valueOf(2)) &&
                        orderForSave.getDate().equals(LocalDate.now()) &&
                        orderForSave.getId() == (null) &&
                        orderForSave.getProcessed().equals(false) &&
                        orderForSave.getTotalPrice().equals(BigDecimal.valueOf(4))));
    }


    @Test
    void testEdit_Successful() {
        Order orderWithEdits = new Order();
        List<Product> productsForEdit = new ArrayList<>();
        Currency currencyForEdit = new Currency();
        currencyForEdit.setMultiplier(BigDecimal.valueOf(3));
        productsForEdit.add(productForTesting);
        productsForEdit.add(productForTesting);
        orderWithEdits.setProducts(productsForEdit);
        orderWithEdits.setCurrency(currencyForEdit);
        orderWithEdits.setDate(LocalDate.of(2023, 01, 01));
        when(orderRepository.findById(any()))
                .thenReturn(Optional.of(orderForTesting));
        when(currencyService.getById(any()))
                .thenReturn(currencyForEdit);
        orderService.edit(orderWithEdits);
        verify(orderRepository).save(argThat(orderForSave ->
                orderForSave.getProducts().size() == 2 &&
                        orderForSave.getCurrency().getMultiplier().equals(BigDecimal.valueOf(3)) &&
                        orderForSave.getDate().equals(LocalDate.of(2023, 01, 01)) &&
                        orderForSave.getTotalPrice().equals(BigDecimal.valueOf(6))));
    }

    @Test
    void testDelete_Successful() {
        when(orderRepository.findById(any()))
                .thenReturn(Optional.of(orderForTesting));
        orderService.delete(1L);
        verify(orderRepository).delete(orderForTesting);
    }
}