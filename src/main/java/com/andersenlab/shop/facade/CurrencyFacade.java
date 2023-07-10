package com.andersenlab.shop.facade;

import com.andersenlab.shop.dto.CurrencyDto;

import java.util.List;

public interface CurrencyFacade {

    List<CurrencyDto> getAll();

    CurrencyDto getById(Long id);

    CurrencyDto create(CurrencyDto currencyDto);

    CurrencyDto edit(CurrencyDto currencyDto);

    void delete(Long id);
}
