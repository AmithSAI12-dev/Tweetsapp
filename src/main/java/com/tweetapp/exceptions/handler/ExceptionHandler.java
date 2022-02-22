package com.tweetapp.exceptions.handler;

import com.tweetapp.dto.ExceptionFormat;
import com.tweetapp.exceptions.NoDataAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NoDataAvailableException.class, IllegalArgumentException.class})
    public ResponseEntity<ExceptionFormat> handleException(Exception exception) {
        ExceptionFormat exceptionFormat = new ExceptionFormat(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exceptionFormat, HttpStatus.BAD_REQUEST);
    }
}
