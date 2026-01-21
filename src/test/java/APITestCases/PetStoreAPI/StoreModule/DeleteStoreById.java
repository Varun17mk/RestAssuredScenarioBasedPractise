package APITestCases.PetStoreAPI.StoreModule;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteStoreById {

    @Test
    public void deleteStoreByIdTest(){

        RequestSpecification request = given().baseUri("https://petstore.swagger.io/v2");

        Response response = request
                .when()
                .delete("/store/order/159");

        response.then()
                .statusCode(200)
                .body("code",equalTo(200))
                .log().all();

    }
}
