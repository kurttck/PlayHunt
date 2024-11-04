package com.aluracursos.playhunt.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataPlatform(
    @JsonAlias("results") List<Results> results
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Results(
        @JsonAlias("name") String name,
        @JsonAlias("image_background") String imageBackground
    ){}
}
