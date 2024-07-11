/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Trong Phu
 */
public class MauSac {

    private int id;
    private String tenMau;
    private String moTa;

    public MauSac() {
    }

    public MauSac(int id, String tenMau, String moTa) {
        this.id = id;
        this.tenMau = tenMau;
        this.moTa = moTa;
    }

    public MauSac(String tenMau, String moTa) {
        this.tenMau = tenMau;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return tenMau;
    }

    public Object[] toDaTaRowMS() {
        return new Object[]{this.id, this.tenMau, this.moTa};
    }

}
