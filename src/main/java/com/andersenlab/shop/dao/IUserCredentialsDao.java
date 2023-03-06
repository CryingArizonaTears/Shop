package com.andersenlab.shop.dao;

import com.andersenlab.shop.model.UserCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserCredentialsDao extends CrudRepository<UserCredentials, Long> {
    UserCredentials getByUsername(String username);
}
