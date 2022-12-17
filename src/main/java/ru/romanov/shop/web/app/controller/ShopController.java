package ru.romanov.shop.web.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.shop.web.app.dto.CashReceiptDto;
import ru.romanov.shop.web.app.dto.ProductDto;
import ru.romanov.shop.web.app.service.ShopService;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;

    @GetMapping(value = "/all-products")
    public ResponseEntity<?> getGoods(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "cost", required = false) Double cost
    ) {
        if (name != null && cost != null) {
            return ResponseEntity.ok(shopService.getAllByNameAndCost(name, cost));
        }
        if (name != null) {
            return ResponseEntity.ok(shopService.getAllProductByName(name));
        }
        if (cost != null) {
            return ResponseEntity.ok(shopService.getAllProductByCost(cost));
        }
        return ResponseEntity.ok(shopService.getAllProducts());
    }

    @PostMapping(value = "/create-product")
    public void createProduct(@RequestBody ProductDto productDto) {
        shopService.createProduct(productDto);
    }

    @PostMapping(value = "/update-product")
    public void updateProduct(@RequestBody ProductDto productDto,
                              @RequestParam(name = "id") Long id) {
        shopService.updateProduct(productDto, id);
    }

    @DeleteMapping(value = "/delete-product")
    public void deleteProduct(@RequestParam(name = "id") Long id) {
        shopService.deleteProduct(id);
    }

    @GetMapping(value = "/cash-receipts")
    public ResponseEntity<?> getCashReceipts(@RequestParam(name = "id", required = false) Long id,
                                             @RequestParam(name = "clientId", required = false) Long clientId) {
        if (id != null) {
            return ResponseEntity.ok(shopService.getCashReceiptById(id));
        }
        if (clientId != null) {
            return ResponseEntity.ok(shopService.getCashReceiptsByClient(clientId));
        }
        return ResponseEntity.ok(shopService.getAllCashReceipts());
    }

    @PostMapping(value = "/create-cash-receipt")
    public void createCashReceipt(@RequestBody CashReceiptDto cashReceiptDto) {
        log.info("http request:" + cashReceiptDto);
        shopService.createCashReceipt(cashReceiptDto);
    }
}
