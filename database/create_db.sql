-- Script tao co so du lieu cho ung dung Spring Boot Security + MapStruct
-- DBMS: Microsoft SQL Server

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'webst_security')
BEGIN
    CREATE DATABASE [webst_security];
END
GO

USE [webst_security];
GO

-- Cac bang roles va users se duoc Hibernate tu dong tao (ddl-auto=update).
-- Duoi day la cau truc bang de tham khao hoac tao thu cong neu can:

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'roles')
BEGIN
    CREATE TABLE roles (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        name VARCHAR(30) NOT NULL UNIQUE
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'users')
BEGIN
    CREATE TABLE users (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        email VARCHAR(120) NOT NULL UNIQUE,
        password VARCHAR(150) NOT NULL,
        full_name NVARCHAR(120) NOT NULL,
        enabled BIT NOT NULL DEFAULT 1,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        role_id BIGINT NOT NULL,
        CONSTRAINT fk_users_roles FOREIGN KEY (role_id) REFERENCES roles(id)
    );
END
GO
