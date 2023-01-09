package ru.romanov.shop.web.app.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.shop.web.app.entity.CashReceipt;
import ru.romanov.shop.web.app.entity.User;

import java.util.List;

@Repository
public interface CashReceiptsRepository extends JpaRepository<CashReceipt, Long> {
    List<CashReceipt> findAllByUser(User user);
}
