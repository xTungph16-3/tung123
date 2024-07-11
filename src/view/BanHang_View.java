/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.BanHangDAO;
import dao.ChatLieuDAO;
import dao.HoaDonChiTietDAO;
import dao.HoaDonDAO;
import dao.MauSacDAO;
import dao.SizeDAO;
import dao.ThanhToanDAO;
import entity.ChatLieu;
import entity.HoaDon;
import entity.HoaDonChiTiet;
import entity.KhachHang;
import entity.MauSac;
import entity.SanPhamChiTiet;
import entity.Size;
import entity.ThanhToan;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import utils.XLogin;

/**
 *
 * @author Trong Phu
 */
public class BanHang_View extends javax.swing.JPanel {

    BanHangDAO bhd = new BanHangDAO();
    MauSacDAO msd = new MauSacDAO();
    SizeDAO sd = new SizeDAO();
    ChatLieuDAO cld = new ChatLieuDAO();
    ThanhToanDAO ttDAO = new ThanhToanDAO();
    HoaDonDAO hDDAO = new HoaDonDAO();
    HoaDonChiTietDAO hDCTDAO = new HoaDonChiTietDAO();
    public static String sdtKH;
    public static String hoTenKH;

    /**
     * Creates new form BanHang_View
     */
    public BanHang_View() {
        initComponents();
        setComboChatLieu();
        setComboMauSac();
        setComboSize();
        setComboThanhToan();
        loadDataToTableHD(bhd.selectHDCho());
        loadDataToTableSPCT(bhd.selectSPCT());

        tuDongTimSP();
        tuDongTinhTienKhachDua();
        tuDongTimKH();

    }

