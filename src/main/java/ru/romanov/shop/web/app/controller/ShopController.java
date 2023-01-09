package ru.romanov.shop.web.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.romanov.shop.web.app.dto.CashReceiptDto;
import ru.romanov.shop.web.app.dto.ProductDto;
import ru.romanov.shop.web.app.service.ShopService;

import java.util.List;


@Api("Контроллер для работы с товарами")
@Slf4j
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @ApiOperation("Получение товаров")
    @GetMapping(value = "/all-products", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ProductDto> getGoods(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "cost", required = false) Double cost
    ) {
        if (name != null && cost != null) {
        }
        if (name != null) {
            return shopService.getAllProductByName(name);
        }
        if (cost != null) {
            return shopService.getAllProductByCost(cost);
        }
        return shopService.getAllProducts();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Создание/редактирование товара")
    @PostMapping(value = "/save-product", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void saveProduct(@RequestBody ProductDto dto) {
        shopService.saveProduct(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Удаление товара")
    @DeleteMapping(value = "/delete-product")
    public void deleteProduct(@RequestParam(name = "id") Long id) {
        shopService.deleteProduct(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation("Получение чеков(сделанных покупок)")
    @GetMapping(value = "/cash-receipts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CashReceiptDto> getCashReceipts(@RequestParam(name = "id", required = false) Long id,
                                                @RequestParam(name = "clientId", required = false) Long clientId) {
        /*if (id != null) {
            return shopService.getCashReceiptById(id);
        }*/
        if (clientId != null) {
            return shopService.getCashReceiptsByClient(clientId);
        }
        return shopService.getAllCashReceipts();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @ApiOperation("Создание чека (покупки)")
    @PostMapping(value = "/create-cash-receipt", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void createCashReceipt(@RequestBody CashReceiptDto cashReceiptDto) {
        log.info("http request:" + cashReceiptDto);
        shopService.createCashReceipt(cashReceiptDto);
    }
}
