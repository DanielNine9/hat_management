CREATE DATABASE NonSys
go

--use test
--drop database NonSyS
--GO
USE NONSYS


select *
from nhanvien

use nonsys

CREATE TABLE NhanVien(
	MaNV int PRIMARY KEY identity,
	MatKhau NVARCHAR(255) NOT NULL,
	HoTen NVARCHAR(50) NOT NULL,
	VaiTro BIT DEFAULT 0,
	Hinh VARCHAR(50),
	deleted bit default 0,
	Email NVARCHAR(50),
	Otp VARCHAR(10),
	SoDienThoai CHAR(10)
	)
GO


CREATE table THUONGHIEU(
	MaThuongHieu int primary key identity,
	TenThuongHieu nvarchar(50),
	Logo VARCHAR(50),
	Deleted BIT DEFAULT 0,
)


create table LOAI(
	MaLoai int primary key identity,
	TenLoai nvarchar(50),
	Deleted BIT DEFAULT 0
)

create table NON(
	MaNon int primary key identity,
	TenNon nvarchar(50),
	MaThuongHieu int,
	MaLoai int,
	Gia Money,
	GiamGia int,
	NgayTao Datetime DEFAULT GETDATE(),
	SoLuong INT,
	Hinh NVARCHAR(50),
	Deleted BIT DEFAULT 0,

	foreign key(MaThuongHieu) references THUONGHIEU(MaThuongHieu),
	foreign key(MaLoai) references LOAI(MaLoai)
)
create table KHACHHANG(
	MaKhachHang int primary key identity,
	Ten nvarchar(50),
	NgaySinh DATE,
	SoDienThoai char(10),
	Email char(50),
	Hinh NVARCHAR(50), 
	NgayGiaNhap datetime DEFAULT GETDATE(),
	GioiTinh bit,
	deleted bit default 0
)

create table HOADON(
	MaHoaDon INT primary key IDENTITY,
	MaNhanVien int,
	MaKhachHang int,
	foreign key(MaKhachHang) references KHACHHANG(MaKhachHang),
	foreign key(MaNhanVien) references NHANVIEN(MaNV),
	TongTien Money,
	TrangThai NVARCHAR(50),
	NgayTao DATETIME DEFAULT GETDATE(),
)


CREATE TABLE HOADONCHITIET(
	MaHoaDonChiTiet INT primary key IDENTITY,
	SoLuong INT,
	GiaTien Money,
	MaHoaDon INT,
	MaNon INT,
	foreign key(MaHoaDon) references HOADON(MaHoaDon),
	foreign key(MaNon) references NON(MaNon),
)
go



CREATE OR ALTER PROC sp_soluongnoncualoai
AS BEGIN 
	select tenloai, count(*) as soluong
	from loai a
	inner join non b on a.maloai = b.maloai 
	group by tenloai
END

GO

CREATE OR ALTER PROC sp_soluongnoncuathuonghieu
AS BEGIN 
	select TenThuongHieu, count(*) as soluong
	from thuonghieu a
	inner join non b on a.MaThuongHieu = b.MaThuongHieu 
	group by TenThuongHieu
END

GO



CREATE OR ALTER proc sp_getdoanhthuhomnay
as begin
	select sum(tongtien) as doanhthuhomnay
	from hoadon
	where day(ngaytao) = day(getdate()) and month(getdate()) = month(getdate())
	and year(ngaytao) = year(getdate())
end

GO

CREATE OR ALTER proc sp_getdoanhthu
as begin
	select sum(tongtien) as doanhthu
	from hoadon
	where year(getdate()) = year(ngaytao)
end

GO


CREATE OR ALTER proc sp_getKhachHangMuaNhieuNhat
as begin
	select concat('KH',MaKhachHang) as makhachhang, ten, SoDienThoai
	from KHACHHANG 
	where MaKhachHang = (
		SELECT TOP 1 MaKhachHang
		FROM (
			SELECT MaKhachHang, SUM(tongtien) AS total_amount
			FROM hoadon 
			GROUP BY MaKhachHang
		) AS tongtien
		ORDER BY total_amount DESC
	)
