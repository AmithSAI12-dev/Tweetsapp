package com.tweetapp.controller;

import com.tweetapp.dto.UserDto;
import com.tweetapp.exceptions.NoDataAvailableException;
import com.tweetapp.model.Users;
import com.tweetapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Register New User",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "User Already Exists",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<UserDto> registerUser(@Parameter(description = "Users Pojo Object") @Valid @RequestBody Users users) {
        log.info("Attempting to create new user, {}", UserController.class.toString());
        UserDto userDto = userService.registerUser(users);
        log.info("Successfully created user, {}", TweetsController.class.toString());
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Login User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Login User",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid Username/Password",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<UserDto> login(@Parameter(description = "Users Pojo Object") @Valid @RequestBody Users users, HttpSession session) {
        log.info("Attempting to login, {}", UserController.class.toString());
        UserDto login = userService.login(users);
        session.setAttribute("user", login.getEmail());
        log.info("Successfully logged in, {}", UserController.class.toString());
        log.info("Session :{}, class: {}", session.getAttribute("user"), UserController.class.toString());
        return new ResponseEntity<>(login, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{username}/forgot")
    @Operation(summary = "Forgot Password")@ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Login User",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "User DOes Not Exists",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<String> forgotPassword(@Parameter(description = "Username") @PathVariable String username,@Parameter(description = "password")  @RequestParam String password) {
        log.info("Attempting to change password, {}", UserController.class.toString());
        userService.changePassword(username, password);
        log.info("Successfully changed password , {}", UserController.class.toString());
        return new ResponseEntity<>("Successfully Updated Password", HttpStatus.ACCEPTED);
    }

    @GetMapping("/users/all")
    @Operation(summary = "Fetch all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Login User",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No Data Available",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<List<Users>> getAllUsers(@Parameter(description = "Page number") @RequestParam("page") int page,@Parameter(description = "No of items per page") @RequestParam("size") int size) throws NoDataAvailableException {
        log.info("Attempting to get all users, {}", UserController.class.toString());
        List<Users> users = userService.retrieveAll(page, size);
        log.info("Successfully retrieved all user, {}", UserController.class.toString());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/users/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.removeAttribute("user");
        return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
    }
}
