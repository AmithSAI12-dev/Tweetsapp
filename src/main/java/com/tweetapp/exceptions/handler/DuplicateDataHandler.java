package com.tweetapp.exceptions.handler;

import com.mongodb.MongoWriteException;
import com.tweetapp.dto.ExceptionFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class DuplicateDataHandler {

    @ExceptionHandler(value = {MongoWriteException.class})
    public ResponseEntity<ExceptionFormat> handleException(Exception exception) {
        return new ResponseEntity<>(new ExceptionFormat("User Already Exists", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }
}
