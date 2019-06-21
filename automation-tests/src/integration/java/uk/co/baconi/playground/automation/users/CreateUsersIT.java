package uk.co.baconi.playground.automation.users;

import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.baconi.playground.automation.ApiProperties;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CreateUsersIT {

    private final ApiProperties properties;

    @Test
    @DisplayName("POST /user should return 201 with expected user")
    void createUserReturns201WithExpectedUser() {

        final Map<String, String> newUser = new HashMap<>();
        newUser.put("name", "badger");
        newUser.put("email", "mash.potatoes@bodger.com");

        given().baseUri(properties.getUrl())
                .contentType(ContentType.JSON)
                .body(newUser)
                .post("/user")
                .then()
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body(
                        "id", equalTo(2),
                        "name", equalTo("badger"),
                        "email", equalTo("mash.potatoes@bodger.com")
                );

        // TODO - Assert that the user appears in the database

        // TODO - Create tear down to delete User
    }
}
