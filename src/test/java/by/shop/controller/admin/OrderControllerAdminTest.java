package by.shop.controller.admin;

import by.shop.dto.*;
import by.shop.facade.OrderFacade;
import by.shop.model.ProductType;
import by.shop.model.Role;
import by.shop.model.UserCredentials;
import by.shop.model.UserProfile;
import by.shop.security.CustomUserDetails;
import by.shop.security.CustomUserDetailsService;
import by.shop.security.filter.TokenProvider;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static api.Specification.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DirtiesContext
class OrderControllerAdminTest {

    @MockBean
    OrderFacade orderFacade;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ModelMapper modelMapper;
    final String TOKEN = "Bearer null";
    final String AUTHORIZATION = "Authorization";
    final String URI = "admin/orders";
    final String ORDER_BODY = """
            {
              "id": 1,
              "currency": {
                "id": 1,
                "multiplier": 1,
                "name": "testCurrency"
              },
              "userProfile": {
                "id": 1,
                "userCredentials": {
                  "id": 1,
                  "password": "testPassword",
                  "username": "testUsername"
                },
                "role": {
                  "id": 1,
                  "name": "ROLE_USER"
                },
                "bucket": {
                  "id": 1,
                  "products": [
                    {
                      "id": 1,
                      "warehouse": {
                        "id": 1,
                        "address": "testAddress"
                      },
                      "productType": "FOOD",
                      "price": 1,
                      "name": "testProduct",
                      "expDate": 1
                    }
                  ],
                  "totalPrice": 1
                },
                "phone": "testPhone",
                "address": "testAddress",
                "email": "testEmail"
              },
              "date": "2023-07-03",
              "processed": false,
              "products": [
                {
                  "id": 1,
                  "warehouse": {
                    "id": 1,
                    "address": "testAddress"
                  },
                  "productType": "FOOD",
                  "price": 1,
                  "name": "testProduct",
                  "expDate": 1
                }
              ],
              "totalPrice": 1
            }""";
    List<OrderDto> orderDtosListForTesting;
    final OrderDto orderDtoForTesting = new OrderDto();


    @BeforeEach
    void beforeTests() {
        UserProfile userProfileAdmin = new UserProfile();
        UserCredentials userCredentialsAdmin = new UserCredentials();
        userCredentialsAdmin.setPassword("admin");
        userCredentialsAdmin.setUsername("admin");
        userProfileAdmin.setUserCredentials(userCredentialsAdmin);
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        userProfileAdmin.setRole(roleAdmin);
        CustomUserDetails customUserDetailsAdmin = modelMapper.map(userProfileAdmin, CustomUserDetails.class);
        UserProfileDto userProfileDtoForTesting = new UserProfileDto();
        UserCredentialsDto userCredentialsDtoForTesting = new UserCredentialsDto();
        userCredentialsDtoForTesting.setId(1L);
        userCredentialsDtoForTesting.setUsername("testUsername");
        WarehouseDto warehouseDtoForTesting = new WarehouseDto();
        warehouseDtoForTesting.setId(1L);
        warehouseDtoForTesting.setAddress("testAddress");
        ProductDto productDtoForTesting = new ProductDto();
        productDtoForTesting.setId(1L);
        productDtoForTesting.setWarehouse(warehouseDtoForTesting);
        productDtoForTesting.setProductType(ProductType.FOOD);
        productDtoForTesting.setPrice(BigDecimal.valueOf(1));
        productDtoForTesting.setName("testProduct");
        productDtoForTesting.setExpDate(1);
        BucketDto bucketDto = new BucketDto();
        bucketDto.setId(1L);
        bucketDto.setProducts(List.of(productDtoForTesting));
        bucketDto.setTotalPrice(BigDecimal.valueOf(1));
        RoleDto roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setName("ROLE_USER");
        userProfileDtoForTesting.setUserCredentials(userCredentialsDtoForTesting);
        userProfileDtoForTesting.setRole(roleDto);
        userProfileDtoForTesting.setBucket(bucketDto);
        userProfileDtoForTesting.setPhone("testPhone");
        userProfileDtoForTesting.setAddress("testAddress");
        userProfileDtoForTesting.setEmail("testEmail");
        userProfileDtoForTesting.setId(1L);
        CurrencyDto currencyDtoForTesting = new CurrencyDto();
        currencyDtoForTesting.setId(1L);
        currencyDtoForTesting.setMultiplier(BigDecimal.valueOf(1));
        currencyDtoForTesting.setName("testCurrency");
        orderDtoForTesting.setId(1L);
        orderDtoForTesting.setCurrency(currencyDtoForTesting);
        orderDtoForTesting.setUserProfile(userProfileDtoForTesting);
        orderDtoForTesting.setDate(LocalDate.of(2023, 07, 03));
        orderDtoForTesting.setProcessed(false);
        orderDtoForTesting.setProducts(List.of(productDtoForTesting));
        orderDtoForTesting.setTotalPrice(BigDecimal.valueOf(1));
        orderDtosListForTesting = List.of(orderDtoForTesting);
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(customUserDetailsAdmin);
    }

    @Test
    void testGetAll_Successful() {
        when(orderFacade.getAll())
                .thenReturn(orderDtosListForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        List<OrderDto> expectedOrderDtos = given()
                .when()
                .get()
                .then()
                .extract().body().jsonPath().getList(".", OrderDto.class);
        assertEquals(expectedOrderDtos, orderDtosListForTesting);
    }

    @Test
    void testGetAllByUserId_Successful() {
        when(orderFacade.getAllByUserId(anyLong()))
                .thenReturn(orderDtosListForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        List<OrderDto> expectedOrderDtos = given()
                .when()
                .get("/user/1")
                .then()
                .extract().body().jsonPath().getList(".", OrderDto.class);
        assertEquals(expectedOrderDtos, orderDtosListForTesting);
    }

    @Test
    void testGetById_Successful() {
        when(orderFacade.getById(any(), anyLong()))
                .thenReturn(orderDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        OrderDto expectedOrderDto = given()
                .when()
                .get("/1")
                .then()
                .extract().as(OrderDto.class);
        assertEquals(expectedOrderDto, orderDtoForTesting);
    }

    @Test
    void testCreate_Successful() {
        when(orderFacade.createAsAdmin(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(201));
        OrderDto expectedOrderDto = given()
                .body(ORDER_BODY)
                .when()
                .post()
                .then()
                .extract().as(OrderDto.class);
        assertEquals(expectedOrderDto, orderDtoForTesting);
    }

    @Test
    void testEdit_Successful() {
        when(orderFacade.edit(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        OrderDto expectedOrderDto = given()
                .body(ORDER_BODY)
                .when()
                .put()
                .then()
                .extract().as(OrderDto.class);
        assertEquals(expectedOrderDto, orderDtoForTesting);
    }

    @Test
    void testDelete_Successful() {
        doNothing().when(orderFacade).delete(anyLong());
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(204));
        given()
                .when()
                .delete("/1");
    }
}