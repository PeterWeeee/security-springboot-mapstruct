-- Script tao co so du lieu cho Vi du 2: Custom Login (Username hoac Email)
-- DBMS: Microsoft SQL Server

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'webst_security_v2')
BEGIN
    CREATE DATABASE [webst_security_v2];
END
GO

USE [webst_security_v2];
GO

-- Cac bang roles va users se duoc Hibernate tu dong tao (ddl-auto=update).
-- Duoi day la cau truc bang de tham khao:

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'roles')
BEGIN
    CREATE TABLE roles (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        name VARCHAR(50) NOT NULL UNIQUE
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'users')
BEGIN
    CREATE TABLE users (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        username VARCHAR(50) NOT NULL UNIQUE,
        email VARCHAR(150) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        full_name NVARCHAR(200) NULL,
        images NVARCHAR(500) NULL,
        enabled BIT NOT NULL DEFAULT 1,
        role_id BIGINT NOT NULL,
        CONSTRAINT fk_users_roles_v2 FOREIGN KEY (role_id) REFERENCES roles(id)
    );
END
GO
