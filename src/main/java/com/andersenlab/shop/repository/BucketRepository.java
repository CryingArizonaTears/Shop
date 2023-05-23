package com.andersenlab.shop.repository;

import com.andersenlab.shop.model.Bucket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends CrudRepository<Bucket, Long> {
}
