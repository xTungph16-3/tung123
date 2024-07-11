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

/**
 *
 * @author Trong Phu
 */
public class NhanVienDAO extends QLCHBG_DAO<NhanVien, String> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public List<NhanVien> selectAll() {
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
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public NhanVien selectByID(String id) {
        List<NhanVien> listNV = new ArrayList<>();
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
          FROM [dbo].[nhanVien] where nhanVien_id like ?
         """;
//        List<NhanVien> listNV = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, id);
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
            if (!listNV.isEmpty()) {
                return listNV.get(0);
            } else {
                return null;
            }

        } catch (SQLException e) {
            return null;

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                // Xử lý ngoại lệ trong trường hợp đóng kết nối thất bại
            }
        }
    }

    @Override
    public int insert(NhanVien entity
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(String key, NhanVien entity
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(String key
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Integer addNH(NhanVien n) {
        Integer row = null;
        String sql = """
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
                          VALUES (?,?,?,?,?,?,?,?,?,?)""";
        Connection cn = DB_Connect.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, n.getNhanVien_id());
            pstm.setObject(2, n.getHoTen());
            pstm.setObject(3, n.isGioiTinh());
            pstm.setObject(4, n.getNgaySinh());
            pstm.setObject(5, n.getDiaChi());
            pstm.setObject(6, n.getSdt());
            pstm.setObject(7, n.getTrangThai());
            pstm.setObject(8, n.getAnh());
            pstm.setObject(9, n.getMatkhau());
            pstm.setObject(10, n.getVaiTro());
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return row;
    }

    public boolean deleteNV(String id) {
        int check = 0;
        String query = "DELETE NhanVien WHERE nhanVien_id = ?";
        try (Connection cnn = DB_Connect.getConnection(); PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public String update(NhanVien nv, String id) {
        Connection conn = DB_Connect.getConnection();
        String update = """
                        UPDATE [dbo].[nhanVien] set
                               [hoTen] = ?
                              ,[gioiTinh] = ?
                              ,[ngaySinh] = ?
                              ,[diaChi] = ?
                              ,[sdt] = ?
                              ,[trangThai] = ?
                              ,[anh] = ?
                              ,[matkhau] = ?
                              ,[vaiTro] = ?
                         WHERE nhanVien_id = ?""";
        try {
            PreparedStatement ps = conn.prepareStatement(update);

            // ps.setObject(1, nv.getNhanVien_id());
            ps.setObject(1, nv.getHoTen());
            ps.setObject(2, nv.isGioiTinh());
            ps.setObject(3, nv.getNgaySinh());
            ps.setObject(4, nv.getDiaChi());
            ps.setObject(5, nv.getSdt());
            ps.setObject(6, nv.getTrangThai());
            ps.setObject(7, nv.getAnh());
            ps.setObject(8, nv.getMatkhau());
            ps.setObject(9, nv.getVaiTro());
            ps.setObject(10, id);
            ps.executeUpdate();
            return "Sửa thành công";
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Sủa không thành công";
        }
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
                listNV.add(new NhanVien(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
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

                listNV.add(new NhanVien(rs.getString(1), rs.getString(2), rs.getBoolean(3), rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
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
