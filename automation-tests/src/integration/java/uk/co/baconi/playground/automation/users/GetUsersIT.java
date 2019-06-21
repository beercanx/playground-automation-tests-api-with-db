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
class GetUsersIT {

    private final ApiProperties properties;

    @Test
    @DisplayName("GET /user should return 200 with expected users")
    void getUserReturns200WithExpectedUsers() {
        given().baseUri(properties.getUrl())
                .get("/user")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(
                        ".", hasSize(1),
                        "[0].id", equalTo(1),
                        "[0].name", equalTo("Aardvark"),
                        "[0].email", equalTo("aardvark@animals.co.uk")
                );
    }
}
