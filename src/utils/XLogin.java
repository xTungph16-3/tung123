/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entity.NhanVien;

/**
 *
 * @author Trong Phu
 */
public class XLogin {
    public static NhanVien user = null;
    
   public static void dangXuat(){
        XLogin.user = null;
    }
   public static boolean daDangNhap(){
      return XLogin.user != null;
   }
   public static boolean trangThaiTaiKhoan(){
       return "Hoạt động".equals(XLogin.user.getTrangThai());
   }
    
    public static boolean phanQuyen(){
        return XLogin.daDangNhap() && XLogin.user.getVaiTro().equals("Quản lý");
    }
    
}
