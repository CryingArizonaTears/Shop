package by.shop.controller.all;

import by.shop.annotation.Logging;
import by.shop.dto.UserCredentialsDto;
import by.shop.dto.UserProfileDto;
import by.shop.facade.UserAuthFacade;
import by.shop.facade.UserFacade;
import by.shop.security.filter.TokenProvider;
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
