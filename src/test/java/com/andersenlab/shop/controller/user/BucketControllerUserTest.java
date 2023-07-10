package com.andersenlab.shop.controller.user;

import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.dto.WarehouseDto;
import com.andersenlab.shop.facade.BucketFacade;
import com.andersenlab.shop.facade.UserAuthFacade;
import com.andersenlab.shop.model.*;
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
import java.util.ArrayList;
import java.util.List;

import static api.Specification.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DirtiesContext
class BucketControllerUserTest {

    @MockBean
    BucketFacade bucketFacade;
    @MockBean
    UserAuthFacade userAuthFacade;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ModelMapper modelMapper;
    final String TOKEN = "Bearer null";
    final String AUTHORIZATION = "Authorization";
    final String URI = "buckets";
    final String PRODUCT_BODY = """
            {
                  "id": 1,
                  "warehouseDto": {
                    "id": 1,
                    "address": "testAddress"
                  },
                  "productType": "FOOD",
                  "price": 1,
                  "name": "testProduct",
                  "expDate": 1
                
            }""";
    final BucketDto bucketDtoForTesting = new BucketDto();


    @BeforeEach
    void beforeTests() {
        UserProfile userProfileUserForAuth = new UserProfile();
        userProfileUserForAuth.setId(1L);
        UserCredentials userCredentialsUser = new UserCredentials();
        userCredentialsUser.setPassword("user");
        userCredentialsUser.setUsername("user");
        userProfileUserForAuth.setUserCredentials(userCredentialsUser);
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        userProfileUserForAuth.setRole(roleUser);
        userProfileUserForAuth.setBucket(new Bucket());
        CustomUserDetails customUserDetailsUser = modelMapper.map(userProfileUserForAuth, CustomUserDetails.class);
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setId(1L);
        warehouseDto.setAddress("testAddress");
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setWarehouse(warehouseDto);
        productDto.setProductType(ProductType.FOOD);
        productDto.setPrice(BigDecimal.valueOf(1));
        productDto.setName("testProduct");
        productDto.setExpDate(1);
        bucketDtoForTesting.setId(1L);
        bucketDtoForTesting.setProducts(List.of(productDto));
        bucketDtoForTesting.setTotalPrice(BigDecimal.valueOf(1));
        when(userAuthFacade.getCurrent()).thenReturn(modelMapper.map(userProfileUserForAuth, UserProfileDto.class));
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(customUserDetailsUser);
    }

    @Test
    void testGetBucketById_Successful() {
        when(bucketFacade.getById(any()))
                .thenReturn(bucketDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        BucketDto expectedBucketDto = given()
                .when()
                .get()
                .then()
                .extract().as(BucketDto.class);
        assertEquals(expectedBucketDto, bucketDtoForTesting);
    }

    @Test
    void testAddProductToBucket_Successful() {
        when(bucketFacade.addProductToBucket(any(), any()))
                .thenReturn(bucketDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        BucketDto expectedBucketDto =
                given()
                        .body(PRODUCT_BODY)
                        .when()
                        .post()
                        .then()
                        .extract().as(BucketDto.class);
        assertEquals(expectedBucketDto, bucketDtoForTesting);
    }

    @Test
    void testDeleteProductFromBucket_Successful() {
        when(bucketFacade.deleteProductFromBucket(any(), any()))
                .thenReturn(bucketDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        BucketDto expectedBucketDto =
                given()
                        .body(PRODUCT_BODY)
                        .when()
                        .put()
                        .then()
                        .extract().as(BucketDto.class);
        assertEquals(expectedBucketDto, bucketDtoForTesting);
    }

    @Test
    void testClearBucket_Successful() {
        bucketDtoForTesting.setTotalPrice(BigDecimal.valueOf(0));
        bucketDtoForTesting.setProducts(new ArrayList<>());
        when(bucketFacade.clearBucket(any()))
                .thenReturn(bucketDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        BucketDto expectedBucketDto =
                given()
                        .when()
                        .delete()
                        .then()
                        .extract().as(BucketDto.class);
        assertEquals(expectedBucketDto, bucketDtoForTesting);
    }
}