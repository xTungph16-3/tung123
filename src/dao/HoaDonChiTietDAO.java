/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.HoaDonChiTiet;
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
public class HoaDonChiTietDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    // hàm này có chức năng thêm 1 hdct mới
    public int insertHoaDonCT(HoaDonChiTiet hDCT) {

        sql = """
                   INSERT INTO [dbo].[hoaDonChiTiet]
                              ([hDCT_id]
                              ,[hoaDon_id]
                              ,[sPCT_id]
                              ,[soLuong]
                              ,[giaBan]
                              ,[thanhTien]
                              ,[trangThaiHDCT])
                        VALUES ( ?, ?, ?, ?, ?, ?, ?)
              """;
        try {
            con = DB_Connect.getConnection();

            ps = con.prepareCall(sql);
            ps.setObject(1, hDCT.getHDCT_id());
            ps.setObject(2, hDCT.getHoaDon_id());
            ps.setObject(3, hDCT.getSPCT_id());
            ps.setObject(4, hDCT.getSoLuong());
            ps.setObject(5, hDCT.getGiaBan());
            ps.setObject(6, hDCT.getThanhTien());
            ps.setObject(7, hDCT.getTrangThaiHDCT());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<HoaDonChiTiet> selectAll() {
        List<HoaDonChiTiet> list2 = new ArrayList<>();
        sql = """
              SELECT [hDCT_id]
                    ,[hoaDon_id]
                    ,[sPCT_id]
                    ,[soLuong]
                    ,[giaBan]
                    ,[thanhTien]
                    ,[trangThaiHDCT]
                FROM [dbo].[hoaDonChiTiet]
                order by hoaDon_id
              """;
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet(
                        rs.getString("hDCT_id"),
                        rs.getString("hoaDon_id"),
                        rs.getString("sPCT_id"),
                        rs.getInt("soLuong"),
                        rs.getBigDecimal("giaBan"),
                        rs.getBigDecimal("thanhTien"),
                        rs.getString("trangThaiHDCT")
                );
                list2.add(hdct);
            }
            return list2;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public List<HoaDonChiTiet> selectHDCTByHoaDon_id(String hoaDon_id) {
        List<HoaDonChiTiet> list2 = new ArrayList<>();
        sql = """
              SELECT    [hDCT_id]
                        ,[hoaDon_id]
                        ,[sPCT_id]
                        ,[soLuong]
                        ,[giaBan]
                        ,[thanhTien]
                        ,[trangThaiHDCT]
              FROM [dbo].[hoaDonChiTiet]
              where hoaDon_id like ? and trangThaiHDCT like N'Hoàn thành'
              """;

        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hoaDon_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet(
                        rs.getString("hDCT_id"),
                        rs.getString("hoaDon_id"),
                        rs.getString("sPCT_id"),
                        rs.getInt("soLuong"),
                        rs.getBigDecimal("giaBan"),
                        rs.getBigDecimal("thanhTien"),
                        rs.getString("trangThaiHDCT")
                );
                list2.add(hdct);
            }
            return list2;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public int updateTrangThaiHDCTByHoaDonId(String hoaDon_id, String trangThai) {
        String sql = """
                        UPDATE [dbo].[hoaDonChiTiet]
                        SET [trangThaiHDCT] = ?
                        WHERE hoaDon_id = ?
                     """;
        try (Connection con = DB_Connect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ps.setString(2, hoaDon_id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<HoaDonChiTiet> selectHDCT(String id) {
        ArrayList<HoaDonChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
                        SELECT	dbo.hoaDonChiTiet.hDCT_id AS hDCT_id,
                        		dbo.hoaDon.hoaDon_id AS hoaDon_id,
                        		dbo.sanPhamChiTiet.sPCT_id AS sPCT_id ,
                        		dbo.sanPham.ten AS tenSanPham, 
                        		dbo.size.giaTri AS size, 
                        		dbo.mauSac.tenMau AS mauSac, 
                        		dbo.hoaDonChiTiet.soLuong AS soLuong, 
                        		dbo.hoaDonChiTiet.giaBan AS giaBan, 
                        		dbo.hoaDonChiTiet.thanhTien AS thanhTien,
                        		dbo.hoaDonChiTiet.trangThaiHDCT AS trangThaiHDCT
                        FROM     dbo.hoaDon 
                        INNER JOIN dbo.hoaDonChiTiet ON dbo.hoaDon.hoaDon_id = dbo.hoaDonChiTiet.hoaDon_id 
                        INNER JOIN dbo.sanPhamChiTiet ON dbo.hoaDonChiTiet.sPCT_id = dbo.sanPhamChiTiet.sPCT_id 
                        INNER JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
                        INNER JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
                        INNER JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
                        where hoaDonChiTiet.hoaDon_id like ? and (trangThaiHDCT like N'Chờ thanh toán'
                              or trangThaiHDCT like N'Hoàn thành')
                         """;
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            pstm.setObject(1, id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();

                hoaDonChiTiet.setHDCT_id(rs.getString("hDCT_id"));
                hoaDonChiTiet.setHoaDon_id(rs.getString("hoaDon_id"));
                hoaDonChiTiet.setSPCT_id(rs.getString("sPCT_id"));
                hoaDonChiTiet.setTenSanPham(rs.getString("tenSanPham"));
                hoaDonChiTiet.setSize(rs.getInt("size"));
                hoaDonChiTiet.setMauSac(rs.getString("mauSac"));
                hoaDonChiTiet.setSoLuong(rs.getInt("soLuong"));
                hoaDonChiTiet.setGiaBan(rs.getBigDecimal("giaBan"));
                hoaDonChiTiet.setThanhTien(rs.getBigDecimal("thanhTien"));
                hoaDonChiTiet.setTrangThaiHDCT(rs.getString("trangThaiHDCT"));

                lst.add(hoaDonChiTiet);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return lst;
    }
}
