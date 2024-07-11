/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.SanPham;
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
public class SanPhamDAO extends QLCHBG_DAO<SanPham, String> {

    @Override
    public int insert(SanPham entity) {
        Integer row = null;
        try {
            String sql = "insert into sanPham(sanPham_id, ten, trangThai, moTa) values (?,?,?,?)";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, entity.getID());
            pstm.setString(2, entity.getTenSP());
            pstm.setString(3, entity.getTrangThai());
            pstm.setString(4, entity.getMoTa());
            row = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    @Override
    public int update(String key, SanPham entity) {
        Integer row = null;
        try {
            String sql = "update sanPham set ten = ?, trangThai = ?, moTa = ? where sanPham_id = ?";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(4, key);
            pstm.setString(1, entity.getTenSP());
            pstm.setString(2, entity.getTrangThai());
            pstm.setString(3, entity.getMoTa());
            return row = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }

    }

    @Override
    public int delete(String key) {
        Integer row = null;
        try {
            String sql = "delete from sanPham where sanPham_id = ?";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, key);
            return row = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }

    }

    @Override
    public List<SanPham> selectAll() {
        ArrayList<SanPham> lst = new ArrayList<>();
        try {
            String sql = "select * from sanPham";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setID(rs.getString("sanPham_id"));
                sp.setTenSP(rs.getString("ten"));
                sp.setTrangThai(rs.getString("trangThai"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setNgayTao(rs.getDate("ngayTao"));
                lst.add(sp);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }
/////// hàm này có chức năng phân trang

    public List<SanPham> phanTrangSanPham(int tienLui) {
        ArrayList<SanPham> lst = new ArrayList<>();
        try {
            String sql = """
                        SELECT * FROM sanPham
                        ORDER BY ngayTao desc
                        OFFSET ? ROWS
                        FETCH NEXT 5 ROWS ONLY;""";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, tienLui);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setID(rs.getString("sanPham_id"));
                sp.setTenSP(rs.getString("ten"));
                sp.setTrangThai(rs.getString("trangThai"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setNgayTao(rs.getDate("ngayTao"));
                lst.add(sp);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    ///tét
    public SanPham selectByName(String name) {
        ArrayList<SanPham> lst = new ArrayList<>();
        try {
            String sql = "select * from sanPham where ten like ?";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, name);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setID(rs.getString("sanPham_id"));
                sp.setTenSP(rs.getString("ten"));
                sp.setTrangThai(rs.getString("trangThai"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setNgayTao(rs.getDate("ngayTao"));
                lst.add(sp);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if(!lst.isEmpty()){
            return lst.get(0);
        }else{
            return null;
        }
        
    }

    public List<SanPham> selectAll2(String key) {
        ArrayList<SanPham> lst = new ArrayList<>();
        try {
            String sql = """
                     SELECT [sanPham_id]
                           ,[ten]
                           ,[moTa]
                           ,[trangThai]
                           ,[ngayTao]
                       FROM [dbo].[sanPham] WHERE ten LIKE N'%' + ? + N'%'
                     """;

            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, key);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setID(rs.getString("sanPham_id"));
                sp.setTenSP(rs.getString("ten"));
                sp.setTrangThai(rs.getString("trangThai"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setNgayTao(rs.getDate("ngayTao"));
                lst.add(sp);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }
    ///Phần code này của thống kê

    /*
    public int soLuongSPDangBan() {
        int i = 0;
        try {
            String sql = "SELECT COUNT(*) AS soLuongSanPhamDangBan FROM sanPham where trangThai like N'Đang bán';";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                i = rs.getInt("soLuongSanPhamDangBan");
            }
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }

    public int soLuongSPNgungBan() {
        int i = 0;
        try {
            String sql = "SELECT COUNT(*) AS soLuongSanPhamNgungBan FROM sanPham where trangThai like N'Ngừng bán';";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                i = rs.getInt("soLuongSanPhamNgungBan");
            }
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }

    public int soLuongSPTrongKho() {
        int i = 0;
        try {
            String sql = "select sum(soLuong) as tongHangTrongKho from sanPhamChiTiet ;";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                i = rs.getInt("tongHangTrongKho");
            }
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }
*/

}
