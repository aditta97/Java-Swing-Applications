/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author adittachakraborty
 */
public class AddStudent extends javax.swing.JFrame {

    int xMouse;
    int yMouse;
    String filename = null;

    /**
     * Creates new form AddBook
     */
    public AddStudent() {
        initComponents();
        table();
        studentTable.setRowHeight(35);
    }

    //jTable date format change
    void ChangeTableDateFormat() {
        TableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
            SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof java.util.Date) {
                    value = f.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        studentTable.getColumnModel().getColumn(7).setCellRenderer(tableCellRenderer);
    }

    private void table() {
        try {
            DBclass.createCon();
            String query = "SELECT Registration, StudentName AS 'Student Name', Department, Roll, Batch, Session, PhoneNumber AS 'Phone Number', JoiningDate AS 'Joining Date' FROM Student";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();

            studentTable.setModel(DbUtils.resultSetToTableModel(DBclass.rs));
            ChangeTableDateFormat();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Student Table Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //For Search button (Searching by registration)
    public void searchFetch(String registration) {
        String search = registration;
        if (!txtSearch.getText().isEmpty()) {
            try {
                DBclass.createCon();
                String query = "SELECT Registration, StudentName AS 'Student Name', Department, Roll, Batch, Session, PhoneNumber AS 'Phone Number', JoiningDate AS 'Joining Date' FROM Student WHERE Registration LIKE '%" + search + "%' OR StudentName LIKE '%" + search + "%' OR Department LIKE '%" + search + "%'";
                DBclass.pst = DBclass.con.prepareStatement(query);
                DBclass.rs = DBclass.pst.executeQuery();
                studentTable.setModel(DbUtils.resultSetToTableModel(DBclass.rs));
                ChangeTableDateFormat();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error In Search Fetch", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            table();
        }

    }

    private void profilePicture(String registration) {
        String reg = registration;
        try {
            DBclass.createCon();
            String query = "SELECT ProfilePicture FROM Student WHERE Registration = '" + reg + "'";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                try {
                    InputStream inst = DBclass.rs.getBinaryStream(1);
                    Image im = ImageIO.read(inst);
                    labelpic.setIcon(new ImageIcon(im.getScaledInstance(labelpic.getWidth(), labelpic.getHeight(), Image.SCALE_SMOOTH)));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error In Label Picture", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Picture Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //For Search button (Searching by registration)
    public void searchData(String registration) {
        String search = registration;
        try {
            DBclass.createCon();
            String query = "SELECT ProfilePicture, StudentName, Department, Roll, Batch, Session, PhoneNumber, JoiningDate FROM Student WHERE Registration = '" + search + "'";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                try {
                    InputStream inst = DBclass.rs.getBinaryStream(1);
                    Image im = ImageIO.read(inst);
                    labelpic.setIcon(new ImageIcon(im.getScaledInstance(labelpic.getWidth(), labelpic.getHeight(), Image.SCALE_SMOOTH)));
                } catch (IOException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error In Label Picture", "Error", JOptionPane.ERROR_MESSAGE);
                }
                txtStudentName.setText(DBclass.rs.getString(2));
                txtDepartment.setText(DBclass.rs.getString(3));
                String roll = Integer.toString(DBclass.rs.getInt(4));
                txtRoll.setText(roll);
                txtBatch.setText(DBclass.rs.getString(5));
                txtSession.setText(DBclass.rs.getString(6));
                txtPhoneNumber.setText(DBclass.rs.getString(7));
                txtJoiningDate.setDate(DBclass.rs.getDate(8));
            }
            ChangeTableDateFormat();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error In Search Fetch", "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtStudentName = new javax.swing.JTextField();
        txtDepartment = new javax.swing.JTextField();
        txtRoll = new javax.swing.JTextField();
        txtRegistration = new javax.swing.JTextField();
        txtBatch = new javax.swing.JTextField();
        txtSession = new javax.swing.JTextField();
        txtPhoneNumber = new javax.swing.JTextField();
        labelpic = new javax.swing.JLabel();
        btnAttachImage = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtJoiningDate = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Student");
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel1.setText("Student name");

        jLabel2.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel2.setText("Department");

        jLabel3.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel3.setText("Roll");

        jLabel4.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel4.setText("Registration");

        jLabel5.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel5.setText("Batch");

        jLabel6.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel6.setText("Phone Number");

        jLabel7.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel7.setText("Session");

        txtStudentName.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtDepartment.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtRoll.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtRoll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRollKeyTyped(evt);
            }
        });

        txtRegistration.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtRegistration.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRegistrationKeyReleased(evt);
            }
        });

        txtBatch.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtSession.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtPhoneNumber.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtPhoneNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneNumberKeyTyped(evt);
            }
        });

        labelpic.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelpic.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAttachImage.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnAttachImage.setText("Attach Image");
        btnAttachImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttachImageActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel8.setText("Book Image");

        studentTable.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Registration", "Student Name", "Department", "Roll", "Batch", "Session", "Phone Number", "Joining date"
            }
        ));
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(studentTable);

        jLabel9.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel9.setText("Search");

        txtSearch.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRemove.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel10.setText("Joining Date");

        txtJoiningDate.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPhoneNumber)
                            .addComponent(txtSession)
                            .addComponent(txtBatch)
                            .addComponent(btnAttachImage)
                            .addComponent(txtJoiningDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(labelpic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnSave)
                        .addGap(40, 40, 40)
                        .addComponent(btnUpdate)
                        .addGap(40, 40, 40)
                        .addComponent(btnRemove))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtDepartment, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                        .addComponent(txtStudentName, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtRoll)))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(30, 30, 30)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnReset)
                        .addGap(0, 333, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(20, 20, 20))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnRemove, btnSave, btnUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelpic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAttachImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRoll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBatch))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSession, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                            .addComponent(txtJoiningDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnUpdate)
                            .addComponent(btnRemove)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(btnReset))
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1)))
                .addGap(25, 25, 25))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnRemove, btnSave, btnUpdate});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void txtPhoneNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneNumberKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtPhoneNumberKeyTyped
    FileInputStream pic;
    //Picture format converting for attach image
    File fi = new File(System.getProperty("user.home") + "/Desktop/Profile Picture.png");

    int selectAndCreate() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Select an image");
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPG, JPEG, and GIF images", "png", "jpg", "jpeg", "gif");
        chooser.addChoosableFileFilter(filter);
        int i = chooser.showOpenDialog(null);
        if (i == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage bI;
                try {
                    bI = ImageIO.read(chooser.getSelectedFile());
                    BufferedImage newBI = new BufferedImage(bI.getWidth(), bI.getHeight(), bI.getType());
                    newBI.createGraphics().drawImage(bI, 0, 0, Color.WHITE, null);
                    try {
                        ImageIO.write(newBI, "jpg", new File(System.getProperty("user.home") + "/Desktop/Profile Picture.jpg"));
                    } catch (IOException e) {
                        e.getMessage();
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "IOException Error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(null, "HeadlessException Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return i;
    }
    private void btnAttachImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttachImageActionPerformed
        int i = selectAndCreate();
        if (i == JFileChooser.APPROVE_OPTION) {
            if (fi.isFile()) {
                filename = fi.getAbsolutePath();
                try {
                    pic = new FileInputStream(filename);
                } catch (FileNotFoundException e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Picture Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                }
                ImageIcon im = new ImageIcon(filename);
                Image iimage = im.getImage();
                Image newImage = iimage.getScaledInstance(labelpic.getWidth(), labelpic.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon imicon = new ImageIcon(newImage);
                labelpic.setIcon(imicon);
                try {
                    File image = new File(filename);
                    FileInputStream fs = new FileInputStream(image);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    for (int readNum; (readNum = fs.read(buf)) != -1;) {
                        bos.write(buf, 0, readNum);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
        fi.delete();
    }//GEN-LAST:event_btnAttachImageActionPerformed

    private void txtRollKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRollKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtRollKeyTyped

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (labelpic.getIcon() == null || txtStudentName.getText().equals("") || txtDepartment.getText().equals("") || txtRoll.getText().equals("") || txtRegistration.getText().equals("") || txtBatch.getText().equals("") || txtSession.getText().equals("") || txtPhoneNumber.getText().equals("") || txtJoiningDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "All Fields Are Required", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String registration = txtRegistration.getText();

                DBclass.createCon();
                String query = "SELECT Registration FROM Student WHERE Registration = '" + registration + "'";
                DBclass.pst = DBclass.con.prepareStatement(query);
                DBclass.rs = DBclass.pst.executeQuery();
                if (DBclass.rs.next()) {
                    JOptionPane.showMessageDialog(this, "The Student Already Registered", "Information Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    DBclass.createCon();
                    String studentInsert = "INSERT INTO Student (Registration, ProfilePicture, StudentName, Department, Roll, Batch, Session, PhoneNumber, JoiningDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    DBclass.pst = DBclass.con.prepareStatement(studentInsert);

                    DBclass.pst.setString(1, txtRegistration.getText());
                    try {
                        DBclass.pst.setBinaryStream(2, pic, pic.available());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Picture Didn't Entered", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    DBclass.pst.setString(3, txtStudentName.getText());
                    DBclass.pst.setString(4, txtDepartment.getText());
                    DBclass.pst.setString(5, txtRoll.getText());
                    DBclass.pst.setString(6, txtBatch.getText());
                    DBclass.pst.setString(7, txtSession.getText());
                    DBclass.pst.setString(8, txtPhoneNumber.getText());
                    java.sql.Date date = new java.sql.Date(txtJoiningDate.getDate().getTime());
                    DBclass.pst.setDate(9, date);

                    DBclass.pst.executeUpdate();
                    table();
                    JOptionPane.showMessageDialog(this, "Student Successfully Registered", "Information Message", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtRegistrationKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegistrationKeyReleased
        //String registration = txtRegistration.getText();
        //searchData(registration);
    }//GEN-LAST:event_txtRegistrationKeyReleased

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String registration = txtSearch.getText();
        searchFetch(registration);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (txtStudentName.getText().equals("") || txtDepartment.getText().equals("") || txtRoll.getText().equals("") || txtRegistration.getText().equals("") || txtBatch.getText().equals("") || txtSession.getText().equals("") || txtPhoneNumber.getText().equals("") || txtJoiningDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "All Fields Are Required", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String registration = txtRegistration.getText();

                DBclass.createCon();
                String query = "UPDATE Student SET StudentName = ?, Department = ?, Roll = ?, Batch = ?, Session = ?, PhoneNumber = ?, JoiningDate = ? WHERE Registration = " + registration;
                DBclass.pst = DBclass.con.prepareStatement(query);

                if (DBclass.rs.next()) {
                    DBclass.pst.setString(1, txtRegistration.getText());
                    try {
                        DBclass.pst.setBinaryStream(2, pic, pic.available());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Picture Didn't Entered", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    DBclass.pst.setString(3, txtStudentName.getText());
                    DBclass.pst.setString(4, txtDepartment.getText());
                    int roll = Integer.valueOf(txtRoll.getText());
                    DBclass.pst.setInt(5, roll);
                    DBclass.pst.setString(6, txtBatch.getText());
                    DBclass.pst.setString(7, txtSession.getText());
                    DBclass.pst.setString(8, txtPhoneNumber.getText());
                    java.sql.Date date = new java.sql.Date(txtJoiningDate.getDate().getTime());
                    DBclass.pst.setDate(9, date);

                    int k = DBclass.pst.executeUpdate();
                    if (k != 0) {
                        table();
                        JOptionPane.showMessageDialog(this, "Successfully Updated");
                    } else {
                        JOptionPane.showMessageDialog(this, "Not Updated", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Student Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if (labelpic.getIcon() == null || txtStudentName.getText().equals("") || txtDepartment.getText().equals("") || txtRoll.getText().equals("") || txtRegistration.getText().equals("") || txtBatch.getText().equals("") || txtSession.getText().equals("") || txtPhoneNumber.getText().equals("") || txtJoiningDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "All Fields Are Required", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int a = JOptionPane.showConfirmDialog(this, "Do you want to Remove Student?", "Remove Student", JOptionPane.YES_NO_CANCEL_OPTION);
            if (a == 0) {
                try {
                    String registration = txtRegistration.getText();

                    DBclass.createCon();
                    String query = "DELETE FROM Student WHERE Registration = " + registration;
                    DBclass.pst = DBclass.con.prepareStatement(query);
                    DBclass.pst.executeUpdate();
                    table();
                    JOptionPane.showMessageDialog(this, "Succesfully Removed", "Student Deleted", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Student Is Not Removed", "Not Deleted", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void studentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMouseClicked
        //get values from jTable to components by mouse clicked
        int i = studentTable.getSelectedRow();
        String registration = studentTable.getValueAt(i, 0).toString();
        txtRegistration.setText(registration);
        profilePicture(registration);
        txtStudentName.setText(studentTable.getValueAt(i, 1).toString());
        txtDepartment.setText(studentTable.getValueAt(i, 2).toString());
        txtRoll.setText(studentTable.getValueAt(i, 3).toString());
        txtBatch.setText(studentTable.getValueAt(i, 4).toString());
        txtSession.setText(studentTable.getValueAt(i, 5).toString());
        txtPhoneNumber.setText(studentTable.getValueAt(i, 6).toString());
        String date = studentTable.getValueAt(i, 7).toString();
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            txtJoiningDate.setDate(date1);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Error In Table For Mouse Clicked", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_studentTableMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        table();
        labelpic.setIcon(null);
        txtRegistration.setText("");
        txtStudentName.setText("");
        txtDepartment.setText("");
        txtRoll.setText("");
        txtBatch.setText("");
        txtSession.setText("");
        txtPhoneNumber.setText("");
        txtJoiningDate.setDate(null);
    }//GEN-LAST:event_btnResetActionPerformed

    /**
     * @param args the command line arguments
     * @throws javax.swing.UnsupportedLookAndFeelException
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AddStudent().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAttachImage;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelpic;
    private javax.swing.JTable studentTable;
    private javax.swing.JTextField txtBatch;
    private javax.swing.JTextField txtDepartment;
    private com.toedter.calendar.JDateChooser txtJoiningDate;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtRegistration;
    private javax.swing.JTextField txtRoll;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSession;
    private javax.swing.JTextField txtStudentName;
    // End of variables declaration//GEN-END:variables
}
