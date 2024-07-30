/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entity.Menu;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import view.BanHang_View;
import view.HoaDon_View;
import view.KhachHang_View;
import view.KhuyenMai_Views;
import view.NhanVien_View;
import view.SanPham_View;
import view.ThongKe_View;

/**
 *
 * @author Trong Phu
 */
public class XMenu {

    private JPanel root;
    private String selected = "";

    private List<Menu> listItem = null;
    public XMenu(JPanel jpnRoot) {
        root = jpnRoot;
    }


    public void setEvent(List<Menu> list) {
        this.listItem = list;
        for (Menu menu : list) {
            menu.getJlb().addMouseListener(new LabelEvent(menu.getKind(), menu.getJpn(), menu.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {
        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "BanHang":
                    node = new BanHang_View();
                    setView();
                    break;
                case "NhanVien":
                    if (XLogin.phanQuyen()) {
                        node = new NhanVien_View();
                        setView();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(root, "Bạn không có quyền Quản lý nhân viên!");
                        break;
                    }
                case "HoaDon":
                    node = new HoaDon_View();
                    setView();
                    break;
                case "SanPham":
                    node = new SanPham_View();
                    setView();
                    break;
                case "KhuyenMai":
                    node = new KhuyenMai_Views();
                    setView();
                    break;
                case "ThongKe":
                    if (XLogin.phanQuyen()) {
                        node = new ThongKe_View();
                        setView();
                        break;
                    } else {
                        JOptionPane.showMessageDialog(root, "Bạn không có quyền xem thống kê!");
                        break;
                    }

                case "KhachHang":
                    node = new KhachHang_View();
                    setView();
                    break;
                default:
                    break;

            }

        }

        private void setView() {
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }
}
