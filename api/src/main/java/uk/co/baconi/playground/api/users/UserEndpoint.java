package uk.co.baconi.playground.api.users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;

@Slf4j
@Validated
@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEndpoint {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Iterable<User>> get() {

        log.info("GET /user");

        final var users = userRepository.findAll();

        log.info("Found users: {}", users);

        return ResponseEntity.ok(users);
    }

    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody final NewUser newUser) {

        log.info("POST /user - {}", newUser);

        final User user = userRepository.save(new User(newUser.getName(), newUser.getEmail()));

        log.info("Created user: {}", user);

        return ResponseEntity.created(URI.create("/user")).body(user);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<Iterable<User>> byName(@PathVariable String name) {

        log.info("GET /by-name/{}", name);

        final var users = userRepository.findByName(name);

        log.info("Found users: {}", users);

        return ResponseEntity.ok(users);
    }
}
