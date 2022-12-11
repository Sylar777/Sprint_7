package com.yandex.practicum;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    
    @Test
    public void courierCanBeLogin(){
        String json = "{\"login\": \"testDan3\", \"password\": \"14243\"}";

        Response response =
            given()
            .header("Content-type", "application/json")
            .and()
            .body(json)
            .when()
            .post("/api/v1/courier/login");

        System.out.println("response = " + response.asString());

        response
            .then()
            .assertThat()
            .body("id", notNullValue())
            .and()
            .statusCode(200);
    }
    /*
     * 
     * Проверь:
            + курьер может авторизоваться;
            для авторизации нужно передать все обязательные поля;
            система вернёт ошибку, если неправильно указать логин или пароль;
            если какого-то поля нет, запрос возвращает ошибку;
            если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
            успешный запрос возвращает id.
     */
}
