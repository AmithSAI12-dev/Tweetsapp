package com.tweetapp.controller;

import com.tweetapp.dto.TweetsDto;
import com.tweetapp.exceptions.NoDataAvailableException;
import com.tweetapp.model.Tweets;
import com.tweetapp.service.TweetsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://54.84.169.248:3000")
public class TweetsController {

    private final TweetsService tweetsService;

    @Autowired
    public TweetsController(TweetsService tweetsService) {
        this.tweetsService = tweetsService;
    }

    @GetMapping(value = "/all")
    @Operation(summary = "Fetch All Tweets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Fetch All Tweets",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
            description = "Not Available",
            content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<List<Tweets>> getAllTweets(@Parameter(description = "No of items per page") @RequestParam int size,
                                                     @Parameter( description = "Page Number") @RequestParam int page,
                                                     HttpSession session) throws NoDataAvailableException {
        log.info("Attempting to fetch all tweets, {}", TweetsController.class.toString());
        List<Tweets> tweets = tweetsService.getAllTweets(page, size);
        log.info("Successfully retrieved all tweets, {}", TweetsController.class.toString());
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @GetMapping(value = "/username")
    @Operation(summary = "Fetch All tweets that belong to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetch All Tweets that belong to a user",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Not Available",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<List<Tweets>> getAllTweetsByUser(@Parameter(description = "Email Id of the user") @RequestParam String email,
                                                           HttpSession session) throws NoDataAvailableException {
        log.info("Attempting to fetch all tweets by user, {}", TweetsController.class.toString());
        List<Tweets> tweetsByUser = tweetsService.getTweetsByUser(email);
        log.info("Successfully retrieved tweets, {}", TweetsController.class.toString());
        return new ResponseEntity<>(tweetsByUser, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    @Operation(summary = "Post new tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Post new tweet",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Tweet already exists",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<TweetsDto> postTweet(@Parameter(description = "Email Id of the user") @RequestParam("username") String email,
                                               @Parameter(description = "Tweets POJO object") @RequestBody Tweets tweets,
                                               HttpSession session) throws NoDataAvailableException {
        log.info("Attempting to create new tweet, {}", TweetsController.class.toString());
        TweetsDto tweetsDto = tweetsService.saveTweet(tweets);
        log.info("Successfully created new tweet, {}", TweetsController.class.toString());
        return new ResponseEntity<>(tweetsDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    @Operation(summary = "Update Tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Update Tweet",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Tweet does not exists",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<TweetsDto> updateTweet(@Parameter(description = "Email Id of the user") @RequestParam(value = "username", required = false) String username,
                                                 @Parameter(description = "Tweet ID") @RequestParam(value = "tweetId", required = false) String tweetId,
                                                 @Parameter(description = "Tweets Pojo Object") @RequestBody Tweets tweets,
                                                 HttpSession session) throws NoDataAvailableException {
        log.info("Attempting to Update Tweet, {}", TweetsController.class.toString());
        TweetsDto tweetsDto = tweetsService.updateTweet(tweets);
        log.info("Successfully Updated Tweet, {}", TweetsController.class.toString());
        return new ResponseEntity<>(tweetsDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Tweet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Delete Tweet",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Tweet does not exists",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<String> deleteTweet(@Parameter(description = "Email Id of the user") @RequestParam("username") String email,
                                              @Parameter(description = "Tweet ID") @RequestParam("tweetId") String tweetId,
                                              HttpSession session) throws NoDataAvailableException {
        log.info("Attempting to delete tweet, {}", TweetsController.class.toString());
        tweetsService.deleteTweet(email, tweetId);
        log.info("Successfully deleted tweet, {}", TweetsController.class.toString());
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
