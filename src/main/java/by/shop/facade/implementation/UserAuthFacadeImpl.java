package by.shop.facade.implementation;

import by.shop.annotation.Logging;
import by.shop.dto.UserCredentialsDto;
import by.shop.dto.UserProfileDto;
import by.shop.facade.UserAuthFacade;
import by.shop.model.UserCredentials;
import by.shop.modelMapperMethods.ExtendedModelMapper;
import by.shop.service.UserAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserAuthFacadeImpl implements UserAuthFacade {

    UserAuthenticationService userAuthenticationService;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public UserProfileDto getCurrent() {
        return modelMapper.map(userAuthenticationService.getCurrent(), UserProfileDto.class);
    }

    @Logging
    @Override
    public UserCredentialsDto getEncryptedUserCredentials(UserCredentialsDto userCredentialsDto) {
        return modelMapper.map(userAuthenticationService.getEncryptedUserCredentials(modelMapper
                .map(userCredentialsDto, UserCredentials.class)), UserCredentialsDto.class);
    }
}
