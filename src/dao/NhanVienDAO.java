/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.NhanVien;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.DB_Connect;
import java.sql.*;
import utils.jdbcHelper;

/**
 *
 * @author Trong Phu
 */
public class NhanVienDAO extends QLCHBG_DAO<NhanVien, String> {

    private String sql = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[nhanVien]
                                         ([nhanVien_id]
                                         ,[hoTen]
                                         ,[gioiTinh]
                                         ,[ngaySinh]
                                         ,[diaChi]
                                         ,[sdt]
                                         ,[trangThai]
                                         ,[anh]
                                         ,[matkhau]
                                         ,[vaiTro])
                                   VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE [dbo].[nhanVien]
                                 SET [hoTen] = ?
                                    ,[gioiTinh] = ? 
                                    ,[ngaySinh] = ?
                                    ,[diaChi] = ?
                                    ,[sdt] = ?
                                    ,[trangThai] = ?
                                    ,[anh] = ?
                                    ,[matkhau] = ?
                                    ,[vaiTro] = ?
                               WHERE [nhanVien_id] LIKE ?
                              """;

    final String DELETE_SQL = """
                              UPDATE [dbo].[nhanVien]
                                 SET [trangThai] = 'Nghỉ việc'
                               WHERE nhanVien_id = ?
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT [nhanVien_id]
                                        ,[hoTen]
                                        ,[gioiTinh]
                                        ,[ngaySinh]
                                        ,[diaChi]
                                        ,[sdt]
                                        ,[trangThai]
                                        ,[anh]
                                        ,[matkhau]
                                        ,[vaiTro]
                                    FROM [dbo].[nhanVien]
                                  """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [nhanVien_id]
                                          ,[hoTen]
                                          ,[gioiTinh]
                                          ,[ngaySinh]
                                          ,[diaChi]
                                          ,[sdt]
                                          ,[trangThai]
                                          ,[anh]
                                          ,[matkhau]
                                          ,[vaiTro]
                                      FROM [dbo].[nhanVien]
                                    WHERE [nhanVien_id] LIKE ?
                                    """;

    @Override
    public void insert(NhanVien entity) {
        jdbcHelper.update(INSERT_SQL, entity.getNhanVien_id(),
                entity.getHoTen(),
                entity.getGioiTinh() ? 1 : 0,
                entity.getNgaySinh(),
                entity.getDiaChi(),
                entity.getSdt(),
                entity.getTrangThai(),
                entity.getAnh(),
                entity.getMatkhau(),
                entity.getVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        jdbcHelper.update(UPADTE_SQL, entity.getHoTen(),
                entity.getGioiTinh() ? 1 : 0,
                entity.getNgaySinh(),
                entity.getDiaChi(),
                entity.getSdt(),
                entity.getTrangThai(),
                entity.getAnh(),
                entity.getMatkhau(),
                entity.getVaiTro(),
                entity.getNhanVien_id());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySQL(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);
            while (resultSet.next()) {
                NhanVien entity = new NhanVien();

                entity.setNhanVien_id(resultSet.getString("nhanVien_id"));
                entity.setHoTen(resultSet.getString("hoTen"));
                entity.setGioiTinh(resultSet.getBoolean("gioiTinh"));
                entity.setNgaySinh(resultSet.getDate("ngaySinh"));
                entity.setDiaChi(resultSet.getString("diaChi"));
                entity.setSdt(resultSet.getString("sdt"));
                entity.setTrangThai(resultSet.getString("trangThai"));
                entity.setAnh(resultSet.getString("anh"));
                entity.setMatkhau(resultSet.getString("matkhau"));
                entity.setVaiTro(resultSet.getString("vaiTro"));

                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }

    public List<NhanVien> timKiem(String txt) {
        sql = """
              SELECT [nhanVien_id]
              ,[hoTen]
              ,[gioiTinh]
              ,[ngaySinh]
              ,[diaChi]
              ,[sdt]
              ,[trangThai]
              ,[anh]
              ,[matkhau]
              ,[vaiTro]
          FROM [dbo].[nhanVien]
         """;
        List<NhanVien> listNV = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("nhanVien_id"),
                        rs.getString("hoTen"),
                        rs.getBoolean("gioiTinh"),
                        rs.getDate("ngaySinh"),
                        rs.getString("diaChi"),
                        rs.getString("sdt"),
                        rs.getString("trangThai"),
                        rs.getString("anh"),
                        rs.getString("matKhau"),
                        rs.getString("vaiTro"));
                listNV.add(nv);
            }

            return listNV;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public List<NhanVien> phantrang(int tienLui) {
        List<NhanVien> listNV = new ArrayList();
        NhanVien nv = new NhanVien();

        try {
            Connection connection = DB_Connect.getConnection();

            String where_condition = " SELECT [nhanVien_id]\n"
                    + "              ,[hoTen]\n"
                    + "              ,[gioiTinh]\n"
                    + "              ,[ngaySinh]\n"
                    + "              ,[diaChi]\n"
                    + "              ,[sdt]\n"
                    + "              ,[trangThai]\n"
                    + "              ,[anh]\n"
                    + "              ,[matkhau]\n"
                    + "              ,[vaiTro]\n"
                    + "          FROM [dbo].[nhanVien] \n"
                    + "                    order by nhanVien_id\n"
                    + "            offset " + tienLui + "rows fetch next 5 rows only";
            String query = where_condition;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                listNV.add(new NhanVien(rs.getString(1),
                        rs.getString(2),
                        rs.getBoolean(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10)));
            }

        } catch (Exception ex) {
            System.out.println("Lỗi" + ex.toString());
        }

        return listNV;
    }

