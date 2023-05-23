package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.repository.CurrencyRepository;
import com.andersenlab.shop.dto.CurrencyDto;
import com.andersenlab.shop.model.Currency;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.CurrencyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CurrencyServiceAdminImpl implements CurrencyService {

    CurrencyRepository currencyRepository;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<CurrencyDto> getAll() {
        List<Currency> currencies = (List<Currency>) currencyRepository.findAll();
        return modelMapper.mapList(currencies, CurrencyDto.class);
    }

    @Logging
    @Override
    public CurrencyDto getById(Long id) {
        return modelMapper.map(currencyRepository.findById(id), CurrencyDto.class);
    }

    @Logging
    @Override
    public void create(CurrencyDto currencyDto) {
        currencyRepository.save(modelMapper.map(currencyDto, Currency.class));
    }

    @Logging
    @Override
    public void edit(CurrencyDto currencyDto) {
        currencyRepository.save(modelMapper.map(currencyDto, Currency.class));
    }

    @Logging
    @Override
    public void delete(CurrencyDto currencyDto) {
        currencyRepository.delete(modelMapper.map(currencyDto, Currency.class));
    }
}
