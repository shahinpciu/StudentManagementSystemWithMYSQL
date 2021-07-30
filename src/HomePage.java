
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.lang.String;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;


public class HomePage extends javax.swing.JFrame {
    
    //=========== GLOBAL SQL ============//
    
    private static final String user = "root";
    private static final String pass = "";
    private static final String url = "jdbc:mysql://localhost:3306/student_management_system ";  
    
    // private static final String user = "shahin";
    //private static final String pass = "shahin@123";
    //private static final String url = "jdbc:mysql://server113.web-hosting.com:2083/cpsess2660223334/3rdparty/phpMyAdmin/sql.php:3306/parvpgta_student_management_system_01";  
    
    
    public int phoneDCounter = 0;
   
    Connection conn = null; 
    Connection conn2 = null;
    PreparedStatement pst = null;
    PreparedStatement pst2 = null;
    ResultSet rs = null;
    
     // =============**** ==========//
    
    

    //For Student_List
    public static boolean shouldAdd = false;
    public static boolean shouldUpdate = true;
    public static boolean shouldDelete = true;
    public static int totalStudent =0;
    
    //for Student_Info
    public static boolean shouldAdd2 = true;
    public static boolean shouldUpdate2 = true;
    public static boolean shouldDelete2 = true;

    
    // ========== For Student_List ==========
    public void addToStudentListDB()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);
            String addQuery = "INSERT INTO student_list(ID,NAME,PHONE,DEPT)VALUES(?,?,?,?)";
            pst = conn.prepareStatement(addQuery);
            
            pst.setString(1,jtxtId.getText());
            pst.setString(2,jtxtName.getText());
            pst.setString(3,jtxtPhone.getText());
            pst.setString(4,jtxtDept.getText());
            
            pst.executeUpdate();            
            JOptionPane.showMessageDialog(this, "New Student Added"); 
            shouldUpdate=false;
            showStudentListDB();
            showIdComboBox();
                                    
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
               //JOptionPane.showMessageDialog(this, "No Internet Connection!");
               //System.exit(0);

        }
        
        
    }
    
    public void updateStudentListDB()
    {
        if(shouldUpdate)             
         {
            try 
            {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);
            String updateId = jtxtId.getText();
            String updateQuery = "UPDATE student_list SET ID = ?,NAME = ?,PHONE = ?,DEPT = ? WHERE ID ="+updateId;
            pst = conn.prepareStatement(updateQuery);
            
            pst.setString(1,jtxtId.getText());
            pst.setString(2,jtxtName.getText());
            pst.setString(3,jtxtPhone.getText());
            pst.setString(4,jtxtDept.getText());  
            
            pst.executeUpdate();            
            JOptionPane.showMessageDialog(this, "Data Has Been Updated");
            jtxtId.enable();
            shouldUpdate=false;
            
            showStudentListDB();
            showIdComboBox();
            
            pst.close();
            conn.close();
            
            
            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
         
          }      
         
        
    }
    
    public void deleteStudentListDB()
    {
        
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn = DriverManager.getConnection(url,user,pass);
                        conn2 = DriverManager.getConnection(url,user,pass);
                        String deleteQuery2 = "DELETE FROM student_info WHERE ID ="+jtxtId.getText().toString();
                        String deleteQuery = "DELETE FROM student_list WHERE ID ="+jtxtId.getText().toString();
                        pst = conn.prepareStatement(deleteQuery); 
                        pst2 = conn2.prepareStatement(deleteQuery2);
            
                        int deleteItem = JOptionPane.showConfirmDialog(null,"Are you confirm to delete?", "WARNING!",JOptionPane.YES_NO_OPTION );
                        if(deleteItem ==JOptionPane.YES_OPTION)
                        {
                            
                            pst2.executeUpdate(); 
                            pst.executeUpdate();
                                                                                 
                            JOptionPane.showMessageDialog(this, "Record Deleted!"); 
                            shouldUpdate=false;
                            showStudentListDB();
                            shouldDelete = false;
                        }
                        else{
                            clearDisplay();
                            shouldDelete = false;
                            shouldUpdate=false;
                        }
                        
                        shouldUpdate=false;   
                        shouldDelete = false;
                        showIdComboBox();
                        
           
                       
                        
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }             
               
    public void displaySelectedRow()
    {
        DefaultTableModel recordTable = (DefaultTableModel)StudentListTable.getModel();
        int selectedRow = StudentListTable.getSelectedRow();
        
        jtxtId.setText(recordTable.getValueAt(selectedRow,0).toString());
        jtxtId.disable();
        jtxtName.setText(recordTable.getValueAt(selectedRow,1).toString());
        jtxtPhone.setText(recordTable.getValueAt(selectedRow,2).toString());
        jtxtDept.setText(recordTable.getValueAt(selectedRow,3).toString());
    }
    
    public void showStudentListDB()
    {
         int q,i;
        try
        {
            totalStudent = 0;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);
            String selectQuery = "SELECT * FROM student_list";
            pst = conn.prepareStatement(selectQuery);
            
            rs = pst.executeQuery();
            ResultSetMetaData stData = rs.getMetaData();
            q = stData.getColumnCount();
            
            DefaultTableModel recordTable = (DefaultTableModel)StudentListTable.getModel();
            recordTable.setRowCount(0);
            while(rs.next())
            {
                totalStudent++;
                Vector columData = new Vector();
                for(i =1 ; i<=q ; i++)
                {
                    columData.add(rs.getString("ID"));
                    columData.add(rs.getString("NAME"));
                    columData.add(rs.getString("PHONE"));
                    columData.add(rs.getString("DEPT"));
                    
                    
                }
                recordTable.addRow(columData);
            }            
            jlabelTotalStudent.setText(String.valueOf(totalStudent));
                      
                        
                        
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void searchOnStudentListDB(String ID)
    {
        int q,i;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);
            String searchQuery = "SELECT * FROM student_list WHERE ID="+ID;
            pst = conn.prepareStatement(searchQuery);                                  
            rs = pst.executeQuery();        
            
            ResultSetMetaData stData = rs.getMetaData();
            q = stData.getColumnCount();
            
            DefaultTableModel recordTable = (DefaultTableModel)StudentListTable.getModel();
            
            if(rs.next())
            {
                recordTable.setRowCount(0);
                Vector columData = new Vector();
                
                    columData.add(rs.getString("ID"));
                    columData.add(rs.getString("NAME"));
                    columData.add(rs.getString("PHONE"));
                    columData.add(rs.getString("DEPT"));                 
                    
                
                recordTable.addRow(columData);
                //JOptionPane.showMessageDialog(this, "Result Found!"); 
                StudentListTable.selectAll();
                displaySelectedRow();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Data Not Found!"); 
                clearDisplay();
            }
            
            
            
            shouldUpdate=false;            
            showIdComboBox();
                                    
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Data not Found!"); 
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Data not Found!"); 
        }
    }
    
    public boolean checkIdExistOnStudentList(String id)
    {
        boolean c = false;      
               
        
        try {
            String givenID=id;
            
            String existQuery = "SELECT ID FROM student_list WHERE EXISTS (SELECT ID FROM student_list WHERE ID ="+givenID+")";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);           
            pst = conn.prepareStatement(existQuery);
            rs = pst.executeQuery();
            
            if(rs.next())
                return c =true;
            else
                return c =false;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex)
        {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }          
        
        
              return c;  
        
    }

    // =============**** ==========//
    
    
    
    
    // ========== For Student_Info ==========//    
    public void addToStudentInfoDB()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);
            String addQuery = "INSERT INTO student_info(ID,TRIMESTER,CGPA)VALUES(?,?,?)";
            pst = conn.prepareStatement(addQuery);
            
            pst.setString(1,cbID.getItemAt(cbID.getSelectedIndex()));
            //System.out.println("Selected: "+cbID.getItemAt(cbID.getSelectedIndex()));
                  
            pst.setString(2,jtxtTrimester.getText());
            pst.setString(3,jtxtCgpa.getText());            
            
            pst.executeUpdate();  
            
            JOptionPane.showMessageDialog(this, "Student Info Added"); 
            shouldUpdate2=false;
            showStudentInfoDB();
            showIdComboBox();
                                    
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void displaySelectedInfo()
    {
        DefaultTableModel recordTable = (DefaultTableModel)StudentInfoTable.getModel();
        int selectedRow = StudentInfoTable.getSelectedRow();
        
        cbID.setSelectedItem(recordTable.getValueAt(selectedRow,0).toString());
        cbID.disable();
        jtxtTrimester.setText(recordTable.getValueAt(selectedRow,1).toString());
        jtxtCgpa.setText(recordTable.getValueAt(selectedRow,2).toString());
    }
    
     public void updateStudentInfoDB()
    {
        if(shouldUpdate2)             
         {
            try 
            {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);
            String updateId = cbID.getItemAt(cbID.getSelectedIndex());
            String updateQuery = "UPDATE student_info SET TRIMESTER = ?,CGPA = ? WHERE ID ="+updateId;
            pst = conn.prepareStatement(updateQuery);
            
            
            pst.setString(1,jtxtTrimester.getText());
            pst.setString(2,jtxtCgpa.getText());
            
            
            pst.executeUpdate();            
            JOptionPane.showMessageDialog(this, "Info Has Been Updated");
            cbID.enable();
            shouldUpdate2=false;
            
            showStudentInfoDB();
            showIdComboBox();
            
            pst.close();
            conn.close();
            
            
            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
         
          }      
         
        
    }
    
     
      public void deleteStudentInfoDB()
    {
        
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn = DriverManager.getConnection(url,user,pass);
                        String deleteQuery = "DELETE FROM student_info WHERE ID ="+cbID.getItemAt(cbID.getSelectedIndex()).toString();
                        pst = conn.prepareStatement(deleteQuery);          
            
                        int deleteItem = JOptionPane.showConfirmDialog(null,"Are you confirm to delete?", "WARNING!",JOptionPane.YES_NO_OPTION );
                        if(deleteItem ==JOptionPane.YES_OPTION)
                        {
                            pst.executeUpdate();            
                            JOptionPane.showMessageDialog(this, "Record Deleted!"); 
                            shouldUpdate2=false;
                            showStudentInfoDB();
                            shouldDelete2 = false;
                        }
                        else{
                            clearDisplay();
                            shouldDelete2 = false;
                            shouldUpdate2=false;
                        }
                        shouldUpdate2=false;   
                        shouldDelete2 = false;
                        showIdComboBox();
                        
           
                       
                        
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }   
     
     
    public void showStudentInfoDB()
    {
         int q,i;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);
            String selectQuery = "SELECT * FROM student_info";
            pst = conn.prepareStatement(selectQuery);
            
            rs = pst.executeQuery();
            ResultSetMetaData stData = rs.getMetaData();
            q = stData.getColumnCount();
            
            DefaultTableModel recordTable = (DefaultTableModel)StudentInfoTable.getModel();
            recordTable.setRowCount(0);
            while(rs.next())
            {
                Vector columData = new Vector();
                for(i =1 ; i<=q ; i++)
                {
                    columData.add(rs.getString("ID"));
                    columData.add(rs.getString("TRIMESTER"));
                    columData.add(rs.getString("CGPA"));   
                }
                recordTable.addRow(columData);
            }      
                   
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showWarningOnInfoExist()
    {
        
        shouldUpdate2 =false;
        shouldDelete2= false;
                
        
            if(checkStudentInfoExist(cbID.getItemAt(cbID.getSelectedIndex())))
            {
                idWarning2.setVisible(true);
                idWarning2.setText("EXIST!");
                shouldAdd2 = false;      
            }
            else {
                idWarning2.setVisible(false);
                shouldAdd2 = true;
            }                           
                
   
    }
    
    public void showIdComboBox()
    {
        int i,q;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);
            String selectQuery = "SELECT ID FROM student_list";
            pst = conn.prepareStatement(selectQuery);            
            rs = pst.executeQuery();
            
            cbID.removeAllItems();
  
            while(rs.next())
            {
                {
                   cbID.addItem(rs.getString("ID"));               
                }
            }    
   
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void searchOnStudentInfoDB(String ID)
    {
        int q,i;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);
            String searchQuery = "SELECT * FROM student_info WHERE ID="+ID;
            pst = conn.prepareStatement(searchQuery);                                  
            rs = pst.executeQuery();        
            
            ResultSetMetaData stData = rs.getMetaData();
            q = stData.getColumnCount();
            
            DefaultTableModel recordTable = (DefaultTableModel)StudentInfoTable.getModel();
            
            if(rs.next())
            {
                recordTable.setRowCount(0);
                Vector columData = new Vector();
                
                    columData.add(rs.getString("ID"));
                    columData.add(rs.getString("TRIMESTER"));
                    columData.add(rs.getString("CGPA"));
                                   
                    
                recordTable.addRow(columData);
                //JOptionPane.showMessageDialog(this, "Result Found!"); 
                StudentInfoTable.selectAll();
                displaySelectedInfo();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Data Not Found!"); 
                clearDisplay();
            }
            
            
            
            shouldUpdate2=false;            
            showIdComboBox();
                                    
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Data not Found!"); 
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Data not Found!"); 
        }
    }
    
    
    public boolean checkStudentInfoExist(String id)
    {
        boolean c = false;      
               
        
        try {
            String givenID=id;
            
            String existQuery = "SELECT ID FROM student_info WHERE EXISTS (SELECT ID FROM student_info WHERE ID ="+givenID+")";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,pass);           
            pst = conn.prepareStatement(existQuery);
            rs = pst.executeQuery();
            
            if(rs.next())
                return c =true;
            else
                return c =false;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex)
        {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }          
        
        
              return c;  
        
    }
    
    // =============**** ==========//
    
    
    public HomePage() {
        initComponents();
        StudentListPage.setVisible(true);
        StudentInfoPage.setVisible(false);
        idWarning.setVisible(false);
        idWarning2.setVisible(false);
        showStudentListDB();
        showStudentInfoDB();
        showIdComboBox();
        searchWarning.setVisible(false);
        
        
        
        
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        StudentListPage = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        HeaderName = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jlabelTotalStudent = new javax.swing.JLabel();
        LeftSideBar = new javax.swing.JPanel();
        jLabelID = new javax.swing.JLabel();
        jtxtId = new javax.swing.JTextField();
        jtxtName = new javax.swing.JTextField();
        jLabelID1 = new javax.swing.JLabel();
        jtxtPhone = new javax.swing.JTextField();
        jLabelID2 = new javax.swing.JLabel();
        jtxtDept = new javax.swing.JTextField();
        jLabelID3 = new javax.swing.JLabel();
        jbtnUpdate = new javax.swing.JButton();
        jbtnAdd = new javax.swing.JButton();
        jbtnClear = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jtxtSearch = new javax.swing.JTextField();
        jbtnSearch = new javax.swing.JButton();
        idWarning = new javax.swing.JLabel();
        jbtnDelete = new javax.swing.JButton();
        searchWarning = new javax.swing.JLabel();
        digitWarningID = new javax.swing.JLabel();
        RightSideBar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        StudentListTable = new javax.swing.JTable();
        StudentInfoPage = new javax.swing.JPanel();
        jPanelHeader2 = new javax.swing.JPanel();
        HeaderName1 = new javax.swing.JLabel();
        LeftSideBar2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbID = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jtxtTrimester = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtCgpa = new javax.swing.JTextField();
        jbtnDelete2 = new javax.swing.JButton();
        jbtnAdd2 = new javax.swing.JButton();
        jbtnUpdate2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jbtnSearch2 = new javax.swing.JButton();
        jtxtSearch2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        idWarning2 = new javax.swing.JLabel();
        jbtnClear2 = new javax.swing.JButton();
        searchWarning2 = new javax.swing.JLabel();
        RightSideBar2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        StudentInfoTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jmenuLogout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1080, 600));
        setMinimumSize(new java.awt.Dimension(1080, 600));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StudentListPage.setBackground(new java.awt.Color(0, 102, 102));
        StudentListPage.setMaximumSize(new java.awt.Dimension(1080, 560));
        StudentListPage.setMinimumSize(new java.awt.Dimension(1080, 560));
        StudentListPage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelHeader.setBackground(new java.awt.Color(0, 153, 153));

        HeaderName.setFont(new java.awt.Font("Lucida Calligraphy", 3, 24)); // NOI18N
        HeaderName.setForeground(new java.awt.Color(255, 255, 255));
        HeaderName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HeaderName.setText("Student List");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total Student:");

        jlabelTotalStudent.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlabelTotalStudent.setForeground(new java.awt.Color(255, 255, 255));
        jlabelTotalStudent.setText("0");

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addGap(430, 430, 430)
                .addComponent(HeaderName, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlabelTotalStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelHeaderLayout.setVerticalGroup(
            jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeaderLayout.createSequentialGroup()
                .addComponent(HeaderName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlabelTotalStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        StudentListPage.add(jPanelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, -1));

        LeftSideBar.setBackground(new java.awt.Color(255, 255, 255));
        LeftSideBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        LeftSideBar.setMaximumSize(new java.awt.Dimension(389, 479));
        LeftSideBar.setMinimumSize(new java.awt.Dimension(389, 479));
        LeftSideBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelID.setForeground(new java.awt.Color(0, 153, 153));
        jLabelID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelID.setText("ID");
        LeftSideBar.add(jLabelID, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 46, 30, -1));

        jtxtId.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jtxtId.setForeground(new java.awt.Color(0, 102, 102));
        jtxtId.setCaretColor(new java.awt.Color(0, 102, 102));
        jtxtId.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jtxtId.setDisabledTextColor(new java.awt.Color(0, 102, 102));
        jtxtId.setDoubleBuffered(true);
        jtxtId.setSelectedTextColor(new java.awt.Color(0, 102, 102));
        jtxtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtIdActionPerformed(evt);
            }
        });
        jtxtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtIdKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtIdKeyTyped(evt);
            }
        });
        LeftSideBar.add(jtxtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 34, 300, 38));

        jtxtName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jtxtName.setForeground(new java.awt.Color(0, 102, 102));
        jtxtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNameActionPerformed(evt);
            }
        });
        LeftSideBar.add(jtxtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 87, 300, 38));

        jLabelID1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelID1.setForeground(new java.awt.Color(0, 153, 153));
        jLabelID1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelID1.setText("NAME");
        LeftSideBar.add(jLabelID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 87, 50, 38));

        jtxtPhone.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jtxtPhone.setForeground(new java.awt.Color(0, 102, 102));
        jtxtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtPhoneActionPerformed(evt);
            }
        });
        jtxtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtPhoneKeyTyped(evt);
            }
        });
        LeftSideBar.add(jtxtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 143, 300, 38));

        jLabelID2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelID2.setForeground(new java.awt.Color(0, 153, 153));
        jLabelID2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelID2.setText("PHONE");
        LeftSideBar.add(jLabelID2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 143, -1, 38));

        jtxtDept.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jtxtDept.setForeground(new java.awt.Color(0, 102, 102));
        jtxtDept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDeptActionPerformed(evt);
            }
        });
        LeftSideBar.add(jtxtDept, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 199, 300, 38));

        jLabelID3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelID3.setForeground(new java.awt.Color(0, 153, 153));
        jLabelID3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelID3.setText("DEPT");
        LeftSideBar.add(jLabelID3, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 200, 44, 38));

        jbtnUpdate.setBackground(new java.awt.Color(0, 153, 153));
        jbtnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jbtnUpdate.setText("UPDATE");
        jbtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateActionPerformed(evt);
            }
        });
        LeftSideBar.add(jbtnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 256, -1, 34));

        jbtnAdd.setBackground(new java.awt.Color(0, 153, 153));
        jbtnAdd.setForeground(new java.awt.Color(255, 255, 255));
        jbtnAdd.setText("ADD");
        jbtnAdd.setBorder(null);
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });
        LeftSideBar.add(jbtnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 256, 60, 34));

        jbtnClear.setBackground(new java.awt.Color(0, 153, 153));
        jbtnClear.setForeground(new java.awt.Color(255, 255, 255));
        jbtnClear.setText("CLEAR");
        jbtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnClearActionPerformed(evt);
            }
        });
        LeftSideBar.add(jbtnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 256, -1, 34));

        jButton10.setBackground(new java.awt.Color(0, 153, 153));
        jButton10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("NEXT>");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        LeftSideBar.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 420, 91, 40));

        jButton12.setBackground(new java.awt.Color(255, 51, 51));
        jButton12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("EXIT");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        LeftSideBar.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, 74, 40));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("SEARCH BY ID");
        LeftSideBar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 316, 110, 40));

        jtxtSearch.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jtxtSearch.setForeground(new java.awt.Color(0, 102, 102));
        jtxtSearch.setToolTipText("Search by ID....");
        jtxtSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtxtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtSearchActionPerformed(evt);
            }
        });
        jtxtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtSearchKeyTyped(evt);
            }
        });
        LeftSideBar.add(jtxtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(142, 316, 270, 40));

        jbtnSearch.setBackground(new java.awt.Color(0, 153, 153));
        jbtnSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtnSearch.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSearch.setText("SEARCH");
        jbtnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSearchActionPerformed(evt);
            }
        });
        LeftSideBar.add(jbtnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 366, -1, 30));

        idWarning.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        idWarning.setForeground(new java.awt.Color(255, 51, 51));
        idWarning.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LeftSideBar.add(idWarning, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 34, 80, 38));

        jbtnDelete.setBackground(new java.awt.Color(0, 153, 153));
        jbtnDelete.setForeground(new java.awt.Color(255, 255, 255));
        jbtnDelete.setText("DELETE");
        jbtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteActionPerformed(evt);
            }
        });
        LeftSideBar.add(jbtnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 256, -1, 34));

        searchWarning.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        searchWarning.setForeground(new java.awt.Color(255, 51, 51));
        searchWarning.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LeftSideBar.add(searchWarning, new org.netbeans.lib.awtextra.AbsoluteConstraints(333, 363, 79, 38));

        digitWarningID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        digitWarningID.setForeground(new java.awt.Color(255, 0, 0));
        digitWarningID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        digitWarningID.setMaximumSize(new java.awt.Dimension(133, 27));
        digitWarningID.setMinimumSize(new java.awt.Dimension(133, 27));
        digitWarningID.setPreferredSize(new java.awt.Dimension(133, 27));
        LeftSideBar.add(digitWarningID, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 90, 30));

        StudentListPage.add(LeftSideBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 68, 480, 479));

        RightSideBar.setBackground(new java.awt.Color(255, 255, 255));
        RightSideBar.setMaximumSize(new java.awt.Dimension(599, 402));
        RightSideBar.setMinimumSize(new java.awt.Dimension(599, 402));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        StudentListTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        StudentListTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StudentListTable.setForeground(new java.awt.Color(0, 102, 102));
        StudentListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "PHONE", "DEPT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        StudentListTable.setGridColor(new java.awt.Color(0, 102, 102));
        StudentListTable.setRowHeight(30);
        StudentListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StudentListTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(StudentListTable);

        javax.swing.GroupLayout RightSideBarLayout = new javax.swing.GroupLayout(RightSideBar);
        RightSideBar.setLayout(RightSideBarLayout);
        RightSideBarLayout.setHorizontalGroup(
            RightSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
        );
        RightSideBarLayout.setVerticalGroup(
            RightSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
        );

        StudentListPage.add(RightSideBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(508, 68, 560, 479));

        getContentPane().add(StudentListPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 560));

        StudentInfoPage.setBackground(new java.awt.Color(0, 102, 102));
        StudentInfoPage.setMaximumSize(new java.awt.Dimension(1080, 560));
        StudentInfoPage.setMinimumSize(new java.awt.Dimension(1080, 560));

        jPanelHeader2.setBackground(new java.awt.Color(0, 153, 153));

        HeaderName1.setFont(new java.awt.Font("Lucida Calligraphy", 3, 24)); // NOI18N
        HeaderName1.setForeground(new java.awt.Color(255, 255, 255));
        HeaderName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HeaderName1.setText("Student Info");

        javax.swing.GroupLayout jPanelHeader2Layout = new javax.swing.GroupLayout(jPanelHeader2);
        jPanelHeader2.setLayout(jPanelHeader2Layout);
        jPanelHeader2Layout.setHorizontalGroup(
            jPanelHeader2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeader2Layout.createSequentialGroup()
                .addGap(430, 430, 430)
                .addComponent(HeaderName1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(465, Short.MAX_VALUE))
        );
        jPanelHeader2Layout.setVerticalGroup(
            jPanelHeader2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderName1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        LeftSideBar2.setBackground(new java.awt.Color(255, 255, 255));
        LeftSideBar2.setMaximumSize(new java.awt.Dimension(389, 479));
        LeftSideBar2.setMinimumSize(new java.awt.Dimension(389, 479));
        LeftSideBar2.setPreferredSize(new java.awt.Dimension(389, 479));
        LeftSideBar2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("TRIMESTER");
        LeftSideBar2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 90, 40));

        cbID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cbID.setForeground(new java.awt.Color(0, 102, 102));
        cbID.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbIDItemStateChanged(evt);
            }
        });
        cbID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbIDMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbIDMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cbIDMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbIDMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cbIDMouseReleased(evt);
            }
        });
        LeftSideBar2.add(cbID, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 330, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("ID");
        LeftSideBar2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 50, 40));

        jtxtTrimester.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jtxtTrimester.setForeground(new java.awt.Color(0, 102, 102));
        jtxtTrimester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtTrimesterActionPerformed(evt);
            }
        });
        jtxtTrimester.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtTrimesterKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtTrimesterKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtTrimesterKeyTyped(evt);
            }
        });
        LeftSideBar2.add(jtxtTrimester, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 330, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("SEARCH BY ID");
        LeftSideBar2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 110, 40));

        jtxtCgpa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jtxtCgpa.setForeground(new java.awt.Color(0, 102, 102));
        LeftSideBar2.add(jtxtCgpa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 330, 40));

        jbtnDelete2.setBackground(new java.awt.Color(0, 153, 153));
        jbtnDelete2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbtnDelete2.setForeground(new java.awt.Color(255, 255, 255));
        jbtnDelete2.setText("DELETE");
        jbtnDelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDelete2ActionPerformed(evt);
            }
        });
        LeftSideBar2.add(jbtnDelete2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, -1, 30));

        jbtnAdd2.setBackground(new java.awt.Color(0, 153, 153));
        jbtnAdd2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbtnAdd2.setForeground(new java.awt.Color(255, 255, 255));
        jbtnAdd2.setText("ADD");
        jbtnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAdd2ActionPerformed(evt);
            }
        });
        LeftSideBar2.add(jbtnAdd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 70, 30));

        jbtnUpdate2.setBackground(new java.awt.Color(0, 153, 153));
        jbtnUpdate2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbtnUpdate2.setForeground(new java.awt.Color(255, 255, 255));
        jbtnUpdate2.setText("UPDATE");
        jbtnUpdate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdate2ActionPerformed(evt);
            }
        });
        LeftSideBar2.add(jbtnUpdate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 90, 30));

        jButton7.setBackground(new java.awt.Color(255, 0, 0));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("EXIT");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        LeftSideBar2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 410, 70, 40));

        jButton8.setBackground(new java.awt.Color(0, 153, 153));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("<BACK");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        LeftSideBar2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, -1, 40));

        jbtnSearch2.setBackground(new java.awt.Color(0, 153, 153));
        jbtnSearch2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtnSearch2.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSearch2.setText("SEARCH");
        jbtnSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSearch2ActionPerformed(evt);
            }
        });
        LeftSideBar2.add(jbtnSearch2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, -1, 30));

        jtxtSearch2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jtxtSearch2.setForeground(new java.awt.Color(0, 102, 102));
        jtxtSearch2.setToolTipText("Search by ID....");
        jtxtSearch2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtxtSearch2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jtxtSearch2.setName(""); // NOI18N
        jtxtSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtSearch2ActionPerformed(evt);
            }
        });
        jtxtSearch2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtSearch2KeyTyped(evt);
            }
        });
        LeftSideBar2.add(jtxtSearch2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 320, 40));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("CGPA");
        LeftSideBar2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 90, 40));

        idWarning2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        idWarning2.setForeground(new java.awt.Color(255, 0, 0));
        idWarning2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LeftSideBar2.add(idWarning2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, -4, 70, 20));

        jbtnClear2.setBackground(new java.awt.Color(0, 153, 153));
        jbtnClear2.setForeground(new java.awt.Color(255, 255, 255));
        jbtnClear2.setText("CLEAR");
        jbtnClear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnClear2ActionPerformed(evt);
            }
        });
        LeftSideBar2.add(jbtnClear2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 70, 30));

        searchWarning2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        searchWarning2.setForeground(new java.awt.Color(255, 51, 51));
        searchWarning2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LeftSideBar2.add(searchWarning2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 350, 90, 30));

        RightSideBar2.setMaximumSize(new java.awt.Dimension(599, 431));
        RightSideBar2.setMinimumSize(new java.awt.Dimension(599, 431));
        RightSideBar2.setPreferredSize(new java.awt.Dimension(599, 431));
        RightSideBar2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setMaximumSize(new java.awt.Dimension(599, 431));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(599, 431));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(599, 431));

        StudentInfoTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        StudentInfoTable.setForeground(new java.awt.Color(0, 102, 102));
        StudentInfoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "TRIMESTER", "CGPA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        StudentInfoTable.setRowHeight(25);
        StudentInfoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StudentInfoTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(StudentInfoTable);

        RightSideBar2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 480));

        javax.swing.GroupLayout StudentInfoPageLayout = new javax.swing.GroupLayout(StudentInfoPage);
        StudentInfoPage.setLayout(StudentInfoPageLayout);
        StudentInfoPageLayout.setHorizontalGroup(
            StudentInfoPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StudentInfoPageLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelHeader2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(StudentInfoPageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LeftSideBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RightSideBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        StudentInfoPageLayout.setVerticalGroup(
            StudentInfoPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentInfoPageLayout.createSequentialGroup()
                .addComponent(jPanelHeader2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(StudentInfoPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RightSideBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LeftSideBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(StudentInfoPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 560));

        jMenuBar1.setBackground(new java.awt.Color(0, 102, 102));
        jMenuBar1.setForeground(new java.awt.Color(0, 102, 102));
        jMenuBar1.setMaximumSize(new java.awt.Dimension(174, 20));
        jMenuBar1.setMinimumSize(new java.awt.Dimension(174, 20));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(174, 30));

        jMenu1.setText("Student List");
        jMenu1.setFocusable(false);
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu1.setMaximumSize(new java.awt.Dimension(81, 24));
        jMenu1.setMinimumSize(new java.awt.Dimension(81, 24));
        jMenu1.setRequestFocusEnabled(false);
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Student Info");
        jMenu2.setFocusable(false);
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jmenuLogout.setText("LogOut");
        jmenuLogout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jmenuLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jmenuLogoutMouseClicked(evt);
            }
        });
        jmenuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuLogoutActionPerformed(evt);
            }
        });
        jMenuBar1.add(jmenuLogout);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
       StudentInfoPage.setVisible(true);
       StudentListPage.setVisible(false);
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
       StudentInfoPage.setVisible(false);
       StudentListPage.setVisible(true);
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
       StudentInfoPage.setVisible(false);
       StudentListPage.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
           if(JOptionPane.showConfirmDialog(null,"Are you want to exit?", "WARNING!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
               {
                    System.exit(0);
               }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
       if(JOptionPane.showConfirmDialog(null,"Are you want to exit?", "WARNING!",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                        {
                             System.exit(0);
                        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       StudentInfoPage.setVisible(true);
       StudentListPage.setVisible(false);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed

                    if(jtxtId.getText().length()!=0)
                {
                    shouldAdd = !checkIdExistOnStudentList(jtxtId.getText());
                }      

                if(shouldAdd)
                {
                    if(jtxtId.getText().length()!=0 && jtxtName.getText().length()!=0 && jtxtPhone.getText().length()!=0 && jtxtDept.getText().length()!=0)
                    {
                        if(jtxtPhone.getText().length()>11)
                            {
                                JOptionPane.showMessageDialog(this, "Phone Number Should not Exceed 11 Digit!");
                                jtxtPhone.requestFocus();
                            }
                        else if(jtxtPhone.getText().length()==11)
                        {
                            phoneDCounter = 0;
                            addToStudentListDB();
                            clearDisplay();
                            
                        }
                        else if(jtxtPhone.getText().length()<11)
                        {
                            JOptionPane.showMessageDialog(this, "Phone Number Should be 11 Digit!");
                            jtxtPhone.requestFocus();
                        }
                            
                    } 
                    else
                    {
                        JOptionPane.showMessageDialog(this, "One or More Fields are Empty!");
                    }
            
                }
                else if(jtxtId.getText().length()==0 && jtxtName.getText().length()==0 && jtxtPhone.getText().length()==0 && jtxtDept.getText().length()==0)
                        {
                            JOptionPane.showMessageDialog(this, "One or More Fields are Empty!");
                        }
                
                else if (!shouldAdd){
                    JOptionPane.showMessageDialog(this, "Data Already Exist!");
                }
            
 
    }//GEN-LAST:event_jbtnAddActionPerformed

    private void jtxtIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtIdKeyReleased
    shouldUpdate=false;
    shouldDelete = false;
                
        if(jtxtId.getText().length()!=0)
        {
            if(checkIdExistOnStudentList(jtxtId.getText()))
            {
                idWarning.setVisible(true);
                idWarning.setText("EXIST!");
                shouldAdd = false;      
            }
            else {
                idWarning.setVisible(false);
                shouldAdd = true;
            }
        }
        else if (jtxtId.getText().length()==0)
        {
            idWarning.setText("");
            idWarning.setVisible(false);
        }
             
                
        else
        {
            idWarning.setVisible(false);
            shouldAdd = true;
        }
        
        
        
        
        
    }//GEN-LAST:event_jtxtIdKeyReleased

    private void StudentListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentListTableMouseClicked
        
        idWarning.setVisible(false);
        displaySelectedRow();
        shouldUpdate=true;
        
    }//GEN-LAST:event_StudentListTableMouseClicked

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed
        shouldUpdate=false;
        
         if(jtxtId.getText().length()!=0)
         {
             shouldDelete = checkIdExistOnStudentList(jtxtId.getText());
         } 
        
        
        if(shouldDelete)
        {
            if(jtxtId.getText().length()!=0 && jtxtName.getText().length()!=0 && jtxtPhone.getText().length()!=0 && jtxtDept.getText().length()!=0)
            {
                deleteStudentListDB();
                clearDisplay();
            } 
            else if(jtxtId.getText().length()==0 && jtxtName.getText().length()==0 && jtxtPhone.getText().length()==0 && jtxtDept.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(this, "Please Select Atleast one Record!");
                }
        }
            
            else if(jtxtId.getText().length()==0 && jtxtName.getText().length()==0 && jtxtPhone.getText().length()==0 && jtxtDept.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(this, "Please Select Atleast one Record!");
                }
           
                 
        else if (!shouldDelete){
            JOptionPane.showMessageDialog(this, "Error While Deleting!");
        }
    }//GEN-LAST:event_jbtnDeleteActionPerformed

    private void jbtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnClearActionPerformed
       clearDisplay();       
       idWarning.setVisible(false);
       
    }//GEN-LAST:event_jbtnClearActionPerformed

    private void jbtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdateActionPerformed
               
        
        if(shouldUpdate)
        {
            if(jtxtId.getText().length()!=0 && jtxtName.getText().length()!=0 && jtxtPhone.getText().length()!=0 && jtxtDept.getText().length()!=0)
            {
                if(jtxtPhone.getText().length()>11)
                            {
                                JOptionPane.showMessageDialog(this, "Phone Number Should not Exceed 11 Digit!");
                                jtxtPhone.requestFocus();
                            }
                        else if(jtxtPhone.getText().length()==11)
                        {
                            phoneDCounter = 0;
                            updateStudentListDB();
                            clearDisplay();
                            
                        }
                        else if(jtxtPhone.getText().length()<11)
                        {
                            JOptionPane.showMessageDialog(this, "Phone Number Should be 11 Digit!");
                            jtxtPhone.requestFocus();
                        }

            } 
            else if(jtxtId.getText().length()==0 && jtxtName.getText().length()==0 && jtxtPhone.getText().length()==0 && jtxtDept.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(this, "Please Select Atleast one Record!");
                }
            else if(jtxtId.getText().length()==0 || jtxtName.getText().length()==0 || jtxtPhone.getText().length()==0 || jtxtDept.getText().length()==0)
            {
                
                JOptionPane.showMessageDialog(this, "One or More Fields are Empty!");
            }
        }
            
        else if(jtxtId.getText().length()==0 || jtxtName.getText().length()==0 || jtxtPhone.getText().length()==0 || jtxtDept.getText().length()==0)
            {
                
                JOptionPane.showMessageDialog(this, "Please Select Atleast one Record!");
            }          
        
        
                        
        else if (!shouldUpdate){
            JOptionPane.showMessageDialog(this, "Error While Updating!");
        }      
              
    }//GEN-LAST:event_jbtnUpdateActionPerformed

    private void jbtnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAdd2ActionPerformed
        shouldAdd2 = !checkStudentInfoExist(cbID.getItemAt(cbID.getSelectedIndex()));
    
        if(shouldAdd2)
        {
            if( jtxtTrimester.getText().length()!=0 && jtxtCgpa.getText().length()!=0)
            {
                addToStudentInfoDB();
                clearDisplay();
            } 
            else
            {
                JOptionPane.showMessageDialog(this, "One or More Fields are Empty!");
            }            
        }
        else if(jtxtTrimester.getText().length()==0 && jtxtCgpa.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(this, "One or More Fields are Empty!");
                }                
        else if (!shouldAdd2){
            JOptionPane.showMessageDialog(this, "Data Already Exist!");
        }        
    }//GEN-LAST:event_jbtnAdd2ActionPerformed

    private void cbIDMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIDMouseReleased
    
    
    }//GEN-LAST:event_cbIDMouseReleased

    private void cbIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIDMouseClicked
        
        
    }//GEN-LAST:event_cbIDMouseClicked

    private void cbIDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIDMousePressed
          
    }//GEN-LAST:event_cbIDMousePressed

    private void cbIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbIDItemStateChanged
            
    }//GEN-LAST:event_cbIDItemStateChanged

    private void cbIDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIDMouseEntered
       
    }//GEN-LAST:event_cbIDMouseEntered

    private void cbIDMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbIDMouseExited
        
    }//GEN-LAST:event_cbIDMouseExited

    private void jtxtTrimesterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtTrimesterKeyPressed
      
    }//GEN-LAST:event_jtxtTrimesterKeyPressed

    private void jbtnClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnClear2ActionPerformed
       clearDisplay();       
       idWarning2.setVisible(false);
    }//GEN-LAST:event_jbtnClear2ActionPerformed

    private void StudentInfoTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentInfoTableMouseClicked
        displaySelectedInfo();
        shouldUpdate2=true;
        
    }//GEN-LAST:event_StudentInfoTableMouseClicked

    private void jbtnUpdate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdate2ActionPerformed
         if(shouldUpdate2)
        {
            if(jtxtTrimester.getText().length()!=0 && jtxtCgpa.getText().length()!=0)
            {
                updateStudentInfoDB();
                clearDisplay();
            } 
            else if(jtxtTrimester.getText().length()==0 && jtxtCgpa.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(this, "Please Select Atleast one Record!");
                }
            else if(jtxtTrimester.getText().length()==0 || jtxtCgpa.getText().length()==0)
            {
                
                JOptionPane.showMessageDialog(this, "One or More Fields are Empty!");
            }
        }
            
        else if(jtxtTrimester.getText().length()==0 || jtxtCgpa.getText().length()==0)
            {
                
                JOptionPane.showMessageDialog(this, "Please Select Atleast one Record!");
            }        
              
        else if (!shouldUpdate2){
            JOptionPane.showMessageDialog(this, "Error While Updating!");
        }      
    }//GEN-LAST:event_jbtnUpdate2ActionPerformed

    private void jbtnDelete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDelete2ActionPerformed
       shouldUpdate2=false;
        
         
       shouldDelete2 = checkStudentInfoExist(cbID.getItemAt(cbID.getSelectedIndex()));
         
        
        
        if(shouldDelete2)
        {
            if(jtxtTrimester.getText().length()!=0 && jtxtCgpa.getText().length()!=0 )
            {
                deleteStudentInfoDB();
                clearDisplay();
            } 
            else if(jtxtTrimester.getText().length()==0 && jtxtCgpa.getText().length()==0 )
                {
                    JOptionPane.showMessageDialog(this, "Please Select Atleast one Record!");
                }
        }
            
        else if(jtxtTrimester.getText().length()==0 && jtxtCgpa.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(this, "Please Select Atleast one Record!");
                }
           
                 
        else if (!shouldDelete2){
            JOptionPane.showMessageDialog(this, "Error While Deleting!");
        }
    }//GEN-LAST:event_jbtnDelete2ActionPerformed

    private void jbtnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSearchActionPerformed
       
        if(jtxtSearch.getText().length()!=0)
        {
            searchOnStudentListDB(jtxtSearch.getText().toString());
            shouldUpdate = true;
            shouldDelete = true;
            shouldAdd = false;
            
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Search Field Empty!");
        }
        
    }//GEN-LAST:event_jbtnSearchActionPerformed

    private void jbtnSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSearch2ActionPerformed
        if(jtxtSearch2.getText().length()!=0)
        {
            searchOnStudentInfoDB(jtxtSearch2.getText().toString());
            shouldUpdate2 = true;
            shouldDelete2 = true;
            shouldAdd2 = false;
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Search Field Empty!");
        }
    }//GEN-LAST:event_jbtnSearch2ActionPerformed

    private void jtxtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtIdKeyTyped
        char a = evt.getKeyChar();
        if(!Character.isDigit(a))
        {
            if(Character.isAlphabetic(a))
            {
                digitWarningID.setText("Digit Only!");            
                digitWarningID.setVisible(true);            
                evt.consume();
            }
            evt.consume();
                  
            
        }
        else
        {
            digitWarningID.setText("");
            digitWarningID.setVisible(false);
        }
        
    }//GEN-LAST:event_jtxtIdKeyTyped

    private void jtxtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtSearchKeyTyped
        char a = evt.getKeyChar();
        if(!Character.isDigit(a))
        {
            if(Character.isAlphabetic(a))
            {
                searchWarning.setText("Digit Only!");            
                searchWarning.setVisible(true);            
                evt.consume();
            }
            evt.consume();
                  
            
        }
        else
        {
            searchWarning.setText("");
            searchWarning.setVisible(false);
        }
    }//GEN-LAST:event_jtxtSearchKeyTyped

    private void jtxtSearch2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtSearch2KeyTyped
       char a = evt.getKeyChar();
        if(!Character.isDigit(a))
        {
            if(Character.isAlphabetic(a))
            {
                searchWarning2.setText("Digit Only!");            
                searchWarning2.setVisible(true);            
                evt.consume();
            }
            evt.consume();
                  
            
        }
        else
        {
            searchWarning2.setText("");
            searchWarning2.setVisible(false);
        }
    }//GEN-LAST:event_jtxtSearch2KeyTyped

    private void jtxtTrimesterKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtTrimesterKeyTyped
        
        
    }//GEN-LAST:event_jtxtTrimesterKeyTyped

    private void jtxtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtIdActionPerformed
        jtxtName.requestFocus();
    }//GEN-LAST:event_jtxtIdActionPerformed

    private void jtxtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNameActionPerformed
        jtxtPhone.requestFocus();
    }//GEN-LAST:event_jtxtNameActionPerformed

    private void jtxtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtSearchActionPerformed
        if(jtxtSearch.getText().length()!=0)
        {
            searchOnStudentListDB(jtxtSearch.getText().toString());
            shouldUpdate = true;
            shouldDelete = true;
            shouldAdd = false;
            
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Search Field Empty!");
        }
    }//GEN-LAST:event_jtxtSearchActionPerformed

    private void jtxtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtPhoneActionPerformed
        if(jtxtPhone.getText().length()>11)
        {
            JOptionPane.showMessageDialog(this, "Phone Number Should not Exceed 11 Digit!");
        }
        else if(jtxtPhone.getText().length()==11){
            phoneDCounter = 0;
            jtxtDept.requestFocus();
        }
        else if(jtxtPhone.getText().length()<11)
        {
            JOptionPane.showMessageDialog(this, "Phone Number Should be 11 Digit!");
        }
        
        
        
    }//GEN-LAST:event_jtxtPhoneActionPerformed

    private void jtxtSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtSearch2ActionPerformed
        if(jtxtSearch2.getText().length()!=0)
        {
            searchOnStudentInfoDB(jtxtSearch2.getText().toString());
            shouldUpdate2 = true;
            shouldDelete2 = true;
            shouldAdd2 = false;
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Search Field Empty!");
        }
    }//GEN-LAST:event_jtxtSearch2ActionPerformed

    private void jtxtTrimesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtTrimesterActionPerformed
       jtxtCgpa.requestFocus();
       shouldDelete2 = false;
    }//GEN-LAST:event_jtxtTrimesterActionPerformed

    private void jtxtDeptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDeptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDeptActionPerformed

    private void jtxtTrimesterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtTrimesterKeyReleased
       shouldDelete2 = false;
    }//GEN-LAST:event_jtxtTrimesterKeyReleased

    private void jtxtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPhoneKeyTyped

        char a = evt.getKeyChar();
        if(!Character.isDigit(a))
        { 
            evt.consume();
 
        }
              
        
        
        
        
    }//GEN-LAST:event_jtxtPhoneKeyTyped

    private void jmenuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuLogoutActionPerformed
        dispose();
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }//GEN-LAST:event_jmenuLogoutActionPerformed

    private void jmenuLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmenuLogoutMouseClicked
        dispose();
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }//GEN-LAST:event_jmenuLogoutMouseClicked

    public void clearDisplay()
    {
        //Student_List
        jtxtId.setText("");
        jtxtName.setText("");
        jtxtPhone.setText("");
        jtxtDept.setText("");
        jtxtId.enable();
        idWarning.setVisible(false);
        shouldUpdate=false;
        StudentListTable.setSelectionMode(0);
        jtxtSearch.setText("");
        showStudentListDB();
        searchWarning.setText("");            
        searchWarning.setVisible(false); 
        digitWarningID.setText("");
        digitWarningID.setVisible(false);
        phoneDCounter = 0;
        
        //Student_info
        jtxtTrimester.setText("");
        jtxtCgpa.setText("");
        cbID.enable();
        StudentInfoTable.setSelectionMode(0);
        shouldUpdate2=false;
        jtxtSearch2.setText("");
        showStudentInfoDB();
        searchWarning2.setText("");            
        searchWarning2.setVisible(false);  
        
        
        
        
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
                StudentInfoPage.setVisible(false);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel HeaderName;
    private javax.swing.JLabel HeaderName1;
    private javax.swing.JPanel LeftSideBar;
    private javax.swing.JPanel LeftSideBar2;
    private javax.swing.JPanel RightSideBar;
    private javax.swing.JPanel RightSideBar2;
    private static javax.swing.JPanel StudentInfoPage;
    private javax.swing.JTable StudentInfoTable;
    private static javax.swing.JPanel StudentListPage;
    private javax.swing.JTable StudentListTable;
    private javax.swing.JComboBox<String> cbID;
    private javax.swing.JLabel digitWarningID;
    private javax.swing.JLabel idWarning;
    private javax.swing.JLabel idWarning2;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelID1;
    private javax.swing.JLabel jLabelID2;
    private javax.swing.JLabel jLabelID3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelHeader2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnAdd;
    private javax.swing.JButton jbtnAdd2;
    private javax.swing.JButton jbtnClear;
    private javax.swing.JButton jbtnClear2;
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JButton jbtnDelete2;
    private javax.swing.JButton jbtnSearch;
    private javax.swing.JButton jbtnSearch2;
    private javax.swing.JButton jbtnUpdate;
    private javax.swing.JButton jbtnUpdate2;
    private javax.swing.JLabel jlabelTotalStudent;
    private javax.swing.JMenu jmenuLogout;
    private javax.swing.JTextField jtxtCgpa;
    private javax.swing.JTextField jtxtDept;
    private javax.swing.JTextField jtxtId;
    private javax.swing.JTextField jtxtName;
    private javax.swing.JTextField jtxtPhone;
    private javax.swing.JTextField jtxtSearch;
    private javax.swing.JTextField jtxtSearch2;
    private javax.swing.JTextField jtxtTrimester;
    private javax.swing.JLabel searchWarning;
    private javax.swing.JLabel searchWarning2;
    // End of variables declaration//GEN-END:variables
}
