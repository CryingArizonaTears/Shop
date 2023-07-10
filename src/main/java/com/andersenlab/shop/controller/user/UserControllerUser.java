package com.andersenlab.shop.controller.user;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.facade.UserAuthFacade;
import com.andersenlab.shop.facade.UserFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/users")
public class UserControllerUser {

    UserFacade userFacade;
    UserAuthFacade userAuthFacade;


    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping()
    public ResponseEntity<UserProfileDto> getById() {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        return ResponseEntity.ok(userFacade.getById(currentUser.getId()));
    }

    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> editProfile(@RequestBody UserProfileDto userProfileDto) {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        userProfileDto.setId(currentUser.getId());
        return ResponseEntity.ok(userFacade.editProfileAsUser(userProfileDto));
    }

    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/credentials")
    public ResponseEntity<UserCredentialsDto> editCredentials(@RequestBody UserCredentialsDto userCredentialsDto) {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        userCredentialsDto.setId(currentUser.getId());
        return ResponseEntity.ok(userFacade.editCredentials(userCredentialsDto));
    }
}
