package uk.co.baconi.playground.automation.users;

import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.baconi.playground.automation.ApiProperties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GetUsersByNameIT {

    private final ApiProperties properties;

    @Test
    @DisplayName("GET /user/by-name/ should return 200 with two users")
    void getUserReturns200WithExpectedUsers() {

        // TODO - Generate user in database
        final var generatedUser = new TestUser(-1, "Aardvark", "aardvark@animals.co.uk");

        given().baseUri(properties.getUrl())
                .get("/user/by-name/Aardvark")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(
                        ".", hasSize(2),
                        "[0].id", equalTo(1),
                        "[0].name", equalTo("Aardvark"),
                        "[0].email", equalTo("aardvark@animals.co.uk"),
                        "[1].id", equalTo(generatedUser.getId()),
                        "[1].name", equalTo("Aardvark"),
                        "[1].email", equalTo("aardvark@animals.co.uk")
                );

        // TODO - Remove generated user from database

    }

    @Test
    @DisplayName("GET /user/by-name/aardvark should return 200 with no users")
    void getUserReturns200WithNoUsers() {
        given().baseUri(properties.getUrl())
                .get("/user/by-name/aardvark")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(
                        ".", hasSize(0)
                );
    }
}
