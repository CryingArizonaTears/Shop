package com.andersenlab.shop.service;

import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;

public interface IUserAuthenticationService {
    UserProfileDto getCurrent();

    UserProfileDto getByUsername(String username);

    UserCredentialsDto getEncryptedUserCredentials(UserCredentialsDto userCredentialsDto);
}
