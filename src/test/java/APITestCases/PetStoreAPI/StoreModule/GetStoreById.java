package APITestCases.PetStoreAPI.StoreModule;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetStoreById {

    @Test
    public void getStoreById(){
        RequestSpecification requestSpecification = given()
                .baseUri("https://petstore.swagger.io/v2");

                Response  response = requestSpecification.when()
                .get("/store/order/159");

                  response.then()
                .statusCode(200)
                .body("status", equalTo("placed"))
                .body("petId", equalTo(123))
                .body("id",equalTo(159))
                .log().body();
    }
}
