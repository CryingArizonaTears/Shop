package com.andersenlab.shop.dao;

import com.andersenlab.shop.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleDao extends CrudRepository<Role, Long> {
    Role getByName(String name);
}
