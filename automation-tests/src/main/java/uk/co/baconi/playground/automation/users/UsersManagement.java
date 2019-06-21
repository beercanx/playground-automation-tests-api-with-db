package uk.co.baconi.playground.automation.users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.db.type.Request;
import org.assertj.db.type.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.baconi.playground.automation.ConnectionSupplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.db.api.Assertions.assertThat;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
class UsersManagement {

    private final Source source;
    private final ConnectionSupplier connectionSupplier;

    Request findUserById(final int id) {
        return new Request(source, "SELECT id, name, email FROM user WHERE id = ?;", id);
    }

    Request findUserByName(final String name) {
        return new Request(source, "SELECT id, name, email FROM user WHERE name = ?;", name);
    }

    TestUser createUser(final String name, final String email) throws SQLException {

        final int id = insertUser(name, email);

        assertThat(findUserById(id))
                .hasNumberOfRows(1)
                .row()
                .value("id").isEqualTo(id)
                .value("name").isEqualTo(name)
                .value("email").isEqualTo(email);

        log.info("Created a new user ({}, {}) and got id [{}]", name, email, id);

        return new TestUser(id, name, email);
    }

    void destroyUserByName(final String name) throws SQLException {

        final var updateCount = deleteUserByName(name);

        Assertions
                .assertThat(updateCount)
                .isEqualTo(1);

        assertThat(findUserByName(name))
                .isEmpty();

        log.info("Destroyed a user by name: {}", name);
    }

    void destroyUserById(final int id) throws SQLException {

        final var updateCount = deleteUserById(id);

        Assertions
                .assertThat(updateCount)
                .isEqualTo(1);

        assertThat(findUserById(id))
                .isEmpty();

        log.info("Destroyed a user by id: {}", id);

    }

    /**
     * @return the number of rows affected
     */
    private int deleteUserByName(final String name) throws SQLException {

        try (final Connection connection = connectionSupplier.get()) {

            try (final PreparedStatement statement = deleteUserByName(connection)) {
                statement.setString(1, name);
                statement.execute();

                return statement.getUpdateCount();
            }
        }
    }

    /**
     * @return the number of rows affected
     */
    private int deleteUserById(final int id) throws SQLException {

        try (final Connection connection = connectionSupplier.get()) {

            try (final PreparedStatement statement = deleteUserById(connection)) {
                statement.setInt(1, id);
                statement.execute();

                return statement.getUpdateCount();
            }
        }
    }

    /**
     * @return the id for the new user
     */
    private int insertUser(final String name, final String email) throws SQLException {

        try (final Connection connection = connectionSupplier.get()) {

            try (final PreparedStatement statement = insertUser(connection)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.executeUpdate();

                try (final ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    return resultSet.getInt("id");
                }
            }
        }
    }

    private PreparedStatement insertUser(final Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO USER (name, email) VALUES ( ?, ? );", new String[]{"id"});
    }

    private PreparedStatement deleteUserById(final Connection connection) throws SQLException {
        return connection.prepareStatement("DELETE FROM USER WHERE ID = ? LIMIT 1;");
    }

    private PreparedStatement deleteUserByName(final Connection connection) throws SQLException {
        return connection.prepareStatement("DELETE FROM USER WHERE NAME = ? LIMIT 1;");
    }
}