    public List<NhanVien> timKiemTheoTenHoacMa1(String txt, int phantu) {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = DB_Connect.getConnection();
        String timkiem = "  order by MaNhanVien\n"
                + "            offset " + phantu + "rows fetch next 5 rows only";
        String query = "SELECT [nhanVien_id]\n"
                + "              ,[hoTen]\n"
                + "              ,[gioiTinh]\n"
                + "              ,[ngaySinh]\n"
                + "              ,[diaChi]\n"
                + "              ,[sdt]\n"
                + "              ,[trangThai]\n"
                + "              ,[anh]\n"
                + "              ,[matkhau]\n"
                + "              ,[vaiTro]\n"
                + "          FROM [dbo].[nhanVien] \n"
                + " WHERE nhanVien_id like ? OR hoTen like ? OR trangThai ?"
                + timkiem;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setObject(1, "%" + txt + "%");
            ps.setObject(2, "%" + txt + "%");
            ps.setObject(3, "%" + txt + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listNV.add(new NhanVien(rs.getString(1),
                        rs.getString(2),
                        rs.getBoolean(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10))
                );
//         listNV.add(new NhanVien(query, query, true, ngaySinh, query, sql, query, sql, query, query)
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return listNV;
    }

    public List<NhanVien> timkiem(String id) {
        List<NhanVien> listNV = new ArrayList<>();

        try {
            con = DB_Connect.getConnection();

            String query = """
                           SELECT [nhanVien_id]
                                 ,[hoTen]
                                 ,[gioiTinh]
                                 ,[ngaySinh]
                                 ,[diaChi]
                                 ,[sdt]
                                 ,[trangThai]
                                 ,[anh]
                                 ,[matkhau]
                                 ,[vaiTro]
                             FROM [dbo].[nhanVien] WHERE hoten LIKE N'%' + ? + N'%' or sdt LIKE N'%' + ? + N'%'  
                     
                           
                           """;

            ps = con.prepareCall(query);
            ps.setObject(1, id);
            ps.setObject(2, id);
//            ps.setObject(3, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("nhanVien_id"),
                        rs.getString("hoTen"),
                        rs.getBoolean("gioiTinh"),
                        rs.getDate("ngaySinh"),
                        rs.getString("diaChi"),
                        rs.getString("sdt"),
                        rs.getString("trangThai"),
                        rs.getString("anh"),
                        rs.getString("matKhau"),
                        rs.getString("vaiTro"));
                listNV.add(nv);
            }

            return listNV;
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Lỗi" + ex.toString());
        }

        return null;
    }

    public List<NhanVien> timkiem2(String id, int phantu) {
        List<NhanVien> listNV = new ArrayList<>();

        try {
            con = DB_Connect.getConnection();
            String timkiem = "  order by nhanVien_id\n"
                    + "            offset " + phantu + "rows fetch next 5 rows only";
            String query = """
                           SELECT [nhanVien_id]
                                 ,[hoTen]
                                 ,[gioiTinh]
                                 ,[ngaySinh]
                                 ,[diaChi]
                                 ,[sdt]
                                 ,[trangThai]
                                 ,[anh]
                                 ,[matkhau]
                                 ,[vaiTro]
                             FROM [dbo].[nhanVien] WHERE hoten LIKE N'%' + ? + N'%' or sdt LIKE N'%' + ? + N'%'                       
                           """ + timkiem;

            ps = con.prepareCall(query);
            ps.setObject(1, id);
            ps.setObject(2, id);
//            ps.setObject(3, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("nhanVien_id"),
                        rs.getString("hoTen"),
                        rs.getBoolean("gioiTinh"),
                        rs.getDate("ngaySinh"),
                        rs.getString("diaChi"),
                        rs.getString("sdt"),
                        rs.getString("trangThai"),
                        rs.getString("anh"),
                        rs.getString("matKhau"),
                        rs.getString("vaiTro"));
                listNV.add(nv);
            }

            return listNV;
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("Lỗi" + ex.toString());
        }

        return null;
    }
}
