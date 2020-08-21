package com.p.moviecatalogservice.services;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.p.moviecatalogservice.model.CatalogItem;
import com.p.moviecatalogservice.model.Movie;
import com.p.moviecatalogservice.model.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Service
public class MovieInfo {


    private  final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackCatalog")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service:8082/movies/"+rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(),rating.getRating());
    }

    public CatalogItem getFallBackCatalog(Rating rating){

        return new CatalogItem("No movie" , "" , 0);
    }
}
