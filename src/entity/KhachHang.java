/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author Trong Phu
 */
public class KhachHang {
      private String idKH;
    private String hoTen;
    private boolean gioiTinh;
    private String diaChi ;
    private String sdt ;
    private String email ;
    private String ghiChu ;
    private Date ngayTao ;
    private int soLuotMua ;

    public int getSoLuotMua() {
        return soLuotMua;
    }

    public void setSoLuotMua(int soLuotMua) {
        this.soLuotMua = soLuotMua;
    }

    public KhachHang() {
    }

    public KhachHang(String idKH, String hoTen, boolean gioiTinh, String diaChi, String sdt, String email, String ghiChu, Date ngayTao) {
        this.idKH = idKH;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.ghiChu = ghiChu;
        this.ngayTao = ngayTao;
    }

    public KhachHang(String idKH, String hoTen, boolean gioiTinh, String diaChi, String sdt, String email, String ghiChu, Date ngayTao, int soLuotMua) {
        this.idKH = idKH;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.email = email;
        this.ghiChu = ghiChu;
        this.ngayTao = ngayTao;
        this.soLuotMua = soLuotMua;
    }
    

    public String getIdKH() {
        return idKH;
    }

    public void setIdKH(String idKH) {
        this.idKH = idKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
    public String getgioitinh(){
      if(gioiTinh ==true){
          return "Nam";
          
      }
      return "Nữ";
  }
      public Integer isgioitinh(){
      if(gioiTinh ==true){
          return 1;
          
      }
      return 0;
      }
    
}
