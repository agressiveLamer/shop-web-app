package ru.romanov.shop.web.app.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String login;
    private String password;
    private String fullName;

}
