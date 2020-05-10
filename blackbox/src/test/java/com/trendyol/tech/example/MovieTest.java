package com.trendyol.tech.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieTest {

    public static String imdbId;
    public static Infrastructure _infrastructure;

    @BeforeAll
    public static void setup() {
        RestAssured.basePath = "?s=Harry+Potter&apikey=e159ff3";
        RestAssured.baseURI = "http://www.omdbapi.com/";
        RestAssured.defaultParser = Parser.JSON;
        _infrastructure = new Infrastructure("e159ff3");
    }

    @Test
    @Order(1)
    void getMoviesWithFilter() {

        Response res = _infrastructure
                .PostRequest("s", "Harry Potter")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        List<Movie> movies = res.jsonPath().getList("Search", Movie.class);

        for (Movie movie : movies) {

            if (movie.Title.equals("Harry Potter and the Sorcerer's Stone")) {
                imdbId = movie.imdbID;
            }

            Assertions.assertNotNull(movie.Title, "Title is null");
            Assertions.assertNotNull(movie.Year, "Year is null");

        }

    }

    @Test
    @Order(2)
    void getMovieByImdbId() {

        _infrastructure
                .PostRequest("i", imdbId)
                .then()
                .body("Title", equalTo("Harry Potter and the Sorcerer's Stone"))
                .body("Released", notNullValue());
    }

}
