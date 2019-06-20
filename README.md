# Automation Tests for an API with a DB
Playground project for automation tests for an API with a database.

## API
This module provides a basic and crude API to drive some tests against.

It has two end points, one to return all users and a second that allows the creation of a user.

### Running
* via IntelliJ
```
Run uk.co.baconi.playground.api.ApiApplication
```
* via command line 
```bash
./mvnw spring-boot:run --projects api
```

## Automation Tests
This module provides some automation tests that can be used in theory in complete isolation from the application.

The tests that run against the API server live in `automation-tests/src/integration/java` folders and the unit tests for the automation project are in `automation-tests/src/test/java`.

### Running
* via IntelliJ
```
Run uk.co.baconi.playground.automation.users.GetUsersIT
Run uk.co.baconi.playground.automation.users.CreateUserIT
```
* via command line
```bash
./mvnw verify --projects automation-tests
```
* via command line with different properties
```bash
./mvnw verify --projects automation-tests -Dautomation.api.url=http://localhost:8081
```

## API Documentation

### Get Users
IntelliJ HTTP Client: `api/src/main/resources/http/Get-User.http`
```
GET http://localhost:8080/user

>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 20 Jun 2019 18:23:22 GMT

[
  {
    "id": 1,
    "name": "Aardvark",
    "email": "aardvark@animals.co.uk"
  }
]
```

### Create User
IntelliJ HTTP Client: `api/src/main/resources/http/Create-User.http`
```
POST http://localhost:8080/user
Content-Type: application/json

{
  "name": "badger",
  "email": "mash.potatoes@bodger.com"
}

>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

HTTP/1.1 201 
Location: /user
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 20 Jun 2019 18:38:39 GMT

{
  "id": 2,
  "name": "badger",
  "email": "mash.potatoes@bodger.com"
}
```