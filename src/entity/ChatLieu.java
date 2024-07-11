/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import javax.swing.JOptionPane;

/**
 *
 * @author Trong Phu
 */
public class ChatLieu {
    private int id;
    private String tenCL;
    private String nguonGoc;
    private String mota;

    public ChatLieu() {
    }

    public ChatLieu(int id, String tenCL, String nguonGoc, String mota) {
        this.id = id;
        this.tenCL = tenCL;
        this.nguonGoc = nguonGoc;
        this.mota = mota;
    }

    public ChatLieu(String tenCL, String nguonGoc, String mota) {
        this.tenCL = tenCL;
        this.nguonGoc = nguonGoc;
        this.mota = mota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenCL() {
        return tenCL;
    }

    public void setTenCL(String tenCL) {
        this.tenCL = tenCL;
    }

    public String getNguonGoc() {
        return nguonGoc;
    }

    public void setNguonGoc(String nguonGoc) {
        this.nguonGoc = nguonGoc;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    @Override
    public String toString() {
        return  tenCL;
    }
    
    public Object [] DataCL(){
        return new Object[] {this.id,this.tenCL,this.nguonGoc,this.mota};
    }
}
