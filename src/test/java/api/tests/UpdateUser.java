package api.tests;

import api.models.UserTime;
import api.models.UserTimeResponse;
import api.specifications.Specifications;
import io.qameta.allure.Description;
import junit.framework.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static api.listeners.CustomAllureListener.withCustomTemplates;
import static api.tests.TestBase.URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class UpdateUser {

    @Test
    @DisplayName("Проверка параметра UpdatedAt")
    @Description("Проверка, что время которое возвращается в параметре UpdatedAt совпадает с серверным")
    public void timeTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());
        UserTime user = new UserTime("morpheus", "zion resident");
        UserTimeResponse response = given()
                .body(user)
                .when()
                .filter(withCustomTemplates())
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);

        String currentTime = Clock.systemUTC().instant().toString();
        currentTime=currentTime.substring(0,14);

        String respTime = response.getUpdatedAt();
        respTime=respTime.substring(0,14);

        Assert.assertEquals(currentTime,respTime);
    }
}
