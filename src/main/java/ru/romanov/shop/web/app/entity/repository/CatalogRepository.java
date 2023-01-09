package ru.romanov.shop.web.app.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.shop.web.app.entity.Catalog;


@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

}
