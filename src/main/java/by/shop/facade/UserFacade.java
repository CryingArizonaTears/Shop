package by.shop.facade;

import by.shop.dto.UserCredentialsDto;
import by.shop.dto.UserProfileDto;

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
