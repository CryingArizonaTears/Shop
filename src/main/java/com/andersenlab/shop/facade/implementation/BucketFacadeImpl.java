package com.andersenlab.shop.facade.implementation;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.facade.BucketFacade;
import com.andersenlab.shop.model.Product;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.BucketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BucketFacadeImpl implements BucketFacade {

    BucketService bucketService;
    ExtendedModelMapper modelMapper;


    @Logging
    @Override
    public BucketDto getById(Long id) {
        return modelMapper.map(bucketService.getById(id), BucketDto.class);
    }

    @Logging
    @Override
    public BucketDto addProductToBucket(Long id, ProductDto productDto) {
        return modelMapper.map(bucketService.addProductToBucket(id,
                modelMapper.map(productDto, Product.class)), BucketDto.class);
    }

    @Logging
    @Override
    public BucketDto deleteProductFromBucket(Long id, ProductDto productDto) {
        return modelMapper.map(bucketService.deleteProductFromBucket(id,
                modelMapper.map(productDto, Product.class)), BucketDto.class);
    }

    @Override
    public BucketDto clearBucket(Long id) {
        return modelMapper.map(bucketService.clearBucket(id), BucketDto.class);
    }
}
