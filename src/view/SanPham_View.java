/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import dao.BrandDAO;
import dao.ChatLieuDAO;
import dao.LoaiDeGiayDAO;
import dao.NhaCungCapDAO;
import dao.SanPhamDAO;
import entity.Brand;
import entity.ChatLieu;
import entity.LoaiDeGiay;
import entity.NhaCungCap;
import entity.SanPham;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Trong Phu
 */
public class SanPham_View extends javax.swing.JPanel {

    private final DefaultComboBoxModel<Brand> dcbmBrand = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<ChatLieu> dcbmChatLieu = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<LoaiDeGiay> dcbmLoaiDeGiay = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<NhaCungCap> dcbmNhaCungCap = new DefaultComboBoxModel<>();

    private DefaultTableModel dtm;

    private BrandDAO brandDAO;
    private LoaiDeGiayDAO loaiDeGiayDAO;
    private ChatLieuDAO chatLieuDAO;
    private NhaCungCapDAO nhaCungCapDAO;

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

        // Khởi tạo DAO objects
        brandDAO = new BrandDAO();
        loaiDeGiayDAO = new LoaiDeGiayDAO();
        chatLieuDAO = new ChatLieuDAO();
        nhaCungCapDAO = new NhaCungCapDAO();

        // day();
        setSoTrang();
//        loadDataToTableSanPham(spd.getAll());
        loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));//load bảng sản phẩm

        this.showComboBoxData();
    }

    private void showComboBoxData() {
        try {
            // Xóa dữ liệu cũ trong các ComboBoxModel
            dcbmBrand.removeAllElements();
            dcbmChatLieu.removeAllElements();
            dcbmLoaiDeGiay.removeAllElements();
            dcbmNhaCungCap.removeAllElements();

            for (Brand brand : brandDAO.selectAll()) {
                cboBrand.addItem(brand.getTenBrand());
            }

            for (LoaiDeGiay loaiDeGiay : loaiDeGiayDAO.selectAll()) {
                cboLoaiDeGiay.addItem(loaiDeGiay.getTenDeGiay());
            }

            for (ChatLieu chatLieu : chatLieuDAO.selectAll()) {
                cboChatLieu.addItem(chatLieu.getTenChatLieu());
            }

            for (NhaCungCap nhaCungCap : nhaCungCapDAO.selectAll()) {
                cboNhaCungCap.addItem(nhaCungCap.getTenNhaCC());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu ComboBox!");
            ex.printStackTrace(); // In ra stack trace để debug lỗi
        }
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
        soTrangLe = Math.ceil((double) spd.getAll().size() / 5);
        soTrang = (int) soTrangLe;
        lblSoTrangSP.setText(viTriTrang + "/" + soTrang);
    }

    //hàm này có chức năng đổ dữ liệu lên bảng quản lý sản phẩm
    private void loadDataToTableSanPham(List<SanPham> lst) {

        DefaultTableModel model = (DefaultTableModel) tblQLSP.getModel();
        model.setRowCount(0); // Xóa hết dữ liệu cũ trong bảng

        for (SanPham sp : lst) {

            Object[] rowData = new Object[]{
                sp.getSanPham_id(),
                sp.getTenSanPham(),
                sp.getTrangThai(),
                sp.getMoTa(),
                
                sp.getBrand().getTenBrand(), // Thêm thông tin Brand

                sp.getLoaiDeGiay().getTenDeGiay(), // Thêm thông tin LoaiDeGiay

                sp.getChatLieu().getTenChatLieu(), // Thêm thông tin ChatLieu

                sp.getNhaCungCap().getTenNhaCC() // Thêm thông tin NhaCungCap
            };

            model.addRow(rowData); // Thêm dòng dữ liệu vào model
        }
    }
// hàm này có chức năng khi ấn vào 1 dòng trên bảng sẽ hiển thị thông tin lên form quản lý sản phẩm

    private void showDetail() {
        int index = tblQLSP.getSelectedRow();

        if (index >= 0) {
            txtIDSanPham.setText(tblQLSP.getValueAt(index, 0).toString());

            txtTen.setText(tblQLSP.getValueAt(index, 1).toString());

            if ("Đang bán".equals(tblQLSP.getValueAt(index, 2))) {
                rdoDangBan.setSelected(true);
            } else {
                rdoNgungBan.setSelected(true);
            }

            txtMoTaSanPham.setText(tblQLSP.getValueAt(index, 3).toString());

            cboBrand.setSelectedItem(tblQLSP.getValueAt(index, 4));

            cboLoaiDeGiay.setSelectedItem(tblQLSP.getValueAt(index, 5));

            cboChatLieu.setSelectedItem(tblQLSP.getValueAt(index, 6));

            cboNhaCungCap.setSelectedItem(tblQLSP.getValueAt(index, 7));
        }
    }
// hàm này có chức năng làm mới form quản lý sản phấm

    private void clearForm() {

        txtIDSanPham.setText("");

        txtTen.setText("");

        txtMoTaSanPham.setText("");

        rdoDangBan.setSelected(true);

        tblQLSP.clearSelection();

    }

    // hàm này có chức năng đọc dữ liệu iput trên form  trả về 1 đối tướng sản phẩm để thực hiện thêm và sửa sản phẩm
    private SanPham getFormInput() {

        String trangThai = rdoDangBan.isSelected() ? "Đang bán" : "Ngừng bán";

        String tenBrand = (String) cboBrand.getSelectedItem();
        Integer brand_id = brandDAO.selectBrandIdByName(tenBrand);
        if (brand_id == null) {
            throw new RuntimeException("Không tìm thấy Brand với tên: " + tenBrand);
        }

        String tenLoaiDeGiay = (String) cboLoaiDeGiay.getSelectedItem();
        Integer loaiDeGiay_id = loaiDeGiayDAO.selectLoaiDeGiayIdByName(tenLoaiDeGiay);
        if (loaiDeGiay_id == null) {
            throw new RuntimeException("Không tìm thấy LoaiDeGiay với tên: " + tenLoaiDeGiay);
        }

        String tenChatLieu = (String) cboChatLieu.getSelectedItem();
        Integer chatLieu_id = chatLieuDAO.selectChatLieuIdByName(tenChatLieu);
        if (chatLieu_id == null) {
            throw new RuntimeException("Không tìm thấy ChatLieu với tên: " + tenChatLieu);
        }

        String tenNhaCC = (String) cboNhaCungCap.getSelectedItem();
        Integer nhaCC_id = nhaCungCapDAO.selectNhaCCIdByName(tenNhaCC);
        if (nhaCC_id == null) {
            throw new RuntimeException("Không tìm thấy NhaCungCap với tên: " + tenNhaCC);
        }

        SanPham sp = new SanPham();

        sp.setSanPham_id(txtIDSanPham.getText());

        sp.setTenSanPham(txtTen.getText());

        sp.setTrangThai(trangThai);

        sp.setMoTa(txtMoTaSanPham.getText());

        // Gán các đối tượng Brand, LoaiDeGiay, ChatLieu, NhaCungCap vào SanPham
        Brand brand = new Brand();
        brand.setBrand_id(brand_id);
        sp.setBrand(brand);

        LoaiDeGiay loaiDeGiay = new LoaiDeGiay();
        loaiDeGiay.setDeGiay_id(loaiDeGiay_id);
        sp.setLoaiDeGiay(loaiDeGiay);

        ChatLieu chatLieu = new ChatLieu();
        chatLieu.setChatLieu_id(chatLieu_id);
        sp.setChatLieu(chatLieu);

        NhaCungCap nhaCungCap = new NhaCungCap();
        nhaCungCap.setNhaCC_id(nhaCC_id);
        sp.setNhaCungCap(nhaCungCap);

        return sp;
    }

    // hàm này có tác dụng thêm sản phẩm
    private void addSanPham() throws HeadlessException {
        if (!validateInputForAdd()) {
            return;
        }
        try {
            SanPham sp = getFormInput();
            spd.insert(sp);
            loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
            setSoTrang();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm: " + ex.getMessage());
        }
    }
// hàm này có tác dụng kiểm tra và yêu cầu người dùng nhập dữ liệu đúng với kiểu dữ liệu của các trường

    private boolean validateInputForUpdate() {
        String idSanPham = txtIDSanPham.getText().trim();
        String tenSanPham = txtTen.getText().trim();
        String moTa = txtMoTaSanPham.getText().trim();

        if (idSanPham.isEmpty() || idSanPham.length() > 10) {
            JOptionPane.showMessageDialog(this, "ID sản phẩm không hợp lệ!");
            return false;
        }

        SanPham currentSanPham = spd.selectById(idSanPham);

        if (currentSanPham == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm với ID này!");
            return false;
        }

        for (SanPham sp : spd.getAll()) {
            if (!sp.getSanPham_id().equals(currentSanPham.getSanPham_id()) && idSanPham.equalsIgnoreCase(sp.getSanPham_id())) {
                JOptionPane.showMessageDialog(this, "ID sản phẩm đã tồn tại!");
                return false;
            }
        }

        if (tenSanPham.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm!");
            return false;
        }

        for (SanPham sp : spd.getAll()) {
            if (!sp.getTenSanPham().equals(currentSanPham.getTenSanPham()) && tenSanPham.equalsIgnoreCase(sp.getTenSanPham())) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm đã tồn tại!");
                return false;
            }
        }

        if (moTa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả sản phẩm!");
            return false;
        }

        return true;
    }

    private boolean validateInputForAdd() {
        String idSanPham = txtIDSanPham.getText().trim();
        String tenSanPham = txtTen.getText().trim();
        String moTa = txtMoTaSanPham.getText().trim();

        if (idSanPham.isEmpty() || idSanPham.length() > 10) {
            JOptionPane.showMessageDialog(this, "ID sản phẩm không hợp lệ!");
            return false;
        }

        for (SanPham sp : spd.getAll()) {
            if (idSanPham.equalsIgnoreCase(sp.getSanPham_id())) {
                JOptionPane.showMessageDialog(this, "ID sản phẩm đã tồn tại!");
                return false;
            }
        }

        if (tenSanPham.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm!");
            return false;
        }

        for (SanPham sp : spd.getAll()) {
            if (tenSanPham.equalsIgnoreCase(sp.getTenSanPham())) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm đã tồn tại!");
                return false;
            }
        }

        if (moTa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả sản phẩm!");
            return false;
        }

        return true;
    }

    void suaSanPham() {
        if (tblQLSP.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa!");
            return;
        }

        if (!validateInputForUpdate()) {
            return;
        }

        SanPham sp = getFormInput();

        try {
            spd.update(sp);
            loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
            setSoTrang();
            clearForm();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi sửa sản phẩm: " + e.getMessage());
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
        btnXoaSanPham = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cboBrand = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboLoaiDeGiay = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cboChatLieu = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cboNhaCungCap = new javax.swing.JComboBox<>();

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
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Sản phẩm", "Tên sản phẩm", "Trạng thái", "Mô tả", "Brand", "Đế giày", "Chất liệu", "Nhà cung cấp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
        if (tblQLSP.getColumnModel().getColumnCount() > 0) {
            tblQLSP.getColumnModel().getColumn(5).setHeaderValue("Đế giày");
        }

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
                .addContainerGap(416, Short.MAX_VALUE))
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
        btnThemSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add3.png"))); // NOI18N
        btnThemSanPham.setText("Thêm");
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        btnSuaSanPham.setBackground(new java.awt.Color(255, 204, 51));
        btnSuaSanPham.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        btnXoaSanPham.setBackground(new java.awt.Color(255, 204, 51));
        btnXoaSanPham.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Delete.png"))); // NOI18N
        btnXoaSanPham.setText("Xóa");
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLamMoiSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnLamMoiSanPham, btnSuaSanPham, btnThemSanPham});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnThemSanPham)
                .addGap(18, 18, 18)
                .addComponent(btnSuaSanPham)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(btnXoaSanPham)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoiSanPham)
                .addGap(21, 21, 21))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Brand");

        cboBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboBrandActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Loại đế giày");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Chất liệu");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Nhà cung cấp");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel15)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtIDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel5)
                        .addGap(52, 52, 52)
                        .addComponent(cboBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoDangBan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdoNgungBan))
                            .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(52, 52, 52)
                                .addComponent(cboLoaiDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(52, 52, 52)
                                .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(52, 52, 52)
                                .addComponent(cboNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel15, jLabel4});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, jLabel6, jLabel7, jLabel8});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtIDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(cboBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(cboLoaiDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(rdoDangBan)
                            .addComponent(rdoNgungBan)
                            .addComponent(jLabel7)
                            .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(cboNhaCungCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel15, jLabel4});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel5, jLabel6, jLabel7, jLabel8});

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 105, Short.MAX_VALUE))
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
        addSanPham();
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
        jPopup.show(tblQLSP, evt.getX(), evt.getY());
        try {
            showDetail();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi click Table!");
        }
    }//GEN-LAST:event_tblQLSPMouseClicked

    // Liên kết với SanPhamChiTietJDialog để xem sản phẩm chi tiết
    private void xemSPCT() throws HeadlessException {

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

    private void xoaSanPham() {
        if (tblQLSP.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                String idSanPham = txtIDSanPham.getText();
                spd.delete(idSanPham);
                loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
                setSoTrang();
                clearForm();
                JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm!");
            }
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
            if (spd.selectAll(txtTimKiem1.getText().trim()) != null) {
                loadDataToTableSanPham(spd.selectAll(txtTimKiem1.getText().trim()));
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

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        // TODO add your handling code here:
        // Chuyển trạng thái Đang bán thành Ngừng bán 
        int row = tblQLSP.getSelectedRow();
        if (row == -1) {
            // Kiểm tra nếu không có dòng nào được chọn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần ngừng bán!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sanPhamId = (String) tblQLSP.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn ngừng bán sản phẩm này?", "Xác nhận ngừng bán", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return; // Nếu người dùng không đồng ý, thoát khỏi hàm
        }

        try {

            spd.delete(sanPhamId);
            loadDataToTableSanPham(spd.phanTrangSanPham(tienLui));
            setSoTrang();
            clearForm();
            JOptionPane.showMessageDialog(this, "Đã ngừng bán sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void cboBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboBrandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboBrandActionPerformed


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
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboBrand;
    private javax.swing.JComboBox<String> cboChatLieu;
    private javax.swing.JComboBox<String> cboLoaiDeGiay;
    private javax.swing.JComboBox<String> cboNhaCungCap;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
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

}
