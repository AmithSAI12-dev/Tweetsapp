package com.tweetapp.dto;

import com.tweetapp.model.Tweets;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetsDto {

    private String message;
    private String tags;
    private String email;
    private String successMessage;

    public TweetsDto(Tweets tweets, String successMessage) {
        this.message = tweets.getMessage();
        this.tags = tweets.getTags();
        this.email = tweets.getEmail();
        this.successMessage = successMessage;
    }
}
