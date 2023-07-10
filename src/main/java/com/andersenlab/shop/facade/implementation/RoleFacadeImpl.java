package com.andersenlab.shop.facade.implementation;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.RoleDto;
import com.andersenlab.shop.facade.RoleFacade;
import com.andersenlab.shop.model.Role;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.RoleService;
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
