package com.andersenlab.shop.service;

import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.ProductDto;

public interface BucketService {

    BucketDto getById(Long id);
    void addProductToBucket(BucketDto bucketDto, ProductDto productDto);
    void deleteProductFromBucket(BucketDto bucketDto, ProductDto productDto);
}
