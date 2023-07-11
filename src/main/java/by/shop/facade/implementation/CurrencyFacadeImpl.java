package by.shop.facade.implementation;

import by.shop.annotation.Logging;
import by.shop.dto.CurrencyDto;
import by.shop.facade.CurrencyFacade;
import by.shop.model.Currency;
import by.shop.modelMapperMethods.ExtendedModelMapper;
import by.shop.service.CurrencyService;
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
