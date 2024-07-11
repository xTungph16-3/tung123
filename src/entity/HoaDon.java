/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;

/**
 *
 * @author Trong Phu
 */
public class HoaDon {

    private String maHoaDon;
    private String maNhanVien;
    private String tenNhanVien;
    private String maKhachHang;
    private String tenKhachHang;
    private BigDecimal tongTien;
    private int maThanhToan;
    private String hinhThucThanhToan;
    private String ngayTao;
    private String trangThai;
   
    int tongSoHD;
    String ghiChu;
    private String hanDoiTra;

    public String getHanDoiTra() {
        return hanDoiTra;
    }

    public void setHanDoiTra(String hanDoiTra) {
        this.hanDoiTra = hanDoiTra;
    }
    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getTongSoHD() {
        return tongSoHD;
    }

    public void setTongSoHD(int tongSoHD) {
        this.tongSoHD = tongSoHD;
    }
    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String maNhanVien, String maKhachHang, BigDecimal tongTien, int maThanhToan, String trangThai) {
        this.maHoaDon = maHoaDon;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.tongTien = tongTien;
        this.maThanhToan = maThanhToan;
        this.trangThai = trangThai;
    }
    
    

    public HoaDon(String maHoaDon, String maNhanVien, String tenNhanVien, String maKhachHang, String tenKhachHang, BigDecimal tongTien, String hinhThucThanhToan, String ngayTao, String trangThai, String ghiChu) {
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
    
    
    
    public HoaDon(String maHoaDon, String maNhanVien, String tenNhanVien, String maKhachHang, String tenKhachHang, BigDecimal tongTien, String hinhThucThanhToan, String ngayTao, String trangThai, String ghiChu, String hanDoiTra) {
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
        this.hanDoiTra = hanDoiTra;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public int getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(int maThanhToan) {
        this.maThanhToan = maThanhToan;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public Object[] toDaTaRow() {
        return new Object[]{this.maHoaDon,this.maNhanVien,this.tenNhanVien,this.maKhachHang,this.tenKhachHang,this.tongTien == null?"0.00":this.tongTien,this.hinhThucThanhToan,this.ngayTao,this.trangThai, this.ghiChu};
    }
}
