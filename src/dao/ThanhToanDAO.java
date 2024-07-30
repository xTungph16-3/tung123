/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ThanhToan;
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
public class ThanhToanDAO extends QLCHBG_DAO<ThanhToan, Integer> {
    final String INSERT_SQL = """
                              INSERT INTO [dbo].[thanhToan]([hinhThucThanhToan])
                              VALUES (?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE [dbo].[thanhToan]
                                 SET [hinhThucThanhToan] = ?
                               WHERE thanhToan_id = ?
                              """;

    final String DELETE_SQL = """
                              
                              """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [thanhToan_id],[hinhThucThanhToan]
                                    FROM [dbo].[thanhToan]
                                    WHERE thanhToan_id = ?
                                    """;

    final String SELECT_ALL_SQL = """
                                  SELECT [thanhToan_id]
                                        ,[hinhThucThanhToan]
                                    FROM [dbo].[thanhToan]
                                  """;

    @Override
    public void insert(ThanhToan entity) {
        jdbcHelper.update(INSERT_SQL, entity.getHinhThucThanhToan());
    }

    @Override
    public void update(ThanhToan entity) {
         jdbcHelper.update(UPADTE_SQL, entity.getHinhThucThanhToan(), entity.getThanhToan_id());
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<ThanhToan> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public ThanhToan selectById(Integer id) {
        List<ThanhToan> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ThanhToan> selectBySQL(String sql, Object... args) {
        List<ThanhToan> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);
            while (resultSet.next()) {
                ThanhToan entity = new ThanhToan();

                entity.setThanhToan_id(resultSet.getInt("thanhToan_id"));
                entity.setHinhThucThanhToan(resultSet.getString("hinhThucThanhToan"));

                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }

}
