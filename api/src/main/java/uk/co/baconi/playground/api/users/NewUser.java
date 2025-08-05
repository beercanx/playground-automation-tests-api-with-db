package uk.co.baconi.playground.api.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Getter
@ToString
@AllArgsConstructor
public class NewUser {

    @Valid
    @NotNull
    private final String name;

    @Valid
    @NotNull
    private final String email;
}
