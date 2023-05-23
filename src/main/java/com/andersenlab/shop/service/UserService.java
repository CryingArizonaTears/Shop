package com.andersenlab.shop.service;

import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;

import java.util.List;


public interface UserService {

    List<UserProfileDto> getAll();

    UserProfileDto getById(Long id);

    void create(UserProfileDto userProfileDto);

    void editProfile(UserProfileDto userProfileDto);

    void editCredentials(UserCredentialsDto userCredentialsDto);

    void delete(UserProfileDto userProfileDto);
}
