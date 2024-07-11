/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Trong Phu
 */
public class ThanhToan {

    private int thanhToan_id;
    private String hinhThucThanhToan;

    public ThanhToan() {
    }

    public ThanhToan(int thanhToan_id, String hinhThucThanhToan) {
        this.thanhToan_id = thanhToan_id;
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public int getThanhToan_id() {
        return thanhToan_id;
    }

    public void setThanhToan_id(int thanhToan_id) {
        this.thanhToan_id = thanhToan_id;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }
    
    
}
