package ru.romanov.shop.web.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.romanov.shop.web.app.dto.AuthenticationRequestDto;
import ru.romanov.shop.web.app.entity.User;
import ru.romanov.shop.web.app.repository.UserRepository;
import ru.romanov.shop.web.app.security.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public Map<Object, Object> doLogin(AuthenticationRequestDto dto) {
        try {
            String username = dto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, dto.getPassword()));
            User user = userRepository.findUserByLogin(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRole());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return response;

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
