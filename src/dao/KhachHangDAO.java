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
import utils.jdbcHelper;

/**
 *
 * @author Trong Phu
 */
public class KhachHangDAO extends QLCHBG_DAO<KhachHang, String> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[khachHang]
                                         ([khachHang_id]
                                         ,[hoTenKH]
                                         ,[gioiTinh]
                                         ,[diaChi]
                                         ,[sdt]
                                         ,[email]
                                         ,[ghiChu])
                              VALUES(?, ?, ?, ?, ?, ?, ?)
                              """;

    final String UPDATE_SQL = """
                              UPDATE [dbo].[khachHang]
                                 SET [hoTenKH] = ?
                                    ,[gioiTinh] = ?
                                    ,[diaChi] = ?
                                    ,[sdt] = ?
                                    ,[email] = ?
                                    ,[ghiChu] = ?
                               WHERE [khachHang_id] = ?
                              """;

    final String DELETE_SQL = """
                              
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT [khachHang].[khachHang_id],
                                    			[hoTenKH],
                                    			[gioiTinh],
                                    			[diaChi],
                                    			[sdt],
                                                        [email],
                                                        [khachHang].[ghiChu],
                                                        COUNT(CASE WHEN hoaDon.trangThai = N'Hoàn thành' THEN 1 END) AS soLanMua,
                                                        [khachHang].[ngayTaoKH]
                                    FROM [dbo].[khachHang]
                                         LEFT JOIN hoaDon ON hoaDon.khachHang_id = khachHang.khachHang_id
                                    GROUP BY  [khachHang].[khachHang_id],
                                                            [hoTenKH],
                                                            [gioiTinh],
                                                            [diaChi],
                                                            [sdt],
                                                            [email],
                                                            [khachHang].[ghiChu],
                                                            [khachHang].[ngayTaoKH];
                                  """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [khachHang_id]
                                          ,[hoTenKH]
                                          ,[gioiTinh]
                                          ,[diaChi]
                                          ,[sdt]
                                          ,[email]
                                          ,[ghiChu]
                                          ,[ngayTaoKH]
                                      FROM [dbo].[khachHang]
                                    WHERE [khachHang_id] = ?
                                    """;

    @Override
    public List<KhachHang> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public void insert(KhachHang entity) {

        int gioiTinhBit = entity.getgioitinh().equalsIgnoreCase("Nam") ? 1 : 0;

        jdbcHelper.update(INSERT_SQL,
                entity.getKhachHang_id(),
                entity.getHoTenHK(),
                gioiTinhBit,
                entity.getDiaChi(),
                entity.getSdt(),
                entity.getEmail(),
                entity.getGhiChu());

    }

    @Override
    public void update(KhachHang entity) {

        int gioiTinhBit = entity.getgioitinh().equalsIgnoreCase("Nam") ? 1 : 0;

        jdbcHelper.update(UPDATE_SQL,
                entity.getHoTenHK(),
                gioiTinhBit,
                entity.getDiaChi(),
                entity.getSdt(),
                entity.getEmail(),
                entity.getGhiChu(),
                entity.getKhachHang_id());
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public KhachHang selectById(String id) {
        List<KhachHang> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectBySQL(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);
            while (resultSet.next()) {

                KhachHang entity = new KhachHang();

                entity.setKhachHang_id(resultSet.getString("khachHang_id"));
                entity.setHoTenHK(resultSet.getString("hoTenKH"));
                entity.setGioiTinh(resultSet.getBoolean("gioiTinh"));
                entity.setDiaChi(resultSet.getString("diaChi"));
                entity.setSdt(resultSet.getString("sdt"));
                entity.setEmail(resultSet.getString("email"));
                entity.setGhiChu(resultSet.getString("ghiChu"));
                entity.setNgayTao(resultSet.getDate("ngayTaoKH"));

                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<KhachHang> timkiem(String id) {
        List<KhachHang> listNV = new ArrayList<>();

        try {
            con = DB_Connect.getConnection();

            String query = """
                            SELECT [khachHang].[khachHang_id],
                                                hoTenKH],
                                                [gioiTinh],
                                                [diaChi],
                                                [sdt],
                                                [email],
                                                [khachHang].[ghiChu],
                                                COUNT(CASE WHEN hoaDon.trangThai = N'Hoàn thành' THEN 1 END) AS soLanMua,
                                                [khachHang].[ngayTaoKH]
                            FROM [dbo].[khachHang]  
                            LEFT JOIN hoaDon ON hoaDon.khachHang_id = khachHang.khachHang_id
                            WHERE hoTenKH LIKE N'%' + ? + N'%' or gioiTinh LIKE N'%' + ? + N'%'  or sdt LIKE N'%' + ? + N'%' or email LIKE N'%' + ? + N'%'  
                            GROUP BY [khachHang].[khachHang_id],
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
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("Lỗi" + e.toString());
        }

        return null;
    }

    public List<KhachHang> phanTrangKH(int tienLui) {
        sql = """
                SELECT [khachHang].[khachHang_id],
                                    [hoTenKH],
                                    [gioiTinh],
                                    [diaChi],
                                    [sdt],
                                    [email],
                                    [khachHang].[ghiChu],
                                    COUNT(CASE WHEN hoaDon.trangThai = N'Hoàn thành' THEN 1 END) AS soLanMua,
                                    [khachHang].[ngayTaoKH]
                FROM [dbo].[khachHang]
                LEFT JOIN hoaDon ON hoaDon.khachHang_id = khachHang.khachHang_id
                GROUP BY [khachHang].[khachHang_id],
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
