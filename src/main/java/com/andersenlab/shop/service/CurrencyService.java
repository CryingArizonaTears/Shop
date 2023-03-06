package com.andersenlab.shop.service;

import com.andersenlab.shop.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {

    List<CurrencyDto> getAll();
    CurrencyDto getById(Long id);

    void create(CurrencyDto currencyDto);

    void edit(CurrencyDto currencyDto);

    void delete(CurrencyDto currencyDto);
}
