package api.tests;

import api.specifications.Specifications;
import api.models.UserDataWithLombok;
import io.qameta.allure.Description;
import junit.framework.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static api.listeners.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;



public class ListUsersRequestTest {

    public final static String URL = "https://reqres.in/";

    @Test
    @DisplayName("Scheme ответа")
    @Description("Проверка, ответ от сервера соответствует scheme")
    public void checkSchemeResponse() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());
        given()
                .filter(withCustomTemplates())
                .get("api/users?page=1")
                .then().log().all()
                .body(matchesJsonSchemaInClasspath("schemas/ListUsers_scheme.json"));
    }

    @Test
    @DisplayName("Аватар содержит id пользователя")
    @Description("Проверка, что у каждого пользователя в строке с автаром есть id")
    public void checkAvatarAndIdTest() {

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());
        List<UserDataWithLombok> users = given()
                .when()
                .filter(withCustomTemplates())
                .get("api/users?page=1")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserDataWithLombok.class);

        //Проверка что у каждого пользователя в строке с автаром есть id, с помощью stream
        users.stream().forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        //Проверка что у каждого пользователя в строке с автаром есть id, с помощью List
        List<String> avatars = users.stream().map(UserDataWithLombok::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    @DisplayName("Email оканчивается на reqres.in")
    @Description("Проверка, что Email оканчивается на reqres.in")
    public void checkEmail() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());
        List<UserDataWithLombok> users = given()
                .when()
                .filter(withCustomTemplates())
                .get("api/users?page=1")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserDataWithLombok.class);

        //Проверка что у каждого пользователя Email оканчвается на reqres.in, с помощью stream
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("reqres.in")));

        //Проверка что у каждого пользователя Email оканчвается на reqres.in, с помощью List
        List<String> emails = users.stream().map(UserDataWithLombok::getEmail).collect(Collectors.toList());
        for (int i = 0; i < emails.size(); i++) {
            Assert.assertTrue(emails.get(i).contains("reqres.in"));
        }
    }


}



