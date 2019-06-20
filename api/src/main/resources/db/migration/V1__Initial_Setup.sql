-- Create a basic user table
CREATE TABLE user
(
    id    IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR  NOT NULL,
    email VARCHAR  NOT NULL
);

-- Create an example user
INSERT INTO user (name, email) VALUES ('Aardvark', 'aardvark@animals.co.uk');