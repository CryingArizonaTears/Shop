package com.andersenlab.shop.controller.all;

import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.dto.WarehouseDto;
import com.andersenlab.shop.facade.ProductFacade;
import com.andersenlab.shop.model.ProductType;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

import static api.Specification.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DirtiesContext
class ProductControllerTest {

    @MockBean
    ProductFacade productFacade;
    final String TOKEN = "null";
    final String AUTHORIZATION = "null";
    final String URI = "products";
    List<ProductDto> productDtosListForTesting;
    final ProductDto productDtoForTesting = new ProductDto();

    @BeforeEach
    void beforeTests() {
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
}