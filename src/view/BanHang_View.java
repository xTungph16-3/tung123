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
import dao.SanPhamChiTietDAO;
import dao.SizeDAO;
import dao.ThanhToanDAO;
import dao.VoucherDAO;
import entity.ChatLieu;
import entity.HoaDon;
import entity.HoaDonChiTiet;
import entity.KhachHang;
import entity.MauSac;
import entity.SanPhamChiTiet;
import entity.Size;
import entity.ThanhToan;
import entity.Voucher;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import utils.XLogin;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;
import utils.DB_Connect;

/**
 *
 * @author Trong Phu
 */
public class BanHang_View extends javax.swing.JPanel {

    private ArrayList<HoaDonChiTiet> list = new ArrayList<>();

    private ArrayList<HoaDon> list_HD = new ArrayList<>();

    SanPhamChiTietDAO sPCTDAO = new SanPhamChiTietDAO();
    BanHangDAO bhd = new BanHangDAO();
    MauSacDAO msd = new MauSacDAO();
    SizeDAO sd = new SizeDAO();
    ThanhToanDAO ttDAO = new ThanhToanDAO();
    HoaDonDAO hDDAO = new HoaDonDAO();
    HoaDonChiTietDAO hDCTDAO = new HoaDonChiTietDAO();
    VoucherDAO voucherDAO = new VoucherDAO();

    public static String sdtKH;
    public static String hoTenKH;

