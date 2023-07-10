package com.andersenlab.shop.facade;

import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;

public interface UserAuthFacade {
    UserProfileDto getCurrent();

    UserCredentialsDto getEncryptedUserCredentials(UserCredentialsDto userCredentialsDto);
}
