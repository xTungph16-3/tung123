/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ChatLieu;
import entity.HoaDon;
import entity.HoaDonChiTiet;
import entity.KhachHang;
import entity.MauSac;
import entity.SanPhamChiTiet;
import entity.Size;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DB_Connect;
import utils.XLogin;

/**
 *
 * @author Trong Phu
 */
public class BanHangDAO {

    public ArrayList<HoaDon> selectHDCho() {

        ArrayList<HoaDon> lst = new ArrayList<>();

        String nhanVien_id = XLogin.user.getNhanVien_id();

        try {
            String sqlLocal = """
                                SELECT [hoaDon_id]
                                      ,[ngayTaoHD]
                                      ,[nhanVien_id]
                                      ,[tongTien]
                                      ,[trangThai]
                                      ,[khachHang_id]
                                      ,[thanhToan_id]
                                      ,[hanDoiTra]
                                      ,[ghiChu]
                                      ,[voucher_id]
                                  FROM [dbo].[hoaDon]
                                where trangThai = N'Chờ thanh toán' and nhanVien_id = ?
                                order by ngayTaoHD desc
                              """;

            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            pstm.setString(1, nhanVien_id);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                HoaDon hd = new HoaDon();

                hd.setMaHoaDon(rs.getString("hoaDon_id"));
                hd.setNgayTao(rs.getDate("ngayTaoHD"));
                hd.setTongTien(rs.getBigDecimal("tongTien"));
                hd.setMaNhanVien(rs.getString("nhanVien_id"));
                hd.setMaKhachHang(rs.getString("khachHang_id"));
                hd.setTrangThai(rs.getString("trangThai"));

                lst.add(hd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public ArrayList<HoaDonChiTiet> selectHDCT(String id) {
        ArrayList<HoaDonChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
                        SELECT	dbo.hoaDonChiTiet.hDCT_id,
                        		dbo.hoaDon.hoaDon_id,
                        		dbo.sanPhamChiTiet.sPCT_id AS sPCT_id ,
                        		dbo.sanPham.ten AS tenSanPham, 
                        		dbo.size.giaTri AS size, 
                        		dbo.mauSac.tenMau AS mauSac, 
                        		dbo.hoaDonChiTiet.soLuong, 
                        		dbo.hoaDonChiTiet.giaBan, 
                        		dbo.hoaDonChiTiet.thanhTien,
                        		dbo.hoaDonChiTiet.trangThaiHDCT
                        FROM     dbo.hoaDon 
                        INNER JOIN dbo.hoaDonChiTiet ON dbo.hoaDon.hoaDon_id = dbo.hoaDonChiTiet.hoaDon_id 
                        INNER JOIN dbo.sanPhamChiTiet ON dbo.hoaDonChiTiet.sPCT_id = dbo.sanPhamChiTiet.sPCT_id 
                        INNER JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
                        INNER JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
                        INNER JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
                        where hoaDonChiTiet.hoaDon_id like ? and trangThaiHDCT like N'Chờ thanh toán'
                         """;
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            pstm.setObject(1, id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();

                hdct.setSPCT_id(rs.getString("sPCT_id"));
                hdct.setTenSanPham(rs.getString("tenSanPham"));
                hdct.setSize(rs.getInt("size"));
                hdct.setMauSac(rs.getString("mauSac"));
                hdct.setGiaBan(rs.getBigDecimal("giaBan"));
                hdct.setSoLuong(rs.getInt("soLuong"));
                hdct.setThanhTien(rs.getBigDecimal("thanhTien"));
                hdct.setTrangThaiHDCT(rs.getString("trangThaiHDCT"));

                lst.add(hdct);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public HoaDonChiTiet selectHDCTByHD_idANDSPCT_id(String hoaDon_id, String sPCT_id) {
        List<HoaDonChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
                        SELECT          dbo.hoaDonChiTiet.hDCT_id AS hDCT_id,
                        		dbo.hoaDon.hoaDon_id AS hoaDon_id,
                        		dbo.sanPhamChiTiet.sPCT_id sPCT_id ,
                        		dbo.sanPham.ten AS tenSanPham, 
                        		dbo.size.giaTri AS size, 
                        		dbo.mauSac.tenMau AS mauSac, 
                        		dbo.hoaDonChiTiet.soLuong AS soLuong, 
                        		dbo.hoaDonChiTiet.giaBan AS giaBan, 
                        		dbo.hoaDonChiTiet.thanhTien AS thanhTien,
                        		dbo.hoaDonChiTiet.trangThaiHDCT AS trangThaiHDCT
                        FROM     dbo.hoaDon 
                        INNER JOIN dbo.hoaDonChiTiet ON dbo.hoaDon.hoaDon_id = dbo.hoaDonChiTiet.hoaDon_id 
                        INNER JOIN dbo.sanPhamChiTiet ON dbo.hoaDonChiTiet.sPCT_id = dbo.sanPhamChiTiet.sPCT_id 
                        INNER JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
                        INNER JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
                        INNER JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
                        where hoaDonChiTiet.hoaDon_id like ? and sanPhamChiTiet.sPCT_id like ? 
                              and trangThaiHDCT like N'Chờ thanh toán'
                         """;
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            pstm.setObject(1, hoaDon_id);
            pstm.setObject(2, sPCT_id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();

                hdct.setHDCT_id(rs.getString("hDCT_id"));
                hdct.setSPCT_id(rs.getString("sPCT_id"));
                hdct.setTenSanPham(rs.getString("tenSanPham"));
                hdct.setSize(rs.getInt("size"));
                hdct.setMauSac(rs.getString("mauSac"));
                hdct.setGiaBan(rs.getBigDecimal("giaBan"));
                hdct.setSoLuong(rs.getInt("soLuong"));
                hdct.setThanhTien(rs.getBigDecimal("thanhTien"));
                hdct.setTrangThaiHDCT(rs.getString("trangThaiHDCT"));

                lst.add(hdct);
            }

        } catch (Exception e) {
            System.out.println(e);
            // return null;
        }
        if (!lst.isEmpty()) {
            return lst.get(0);
        } else {
            return null;
        }
    }

    public ArrayList<SanPhamChiTiet> selectSPCT2(String key) {
        ArrayList<SanPhamChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
                        SELECT     dbo.sanPhamChiTiet.sPCT_id AS sPCT_id, 
                                    dbo.sanPham.ten AS tenSanPham, 
                                    dbo.mauSac.tenMau AS mauSac, 
                                    dbo.size.giaTri AS size, 
                                    dbo.sanPhamChiTiet.donGia AS donGia, 
                                    dbo.sanPhamChiTiet.soLuong AS soLuong, 
                                    dbo.chuongTrinhKhuyenMai.giamGia AS giamGia
                        FROM     dbo.chuongTrinhKhuyenMai 
                        INNER JOIN dbo.sanPham_chuongTrinhKhuyenMai ON dbo.chuongTrinhKhuyenMai.chuongTrinh_id = dbo.sanPham_chuongTrinhKhuyenMai.chuongTrinh_id 
                        INNER JOIN dbo.sanPhamChiTiet ON dbo.sanPham_chuongTrinhKhuyenMai.sPCT_id = dbo.sanPhamChiTiet.sPCT_id 
                        INNER JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
                        INNER JOIN dbo.mauSac ON dbo.sanPhamChiTiet.mauSac_id = dbo.mauSac.mauSac_id 
                        INNER JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id
                        WHERE
                             sanPham.trangThai LIKE N'Đang bán'
                             AND (sanPham.ten LIKE N'%' + ? + N'%' OR sanPhamChiTiet.sPCT_id LIKE N'%' + ? + N'%');""";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            pstm.setObject(1, key);
            pstm.setObject(2, key);
            ResultSet rsLocal = pstm.executeQuery();
            while (rsLocal.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();

                spct.setSPCT_id(rsLocal.getString("sPCT_id"));
                spct.setTenSanPham(rsLocal.getString("tenSanPham"));
                spct.setMauSac(rsLocal.getString("mauSac"));
                spct.setSize(rsLocal.getInt("size"));
                spct.setDonGia(rsLocal.getBigDecimal("donGia"));
                spct.setSoLuong(rsLocal.getInt("soLuong"));
                spct.setGiamGia(rsLocal.getInt("giamGia"));

                lst.add(spct);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    /// hàm này có chức năng tạo hoá đơn chờ
    Connection con = null;
    ResultSet rs = null;
    String sql = null;
    PreparedStatement ps = null;

    public int insertHoaDonCho(HoaDon hDC) {
        sql = """
              INSERT INTO [dbo].[hoaDon]
                                         ([hoaDon_id]
                                         ,[ngayTaoHD]
                                         ,[nhanVien_id]
                                         ,[trangThai]
                                         ,[khachHang_id])
                                   VALUES(?, ?, ?, ?, ?)
              """;
        try {
            con = DB_Connect.getConnection();

            ps = con.prepareCall(sql);

            ps.setObject(1, hDC.getMaHoaDon());
            ps.setObject(2, hDC.getNgayTao());
            ps.setObject(3, hDC.getMaNhanVien());
            ps.setObject(4, hDC.getTrangThai());
            ps.setObject(5, hDC.getMaKhachHang());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // hàm này có chức năng thêm 1 hdct mới
    public int insertHoaDonCT(HoaDonChiTiet hDCT) {
        sql = """
                   INSERT INTO [dbo].[hoaDon]
                                            ([hoaDon_id]
                                            ,[nhanVien_id]
                                            ,[tongTien]
                                            ,[trangThai]
                                            ,[khachHang_id]
                                            ,[thanhToan_id]
                                            ,[voucher_id])
                   VALUES ( ?, ?, ?, ?, ?, ?, ?)
              """;
        try {
            con = DB_Connect.getConnection();

            ps = con.prepareCall(sql);
            ps.setObject(1, hDCT.getHDCT_id());
            ps.setObject(2, hDCT.getHoaDon_id());
            ps.setObject(3, hDCT.getSPCT_id());
            ps.setObject(4, hDCT.getSoLuong());
            ps.setObject(5, hDCT.getGiaBan());
            ps.setObject(6, hDCT.getThanhTien());
            ps.setObject(7, hDCT.getVoucher_id());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /// hàm này có chức năng xoá sản phẩm khỏi giỏ hàng
    public int duaHDCTVeTrangThaiHuy(String hDCT_id, String hoaDon_id, String sPCT_id) {
        sql = "{CALL duaHDCTVeTrangThaiHuy(?,?,?)}";
        try {
            con = DB_Connect.getConnection();

            ps = con.prepareCall(sql);
            ps.setObject(1, hDCT_id);
            ps.setObject(2, hoaDon_id);
            ps.setObject(3, sPCT_id);

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // hàm này có chức năng huỷ 1 hoá đơn chính
    public int huyHoaDonCho(String hoaDon_id, String hDCT_id, String sPCT_id, String ghiChu) {
        sql = "{CALL HUYHOADON(?,?,?,?)}";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, hoaDon_id);
            ps.setObject(2, hDCT_id);
            ps.setObject(3, sPCT_id);
            ps.setObject(4, ghiChu);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /// thanh toán
    public int thanhToan(String hoaDon_id, BigDecimal tongTien, int maThanhToan, String maKH, String ghiChu) {
        sql = "{CALL THANHTOANHOADON(?,?,?,?,?)}";
        try {
            con = DB_Connect.getConnection();

            ps = con.prepareCall(sql);
            ps.setObject(1, hoaDon_id);
            ps.setObject(2, tongTien);
            ps.setObject(3, maThanhToan);
            ps.setObject(4, maKH);
            ps.setObject(5, ghiChu);

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public KhachHang selectKHbySDT(String sdt) {
        sql = """
             SELECT 
                        [khachHang].[khachHang_id],
                        [hoTenKH],
                        [gioiTinh],
                        [diaChi],
                        [sdt],
                        [email],
                        [khachHang].[ghiChu],
                        COUNT(hoaDon.khachHang_id) AS soLanMua,
                        [khachHang].[ngayTaoKH]
                    FROM 
                        [dbo].[khachHang] 
                    LEFT JOIN 
                        hoaDon ON hoaDon.khachHang_id = khachHang.khachHang_id 
                    where sdt = ?
                    GROUP BY 
                        [khachHang].[khachHang_id],
                        [hoTenKH],
                        [gioiTinh],
                        [diaChi],
                        [sdt],
                        [email],
                         [khachHang].[ghiChu],
                        [khachHang].[ngayTaoKH] ;
         """;
        List<KhachHang> listKH = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sdt);
            rs = ps.executeQuery();
            while (rs.next()) {
                listKH.add(new KhachHang(rs.getString("khachHang_id"),
                        rs.getString("hoTenKH"),
                        rs.getBoolean("gioiTinh"),
                        rs.getString("diaChi"),
                        rs.getString("sdt"),
                        rs.getString("email"),
                        rs.getString("ghiChu"),
                        rs.getDate("ngayTaoKH"),
                        rs.getInt("soLanMua")));
            }
            if (!listKH.isEmpty()) {
                return listKH.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SanPhamChiTiet> locSanPhamChiTiet(Integer mauSac_id, Integer size_id) {
        sql = """
                    SELECT      dbo.sanPhamChiTiet.sPCT_id AS sPCT_id , 
                                dbo.sanPham.ten AS tenSanPham, 
                                dbo.mauSac.tenMau AS mauSac, 
                                dbo.size.giaTri AS size, 
                                dbo.sanPhamChiTiet.donGia AS donGia, 
                                dbo.sanPhamChiTiet.soLuong AS soLuong
                    FROM     dbo.mauSac 
                    INNER JOIN dbo.sanPhamChiTiet ON dbo.mauSac.mauSac_id = dbo.sanPhamChiTiet.mauSac_id 
                    INNER JOIN dbo.sanPham ON dbo.sanPhamChiTiet.sanPham_id = dbo.sanPham.sanPham_id 
                    INNER JOIN dbo.size ON dbo.sanPhamChiTiet.size_id = dbo.size.size_id                    
                    where sanPham.trangThai like N'Đang bán' and (sanPhamChiTiet.mauSac_id = ? OR ? IS NULL OR 1 = '')
                    AND (sanPhamChiTiet.size_id = ? OR ? IS NULL OR 1 = '');
              """;
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();

            ps = con.prepareCall(sql);
            ps.setObject(1, mauSac_id);
            ps.setObject(2, mauSac_id);
            ps.setObject(3, size_id);
            ps.setObject(4, size_id);

            rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();

                spct.setSPCT_id(rs.getString("sPCT_id"));
                spct.setTenSanPham(rs.getString("tenSanPham"));
                spct.setMauSac(rs.getString("mauSac"));
                spct.setSize(rs.getInt("size"));
                spct.setDonGia(rs.getBigDecimal("donGia"));
                spct.setSoLuong(rs.getInt("soLuong"));
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Size selectByGiaTri(int size) {
        sql = "Select Size_id,giatri from size where giaTri = ? ";
        List<Size> lst = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, size);
            rs = ps.executeQuery();
            while (rs.next()) {
                Size sz = new Size(rs.getInt(1),
                        rs.getInt(2));
                lst.add(sz);
            }
            if (!lst.isEmpty()) {
                return lst.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public MauSac selectByTenMau(String tenMau) {
        sql = "select mausac_id, tenmau ,mota from MauSac where tenMau like ?";
        List<MauSac> lst = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tenMau);
            rs = ps.executeQuery();
            while (rs.next()) {
                MauSac ms = new MauSac(
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3)
                );
                lst.add(ms);
            }
            if (!lst.isEmpty()) {
                return lst.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public int updateTrangThaiHoaDon(String hoaDon_id, String trangThai) {

        String sql = """
                        UPDATE [dbo].[hoaDon]
                        SET [trangThai] = ?                
                        WHERE hoaDon_id = ?
                     """;
        try (Connection con = DB_Connect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ps.setString(2, hoaDon_id);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Cập nhật số lượng sản phẩm còn lại trong cơ sở dữ liệu và bảng tblSanPham
     *
     * @param spctId ID của sản phẩm chi tiết
     * @param soLuongBan Số lượng sản phẩm đã bán
     */
    
    public void updateSoLuongSanPham(String spctId, int soLuongBan) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            // Chuẩn bị câu lệnh SQL để cập nhật số lượng sản phẩm trong cơ sở dữ liệu
            String sqlUpdate = """
                                UPDATE [dbo].[sanPhamChiTiet]
                                   SET [soLuong] = soLuong - ?
                                 WHERE [sPCT_id] = ?
                               """;
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sqlUpdate);

            // Thiết lập giá trị cho các tham số của câu lệnh SQL
            ps.setInt(1, soLuongBan); // Trừ số lượng bán từ số lượng hiện tại
            ps.setString(2, spctId); // ID của sản phẩm chi tiết

            // Thực thi câu lệnh SQL
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
    }
}
