package api.tests;

import api.specifications.Specifications;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static api.listeners.CustomAllureListener.withCustomTemplates;
import static api.tests.TestBase.URL;
import static io.restassured.RestAssured.given;

public class DeleteUser {

    @Test
    @DisplayName("Delete запрос")
    @Description("Проверка, ответ от сервера содержит 204 статус код")
    public void deleteUserTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus(204));

        given()
                .when()
                .filter(withCustomTemplates())
                .delete("api/users/2")
                .then().log().all();

    }
}
