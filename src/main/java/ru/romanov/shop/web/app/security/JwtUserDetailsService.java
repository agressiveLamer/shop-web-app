package ru.romanov.shop.web.app.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.romanov.shop.web.app.entity.User;
import ru.romanov.shop.web.app.repository.UserRepository;
import ru.romanov.shop.web.app.security.jwt.JwtUserFactory;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);
        if (user == null){
            throw new UsernameNotFoundException("User with login: " + login + " no found");
        }
        return JwtUserFactory.create(user);
    }
}
