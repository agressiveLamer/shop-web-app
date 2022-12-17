package ru.romanov.shop.web.app.converter;

import org.springframework.stereotype.Component;
import ru.romanov.shop.web.app.dto.GoodDto;
import ru.romanov.shop.web.app.entity.Catalog;

@Component
public class CatalogConverter {

    public GoodDto convertFromEntity(Catalog entity){
        final GoodDto goodDto = new GoodDto();
        goodDto.setId(entity.getId());
        return goodDto;
    }
}
