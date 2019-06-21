package uk.co.baconi.playground.automation;

import org.assertj.db.type.Source;
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
    public Source createDatabaseSource(final DatabaseProperties properties) {
        return new Source(properties.getUrl(), properties.getUser(), properties.getPassword());
    }
}
