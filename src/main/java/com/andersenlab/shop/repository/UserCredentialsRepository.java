package com.andersenlab.shop.repository;

import com.andersenlab.shop.model.UserCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {
    UserCredentials getByUsername(String username);
}
