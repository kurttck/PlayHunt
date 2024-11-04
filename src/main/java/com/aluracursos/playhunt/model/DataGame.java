package com.aluracursos.playhunt.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataGame(
        @JsonAlias("name") String name,
        @JsonAlias("description") String description,
        @JsonAlias("released") String releaseDate,
        @JsonAlias("background_image") String poster,
        @JsonAlias("rating") Double rating,
        @JsonAlias("genres") List<Genre> genres,
        @JsonAlias("parent_platforms") List<Platforms> platforms
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Genre(
            @JsonAlias("name") String name){
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Platforms(
            @JsonAlias("platform") Platform platform){


        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Platform(
                @JsonAlias("name") String name){
        }

    }
}
