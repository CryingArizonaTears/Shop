package by.shop.facade;

import by.shop.dto.BucketDto;
import by.shop.dto.ProductDto;

public interface BucketFacade {
    BucketDto getById(Long id);

    BucketDto addProductToBucket(Long id, ProductDto productDto);

    BucketDto deleteProductFromBucket(Long id, ProductDto productDto);

    BucketDto clearBucket(Long id);
}
