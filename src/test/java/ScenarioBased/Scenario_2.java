package ScenarioBased;

import com.beust.ah.A;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Scenario_2 {
    //Note: This API does not have active field,
    //so for learning purposes we’ll simulate “active” logic:
    //
    //📌 Business rule assumption (for practice):
    //User is ACTIVE if email contains .biz

    //✅ Requirements Recap
    //Fetch all users
    //Filter active users
    //Loop through active users
    //
    //Validate:
    //Email must contain "@"
    //Collect invalid emails
    //
    //Assert:
    //At least one active user exists
    //No active user has invalid email

    @Test
    public void validateEmailFormat_activeUsersOnly() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();

        // Extract all users
        List<Map<String, Object>> users = response.jsonPath().getList("$");

        // Storing invalid emails
        List<String> invalidEmails = new ArrayList<>();
        int activeUserCount = 0;

        // Loop + filtering
        for (Map<String, Object> user : users) {
            String email = (String) user.get("email");

            // FILTER: Active users (practice condition)
            if (email != null || email.contains(".biz")) {
                // append usercount
                activeUserCount++;

                if (!email.contains("@")) {
                    invalidEmails.add(email);
                }

            }


        }

        Assert.assertTrue(activeUserCount>0,"No active users found");

        Assert.assertTrue(invalidEmails.isEmpty(),"Invalid email(s) found for active users: "+invalidEmails);

    }
}
