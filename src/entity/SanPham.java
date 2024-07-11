/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author Trong Phu
 */
public class SanPham {
    private String ID;
    private String tenSP;
    private String moTa;
    private String trangThai;
    private Date ngayTao;

    public SanPham() {
    }

    public SanPham(String ID, String tenSP, String moTa, String trangThai, Date ngayTao) {
        this.ID = ID;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    
    @Override
    public String toString() {
        return  tenSP;
    }
    
   
}
