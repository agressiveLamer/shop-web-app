package ru.romanov.shop.web.app.converter;

import org.springframework.stereotype.Component;
import ru.romanov.shop.web.app.dto.ProductDto;
import ru.romanov.shop.web.app.entity.Product;

@Component
/*@UtilityClass*/
public class ProductConverter {

    public ProductDto convertFromEntity(Product entity) {
        final ProductDto productDto = new ProductDto();

        productDto.setId(entity.getId());
        productDto.setCost(entity.getCost());
        productDto.setDescribe(entity.getDescribe());
        productDto.setName(entity.getName());
        productDto.setIsSold(entity.getIsSold());
        productDto.setCatalog(entity.getCatalog());
        return productDto;
    }

    public Product convertFromDto(ProductDto dto) {
        final Product product = new Product();
        product.setId(dto.getId());
        product.setCost(dto.getCost());
        product.setDescribe(dto.getDescribe());
        product.setName(dto.getName());
        product.setCatalog(dto.getCatalog());
        product.setIsSold(dto.getIsSold());
        return product;
    }

}
