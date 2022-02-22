package com.tweetapp.dto;

import com.tweetapp.model.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String message;

    public UserDto(Users users, String message) {
        this.name = users.getName();
        this.password = users.getPassword();
        this.email = users.getEmail();
        this.message = message;
    }
}
