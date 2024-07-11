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
public class HoaDonChiTiet {
    private String maHDCT;
    private String maHoaDon;
    private String maSanPham;
    private String tenSanPham;
    private int soLuong;
    private BigDecimal giaBan;
    private BigDecimal thanhTien;
    private String trangThaiHDCT;

    

  
    
    private int size;
    private String chatLieu;
    private String nhaCC;
    private String mauSac;
    private String sanPham;
    
    public HoaDonChiTiet() {
    }
    
    
    //constructor này có chức năng thêm hdct
    public HoaDonChiTiet(String maHDCT, String maHoaDon, String maSanPham, int soLuong, BigDecimal giaBan, BigDecimal thanhTien, String trangThaiHDCT) {
        this.maHDCT = maHDCT;
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
        this.trangThaiHDCT = trangThaiHDCT;
    }
    
    //constructor này có chức năng phục vụ hiển thị dữ liệu lên giỏ hàng chức năng bán hàng

    public HoaDonChiTiet(String maHDCT, String maHoaDon, String maSanPham, String tenSanPham, int soLuong, BigDecimal giaBan, BigDecimal thanhTien, int size, String chatLieu, String nhaCC, String mauSac, String sanPham, String trangThaiHDCT) {
        this.maHDCT = maHDCT;
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
        this.size = size;
        this.chatLieu = chatLieu;
        this.nhaCC = nhaCC;
        this.mauSac = mauSac;
        this.sanPham = sanPham;
        this.trangThaiHDCT = trangThaiHDCT;
    }
    

    public String getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(String maHDCT) {
        this.maHDCT = maHDCT;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

     public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
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

    public String getNhaCC() {
        return nhaCC;
    }

    public void setNhaCC(String nhaCC) {
        this.nhaCC = nhaCC;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getSanPham() {
        return sanPham;
    }

    public void setSanPham(String sanPham) {
        this.sanPham = sanPham;
    }
    public String getTrangThaiHDCT() {
        return trangThaiHDCT;
    }

    public void setTrangThaiHDCT(String trangThaiHDCT) {
        this.trangThaiHDCT = trangThaiHDCT;
    }

    @Override
    public String toString() {
        return "HoaDonChiTiet{" + "maHDCT=" + maHDCT + ", maHoaDon=" + maHoaDon + ", maSanPham=" + maSanPham + ", soLuong=" + soLuong + ", giaBan=" + giaBan + ", thanhTien=" + thanhTien + '}';
    }
    
    public Object[] toDataRow1() {
        return new Object[] {this.maHDCT,this.maHoaDon,this.maSanPham,this.soLuong,this.giaBan,this.thanhTien,this.trangThaiHDCT};
    }
}
