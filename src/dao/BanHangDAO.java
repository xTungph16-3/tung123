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
      String  nhanVien_id = XLogin.user.getNhanVien_id();
        
        try {
            String sqlLocal = "select * from hoaDon where trangThai like N'Chờ thanh toán' and nhanVien_id like '" + nhanVien_id + "' order by ngayTaoHD desc";
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getString("hoaDon_id"));
                hd.setNgayTao(rs.getString("ngayTaoHD"));
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
                         select hoaDonChiTiet.hDCT_id,
                        hoaDon.hoaDon_id,
                        sanPhamChiTiet.sPCT_id,
                        sanPham.ten,
                        hoaDonChiTiet.soLuong,
                        hoaDonChiTiet.giaBan,
                        hoaDonChiTiet.thanhTien,
                        size.giaTri,
                        chatLieu.ten as tenChatLieu,
                        nhaCungCap.ten as tenNhaCC,
                        mauSac.tenMau,
                        hoaDonChiTiet.trangThaiHDCT
                        from hoaDonChiTiet
                                                                           join hoaDon on hoaDon.hoaDon_id = hoaDonChiTiet.hoaDon_id
                                                                           join sanPhamChiTiet on sanPhamChiTiet.sPCT_id = hoaDonChiTiet.sPCT_id
                                                                           join sanPham  on sanPhamChiTiet.sanPham_id = sanPham.sanPham_id
                                                                           join size on size.size_id = sanPhamChiTiet.size_id
                                                                           join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                                                                           join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                                                                           join nhaCungCap on nhaCungCap.nhaCC_id = sanPhamChiTiet.nhaCC_id 
                                                  where hoaDonChiTiet.hoaDon_id like ? and trangThaiHDCT like N'Chờ thanh toán'
                         """;
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            pstm.setObject(1, id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaSanPham(rs.getString("sPCT_id"));
                hdct.setTenSanPham(rs.getString("ten"));
                hdct.setSize(rs.getInt("giaTri"));
                hdct.setChatLieu(rs.getString("tenChatLieu"));
                hdct.setNhaCC(rs.getString("tenNhaCC"));
                hdct.setMauSac(rs.getString("tenMau"));
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
                         select hoaDonChiTiet.hDCT_id,
                        hoaDon.hoaDon_id,
                        sanPhamChiTiet.sPCT_id,
                        sanPham.ten,
                        hoaDonChiTiet.soLuong,
                        hoaDonChiTiet.giaBan,
                        hoaDonChiTiet.thanhTien,
                        size.giaTri,
                        chatLieu.ten as tenChatLieu,
                        nhaCungCap.ten as tenNhaCC,
                        mauSac.tenMau,
                        hoaDonChiTiet.trangThaiHDCT
                                                                           from hoaDonChiTiet
                                                                           join hoaDon on hoaDon.hoaDon_id = hoaDonChiTiet.hoaDon_id
                                                                           join sanPhamChiTiet on sanPhamChiTiet.sPCT_id = hoaDonChiTiet.sPCT_id
                                                                           join sanPham  on sanPhamChiTiet.sanPham_id = sanPham.sanPham_id
                                                                           join size on size.size_id = sanPhamChiTiet.size_id
                                                                           join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                                                                           join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                                                                           join nhaCungCap on nhaCungCap.nhaCC_id = sanPhamChiTiet.nhaCC_id 
                                                  where hoaDonChiTiet.hoaDon_id like ? and sanPhamChiTiet.sPCT_id like ? and trangThaiHDCT like N'Chờ thanh toán'
                         """;
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            pstm.setObject(1, hoaDon_id);
            pstm.setObject(2, sPCT_id);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHDCT(rs.getString("hDCT_id"));
                hdct.setMaSanPham(rs.getString("sPCT_id"));
                hdct.setTenSanPham(rs.getString("ten"));
                hdct.setSize(rs.getInt("giaTri"));
                hdct.setChatLieu(rs.getString("tenChatLieu"));
                hdct.setNhaCC(rs.getString("tenNhaCC"));
                hdct.setMauSac(rs.getString("tenMau"));
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

    public ArrayList<SanPhamChiTiet> selectSPCT() {
        ArrayList<SanPhamChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
                         select 
                         sanPhamChiTiet.sPCT_id,
                         sanPham.ten,
                         mauSac.tenMau,
                         chatLieu.ten,
                         size.giaTri,
                         sanPhamChiTiet.donGia,
                         sanPhamChiTiet.soLuong
                         from sanPhamChiTiet 
                         join sanPham on sanPham.sanPham_id = sanPhamChiTiet.sanPham_id 
                         join size on size.size_id = sanPhamChiTiet.size_id
                         join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                         join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                          where sanPham.trangThai like N'Đang bán'
                         """;
            Connection cn = DB_Connect.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sqlLocal);
            ResultSet rsLocal = pstm.executeQuery();
            while (rsLocal.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setsPCT_id(rsLocal.getString("sPCT_id"));
                spct.setSanPham(rsLocal.getString("ten"));
                spct.setMauSac(rsLocal.getString("tenMau"));
                spct.setChatLieu(rsLocal.getString(4));
                spct.setSize(rsLocal.getInt("giaTri"));
                spct.setDonGia(rsLocal.getBigDecimal("donGia"));
                spct.setSoLuong(rsLocal.getInt("soLuong"));
                lst.add(spct);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public ArrayList<SanPhamChiTiet> selectSPCT2(String key) {
        ArrayList<SanPhamChiTiet> lst = new ArrayList<>();
        try {
            String sqlLocal = """
                         select sanPhamChiTiet.sPCT_id, sanPham.ten, mauSac.tenMau, chatLieu.ten, size.giaTri, sanPhamChiTiet.donGia, sanPhamChiTiet.soLuong
                         from sanPhamChiTiet 
                         join sanPham on sanPham.sanPham_id = sanPhamChiTiet.sanPham_id
                         join size on size.size_id = sanPhamChiTiet.size_id
                         join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                         join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id 
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
                spct.setsPCT_id(rsLocal.getString("sPCT_id"));
                spct.setSanPham(rsLocal.getString("ten"));
                spct.setMauSac(rsLocal.getString("tenMau"));
                spct.setChatLieu(rsLocal.getString(4));
                spct.setSize(rsLocal.getInt("giaTri"));
                spct.setDonGia(rsLocal.getBigDecimal("donGia"));
                spct.setSoLuong(rsLocal.getInt("soLuong"));
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
        sql = "INSERT INTO hoaDon (hoaDon_id, nhanVien_id, tongTien, trangThai, khachHang_id, thanhToan_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, hDC.getMaHoaDon());
            ps.setObject(2, hDC.getMaNhanVien());
            ps.setObject(3, hDC.getTongTien());
            ps.setObject(4, hDC.getTrangThai());
            ps.setObject(5, hDC.getMaKhachHang());
            ps.setObject(6, hDC.getMaThanhToan());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // hàm này có chức năng thêm 1 hdct mới
    public int insertHoaDonCT(HoaDonChiTiet hDCT) {
        sql = "INSERT INTO hoaDonChiTiet (hDCT_id, hoaDon_id, sPCT_id, soLuong, giaBan, thanTien) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, hDCT.getMaHDCT());
            ps.setObject(2, hDCT.getMaHoaDon());
            ps.setObject(3, hDCT.getMaSanPham());
            ps.setObject(4, hDCT.getSoLuong());
            ps.setObject(5, hDCT.getGiaBan());
            ps.setObject(6, hDCT.getThanhTien());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    ///hàm này có chức năng thêm sản phẩm vào giỏ
    public int insertHoaDonCTPROC(HoaDonChiTiet hDCT) {
        sql = "{CALL ThemSanPhamVaoHoaDonChiTiet(?,?,?,?)}";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            if (hDCT != null) {
                ps.setObject(1, hDCT.getMaHDCT());
                ps.setObject(2, hDCT.getMaHoaDon());
                ps.setObject(3, hDCT.getMaSanPham());
                ps.setObject(4, hDCT.getSoLuong());
                // ps.setObject(5, hDCT.getGiaBan());
                //  ps.setObject(6, hDCT.getThanhTien());
                return ps.executeUpdate();
            } else {
                return 0;
            }
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
// hàm này có chức năng chỉnh sửa số lượng sản phẩm trong giỏ hàng

    public int suaSLSPTrongGio(String hoaDon_id, String hDCT_id, String sPCT_id, int soLuongSPThayDoi, int soLuongSPTrongGio) {
        sql = "{CALL CHINHSUASOLUONGSP(?,?,?,?,?)}";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, hoaDon_id);
            ps.setObject(2, hDCT_id);
            ps.setObject(3, sPCT_id);
            ps.setObject(4, soLuongSPThayDoi);
            ps.setObject(5, soLuongSPTrongGio);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    
    /// hàm này có tác dụng lấy ra số lượng của sản phẩm chi tiết theo mã sản phẩm chi tiết
    public SanPhamChiTiet selectSoLuongSPCTAnđonGia(String sPCT_id) {
        sql = """
              SELECT  [sPCT_id]
                    ,[soLuong]
                    ,[donGia]
                    ,[size_id]
                    ,[chatLieu_id]
                    ,[nhaCC_id]
                    ,[anh]
                    ,[mauSac_id]
                    ,[sanPham_id]
                FROM [dbo].[sanPhamChiTiet] where sPCT_id like ?
              """;
        int soLuong = 0;
        BigDecimal donGia;
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, sPCT_id);
            rs = ps.executeQuery();
             SanPhamChiTiet spct = new SanPhamChiTiet();
            while (rs.next()) {
                spct.setSoLuong(rs.getInt("soLuong"));
                spct.setDonGia(rs.getBigDecimal("donGia"));
                soLuong = spct.getSoLuong();
            }
            return spct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public List<SanPhamChiTiet> locSanPhamChiTiet(Integer mauSac_id, Integer size_id, Integer chatLieu_id) {
        sql = """
              select 
                                       sanPhamChiTiet.sPCT_id,
                                       sanPham.ten,
                                       mauSac.tenMau,
                                       chatLieu.ten,
                                       size.giaTri,
                                       sanPhamChiTiet.donGia,
                                       sanPhamChiTiet.soLuong
                                       from sanPhamChiTiet 
                                       join sanPham on sanPham.sanPham_id = sanPhamChiTiet.sanPham_id 
                                       join size on size.size_id = sanPhamChiTiet.size_id
                                       join mauSac on mauSac.mauSac_id = sanPhamChiTiet.mauSac_id
                                       join chatLieu on chatLieu.chatLieu_id = sanPhamChiTiet.chatLieu_id
                                        where sanPham.trangThai like N'Đang bán' and
                  
                  (sanPhamChiTiet.mauSac_id = ? OR ? IS NULL OR 1 = '')
                  AND (sanPhamChiTiet.size_id = ? OR ? IS NULL OR 1 = '')
                  AND (sanPhamChiTiet.chatLieu_id = ? OR ? IS NULL OR 1 = '');
              """;
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareCall(sql);
            ps.setObject(1, mauSac_id);
            ps.setObject(2, mauSac_id);
            ps.setObject(3, size_id);
            ps.setObject(4, size_id);
            ps.setObject(5, chatLieu_id);
            ps.setObject(6, chatLieu_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setsPCT_id(rs.getString("sPCT_id"));
                spct.setSanPham(rs.getString("ten"));
                spct.setMauSac(rs.getString("tenMau"));
                spct.setChatLieu(rs.getString(4));
                spct.setSize(rs.getInt("giaTri"));
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

    public ChatLieu selectByTenChatLieu(String tenChatLieu) {
        List<ChatLieu> lst = new ArrayList<>();
        sql = "select chatLieu_id, ten, nguongoc,mota from chatLieu where ten like ?";
        try {
            con = DB_Connect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tenChatLieu);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieu cl = new ChatLieu(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                lst.add(cl);
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
}
