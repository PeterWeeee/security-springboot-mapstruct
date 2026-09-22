-- Script tao co so du lieu cho Vi du 3: He thong hoan chinh (Users, Roles, OtpToken, Products)
-- DBMS: Microsoft SQL Server

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'webst_security_v3')
BEGIN
    CREATE DATABASE [webst_security_v3];
END
GO

USE [webst_security_v3];
GO

-- Cac bang se duoc Hibernate tu dong tao (ddl-auto=update).
-- Duoi day la cau truc bang de tham khao:

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'roles')
BEGIN
    CREATE TABLE roles (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        name VARCHAR(30) NOT NULL
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
        full_name NVARCHAR(500) NULL,
        enabled BIT NOT NULL DEFAULT 0,
        role_id BIGINT NOT NULL,
        CONSTRAINT fk_users_roles_v3 FOREIGN KEY (role_id) REFERENCES roles(id)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'products')
BEGIN
    CREATE TABLE products (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        name NVARCHAR(500) NOT NULL,
        description NVARCHAR(500) NULL,
        price DECIMAL(18,2) NOT NULL,
        image_url VARCHAR(1000) NULL,
        user_id BIGINT NOT NULL,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        CONSTRAINT fk_products_users_v3 FOREIGN KEY (user_id) REFERENCES users(id)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'otp_tokens')
BEGIN
    CREATE TABLE otp_tokens (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        email VARCHAR(150) NOT NULL,
        otp_hash VARCHAR(100) NOT NULL,
        type VARCHAR(30) NOT NULL,
        expires_at DATETIME2 NOT NULL,
        attempts INT NOT NULL DEFAULT 0,
        used BIT NOT NULL DEFAULT 0,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE()
    );
END
GO
