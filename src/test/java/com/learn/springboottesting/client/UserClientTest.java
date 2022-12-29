package com.learn.springboottesting.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(UserClient.class)
public class UserClientTest {

    @Autowired
    UserClient userClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Test
    public void givenUserId_whenGetUserDetails_thenReturnUser(){

    }

    @Test
    public void givenUserId_whenGetUserDetails_thenReturnEmptyUser(){

    }

}
