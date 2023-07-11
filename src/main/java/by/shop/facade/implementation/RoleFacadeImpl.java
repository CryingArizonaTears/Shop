package by.shop.facade.implementation;

import by.shop.annotation.Logging;
import by.shop.dto.RoleDto;
import by.shop.facade.RoleFacade;
import by.shop.model.Role;
import by.shop.modelMapperMethods.ExtendedModelMapper;
import by.shop.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleFacadeImpl implements RoleFacade {

    RoleService roleService;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<RoleDto> getAll() {
        return modelMapper.mapList(roleService.getAll(), RoleDto.class);
    }

    @Logging
    @Override
    public RoleDto getById(Long id) {
        return modelMapper.map(roleService.getById(id), RoleDto.class);
    }

    @Logging
    @Override
    public RoleDto create(RoleDto roleDto) {
        return modelMapper.map(roleService.create(modelMapper.map(roleDto, Role.class)), RoleDto.class);
    }

    @Logging
    @Override
    public RoleDto edit(RoleDto roleDto) {
        return modelMapper.map(roleService.edit(modelMapper.map(roleDto, Role.class)), RoleDto.class);
    }

    @Logging
    @Override
    public void delete(Long id) {
        roleService.delete(id);
    }
}
