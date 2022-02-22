package com.tweetapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Users {

    @Id
    private String email;
    private String name;
    private String password;
}
