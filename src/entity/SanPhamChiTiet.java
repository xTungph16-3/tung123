package entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Trong Phu
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SanPhamChiTiet {

    private String sPCT_id;

    private int soLuong;

    private BigDecimal donGia;

    private int size_id;

    private String anh;

    private int mauSac_id;

    private String sanPham_id;

    private String trangThai;

    private String moTa;

    private int size;

    private String mauSac;

    private int giamGia;
    
    private String tenCTKM;

    private String tenSanPham;

    private String tenChatLieu;

    private BigDecimal doanhThu;

    private BigDecimal giaBan;

    private int soLuongBan;

    private BigDecimal thanhTien;

    // Constructor liên quan đến join bảng để fill thông tin lên sản phẩm chi tiết
    public SanPhamChiTiet(String sPCT_id, int soLuong, BigDecimal donGia, String sanPham_id, String trangThai, String moTa, int size, String mauSac, int giamGia, String tenSanPham) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.sanPham_id = sanPham_id;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.size = size;
        this.mauSac = mauSac;
        this.giamGia = giamGia;
        this.tenSanPham = tenSanPham;
    }

    public SanPhamChiTiet(String sPCT_id, int soLuong, BigDecimal donGia, int size_id, String anh, int mauSac_id, String sanPham_id, String trangThai, String moTa, String tenCTKM) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.size_id = size_id;
        this.anh = anh;
        this.mauSac_id = mauSac_id;
        this.sanPham_id = sanPham_id;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.tenCTKM = tenCTKM;
    }
    
    public SanPhamChiTiet(String sPCT_id, int soLuong, BigDecimal donGia, String anh, String sanPham_id, String trangThai, String moTa, int size, String mauSac, int giamGia, String tenSanPham) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.anh = anh;
        this.sanPham_id = sanPham_id;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.size = size;
        this.mauSac = mauSac;
        this.giamGia = giamGia;
        this.tenSanPham = tenSanPham;
    }

    // Constructor liên quan đến selectALL()
    public SanPhamChiTiet(String sPCT_id, int soLuong, BigDecimal donGia, int size_id, String anh, int mauSac_id, String sanPham_id, String trangThai, String moTa) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.size_id = size_id;
        this.anh = anh;
        this.mauSac_id = mauSac_id;
        this.sanPham_id = sanPham_id;
        this.trangThai = trangThai;
        this.moTa = moTa;
    }

    // Constructor liên quan đến bảng thống kê
    public SanPhamChiTiet(String sPCT_id, int soLuong, String anh, String sanPham_id, String trangThai, String moTa, int size, String mauSac, String tenSanPham, BigDecimal doanhThu, int soLuongBan) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.anh = anh;
        this.sanPham_id = sanPham_id;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.size = size;
        this.mauSac = mauSac;
        this.tenSanPham = tenSanPham;
        this.doanhThu = doanhThu;
        this.soLuongBan = soLuongBan;
    }

    // Constructor liên quan đến bảng sản phẩm bán hàng
    public SanPhamChiTiet(int soLuong, BigDecimal donGia, String sanPham_id, int size, String mauSac, int giamGia, String tenSanPham) {
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.sanPham_id = sanPham_id;
        this.size = size;
        this.mauSac = mauSac;
        this.giamGia = giamGia;
        this.tenSanPham = tenSanPham;
    }

    // Constructor liên quan đến bảng giỏ hàng
    public SanPhamChiTiet(String sanPham_id, int size, String mauSac, String tenSanPham, String tenChatLieu, BigDecimal giaBan, int soLuongBan, BigDecimal thanhTien) {
        this.sanPham_id = sanPham_id;
        this.size = size;
        this.mauSac = mauSac;
        this.tenSanPham = tenSanPham;
        this.tenChatLieu = tenChatLieu;
        this.giaBan = giaBan;
        this.soLuongBan = soLuongBan;
        this.thanhTien = thanhTien;
    }

    public SanPhamChiTiet(String sPCT_id, int soLuong, BigDecimal donGia, String sanPham_id, String trangThai, String moTa, int size, String mauSac, String tenCTKM, String tenSanPham) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.sanPham_id = sanPham_id;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.size = size;
        this.mauSac = mauSac;
        this.tenCTKM = tenCTKM;
        this.tenSanPham = tenSanPham;
    }

    
}
