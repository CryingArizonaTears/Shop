package com.andersenlab.shop.controller.admin;

import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.dto.WarehouseDto;
import com.andersenlab.shop.facade.ProductFacade;
import com.andersenlab.shop.model.ProductType;
import com.andersenlab.shop.model.Role;
import com.andersenlab.shop.model.UserCredentials;
import com.andersenlab.shop.model.UserProfile;
import com.andersenlab.shop.security.CustomUserDetails;
import com.andersenlab.shop.security.CustomUserDetailsService;
import com.andersenlab.shop.security.filter.TokenProvider;
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
class ProductControllerAdminTest {

    @MockBean
    ProductFacade productFacade;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ModelMapper modelMapper;
    final String TOKEN = "Bearer null";
    final String AUTHORIZATION = "Authorization";
    final String URI = "admin/products";
    final String PRODUCT_BODY = """
            {
              "warehouse": {
                "id": 1,
                "address": "testAddress"
              },
              "id": 1,
              "productType": "FOOD",
              "price": 1,
              "name": "testProduct",
              "expDate": 1
            }""";
    List<ProductDto> productDtosListForTesting;
    final ProductDto productDtoForTesting = new ProductDto();

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
        WarehouseDto warehouseDtoForTesting = new WarehouseDto();
        warehouseDtoForTesting.setId(1L);
        warehouseDtoForTesting.setAddress("testAddress");
        productDtoForTesting.setId(1L);
        productDtoForTesting.setWarehouse(warehouseDtoForTesting);
        productDtoForTesting.setProductType(ProductType.FOOD);
        productDtoForTesting.setPrice(BigDecimal.valueOf(1));
        productDtoForTesting.setName("testProduct");
        productDtoForTesting.setExpDate(1);
        productDtosListForTesting = List.of(productDtoForTesting);
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(customUserDetailsAdmin);
    }

    @Test
    void testGetAll_Successful() {
        when(productFacade.getAll())
                .thenReturn(productDtosListForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        List<ProductDto> expectedProductDtos = given()
                .when()
                .get()
                .then()
                .extract().body().jsonPath().getList(".", ProductDto.class);
        assertEquals(expectedProductDtos, productDtosListForTesting);
    }

    @Test
    void testGetById_Successful() {
        when(productFacade.getById(anyLong()))
                .thenReturn(productDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        ProductDto expectedProductDto = given()
                .when()
                .get("/1")
                .then()
                .extract().as(ProductDto.class);
        assertEquals(expectedProductDto, productDtoForTesting);
    }

    @Test
    void testCreate_Successful() {
        when(productFacade.create(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(201));
        ProductDto expectedProductDto = given()
                .body(PRODUCT_BODY)
                .when()
                .post()
                .then()
                .extract().as(ProductDto.class);
        assertEquals(expectedProductDto, productDtoForTesting);
    }

    @Test
    void testEdit_Successful() {
        when(productFacade.edit(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        ProductDto expectedProductDto = given()
                .body(PRODUCT_BODY)
                .when()
                .put()
                .then()
                .extract().as(ProductDto.class);
        assertEquals(expectedProductDto, productDtoForTesting);
    }

    @Test
    void testDelete_Successful() {
        doNothing().when(productFacade).delete(anyLong());
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(204));
        given()
                .when()
                .delete("/1");
    }
}