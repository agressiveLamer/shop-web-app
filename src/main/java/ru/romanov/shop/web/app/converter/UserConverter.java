package ru.romanov.shop.web.app.converter;

import org.springframework.stereotype.Component;
import ru.romanov.shop.web.app.dto.UserDto;
import ru.romanov.shop.web.app.entity.User;

@Component
public class UserConverter {

    public UserDto convertFromEntity(User entity){
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        return dto;
    }


    public User convertFromDto(UserDto dto){
        User entity = new User();
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
