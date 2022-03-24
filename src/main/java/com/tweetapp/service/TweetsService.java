package com.tweetapp.service;

import com.tweetapp.controller.TweetsController;
import com.tweetapp.dto.TweetsDto;
import com.tweetapp.exceptions.NoDataAvailableException;
import com.tweetapp.model.Tweets;
import com.tweetapp.repository.TweetsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TweetsService {

    private final TweetsRepository tweetsRepository;

    @Autowired
    public TweetsService(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }

    public TweetsDto saveTweet(Tweets tweets) {
        log.info("Attempting to create new tweet, {}", TweetsService.class.toString());
        Tweets savedTweet = tweetsRepository.insert(tweets);
        log.info("Successfully created new tweet, {}", TweetsService.class.toString());
        return new TweetsDto(tweets, "Successfully Saved");
    }

    public TweetsDto updateTweet(Tweets tweets) throws NoDataAvailableException {
        log.info("Attempting to Update Tweet, {}", TweetsService.class.toString());
        tweetsRepository.findById(tweets.getId()).stream().findFirst().orElseThrow(() -> new NoDataAvailableException("Tweet Does not exists"));
        tweetsRepository.deleteByIdAndEmail(tweets.getEmail(), tweets.getId());
        Tweets updatedTweet = tweetsRepository.insert(tweets);
        log.info("Successfully Updated Tweet, {}", TweetsService.class.toString());
        return new TweetsDto(tweets, "Successfully Updated Tweet");
    }

    public List<Tweets> getAllTweets(int page, int size) throws NoDataAvailableException {
        log.info("Attempting to fetch all tweets, {}", TweetsService.class.toString());
        List<Tweets> tweets = tweetsRepository.findAll(PageRequest.of(page, size)).getContent();
        if(tweets.isEmpty()) {
            log.info("No tweets found, {}", TweetsService.class.toString());
            throw new NoDataAvailableException("No Data Available");
        }
        log.info("Successfully retrieved all tweets, {}", TweetsService.class.toString());
        return tweets;
    }

    public List<Tweets> getTweetsByUser(String email) throws NoDataAvailableException {
        log.info("Attempting to fetch all tweets by user, {}", TweetsService.class.toString());
        List<Tweets> tweets = tweetsRepository.findByEmail(email);
        if (tweets.isEmpty()) {
            log.info("No tweets available, {}", TweetsService.class.toString());
            throw new NoDataAvailableException("No Tweets Available for that user");
        }
        log.info("Successfully retrieved tweets, {}", TweetsService.class.toString());
        return tweets;
    }

    public void deleteTweet(String email,String id) {
        tweetsRepository.deleteByIdAndEmail(email, id);
    }



}
