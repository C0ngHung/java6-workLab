--========================================================
-- CREATE DATABASE & USE
--========================================================
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'J6Security2')
    CREATE DATABASE J6Security2;
GO

USE J6Security2;
GO

--========================================================
-- TABLE: Users
--========================================================
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Users' AND xtype='U')
BEGIN
    CREATE TABLE Users (
        Username VARCHAR(50) NOT NULL PRIMARY KEY,
        Password VARCHAR(500) NOT NULL,
        Enabled BIT NOT NULL
    );
END
GO

--========================================================
-- TABLE: Roles
--========================================================
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='Roles' AND xtype='U')
BEGIN
    CREATE TABLE Roles (
        Id VARCHAR(50) NOT NULL PRIMARY KEY,
        Name VARCHAR(50) NOT NULL
    );
END
GO

--========================================================
-- TABLE: UserRoles
--========================================================
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='UserRoles' AND xtype='U')
BEGIN
    CREATE TABLE UserRoles (
        Id BIGINT IDENTITY(1,1) PRIMARY KEY,
        Username VARCHAR(50) NOT NULL,
        RoleId VARCHAR(50) NOT NULL,
        CONSTRAINT FK_UserRoles_Users FOREIGN KEY (Username) REFERENCES Users (Username) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT FK_UserRoles_Roles FOREIGN KEY (RoleId) REFERENCES Roles (Id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT UX_UserRoles UNIQUE (Username, RoleId)
    );
END
GO

--========================================================
-- SEED USERS
--========================================================
IF NOT EXISTS (SELECT 1 FROM Users WHERE Username='user@gmail.com')
    INSERT INTO Users (Username, Password, Enabled) VALUES ('user@gmail.com', '{noop}123', 1);

IF NOT EXISTS (SELECT 1 FROM Users WHERE Username='admin@gmail.com')
    INSERT INTO Users (Username, Password, Enabled) VALUES ('admin@gmail.com', '{noop}123', 1);

IF NOT EXISTS (SELECT 1 FROM Users WHERE Username='both@gmail.com')
    INSERT INTO Users (Username, Password, Enabled) VALUES ('both@gmail.com', '{noop}123', 1);
GO

--========================================================
-- SEED ROLES
--========================================================
IF NOT EXISTS (SELECT 1 FROM Roles WHERE Id = 'ROLE_USER')
    INSERT INTO Roles (Id, Name) VALUES ('ROLE_USER', N'Nhân viên');

IF NOT EXISTS (SELECT 1 FROM Roles WHERE Id = 'ROLE_ADMIN')
    INSERT INTO Roles (Id, Name) VALUES ('ROLE_ADMIN', N'Quản lý');
GO

--========================================================
-- SEED USER ROLES
--========================================================
IF NOT EXISTS (SELECT 1 FROM UserRoles WHERE Username='user@gmail.com' AND RoleId='ROLE_USER')
    INSERT INTO UserRoles (Username, RoleId) VALUES ('user@gmail.com', 'ROLE_USER');

IF NOT EXISTS (SELECT 1 FROM UserRoles WHERE Username='admin@gmail.com' AND RoleId='ROLE_ADMIN')
    INSERT INTO UserRoles (Username, RoleId) VALUES ('admin@gmail.com', 'ROLE_ADMIN');

IF NOT EXISTS (SELECT 1 FROM UserRoles WHERE Username='both@gmail.com' AND RoleId='ROLE_USER')
    INSERT INTO UserRoles (Username, RoleId) VALUES ('both@gmail.com', 'ROLE_USER');

IF NOT EXISTS (SELECT 1 FROM UserRoles WHERE Username='both@gmail.com' AND RoleId='ROLE_ADMIN')
    INSERT INTO UserRoles (Username, RoleId) VALUES ('both@gmail.com', 'ROLE_ADMIN');
GO

ALTER TABLE Roles
ALTER COLUMN Name NVARCHAR(50);

UPDATE Roles SET Name = N'Quản lý' WHERE Id = 'ROLE_ADMIN';
UPDATE Roles SET Name = N'Nhân viên' WHERE Id = 'ROLE_USER';
