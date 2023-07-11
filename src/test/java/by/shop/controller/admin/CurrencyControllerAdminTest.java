package by.shop.controller.admin;


import by.shop.dto.CurrencyDto;
import by.shop.facade.CurrencyFacade;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DirtiesContext
class CurrencyControllerAdminTest {

    @MockBean
    CurrencyFacade currencyFacade;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ModelMapper modelMapper;
    final String TOKEN = "Bearer null";
    final String AUTHORIZATION = "Authorization";
    final String URI = "admin/currencies";
    final String CURRENCY_BODY = """
            {
              "id": 1,
              "multiplier": 1,
              "name": "testCurrency"
            }""";
    List<CurrencyDto> currencyDtoListForTesting;
    final CurrencyDto currencyDtoForTesting = new CurrencyDto();


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
        currencyDtoForTesting.setId(1L);
        currencyDtoForTesting.setMultiplier(BigDecimal.valueOf(1));
        currencyDtoForTesting.setName("testCurrency");
        currencyDtoListForTesting = List.of(currencyDtoForTesting);
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(customUserDetailsAdmin);
    }


    @Test
    void testGetAll_Successful() {
        when(currencyFacade.getAll())
                .thenReturn(currencyDtoListForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        List<CurrencyDto> expectedCurrencyDtos = given()
                .when()
                .get()
                .then()
                .extract().body().jsonPath().getList(".", CurrencyDto.class);
        assertEquals(expectedCurrencyDtos, currencyDtoListForTesting);
    }

    @Test
    void testGetById_Successful() {
        when(currencyFacade.getById(anyLong()))
                .thenReturn(currencyDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        CurrencyDto expectedCurrencyDto = given()
                .when()
                .get("/1")
                .then()
                .extract().as(CurrencyDto.class);
        assertEquals(expectedCurrencyDto, currencyDtoForTesting);
    }

    @Test
    void testCreate_Successful() {
        when(currencyFacade.create(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(201));
        CurrencyDto expectedCurrencyDto = given()
                .body(CURRENCY_BODY)
                .when()
                .post()
                .then()
                .extract().as(CurrencyDto.class);
        assertEquals(expectedCurrencyDto, currencyDtoForTesting);
    }

    @Test
    void testEdit_Successful() {
        when(currencyFacade.edit(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        CurrencyDto expectedCurrencyDto = given()
                .body(CURRENCY_BODY)
                .when()
                .put()
                .then()
                .extract().as(CurrencyDto.class);
        assertEquals(expectedCurrencyDto, currencyDtoForTesting);
    }

    @Test
    void testDelete_Successful() {
        doNothing().when(currencyFacade).delete(anyLong());
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(204));
        given()
                .when()
                .delete("/1");
    }
}
