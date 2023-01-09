package ru.romanov.shop.web.app.entity;

import lombok.Getter;
import lombok.Setter;
import ru.romanov.shop.web.app.type.ProductType;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "catalog", schema = "public")
public class Catalog extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "catalog_name")
    private String catalogName;

    @Column(name = "product_type")
    private ProductType type;

}
