package by.shop.facade;

import by.shop.dto.RoleDto;

import java.util.List;

public interface RoleFacade {
    List<RoleDto> getAll();

    RoleDto getById(Long id);

    RoleDto create(RoleDto roleDto);

    RoleDto edit(RoleDto roleDto);

    void delete(Long id);
}
