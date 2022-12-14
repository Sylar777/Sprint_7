package com.yandex.practicum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.Random;

public class CourierCreationTest {

    private String login;
    private Response responseOfCourierCreation;
    private final String PASSWORD = "14243";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        login = "testDan" + new Random().nextInt(10000);
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
        
            if(!responseOfLogin.asString().contains("Учетная запись не найдена")){
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
    }

    @Test
    @DisplayName("Check that courier can be created")
    @Description("Check that courier can be created")
    @Step("Send POST request to create courier")
    public void courierCanBeCreatedTest(){
        String json = "{\"login\":\"" + login + "\", \"password\": \"" + PASSWORD + "\", \"firstName\": \"saske\" }";

        responseOfCourierCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier");

        responseOfCourierCreation
            .then()
            .assertThat()
            .body("ok", equalTo(true))
            .and()
            .statusCode(201);
    }


    @Test
    @DisplayName("Check that the same courier can't be created")
    @Description("Check that if courier with this login and password already exist then courier can't be created")
    @Step("Send POST request to create the same courier")
    public void cantBeCreatedTheSameCourierTest(){
        String json = "{\"login\":\"" + login + "\", \"password\": \"" + PASSWORD + "\", \"firstName\": \"saske\" }";

        responseOfCourierCreation = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier");

        responseOfCourierCreation
            .then()
            .statusCode(201);

        Response responseOfCourierCreation2 =
            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier");

        responseOfCourierCreation2
            .then()
            .statusCode(409);
    }

    @Test
    @DisplayName("Courier can't be created w/o all required fields")
    @Description("Check that if some required fields are missed then courier can't be created")
    @Step("Send POST request to create courier w/o all required fields")
    public void allRequiredFieldsMustBePopulatedThroughCreatingCourierTest(){
        String json = "{\"login\":\"" + login + "\", \"firstName\": \"saske\" }";

        responseOfCourierCreation =
            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier");

        responseOfCourierCreation
            .then()
            .statusCode(400);
    }
}