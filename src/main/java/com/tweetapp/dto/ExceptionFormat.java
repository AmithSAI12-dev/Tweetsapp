package com.tweetapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionFormat {

    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime localDateTime;
}