    private void tuDongTinhTienKhachDua() {
        txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTienThua();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTienThua();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

            private void updateTienThua() {
                try {
                    String tienKhachDuaStr = txtTienKhachDua.getText();
                    if (!tienKhachDuaStr.isEmpty()) {
                        BigDecimal tienKhachDua = new BigDecimal(tienKhachDuaStr);
                        BigDecimal tienThua = tienKhachDua.subtract(tinhTongTien());

                        if (tienThua.compareTo(BigDecimal.ZERO) >= 0) {
                            lblTienThuaThieu.setText("Tiền thừa:");
                        } else {
                            lblTienThuaThieu.setText("Tiền thiếu:");
                        }

                        lblTienThua.setText(String.valueOf(tienThua.abs()));
                    } else {
                        // Chuỗi trống, có thể thực hiện các xử lý khác tùy thuộc vào yêu cầu của bạn.
                        // Hiện tại, tôi chỉ đặt lblTienThua và lblTienThuaThieu về giá trị mặc định.
                        lblTienThua.setText("0.00");
                        lblTienThuaThieu.setText("Tiền thừa:");
                    }
                } catch (NumberFormatException ex) {
                    lblTienThua.setText("Nhập số hợp lệ");
                    lblTienThuaThieu.setText("");
                }
            }
        });
    }

    private void tuDongTimKH() {
        txtSDT.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //bhd.selectKHbySDT(txtSDT.getText());
                if (null != bhd.selectKHbySDT(txtSDT.getText())) {
                    txtTenKH.setText(bhd.selectKHbySDT(txtSDT.getText()).getHoTen());
                    lblBaoLoiKH.setText("");
                } else {
                    txtTenKH.setText("");
                    lblBaoLoiKH.setText("Khách hàng không tồn tại!!");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // bhd.selectKHbySDT(txtSDT.getText());
                if (null != bhd.selectKHbySDT(txtSDT.getText())) {
                    txtTenKH.setText(bhd.selectKHbySDT(txtSDT.getText()).getHoTen());
                    lblBaoLoiKH.setText("");
                } else {
                    txtTenKH.setText("");
                    lblBaoLoiKH.setText("Khách hàng không tồn tại!!");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

        });
    }

    private void tuDongTimSP() {
        txtTim.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //bhd.selectKHbySDT(txtSDT.getText());
                testAuTo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // bhd.selectKHbySDT(txtSDT.getText());
                testAuTo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

        });
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        BanHang_View bHV = new BanHang_View();
        jf.add(bHV);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }

    private void loadDataToTableHD(List<HoaDon> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        for (HoaDon hd : lst) {
            model.addRow(new Object[]{
                hd.getMaHoaDon(),
                hd.getNgayTao(),
                hd.getMaNhanVien(),
                hd.getMaKhachHang(),
                hd.getTrangThai()
            });
        }
    }

    private void loadDataToTableHDCT(List<HoaDonChiTiet> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblGioHang.getModel();
        model.setRowCount(0);
        for (HoaDonChiTiet hdct : lst) {
            model.addRow(new Object[]{
                hdct.getMaSanPham(),
                hdct.getTenSanPham(),
                hdct.getSize(),
                hdct.getMauSac(),
                hdct.getChatLieu(),
                hdct.getNhaCC(),
                hdct.getGiaBan(),
                hdct.getSoLuong(),
                hdct.getThanhTien()
            });
        }
    }

    private void loadDataToTableSPCT(List<SanPhamChiTiet> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        for (SanPhamChiTiet spct : lst) {
            model.addRow(new Object[]{
                spct.getsPCT_id(),
                spct.getSanPham(),
                spct.getMauSac(),
                spct.getChatLieu(),
                spct.getSize(),
                spct.getDonGia(),
                spct.getSoLuong()
            });
        }
    }

    //set dữ liệu cho cbo size
    private void setComboSize() {
        DefaultComboBoxModel modelc = (DefaultComboBoxModel) cboSize.getModel();
        modelc.removeAllElements();
        modelc.addElement("Tất cả");
        for (Size size : sd.selectAll()) {
            modelc.addElement(size.getGiatri());
        }
    }

    //set dữ liệu cho cbo màu sắc
    private void setComboMauSac() {
        DefaultComboBoxModel modelc = (DefaultComboBoxModel) cboMau.getModel();
        modelc.removeAllElements();
        modelc.addElement("Tất cả");
        for (MauSac mauSac : msd.selectAll()) {
            modelc.addElement(mauSac.getTenMau());
        }
    }

    //set dữ liệu cho cbo chất liệu
    private void setComboChatLieu() {

        DefaultComboBoxModel modelc = (DefaultComboBoxModel) cboChatLieu.getModel();
        modelc.removeAllElements();
        modelc.addElement("Tất cả");
        for (ChatLieu cl : cld.selectAll()) {
            modelc.addElement(cl.getTenCL());
        }
    }

    // set dữ liệu cho combo thanh toán;
    private void setComboThanhToan() {
        DefaultComboBoxModel modelc = (DefaultComboBoxModel) cboHinhThucThanhToan.getModel();
        modelc.removeAllElements();
        for (ThanhToan tt : ttDAO.selectAll()) {
            modelc.addElement(tt.getHinhThucThanhToan());
        }
    }

    private void testAuTo() throws HeadlessException {
        // TODO add your handling code here:
        try {
            if (null != bhd.selectSPCT2(txtTim.getText().trim())) {
                loadDataToTableSPCT(bhd.selectSPCT2(txtTim.getText().trim()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm!!");
        }
    }

    private String maHDTuDong() {
        int soMaHD = hDDAO.selectAll().size();
        String ma = "HD" + String.format("%06d", ++soMaHD);
        return ma;
    }

    private String maHDCTTuDong() {
        int soMaHD = hDCTDAO.selectAll().size();
        String ma = "HDCT" + String.format("%06d", ++soMaHD);
        return ma;
    }

    /// biến này là biến thêm số lượng sản phẩm vào hoá đơn chi tiết (giỏ hàng)
    int soLuongSanPham = 0;
// hàm này có tác dụng kiểm tra biến số lượng nhập vào 
    // là số, lớn hơn 0, không để null, kiêm tra số lượng tồn có đủ không

    boolean checkFormHDCT() {
        String soLuongSanPhamString;

        do {
            soLuongSanPhamString = JOptionPane.showInputDialog(this, "Mời nhập số lượng!!");
            if (soLuongSanPhamString == null) {
                return false;
            }

            if (soLuongSanPhamString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống số lượng!!");
                return false;
            } else {
                try {
                    soLuongSanPham = Integer.parseInt(soLuongSanPhamString);

                    if (soLuongSanPham <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!!");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số!!");
                    return false;
                }
            }
            if (soLuongSanPham > Integer.parseInt(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 6).toString())) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ!!");
            } else {
                break;
            }
        } while (true);

        return true;
    }

    boolean checkFormQR() {
        String soLuongSanPhamString;

        do {
            soLuongSanPhamString = JOptionPane.showInputDialog(this, "Mời nhập số lượng!!");
            if (soLuongSanPhamString == null) {
                return false;
            }

            if (soLuongSanPhamString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống số lượng!!");
                return false;
            } else {
                try {
                    soLuongSanPham = Integer.parseInt(soLuongSanPhamString);

                    if (soLuongSanPham <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!!");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số!!");
                    return false;
                }
            }
            System.out.println("Số lượng sản phẩm: " + bhd.selectSoLuongSPCTAnđonGia(ReadQRCodeBH.sPCT_id).getSoLuong());
            if (soLuongSanPham > bhd.selectSoLuongSPCTAnđonGia(ReadQRCodeBH.sPCT_id).getSoLuong()) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ!!");
            } else {
                break;
            }
        } while (true);
        return true;
    }

