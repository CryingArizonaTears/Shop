package com.andersenlab.shop.controller.admin;

import com.andersenlab.shop.annotation.Logging;
import com.andersenlab.shop.dto.CurrencyDto;
import com.andersenlab.shop.service.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/currency")
public class CurrencyControllerAdmin {

    @Autowired
    public CurrencyControllerAdmin(@Qualifier("currencyServiceAdmin") ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    private final ICurrencyService currencyService;

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getAll() {
        return ResponseEntity.ok(currencyService.getAll());
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(currencyService.getById(id));
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody CurrencyDto currencyDto) {
        currencyService.create(currencyDto);
        return ResponseEntity.ok().build();
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PutMapping()
    public ResponseEntity<Void> edit(@RequestBody CurrencyDto currencyDto) {
        currencyService.edit(currencyDto);
        return ResponseEntity.ok().build();
    }

    @Logging
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setId(id);
        currencyService.delete(currencyDto);
        return ResponseEntity.ok().build();
    }
}
