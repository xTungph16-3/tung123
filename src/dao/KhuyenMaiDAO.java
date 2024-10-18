/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.KhuyenMai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.DB_Connect;
import utils.jdbcHelper;

/**
 *
 * @author luuvi
 */
public class KhuyenMaiDAO extends QLCHBG_DAO<KhuyenMai, Integer> {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[chuongTrinhKhuyenMai]
                                         ([ten]
                                         ,[moTa]
                                         ,[ngayBD]
                                         ,[ngayKT]
                                         ,[giamGia]
                                         ,[trangThai])
                               VALUES (?, ?, ?, ?, ?, ?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE [dbo].[chuongTrinhKhuyenMai]
                                 SET [ten] = ?
                                    ,[moTa] = ?
                                    ,[ngayBD] = ?
                                    ,[ngayKT] = ?
                                    ,[giamGia] = ?
                                    ,[trangThai] = ?
                               WHERE chuongTrinh_id = ?
                              """;

    final String DELETE_SQL = """
                              UPDATE [dbo].[chuongTrinhKhuyenMai]
                              SET [trangThai] = 'Tạm ngừng'
                              WHERE chuongTrinh_id = ?
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT [chuongTrinh_id]
                                        ,[ten]
                                        ,[moTa]
                                        ,[ngayBD]
                                        ,[ngayKT]
                                        ,[giamGia]
                                        ,[trangThai]
                                    FROM [dbo].[chuongTrinhKhuyenMai]
                                  """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [chuongTrinh_id]
                                          ,[ten]
                                          ,[moTa]
                                          ,[ngayBD]
                                          ,[ngayKT]
                                          ,[giamGia]
                                          ,[trangThai]
                                    FROM [dbo].[chuongTrinhKhuyenMai]
                                    WHERE chuongTrinh_id = ?
                                    """;

    final String SELECT_BY_NAME_SQL = """
                                    SELECT [chuongTrinh_id]
                                          ,[ten]
                                          ,[moTa]
                                          ,[ngayBD]
                                          ,[ngayKT]
                                          ,[giamGia]
                                          ,[trangThai]
                                    FROM [dbo].[chuongTrinhKhuyenMai]
                                    WHERE ten LIKE ?
                                    """;

    @Override
    public void insert(KhuyenMai entity) {
        jdbcHelper.update(INSERT_SQL, entity.getTen(), entity.getMoTa(), entity.getNgayBD(),
                entity.getNgayKT(), entity.getGiamGia(), entity.getTrangThai());
    }

    @Override
    public void update(KhuyenMai entity) {
        jdbcHelper.update(UPADTE_SQL, entity.getTen(), entity.getMoTa(), entity.getNgayBD(),
                entity.getNgayKT(), entity.getGiamGia(), entity.getTrangThai(), entity.getChuongTrinh_id());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<KhuyenMai> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public KhuyenMai selectById(Integer id) {
        List<KhuyenMai> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public KhuyenMai selectByName(String ten) {
        List<KhuyenMai> list = selectBySQL(SELECT_BY_NAME_SQL, ten);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhuyenMai> selectBySQL(String sql, Object... args) {
        List<KhuyenMai> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);
            while (resultSet.next()) {
                KhuyenMai entity = new KhuyenMai();

                entity.setChuongTrinh_id(resultSet.getInt("chuongTrinh_id"));
                entity.setTen(resultSet.getString("ten"));
                entity.setMoTa(resultSet.getString("moTa"));
                entity.setNgayBD(resultSet.getDate("ngayBD"));
                entity.setNgayKT(resultSet.getDate("ngayKT"));
                entity.setGiamGia(resultSet.getDouble("giamGia"));
                entity.setTrangThai(resultSet.getString("trangThai"));

                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<KhuyenMai> phanTrangCTKM(int tienLui) {
        List<KhuyenMai> list = new ArrayList<>();
        try {
            String sql = """
                            SELECT [ten]
                                    ,[moTa]
                                    ,[ngayBD]
                                    ,[ngayKT]
                                    ,[giamGia]
                                    ,[trangThai]
                            FROM [dbo].[chuongTrinhKhuyenMai]
                            ORDER BY ngayBD DESC
                            OFFSET ? ROWS
                            FETCH NEXT 5 ROWS ONLY;
                         """;

            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, tienLui);
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                KhuyenMai entity = new KhuyenMai();

                entity.setTen(resultSet.getString("ten"));
                entity.setMoTa(resultSet.getString("moTa"));
                entity.setNgayBD(resultSet.getDate("ngayBD"));
                entity.setNgayKT(resultSet.getDate("ngayKT"));
                entity.setGiamGia(resultSet.getDouble("giamGia"));
                entity.setTrangThai(resultSet.getString("trangThai"));

                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
