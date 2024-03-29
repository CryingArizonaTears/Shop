package by.shop.dto;

import by.shop.model.ProductType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    Long id;
    String name;
    ProductType productType;
    BigDecimal price;
    Integer expDate;
    WarehouseDto warehouse;
}
