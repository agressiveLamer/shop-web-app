package ru.romanov.shop.web.app.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.shop.web.app.dto.AuthenticationRequestDto;
import ru.romanov.shop.web.app.service.AuthService;

import java.util.Map;

@Api("Контроллер для работы с авторизацией")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Object, Object> login(@RequestBody AuthenticationRequestDto requestDto) {
        return authService.doLogin(requestDto);
    }
}
