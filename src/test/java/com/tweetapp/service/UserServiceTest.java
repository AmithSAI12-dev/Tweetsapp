package com.tweetapp.service;

import com.tweetapp.dto.UserDto;
import com.tweetapp.exceptions.NoDataAvailableException;
import com.tweetapp.model.Users;
import com.tweetapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private Page<Users> usersPage;
    @InjectMocks
    private UserService userService;
    private Users users;

    @BeforeEach()
    void setUp() {
        users = new Users();
        users.setEmail("Mock Email");
        users.setPassword("Mock password");
        users.setName("Mock Name");
    }

    @Test
    void testRegisterUser() {
        when(userRepository.insert(any(Users.class))).thenReturn(users);
        UserDto userDto = userService.registerUser(users);
        verify(userRepository, atMostOnce()).insert(users);
        assertEquals(userDto.getEmail(), users.getEmail());
    }

    @Test
    void testLogin() {
        when(userRepository.findByIdAndPassword(anyString(), anyString())).thenReturn(Optional.of(users));
        UserDto login = userService.login(users);
        verify(userRepository, atMostOnce()).findByIdAndPassword(users.getEmail(), users.getPassword());
        assertEquals(login.getEmail(), users.getEmail());
    }

    @Test
    void testLogin_throwsException() {
        when(userRepository.findByIdAndPassword(anyString(), anyString())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.login(users));
        verify(userRepository, atMostOnce()).findByIdAndPassword(users.getEmail(), users.getPassword());
    }

    @Test
    void testChangePassword() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(users));
        when(userRepository.save(any(Users.class))).thenReturn(users);
        userService.changePassword("Mock Username", "Mock Password");
        verify(userRepository, atMostOnce()).findById("Mock user");
        verify(userRepository, atMostOnce()).save(users);
    }

    @Test
    void testChangePassword_throwsException() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.changePassword(users.getEmail(), users.getPassword()));
        verify(userRepository, atMostOnce()).findById(users.getEmail());
    }

    @Test
    void testRetrieveAll() throws NoDataAvailableException {
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(usersPage);
        when(usersPage.getContent()).thenReturn(Collections.singletonList(users));
        List<Users> usersList = userService.retrieveAll(0, 10);
        assertEquals(usersList.get(0).getEmail(), users.getEmail());
        verify(userRepository, atMostOnce()).findAll(PageRequest.of(0, 10));
        verify(usersPage, atMostOnce()).getContent();
    }

    @Test
    void testRetrieveAll_throwsException() {
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());
        assertThrows(NoDataAvailableException.class, () -> userService.retrieveAll(0, 10));
        verify(userRepository, atMostOnce()).findAll(PageRequest.of(0,10));
    }



}