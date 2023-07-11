package by.shop.controller.admin;

import by.shop.annotation.Logging;
import by.shop.dto.UserCredentialsDto;
import by.shop.dto.UserProfileDto;
import by.shop.facade.UserFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/admin/users")
public class UserControllerAdmin {

    UserFacade userFacade;

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserProfileDto>> getAll() {
        return ResponseEntity.ok(userFacade.getAll());
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userFacade.getById(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserProfileDto> create(@RequestBody UserProfileDto userProfileDto) {
        return new ResponseEntity<>(userFacade.createAsAdmin(userProfileDto), HttpStatus.CREATED);
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> editProfile(@RequestBody UserProfileDto userProfileDto) {
        return ResponseEntity.ok(userFacade.editProfileAsAdmin(userProfileDto));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/credentials")
    public ResponseEntity<UserCredentialsDto> editCredentials(@RequestBody UserCredentialsDto userCredentialsDto) {
        return ResponseEntity.ok(userFacade.editCredentials(userCredentialsDto));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
