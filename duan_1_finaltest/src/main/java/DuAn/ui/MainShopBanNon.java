/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package DuAn.ui;

import DuAn.constant.Constant;
import DuAn.dao.CommonDao;
import DuAn.dao.HoaDonChiTietDAO;
import DuAn.dao.HoaDonDao;
import DuAn.dao.KhachHangDAO;
import DuAn.dao.LoaiDAO;
import DuAn.dao.NhanVienDAO;
import DuAn.dao.NonDAO;
import DuAn.dao.ThongKeDAO;
import DuAn.dao.ThuongHieuDAO;
import DuAn.entity.Entity;
import DuAn.entity.HoaDonChiTiet;
import DuAn.entity.KhachHang;
import DuAn.entity.Loai;
import DuAn.entity.NhanVien;
import DuAn.entity.Non;
import DuAn.entity.HoaDon;
import DuAn.entity.KhachHangThongKe;
import DuAn.entity.NhanVienFile;

import DuAn.entity.ThuongHieu;
import DuAn.utils.Auth;
import DuAn.utils.Common;
import DuAn.utils.MsgBox;
import DuAn.utils.XDate;
import DuAn.utils.XExcel;
import DuAn.utils.XImage;
import DuAn.utils.XMail;
import DuAn.utils.XPDF;
import com.beust.jcommander.internal.Lists;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author tiend
 */
public class MainShopBanNon extends javax.swing.JFrame {

    /**
     * Creates new form MainShopBanGiay
     */
    NhanVienDAO nvdao = new NhanVienDAO();
    ThuongHieuDAO thdao = new ThuongHieuDAO();
    KhachHangDAO khdao = new KhachHangDAO();
    LoaiDAO ldao = new LoaiDAO();
    NonDAO ndao = new NonDAO();
    ThongKeDAO tkdao = new ThongKeDAO();
    Border grayBorderThuongHieuTen = Constant.getGrayBorder("Tên thương hiệu");
    Border redBorderThuongHieuTen = Constant.getRedBorder("Tên thương hiệu");

    public MainShopBanNon() {
        initComponents();

        fillAllTable();
        fillAllCombobox();

//        clearAllForm();
        AutoCompleteDecorator.decorate(cboHoaDonNon);
        AutoCompleteDecorator.decorate(cboHoaDonSDT);
        AutoCompleteDecorator.decorate(cboNonLoai);
        AutoCompleteDecorator.decorate(cboNonThuongHieu);
        this.dcNgayBatDau.setDate(new Date());
        this.dcNgayKetThuc.setDate(new Date());

        //        this.chart.add(new BarChartExample().setVisible(true));
        dongho();
        try {

            if (!Auth.user.isVaiTro()) {
                jTabbedPane1.remove(this.pnlNhanVien);
                jTabbedPane1.remove(this.pnlThongKe);
                btnHoaDonXoa.setVisible(false);
                btnNhanVienXoa.setVisible(false);
                btnKhachHangXoa.setVisible(false);
                btnNonXoa.setVisible(false);
                btnThuongHieuXoa.setVisible(false);
                btnLoaiXoa.setVisible(false);
            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Bạn chưa đăng nhập");
//            e.printStackTrace();
            new LoginJDialog();
            this.dispose();
            return;
        }
        this.setVisible(true);

        this.setLocationRelativeTo(null);
        if (Auth.user != null) {
            lblTen.setText(Auth.user.getHoTen());
        }
        JFrame frame = this;

        this.tblBanHang.getModel().addTableModelListener(
                new TableModelListener() {

            public void tableChanged(TableModelEvent evt) {
                // here goes your code "on cell update"
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                if (column == 2) { // Check if the changed column is column 2
                    // Retrieve the value of column 3 in the same row
                    Object soluong = tblBanHang.getModel().getValueAt(row, 2);
                    // Check if the value of column 3 has changed
                    if (Integer.parseInt(soluong.toString()) < 1) {
                        MsgBox.showMessageDialog(frame, "Số lượng không được bé hơn 1");
                        soluong = 1;
                    }
                    Object maNon = tblBanHang.getModel().getValueAt(row, 0);
                    Non non = ndao.selectById(getId(maNon.toString()));
                    int soLuongHienCo = non.getSoLuong() - Integer.parseInt(soluong.toString());
                    if (soLuongHienCo < 0) {
                        MsgBox.showMessageDialog(frame, "Số lượng có mã SP" + non.getMaNon() + " chỉ có " + non.getSoLuong() + "\nVui lòng chọn lại số lượng");
                        soluong = non.getSoLuong();
                    }
                    hoaDonChiTietList.get(row).setSoLuong(Integer.parseInt(soluong.toString()));
                    fillTableBanHang(hoaDonChiTietList);
                }
            }
        }
        );

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioChucVu = new javax.swing.ButtonGroup();
        radioGioiTinh = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblKhacHangNhieuNhat = new javax.swing.JLabel();
        lblSoDienThoaiKhachHang = new javax.swing.JLabel();
        lblTenKhachHang = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        txtDoanhThuHomNay = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        txtDoanhThu = new javax.swing.JLabel();
        pnlChart = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBanHang = new javax.swing.JTable();
        cboHoaDonNon = new javax.swing.JComboBox<>();
        txtHoaDonID = new javax.swing.JTextField();
        txtHoaDonTenKhachHang = new javax.swing.JTextField();
        txtHoaDonNgayTao = new javax.swing.JTextField();
        txtHoaDonTongTien = new javax.swing.JTextField();
        btnHoaDonLuu = new javax.swing.JButton();
        btnHoaDonHuy = new javax.swing.JButton();
        cboHoaDonSDT = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        txtThuongHieuID = new javax.swing.JTextField();
        btnThuongHieuSua = new javax.swing.JButton();
        btnThuongHieuThem = new javax.swing.JButton();
        btnThuongHieuLamMoi = new javax.swing.JButton();
        txtThuongHieuTen = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        lblThuongHieuHinhAnh = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblThuongHieu = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        txtThuongHieuTimKiemTen = new javax.swing.JTextField();
        btnThuongHieuXoa = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        txtKhachHangID = new javax.swing.JTextField();
        txtKhachHangTen = new javax.swing.JTextField();
        txtKhachHangEmail = new javax.swing.JTextField();
        txtKhachHangNgaySinh = new com.toedter.calendar.JDateChooser();
        txtKhachHangSDT = new javax.swing.JTextField();
        btnKhachHangThem = new javax.swing.JButton();
        btnKhachHangSua = new javax.swing.JButton();
        btnKhachHangLamMoi = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        rdoKhachHangNu = new javax.swing.JRadioButton();
        rdoKhachHangNam = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        lblKhachHangHinhAnh = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        btnExcelKhachHang = new javax.swing.JButton();
        btnPDFKhachHang = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        txtKhachHangTimKiemSoDienThoai = new javax.swing.JTextField();
        btnChonKhachHang = new javax.swing.JButton();
        btnKhachHangXoa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        txtNonID = new javax.swing.JTextField();
        txtNonTen = new javax.swing.JTextField();
        txtNonGia = new javax.swing.JTextField();
        cboNonThuongHieu = new javax.swing.JComboBox<>();
        txtNonGiamGia = new javax.swing.JTextField();
        cboNonLoai = new javax.swing.JComboBox<>();
        btnPDFNon = new javax.swing.JButton();
        btnNonLamMoi = new javax.swing.JButton();
        btnNonThem = new javax.swing.JButton();
        btnNonSua = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        lblNonHinhAnh = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNon = new javax.swing.JTable();
        btnExcel = new javax.swing.JButton();
        txtNonSoLuong = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        txtNonTimKiemTen = new javax.swing.JTextField();
        btnNonXoa = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        txtLoaiID = new javax.swing.JTextField();
        btnLoaiSua = new javax.swing.JButton();
        btnLoaiThem = new javax.swing.JButton();
        txtLoaiTen = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblLoai = new javax.swing.JTable();
        btnLoaiLamMoi = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        txtLoaiTimKiemTen = new javax.swing.JTextField();
        btnLoaiXoa = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        pnlNhanVien = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lblNhanVienHinhAnh = new javax.swing.JLabel();
        txtNhanVienHoTen = new javax.swing.JTextField();
        txtNhanVienID = new javax.swing.JTextField();
        txtNhanVienXacNhanMatKhau = new javax.swing.JPasswordField();
        txtNhanVienMatKhau = new javax.swing.JPasswordField();
        jPanel9 = new javax.swing.JPanel();
        rdoNhanVienNhanVien = new javax.swing.JRadioButton();
        rdoNhanVienQuanLy = new javax.swing.JRadioButton();
        btnNhanVienThem = new javax.swing.JButton();
        btnNhanVienSua = new javax.swing.JButton();
        btnNhanVienLamMoi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        btnNhanVienExcel = new javax.swing.JButton();
        btnNhanVienPDF = new javax.swing.JButton();
        txtNhanVienSDT = new javax.swing.JTextField();
        txtNhanVienEmail = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        txtNhanVienTimKiem = new javax.swing.JTextField();
        btnNhanVienXoa = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel28 = new javax.swing.JPanel();
        txtHoaDonTimKiem = new javax.swing.JTextField();
        btnHoaDonChiTiet = new javax.swing.JButton();
        btnHoaDonChiTiet1 = new javax.swing.JButton();
        btnHoaDonXoa = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        pnlThongKe = new javax.swing.JPanel();
        jtpThongKe = new javax.swing.JTabbedPane();
        pnlThongKeNon = new javax.swing.JPanel();
        pnlDoanhThuTrongNam = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        pnlKhachHangMuaNhieuNhat = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        pnlDoanhThuTheoNam = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        pnlSoLuongThuongHieu = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        pnlTrangThaiHoaDon = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblThongKeHoaDon = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        lblTongTien = new javax.swing.JLabel();
        dcNgayBatDau = new com.toedter.calendar.JDateChooser();
        dcNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        jPanel36 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblThongKeKhachHang = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        txtDoiMatKhauXacNhanMatKhau = new javax.swing.JPasswordField();
        txtDoiMatKhauMatKhauCu = new javax.swing.JPasswordField();
        txtDoiMatKhauMatKhauMoi = new javax.swing.JPasswordField();
        btnDoiMatKhau = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTen = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblDongHo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(6, 93, 157));

        jTabbedPane1.setBackground(new java.awt.Color(6, 93, 157));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(0, 80, 140));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Trang chủ");

