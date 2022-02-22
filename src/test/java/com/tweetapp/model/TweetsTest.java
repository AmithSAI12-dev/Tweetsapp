package com.tweetapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TweetsTest {

    private Tweets tweets;

    @BeforeEach
    void setUp() {
        tweets = new Tweets();
        tweets.setId("Mock ID");
        tweets.setTags("Mock Tags");
        tweets.setMessage("Mock Message");
        tweets.setEmail("Mock Email");
    }

    @Test
    void testGetterAndSetter() {
        assertEquals("Mock ID", tweets.getId());
        assertEquals("Mock Tags", tweets.getTags());
        assertEquals("Mock Message", tweets.getMessage());
        assertEquals("Mock Email", tweets.getEmail());
    }
}