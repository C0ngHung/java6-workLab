CREATE DATABASE J6Security2
USE J6Security2

CREATE TABLE J6users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(200),
    enabled BIT
);

CREATE TABLE J6roles (
    id VARCHAR(50) PRIMARY KEY,
    name NVARCHAR(50)
);

CREATE TABLE J6userroles (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50),
    roleid VARCHAR(50),
    FOREIGN KEY (username) REFERENCES J6users(username),
    FOREIGN KEY (roleid) REFERENCES J6roles(id)
);

INSERT INTO J6users VALUES ('user@gmail.com', '{noop}123', 1);
INSERT INTO J6users VALUES ('admin@gmail.com', '{noop}123', 1);
INSERT INTO J6users VALUES ('both@gmail.com', '{noop}123', 1);

INSERT INTO J6roles VALUES ('ROLE_USER', N'Nhân viên');
INSERT INTO J6roles VALUES ('ROLE_ADMIN', N'Quản lý');

INSERT INTO J6userroles (username, roleid) VALUES
('user@gmail.com', 'ROLE_USER'),
('admin@gmail.com', 'ROLE_ADMIN'),
('both@gmail.com', 'ROLE_USER'),
('both@gmail.com', 'ROLE_ADMIN');
