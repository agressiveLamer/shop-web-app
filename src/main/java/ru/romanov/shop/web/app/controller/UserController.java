package ru.romanov.shop.web.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.shop.web.app.dto.UserDto;
import ru.romanov.shop.web.app.service.UserService;

@Api("Контроллер для работы с пользователями")
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Получение всех пользователей")
    @GetMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserDto getUsers(@RequestParam (value = "id") Long id) {
        return userService.getUsers(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Создание/редактирование пользователей")
    @PostMapping(value = "/save-user", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUser(@RequestBody UserDto dto){
        userService.saveUser(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Удаление пользователей")
    @DeleteMapping(value = "/delete-user")
    public void deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
    }
}
