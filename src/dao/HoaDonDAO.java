/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.HoaDon;
import java.math.BigDecimal;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DB_Connect;

/**
 *
 * @author Trong Phu
 */
public class HoaDonDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<HoaDon> selectAll() {
        List<HoaDon> list1 = new ArrayList<>();
        sql = """
              SELECT [hoaDon_id]
                    ,[ngayTaoHD]
                    ,[nhanVien_id]
                    ,[tongTien]
                    ,[trangThai]
                    ,[khachHang_id]
                    ,[thanhToan_id]
                    ,[hanDoiTra]
                FROM [dbo].[hoaDon]
              """;
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getString("hoaDon_id"),
                        rs.getString("nhanVien_id"),
                        rs.getString("khachHang_id"),
                        rs.getBigDecimal("tongTien"),
                        rs.getInt("thanhToan_id"),
                        rs.getString("trangThai"));
                list1.add(hd);
            }
            return list1;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public List<HoaDon> selectAll2() {
        List<HoaDon> list1 = new ArrayList<>();
        sql = """
                SELECT dbo.hoaDon.hoaDon_id, 
                        dbo.nhanVien.nhanVien_id, 
                        dbo.nhanVien.hoTen, 
                        dbo.khachHang.khachHang_id, 
                        dbo.khachHang.hoTenKH, 
                        dbo.hoaDon.tongTien, 
                        dbo.thanhToan.hinhThucThanhToan, 
                        dbo.hoaDon.ngayTaoHD, 
                        dbo.hoaDon.trangThai, 
                        dbo.hoaDon.ghiChu
                FROM     dbo.hoaDon 
                INNER JOIN dbo.khachHang ON dbo.hoaDon.khachHang_id = dbo.khachHang.khachHang_id 
                INNER JOIN dbo.nhanVien ON dbo.hoaDon.nhanVien_id = dbo.nhanVien.nhanVien_id 
                LEFT JOIN dbo.thanhToan ON dbo.hoaDon.thanhToan_id = dbo.thanhToan.thanhToan_id 
                order by ngayTaoHD
              """;
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBigDecimal(6),
                        rs.getString(7),
                        rs.getDate(8),
                        rs.getString(9),
                        rs.getString(10)
                );
                list1.add(hd);
            }
            return list1;
        } catch (Exception e) {
            e.fillInStackTrace();
            return null;
        }

    }

    public List<HoaDon> phanTrangHoaDon(int tienLui) {
        ArrayList<HoaDon> lst = new ArrayList<>();
        try {
            sql = """
                        SELECT dbo.hoaDon.hoaDon_id, 
                                dbo.nhanVien.nhanVien_id, 
                                dbo.nhanVien.hoTen, 
                                dbo.khachHang.khachHang_id, 
                                dbo.khachHang.hoTenKH, 
                                dbo.hoaDon.tongTien, 
                                dbo.thanhToan.hinhThucThanhToan, 
                                dbo.hoaDon.ngayTaoHD, 
                                dbo.hoaDon.trangThai, 
                                dbo.hoaDon.ghiChu
                        FROM     dbo.hoaDon 
                        INNER JOIN dbo.khachHang ON dbo.hoaDon.khachHang_id = dbo.khachHang.khachHang_id 
                        INNER JOIN dbo.nhanVien ON dbo.hoaDon.nhanVien_id = dbo.nhanVien.nhanVien_id 
                        LEFT JOIN dbo.thanhToan ON dbo.hoaDon.thanhToan_id = dbo.thanhToan.thanhToan_id
                        order by hoaDon.ngayTaoHD desc
                        OFFSET ? ROWS
                        FETCH NEXT 5 ROWS ONLY;
                         """;
            con = DB_Connect.getConnection();
            
            ps = con.prepareStatement(sql);
            ps.setObject(1, tienLui);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBigDecimal(6),
                        rs.getString(7),
                        rs.getDate(8),
                        rs.getString(9),
                        rs.getString(10));
                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("HoaDon List Size: " + lst.size()); // Add this line for debugging
        }
        return lst;
    }

    public List<HoaDon> timKiemPhanTrangHoaDon(String maHoaDon) {
        ArrayList<HoaDon> lst = new ArrayList<>();
        try {
            sql = """
                        SELECT dbo.hoaDon.hoaDon_id, 
                                dbo.nhanVien.nhanVien_id, 
                                dbo.nhanVien.hoTen, 
                                dbo.khachHang.khachHang_id, 
                                dbo.khachHang.hoTenKH, 
                                dbo.hoaDon.tongTien, 
                                dbo.thanhToan.hinhThucThanhToan, 
                                dbo.hoaDon.ngayTaoHD, 
                                dbo.hoaDon.trangThai, 
                                dbo.hoaDon.ghiChu
                        FROM     dbo.hoaDon 
                        INNER JOIN dbo.khachHang ON dbo.hoaDon.khachHang_id = dbo.khachHang.khachHang_id 
                        INNER JOIN dbo.nhanVien ON dbo.hoaDon.nhanVien_id = dbo.nhanVien.nhanVien_id 
                        LEFT JOIN dbo.thanhToan ON dbo.hoaDon.thanhToan_id = dbo.thanhToan.thanhToan_id
                        WHERE hoadon.hoaDon_id = ?
                        order by hoaDon.ngayTaoHD desc
                        OFFSET 0 ROWS
                        FETCH NEXT 5 ROWS ONLY;
                         """;
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, maHoaDon);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBigDecimal(6),
                        rs.getString(7),
                        rs.getDate(8),
                        rs.getString(9),
                        rs.getString(10));
                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public List<HoaDon> locTrangThaiPhanTrangHoaDon(String trangThai) {
        ArrayList<HoaDon> lst = new ArrayList<>();
        try {
            sql = """
                        SELECT dbo.hoaDon.hoaDon_id, 
                                dbo.nhanVien.nhanVien_id, 
                                dbo.nhanVien.hoTen, 
                                dbo.khachHang.khachHang_id, 
                                dbo.khachHang.hoTenKH, 
                                dbo.hoaDon.tongTien, 
                                dbo.thanhToan.hinhThucThanhToan, 
                                dbo.hoaDon.ngayTaoHD, 
                                dbo.hoaDon.trangThai, 
                                dbo.hoaDon.ghiChu
                        FROM     dbo.hoaDon 
                        INNER JOIN dbo.khachHang ON dbo.hoaDon.khachHang_id = dbo.khachHang.khachHang_id 
                        INNER JOIN dbo.nhanVien ON dbo.hoaDon.nhanVien_id = dbo.nhanVien.nhanVien_id 
                        LEFT JOIN dbo.thanhToan ON dbo.hoaDon.thanhToan_id = dbo.thanhToan.thanhToan_id
                        where hoadon.trangThai = ?
                        order by hoaDon.ngayTaoHD desc                                               
                         """;
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, trangThai);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBigDecimal(6),
                        rs.getString(7),
                        rs.getDate(8),
                        rs.getString(9),
                        rs.getString(10));
                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

}
