package ru.romanov.shop.web.app.dto;

import lombok.Data;
import ru.romanov.shop.web.app.entity.CashReceipt;
import ru.romanov.shop.web.app.entity.Product;

@Data
public class GoodDto {

    private Long id;
    private Product product;
    private CashReceipt cashReceipt;
    private boolean isSales;


}
