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
    UserProfileDto userProfile;
    List<ProductDto> products;
    LocalDate date;
    CurrencyDto currency;
    Boolean processed;
    BigDecimal totalPrice;
}
