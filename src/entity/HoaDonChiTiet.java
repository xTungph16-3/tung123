/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Trong Phu
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class HoaDonChiTiet {

    private String hDCT_id;

    private String hoaDon_id;

    private String sPCT_id;

    private String tenSanPham;

    private int soLuong;

    private BigDecimal giaBan;

    private BigDecimal thanhTien;

    private String trangThaiHDCT;

    private int size;

    private String chatLieu;

    private String mauSac;

    private String sanPham;

    private Integer voucher_id;

    private int giamGia;

    //constructor này có chức năng thêm hdct
    public HoaDonChiTiet(String hDCT_id, String hoaDon_id, String sPCT_id, int soLuong, BigDecimal giaBan, BigDecimal thanhTien, String trangThaiHDCT) {
        this.hDCT_id = hDCT_id;
        this.hDCT_id = hDCT_id;
        this.sPCT_id = sPCT_id;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
        this.trangThaiHDCT = trangThaiHDCT;
    }

    public Object[] toDataRow1() {
        return new Object[]{
            this.hDCT_id,
            this.hoaDon_id,
            this.sPCT_id,
            this.soLuong,
            this.giaBan,
            this.thanhTien,
            this.trangThaiHDCT};
    }

    //constructor này có chức năng phục vụ hiển thị dữ liệu lên giỏ hàng chức năng bán hàng
    public HoaDonChiTiet(String hDCT_id, String hoaDon_id, String sPCT_id, String tenSanPham, int size, String mauSac, int soLuong, BigDecimal giaBan, BigDecimal thanhTien) {
        this.hDCT_id = hDCT_id;
        this.hoaDon_id = hoaDon_id;
        this.sPCT_id = sPCT_id;
        this.tenSanPham = tenSanPham;
        this.size = size;
        this.mauSac = mauSac;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
    }
}
