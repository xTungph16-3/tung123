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
import utils.jdbcHelper;

/**
 *
 * @author Trong Phu
 */
public class SizeDAO extends QLCHBG_DAO<Size, Integer> {

    final String INSERT_SQL = """
                              INSERT INTO size(giaTri)
                              VALUES (?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE size
                              SET giaTri = ?
                              WHERE size_id = ?
                              """;

    final String DELETE_SQL = """
                                DELETE FROM size
                                WHERE size_id = ?
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT * FROM size
                                  """;

    final String SELECT_BY_GIATRI_SQL = """
                                    SELECT * FROM size
                                    WHERE  giaTri = ?
                                    """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT * FROM size
                                    WHERE size_id = ?
                                    """;

    @Override
    public void insert(Size entity) {
        jdbcHelper.update(INSERT_SQL, entity.getGiatri());
    }

    @Override
    public void update(Size entity) {
        jdbcHelper.update(UPADTE_SQL, entity.getGiatri(), entity.getSize_id());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Size> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    public Size selectByGiaTri(Integer giaTri) {
        List<Size> list = selectBySQL(SELECT_BY_GIATRI_SQL, giaTri);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Size selectById(Integer id) {
        List<Size> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Size> selectBySQL(String sql, Object... args) {
        List<Size> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);
            while (resultSet.next()) {
                Size entity = new Size();

                entity.setSize_id(resultSet.getInt("size_id"));
                entity.setGiatri(resultSet.getInt("giaTri"));

                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }

}
