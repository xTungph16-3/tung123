/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.NhaCungCap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.jdbcHelper;

/**
 *
 * @author Trong Phu
 */
public class NhaCungCapDAO extends QLCHBG_DAO<NhaCungCap, Integer> {

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[nhaCungCap]
                                         ([ten]
                                         ,[moTa]
                                         ,[diaChi]
                                   VALUES(?, ?, ?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE [dbo].[nhaCungCap]
                                  SET [ten] = ?
                                     ,[moTa] = ?
                                     ,[diaChi] = ?
                                WHERE nhaCC_id = ?
                              """;

    final String DELETE_SQL = """
                              
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT [nhaCC_id]
                                          ,[ten]
                                          ,[moTa]
                                          ,[diaChi]
                                      FROM [dbo].[nhaCungCap]
                                  """;

    final String SELECT_NHA_CC_ID_BY_NAME_SQL = """
                                               SELECT [nhaCC_id]
                                               FROM [dbo].[nhaCungCap]
                                               WHERE ten = ?
                                               """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [nhaCC_id]
                                          ,[ten]
                                          ,[moTa]
                                          ,[diaChi]
                                      FROM [dbo].[nhaCungCap]
                                    WHERE nhaCC_id = ?
                                    """;

    @Override
    public void insert(NhaCungCap entity) {
        jdbcHelper.update(INSERT_SQL, entity.getTenNhaCC(), entity.getMoTa(), entity.getDiaChi());
    }

    @Override
    public void update(NhaCungCap entity) {
        jdbcHelper.update(UPADTE_SQL, entity.getTenNhaCC(), entity.getMoTa(), entity.getDiaChi(), entity.getNhaCC_id());
    }

    @Override
    public void delete(Integer id) {
    }

    @Override
    public List<NhaCungCap> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public NhaCungCap selectById(Integer id) {
        List<NhaCungCap> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Integer selectNhaCCIdByName(String name) {
        try {
            ResultSet rs = jdbcHelper.query(SELECT_NHA_CC_ID_BY_NAME_SQL, name);
            if (rs.next()) {
                return rs.getInt("nhaCC_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<NhaCungCap> selectBySQL(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);
            while (resultSet.next()) {
                NhaCungCap entity = new NhaCungCap();

                entity.setNhaCC_id(resultSet.getInt("nhaCC_id"));
                entity.setTenNhaCC(resultSet.getString("ten"));
                entity.setMoTa(resultSet.getString("moTa"));
                entity.setDiaChi(resultSet.getString("diaChi"));

                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
