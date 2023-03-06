package com.andersenlab.shop.dao;

import com.andersenlab.shop.model.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICurrencyDao extends CrudRepository<Currency, Long> {
}
