package ru.romanov.shop.web.app.entity.repository.criteria;

import lombok.Getter;
import lombok.Setter;
import ru.romanov.shop.web.app.entity.Catalog;

@Setter
@Getter
public class ProductFilter {
    private Long id;
    private String name;
    private String describe;
    private Double cost;
    private Catalog catalog;
    private Boolean isSold;
}
