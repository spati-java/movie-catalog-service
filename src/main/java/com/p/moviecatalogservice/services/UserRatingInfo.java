package com.p.moviecatalogservice.services;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.p.moviecatalogservice.model.Rating;
import com.p.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackUserRating")
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject( "http://movie-rating-service:8083/ratings/" + userId, UserRating.class);
    }

    public UserRating getFallBackUserRating(String userId) {
        return new UserRating(Collections.emptyList());
    }

}
