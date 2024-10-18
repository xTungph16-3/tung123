/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Brand;
import entity.ChatLieu;
import entity.LoaiDeGiay;
import entity.NhaCungCap;
import entity.SanPham;
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
public class SanPhamDAO {

    final String INSERT_SQL = """
                              INSERT INTO [dbo].[sanPham]
                                         ([sanPham_id]
                                         ,[ten]
                                         ,[moTa]
                                         ,[trangThai]
                                         ,[brand_id]
                                         ,[deGiay_id]
                                         ,[chatLieu_id]
                                         ,[nhaCC_id])
                                   VALUES(?, ?, ?, ?, ?, ?, ?, ?)
                              """;

    final String UPADTE_SQL = """
                              UPDATE [dbo].[sanPham]
                                 SET [ten] = ?
                                    ,[moTa] = ?
                                    ,[trangThai] = ?
                                    ,[brand_id] = ?
                                    ,[deGiay_id] = ?
                                    ,[chatLieu_id] = ?
                                    ,[nhaCC_id] = ?
                               WHERE sanPham_id = ?
                              """;

    final String REMOVE_SQL = """
                              UPDATE [dbo].[sanPham]
                                 SET [trangThai] = N'Ngừng bán'           
                               WHERE sanPham_id = ?
                              """;

    final String SELECT_BY_ID_SQL = """
                                    SELECT [sanPham_id]
                                          ,[ten]
                                          ,[moTa]
                                          ,[trangThai]
                                          ,[brand_id]
                                          ,[deGiay_id]
                                          ,[chatLieu_id]
                                          ,[nhaCC_id]
                                      FROM [dbo].[sanPham]
                                      WHERE sanPham_id = ?
                                    """;

    final String SELECT_BY_TEN_SQL = """
                                    SELECT [sanPham_id]
                                          ,[ten]
                                          ,[moTa]
                                          ,[trangThai]
                                          ,[brand_id]
                                          ,[deGiay_id]
                                          ,[chatLieu_id]
                                          ,[nhaCC_id]
                                      FROM [dbo].[sanPham]
                                      WHERE ten = ?
                                    """;

    final String SELECT_ALL_SQL = """
                                 
                                  """;

    public void insert(SanPham entity) {
        jdbcHelper.update(INSERT_SQL,
                entity.getSanPham_id(),
                entity.getTenSanPham(),
                entity.getMoTa(),
                entity.getTrangThai(),
                entity.getBrand().getBrand_id(),
                entity.getLoaiDeGiay().getDeGiay_id(),
                entity.getChatLieu().getChatLieu_id(),
                entity.getNhaCungCap().getNhaCC_id());
    }

    public void update(SanPham entity) {
        jdbcHelper.update(UPADTE_SQL, entity.getTenSanPham(),
                entity.getMoTa(),
                entity.getTrangThai(),
                entity.getBrand().getBrand_id(),
                entity.getLoaiDeGiay().getDeGiay_id(),
                entity.getChatLieu().getChatLieu_id(),
                entity.getNhaCungCap().getNhaCC_id(),
                entity.getSanPham_id());
    }

    public void delete(String id) {
        jdbcHelper.update(REMOVE_SQL, id);
    }

