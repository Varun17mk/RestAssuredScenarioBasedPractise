package APITestCases.PetStoreAPI.StoreModuleAPIChaining;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StoreAPIChainingTest {

    // API Chaining is like storing input from response body of one request and using that in other request

    // 1] Create Store and extract ID
    // 2] Get order by stored ID from created Store
    // 3] Delete Order by Id using stored ID
    // 4] Get order of same deleted order to check if the store is deleted from DB

    RequestSpecification request;
    int orderID;

    @BeforeClass
    public void setUp() {
        request = given().baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.JSON);
    }


    // 1] Create Store and extract ID

    @Test(priority = 1)
    public void creteStoreOrderTest() {
        String body = "{\n" +
                "  \"id\": 753,\n" +
                "  \"petId\": 456,\n" +
                "  \"quantity\": 159,\n" +
                "  \"shipDate\": \"2025-12-24T18:43:30.775Z\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}";
        Response response = request.body(body).when().post("/store/order");

        response.then()
                .statusCode(200)
                .body("petId", equalTo(456))
                .body("status", equalTo("placed"));

        // Extract orderId for chaining
        orderID = response.jsonPath().getInt("id");
        System.out.println("Your OrderId is : " + orderID);
    }


// 2] Get order by stored ID from created Store

    @Test(priority = 2)
    public void getStoreByIDTest() {
        Response response = request.when().get("/store/order/" + orderID);
        response.then()
                .statusCode(200)
                .body("petId", equalTo(456))
                .body("status", equalTo("placed"))
                .body("id", equalTo(753));
    }

    // 3] Delete Order by Id using stored ID
    @Test(priority = 3)
    public void deleteStoreByIDTest() {
        Response response = request.when().delete("/store/order/" + orderID);
        response.then()
                .statusCode(200)
                .body("code", equalTo(200));
    }


    // 4] Get order of same deleted order to check if the store is deleted from DB
    @Test(priority = 4)
    public void getStoreOfDeletedStoreByIDTest() {
        Response response = request.when().get("/store/order/" + orderID);
        response.then()
                .statusCode(404);
    }
}
