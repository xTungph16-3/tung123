create database quanLyCuaHangGiay
go
use quanLyCuaHangGiay
go

create table size(
	size_id int identity(1,1) primary key,
	giaTri float not null,
		ngayTao datetime default getdate()

)
go
create table nhaCungCap(
	nhaCC_id int identity(1,1) primary key,
	ten nvarchar(100) not null,
	moTa nvarchar(max),
	diaChi nvarchar(200),
		ngayTao datetime default getdate()

)

go

create table chatLieu(
	chatLieu_id int identity(1,1) primary key,
	ten nvarchar(100) not null,
	nguonGoc nvarchar(100),
	moTa nvarchar(max),
		ngayTao datetime default getdate()

)

go

create table mauSac(
	mauSac_id int identity(1,1) primary key,
	tenMau nvarchar(100) not null,
	moTa nvarchar(100),
	ngayTao datetime default getdate()
)
go
create table nhanVien(
	nhanVien_id varchar(7) primary key,
	hoTen nvarchar(100) not null,
	gioiTinh bit not null,
	ngaySinh date not null,
	diaChi nvarchar(max) not null,
	sdt varchar(10) not null,
	trangThai nvarchar(100) not null,
	anh nvarchar(max),
	matkhau varchar(max) not null,
	vaiTro nvarchar(100) not null
)
go 
create table sanPham(
	sanPham_id varchar(10) primary key,
	ten nvarchar(100) not null,
	moTa nvarchar(max),
	trangThai nvarchar(100) not null,
	ngayTao datetime default getdate(),
)
go
create table sanPhamChiTiet(
	sPCT_id varchar(10) primary key,
	soLuong int not null,
	donGia decimal(18, 2) not null,
	size_id int not null,
	chatLieu_id int not null,
	nhaCC_id int not null,
	anh nvarchar (max),
	mauSac_id int not null,
	sanPham_id varchar(10) not null,
	trangThai nvarchar(100) not null,
	moTa nvarchar(max),
	ngayTaoSPCT datetime default getdate()
)
go 
create table khachHang(
	khachHang_id varchar(10) primary key,
	hoTenKH nvarchar(100) not null,
	gioiTinh bit,
	diaChi nvarchar(200),
	sdt varchar(10) not null,
	email varchar(100),
	ghiChu nvarchar(max),
	ngayTaoKH datetime default getdate()
)
go
create table thanhToan(
	thanhToan_id int identity(1,1) primary key,
	hinhThucThanhToan nvarchar(100) not null
)
go
create table hoaDon(
	hoaDon_id varchar(10)  primary key,
	ngayTaoHD datetime default getdate(),
	nhanVien_id varchar(7) not null,
	tongTien decimal(18, 2),
	trangThai nvarchar(100),
	khachHang_id varchar(10),
	thanhToan_id int,
	hanDoiTra DATETIME DEFAULT DATEADD(DAY, 7, GETDATE()),
	ghiChu nvarchar(max)
)
go 
create table hoaDonChiTiet(
	hDCT_id varchar(10) primary key,
	hoaDon_id varchar(10) not null,
	sPCT_id varchar(10) not null,
	soLuong int not null,
	giaBan decimal(18, 2) not null,
	thanhTien decimal(18, 2) not null,
	trangThaiHDCT nvarchar(100)
)
go

create table traHang(
 traHang_id int identity(1,1) primary key,
 hoaDon_id varchar(10) not null,
 sPCT_id varchar (10) not null,
 nhanVien_id varchar(7),
 soLuongTra int  null,
 giaTra decimal(18, 2)  null,
 tongTienHoanTra decimal(18,2),
 ngayTraHang datetime default getdate(),
 lyDoTraHang nvarchar (max),
 trangThai nvarchar(100)
 )
 go


