/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Size;
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
public class SizeDAO extends QLCHBG_DAO<Size, String> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public List<Size> selectAll() {
        sql = "Select Size_id,giatri from size order by ngayTao DESC";
        List<Size> lst = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Size sz = new Size(rs.getInt(1),
                        rs.getInt(2));
                lst.add(sz);
            }
            return lst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int insert(Size entity) {
        sql = "insert into Size(giatri) values (?) ";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getGiatri());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(String key, Size entity) {
        sql = "Update Size set giatri = ? where size_id like ?";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getGiatri());
            ps.setObject(2, key);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String key) {
        sql = " delete Size where size_id =?";
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

    //phần code này ảnh hướng đến thêm spct -ntp
    public Size selectByGiaTri(int size) {
        sql = "Select Size_id,giatri from size where giaTri = ? ";
        List<Size> lst = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, size);
            rs = ps.executeQuery();
            while (rs.next()) {
                Size sz = new Size(rs.getInt(1),
                        rs.getInt(2));
                lst.add(sz);
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
