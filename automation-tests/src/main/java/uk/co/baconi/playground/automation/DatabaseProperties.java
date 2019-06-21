package uk.co.baconi.playground.automation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "automation.db")
public class DatabaseProperties {
    private String url;
    private String user;
    private String password;
}
