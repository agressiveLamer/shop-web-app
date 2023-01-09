package ru.romanov.shop.web.app.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanov.shop.web.app.converter.CashReceiptConverter;
import ru.romanov.shop.web.app.converter.ProductConverter;
import ru.romanov.shop.web.app.dto.CashReceiptDto;
import ru.romanov.shop.web.app.dto.ProductDto;
import ru.romanov.shop.web.app.entity.CashReceipt;
import ru.romanov.shop.web.app.entity.Product;
import ru.romanov.shop.web.app.entity.User;
import ru.romanov.shop.web.app.entity.repository.CashReceiptsRepository;
import ru.romanov.shop.web.app.entity.repository.ProductRepository;
import ru.romanov.shop.web.app.entity.repository.UserRepository;
import ru.romanov.shop.web.app.exception.ResourceNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CashReceiptsRepository cashReceiptsRepository;
    private final CashReceiptConverter cashReceiptConverter;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProductByName(String name) {
        return productRepository.findAllByName(name)
                .stream()
                .map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProductByCost(Double cost) {
        return productRepository.findAllByCost(cost)
                .stream()
                .map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAllByNameAndCost(String name, Double cost) {
        return productRepository.findAllByNameAndCost(name, cost)
                .stream()
                .map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveProduct(ProductDto dto){
        if (dto.getId() == null){
            productRepository.save(productConverter.convertFromDto(dto));
        }else {
            Product product;
            Optional<Product> productOptional = productRepository.findById(dto.getId());
            product = productOptional.orElseThrow(NoSuchElementException::new);
            product.setCatalog(dto.getCatalog());
            product.setName(dto.getName());
            product.setCost(dto.getCost());
            product.setDescribe(dto.getDescribe());
            productRepository.save(product);
        }

    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CashReceiptDto> getAllCashReceipts() {
        return cashReceiptsRepository.findAll().stream()
                .map(cashReceiptConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CashReceiptDto getCashReceiptById(Long id) {
        return cashReceiptConverter.convertFromEntity(cashReceiptsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cash receipts with " + id + " not found")));
    }

    @Transactional(readOnly = true)
    public List<CashReceiptDto> getCashReceiptsByClient(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with" + id + " not found"));
        return cashReceiptsRepository.findAllByUser(user)
                .stream()
                .map(cashReceiptConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createCashReceipt(CashReceiptDto cashReceiptDto) {
        CashReceipt cashReceipt = cashReceiptConverter.convertFromDto(cashReceiptDto);
        List<Product> products = productRepository.findAllById(cashReceipt.
                getProducts().
                stream().
                map(Product::getId).
                collect(Collectors.toList()));
        products.forEach(product -> {
            product.setCashReceipt(cashReceipt);
            product.setIsSold(true);
        });
        cashReceiptsRepository.save(cashReceipt);
    }


}