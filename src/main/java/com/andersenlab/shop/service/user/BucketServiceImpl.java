package com.andersenlab.shop.service.user;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.repository.BucketRepository;
import com.andersenlab.shop.repository.ProductRepository;
import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.dto.UserProfileDto;
import com.andersenlab.shop.model.Bucket;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.BucketService;
import com.andersenlab.shop.service.UserAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {
    BucketRepository bucketRepository;
    ProductRepository productRepository;
    UserAuthenticationService userAuthenticationService;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public BucketDto getById(Long id) {
        UserProfileDto currentUser = userAuthenticationService.getCurrent();
        return modelMapper.map(bucketRepository.findById(currentUser.getId()), BucketDto.class);
    }

    @Logging
    @Override
    public void addProductToBucket(BucketDto bucketDto, ProductDto productDto) {
        UserProfileDto currentUser = userAuthenticationService.getCurrent();
        BucketDto bucket = modelMapper.map(bucketRepository.findById(currentUser.getId()), BucketDto.class);
        ProductDto product = modelMapper.map(productRepository.findById(productDto.getId()), ProductDto.class);
        bucket.getProducts().add(product);
        bucketRepository.save(modelMapper.map(bucket, Bucket.class));
    }

    @Logging
    @Override
    public void deleteProductFromBucket(BucketDto bucketDto, ProductDto productDto) {
        UserProfileDto currentUser = userAuthenticationService.getCurrent();
        BucketDto bucket = modelMapper.map(bucketRepository.findById(currentUser.getId()), BucketDto.class);
        ProductDto product = modelMapper.map(productRepository.findById(productDto.getId()), ProductDto.class);
        bucket.getProducts().remove(product);
        bucketRepository.save(modelMapper.map(bucket, Bucket.class));
    }
}
