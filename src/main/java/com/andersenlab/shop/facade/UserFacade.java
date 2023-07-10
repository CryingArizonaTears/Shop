package com.andersenlab.shop.facade;

import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;

import java.util.List;

public interface UserFacade {
    List<UserProfileDto> getAll();

    UserProfileDto getById(Long id);

    UserProfileDto createAsGuest(UserProfileDto userProfileDto);

    UserProfileDto createAsAdmin(UserProfileDto userProfileDto);

    UserProfileDto editProfileAsUser(UserProfileDto userProfileDto);

    UserProfileDto editProfileAsAdmin(UserProfileDto userProfileDto);

    UserCredentialsDto editCredentials(UserCredentialsDto userCredentialsDto);

    void delete(Long id);
}
