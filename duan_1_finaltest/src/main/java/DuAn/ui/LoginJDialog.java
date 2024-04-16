package DuAn.ui;

import DuAn.constant.Constant;
import DuAn.dao.NhanVienDAO;
import DuAn.entity.NhanVien;
import DuAn.utils.Auth;
import DuAn.utils.Common;
import DuAn.utils.MsgBox;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.Border;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author dinhh
 */
public class LoginJDialog extends javax.swing.JFrame {

    public LoginJDialog() {
//        (new MainShopBanNon()).setVisible(true);

        if (Auth.first) {
            new ChaoJDialog(this, true).setVisible(true);
            Auth.first = false;
        }
        NhanVienDAO nvdao = new NhanVienDAO();
        NhanVien nv = nvdao.selectById(1);
        if (nv == null) {
            nv = new NhanVien();
            nv.setMatKhau("1234");
            nv.setHoTen("admin");
            nv.setEmail("huydqpc07859@fpt.edu.vn");
            nv.setSdt("0912341234");
            nv.setVaiTro(true);

            nvdao.insert(nv);
        }

        initComponents();
        setLocationRelativeTo(null);
        this.setVisible(true);
        txtTenTaiKhoan.requestFocus();
    }

    NhanVienDAO dao = new NhanVienDAO();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txtTenTaiKhoan = new javax.swing.JTextField();
        btnDangNhap = new javax.swing.JButton();
        lblLoiTen = new javax.swing.JLabel();
        lblLoiMatKhau = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        lblTitle = new javax.swing.JLabel();
        lblForgetPassword = new javax.swing.JLabel();
        btnKetThuc = new javax.swing.JButton();
        chkShowpass = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 153));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));

        txtTenTaiKhoan.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên đăng nhập"));
        txtTenTaiKhoan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenTaiKhoanKeyPressed(evt);
            }
        });

        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDangNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/login.png"))); // NOI18N
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });

        lblLoiTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLoiTen.setForeground(new java.awt.Color(255, 51, 51));
        lblLoiTen.setText("            ");

        lblLoiMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLoiMatKhau.setForeground(new java.awt.Color(255, 51, 51));
        lblLoiMatKhau.setText("               ");

        txtMatKhau.setBorder(javax.swing.BorderFactory.createTitledBorder("Mật khẩu"));
        txtMatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMatKhauKeyPressed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/school.png"))); // NOI18N
        lblTitle.setText("Đăng Nhập");

        lblForgetPassword.setForeground(new java.awt.Color(51, 102, 255));
        lblForgetPassword.setText("Quên mật khẩu?");
        lblForgetPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblForgetPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForgetPasswordMouseClicked(evt);
            }
        });

        btnKetThuc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKetThuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/icon/logout.png"))); // NOI18N
        btnKetThuc.setText("Kết thúc");
        btnKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetThucActionPerformed(evt);
            }
        });

        chkShowpass.setBackground(new java.awt.Color(255, 255, 255));
        chkShowpass.setText("Hiển thị mật khẩu?");
        chkShowpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowpassActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/images/bg.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblLoiTen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(btnDangNhap)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnKetThuc))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(chkShowpass)
                                    .addGap(66, 66, 66)
                                    .addComponent(lblForgetPassword))
                                .addComponent(lblLoiMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 22, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoiTen)
                .addGap(12, 12, 12)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoiMatKhau)
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkShowpass)
                    .addComponent(lblForgetPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        // TODO add your handling code here:
        dangNhap();
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void btnKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetThucActionPerformed
        // TODO add your handling code here:
        ketThuc();
    }//GEN-LAST:event_btnKetThucActionPerformed

    private void chkShowpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowpassActionPerformed
        //show hoặc ẩn  password
        if (chkShowpass.isSelected()) {
            txtMatKhau.setEchoChar((char) 0);
        } else {
            txtMatKhau.setEchoChar('*');
        }
    }//GEN-LAST:event_chkShowpassActionPerformed

    private void txtTenTaiKhoanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenTaiKhoanKeyPressed
        // TODO add your handling code here:
        lblLoiTen.setText("");
        Border GRAY_BORDER = Constant.getGrayBorder("Tên đăng nhập");

        txtTenTaiKhoan.setBorder(GRAY_BORDER);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dangNhap();
        }
    }//GEN-LAST:event_txtTenTaiKhoanKeyPressed

    private void txtMatKhauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatKhauKeyPressed
        // TODO add your handling code here:
        lblLoiMatKhau.setText("");
        Border GRAY_BORDER = Constant.getGrayBorder("Mật khẩu");

        txtMatKhau.setBorder(GRAY_BORDER);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dangNhap();
        }
    }//GEN-LAST:event_txtMatKhauKeyPressed

    private void lblForgetPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgetPasswordMouseClicked
        // TODO add your handling code here:
        this.dispose();
        new QuenMatKhauJDialog();
    }//GEN-LAST:event_lblForgetPasswordMouseClicked
    public void kiemTra() {
        if (txtTenTaiKhoan.getText().equals("")) {
            lblLoiTen.setText("Tên đăng nhập không được bỏ trống");
        } else {
            lblLoiTen.setText(" ");
        }
        if (txtMatKhau.getText().equals("")) {
            lblLoiMatKhau.setText("Mật khẩu không được bỏ trống");
        } else {
            lblLoiMatKhau.setText(" ");
        }
    }

    public boolean validateLogin() {
        String username = txtTenTaiKhoan.getText();
        String password = new String(txtMatKhau.getPassword());
        String error = "";

        if (password.isBlank()) {
            error += "\nMật khẩu không được để trống";
            txtMatKhau.setBorder(borderMatKhau);
            txtMatKhau.requestFocus();
        }

        if (username.isBlank()) {
            error += "Tài khoản không được để trống";
            txtTenTaiKhoan.setBorder(borderTenDangNhap);
            txtTenTaiKhoan.requestFocus();
        }

        if (!error.isBlank()) {
            MsgBox.showErrorDialog(this, error, "Lỗi");
            return false;
        }

        return true;
    }
    Border borderTenDangNhap = Constant.getRedBorder("Tên đăng nhập");
    Border borderMatKhau = Constant.getRedBorder("Mật khẩu");

    public void dangNhap() {
        if (!validateLogin()) {
            return;
        }
        if (txtTenTaiKhoan.getText().length() < 3) {
            MsgBox.showErrorDialog(this, "Thông tin đăng nhập không chính xác!", "Lỗi");
            txtMatKhau.setBorder(borderMatKhau);
            txtTenTaiKhoan.setBorder(borderTenDangNhap);
            return;
        }
        if (!this.txtTenTaiKhoan.getText().substring(0, 2).equals("NV")) {
            MsgBox.showErrorDialog(this, "Thông tin đăng nhập không chính xác!", "Lỗi");
            txtMatKhau.setBorder(borderMatKhau);
            txtTenTaiKhoan.setBorder(borderTenDangNhap);
            return;
        }
        String maNV = txtTenTaiKhoan.getText().substring(2);
        String matKhau = new String(txtMatKhau.getPassword());

        try {
            NhanVien nv = dao.selectById(Integer.parseInt(maNV));
           
            if (nv == null) {
                kiemTra();
                MsgBox.showErrorDialog(this, "Tài khoản hoặc mật khẩu không chính xác!", "Lỗi");
                txtTenTaiKhoan.requestFocus();
                txtMatKhau.setBorder(borderMatKhau);
                txtTenTaiKhoan.setBorder(borderTenDangNhap);
            } else if (!BCrypt.checkpw(matKhau, nv.getMatKhau())) {
                kiemTra();
                MsgBox.showErrorDialog(this, "Tài khoản hoặc mật khẩu không chính xác!", "Lỗi");
                txtTenTaiKhoan.requestFocus();
                txtMatKhau.setBorder(borderMatKhau);
                txtTenTaiKhoan.setBorder(borderTenDangNhap);
            } else {
                Auth.user = nv;
                if (Auth.user.isDeleted()) {
                    MsgBox.showMessageDialog(this,
                            "Tài khoản này đã bị khóa");
                    return;
                }
                MsgBox.showMessageDialog(this,
                        "Đăng nhập thành công !\nNgười dùng " + "'NV" + Auth.user.getMaNV() + "'");

                new MainShopBanNon().setVisible(true);
                this.dispose();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            MsgBox.showErrorDialog(this, "Thông tin đăng nhập không chính xác!", "Lỗi");
            txtMatKhau.setBorder(borderMatKhau);
            txtTenTaiKhoan.setBorder(borderTenDangNhap);
        }

    }

    public void ketThuc() {
        if (MsgBox.showConfirmDialog(this, "Bạn có muốn kết thúc ứng dụng")) {
            System.exit(0);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//          try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainShopBanNon.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainShopBanNon.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainShopBanNon.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainShopBanNon.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JButton btnKetThuc;
    private javax.swing.JCheckBox chkShowpass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblForgetPassword;
    private javax.swing.JLabel lblLoiMatKhau;
    private javax.swing.JLabel lblLoiTen;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
