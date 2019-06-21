package uk.co.baconi.playground.automation.users;

import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.baconi.playground.automation.ApiProperties;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CreateUsersIT {

    private final ApiProperties properties;
    private final UsersManagement usersManagement;

    @Test
    @DisplayName("POST /user should return 201 with expected user")
    void createUserReturns201WithExpectedUser() {

        final Map<String, String> newUser = new HashMap<>();
        newUser.put("name", "badger");
        newUser.put("email", "mash.potatoes@bodger.com");

        final var response = given()
                .baseUri(properties.getUrl())
                .contentType(ContentType.JSON)
                .body(newUser)
                .post("/user")
                .then()
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body(
                        "id", not(1),
                        "name", equalTo("badger"),
                        "email", equalTo("mash.potatoes@bodger.com")
                );

        final int id = response.extract().body().path("id");

        assertThat(usersManagement.findUserById(id))
                .hasNumberOfRows(1)
                .row()
                .value("id").isEqualTo(id)
                .value("name").isEqualTo("badger")
                .value("email").isEqualTo("mash.potatoes@bodger.com");
    }

    @AfterEach
    void removeGeneratedUsers() throws SQLException {
        usersManagement.destroyUserByName("badger");
    }
}
