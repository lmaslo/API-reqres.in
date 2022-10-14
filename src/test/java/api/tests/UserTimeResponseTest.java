package api.tests;

import api.models.UserTime;
import api.models.UserTimeResponse;
import api.specifications.Specifications;
import org.junit.jupiter.api.Test;

import static api.tests.ListUsersRequestTest.URL;
import static io.restassured.RestAssured.given;

public class UserTimeResponseTest {
    @Test
    //need fix
    public void userTimeTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());
        UserTime user = new UserTime("morpheus", "zion resident");

        UserTimeResponse response = given()
                .body(user)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);

        int i=0;
    }

}
