package com.andersenlab.shop.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Data
@Table(name = "order_")
public class Order extends AbstractModel {
    @Column(name = "id", nullable = false)
    Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    UserProfile userProfile;
    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<Product> products = new ArrayList<>();
    @Column(name = "date", nullable = false)
    LocalDate date;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", nullable = false)
    Currency currency;
    @Column(name = "processed", nullable = false)
    Boolean processed;
    @Column(name = "total_price", nullable = false)
    BigDecimal totalPrice;
}
