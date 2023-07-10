package com.andersenlab.shop.service;

import com.andersenlab.shop.model.Bucket;
import com.andersenlab.shop.model.Product;

public interface BucketService {

    Bucket getById(Long id);

    Bucket addProductToBucket(Long id, Product product);

    Bucket deleteProductFromBucket(Long id, Product product);

    Bucket clearBucket(Long id);
}
