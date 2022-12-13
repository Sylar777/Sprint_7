package com.yandex.practicum;

import org.junit.Before;
import org.junit.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class OrderListTest {
    
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check that list of Orders can be gotten")
    @Description("Check that list of Orders can be gotten")
    @Step("Send GET request to get list of Orders")
    public void listOfOrdersTest(){
            given()
            .when()
            .get("/api/v1/orders")
            .then()
            .assertThat()
            .body("orders", notNullValue())
            .and()
            .statusCode(200);
    }
}
