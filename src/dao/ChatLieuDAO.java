/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ChatLieu;
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
public class ChatLieuDAO extends QLCHBG_DAO<ChatLieu, String> {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    @Override
    public List<ChatLieu> selectAll() {
        List<ChatLieu> lst = new ArrayList<>();
        sql = "select chatLieu_id, ten, nguongoc,mota from chatLieu order by ngayTao DESC";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieu cl = new ChatLieu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                lst.add(cl);

            }
            return lst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int insert(ChatLieu entity) {
        sql = "insert into ChatLieu(ten,nguongoc,mota) values (?,?,?)";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getTenCL());
            ps.setObject(2, entity.getNguonGoc());
            ps.setObject(3, entity.getMota());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(String key, ChatLieu entity) {
        sql = "Update chatLieu set ten = ?, nguonGoc = ?, mota =? where chatLieu_id = ?";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, entity.getTenCL());
            ps.setObject(2, entity.getNguonGoc());
            ps.setObject(3, entity.getMota());
            ps.setObject(4, key);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int delete(String key) {
        sql = "delete ChatLieu where chatLieu_id = ? ";
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

    //phần code này ảnh hưởng đến thêm spct - ntp
    public ChatLieu selectByTenChatLieu(String tenChatLieu) {
        List<ChatLieu> lst = new ArrayList<>();
        sql = "select chatLieu_id, ten, nguongoc,mota from chatLieu where ten like ?";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tenChatLieu);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieu cl = new ChatLieu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                lst.add(cl);
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
