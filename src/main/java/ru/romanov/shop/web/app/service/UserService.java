package ru.romanov.shop.web.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanov.shop.web.app.converter.UserConverter;
import ru.romanov.shop.web.app.dto.UserDto;
import ru.romanov.shop.web.app.entity.Role;
import ru.romanov.shop.web.app.entity.User;
import ru.romanov.shop.web.app.entity.repository.RoleRepository;
import ru.romanov.shop.web.app.entity.repository.UserRepository;
import ru.romanov.shop.web.app.exception.ResourceNotFoundException;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public UserDto getUsers(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return userConverter.convertFromEntity(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void saveUser(UserDto dto){
        if (dto.getId() == null){
            Role userRole = roleRepository.findByName("USER_ROLE");
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            User user = userConverter.convertFromDto(dto);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole(userRoles);

            User registeredUser = userRepository.save(user);

            log.info("IN register - user: {} successfully registered", registeredUser);
        }else {
            User entity = userRepository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
            entity.setLogin(dto.getLogin());
            entity.setFullName(dto.getFullName());
            userRepository.save(entity);
        }

    }

   /* @Transactional
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
    }*/
}
