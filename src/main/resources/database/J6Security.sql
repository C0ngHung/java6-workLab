CREATE DATABASE J6Security
USE J6Security

CREATE TABLE Users (
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(500) NOT NULL,
    Enabled BIT NOT NULL,
    PRIMARY KEY (Username)
);

CREATE TABLE Authorities (
    Id BIGINT IDENTITY(1,1) PRIMARY KEY,
    Username VARCHAR(50) NOT NULL,
    Authority VARCHAR(50) NOT NULL,
    UNIQUE(Username, Authority),
    FOREIGN KEY (Username) REFERENCES Users(Username)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Users VALUES ('user@gmail.com', '{noop}123', 1);
INSERT INTO Users VALUES ('admin@gmail.com', '{noop}123', 1);
INSERT INTO Users VALUES ('both@gmail.com', '{noop}123', 1);

INSERT INTO Authorities VALUES ('user@gmail.com', 'ROLE_USER');
INSERT INTO Authorities VALUES ('admin@gmail.com', 'ROLE_ADMIN');
INSERT INTO Authorities VALUES ('both@gmail.com', 'ROLE_USER');
INSERT INTO Authorities VALUES ('both@gmail.com', 'ROLE_ADMIN');
