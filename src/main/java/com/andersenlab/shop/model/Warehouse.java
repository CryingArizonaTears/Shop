package com.andersenlab.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "warehouse")
@Data
public class Warehouse extends AbstractModel {
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "address", nullable = false)
    String address;
}