        jPanel31.setAutoscrolls(true);

        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder("Thống kê"));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Khách hàng mua nhiều nhất"));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        lblKhacHangNhieuNhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblKhacHangNhieuNhat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKhacHangNhieuNhat.setText("23");

        lblSoDienThoaiKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSoDienThoaiKhachHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoDienThoaiKhachHang.setText("23");

        lblTenKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenKhachHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenKhachHang.setText("23");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSoDienThoaiKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(lblKhacHangNhieuNhat, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(lblTenKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTenKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblKhacHangNhieuNhat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSoDienThoaiKhachHang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Doanh thu hôm nay"));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel11MouseClicked(evt);
            }
        });

        txtDoanhThuHomNay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDoanhThuHomNay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtDoanhThuHomNay.setText("22");
        txtDoanhThuHomNay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDoanhThuHomNay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(txtDoanhThuHomNay)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder("Tổng doanh thu trong năm"));
        jPanel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel41MouseClicked(evt);
            }
        });

        txtDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtDoanhThu.setText("23");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(txtDoanhThu)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pnlChart.setAutoscrolls(true);

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 358, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(335, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("                    Trang chủ", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-home-35.png")), jPanel4); // NOI18N

        jPanel18.setBackground(new java.awt.Color(0, 80, 140));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        tblBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Giá", "Thành tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBanHangMouseClicked(evt);
            }
        });
        tblBanHang.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                tblBanHangInputMethodTextChanged(evt);
            }
        });
        tblBanHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblBanHangKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tblBanHangKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(tblBanHang);
        if (tblBanHang.getColumnModel().getColumnCount() > 0) {
            tblBanHang.getColumnModel().getColumn(0).setResizable(false);
            tblBanHang.getColumnModel().getColumn(1).setResizable(false);
            tblBanHang.getColumnModel().getColumn(2).setResizable(false);
            tblBanHang.getColumnModel().getColumn(3).setResizable(false);
            tblBanHang.getColumnModel().getColumn(4).setResizable(false);
        }

        cboHoaDonNon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboHoaDonNon.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã sản phẩm"));
        cboHoaDonNon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHoaDonNonActionPerformed(evt);
            }
        });

        txtHoaDonID.setEditable(false);
        txtHoaDonID.setText("ID sẽ tự sinh");
        txtHoaDonID.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã hóa đơn"));

        txtHoaDonTenKhachHang.setEditable(false);
        txtHoaDonTenKhachHang.setBorder(javax.swing.BorderFactory.createTitledBorder("Họ tên khách hàng"));

        txtHoaDonNgayTao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtHoaDonNgayTao.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày tạo"));
        txtHoaDonNgayTao.setEnabled(false);

        txtHoaDonTongTien.setEditable(false);
        txtHoaDonTongTien.setBorder(javax.swing.BorderFactory.createTitledBorder("Tổng tiền"));

        btnHoaDonLuu.setText("Lưu");
        btnHoaDonLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonLuuActionPerformed(evt);
            }
        });

        btnHoaDonHuy.setText("Hủy");
        btnHoaDonHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonHuyActionPerformed(evt);
            }
        });

        cboHoaDonSDT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboHoaDonSDT.setBorder(javax.swing.BorderFactory.createTitledBorder("Số điện thoại khách hàng"));
        cboHoaDonSDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboHoaDonSDTMouseClicked(evt);
            }
        });
        cboHoaDonSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHoaDonSDTActionPerformed(evt);
            }
        });

        jButton2.setText("Quét QR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel26Layout.createSequentialGroup()
                            .addComponent(btnHoaDonLuu)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnHoaDonHuy)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 497, Short.MAX_VALUE)
                            .addComponent(txtHoaDonTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
                        .addComponent(txtHoaDonTenKhachHang, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cboHoaDonSDT, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel26Layout.createSequentialGroup()
                            .addComponent(cboHoaDonNon, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(txtHoaDonID, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHoaDonNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHoaDonID, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoaDonNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboHoaDonSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoaDonTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboHoaDonNon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHoaDonTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHoaDonLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHoaDonHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Quản lý bán hàng");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(328, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("        Quản lý bán hàng", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-seller-35.png")), jPanel18); // NOI18N

        jPanel14.setBackground(new java.awt.Color(0, 80, 140));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        txtThuongHieuID.setEditable(false);
        txtThuongHieuID.setText("ID sẽ tự sinh");
        txtThuongHieuID.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        btnThuongHieuSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-update-24.png"))); // NOI18N
        btnThuongHieuSua.setText("Sửa");
        btnThuongHieuSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuongHieuSuaActionPerformed(evt);
            }
        });

        btnThuongHieuThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-add-24.png"))); // NOI18N
        btnThuongHieuThem.setText("Thêm");
        btnThuongHieuThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuongHieuThemActionPerformed(evt);
            }
        });

        btnThuongHieuLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-reset-24.png"))); // NOI18N
        btnThuongHieuLamMoi.setText("Làm mới");
        btnThuongHieuLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuongHieuLamMoiActionPerformed(evt);
            }
        });

        txtThuongHieuTen.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên"));
        txtThuongHieuTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtThuongHieuTenKeyPressed(evt);
            }
        });

        jPanel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel29.setLayout(new java.awt.GridLayout(1, 0));

        lblThuongHieuHinhAnh.setText("jLabel4");
        lblThuongHieuHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThuongHieuHinhAnhMouseClicked(evt);
            }
        });
        jPanel29.add(lblThuongHieuHinhAnh);

        tblThuongHieu.setAutoCreateRowSorter(true);
        tblThuongHieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã thương hiệu", "Tên thương hiệu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuongHieuMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblThuongHieu);
        if (tblThuongHieu.getColumnModel().getColumnCount() > 0) {
            tblThuongHieu.getColumnModel().getColumn(0).setResizable(false);
            tblThuongHieu.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtThuongHieuTimKiemTen.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên thương hiệu"));
        txtThuongHieuTimKiemTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThuongHieuTimKiemTenActionPerformed(evt);
            }
        });
        txtThuongHieuTimKiemTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtThuongHieuTimKiemTenKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtThuongHieuTimKiemTen, javax.swing.GroupLayout.PREFERRED_SIZE, 795, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(txtThuongHieuTimKiemTen, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnThuongHieuXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/Delete.png"))); // NOI18N
        btnThuongHieuXoa.setText("Xóa");
        btnThuongHieuXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuongHieuXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtThuongHieuID, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(btnThuongHieuThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThuongHieuSua)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThuongHieuLamMoi)
                                .addGap(4, 4, 4)
                                .addComponent(btnThuongHieuXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtThuongHieuTen))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane4)
                        .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(txtThuongHieuID, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtThuongHieuTen))
                    .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThuongHieuThem)
                    .addComponent(btnThuongHieuSua)
                    .addComponent(btnThuongHieuLamMoi)
                    .addComponent(btnThuongHieuXoa))
                .addGap(17, 17, 17)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Quản lý thương hiệu");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(328, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("    Quản lý thương hiệu", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-trademark-35.png")), jPanel14); // NOI18N

        jPanel5.setBackground(new java.awt.Color(0, 80, 140));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Quản lý khách hàng");

        txtKhachHangID.setEditable(false);
        txtKhachHangID.setText("ID sẽ tự sinh");
        txtKhachHangID.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        txtKhachHangTen.setBorder(javax.swing.BorderFactory.createTitledBorder("Họ và tên"));
        txtKhachHangTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKhachHangTenKeyPressed(evt);
            }
        });

        txtKhachHangEmail.setBorder(javax.swing.BorderFactory.createTitledBorder("Email"));
        txtKhachHangEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKhachHangEmailActionPerformed(evt);
            }
        });
        txtKhachHangEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKhachHangEmailKeyPressed(evt);
            }
        });

        txtKhachHangNgaySinh.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày sinh"));
        txtKhachHangNgaySinh.setDateFormatString("dd/MM/yyyy");

        txtKhachHangSDT.setBorder(javax.swing.BorderFactory.createTitledBorder("Số điện thoại"));
        txtKhachHangSDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKhachHangSDTKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKhachHangSDTKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKhachHangSDTKeyTyped(evt);
            }
        });

        btnKhachHangThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-add-24.png"))); // NOI18N
        btnKhachHangThem.setText("Thêm");
        btnKhachHangThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangThemActionPerformed(evt);
            }
        });

        btnKhachHangSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-update-24.png"))); // NOI18N
        btnKhachHangSua.setText("Sửa");
        btnKhachHangSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangSuaActionPerformed(evt);
            }
        });

        btnKhachHangLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-reset-24.png"))); // NOI18N
        btnKhachHangLamMoi.setText("Làm mới");
        btnKhachHangLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangLamMoiActionPerformed(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Giới tính"));

        radioGioiTinh.add(rdoKhachHangNu);
        rdoKhachHangNu.setText("Nữ");

        radioGioiTinh.add(rdoKhachHangNam);
        rdoKhachHangNam.setSelected(true);
        rdoKhachHangNam.setText("Nam");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoKhachHangNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(rdoKhachHangNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoKhachHangNam)
                    .addComponent(rdoKhachHangNu))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        lblKhachHangHinhAnh.setText("jLabel3");
        lblKhachHangHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblKhachHangHinhAnhMouseClicked(evt);
            }
        });
        jPanel13.add(lblKhachHangHinhAnh);

        tblKhachHang.setAutoCreateRowSorter(true);
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Họ tên", "Số điện thoại", "Ngày sinh", "Giới tính"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblKhachHang);
        if (tblKhachHang.getColumnModel().getColumnCount() > 0) {
            tblKhachHang.getColumnModel().getColumn(0).setResizable(false);
            tblKhachHang.getColumnModel().getColumn(1).setResizable(false);
            tblKhachHang.getColumnModel().getColumn(2).setResizable(false);
            tblKhachHang.getColumnModel().getColumn(3).setResizable(false);
            tblKhachHang.getColumnModel().getColumn(4).setResizable(false);
        }

        btnExcelKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/excel.png"))); // NOI18N
        btnExcelKhachHang.setText("Excel");
        btnExcelKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelKhachHangActionPerformed(evt);
            }
        });

        btnPDFKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/pdf.png"))); // NOI18N
        btnPDFKhachHang.setText("PDF");
        btnPDFKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFKhachHangActionPerformed(evt);
            }
        });

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtKhachHangTimKiemSoDienThoai.setBorder(javax.swing.BorderFactory.createTitledBorder("Số điện thoại"));
        txtKhachHangTimKiemSoDienThoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKhachHangTimKiemSoDienThoaiKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtKhachHangTimKiemSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 808, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtKhachHangTimKiemSoDienThoai, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        btnChonKhachHang.setText("Chọn khách hàng");
        btnChonKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKhachHangActionPerformed(evt);
            }
        });

        btnKhachHangXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/Delete.png"))); // NOI18N
        btnKhachHangXoa.setText("Xóa");
        btnKhachHangXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPDFKhachHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcelKhachHang)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(btnKhachHangThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKhachHangSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKhachHangLamMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChonKhachHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKhachHangXoa)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtKhachHangID, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                                    .addComponent(txtKhachHangTen)
                                    .addComponent(txtKhachHangEmail))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKhachHangNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKhachHangSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(24, Short.MAX_VALUE))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtKhachHangNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(txtKhachHangID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKhachHangSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKhachHangTen, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKhachHangEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnKhachHangThem)
                        .addComponent(btnKhachHangSua)
                        .addComponent(btnKhachHangLamMoi))
                    .addComponent(btnChonKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKhachHangXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPDFKhachHang)
                    .addComponent(btnExcelKhachHang))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(333, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("    Quản lý khách hàng", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-customer-35.png")), jPanel5); // NOI18N

        jPanel2.setBackground(new java.awt.Color(0, 80, 140));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Quản lý nón");

        txtNonID.setEditable(false);
        txtNonID.setText("ID sẽ tự sinh");
        txtNonID.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        txtNonTen.setText("test");
        txtNonTen.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên nón"));
        txtNonTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNonTenActionPerformed(evt);
            }
        });
        txtNonTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNonTenKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNonTenKeyReleased(evt);
            }
        });

        txtNonGia.setText("1");
        txtNonGia.setBorder(javax.swing.BorderFactory.createTitledBorder("Giá"));
        txtNonGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNonGiaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNonGiaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNonGiaKeyTyped(evt);
            }
        });

        cboNonThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNonThuongHieu.setBorder(javax.swing.BorderFactory.createTitledBorder("Thương hiệu"));

        txtNonGiamGia.setText("1");
        txtNonGiamGia.setBorder(javax.swing.BorderFactory.createTitledBorder("Giảm giá"));
        txtNonGiamGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNonGiamGiaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNonGiamGiaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNonGiamGiaKeyTyped(evt);
            }
        });

        cboNonLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNonLoai.setBorder(javax.swing.BorderFactory.createTitledBorder("Loại"));

        btnPDFNon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/pdf.png"))); // NOI18N
        btnPDFNon.setText("PDF");
        btnPDFNon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFNonActionPerformed(evt);
            }
        });

        btnNonLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-reset-24.png"))); // NOI18N
        btnNonLamMoi.setText("Làm mới");
        btnNonLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNonLamMoiActionPerformed(evt);
            }
        });

        btnNonThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-add-24.png"))); // NOI18N
        btnNonThem.setText("Thêm");
        btnNonThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNonThemActionPerformed(evt);
            }
        });

        btnNonSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-update-24.png"))); // NOI18N
        btnNonSua.setText("Sửa");
        btnNonSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNonSuaActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        lblNonHinhAnh.setText("jLabel6");
        lblNonHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNonHinhAnhMouseClicked(evt);
            }
        });
        jPanel10.add(lblNonHinhAnh);

        tblNon.setAutoCreateRowSorter(true);
        tblNon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nón", "Tên nón", "Giá tiền", "Thương hiệu", "Loại", "Số lượng", "Giảm giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblNon);
        if (tblNon.getColumnModel().getColumnCount() > 0) {
            tblNon.getColumnModel().getColumn(0).setResizable(false);
            tblNon.getColumnModel().getColumn(1).setResizable(false);
            tblNon.getColumnModel().getColumn(2).setResizable(false);
            tblNon.getColumnModel().getColumn(3).setResizable(false);
            tblNon.getColumnModel().getColumn(4).setResizable(false);
            tblNon.getColumnModel().getColumn(5).setResizable(false);
            tblNon.getColumnModel().getColumn(6).setResizable(false);
        }

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/excel.png"))); // NOI18N
        btnExcel.setText("Excel");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        txtNonSoLuong.setText("1");
        txtNonSoLuong.setBorder(javax.swing.BorderFactory.createTitledBorder("Số lượng"));
        txtNonSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNonSoLuongKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNonSoLuongKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNonSoLuongKeyTyped(evt);
            }
        });

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtNonTimKiemTen.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên sản phẩm"));
        txtNonTimKiemTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNonTimKiemTenKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtNonTimKiemTen, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNonTimKiemTen, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        btnNonXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/Delete.png"))); // NOI18N
        btnNonXoa.setText("Xoa");
        btnNonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNonXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btnNonThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNonSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNonLamMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNonXoa)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(btnPDFNon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExcel))
                            .addComponent(jPanel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel16Layout.createSequentialGroup()
                                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNonID, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cboNonThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel16Layout.createSequentialGroup()
                                                .addComponent(txtNonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtNonGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel16Layout.createSequentialGroup()
                                                .addComponent(cboNonLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtNonSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtNonTen, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3))
                        .addGap(20, 20, 20))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNonGia)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(txtNonID, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 5, Short.MAX_VALUE))
                            .addComponent(txtNonGiamGia))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNonTen, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboNonLoai, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(txtNonSoLuong)
                            .addComponent(cboNonThuongHieu)))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNonLamMoi)
                    .addComponent(btnNonThem)
                    .addComponent(btnNonSua)
                    .addComponent(btnNonXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPDFNon)
                    .addComponent(btnExcel))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(348, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("               Quản lý nón", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-hat-35.png")), jPanel2); // NOI18N

        jPanel17.setBackground(new java.awt.Color(0, 80, 140));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        txtLoaiID.setEditable(false);
        txtLoaiID.setText("ID sẽ tự sinh");
        txtLoaiID.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        btnLoaiSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-update-24.png"))); // NOI18N
        btnLoaiSua.setText("Sửa");
        btnLoaiSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiSuaActionPerformed(evt);
            }
        });

        btnLoaiThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-add-24.png"))); // NOI18N
        btnLoaiThem.setText("Thêm");
        btnLoaiThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiThemActionPerformed(evt);
            }
        });

        txtLoaiTen.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên"));
        txtLoaiTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoaiTenKeyPressed(evt);
            }
        });

        tblLoai.setAutoCreateRowSorter(true);
        tblLoai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã loại", "Tên loại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLoai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblLoai);
        if (tblLoai.getColumnModel().getColumnCount() > 0) {
            tblLoai.getColumnModel().getColumn(0).setResizable(false);
            tblLoai.getColumnModel().getColumn(1).setResizable(false);
        }

        btnLoaiLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-reset-24.png"))); // NOI18N
        btnLoaiLamMoi.setText("Làm mới");
        btnLoaiLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiLamMoiActionPerformed(evt);
            }
        });

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtLoaiTimKiemTen.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên loại"));
        txtLoaiTimKiemTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLoaiTimKiemTenKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtLoaiTimKiemTen, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtLoaiTimKiemTen, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        btnLoaiXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/Delete.png"))); // NOI18N
        btnLoaiXoa.setText("Xóa");
        btnLoaiXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel30Layout.createSequentialGroup()
                        .addComponent(btnLoaiThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoaiSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoaiLamMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoaiXoa))
                    .addComponent(txtLoaiID, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLoaiTen, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel30Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(24, Short.MAX_VALUE)))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(txtLoaiID, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtLoaiTen, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoaiSua)
                    .addComponent(btnLoaiThem)
                    .addComponent(btnLoaiLamMoi)
                    .addComponent(btnLoaiXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel30Layout.createSequentialGroup()
                    .addGap(226, 226, 226)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(226, Short.MAX_VALUE)))
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Quản lý loại");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(337, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("                  Quản lý loại", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-type-35.png")), jPanel17); // NOI18N

        pnlNhanVien.setBackground(new java.awt.Color(0, 80, 140));
        pnlNhanVien.setPreferredSize(new java.awt.Dimension(800, 614));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0));

        lblNhanVienHinhAnh.setText("jLabel5");
        lblNhanVienHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNhanVienHinhAnhMouseClicked(evt);
            }
        });
        jPanel8.add(lblNhanVienHinhAnh);

        txtNhanVienHoTen.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên"));
        txtNhanVienHoTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhanVienHoTenActionPerformed(evt);
            }
        });
        txtNhanVienHoTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNhanVienHoTenKeyPressed(evt);
            }
        });

        txtNhanVienID.setEditable(false);
        txtNhanVienID.setText("ID sẽ tự sinh");
        txtNhanVienID.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));
        txtNhanVienID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhanVienIDActionPerformed(evt);
            }
        });

        txtNhanVienXacNhanMatKhau.setBorder(javax.swing.BorderFactory.createTitledBorder("Xác nhận mật khẩu"));
        txtNhanVienXacNhanMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhanVienXacNhanMatKhauActionPerformed(evt);
            }
        });
        txtNhanVienXacNhanMatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNhanVienXacNhanMatKhauKeyPressed(evt);
            }
        });

        txtNhanVienMatKhau.setBorder(javax.swing.BorderFactory.createTitledBorder("Mật khẩu"));
        txtNhanVienMatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNhanVienMatKhauKeyPressed(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức vụ"));

        radioChucVu.add(rdoNhanVienNhanVien);
        rdoNhanVienNhanVien.setSelected(true);
        rdoNhanVienNhanVien.setText("Nhân viên");

        radioChucVu.add(rdoNhanVienQuanLy);
        rdoNhanVienQuanLy.setText("Quản lý");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoNhanVienQuanLy, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(rdoNhanVienNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNhanVienQuanLy)
                    .addComponent(rdoNhanVienNhanVien))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        btnNhanVienThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-add-24.png"))); // NOI18N
        btnNhanVienThem.setText("Thêm");
        btnNhanVienThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienThemActionPerformed(evt);
            }
        });

        btnNhanVienSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-update-24.png"))); // NOI18N
        btnNhanVienSua.setText("Sửa");
        btnNhanVienSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienSuaActionPerformed(evt);
            }
        });

        btnNhanVienLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-reset-24.png"))); // NOI18N
        btnNhanVienLamMoi.setText("Làm mới");
        btnNhanVienLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienLamMoiActionPerformed(evt);
            }
        });

        tblNhanVien.setAutoCreateRowSorter(true);
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Email", "SDT", "Vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(1).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(2).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(3).setResizable(false);
            tblNhanVien.getColumnModel().getColumn(4).setResizable(false);
        }

        btnNhanVienExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/excel.png"))); // NOI18N
        btnNhanVienExcel.setText("Excel");
        btnNhanVienExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienExcelActionPerformed(evt);
            }
        });

        btnNhanVienPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/pdf.png"))); // NOI18N
        btnNhanVienPDF.setText("PDF");
        btnNhanVienPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienPDFActionPerformed(evt);
            }
        });

        txtNhanVienSDT.setBorder(javax.swing.BorderFactory.createTitledBorder("Số điện thoại"));
        txtNhanVienSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhanVienSDTActionPerformed(evt);
            }
        });
        txtNhanVienSDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNhanVienSDTKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNhanVienSDTKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNhanVienSDTKeyTyped(evt);
            }
        });

        txtNhanVienEmail.setBorder(javax.swing.BorderFactory.createTitledBorder("Email"));
        txtNhanVienEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhanVienEmailActionPerformed(evt);
            }
        });
        txtNhanVienEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNhanVienEmailKeyPressed(evt);
            }
        });

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtNhanVienTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder("Email"));
        txtNhanVienTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNhanVienTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtNhanVienTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNhanVienTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNhanVienXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/Delete.png"))); // NOI18N
        btnNhanVienXoa.setText("Xóa");
        btnNhanVienXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(btnNhanVienThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNhanVienSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNhanVienLamMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNhanVienXoa)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNhanVienPDF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNhanVienExcel)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtNhanVienXacNhanMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtNhanVienMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                                .addComponent(txtNhanVienID)))
                                        .addGap(46, 46, 46)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNhanVienSDT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                                            .addComponent(txtNhanVienHoTen, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtNhanVienEmail))))))
                        .addGap(44, 44, 44))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNhanVienHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                            .addComponent(txtNhanVienID))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(txtNhanVienMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNhanVienSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNhanVienXacNhanMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNhanVienEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNhanVienLamMoi)
                    .addComponent(btnNhanVienSua)
                    .addComponent(btnNhanVienThem)
                    .addComponent(btnNhanVienXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNhanVienPDF)
                    .addComponent(btnNhanVienExcel))
                .addGap(23, 23, 23))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Quản lý nhân viên");

        javax.swing.GroupLayout pnlNhanVienLayout = new javax.swing.GroupLayout(pnlNhanVien);
        pnlNhanVien.setLayout(pnlNhanVienLayout);
        pnlNhanVienLayout.setHorizontalGroup(
            pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNhanVienLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 861, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(354, Short.MAX_VALUE))
        );
        pnlNhanVienLayout.setVerticalGroup(
            pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jTabbedPane1.addTab("        Quản lý nhân viên", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-employee-35 (1).png")), pnlNhanVien); // NOI18N

        jPanel19.setBackground(new java.awt.Color(0, 80, 140));

        tblHoaDon.setAutoCreateRowSorter(true);
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Ngày tạo", "Mã nhân viên", "SDT khách hàng", "Trạng thái", "Tổng tiền"
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
        jScrollPane9.setViewportView(tblHoaDon);

        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtHoaDonTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder("Mã hóa đơn"));
        txtHoaDonTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHoaDonTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtHoaDonTimKiem, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtHoaDonTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnHoaDonChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-bill-35 (1).png"))); // NOI18N
        btnHoaDonChiTiet.setText("Xem hóa đơn chi tiết");
        btnHoaDonChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonChiTietActionPerformed(evt);
            }
        });

        btnHoaDonChiTiet1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/Mail.png"))); // NOI18N
        btnHoaDonChiTiet1.setText("Nhắc nhở");
        btnHoaDonChiTiet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonChiTiet1ActionPerformed(evt);
            }
        });

        btnHoaDonXoa.setText("Xóa hóa đơn");
        btnHoaDonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(btnHoaDonChiTiet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHoaDonChiTiet1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHoaDonXoa))
                    .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)))
                .addGap(29, 29, 29))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHoaDonChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHoaDonChiTiet1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHoaDonXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Quản lý hóa đơn");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(340, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("         Quản lý hóa đơn", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-bill-35 (1).png")), jPanel19); // NOI18N

        pnlThongKe.setBackground(new java.awt.Color(0, 80, 140));

        javax.swing.GroupLayout pnlDoanhThuTrongNamLayout = new javax.swing.GroupLayout(pnlDoanhThuTrongNam);
        pnlDoanhThuTrongNam.setLayout(pnlDoanhThuTrongNamLayout);
        pnlDoanhThuTrongNamLayout.setHorizontalGroup(
            pnlDoanhThuTrongNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 906, Short.MAX_VALUE)
        );
        pnlDoanhThuTrongNamLayout.setVerticalGroup(
            pnlDoanhThuTrongNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlThongKeNonLayout = new javax.swing.GroupLayout(pnlThongKeNon);
        pnlThongKeNon.setLayout(pnlThongKeNonLayout);
        pnlThongKeNonLayout.setHorizontalGroup(
            pnlThongKeNonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeNonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDoanhThuTrongNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlThongKeNonLayout.setVerticalGroup(
            pnlThongKeNonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeNonLayout.createSequentialGroup()
                .addComponent(pnlDoanhThuTrongNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtpThongKe.addTab("Doanh thu trong năm", pnlThongKeNon);

        javax.swing.GroupLayout pnlKhachHangMuaNhieuNhatLayout = new javax.swing.GroupLayout(pnlKhachHangMuaNhieuNhat);
        pnlKhachHangMuaNhieuNhat.setLayout(pnlKhachHangMuaNhieuNhatLayout);
        pnlKhachHangMuaNhieuNhatLayout.setHorizontalGroup(
            pnlKhachHangMuaNhieuNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlKhachHangMuaNhieuNhatLayout.setVerticalGroup(
            pnlKhachHangMuaNhieuNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlKhachHangMuaNhieuNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlKhachHangMuaNhieuNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jtpThongKe.addTab("Top khách hàng", jPanel38);

        javax.swing.GroupLayout pnlDoanhThuTheoNamLayout = new javax.swing.GroupLayout(pnlDoanhThuTheoNam);
        pnlDoanhThuTheoNam.setLayout(pnlDoanhThuTheoNamLayout);
        pnlDoanhThuTheoNamLayout.setHorizontalGroup(
            pnlDoanhThuTheoNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlDoanhThuTheoNamLayout.setVerticalGroup(
            pnlDoanhThuTheoNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDoanhThuTheoNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDoanhThuTheoNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jtpThongKe.addTab("Doanh thu theo năm", jPanel39);

        javax.swing.GroupLayout pnlSoLuongThuongHieuLayout = new javax.swing.GroupLayout(pnlSoLuongThuongHieu);
        pnlSoLuongThuongHieu.setLayout(pnlSoLuongThuongHieuLayout);
        pnlSoLuongThuongHieuLayout.setHorizontalGroup(
            pnlSoLuongThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 912, Short.MAX_VALUE)
        );
        pnlSoLuongThuongHieuLayout.setVerticalGroup(
            pnlSoLuongThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(pnlSoLuongThuongHieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSoLuongThuongHieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jtpThongKe.addTab("Thương hiệu", jPanel40);

        javax.swing.GroupLayout pnlTrangThaiHoaDonLayout = new javax.swing.GroupLayout(pnlTrangThaiHoaDon);
        pnlTrangThaiHoaDon.setLayout(pnlTrangThaiHoaDonLayout);
        pnlTrangThaiHoaDonLayout.setHorizontalGroup(
            pnlTrangThaiHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlTrangThaiHoaDonLayout.setVerticalGroup(
            pnlTrangThaiHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTrangThaiHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTrangThaiHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jtpThongKe.addTab("Trạng thái hóa đơn", jPanel3);

        tblThongKeHoaDon.setAutoCreateRowSorter(true);
        tblThongKeHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Tồng tiền", "Trạng thái"
            }
        ));
        jScrollPane7.setViewportView(tblThongKeHoaDon);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/excel.png"))); // NOI18N
        jButton3.setText("Excel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTongTien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongTien.setText("Tổng tiền: 0");

        dcNgayBatDau.setDateFormatString("dd, MM. yyyy");

        dcNgayKetThuc.setDateFormatString("dd, MM. yyyy");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-find-24.png"))); // NOI18N
        jButton5.setText("Tìm kiếm");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(dcNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(58, 58, 58))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5))
                    .addComponent(dcNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dcNgayKetThuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpThongKe.addTab("Doanh thu", jPanel32);

        tblThongKeKhachHang.setAutoCreateRowSorter(true);
        tblThongKeKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Họ tên", "Giới tính", "SDT", "Tổng tiền"
            }
        ));
        jScrollPane8.setViewportView(tblThongKeKhachHang);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/excel.png"))); // NOI18N
        jButton4.setText("Excel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpThongKe.addTab("Tổng tiền khách hàng", jPanel36);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Thống kê");

        javax.swing.GroupLayout pnlThongKeLayout = new javax.swing.GroupLayout(pnlThongKe);
        pnlThongKe.setLayout(pnlThongKeLayout);
        pnlThongKeLayout.setHorizontalGroup(
            pnlThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jtpThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(322, Short.MAX_VALUE))
        );
        pnlThongKeLayout.setVerticalGroup(
            pnlThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpThongKe)
                .addContainerGap())
        );

        jTabbedPane1.addTab("                     Thống kê", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-analytics-35.png")), pnlThongKe); // NOI18N

        jPanel33.setBackground(new java.awt.Color(0, 80, 140));

        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder("Đổi mật khẩu"));

        txtDoiMatKhauXacNhanMatKhau.setBorder(javax.swing.BorderFactory.createTitledBorder("Xác nhận mật khẩu"));
        txtDoiMatKhauXacNhanMatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDoiMatKhauXacNhanMatKhauKeyPressed(evt);
            }
        });

        txtDoiMatKhauMatKhauCu.setBorder(javax.swing.BorderFactory.createTitledBorder("Mật khẩu hiện tại"));
        txtDoiMatKhauMatKhauCu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDoiMatKhauMatKhauCuKeyPressed(evt);
            }
        });

        txtDoiMatKhauMatKhauMoi.setBorder(javax.swing.BorderFactory.createTitledBorder("Mật khẩu mới"));
        txtDoiMatKhauMatKhauMoi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDoiMatKhauMatKhauMoiKeyPressed(evt);
            }
        });

        btnDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/update.png"))); // NOI18N
        btnDoiMatKhau.setText("Đổi mật khẩu");
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDoiMatKhauXacNhanMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDoiMatKhauMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
            .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                    .addContainerGap(54, Short.MAX_VALUE)
                    .addComponent(txtDoiMatKhauMatKhauCu, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(32, 32, 32)))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap(133, Short.MAX_VALUE)
                .addComponent(txtDoiMatKhauMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(txtDoiMatKhauXacNhanMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel37Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(txtDoiMatKhauMatKhauCu, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(295, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(364, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("               Đổi mật khẩu ", new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-change-35.png")), jPanel33); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/icons8-hat-48.png"))); // NOI18N
        jLabel1.setText("ShopBanNon");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Xin chào, ");

        lblTen.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lblTen.setForeground(new java.awt.Color(255, 255, 255));
        lblTen.setText("Admin");

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Đăng xuất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblDongHo.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(255, 255, 255));
        lblDongHo.setText("Đồng hồ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblDongHo, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(233, 233, 233)
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTen, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(397, 397, 397))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1478, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTen)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblDongHo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1178, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Auth.user = null;
        new LoginJDialog();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNhanVienXacNhanMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhanVienXacNhanMatKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhanVienXacNhanMatKhauActionPerformed

    private void txtNhanVienHoTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhanVienHoTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhanVienHoTenActionPerformed

    private void btnLoaiSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoaiSuaActionPerformed
        // TODO add your handling code here:
        if (!validateForm(4)) {
            return;
        }
        update(4);


    }//GEN-LAST:event_btnLoaiSuaActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        // TODO add your handling code here:
        List<Non> noList = ndao.selectAll();

        // Create an instance of NhanVien to pass to the writeExcel method
        Non instance = new Non(); // Assuming NhanVien has a no-argument constructor

        try {
            // Specify the file path where the Excel file will be saved
            String filePath = "sanpham";
            // Call the writeExcel method from XExcel class
            String excelFilePath = XExcel.writeExcel(this, noList, filePath, instance);

            // Print the path where the Excel file is saved
            System.out.println("Xuất thành công excel: " + excelFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPDFNonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFNonActionPerformed
        // TODO add your handling code here:
        List<Non> nonList = ndao.selectAll();

        // Generate PDF for employees
        String title = "Thong tin san pham";
        String filePath = "sanpham";
        XPDF.generatePdf(this, title, nonList, filePath);
    }//GEN-LAST:event_btnPDFNonActionPerformed

    private void txtNonTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNonTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNonTenActionPerformed

    private void btnKhachHangSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangSuaActionPerformed
        // TODO add your handling code here:
        if (!validateForm(3)) {
            return;
        }
        update(3);

    }//GEN-LAST:event_btnKhachHangSuaActionPerformed

    private void txtNhanVienEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhanVienEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhanVienEmailActionPerformed

    private void txtThuongHieuTimKiemTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThuongHieuTimKiemTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThuongHieuTimKiemTenActionPerformed

    private void txtNhanVienSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhanVienSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhanVienSDTActionPerformed

