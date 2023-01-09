package ru.romanov.shop.web.app.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.romanov.shop.web.app.exception.error.AppError;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    //TODO: Вернуть проверку на валдность обратно в провайдер

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException, JwtAuthException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && !token.isEmpty()){
            try {
                jwtTokenProvider.isValidateToken(token);
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
                filterChain.doFilter(request, response);
        }



    }
}