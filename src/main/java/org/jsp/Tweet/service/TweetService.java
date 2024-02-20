package org.jsp.Tweet.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import twitter4j.HttpResponseCode;
import twitter4j.JSONObject;
import twitter4j.TwitterException;	

@Service
public class TweetService {

	@Value("${twitter.bearerToken}")
	private String bearerToken;

	public void postTweet(String tweetText) throws TwitterException, IOException {

		String url = "https://api.twitter.com/2/tweets";

		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			URL apiUrl = new URL(url);
			connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			String postData = "{\"text\":\"" + tweetText + "\"}";
			connection.getOutputStream().write(postData.getBytes("UTF-8"));

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpResponseCode.OK) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String response = reader.lines().collect(Collectors.joining("\n"));
				JSONObject jsonResponse = new JSONObject(response);
				System.out.println("Tweet posted successfully: " + jsonResponse);
			} else {
				throw new TwitterException("Failed to post tweet: " + responseCode);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}

//@Service
//public class TweetService {
//
//    @Value("${twitter.api.bearer-token}")
//    private String bearerToken;
//
//    private final RestTemplate restTemplate;
//
//    public TweetService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public void postTweet(String tweetContent) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + bearerToken);
//
//        String url = "https://api.twitter.com/2/tweets";
//        String requestBody = "{\"text\": \"" + tweetContent + "\"}";
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
//
//        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
//            System.out.println("Tweet posted successfully!");
//        } else {
//            System.out.println("Failed to post tweet. Error: " + responseEntity.getBody());
//        }
//    }
//}