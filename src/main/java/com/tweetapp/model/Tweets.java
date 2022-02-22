package com.tweetapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Tweets {

    @Id
    private String id;
    private String message;
    private String tags;
    private String email;
}