--KHOÁ PHỤ BẢNG SẢN PHẨM CHI TIẾT
alter table sanPhamChiTiet
add constraint fk_spct_chatLieu foreign key (chatLieu_id) references chatLieu (chatLieu_id)
go
alter table sanPhamChiTiet
add constraint fk_spct_nhaCC foreign key (nhaCC_id) references nhaCungCap (nhaCC_id)
go
alter table sanPhamChiTiet
add constraint fk_spct_mauSac foreign key (mauSac_id) references mauSac (mauSac_id)
go
alter table sanPhamChiTiet
add constraint fk_spct_sanPham foreign key (sanPham_id) references sanPham (sanPham_id)
go
alter table sanPhamChiTiet
add constraint fk_spct_size foreign key (size_id) references size (size_id)
go
--KHOÁ PHỤ BẢNG HOÁ ĐƠN
alter table hoaDon
add constraint fk_hoaDon_thanhToan foreign key (thanhToan_id) references thanhToan (thanhToan_id)
go
alter table hoaDon
add constraint fk_hoaDon_khachHang foreign key (khachHang_id) references khachHang (khachHang_id)
go
alter table hoaDon
add constraint fk_hoaDon_nhanVien foreign key (nhanVien_id) references nhanVien (nhanVien_id)
go
--KHOÁ PHỤ BẢNG HOÁ ĐƠN CHI TIẾT
alter table hoaDonChiTiet
add constraint fk_hoaDonChiTiet_hoaDon foreign key (hoaDon_id) references hoaDon (hoaDon_id)
go
alter table hoaDonChiTiet
add constraint fk_hoaDonChiTiet_sanPhamChiTiet foreign key (sPCT_id) references sanPhamChiTiet (sPCT_id)

go
--KHOÁ PHỤ BẢNG TRẢ HÀNG
alter table traHang
add constraint fk_traHang_hoaDon foreign key (hoaDon_id) references hoaDon (hoaDon_id)
go
alter table traHang
add constraint fk_traHang_sanPhamChiTiet foreign key (sPCT_id) references sanPhamChiTiet (sPCT_id)


go
INSERT INTO size (giaTri) VALUES
(38.0),
(39.0),
(40.0),
(41.0),
(42.0),
(43.0),
(44.0),
(45.0);
go
INSERT INTO nhaCungCap (ten, moTa, diaChi) VALUES
('NIKE', N'BÁN ĐỘC QUYỀN TẠI CỬA HÀNG', N'MỸ');
go
INSERT INTO thanhToan (hinhThucThanhToan) VALUES
( N'Tiền mặt'),
( N'Chuyển khoản');


go
INSERT INTO chatLieu (ten, nguonGoc, moTa) VALUES
(N'Cotton Canvas', N'Chủ yếu từ cotton hoặc linen.', N'Nhẹ, thoáng khí, và phổ biến trong giày sneaker thoải mái'),
(N'Da lộn', N'Da nubuck hoặc da lộn', N'Cảm giác mềm mại, thường được sử dụng trong giày thể thao và thời trang.'),
(N'Vải lưới', N'Thường được làm từ sợi tổng hợp như polyester hoặc nylon.', N'Đàn hồi, thoáng khí, thích hợp cho giày chạy và thể thao.'),
(N'Da', N'Có thể từ da bò, da cá sấu, hoặc da cá.', N'Sang trọng, bền, và thường xuất hiện trong giày công sở và thời trang.'),
(N'Len', N'Thường là sợi tổng hợp hoặc sợi tự nhiên như len.', N'Co giãn, ôm chân, thích hợp cho giày thoải mái và thời trang.'),
(N'Da Nappa', N'Da bò được xử lý đặc biệt để đạt được độ mềm và bóng.', N'Cảm giác mịn, bền, thường xuất hiện trong giày thời trang và đôi khi được làm tay.'),
(N'Vải Dệt Đan', N' Sợi tổng hợp như polyester hoặc nylon.', N'Dệt chéo có độ đàn hồi, thoáng khí, thích hợp cho giày chạy và thể thao.'),
(N'Cao su Neoprene ', N'Chủ yếu là chất liệu tổng hợp.', N'Đàn hồi, chống nước, thường được sử dụng trong giày thể thao và sneaker hiện đại.');
go
INSERT INTO mauSac (tenMau, moTa) VALUES
(N'Màu Đỏ', N'Mô tả màu đỏ'),
(N'Màu Vàng', N'Mô tả màu vàng'),
(N'Màu Cam', N'Mô tả màu cam'),
(N'Màu Nâu', N'Mô tả màu nâu'),
(N'Màu Trắng', N'Mô tả màu trắng'),
(N'Màu Xám', N'Mô tả màu xám'),
(N'Màu Be', N'Mô tả màu be'),
(N'Màu xanh dương', N'Mô tả màu xanh dương'),
(N'Màu xanh lá', N'Mô tả màu xanh lá');

