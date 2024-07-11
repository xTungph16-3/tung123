/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.HoaDon;
import entity.SanPhamChiTiet;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DB_Connect;

/**
 *
 * @author Trong Phu
 */
public class ThongKeDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    // hàm này có chức năng trả về số lượng sản phẩm đang bán
    public int soLuongSPDangBan() {
        
        int i = 0;
        try {
            String sql = "SELECT COUNT(*) AS soLuongSanPhamDangBan FROM sanPham where trangThai like N'Đang bán';";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                i = rs.getInt("soLuongSanPhamDangBan");
            }
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }
    // hàm này có chức năng trả về số lượng sản phẩm ngừng bán
    public int soLuongSPNgungBan() {
        int i = 0;
        try {
            String sql = "SELECT COUNT(*) AS soLuongSanPhamNgungBan FROM sanPham where trangThai like N'Ngừng bán';";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                i = rs.getInt("soLuongSanPhamNgungBan");
            }
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }
    // hàm này trả về tổng sản phẩm chi tiết
    public int soLuongSPTrongKho() {
        int i = 0;
        try {
            String sql = "select sum(soLuong) as tongHangTrongKho from sanPhamChiTiet ;";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                i = rs.getInt("tongHangTrongKho");
            }
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }
    
    
    /// hàmnày trả về 1 danh sách để fill lên bảng thống kê sản phẩm chi tiết
    public List<SanPhamChiTiet> bangThongKeSPCT() {
        sql = """
             SELECT  sanPhamChiTiet.[sPCT_id]
                                                                          	,sanPham.sanPham_id
                                                                          	,sanPham.[ten] [tenSanPham]
                                                                          	,sanPhamChiTiet.trangThai
                                                                          	,sanPhamChiTiet.moTa
                                                                                ,sanPhamChiTiet.[soLuong]
                                                                                ,size.[giaTri] [size]
                                                                                ,chatLieu.[ten] [tenChatlieu]
                                                                                ,nhaCungCap.[ten] [tenNhaCC]
                                                                                ,sanPhamChiTiet.anh
                                                                                ,mauSac.[tenMau] [tenMau]
                                                          					  ,[donGia]
                                                          					  ,SUM(hoaDonChiTiet.soLuong) as [soLuongBan]
                                                          					  ,SUM(hoaDonChiTiet.thanhTien) as doanhThu
                                                                            FROM [quanLyCuaHangGiay].[dbo].[sanPhamChiTiet] 
                                                                            join sanPham on sanPham.sanPham_id = sanPhamChiTiet.sanPham_id
                                                                            join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                                                                            join size on size.size_id = sanPhamChiTiet.size_id
                                                                            join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                                                                            join nhaCungCap on nhaCungCap.nhaCC_id = sanPhamChiTiet.nhaCC_id
                                                          				  join hoaDonChiTiet on hoaDonChiTiet.sPCT_id = sanPhamChiTiet.sPCT_id where trangThaiHDCT = N'Hoàn thành'
                                                          				  group by sanPhamChiTiet.[sPCT_id]
                                                                          	,sanPham.sanPham_id
                                                                          	,sanPham.[ten]
                                                                          	,sanPhamChiTiet.trangThai
                                                                          	,sanPhamChiTiet.moTa
                                                                                ,sanPhamChiTiet.[soLuong]
                                                                                ,[donGia]
                                                                                ,size.[giaTri] 
                                                                                ,chatLieu.[ten] 
                                                                                ,nhaCungCap.[ten] 
                                                                                ,sanPhamChiTiet.anh
                                                                                ,mauSac.[tenMau]
              """;
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet(
                        rs.getString("sPCT_id"),
                        rs.getInt("soLuong"),
                        rs.getString("anh"),
                        rs.getString("sanPham_id"),
                        rs.getString("trangThai"),
                        rs.getString("moTa"),
                        rs.getInt("size"),
                        rs.getString("tenChatLieu"),
                        rs.getString("tenNhaCC"),
                        rs.getString("tenMau"),
                        rs.getString("tenSanPham"),
                        rs.getBigDecimal("doanhThu"),
                        rs.getInt("soLuongBan"));
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /// hàm này trả về doanh thu của hoá đơn ở trạng thái hoàn thành ngày hôm nay
    public BigDecimal doanhThuNgay(){
        BigDecimal tongDoanhThuNgay = BigDecimal.valueOf(2) ;
        sql ="select SUM(tongTien) as [tongDoanhThuNgay] from hoaDon where (trangThai = N'Hoàn thành' or trangThai = N'Trả hàng') and CAST(ngayTaoHD AS DATE) = CAST(GETDATE() AS DATE);";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                tongDoanhThuNgay = rs.getBigDecimal("tongDoanhThuNgay");
            }
            return tongDoanhThuNgay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //// hàm này trả về doanh thu của tháng
    public BigDecimal doanhThuThang(){
        BigDecimal tongDoanhThuThang = BigDecimal.valueOf(2) ;
        sql ="SELECT SUM(tongTien) as [tongDoanhThuThang] FROM hoaDon WHERE (trangThai = N'Hoàn thành' or trangThai = N'Trả hàng') AND ngayTaoHD >= DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0) AND ngayTaoHD < DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) + 1, 0);";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                tongDoanhThuThang = rs.getBigDecimal("tongDoanhThuThang");
            }
            return tongDoanhThuThang;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /// hàm này trả về  tổng doanh thu của cửa hàng
    public BigDecimal tongDoanhThu(){
        BigDecimal tongDoanhThu = BigDecimal.valueOf(2) ;
        sql ="select SUM(tongTien) as [tongDoanhThu] from hoaDon where (trangThai = N'Hoàn thành' or trangThai = N'Trả hàng') ";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                tongDoanhThu = rs.getBigDecimal("tongDoanhThu");
            }
            return tongDoanhThu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /// hàm này trả về 1 danh sách doanh thu từng ngày
    public List<HoaDon> danhSachDoanhThuNgay(){
        sql = """
              SELECT 
                  CONVERT(DATE, ngayTaoHD) AS Ngay,
                  SUM(tongTien) AS TongDoanhThu,
              COUNT(*) as [tongSoHoaDon]
              FROM 
                  hoaDon where trangThai = N'Hoàn thành' or trangThai = N'Trả hàng'
              GROUP BY 
                  CONVERT(DATE, ngayTaoHD);""";
        List<HoaDon> lst = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                HoaDon hd = new HoaDon();
                hd.setNgayTao(rs.getString("Ngay"));
                hd.setTongTien(rs.getBigDecimal("TongDoanhThu"));
                hd.setTongSoHD(rs.getInt("tongSoHoaDon"));
                lst.add(hd);
            }
            return lst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } 
    
    
   public List<Object[]> danhSachDoanhThuNam() {
    sql = """
          SELECT
              YEAR(ngayTaoHD) AS Nam,
              SUM(tongTien) AS DoanhThu,
              MAX(MonthlyRevenue.MonthlyRevenue) AS ThangCaoNhat,
              MIN(MonthlyRevenue.MonthlyRevenue) AS ThangThapNhat,
              AVG(MonthlyRevenue.MonthlyRevenue) AS DoanhThuTrungBinh
          FROM
              hoaDon 
              INNER JOIN (
                  SELECT
                      YEAR(ngayTaoHD) AS Nam,
                      MONTH(ngayTaoHD) AS Thang,
                      SUM(tongTien) AS MonthlyRevenue
                  FROM
                      hoaDon
                  WHERE
                      trangThai = N'Hoàn thành' or hoaDon.trangThai = N'Trả hàng'
                  GROUP BY
                      YEAR(ngayTaoHD),
                      MONTH(ngayTaoHD)
              ) AS MonthlyRevenue ON YEAR(hoaDon.ngayTaoHD) = MonthlyRevenue.Nam AND MONTH(hoaDon.ngayTaoHD) = MonthlyRevenue.Thang
          	
          WHERE
              hoaDon.trangThai = N'Hoàn thành' or hoaDon.trangThai = N'Trả hàng'
          GROUP BY
              YEAR(ngayTaoHD)
          ORDER BY
              YEAR(ngayTaoHD);
         """;
    List<Object[]> resultList = new ArrayList<>();
    try {
        con = DB_Connect.getConnection();
        ps = con.prepareCall(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = new Object[5];
            row[0] = rs.getInt("Nam");
            row[1] = rs.getBigDecimal("DoanhThu");
            row[2] = rs.getBigDecimal("ThangCaoNhat");
            row[3] = rs.getBigDecimal("ThangThapNhat");
            row[4] = rs.getBigDecimal("DoanhThuTrungBinh");
            resultList.add(row);
        }
        return resultList;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        
    }
}
   
   public List<Object[]> danhSachDoanhThuThang(String nam) {
    sql = """
           SELECT
                            YEAR(ngayTaoHD) AS Nam,
                            MONTH(ngayTaoHD) AS Thang,
                            SUM(tongTien) AS DoanhThu
                        FROM
                            hoaDon
                        WHERE
                            (trangThai = N'Hoàn thành' or hoaDon.trangThai = N'Trả hàng') and YEAR(ngayTaoHD)   = ?
                        GROUP BY
                            YEAR(ngayTaoHD),
                            MONTH(ngayTaoHD)
                        ORDER BY
                            Nam, Thang;
         """;
    List<Object[]> resultList = new ArrayList<>();
    try {
        con = DB_Connect.getConnection();
        ps = con.prepareStatement(sql);
        ps.setObject(1, nam);
        rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = new Object[3];
            row[0] = rs.getInt("Nam");
            row[1] = rs.getInt("Thang");
            row[2] = rs.getBigDecimal("DoanhThu");
            resultList.add(row);
        }
        return resultList;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        // Close resources in the finally block
//        try {
//            if (rs != null) rs.close();
//            if (ps != null) ps.close();
//            if (con != null) con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}



    
}
