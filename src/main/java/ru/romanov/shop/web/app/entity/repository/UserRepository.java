package ru.romanov.shop.web.app.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.romanov.shop.web.app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);
}
