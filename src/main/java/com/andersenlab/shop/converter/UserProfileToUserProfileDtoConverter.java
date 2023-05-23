package com.andersenlab.shop.converter;

import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.RoleDto;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.model.UserProfile;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
public class UserProfileToUserProfileDtoConverter implements Converter<UserProfile, UserProfileDto> {
    ExtendedModelMapper modelMapper;

    @Override
    public UserProfileDto convert(MappingContext<UserProfile, UserProfileDto> mappingContext) {
        UserProfile source = mappingContext.getSource();
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(source.getId());
        userProfileDto.setUserCredentialsDto(modelMapper.map(source.getUserCredentials(), UserCredentialsDto.class));
        userProfileDto.setRoleDto(modelMapper.map(source.getRole(), RoleDto.class));
        userProfileDto.setBucketDto(modelMapper.map(source.getBucket(), BucketDto.class));
        userProfileDto.setPhone(source.getPhone());
        userProfileDto.setFullName(source.getFullName());
        userProfileDto.setAddress(source.getFullName());
        userProfileDto.setEmail(source.getEmail());
        return userProfileDto;
    }
}
