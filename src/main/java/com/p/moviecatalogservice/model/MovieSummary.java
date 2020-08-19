package com.p.moviecatalogservice.model;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MovieSummary {

    private String id;
    private String title;
    private  String overview;


}
