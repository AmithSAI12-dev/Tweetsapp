package com.tweetapp.service;

import com.tweetapp.controller.TweetsController;
import com.tweetapp.controller.UserController;
import com.tweetapp.dto.UserDto;
import com.tweetapp.exceptions.NoDataAvailableException;
import com.tweetapp.model.Users;
import com.tweetapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto registerUser(Users users) {
        log.info("Attempting to create new user, {}", UserService.class.toString());
        Users register = userRepository.insert(users);
        log.info("Successfully created user, {}", UserService.class.toString());
        return new UserDto(register, "Successfully Registered");
    }

    @Transactional
    public UserDto login(Users users) throws IllegalArgumentException {
        log.info("Attempting to login, {}", UserService.class.toString());
        Users loggedIn = userRepository.findByIdAndPassword(users.getEmail(), users.getPassword())
                .stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid Username/Password"));
        log.info("Successfully logged in, {}", UserService.class.toString());
        return new UserDto(loggedIn, "Successfully Logged In");
    }

    @Transactional
    public void changePassword(String username, String password) throws IllegalArgumentException {
        log.info("Attempting to change password, {}", UserService.class.toString());
        Users users = userRepository.findById(username).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Email Does Not Exists"));
        users.setPassword(password);
        log.info("Successfully changed password , {}", UserService.class.toString());
        userRepository.save(users);
    }

    @Transactional
    public List<Users> retrieveAll(int page, int size) throws NoDataAvailableException {
        log.info("Attempting to get all users, {}", UserService.class.toString());
        List<Users> users = userRepository.findAll(PageRequest.of(page, size)).getContent();
        if(users.isEmpty()) {
            throw new NoDataAvailableException("No Data Available in database");
        }
        log.info("Successfully retrieved all user, {}", UserService.class.toString());
        return users;
    }
}
