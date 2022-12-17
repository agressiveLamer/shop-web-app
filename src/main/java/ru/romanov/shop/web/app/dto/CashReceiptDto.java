package ru.romanov.shop.web.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.romanov.shop.web.app.entity.User;
import ru.romanov.shop.web.app.entity.Product;

import java.util.Set;

@Data
public class CashReceiptDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("sum")
    private Double sum;
    private Set<Product> products;
    private User user;
}