    /**
     * Creates new form BanHang_View
     */
    public BanHang_View() {
        init();

        setComboMauSac();
        setComboSize();
        setComboThanhToan();
        setComboVoucher();

        loadDataToTableHD(bhd.selectHDCho());
        loadDataToTableSPCT(sPCTDAO.selectSPCT());

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
                    txtTenKH.setText(bhd.selectKHbySDT(txtSDT.getText()).getHoTenHK());
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
                    txtTenKH.setText(bhd.selectKHbySDT(txtSDT.getText()).getHoTenHK());
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

    // Hóa đơn chờ vào Table
    private void loadDataToTableHD(List<HoaDon> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblHoaDon.getModel();
        int i = 1;
        model.setRowCount(0);
        for (HoaDon hd : lst) {
            model.addRow(new Object[]{
                i++,
                hd.getMaHoaDon(),
                hd.getMaNhanVien(),
                hd.getMaKhachHang(),
                hd.getNgayTao(),
                hd.getTrangThai()
            });
        }
    }

    // Load HDCT vào table GioHang
    private void loadDataToTableHDCT(List<HoaDonChiTiet> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblGioHang.getModel();
        int i = 1;
        model.setRowCount(0);

        for (HoaDonChiTiet hoaDonChiTiet : lst) {
            model.addRow(new Object[]{
                i++,
                hoaDonChiTiet.getSPCT_id(),
                hoaDonChiTiet.getTenSanPham(),
                hoaDonChiTiet.getSize(),
                hoaDonChiTiet.getMauSac(),
                hoaDonChiTiet.getSoLuong(),
                hoaDonChiTiet.getGiaBan(),
                hoaDonChiTiet.getThanhTien()
            });
        }
    }

    // Load sPCT vào table SanPham
    private void loadDataToTableSPCT(List<SanPhamChiTiet> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        int i = 1;
        for (SanPhamChiTiet spct : lst) {
            model.addRow(new Object[]{
                i++,
                spct.getSPCT_id(),
                spct.getTenSanPham(),
                spct.getSize(),
                spct.getMauSac(),
                spct.getSoLuong(),
                spct.getDonGia(),
                spct.getGiamGia()
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
            modelc.addElement(mauSac.getTenMauSac());
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

    // Set dữ liệu cho combo Voucher hoạt động
    private void setComboVoucher() {
        DefaultComboBoxModel modelc = (DefaultComboBoxModel) cboVoucher.getModel();
        modelc.removeAllElements();
        for (Voucher voucher : voucherDAO.selectAllHoatDong()) {
            modelc.addElement(voucher.getTen());
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
    // Biến này dùng để lưu số lượng sản phẩm muốn bán
    private int soLuongBan;

    // Hàm này có tác dụng kiểm tra biến số lượng nhập vào 
    // là số, lớn hơn 0, không để null, kiểm tra số lượng tồn có đủ không
    boolean checkFormHDCT() {
        String soLuongBanString;

        do {
            soLuongBanString = JOptionPane.showInputDialog(this, "Mời nhập số lượng!!");
            if (soLuongBanString == null) {
                return false;
            }

            if (soLuongBanString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống số lượng!!");
                return false;
            } else {
                try {
                    soLuongBan = Integer.parseInt(soLuongBanString);

                    if (soLuongBan <= 0) {
                        JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!!");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải là số!!");
                    return false;
                }
            }
            // Kiểm tra số lượng tồn kho có đủ không
            if (soLuongBan > Integer.parseInt(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 5).toString())) {
                JOptionPane.showMessageDialog(this, "Số lượng tồn không đủ!!");
            } else {
                break;
            }
        } while (true);

        return true;
    }

    // Thêm hóa sản phẩm vào giỏ hàng
    private HoaDonChiTiet readFormGioHang() {

        int index = tblSanPham.getSelectedRow();

        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trước khi thêm vào giỏ hàng!");
            return null;
        }

        if (!checkFormHDCT()) {
            return null;  // Nếu kiểm tra không thành công, thoát khỏi hàm
        }

        try {

            String sPCT_id = tblSanPham.getValueAt(index, 1).toString();
            String tenSanPham = tblSanPham.getValueAt(index, 2).toString();
            int size = (int) tblSanPham.getValueAt(index, 3);
            String mauSac = tblSanPham.getValueAt(index, 4).toString();

            // Tính giá bán sau khi áp dụng giảm giá
            double giaGoc = Double.parseDouble(tblSanPham.getValueAt(index, 6).toString().trim());
            int giamGia = Integer.parseInt(tblSanPham.getValueAt(index, 7).toString().trim());

            BigDecimal giaGocBD = BigDecimal.valueOf(giaGoc);
            BigDecimal giamGiaBD = BigDecimal.valueOf(giamGia);
            BigDecimal giaBan = giaGocBD.subtract(giaGocBD.multiply(giamGiaBD).divide(BigDecimal.valueOf(100)));

            // Lấy mã hóa đơn từ bảng hóa đơn
            String hoaDon_Id = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1).toString();

            // Tạo mã HDCT tự động
            String maHDCT = maHDCTTuDong();

            // Thiết lập số lượng sản phẩm và tính thành tiền
            int soLuongBan = this.soLuongBan; // Lấy số lượng từ kiểm tra
            BigDecimal soLuongBanBD = BigDecimal.valueOf(soLuongBan);
            BigDecimal thanhTien = giaBan.multiply(soLuongBanBD);

            // Tạo đối tượng HoaDonChiTiet
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setSPCT_id(sPCT_id);
            hoaDonChiTiet.setTenSanPham(tenSanPham);
            hoaDonChiTiet.setSize(size);
            hoaDonChiTiet.setMauSac(mauSac);
            hoaDonChiTiet.setGiaBan(giaBan);
            hoaDonChiTiet.setHoaDon_id(hoaDon_Id);
            hoaDonChiTiet.setHDCT_id(maHDCT);
            hoaDonChiTiet.setSoLuong(soLuongBan);
            hoaDonChiTiet.setThanhTien(thanhTien);
            hoaDonChiTiet.setTrangThaiHDCT("Chờ thanh toán");

            return hoaDonChiTiet;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập vào không hợp lệ!");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateHoaDonChiTiet(HoaDonChiTiet hdct) {
        // Câu lệnh SQL để cập nhật số lượng và thành tiền của một hóa đơn chi tiết
        String sql = """
                        UPDATE hoaDonChiTiet
                        SET 
                            soLuong = ?,
                            thanhTien = ?
                        WHERE hDCT_id = ?;
                    """;

        try (Connection con = DB_Connect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Thiết lập các tham số cho câu lệnh SQL
            ps.setInt(1, hdct.getSoLuong());
            ps.setBigDecimal(2, hdct.getThanhTien());
            ps.setString(3, hdct.getHDCT_id());

            // Thực thi câu lệnh SQL và lấy số dòng bị ảnh hưởng
            int rowsAffected = ps.executeUpdate();

            // Kiểm tra số dòng bị ảnh hưởng để xác nhận cập nhật thành công
            if (rowsAffected > 0) {
                System.out.println("Cập nhật thành công HoaDonChiTiet với ID: " + hdct.getHDCT_id());
            } else {
                System.out.println("Không tìm thấy HoaDonChiTiet với ID: " + hdct.getHDCT_id());
            }

        } catch (SQLException e) {
            // In ra thông tin lỗi nếu có
            e.printStackTrace();
        }
    }

    HoaDon readFormHDCho() {
        int maThanhToan = 1;
        for (ThanhToan tt : ttDAO.selectAll()) {
            if (cboHinhThucThanhToan.getSelectedItem().toString().equalsIgnoreCase(tt.getHinhThucThanhToan())) {
                maThanhToan = tt.getThanhToan_id();
            }
        }
        String maHD = maHDTuDong();
        Date ngayTao = new Date();
        return new HoaDon(
                maHD,
                XLogin.user.getNhanVien_id(),
                "KH000",
                ngayTao,
                "Chờ thanh toán"
        );
    }

    // hàm này có chức năng tạo hoá đơn chờ
    private void addHDCho() {
        HoaDon hd = readFormHDCho();
        if (bhd.insertHoaDonCho(hd) != 0) {
            JOptionPane.showMessageDialog(this, "Tạo hoá đơn chờ thành công!!!");
            loadDataToTableHD(bhd.selectHDCho());

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void init(){
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
        lblThanhTien = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
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
        jLabel9 = new javax.swing.JLabel();
        txtTienDuocGiam = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        btnHuyHoaDon = new javax.swing.JButton();
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
        btnTimKiem = new javax.swing.JButton();
        btnResetBang = new javax.swing.JButton();

//        JMniSuaSL.setBackground(new java.awt.Color(51, 204, 0));
//        JMniSuaSL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
//        JMniSuaSL.setText("Sửa số lượng");
//        JMniSuaSL.setOpaque(true);
//        JMniSuaSL.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                JMniSuaSLActionPerformed(evt);
//            }
//        });
//        jPopupGioHang.add(JMniSuaSL);

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
        jLabel6.setText("Thành tiền");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Tổng tiền");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Tiền khách đưa:");

        lblTienThuaThieu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Hình thức thanh toán:");

        lblThanhTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblThanhTien.setText("VND");

        lblTongTien.setBackground(new java.awt.Color(0, 204, 0));
        lblTongTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTongTien.setText("VND");

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

        cboVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVoucherActionPerformed(evt);
            }
        });

        jLabel9.setText("Tiền được giảm");

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
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTienThua)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(txtTienDuocGiam))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSeparator1, jSeparator2, jSeparator3, jSeparator4});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblMaHD, lblThanhTien, lblTienThua, lblTongTien});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, jLabel6, jLabel7, jLabel8});

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
                    .addComponent(lblThanhTien))
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTienDuocGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, jLabel6, jLabel7, jLabel8});

        jPanel2.setBackground(new java.awt.Color(255, 255, 237));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Nhân viên", "Khách hàng", "Ngày tạo", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHuyHoaDon, btnTaoHoaDon});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHuyHoaDon, btnTaoHoaDon});

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SPCT", "Tên sản phẩm", "Size", "Màu sắc", "Số lượng", "Giá bán", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
            tblGioHang.getColumnModel().getColumn(1).setMinWidth(100);
            tblGioHang.getColumnModel().getColumn(1).setMaxWidth(100);
            tblGioHang.getColumnModel().getColumn(2).setMinWidth(100);
            tblGioHang.getColumnModel().getColumn(2).setMaxWidth(100);
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
                "STT", "Mã SPCT", "Tên SP", "Size", "Màu sắc", "Số lượng tồn", "Đơn giá", "Giảm giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
                        .addComponent(jScrollPane3)
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
                                .addGap(71, 71, 71)
                                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
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
                                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
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
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
        );
    }

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
        lblThanhTien = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
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
        jLabel9 = new javax.swing.JLabel();
        txtTienDuocGiam = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        btnHuyHoaDon = new javax.swing.JButton();
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
        jLabel6.setText("Thành tiền");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Tổng tiền");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Tiền khách đưa:");

        lblTienThuaThieu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Hình thức thanh toán:");

        lblThanhTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblThanhTien.setText("VND");

        lblTongTien.setBackground(new java.awt.Color(0, 204, 0));
        lblTongTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTongTien.setText("VND");

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

        cboVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVoucherActionPerformed(evt);
            }
        });

        jLabel9.setText("Tiền được giảm");

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
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTienThua)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(txtTienDuocGiam))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jSeparator1, jSeparator2, jSeparator3, jSeparator4});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblMaHD, lblThanhTien, lblTienThua, lblTongTien});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, jLabel6, jLabel7, jLabel8});

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
                    .addComponent(lblThanhTien))
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTienDuocGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, jLabel6, jLabel7, jLabel8});

        jPanel2.setBackground(new java.awt.Color(255, 255, 237));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Nhân viên", "Khách hàng", "Ngày tạo", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHuyHoaDon, btnTaoHoaDon});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHuyHoaDon, btnTaoHoaDon});

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SPCT", "Tên sản phẩm", "Size", "Màu sắc", "Số lượng", "Giá bán", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
            tblGioHang.getColumnModel().getColumn(1).setMinWidth(100);
            tblGioHang.getColumnModel().getColumn(1).setMaxWidth(100);
            tblGioHang.getColumnModel().getColumn(2).setMinWidth(100);
            tblGioHang.getColumnModel().getColumn(2).setMaxWidth(100);
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
                "STT", "Mã SPCT", "Tên SP", "Size", "Màu sắc", "Số lượng tồn", "Đơn giá", "Giảm giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
                        .addComponent(jScrollPane3)
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
                                .addGap(71, 71, 71)
                                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
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
                                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
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
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauActionPerformed
        // TODO add your handling code here:
        if (cboMau.getItemCount() == msd.selectAll().size() + 1 && cboSize.getItemCount() == sd.selectAll().size() + 1) {
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
        if (cboMau.getItemCount() == msd.selectAll().size() + 1 && cboSize.getItemCount() == sd.selectAll().size() + 1) {
            locSanPhamChiTiet();
            System.out.println("Lọc theo size");
        }
    }//GEN-LAST:event_cboSizeActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        testAuTo();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnResetBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetBangActionPerformed
        // TODO add your handling code here:
        cboMau.setSelectedItem("Tất cả");
        cboSize.setSelectedItem("Tất cả");
        txtTim.setText("");
        loadDataToTableSPCT(sPCTDAO.selectSPCT());
    }//GEN-LAST:event_btnResetBangActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        btnThanhToan.setEnabled(true);

        int index = tblHoaDon.getSelectedRow();

        String id = tblHoaDon.getValueAt(index, 1).toString();

        loadDataToTableHDCT(hDCTDAO.selectHDCT(id));

        lblMaHD.setText(id);

        tinhTongTien();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        try {
            // Kiểm tra xem có dòng nào trong bảng hóa đơn được chọn không
            if (tblHoaDon.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(this, "Chọn hoá đơn trước khi thanh toán");
                return;
            }

            // Kiểm tra xem giỏ hàng có sản phẩm nào không
            if (tblGioHang.getRowCount() <= 0) {
                JOptionPane.showMessageDialog(this, "Hoá đơn chưa có sản phẩm");
                return;
            }

            // Kiểm tra xem tiền khách đưa có đủ để thanh toán không
            BigDecimal tongTien = tinhTongTien();
            double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText().trim());

            // Kiểm tra xem tiền khách đưa có đủ để thanh toán không
            if (tienKhachDua < tongTien.doubleValue()) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa thiếu");
                return;
            }

            // Kiểm tra xem khách hàng có tồn tại không
            if (lblBaoLoiKH.getText().equalsIgnoreCase("Khách hàng không tồn tại!!")) {
                int choice = JOptionPane.showConfirmDialog(this, "Khách hàng này chưa tồn tại. Bạn có muốn thêm nhanh khách hàng không?", "THÊM KHÁCH HÀNG?", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    moThemNhanhKhachHang();
                } else {
                    return; // Ngừng thanh toán nếu không muốn thêm khách hàng
                }
            }

            int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thanh toán hoá đơn này?", "THANH TOÁN", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                return; // Ngừng thanh toán nếu người dùng không xác nhận
            }

            // Lấy mã thanh toán dựa trên hình thức thanh toán
            int maThanhToan = 1;
            String selectedPaymentMethod = cboHinhThucThanhToan.getSelectedItem().toString();
            for (ThanhToan tt : ttDAO.selectAll()) {
                if (selectedPaymentMethod.equalsIgnoreCase(tt.getHinhThucThanhToan())) {
                    maThanhToan = tt.getThanhToan_id();
                    break;
                }
            }

            // Lấy các thông tin cần thiết để cập nhật hóa đơn
            String hoaDon_id = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1).toString();
            String maKH = "KH000";
            KhachHang kh = bhd.selectKHbySDT(txtSDT.getText());
            if (kh != null) {
                maKH = kh.getKhachHang_id();
            }
            String ghiChu = txtGhiChu.getText().trim();

            // Gọi phương thức thanh toán từ DAO
            int result = bhd.thanhToan(hoaDon_id, tongTien, maThanhToan, maKH, ghiChu);
            if (result > 0) {
                // Cập nhật trạng thái của hóa đơn và giỏ hàng
                bhd.updateTrangThaiHoaDon(hoaDon_id, "Hoàn thành");

                for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                    String sPCT_id = tblGioHang.getValueAt(i, 1).toString();
                    int soLuongBan = (int) tblGioHang.getValueAt(i, 5);
                    bhd.updateSoLuongSanPham(sPCT_id, soLuongBan);
                }
                JOptionPane.showMessageDialog(this, "Thanh toán thành công");
                // Xử lý các hành động sau thanh toán nếu cần
                clearFormThanhToan();

            }
            this.loadDataToTableHD(bhd.selectHDCho());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa không hợp lệ!!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình thanh toán: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed


    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        int index = tblHoaDon.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoá đơn trước khi thêm sản phẩm!!");
        } else {
            addHDCTModelPROC();
        }

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed
        // TODO add your handling code here:
        int index = tblHoaDon.getSelectedRow();

        if (index >= 0) {
            String maHD = tblHoaDon.getValueAt(index, 1).toString();
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
        lblTongTien.setText("");
        lblThanhTien.setText("");
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
//
    private void JMniXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMniXoaActionPerformed
        // TODO add your handling code here:
        doiTrangThaiHDCTPROC();
        tinhTongTien();
    }//GEN-LAST:event_JMniXoaActionPerformed

    int soLuongSanPhamThayDoi = 0;

    private void JMniSuaSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMniSuaSLActionPerformed

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

    }//GEN-LAST:event_txtTimFocusGained

    private void txtTimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimFocusLost

    }//GEN-LAST:event_txtTimFocusLost

    private void cboMauItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMauItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMauItemStateChanged

    private void cboSizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSizeItemStateChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_cboSizeItemStateChanged

    private void cboVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboVoucherActionPerformed
        try {
            tinhTongTien();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tính toán tổng tiền: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cboVoucherActionPerformed

    private void locSanPhamChiTiet() {
        Integer mauSac_id = null;
        Integer size_id = null;
        if (cboMau.getItemCount() == msd.selectAll().size() + 1 && cboSize.getItemCount() == sd.selectAll().size() + 1) {
            if (cboMau.getSelectedItem().equals("Tất cả")) {
                mauSac_id = null;
            } else {
                mauSac_id = bhd.selectByTenMau(cboMau.getSelectedItem() + "").getMauSac_id();

            }
            if (cboSize.getSelectedItem().equals("Tất cả")) {
                size_id = null;
            } else {
                size_id = bhd.selectByGiaTri(Integer.parseInt(cboSize.getSelectedItem() + "")).getSize_id();

            }
            System.out.println("Load bảng");
            loadDataToTableSPCT(bhd.locSanPhamChiTiet(mauSac_id, size_id));
        } else {
            System.out.println("không lọc ");
        }
    }

    private void moThemNhanhKhachHang() {
        // TODO add your handling code here:
        TrangChu tc = new TrangChu();
        new KhachHangJDialog(tc, true).setVisible(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JMniSuaSL;
    private javax.swing.JMenuItem JMniXoa;
    private javax.swing.JButton btnHuyHoaDon;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnResetBang;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemNhanhKhachHang;
    private javax.swing.JButton btnTimKiem;
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
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTienThuaThieu;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienDuocGiam;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables

    private void addHDCTModelPROC() {
        HoaDonChiTiet hoaDonChiTiet = readFormGioHang();

        if (hoaDonChiTiet == null) {
            // Nếu không đọc được thông tin giỏ hàng, không làm gì cả
            return;
        }

        // Lấy mã hóa đơn từ bảng hóa đơn đã chọn
        int index = tblHoaDon.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước khi thêm sản phẩm!");
            return;
        }
        String hoaDon_id = tblHoaDon.getValueAt(index, 1).toString();

        // Kiểm tra nếu sản phẩm chi tiết đã tồn tại trong danh sách
        HoaDonChiTiet existingItem = bhd.selectHDCTByHD_idANDSPCT_id(hoaDonChiTiet.getSPCT_id(), hoaDon_id);

        if (existingItem != null) {
            // Cập nhật số lượng và thành tiền của sản phẩm đã tồn tại
            int newQuantity = existingItem.getSoLuong() + hoaDonChiTiet.getSoLuong();
            BigDecimal newThanhTien = existingItem.getGiaBan().multiply(BigDecimal.valueOf(newQuantity));

            existingItem.setSoLuong(newQuantity);
            existingItem.setThanhTien(newThanhTien);

            // Cập nhật vào cơ sở dữ liệu
            updateHoaDonChiTiet(existingItem);

            System.out.println("Sản phẩm đã tồn tại. Cập nhật số lượng: " + newQuantity + ", Thành tiền: " + newThanhTien);
        } else {
            // Thêm mới sản phẩm vào giỏ hàng
            if (hDCTDAO.insertHoaDonCT(hoaDonChiTiet) > 0) {
                System.out.println("Thêm mới sản phẩm vào giỏ hàng: " + hoaDonChiTiet.getHDCT_id());
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thêm sản phẩm vào giỏ hàng!");
                return;
            }
        }

        // Tải lại dữ liệu bảng giỏ hàng chi tiết và bảng sản phẩm chi tiết
        loadDataToTableHDCT(hDCTDAO.selectHDCT(hoaDon_id));

        // Cập nhật số lượng sản phẩm còn lại trong cơ sở dữ liệu và bảng tblSanPham
        bhd.updateSoLuongSanPham(hoaDonChiTiet.getSPCT_id(), hoaDonChiTiet.getSoLuong());

        // Tải lại dữ liệu bảng sản phẩm chi tiết
        loadDataToTableSPCT(sPCTDAO.selectSPCT());

        // Cập nhật mã hóa đơn hiển thị
        lblMaHD.setText(hoaDon_id);

        // Tính tổng tiền
        tinhTongTien();
    }

    private void doiTrangThaiHDCTPROC() {
        int index = tblGioHang.getSelectedRow();
        if (index >= 0) {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn muốn xoá sản phẩm này?", "XOÁ SẢN PHẨM RA KHỎI GIỎ HÀNG", JOptionPane.YES_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                String hoaDon_id = lblMaHD.getText();
                String sPCT_id = tblGioHang.getValueAt(index, 1) + "";
                String hDCT_id = bhd.selectHDCTByHD_idANDSPCT_id(hoaDon_id, sPCT_id).getHDCT_id();
                bhd.duaHDCTVeTrangThaiHuy(hDCT_id, hoaDon_id, sPCT_id);
                loadDataToTableHDCT(hDCTDAO.selectHDCT(hoaDon_id));
                loadDataToTableSPCT(sPCTDAO.selectSPCT());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xoá!!");
        }

    }

    private void huyHoaDonCho() {

        if (tblGioHang.getRowCount() <= 0) {

            String hoaDon_id = tblHoaDon.getValueAt(tblHoaDon.getSelectedRow(), 1).toString();

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
                String sPCT_id = tblGioHang.getValueAt(i, 1) + "";
                String hDCT_id = bhd.selectHDCTByHD_idANDSPCT_id(hoaDon_id, sPCT_id).getHDCT_id();
                bhd.huyHoaDonCho(hoaDon_id, hDCT_id, sPCT_id, ghiChu);

            }
            JOptionPane.showMessageDialog(this, "Đã huỷ HĐ");
            loadDataToTableHD(bhd.selectHDCho());
            loadDataToTableHDCT(hDCTDAO.selectHDCT(""));
            loadDataToTableSPCT(sPCTDAO.selectSPCT());
        }
    }

    private BigDecimal tinhTongTien() throws NumberFormatException {
        // Khởi tạo thành tiền là BigDecimal.ZERO
        BigDecimal thanhTien = BigDecimal.ZERO;

        // Duyệt qua từng hàng trong bảng tblGioHang
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            try {
                // Lấy giá đơn và số lượng từ bảng
                BigDecimal donGia = new BigDecimal(tblGioHang.getValueAt(i, 6).toString());
                int soLuong = Integer.parseInt(tblGioHang.getValueAt(i, 5).toString());

                // Tính thành tiền cho từng sản phẩm
                BigDecimal thanhTienSP = donGia.multiply(BigDecimal.valueOf(soLuong));

                // Cộng dồn vào thành tiền
                thanhTien = thanhTien.add(thanhTienSP);
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý lỗi định dạng số, có thể thông báo cho người dùng
                JOptionPane.showMessageDialog(this, "Lỗi định dạng số liệu: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Định dạng số tiền
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        // Cập nhật giá trị thành tiền lên giao diện
        lblThanhTien.setText(currencyFormat.format(thanhTien));

        // Lấy giá trị giảm giá từ voucher (nếu có)
        int voucher = 0; // Mặc định là 0 nếu không có voucher

        try {
            String voucherName = (String) cboVoucher.getSelectedItem();
            if (voucherName != null && !voucherName.isEmpty()) {
                voucher = voucherDAO.selectGiamGiaByTen(voucherName);
                System.out.println(voucherDAO.selectGiamGiaByTen(voucherName));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi khi lấy giảm giá từ voucher
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin voucher: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Tính toán số tổng tiền sau giảm giá
        BigDecimal tongTien = thanhTien.multiply(BigDecimal.valueOf(100 - voucher)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

        // Cập nhật giá trị tổng lên giao diện
        lblTongTien.setText(currencyFormat.format(tongTien));

        // Tính toán và cập nhật giá trị tiền được giảm lên giao diện
        BigDecimal tienDuocGiam = thanhTien.subtract(tongTien);
        // Kiểm tra txtTienDuocGiam trước khi sử dụng
        txtTienDuocGiam.setText(currencyFormat.format(tienDuocGiam));

        // Trả về tổng tiền
        return tongTien;
    }

    public void chonKH(String tenKH, String sdt) {
        txtTenKH.setText(tenKH);
        txtSDT.setText(sdt);

    }

    boolean checkThanhToan() {

        return true;
    }

}
