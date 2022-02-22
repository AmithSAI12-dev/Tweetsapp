package com.tweetapp.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class ExceptionFormatTest {

    private ExceptionFormat exceptionFormat;

    @BeforeEach
    void setUp() {
        exceptionFormat = new ExceptionFormat("Mock Message", HttpStatus.OK, LocalDateTime.now());
    }

    @Test
    void testGetterAndSetter() {
        assertEquals("Mock Message", exceptionFormat.getMessage());
        assertEquals(HttpStatus.OK, exceptionFormat.getHttpStatus());
        assertNotNull(exceptionFormat.getLocalDateTime());
    }
}