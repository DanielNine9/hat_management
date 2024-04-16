use nonsys
insert into THUONGHIEU(TenThuongHieu,Logo)
values('Gucci','png')
go

insert into LOAI(TenLoai)
values('Nón đẹp')
go

insert into NON(TenNon,MaThuongHieu,MaLoai,Gia,GiamGia,NgayTao,SoLuong,Hinh)
values	('nón 1',1,1,1000000,50,'2024-02-20 16:33:20',10,'sp.png')
go

insert into KHACHHANG(Ten,NgaySinh,SoDienThoai,Email, GioiTinh)
values	('Huy','2003-05-02','0812341234','dinhhuy0213@gmail.com', 1)
go

insert into HOADON(MaNhanVien,MaKhachHang,TongTien,TrangThai,NgayTao)
values	(1,1,100000,N'Đã thanh toán','2023-03-27 16:12:10'),
		(1,1,100000,N'Đã thanh toán','2024-01-27 16:12:10')
go

insert into HOADONCHITIET(SoLuong,GiaTien,MaHoaDon,MaNon)
values	(1,100000,1,1),
		(1,100000,2,1)
go