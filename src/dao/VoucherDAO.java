/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Voucher;
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
public class VoucherDAO extends QLCHBG_DAO<Voucher, Integer>{

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    
    @Override
    public int insert(Voucher entity) {
        sql = """
              INSERT INTO khuyenMaiVoucher(tenVoucher, moTa, ngayBD, ngayKT, giamGia, giaToiDa, donToiThieu)
              VALUES(?, ?, ?, ?, ?, ?, ?)
              """;
        
        try {
            con = DB_Connect.getConnection();
            
            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getTen());
            ps.setObject(2, entity.getMoTa());
            ps.setObject(3, entity.getNgayBD());
            ps.setObject(4, entity.getNgayKT());
            ps.setObject(5, entity.getGiamGia());
            ps.setObject(6, entity.getGiaToiDa());
            ps.setObject(7, entity.getDonToiThieu());
            
            return ps.executeUpdate();                      
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        
    }

    @Override
    public int update(Integer key, Voucher entity) {
        sql = """
              UPDATE khuyenMaiVoucher
              SET tenVoucher = ?, moTa = ?, ngayBD = ?, ngayKT = ?, giamGia = ?, giaToiDa = ?, donToiThieu = ?
              WHERE voucher_id = ?
              """;
        
        try {
            con = DB_Connect.getConnection();
            
            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getTen());
            ps.setObject(2, entity.getMoTa());
            ps.setObject(3, entity.getNgayBD());
            ps.setObject(4, entity.getNgayKT());
            ps.setObject(5, entity.getGiamGia());
            ps.setObject(6, entity.getGiaToiDa());
            ps.setObject(7, entity.getDonToiThieu());            
            ps.setObject(8, entity.getVoucher_id());
            
            return ps.executeUpdate();            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        
    }

    @Override
    public int delete(Integer key) {
        sql = """
              DELETE khuyenMaiVoucher
              WHERE voucher_id = ?
              """;
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, key);
            return ps.executeUpdate();
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Voucher> selectAll() {
        List<Voucher> list_Voucher = new ArrayList<>();
        
        sql = """
              SELECT voucher_id, tenVoucher, moTa, ngayBD, ngayKT, giamGia, giaToiDa, donToiThieu 
              FROM khuyenMaiVoucher
              """;
        
        try {
            con = DB_Connect.getConnection();
            
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                int voucher_id = rs.getInt(1);
                String tenVoucher = rs.getString(2);
                String moTa = rs.getString(3);
                Date ngayBD = rs.getDate(4);
                Date ngayKT = rs.getDate(5);
                double giamGia = rs.getDouble(6);
                double giaToiDa = rs.getDouble(7);
                double donToiThieu = rs.getDouble(8);
                
                Voucher voucher = new Voucher(voucher_id, tenVoucher, moTa, ngayBD, ngayKT, giamGia, giaToiDa, donToiThieu);
                list_Voucher.add(voucher);
            }
            
            return list_Voucher;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
