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
public class NhanVien {

    private String nhanVien_id;
    private String hoTen;
    private boolean gioiTinh;
    private Date ngaySinh ;
    private String diaChi ;
    private String sdt ;
    private String trangThai ;
    private String anh ;
    private String matkhau ;
    private String vaiTro ;

    public NhanVien() {
    }

    public NhanVien(String nhanVien_id, String hoTen, boolean gioiTinh, Date ngaySinh, String diaChi, String sdt, String trangThai, String anh, String matkhau, String vaiTro) {
        this.nhanVien_id = nhanVien_id;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.trangThai = trangThai;
        this.anh = anh;
        this.matkhau = matkhau;
        this.vaiTro = vaiTro;
    }

    public String getNhanVien_id() {
        return nhanVien_id;
    }

    public void setNhanVien_id(String nhanVien_id) {
        this.nhanVien_id = nhanVien_id;
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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
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

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
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

    @Override
    public String toString() {
        return "NhanVien{" + "nhanVien_id=" + nhanVien_id + ", hoTen=" + hoTen + ", gioiTinh=" + gioiTinh + ", ngaySinh=" + ngaySinh + ", diaChi=" + diaChi + ", sdt=" + sdt + ", trangThai=" + trangThai + ", anh=" + anh + ", matkhau=" + matkhau + ", vaiTro=" + vaiTro + '}';
    }
    
}
