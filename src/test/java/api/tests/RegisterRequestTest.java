package api.tests;

import api.models.*;
import api.specifications.Specifications;
import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.json.Json;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import static api.tests.ListUsersRequestTest.URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RegisterRequestTest {

    @Test
    @DisplayName("Сравнение параметров ответа")
    @Description("Проверка, что id, token соответствуют заданным")
    public void successRegister() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        Register user = new Register("eve.holt@reqres.in", "pistol");

        //example 1
        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("token", is(token))
                .body("id", is(id));

        //example 2
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);

        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(id,successReg.getId());
        Assert.assertEquals(token,successReg.getToken());
    }

    @Test
    @DisplayName("Текст ошибки")
    @Description("Проверка, что текст ошибки соответствует заданному")
    public void unSuccessRegister(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus400());
        Register user = new Register("sydney@fife", "");

        String error = "Missing password";

        UnSuccessReg unSuccessReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);
        Assert.assertEquals(error, unSuccessReg.getError());
    }


    @Test
    @DisplayName("1")
    @Description("1")
    public void successRegisterWithOutModel() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());
        Map<String, String> user = new HashMap<>();
        user.put("email","eve.holt@reqres.in");
        user.put("password","pistol");

        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("id", equalTo(4))
                .body("token",equalTo("QpwL5tke4Pnpja7X4"));


        Response response =  given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response();
        JsonPath jsonPath=response.jsonPath();
        int id =jsonPath.get("id");
        String token = jsonPath.get("token");

        Assert.assertEquals(4,id);
        Assert.assertEquals("QpwL5tke4Pnpja7X4",token);
    }

    @Test
    @DisplayName("1")
    @Description("П1")
    public void unSuccessRegisterWithOutModel() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus400());

        Map<String, String> user = new HashMap<>();
        user.put("email","sydney@fife");

        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("error", equalTo("Missing password"));

        Response response =given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response();

        JsonPath jsonPath=response.jsonPath();
        String error =jsonPath.get("error");
        Assert.assertEquals("Missing password",error);
    }


}
