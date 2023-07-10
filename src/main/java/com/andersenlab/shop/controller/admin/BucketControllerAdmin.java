package com.andersenlab.shop.controller.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.facade.BucketFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/admin/buckets")
public class BucketControllerAdmin {

    BucketFacade bucketFacade;

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BucketDto> getBucketById(@PathVariable Long id) {
        return ResponseEntity.ok(bucketFacade.getById(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<BucketDto> addProductToBucket(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(bucketFacade.addProductToBucket(id, productDto));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BucketDto> deleteProductFromBucket(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(bucketFacade.deleteProductFromBucket(id, productDto));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BucketDto> clearBucket(@PathVariable Long id) {
        return ResponseEntity.ok(bucketFacade.clearBucket(id));
    }
}