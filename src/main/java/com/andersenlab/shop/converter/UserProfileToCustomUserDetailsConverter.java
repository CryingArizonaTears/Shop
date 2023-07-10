package com.andersenlab.shop.converter;

import com.andersenlab.shop.model.UserProfile;
import com.andersenlab.shop.security.CustomUserDetails;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserProfileToCustomUserDetailsConverter implements Converter<UserProfile, CustomUserDetails> {

    @Override
    public CustomUserDetails convert(MappingContext<UserProfile, CustomUserDetails> mappingContext) {
        UserProfile source = mappingContext.getSource();
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(source.getUserCredentials().getUsername());
        customUserDetails.setPassword(source.getUserCredentials().getPassword());
        customUserDetails.setGrantedAuthorities(Collections.singletonList(new SimpleGrantedAuthority(source.getRole().getName())));
        return customUserDetails;
    }
}
