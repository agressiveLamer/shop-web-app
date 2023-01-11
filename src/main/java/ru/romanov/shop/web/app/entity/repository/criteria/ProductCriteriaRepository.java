/*
package ru.romanov.shop.web.app.entity.repository.criteria;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import ru.romanov.shop.web.app.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class
ProductCriteriaRepository {

    //TODO: Добавить возможность пагинации и сортировки
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public Page<Product> findAllWithFilters(ProductPage productPage,
                                            ProductFilter productFilter) {

        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Predicate predicate = getPredicate(productFilter, productRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(productPage.getPageNumber());
        typedQuery.setMaxResults(productPage.getPageSize());

        Pageable pageable = getPageable(productPage);

        long productCount = getProductCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, productCount);

    }

    private Predicate getPredicate(ProductFilter productFilter,
                                   Root<Product> productRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(productFilter.getDescribe())) {
            predicates.add(
                    criteriaBuilder.like(productRoot.get("describe"),
                            "%" + productFilter.getDescribe() + "%")
            );
        }
        if (Objects.nonNull(productFilter.getName())) {
            predicates.add(
                    criteriaBuilder.like(productRoot.get("name"),
                            "%" + productFilter.getName() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


    private Pageable getPageable(ProductPage productPage) {
        Sort sort = Sort.by(productPage.getSortDirection(), productPage.getSortBy());
        return PageRequest.of(productPage.getPageNumber(),productPage.getPageSize(), sort);
    }

    private long getProductCount(Predicate predicate){
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        criteriaQuery.select(criteriaBuilder.count(productRoot)).where(predicate);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
*/
