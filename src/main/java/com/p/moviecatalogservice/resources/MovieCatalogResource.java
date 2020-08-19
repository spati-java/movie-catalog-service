package com.p.moviecatalogservice.resources;


import com.p.moviecatalogservice.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/catalog")
@Slf4j
public class MovieCatalogResource {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/test")
    public String test(){
        return "Catalog Service Working !";
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId")  String userId) {

        UserRating userRating = restTemplate.getForObject( "http://movie-rating-service:8083/ratings/" + "movieId", UserRating.class);

        System.out.println(userRating.getRatings());

        return userRating.getRatings().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service:8082/movies/"+rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), movie.getDescription(),rating.getRating());
        }).collect(Collectors.toList());


    }
}