go
INSERT INTO nhanVien (nhanVien_id, hoTen, gioiTinh, ngaySinh, diaChi, sdt, trangThai, anh, matkhau, vaiTro) VALUES
('NV001', N'Nguyễn Trọng Phú', 1, '2004-09-13', N'123 Đường ABC, Huyện HL, TP.Lạng Sơn', '0123456789', N'Hoạt động', N'duongdananh1.jpg', 'matkhau1', N'Quản lý'),
('NV002', N'Vũ Quang Minh', 1, '1995-03-20', N'Hà Nội', '0987654321', N'Hoạt động', N'duongdananh2.jpg', 'matkhau2', N'Quản lý'),
('NV003', N'Lê Xuân Tiến', 1, '1985-08-10', N'789 Đường DEF, Hà Nội', '0123456780', N'Nghỉ việc', N'duongdananh3.jpg', 'matkhau3', N'Quản lý'),
('NV004', N'Dương Doãn Tuân', 1, '1992-12-05', N'101 Đường GHI, Hà Nội', '0909090909', N'Hoạt động', N'duongdananh4.jpg', 'matkhau4', N'Quản lý'),
('NV005', N'Hoàng Văn E', 1, '1980-04-25', N'321 Đường KLM, Hà Nội', '0888888888', N'Hoạt động', N'duongdananh5.jpg', 'matkhau5', N'Nhân viên'),
('NV006', N'Trịnh Thị F', 0, '1997-06-15', N'555 Đường NOP, Hà Nội', '0777777777', N'Hoạt động', N'duongdananh6.jpg', 'matkhau6', N'Nhân viên'),
('NV007', N'Đỗ Văn G', 1, '1988-10-30', N'888 Đường QRS, Hà Nội', '0666666666', N'Hoạt động', N'duongdananh7.jpg', 'matkhau7', N'Nhân viên'),
('NV008', N'Mai Thị H', 0, '1993-02-12', N'666 Đường TUV, Hà Nội', '0555555555', N'Hoạt động', N'duongdananh8.jpg', 'matkhau8', N'Nhân viên');
go
INSERT INTO khachHang (khachHang_id, hoTenKH, gioiTinh, diaChi, sdt, email, ghiChu) VALUES
('KH000', N'Khách bán lẻ', 1, N'', '', '', N'Mô tả Khách bán lẻ'),
('KH001', N'Nguyễn Thị M', 0, N'321 Đường ABC, Hà Nội', '0123456789', 'nguyen.thi.m@example.com', N'Ghi chú 1'),
('KH002', N'Trần Văn N', 1, N'456 Đường XYZ, Hà Nội', '0987654321', 'tran.van.n@example.com', N'Ghi chú 2'),
('KH003', N'Lê Thị O', 0, N'789 Đường DEF, Hà Nội', '0123456780', 'le.thi.o@example.com', N'Ghi chú 3'),
('KH004', N'Phạm Văn P', 1, N'101 Đường GHI, Hà Nội', '0909090909', 'pham.van.p@example.com', N'Ghi chú 4'),
('KH005', N'Hoàng Thị Q', 0, N'123 Đường KLM, Hà Nội', '0888888888', 'hoang.thi.q@example.com', N'Ghi chú 5'),
('KH006', N'Trịnh Văn R', 1, N'555 Đường NOP, Hà Nội', '0777777777', 'trinh.van.r@example.com', N'Ghi chú 6'),
('KH007', N'Đỗ Thị S', 0, N'666 Đường QRS, Hà Nội', '0666666666', 'do.thi.s@example.com', N'Ghi chú 7'),
('KH008', N'Mai Văn T', 1, N'888 Đường TUV, Hà Nội', '0555555555', 'mai.van.t@example.com', N'Ghi chú 8');
-- Bảng SanPham (sanPham_id, ten, moTa, trangThai, ngayTao)
go
INSERT INTO SanPham (sanPham_id, ten, moTa, trangThai) VALUES
('SP001', N'Air Jordan 1 Low', N'Mô tả sản phẩm 1', N'Đang bán'),
('SP002', N'Jordan One Take 4 PF', N'Mô tả sản phẩm 2', N'Đang bán'),
('SP003', N'Air Jordan Legacy 312 Low', N'Mô tả sản phẩm 3', N'Đang bán'),
('SP004', N'Air Jordan 1 Low SE', N'Mô tả sản phẩm 4', N'Đang bán'),
('SP005', N'Nike Air Max', N'Mô tả sản phẩm 5', N'Ngừng bán'),
('SP006', N'Nike Dunk Low 2', N'Mô tả sản phẩm 6', N'Đang bán'),
('SP007', N'Jordan Nu Retro 1 Low', N'Mô tả sản phẩm 7', N'Đang bán'),
('SP008', N'Nike Dunk Low', N'Mô tả sản phẩm 8', N'Đang bán');
go 
-- Bảng SanPhamChiTiet (sPCT_id, soLuong, donGia, size_id, chatLieu_id, nhaCC_id, anh, mauSac_id, sanPham_id)
INSERT INTO SanPhamChiTiet (sPCT_id, soLuong, donGia, size_id, chatLieu_id, nhaCC_id, anh, mauSac_id, sanPham_id, trangThai, moTa) VALUES
							('SPCT000001', 100, 250000, 1, 1, 1, N'duongdananh1.jpg', 1, 'SP001',N'Còn hàng',N'Mô tả'),
							('SPCT000002', 100, 250000, 2, 2, 1, N'duongdananh1.jpg', 2, 'SP001',N'Còn hàng',N'Mô tả'),
							('SPCT000003', 100, 250000, 3, 3, 1, N'duongdananh1.jpg', 3, 'SP001',N'Còn hàng',N'Mô tả'),
							('SPCT000004', 100, 250000, 4, 4, 1, N'duongdananh1.jpg', 4, 'SP001',N'Còn hàng',N'Mô tả'),
							('SPCT000005', 100, 250000, 5, 5, 1, N'duongdananh1.jpg', 5, 'SP001',N'Còn hàng',N'Mô tả'),


							('SPCT000006', 100, 300000, 1, 1, 1, N'duongdananh2.jpg', 1, 'SP002',N'Còn hàng',N'Mô tả'),
							('SPCT000007', 100, 300000, 2, 2, 1, N'duongdananh2.jpg', 2, 'SP002',N'Còn hàng',N'Mô tả'),
							('SPCT000008', 100, 300000, 3, 3, 1, N'duongdananh2.jpg', 3, 'SP002',N'Còn hàng',N'Mô tả'),
							('SPCT000009', 100, 300000, 4, 4, 1, N'duongdananh2.jpg', 4, 'SP002',N'Còn hàng',N'Mô tả'),
							('SPCT000010', 100, 300000, 5, 5, 1, N'duongdananh2.jpg', 5, 'SP002',N'Còn hàng',N'Mô tả'),

							('SPCT000011', 100, 200000, 1, 1, 1, N'duongdananh3.jpg', 1, 'SP003',N'Còn hàng',N'Mô tả'),
							('SPCT000012', 100, 200000, 2, 2, 1, N'duongdananh3.jpg', 2, 'SP003',N'Còn hàng',N'Mô tả'),
							('SPCT000013', 100, 200000, 3, 3, 1, N'duongdananh3.jpg', 3, 'SP003',N'Còn hàng',N'Mô tả'),
							('SPCT000014', 100, 200000, 4, 4, 1, N'duongdananh3.jpg', 4, 'SP003',N'Còn hàng',N'Mô tả'),


							('SPCT000015', 100, 350000, 1, 1, 1, N'duongdananh4.jpg', 1, 'SP004',N'Còn hàng',N'Mô tả'),
							('SPCT000016', 100, 350000, 2, 2, 1, N'duongdananh4.jpg', 2, 'SP004',N'Còn hàng',N'Mô tả'),
							('SPCT000017', 100, 350000, 3, 3, 1, N'duongdananh4.jpg', 3, 'SP004',N'Còn hàng',N'Mô tả'),
							('SPCT000018', 100, 350000, 4, 4, 1, N'duongdananh4.jpg', 4, 'SP004',N'Còn hàng',N'Mô tả'),


							('SPCT000019', 100, 180000, 1, 1, 1, N'duongdananh5.jpg', 1, 'SP005',N'Còn hàng',N'Mô tả'),
							('SPCT000020', 100, 180000, 2, 2, 1, N'duongdananh5.jpg', 2, 'SP005',N'Còn hàng',N'Mô tả'),
							('SPCT000021', 100, 180000, 3, 3, 1, N'duongdananh5.jpg', 3, 'SP005',N'Còn hàng',N'Mô tả'),
							('SPCT000022', 100, 180000, 4, 4, 1, N'duongdananh5.jpg', 4, 'SP005',N'Còn hàng',N'Mô tả'),

							('SPCT000023', 100, 320000, 1, 1, 1, N'duongdananh6.jpg', 1, 'SP006',N'Còn hàng',N'Mô tả'),
							('SPCT000024', 100, 320000, 2, 2, 1, N'duongdananh6.jpg', 2, 'SP006',N'Còn hàng',N'Mô tả'),
							('SPCT000025', 100, 320000, 3, 3, 1, N'duongdananh6.jpg', 3, 'SP006',N'Còn hàng',N'Mô tả'),


							('SPCT000026', 100, 270000, 1, 1, 1, N'duongdananh7.jpg', 1, 'SP007',N'Còn hàng',N'Mô tả'),
							('SPCT000027', 100, 270000, 2, 2, 1, N'duongdananh7.jpg', 2, 'SP007',N'Còn hàng',N'Mô tả'),
							('SPCT000028', 100, 270000, 3, 3, 1, N'duongdananh7.jpg', 3, 'SP007',N'Còn hàng',N'Mô tả'),

							('SPCT000029', 100, 290000, 1, 1, 1, N'duongdananh8.jpg', 1, 'SP008',N'Còn hàng',N'Mô tả'),
							('SPCT000030', 100, 290000, 2, 2, 1, N'duongdananh8.jpg', 2, 'SP008',N'Còn hàng',N'Mô tả'),
							('SPCT000031', 100, 290000, 3, 3, 1, N'duongdananh8.jpg', 3, 'SP008',N'Còn hàng',N'Mô tả');