// thêm hoá sản phẩm vào giỏ hàng bằng cơm
    HoaDonChiTiet readFormHDCT() {
        int index = tblSanPham.getSelectedRow();
        String sPCT_id = tblSanPham.getValueAt(index, 0).toString();
        BigDecimal giaBan = BigDecimal.valueOf(Double.parseDouble(tblSanPham.getValueAt(index, 5).toString().trim()));
        String maHD = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0).toString();
        if (checkFormHDCT()) {
            return new HoaDonChiTiet(
                    maHDCTTuDong(),
                    maHD,
                    sPCT_id,
                    soLuongSanPham,
                    giaBan,
                    null,
                    "Chờ thanh toán"
            );
        } else {
            return null;
        }
    }

    /// thêm sản phẩm vào giỏ hàng bằng tay
    HoaDonChiTiet readFormQUETQR() {
        int index = tblSanPham.getSelectedRow();
        String sPCT_id = ReadQRCodeBH.sPCT_id;
        BigDecimal giaBan = bhd.selectSoLuongSPCTAnđonGia(ReadQRCodeBH.sPCT_id).getDonGia();
        String maHD = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0).toString();
        if (checkFormQR()) {
            return new HoaDonChiTiet(
                    maHDCTTuDong(),
                    maHD,
                    sPCT_id,
                    soLuongSanPham,
                    giaBan,
                    null,
                    "Chờ thanh toán"
            );
        } else {
            return null;
        }
    }

    HoaDon readFormHDCho() {
        int maThanhToan = 1;
        for (ThanhToan tt : ttDAO.selectAll()) {
            if (cboHinhThucThanhToan.getSelectedItem().toString().equalsIgnoreCase(tt.getHinhThucThanhToan())) {
                maThanhToan = tt.getThanhToan_id();
            }
        }
        return new HoaDon(maHDTuDong(), XLogin.user.getNhanVien_id(), "KH000", BigDecimal.valueOf(0), maThanhToan, "Chờ thanh toán");
    }

    // hàm này có chức năng tạo hoá đơn chờ
    private void addHDCho() {
        HoaDon hd = readFormHDCho();
        if (bhd.insertHoaDonCho(hd) != 0) {
            JOptionPane.showMessageDialog(this, "Tạo hoá đơn chờ thành công!!!");
            loadDataToTableHD(bhd.selectHDCho());
        }
    }
