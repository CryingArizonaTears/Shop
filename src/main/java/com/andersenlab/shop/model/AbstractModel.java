package com.andersenlab.shop.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    private Long id;
}