-- Bảng hoaDon (hoaDon_id, ngayTao, nhanVien_id, tongTien, trangThai, khachHang_id, thanhToan_id)
go
INSERT INTO hoaDon (hoaDon_id, ngayTaoHD, nhanVien_id, tongTien, trangThai, khachHang_id, thanhToan_id, ghiChu,hanDoiTra) VALUES
('HD000001', '2023-9-01 10:30:00', 'NV001', 800000.0, N'Hoàn thành', 'KH001', 1,N'Ghi chú HD1','2023-9-08 10:30:00'),
('HD000002', '2023-10-02 11:15:00', 'NV002', 1300000.0, N'Hoàn thành', 'KH002', 1,N'Ghi chú HD1','2023-10-9 10:30:00'),
('HD000003', '2023-11-03 12:20:00', 'NV003', 820000.0, N'Hoàn thành', 'KH000', 2,N'Ghi chú HD1','2023-11-10 10:30:00'),
('HD000004', '2023-11-04 14:00:00', 'NV004', 1080000.0, N'Hoàn thành', 'KH001', 2,N'Ghi chú HD1','2023-11-11 10:30:00');

-- Bảng hoaDonChiTiet (hDCT_id, hoaDon_id, sanPham_id, soLuong, giaBan, thanhTien)
go
INSERT INTO hoaDonChiTiet (hDCT_id, hoaDon_id, sPCT_id, soLuong, giaBan, thanhTien, trangThaiHDCT) VALUES
('HDCT000001', 'HD000001', 'SPCT000001', 2, 250000.0, 500000.0, N'Hoàn thành'),
('HDCT000002', 'HD000001', 'SPCT000006', 1, 300000.0, 300000.0, N'Hoàn thành'),
('HDCT000003', 'HD000002', 'SPCT000011', 3, 200000.0, 600000.0, N'Hoàn thành'),
('HDCT000004', 'HD000002', 'SPCT000015', 2, 350000.0, 700000.0, N'Hoàn thành'),
('HDCT000005', 'HD000003', 'SPCT000019', 1, 180000.0, 180000.0, N'Hoàn thành'),
('HDCT000006', 'HD000003', 'SPCT000023', 2, 320000.0, 640000.0, N'Hoàn thành'),
('HDCT000007', 'HD000004', 'SPCT000026', 4, 270000.0, 1080000.0, N'Hoàn thành'),
('HDCT000008', 'HD000004', 'SPCT000029', 3, 290000.0, 870000.0, N'Xoá');

