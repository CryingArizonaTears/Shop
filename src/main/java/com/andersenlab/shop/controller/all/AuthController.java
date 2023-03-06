package com.andersenlab.shop.controller.all;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.security.filter.TokenProvider;
import com.andersenlab.shop.service.IUserAuthenticationService;
import com.andersenlab.shop.service.IUserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "")
public class AuthController {

    @Autowired
    public AuthController(@Qualifier("userService") IUserService userService, IUserAuthenticationService userAuthenticationService, TokenProvider tokenprovider) {
        this.userService = userService;
        this.userAuthenticationService = userAuthenticationService;
        this.tokenProvider = tokenprovider;
    }

    IUserService userService;
    IUserAuthenticationService userAuthenticationService;
    TokenProvider tokenProvider;

    @Logging
    @PostMapping("/registration")
    public ResponseEntity<Void> create(@RequestBody UserProfileDto userProfileDto) {
        userService.create(userProfileDto);
        return ResponseEntity.ok().build();
    }

    @Logging
    @PostMapping("/auth")
    public ResponseEntity<String> logIn(@RequestBody UserCredentialsDto userDto) {
        UserCredentialsDto userLogin = userAuthenticationService.getEncryptedUserCredentials(userDto);
        String token = tokenProvider.createToken(userLogin.getUsername());
        return ResponseEntity.ok(token);
    }
}