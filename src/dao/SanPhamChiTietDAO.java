/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

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
public class SanPhamChiTietDAO extends QLCHBG_DAO<SanPhamChiTiet, String> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public List<SanPhamChiTiet> selectAll() {
        sql = """
              SELECT  [sPCT_id]
                    ,[soLuong]
                    ,[donGia]
                    ,[size_id]
                    ,[chatLieu_id]
                    ,[nhaCC_id]
                    ,[anh]
                    ,[mauSac_id]
                    ,[sanPham_id]
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
                        rs.getInt("chatLieu_id"),
                        rs.getInt("nhaCC_id"),
                        rs.getString("anh"),
                        rs.getInt("mauSac_id"),
                        rs.getString("sanPham_id"));
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SanPhamChiTiet> selectAll2() {
        sql = """
              SELECT  [sPCT_id]
                	,sanPham.sanPham_id
                	,sanPham.[ten] [tenSanPham]
                	,sanPhamChiTiet.trangThai
                	,sanPhamChiTiet.moTa
                      ,[soLuong]
                      ,[donGia]
                      ,size.[giaTri] [size]
                      ,chatLieu.[ten] [tenChatlieu]
                      ,nhaCungCap.[ten] [tenNhaCC]
                      ,sanPhamChiTiet.anh
                      ,mauSac.[tenMau] [tenMau]
                  FROM [dbo].[sanPhamChiTiet] 
                  join sanPham on sanPham.sanPham_id = sanPhamChiTiet.sanPham_id
                  join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                  join size on size.size_id = sanPhamChiTiet.size_id
                  join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                  join nhaCungCap on nhaCungCap.nhaCC_id = sanPhamChiTiet.nhaCC_id
                
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
                        rs.getString("mota"),
                        rs.getInt("size"),
                        rs.getString("tenChatlieu"),
                        rs.getString("tenNhaCC"),
                        rs.getString("anh"),
                        rs.getString("tenMau"),
                        rs.getString("tenSanPham"));
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
              SELECT  [sPCT_id]
                	,sanPham.sanPham_id
                	,sanPham.[ten] [tenSanPham]
                	,sanPhamChiTiet.trangThai
                	,sanPhamChiTiet.moTa
                      ,[soLuong]
                      ,[donGia]
                      ,size.[giaTri] [size]
                      ,chatLieu.[ten] [tenChatlieu]
                      ,nhaCungCap.[ten] [tenNhaCC]
                      ,sanPhamChiTiet.anh
                      ,mauSac.[tenMau] [tenMau]
                  FROM [dbo].[sanPhamChiTiet] 
                  join sanPham on sanPham.sanPham_id = sanPhamChiTiet.sanPham_id
                  join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                  join size on size.size_id = sanPhamChiTiet.size_id
                  join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                  join nhaCungCap on nhaCungCap.nhaCC_id = sanPhamChiTiet.nhaCC_id
              where sPCT_id like ?
                
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
                        rs.getString("mota"),
                        rs.getInt("size"),
                        rs.getString("tenChatlieu"),
                        rs.getString("tenNhaCC"),
                        rs.getString("anh"),
                        rs.getString("tenMau"),
                        rs.getString("tenSanPham"));
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
              SELECT  [sPCT_id]
                	,sanPham.sanPham_id
                	,sanPham.[ten] [tenSanPham]
                	,sanPhamChiTiet.trangThai
                	,sanPhamChiTiet.moTa
                      ,[soLuong]
                      ,[donGia]
                      ,size.[giaTri] [size]
                      ,chatLieu.[ten] [tenChatlieu]
                      ,nhaCungCap.[ten] [tenNhaCC]
                      ,sanPhamChiTiet.anh
                      ,mauSac.[tenMau] [tenMau]
                  FROM [dbo].[sanPhamChiTiet] 
                  join sanPham on sanPham.sanPham_id = sanPhamChiTiet.sanPham_id
                  join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                  join size on size.size_id = sanPhamChiTiet.size_id
                  join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                  join nhaCungCap on nhaCungCap.nhaCC_id = sanPhamChiTiet.nhaCC_id
              where sanPham.sanPham_id like ?
                
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
                        rs.getString("mota"),
                        rs.getInt("size"),
                        rs.getString("tenChatlieu"),
                        rs.getString("tenNhaCC"),
                        rs.getString("anh"),
                        rs.getString("tenMau"),
                        rs.getString("tenSanPham"));
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // hàm này thực hiện phân trang sản phẩm chi tiết
    public List<SanPhamChiTiet> phanTrangSPCT(int tienLui, String sP_id) {
        sql = """
             SELECT  [sPCT_id]
                                                        	,sanPham.sanPham_id
                                                        	,sanPham.[ten] [tenSanPham]
                                                        	,sanPhamChiTiet.trangThai
                                                        	,sanPhamChiTiet.moTa
                                                              ,[soLuong]
                                                              ,[donGia]
                                                              ,size.[giaTri] [size]
                                                              ,chatLieu.[ten] [tenChatlieu]
                                                              ,nhaCungCap.[ten] [tenNhaCC]
                                                              ,sanPhamChiTiet.anh
                                                              ,mauSac.[tenMau] [tenMau]
                                                          FROM [dbo].[sanPhamChiTiet] 
                                                          join sanPham on sanPham.sanPham_id = sanPhamChiTiet.sanPham_id
                                                          join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                                                          join size on size.size_id = sanPhamChiTiet.size_id
                                                          join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                                                          join nhaCungCap on nhaCungCap.nhaCC_id = sanPhamChiTiet.nhaCC_id
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
                        rs.getString("tenChatlieu"),
                        rs.getString("tenNhaCC"),
                        rs.getString("anh"),
                        rs.getString("tenMau"),
                        rs.getString("tenSanPham"));
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int insert(SanPhamChiTiet entity) {
        sql = "INSERT INTO SanPhamChiTiet (sPCT_id, soLuong, donGia, size_id, chatLieu_id, nhaCC_id, anh, mauSac_id, sanPham_id, trangThai, moTa) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, entity.getsPCT_id());
            ps.setObject(2, entity.getSoLuong());
            ps.setObject(3, entity.getDonGia());
            ps.setObject(4, entity.getSize_id());
            ps.setObject(5, entity.getChatLieu_id());
            ps.setObject(6, entity.getNhaCC_id());
            ps.setObject(7, entity.getAnh());
            ps.setObject(8, entity.getMauSac_id());
            ps.setObject(9, entity.getSanPham_id());
            ps.setObject(10, entity.getTrangThai());
            ps.setObject(11, entity.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(String key, SanPhamChiTiet entity) {
        sql = "update sanPhamChiTiet set soLuong = ?, donGia = ?, size_id = ?, chatLieu_id = ?, nhaCC_id = ?,  anh = ?, mauSac_id = ?, sanPham_id = ?, trangThai = ?, moTa = ? where sPCT_id = ?";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, entity.getSoLuong());
            ps.setObject(2, entity.getDonGia());
            ps.setObject(3, entity.getSize_id());
            ps.setObject(4, entity.getChatLieu_id());
            ps.setObject(5, entity.getNhaCC_id());
            ps.setObject(6, entity.getAnh());
            ps.setObject(7, entity.getMauSac_id());
            ps.setObject(8, entity.getSanPham_id());
            ps.setObject(9, entity.getTrangThai());
            ps.setObject(10, entity.getMoTa());
            ps.setObject(11, key);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
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

}
