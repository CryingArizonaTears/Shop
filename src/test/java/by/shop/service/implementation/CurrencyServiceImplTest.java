package by.shop.service.implementation;

import by.shop.model.Currency;
import by.shop.repository.CurrencyRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class CurrencyServiceImplTest {

    @InjectMocks
    CurrencyServiceImpl currencyService;
    @Mock
    CurrencyRepository currencyRepository;
    final Currency currencyForTesting = new Currency();

    @BeforeEach
    void beforeTests() {
        currencyForTesting.setMultiplier(BigDecimal.valueOf(1));
        currencyForTesting.setName("testCurrency");
    }

    @Test
    void testGetAll_Successful() {
        List<Currency> currencies = List.of(currencyForTesting);
        when(currencyRepository.findAll())
                .thenReturn(currencies);
        List<Currency> expectingCurrencies = currencyService.getAll();
        assertEquals(expectingCurrencies, currencies);
    }

    @Test
    void testGetById_Successful() {
        when(currencyRepository.findById(any()))
                .thenReturn(Optional.of(currencyForTesting));
        Currency expectingCurrency = currencyService.getById(1L);
        assertEquals(expectingCurrency, currencyForTesting);
    }

    @Test
    void testCreate_Successful() {
        currencyService.create(currencyForTesting);
        verify(currencyRepository).save(argThat(currencyForSaving ->
                currencyForSaving.getName().equals("testCurrency") &&
                        currencyForSaving.getMultiplier().equals(BigDecimal.valueOf(1))));
    }

    @Test
    void testEdit_Successful() {
        Currency currencyWithEdits = new Currency();
        currencyWithEdits.setName("editedName");
        currencyWithEdits.setMultiplier(BigDecimal.valueOf(2));
        when(currencyRepository.findById(any()))
                .thenReturn(Optional.of(currencyForTesting));
        currencyService.edit(currencyWithEdits);
        verify(currencyRepository).save(argThat(currencyForSaving ->
                currencyForSaving.equals(currencyWithEdits)));
    }

    @Test
    void delete() {
        when(currencyRepository.findById(any()))
                .thenReturn(Optional.of(currencyForTesting));
        currencyService.delete(1L);
        verify(currencyRepository).delete(currencyForTesting);
    }
}