//    public boolean validateFormNhanVien() {//kiểm tra nhập liệu để trống
//        if (txtNhanVienID.getText().isEmpty()
//                || txtNhanVienHoTen.getText().isEmpty()
//                || txtNhanVienMatKhau.getText().isEmpty()
//                || txtNhanVienSDT.getText().isEmpty()
//                || txtNhanVienXacNhanMatKhau.getText().isEmpty()
//                || txtNhanVienEmail.getText().isEmpty()) {
//            return false;
//        }
//        return true;
//    }

    private void btnNhanVienThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienThemActionPerformed
        // TODO add your handling code here:
        if (!validateForm(1)) {
            return;
        }

        insert(1);

    }//GEN-LAST:event_btnNhanVienThemActionPerformed

    File file;

    // chon anh
    void chonAnh(JLabel lblHinhAnh) {
        String home = System.getProperty("user.home");
        File file = new File(home + "/Pictures/");
        JFileChooser jfc = new JFileChooser(file.getAbsoluteFile());
        // mở đến thư mục theo đường dẫn 
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = jfc.getSelectedFile();
//            XImage.save(file); //lưu hình ảnh vào thư mục images
            ImageIcon icon = XImage.read(file.getName()); //đọc hình từ logo
            try {
                Image img = ImageIO.read(file);
                int width = lblHinhAnh.getWidth();
                int height = lblHinhAnh.getHeight();
                lblHinhAnh.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0))); //set hình vừa với khung của jlabel
                lblHinhAnh.setToolTipText(XImage.save(file)); //giữ tên hình trong tooltip
            } catch (IOException ex) {
                MsgBox.showErrorDialog(this, ex.getMessage(), "Error");
            }
