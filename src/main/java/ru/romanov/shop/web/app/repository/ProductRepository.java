package ru.romanov.shop.web.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.shop.web.app.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {
    List<Product> findAllByName(String name);
    List<Product> findAllByCost(Double cost);
    List<Product> findAllByNameAndCost(String name, Double cost);
}
