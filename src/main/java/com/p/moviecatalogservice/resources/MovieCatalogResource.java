package com.p.moviecatalogservice.resources;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.p.moviecatalogservice.model.*;
import com.p.moviecatalogservice.services.MovieInfo;
import com.p.moviecatalogservice.services.UserRatingInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/catalog")
@Slf4j
@RequiredArgsConstructor
public class MovieCatalogResource {

    private final MovieInfo movieInfo;

    private final UserRatingInfo userRatingInfo;

    @GetMapping("/test")
    public String test() {
        return "Catalog Service Working !";
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        return userRatingInfo.getUserRating(userId).getRatings()
                .stream()
                .map(rating -> movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());

    }


}
