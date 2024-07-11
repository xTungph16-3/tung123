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
public class SanPhamChiTiet {

    //INSERT INTO SanPhamChiTiet (sPCT_id, soLuong, donGia, size_id, chatLieu_id, nhaCC_id, anh, mauSac_id, sanPham_id) VALUES
    private String sPCT_id;
    private int soLuong;
    private BigDecimal donGia;
    private int size_id;
    private int chatLieu_id;
    private int nhaCC_id;
    private String anh;
    private int mauSac_id;
    private String sanPham_id;
    private String trangThai;
    private String moTa;
    private int size;
    private String chatLieu;
    private String nhaCC;
    private String mauSac;
    private String sanPham;
    private BigDecimal doanhThu;
    private int soLuongBan;

    public SanPhamChiTiet() {
    }
    
    

    //Liên quan đến join bảng để fill thông tin lên sản phẩm chi tiết
    public SanPhamChiTiet(String sPCT_id, int soLuong, BigDecimal donGia, String sanPham_id, String trangThai, String moTa, int size, String chatLieu, String nhaCC, String anh, String mauSac, String sanPham) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.sanPham_id = sanPham_id;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.size = size;
        this.chatLieu = chatLieu;
        this.nhaCC = nhaCC;
        this.anh = anh;
        this.mauSac = mauSac;
        this.sanPham = sanPham;
    }
    
    
//Liên quan đến selectALL();
    public SanPhamChiTiet(String sPCT_id, int soLuong, BigDecimal donGia, int size_id, int chatLieu_id, int nhaCC_id, String anh, int mauSac_id, String sanPham_id) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.size_id = size_id;
        this.chatLieu_id = chatLieu_id;
        this.nhaCC_id = nhaCC_id;
        this.anh = anh;
        this.mauSac_id = mauSac_id;
        this.sanPham_id = sanPham_id;
    }

    //Liên quan đến readForm thêm sản phẩm
    public SanPhamChiTiet(String sPCT_id, int soLuong, BigDecimal donGia, int size_id, int chatLieu_id, int nhaCC_id, String anh, int mauSac_id, String sanPham_id, String trangThai, String moTa) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.size_id = size_id;
        this.chatLieu_id = chatLieu_id;
        this.nhaCC_id = nhaCC_id;
        this.anh = anh;
        this.mauSac_id = mauSac_id;
        this.sanPham_id = sanPham_id;
        this.trangThai = trangThai;
        this.moTa = moTa;
    }

    ///liên quan đến bảng thống kê 
    public SanPhamChiTiet(String sPCT_id, int soLuong, String anh, String sanPham_id, String trangThai, String moTa, int size, String chatLieu, String nhaCC, String mauSac, String sanPham, BigDecimal doanhThu, int soLuongBan) {
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.anh = anh;
        this.sanPham_id = sanPham_id;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.size = size;
        this.chatLieu = chatLieu;
        this.nhaCC = nhaCC;
        this.mauSac = mauSac;
        this.sanPham = sanPham;
        this.doanhThu = doanhThu;
        this.soLuongBan = soLuongBan;
    }
    
    
    

    public String getsPCT_id() {
        return sPCT_id;
    }

    public void setsPCT_id(String sPCT_id) {
        this.sPCT_id = sPCT_id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public int getChatLieu_id() {
        return chatLieu_id;
    }

    public void setChatLieu_id(int chatLieu_id) {
        this.chatLieu_id = chatLieu_id;
    }

    public int getNhaCC_id() {
        return nhaCC_id;
    }

    public void setNhaCC_id(int nhaCC_id) {
        this.nhaCC_id = nhaCC_id;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getMauSac_id() {
        return mauSac_id;
    }

    public void setMauSac_id(int mauSac_id) {
        this.mauSac_id = mauSac_id;
    }

    public String getSanPham_id() {
        return sanPham_id;
    }

    public void setSanPham_id(String sanPham_id) {
        this.sanPham_id = sanPham_id;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
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

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

}
