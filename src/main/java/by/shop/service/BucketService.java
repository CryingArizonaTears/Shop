package by.shop.service;

import by.shop.model.Bucket;
import by.shop.model.Product;

public interface BucketService {

    Bucket getById(Long id);

    Bucket addProductToBucket(Long id, Product product);

    Bucket deleteProductFromBucket(Long id, Product product);

    Bucket clearBucket(Long id);
}
