/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuAn.ui;

/**
 *
 * @author dinhh
 */
public class ChaoJDialog extends javax.swing.JDialog {

    /**
     */
    public ChaoJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Cửa Sổ Chào");
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLogo = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        lblStatus = new javax.swing.JLabel();
        lblLogo1 = new javax.swing.JLabel();

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/images/fpl.jpg"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cửa Sổ Chào");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        progressBar.setToolTipText("");
        progressBar.setStringPainted(true);
        getContentPane().add(progressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 630, 29));

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 51, 51));
        lblStatus.setText("Khởi động ứng dụng ....");
        getContentPane().add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 160, 16));

        lblLogo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DuAn/images/fpl.jpg"))); // NOI18N
        getContentPane().add(lblLogo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ChaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChaoJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChaoJDialog dialog = new ChaoJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
 
    // khoa
    public void init() {
        setLocationRelativeTo(null);
        Thread t = new Thread() {
            int i = -1;

            @Override
            public void run() {
                while (true) {
                    try {
                        i++;
                        progressBar.setValue(i);
                        if (i == 20) {
                            lblStatus.setText("Đang khởi tạo cái modun...");
                        }
                        if (i == 50) {
                            lblStatus.setText("Đang kết nối CSDL...");
                        }
                        if (i == 70) {
                            lblStatus.setText("Chuẩn bị vào chương trình...");
                        }
                        if (i == 90) {
                            lblStatus.setText("Thành công !");
                        }
                        if (i == 100) {
                            ChaoJDialog.this.dispose();
                            break;
                        }
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            }
        };
        t.start();

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblLogo1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
