/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Trong Phu
 */
public class Size {
    private int id;
    private int giatri;

    public Size() {
    }

    public Size(int giatri) {
        this.giatri = giatri;
    }
    
    

    public Size(int id, int giatri) {
        this.id = id;
        this.giatri = giatri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGiatri() {
        return giatri;
    }

    public void setGiatri(int giatri) {
        this.giatri = giatri;
    }

    @Override
    public String toString() {
        return giatri+"";
    }
    
    public Object [] toDataRow(){
        return new Object[] {
            this.id, this.giatri
        };
    }
    
}
