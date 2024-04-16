/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package DuAn.ui;

import DuAn.dao.HoaDonChiTietDAO;
import DuAn.dao.HoaDonDao;
import DuAn.dao.KhachHangDAO;
import DuAn.dao.NonDAO;
import DuAn.entity.HoaDon;
import DuAn.entity.HoaDonChiTiet;
import DuAn.entity.KhachHang;
import DuAn.entity.Non;
import DuAn.utils.Auth;
import DuAn.utils.Common;
import DuAn.utils.MsgBox;
import DuAn.utils.XDate;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author dinhh
 */
public class XuatHoaDon extends javax.swing.JFrame {

    /**
     * Creates new form XuatHoaDon
     */
    public XuatHoaDon() {
        initComponents();
    }

    private HoaDon hoadon;
    private JLabel[] column;

    public XuatHoaDon(HoaDon hoadon) {
        this.hoadon = hoadon;

        initComponents();
        init();
        this.setLocationRelativeTo(null);
    }

    public void init() {
        KhachHangDAO khachHangDAO = null;

        try {
            khachHangDAO = new KhachHangDAO();
        } catch (Exception ex) {
            Logger.getLogger(XuatHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }

        KhachHang khachHang = khachHangDAO.selectById(hoadon.getMaKhachHang());
        if (khachHang == null) {
            MsgBox.showErrorDialog(this, "Không tìm thấy khách hàng", "Lỗi");
            return;
        }
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setHoaDonInfo();
        setNhanVienInfo();
        setKhachHangInfo(khachHang);
        setThoiGianInfo();
        if (hoadon.getTrangThai().equalsIgnoreCase("Chưa thanh toán")) {
            lblTrangThai.setText("Chưa thanh toán");
            lblTrangThai.setForeground(Color.RED);
        } else {
            lblTrangThai.setText("Đã thanh toán");
            lblTrangThai.setForeground(Color.GREEN);
        }
//        setTongTienInfo();

        setImg(this.lblHinhAnh);
        createPanel_CTHD();
    }

    private void setImg(JLabel lblHinhAnh) {
        ImageIcon img = new ImageIcon("images\\qr\\HD" + hoadon.getMaHoaDon() + ".png");
        lblHinhAnh.setToolTipText(img.getDescription());
        System.out.println("img : " + img);
        int width = lblHinhAnh.getWidth();
        int height = lblHinhAnh.getHeight();
        Image img1 = img.getImage();
        Image img2 = img1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        lblHinhAnh.setIcon(new ImageIcon(img2));
//        setImg("qr\\HD9.png", lblHinhAnh);
    }
//
//    private void setImg(String path, JLabel lblHinhAnh) {
//        ImageIcon img = new ImageIcon("images\\" + path);
//        System.out.println(new File("images\\0fcbd315-dae1-4c4c-9c45-b4f6957de38abc7e2cc8-5").getAbsoluteFile());
//        int width = lblHinhAnh.getWidth();
//        int height = lblHinhAnh.getHeight();
//        Image img1 = img.getImage();
//        Image img2 = img1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        lblHinhAnh.setIcon(new ImageIcon(img2));
//    }

    private void setHoaDonInfo() {
        lblMaHoaDon.setText("Mã hóa đơn: HD" + hoadon.getMaHoaDon());
    }

    private void setNhanVienInfo() {
        lblNhanVien.setText(lblNhanVien.getText() + " " + Auth.user.getHoTen());
//        lblNhanVien.setText(lblNhanVien.getText() + " " + "Test");

    }

    private void setKhachHangInfo(KhachHang khachHang) {
        lblKhachHang.setText(lblKhachHang.getText() + " " + khachHang.getTenKhachHang());
        lblSDTKhachHang.setText(lblSDTKhachHang.getText() + " " + khachHang.getSoDienThoai());
    }

    private void setThoiGianInfo() {
        lblThoiGian.setText(lblThoiGian.getText() + " " + XDate.toString(hoadon.getNgayTao()));
    }

//    private void setTongTienInfo() {
//        // Set the text of the label including the formatted Vietnamese money
//        String totalAmountText = Common.formatVietnameseMoney(hoadon.getTongTien());
//        jLabelTenSP7.setText(totalAmountText);
//        // Calculate the preferred width of the label based on the text width
//        FontMetrics metrics = jLabelTenSP7.getFontMetrics(jLabelTenSP7.getFont());
//        int textWidth = metrics.stringWidth(totalAmountText);
//
//        // Set the preferred size of the label to match the width of the text with a maximum width
//        int maxWidth = 100000; // Set your desired maximum width here     
//        int preferredWidth = Math.max(textWidth, maxWidth);
//        jLabelTenSP7.setPreferredSize(new java.awt.Dimension(preferredWidth, jLabelTenSP7.getPreferredSize().height));
//
//    }
    private JPanel createPanel_CTHD() {
        JPanel panel = new JPanel();
        HoaDonChiTietDAO bus = null;
        NonDAO spbus = null;

        try {
            spbus = new NonDAO();
        } catch (Exception ex) {
            Logger.getLogger(XuatHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            bus = new HoaDonChiTietDAO();
        } catch (Exception ex) {
            Logger.getLogger(XuatHoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }

        panel.setLayout(null);
        int iNumbSP = 0;
        int toadox = 0, toadoy = 0;
        ArrayList<HoaDonChiTiet> list = (ArrayList<HoaDonChiTiet>) bus.selectAll();
        for (int i = 0; i < list.size(); i++) {

            HoaDonChiTiet dto = list.get(i);

            if (hoadon.getMaHoaDon() == (dto.getMaHoaDon())) {
                Non sp = spbus.selectById(dto.getMaNon());
                int km = sp.getGiamGia();
                JPanel p = createPanel_SP(dto, sp, km);
                iNumbSP++;
                p.setBounds(toadox, toadoy, 1050, 30);
                panel.add(p);
                toadoy += 30;
            }
        }

        panel.setSize(1050, 30 * iNumbSP);
        String totalAmountText = Common.formatVietnameseMoney(hoadon.getTongTien());
        lblTongTien.setText("Tổng tiền: " + totalAmountText);
        pnlHDCT.add(panel);
//        jPanel3.setSize(1050, 30 * iNumbSP);
        return panel;
    }

    private JPanel createPanel_SP(HoaDonChiTiet hdct, Non sp, double giamGia) {
        JPanel panel = new JPanel();
        column = new JLabel[4];
        column[0] = new JLabel();
        column[1] = new JLabel();
        column[2] = new JLabel();
        column[3] = new JLabel();
//        column[4] = new JLabel();

        panel.setLayout(null);

        column[0].setText(sp.getTenNon());
        column[1].setText(String.valueOf(hdct.getSoLuong()));
        column[2].setText(Common.formatVietnameseMoney(hdct.getGia()));
//        column[3].setText(giamGia + "%");

        column[3].setText(Common.formatVietnameseMoney(hdct.getGia() * hdct.getSoLuong()));

        column[0].setBounds(0, 0, 270, 30);
        column[1].setBounds(50, 0, 100, 30);
        column[2].setBounds(100, 0, 150, 30);
        column[3].setBounds(200, 0, 100, 30);
//        column[4].setBounds(200, 0, 200, 30);

        column[0].setFont(new Font("Arial", Font.PLAIN, 10));
        column[1].setFont(new Font("Arial", Font.PLAIN, 10));
        column[2].setFont(new Font("Arial", Font.PLAIN, 10));
        column[3].setFont(new Font("Arial", Font.PLAIN, 10));
//        column[4].setFont(new Font("Arial", Font.PLAIN, 10));

        column[0].setBackground(new Color(204, 204, 204));
        column[1].setBackground(new Color(204, 204, 204));
        column[2].setBackground(new Color(204, 204, 204));
        column[3].setBackground(new Color(204, 204, 204));
//        column[4].setBackground(new Color(255, 255, 255));

        panel.add(column[0]);
        panel.add(column[1]);
        panel.add(column[2]);
        panel.add(column[3]);
//        panel.add(column[4]);

        panel.setBackground(new Color(204, 204, 204));
        return panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_ = new javax.swing.JLabel();
        lblNhanVien = new javax.swing.JLabel();
        lblKhachHang = new javax.swing.JLabel();
        lblThoiGian = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlHDCT = new javax.swing.JPanel();
        lblTongTien = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        lblSDTKhachHang = new javax.swing.JLabel();
        pnlControl = new javax.swing.JPanel();
        btnIn = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        lblHinhAnh = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("HOÁ ĐƠN");

        lbl_.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_.setText("----------------------------------------------------------------------------------------------------------------------------------------------------");

        lblNhanVien.setText("Nhân viên:");

        lblKhachHang.setText("Khách hàng:");

        lblThoiGian.setText("Ngày bán:");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi tiết hóa đơn"));

        jLabel2.setText("Tên");

        jLabel3.setText("Số lượng");

        jLabel4.setText("Giá");

        jLabel5.setText("Thành tiền");

        javax.swing.GroupLayout pnlHDCTLayout = new javax.swing.GroupLayout(pnlHDCT);
        pnlHDCT.setLayout(pnlHDCTLayout);
        pnlHDCTLayout.setHorizontalGroup(
            pnlHDCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlHDCTLayout.setVerticalGroup(
            pnlHDCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 244, Short.MAX_VALUE)
        );

        lblTongTien.setText("Tổng tiền: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addComponent(pnlHDCT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTongTien)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        lblMaHoaDon.setText("Mã hóa đơn:");

        lblSDTKhachHang.setText("Số điện thoại: ");

        pnlControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnIn.setText("Thoát");
        btnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInActionPerformed(evt);
            }
        });

        btnThoat.setText("In");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlControlLayout = new javax.swing.GroupLayout(pnlControl);
        pnlControl.setLayout(pnlControlLayout);
        pnlControlLayout.setHorizontalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThoat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIn)
                .addContainerGap())
        );
        pnlControlLayout.setVerticalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIn)
                    .addComponent(btnThoat))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblHinhAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon1/Create.png"))); // NOI18N
        lblHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel6.setText("Trạng thái: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lblSDTKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pnlControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 27, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThoiGian)
                    .addComponent(lblMaHoaDon))
                .addGap(2, 2, 2)
                .addComponent(lblNhanVien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTrangThai))
                .addGap(3, 3, 3)
                .addComponent(lbl_, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSDTKhachHang)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnInActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here: jPanelControl.setVisible(false);
        String ten = "./images/hoadon/"
                + hoadon.getNgayTao() + "_HD" + hoadon.getMaHoaDon() + ".png";
        this.pnlControl.setVisible(false);
        try {
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            this.paint(graphics2D);
            ImageIO.write(image, "png", new File(ten));
        } catch (Exception exception) {
            MsgBox.showErrorDialog(this, "Có lỗi xảy ra khi xuất hóa đơn", "Lỗi");
            return;
        }
        new Printer2().printPage(ten);
//        HoaDonDao hoaDonDao = null;
//        HoaDon hoaDon;
//        try {
//            hoaDonDao = new HoaDonDao();
//        } catch (Exception ex) {
//            Logger.getLogger(InHoaDon.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        hoaDon = hoaDonDao.selectById(hoadon.getMaHoaDon());
//        hoaDon.setImage(ten);
//        hoaDon.setTrangThai("Đã thanh toán");
//        try {
//            hoaDonDao.update(hoaDon);
//        } catch (Exception ex) {
//            Logger.getLogger(InHoaDon.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_btnThoatActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(XuatHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(XuatHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(XuatHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(XuatHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
        HoaDonDao hddao = new HoaDonDao();
        HoaDon hd = hddao.selectById(9);
        new XuatHoaDon(hd).setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIn;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblSDTKhachHang;
    private javax.swing.JLabel lblThoiGian;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JLabel lbl_;
    private javax.swing.JPanel pnlControl;
    private javax.swing.JPanel pnlHDCT;
    // End of variables declaration//GEN-END:variables
}
