package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.repository.BucketRepository;
import com.andersenlab.shop.repository.ProductRepository;
import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.model.Bucket;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.BucketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BucketServiceAdminImpl implements BucketService {
    BucketRepository bucketRepository;
    ProductRepository productRepository;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public BucketDto getById(Long id) {
        return modelMapper.map(bucketRepository.findById(id), BucketDto.class);
    }
    @Logging
    @Override
    public void addProductToBucket(BucketDto bucketDto, ProductDto productDto) {
        BucketDto bucket = modelMapper.map(bucketRepository.findById(bucketDto.getId()), BucketDto.class);
        ProductDto product = modelMapper.map(productRepository.findById(productDto.getId()), ProductDto.class);
        bucket.getProducts().add(product);
        bucketRepository.save(modelMapper.map(bucket, Bucket.class));
    }

    @Logging
    @Override
    public void deleteProductFromBucket(BucketDto bucketDto, ProductDto productDto) {
        BucketDto bucket = modelMapper.map(bucketRepository.findById(bucketDto.getId()), BucketDto.class);
        ProductDto product = modelMapper.map(productRepository.findById(productDto.getId()), ProductDto.class);
        bucket.getProducts().remove(product);
        bucketRepository.save(modelMapper.map(bucket, Bucket.class));
    }
}
