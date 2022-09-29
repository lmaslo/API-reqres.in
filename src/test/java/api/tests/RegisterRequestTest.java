package api.tests;

import api.models.Register;
import api.models.SuccessReg;
import api.models.UnSuccessReg;
import api.specifications.Specifications;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static api.tests.ListUsersRequestTest.URL;
import static io.restassured.RestAssured.given;

public class RegisterRequestTest {

    @Test
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
}
