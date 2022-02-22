package com.tweetapp.service;

import com.tweetapp.dto.TweetsDto;
import com.tweetapp.exceptions.NoDataAvailableException;
import com.tweetapp.model.Tweets;
import com.tweetapp.repository.TweetsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetsServiceTest {

    @Mock
    private TweetsRepository tweetsRepository;
    @InjectMocks
    private TweetsService tweetsService;
    private Tweets tweets;
    @Mock
    private Page<Tweets> tweetsPage;

    @BeforeEach
    void setUp() {
        tweets = new Tweets();
        tweets.setId("Mock ID");
        tweets.setEmail("Mock Email");
        tweets.setMessage("Mock Message");
        tweets.setTags("Mock tag");
    }


    @Test
    void testSaveTweet() {
        when(tweetsRepository.insert(any(Tweets.class))).thenReturn(tweets);
        TweetsDto tweetsDto = tweetsService.saveTweet(tweets);
        verify(tweetsRepository, atMostOnce()).save(tweets);
        assertEquals(tweetsDto.getEmail(), tweets.getEmail());
    }

    @Test
    void testUpdateTweet() throws NoDataAvailableException {
        when(tweetsRepository.findById(anyString())).thenReturn(Optional.of(tweets));
        when(tweetsRepository.save(any(Tweets.class))).thenReturn(tweets);
        TweetsDto tweetsDto = tweetsService.updateTweet(tweets);
        assertEquals(tweetsDto.getEmail(), tweets.getEmail());
        verify(tweetsRepository, atMostOnce()).findById(tweets.getId());
        verify(tweetsRepository, atMostOnce()).save(tweets);
    }

    @Test
    void testUpdateTweet_throwsException() {
        when(tweetsRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(NoDataAvailableException.class, () -> tweetsService.updateTweet(tweets));
        verify(tweetsRepository, atMostOnce()).findById(tweets.getId());
    }

    @Test
    void testGetAllTweets() throws NoDataAvailableException {
        when(tweetsRepository.findAll(any(Pageable.class))).thenReturn(tweetsPage);
        when(tweetsPage.getContent()).thenReturn(Collections.singletonList(tweets));
        List<Tweets> allTweets = tweetsService.getAllTweets(0, 10);
        verify(tweetsRepository, atMostOnce()).findAll(PageRequest.of(0,10));
        verify(tweetsPage, atMostOnce()).getContent();
        assertEquals(allTweets.get(0).getEmail(), tweets.getEmail());
    }

    @Test
    void testGetAllTweets_throwsException() {
        when(tweetsRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        assertThrows(NoDataAvailableException.class, () -> tweetsService.getAllTweets(0, 10));
        verify(tweetsRepository, atMostOnce()).findAll(PageRequest.of(0,10));
    }

    @Test
    void testGetTweetByUser() throws NoDataAvailableException {
        when(tweetsRepository.findByEmail(anyString())).thenReturn(Collections.singletonList(tweets));
        List<Tweets> mock_user = tweetsService.getTweetsByUser("Mock User");
        verify(tweetsRepository, atMostOnce()).findById("Mock User");
        assertEquals(mock_user.get(0).getEmail(), tweets.getEmail());
    }

    @Test
    void testGetTweetsByUser_throwsException() {
        when(tweetsRepository.findByEmail(anyString())).thenReturn(Collections.emptyList());
        assertThrows(NoDataAvailableException.class, () -> tweetsService.getTweetsByUser("Mock user"));
        verify(tweetsRepository, atMostOnce()).findById("Mock user");
    }

    @Test
    void testDeleteTweet() {
        doNothing().when(tweetsRepository).deleteByIdAndEmail(anyString(), anyString());
        tweetsService.deleteTweet("Mock Email", "Mock ID");
        verify(tweetsRepository, atMostOnce()).deleteByIdAndEmail("Mock Email", "Mock ID");

    }






}