// public void AddPleacehoderStyle(JTextField textField) {
//        Font font = textField.getFont();
//        font = font.deriveFont(Font.ITALIC);
//        textField.setFont(font);
//        textField.setForeground(Color.gray);
//    }
//    
//    public void RemovePleacehoderStyle(JTextField textField) {
//        Font font = textField.getFont();
//        font = font.deriveFont(Font.PLAIN);
//        textField.setFont(font);
//        textField.setForeground(Color.black);
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupGioHang = new javax.swing.JPopupMenu();
        JMniSuaSL = new javax.swing.JMenuItem();
        JMniXoa = new javax.swing.JMenuItem();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTienThuaThieu = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboHinhThucThanhToan = new javax.swing.JComboBox<>();
        lblTongTien = new javax.swing.JLabel();
        lblThanhToan = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnLamMoi = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        btnThemNhanhKhachHang = new javax.swing.JButton();
        lblBaoLoiKH = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboVoucher = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        btnHuyHoaDon = new javax.swing.JButton();
        btnQuetQR = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        cboMau = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cboSize = new javax.swing.JComboBox<>();
        cboChatLieu = new javax.swing.JComboBox<>();
        btnTimKiem = new javax.swing.JButton();
        btnResetBang = new javax.swing.JButton();

        JMniSuaSL.setBackground(new java.awt.Color(51, 204, 0));
        JMniSuaSL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        JMniSuaSL.setText("Sửa số lượng");
        JMniSuaSL.setOpaque(true);
        JMniSuaSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMniSuaSLActionPerformed(evt);
            }
        });
        jPopupGioHang.add(JMniSuaSL);

        JMniXoa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        JMniXoa.setText("Xoá sản phẩm");
        JMniXoa.setOpaque(true);
        JMniXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMniXoaActionPerformed(evt);
            }
        });
        jPopupGioHang.add(JMniXoa);

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 246, 240));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Thanh Toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel5.setMaximumSize(new java.awt.Dimension(357, 638));
        jPanel5.setPreferredSize(new java.awt.Dimension(683, 689));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Mã hóa đơn:");

        lblMaHD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaHD.setText("Hóa đơn");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Tổng tiền:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Thanh toán:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Tiền khách đưa:");

        lblTienThuaThieu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Hình thức thanh toán:");

        cboHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongTien.setText("VND");

        lblThanhToan.setBackground(new java.awt.Color(0, 204, 0));
        lblThanhToan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblThanhToan.setText("VND");

        lblTienThua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTienThua.setText("VND");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Ghi chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        btnLamMoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(0, 204, 51));
        btnThanhToan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(102, 204, 255));

        jSeparator2.setBackground(new java.awt.Color(102, 204, 255));

        jSeparator3.setBackground(new java.awt.Color(102, 204, 255));

        jSeparator4.setBackground(new java.awt.Color(102, 204, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Tên khách hàng:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("SĐT");

        txtSDT.setAutoscrolls(false);

        btnThemNhanhKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Add.png"))); // NOI18N
        btnThemNhanhKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanhKhachHangActionPerformed(evt);
            }
        });

        lblBaoLoiKH.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblBaoLoiKH.setForeground(new java.awt.Color(255, 51, 51));

        txtTenKH.setText("Khách bán lẻ");
        txtTenKH.setAutoscrolls(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemNhanhKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblBaoLoiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnThemNhanhKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBaoLoiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel5.setText("Voucher:");

        cboVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLamMoi)
                .addGap(96, 96, 96))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblTienThuaThieu)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(16, 19, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTienThua)
                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(lblThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSeparator1, jSeparator2, jSeparator3, jSeparator4});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblMaHD, lblThanhToan, lblTienThua, lblTongTien});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblMaHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTongTien))
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblThanhToan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTienThuaThieu)
                    .addComponent(lblTienThua))
                .addGap(5, 5, 5)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 237));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Ngày tạo", "Nhân viên", "Khách hàng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        btnTaoHoaDon.setBackground(new java.awt.Color(0, 204, 0));
        btnTaoHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTaoHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add3.png"))); // NOI18N
        btnTaoHoaDon.setText("Tạo hoá đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        btnHuyHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHuyHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Delete.png"))); // NOI18N
        btnHuyHoaDon.setText("Hủy hóa đơn");
        btnHuyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHoaDonActionPerformed(evt);
            }
        });

        btnQuetQR.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnQuetQR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/product-placement.png"))); // NOI18N
        btnQuetQR.setText("QUÉT QR");
        btnQuetQR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQuetQRMouseClicked(evt);
            }
        });
        btnQuetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnQuetQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHuyHoaDon, btnTaoHoaDon});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnQuetQR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHuyHoaDon, btnTaoHoaDon});

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phầm", "Tên sản phẩm", "Size", "Màu sắc", "Chất liệu", "Nhà cung cấp", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);
        if (tblGioHang.getColumnModel().getColumnCount() > 0) {
            tblGioHang.getColumnModel().getColumn(0).setMinWidth(100);
            tblGioHang.getColumnModel().getColumn(0).setMaxWidth(100);
            tblGioHang.getColumnModel().getColumn(1).setMinWidth(100);
            tblGioHang.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 238, 238));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setText("Tìm kiếm:");

        txtTim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimFocusLost(evt);
            }
        });

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên", "Màu sắc", "Chất liệu", "Size", "Đơn giá", "Số lượng tồn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);

        cboMau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Màu", "Item 2", "Item 3", "Item 4" }));
        cboMau.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMauItemStateChanged(evt);
            }
        });
        cboMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauActionPerformed(evt);
            }
        });

        jLabel15.setText("Bộ lọc");

        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Size", "Item 2", "Item 3", "Item 4" }));
        cboSize.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSizeItemStateChanged(evt);
            }
        });
        cboSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSizeActionPerformed(evt);
            }
        });

        cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chất liệu", "Item 2", "Item 3", "Item 4" }));
        cboChatLieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboChatLieuItemStateChanged(evt);
            }
        });
        cboChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChatLieuActionPerformed(evt);
            }
        });

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnResetBang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh (1).png"))); // NOI18N
        btnResetBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetBangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cboMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnResetBang)))
                        .addGap(17, 17, 17))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimKiem)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnResetBang)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauActionPerformed
        // TODO add your handling code here:
          if (cboChatLieu.getItemCount() == cld.selectAll().size() + 1 && cboMau.getItemCount() == msd.selectAll().size() + 1 && cboSize.getItemCount() == sd.selectAll().size() + 1) {
            locSanPhamChiTiet();
            System.out.println("Lọc theo màu");
        }
    }//GEN-LAST:event_cboMauActionPerformed

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Bạn muốn tạo hoá đơn mới?", "TẠO HOÁ ĐƠN MỚI", JOptionPane.YES_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            addHDCho();
        }

    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void cboSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSizeActionPerformed
        // TODO add your handling code here:
         if (cboChatLieu.getItemCount() == cld.selectAll().size() + 1 && cboMau.getItemCount() == msd.selectAll().size() + 1 && cboSize.getItemCount() == sd.selectAll().size() + 1) {
            locSanPhamChiTiet();
            System.out.println("Lọc theo size");
        }
    }//GEN-LAST:event_cboSizeActionPerformed

    private void cboChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChatLieuActionPerformed
        // TODO add your handling code here:
        if (cboChatLieu.getItemCount() == cld.selectAll().size() + 1 && cboMau.getItemCount() == msd.selectAll().size() + 1 && cboSize.getItemCount() == sd.selectAll().size() + 1) {
            locSanPhamChiTiet();
            System.out.println("Lọc theo chất liệu");
        }
    }//GEN-LAST:event_cboChatLieuActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        testAuTo();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnResetBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetBangActionPerformed
        // TODO add your handling code here:
        cboChatLieu.setSelectedItem("Tất cả");
        cboMau.setSelectedItem("Tất cả");
        cboSize.setSelectedItem("Tất cả");
        txtTim.setText("");
        loadDataToTableSPCT(bhd.selectSPCT());
    }//GEN-LAST:event_btnResetBangActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        btnThanhToan.setEnabled(true);
        //txtTenKH.setText("Khách bán lẻ");
        int index = tblHoaDon.getSelectedRow();
        String id = tblHoaDon.getValueAt(index, 0).toString();
        loadDataToTableHDCT(bhd.selectHDCT(id));
        lblMaHD.setText(id);
        tinhTongTien();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        try {
            if (tblHoaDon.getSelectedRow() >= 0) {
                if (tblGioHang.getRowCount() > 0) {
                    if (Double.valueOf(txtTienKhachDua.getText()) < Double.valueOf(lblTongTien.getText())) {
                        JOptionPane.showMessageDialog(this, "Tiền khách đưa thiếu");
                    } else {
                        if (!lblBaoLoiKH.getText().equalsIgnoreCase("Khách hàng không tồn tại!!")) {
                            int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thanh toán hoá đơn này?", "THANH TOÁN", JOptionPane.YES_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                int maThanhToan = 1;
                                for (ThanhToan tt : ttDAO.selectAll()) {
                                    if (cboHinhThucThanhToan.getSelectedItem().toString().equalsIgnoreCase(tt.getHinhThucThanhToan())) {
                                        maThanhToan = tt.getThanhToan_id();
                                    }
                                }
                                String hoaDon_id = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0).toString();
                                BigDecimal tongTien = BigDecimal.valueOf(Double.parseDouble(lblTongTien.getText()));
                                String maKH = "KH000";
                                if (null != bhd.selectKHbySDT(txtSDT.getText())) {
                                    maKH = bhd.selectKHbySDT(txtSDT.getText()).getIdKH();
                                }
                                String ghiChu = txtGhiChu.getText();
                                if (bhd.thanhToan(hoaDon_id, tongTien, maThanhToan, maKH, ghiChu) > 0) {
                                    JOptionPane.showMessageDialog(this, "Thanh toán thành công!!");
                                    loadDataToTableHD(bhd.selectHDCho());
                                    loadDataToTableHDCT(bhd.selectHDCT(""));
                                    clearFormThanhToan();
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(this, "Khách hàng không tồn tại!!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Hoá đơn chưa có sản phẩm");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Chọn hoá đơn trước khi thanh toán");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa không hợp lệ!!");
        }

    }//GEN-LAST:event_btnThanhToanActionPerformed
    boolean checkFormThanhToan() {
        if (lblBaoLoiKH.getText().trim().equalsIgnoreCase("Khách hàng không tồn tại!!")) {
            int choice = JOptionPane.showConfirmDialog(this, "Khách hàng này chưa tồn tại \n Bạn có muốn thêm nhanh khách hàng không?", "THÊM KHÁCH HÀNG?", JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                moThemNhanhKhachHang();
            }

        }

        return true;
    }

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        int index = tblHoaDon.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn trước khi thêm sản phẩm!!");
        } else {
            addHDCTPROC();
        }

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed
        // TODO add your handling code here:
        int index = tblHoaDon.getSelectedRow();

        if (index >= 0) {
            String maHD = tblHoaDon.getValueAt(index, 0).toString();
            int choice = JOptionPane.showConfirmDialog(this, "Bạn muốn huỷ hoá đơn: " + maHD + "?", "HUỶ HOÁ ĐƠN", JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                huyHoaDonCho();
                clearFormThanhToan();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn cần huỷ!!");
        }

    }//GEN-LAST:event_btnHuyHoaDonActionPerformed

    public void clearFormThanhToan() {
        lblMaHD.setText("");
        lblThanhToan.setText("");
        lblTongTien.setText("");
        txtGhiChu.setText("");
        lblTienThua.setText("");
        lblTienThuaThieu.setText("");
        txtTienKhachDua.setText("");
        txtSDT.setText("");
        txtTenKH.setText("Khách bán lẻ");
    }

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
        jPopupGioHang.show(tblGioHang, evt.getX(), evt.getY());
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void JMniXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMniXoaActionPerformed
        // TODO add your handling code here:
        doiTrangThaiHDCTPROC();
        tinhTongTien();
    }//GEN-LAST:event_JMniXoaActionPerformed
    int soLuongSanPhamThayDoi = 0;
    private void JMniSuaSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMniSuaSLActionPerformed
        // TODO add your handling code here:
        if (checkSoLuongThayDoi()) {
            String hoaDon_id = lblMaHD.getText();
            String sPCT_id = tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 0) + "";
            String hDCT_id = bhd.selectHDCTByHD_idANDSPCT_id(hoaDon_id, sPCT_id).getMaHDCT() + "";
            int soLuongSanPhamTrongGio = Integer.parseInt(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 7) + "");
            bhd.suaSLSPTrongGio(hoaDon_id, hDCT_id, sPCT_id, soLuongSanPhamThayDoi, soLuongSanPhamTrongGio);
            loadDataToTableHDCT(bhd.selectHDCT(hoaDon_id));
            loadDataToTableSPCT(bhd.selectSPCT());
            tinhTongTien();
            System.out.println("HHIIHH chỉnh sửa nè");
        }

    }//GEN-LAST:event_JMniSuaSLActionPerformed

    private void btnThemNhanhKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanhKhachHangActionPerformed

        BanHang_View.sdtKH = txtSDT.getText();
        moThemNhanhKhachHang();
        System.out.println("MỜ thêm nhanh");
        chonKH(BanHang_View.hoTenKH, BanHang_View.sdtKH);
    }//GEN-LAST:event_btnThemNhanhKhachHangActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearFormThanhToan();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtTimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimFocusGained
