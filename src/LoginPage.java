
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class LoginPage extends javax.swing.JFrame {
    
    // =========== Global SQL Database ========= //
    private static final String user = "root";
    private static final String pass = "";
    private static final String url = "jdbc:mysql://localhost:3306/student_management_system "; 
    
    Connection conn1 = null;  
    Connection conn2 = null;    
    PreparedStatement pstUsername = null;  
    PreparedStatement pstPassword = null;  
    ResultSet username = null;
    ResultSet password = null;
    
    
    

   //public static String userName = "pciu";
   //public static String passWord = "123";
   public int counter=0;
   

    
    public LoginPage() {
        initComponents();       
    
   
    }
    
    public boolean loginChecker(String usernameInput , String passInput) throws ClassNotFoundException, SQLException
    {
        String userQuery = "SELECT username FROM user WHERE username =?";
        String passQuery = "SELECT password FROM user WHERE password =?";
        boolean ret = false;
        
        
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn1 = DriverManager.getConnection(url,user,pass);
            conn2 = DriverManager.getConnection(url,user,pass);
            pstUsername = conn1.prepareStatement(userQuery); 
            pstUsername.setString(1, usernameInput);
            pstPassword = conn2.prepareStatement(passQuery); 
            pstPassword.setString(1, passInput);
            
            
            
            
            username = pstUsername.executeQuery();
            password = pstPassword.executeQuery();
            
            
            if(username.next()&& password.next())
            {
                if(username.getString("username").equals(usernameInput)  && password.getString("password").equals(passInput))
                {
                    ret = true;
                    
                }
            }
            else{
                ret = false;
                
            }
            
            
        
        return ret;
       
    }
    
    
    
    
    
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HeaderPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BodyPane = new javax.swing.JPanel();
        jtxtUserName = new javax.swing.JTextField();
        jlabelUserName = new javax.swing.JLabel();
        jlabelUserName1 = new javax.swing.JLabel();
        jlabelUserName2 = new javax.swing.JLabel();
        jbtnLogin = new javax.swing.JButton();
        jbtnReset = new javax.swing.JButton();
        jbtnExit = new javax.swing.JButton();
        jtxtPassWord = new javax.swing.JPasswordField();
        jrbtnShowPassword = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Student Mangement System"); // NOI18N
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HeaderPane.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Student Management System ");

        javax.swing.GroupLayout HeaderPaneLayout = new javax.swing.GroupLayout(HeaderPane);
        HeaderPane.setLayout(HeaderPaneLayout);
        HeaderPaneLayout.setHorizontalGroup(
            HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );
        HeaderPaneLayout.setVerticalGroup(
            HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(HeaderPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 60));

        BodyPane.setBackground(new java.awt.Color(255, 255, 255));
        BodyPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 5));

        jtxtUserName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtUserName.setForeground(new java.awt.Color(0, 153, 153));
        jtxtUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtUserNameActionPerformed(evt);
            }
        });

        jlabelUserName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlabelUserName.setForeground(new java.awt.Color(0, 153, 153));
        jlabelUserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabelUserName.setText("UserName");

        jlabelUserName1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlabelUserName1.setForeground(new java.awt.Color(0, 153, 153));
        jlabelUserName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabelUserName1.setText("PassWord");

        jlabelUserName2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlabelUserName2.setForeground(new java.awt.Color(0, 153, 153));
        jlabelUserName2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabelUserName2.setText("LOGIN");

        jbtnLogin.setBackground(new java.awt.Color(0, 153, 153));
        jbtnLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbtnLogin.setForeground(new java.awt.Color(255, 255, 255));
        jbtnLogin.setText("LOGIN");
        jbtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLoginActionPerformed(evt);
            }
        });

        jbtnReset.setBackground(new java.awt.Color(0, 153, 153));
        jbtnReset.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbtnReset.setForeground(new java.awt.Color(255, 255, 255));
        jbtnReset.setText("RESET");
        jbtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnResetActionPerformed(evt);
            }
        });

        jbtnExit.setBackground(new java.awt.Color(0, 153, 153));
        jbtnExit.setForeground(new java.awt.Color(255, 255, 255));
        jbtnExit.setText("EXIT");
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });

        jtxtPassWord.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtxtPassWord.setForeground(new java.awt.Color(0, 153, 153));
        jtxtPassWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtPassWordActionPerformed(evt);
            }
        });

        jrbtnShowPassword.setForeground(new java.awt.Color(0, 153, 153));
        jrbtnShowPassword.setText("Show Password");
        jrbtnShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbtnShowPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BodyPaneLayout = new javax.swing.GroupLayout(BodyPane);
        BodyPane.setLayout(BodyPaneLayout);
        BodyPaneLayout.setHorizontalGroup(
            BodyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlabelUserName2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BodyPaneLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(BodyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlabelUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(jlabelUserName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(BodyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtUserName)
                    .addComponent(jtxtPassWord)
                    .addGroup(BodyPaneLayout.createSequentialGroup()
                        .addGroup(BodyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jrbtnShowPassword)
                            .addGroup(BodyPaneLayout.createSequentialGroup()
                                .addComponent(jbtnLogin)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        BodyPaneLayout.setVerticalGroup(
            BodyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BodyPaneLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jlabelUserName2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(BodyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlabelUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BodyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlabelUserName1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jtxtPassWord))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jrbtnShowPassword)
                .addGap(18, 18, 18)
                .addGroup(BodyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        getContentPane().add(BodyPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 500, 250));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtUserNameActionPerformed
        jtxtPassWord.requestFocus();
    }//GEN-LAST:event_jtxtUserNameActionPerformed

    private void jbtnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnResetActionPerformed
       jtxtUserName.setText("");
       jtxtPassWord.setText("");
       jrbtnShowPassword.setSelected(false);
    }//GEN-LAST:event_jbtnResetActionPerformed

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
         if(JOptionPane.showConfirmDialog(null,"Are you want to exit?", "WARNING!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                        {
                             System.exit(0);
                        }
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jbtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoginActionPerformed
       if(jtxtUserName.getText().length()!= 0 && jtxtPassWord.getText().length()!= 0)
        {
            String user = jtxtUserName.getText();
            String pass = jtxtPassWord.getText();
            try {
            boolean f = loginChecker(user,pass);
            
            if(f==true)
            {
                            
                dispose();
                HomePage home = new HomePage();
                home.setVisible(true);
                
            }
            else{
                
                counter++;
                if(counter==4)
                {
                    JOptionPane.showMessageDialog(this, "You have blocked!");
                    System.exit(0);
                }
                
                int attempt = 4-counter;
                
                JOptionPane.showMessageDialog(this,  "Invalid UserName or Password!",attempt+" Attempt Remaining!",0);
                
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }           
  
        }
        else if(jtxtUserName.getText().length()== 0 || jtxtPassWord.getText().length()== 0){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            jtxtUserName.requestFocus();
        }
        
 
 
            
            
    }//GEN-LAST:event_jbtnLoginActionPerformed

    private void jtxtPassWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtPassWordActionPerformed
        if(jtxtUserName.getText().length()!= 0 && jtxtPassWord.getText().length()!= 0)
        {
            String user = jtxtUserName.getText();
            String pass = jtxtPassWord.getText();
            try {
            boolean f = loginChecker(user,pass);
            
            if(f==true)
            {
                            
                dispose();
                HomePage home = new HomePage();
                home.setVisible(true);
                
            }
            else{
                
                counter++;
                if(counter==4)
                {
                    JOptionPane.showMessageDialog(this, "You have blocked!");
                    System.exit(0);
                }
                
                int attempt = 4-counter;
                
                JOptionPane.showMessageDialog(this,  "Invalid UserName or Password!",attempt+" Attempt Remaining!",0);
                
                
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }           
  
        }
        else if(jtxtUserName.getText().length()== 0 || jtxtPassWord.getText().length()== 0){
            JOptionPane.showMessageDialog(this, "One or more fields are empty");
            jtxtUserName.requestFocus();
        }
        
 
            
    }//GEN-LAST:event_jtxtPassWordActionPerformed

    private void jrbtnShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbtnShowPasswordActionPerformed
        if(jrbtnShowPassword.isSelected())
        {
            
            jtxtPassWord.setEchoChar((char)0);
            
            
        }
        else if(!jrbtnShowPassword.isSelected())
        {
           jtxtPassWord.setEchoChar('*');
        }
        
        
        
          
        
    }//GEN-LAST:event_jrbtnShowPasswordActionPerformed

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
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BodyPane;
    private javax.swing.JPanel HeaderPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnLogin;
    private javax.swing.JButton jbtnReset;
    private javax.swing.JLabel jlabelUserName;
    private javax.swing.JLabel jlabelUserName1;
    private javax.swing.JLabel jlabelUserName2;
    private javax.swing.JRadioButton jrbtnShowPassword;
    private javax.swing.JPasswordField jtxtPassWord;
    private javax.swing.JTextField jtxtUserName;
    // End of variables declaration//GEN-END:variables
}
