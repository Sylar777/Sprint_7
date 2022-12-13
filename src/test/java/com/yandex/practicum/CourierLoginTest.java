package com.yandex.practicum;

import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Random;

public class CourierLoginTest {
    
    private String login;
    private final String PASSWORD = "14243";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";

        login = "testDan" + new Random().nextInt(10000);

        String json = "{\"login\":\"" + login + "\", \"password\": \"" + PASSWORD + "\", \"firstName\": \"saske\" }";

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
    public void courierCanBeLoginTest(){
        String json = "{\"login\": \"" + login + "\", \"password\": \"" + PASSWORD + "\"}";

            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier/login")
            .then()
            .assertThat()
            .body("id", notNullValue())
            .and()
            .statusCode(200);
    }

    @Test
    public void courierCantBeLoginWOAllFieldsTest(){
        String json = "{\"password\": \"" + PASSWORD + "\"}";

            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier/login")
            .then()
            .statusCode(400);
    }

    @Test
    public void courierWrongPasswordTest(){
        String json = "{\"login\": \"" + login + "\", \"password\": \"test\"}";

            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier/login")
            .then()
            .statusCode(404);
    }
}
