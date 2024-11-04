package com.aluracursos.playhunt.service;

import com.aluracursos.playhunt.model.DataTranslate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiTranslate {

    private DataConvert dataConvert;

    public ApiTranslate(){
        dataConvert = new DataConvert();
    }

    public static String translate(String text) throws IOException, InterruptedException {
        DataConvert dataConvert = new DataConvert();
        String plainText = text.replaceAll("<[^>]*>", "");
        plainText = plainText.replaceAll("[^a-zA-Z0-9]", "");

        String jsonBody = String.format("{\"q\":\"%s\",\"source\":\"en\",\"target\":\"es\"}", plainText);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://deep-translate1.p.rapidapi.com/language/translate/v2"))
                .header("x-rapidapi-key", "de1d84bd43msha4ffcee32a81131p14ff80jsne4738fd14f2d")
                .header("x-rapidapi-host", "deep-translate1.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        DataTranslate datos = dataConvert.convert(response.body(), DataTranslate.class);
        return datos.translations().translatedText().text();
    }
}