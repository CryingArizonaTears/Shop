package com.andersenlab.shop.security;

import com.andersenlab.shop.service.IUserAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    IUserAuthenticationService userAuthenticationService;
    ModelMapper modelMapper;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(userAuthenticationService.getByUsername(username).getUserCredentialsDto(), CustomUserDetails.class);
    }
}
