/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.KhuyenMai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.DB_Connect;

/**
 *
 * @author luuvi
 */
public class KhuyenMaiDAO extends QLCHBG_DAO<KhuyenMai, Integer> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public List<KhuyenMai> selectAll() {
        List<KhuyenMai> list_KM = new ArrayList<>();
        sql = """
              SELECT chuongTrinh_id, ten, moTa, ngayBD, ngayKT, giamGia
              FROM chuongTrinhKhuyenMai
              """;
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int chuongTrinh_id = rs.getInt(1);
                String ten = rs.getString(2);
                String moTa = rs.getString(3);
                Date ngayBD = rs.getDate(4);
                Date ngayKT = rs.getDate(5);
                double giamGia = rs.getDouble(6);

                KhuyenMai km = new KhuyenMai(chuongTrinh_id, ten, moTa, ngayBD, ngayKT, chuongTrinh_id);
                list_KM.add(km);
            }
            return list_KM;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int insert(KhuyenMai entity) {
        sql = """
              INSERT INTO chuongTrinhKhuyenMai(ten, moTa, ngayBD, ngayKT, giamGia)
              VALUES(?, ?, ?, ?, ?)
              """;
        try {
            con = DB_Connect.getConnection();

            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getTen());
            ps.setObject(2, entity.getMoTa());
            ps.setObject(3, entity.getNgayBD());
            ps.setObject(4, entity.getNgayKT());
            ps.setObject(5, entity.getGiamGia());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Integer key, KhuyenMai entity) {
        sql = """
              UPDATE chuongTrinhKhuyenMai
              SET ten= ?, moTa = ?, ngayBD = ?, ngayKT = ?, giamGia = ?
              WHERE chuongTrinh_id = ?
              """;
        try {
            con = DB_Connect.getConnection();
            
            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getTen());
            ps.setObject(2, entity.getMoTa());
            ps.setObject(3, entity.getNgayBD());
            ps.setObject(4, entity.getNgayKT());
            ps.setObject(5, entity.getGiamGia());
            ps.setObject(6, key);
            
            return ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(Integer key) {
        sql = """
              DELETE chuongTrinhKhuyenMai
              WHERE chuongTrinh_id = ?
              """;
        try {
            con = DB_Connect.getConnection();
            
            ps = con.prepareStatement(sql);
            ps.setObject(1, key);
            
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
