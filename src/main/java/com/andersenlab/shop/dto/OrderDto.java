package com.andersenlab.shop.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {
    Long id;
    UserProfileDto userProfileDto;
    List<ProductDto> products;
    LocalDate date;
    CurrencyDto currencyDto;
    Boolean processed;
    BigDecimal totalPrice;
}
