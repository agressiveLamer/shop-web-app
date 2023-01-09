package ru.romanov.shop.web.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Setter
@Getter
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
}
