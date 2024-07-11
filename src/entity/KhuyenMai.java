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
public class KhuyenMai {

    private int chuongTrinh_id;
    private String ten;
    private String moTa;
    private Date ngayBD;
    private Date ngayKT;
    private float giamGia;

    public KhuyenMai() {
    }

    public KhuyenMai(int chuongTrinh_id, String ten, String moTa, Date ngayBD, Date ngayKT, float giamGia) {
        this.chuongTrinh_id = chuongTrinh_id;
        this.ten = ten;
        this.moTa = moTa;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.giamGia = giamGia;
    }

    public int getChuongTrinh_id() {
        return chuongTrinh_id;
    }

    public void setChuongTrinh_id(int chuongTrinh_id) {
        this.chuongTrinh_id = chuongTrinh_id;
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

    public float getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(float giamGia) {
        this.giamGia = giamGia;
    }

    public Object[] toDataRow() {
        return new Object[]{
            chuongTrinh_id,
            ten,
            moTa,
            ngayBD,
            ngayKT,
            giamGia
        };
    }

}
