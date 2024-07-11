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
public class HoaDonChiTietDAO extends QLCHBG_DAO<HoaDonChiTiet, String> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public List<HoaDonChiTiet> selectAll() {
        List<HoaDonChiTiet> list2 = new ArrayList<>();
        sql = "select * from hoaDonChiTiet order by hoaDon_id";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBigDecimal(5), rs.getBigDecimal(6), rs.getString(7));
                list2.add(hdct);
            }
            return list2;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return null;
    }

    public List<HoaDonChiTiet> selectHDCTByHoaDon_id(String hoaDon_id) {
        List<HoaDonChiTiet> list2 = new ArrayList<>();
        sql = "select * from hoaDonChiTiet where hoaDon_id like ? and trangThaiHDCT like N'Hoàn thành'";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, hoaDon_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBigDecimal(5), rs.getBigDecimal(6), rs.getString(7));
                list2.add(hdct);
            }
            return list2;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return null;
    }

    @Override
    public int insert(HoaDonChiTiet entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int update(String key, HoaDonChiTiet entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<HoaDonChiTiet> selectHDCT(String id) {
        ArrayList<HoaDonChiTiet> lst = new ArrayList<>();
        try {
             sql = """
                         select hoaDonChiTiet.hDCT_id,
                        hoaDon.hoaDon_id,
                        sanPhamChiTiet.sPCT_id,
                        sanPham.ten,
                        hoaDonChiTiet.soLuong,
                        hoaDonChiTiet.giaBan,
                        hoaDonChiTiet.thanhTien,
                        size.giaTri,
                        chatLieu.ten as tenChatLieu,
                        nhaCungCap.ten as tenNhaCC,
                        mauSac.tenMau,
                        hoaDonChiTiet.trangThaiHDCT
                        from hoaDonChiTiet
                                                                           join hoaDon on hoaDon.hoaDon_id = hoaDonChiTiet.hoaDon_id
                                                                           join sanPhamChiTiet on sanPhamChiTiet.sPCT_id = hoaDonChiTiet.sPCT_id
                                                                           join sanPham  on sanPhamChiTiet.sanPham_id = sanPham.sanPham_id
                                                                           join size on size.size_id = sanPhamChiTiet.size_id
                                                                           join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                                                                           join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                                                                           join nhaCungCap on nhaCungCap.nhaCC_id = sanPhamChiTiet.nhaCC_id 
                                                  where hoaDonChiTiet.hoaDon_id like ? and (trangThaiHDCT like N'Hoàn thành' or trangThaiHDCT like N'Chờ thanh toán' or trangThaiHDCT like N'Đã huỷ')
                         """;
             con = DB_Connect.getConnection();
             ps = con.prepareStatement(sql);
            ps.setObject(1, id);
             rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHDCT(rs.getString("hDCT_id"));
                hdct.setMaSanPham(rs.getString("sPCT_id"));
                hdct.setTenSanPham(rs.getString("ten"));
                hdct.setSize(rs.getInt("giaTri"));
                hdct.setChatLieu(rs.getString("tenChatLieu"));
                hdct.setNhaCC(rs.getString("tenNhaCC"));
                hdct.setMauSac(rs.getString("tenMau"));
                hdct.setGiaBan(rs.getBigDecimal("giaBan"));
                hdct.setSoLuong(rs.getInt("soLuong"));
                hdct.setThanhTien(rs.getBigDecimal("thanhTien"));
                hdct.setTrangThaiHDCT(rs.getString("trangThaiHDCT"));
                lst.add(hdct);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

}
