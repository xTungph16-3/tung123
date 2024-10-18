/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.HoaDonChiTietDAO;
import dao.HoaDonDAO;
import entity.HoaDon;
import entity.HoaDonChiTiet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import utils.XLogin;

/**
 *
 * @author Trong Phu
 */
public class HoaDon_View extends javax.swing.JPanel {

    private HoaDonDAO dao = new HoaDonDAO();

    private HoaDonChiTietDAO dao2 = new HoaDonChiTietDAO();

    double soTrangLe;
    int soTrang;
    int tienLui = 0;
    int viTriTrang = 1;

    public HoaDon_View() {
        initComponents();

        fillTableHoaDon(dao.phanTrangHoaDon(tienLui));

        setSoTrang();
    }

    public void AddPleacehoderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.gray);
    }

    public void RemovePleacehoderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN);
        textField.setFont(font);
        textField.setForeground(Color.black);
    }

    private void fillTableHoaDon(List<HoaDon> list) {
        DefaultTableModel model = (DefaultTableModel) tblHD.getModel();

        model.setRowCount(0);
        for (HoaDon hd : list) {
            model.addRow(hd.toDaTaRow());
        }
        System.out.println("Rows added to table: " + list.size()); // Add this line for debugging
    }

    public void showData(int index) {
        HoaDon hd = dao.selectAll().get(index);
        txtMaHoaDon.setText(hd.getMaHoaDon());
        cbo_TrangThai.setSelectedItem(hd.getTrangThai());
    }

    private void loadDataToTableHDCT(List<HoaDonChiTiet> lst) {

        DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();

        model.setRowCount(0);

        int i = 1;
        String trangThaiHDCT = "";

        for (HoaDonChiTiet hdct : lst) {

            if (hdct.getTrangThaiHDCT().equalsIgnoreCase("Hoàn thành")) {
                trangThaiHDCT = "<html><font color='green'><b>Hoàn thành</b></font></html>";
            } else if (hdct.getTrangThaiHDCT().equalsIgnoreCase("Chờ thanh toán")) {
                trangThaiHDCT = "<html><font color='red'><b>Chờ thanh toán</b></font></html>";
            }

            model.addRow(new Object[]{
                i++,
                hdct.getSPCT_id(),
                hdct.getTenSanPham(),
                hdct.getSize(),
                hdct.getMauSac(),
                hdct.getGiaBan(),
                hdct.getSoLuong(),
                hdct.getThanhTien()
            });
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý hoá đơn view");
        HoaDon_View hoaDon_View = new HoaDon_View();
        frame.add(hoaDon_View);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void setSoTrang() {
        soTrangLe = Math.ceil((double) dao.selectAll().size() / 5);
        soTrang = (int) soTrangLe;
        lblSoTrangHoaDon.setText(viTriTrang + "/" + soTrang);
    }

    private void next() {
        if (viTriTrang < soTrang) {
            viTriTrang++;
            tienLui += 5;
            fillTableHoaDon(dao.phanTrangHoaDon(tienLui));
            setSoTrang();
        } else {
            first();
        }
    }
// hàm này có chứuc năng lùi lại 1 trang

    private void preview() {
        if (viTriTrang > 1) {
            viTriTrang--;
            tienLui -= 5;
            fillTableHoaDon(dao.phanTrangHoaDon(tienLui));
            setSoTrang();
        } else {
            last();
        }
    }
// hàm này có chức năng trở về trang cuối cùng

    private void last() {
        tienLui = soTrang * 5 - 5;
        viTriTrang = soTrang;
        setSoTrang();
        fillTableHoaDon(dao.phanTrangHoaDon(tienLui));
    }
// hàm này có chức năng trở về trang đầu tiên

    private void first() {
        tienLui = 0;
        viTriTrang = 1;
        setSoTrang();
        fillTableHoaDon(dao.phanTrangHoaDon(tienLui));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbo_TrangThai = new javax.swing.JComboBox<>();
        btnResetTable1 = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        lblSoTrangHoaDon = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        lblNguoiTao = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblNguoiMua = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        lblTrangThai = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1145, 683));

        jPanel1.setBackground(new java.awt.Color(255, 251, 246));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "QUẢN LÝ HOÁ ĐƠN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Mã hóa đơn:");

        txtMaHoaDon.setText("Mã hoá đơn");
        txtMaHoaDon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaHoaDonFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaHoaDonFocusLost(evt);
            }
        });
        txtMaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHoaDonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Trạng thái:");

        cbo_TrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Hoàn thành", "Đã huỷ", "Chờ thanh toán" }));
        cbo_TrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_TrangThaiItemStateChanged(evt);
            }
        });
        cbo_TrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_TrangThaiActionPerformed(evt);
            }
        });

        btnResetTable1.setText("Reset Table");
        btnResetTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetTable1ActionPerformed(evt);
            }
        });

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search3.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbo_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnResetTable1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cbo_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetTable1)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã NV", "Tên NV", "Mã KH", "Tên KH", "Tổng tiền", "Kiểu thanh toán", "Ngày tạo", "Trạng thái", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblHDMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tblHD);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Lọc hóa đơn");

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPreview.setText("<");
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });

        lblSoTrangHoaDon.setText("Số trang HD");

        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 204, 51));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Price list.png"))); // NOI18N
        jButton1.setText("Xuất file hoá đơn");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 751, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(btnFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPreview)
                .addGap(18, 18, 18)
                .addComponent(lblSoTrangHoaDon)
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addGap(18, 18, 18)
                .addComponent(btnLast)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFirst)
                            .addComponent(btnPreview)
                            .addComponent(lblSoTrangHoaDon)
                            .addComponent(btnNext)
                            .addComponent(btnLast))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addGap(177, 177, 177))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản phẫm"));

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã sản phầm", "Tên sản phẩm", "Size", "Màu sắc", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCTMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblHDCT);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Hóa Đơn");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Mã HĐ:");

        lblMaHD.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Người tạo:");

        lblNguoiTao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblNguoiMua.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Người mua:");

        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Tổng tiền:");

        lblNgayTao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Ngày tạo:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Ghi chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Trạng thái:");

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTrangThai.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(lblTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel10)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(lblNguoiMua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator5)
                            .addComponent(jSeparator6))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSeparator1, jSeparator2, jSeparator3, jSeparator4, jSeparator5, jSeparator6});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblNguoiTao))
                .addGap(1, 1, 1)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblNguoiMua))
                .addGap(1, 1, 1)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblTongTien))
                .addGap(1, 1, 1)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblNgayTao))
                .addGap(1, 1, 1)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblTrangThai))
                .addGap(1, 1, 1)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 15, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 404, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(93, 93, 93))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 671, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        // TODO add your handling code here:
        int index = tblHD.getSelectedRow();

        String trangThai = tblHD.getValueAt(index, 8).toString();

        if (trangThai.equalsIgnoreCase("Đã huỷ")) {
            trangThai = "<html><font color='red'>Đã huỷ</font></html>";
        } else if (trangThai.equalsIgnoreCase("Hoàn thành")) {
            trangThai = "<html><font color='green'>Hoàn thành</font></html>";
        }

        String hoaDon_id = tblHD.getValueAt(index, 0).toString();

        // Load dữ liệu chi tiết hóa đơn
        ArrayList<HoaDonChiTiet> list = dao2.selectHDCT(hoaDon_id);

        // Kiểm tra xem danh sách chi tiết hóa đơn có trống hay không
        if (list.isEmpty()) {
            System.out.println("Không có chi tiết hóa đơn nào.");
        } else {
            for (HoaDonChiTiet hoaDonChiTiet : list) {
                System.out.println(hoaDonChiTiet);
            }
        }

        // Cập nhật bảng chi tiết hóa đơn
        loadDataToTableHDCT(list);

        lblMaHD.setText(tblHD.getValueAt(index, 0).toString());

        lblNgayTao.setText(tblHD.getValueAt(index, 7).toString());

        lblNguoiMua.setText(tblHD.getValueAt(index, 4).toString());

        lblNguoiTao.setText(tblHD.getValueAt(index, 2).toString());

        lblTongTien.setText(tblHD.getValueAt(index, 5).toString() == null ? "0.00" : tblHD.getValueAt(index, 5).toString());

        lblTrangThai.setText(trangThai);

        if (tblHD.getValueAt(index, 9) != null) {

            txtGhiChu.setText(tblHD.getValueAt(index, 9).toString());

        } else {

            txtGhiChu.setText("");
        }


    }//GEN-LAST:event_tblHDMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        // TODO add your handling code here:
        preview();
    }//GEN-LAST:event_btnPreviewActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void txtMaHoaDonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaHoaDonFocusGained
        // TODO add your handling code here:
        if (txtMaHoaDon.getText().equals("Mã hoá đơn")) {
            txtMaHoaDon.setText(null);
            txtMaHoaDon.requestFocus();
            RemovePleacehoderStyle(txtMaHoaDon);
        }
    }//GEN-LAST:event_txtMaHoaDonFocusGained

    private void txtMaHoaDonFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaHoaDonFocusLost
        // TODO add your handling code here:
        if (txtMaHoaDon.getText().length() == 0) {
            AddPleacehoderStyle(txtMaHoaDon);
            txtMaHoaDon.setText("Mã hoá đơn");

        }
    }//GEN-LAST:event_txtMaHoaDonFocusLost

    private void txtMaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHoaDonActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:

        fillTableHoaDon(dao.timKiemPhanTrangHoaDon(txtMaHoaDon.getText()));

    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblHDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblHDCTMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // Kiểm tra xem đã chọn hàng nào chưa
        if (tblHD.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Chọn hoá đơn cần xuất file!!");
            return;
        }

        // Kiểm tra xem hoá đơn có thể xuất được không
        String trangThaiHoaDon = tblHD.getValueAt(tblHD.getSelectedRow(), 8).toString();
        if (trangThaiHoaDon.equalsIgnoreCase("Đã huỷ") || trangThaiHoaDon.equalsIgnoreCase("Chờ thanh toán")) {
            JOptionPane.showMessageDialog(this, "Hoá đơn " + trangThaiHoaDon + " không thể xuất!!");
            return;
        }

        // Kiểm tra xem hoá đơn có sản phẩm nào không
        if (tblHDCT.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Hoá đơn đã trả hết hàng!!");
            return;
        }

        // Chọn thư mục để lưu file PDF
        String path = "";
        JFileChooser jfile = new JFileChooser();
        jfile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = jfile.showSaveDialog(this);
        if (x == JFileChooser.APPROVE_OPTION) {
            path = jfile.getSelectedFile().getAbsolutePath();
        } else {
            return;
        }

        // Khởi tạo tài liệu PDF
        Document doc = new Document();
        try {
            String maHoaDon = tblHD.getValueAt(tblHD.getSelectedRow(), 0).toString().trim();
            String filePath = path + "/" + maHoaDon + ".pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            doc.open();

            // Dữ liệu hoá đơn
            String tenKhachHang = tblHD.getValueAt(tblHD.getSelectedRow(), 4).toString();
            String ngayTaoHoaDon = tblHD.getValueAt(tblHD.getSelectedRow(), 7).toString();
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String ngayXuatHoaDon = formatter.format(now);

            // Thêm logo
            URL url = TrangChu.class.getResource("/img/nike.png");
            Image logo = Image.getInstance(url);
            logo.scaleAbsolute(60f, 60f); // Điều chỉnh kích thước logo
            logo.setAlignment(Element.ALIGN_LEFT);
            doc.add(logo);

            // Thêm tiêu đề
            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 32, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
            Paragraph title = new Paragraph("HOA DON BAN LE LIKEY", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph("\n"));

            // Thêm thông tin hoá đơn
            com.itextpdf.text.Font infoFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.NORMAL, BaseColor.DARK_GRAY);
            doc.add(new Paragraph("Ma hoa don: " + maHoaDon, infoFont));
            doc.add(new Paragraph("Ten khach hang: " + removeDiacritics(tenKhachHang), infoFont));
            doc.add(new Paragraph("Ngay tao hoa don: " + ngayTaoHoaDon, infoFont));
            doc.add(new Paragraph("Ngay xuat hoa don: " + ngayXuatHoaDon, infoFont));
            doc.add(new Paragraph("Nguoi tao hoa don: " + removeDiacritics(lblNguoiTao.getText()), infoFont));
            doc.add(new Paragraph("Nguoi xuat hoa don: " + removeDiacritics(XLogin.user.getHoTen()), infoFont));
            doc.add(new Paragraph("Tong tien cua hoa don: " + lblTongTien.getText(), infoFont));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("Danh sach san pham da mua", infoFont));

            // Tạo bảng cho các sản phẩm trong hoá đơn
            PdfPTable table = new PdfPTable(8); // Sửa lại số cột thành 7
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Tiêu đề bảng
            com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.BOLD, BaseColor.DARK_GRAY);
            String[] headers = {"STT", "Ma san pham", "Ten san pham", "Size", "Mau sac", "Đơn gia", "So luong", "Thanh tien"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Dữ liệu bảng
            com.itextpdf.text.Font dataFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.NORMAL, BaseColor.DARK_GRAY);
            for (int i = 0; i < tblHDCT.getRowCount(); i++) {
                table.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 0).toString()), dataFont))); // STT
                table.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 1).toString()), dataFont))); // Mã sản phẩm
                table.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 2).toString()), dataFont))); // Tên sản phẩm
                table.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 3).toString()), dataFont))); // Size
                table.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 4).toString()), dataFont))); // Màu sắc
                table.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 5).toString()), dataFont))); // Đơn giá
                table.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 6).toString()), dataFont))); // Số lượng
                table.addCell(new PdfPCell(new Phrase(removeDiacritics(tblHDCT.getValueAt(i, 7).toString()), dataFont))); // Thành tiền
            }

            // Thêm hàng tổng tiền
            PdfPCell totalCell = new PdfPCell(new Phrase("Tong tien: " + lblTongTien.getText(), headerFont));
            totalCell.setColspan(8); // Gộp cột
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Căn phải
            table.addCell(totalCell);

            doc.add(table);

            // Thêm footer
            com.itextpdf.text.Font footerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
            Paragraph hanTra = new Paragraph("Han tra hang 2 ngay sau khi thanh toan hoa đon", footerFont);
            doc.add(hanTra);

            com.itextpdf.text.Font footerTitleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 20, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
            Paragraph footerTitle = new Paragraph("CHUC QUY KHACH MUA SAM VUI VE!!!!", footerTitleFont);
            doc.add(footerTitle);

            PdfPTable footerTable = new PdfPTable(1);
            footerTable.setWidthPercentage(100);
            footerTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell footerCell = new PdfPCell(new Phrase("----------------------------- CAM ON QUY KHACH ĐA LUA CHON CUA HANG ---------------------------", footerFont));
            footerTable.addCell(footerCell);
            doc.add(footerTable);

            doc.close();

            // Hiển thị thông báo hỏi xem có muốn mở file PDF không
            int choice = JOptionPane.showConfirmDialog(this, "Xem hoá đơn vừa tạo?", "XEM HOÁ ĐƠN", JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                openPdfFile(filePath);
            }

        } catch (DocumentException | IOException ex) {
            Logger.getLogger(HoaDon_View.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xuất hoá đơn. Vui lòng thử lại.");
        }

    }//GEN-LAST:event_jButton1ActionPerformed
    private static void openPdfFile(String filePath) {
        try {
            File file = new File(filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Desktop is not supported");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String removeDiacritics(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        input = input.replace("Đ", "D");
        input = input.replace("đ", "d");
        String decomposed = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(decomposed).replaceAll("");
    }
    private void cbo_TrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_TrangThaiItemStateChanged
        // TODO add your handling code here:

        if (cbo_TrangThai.getSelectedItem().equals("Tất cả")) {
            viTriTrang = 1;
            tienLui = 0;
            setSoTrang();
            btnFirst.setEnabled(true);
            btnLast.setEnabled(true);
            btnNext.setEnabled(true);
            btnPreview.setEnabled(true);
            fillTableHoaDon(dao.phanTrangHoaDon(tienLui));
        } else {
            lblSoTrangHoaDon.setText("1/1");
            btnFirst.setEnabled(false);
            btnLast.setEnabled(false);
            btnNext.setEnabled(false);
            btnPreview.setEnabled(false);
            fillTableHoaDon(dao.locTrangThaiPhanTrangHoaDon(cbo_TrangThai.getSelectedItem().toString()));
        }


    }//GEN-LAST:event_cbo_TrangThaiItemStateChanged

    private void cbo_TrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_TrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_TrangThaiActionPerformed

    private void tblHDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHDMouseEntered

    private void btnResetTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetTable1ActionPerformed
        // TODO add your handling code here:
        txtMaHoaDon.setText("");

    }//GEN-LAST:event_btnResetTable1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnResetTable1;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cbo_TrangThai;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNguoiMua;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JLabel lblSoTrangHoaDon;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaHoaDon;
    // End of variables declaration//GEN-END:variables
}
