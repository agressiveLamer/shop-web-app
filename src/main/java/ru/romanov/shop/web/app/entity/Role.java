package ru.romanov.shop.web.app.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    @Table(name = "role", schema = "public")
    public class Role extends BaseEntity implements Serializable {
        @Id
        @Column(name = "id")
        private Long id;

        @Column(name = "name")
        private String name;

        @ManyToMany(fetch = FetchType.LAZY, mappedBy = "role")
        private Set<User> user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
