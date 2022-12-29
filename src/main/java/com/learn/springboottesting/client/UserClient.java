package com.learn.springboottesting.client;

import com.learn.springboottesting.domain.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate;

    public UserClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.rootUri("https://reqres.in").build();
    }

    public User getUserDetails(Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return this.restTemplate
                .exchange("/api/users/{id}", HttpMethod.GET, requestEntity, User.class, id)
                .getBody();
    }
}
