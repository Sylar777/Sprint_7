package com.yandex.practicum;

import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class OrderListTest {
    
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
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
