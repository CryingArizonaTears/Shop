package com.andersenlab.shop.controller.user;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.UserCredentialsDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserControllerUser {

    @Autowired
    public UserControllerUser(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @Logging
    @GetMapping()
    public ResponseEntity<UserProfileDto> getById() {
        return ResponseEntity.ok(userService.getById(null));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PutMapping("/profile")
    public ResponseEntity<Void> editProfile(@RequestBody UserProfileDto userProfileDto) {
        userService.editProfile(userProfileDto);
        return ResponseEntity.ok().build();
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PutMapping("/credentials")
    public ResponseEntity<Void> editCredentials(@RequestBody UserCredentialsDto userCredentialsDto) {
        userService.editCredentials(userCredentialsDto);
        return ResponseEntity.ok().build();
    }
}