go
select * from size
select * from mauSac
select * from chatLieu
select * from nhaCungCap
select * from nhanVien
select * from khachHang
select * from hoaDon
select * from hoaDonChiTiet order by hoaDon_id 
select * from sanPham
select * from sanPhamChiTiet
select * from thanhToan
select * from traHang


go
--PROC 
--  stored procedure để thêm sản phẩm vào hóa đơn chi tiết
CREATE OR ALTER PROCEDURE ThemSanPhamVaoHoaDonChiTiet
    @MaHoaDonChiTiet VARCHAR(10),
    @MaHoaDon VARCHAR(10),
    @MaSanPhamChiTiet NVARCHAR(50),
    @SoLuong INT
    --@ThanhTien Decimal(18,2) = NULL OUTPUT
AS
BEGIN
	Declare  @GiaBan decimal(18,2);
	Declare @ThanhTien Decimal(18,2);
	Declare @TrangThaiHDCT nvarchar(100);
	set @GiaBan = (select donGia  from sanPhamChiTiet where sPCT_id like @MaSanPhamChiTiet)
    set @TrangThaiHDCT = N'Chờ thanh toán'
   --Tính thành tiền = số lượng x giá bán
        SET @ThanhTien = @SoLuong * @GiaBan;
   
    -- Thêm sản phẩm vào hóa đơn chi tiết
		 IF EXISTS (SELECT 1 FROM HoaDonChiTiet WHERE hoaDon_id = @MaHoaDon AND sPCT_id = @MaSanPhamChiTiet and trangThaiHDCT like N'Chờ thanh toán')
    BEGIN
        -- Nếu sản phẩm đã tồn tại trong hoá đơn chi tiết thì thực hiện update lại số lượng sản phẩm
        UPDATE HoaDonChiTiet
        SET SoLuong = SoLuong + @SoLuong,
            giaBan = @GiaBan,
            thanhTien = thanhTien + @ThanhTien
        WHERE hoaDon_id = @MaHoaDon AND sPCT_id = @MaSanPhamChiTiet;
    END
    ELSE
    BEGIN
        -- Nếu sẩn phẩm chưa tồn tại thì thực hiện thêm 1 bản ghi mới vào hoá đơn chi tiết
       INSERT INTO HoaDonChiTiet (hDCT_id, hoaDon_id, sPCT_id, SoLuong, giaBan, thanhTien, trangThaiHDCT)
			VALUES (@MaHoaDonChiTiet, @MaHoaDon, @MaSanPhamChiTiet, @SoLuong, @GiaBan, @ThanhTien, @TrangThaiHDCT);
    END;
	UPDATE hoaDon SET 
	tongTien = tongTien+ @ThanhTien WHERE hoaDon_id = @MaHoaDon

    -- Cập nhật số lượng sản phẩm trong bảng SanPhamChiTiet
    UPDATE sanPhamChiTiet
        SET SoLuong = SoLuong - @SoLuong,
            trangThai = CASE WHEN SoLuong - @SoLuong = 0 THEN N'Hết hàng' ELSE trangThai END
        WHERE sPCT_id = @MaSanPhamChiTiet;
