/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

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

public class NhaCungCap {

    private Integer nhaCC_id;

    private String tenNhaCC;

    private String moTa;

    private String diaChi;

    public NhaCungCap(String ten, String moTa, String diaChi) {
        this.tenNhaCC = ten;
        this.moTa = moTa;
        this.diaChi = diaChi;
    }

    public Object[] DataNhaCC() {
        return new Object[]{
            nhaCC_id,
            tenNhaCC,
            moTa,
            diaChi
        };
    }
}
