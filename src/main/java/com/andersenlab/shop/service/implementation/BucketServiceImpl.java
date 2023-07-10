package com.andersenlab.shop.service.implementation;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.model.Bucket;
import com.andersenlab.shop.model.Product;
import com.andersenlab.shop.repository.BucketRepository;
import com.andersenlab.shop.service.BucketService;
import com.andersenlab.shop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {
    BucketRepository bucketRepository;
    ProductService productService;

    @Logging
    @Override
    public Bucket getById(Long id) {
        return bucketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bucket with id " + id + " not found"));
    }

    @Logging
    @Override
    public Bucket addProductToBucket(Long id, Product product) {
        Bucket bucket = getById(id);
        Product productFromRepo = productService.getById(product.getId());
        List<Product> productsInBucket = new ArrayList<>(bucket.getProducts());
        productsInBucket.add(productFromRepo);
        bucket.setProducts(productsInBucket);
        BigDecimal totalPrice = bucket.getTotalPrice();
        totalPrice = totalPrice.add(productFromRepo.getPrice());
        bucket.setTotalPrice(totalPrice);
        return bucketRepository.save(bucket);
    }

    @Logging
    @Override
    public Bucket deleteProductFromBucket(Long id, Product product) {
        Bucket bucket = getById(id);
        Product productFromRepo = productService.getById(product.getId());
        List<Product> productsInBucket = new ArrayList<>(bucket.getProducts());
        if (productsInBucket.contains(productFromRepo)) {
            productsInBucket.remove(productFromRepo);
            bucket.setProducts(productsInBucket);
            BigDecimal totalPrice = bucket.getTotalPrice();
            bucket.setTotalPrice(totalPrice.subtract(productFromRepo.getPrice()));
            return bucketRepository.save(bucket);
        }
        throw new EntityNotFoundException("Bucket with " + id + " does not contain product with id " + product.getId());
    }

    @Override
    public Bucket clearBucket(Long id) {
        Bucket bucketFromRepo = getById(id);
        bucketFromRepo.setProducts(new ArrayList<>());
        bucketFromRepo.setTotalPrice(BigDecimal.valueOf(0));
        return bucketRepository.save(bucketFromRepo);
    }
}
