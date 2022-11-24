package api.tests;

import api.models.*;
import api.specifications.Specifications;
import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static api.listeners.CustomAllureListener.withCustomTemplates;
import static api.tests.TestBase.URL;
import static org.hamcrest.Matchers.*;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RegisterRequestTest {

    Integer id = 4;
    String token = "QpwL5tke4Pnpja7X4";
    String error = "Missing password";

    @Test
    @DisplayName("Сравнение параметров ответа")
    @Description("Проверка, что id, token соответствуют заданным")
    public void successRegister() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());
        Register user = new Register("eve.holt@reqres.in", "pistol");

        //example 1
        given()
                .body(user)
                .when()
                .filter(withCustomTemplates())
                .post("api/register")
                .then().log().all()
                .body("token", is(token))
                .body("id", is(id));

        //example 2
        SuccessReg successReg = given()
                .body(user)
                .when()
                .filter(withCustomTemplates())
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);

        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(id,successReg.getId());
        Assert.assertEquals(token,successReg.getToken());

        //example 3
        Response response =  given()
                .body(user)
                .when()
                .filter(withCustomTemplates())
                .post("api/register")
                .then().log().all()
                .extract().response();

        JsonPath jsonPath=response.jsonPath();
        int idResponse =jsonPath.get("id");
        String tokenResponse = jsonPath.get("token");

        Assert.assertEquals(id.intValue(),idResponse);
        Assert.assertEquals(token,tokenResponse);
    }

    @Test
    @DisplayName("Текст ошибки")
    @Description("Проверка, что текст ошибки соответствует заданному")
    public void unSuccessRegister(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus400());
        Register user = new Register("sydney@fife", "");

        //example 1
        UnSuccessReg unSuccessReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);
        Assert.assertEquals(error, unSuccessReg.getError());

        //example 2
        Map<String, String> user2 = new HashMap<>();
        user2.put("email","sydney@fife");

        given()
                .body(user2)
                .when()
                .post("api/register")
                .then().log().all()
                .body("error", equalTo("Missing password"));

        //example 3
        Response response =given()
                .body(user2)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response();

        JsonPath jsonPath=response.jsonPath();
        String error =jsonPath.get("error");
        Assert.assertEquals("Missing password",error);
    }

}