END;


go
--  stored procedure để xoá sản phẩm khỏi hóa đơn chi tiết

CREATE or alter PROC  duaHDCTVeTrangThaiHuy
 -- khai báo các biến đầu vào để xác định chính xác hoá đơn chi tiết cần đưa về trạng thái huỷ
    @MaHDCT NVARCHAR(10),
	@MaHoaDon NVARCHAR(10),
	@MaSanPhamChiTiet NVARCHAR(10)
	
AS
BEGIN
    -- Lấy thông tin số lượng sản phẩm trong hóa đơn chi tiết
    DECLARE @SoLuongXoa INT;
    SELECT @SoLuongXoa = SoLuong
    FROM HoaDonChiTiet
    WHERE sPCT_id = @MaSanPhamChiTiet and hDCT_id = @MaHDCT;

    -- Xoá sản phẩm khỏi hóa đơn chi tiết
    Update  HoaDonChiTiet set trangThaiHDCT = N'Xoá'
    WHERE sPCT_id = @MaSanPhamChiTiet and hoaDon_id = @MaHoaDon and hDCT_id = @MaHDCT;


	declare @tongTien decimal(18,2);
	set @tongTien = (select SUM(giaBan*soLuong) from hoaDonChiTiet where trangThaiHDCT like N'Chờ thanh toán' and hoaDon_id = @MaHoaDon)
	Update  hoaDon set tongTien =  @tongTien
    WHERE hoaDon_id = @MaHoaDon;

    -- Cập nhật số lượng sản phẩm trong bảng SanPham
   
	 UPDATE sanPhamChiTiet
        SET SoLuong = SoLuong + @SoLuongXoa,
            trangThai = CASE WHEN SoLuong + @SoLuongXoa > 0 THEN N'Còn hàng' ELSE N'Hết hàng' END
        WHERE sPCT_id = @MaSanPhamChiTiet;
