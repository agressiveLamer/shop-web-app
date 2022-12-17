package ru.romanov.shop.web.app.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {

    @CreationTimestamp
    @Column(name = "created")
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;
}
