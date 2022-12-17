package ru.romanov.shop.web.app.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import ru.romanov.shop.web.app.entity.Catalog;



@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductDto {

    private Long id;
    private String name;
    private String describe;
    private Double cost;
    private Catalog catalog;
    private Boolean isSold = false;
}