//                lblHinhAnh.setIcon(icon);
        }
    }

    private void dangXuat() {
    }

    private void doimatkhau() {
        int manv = Auth.user.getMaNV();
        String matkhau = new String(txtDoiMatKhauMatKhauCu.getPassword());
        String matkhaumoi = new String(txtDoiMatKhauMatKhauMoi.getPassword());
        String matkhau2 = new String(txtDoiMatKhauXacNhanMatKhau.getPassword());

        if (!(manv == Auth.user.getMaNV())) {
            MsgBox.showMessageDialog(this, "Sai tên đăng nhập !");
        } else if (!matkhau.equals(Auth.user.getMatKhau())) {
            MsgBox.showMessageDialog(this, "Mật khẩu hiện tại không đúng !");
            txtDoiMatKhauMatKhauCu.setBackground(Color.pink);
        } else if (!matkhaumoi.equals(matkhau2)) {
            MsgBox.showMessageDialog(this, "Mật khẩu không trùng khớp");
            txtDoiMatKhauXacNhanMatKhau.setBackground(Color.pink);
        } else {
            NhanVienDAO dao = new NhanVienDAO();
            Auth.user.setMatKhau(matkhaumoi);
            dao.update(Auth.user);
            MsgBox.showMessageDialog(this, "Đổi mật khẩu thành công");
            this.dangXuat();
        }
    }

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        // TODO add your handling code here:

        if (Auth.user == null) {
            MsgBox.showMessageDialog(this, "Bạn chưa đăng nhập");
            return;
        }

        if (txtDoiMatKhauMatKhauMoi.getText().isBlank() || txtDoiMatKhauMatKhauCu.getText().isBlank() || txtDoiMatKhauXacNhanMatKhau.getText().isBlank()) {
            MsgBox.showErrorDialog(this, "Vui lòng nhập đầy đủ tất cả các trường", "Lỗi");
            return;
        }

        try {
            if (BCrypt.checkpw(txtDoiMatKhauMatKhauCu.getText(), Auth.user.getMatKhau())) {
                if (txtDoiMatKhauMatKhauMoi.getText().equals(txtDoiMatKhauXacNhanMatKhau.getText())) {
                    Auth.user.setMatKhau(txtDoiMatKhauMatKhauMoi.getText());
                    nvdao.update(Auth.user);
                    MsgBox.showMessageDialog(this, "Đổi mật khẩu thành công");
                    txtDoiMatKhauMatKhauCu.setText("");
                    txtDoiMatKhauMatKhauMoi.setText("");
                    txtDoiMatKhauXacNhanMatKhau.setText("");
                    txtDoiMatKhauMatKhauCu.requestFocus();
                    Auth.user = nvdao.getByEmail(Auth.user.getEmail());
                } else {
                    MsgBox.showMessageDialog(this, "Xác nhận mật khẩu phải khớp với mật khẩu mới");
                }
            } else {
                MsgBox.showMessageDialog(this, "Mật khẩu hiện tại không đúng !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void txtKhachHangEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKhachHangEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKhachHangEmailActionPerformed

    private void btnLoaiThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoaiThemActionPerformed
        // TODO add your handling code here:
        if (!validateForm(4)) {
            return;
        }
        insert(4);
    }//GEN-LAST:event_btnLoaiThemActionPerformed

    private void btnThuongHieuThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuongHieuThemActionPerformed
        // TODO add your handling code here:
        // kiem tra 
        if (!validateForm(2)) {
            return;
        }
        insert(2);
    }//GEN-LAST:event_btnThuongHieuThemActionPerformed

    private void btnNonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNonThemActionPerformed
        // TODO add your handling code here:
        if (!validateForm(5)) {
            return;
        }
        insert(5);

    }//GEN-LAST:event_btnNonThemActionPerformed

    private void btnKhachHangThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangThemActionPerformed
        // TODO add your handling code here:
        if (!validateForm(3)) {
            return;
        }

        insert(3);
    }//GEN-LAST:event_btnKhachHangThemActionPerformed

    private void btnKhachHangLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm(3);
    }//GEN-LAST:event_btnKhachHangLamMoiActionPerformed

    private void btnThuongHieuSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuongHieuSuaActionPerformed
        // TODO add your handling code here:
        if (!validateForm(2)) {
            return;
        }
        update(2);
    }//GEN-LAST:event_btnThuongHieuSuaActionPerformed

    private void btnThuongHieuLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuongHieuLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm(2);
    }//GEN-LAST:event_btnThuongHieuLamMoiActionPerformed

    private void btnNonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNonSuaActionPerformed
        // TODO add your handling code here:
        if (!validateForm(5)) {
            return;
        }
        update(5);
    }//GEN-LAST:event_btnNonSuaActionPerformed

    private void btnNonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNonLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm(5);

    }//GEN-LAST:event_btnNonLamMoiActionPerformed

    private void btnLoaiLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoaiLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm(4);
    }//GEN-LAST:event_btnLoaiLamMoiActionPerformed

    private void btnNhanVienSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienSuaActionPerformed
        // TODO add your handling code here:
        if (!validateForm(1)) {
            return;
        }
        update(1);
    }//GEN-LAST:event_btnNhanVienSuaActionPerformed

    private void btnNhanVienLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm(1);
    }//GEN-LAST:event_btnNhanVienLamMoiActionPerformed

    private void lblThuongHieuHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThuongHieuHinhAnhMouseClicked
        // TODO add your handling code here:
        chonAnh(lblThuongHieuHinhAnh);
    }//GEN-LAST:event_lblThuongHieuHinhAnhMouseClicked

    private void lblNonHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNonHinhAnhMouseClicked
        // TODO add your handling code here:
        chonAnh(lblNonHinhAnh);
    }//GEN-LAST:event_lblNonHinhAnhMouseClicked

    private void lblNhanVienHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanVienHinhAnhMouseClicked
        // TODO add your handling code here:
        chonAnh(lblNhanVienHinhAnh);
    }//GEN-LAST:event_lblNhanVienHinhAnhMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void fillAllTable() {
        fillTable(1, null);
        fillTable(2, null);
        fillTable(3, null);
        fillTable(4, null);
        fillTable(5, null);
    }

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        clearForm(5);
        fillTable(5, null);
    }//GEN-LAST:event_jPanel4MouseClicked

    private void tblThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuongHieuMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            clickTableThuongHieu();
        }

    }//GEN-LAST:event_tblThuongHieuMouseClicked

    private void tblLoaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            clickTableLoai();
        }
    }//GEN-LAST:event_tblLoaiMouseClicked

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            clickTableNhanVien();
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            clickTableKhachHang();
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void tblNonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNonMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            clickTableNon();
        }
    }//GEN-LAST:event_tblNonMouseClicked

    private void txtNonGiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonGiaKeyTyped
        // TODO add your handling code here:
        inputDigit(evt);
    }//GEN-LAST:event_txtNonGiaKeyTyped

    private void txtNonGiamGiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonGiamGiaKeyTyped
        // TODO add your handling code here:
        inputDigit(evt);

    }//GEN-LAST:event_txtNonGiamGiaKeyTyped

    private void txtNonSoLuongKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonSoLuongKeyTyped
        // TODO add your handling code here:
        inputDigit(evt);


    }//GEN-LAST:event_txtNonSoLuongKeyTyped

    private void txtNonGiamGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonGiamGiaKeyReleased
        // TODO add your handling code here:
        try {
            if (Integer.parseInt(txtNonGiamGia.getText()) > 100) {
                txtNonGiamGia.setText("100");
            }
        } catch (NumberFormatException e) {

        }

    }//GEN-LAST:event_txtNonGiamGiaKeyReleased

    private void txtNonSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonSoLuongKeyReleased
        // TODO add your handling code here:
        try {
            if (txtNonSoLuong.getText() != "" && Integer.parseInt(txtNonSoLuong.getText()) <= 0) {
                txtNonSoLuong.setText("1");
            }
        } catch (NumberFormatException e) {

        }
    }//GEN-LAST:event_txtNonSoLuongKeyReleased

    private void txtNonTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonTenKeyReleased
        // TODO add your handling code here:
        inputCharacter(evt);
    }//GEN-LAST:event_txtNonTenKeyReleased

    private void txtKhachHangSDTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachHangSDTKeyTyped
        // TODO add your handling code here:
        if (this.txtKhachHangSDT.getText().length() >= 10 || !Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtKhachHangSDTKeyTyped

    private void txtKhachHangSDTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachHangSDTKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKhachHangSDTKeyReleased

    private void txtNonGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonGiaKeyReleased
        // TODO add your handling code here:

        try {
            if (Integer.parseInt(txtNonGia.getText()) <= 0) {
                txtNonGia.setText("1");
            }
        } catch (NumberFormatException e) {

        }
    }//GEN-LAST:event_txtNonGiaKeyReleased

    private void txtNhanVienSDTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNhanVienSDTKeyTyped
        // TODO add your handling code here:
        if (this.txtNhanVienSDT.getText().length() >= 10 || !Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNhanVienSDTKeyTyped

    private void txtNhanVienSDTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNhanVienSDTKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtNhanVienSDTKeyReleased

    private void txtNhanVienIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhanVienIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhanVienIDActionPerformed

    private void txtDoiMatKhauXacNhanMatKhauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDoiMatKhauXacNhanMatKhauKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDoiMatKhauActionPerformed(null);
        }
    }//GEN-LAST:event_txtDoiMatKhauXacNhanMatKhauKeyPressed

    private void txtDoiMatKhauMatKhauCuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDoiMatKhauMatKhauCuKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDoiMatKhauActionPerformed(null);
        }
    }//GEN-LAST:event_txtDoiMatKhauMatKhauCuKeyPressed
    private boolean themSDT = false;
    private void cboHoaDonSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHoaDonSDTActionPerformed
        // TODO add your handling code here:
        if (String.valueOf(cboHoaDonSDT.getSelectedItem()).equalsIgnoreCase("Thêm số điện thoại mới")) {
            jTabbedPane1.setSelectedIndex(3);
            themSDT = true;
            return;
        }

        try {
            KhachHang kh = (KhachHang) cboHoaDonSDT.getSelectedItem();
            if (kh != null) {
                txtHoaDonTenKhachHang.setText(kh.getTenKhachHang());
            }
        } catch (ClassCastException ex) {
            txtHoaDonTenKhachHang.setText("");
        }

    }//GEN-LAST:event_cboHoaDonSDTActionPerformed
    ArrayList<HoaDonChiTiet> hoaDonChiTietList = new ArrayList();
    private void cboHoaDonNonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHoaDonNonActionPerformed
        // TODO add your handling code here:
        try {
            Non non = (Non) cboHoaDonNon.getSelectedItem();

            if (non != null) {
                if (non.getSoLuong() == 0) {
                    MsgBox.showMessageDialog(this, "Sản phẩm với mã " + non.getMaNon() + " dã hết hàng");
                    return;
                }
                boolean check = false;// check sản phẩm có trong danh sách chưa

                for (HoaDonChiTiet item : hoaDonChiTietList) {
                    if (item.getMaNon() == non.getMaNon()) {
//                        item.setSoLuong(item.getSoLuong() + 1);
                        item.setSoLuong(1);
                        double giaGiam = (non.getGiamGia() * non.getGia()) / 100;
                        item.setGia(non.getGia() - giaGiam);
                        check = true;
                    }
                }
                if (!check) {
                    HoaDonChiTiet hdct = new HoaDonChiTiet();
                    hdct.setMaNon(non.getMaNon());
                    hdct.setSoLuong(1);
                    double giaGiam = (non.getGiamGia() * non.getGia()) / 100;
                    hdct.setGia(non.getGia() - giaGiam);
                    hdct.setTenNon(non.getTenNon());
                    hoaDonChiTietList.add(hdct);
                }

                fillTableBanHang(hoaDonChiTietList);
            }
        } catch (ClassCastException ex) {

        }


    }//GEN-LAST:event_cboHoaDonNonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Webcam webcam = Webcam.getDefault();
        if (webcam == null) {
            JOptionPane.showMessageDialog(null, "No webcam detected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Webcam Feed");
        frame.setSize(200, 200);

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setPreferredSize(new Dimension(100, 100));
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        final boolean[] stopScanning = {false}; // Array to hold a mutable boolean value

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopScanning[0] = true; // Set the flag to stop scanning
            }
        });

        new Thread(() -> {
            while (!stopScanning[0]) { // Continue scanning until stopScanning is true
                BufferedImage image = webcam.getImage();

                if (image != null) {
                    try {
                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        Result result = new MultiFormatReader().decode(bitmap);
                        if (result != null) {
                            try {
                                Non non = ndao.selectById(getId(result.getText()));
                                if (non.isDeleted()) {
                                    MsgBox.showMessageDialog(this, "Không tìm thấy sản phẩm");
                                } else if (non.getSoLuong() < 1) {
                                    MsgBox.showMessageDialog(this, "Sản phẩm đã hết");
                                } else {
                                    cboHoaDonNon.setSelectedItem(result.getText());
                                    SwingUtilities.invokeLater(() -> {
                                        JOptionPane.showMessageDialog(frame, result.getText(), "Scanning Successful", JOptionPane.INFORMATION_MESSAGE);
                                    });

                                }
                                stopScanning[0] = true; // Set the flag to stop scanning

                            } catch (Exception e) {
                                e.printStackTrace();
                                MsgBox.showMessageDialog(this, "Không tìm thấy sản phẩm");
                            }

                        }
                    } catch (NotFoundException ignored) {
                        // QR code not found in the image
                    }
                }
            }
            webcam.close(); // Close the webcam
            frame.dispose(); // Close the webcam frame

        }).start();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        maHoaDon = -1;
        themSDT = false;

        int lc = jTabbedPane1.getSelectedIndex();
        try {
            if (Auth.user.isVaiTro()) {
                if (lc == 0) {
                    chonTrangChu();
                } else if (lc == 1) {
                    chonBanHang();
                } else if (lc == 2) {
                    chonFormThuongHieu();
                } else if (lc == 3) {
                    chonFormKhachHang();
                } else if (lc == 4) {
                    chonFormNon();
                } else if (lc == 5) {
                    chonFormLoai();
                } else if (lc == 6) {
                    chonFormNhanVien();
                } else if (lc == 7) {
                    chonFormHoaDon();
                } else if (lc == 8) {
                    chonThongKe();
                }
            } else {
                if (lc == 0) {
                    chonTrangChu();
                } else if (lc == 1) {
                    chonBanHang();
                } else if (lc == 2) {
                    chonFormThuongHieu();
                } else if (lc == 3) {
                    chonFormKhachHang();
                } else if (lc == 4) {
                    chonFormNon();
                } else if (lc == 5) {
                    chonFormLoai();
                } else if (lc == 6) {
                    chonFormHoaDon();
                }
            }
        } catch (Exception e) {

        }


    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void clearFormBanHang() {
        fillComboboxSDT();
        fillComboboxSP();

        cboHoaDonNon.setSelectedIndex(0);
        cboHoaDonSDT.setSelectedIndex(0);
        txtHoaDonNgayTao.setText(XDate.toString(new Date()));
        hoaDonChiTietList = new ArrayList();
        txtHoaDonTongTien.setText("0");
        txtHoaDonTenKhachHang.setText("");
        txtHoaDonID.setText("Id sẽ tự sinh");

        fillTableBanHang(hoaDonChiTietList);
    }

    ChartPanel chartPanel;

    private void chonTrangChu() {
        List<Object[]> list = tkdao.getSoLuongSanPhamLoai();
        List<Object[]> doanhThuHomNay = tkdao.getDoanhThuHomNay();
        List<Object[]> doanhThu = tkdao.getDoanhThu();
        List<Object[]> khachHang = tkdao.getKhachHangMuaNhieuNhat();

        Object doanhThuHomNayString = doanhThuHomNay.get(0)[0];
        Object doanhThuString = doanhThu.get(0)[0];
        Object khacHangString = !khachHang.isEmpty() ? khachHang.get(0)[0] : "Chưa có khách hàng nào";

        if (!(String.valueOf(khacHangString).equalsIgnoreCase("Chưa có khách hàng nào"))) {
            lblTenKhachHang.setText(String.valueOf(khachHang.get(0)[1]));
            lblSoDienThoaiKhachHang.setText(String.valueOf(khachHang.get(0)[2]));
        } else {
            lblTenKhachHang.setText("");
            lblSoDienThoaiKhachHang.setText("");
        }
        txtDoanhThuHomNay.setText(doanhThuHomNayString != null
                ? Common.formatVietnameseMoney(Double.parseDouble(doanhThuHomNayString.toString())) : "0 VNĐ");
        txtDoanhThu.setText(doanhThuString != null
                ? Common.formatVietnameseMoney(Double.parseDouble(doanhThuString.toString())) : "0 VNĐ");
        lblKhacHangNhieuNhat.setText((String) khacHangString);

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Object[] item : list) {
            dataset.setValue(item[0].toString(), Double.valueOf(item[1].toString())); // Add category label and quantity to dataset
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Số lượng sản phẩm của loại",
                dataset,
                true, // Include legend
                true, // Include tooltips
                false // Don't include URLs
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290); // Set the starting angle
        plot.setForegroundAlpha(0.7f); // Set transparency

        // Set number format for displaying quantity values as floats
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2); // Set maximum fraction digits to 2
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}", numberFormat, new DecimalFormat("0.00%")));

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 400)); // Set chart panel size
        JScrollPane scrollPane = new JScrollPane(chartPanel); // Wrap chart panel within scroll pane
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Set horizontal scroll policy
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Set vertical scroll policy

        pnlChart.setLayout(new BorderLayout()); // Assuming you want to use BorderLayout
        pnlChart.add(scrollPane, BorderLayout.CENTER); // Add scroll pane to panel
    }

    private void chonThongKe() {
        chartThongKeDoanhThuTrongNam();
        chartThongKeKhachHangMuaNhieuNhat();
        chartThongKeDoanhThuTheoNam();
        chartThongKeThuongHieu();
        chartThongKeTrangThaiHoaDon();
        fillTableThongKeKhachHang();
    }

    private void chartThongKeThuongHieu() {
        List<Object[]> list = tkdao.getSoLuongSanPhamThuongHieu();
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Object[] item : list) {
            dataset.setValue(item[0].toString(), Double.valueOf(item[1].toString())); // Add category label and quantity to dataset
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Số lượng sản phẩm của thương hiệu",
                dataset,
                true, // Include legend
                true, // Include tooltips
                false // Don't include URLs
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290); // Set the starting angle
        plot.setForegroundAlpha(0.7f); // Set transparency

        // Set number format for displaying quantity values as floats
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2); // Set maximum fraction digits to 2
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}", numberFormat, new DecimalFormat("0.00%")));

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 400)); // Set chart panel size
        JScrollPane scrollPane = new JScrollPane(chartPanel); // Wrap chart panel within scroll pane
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Set horizontal scroll policy
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Set vertical scroll policy

        pnlSoLuongThuongHieu.setLayout(new BorderLayout()); // Assuming you want to use BorderLayout
        pnlSoLuongThuongHieu.add(scrollPane, BorderLayout.CENTER); // Add scroll pane to panel
    }

    private void chartThongKeTrangThaiHoaDon() {
        List<Object[]> list = tkdao.getTrangThaiHoaDon();
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Object[] item : list) {
            dataset.setValue(item[0].toString(), Double.valueOf(item[1].toString())); // Add category label and quantity to dataset
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Trạng thái của hóa đơn",
                dataset,
                true, // Include legend
                true, // Include tooltips
                false // Don't include URLs
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290); // Set the starting angle
        plot.setForegroundAlpha(0.7f); // Set transparency

        // Set number format for displaying quantity values as floats
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2); // Set maximum fraction digits to 2
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}", numberFormat, new DecimalFormat("0.00%")));

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 400)); // Set chart panel size
        JScrollPane scrollPane = new JScrollPane(chartPanel); // Wrap chart panel within scroll pane
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Set horizontal scroll policy
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Set vertical scroll policy

        pnlTrangThaiHoaDon.setLayout(new BorderLayout()); // Assuming you want to use BorderLayout
        pnlTrangThaiHoaDon.add(scrollPane, BorderLayout.CENTER); // Add scroll pane to panel
    }

    private void chartThongKeDoanhThuTrongNam() {
        try {
            // Create a dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            ThongKeDAO tkdao = new ThongKeDAO();
            List<Object[]> list = tkdao.sp_getDoanhThuTheoThang();

            // Iterate over the list and add data to the dataset
            for (Object[] row : list) {
                if (row[0] == null || row[1] == null) {
                    continue;
                }
                int year = row[0] != null ? (int) row[0] : 1;
                int month = row[1] != null ? (int) row[1] : 1;
                BigDecimal doanhThu = (BigDecimal) row[2];
                dataset.addValue(doanhThu, "Doanh thu", month + "-" + year);
                System.out.println(doanhThu);
                System.out.println(month + "-" + year);
            }

            // Get the current year
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            // Create the chart with the concatenated title
            JFreeChart chart = ChartFactory.createLineChart(
                    "Doanh thu từng tháng của năm " + currentYear,
                    "Tháng",
                    "Doanh thu",
                    dataset
            );

            // Display the chart in a panel
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

            // Assuming pnlDoanhThuTrongNam is a JPanel, add chart panel to it
            pnlDoanhThuTrongNam.setLayout(new BorderLayout()); // Assuming you want to use BorderLayout
            pnlDoanhThuTrongNam.add(chartPanel, BorderLayout.CENTER); // Add chart panel to panel

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void bangHoaDon() {

    }

    private void chartThongKeDoanhThuTheoNam() {
        try {
            // Create a dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            ThongKeDAO tkdao = new ThongKeDAO();
            List<Object[]> list = tkdao.sp_getDoanhThuTheoNam();

            // Iterate over the list and add data to the dataset
            for (Object[] row : list) {
                if (row[0] == null || row[1] == null) {
                    continue;
                }
                int year = row[0] != null ? (int) row[0] : 1;
                BigDecimal doanhThu = (BigDecimal) row[1];
                dataset.addValue(doanhThu, "Doanh thu", year);
            }

            // Get the current year
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            // Create the chart with the concatenated title
            JFreeChart chart = ChartFactory.createLineChart(
                    "Doanh thu từng tháng theo năm",
                    "Năm",
                    "Doanh thu",
                    dataset
            );

            // Display the chart in a panel
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

            // Assuming pnlDoanhThuTrongNam is a JPanel, add chart panel to it
            pnlDoanhThuTheoNam.setLayout(new BorderLayout()); // Assuming you want to use BorderLayout
            pnlDoanhThuTheoNam.add(chartPanel, BorderLayout.CENTER); // Add chart panel to panel

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void chartThongKeKhachHangMuaNhieuNhat() {
        try {
            // Create a dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            ThongKeDAO tkdao = new ThongKeDAO();
            List<Object[]> list = tkdao.getTongTienKhachHang();

            // Iterate over the list and add data to the dataset
            for (Object[] row : list) {
                if (row[0] == null || row[1] == null) {
                    continue;
                }
                int maKH = (int) row[0];
                BigDecimal doanhThu = (BigDecimal) row[1];
                dataset.addValue(doanhThu.doubleValue(), "Tổng Tiền", "KH" + maKH); // Adding data to the dataset
            }

            // Create the chart
            JFreeChart chart = ChartFactory.createBarChart(
                    "Tổng Tiền Khách Hàng", // Chart title
                    "Mã Khách Hàng", // X-axis label
                    "Tổng Tiền", // Y-axis label
                    dataset // Dataset
            );

            // Display the chart in a panel
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

            pnlKhachHangMuaNhieuNhat.setLayout(new BorderLayout()); // Assuming you want to use BorderLayout
            pnlKhachHangMuaNhieuNhat.add(chartPanel, BorderLayout.CENTER); // Add chart panel to panel

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void chonFormHoaDon() {
        fillTable(7, null);
    }

    private void chonBanHang() {
        clearFormBanHang();

    }

    private void chonFormThuongHieu() {
        fillTable(2, null);
        clearForm(2);

    }

    private void chonFormLoai() {
        fillTable(4, null);
        clearForm(4);

    }

    private void chonFormNon() {
        fillTable(5, null);
        clearForm(5);
        fillComboBoxLoai();
        fillComboboxThuongHieu();

    }

    private void chonFormNhanVien() {
        fillTable(1, null);
        clearForm(1);

    }

    private void chonFormKhachHang() {
        fillTable(3, null);
        clearForm(3);

    }

    private HoaDonDao hoaDonDao = new HoaDonDao();
    private int maHoaDon = 0;
    private void btnHoaDonChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonChiTietActionPerformed
        // TODO add your handling code here:
        if (Auth.user == null) {
            MsgBox.showMessageDialog(this, "Bạn chưa đăng nhập");
            return;
        }
        if (maHoaDon == 0) {
            MsgBox.showMessageDialog(this, "Bạn chưa chọn hóa đơn nào");
            return;
        }

        HoaDon hd = hoaDonDao.selectById(maHoaDon);

//        (new InHoaDon(hd)).setVisible(true);
        (new XuatHoaDon(hd)).setVisible(true);

    }//GEN-LAST:event_btnHoaDonChiTietActionPerformed
    private HoaDonChiTietDAO hcdtDao = new HoaDonChiTietDAO();
    private void btnHoaDonLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonLuuActionPerformed
        try {
            // TODO add your handling code here:
            if (cboHoaDonSDT.getSelectedIndex() == 0) {
                MsgBox.showMessageDialog(this, "Vui lòng chọn số điện thoại của khách hàng");
                return;
            }
            if (hoaDonChiTietList.size() == 0) {
                MsgBox.showMessageDialog(this, "Vui lòng chọn sản phẩm cho hóa đơn");
                return;
            }
            int i = 0;
            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                Non non = ndao.selectById(hdct.getMaNon());
                int sl = Integer.parseInt(tblBanHang.getValueAt(i, 2).toString());
                hdct.setSoLuong(sl);
                ++i;
                if (non.getSoLuong() - hdct.getSoLuong() < 0) {
                    MsgBox.showMessageDialog(this, "Vượt quá số lượng của sản phẩm có mã SP" + non.getMaNon());
                    return;
                }
                if (hdct.getSoLuong() <= 0) {
                    MsgBox.showMessageDialog(this, "Số lượng sản phẩm không được bé hơn 1");
                    return;
                }
            }
            HoaDon hd = new HoaDon();
            KhachHang kh = khdao.getBySDT(cboHoaDonSDT.getSelectedItem().toString());
            hd.setMaKhachHang(kh.getMaKhachHang());
            if (Auth.user != null) {
                hd.setMaNhanVien(Auth.user.getMaNV());
            } else {
                hd.setMaNhanVien(1);
            }
            hd.setNgayTao(new Date());

            try {
                hd.setTongTien(Common.parseVietnameseMoney(txtHoaDonTongTien.getText()));
            } catch (ParseException ex) {
                Logger.getLogger(MainShopBanNon.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean check = false;
            while (true) {
                check = MsgBox.showConfirmDialog(this, "Hóa đơn đã thanh toán?");
                break;
            }
            if (check) {
                hd.setTrangThai("Đã thanh toán");
            } else {
                hd.setTrangThai("Chưa thanh toán");
            }
            hoaDonDao.insert(hd);
            int hdid = getId(hoaDonDao.generateNewId());
            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                hdct.setMaHoaDon(hdid);
                hcdtDao.insert(hdct);
                Non non = ndao.selectById(hdct.getMaNon());
                non.setSoLuong(non.getSoLuong() - hdct.getSoLuong());
                ndao.update(non);
            }
            generateQR("HD" + hdid);

            clearFormBanHang();
            MsgBox.showMessageDialog(this, "Thêm hóa đơn thành công với mã HD" + hdid);
            if (check) {
                hd = hoaDonDao.selectById(hdid);
                (new XuatHoaDon(hd)).setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainShopBanNon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnHoaDonLuuActionPerformed

    void dongho() {
        new Timer(1000, new ActionListener() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblDongHo.setText(format.format(new Date()));
            }
        }).start();
    }

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        int row = tblHoaDon.getSelectedRow();
        if (row != -1) {
            maHoaDon = getId(((String) tblHoaDon.getValueAt(row, 0)));
            System.out.println(maHoaDon);
            if (evt.getClickCount() == 2) {

                HoaDon hd = hoaDonDao.selectById(maHoaDon);

                if (hd.getTrangThai().equalsIgnoreCase("Đã thanh toán")) {
                    return;
                }
                boolean lc = MsgBox.showConfirmDialog(this, "Bạn có chắc muốn chuyển đổi trạng thái của hóa đơn này sang \"Đã thanh toán\"");
                if (lc) {
                    hd.setTrangThai("Đã thanh toán");
                    hoaDonDao.update(hd);
                    MsgBox.showMessageDialog(this, "Cập nhật trạng thái thành công");
                    fillTable(8, null);
                }
            }
        }


    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblBanHangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblBanHangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBanHangKeyPressed

    private void tblBanHangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblBanHangKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBanHangKeyTyped

    private void tblBanHangInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tblBanHangInputMethodTextChanged
        // TODO add your handling code here:
        System.out.println(evt.getText());
    }//GEN-LAST:event_tblBanHangInputMethodTextChanged

    private void txtThuongHieuTimKiemTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtThuongHieuTimKiemTenKeyReleased
        // TODO add your handling code here:
        handleTimKiemThuongHieu(this.txtThuongHieuTimKiemTen.getText());
    }//GEN-LAST:event_txtThuongHieuTimKiemTenKeyReleased

    private void txtKhachHangTimKiemSoDienThoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachHangTimKiemSoDienThoaiKeyReleased
        // TODO add your handling code here:
        handleTimKiemKhachHang(this.txtKhachHangTimKiemSoDienThoai.getText());
    }//GEN-LAST:event_txtKhachHangTimKiemSoDienThoaiKeyReleased

    private void txtNonTimKiemTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonTimKiemTenKeyReleased
        // TODO add your handling code here:
        handleTimKiemNon(this.txtNonTimKiemTen.getText());
    }//GEN-LAST:event_txtNonTimKiemTenKeyReleased

    private void txtNhanVienTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNhanVienTimKiemKeyReleased
        // TODO add your handling code here:
        handleTimKiemNhanVien(this.txtNhanVienTimKiem.getText());
    }//GEN-LAST:event_txtNhanVienTimKiemKeyReleased

    private void txtLoaiTimKiemTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoaiTimKiemTenKeyReleased
        // TODO add your handling code here:
        handleTimKiemLoai(this.txtLoaiTimKiemTen.getText());
    }//GEN-LAST:event_txtLoaiTimKiemTenKeyReleased

    private void txtHoaDonTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoaDonTimKiemKeyReleased
        // TODO add your handling code here:
        handleTimKiemHoaDon(this.txtHoaDonTimKiem.getText());
    }//GEN-LAST:event_txtHoaDonTimKiemKeyReleased

    private void btnHoaDonHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonHuyActionPerformed
        // TODO add your handling code here:
        clearFormBanHang();
    }//GEN-LAST:event_btnHoaDonHuyActionPerformed

    private void jPanel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(8);
    }//GEN-LAST:event_jPanel11MouseClicked

    private void jPanel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel41MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(8);

    }//GEN-LAST:event_jPanel41MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
        int maKH = getId(lblKhacHangNhieuNhat.getText());
        KhachHang kh = khdao.selectById(maKH);
        fillFormKhachHang(kh);
    }//GEN-LAST:event_jPanel6MouseClicked

    private void btnExcelKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelKhachHangActionPerformed
        // TODO add your handling code here:
        List<KhachHang> khachHangList = khdao.selectAll();

        KhachHang instance = new KhachHang();

        try {
            String filePath = "khachhang";
            String excelFilePath = XExcel.writeExcel(this, khachHangList, filePath, instance);

            System.out.println("Xuất thành công excel: " + excelFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnExcelKhachHangActionPerformed

    private void btnNhanVienExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienExcelActionPerformed
        // TODO add your handling code here:
        List<NhanVien> employeeList = nvdao.selectAll();

        NhanVienFile instance = new NhanVienFile();

        try {
            String filePath = "nhanvien";
            List<NhanVienFile> empFile = convertToNhanVienFile(employeeList);
            String excelFilePath = XExcel.writeExcel(this, empFile, filePath, instance);

            System.out.println("Xuất thành công excel: " + excelFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnNhanVienExcelActionPerformed

    private void btnNhanVienPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienPDFActionPerformed
        // TODO add your handling code here:
        List<NhanVien> employeeList = nvdao.selectAll();

        List<NhanVienFile> pdfList = convertToNhanVienFile(employeeList);

        String title = "Thong tin nhan vien";
        String filePath = "nhanvien";
        XPDF.generatePdf(this, title, pdfList, filePath);
    }//GEN-LAST:event_btnNhanVienPDFActionPerformed

    private void btnPDFKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFKhachHangActionPerformed
        // TODO add your handling code here:
        List<KhachHang> khList = khdao.selectAll();

        String title = "Thong tin khach hang";
        String filePath = "khachhang";
        XPDF.generatePdf(this, title, khList, filePath);
    }//GEN-LAST:event_btnPDFKhachHangActionPerformed

    private void tblBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBanHangMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            boolean check = MsgBox.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này khỏi hóa đơn");
            if (check) {
                int row = tblBanHang.getSelectedRow();
                if (row != -1) {
                    hoaDonChiTietList.remove(row);
                    fillTableBanHang(hoaDonChiTietList);
                }
            }
        }
    }//GEN-LAST:event_tblBanHangMouseClicked

    private void txtNhanVienHoTenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNhanVienHoTenKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnNhanVienThem.isEnabled()) {
                btnNhanVienThemActionPerformed(null);
            } else {
                btnNhanVienSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtNhanVienHoTenKeyPressed

    private void txtNhanVienMatKhauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNhanVienMatKhauKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnNhanVienThem.isEnabled()) {
                btnNhanVienThemActionPerformed(null);
            } else {
                btnNhanVienSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtNhanVienMatKhauKeyPressed

    private void txtNhanVienSDTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNhanVienSDTKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnNhanVienThem.isEnabled()) {
                btnNhanVienThemActionPerformed(null);
            } else {
                btnNhanVienSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtNhanVienSDTKeyPressed

    private void txtNhanVienXacNhanMatKhauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNhanVienXacNhanMatKhauKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnNhanVienThem.isEnabled()) {
                btnNhanVienThemActionPerformed(null);
            } else {
                btnNhanVienSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtNhanVienXacNhanMatKhauKeyPressed

    private void txtNhanVienEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNhanVienEmailKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnNhanVienThem.isEnabled()) {
                btnNhanVienThemActionPerformed(null);
            } else {
                btnNhanVienSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtNhanVienEmailKeyPressed

    private void txtKhachHangTenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachHangTenKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnKhachHangThem.isEnabled()) {
                btnKhachHangThemActionPerformed(null);
            } else {
                btnKhachHangSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtKhachHangTenKeyPressed

    private void txtKhachHangSDTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachHangSDTKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnKhachHangThem.isEnabled()) {
                btnKhachHangThemActionPerformed(null);
            } else {
                btnKhachHangSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtKhachHangSDTKeyPressed

    private void txtKhachHangEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachHangEmailKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnKhachHangThem.isEnabled()) {
                btnKhachHangThemActionPerformed(null);
            } else {
                btnKhachHangSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtKhachHangEmailKeyPressed

    private void txtNonGiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonGiaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnNonThem.isEnabled()) {
                btnNonThemActionPerformed(null);
            } else {
                btnNonSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtNonGiaKeyPressed

    private void txtNonGiamGiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonGiamGiaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnNonThem.isEnabled()) {
                btnNonThemActionPerformed(null);
            } else {
                btnNonSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtNonGiamGiaKeyPressed

    private void txtNonTenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonTenKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnNonThem.isEnabled()) {
                btnNonThemActionPerformed(null);
            } else {
                btnNonSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtNonTenKeyPressed

    private void txtNonSoLuongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNonSoLuongKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnNonThem.isEnabled()) {
                btnNonThemActionPerformed(null);
            } else {
                btnNonSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtNonSoLuongKeyPressed

    private void txtLoaiTenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoaiTenKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnLoaiThem.isEnabled()) {
                btnLoaiThemActionPerformed(null);
            } else {
                btnLoaiSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtLoaiTenKeyPressed

    private void txtThuongHieuTenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtThuongHieuTenKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnThuongHieuThem.isEnabled()) {
                btnThuongHieuThemActionPerformed(null);
            } else {
                btnThuongHieuSuaActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtThuongHieuTenKeyPressed

    private void txtDoiMatKhauMatKhauMoiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDoiMatKhauMatKhauMoiKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDoiMatKhauActionPerformed(null);
        }
    }//GEN-LAST:event_txtDoiMatKhauMatKhauMoiKeyPressed

    private void btnHoaDonChiTiet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonChiTiet1ActionPerformed
        // TODO add your handling code here:
        int[] rows = tblHoaDon.getSelectedRows();
        for (int row : rows) {
            if(tblHoaDon.getValueAt(row, 4).toString().equalsIgnoreCase("Đã thanh toán")){
                MsgBox.showMessageDialog(this, "Chỉ nhắc nhở những hóa đơn \"Chưa thanh toán\"");
                return;
            }
        }
        for (int row : rows) {
            String sdt = tblHoaDon.getValueAt(row, 3).toString();
            String maHD = tblHoaDon.getValueAt(row, 0).toString();
            KhachHang kh = khdao.getBySDT(sdt);
            XMail.sendMail(kh.getEmail(), "Nhắc nhở", maHD + " chưa thanh toán, mong bạn sớm thanh toán");
        }
        
        MsgBox.showMessageDialog(this, "Gửi nhắc nhở tới mail của những khách hàng đã chọn");
    }//GEN-LAST:event_btnHoaDonChiTiet1ActionPerformed

    private void lblKhachHangHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhachHangHinhAnhMouseClicked
        // TODO add your handling code here:
        chonAnh(lblKhachHangHinhAnh);
    }//GEN-LAST:event_lblKhachHangHinhAnhMouseClicked

    private void btnChonKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKhachHangActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
        System.out.println(txtKhachHangSDT.getText());

        cboHoaDonSDT.setSelectedItem(txtKhachHangSDT.getText());
    }//GEN-LAST:event_btnChonKhachHangActionPerformed

    private void cboHoaDonSDTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboHoaDonSDTMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cboHoaDonSDTMouseClicked

    private void btnHoaDonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonXoaActionPerformed
        // TODO add your handling code here:
        if (maHoaDon == 0) {
            MsgBox.showMessageDialog(this, "Bạn chưa chọn hóa đơn nào");
            return;
        }

        boolean check = MsgBox.showConfirmDialog(this, "Bạn có muốn xóa hóa đơn này không");
        if (!check) {
            return;
        }

        HoaDon hd = hoaDonDao.selectById(maHoaDon);
        if (hd == null) {
            MsgBox.showMessageDialog(this, "Hóa đơn không tồn tại");
            return;
        }
        if (hd.getTrangThai().equalsIgnoreCase("Đã thanh toán")) {
            MsgBox.showMessageDialog(this, "Không thể xóa hóa đơn đã thanh toán");
            return;
        }
        List<HoaDonChiTiet> hdctList = hcdtDao.selectByMaHD(hd.getMaHoaDon());
        for (HoaDonChiTiet hdct : hdctList) {
            hcdtDao.delete(hdct.getMaHoaDonChiTiet());
        }
        hoaDonDao.delete(hd.getMaHoaDon());
        MsgBox.showMessageDialog(this, "Xóa thành công hóa đơn");
        fillTable(6, null);

    }//GEN-LAST:event_btnHoaDonXoaActionPerformed

    private int monthNgayBatDau = 1;
    private int monthNgayKetThuc = 12;
    private int nam;
    private void btnThuongHieuXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuongHieuXoaActionPerformed
        // TODO add your handling code here:
        delete(2);
    }//GEN-LAST:event_btnThuongHieuXoaActionPerformed

    private void btnKhachHangXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangXoaActionPerformed
        // TODO add your handling code here:
        delete(3);
    }//GEN-LAST:event_btnKhachHangXoaActionPerformed

    private void btnNonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNonXoaActionPerformed
        // TODO add your handling code here:
        delete(5);
    }//GEN-LAST:event_btnNonXoaActionPerformed

    private void btnLoaiXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoaiXoaActionPerformed
        // TODO add your handling code here:
        delete(4);
    }//GEN-LAST:event_btnLoaiXoaActionPerformed

    private void btnNhanVienXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienXoaActionPerformed
        // TODO add your handling code here:
        delete(1);
    }//GEN-LAST:event_btnNhanVienXoaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        HoaDon instance = new HoaDon();

        try {
            String filePath = "thongke_hoadon";
            String excelFilePath = XExcel.writeExcel(this, thongKeHoaDonList, filePath, instance);

            System.out.println("Xuất thành công excel: " + excelFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        KhachHangThongKe instance = new KhachHangThongKe();

        try {
            String filePath = "thongke_khachhang";
            String excelFilePath = XExcel.writeExcel(this, khtkList, filePath, instance);

            System.out.println("Xuất thành công excel: " + excelFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed
    Date ngayBatDauCheck = new Date();
    Date ngayKetThucCheck = new Date();
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Date ngayBatDau = dcNgayBatDau.getDate();
        Date ngayKetThuc = dcNgayKetThuc.getDate();

        if (ngayBatDau == null || ngayKetThuc == null) {
            MsgBox.showErrorDialog(this, "Vui lòng chọn chính xác ngày", "Lỗi");
            return;
        }

// Create a SimpleDateFormat object with the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

// Format the dates using the SimpleDateFormat object
        String formattedNgayBatDau = dateFormat.format(ngayBatDau);
        String formattedNgayKetThuc = dateFormat.format(ngayKetThuc);

        // Check if ngayKetThuc is greater than ngayBatDau
        if (ngayKetThuc != null && ngayKetThuc.after(ngayBatDau)) {
            ArrayList<Object[]> list = (ArrayList<Object[]>) tkdao.getDoanhThu(formattedNgayBatDau, formattedNgayKetThuc);
            fillTableThongKeHoaDon(list);
            ngayBatDauCheck = ngayBatDau;
            ngayKetThucCheck = ngayKetThuc;
        } else {
            dcNgayBatDau.setDate(ngayBatDauCheck);
            dcNgayKetThuc.setDate(ngayKetThucCheck);
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed
    private List<NhanVienFile> convertToNhanVienFile(List<NhanVien> employeeList) {
        List<NhanVienFile> pdfList = new ArrayList<>();
        for (NhanVien nv : employeeList) {
            NhanVienFile pdf = new NhanVienFile(nv);
            pdfList.add(pdf);
        }
        return pdfList;
    }

    private void handleTimKiemThuongHieu(String search) {
        DefaultTableModel model = (DefaultTableModel) this.tblThuongHieu.getModel();
        model.setRowCount(0);
        try {
            ArrayList<ThuongHieu> list = new ArrayList<>();
            ArrayList<ThuongHieu> currentList = (ArrayList<ThuongHieu>) thdao.selectAll();
            for (ThuongHieu th : currentList) {
                if (th.getTenThuongHieu().toLowerCase().contains(search.toLowerCase())) {
                    list.add(th);
                }
            }

            fillTableThuongHieu(list);
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void handleTimKiemNon(String search) {
        DefaultTableModel model = (DefaultTableModel) this.tblNon.getModel();
        model.setRowCount(0);
        try {
            ArrayList<Non> list = new ArrayList<>();
            ArrayList<Non> currentList = (ArrayList<Non>) ndao.selectAll();
            for (Non th : currentList) {
                if (th.getTenNon().toLowerCase().contains(search.toLowerCase())) {
                    list.add(th);
                }
            }

            fillTableNon(list);
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void handleTimKiemNhanVien(String search) {
        DefaultTableModel model = (DefaultTableModel) this.tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            ArrayList<NhanVien> list = new ArrayList<>();
            ArrayList<NhanVien> currentList = (ArrayList<NhanVien>) nvdao.selectAll();
            System.out.println(search);
            for (NhanVien nh : currentList) {
                System.out.println(nh.getHoTen().toLowerCase());
                if (nh.getEmail().toLowerCase().contains(search.toLowerCase())) {
                    list.add(nh);
                }
            }

            fillTableNhanVien(list);
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void handleTimKiemHoaDon(String search) {
        DefaultTableModel model = (DefaultTableModel) this.tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            ArrayList<HoaDon> list = new ArrayList<>();
            ArrayList<HoaDon> currentList = (ArrayList<HoaDon>) hoaDonDao.selectAll();
            for (HoaDon nh : currentList) {
                if (("HD" + nh.getMaHoaDon()).toLowerCase().contains(search.toLowerCase())) {
                    list.add(nh);
                }
            }

            fillTableHoaDon(list);
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void handleTimKiemLoai(String search) {
        DefaultTableModel model = (DefaultTableModel) this.tblLoai.getModel();
        model.setRowCount(0);
        try {
            ArrayList<Loai> list = new ArrayList<>();
            ArrayList<Loai> currentList = (ArrayList<Loai>) ldao.selectAll();
            for (Loai nh : currentList) {
                if (nh.getTenLoai().toLowerCase().contains(search.toLowerCase())) {
                    list.add(nh);
                }
            }

            fillTableLoai(list);
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void handleTimKiemKhachHang(String search) {
        DefaultTableModel model = (DefaultTableModel) this.tblKhachHang.getModel();
        model.setRowCount(0);
        try {
            ArrayList<KhachHang> list = new ArrayList<>();
            ArrayList<KhachHang> currentList = (ArrayList<KhachHang>) khdao.selectAll();
            for (KhachHang nh : currentList) {
                if (nh.getSoDienThoai().toLowerCase().contains(search.toLowerCase())) {
                    list.add(nh);
                }
            }

            fillTableKhachHang(list);
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

// menu
    // 1. nhanvien
    // 2. thuong hieu   
    // 3. khach hang
    // 4. loai
    // 5. non
    // validate form 
    private boolean validateForm(int type) {
        if (type == 1) {
            return validateFormNhanVien();
        } else if (type == 2) {
            return validateFormThuongHieu();
        } else if (type == 3) {
            return validateFormKhachHang();
        } else if (type == 4) {
            return validateFormLoai();
        } else if (type == 5) {
            return validateFormNon();
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isVietnamesePhoneNumber(String number) {
        String vietnamesePhoneRegex = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(vietnamesePhoneRegex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private boolean checkEmpty(JTextField... txts) {
        for (JTextField txt : txts) {
            if (txt.getText().isEmpty() || txt.getText().isBlank()) {
                return true;
            }
        }
        return false;
    }

    private boolean validateFormNhanVien() {
        String error = "";

        if (this.txtNhanVienEmail.getText().isEmpty()
                || this.txtNhanVienHoTen.getText().isEmpty()
                || new String(this.txtNhanVienMatKhau.getPassword()).isEmpty()
                || new String(this.txtNhanVienXacNhanMatKhau.getPassword()).isEmpty()
                || this.txtNhanVienSDT.getText().isEmpty()) {
            MsgBox.showErrorDialog(this, "Bạn phải nhập đầy đủ các trường của nhân viên", "Thông báo");
            return false;
        }

        if (!isValidEmail(txtNhanVienEmail.getText())) {
            error += "Phải đúng định dạng email";
        }

        if (!isVietnamesePhoneNumber(txtNhanVienSDT.getText())) {
            error += "\nPhải là số điện thoại Việt Nam (Đầu 03, 05, 07, 08, 09 và phải đủ 10 số)";
        }

        NhanVien nhanVien = nvdao.getByEmail(this.txtNhanVienEmail.getText());
        if (nhanVien != null && nhanVien.getMaNV() != getId(this.txtNhanVienID.getText())) {
            MsgBox.showErrorDialog(this, "Email đã tồn tại!", "Lỗi");
            return false;
        }

        String s1 = txtNhanVienMatKhau.getText();
        String s2 = txtNhanVienXacNhanMatKhau.getText();
        if (s1.equals(s2) == false) {
            error += "\nXác nhận mật khẩu không khớp";
            txtNhanVienXacNhanMatKhau.requestFocus();
        }
        if (error != "") {
            MsgBox.showErrorDialog(this, error, "Lỗi");
        }

        return error.isEmpty();
    }

    private boolean validateFormNon() {
        if (this.checkEmpty(txtNonGia, txtNonGiamGia, txtNonSoLuong, txtNonTen)) {
            MsgBox.showErrorDialog(this, "Bạn phải nhập đầy đủ các trường của nón", "Thông báo");
            return false;
        }
        Non non = ndao.getByName(this.txtNonTen.getText());
        if (non != null && non.getMaNon() != getId(this.txtNonID.getText())) {
            MsgBox.showErrorDialog(this, "Tên nón đã tồn tại vui lòng chọn tên nón khác!", "Thông báo");
            return false;
        }
        if (txtNonGia.getText().length() >= 10) {
            MsgBox.showMessageDialog(this, "Giá của sản phẩm quá cao không thể nhập");
            return false;
        }

        if (cboNonLoai.getItemCount() < 1) {
            MsgBox.showMessageDialog(this, "Hiện tại chưa có loại");
            return false;
        }
        if (cboNonThuongHieu.getItemCount() < 1) {
            MsgBox.showMessageDialog(this, "Hiện tại chưa có thương hiệu");
            return false;
        }
        int giamGia = Integer.parseInt(txtNonGiamGia.getText());
        if (giamGia < 0 || giamGia > 100) {
            MsgBox.showMessageDialog(this, "Giảm giá phải từ (0 - 100)");
            return false;
        }

        return true;

    }

    // trong kiem tra
    private boolean validateFormLoai() {
        // kiểm tra có trống không
        if (this.checkEmpty(this.txtLoaiTen)) {
            MsgBox.showErrorDialog(this, "Bạn phải nhập đầy đủ các trường của nón", "Thông báo");
            return false;
        }

        Loai loai = ldao.getByName(this.txtLoaiTen.getText());
        // kiểm tra có null không nếu không thì lấy id thương hiệu ra check có giống không
        if (loai != null && loai.getMaLoai() != getId(txtLoaiID.getText())) {
            MsgBox.showErrorDialog(this, "Tên loại đã tồn tại vui lòng chọn tên loại khác!", "Thông báo");
            return false;
        }

        return true;

    }

    // khoa kiem tra
    private boolean validateFormThuongHieu() {
        // kiem tra tên thương hiệu có trống không
        if (this.checkEmpty(this.txtThuongHieuTen)) {
            MsgBox.showErrorDialog(this, "Bạn phải nhập đầy đủ các trường của nón", "Thông báo");
            return false;
        }
        ThuongHieu thuongHieu = thdao.getByName(this.txtThuongHieuTen.getText());
        // kiểm tra có null không nếu không thì lấy id thương hiệu ra check có giống không
        if (thuongHieu != null && thuongHieu.getMaThuongHieu() != getId(this.txtThuongHieuID.getText())) {
            MsgBox.showErrorDialog(this, "Tên loại đã trùng vui lòng nhập tên loại khác", "Thông báo");
            return false;
        }

        return true;

    }

    private boolean validateFormKhachHang() {
        if (this.checkEmpty(this.txtKhachHangSDT, this.txtKhachHangTen)) {
            MsgBox.showErrorDialog(this, "Bạn phải nhập đầy đủ các trường của nón", "Thông báo");
            return false;
        }
        KhachHang khachHang = khdao.getBySDT(this.txtKhachHangSDT.getText());
        if (khachHang != null && khachHang.getMaKhachHang() != getId(this.txtKhachHangID.getText())) {
            MsgBox.showErrorDialog(this, "Số điện thoại đã tồn tại vui lòng nhập số khác", "Thông báo");
            return false;
        }
        if (!isValidEmail(txtKhachHangEmail.getText())) {
            MsgBox.showErrorDialog(this, "Phải đúng định dạng email", "Thông báo");
            return false;
        }

        if (!isVietnamesePhoneNumber(txtKhachHangSDT.getText())) {
            MsgBox.showErrorDialog(this, "Phải là số điện thoại Việt Nam (Đầu 03, 05, 07, 08, 09 và phải đủ 10 số)", "Thông báo");
            return false;
        }
        Calendar currentDate = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(txtKhachHangNgaySinh.getDate());

        int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        if (age < 10) {
            MsgBox.showErrorDialog(this, "Khách hàng phải từ 10 tuổi trở lên", "Thông báo");
            return false;
        }

        return true;
    }

    // clear form
    private void clearAllForm() {

        clearForm(1);
        clearForm(2);
        clearForm(3);
        clearForm(4);
        clearForm(5);
        clearForm(6);

    }

    private void clearForm(int type) {
        switch (type) {
            case 1:
                clearFormNhanVien();
                break;
            case 2:
                clearFormThuongHieu();
                break;
            case 3:
                clearFormKhachHang();
                break;
            case 4:
                clearFormLoai();
                break;
            case 5:
                clearFormNon();
                break;
            default:
                clearFormHoaDon();
                break;
        }
        generateId(type);

    }

    private void setImg(String path, JLabel lblHinhAnh) {
        ImageIcon img = new ImageIcon("images\\" + path);
        lblHinhAnh.setToolTipText(path);
        int width = lblHinhAnh.getWidth();
        int height = lblHinhAnh.getHeight();
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        lblHinhAnh.setIcon(new ImageIcon(img2));
    }

    private void clearFormNhanVien() {
        this.txtNhanVienEmail.setText("");
        this.txtNhanVienHoTen.setText("");
        this.txtNhanVienMatKhau.setText("");
        this.txtNhanVienMatKhau.setText("");
        this.txtNhanVienXacNhanMatKhau.setText("");
        this.txtNhanVienSDT.setText("");
        this.txtNhanVienID.setText("ID sẽ tự sinh");
        this.txtNhanVienMatKhau.setEditable(true);
        this.txtNhanVienXacNhanMatKhau.setEditable(true);
        setImg("user.png", this.lblNhanVienHinhAnh);
        rdoNhanVienNhanVien.setSelected(true);

        btnNhanVienSua.setEnabled(false);
        btnNhanVienThem.setEnabled(true);

        this.txtNhanVienHoTen.requestFocus();
    }

    private void clearFormNon() {
        this.txtNonGia.setText("");
        this.txtNonGiamGia.setText("");
        this.txtNonSoLuong.setText("");
        this.txtNonID.setText("ID sẽ tự sinh");
        this.txtNonTen.setText("");
        if (cboNonLoai.getItemCount() > 0) {
            this.cboNonLoai.setSelectedIndex(0);

        }
        if (cboNonThuongHieu.getItemCount() > 0) {
            this.cboNonThuongHieu.setSelectedIndex(0);

        }
        setImg("sanpham.jpg", this.lblNonHinhAnh);

        btnNonSua.setEnabled(false);
        btnNonThem.setEnabled(true);
        btnNonXoa.setEnabled(false);

        this.txtNonGia.requestFocus();

    }

    private void clearFormLoai() {
        this.txtLoaiID.setText("ID sẽ tự sinh");
        this.txtLoaiTen.setText("");

        btnLoaiSua.setEnabled(false);
        btnLoaiThem.setEnabled(true);
        btnLoaiXoa.setEnabled(false);

        txtLoaiTen.requestFocus();

    }

    private void clearFormThuongHieu() {
        this.txtThuongHieuID.setText("ID sẽ tự sinh");
        this.txtThuongHieuTen.setText("");
        setImg("logo.png", this.lblThuongHieuHinhAnh);
        btnThuongHieuSua.setEnabled(false);
        btnThuongHieuThem.setEnabled(true);
        txtThuongHieuTen.requestFocus();
        btnThuongHieuXoa.setEnabled(false);
    }

    private void clearFormKhachHang() {
        this.txtKhachHangID.setText("ID sẽ tự sinh");
        this.txtKhachHangEmail.setText("");
        this.txtKhachHangNgaySinh.setDate(new Date());
        this.txtKhachHangSDT.setText("");
        this.txtKhachHangTen.setText("");
        setImg("kh.jpg", this.lblKhachHangHinhAnh);

        btnKhachHangSua.setEnabled(false);
        btnKhachHangThem.setEnabled(true);
        btnKhachHangXoa.setEnabled(false);

        this.txtKhachHangTen.requestFocus();
        btnChonKhachHang.setVisible(false);

    }

    private void clearFormHoaDon() {
        this.txtHoaDonID.setText("ID sẽ tự sinh");
        this.txtHoaDonNgayTao.setText(XDate.toString(new Date()));
        cboHoaDonNon.setSelectedIndex(0);
        cboHoaDonSDT.setSelectedIndex(0);
        txtHoaDonTongTien.setText("0");
    }

    // get form
    private Entity getForm(int type) {
        Entity e = null;
        switch (type) {
            case 1:
                e = getNhanVienForm();
                break;
            case 2:
                e = getThuongHieuForm();
                break;
            case 3:
                e = getKhachHangForm();
                break;
            case 4:
                e = getLoaiForm();
                break;
            case 5:
                e = getNonForm();
                break;
            default:
                break;

        }
        return e;

    }

    private NhanVien getNhanVienForm() {
        NhanVien nv = new NhanVien();
        nv.setEmail(txtNhanVienEmail.getText());
        nv.setHoTen(txtNhanVienHoTen.getText());
        nv.setHinh(lblNhanVienHinhAnh.getToolTipText());
        nv.setMatKhau(txtNhanVienMatKhau.getText());
        nv.setSdt(txtNhanVienSDT.getText());
        nv.setVaiTro(rdoNhanVienQuanLy.isSelected() ? true : false);
        nv.setHinh(lblNhanVienHinhAnh.getToolTipText() == null || lblNhanVienHinhAnh.getToolTipText().isEmpty() || lblNhanVienHinhAnh.getToolTipText().isBlank() ? "user.png" : lblNhanVienHinhAnh.getToolTipText());

        return nv;
    }

    private Non getNonForm() {
        Non non = new Non();
        non.setGiamGia(Integer.parseInt(txtNonGiamGia.getText()));
        non.setGia(Integer.parseInt(txtNonGia.getText()));
        non.setTenNon(txtNonTen.getText());
        non.setHinh(lblNonHinhAnh.getToolTipText() == null || lblNonHinhAnh.getToolTipText().isEmpty() || lblNonHinhAnh.getToolTipText().isBlank() ? "sanpham.jpg" : lblNonHinhAnh.getToolTipText());
        non.setMaLoai(((Loai) cboNonLoai.getSelectedItem()).getMaLoai());
        non.setMaThuongHieu(((ThuongHieu) cboNonThuongHieu.getSelectedItem()).getMaThuongHieu());
        non.setSoLuong(Integer.parseInt(txtNonSoLuong.getText()));
        non.setNgayTao(new Date());
        return non;
    }

    // khoa get form
    private ThuongHieu getThuongHieuForm() {
        ThuongHieu th = new ThuongHieu();
        th.setTenThuongHieu(txtThuongHieuTen.getText());
        th.setLogo(lblThuongHieuHinhAnh.getToolTipText() == null || lblThuongHieuHinhAnh.getToolTipText().isEmpty() || lblThuongHieuHinhAnh.getToolTipText().isBlank() ? "logo.png" : lblThuongHieuHinhAnh.getToolTipText());
        return th;
    }

    private Loai getLoaiForm() {
        Loai loai = new Loai();
        loai.setTenLoai(txtLoaiTen.getText());
        return loai;
    }

    private KhachHang getKhachHangForm() {
        KhachHang kh = new KhachHang();
        kh.setTenKhachHang(txtKhachHangTen.getText());
        kh.setEmail(txtKhachHangEmail.getText());
        kh.setNgaySinh(txtKhachHangNgaySinh.getDate());
        kh.setNgayGiaNhap(new Date());
        kh.setSoDienThoai(txtKhachHangSDT.getText());
        kh.setGioiTinh(rdoKhachHangNam.isSelected());
        kh.setHinh(lblKhachHangHinhAnh.getToolTipText() == null || lblKhachHangHinhAnh.getToolTipText().isEmpty() || lblKhachHangHinhAnh.getToolTipText().isBlank() ? "kh.jpg" : lblKhachHangHinhAnh.getToolTipText());
        return kh;
    }

    public static void createQR(String data, String path,
            String charset, Map hashMap,
            int height, int width)
            throws WriterException, IOException {

        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToFile(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                new File(path));
    }

    private void generateQR(String maSP) {
        String data = maSP;

        String path = "qr//" + data + ".png";
        File dst = new File("images", path);
        Path to = Paths.get(dst.getAbsolutePath());
        System.out.println(to.toString());
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);

        try {
            createQR(data, to.toString(), charset, hashMap, 200, 200);

        } catch (Exception ex) {
            Logger.getLogger(MainShopBanNon.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("QR Code Generated!!! ");
    }

    // insert
    void insert(int type) {
        CommonDao dao = null;
        String text = "";
        switch (type) {
            case 1:
                text = "nhân viên";
                dao = nvdao;
                break;
            case 2:
                text = "thương hiệu";
                dao = thdao;
                break;
            case 3:
                text = "khách hàng";
                dao = khdao;
                break;
            case 4:
                text = "loại nón";
                dao = ldao;
                break;
            case 5:
                text = "nón";
                dao = ndao;
                break;

            default:
                break;
        }

        if (dao == null) {
            return;
        }

        Entity model = getForm(type);
        try {
            dao.insert(model);
            if (themSDT) {
                btnChonKhachHangActionPerformed(null);
                themSDT = false;
            }
            MsgBox.showMessageDialog(this, "Thêm mới " + text + " thành công");
            fillTable(type, null);

            clearForm(type);
            if (type == 5) {
                Non non = ndao.getByName(((Non) model).getTenNon());
                if (non != null) {
                    generateQR("SP" + non.getMaNon());
                }
                fillComboboxSP();
            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Thêm mới " + text + " thất bại !");
            e.printStackTrace();
        }
    }

    private void inputDigit(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }

    private void inputCharacter(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }

    // update
    private void update(int type) {
        String entity = "";
        if (type == 1) {
            entity = "nhân viên";
        } else if (type == 2) {
            entity = "thương hiệu";
        } else if (type == 3) {
            entity = "khách hàng";
        } else if (type == 4) {
            entity = "loại";
        } else {
            entity = "nón";
        }
        boolean check = MsgBox.showConfirmDialog(this, "Bạn có chắc muốn cập nhật " + entity + " này không?");
        if (!check) {
            return;
        }
        switch (type) {
            case 1:
                updateNhanVien();
                break;
            case 2:
                updateThuongHieu();
                break;
            case 3:
                updateKhachHang();
                break;
            case 4:
                updateLoai();
                break;
            case 5:
                updateNon();
                break;
            default:
                break;
        }
        fillTable(type, null);
//        if (type == 2) {
//            fillComboboxThuongHieu();
//        }
//        if (type == 4) {
//            fillComboBoxLoai();
//        }
        clearForm(type);
    }

    private void updateNhanVien() {
        try {
            NhanVien nv = getNhanVienForm();
            int id = getId(txtNhanVienID.getText());
            NhanVien nvCu = nvdao.selectById(id);
            if (nvCu.isVaiTro() && Auth.user.getMaNV() != 1) {
                MsgBox.showMessageDialog(this, "Bạn không có quyền chỉnh sửa người có quyền quản lý");
                return;
            }
            nv.setMaNV(id);
            nvdao.updateKhongDoiMatKhau(nv);
            MsgBox.showMessageDialog(this, "Cập nhật thành công nhân viên!");

        } catch (Exception ex) {
            MsgBox.showMessageDialog(this, "Cập nhật thất bại nhân viên: " + ex.getMessage());
        }

    }

    private void updateNon() {
        try {
            Non non = getNonForm();
            int id = getId(txtNonID.getText());
            non.setMaNon(id);
            ndao.update(non);
            MsgBox.showMessageDialog(this, "Cập nhật thành công nón!");

        } catch (Exception ex) {
            MsgBox.showMessageDialog(this, "Cập nhật thất bại nón: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    // trong chinh sua
    private void updateLoai() {
        try {
            Loai loai = getLoaiForm();
            int id = getId(txtLoaiID.getText());
            loai.setMaLoai(id);
            ldao.update(loai);
            MsgBox.showMessageDialog(this, "Cập nhật thành công loại!");
        } catch (Exception ex) {
            MsgBox.showMessageDialog(this, "Cập nhật thất bại loại nón: " + ex.getMessage());
        }

    }

    // khoa chinh sua
    private void updateThuongHieu() {
        try {
            ThuongHieu th = getThuongHieuForm();

            int id = getId(txtThuongHieuID.getText());
            th.setMaThuongHieu(id);
            thdao.update(th);
            MsgBox.showMessageDialog(this, "Cập nhật thành công thương hiệu!");
        } catch (Exception ex) {
            MsgBox.showMessageDialog(this, "Cập nhật thất bại thương hiệu: " + ex.getMessage());
        }

    }

    private void updateKhachHang() {
        try {
            KhachHang kh = getKhachHangForm();
            int id = getId(txtKhachHangID.getText());
            kh.setMaKhachHang(id);
            khdao.update(kh);
            MsgBox.showMessageDialog(this, "Cập nhật thành công khách hàng!");
        } catch (Exception ex) {
            MsgBox.showMessageDialog(this, "Cập nhật thất bại khách hàng: " + ex.getMessage());
        }

    }

    private int getId(String id) {
        try {
            return Integer.parseInt(id.substring(2));
        } catch (NumberFormatException ex) {
            return -1;
        }

    }

    // delete
    void delete(int type) {
        if (Auth.user != null) {
            if (!Auth.user.isVaiTro()) {
                return;
            }
        }
        String entity = "";
        if (type == 1) {
            entity = "nhân viên";
        } else if (type == 2) {
            entity = "thương hiệu";
        } else if (type == 3) {
            entity = "khách hàng";
        } else if (type == 4) {
            entity = "loại";
        } else {
            entity = "nón";
        }
        boolean check = MsgBox.showConfirmDialog(this, "Bạn có chắc muốn xóa " + entity + " này không?");
        if (!check) {
            return;
        }
        Entity e = null;
        switch (type) {
            case 1:
                deleteNhanVien();
                break;
            case 2:
                deleteThuongHieu();
                break;
            case 3:
                deleteKhachHang();
                break;
            case 4:
                deleteLoai();
                break;
            case 5:
                deleteNon();
                break;
            default:
                break;

        }
        fillTable(type, null);
        if (type == 2) {
            fillComboboxThuongHieu();
        }
        if (type == 4) {
            fillComboBoxLoai();
        }
        clearForm(type);

    }

    private void deleteNhanVien() {
        int id = getId(txtNhanVienID.getText());
        NhanVien nvCu = nvdao.selectById(id);
        if (nvCu.isVaiTro() && Auth.user.getMaNV() != 1) {
            MsgBox.showMessageDialog(this, "Bạn không có quyền xóa người có quyền quản lý");
            return;
        }
        nvdao.delete(getId(this.txtNhanVienID.getText()), true);
        MsgBox.showMessageDialog(this, "Xóa thành công nhân viên");
    }

    private void deleteNon() {
        HoaDonChiTiet hdct = hcdtDao.selectByMaNon(getId(this.txtNonID.getText()));
        if (hdct != null) {
            MsgBox.showMessageDialog(this, "Xóa thất bại do sản phẩm đã tồn tại trong hóa đơn");
            return;
        }
        ndao.delete(getId(this.txtNonID.getText()), true);
        MsgBox.showMessageDialog(this, "Xóa thành công nón");
    }

    // trong xoa
    private void deleteLoai() {
        Non non = ndao.getByLoai(getId(this.txtLoaiID.getText()));
        if (non == null) {
            ldao.delete(getId(this.txtLoaiID.getText()), true);
            MsgBox.showMessageDialog(this, "Xóa thành công loại");
        } else {
            MsgBox.showMessageDialog(this, "Xóa thất bại do có nón sử dụng loại này");
        }
    }

    // khoa xoa
    private void deleteThuongHieu() {
        Non non = ndao.getByThuongHieu(getId(this.txtThuongHieuID.getText()));
        if (non == null) {
            thdao.delete(getId(this.txtThuongHieuID.getText()), true);
            MsgBox.showMessageDialog(this, "Xóa thành công thương hiệu");
            fillTable(2, null);
            clearForm(2);
        } else {
            MsgBox.showMessageDialog(this, "Xóa thất bại do có nón sử dụng thương hiệu này");
        }
    }

    private void deleteKhachHang() {
        HoaDon hd = hoaDonDao.selectByMaKH(getId(this.txtKhachHangID.getText()));
        if (hd != null) {
            MsgBox.showMessageDialog(this, "Xóa thất bại do khách hàng này đã mua hàng");
            return;
        }
        khdao.delete(getId(this.txtKhachHangID.getText()), true);
        MsgBox.showMessageDialog(this, "Xóa thành công khách hàng");

    }

    // fill table
    private void fillTable(int type, ArrayList<Entity> list) {
        Entity e = null;
        switch (type) {
            case 1:
                ArrayList<NhanVien> nhanVienList = new ArrayList<>();
                if (list == null) {
                    nhanVienList = (ArrayList<NhanVien>) nvdao.selectAll();
                } else {
                    for (Entity entity : list) {
                        if (entity instanceof NhanVien) {
                            nhanVienList.add((NhanVien) entity);
                        }
                    }
                }

                fillTableNhanVien(nhanVienList);
                break;
            case 2:
                // khoa fill table
                ArrayList<ThuongHieu> thuongHieuList = new ArrayList<>();
                if (list == null) {
                    thuongHieuList = (ArrayList<ThuongHieu>) thdao.selectAll();
                } else {
                    for (Entity entity : list) {
                        if (entity instanceof ThuongHieu) {
                            thuongHieuList.add((ThuongHieu) entity);
                        }
                    }
                }

                fillTableThuongHieu(thuongHieuList);
                break;
            case 3:
                ArrayList<KhachHang> khachHangList = new ArrayList<>();
                if (list == null) {
                    khachHangList = (ArrayList<KhachHang>) khdao.selectAll();
                } else {
                    for (Entity entity : list) {
                        if (entity instanceof KhachHang) {
                            khachHangList.add((KhachHang) entity);
                        }
                    }
                }

                fillTableKhachHang(khachHangList);
                break;
            case 4:
                // trong fill table
                ArrayList<Loai> loaiList = new ArrayList<>();
                if (list == null) {
                    loaiList = (ArrayList<Loai>) ldao.selectAll();
                } else {
                    for (Entity entity : list) {
                        if (entity instanceof Loai) {
                            loaiList.add((Loai) entity);
                        }
                    }
                }

                fillTableLoai(loaiList);
                break;
            case 5:
                ArrayList<Non> nonList = new ArrayList<>();
                if (list == null) {
                    nonList = (ArrayList<Non>) ndao.selectAll();
                } else {
                    for (Entity entity : list) {
                        if (entity instanceof Non) {
                            nonList.add((Non) entity);
                        }
                    }
                }
                fillTableNon(nonList);
                break;
            default:
                ArrayList<HoaDon> hdList = new ArrayList<>();
                if (list == null) {
                    hdList = (ArrayList<HoaDon>) hoaDonDao.selectAll();
                } else {
                    for (Entity entity : list) {
                        if (entity instanceof Non) {
                            hdList.add((HoaDon) entity);
                        }
                    }
                }
                fillTableHoaDon(hdList);
                break;

        }
    }

    private void fillTableHoaDon(ArrayList<HoaDon> list) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();

        model.setRowCount(0);

        try {
            for (HoaDon hd : list) {
                Object[] row = {
                    "HD" + hd.getMaHoaDon(),
                    XDate.toString(hd.getNgayTao()),
                    hd.getMaNhanVien(),
                    khdao.selectById(hd.getMaKhachHang()).getSoDienThoai(),
                    hd.getTrangThai(),
                    Common.formatVietnameseMoney(hd.getTongTien())
                };

                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }

    }

    private void fillTableBanHang(ArrayList<HoaDonChiTiet> list) {
        DefaultTableModel model = (DefaultTableModel) tblBanHang.getModel();
        Double tongTien = 0.0;
        model.setRowCount(0);

        try {
            for (HoaDonChiTiet hdct : list) {

                Object[] row = {
                    "SP" + hdct.getMaNon(),
                    hdct.getTenNon(),
                    hdct.getSoLuong(),
                    Common.formatVietnameseMoney(hdct.getGia()),
                    Common.formatVietnameseMoney(hdct.getGia() * hdct.getSoLuong())
                };
                tongTien += hdct.getGia() * hdct.getSoLuong();
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
        txtHoaDonTongTien.setText(Common.formatVietnameseMoney(tongTien));
    }

    private void fillTableNhanVien(ArrayList<NhanVien> list) {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();

        model.setRowCount(0);

        try {
//            List<NhanVien> list = daoNhanVien.selectAll();

            List<NhanVien> current = new ArrayList<>();//đọc dữ liệu từ CSDL

            for (NhanVien cd : list) {
                if (!cd.isDeleted()) {
                    current.add(cd);
                }
            }

            for (NhanVien nv : current) {
                if (nv.getMaNV() == 1) {
                    continue;
                }
                if (nv.getMaNV() == Auth.user.getMaNV()) {
                    continue;
                }
                Object[] row = {
                    "NV" + nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getEmail(),
                    nv.getSdt(),
                    (nv.isVaiTro() ? "Quản lý" : "Nhân viên")};

                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    List<HoaDon> thongKeHoaDonList = new ArrayList<>();

    private void fillTableThongKeHoaDon(ArrayList<Object[]> list) {
        DefaultTableModel model = (DefaultTableModel) tblThongKeHoaDon.getModel();

        model.setRowCount(0);

        try {
//            List<NhanVien> list = daoNhanVien.selectAll();
//            ArrayList<Object[]> list = (ArrayList<Object[]>) tkdao.getDoanhThu(monthNgayBatDau, monthNgayKetThuc);
            thongKeHoaDonList = new ArrayList<>();
            int sum = 0;
            for (Object[] rowData : list) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(Integer.valueOf(rowData[0].toString()));
                hd.setMaNhanVien(Integer.valueOf(rowData[1].toString()));
                hd.setMaKhachHang(Integer.valueOf(rowData[2].toString()));
                hd.setTongTien(Double.valueOf(rowData[3].toString()));
                hd.setTrangThai(rowData[4].toString());
                hd.setNgayTao((Date) rowData[5]);
                thongKeHoaDonList.add(hd);
                sum += hd.getTongTien();
            }
            for (Object[] rowData : list) {
                rowData[3] = Common.formatVietnameseMoney(Double.valueOf(rowData[3].toString()));
                rowData[0] = "HD" + rowData[0];
                rowData[1] = "NV" + rowData[1];
                rowData[2] = "KH" + rowData[2];

                model.addRow(rowData);
            }
            lblTongTien.setText("Tổng tiền: " + Common.formatVietnameseMoney(sum));
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void fillTableNon(List<Non> list) {
        DefaultTableModel model = (DefaultTableModel) tblNon.getModel();

        model.setRowCount(0);

        try {
//            List<Non> list = ndao.selectAll();

            List<Non> current = new ArrayList<>();//đọc dữ liệu từ CSDL

            for (Non cd : list) {
                if (!cd.isDeleted()) {
                    current.add(cd);
                }
            }
            for (Non nv : current) {
                Object[] row = {
                    "SP" + nv.getMaNon(),
                    nv.getTenNon(),
                    Common.formatVietnameseMoney(nv.getGia()),
                    thdao.selectById(nv.getMaThuongHieu()),
                    ldao.selectById(nv.getMaLoai()),
                    nv.getSoLuong() == 0 ? "Hết hàng" : nv.getSoLuong(),
                    nv.getGiamGia() + "%"
                };
                model.addRow(row);

            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void fillTableLoai(List<Loai> list) {
        DefaultTableModel model = (DefaultTableModel) tblLoai.getModel();

        model.setRowCount(0);

        try {
//            List<Loai> list = ldao.selectAll();

            List<Loai> current = new ArrayList<>();//đọc dữ liệu từ CSDL

            for (Loai cd : list) {
                if (!cd.isDeleted()) {
                    current.add(cd);
                }
            }

            for (Loai nv : current) {
                Object[] row = {
                    "LO" + nv.getMaLoai(),
                    nv.getTenLoai()
                };

                model.addRow(row);

            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void fillTableThuongHieu(List<ThuongHieu> list) {
        DefaultTableModel model = (DefaultTableModel) tblThuongHieu.getModel();

        model.setRowCount(0);

        try {
//            List<ThuongHieu> list = thdao.selectAll();

            List<ThuongHieu> current = new ArrayList<>();//đọc dữ liệu từ CSDL

            for (ThuongHieu cd : list) {
                if (!cd.isDeleted()) {
                    current.add(cd);
                }
            }

            for (ThuongHieu nv : current) {
                Object[] row = {
                    "TH" + nv.getMaThuongHieu(),
                    nv.getTenThuongHieu()
                };

                model.addRow(row);

            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void fillTableKhachHang(List<KhachHang> list) {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();

        model.setRowCount(0);

        try {
//            List<KhachHang> list = khdao.selectAll();

            List<KhachHang> current = new ArrayList<>();//đọc dữ liệu từ CSDL

            for (KhachHang cd : list) {
                if (!cd.isDeleted()) {
                    current.add(cd);
                }
            }

            for (KhachHang nv : current) {
                Object[] row = {
                    "KH" + nv.getMaKhachHang(),
                    nv.getTenKhachHang(),
                    nv.getSoDienThoai(),
                    nv.getNgaySinh().toString(),
                    nv.isGioiTinh() ? "Nam" : "Nữ"
                };

                model.addRow(row);

            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private ArrayList<KhachHangThongKe> khtkList = new ArrayList();

    void fillTableThongKeKhachHang() {
        DefaultTableModel model = (DefaultTableModel) tblThongKeKhachHang.getModel();
        khtkList = new ArrayList();
        model.setRowCount(0);
        List<KhachHang> list = khdao.selectAll();

        try {
//            List<KhachHang> list = khdao.selectAll();

            List<KhachHang> current = new ArrayList<>();//đọc dữ liệu từ CSDL

            for (KhachHang cd : list) {
                if (!cd.isDeleted()) {
                    current.add(cd);
                }
            }
            int tongTienTatCa = 0;
            for (KhachHang nv : current) {
                List<HoaDon> listHoaDon = hoaDonDao.selectByMaKHFull(nv.getMaKhachHang());
                int tongTien = 0;
                for (HoaDon hd : listHoaDon) {
                    tongTien += hd.getTongTien();

                }
                Object[] row = {
                    "KH" + nv.getMaKhachHang(),
                    nv.getTenKhachHang(),
                    nv.isGioiTinh() ? "Nam" : "Nữ",
                    nv.getSoDienThoai(),
                    Common.formatVietnameseMoney(tongTien)
                };
                KhachHangThongKe khtk = new KhachHangThongKe(row);
                khtkList.add(khtk);
                tongTienTatCa += tongTien;
                model.addRow(row);

            }
        } catch (Exception e) {
            MsgBox.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
            e.printStackTrace();
        }
    }

    // click table
    private void clickTableThuongHieu() {
        int row = tblThuongHieu.getSelectedRow();
        if (row != -1) {
            String maTH = ((String) tblThuongHieu.getValueAt(row, 0)).substring(2);

            ThuongHieu th = thdao.selectById(Integer.parseInt(maTH));
            fillFormThuongHieu(th);
        }

    }

    private void clickTableLoai() {
        int row = tblLoai.getSelectedRow();
        if (row != -1) {
            String maTH = ((String) tblLoai.getValueAt(row, 0)).substring(2);
            Loai l = ldao.selectById(Integer.parseInt(maTH));
            fillFormLoai(l);
        }
    }

    private void clickTableNhanVien() {
        int row = tblNhanVien.getSelectedRow();
        if (row != -1) {
            String maNV = ((String) tblNhanVien.getValueAt(row, 0)).substring(2);
            NhanVien nv = nvdao.selectById(Integer.parseInt(maNV));
            fillFormNhanVien(nv);
        }
    }

    private void clickTableNon() {
        int row = tblNon.getSelectedRow();
        if (row != -1) {
            String maNon = ((String) tblNon.getValueAt(row, 0)).substring(2);
            Non n = ndao.selectById(Integer.parseInt(maNon));
            fillFormNon(n);
        }
    }

    private void clickTableKhachHang() {
        int row = tblKhachHang.getSelectedRow();
        if (row != -1) {
            String maNon = ((String) tblKhachHang.getValueAt(row, 0)).substring(2);
            KhachHang kh = khdao.selectById(Integer.parseInt(maNon));
            fillFormKhachHang(kh);
        }
    }

    // fill form
    private void fillFormNon(Non nv) {
        this.txtNonGia.setText(String.valueOf(nv.getGia()));
        this.txtNonGiamGia.setText(String.valueOf(nv.getGiamGia()));
        this.txtNonSoLuong.setText(String.valueOf(nv.getSoLuong()));
        this.txtNonID.setText("SP" + nv.getMaNon());
        this.txtNonTen.setText(nv.getTenNon());
        this.cboNonLoai.setSelectedItem(this.ldao.selectById(nv.getMaLoai()));
        this.cboNonThuongHieu.setSelectedItem(this.thdao.selectById(nv.getMaThuongHieu()));
        String hinh = nv.getHinh() == null || nv.getHinh().isBlank() || nv.getHinh().isEmpty() ? "ong140.png" : nv.getHinh();
        lblNonHinhAnh.setToolTipText(hinh);

        setImg(hinh, this.lblNonHinhAnh);

        btnNonSua.setEnabled(true);
        btnNonThem.setEnabled(false);
        btnNonXoa.setEnabled(true);
    }

    // trong fill form
    private void fillFormLoai(Loai nv) {
        this.txtLoaiID.setText("LO" + nv.getMaLoai());
        this.txtLoaiTen.setText(nv.getTenLoai());

        btnLoaiSua.setEnabled(true);
        btnLoaiThem.setEnabled(false);
        btnLoaiXoa.setEnabled(true);
    }

    // khoa fill form
    private void fillFormThuongHieu(ThuongHieu nv) {

        this.txtThuongHieuID.setText("TH" + nv.getMaThuongHieu());
        this.txtThuongHieuTen.setText(nv.getTenThuongHieu());
        String hinh = nv.getLogo() == null || nv.getLogo().isBlank() || nv.getLogo().isEmpty() ? "ong140.png" : nv.getLogo();
        lblThuongHieuHinhAnh.setToolTipText(hinh);

        setImg(hinh, this.lblThuongHieuHinhAnh);

        btnThuongHieuSua.setEnabled(true);
        btnThuongHieuThem.setEnabled(false);
        btnThuongHieuXoa.setEnabled(true);
    }

    private void fillFormKhachHang(KhachHang nv) {
        this.txtKhachHangID.setText("KH" + nv.getMaKhachHang());
        this.txtKhachHangEmail.setText(nv.getEmail());
        this.txtKhachHangNgaySinh.setDate(nv.getNgaySinh());
        this.txtKhachHangSDT.setText(nv.getSoDienThoai().toString());
        this.txtKhachHangTen.setText(nv.getTenKhachHang());
        String hinh = nv.getHinh() == null || nv.getHinh().isBlank() || nv.getHinh().isEmpty() ? "ong140.png" : nv.getHinh();
        lblKhachHangHinhAnh.setToolTipText(hinh);
        setImg(hinh, this.lblKhachHangHinhAnh);
        if (nv.isGioiTinh()) {
            rdoKhachHangNam.setSelected(true);
        } else {
            rdoKhachHangNu.setSelected(true);

        }

        btnKhachHangSua.setEnabled(true);
        btnKhachHangThem.setEnabled(false);
        btnChonKhachHang.setVisible(true);
        btnKhachHangXoa.setEnabled(true);

    }

    private void fillFormNhanVien(NhanVien nv) {

        this.txtNhanVienEmail.setText(nv.getEmail());
        this.txtNhanVienHoTen.setText(nv.getHoTen());
        this.txtNhanVienMatKhau.setText("*******************");
        this.txtNhanVienXacNhanMatKhau.setText("*******************");
        this.txtNhanVienMatKhau.setEditable(false);
        this.txtNhanVienXacNhanMatKhau.setEditable(false);
        this.txtNhanVienSDT.setText(nv.getSdt());
        this.txtNhanVienID.setText("NV" + nv.getMaNV());
        String hinh = nv.getHinh() == null || nv.getHinh().isBlank() || nv.getHinh().isEmpty() ? "ong140.png" : nv.getHinh();
        lblNhanVienHinhAnh.setToolTipText(hinh);
        if (nv.isVaiTro()) {
            rdoNhanVienQuanLy.setSelected(true);
        } else {
            rdoNhanVienNhanVien.setSelected(false);
        }

        setImg(hinh, this.lblNhanVienHinhAnh);

        btnNhanVienSua.setEnabled(true);
        btnNhanVienThem.setEnabled(false);
        if (nv.isVaiTro() && Auth.user.getMaNV() != 1) {
            btnNhanVienSua.setEnabled(false);
            btnNhanVienXoa.setEnabled(false);
        } else {
            btnNhanVienSua.setEnabled(true);
            btnNhanVienXoa.setEnabled(true);
        }
    }

    private void generateId(int type) {
        switch (type) {
            case 1:
                generateIdNhanVien();
                break;
            case 2:
                generateIdThuongHieu();
                break;
            case 3:
                generateIdKhachHang();
                break;
            case 4:
                generateIdLoai();
                break;
            case 5:
                generateIdNon();
                break;

            default:
                // Handle other cases if needed
                generateIdHoaDon();
                break;
        }
    }

    private void generateIdHoaDon() {
        try {
            String id = hoaDonDao.generateNewId();
            txtHoaDonID.setText(id);

        } catch (SQLException ex) {
            Logger.getLogger(MainShopBanNon.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateIdNhanVien() {
        try {
            String id = nvdao.generateNewId();
            txtNhanVienID.setText(id);

        } catch (SQLException ex) {
            Logger.getLogger(MainShopBanNon.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateIdThuongHieu() {
        try {
            String id = thdao.generateNewId();
            txtThuongHieuID.setText(id);

        } catch (SQLException ex) {
            Logger.getLogger(MainShopBanNon.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateIdLoai() {
        try {
            String id = ldao.generateNewId();
            txtLoaiID.setText(id);

        } catch (SQLException ex) {
            Logger.getLogger(MainShopBanNon.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateIdNon() {
        try {
            String id = ndao.generateNewId();
            txtNonID.setText(id);

        } catch (SQLException ex) {
            Logger.getLogger(MainShopBanNon.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generateIdKhachHang() {
        try {
            String id = khdao.generateNewId();
            txtKhachHangID.setText(id);

        } catch (SQLException ex) {
            Logger.getLogger(MainShopBanNon.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    // fill combobox
    void fillComboboxSDT() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboHoaDonSDT.getModel();
        model.removeAllElements();
        List<KhachHang> list = khdao.selectAll(); //lấy khóa học theo mã chuyên đề
        model.addElement("Chọn SDT khách hàng");
        for (KhachHang kh : list) {
            if (!kh.isDeleted()) {
                model.addElement(kh);
            }
        }
        model.addElement("Thêm số điện thoại mới");
    }

    void fillComboboxSP() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboHoaDonNon.getModel();
        model.removeAllElements();
        List<Non> list = ndao.selectAll(); //lấy khóa học theo mã chuyên đề
        model.addElement("Chọn mã nón");
        for (Non non : list) {
            if (!non.isDeleted() && non.getSoLuong() > 0) {
                model.addElement(non);
            }
        }
    }

    void fillAllCombobox() {
        fillComboBoxLoai();
        fillComboboxThuongHieu();
        fillComboboxSDT();
        fillComboboxSP();
    }

    void fillComboBoxLoai() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNonLoai.getModel();
        model.removeAllElements();
        List<Loai> list = ldao.selectAll(); //lấy khóa học theo mã chuyên đề
        for (Loai loai : list) {
            if (!loai.isDeleted()) {
                model.addElement(loai);
            }
        }
    }

    void fillComboboxThuongHieu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNonThuongHieu.getModel();
        model.removeAllElements();
        List<ThuongHieu> list = thdao.selectAll(); //lấy khóa học theo mã chuyên đề
        for (ThuongHieu thuonghieu : list) {
            if (!thuonghieu.isDeleted()) {
                model.addElement(thuonghieu);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());

                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainShopBanNon.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainShopBanNon.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainShopBanNon.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainShopBanNon.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainShopBanNon();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonKhachHang;
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnExcelKhachHang;
    private javax.swing.JButton btnHoaDonChiTiet;
    private javax.swing.JButton btnHoaDonChiTiet1;
    private javax.swing.JButton btnHoaDonHuy;
    private javax.swing.JButton btnHoaDonLuu;
    private javax.swing.JButton btnHoaDonXoa;
    private javax.swing.JButton btnKhachHangLamMoi;
    private javax.swing.JButton btnKhachHangSua;
    private javax.swing.JButton btnKhachHangThem;
    private javax.swing.JButton btnKhachHangXoa;
    private javax.swing.JButton btnLoaiLamMoi;
    private javax.swing.JButton btnLoaiSua;
    private javax.swing.JButton btnLoaiThem;
    private javax.swing.JButton btnLoaiXoa;
    private javax.swing.JButton btnNhanVienExcel;
    private javax.swing.JButton btnNhanVienLamMoi;
    private javax.swing.JButton btnNhanVienPDF;
    private javax.swing.JButton btnNhanVienSua;
    private javax.swing.JButton btnNhanVienThem;
    private javax.swing.JButton btnNhanVienXoa;
    private javax.swing.JButton btnNonLamMoi;
    private javax.swing.JButton btnNonSua;
    private javax.swing.JButton btnNonThem;
    private javax.swing.JButton btnNonXoa;
    private javax.swing.JButton btnPDFKhachHang;
    private javax.swing.JButton btnPDFNon;
    private javax.swing.JButton btnThuongHieuLamMoi;
    private javax.swing.JButton btnThuongHieuSua;
    private javax.swing.JButton btnThuongHieuThem;
    private javax.swing.JButton btnThuongHieuXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboHoaDonNon;
    private javax.swing.JComboBox<String> cboHoaDonSDT;
    private javax.swing.JComboBox<String> cboNonLoai;
    private javax.swing.JComboBox<String> cboNonThuongHieu;
    private com.toedter.calendar.JDateChooser dcNgayBatDau;
    private com.toedter.calendar.JDateChooser dcNgayKetThuc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jtpThongKe;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblKhacHangNhieuNhat;
    private javax.swing.JLabel lblKhachHangHinhAnh;
    private javax.swing.JLabel lblNhanVienHinhAnh;
    private javax.swing.JLabel lblNonHinhAnh;
    private javax.swing.JLabel lblSoDienThoaiKhachHang;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblTenKhachHang;
    private javax.swing.JLabel lblThuongHieuHinhAnh;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JPanel pnlDoanhThuTheoNam;
    private javax.swing.JPanel pnlDoanhThuTrongNam;
    private javax.swing.JPanel pnlKhachHangMuaNhieuNhat;
    private javax.swing.JPanel pnlNhanVien;
    private javax.swing.JPanel pnlSoLuongThuongHieu;
    private javax.swing.JPanel pnlThongKe;
    private javax.swing.JPanel pnlThongKeNon;
    private javax.swing.JPanel pnlTrangThaiHoaDon;
    private javax.swing.ButtonGroup radioChucVu;
    private javax.swing.ButtonGroup radioGioiTinh;
    private javax.swing.JRadioButton rdoKhachHangNam;
    private javax.swing.JRadioButton rdoKhachHangNu;
    private javax.swing.JRadioButton rdoNhanVienNhanVien;
    private javax.swing.JRadioButton rdoNhanVienQuanLy;
    private javax.swing.JTable tblBanHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblLoai;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblNon;
    private javax.swing.JTable tblThongKeHoaDon;
    private javax.swing.JTable tblThongKeKhachHang;
    private javax.swing.JTable tblThuongHieu;
    private javax.swing.JLabel txtDoanhThu;
    private javax.swing.JLabel txtDoanhThuHomNay;
    private javax.swing.JPasswordField txtDoiMatKhauMatKhauCu;
    private javax.swing.JPasswordField txtDoiMatKhauMatKhauMoi;
    private javax.swing.JPasswordField txtDoiMatKhauXacNhanMatKhau;
    private javax.swing.JTextField txtHoaDonID;
    private javax.swing.JTextField txtHoaDonNgayTao;
    private javax.swing.JTextField txtHoaDonTenKhachHang;
    private javax.swing.JTextField txtHoaDonTimKiem;
    private javax.swing.JTextField txtHoaDonTongTien;
    private javax.swing.JTextField txtKhachHangEmail;
    private javax.swing.JTextField txtKhachHangID;
    private com.toedter.calendar.JDateChooser txtKhachHangNgaySinh;
    private javax.swing.JTextField txtKhachHangSDT;
    private javax.swing.JTextField txtKhachHangTen;
    private javax.swing.JTextField txtKhachHangTimKiemSoDienThoai;
    private javax.swing.JTextField txtLoaiID;
    private javax.swing.JTextField txtLoaiTen;
    private javax.swing.JTextField txtLoaiTimKiemTen;
    private javax.swing.JTextField txtNhanVienEmail;
    private javax.swing.JTextField txtNhanVienHoTen;
    private javax.swing.JTextField txtNhanVienID;
    private javax.swing.JPasswordField txtNhanVienMatKhau;
    private javax.swing.JTextField txtNhanVienSDT;
    private javax.swing.JTextField txtNhanVienTimKiem;
    private javax.swing.JPasswordField txtNhanVienXacNhanMatKhau;
    private javax.swing.JTextField txtNonGia;
    private javax.swing.JTextField txtNonGiamGia;
    private javax.swing.JTextField txtNonID;
    private javax.swing.JTextField txtNonSoLuong;
    private javax.swing.JTextField txtNonTen;
    private javax.swing.JTextField txtNonTimKiemTen;
    private javax.swing.JTextField txtThuongHieuID;
    private javax.swing.JTextField txtThuongHieuTen;
    private javax.swing.JTextField txtThuongHieuTimKiemTen;
    // End of variables declaration//GEN-END:variables
}
