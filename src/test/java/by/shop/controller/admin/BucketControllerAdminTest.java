package by.shop.controller.admin;

import by.shop.dto.BucketDto;
import by.shop.dto.ProductDto;
import by.shop.dto.WarehouseDto;
import by.shop.facade.BucketFacade;
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
import java.util.ArrayList;
import java.util.List;

import static api.Specification.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DirtiesContext
class BucketControllerAdminTest {

    @MockBean
    BucketFacade bucketFacade;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ModelMapper modelMapper;
    final String TOKEN = "Bearer null";
    final String AUTHORIZATION = "Authorization";
    final String URI = "admin/buckets";
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
        UserProfile userProfileAdmin = new UserProfile();
        UserCredentials userCredentialsAdmin = new UserCredentials();
        userCredentialsAdmin.setPassword("admin");
        userCredentialsAdmin.setUsername("admin");
        userProfileAdmin.setUserCredentials(userCredentialsAdmin);
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        userProfileAdmin.setRole(roleAdmin);
        CustomUserDetails customUserDetailsAdmin = modelMapper.map(userProfileAdmin, CustomUserDetails.class);
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
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(customUserDetailsAdmin);
    }

    @Test
    void testGetBucketById_Successful() {
        when(bucketFacade.getById(anyLong()))
                .thenReturn(bucketDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        BucketDto expectedBucketDto = given()
                .when()
                .get("/1")
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
                        .post("/1")
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
                        .put("/1")
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
                        .delete("/1")
                        .then()
                        .extract().as(BucketDto.class);
        assertEquals(expectedBucketDto, bucketDtoForTesting);
    }
}