package com.andersenlab.shop.service.implementation;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.model.Role;
import com.andersenlab.shop.repository.RoleRepository;
import com.andersenlab.shop.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
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

    @Logging
    @Override
    public List<Role> getAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Logging
    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role with id " + id + " not found "));
    }

    @Logging
    @Override
    public Role getByName(String name) {
        return roleRepository.getByName(name).orElseThrow(() -> new EntityNotFoundException("Role with name " + name + " not found "));
    }

    @Logging
    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Logging
    @Override
    public Role edit(Role role) {
        return roleRepository.save(role);
    }

    @Logging
    @Override
    public void delete(Long id) {
        Role existingRole = getById(id);
        roleRepository.delete(existingRole);
    }
}
