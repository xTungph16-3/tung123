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
public class HoaDonDAO extends QLCHBG_DAO<HoaDon, String> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
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
                FROM [dbo].[hoaDon]""";
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
              Select hoaDon.hoaDon_id,
                            nhanVien.nhanVien_id, 
                            nhanVien.hoTen, 
                            khachHang.khachHang_id, 
                            khachHang.hoTenKH, 
                            tongTien,
                            thanhToan.hinhThucThanhToan,
                            hoaDon.ngayTaoHD,
                            hoaDon.trangThai, 
                            hoaDon.ghiChu, 
                            from hoaDon
                            join nhanVien on nhanVien.nhanVien_id = hoaDon.nhanVien_id
                            join khachHang on khachHang.khachHang_id = hoaDon.khachHang_id
                            join thanhToan on thanhToan.thanhToan_id = hoaDon.thanhToan_id order by ngayTaoHD
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
                        rs.getString(8),
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
                       Select hoaDon.hoaDon_id,
                                                    nhanVien.nhanVien_id, 
                                                    nhanVien.hoTen, 
                                                    khachHang.khachHang_id, 
                                                    khachHang.hoTenKH, 
                                                    tongTien,
                                                    thanhToan.hinhThucThanhToan,
                                                    hoaDon.ngayTaoHD,
                                                    hoaDon.trangThai,
                                                    hoaDon.ghiChu
                                                    from hoaDon
                                                    join nhanVien on nhanVien.nhanVien_id = hoaDon.nhanVien_id
                                                    join khachHang on khachHang.khachHang_id = hoaDon.khachHang_id
                                                    join thanhToan on thanhToan.thanhToan_id = hoaDon.thanhToan_id
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
                        rs.getString(8),
                        rs.getString(9),
                rs.getString(10));
                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }
    
    public List<HoaDon> timKiemPhanTrangHoaDon( String maHoaDon) {
        ArrayList<HoaDon> lst = new ArrayList<>();
        try {
             sql = """
                       Select hoaDon.hoaDon_id,
                                                                                                       nhanVien.nhanVien_id, 
                                                                                                       nhanVien.hoTen, 
                                                                                                       khachHang.khachHang_id, 
                                                                                                       khachHang.hoTenKH, 
                                                                                                       tongTien,
                                                                                                       thanhToan.hinhThucThanhToan,
                                                                                                       hoaDon.ngayTaoHD,
                                                                                                       hoaDon.trangThai,
                                                                                                       hoaDon.ghiChu
                                                                                                       from hoaDon 
                                                                                                       join nhanVien on nhanVien.nhanVien_id = hoaDon.nhanVien_id
                                                                                                       join khachHang on khachHang.khachHang_id = hoaDon.khachHang_id
                                                                                                       join thanhToan on thanhToan.thanhToan_id = hoaDon.thanhToan_id
                                                   													where hoadon.hoaDon_id = ?
                                                   
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
                        rs.getString(8),
                        rs.getString(9),
                rs.getString(10));
                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }
    
     public List<HoaDon> locTrangThaiPhanTrangHoaDon( String trangThai) {
        ArrayList<HoaDon> lst = new ArrayList<>();
        try {
             sql = """
                       Select hoaDon.hoaDon_id,
                                                                                                       nhanVien.nhanVien_id, 
                                                                                                       nhanVien.hoTen, 
                                                                                                       khachHang.khachHang_id, 
                                                                                                       khachHang.hoTenKH, 
                                                                                                       tongTien,
                                                                                                       thanhToan.hinhThucThanhToan,
                                                                                                       hoaDon.ngayTaoHD,
                                                                                                       hoaDon.trangThai,
                                                                                                       hoaDon.ghiChu
                                                                                                       from hoaDon 
                                                                                                       join nhanVien on nhanVien.nhanVien_id = hoaDon.nhanVien_id
                                                                                                       join khachHang on khachHang.khachHang_id = hoaDon.khachHang_id
                                                                                                       join thanhToan on thanhToan.thanhToan_id = hoaDon.thanhToan_id
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
                        rs.getString(8),
                        rs.getString(9),
                rs.getString(10));
                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }


    @Override
    public int insert(HoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(String key, HoaDon entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
