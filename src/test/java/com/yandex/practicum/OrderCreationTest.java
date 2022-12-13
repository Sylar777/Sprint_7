package com.yandex.practicum;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    
    private String colorJSON = "";

    public OrderCreationTest(List<String> color){

        if(!color.isEmpty()){
            for(int i=0; i< color.size(); i++ ){
                colorJSON += color.get(i);

                if(i!=color.size()-1){
                    colorJSON += "\", \"";
                }
            }
        }

        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters(name = "Color: {0}")
    public static Object[][] params() {
        return new Object[][] {
            { List.of("BLACK") },
            { List.of("BLACK", "GREY") },
            { List.of("")}
        };
    }
    
    @Test
    public void colorParametrTest(){
        String json = "{\"firstName\": \"Naruto\", \"lastName\": \"Uchiha\", \"address\": \"Konoha, 142 apt.\", \"metroStation\": 4, \"phone\": \"+7 800 355 35 35\", \"rentTime\": 5, \"deliveryDate\": \"2020-06-06\", \"comment\": \"Saske, come back to Konoha\", \"color\": [ \"" + colorJSON + "\" ]}";
        assertEquals(true, true);

        Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/orders");

        response
            .then()
            .assertThat()
            .body("track", notNullValue())
            .and()
            .statusCode(201);
    }
}
