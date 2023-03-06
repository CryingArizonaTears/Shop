package com.andersenlab.shop.service.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dao.ICurrencyDao;
import com.andersenlab.shop.dto.CurrencyDto;
import com.andersenlab.shop.model.Currency;
import com.andersenlab.shop.modelMapperMethods.ExtendedModelMapper;
import com.andersenlab.shop.service.ICurrencyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CurrencyServiceAdmin implements ICurrencyService {

    ICurrencyDao currencyDao;
    ExtendedModelMapper modelMapper;

    @Logging
    @Override
    public List<CurrencyDto> getAll() {
        List<Currency> currencies = (List<Currency>) currencyDao.findAll();
        return modelMapper.mapList(currencies, CurrencyDto.class);
    }

    @Logging
    @Override
    public CurrencyDto getById(Long id) {
        return modelMapper.map(currencyDao.findById(id), CurrencyDto.class);
    }

    @Logging
    @Override
    public void create(CurrencyDto currencyDto) {
        currencyDao.save(modelMapper.map(currencyDto, Currency.class));
    }

    @Logging
    @Override
    public void edit(CurrencyDto currencyDto) {
        currencyDao.save(modelMapper.map(currencyDto, Currency.class));
    }

    @Logging
    @Override
    public void delete(CurrencyDto currencyDto) {
        currencyDao.delete(modelMapper.map(currencyDto, Currency.class));
    }
}
