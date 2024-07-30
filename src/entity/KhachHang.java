/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Trong Phu
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class KhachHang {

    private String khachHang_id;

    private String hoTenHK;

    private Boolean gioiTinh;

    private String diaChi;

    private String sdt;

    private String email;

    private String ghiChu;

    private Date ngayTao;

    private Integer soLuotMua;

    public KhachHang(String khachHang_id, String hoTenHK, Boolean gioiTinh, String diaChi, String sdt, String email, String ghiChu, Date ngayTao) {
        this.khachHang_id = khachHang_id;
        this.hoTenHK = hoTenHK;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.ghiChu = ghiChu;
        this.ngayTao = ngayTao;
    }

    public KhachHang(String khachHang_id, String hoTenHK, Boolean gioiTinh, String diaChi, String sdt, String email, String ghiChu) {
        this.khachHang_id = khachHang_id;
        this.hoTenHK = hoTenHK;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.ghiChu = ghiChu;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getgioitinh() {
        if (gioiTinh == true) {
            return "Nam";

        }
        return "Nữ";
    }

    public Integer isgioitinh() {
        if (gioiTinh == true) {
            return 1;

        }
        return 0;
    }

}
