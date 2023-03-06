package com.andersenlab.shop.dao;

import com.andersenlab.shop.model.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserProfileDao extends CrudRepository<UserProfile, Long> {
}
