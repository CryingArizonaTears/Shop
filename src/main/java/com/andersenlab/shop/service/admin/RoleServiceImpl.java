package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.repository.RoleRepository;
import com.andersenlab.shop.dto.RoleDto;
import com.andersenlab.shop.model.Role;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<RoleDto> getAll() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return modelMapper.mapList(roles, RoleDto.class);
    }

    @Logging
    @Override
    public RoleDto getById(Long id) {
        return modelMapper.map(roleRepository.findById(id), RoleDto.class);
    }

    @Logging
    @Override
    public RoleDto getByName(String name) {
        return modelMapper.map(roleRepository.getByName(name), RoleDto.class);
    }

    @Logging
    @Override
    public void create(RoleDto roleDto) {
        roleRepository.save(modelMapper.map(roleDto, Role.class));
    }

    @Logging
    @Override
    public void edit(RoleDto roleDto) {
        roleRepository.save(modelMapper.map(roleDto, Role.class));
    }

    @Logging
    @Override
    public void delete(RoleDto roleDto) {
        roleRepository.delete(modelMapper.map(roleDto, Role.class));
    }
}
