package com.andersenlab.shop.facade;

import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.ProductDto;

public interface BucketFacade {
    BucketDto getById(Long id);

    BucketDto addProductToBucket(Long id, ProductDto productDto);

    BucketDto deleteProductFromBucket(Long id, ProductDto productDto);

    BucketDto clearBucket(Long id);
}
