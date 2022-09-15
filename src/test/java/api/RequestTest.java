package api;

import api.models.UserDataWithLombok;


import junit.framework.Assert;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RequestTest {

    private final static String URL = "https://reqres.in/";


    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());

        /*List<UserData> users= given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL+"api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);*/

        List<UserDataWithLombok> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserDataWithLombok.class);

        //Проверка что у каждого пользователя в строке с автаром есть id
        users.stream().forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));

        //Проверка что у каждого пользователя Email оканчвается на reqres.in
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("reqres.in")));


        //Проверка что у каждого пользователя в строке с автаром есть id
        List<String> avatars = users.stream().map(UserDataWithLombok::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));

        }

        //Проверка что у каждого пользователя Email оканчвается на reqres.in
        List<String> emails = users.stream().map(UserDataWithLombok::getEmail).collect(Collectors.toList());

        for (int i = 0; i < emails.size(); i++) {
            Assert.assertTrue(emails.get(i).contains("reqres.in"));

        }


    }


}