END;
GO
CREATE OR ALTER PROC HUYHOADON
@MAHOADON VARCHAR(10),
@MAHDCT VARCHAR(10),
@MASPCT VARCHAR(10),
@ghiChu NVARCHAR(max)
AS
BEGIN
	DECLARE @SOLUONG INT;
	SET @SOLUONG = (SELECT SOLUONG FROM hoaDonChiTiet WHERE hoaDon_id = @MAHOADON and @MAHDCT = hDCT_id and sPCT_id = @MASPCT and trangThaiHDCT = N'Chờ thanh toán')
	UPDATE  sanPhamChiTiet set soLuong = soLuong + @SOLUONG,
            trangThai = CASE WHEN SoLuong + @SOLUONG > 0 THEN N'Còn hàng' ELSE trangThai END
        WHERE sPCT_id = @MASPCT;

	UPDATE hoaDon SET trangThai = N'Đã huỷ', ghiChu = @ghiChu where hoaDon_id = @MAHOADON;

	update hoaDonChiTiet set trangThaiHDCT = N'Đã huỷ' where hDCT_id = @MAHDCT;
END;

GO
CREATE OR ALTER PROC CHINHSUASOLUONGSP
--Khai báo các biến truyền từ ngoài vào
@MAHOADON VARCHAR(10),
@MAHDCT VARCHAR(10),
@MASPCT VARCHAR(10),
@SOLUONGSPTHAYDOI INT,
@SOLUONGSPTRONGGIO INT -- lấy từ bảng 
AS
BEGIN
--Khai báo 3 biến cục bộ
	DECLARE @SOLUONGSP INT;
	DECLARE @DONGIA DECIMAL(18,2);
	DECLARE @THANHTIEN DECIMAL(18,2);

	-- set đơn giá mặc định được lấy từ sản phẩm theo mã sản phẩm chi tiết
	SET @DONGIA = (SELECT donGia FROM sanPhamChiTiet WHERE sPCT_id = @MASPCT)
	-- set số lượng được lấy từ sản phẩm chi tiết theo  mã sản phẩm chi tiết
	SET @SOLUONGSP = (SELECT soLuong FROM sanPhamChiTiet WHERE sPCT_id = @MASPCT)

	-- thực hiện update lại số lượng sản phẩm chi tiết = (số lượng spct + số sản phẩm đã thêm vào giỏ hàng) - số sản phẩm cần thay đổi đã nhập vào;;  có thể bỏ cập nhật trạng thái
	UPDATE sanPhamChiTiet SET soLuong = (@SOLUONGSP + @SOLUONGSPTRONGGIO) - @SOLUONGSPTHAYDOI,
    trangThai = CASE
                    WHEN ((@SOLUONGSP + @SOLUONGSPTRONGGIO) - @SOLUONGSPTHAYDOI) > 0 THEN N'Còn hàng'
                    ELSE N'Hết hàng' 
                END
	WHERE sPCT_id = @MASPCT
	SET @THANHTIEN = @DONGIA * @SOLUONGSPTHAYDOI

	--- update lại số lượng sản phẩm trong hoá đơn
	UPDATE hoaDonChiTiet set soLuong = @SOLUONGSPTHAYDOI, thanhTien = @THANHTIEN  WHERE hoaDon_id = @MAHOADON AND hDCT_id = @MAHDCT AND sPCT_id = @MASPCT

	declare @tongTien decimal(18,2);
	set @tongTien = (select SUM(giaBan*soLuong) from hoaDonChiTiet where trangThaiHDCT like N'Chờ thanh toán' and hoaDon_id = @MaHoaDon)
	Update  hoaDon set tongTien =  @tongTien
    WHERE hoaDon_id = @MaHoaDon;
