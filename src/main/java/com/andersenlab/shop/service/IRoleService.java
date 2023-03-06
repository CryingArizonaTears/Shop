package com.andersenlab.shop.service;

import com.andersenlab.shop.dto.RoleDto;

import java.util.List;

public interface IRoleService {

    List<RoleDto> getAll();

    RoleDto getById(Long id);

    RoleDto getByName(String name);

    void create(RoleDto roleDto);

    void edit(RoleDto roleDto);

    void delete(RoleDto roleDto);
}
