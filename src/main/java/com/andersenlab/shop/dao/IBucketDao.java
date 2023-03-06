package com.andersenlab.shop.dao;

import com.andersenlab.shop.model.Bucket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBucketDao extends CrudRepository<Bucket, Long> {
}
