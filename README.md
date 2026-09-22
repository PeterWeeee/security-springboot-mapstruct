# Spring Boot 4 + Spring Security 7: Vi du 2 - Custom Login

Nhanh nay (example-2-custom-login) thuc hien Vi du 2 theo tai lieu:
- Chuc nang Custom Login: Cho phep dang nhap bang Username hoac Email deu duoc.
- Hien thi thong tin nguoi dung tren header.html: Avatar (images), Ho va ten (fullName), Username, Email, Role va nut Dang xuat.
- Su dung Thymeleaf Layout Dialect (layout:decorate="~{layouts/layout}").
- Su dung MapStruct 1.6.3 de anh xa Entity sang DTO.
- Ket noi co so du lieu SQL Server: database webst_security_v2.

## Thong so he thong
- Spring Boot 4.1.1
- Spring Security 7.1.x
- Database: webst_security_v2 tren SQL Server localhost:1433 (user: sa, pass: 123456)
- Port chay ung dung: 8081

## Tai khoan mau thu nghiem
He thong tu dong khoi tao tai khoan khi ung dung khoi dong (DataInitializer):
1. User:
   - Username: user01
   - Email: user01@gmail.com
   - Password: `123456`
   - Ho va ten: Nguyen Huu Trung
   - Avatar: /images/user.png
   - Role: ROLE_USER
2. Admin:
   - Username: admin
   - Email: admin@hcmute.edu.vn
   - Password: `123456`
   - Ho va ten: System Administrator
   - Avatar: /images/avatar-default.png
   - Role: ROLE_ADMIN

## Huong dan chay
1. Thuc thi file `database/create_db_v2.sql` tren SQL Server (hoac de Hibernate ddl-auto tu dong tao).
2. Chay lenh:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Truy cap ung dung tai: `http://localhost:8081/login`
