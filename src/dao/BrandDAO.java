/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Brand;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import utils.jdbcHelper;

/**
 *
 * @author luuvi
 */
public class BrandDAO extends QLCHBG_DAO<Brand, Integer> {

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[brand]
                                         ([ten]
                                         ,[congTy]
                                         ,[moTa])
                                   VALUES (? , ? , ?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE [dbo].[brand]
                                 SET [ten] = ?
                                    ,[congTy] = ?
                                    ,[moTa] = ?
                               WHERE brand_id = ?
                              """;

    final String DELETE_SQL = """
                              
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT [brand_id]
                                        ,[ten]
                                        ,[congTy]
                                        ,[moTa]
                                    FROM [dbo].[brand]
                                  """;

    final String SELECT_BRAND_ID_BY_NAME_SQL = """
                                               SELECT [brand_id]
                                               FROM [dbo].[brand]
                                               WHERE ten = ?
                                               """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [brand_id]
                                          ,[ten]
                                          ,[congTy]
                                          ,[moTa]
                                      FROM [dbo].[brand]
                                    WHERE brand_id = ?
                                    """;

    @Override
    public void insert(Brand entity) {
        jdbcHelper.update(INSERT_SQL, entity.getTenBrand(), entity.getCongTy(), entity.getMoTa());
    }

    @Override
    public void update(Brand entity) {
        jdbcHelper.update(UPADTE_SQL, entity.getTenBrand(), entity.getCongTy(), entity.getMoTa(), entity.getBrand_id());
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Brand> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public Brand selectById(Integer id) {
        List<Brand> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Integer selectBrandIdByName(String name) {
        try {
            ResultSet rs = jdbcHelper.query(SELECT_BRAND_ID_BY_NAME_SQL, name);
            if (rs.next()) {
                return rs.getInt("brand_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Brand> selectBySQL(String sql, Object... args) {
        List<Brand> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);

            while (resultSet.next()) {
                Brand entity = new Brand();

                entity.setBrand_id(resultSet.getInt("brand_id"));
                entity.setTenBrand(resultSet.getString("ten"));
                entity.setCongTy(resultSet.getString("congTy"));
                entity.setMoTa(resultSet.getString("moTa"));

                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }

}
