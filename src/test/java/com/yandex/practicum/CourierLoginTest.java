package com.yandex.practicum;

import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    
    @Test
    public void courierCanBeLoginTest(){
        String json = "{\"login\": \"testDan3\", \"password\": \"14243\"}";

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
        String json = "{\"password\": \"14243\"}";

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
        String json = "{\"login\": \"testDan3\", \"password\": \"14$243\"}";

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
