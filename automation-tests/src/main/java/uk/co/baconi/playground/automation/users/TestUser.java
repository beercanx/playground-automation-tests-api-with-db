package uk.co.baconi.playground.automation.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TestUser {
    private final int id;
    private final String name;
    private final String email;
}
