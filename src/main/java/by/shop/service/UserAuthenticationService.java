package by.shop.service;

import by.shop.model.UserCredentials;
import by.shop.model.UserProfile;

public interface UserAuthenticationService {
    UserProfile getCurrent();

    UserProfile getByUsername(String username);

    UserCredentials getEncryptedUserCredentials(UserCredentials userCredentials);
}
