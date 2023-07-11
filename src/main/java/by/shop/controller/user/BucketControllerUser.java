package by.shop.controller.user;

import by.shop.annotation.Logging;
import by.shop.dto.BucketDto;
import by.shop.dto.ProductDto;
import by.shop.dto.UserProfileDto;
import by.shop.facade.BucketFacade;
import by.shop.facade.UserAuthFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/buckets")
public class BucketControllerUser {

    BucketFacade bucketFacade;
    UserAuthFacade userAuthFacade;

    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping()
    public ResponseEntity<BucketDto> getBucketById() {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        return ResponseEntity.ok(bucketFacade.getById(currentUser.getId()));
    }

    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping()
    public ResponseEntity<BucketDto> addProductToBucket(@RequestBody ProductDto productDto) {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        return ResponseEntity.ok(bucketFacade.addProductToBucket(currentUser.getBucket().getId(), productDto));
    }

    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping()
    public ResponseEntity<BucketDto> deleteProductFromBucket(@RequestBody ProductDto productDto) {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        return ResponseEntity.ok(bucketFacade.deleteProductFromBucket(currentUser.getBucket().getId(), productDto));
    }

    @Logging
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping()
    public ResponseEntity<BucketDto> clearBucket() {
        UserProfileDto currentUser = userAuthFacade.getCurrent();
        return ResponseEntity.ok(bucketFacade.clearBucket(currentUser.getBucket().getId()));
    }
}
