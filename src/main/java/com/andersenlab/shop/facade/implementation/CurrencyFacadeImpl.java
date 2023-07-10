package com.andersenlab.shop.facade.implementation;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.CurrencyDto;
import com.andersenlab.shop.facade.CurrencyFacade;
import com.andersenlab.shop.model.Currency;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.CurrencyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CurrencyFacadeImpl implements CurrencyFacade {

    CurrencyService currencyService;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<CurrencyDto> getAll() {
        return modelMapper.mapList(currencyService.getAll(), CurrencyDto.class);
    }

    @Logging
    @Override
    public CurrencyDto getById(Long id) {
        return modelMapper.map(currencyService.getById(id), CurrencyDto.class);
    }

    @Logging
    @Override
    public CurrencyDto create(CurrencyDto currencyDto) {
        return modelMapper.map(currencyService.create(modelMapper.map(currencyDto, Currency.class)), CurrencyDto.class);
    }

    @Logging
    @Override
    public CurrencyDto edit(CurrencyDto currencyDto) {
        return modelMapper.map(currencyService.edit(modelMapper.map(currencyDto, Currency.class)), CurrencyDto.class);
    }

    @Logging
    @Override
    public void delete(Long id) {
        currencyService.delete(id);
    }
}
