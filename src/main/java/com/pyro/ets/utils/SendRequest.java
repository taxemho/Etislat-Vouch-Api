package com.pyro.ets.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SendRequest {

    private static final Logger logger = LoggerFactory.getLogger(SendRequest.class);

    @Autowired
    private RestTemplate restTemplate;

    public String hitRequest(String url, String contimeout, String readTimeout, String data) {
        logger.info("URL: {}", url);
        logger.info("Request data: {}", data);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/xml");

        HttpEntity<String> entity = new HttpEntity<>(data, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            logger.info("Response Code: {}", response.getStatusCodeValue());
            logger.info("Response Body: {}", response.getBody());
            return response.getBody();
        } catch (Exception e) {
            logger.error("Exception in hitRequest", e);
            return "{\"responseCode\":\"500\",\"status\":\"FAILURE\",\"responseMessage\":\"" + e.getMessage() + "\"}";
        }
    }

    public String sendGET(String url) {
        logger.info("sendGET URL: {}", url);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            logger.info("GET Response Code: {}", response.getStatusCodeValue());
            return response.getBody();
        } catch (Exception e) {
            logger.error("Exception in sendGET", e);
            return "{\"ErrorCode\":\"5002\",\"status\":\"FAILED\",\"ErrorMessage\":\"Internal Server Error\"}";
        }
    }
}
