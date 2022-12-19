package ru.romanov.shop.web.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.romanov.shop.web.app.entity.Role;

@Service
public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
