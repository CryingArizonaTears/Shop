package com.andersenlab.shop.controller.all;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.facade.UserAuthFacade;
import com.andersenlab.shop.facade.UserFacade;
import com.andersenlab.shop.security.filter.TokenProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "")
public class AuthController {

    UserFacade userFacade;
    UserAuthFacade userAuthFacade;
    TokenProvider tokenProvider;

    @Logging
    @PostMapping("/registration")
    public ResponseEntity<UserProfileDto> create(@RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userFacade.createAsGuest(userProfileDto));
    }

    @Logging
    @PostMapping("/auth")
    public ResponseEntity<String> logIn(@RequestBody UserCredentialsDto UserCredentialsDto) {
        UserCredentialsDto userLogin = userAuthFacade.getEncryptedUserCredentials(UserCredentialsDto);
        String token = tokenProvider.createToken(userLogin.getUsername());
        return ResponseEntity.ok(token);
    }
}
