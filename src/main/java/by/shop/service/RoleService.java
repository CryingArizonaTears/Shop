package by.shop.service;

import by.shop.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAll();

    Role getById(Long id);

    Role getByName(String name);

    Role create(Role role);

    Role edit(Role role);

    void delete(Long id);
}
