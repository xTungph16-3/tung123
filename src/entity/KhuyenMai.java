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
 * @author luuvi
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString


public class KhuyenMai {

    private Integer chuongTrinh_id;

    private String ten;

    private String moTa;

    private Date ngayBD;

    private Date ngayKT;

    private Double giamGia;

    private String trangThai;

    public KhuyenMai(String ten, String moTa, Date ngayBD, Date ngayKT, Double giamGia, String trangThai) {
        this.ten = ten;
        this.moTa = moTa;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.giamGia = giamGia;
        this.trangThai = trangThai;
    }

    public Object[] toDataRow() {
        return new Object[]{
            chuongTrinh_id,
            ten,
            moTa,
            ngayBD,
            ngayKT,
            giamGia, 
            trangThai
        };
    }

}
