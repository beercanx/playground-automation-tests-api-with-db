package uk.co.baconi.playground.automation;

import org.assertj.db.type.AssertDbConnection;
import org.assertj.db.type.AssertDbConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.DriverManager;

@SpringBootApplication
public class AutomationApplication {

    @Bean
    public ConnectionSupplier createConnectionSupplier(final DatabaseProperties properties) {
        return () -> DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());
    }

    @Bean
    public AssertDbConnection createDatabaseSource(final DatabaseProperties properties) {
        return AssertDbConnectionFactory.of(properties.getUrl(), properties.getUser(), properties.getPassword()).create();
    }
}
