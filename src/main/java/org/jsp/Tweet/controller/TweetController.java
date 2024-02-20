package org.jsp.Tweet.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.jsp.Tweet.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import twitter4j.TwitterException;

@Controller
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/tweet")
    public void postTweet(@RequestBody String tweetText) throws TwitterException, IOException, InvalidKeyException, NoSuchAlgorithmException{
        tweetService.postTweet(tweetText);
    }
}

