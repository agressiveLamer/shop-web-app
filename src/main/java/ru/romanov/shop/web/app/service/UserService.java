package ru.romanov.shop.web.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.romanov.shop.web.app.converter.UserConverter;
import ru.romanov.shop.web.app.dto.UserDto;
import ru.romanov.shop.web.app.entity.Role;
import ru.romanov.shop.web.app.entity.User;
import ru.romanov.shop.web.app.repository.RoleRepository;
import ru.romanov.shop.web.app.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(userConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void createUser(UserDto dto) {
        Role userRole = roleRepository.findByName("USER_ROLE");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        User user = userConverter.convertFromDto(dto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(userRoles);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);
    }

    @Transactional
    public void updateUser(UserDto dto, Long id) {
        User entity = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        entity.setLogin(dto.getLogin());
        entity.setFullName(dto.getFullName());
        userRepository.save(entity);
    }
}