    public SanPham selectById(String id) {
        List<SanPham> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<SanPham> getAll() {
        List<SanPham> lst = new ArrayList<>();
        String sql = """
                    SELECT dbo.sanPham.sanPham_id,
                            dbo.sanPham.ten AS tenSP, 
                            dbo.sanPham.trangThai,
                            dbo.sanPham.moTa, 
                            dbo.brand.ten AS brand, 
                            dbo.loaiDeGiay.tenDe, 
                            dbo.chatLieu.ten AS chatLieu, 
                            dbo.nhaCungCap.ten AS tenNhaCC
                    FROM     dbo.brand 
                    INNER JOIN dbo.sanPham ON dbo.brand.brand_id = dbo.sanPham.brand_id 
                    INNER JOIN dbo.chatLieu ON dbo.sanPham.chatLieu_id = dbo.chatLieu.chatLieu_id 
                    INNER JOIN dbo.loaiDeGiay ON dbo.sanPham.deGiay_id = dbo.loaiDeGiay.deGiay_id 
                    INNER JOIN dbo.nhaCungCap ON dbo.sanPham.nhaCC_id = dbo.nhaCungCap.nhaCC_id
                     """;
        try {
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setSanPham_id(rs.getString("sanPham_id"));
                sp.setTenSanPham(rs.getString("tenSP"));
                sp.setTrangThai(rs.getString("trangThai"));
                sp.setMoTa(rs.getString("moTa"));

                Brand brand = new Brand();
                brand.setTenBrand(rs.getString("brand"));
                sp.setBrand(brand);

                LoaiDeGiay loaiDeGiay = new LoaiDeGiay();
                loaiDeGiay.setTenDeGiay(rs.getString("tenDe"));
                sp.setLoaiDeGiay(loaiDeGiay);

                ChatLieu chatLieu = new ChatLieu();
                chatLieu.setTenChatLieu(rs.getString("chatLieu"));
                sp.setChatLieu(chatLieu);

                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setTenNhaCC(rs.getString("tenNhaCC"));
                sp.setNhaCungCap(nhaCungCap);

                lst.add(sp);
            }
            rs.close();
            pstm.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    /////// hàm này có chức năng phân trang
    public List<SanPham> phanTrangSanPham(int tienLui) {
        List<SanPham> lst = new ArrayList<>();
        try {
            String sql = """
                SELECT dbo.sanPham.sanPham_id,
                        dbo.sanPham.ten AS tenSP, 
                        dbo.sanPham.trangThai,
                        dbo.sanPham.moTa, 
                        dbo.sanPham.ngayTao,
                        dbo.brand.ten AS brand, 
                        dbo.loaiDeGiay.tenDe, 
                        dbo.chatLieu.ten AS chatLieu, 
                        dbo.nhaCungCap.ten AS tenNhaCC
                FROM     dbo.brand 
                INNER JOIN dbo.sanPham ON dbo.brand.brand_id = dbo.sanPham.brand_id 
                INNER JOIN dbo.chatLieu ON dbo.sanPham.chatLieu_id = dbo.chatLieu.chatLieu_id 
                INNER JOIN dbo.loaiDeGiay ON dbo.sanPham.deGiay_id = dbo.loaiDeGiay.deGiay_id 
                INNER JOIN dbo.nhaCungCap ON dbo.sanPham.nhaCC_id = dbo.nhaCungCap.nhaCC_id
                ORDER BY dbo.sanPham.ngayTao DESC
                OFFSET ? ROWS
                FETCH NEXT 5 ROWS ONLY;
                """;
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, tienLui);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setSanPham_id(rs.getString("sanPham_id"));
                sp.setTenSanPham(rs.getString("tenSP"));
                sp.setTrangThai(rs.getString("trangThai"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setNgayTao(rs.getDate("ngayTao"));

                Brand brand = new Brand();
                brand.setTenBrand(rs.getString("brand"));
                sp.setBrand(brand);

                LoaiDeGiay loaiDeGiay = new LoaiDeGiay();
                loaiDeGiay.setTenDeGiay(rs.getString("tenDe"));
                sp.setLoaiDeGiay(loaiDeGiay);

                ChatLieu chatLieu = new ChatLieu();
                chatLieu.setTenChatLieu(rs.getString("chatLieu"));
                sp.setChatLieu(chatLieu);

                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setTenNhaCC(rs.getString("tenNhaCC"));
                sp.setNhaCungCap(nhaCungCap);

                lst.add(sp);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    ///tét
    public SanPham selectByName(String name) {
        List<SanPham> list = selectBySQL(SELECT_BY_TEN_SQL, name);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    // Hàm hỗ trợ thực hiện các truy vấn SELECT
    // Hàm hỗ trợ thực hiện các truy vấn SELECT
    private List<SanPham> selectBySQL(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try (Connection con = DB_Connect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham entity = new SanPham();

                entity.setSanPham_id(rs.getString("sanPham_id"));
                entity.setTenSanPham(rs.getString("ten"));
                entity.setMoTa(rs.getString("moTa"));
                entity.setTrangThai(rs.getString("trangThai"));

                Brand brand = new Brand();
                brand.setBrand_id(rs.getInt("brand_id"));
                entity.setBrand(brand);

                LoaiDeGiay loaiDeGiay = new LoaiDeGiay();
                loaiDeGiay.setDeGiay_id(rs.getInt("deGiay_id"));
                entity.setLoaiDeGiay(loaiDeGiay);

                ChatLieu chatLieu = new ChatLieu();
                chatLieu.setChatLieu_id(rs.getInt("chatLieu_id"));
                entity.setChatLieu(chatLieu);

                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setNhaCC_id(rs.getInt("nhaCC_id"));
                entity.setNhaCungCap(nhaCungCap);

                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<SanPham> selectAll(String key) {
        List<SanPham> lst = new ArrayList<>();
        try {
            String sql = """
                     SELECT dbo.sanPham.sanPham_id,
                             dbo.sanPham.ten AS tenSP, 
                             dbo.sanPham.trangThai,
                             dbo.sanPham.moTa, 
                             dbo.brand.ten AS brand, 
                             dbo.loaiDeGiay.tenDe, 
                             dbo.chatLieu.ten AS chatLieu, 
                             dbo.nhaCungCap.ten AS tenNhaCC
                     FROM     dbo.brand 
                     INNER JOIN dbo.sanPham ON dbo.brand.brand_id = dbo.sanPham.brand_id 
                     INNER JOIN dbo.chatLieu ON dbo.sanPham.chatLieu_id = dbo.chatLieu.chatLieu_id 
                     INNER JOIN dbo.loaiDeGiay ON dbo.sanPham.deGiay_id = dbo.loaiDeGiay.deGiay_id 
                     INNER JOIN dbo.nhaCungCap ON dbo.sanPham.nhaCC_id = dbo.nhaCungCap.nhaCC_id
                     WHERE dbo.sanPham.ten LIKE ?
                     """;

            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, "%" + key + "%");

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setSanPham_id(rs.getString("sanPham_id"));
                sp.setTenSanPham(rs.getString("tenSP"));
                sp.setTrangThai(rs.getString("trangThai"));
                sp.setMoTa(rs.getString("moTa"));

                Brand brand = new Brand();
                brand.setTenBrand(rs.getString("brand"));
                sp.setBrand(brand);

                LoaiDeGiay loaiDeGiay = new LoaiDeGiay();
                loaiDeGiay.setTenDeGiay(rs.getString("tenDe"));
                sp.setLoaiDeGiay(loaiDeGiay);

                ChatLieu chatLieu = new ChatLieu();
                chatLieu.setTenChatLieu(rs.getString("chatLieu"));
                sp.setChatLieu(chatLieu);

                NhaCungCap nhaCungCap = new NhaCungCap();
                nhaCungCap.setTenNhaCC(rs.getString("tenNhaCC"));
                sp.setNhaCungCap(nhaCungCap);

                lst.add(sp);
            }
            rs.close();
            pstm.close();
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

}
///Phần code này của thống kê

/*
    public int soLuongSPDangBan() {
        int i = 0;
        try {
            String sql = "SELECT COUNT(*) AS soLuongSanPhamDangBan FROM sanPham where trangThai like N'Đang bán';";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                i = rs.getInt("soLuongSanPhamDangBan");
            }
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }

    public int soLuongSPNgungBan() {
        int i = 0;
        try {
            String sql = "SELECT COUNT(*) AS soLuongSanPhamNgungBan FROM sanPham where trangThai like N'Ngừng bán';";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                i = rs.getInt("soLuongSanPhamNgungBan");
            }
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }

    public int soLuongSPTrongKho() {
        int i = 0;
        try {
            String sql = "select sum(soLuong) as tongHangTrongKho from sanPhamChiTiet ;";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                i = rs.getInt("tongHangTrongKho");
            }
            return i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return i;
    }
 */
