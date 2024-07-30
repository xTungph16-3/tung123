/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import javax.swing.JOptionPane;
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

public class ChatLieu {

    private Integer chatLieu_id;

    private String tenChatLieu;

    private String nguonGoc;

    private String mota;

    public ChatLieu(String ten, String nguonGoc, String mota) {
        this.tenChatLieu = ten;
        this.nguonGoc = nguonGoc;
        this.mota = mota;
    }

    public Object[] DataChatLieu() {
        return new Object[]{
            chatLieu_id,
            tenChatLieu,
            nguonGoc,
            mota
        };
    }
}
