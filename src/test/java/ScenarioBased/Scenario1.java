package ScenarioBased;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Scenario1 {
    //Scenario 1 — Validate Email Format (Multiple Records)
    //
    //🎯 Goal: Learn looping + basic validation
    //
    //API Assumption
    //Response returns list of users:
    //[
    //  { "email": "abc@gmail.com", "active": true },
    //  { "email": "xyz.com", "active": false }
    //]

    //Requirements
    //Fetch all users
    //Loop through all users
    //
    //Validate:
    //email contains "@"
    //Collect invalid emails
    //
    //Assert:
    //Invalid email list must be empty
    //
    //📌 No filtering yet — only looping

    @Test
    public void validateEmailFormat_MultipleRecords() {

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given().when().
                get("/users")
                .then()
                .statusCode(200)
                .extract().response();

        // Step 1: Extract users list (JSONPath only for extraction)
        // "$" is root node is a list as response doesn't have name for json or json response is itself is an arryay
        List<Map<String, Object>> users = response.jsonPath().getList("$");

        // Step 2: Collect invalid emails
        List<String> invalidEmails = new ArrayList<>();

        //Step 3: Loop through all users
        for (Map<String, Object> user : users) {
            String email = (String) user.get("email"); // typecasting to String is required as value of email[key] is an Object

            if (email == null || !email.contains("@")) {
                invalidEmails.add(email);
            }
        }

        // Step 4: Final assertion
        Assert.assertTrue(invalidEmails.isEmpty(),"Invalid email(s) found" + invalidEmails);

    }


}
