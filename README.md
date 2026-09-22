# Spring Boot Security 7 with MapStruct and Thymeleaf

Project huong dan chuc nang Login bang Spring Security 7 tren nen tang Spring Boot 4, su dung MapStruct de chuyen doi DTO - Entity, Thymeleaf lam template engine (layout khong dung Thymeleaf Layout Dialect), ket noi co so du lieu SQL Server.

## Yeu cau he thong
- Java 17 hoac Java 21/26
- Maven 3.9+
- Microsoft SQL Server

## Cau hinh co so du lieu
- Database name: webst_security
- User: sa
- Password: sa-password (mac dinh trong tai lieu: 123456)
- Script tao database nam trong thu muc: `database/create_db.sql`

## Cac cong nghe su dung
- Spring Boot 4.1.1
- Spring Security 7.1.x
- Spring Data JPA / Hibernate
- MapStruct 1.6.3
- Lombok
- Thymeleaf + Thymeleaf Extras Spring Security
- SQL Server JDBC Driver

## Tai khoan mac dinh
Ung dung tu dong tao du lieu mau khi khoi dong (DataInitializer):
- Admin:
  - Email: admin@hcmute.edu.vn
  - Password: `123456`
  - Quyen: ROLE_ADMIN
- User:
  - Email: user@gmail.com
  - Password: `123456`
  - Quyen: ROLE_USER

## Huong dan chay ung dung
1. Mo file `database/create_db.sql` va thuc thi tren SQL Server de tao database `webst_security`.
2. Cau hinh thong so ket noi trong file `application.properties` hoac `.env`.
3. Chay lenh:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Truy cap ung dung tai: `http://localhost:8088/login`
