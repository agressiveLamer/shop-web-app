package ru.romanov.shop.web.app.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.romanov.shop.web.app.entity.Role;
import ru.romanov.shop.web.app.exception.error.AppError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    protected static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Integer VALID_TIME_OUT = 3600000;

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String createToken(String username, Set<Role> role) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(role));

        Date now = new Date();
        Date validity = new Date(now.getTime() + VALID_TIME_OUT);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //TODO: Исключить дублирование кода

    public String resolveToken(HttpServletRequest request, HttpServletResponse response) throws IOException, AccessDeniedException {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer")) {
                return bearerToken.substring(7);
            }
                AppError appError = new AppError(HttpStatus.UNAUTHORIZED.value(), "Access denied. Token not found or authorization type is not correct");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                OutputStream outputStream = response.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(outputStream, appError);
                outputStream.flush();
                throw new AccessDeniedException("Access denied. Token not found or authorization type is not correct");
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //TODO: Расширить список exception, сделать универсальный механизм возврата ошибок

    public boolean isValidateToken(String token, HttpServletResponse response) throws IOException {
        try {
                Jws<Claims> claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET)
                        .build()
                        .parseClaimsJws(token);
                return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e){

            AppError appError = new AppError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            OutputStream outputStream = response.getOutputStream();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream, appError);

            outputStream.flush();

            throw new JwtAuthException("JWT token is expired or invalid");
        }

    }

    private Set<String> getRoleNames(Set<Role> userRoles) {
        Set<String> result = new HashSet<>();

        userRoles.forEach(role -> result.add(role.getName()));
        return result;

    }

}
