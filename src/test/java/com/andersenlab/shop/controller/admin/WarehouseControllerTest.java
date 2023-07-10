package com.andersenlab.shop.controller.admin;

import com.andersenlab.shop.dto.WarehouseDto;
import com.andersenlab.shop.facade.WarehouseFacade;
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
class WarehouseControllerTest {

    @MockBean
    WarehouseFacade warehouseFacade;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ModelMapper modelMapper;
    final String TOKEN = "Bearer null";
    final String AUTHORIZATION = "Authorization";
    final String URI = "admin/warehouses";
    final String WAREHOUSE_BODY = """
            {
              "id": 1,
              "address": "testAddress"
            }""";
    List<WarehouseDto> warehouseDtosListForTesting;
    final WarehouseDto warehouseDtoForTesting = new WarehouseDto();


    @BeforeEach
    void beforeTests() {
        UserProfile userProfileAdmin = new UserProfile();
        UserCredentials userCredentialsAdmin = new UserCredentials();
        userCredentialsAdmin.setPassword("admin");
        userCredentialsAdmin.setUsername("admin");
        userProfileAdmin.setUserCredentials(userCredentialsAdmin);
        Role roleAdmin = new Role();
        roleAdmin.setId(1L);
        roleAdmin.setName("ROLE_ADMIN");
        userProfileAdmin.setRole(roleAdmin);
        CustomUserDetails customUserDetailsAdmin = modelMapper.map(userProfileAdmin, CustomUserDetails.class);
        warehouseDtoForTesting.setId(1L);
        warehouseDtoForTesting.setAddress("testAddress");
        warehouseDtosListForTesting = List.of(warehouseDtoForTesting);
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(customUserDetailsAdmin);
    }

    @Test
    void testGetAll_Successful() {
        when(warehouseFacade.getAll())
                .thenReturn(warehouseDtosListForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        List<WarehouseDto> expectedWarehouseDtos = given()
                .when()
                .get()
                .then()
                .extract().body().jsonPath().getList(".", WarehouseDto.class);
        assertEquals(expectedWarehouseDtos, warehouseDtosListForTesting);
    }

    @Test
    void testGetById_Successful() {
        when(warehouseFacade.getById(anyLong()))
                .thenReturn(warehouseDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        WarehouseDto expectedWarehouseDtos = given()
                .when()
                .get("/1")
                .then()
                .extract().as(WarehouseDto.class);
        assertEquals(expectedWarehouseDtos, warehouseDtoForTesting);
    }

    @Test
    void testCreate_Successful() {
        when(warehouseFacade.create(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(201));
        WarehouseDto expectedWarehouseDtos = given()
                .body(WAREHOUSE_BODY)
                .when()
                .post()
                .then()
                .extract().as(WarehouseDto.class);
        assertEquals(expectedWarehouseDtos, warehouseDtoForTesting);
    }

    @Test
    void testEdit_Successful() {
        when(warehouseFacade.edit(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        WarehouseDto expectedWarehouseDtos = given()
                .body(WAREHOUSE_BODY)
                .when()
                .put()
                .then()
                .extract().as(WarehouseDto.class);
        assertEquals(expectedWarehouseDtos, warehouseDtoForTesting);
    }

    @Test
    void testDelete_Successful() {
        doNothing().when(warehouseFacade).delete(anyLong());
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(204));
        given()
                .when()
                .delete("/1");
    }
}