end

GO

ALTER TABLE NhanVien
ALTER COLUMN MatKhau NVARCHAR(255) -- Or the appropriate length

GO

CREATE OR ALTER PROCEDURE sp_getDoanhThuTheoThang
AS
BEGIN
	SELECT Months.Nam,
		   Months.Thang,
		   COALESCE(SUM(HoaDon.TongTien), 0) AS DoanhThu
	FROM
	(
		SELECT YEAR(GETDATE()) AS Nam, 1 AS Thang
		UNION ALL
		SELECT YEAR(GETDATE()), 2
		UNION ALL
		SELECT YEAR(GETDATE()), 3
		UNION ALL
		SELECT YEAR(GETDATE()), 4
		UNION ALL
		SELECT YEAR(GETDATE()), 5
		UNION ALL
		SELECT YEAR(GETDATE()), 6
		UNION ALL
		SELECT YEAR(GETDATE()), 7
		UNION ALL
		SELECT YEAR(GETDATE()), 8
		UNION ALL
		SELECT YEAR(GETDATE()), 9
		UNION ALL
		SELECT YEAR(GETDATE()), 10
		UNION ALL
		SELECT YEAR(GETDATE()), 11
		UNION ALL
		SELECT YEAR(GETDATE()), 12
	) AS Months
	LEFT JOIN HoaDon ON YEAR(HoaDon.NgayTao) = Months.Nam AND MONTH(HoaDon.NgayTao) = Months.Thang
	GROUP BY Months.Nam, Months.Thang
	ORDER BY Months.Nam ASC, Months.Thang ASC;
END;



GO

CREATE OR ALTER PROCEDURE sp_getDoanhThuTheoNam
AS
BEGIN
    SELECT YEAR(NgayTao) AS Nam,
           SUM(TongTien) AS DoanhThu
    FROM HoaDon
    GROUP BY YEAR(NgayTao)
    ORDER BY Nam ASC;
END;

GO


CREATE OR ALTER PROCEDURE sp_getTongTienKhachHang
AS
BEGIN
	SELECT top 5 MAKHACHHANG, SUM(TONGTIEN) AS SUMTONGTIEN
	FROM HOADON
	GROUP BY MaKhachHang
END

GO

CREATE OR ALTER PROCEDURE sp_getTrangThaiHoaDon
AS BEGIN
	SELECT 
			'Chưa thanh toán' AS TrangThai,
			COUNT(*) AS SoLuong
	FROM hoadon 
	WHERE trangthai LIKE N'%chưa thanh toán%'

	UNION ALL

	SELECT 
		'Đã thanh toán' AS TrangThai,
		COUNT(*) AS SoLuong
	FROM HOADON
	WHERE TRANGTHAI LIKE N'%đã thanh toán%'
END
go

CREATE OR ALTER PROCEDURE GetHoaDonByMonthYearRange
    @batdau DATE,
    @ketthuc DATE
AS
BEGIN
    DECLARE @ngaybatdau DATETIME = DATEADD(DAY, DATEDIFF(DAY, 0, @batdau), 0); -- Convert to DATETIME at the start of the day
    DECLARE @ngayketthuc DATETIME = DATEADD(DAY, DATEDIFF(DAY, 0, @ketthuc) + 1, 0); -- Convert to DATETIME at the start of the next day
    
    SELECT 
        *
    FROM 
        HoaDon
    WHERE 
        NGAYTAO >= @ngaybatdau 
        AND NGAYTAO < @ngayketthuc
        AND TRANGTHAI LIKE N'%Đã thanh toán%'
END;
use nonsys


EXEC GetHoaDonByMonthYearRange @batdau = '2024-01-01', @ketthuc = '2024-04-30';

exec sp_getDoanhThuTheoThang




