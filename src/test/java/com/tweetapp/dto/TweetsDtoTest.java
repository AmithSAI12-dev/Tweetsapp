package com.tweetapp.dto;

import com.tweetapp.model.Tweets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TweetsDtoTest {

    private TweetsDto tweetsDto;

    @BeforeEach
    void setUp() {
        Tweets tweets = new Tweets();
        tweets.setEmail("mock email");
        tweets.setTags("mock tag");
        tweets.setMessage("mock message");
        tweets.setId("mock id");
        tweetsDto = new TweetsDto(tweets, "mock message");

    }

    @Test
    void testGetterAndSetter() {
        assertEquals("mock email", tweetsDto.getEmail());
        assertEquals("mock tag", tweetsDto.getTags());
        assertEquals("mock message", tweetsDto.getMessage());
        assertEquals("mock message", tweetsDto.getSuccessMessage());
    }
}