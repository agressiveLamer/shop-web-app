package ru.romanov.shop.web.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.romanov.shop.web.app.converter.UserConverter;
import ru.romanov.shop.web.app.dto.UserDto;
import ru.romanov.shop.web.app.entity.User;
import ru.romanov.shop.web.app.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

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
        User userFromDb = userRepository.findUserByLogin(dto.getLogin());
        if (userFromDb != null) {

        }
    }

    @Transactional
    public void updateUser(UserDto dto, Long id) {
        User entity = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        entity.setLogin(dto.getLogin());
        entity.setFullName(dto.getFullName());
        userRepository.save(entity);
    }
}
