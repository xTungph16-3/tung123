/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author luuvi
 */
public class Voucher {
    private int voucher_id;
    private String ten;
    private String moTa;
    private Date ngayBD;
    private Date ngayKT;
    private double giamGia;
    private double giaToiDa;
    private double donToiThieu;

    public Voucher() {
    }

    public Voucher(int voucher_id, String ten, String moTa, Date ngayBD, Date ngayKT, double giamGia, double giaToiDa, double donToiThieu) {
        this.voucher_id = voucher_id;
        this.ten = ten;
        this.moTa = moTa;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.giamGia = giamGia;
        this.giaToiDa = giaToiDa;
        this.donToiThieu = donToiThieu;
    }

    public Voucher(String ten, String moTa, Date ngayBD, Date ngayKT, double giamGia, double giaToiDa, double donToiThieu) {
        this.ten = ten;
        this.moTa = moTa;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.giamGia = giamGia;
        this.giaToiDa = giaToiDa;
        this.donToiThieu = donToiThieu;
    }

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(Date ngayBD) {
        this.ngayBD = ngayBD;
    }

    public Date getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(Date ngayKT) {
        this.ngayKT = ngayKT;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getGiaToiDa() {
        return giaToiDa;
    }

    public void setGiaToiDa(double giaToiDa) {
        this.giaToiDa = giaToiDa;
    }

    public double getDonToiThieu() {
        return donToiThieu;
    }

    public void setDonToiThieu(double donToiThieu) {
        this.donToiThieu = donToiThieu;
    }
    
    public Object[] toDataRow(){
        return new Object[]{
            voucher_id,
            ten,
            moTa, 
            ngayBD,
            ngayKT,
            giamGia, 
            giaToiDa, 
            donToiThieu
        };
    }
}
