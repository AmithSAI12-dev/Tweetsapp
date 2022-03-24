package com.tweetapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.dto.TweetsDto;
import com.tweetapp.model.Tweets;
import com.tweetapp.service.TweetsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = TweetsController.class)
class TweetsControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private TweetsService tweetsService;


    @Test
    void testGetAllTweets() throws Exception {
        when(tweetsService.getAllTweets(anyInt(), anyInt())).thenReturn(Collections.singletonList(new Tweets()));
        mockMvc.perform(get("/all")
                .param("size", "10")
                .param("page", "0")).andExpect(status().isOk());
    }

    @Test
    void testGetAllTweets_ErrorStatus() throws Exception {
        when(tweetsService.getAllTweets(anyInt(), anyInt())).thenReturn(Collections.singletonList(new Tweets()));
        mockMvc.perform(get("/all")
                .param("page", "0")).andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllTweetsByUser() throws Exception {
        when(tweetsService.getTweetsByUser(anyString())).thenReturn(Collections.singletonList(new Tweets()));
        mockMvc.perform(get("/username")
//                .sessionAttr("user", "user")
                .param("email", "mock Email")).andExpect(status().isOk());
    }

    @Test
    void testGetAllTweetsByUser_errorStatus() throws Exception {
        when(tweetsService.getTweetsByUser(anyString())).thenReturn(Collections.singletonList(new Tweets()));
        mockMvc.perform(get("/username")).andExpect(status().isBadRequest());
    }

    @Test
    void testPostTweet() throws Exception {
        when(tweetsService.saveTweet(any(Tweets.class))).thenReturn(new TweetsDto());
        mockMvc.perform(post("/add")
//                .sessionAttr("user", "user")
                        .param("username", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new Tweets())))
                .andExpect(status().isCreated());
    }

    @Test
    void testPostTweet_errorStatus() throws Exception {
        when(tweetsService.saveTweet(any(Tweets.class))).thenReturn(new TweetsDto());
        mockMvc.perform(post("/add")
                        .param("username", "test")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(new Tweets())))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateTweet() throws Exception{
        when(tweetsService.updateTweet(any(Tweets.class))).thenReturn(new TweetsDto());
        mockMvc.perform(put("/update" )
//                        .sessionAttr("user", "user")
                        .param("username", "user")
                        .param("tweetId", "id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Tweets())))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateTweet_errorStatus() throws Exception{
        when(tweetsService.updateTweet(any(Tweets.class))).thenReturn(new TweetsDto());
        mockMvc.perform(put("/update")
                        .param("username", "user")
                        .param("tweetId", "id")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(new Tweets())))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteTweet() throws Exception {
        doNothing().when(tweetsService).deleteTweet(anyString(), anyString());
        mockMvc.perform(delete("/delete")
                        .param("username", "user")
                        .param("tweetId", "id")
//                .sessionAttr("user", "user")
                ).andExpect(status().isOk());
    }
//
//    @Test
//    void testDeleteTweet_errorStatus() throws Exception {
//        doNothing().when(tweetsService).deleteTweet(anyString(), anyString());
//        mockMvc.perform(delete("/{username}/delete/{tweetId}", "user", "tweetId")
//        ).andExpect(status().isBadRequest());
//    }

}