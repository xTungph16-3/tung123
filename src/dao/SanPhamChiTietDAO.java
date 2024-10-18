/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.SanPhamChiTiet;
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
public class SanPhamChiTietDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<SanPhamChiTiet> selectAll() {
        sql = """
              SELECT [sPCT_id]
                    ,[soLuong]
                    ,[donGia]
                    ,[size_id]
                    ,[anh]
                    ,[mauSac_id]
                    ,[sanPham_id]
                    ,[trangThai]
                    ,[moTa]
                FROM [dbo].[sanPhamChiTiet]
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
                        rs.getBigDecimal("donGia"),
                        rs.getInt("size_id"),
                        rs.getString("anh"),
                        rs.getInt("mauSac_id"),
                        rs.getString("sanPham_id"),
                        rs.getString("trangThai"),
                        rs.getString("moTa")
                );
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SanPhamChiTiet> selectAll2() {
        String sql = """
                        SELECT dbo.size.giaTri AS size, 
                             dbo.sanPham.sanPham_id AS sanPham_id, 
                             dbo.sanPhamChiTiet.sPCT_id AS sPCT_id, 
                             dbo.mauSac.tenMau AS mauSac, 
                             dbo.chuongTrinhKhuyenMai.giamGia AS giamGia, 
                             dbo.sanPham.ten AS tenSanPham, 
                             dbo.sanPhamChiTiet.soLuong AS soLuong, 
                             dbo.sanPhamChiTiet.donGia AS donGia, 
                             dbo.sanPhamChiTiet.trangThai AS trangThai,
                             dbo.sanPhamChiTiet.moTa AS moTa
                        FROM dbo.chuongTrinhKhuyenMai 
                        INNER JOIN dbo.sanPham_chuongTrinhKhuyenMai ON dbo.chuongTrinhKhuyenMai.chuongTrinh_id = dbo.sanPham_chuongTrinhKhuyenMai.chuongTrinh_id 
                        INNER JOIN dbo.sanPhamChiTiet ON dbo.sanPham_chuongTrinhKhuyenMai.sPCT_id = dbo.sanPhamChiTiet.sPCT_id 
                        INNER JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
                        INNER JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
                        INNER JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
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
                        rs.getBigDecimal("donGia"),
                        rs.getString("sanPham_id"),
                        rs.getString("trangThai"),
                        rs.getString("moTa"),
                        rs.getInt("size"),
                        rs.getString("mauSac"),
                        rs.getInt("giamGia"),
                        rs.getString("tenSanPham")
                );
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SanPhamChiTiet selectSPTCByID(String sPCT_id) {
        sql = """
              SELECT    dbo.size.giaTri AS size, 
                        dbo.sanPham.sanPham_id AS sanPham_id, 
                        dbo.sanPhamChiTiet.sPCT_id AS sPCT_id, 
                        dbo.mauSac.tenMau AS mauSac, 
                        dbo.chuongTrinhKhuyenMai.giamGia AS giamGia, 
                        dbo.sanPham.ten AS tenSanPham, 
                        dbo.sanPhamChiTiet.soLuong AS soLuong, 
                        dbo.sanPhamChiTiet.donGia AS donGia, 
                        dbo.sanPhamChiTiet.trangThai AS trangThai,
                        dbo.sanPhamChiTiet.moTa AS moTa
              FROM dbo.chuongTrinhKhuyenMai 
              INNER JOIN dbo.sanPham_chuongTrinhKhuyenMai ON dbo.chuongTrinhKhuyenMai.chuongTrinh_id = dbo.sanPham_chuongTrinhKhuyenMai.chuongTrinh_id 
              INNER JOIN dbo.sanPhamChiTiet ON dbo.sanPham_chuongTrinhKhuyenMai.sPCT_id = dbo.sanPhamChiTiet.sPCT_id 
              INNER JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
              INNER JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
              INNER JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
              where sPCT_id = ?
                
              """;
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sPCT_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet(
                        rs.getString("sPCT_id"),
                        rs.getInt("soLuong"),
                        rs.getBigDecimal("donGia"),
                        rs.getString("sanPham_id"),
                        rs.getString("trangThai"),
                        rs.getString("moTa"),
                        rs.getInt("size"),
                        rs.getString("mauSac"),
                        rs.getInt("giamGia"),
                        rs.getString("tenSanPham")
                );
                listSPCT.add(spct);
            }
            return listSPCT.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /// hàm này thực hiện lọc sản phẩm chi tiết theo id sản phẩm cha
    public List<SanPhamChiTiet> selectSPTCByIDSP(String sP_id) {
        sql = """
              SELECT dbo.size.giaTri AS size, 
                              dbo.sanPham.sanPham_id AS sanPham_id, 
                              dbo.sanPhamChiTiet.sPCT_id AS sPCT_id, 
                              dbo.mauSac.tenMau AS mauSac, 
                              dbo.chuongTrinhKhuyenMai.giamGia AS giamGia, 
                              dbo.sanPham.ten AS tenSanPham, 
                              dbo.sanPhamChiTiet.soLuong AS soLuong, 
                              dbo.sanPhamChiTiet.donGia AS donGia, 
                              dbo.sanPhamChiTiet.trangThai AS trangThai,
                              dbo.sanPhamChiTiet.moTa AS moTa
                         FROM dbo.chuongTrinhKhuyenMai 
                         LEFT JOIN dbo.sanPham_chuongTrinhKhuyenMai ON dbo.chuongTrinhKhuyenMai.chuongTrinh_id = dbo.sanPham_chuongTrinhKhuyenMai.chuongTrinh_id 
                         LEFT JOIN dbo.sanPhamChiTiet ON dbo.sanPham_chuongTrinhKhuyenMai.sPCT_id = dbo.sanPhamChiTiet.sPCT_id 
                         LEFT JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
                         LEFT JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
                         LEFT JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
              where sanPham.sanPham_id = ?
              """;
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sP_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet(
                        rs.getString("sPCT_id"),
                        rs.getInt("soLuong"),
                        rs.getBigDecimal("donGia"),
                        rs.getString("sanPham_id"),
                        rs.getString("trangThai"),
                        rs.getString("moTa"),
                        rs.getInt("size"),
                        rs.getString("mauSac"),
                        rs.getInt("giamGia"),
                        rs.getString("tenSanPham")
                );
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // hàm này thực hiện phân trang sản phẩm chi tiết
    public List<SanPhamChiTiet> phanTrangSPCT(String sP_id, int tienLui) {
        sql = """
                    SELECT dbo.size.giaTri AS size, 
                           dbo.sanPham.sanPham_id AS sanPham_id, 
                           dbo.sanPhamChiTiet.sPCT_id AS sPCT_id, 
                           dbo.mauSac.tenMau AS mauSac, 
                           dbo.chuongTrinhKhuyenMai.giamGia AS giamGia, 
                           dbo.sanPham.ten AS tenSanPham, 
                           dbo.sanPhamChiTiet.soLuong AS soLuong, 
                           dbo.sanPhamChiTiet.donGia AS donGia, 
                           dbo.sanPhamChiTiet.trangThai AS trangThai,
                           dbo.sanPhamChiTiet.moTa AS moTa
                   FROM dbo.chuongTrinhKhuyenMai 
                   LEFT JOIN dbo.sanPham_chuongTrinhKhuyenMai ON dbo.chuongTrinhKhuyenMai.chuongTrinh_id = dbo.sanPham_chuongTrinhKhuyenMai.chuongTrinh_id 
                   LEFT JOIN dbo.sanPhamChiTiet ON dbo.sanPham_chuongTrinhKhuyenMai.sPCT_id = dbo.sanPhamChiTiet.sPCT_id 
                   LEFT JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
                   LEFT JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
                   LEFT JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
                   where sanPham.sanPham_id like ?
                   order by ngayTaoSPCT desc
                   offset ? rows  fetch next 5 rows only                
              """;
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sP_id);
            ps.setObject(2, tienLui);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet(
                        rs.getString("sPCT_id"),
                        rs.getInt("soLuong"),
                        rs.getBigDecimal("donGia"),
                        rs.getString("sanPham_id"),
                        rs.getString("trangThai"),
                        rs.getString("mota"),
                        rs.getInt("size"),
                        rs.getString("mauSac"),
                        rs.getInt("giamGia"),
                        rs.getString("tenSanPham")
                );
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // hàm này thực hiện phân trang sản phẩm chi tiết
    public List<SanPhamChiTiet> phanTrangSPCT1(String sP_id, int tienLui) {
        sql = """
             SELECT dbo.size.giaTri AS size, 
                   dbo.sanPham.sanPham_id AS sanPham_id, 
                   dbo.sanPhamChiTiet.sPCT_id AS sPCT_id, 
                   dbo.mauSac.tenMau AS mauSac, 
                   dbo.chuongTrinhKhuyenMai.ten AS ten, 
                   dbo.sanPham.ten AS tenSanPham, 
                   dbo.sanPhamChiTiet.soLuong AS soLuong, 
                   dbo.sanPhamChiTiet.donGia AS donGia, 
                   dbo.sanPhamChiTiet.trangThai AS trangThai,
                   dbo.sanPhamChiTiet.moTa AS moTa
            FROM dbo.sanPhamChiTiet
            LEFT JOIN dbo.sanPham_chuongTrinhKhuyenMai ON dbo.sanPhamChiTiet.sPCT_id = dbo.sanPham_chuongTrinhKhuyenMai.sPCT_id 
            LEFT JOIN dbo.chuongTrinhKhuyenMai ON dbo.sanPham_chuongTrinhKhuyenMai.chuongTrinh_id = dbo.chuongTrinhKhuyenMai.chuongTrinh_id 
            LEFT JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
            LEFT JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
            LEFT JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
            where sanPham.sanPham_id like ?
            order by ngayTaoSPCT desc
            offset ? rows  fetch next 5 rows only
                
              """;
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sP_id);
            ps.setObject(2, tienLui);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet(
                        rs.getString("sPCT_id"),
                        rs.getInt("soLuong"),
                        rs.getBigDecimal("donGia"),
                        rs.getString("sanPham_id"),
                        rs.getString("trangThai"),
                        rs.getString("mota"),
                        rs.getInt("size"),
                        rs.getString("mauSac"),
                        rs.getString("ten"),
                        rs.getString("tenSanPham")
                );
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insert(SanPhamChiTiet entity) {
        sql = """
                INSERT INTO [dbo].[sanPhamChiTiet]
                           ([sPCT_id]
                           ,[soLuong]
                           ,[donGia]
                           ,[size_id]
                           ,[anh]
                           ,[mauSac_id]
                           ,[sanPham_id]
                           ,[trangThai]
                           ,[moTa])
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, entity.getSPCT_id());
            ps.setObject(2, entity.getSoLuong());
            ps.setObject(3, entity.getDonGia());
            ps.setObject(4, entity.getSize_id());
            ps.setObject(5, entity.getAnh());
            ps.setObject(6, entity.getMauSac_id());
            ps.setObject(7, entity.getSanPham_id());
            ps.setObject(8, entity.getTrangThai());
            ps.setObject(9, entity.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(String key, SanPhamChiTiet entity) {
        sql = """
              UPDATE [dbo].[sanPhamChiTiet]
                 SET [soLuong] = ?
                    ,[donGia] = ?
                    ,[size_id] = ?
                    ,[anh] = ?
                    ,[mauSac_id] = ?
                    ,[sanPham_id] = ?
                    ,[trangThai] = ?
                    ,[moTa] = ?
               WHERE [sPCT_id] = ?
              """;
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, entity.getSoLuong());
            ps.setObject(2, entity.getDonGia());
            ps.setObject(3, entity.getSize_id());
            ps.setObject(4, entity.getAnh());
            ps.setObject(5, entity.getMauSac_id());
            ps.setObject(6, entity.getSanPham_id());
            ps.setObject(7, entity.getTrangThai());
            ps.setObject(8, entity.getMoTa());
            ps.setObject(9, key);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(String key) {
        sql = "delete sanPhamChiTiet where sPCT_id like ? ";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, key);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    // Select SanPhamChiTiet lên BanHang_View
    public List<SanPhamChiTiet> selectAllSPCT_BanHang() {
        String sql = """
                       SELECT   dbo.sanPham.sanPham_id AS maSanPham, 
                                dbo.sanPham.ten AS tenSanPham, 
                                dbo.size.giaTri AS size, 
                                dbo.mauSac.tenMau AS mauSac, 
                                dbo.sanPhamChiTiet.soLuong AS soLuong, 
                                dbo.sanPhamChiTiet.donGia AS donGia, 
                                dbo.chuongTrinhKhuyenMai.giamGia AS giamGia
                       FROM     dbo.chuongTrinhKhuyenMai 
                       INNER JOIN dbo.sanPham_chuongTrinhKhuyenMai ON dbo.chuongTrinhKhuyenMai.chuongTrinh_id = dbo.sanPham_chuongTrinhKhuyenMai.chuongTrinh_id 
                       INNER JOIN dbo.sanPhamChiTiet ON dbo.sanPham_chuongTrinhKhuyenMai.sPCT_id = dbo.sanPhamChiTiet.sPCT_id 
                       INNER JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
                       INNER JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
                       INNER JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
                     """;
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();

                spct.setSanPham_id(rs.getString("maSanPham"));
                spct.setTenSanPham(rs.getString("tenSanPham"));
                spct.setSize(rs.getInt("size"));
                spct.setMauSac(rs.getString("mauSac"));
                spct.setSoLuong(rs.getInt("soLuong"));
                spct.setDonGia(rs.getBigDecimal("donGia"));
                spct.setGiamGia(rs.getInt("giamGia"));

                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<SanPhamChiTiet> selectSPCT() {
        ArrayList<SanPhamChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
                        SELECT
                             spct.sPCT_id AS sPCT_id,
                             sp.ten AS tenSanPham,
                             ms.tenMau AS mauSac,
                             sz.giaTri AS size,
                             spct.donGia AS donGia,
                             spct.soLuong AS soLuong,
                             ctkm.giamGia AS giamGia
                         FROM
                             dbo.sanPhamChiTiet spct
                         LEFT JOIN dbo.sanPham sp ON spct.sanPham_id = sp.sanPham_id
                         LEFT JOIN dbo.mauSac ms ON spct.mauSac_id = ms.mauSac_id
                         LEFT JOIN dbo.size sz ON spct.size_id = sz.size_id
                         LEFT JOIN dbo.sanPham_chuongTrinhKhuyenMai spctkm ON spct.sPCT_id = spctkm.sPCT_id
                         LEFT JOIN dbo.chuongTrinhKhuyenMai ctkm ON spctkm.chuongTrinh_id = ctkm.chuongTrinh_id
                         WHERE
                             sp.trangThai = N'Đang bán';
                         """;

            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            ResultSet rsLocal = pstm.executeQuery();
            while (rsLocal.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();

                spct.setSPCT_id(rsLocal.getString("sPCT_id"));
                spct.setTenSanPham(rsLocal.getString("tenSanPham"));
                spct.setMauSac(rsLocal.getString("mauSac"));
                spct.setSize(rsLocal.getInt("size"));
                spct.setDonGia(rsLocal.getBigDecimal("donGia"));
                spct.setSoLuong(rsLocal.getInt("soLuong"));
                spct.setGiamGia(rsLocal.getInt("giamGia"));

                lst.add(spct);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

}