END;

go
CREATE OR ALTER PROC THANHTOANHOADON
@MAHOADON VARCHAR(10),
@TONGTIEN DECIMAL(18,2),
@MATHANHTOAN INT,
@MAKH VARCHAR(7),
@GHICHU NVARCHAR (MAX)
AS
BEGIN
	UPDATE hoaDon SET
	tongTien = @TONGTIEN,
	trangThai = N'Hoàn thành', 
	thanhToan_id = @MATHANHTOAN,
	khachHang_id = @MAKH,
	hanDoiTra = DATEADD(DAY, 2, GETDATE()),
	ghiChu = @GHICHU
	where hoaDon_id = @MAHOADON

	UPDATE hoaDonChiTiet SET
	trangThaiHDCT = N'Hoàn thành'
	where trangThaiHDCT = N'Chờ thanh toán' and hoaDon_id = @MAHOADON
END
GO



CREATE OR ALTER PROC procTraHang
@MAHOADON VARCHAR(10),
@MASPCHITIET VARCHAR(10),
@MAHOADONCHITIET VARCHAR(10),
@NHANVIEN_ID VARCHAR(7),
@SOLUONGTRA INT,
@LYDOTRA NVARCHAR(max),
@TONGTIENHOADON DECIMAL(18,2)
AS
BEGIN
	Declare @GIATRA decimal(18,2), @TONGTIENHOANTRA DECIMAL(18,2);
	SET @GIATRA = (SELECT giaBan FROM hoaDonChiTiet WHERE hDCT_id = @MAHOADONCHITIET)
	SET @TONGTIENHOANTRA = @GIATRA * @SOLUONGTRA
	INSERT INTO traHang(hoaDon_id, sPCT_id, nhanVien_id, soLuongTra, giaTra, tongTienHoanTra, lyDoTraHang, trangThai) 
	VALUES(@MAHOADON, @MASPCHITIET, @NHANVIEN_ID, @SOLUONGTRA, @GIATRA, @TONGTIENHOANTRA, @LYDOTRA, N'Trả thành công')

	UPDATE hoaDonChiTiet
	SET soLuong = soLuong - @SOLUONGTRA where hDCT_id = @MAHOADONCHITIET

	UPDATE hoaDonChiTiet
	SET thanhTien = soLuong * giaBan
	where hDCT_id = @MAHOADONCHITIET

	IF (SELECT soLuong  FROM hoaDonChiTiet WHERE hDCT_id = @MAHOADONCHITIET )= 0
	BEGIN
		UPDATE hoaDonChiTiet
	SET trangThaiHDCT = N'Xoá'
		where hDCT_id = @MAHOADONCHITIET
	END

	UPDATE hoaDon
	set trangThai = N'Trả hàng', tongTien = @TONGTIENHOADON where hoaDon_id = @MAHOADON

	Update sanPhamChiTiet
	set soLuong = soLuong + @SOLUONGTRA, trangThai =  N'Còn hàng' where sPCT_id = @MASPCHITIET
END