//        // TODO add your handling code here:
//         if (txtTim.getText().equals("Tìm theo mã, tên")) {
//            txtTim.setText(null);
//            txtTim.requestFocus();
//            RemovePleacehoderStyle(txtTim);
//        }
    }//GEN-LAST:event_txtTimFocusGained

    private void txtTimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimFocusLost
//        // TODO add your handling code here:
//         if (txtTim.getText().length() == 0) {
//            AddPleacehoderStyle(txtTim);
//            txtTim.setText("Tìm theo mã, tên");
//            
//        }
    }//GEN-LAST:event_txtTimFocusLost

    private void cboMauItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMauItemStateChanged
        // TODO add your handling code here:
      
    }//GEN-LAST:event_cboMauItemStateChanged

    private void cboSizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSizeItemStateChanged
        // TODO add your handling code here:
       

    }//GEN-LAST:event_cboSizeItemStateChanged

    private void cboChatLieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboChatLieuItemStateChanged
        // TODO add your handling code here:
        

    }//GEN-LAST:event_cboChatLieuItemStateChanged

    private void btnQuetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetQRActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_btnQuetQRActionPerformed

    private void btnQuetQRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuetQRMouseClicked
        // TODO add your handling code here:
        int index = tblHoaDon.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn trước khi quét qr!!");
        } else {
            openQR();
            if (ReadQRCodeBH.sPCT_id != null) {
                addHDCTQR();
            }
        }
    }//GEN-LAST:event_btnQuetQRMouseClicked

    void openQR() {
        TrangChu tc = new TrangChu();
        new ReadQRCodeBH(tc, true).setVisible(true);
//        new ReadQrcode().setVisible(true);
    }

    private void locSanPhamChiTiet() {
        Integer mauSac_id = null;
        Integer size_id = null;
        Integer chatLieu_id = null;
        if (cboChatLieu.getItemCount() == cld.selectAll().size() + 1 && cboMau.getItemCount() == msd.selectAll().size() + 1 && cboSize.getItemCount() == sd.selectAll().size() + 1) {
            if (cboMau.getSelectedItem().equals("Tất cả")) {
                mauSac_id = null;
            } else {
                mauSac_id = bhd.selectByTenMau(cboMau.getSelectedItem() + "").getId();

            }
            if (cboSize.getSelectedItem().equals("Tất cả")) {
                size_id = null;
            } else {
                size_id = bhd.selectByGiaTri(Integer.parseInt(cboSize.getSelectedItem() + "")).getId();

            }
            if (cboChatLieu.getSelectedItem().equals("Tất cả")) {
                chatLieu_id = null;
            } else {
                chatLieu_id = bhd.selectByTenChatLieu(cboChatLieu.getSelectedItem() + "").getId();
            }
            System.out.println("Load bảng nè");
            loadDataToTableSPCT(bhd.locSanPhamChiTiet(mauSac_id, size_id, chatLieu_id));
        } else {
            System.out.println("he he không  lọc nè");
        }
    }

    private void moThemNhanhKhachHang() {
        // TODO add your handling code here:
//        KhachHangJDialog dialogKH = new KhachHangJDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
//        dialogKH.setVisible(true);
        TrangChu tc = new TrangChu();
        new KhachHangJDialog(tc, true).setVisible(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JMniSuaSL;
    private javax.swing.JMenuItem JMniXoa;
    private javax.swing.JButton btnHuyHoaDon;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnQuetQR;
    private javax.swing.JButton btnResetBang;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemNhanhKhachHang;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboChatLieu;
    private javax.swing.JComboBox<String> cboHinhThucThanhToan;
    private javax.swing.JComboBox<String> cboMau;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboVoucher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupGioHang;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblBaoLoiKH;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTienThuaThieu;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables

    private void addHDCTPROC() {
        HoaDonChiTiet hdct = readFormHDCT();
        if (bhd.insertHoaDonCTPROC(hdct) > 0) {
            int index = tblHoaDon.getSelectedRow();
            String id = tblHoaDon.getValueAt(index, 0).toString();
            loadDataToTableHDCT(bhd.selectHDCT(id));
            loadDataToTableSPCT(bhd.selectSPCT());
            lblMaHD.setText(id);
            tinhTongTien();
        }
    }

    private void addHDCTQR() {
        HoaDonChiTiet hdct = readFormQUETQR();
        if (bhd.insertHoaDonCTPROC(hdct) > 0) {
            int index = tblHoaDon.getSelectedRow();
            String id = tblHoaDon.getValueAt(index, 0).toString();
            loadDataToTableHDCT(bhd.selectHDCT(id));
            loadDataToTableSPCT(bhd.selectSPCT());
            lblMaHD.setText(id);
            tinhTongTien();
        }
    }

    private void doiTrangThaiHDCTPROC() {
        int index = tblGioHang.getSelectedRow();
        if (index >= 0) {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn muốn xoá sản phẩm này?", "XOÁ SẢN PHẨM RA KHỎI GIỎ HÀNG", JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                String hoaDon_id = lblMaHD.getText();
                String sPCT_id = tblGioHang.getValueAt(index, 0) + "";
                String hDCT_id = bhd.selectHDCTByHD_idANDSPCT_id(hoaDon_id, sPCT_id).getMaHDCT();
                bhd.duaHDCTVeTrangThaiHuy(hDCT_id, hoaDon_id, sPCT_id);
                loadDataToTableHDCT(bhd.selectHDCT(hoaDon_id));
                loadDataToTableSPCT(bhd.selectSPCT());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xoá!!");
        }

    }

    private void huyHoaDonCho() {
        if (tblGioHang.getRowCount() <= 0) {
            String hoaDon_id = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 0).toString();
            String sPCT_id = "";
            String hDCT_id = "";
            String ghiChu = JOptionPane.showInputDialog(this, "Nhập lý do huỷ hoá đơn!!");

            bhd.huyHoaDonCho(hoaDon_id, hDCT_id, sPCT_id, ghiChu);
            JOptionPane.showMessageDialog(this, "Đã huỷ HĐ");
            loadDataToTableHD(bhd.selectHDCho());

        } else {
            String ghiChu = JOptionPane.showInputDialog(this, "Nhập lý do huỷ hoá đơn!!");
            for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                String hoaDon_id = lblMaHD.getText();
                String sPCT_id = tblGioHang.getValueAt(i, 0) + "";
                String hDCT_id = bhd.selectHDCTByHD_idANDSPCT_id(hoaDon_id, sPCT_id).getMaHDCT();
                bhd.huyHoaDonCho(hoaDon_id, hDCT_id, sPCT_id, ghiChu);

            }
            JOptionPane.showMessageDialog(this, "Đã huỷ HĐ");
            loadDataToTableHD(bhd.selectHDCho());
            loadDataToTableHDCT(bhd.selectHDCT(""));
            loadDataToTableSPCT(bhd.selectSPCT());
        }
    }

    private BigDecimal tinhTongTien() throws NumberFormatException {
        // TODO add your handling code here:
        BigDecimal tongTien2 = BigDecimal.ZERO;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            BigDecimal donGia = BigDecimal.valueOf(Double.parseDouble(tblGioHang.getValueAt(i, 6).toString()));
            int soLuong = Integer.parseInt(tblGioHang.getValueAt(i, 7).toString());
            BigDecimal tongTien1 = donGia.multiply(BigDecimal.valueOf(soLuong));
            tongTien2 = tongTien2.add(tongTien1);
        }

        lblTongTien.setText(tongTien2 + "");
        lblThanhToan.setText(tongTien2 + "");
        return tongTien2;
    }

    boolean checkSoLuongThayDoi() {
        String soLuongSanPhamString;
        do {
            soLuongSanPhamString = JOptionPane.showInputDialog(this, "Mời nhập số lượng cần thay đổi!!");
            if (soLuongSanPhamString == null) {
                return false;
            }

            if (soLuongSanPhamString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống số lượng thay đổi!!");
                return false;
            } else {
                try {
                    soLuongSanPhamThayDoi = Integer.parseInt(soLuongSanPhamString);

                    if (soLuongSanPhamThayDoi <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng thay đổi phải lớn hơn 0!!");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng thay dổi phải là số!!");
                    return false;
                }
            }
            if (soLuongSanPhamThayDoi > bhd.selectSoLuongSPCTAnđonGia(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 0) + "").getSoLuong() + Integer.parseInt(tblGioHang.getValueAt(tblGioHang.getSelectedRow(), 7) + "")) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ!!");
            } else {
                break;
            }
        } while (true);

        return true;
    }

    public void chonKH(String tenKH, String sdt) {
        txtTenKH.setText(tenKH);
        txtSDT.setText(sdt);

    }

    boolean checkThanhToan() {

        return true;
    }

}
