package com.tweetapp.model;

import com.tweetapp.validation.PasswordValidation;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@Document
public class Users {

    @Id
    private String email;
    private String name;
    @NotEmpty
    @PasswordValidation
    private String password;
}
