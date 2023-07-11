package by.shop.controller.admin;

import by.shop.dto.RoleDto;
import by.shop.facade.RoleFacade;
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
class RoleControllerTest {

    @MockBean
    RoleFacade roleFacade;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    ModelMapper modelMapper;
    final String TOKEN = "Bearer null";
    final String AUTHORIZATION = "Authorization";
    final String URI = "admin/roles";
    final String ROLE_BODY = """
            {
              "id": 1,
              "name": "ROLE_ADMIN"
            }""";
    List<RoleDto> roleDtosListForTesting;
    RoleDto roleDtoForTesting = new RoleDto();

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
        roleDtoForTesting = modelMapper.map(roleAdmin, RoleDto.class);
        roleDtosListForTesting = List.of(roleDtoForTesting);
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(customUserDetailsAdmin);
    }

    @Test
    void testGetAll_Successful() {
        when(roleFacade.getAll())
                .thenReturn(roleDtosListForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        List<RoleDto> expectedRoleDtos = given()
                .when()
                .get()
                .then()
                .extract().body().jsonPath().getList(".", RoleDto.class);
        assertEquals(expectedRoleDtos, roleDtosListForTesting);
    }

    @Test
    void testGetById_Successful() {
        when(roleFacade.getById(anyLong()))
                .thenReturn(roleDtoForTesting);
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        RoleDto expectedRoleDto = given()
                .when()
                .get("/1")
                .then()
                .extract().as(RoleDto.class);
        assertEquals(expectedRoleDto, roleDtoForTesting);
    }

    @Test
    void testCreate_Successful() {
        when(roleFacade.create(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(201));
        RoleDto expectedRoleDto = given()
                .body(ROLE_BODY)
                .when()
                .post()
                .then()
                .extract().as(RoleDto.class);
        assertEquals(expectedRoleDto, roleDtoForTesting);
    }

    @Test
    void testEdit_Successful() {
        when(roleFacade.edit(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(200));
        RoleDto expectedRoleDto = given()
                .body(ROLE_BODY)
                .when()
                .put()
                .then()
                .extract().as(RoleDto.class);
        assertEquals(expectedRoleDto, roleDtoForTesting);
    }

    @Test
    void testDelete_Successful() {
        doNothing().when(roleFacade).delete(anyLong());
        installSpecification(requestSpecification(URI, AUTHORIZATION, TOKEN), responseSpecification(204));
        given()
                .when()
                .delete("/1");
    }
}