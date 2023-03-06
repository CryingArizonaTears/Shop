package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dao.IRoleDao;
import com.andersenlab.shop.dto.RoleDto;
import com.andersenlab.shop.model.Role;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService implements IRoleService {

    IRoleDao roleDao;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<RoleDto> getAll() {
        List<Role> roles = (List<Role>) roleDao.findAll();
        return modelMapper.mapList(roles, RoleDto.class);
    }

    @Logging
    @Override
    public RoleDto getById(Long id) {
        return modelMapper.map(roleDao.findById(id), RoleDto.class);
    }

    @Logging
    @Override
    public RoleDto getByName(String name) {
        return modelMapper.map(roleDao.getByName(name), RoleDto.class);
    }

    @Logging
    @Override
    public void create(RoleDto roleDto) {
        roleDao.save(modelMapper.map(roleDto, Role.class));
    }

    @Logging
    @Override
    public void edit(RoleDto roleDto) {
        roleDao.save(modelMapper.map(roleDto, Role.class));
    }

    @Logging
    @Override
    public void delete(RoleDto roleDto) {
        roleDao.delete(modelMapper.map(roleDto, Role.class));
    }
}
