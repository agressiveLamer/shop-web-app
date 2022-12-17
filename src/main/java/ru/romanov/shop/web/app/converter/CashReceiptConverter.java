package ru.romanov.shop.web.app.converter;

import org.springframework.stereotype.Component;
import ru.romanov.shop.web.app.dto.CashReceiptDto;
import ru.romanov.shop.web.app.entity.CashReceipt;

@Component
public class CashReceiptConverter {

    public CashReceipt convertFromDto(CashReceiptDto dto) {
        CashReceipt cashReceipt = new CashReceipt();
        cashReceipt.setId(dto.getId());
        cashReceipt.setUser(dto.getUser());
        cashReceipt.setSum(dto.getSum());
        cashReceipt.setProducts(dto.getProducts());
        return cashReceipt;
    }

    public CashReceiptDto convertFromEntity(CashReceipt entity) {
        CashReceiptDto cashReceiptDto = new CashReceiptDto();
        cashReceiptDto.setUser(entity.getUser());
        cashReceiptDto.setId(entity.getId());
        cashReceiptDto.setProducts(entity.getProducts());
        cashReceiptDto.setSum(entity.getSum());
        return cashReceiptDto;
    }

}
