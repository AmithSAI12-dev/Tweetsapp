package com.tweetapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.dto.UserDto;
import com.tweetapp.model.Tweets;
import com.tweetapp.model.Users;
import com.tweetapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private UserService userService;

    @Test
    void testRegisterUser() throws Exception {
        when(userService.registerUser(any(Users.class))).thenReturn(new UserDto());
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Tweets()))).andExpect(status().isCreated());
    }

    @Test
    void testLoginUser() throws Exception {
        when(userService.login(any(Users.class))).thenReturn(new UserDto());
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Tweets())))
                .andExpect(status().isAccepted());
    }

    @Test
    void testForgotPassword() throws Exception {
        doNothing().when(userService).changePassword(anyString(), anyString());
        mockMvc.perform(get("/{username}/forgot", "username")
                .param("password", "password")
        ).andExpect(status().isAccepted());
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.retrieveAll(0, 10)).thenReturn(Collections.singletonList(new Users()));
        mockMvc.perform(get("/users/all").param("page", "0").param("size", "10"))
                .andExpect(status().isOk());
    }
}