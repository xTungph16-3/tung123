/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.MauSac;
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
public class MauSacDAO extends QLCHBG_DAO<MauSac, String> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public int insert(MauSac entity) {
        sql = "insert into MauSac(tenmau,mota) values (?,?)";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getTenMau());
            ps.setObject(2, entity.getMoTa());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(String key, MauSac entity) {
        sql = "Update MauSac set tenmau = ?,  mota = ? where mauSac_id = ?";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getTenMau());
            ps.setObject(2, entity.getMoTa());
            ps.setObject(3, key);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String key) {
        sql = "delete MauSac where mausac_id = ?";
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
    public List<MauSac> selectAll() {
        sql = "select mausac_id, tenmau ,mota from MauSac order by ngayTao DESC";
        List<MauSac> lst = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MauSac ms = new MauSac(
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3)
                );
                lst.add(ms);
            }
            return lst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //phần code này ảnh hướng đến thêm spct -ntp
    public MauSac selectByTenMau(String tenMau) {
        sql = "select mausac_id, tenmau ,mota from MauSac where tenMau like ?";
        List<MauSac> lst = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tenMau);
            rs = ps.executeQuery();
            while (rs.next()) {
                MauSac ms = new MauSac(
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3)
                );
                lst.add(ms);
            }
            if (!lst.isEmpty()) {
                return lst.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
