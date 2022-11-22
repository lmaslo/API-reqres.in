package api.tests;

import api.models.UserTime;
import api.models.UserTimeResponse;
import api.specifications.Specifications;
import io.qameta.allure.Description;
import junit.framework.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static api.tests.ListUsersRequestTest.URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class UpdateUser {

    @Test
    @DisplayName("Проверка параметра UpdatedAt")
    @Description("Проверка, что время которое возвращается в параметре UpdatedAt совпадает с серверным с погрешностью до 10 минут")
    public void timeTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());
        UserTime user = new UserTime("morpheus", "zion resident");
        UserTimeResponse response = given()
                .body(user)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);

        String regex = "(.{12})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex,"");

        String regexResp = "(.{6})$";
        String respTime = response.getUpdatedAt().replaceAll(regexResp,"");

        Assert.assertEquals(currentTime,respTime);

    }
}
