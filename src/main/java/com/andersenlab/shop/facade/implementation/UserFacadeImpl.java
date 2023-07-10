package com.andersenlab.shop.facade.implementation;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.facade.UserFacade;
import com.andersenlab.shop.model.UserCredentials;
import com.andersenlab.shop.model.UserProfile;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    UserService userService;

    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<UserProfileDto> getAll() {
        return modelMapper.mapList(userService.getAll(), UserProfileDto.class);
    }

    @Logging
    @Override
    public UserProfileDto getById(Long id) {
        return modelMapper.map(userService.getById(id), UserProfileDto.class);
    }

    @Logging
    @Override
    public UserProfileDto createAsGuest(UserProfileDto userProfileDto) {
        return modelMapper.map(userService.createAsGuest(modelMapper.map(userProfileDto, UserProfile.class)), UserProfileDto.class);
    }

    @Logging
    @Override
    public UserProfileDto createAsAdmin(UserProfileDto userProfileDto) {
        return modelMapper.map(userService.createAsAdmin(modelMapper.map(userProfileDto, UserProfile.class)), UserProfileDto.class);
    }

    @Logging
    @Override
    public UserProfileDto editProfileAsUser(UserProfileDto userProfileDto) {
        return modelMapper.map(userService.editProfileAsUser(modelMapper.map(userProfileDto, UserProfile.class)), UserProfileDto.class);
    }

    @Logging
    @Override
    public UserProfileDto editProfileAsAdmin(UserProfileDto userProfileDto) {
        return modelMapper.map(userService.editProfileAsAdmin(modelMapper.map(userProfileDto, UserProfile.class)), UserProfileDto.class);
    }

    @Logging
    @Override
    public UserCredentialsDto editCredentials(UserCredentialsDto userCredentialsDto) {
        return modelMapper.map(userService.editCredentials(modelMapper.map(userCredentialsDto, UserCredentials.class)), UserCredentialsDto.class);
    }

    @Logging
    @Override
    public void delete(Long id) {
        userService.delete(id);
    }
}
