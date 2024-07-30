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
import utils.jdbcHelper;

/**
 *
 * @author Trong Phu
 */
public class MauSacDAO extends QLCHBG_DAO<MauSac, Integer> {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[mauSac]
                                         ([tenMau]
                                         ,[moTa])
                                   VALUES(?, ?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE [dbo].[mauSac]
                                 SET [tenMau] = ?
                                    ,[moTa] = ?
                               WHERE mauSac_id = ?
                              """;

    final String DELETE_SQL = """
                              
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT [mauSac_id]
                                        ,[tenMau]
                                        ,[moTa]
                                    FROM [dbo].[mauSac]
                                  """;

    final String SELECT_BY_TEN_MAU_SQL = """
                                    SELECT [mauSac_id]
                                          ,[tenMau]
                                          ,[moTa]
                                      FROM [dbo].[mauSac]
                                      WHERE tenMau LIKE ?
                                    """;

    final String SELECT_MAU_SAC_ID_BY_NAME_SQL = """
                                               SELECT [mauSac_id] 
                                               FROM [dbo].[mauSac]
                                               WHERE tenMau = ?
                                               """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [mauSac_id]
                                          ,[tenMau]
                                          ,[moTa]
                                      FROM [dbo].[mauSac]
                                      WHERE mauSac_id = ?
                                    """;

    @Override
    public void insert(MauSac entity) {
        jdbcHelper.update(INSERT_SQL, entity.getTenMauSac(), entity.getMoTa());
    }

    @Override
    public void update(MauSac entity) {
        jdbcHelper.update(UPADTE_SQL, entity.getTenMauSac(), entity.getMoTa(), entity.getMauSac_id());
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<MauSac> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    public MauSac selectByTenMau(String tenMau) {
        List<MauSac> list = selectBySQL(SELECT_BY_TEN_MAU_SQL, tenMau);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    public Integer selectMauSacIdByName(String name) {
        try {
            ResultSet rs = jdbcHelper.query(SELECT_MAU_SAC_ID_BY_NAME_SQL, name);
            if (rs.next()) {
                return rs.getInt("mauSac_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // hoặc có thể ném ra một ngoại lệ tùy thuộc vào cách bạn muốn xử lý lỗi
    }

    @Override
    public MauSac selectById(Integer id) {
        List<MauSac> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<MauSac> selectBySQL(String sql, Object... args) {
        List<MauSac> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);
            while (resultSet.next()) {
                MauSac entity = new MauSac();

                entity.setMauSac_id(resultSet.getInt("mauSac_id"));
                entity.setTenMauSac(resultSet.getString("tenMau"));
                entity.setMoTa(resultSet.getString("moTa"));

                list.add(entity);
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }

}
