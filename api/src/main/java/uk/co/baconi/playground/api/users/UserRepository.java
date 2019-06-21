package uk.co.baconi.playground.api.users;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);

    List<User> findByEmail(String email);
}