package com.andersenlab.shop.service;

import com.andersenlab.shop.model.Currency;

import java.util.List;

public interface CurrencyService {

    List<Currency> getAll();

    Currency getById(Long id);

    Currency create(Currency currency);

    Currency edit(Currency currency);

    void delete(Long id);
}
