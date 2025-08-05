package uk.co.baconi.playground.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class ApiApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
