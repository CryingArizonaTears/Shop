package com.andersenlab.shop.facade.implementation;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.facade.UserAuthFacade;
import com.andersenlab.shop.model.UserCredentials;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.UserAuthenticationService;
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
