package ru.romanov.shop.web.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.romanov.shop.web.app.dto.UserDto;
import ru.romanov.shop.web.app.service.UserService;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping(value = "/create-user")
    public void createUser(@RequestBody UserDto dto) {
        userService.createUser(dto);
    }

    @PostMapping(value = "/update-user")
    public void updateUser(@RequestBody UserDto dto,
                           @RequestParam(name = "id") Long id) {
        userService.updateUser(dto, id);
    }

    @DeleteMapping(value = "/delete-user")
    public void deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
    }
}
