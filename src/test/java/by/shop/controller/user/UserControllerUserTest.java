package by.shop.controller.user;

import by.shop.dto.*;
import by.shop.facade.UserAuthFacade;
import by.shop.facade.UserFacade;
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
class UserControllerUserTest {

    @MockBean
    UserFacade userFacade;
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
    final String URI = "users";
    final String USER_BODY = """
            {
              "role": {
                "id": 1,
                "name": "ROLE_USER"
              },
              "userCredentials": {
                "id": 1,
                "password": "testPassword",
                "username": "testUsername"
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
              "email": "testEmail",
              "id": 1
            }""";
    final String CREDENTIALS_BODY = """
            {
            "id": 1,
            "password": "testPassword",
            "username": "testUsername"
            }""";
    final UserProfileDto userProfileDtoForTesting = new UserProfileDto();
    final UserCredentialsDto userCredentialsDtoForTesting = new UserCredentialsDto();


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
        CustomUserDetails customUserDetailsUser = modelMapper.map(userProfileUserForAuth, CustomUserDetails.class);
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
        when(userAuthFacade.getCurrent()).thenReturn(modelMapper.map(userProfileUserForAuth, UserProfileDto.class));
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(customUserDetailsUser);
    }

    @Test
    void testGetById_Successful() {
        when(userFacade.getById(anyLong()))
                .thenReturn(userProfileDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        UserProfileDto expectedUserProfileDto = given()
                .when()
                .get()
                .then()
                .extract().as(UserProfileDto.class);
        assertEquals(expectedUserProfileDto, userProfileDtoForTesting);
    }

    @Test
    void testEditProfile_Successful() {
        when(userFacade.editProfileAsUser(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        UserProfileDto expectedUserProfileDto = given()
                .body(USER_BODY)
                .when()
                .put("/profile")
                .then()
                .extract().as(UserProfileDto.class);
        assertEquals(expectedUserProfileDto, userProfileDtoForTesting);
    }

    @Test
    void testEditCredentials_Successful() {
        when(userFacade.editCredentials(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        UserCredentialsDto expectedUserCredentialsDto = given()
                .body(CREDENTIALS_BODY)
                .when()
                .put("/credentials")
                .then()
                .extract().as(UserCredentialsDto.class);
        assertEquals(expectedUserCredentialsDto, userCredentialsDtoForTesting);
    }
}