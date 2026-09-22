# Spring Boot 4 + Spring Security 7: Vi du 3 - Full System

Nhanh nay (example-3-full-system) thuc hien Vi du 3 theo tai lieu huong dan:
- Chuc nang Authentication:
  - Dang ky tai khoan voi xac thuc OTP qua email Gmail.
  - Gui lai ma OTP (Resend OTP).
  - Quen mat khau va Dat lai mat khau bang ma OTP.
  - Dang nhap luu session, gioi han 1 phien dang nhap dong thoi (maximumSessions = 1).
  - Dang xuat.
- Chuc nang Quan ly Users (Admin):
  - Xem danh sach, tim kiem (Search) theo username, email, ho ten.
  - Phan trang (Pagination).
  - Them User moi (mat khau mac dinh: 123456).
  - Sua thong tin User, doi Role, kich hoat/khoa tai khoan.
  - Xoa User.
  - Dem tong so User va so Product cua tung User.
- Chuc nang Quan ly Products (User):
  - Xem danh sach san pham, tim kiem, phan trang.
  - Them san pham moi kem upload hinh anh Cloudinary (co co che fallback luu local an toan).
  - Sua va Xoa san pham (tu dong xoa anh cu).
- Thymeleaf Layout Dialect + MapStruct 1.6.3 + Spring Security 7 + SQL Server.

## Thong so he thong
- Spring Boot 4.1.1
- Spring Security 7.1.x
- Database: webst_security_v3 tren SQL Server localhost:1433 (user: sa, pass: 123456)
- Port chay ung dung: 8082
- Mail SMTP: smtp.gmail.com (port 587)
- Script tao database: `database/create_db_v3.sql`

## Tai khoan mau thu nghiem
He thong tu dong khoi tao tai khoan khi ung dung khoi dong (DataInitializer):
1. Admin:
   - Username: admin
   - Email: admin@hcmute.edu.vn
   - Password: `123456`
   - Ho va ten: System Administrator
   - Role: ROLE_ADMIN
2. User:
   - Username: user01
   - Email: user01@gmail.com
   - Password: `123456`
   - Ho va ten: Nguyen Huu Trung
   - Role: ROLE_USER

## Huong dan chay
1. Thuc thi file `database/create_db_v3.sql` tren SQL Server (localhost:1433, sa / 123456).
2. Cau hinh moi truong (Tuy chon):
   - Mac dinh, ung dung da cau hinh san ket noi CSDL `webst_security_v3` tren port 8082.
   - Neu muon ung dung gui email OTP that qua Gmail cua ban:
     Copy file `.env.example` thanh file `.env`, sau do dien thong tin email va Mat khau ung dung (App Password) cua ban:
     ```properties
     MAIL_USERNAME=email_cua_ban@gmail.com
     MAIL_PASSWORD=mat_khau_ung_dung_16_ky_tu
     ```
   - Neu khong cau hinh email: Ung dung van hoat dong hoan toan binh thuong! Khi thu nghiem chuc nang Dang ky hoac Quen mat khau, ma OTP duoc tu dong in truc tiep ra man hinh Console/Terminal (`>>> [OTP SYSTEM] MA OTP CUA BAN LA: xxxxxx`), nguoi kiem thu chi can copy ma do vao form ma khong bi chan hay loi.
3. Chay lenh:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Truy cap ung dung tai: `http://localhost:8082/login`
