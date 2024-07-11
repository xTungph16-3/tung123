/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Trong Phu
 */
public class TraHang {

    private int traHang_id;
    private String hoaDon_id;
    private String sPCT_id;
    private String nhanVien_id;
    private int soLuongTra;
    private BigDecimal giaTra;
    private BigDecimal tongTienHoanTra;
    private String ngayTraHang;
    private String lyDoTraHang;
    private String trangThai;

    public String getNhanVien_id() {
        return nhanVien_id;
    }

    public void setNhanVien_id(String nhanVien_id) {
        this.nhanVien_id = nhanVien_id;
    }
    
    
    private String tenSP;

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    private String tenNCC;
    private String mauSac;
    private int size;
    private String chatLieu;
    private int soLuong;
   // private BigDecimal thanhTien;

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

//    public BigDecimal getThanhTien() {
//        return thanhTien;
//    }
//
//    public void setThanhTien(BigDecimal thanhTien) {
//        this.thanhTien = thanhTien;
//    }
    

    public TraHang() {
    }

    public TraHang(int traHang_id, String hoaDon_id, String sPCT_id,String nhanVien_id, int soLuongTra, BigDecimal giaTra, BigDecimal tongTienHoanTra, String ngayTraHang, String lyDoTraHang, String trangThai) {
        this.traHang_id = traHang_id;
        this.hoaDon_id = hoaDon_id;
        this.sPCT_id = sPCT_id;
        this.nhanVien_id = nhanVien_id;
        this.soLuongTra = soLuongTra;
        this.giaTra = giaTra;
        this.tongTienHoanTra = tongTienHoanTra;
        this.ngayTraHang = ngayTraHang;
        this.lyDoTraHang = lyDoTraHang;
        this.trangThai = trangThai;
    }

    public int getTraHang_id() {
        return traHang_id;
    }

    public void setTraHang_id(int traHang_id) {
        this.traHang_id = traHang_id;
    }

    public String getHoaDon_id() {
        return hoaDon_id;
    }

    public void setHoaDon_id(String hoaDon_id) {
        this.hoaDon_id = hoaDon_id;
    }

    public String getsPCT_id() {
        return sPCT_id;
    }

    public void setsPCT_id(String sPCT_id) {
        this.sPCT_id = sPCT_id;
    }

    public int getSoLuongTra() {
        return soLuongTra;
    }

    public void setSoLuongTra(int soLuongTra) {
        this.soLuongTra = soLuongTra;
    }

    public BigDecimal getGiaTra() {
        return giaTra;
    }

    public void setGiaTra(BigDecimal giaTra) {
        this.giaTra = giaTra;
    }

    public BigDecimal getTongTienHoanTra() {
        return tongTienHoanTra;
    }

    public void setTongTienHoanTra(BigDecimal tongTienHoanTra) {
        this.tongTienHoanTra = tongTienHoanTra;
    }

    public String getNgayTraHang() {
        return ngayTraHang;
    }

    public void setNgayTraHang(String ngayTraHang) {
        this.ngayTraHang = ngayTraHang;
    }

    public String getLyDoTraHang() {
        return lyDoTraHang;
    }

    public void setLyDoTraHang(String lyDoTraHang) {
        this.lyDoTraHang = lyDoTraHang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
