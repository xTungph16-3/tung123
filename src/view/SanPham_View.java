/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.SanPhamDAO;
import entity.SanPham;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Trong Phu
 */
public class SanPham_View extends javax.swing.JPanel {

    public static String sP_id;
    public static String ten_SP;
    SanPhamDAO spd = new SanPhamDAO();
    //Các biến sau có chức năng phân trang Sản phẩm
    double soTrangLe;
    int soTrang;
    int tienLui = 0;
    int viTriTrang = 1;

    /**
     * Creates new form SanPham_View2
     */
    public SanPham_View() {
        initComponents();

        // day();
        setSoTrang();
        loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));//load bảng sản phẩm
        //code phần bảng spct
//        this.fillTBSanPhamChiTiet(spctDAO.selectAll2());
//        this.setComboTenSP();
//        this.setComboChatLieu();
//        this.setComboMauSac();
//        this.setComboSize();
        /////////////////

//              txtTimKiem1.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                System.out.println("Text Inserted: " + txtTimKiem1.getText());
//                testAuTo();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                System.out.println("Text Removed: " + txtTimKiem1.getText());
//                testAuTo();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                // Not used for plain text components
//            }
//        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý sản phẩm view");

        SanPham_View sanPhamView = new SanPham_View();
        frame.add(sanPhamView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    ///hàm này có chức năng set số trang cho label số trang
    private void setSoTrang() {
        soTrangLe = Math.ceil((double) spd.selectAll().size() / 5);
        soTrang = (int) soTrangLe;
        lblSoTrangSP.setText(viTriTrang + "/" + soTrang);
    }

    //hàm này có chức năng đổ dữ liệu lên bảng quản lý sản phẩm
    private void loadDataToTableSanPham(List<SanPham> lst) {
        DefaultTableModel model = new DefaultTableModel();
        model = (DefaultTableModel) tblQLSP.getModel();
        model.setRowCount(0);
        for (SanPham sp : lst) {
            model.addRow(new Object[]{
                sp.getID(),
                sp.getTenSP(),
                sp.getTrangThai(),
                sp.getMoTa(),
                sp.getNgayTao()
            });
        }
    }
// hàm này có chức năng khi ấn vào 1 dòng trên bảng sẽ hiển thị thông tin lên form quản lý sản phẩm

    private void showDetail() {
        
        int index = tblQLSP.getSelectedRow();
        txtIDSanPham.setText(tblQLSP.getValueAt(index, 0).toString());
        txtTen.setText(tblQLSP.getValueAt(index, 1).toString());
        if (tblQLSP.getValueAt(index, 2).equals("Đang bán")) {
            rdoDangBan.setSelected(true);
        } else {
            rdoNgungBan.setSelected(true);
        }
        txtMoTaSanPham.setText(tblQLSP.getValueAt(index, 3).toString());
        tblQLSP.setRowSelectionInterval(index, index);
    }
// hàm này có chức năng làm mới form quản lý sản phấm

    private void clearForm() {
        txtIDSanPham.setText("");
        txtTen.setText("");
        txtMoTaSanPham.setText("");
        rdoDangBan.setSelected(true);
        tblQLSP.clearSelection();
    }

//    private void day() {
//        LocalDateTime date = LocalDateTime.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        String fmd = date.format(dtf);
//    }
    // hàm này có chức năng đọc dữ liệu iput trên form  trả về 1 đối tướng sản phẩm để thực hiện thêm và sửa sản phẩm
    private SanPham getFormInput() {
        String trangThai = "";
        if (rdoDangBan.isSelected()) {
            trangThai = "Đang bán";
        } else {
            trangThai = "Ngừng bán";
        }
        SanPham sp = new SanPham();
        sp.setID(txtIDSanPham.getText());
        sp.setTenSP(txtTen.getText());
        sp.setTrangThai(trangThai);
        sp.setMoTa(txtMoTaSanPham.getText());
        return sp;
    }

    // hàm này có tác dụng thêm sản phẩm
    private void addSanPham() throws HeadlessException {
        SanPham sp = getFormInput();
        try {
            spd.insert(sp);
            loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
            setSoTrang();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm!");
        }
    }
// hàm này có tác dụng kiểm tra và yêu cầu người dùng nhập dữ liệu đúng với kiểu dữ liệu của các trường

    private boolean checkADDSP() {
        // TODO add your handling code here:
        if (txtIDSanPham.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ID sản phẩm");
            return false;
        }
        if (txtIDSanPham.getText().trim().length() > 10) {
            JOptionPane.showMessageDialog(this, "ID sản phẩm không dược quá 10 ký tự");
            return false;
        }

        for (SanPham sp : spd.selectAll()) {
            if (txtIDSanPham.getText().trim().equalsIgnoreCase(sp.getID())) {
                JOptionPane.showMessageDialog(this, "ID sản phẩm đã tồn tại!!");
                return false;
            }
        }
        if (txtTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm!!");
            return false;
        }
        for (SanPham sp : spd.selectAll()) {
            if (txtTen.getText().trim().equalsIgnoreCase(sp.getTenSP())) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm đã tồn tại!!");
                return false;
            }
        }
        
        if (txtMoTaSanPham.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả!!");
            return false;
        }
//        String id = txtIDSanPham.getText();
//        for (SanPham sP : spd.selectAll()) {
//            if (sP.getID().equalsIgnoreCase(id)) {
//                JOptionPane.showMessageDialog(this, "ID sản phẩm đã tồn tại!");
//                return false;
//            }
//        }
        return true;
    }

    void suaSanPham() {
        if (tblQLSP.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa!");
            return;
        }
        if (txtTen.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm");
            return;
        }
        if (txtMoTaSanPham.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả");
            return;
        }
        SanPham sp = getFormInput();
        String id = txtIDSanPham.getText();
        try {
            spd.update(id, sp);
            loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
            setSoTrang();
            clearForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi sửa!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPopup = new javax.swing.JPopupMenu();
        jMNXemChiTiet = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMoTaSanPham = new javax.swing.JTextArea();
        txtTen = new javax.swing.JTextField();
        rdoDangBan = new javax.swing.JRadioButton();
        rdoNgungBan = new javax.swing.JRadioButton();
        txtIDSanPham = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQLSP = new javax.swing.JTable();
        btnTimKiemSanPham = new javax.swing.JButton();
        txtTimKiem1 = new javax.swing.JTextField();
        btnLuiSP = new javax.swing.JButton();
        lblSoTrangSP = new javax.swing.JLabel();
        btnTienSP = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        btnThemSanPham = new javax.swing.JButton();
        btnSuaSanPham = new javax.swing.JButton();
        btnLamMoiSanPham = new javax.swing.JButton();

        jMNXemChiTiet.setBackground(new java.awt.Color(255, 204, 51));
        jMNXemChiTiet.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jMNXemChiTiet.setText("Xem chi tiết");
        jMNXemChiTiet.setOpaque(true);
        jMNXemChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMNXemChiTietActionPerformed(evt);
            }
        });
        jPopup.add(jMNXemChiTiet);

        setPreferredSize(new java.awt.Dimension(1145, 690));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1230, 829));

        jPanel2.setBackground(new java.awt.Color(255, 251, 246));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "QUẢN LÝ SẢN PHẨM", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 24))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Id Sản phẩm");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Trạng thái");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Mô tả");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Tên");

        txtMoTaSanPham.setColumns(20);
        txtMoTaSanPham.setRows(5);
        jScrollPane3.setViewportView(txtMoTaSanPham);

        buttonGroup2.add(rdoDangBan);
        rdoDangBan.setText("Đang bán");
        rdoDangBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangBanActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoNgungBan);
        rdoNgungBan.setText("Ngừng bán");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        tblQLSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Sản phẩm", "Tên sản phẩm", "Trạng thái", "Mô tả", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblQLSP);

        btnTimKiemSanPham.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTimKiemSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search3.png"))); // NOI18N
        btnTimKiemSanPham.setOpaque(true);
        btnTimKiemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemSanPhamActionPerformed(evt);
            }
        });

        txtTimKiem1.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                txtTimKiem1HierarchyChanged(evt);
            }
        });
        txtTimKiem1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtTimKiem1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        txtTimKiem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiem1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtTimKiem1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtTimKiem1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtTimKiem1MousePressed(evt);
            }
        });
        txtTimKiem1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                txtTimKiem1CaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtTimKiem1InputMethodTextChanged(evt);
            }
        });
        txtTimKiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiem1ActionPerformed(evt);
            }
        });
        txtTimKiem1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtTimKiem1PropertyChange(evt);
            }
        });
        txtTimKiem1.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                txtTimKiem1VetoableChange(evt);
            }
        });

        btnLuiSP.setText("<");
        btnLuiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuiSPActionPerformed(evt);
            }
        });

        lblSoTrangSP.setText("SoTrang");

        btnTienSP.setText(">");
        btnTienSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTienSPActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiemSanPham)
                .addGap(24, 24, 24)
                .addComponent(btnReset)
                .addGap(33, 33, 33))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(407, 407, 407)
                .addComponent(btnFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLuiSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSoTrangSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTienSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLast)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset)))
                    .addComponent(btnTimKiemSanPham))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuiSP)
                    .addComponent(lblSoTrangSP)
                    .addComponent(btnTienSP)
                    .addComponent(btnFirst)
                    .addComponent(btnLast))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnThemSanPham.setBackground(new java.awt.Color(51, 204, 0));
        btnThemSanPham.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemSanPham.setForeground(new java.awt.Color(0, 0, 0));
        btnThemSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add3.png"))); // NOI18N
        btnThemSanPham.setText("Thêm");
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        btnSuaSanPham.setBackground(new java.awt.Color(255, 204, 51));
        btnSuaSanPham.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSuaSanPham.setForeground(new java.awt.Color(0, 0, 0));
        btnSuaSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Edit.png"))); // NOI18N
        btnSuaSanPham.setText("Sửa");
        btnSuaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSanPhamActionPerformed(evt);
            }
        });

        btnLamMoiSanPham.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLamMoiSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh (1).png"))); // NOI18N
        btnLamMoiSanPham.setText("Làm mới");
        btnLamMoiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLamMoiSanPham)
                    .addComponent(btnSuaSanPham)
                    .addComponent(btnThemSanPham))
                .addGap(19, 19, 19))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnLamMoiSanPham, btnSuaSanPham, btnThemSanPham});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnThemSanPham)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSuaSanPham)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoiSanPham)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(220, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtIDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(35, 35, 35)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoDangBan)
                                .addGap(18, 18, 18)
                                .addComponent(rdoNgungBan))
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(167, 167, 167)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(249, 249, 249))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtIDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoDangBan)
                            .addComponent(rdoNgungBan)
                            .addComponent(jLabel10))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel11))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1138, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoDangBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDangBanActionPerformed

    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        if (checkADDSP()) {
            addSanPham();
        }
    }//GEN-LAST:event_btnThemSanPhamActionPerformed


    private void btnSuaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSanPhamActionPerformed
        // TODO add your handling code here:
        suaSanPham();

    }//GEN-LAST:event_btnSuaSanPhamActionPerformed

    private void btnLamMoiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiSanPhamActionPerformed
        // TODO add your handling code here:
        clearForm();

    }//GEN-LAST:event_btnLamMoiSanPhamActionPerformed

    private void tblQLSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLSPMouseClicked
        // TODO add your handling code here:
        jPopup.show(tblQLSP,evt.getX(),evt.getY());
        try {
            showDetail();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi click Table!");
        }
    }//GEN-LAST:event_tblQLSPMouseClicked

    private void xemSPCT() throws HeadlessException {
        // TODO add your handling code here:
        int index = tblQLSP.getSelectedRow();
        if (tblQLSP.getSelectedRow() != -1) {
            SanPham_View.sP_id = tblQLSP.getValueAt(index, 0).toString();
            SanPham_View.ten_SP = tblQLSP.getValueAt(index, 1).toString();
            SanPhamChiTietJDialog dialogSPCT = new SanPhamChiTietJDialog((Frame) SwingUtilities.getWindowAncestor(this), true);
            dialogSPCT.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xem chi tiết!!");
        }
    }

    private void btnTimKiemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemSanPhamActionPerformed
        testAuTo();
        lblSoTrangSP.setText("1/1");
        btnFirst.setEnabled(false);
        btnLast.setEnabled(false);
        btnLuiSP.setEnabled(false);
        btnTienSP.setEnabled(false);
    }//GEN-LAST:event_btnTimKiemSanPhamActionPerformed

    private void txtTimKiem1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTimKiem1PropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTimKiem1PropertyChange

    private void txtTimKiem1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtTimKiem1InputMethodTextChanged
        testAuTo();
    }//GEN-LAST:event_txtTimKiem1InputMethodTextChanged

    private void testAuTo() throws HeadlessException {
        // TODO add your handling code here:
        try {
            if (spd.selectAll2(txtTimKiem1.getText().trim()) != null) {
                loadDataToTableSanPham(spd.selectAll2(txtTimKiem1.getText().trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm!!");
        }
    }

    private void txtTimKiem1CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtTimKiem1CaretPositionChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTimKiem1CaretPositionChanged

    private void txtTimKiem1VetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_txtTimKiem1VetoableChange
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTimKiem1VetoableChange

    private void txtTimKiem1HierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_txtTimKiem1HierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1HierarchyChanged

    private void txtTimKiem1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiem1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1MousePressed

    private void txtTimKiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1ActionPerformed

    private void txtTimKiem1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtTimKiem1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1AncestorAdded

    private void txtTimKiem1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiem1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1MouseEntered

    private void txtTimKiem1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiem1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1MouseExited

    private void btnLuiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuiSPActionPerformed
        // TODO add your handling code here:
        preview();
    }//GEN-LAST:event_btnLuiSPActionPerformed

    private void btnTienSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTienSPActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnTienSPActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
        setSoTrang();
        btnFirst.setEnabled(true);
        btnLast.setEnabled(true);
        btnLuiSP.setEnabled(true);
        btnTienSP.setEnabled(true);
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtTimKiem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiem1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1MouseClicked

    private void jMNXemChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMNXemChiTietActionPerformed
        xemSPCT();
    }//GEN-LAST:event_jMNXemChiTietActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLamMoiSanPham;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLuiSP;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSuaSanPham;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnTienSP;
    private javax.swing.JButton btnTimKiemSanPham;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMNXemChiTiet;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopup;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblSoTrangSP;
    private javax.swing.JRadioButton rdoDangBan;
    private javax.swing.JRadioButton rdoNgungBan;
    private javax.swing.JTable tblQLSP;
    private javax.swing.JTextField txtIDSanPham;
    private javax.swing.JTextArea txtMoTaSanPham;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem1;
    // End of variables declaration//GEN-END:variables

    //hàm này có chức năng tiến lên 1 trang
    private void next() {
        if (viTriTrang < soTrang) {
            viTriTrang++;
            tienLui += 5;
            loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
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
            loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
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
        loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
    }
// hàm này có chức năng trở về trang đầu tiên

    private void first() {
        tienLui = 0;
        viTriTrang = 1;
        setSoTrang();
        loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
    }
//// code cua San pham chi tiet
//    //đổ dữ liệu lên bảng sản phẩm chi tiết
//    void fillTBSanPhamChiTiet(List<SanPhamChiTiet> list) {
//        DefaultTableModel model = (DefaultTableModel) tblQLSPCT.getModel();
//        model.setRowCount(0);
//        for (SanPhamChiTiet spct : list) {
//            model.addRow(new Object[]{
//                spct.getsPCT_id(),
//                spct.getSanPham_id(),
//                spct.getSanPham(),
//                spct.getTrangThai(),
//                spct.getMoTa(),
//                spct.getSoLuong(),
//                spct.getDonGia(),
//                spct.getSize(),
//                spct.getMauSac(),
//                spct.getChatLieu()
//            });
//        }
//    }
//
//    //lấy dữ liệu từ form trả về 1 đối tượng sản phẩm phục vụ chức năng thêm, sửa
//    SanPhamChiTiet readForm() {
//        return new SanPhamChiTiet(
//                txtSanPhamCTID.getText().trim(),
//                Integer.parseInt(txtSoLuong.getText().trim()),
//                Double.parseDouble(txtDonGia.getText().trim()),
//                sDAO.selectByGiaTri(Integer.parseInt(cboSize.getSelectedItem() + "")).getId(),
//                clDAO.selectByTenChatLieu(cboChatLieu.getSelectedItem().toString()).getId(),
//                1,
//                lblAnhSanPham.getToolTipText(),
//                mauDAO.selectByTenMau(cboMauSac.getSelectedItem() + "").getId(),
//                spd.selectByName(cboTenSanPhamCT.getSelectedItem() + "").getID(),
//                rdoConHangSPCT.isSelected() ? "Còn hàng" : "Hết hàng",
//                txtMoTaSPCT.getText().trim());
//    }
//
//    //set dữ liệu cho cbo Tên sản phẩm
//    void setComboTenSP() {
//        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenSanPhamCT.getModel();
//        model.removeAllElements();
//        for (SanPham sp : spd.selectAll()) {
//            model.addElement(sp.getTenSP());
//        }
//    }
//
//    //set dữ liệu cho cbo size
//    void setComboSize() {
//        DefaultComboBoxModel modelc = (DefaultComboBoxModel) cboSize.getModel();
//        modelc.removeAllElements();
//        for (Size size : sDAO.selectAll()) {
//            modelc.addElement(size.getGiatri());
//        }
//    }
//
//    //set dữ liệu cho cbo màu sắc
//    void setComboMauSac() {
//        DefaultComboBoxModel modelc = (DefaultComboBoxModel) cboMauSac.getModel();
//        modelc.removeAllElements();
//        for (MauSac mauSac : mauDAO.selectAll()) {
//            modelc.addElement(mauSac.getTenMau());
//        }
//    }
//
//    //set dữ liệu cho cbo chất liệu
//    void setComboChatLieu() {
//        DefaultComboBoxModel modelc = (DefaultComboBoxModel) cboChatLieu.getModel();
//        modelc.removeAllElements();
//        for (ChatLieu cl : clDAO.selectAll()) {
//            modelc.addElement(cl.getTenCL());
//        }
//    }
//
//    //hàm thêm sản phẩm chi tiết
//    private void addSPCT() {
//        if (checkFormSPCT()) {
//            SanPhamChiTiet spct = readForm();
//            if (spctDAO.insert(spct) > 0) {
//                XNotification.thongBao = "Thêm spct thành công";
//                try {
//                    XNotification.main();
//                } catch (AWTException ex) {
//                    Logger.getLogger(SanPham_View.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                this.fillTBSanPhamChiTiet(spctDAO.selectAll2());
//            } else {
//                JOptionPane.showMessageDialog(this, "Thêm spct thất bại");
//            }
//        }
//
//    }
//
//    // hàm đổ dữ liệu từ đối tượng được chọn lên các trường của form
//    void showDetailSPCT() {
//        int index = tblQLSPCT.getSelectedRow();
//        SanPhamChiTiet spct = spctDAO.selectSPTCByID(tblQLSPCT.getValueAt(index, 0).toString());
//        txtSanPhamCTID.setText(spct.getsPCT_id());
//        cboTenSanPhamCT.setSelectedItem(spct.getSanPham());
//        if (spct.getTrangThai().equals("Còn hàng")) {
//            rdoConHangSPCT.setSelected(true);
//        } else {
//            rdoHetHangSPCT.setSelected(true);
//        }
//        ImageIcon icon = new ImageIcon(spct.getAnh());
//        // Đặt kích thước cho hình ảnh
//        Image image = icon.getImage().getScaledInstance(266, 266, Image.SCALE_SMOOTH);
//        ImageIcon resizedIcon = new ImageIcon(image);
//        // Đặt hình ảnh cho đối tượng JLabel
//        lblAnhSanPham.setIcon(resizedIcon);
//        txtMoTaSPCT.setText(spct.getMoTa());
//        txtSoLuong.setText(spct.getSoLuong() + "");
//        txtDonGia.setText(spct.getDonGia() + "");
//        cboSize.setSelectedItem(spct.getSize());
//        cboMauSac.setSelectedItem(spct.getMauSac());
//        cboChatLieu.setSelectedItem(spct.getChatLieu());
//        tblQLSPCT.setRowSelectionInterval(index, index);
//    }
//
//    //hàm thực hiện chọn hình ảnh cho sản phẩm chi tiết
//    private void fillAnhSPCT() {
//        JFileChooser jfileSelected = new JFileChooser();
//        int result = jfileSelected.showOpenDialog(null);
//        if (result == jfileSelected.APPROVE_OPTION) {
//            File file = jfileSelected.getSelectedFile();
//            String duongDanAnh = file.getAbsolutePath();
//            ImageIcon icon = new ImageIcon(duongDanAnh);
//            // Đặt kích thước cho hình ảnh
//            Image image = icon.getImage().getScaledInstance(266, 266, Image.SCALE_SMOOTH);
//            ImageIcon resizedIcon = new ImageIcon(image);
//            // Đặt hình ảnh cho đối tượng JLabel
//            lblAnhSanPham.setIcon(resizedIcon);
//            lblAnhSanPham.setToolTipText(duongDanAnh);
//        }
//    }
//
//    private void xoaSanPhamChiTiet() {
//        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá spct này?", "Xoá sản phẩm chi tiết", JOptionPane.YES_OPTION);
//        if (choice == JOptionPane.YES_OPTION) {
//            int index = tblQLSPCT.getSelectedRow();
//            String key = tblQLSPCT.getValueAt(index, 0).toString();
//            try {
//                if (spctDAO.delete(key) != 0) {
//                    //JOptionPane.showMessageDialog(this, "Xoá sản phẩm chi tiết thành công!!");
//
//                    XNotification.thongBao = "Xoá sản phẩm chi tiết thành công!!";
//                    XNotification.main();
//                    this.fillTBSanPhamChiTiet(spctDAO.selectAll2());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(this, "Lỗi xoá sản phẩm chi tiết!!");
//            }
//        }
//    }
//
//    private void suaSPCT() throws AWTException {
//        if (tblQLSPCT.getSelectedRow() >= 0) {
//
//            int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa spct?", "SỬA SPCT", JOptionPane.YES_OPTION);
//            if (choice == JOptionPane.YES_OPTION) {
//
//                if (checkFormSPCTUpdate()) {
//                    try {
//                        int index = tblQLSPCT.getSelectedRow();
//                        SanPhamChiTiet spct = readForm();
//                        String key = tblQLSPCT.getValueAt(index, 0).toString();
//                        if (spctDAO.update(key, spct) > 0) {
//                            fillTBSanPhamChiTiet(spctDAO.selectAll2());
//                            XNotification.thongBao = "Sửa spct thành công";
//                            XNotification.main();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        XNotification.thongBao = "Sửa spct thất bại";
//                        XNotification.main();
//                    }
//                }
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa spct!!");
//        }
//    }
//
//    private void lamMoiFormSPCT() throws AWTException {
//        cboChatLieu.setSelectedIndex(0);
//        cboMauSac.setSelectedIndex(0);
//        cboSize.setSelectedIndex(0);
//        cboTenSanPhamCT.setSelectedIndex(0);
//        txtSanPhamCTID.setText("");
//        txtDonGia.setText("");
//        txtSoLuong.setText("");
//        rdoConHangSPCT.setSelected(true);
//        txtMoTaSPCT.setText("");
//        XNotification.thongBao = "Clear form spct!!";
//        XNotification.main();
//    }
//
//    private boolean checkFormSPCT() {
//        if (txtSanPhamCTID.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "ID spct không được để trống!!");
//            txtSanPhamCTID.requestFocus();
//            return false;
//        } else if (txtSanPhamCTID.getText().trim().length() > 10) {
//            JOptionPane.showMessageDialog(this, "ID spct không được lớn hơn 10 ký tự!!");
//            txtSanPhamCTID.requestFocus();
//            return false;
//        }
//        for (SanPhamChiTiet spct : spctDAO.selectAll2()) {
//            if (txtSanPhamCTID.getText().trim().equalsIgnoreCase(spct.getsPCT_id())) {
//                JOptionPane.showMessageDialog(this, "Trùng ID spct!!");
//                txtSanPhamCTID.requestFocus();
//                return false;
//            }
//        }
//
//        try {
//            if (txtSoLuong.getText().trim().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Số lượng không được để trống!!");
//                txtSoLuong.requestFocus();
//                return false;
//            }
//            if (Integer.parseInt(txtSoLuong.getText().trim()) < 0) {
//                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn hoặc bằng 0!!");
//                txtSoLuong.requestFocus();
//                return false;
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Số lượng phải là số!!");
//            txtSoLuong.requestFocus();
//            return false;
//        }
//        try {
//            if (txtDonGia.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Không được để trống đơn giá!!");
//                txtDonGia.requestFocus();
//                return false;
//            } else if (Double.parseDouble(txtDonGia.getText().trim()) <= 0) {
//                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!!");
//                txtDonGia.requestFocus();
//                return false;
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Đơn giá phải là số!!");
//            txtDonGia.requestFocus();
//            return false;
//        }
//        return true;
//    }
//
//    private boolean checkFormSPCTUpdate() {
//        if (txtSanPhamCTID.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "ID spct không được để trống!!");
//            txtSanPhamCTID.requestFocus();
//            return false;
//        } else if (txtSanPhamCTID.getText().trim().length() > 10) {
//            JOptionPane.showMessageDialog(this, "ID spct không được lớn hơn 10 ký tự!!");
//            txtSanPhamCTID.requestFocus();
//            return false;
//        }
////        for (SanPhamChiTiet spct : spctDAO.selectAll2()) {
////            if (txtSanPhamCTID.getText().trim().equalsIgnoreCase(spct.getsPCT_id())) {
////                JOptionPane.showMessageDialog(this, "Trùng ID spct!!");
////                txtSanPhamCTID.requestFocus();
////                return false;
////            }
////        }
//
//        try {
//            if (txtSoLuong.getText().trim().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Số lượng không được để trống!!");
//                txtSoLuong.requestFocus();
//                return false;
//            }
//            if (Integer.parseInt(txtSoLuong.getText().trim()) < 0) {
//                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn hoặc bằng 0!!");
//                txtSoLuong.requestFocus();
//                return false;
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Số lượng phải là số!!");
//            txtSoLuong.requestFocus();
//            return false;
//        }
//        try {
//            if (txtDonGia.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Không được để trống đơn giá!!");
//                txtDonGia.requestFocus();
//                return false;
//            } else if (Double.parseDouble(txtDonGia.getText().trim()) <= 0) {
//                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!!");
//                txtDonGia.requestFocus();
//                return false;
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Đơn giá phải là số!!");
//            txtDonGia.requestFocus();
//            return false;
//        }
//        return true;
//    }

}
