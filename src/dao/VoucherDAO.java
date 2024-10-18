/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Voucher;
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
public class VoucherDAO extends QLCHBG_DAO<Voucher, Integer> {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[khuyenMaiVoucher]
                                         ([tenVoucher]
                                         ,[moTa]
                                         ,[ngayBD]
                                         ,[ngayKT]
                                         ,[giamGia]
                                         ,[giaToiDa]
                                         ,[donToiThieu]
                                         ,[trangThai])
                                   VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE [dbo].[khuyenMaiVoucher]
                                 SET [tenVoucher] = ?
                                    ,[moTa] = ?
                                    ,[ngayBD] = ?
                                    ,[ngayKT] = ?
                                    ,[giamGia] = ?
                                    ,[giaToiDa] = ?
                                    ,[donToiThieu] = ?
                                    ,[trangThai] = ?
                               WHERE voucher_id = ?
                              """;

    final String DELETE_SQL = """
                                UPDATE [dbo].[khuyenMaiVoucher]
                                SET [trangThai] = 'Tạm dừng'
                                WHERE voucher_id = ?
                              """;

    final String SELECT_ALL_SQL = """
                                  SELECT [voucher_id]
                                        ,[tenVoucher]
                                        ,[moTa]
                                        ,[ngayBD]
                                        ,[ngayKT]
                                        ,[giamGia]
                                        ,[giaToiDa]
                                        ,[donToiThieu]
                                        ,[trangThai]
                                    FROM [dbo].[khuyenMaiVoucher]
                                  """;

    final String SELECT_ALL_Hoat_Dong_SQL = """
                                  SELECT [voucher_id]
                                        ,[tenVoucher]
                                        ,[moTa]
                                        ,[ngayBD]
                                        ,[ngayKT]
                                        ,[giamGia]
                                        ,[giaToiDa]
                                        ,[donToiThieu]
                                        ,[trangThai]
                                    FROM [dbo].[khuyenMaiVoucher]
                                    WHERE trangThai = N'Hoạt động'        
                                  """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [voucher_id]
                                          ,[tenVoucher]
                                          ,[moTa]
                                          ,[ngayBD]
                                          ,[ngayKT]
                                          ,[giamGia]
                                          ,[giaToiDa]
                                          ,[donToiThieu]
                                          ,[trangThai]
                                      FROM [dbo].[khuyenMaiVoucher]
                                    WHERE voucher_id = ?
                                    """;

    final String SELECT_GIAM_GIA_BY_TEN_SQL = """
                                    SELECT [giamGia]
                                      FROM [dbo].[khuyenMaiVoucher]
                                    WHERE tenVoucher = ? AND trangThai = N'Hoạt động'
                                    """;

    @Override
    public void insert(Voucher entity) {
        jdbcHelper.update(INSERT_SQL, entity.getTen(), entity.getMoTa(), entity.getNgayBD(), entity.getNgayKT(),
                entity.getGiamGia(), entity.getGiaToiDa(), entity.getDonToiThieu(), entity.getTrangThai());
    }

    @Override
    public void update(Voucher entity) {
        jdbcHelper.update(UPADTE_SQL, entity.getTen(), entity.getMoTa(), entity.getNgayBD(), entity.getNgayKT(),
                entity.getGiamGia(), entity.getGiaToiDa(), entity.getDonToiThieu(), entity.getTrangThai(), entity.getVoucher_id());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Voucher> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    public List<Voucher> selectAllHoatDong() {
        return selectBySQL(SELECT_ALL_Hoat_Dong_SQL);
    }

    @Override
    public Voucher selectById(Integer id) {
        List<Voucher> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public int selectGiamGiaByTen(String name) {
        int giamGia = 0; // Mặc định là 0 nếu không tìm thấy giảm giá

        try (Connection cn = DB_Connect.getConnection(); PreparedStatement pstm = cn.prepareStatement(SELECT_GIAM_GIA_BY_TEN_SQL)) {
            // Thiết lập tham số cho câu lệnh SQL
            pstm.setString(1, name);

            // Thực thi câu lệnh và nhận kết quả
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    giamGia = rs.getInt("giamGia");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return giamGia;
    }

    @Override
    public List<Voucher> selectBySQL(String sql, Object... args) {
        List<Voucher> list = new ArrayList<>();
        try {
            ResultSet resultSet = jdbcHelper.query(sql, args);
            while (resultSet.next()) {
                Voucher entity = new Voucher();

                entity.setVoucher_id(resultSet.getInt("voucher_id"));
                entity.setTen(resultSet.getString("tenVoucher"));
                entity.setMoTa(resultSet.getString("moTa"));
                entity.setNgayBD(resultSet.getDate("ngayBD"));
                entity.setNgayKT(resultSet.getDate("ngayKT"));
                entity.setGiamGia(resultSet.getDouble("giamGia"));
                entity.setGiaToiDa(resultSet.getDouble("giaToiDa"));
                entity.setDonToiThieu(resultSet.getDouble("donToiThieu"));
                entity.setTrangThai(resultSet.getString("trangThai"));

                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return list;
    }

    // Hàm này có chức năng phân trang Voucher
    public List<Voucher> phanTrangVoucher(int tienLui) {
        List<Voucher> list = new ArrayList<>();
        try {
            String sql = """
                         SELECT [tenVoucher]
                                ,[moTa]
                                ,[ngayBD]
                                ,[ngayKT]
                                ,[giamGia]
                                ,[giaToiDa]
                                ,[donToiThieu]
                                ,[trangThai]
                         FROM [dbo].[khuyenMaiVoucher]
                         ORDER BY ngayBD DESC
                         OFFSET ? ROWS
                         FETCH NEXT 5 ROWS ONLY;
                         """;
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, tienLui);
            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                Voucher entity = new Voucher();

                entity.setTen(resultSet.getString("tenVoucher"));
                entity.setMoTa(resultSet.getString("moTa"));
                entity.setNgayBD(resultSet.getDate("ngayBD"));
                entity.setNgayKT(resultSet.getDate("ngayKT"));
                entity.setGiamGia(resultSet.getDouble("giamGia"));
                entity.setGiaToiDa(resultSet.getDouble("giaToiDa"));
                entity.setDonToiThieu(resultSet.getDouble("donToiThieu"));
                entity.setTrangThai(resultSet.getString("trangThai"));

                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
