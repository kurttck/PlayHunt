package com.aluracursos.playhunt.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataTranslate(
        @JsonAlias("data") Translations translations
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Translations (
            @JsonAlias("translations") TranslatedText translatedText
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record TranslatedText(
                @JsonAlias("translatedText") String text
        ) { }
    }
}
