package com.andersenlab.shop.controller.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/buckets")
public class BucketControllerAdmin {

    @Autowired
    public BucketControllerAdmin(@Qualifier("bucketServiceAdmin") BucketService bucketService) {
        this.bucketService = bucketService;
    }

    private final BucketService bucketService;

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BucketDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(bucketService.getById(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Void> addProductToBucket(@RequestBody BucketDto bucketDto, @RequestBody ProductDto productDto) {
        bucketService.addProductToBucket(bucketDto, productDto);
        return ResponseEntity.ok().build();
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping()
    public ResponseEntity<Void> deleteProductFromBucket(@RequestBody BucketDto bucketDto, @RequestBody ProductDto productDto) {
        bucketService.deleteProductFromBucket(bucketDto, productDto);
        return ResponseEntity.ok().build();
    }
}
