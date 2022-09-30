package api.tests;

import api.models.ColorsData;
import api.specifications.Specifications;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;


import static api.tests.ListUsersRequestTest.URL;
import static io.restassured.RestAssured.given;

public class ListResourseTest {

    @Test
    public void sortYearsTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecStatus200());

        List<ColorsData> colors = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);

        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());

        List<Integer> sortYears = years.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(sortYears,years);
    }
}
