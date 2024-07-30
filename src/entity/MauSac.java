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

public class MauSac {

    private Integer mauSac_id;
    
    private String tenMauSac;
    
    private String moTa;

    public MauSac(String tenMau, String moTa) {
        this.tenMauSac = tenMau;
        this.moTa = moTa;
    }

    public Object[] DataMauSac(){
        return new Object[]{
            mauSac_id,
            tenMauSac,
            moTa
        };
    }
    
}
