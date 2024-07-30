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

public class NhanVien {

    private String nhanVien_id;

    private String hoTen;

    private Boolean gioiTinh;

    private Date ngaySinh;

    private String diaChi;

    private String sdt;

    private String trangThai;

    private String anh;

    private String matkhau;

    private String vaiTro;

//    public String getgioitinh() {
//        if (gioiTinh == true) {
//            return "Nam";
//
//        }
//        return "Nữ";
//    }
//
//    public Integer isgioitinh() {
//        if (gioiTinh == true) {
//            return 1;
//
//        }
//        return 0;
//    }

}
