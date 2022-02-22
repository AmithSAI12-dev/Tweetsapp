package com.tweetapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    private Users users;

    @BeforeEach
    void setUp() {
        users = new Users();
        users.setPassword("Mock Password");
        users.setName("Mock Name");
        users.setEmail("Mock Email");
    }

    @Test
    void testGetterAndSetter() {
        assertEquals("Mock Password", users.getPassword());
        assertEquals("Mock Name", users.getName());
        assertEquals("Mock Email", users.getEmail());
    }
}