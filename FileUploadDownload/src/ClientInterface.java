
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Damien
 */
public class ClientInterface extends javax.swing.JFrame {

    ClientHelper helper;

    /**
     * Creates new form ClientInterface
     */
    public ClientInterface() {
        initComponents();
        loggedOut();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLogin = new javax.swing.JButton();
        tbxUsername = new javax.swing.JTextField();
        btnLogout = new javax.swing.JButton();
        fileChoser = new javax.swing.JFileChooser();
        btnDownload = new javax.swing.JButton();
        tbxDownloadPath = new javax.swing.JTextField();
        tbxFilename = new javax.swing.JTextField();
        lblLoginResponse = new javax.swing.JLabel();
        lblDownloadResponse = new javax.swing.JLabel();
        lblUploadResponse = new javax.swing.JLabel();
        lblLogoutResponse = new javax.swing.JLabel();
        lblFilename = new javax.swing.JLabel();
        lblDownloadLocation = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 123, 38));
        getContentPane().add(tbxUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 120, 30));

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, -1, -1));

        fileChoser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChoserActionPerformed(evt);
            }
        });
        getContentPane().add(fileChoser, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, -1, -1));

        btnDownload.setText("Download");
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
            }
        });
        getContentPane().add(btnDownload, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 640, 130, 40));
        getContentPane().add(tbxDownloadPath, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 652, 250, 30));
        getContentPane().add(tbxFilename, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 650, 170, 30));
        getContentPane().add(lblLoginResponse, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, 400, 30));
        getContentPane().add(lblDownloadResponse, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 690, 460, 40));
        getContentPane().add(lblUploadResponse, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 580, 260, 30));
        getContentPane().add(lblLogoutResponse, new org.netbeans.lib.awtextra.AbsoluteConstraints(911, 50, 90, 20));

        lblFilename.setText("Filename");
        getContentPane().add(lblFilename, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 630, -1, -1));

        lblDownloadLocation.setText("Download Path");
        getContentPane().add(lblDownloadLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 630, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed

        String username = tbxUsername.getText();
        System.out.print(username);

        if (username.equals("")) {
            lblLoginResponse.setText("A username is required");
        } else {
            try {
                helper = new ClientHelper();
                String response = helper.login(username);

                String responseCode = response.substring(0, 3);

                if (responseCode.equals("200") || responseCode.equals("201")) {
                    loggedIn();
                } else {
                    lblLoginResponse.setText("");
                    lblLoginResponse.setText("Unable to Login: " + response);
                }

            } catch (Exception e) {
                System.out.print(e);
            }
        }

    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        String username = tbxUsername.getText();
        System.out.print(username);
        try {
            helper = new ClientHelper();
            String response = helper.logout();
            if(response.substring(0,3).equals("200")){
                loggedOut();
                helper.done();
            }
            else{
                lblLogoutResponse.setText(response);
            }
            
        } catch (Exception e) {
            System.out.print(e);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void fileChoserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChoserActionPerformed
        
        lblDownloadResponse.setText("");
        lblUploadResponse.setText("");
        
        String path = fileChoser.getSelectedFile().getAbsolutePath();
        String fileName = fileChoser.getSelectedFile().getName();
        
        try {
            helper = new ClientHelper();
            String response = helper.upload(fileName, path);
            if (response.substring(0, 3).equals("200")) {
                lblUploadResponse.setText("File successfully uploaded");
            } else {
                lblUploadResponse.setText(response);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }//GEN-LAST:event_fileChoserActionPerformed

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        
        lblDownloadResponse.setText("");
        lblUploadResponse.setText("");
                
        try {
            helper = new ClientHelper();
            String response = helper.download(tbxFilename.getText(), tbxDownloadPath.getText());

            if (response.substring(0, 3).equals("200")) {
                lblDownloadResponse.setText("File successfully downloaded");
            } else {
                lblDownloadResponse.setText(response);
            }
        } catch (Exception e) {
            System.out.print(e);
        }

    }//GEN-LAST:event_btnDownloadActionPerformed

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
            java.util.logging.Logger.getLogger(ClientInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientInterface().setVisible(true);
            }
        });
    }

    private void loggedIn() {
        fileChoser.setVisible(true);
        btnLogout.setVisible(true);
        btnDownload.setVisible(true);
        tbxDownloadPath.setVisible(true);
        tbxFilename.setVisible(true);
        btnLogin.setVisible(false);
        tbxUsername.setVisible(false);
        tbxUsername.setText("");
        lblLoginResponse.setText("");
        lblDownloadLocation.setVisible(true);
        lblFilename.setVisible(true);
    }

    private void loggedOut() {
        fileChoser.setVisible(false);
        btnLogout.setVisible(false);
        btnDownload.setVisible(false);
        tbxDownloadPath.setVisible(false);
        tbxFilename.setVisible(false);
        btnLogin.setVisible(true);
        tbxUsername.setVisible(true);
        lblDownloadResponse.setText("");
        lblUploadResponse.setText("");
        lblDownloadLocation.setVisible(false);
        lblFilename.setVisible(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDownload;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLogout;
    private javax.swing.JFileChooser fileChoser;
    private javax.swing.JLabel lblDownloadLocation;
    private javax.swing.JLabel lblDownloadResponse;
    private javax.swing.JLabel lblFilename;
    private javax.swing.JLabel lblLoginResponse;
    private javax.swing.JLabel lblLogoutResponse;
    private javax.swing.JLabel lblUploadResponse;
    private javax.swing.JTextField tbxDownloadPath;
    private javax.swing.JTextField tbxFilename;
    private javax.swing.JTextField tbxUsername;
    // End of variables declaration//GEN-END:variables
}
