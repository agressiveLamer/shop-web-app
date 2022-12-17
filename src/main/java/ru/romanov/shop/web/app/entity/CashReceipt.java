package ru.romanov.shop.web.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "cash_receipts", schema = "public")
public class CashReceipt extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sum")
    private Double sum;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE,
            mappedBy = "cashReceipt")
    @JsonManagedReference
    private Set<Product> products;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CashReceipt that = (CashReceipt) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
