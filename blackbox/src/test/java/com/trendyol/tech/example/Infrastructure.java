package com.trendyol.tech.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class Infrastructure {

    private String _apiKey;

    public Infrastructure(String apiKey) {
        _apiKey = apiKey;
    }

    public Response PostRequest(String parameter, String value) {

        return given()
                .queryParam(parameter, value)
                .queryParam("apikey", _apiKey)
                .when()
                .contentType(ContentType.JSON)
                .post();
    }
}
