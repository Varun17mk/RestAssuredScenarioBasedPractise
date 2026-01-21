package APITestCases.PetStoreAPI.StoreModule;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.awt.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrder {

    // Positive Test Case
    @Test (priority = 1)
    public void createOrderTest(){
        String body = "{\n" +
                "  \"id\": 159,\n" +
                "  \"petId\": 123,\n" +
                "  \"quantity\": 159,\n" +
                "  \"shipDate\": \"2025-12-24T18:43:30.775Z\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}";

        RequestSpecification request = given()
                .baseUri("https://petstore.swagger.io/v2")
                .body(body)
                .contentType(ContentType.JSON);

        Response response = request.when()
                .post("/store/order");

        response.then()
                .statusCode(200)
                .body("status", equalTo("placed"))
                .body("petId", equalTo(123))
                .log().all();
    }

    // 1] Negative Test Case without Body
    @Test(priority = 2)
    public void createOrderNegativeTest(){
        String body = "";

        RequestSpecification request = given()
                .baseUri("https://petstore.swagger.io/v2")
                .body(body)
                .contentType(ContentType.JSON);

        Response response = request.when()
                .post("/store/order");

        response.then()
                .statusCode(400); // Bad request
    }

    // 2] Negative Test Case without contentType
    @Test(priority = 3)
    public void createOrderNegativeTestWithoutContentType(){
        String body = "";

        RequestSpecification request = given()
                .baseUri("https://petstore.swagger.io/v2")
                .body(body);

        Response response = request.when()
                .post("/store/order");

        response.then()
                .statusCode(415); // Missing content type
    }

    // 2] Negative Test Case with wrong http method (get request)
    @Test(priority = 4)
    public void createOrderNegativeTestWithWrongHttpMethod(){
        String body = "";

        RequestSpecification request = given()
                .baseUri("https://petstore.swagger.io/v2")
                .body(body);

        Response response = request.when()
                .get("/store/order");

        response.then()
                .statusCode(405); // Method not allowed. Need to use POST request to create resource but sending GET request.
    }
}
