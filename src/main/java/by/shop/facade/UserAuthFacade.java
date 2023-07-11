package by.shop.facade;

import by.shop.dto.UserCredentialsDto;
import by.shop.dto.UserProfileDto;

public interface UserAuthFacade {
    UserProfileDto getCurrent();

    UserCredentialsDto getEncryptedUserCredentials(UserCredentialsDto userCredentialsDto);
}
