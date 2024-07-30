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
import utils.jdbcHelper;

/**
 *
 * @author Trong Phu
 */
public class ChatLieuDAO extends QLCHBG_DAO<ChatLieu, Integer> {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[chatLieu]
                                         ([ten]
                                         ,[nguonGoc]
                                         ,[moTa]
                                   VALUES (?, ?, ?)
                              """;

    final String UPDATE_SQL = """
                              UPDATE [dbo].[chatLieu]
                                 SET [ten] = ?
                                    ,[nguonGoc] = ?
                                    ,[moTa] = ?
                               WHERE chatLieu_id =?
                              """;

    final String DELETE_SQL = """
                              DELETE FROM chatLieu
                              WHERE chatLieu_id = ?
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT * FROM chatLieu
                                  """;

    final String SELECT_BY_TENCL_SQL = """
                                    SELECT * FROM chatLieu
                                    WHERE ten LIKE ?
                                    """;

    final String SELECT_CHATLIEU_ID_BY_NAME_SQL = """
                                               SELECT [chatLieu_id]
                                                 FROM [dbo].[chatLieu]
                                                 WHERE ten = ?
                                               """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT * FROM chatLieu
                                    WHERE chatLieu_id = ?
                                    """;

    @Override
    public void insert(ChatLieu entity) {
        jdbcHelper.update(INSERT_SQL, entity.getTenChatLieu(), entity.getNguonGoc(), entity.getMota());
    }

    @Override
    public void update(ChatLieu entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getTenChatLieu(), entity.getNguonGoc(), entity.getMota(), entity.getChatLieu_id());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<ChatLieu> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public ChatLieu selectById(Integer id) {
        List<ChatLieu> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Integer selectChatLieuIdByName(String name) {
        try {
            ResultSet rs = jdbcHelper.query(SELECT_CHATLIEU_ID_BY_NAME_SQL, name);
            if (rs.next()) {
                return rs.getInt("chatLieu_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ChatLieu> selectBySQL(String sql, Object... args) {
        List<ChatLieu> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);
            while (resultSet.next()) {
                ChatLieu entity = new ChatLieu();

                entity.setChatLieu_id(resultSet.getInt("chatLieu_id"));
                entity.setTenChatLieu(resultSet.getString("ten"));
                entity.setNguonGoc(resultSet.getString("nguonGoc"));
                entity.setMota(resultSet.getString("moTa"));

                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }

    public ChatLieu selectByTenCL(String tenCL) {
        List<ChatLieu> list = selectBySQL(SELECT_BY_TENCL_SQL, tenCL);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
