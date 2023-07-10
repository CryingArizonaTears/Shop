package com.andersenlab.shop.controller.all;

import com.andersenlab.shop.dto.CurrencyDto;
import com.andersenlab.shop.facade.CurrencyFacade;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DirtiesContext
class CurrencyControllerTest {
    @MockBean
    CurrencyFacade currencyFacade;
    final String URI = "currencies";
    final String TOKEN = "null";
    final String AUTHORIZATION = "null";
    List<CurrencyDto> currencyDtoListForTesting;
    final CurrencyDto currencyDtoForTesting = new CurrencyDto();


    @BeforeEach
    void beforeTests() {
        currencyDtoForTesting.setId(1L);
        currencyDtoForTesting.setMultiplier(BigDecimal.valueOf(1));
        currencyDtoForTesting.setName("testCurrency");
        currencyDtoListForTesting = List.of(currencyDtoForTesting);
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
}