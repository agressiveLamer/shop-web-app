package ru.romanov.shop.web.app.service;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.romanov.shop.web.app.converter.CashReceiptConverter;
import ru.romanov.shop.web.app.converter.ProductConverter;
import ru.romanov.shop.web.app.dto.CashReceiptDto;
import ru.romanov.shop.web.app.dto.ProductDto;
import ru.romanov.shop.web.app.entity.CashReceipt;
import ru.romanov.shop.web.app.entity.Product;
import ru.romanov.shop.web.app.entity.User;
import ru.romanov.shop.web.app.repository.CashReceiptsRepository;
import ru.romanov.shop.web.app.repository.ProductRepository;
import ru.romanov.shop.web.app.repository.UserRepository;

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

    @Transactional
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDto> getAllProductByName(String name) {
        return productRepository.findAllByName(name)
                .stream()
                .map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDto> getAllProductByCost(Double cost) {
        return productRepository.findAllByCost(cost)
                .stream()
                .map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDto> getAllByNameAndCost(String name, Double cost) {
        return productRepository.findAllByNameAndCost(name, cost)
                .stream()
                .map(productConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createProduct(ProductDto productDto) {
        productRepository.save(productConverter.convertFromDto(productDto));
    }

    @Transactional
    public void updateProduct(ProductDto productDto, Long id) {
        Product product;
        Optional<Product> productOptional = productRepository.findById(id);
        product = productOptional.orElseThrow(NoSuchElementException::new);
        product.setCatalog(productDto.getCatalog());
        product.setName(productDto.getName());
        product.setCost(productDto.getCost());
        product.setDescribe(productDto.getDescribe());
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public List<CashReceiptDto> getAllCashReceipts() {
        return cashReceiptsRepository.findAll().stream()
                .map(cashReceiptConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CashReceiptDto getCashReceiptById(Long id){
        return cashReceiptConverter.convertFromEntity(cashReceiptsRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    @Transactional
    public List<CashReceiptDto> getCashReceiptsByClient(Long id){
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
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