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

public class SanPham {

    private String sanPham_id;

    private String tenSanPham;

    private String moTa;

    private String trangThai;

    private Date ngayTao;

    private Brand brand;

    private LoaiDeGiay loaiDeGiay;

    private ChatLieu chatLieu;

    private NhaCungCap nhaCungCap;

    private int soLuongTon;

}
