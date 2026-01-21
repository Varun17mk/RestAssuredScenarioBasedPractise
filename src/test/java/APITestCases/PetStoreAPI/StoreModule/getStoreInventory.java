package APITestCases.PetStoreAPI.StoreModule;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class getStoreInventory {

    @Test
    public void getStoreInventoryAPI() {

        RequestSpecification requestSpecification = given()
                .baseUri("https://petstore.swagger.io/v2");

        Response  response = requestSpecification
                .when()
                .get("/store/inventory");

        response.then()
                .statusCode(200)
                .body("available", greaterThanOrEqualTo(0))
                .log().body();

    }
}