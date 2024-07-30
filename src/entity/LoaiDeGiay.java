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
 * @author luuvi
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class LoaiDeGiay {

    private Integer deGiay_id;

    private String tenDeGiay;

    private Integer chieuCao;

    public LoaiDeGiay(String ten, Integer chieuCao) {
        this.tenDeGiay = ten;
        this.chieuCao = chieuCao;
    }

    public Object[] DataLoaiDeGiay() {
        return new Object[]{
            deGiay_id,
            tenDeGiay,
            chieuCao
        };
    }
}
