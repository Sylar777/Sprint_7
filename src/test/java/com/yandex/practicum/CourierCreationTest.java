package com.yandex.practicum;

import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.Random;

public class CourierCreationTest {

    private String login;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        login = "testDan" + new Random().nextInt(10000);
    }
    
    @Test
    public void courierCanBeCreatedTest(){
        String json = "{\"login\":\"" + login + "\", \"password\": \"14243\", \"firstName\": \"saske\" }";

            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier")
            .then()
            .assertThat()
            .body("ok", equalTo(true))
            .and()
            .statusCode(201);
    }


    @Test
    public void canBeCreatedTheSameCourierTest(){
        String json = "{\"login\":\"" + login + "\", \"password\": \"14243\", \"firstName\": \"saske\" }";

            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier")
            .then()
            .statusCode(201);

        Response response =
            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier");

        response
            .then()
            .statusCode(409);

    }

    @Test
    public void allRequiredFieldsMustBePopulatedThroughCreatingCourierTest(){
        String json = "{\"login\":\"" + login + "\", \"firstName\": \"saske\" }";

        Response response =
            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier");

        response
            .then()
            .statusCode(400);
    }
}