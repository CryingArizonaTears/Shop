package com.andersenlab.shop.repository;

import com.andersenlab.shop.model.UserCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {
    Optional<UserCredentials> getByUsername(String username);
}
