package com.yandex.practicum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;

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



    @After
    public void clear(){

        String loginJSON = "{\"login\":\"" + login + "\", \"password\": \"" + PASSWORD + "\" }";

        Response responseOfLogin = given()
            .header("Content-type", "application/json")
            .and()
            .body(loginJSON)
            .when()
            .post("/api/v1/courier/login");
        
        responseOfLogin
            .then()
            .assertThat()
            .body("id", notNullValue())
            .and()
            .statusCode(200);
        
        String id = responseOfLogin.asString().substring(6, 12);
        String deletionJSON = "{\"id\":\"" + id + "\" }";
        String deletionRequestLink = "/api/v1/courier/" + id;

        given()
            .header("Content-type", "application/json")
            .and()
            .body(deletionJSON)
            .when()
            .delete(deletionRequestLink)
            .then()
            .statusCode(200);
    }
    
    @Test
    @DisplayName("Check that courier can login")
    @Description("Check that courier can login")
    @Step("Send POST request to login courier")
    public void courierCanLoginTest(){
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
    @DisplayName("Check that courier can't login w/o all required fields")
    @Description("Check that if some required fields are missed then courier can't login")
    @Step("Send POST request to login courier w/o all required fields")
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
    @DisplayName("Check that courier can't login with wrong password")
    @Description("Check that courier can't login with wrong password")
    @Step("Send POST request to login courier with wrong password")
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
