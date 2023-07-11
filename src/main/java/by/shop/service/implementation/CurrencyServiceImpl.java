package by.shop.service.implementation;

import by.shop.annotation.Logging;
import by.shop.model.Currency;
import by.shop.repository.CurrencyRepository;
import by.shop.service.CurrencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    CurrencyRepository currencyRepository;

    @Logging
    @Override
    public List<Currency> getAll() {
        return (List<Currency>) currencyRepository.findAll();
    }

    @Logging
    @Override
    public Currency getById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Currency with id " + id + " not found "));
    }

    @Logging
    @Override
    public Currency create(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Logging
    @Override
    public Currency edit(Currency currency) {
        Currency currencyFromRepo = getById(currency.getId());
        if (currency.getName() != null) {
            currencyFromRepo.setName(currency.getName());
        }
        if (currency.getMultiplier() != null) {
            currencyFromRepo.setMultiplier(currency.getMultiplier());
        }
        return currencyRepository.save(currencyFromRepo);
    }

    @Logging
    @Override
    public void delete(Long id) {
        Currency existingCurrency = getById(id);
        currencyRepository.delete(existingCurrency);
    }
}
