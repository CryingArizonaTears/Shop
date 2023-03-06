package com.andersenlab.shop.controller.all;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.CurrencyDto;
import com.andersenlab.shop.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/currencies")
public class CurrencyController {

    @Autowired
    public CurrencyController(@Qualifier("currencyService") CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    private final CurrencyService currencyService;

    @Logging
    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getAll() {
        return ResponseEntity.ok(currencyService.getAll());
    }

}
