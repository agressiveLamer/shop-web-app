package ru.romanov.shop.web.app.entity;

import lombok.*;
import org.hibernate.Hibernate;
import ru.romanov.shop.web.app.type.ProductType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Catalog catalog = (Catalog) o;
        return id != null && Objects.equals(id, catalog.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
