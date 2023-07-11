package by.shop.controller.all;

import by.shop.annotation.Logging;
import by.shop.dto.CurrencyDto;
import by.shop.facade.CurrencyFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/currencies")
public class CurrencyController {

    CurrencyFacade currencyFacade;

    @Logging
    @GetMapping()
    public ResponseEntity<List<CurrencyDto>> getAll() {
        return ResponseEntity.ok(currencyFacade.getAll());
    }

}
