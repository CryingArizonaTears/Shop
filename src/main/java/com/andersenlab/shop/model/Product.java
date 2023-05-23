package com.andersenlab.shop.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Data
@Table(name = "product")
public class Product extends AbstractModel{
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "product_type", nullable = false)
    @Enumerated(EnumType.STRING)
    ProductType productType;
    @Column(name = "price", nullable = false)
    BigDecimal price;
    @Column(name = "expDate")
    Integer expDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", nullable = false)
    Warehouse warehouse;
}
