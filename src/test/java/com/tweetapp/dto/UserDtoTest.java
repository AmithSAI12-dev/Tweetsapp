package com.tweetapp.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setEmail("Mock Email");
        userDto.setMessage("Mock Message");
        userDto.setName("Mock Name");
        userDto.setPassword("Mock Password");
    }

    @Test
    void testGetterAndSetter() {
        assertEquals("Mock Email", userDto.getEmail());
        assertEquals("Mock Message", userDto.getMessage());
        assertEquals("Mock Name", userDto.getName());
        assertEquals("Mock Password", userDto.getPassword());
    }
}