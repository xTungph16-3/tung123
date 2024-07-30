/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.LoaiDeGiay;
import java.util.ArrayList;
import java.util.List;
import utils.jdbcHelper;
import java.sql.ResultSet;

/**
 *
 * @author luuvi
 */
public class LoaiDeGiayDAO extends QLCHBG_DAO<LoaiDeGiay, Integer> {

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[loaiDeGiay]
                                         ([tenDe]
                                         ,[chieuCao])
                                   VALUES (?, ?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE [dbo].[loaiDeGiay]
                                 SET [tenDe] = ?
                                    ,[chieuCao] = ?
                               WHERE deGiay_id = ?
                              """;

    final String DELETE_SQL = """
                              
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT [deGiay_id]
                                        ,[tenDe]
                                        ,[chieuCao]
                                    FROM [dbo].[loaiDeGiay]
                                  """;

    final String SELECT_LOAI_DE_GIAY_ID_BY_NAME_SQL = """
                                                        SELECT [deGiay_id]
                                                        FROM [dbo].[loaiDeGiay]
                                                         WHERE tenDe = ?
                                                         """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [deGiay_id]
                                          ,[tenDe]
                                          ,[chieuCao]
                                      FROM [dbo].[loaiDeGiay]
                                    WHERE deGiay_id = ?
                                    """;

    @Override
    public void insert(LoaiDeGiay entity) {
        jdbcHelper.update(INSERT_SQL, entity.getTenDeGiay(), entity.getChieuCao());
    }

    @Override
    public void update(LoaiDeGiay entity) {
        jdbcHelper.update(UPADTE_SQL, entity.getTenDeGiay(), entity.getChieuCao(), entity.getDeGiay_id());
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<LoaiDeGiay> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public LoaiDeGiay selectById(Integer id) {
        List<LoaiDeGiay> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Integer selectLoaiDeGiayIdByName(String name) {
        try {
            ResultSet rs = jdbcHelper.query(SELECT_LOAI_DE_GIAY_ID_BY_NAME_SQL, name);
            if (rs.next()) {
                return rs.getInt("deGiay_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // hoặc có thể ném ra một ngoại lệ tùy thuộc vào cách bạn muốn xử lý lỗi
    }

    @Override
    public List<LoaiDeGiay> selectBySQL(String sql, Object... args) {
        List<LoaiDeGiay> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);

            while (resultSet.next()) {
                LoaiDeGiay entity = new LoaiDeGiay();

                entity.setDeGiay_id(resultSet.getInt("deGiay_id"));
                entity.setTenDeGiay(resultSet.getString("tenDe"));
                entity.setChieuCao(resultSet.getInt("chieuCao"));

                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }
}
