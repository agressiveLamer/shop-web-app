package ru.romanov.shop.web.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "product", schema = "public")
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "describe")
    private String describe;

    @Column(name = "cost")
    private Double cost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @Column(name = "is_sold", nullable = false)
    private Boolean isSold;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    @JsonBackReference
    private CashReceipt cashReceipt;
}
