/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class HoaDon {

    private String maHoaDon;

    private String maNhanVien;

    private String tenNhanVien;

    private String maKhachHang;

    private String tenKhachHang;

    private BigDecimal tongTien;

    private int maThanhToan;

    private String hinhThucThanhToan;

    private Date ngayTao;

    private String trangThai;

    private Integer giamGiaKhuyenMai;

    private Integer giamGiaVoucher;

    private Integer voucher_id;

    int tongSoHD;

    String ghiChu;

    public HoaDon(String maHoaDon, String maNhanVien, String maKhachHang, Date ngayTao, String trangThai) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    public HoaDon(String maHoaDon, String maNhanVien, String maKhachHang, BigDecimal tongTien, int maThanhToan, String trangThai) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.tongTien = tongTien;
        this.maThanhToan = maThanhToan;
        this.trangThai = trangThai;
    }

    public HoaDon(String maHoaDon, String maNhanVien, String tenNhanVien, String maKhachHang, String tenKhachHang, BigDecimal tongTien, String hinhThucThanhToan, Date ngayTao, String trangThai, String ghiChu) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.tongTien = tongTien;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public Object[] toDaTaRow() {
        return new Object[]{
            this.maHoaDon,
            this.maNhanVien,
            this.tenNhanVien,
            this.maKhachHang,
            this.tenKhachHang,
            this.tongTien == null ? "0.00" : this.tongTien,
            this.hinhThucThanhToan,
            this.ngayTao,
            this.trangThai,
            this.ghiChu
        };
    }
}
