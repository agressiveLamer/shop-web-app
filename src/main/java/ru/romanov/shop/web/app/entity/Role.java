package ru.romanov.shop.web.app.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "role", schema = "public")
public class Role extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<User> user;
}
