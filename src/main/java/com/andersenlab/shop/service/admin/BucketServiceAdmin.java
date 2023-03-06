package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dao.IBucketDao;
import com.andersenlab.shop.dao.IProductDao;
import com.andersenlab.shop.dto.BucketDto;
import com.andersenlab.shop.dto.ProductDto;
import com.andersenlab.shop.model.Bucket;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.IBucketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BucketServiceAdmin implements IBucketService {
    IBucketDao bucketDao;
    IProductDao productDao;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public BucketDto getById(Long id) {
        return modelMapper.map(bucketDao.findById(id), BucketDto.class);
    }
    @Logging
    @Override
    public void addProductToBucket(BucketDto bucketDto, ProductDto productDto) {
        BucketDto bucket = modelMapper.map(bucketDao.findById(bucketDto.getId()), BucketDto.class);
        ProductDto product = modelMapper.map(productDao.findById(productDto.getId()), ProductDto.class);
        bucket.getProducts().add(product);
        bucketDao.save(modelMapper.map(bucket, Bucket.class));
    }

    @Logging
    @Override
    public void deleteProductFromBucket(BucketDto bucketDto, ProductDto productDto) {
        BucketDto bucket = modelMapper.map(bucketDao.findById(bucketDto.getId()), BucketDto.class);
        ProductDto product = modelMapper.map(productDao.findById(productDto.getId()), ProductDto.class);
        bucket.getProducts().remove(product);
        bucketDao.save(modelMapper.map(bucket, Bucket.class));
    }
}
