package by.shop.service;

import by.shop.model.UserCredentials;
import by.shop.model.UserProfile;

import java.util.List;


public interface UserService {

    List<UserProfile> getAll();

    UserProfile getById(Long id);

    UserProfile createAsGuest(UserProfile userProfile);

    UserProfile createAsAdmin(UserProfile userProfile);

    UserProfile editProfileAsUser(UserProfile userProfile);

    UserProfile editProfileAsAdmin(UserProfile userProfile);

    UserCredentials editCredentials(UserCredentials userCredentials);

    void delete(Long id);
}
