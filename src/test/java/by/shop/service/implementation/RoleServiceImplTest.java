package by.shop.service.implementation;

import by.shop.model.Role;
import by.shop.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class RoleServiceImplTest {
    @InjectMocks
    RoleServiceImpl roleService;
    @Mock
    RoleRepository roleRepository;
    final Role roleForTesting = new Role();

    @BeforeEach
    void beforeTests() {
        roleForTesting.setName("ROLE_ADMIN");
    }

    @Test
    void testGetAll_Successful() {
        List<Role> roles = List.of(roleForTesting);
        when(roleRepository.findAll())
                .thenReturn(roles);
        List<Role> expectingRoles = roleService.getAll();
        assertEquals(expectingRoles, roles);
    }

    @Test
    void testGetById_Successful() {
        when(roleRepository.findById(any()))
                .thenReturn(Optional.of(roleForTesting));
        Role expectingRole = roleService.getById(1L);
        assertEquals(expectingRole, roleForTesting);
    }

    @Test
    void testGetByName_Successful() {
        when(roleRepository.getByName(any()))
                .thenReturn(Optional.of(roleForTesting));
        Role expectingRole = roleService.getByName("name");
        assertEquals(expectingRole, roleForTesting);
    }

    @Test
    void testCreate_Successful() {
        roleService.create(roleForTesting);
        verify(roleRepository).save(argThat(roleForSave ->
                roleForSave.getName().equals("ROLE_ADMIN")));
    }

    @Test
    void testEdit_Successful() {
        roleService.edit(roleForTesting);
        verify(roleRepository).save(argThat(roleForSave ->
                roleForSave.getName().equals("ROLE_ADMIN")));
    }

    @Test
    void testDelete_Successful() {
        when(roleRepository.findById(any()))
                .thenReturn(Optional.of(roleForTesting));
        roleService.delete(1L);
        verify(roleRepository).delete(roleForTesting);
    }
}