package com.andersenlab.shop.service;

import com.andersenlab.shop.model.UserCredentials;
import com.andersenlab.shop.model.UserProfile;

public interface UserAuthenticationService {
    UserProfile getCurrent();

    UserProfile getByUsername(String username);

    UserCredentials getEncryptedUserCredentials(UserCredentials userCredentials);
}
