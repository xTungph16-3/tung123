/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.KhachHang;
import entity.NhanVien;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.DB_Connect;

/**
 *
 * @author Trong Phu
 */
public class KhachHangDAO extends QLCHBG_DAO<KhachHang, String> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public List<KhachHang> selectAll() {
        sql = """
             SELECT 
                        [khachHang].[khachHang_id],
                        [hoTenKH],
                        [gioiTinh],
                        [diaChi],
                        [sdt],
                        [email],
                        [khachHang].[ghiChu],
                        COUNT(CASE WHEN hoaDon.trangThai = N'Hoàn thành' THEN 1 END) AS soLanMua,
                        [khachHang].[ngayTaoKH]
                    FROM 
                        [dbo].[khachHang]
                    LEFT JOIN 
                        hoaDon ON hoaDon.khachHang_id = khachHang.khachHang_id
              
                    GROUP BY 
                        [khachHang].[khachHang_id],
                        [hoTenKH],
                        [gioiTinh],
                        [diaChi],
                        [sdt],
                        [email],
                         [khachHang].[ghiChu],
                        [khachHang].[ngayTaoKH];
         """;
        List<KhachHang> listKH = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                listKH.add(new KhachHang(rs.getString("khachHang_id"),
                        rs.getString("hoTenKH"),
                        rs.getBoolean("gioiTinh"),
                        rs.getString("diaChi"),
                        rs.getString("sdt"),
                        rs.getString("email"),
                        rs.getString("ghiChu"),
                        rs.getDate("ngayTaoKH"),
                        rs.getInt("soLanMua")));

            }

            return listKH;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int insert(KhachHang entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(String key, KhachHang entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Integer addNH(KhachHang n) {
        Integer row = null;
        String sql = """
                     INSERT INTO [dbo].[khachHang]
                                ([khachHang_id]
                                ,[hoTenKH]
                                ,[gioiTinh]
                                ,[diaChi]
                                ,[sdt]
                                ,[email]
                                ,[ghiChu])
                          VALUES(?,?,?,?,?,?,?)
                                """;

        try {
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, n.getIdKH());
            pstm.setObject(2, n.getHoTen());
            pstm.setObject(3, n.isGioiTinh());
            pstm.setObject(4, n.getDiaChi());
            pstm.setObject(5, n.getSdt());
            pstm.setObject(6, n.getEmail());
            pstm.setObject(7, n.getGhiChu());

            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return row;
    }

    public String update(KhachHang nv, String id) {
        Connection conn = DB_Connect.getConnection();
        String update = """
                        UPDATE [dbo].[khachHang]
                            SET [khachHang_id] = ?
                               ,[hoTenKH] = ?
                               ,[gioiTinh] = ?
                               ,[diaChi] = ?
                               ,[sdt] = ?
                               ,[email] = ?
                          WHERE khachHang_id like ?""";
        try {
            PreparedStatement ps = conn.prepareStatement(update);

            // ps.setObject(1, nv.getNhanVien_id());
            ps.setObject(1, nv.getIdKH());
            ps.setObject(2, nv.getHoTen());
            ps.setObject(3, nv.isGioiTinh());
            ps.setObject(4, nv.getDiaChi());
            ps.setObject(5, nv.getSdt());
            ps.setObject(6, nv.getEmail());

            ps.setObject(7, id);
            ps.executeUpdate();
            return "Sửa thành công";
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Sủa không thành công";
        }
    }

    public List<KhachHang> timkiem(String id) {
        List<KhachHang> listNV = new ArrayList<>();

        try {
            con = DB_Connect.getConnection();

            String query = """
                            SELECT 
                                                                                            [khachHang].[khachHang_id],
                                                                                            [hoTenKH],
                                                                                            [gioiTinh],
                                                                                            [diaChi],
                                                                                            [sdt],
                                                                                            [email],
                                                                                            [khachHang].[ghiChu],
                                                                                            COUNT(CASE WHEN hoaDon.trangThai = N'Hoàn thành' THEN 1 END) AS soLanMua,
                                                                                            [khachHang].[ngayTaoKH]
                                                                                        FROM 
                                                                                            [dbo].[khachHang]  
                                                                                        LEFT JOIN 
                                                                                            hoaDon ON hoaDon.khachHang_id = khachHang.khachHang_id
                                                                                   WHERE hoTenKH LIKE N'%' + ? + N'%' or gioiTinh LIKE N'%' + ? + N'%'  or sdt LIKE N'%' + ? + N'%' or email LIKE N'%' + ? + N'%'  
                                                                                        GROUP BY 
                                                                                            [khachHang].[khachHang_id],
                                                                                            [hoTenKH],
                                                                                            [gioiTinh],
                                                                                            [diaChi],
                                                                                            [sdt],
                                                                                            [email],
                                                                                             [khachHang].[ghiChu],
                                                                                            [khachHang].[ngayTaoKH]
                                                          
                     
                           
                           """;

            ps = con.prepareCall(query);
            ps.setObject(1, id);
            ps.setObject(2, id);
            ps.setObject(3, id);
            ps.setObject(4, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(rs.getString("khachHang_id"),
                        rs.getString("hoTenKH"),
                        rs.getBoolean("gioiTinh"),
                        rs.getString("diaChi"),
                        rs.getString("sdt"),
                        rs.getString("email"),
                        rs.getString("ghiChu"),
                        rs.getDate("ngayTaoKH"),
                        rs.getInt("soLanMua"));
                listNV.add(kh);
            }

            return listNV;
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Lỗi" + ex.toString());
        }

        return null;
    }

    public List<KhachHang> phanTrangKH(int tienLui) {
        sql = """
          
                                                                              SELECT
                                                                                  [khachHang].[khachHang_id],
                                                                                  [hoTenKH],
                                                                                  [gioiTinh],
                                                                                  [diaChi],
                                                                                  [sdt],
                                                                                  [email],
                                                                                  [khachHang].[ghiChu],
                                                                                  COUNT(CASE WHEN hoaDon.trangThai = N'Hoàn thành' THEN 1 END) AS soLanMua,
                                                                                  [khachHang].[ngayTaoKH]
                                                                              FROM
                                                                                  [dbo].[khachHang]
                                                                              LEFT JOIN
                                                                                  hoaDon ON hoaDon.khachHang_id = khachHang.khachHang_id
                                                                              GROUP BY
                                                                                  [khachHang].[khachHang_id],
                                                                                  [hoTenKH],
                                                                                  [gioiTinh],
                                                                                  [diaChi],
                                                                                  [sdt],
                                                                                  [email],
                                                                                  [khachHang].[ghiChu],
                                                                                  [khachHang].[ngayTaoKH] order by khachHang.ngayTaoKH DESC
                                                                  offset ? rows  fetch next 5 rows only
                
              """;
        List<KhachHang> listKH = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tienLui);
            rs = ps.executeQuery();
            while (rs.next()) {
                listKH.add(new KhachHang(rs.getString("khachHang_id"),
                        rs.getString("hoTenKH"),
                        rs.getBoolean("gioiTinh"),
                        rs.getString("diaChi"),
                        rs.getString("sdt"),
                        rs.getString("email"),
                        rs.getString("ghiChu"),
                        rs.getDate("ngayTaoKH"),
                        rs.getInt("soLanMua")));
            }
            return listKH;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
