/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storeaccountpassword;

import com.github.sarxos.webcam.Webcam;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author adittachakraborty
 */
public final class UpdateProfile extends javax.swing.JFrame {

    int xMouse;
    int yMouse;
    
    static int userId = 0;
    String filename = null;
    File f = null;
    BufferedImage image2 = null;

    /**
     * Creates new form UpdateProfile
     *
     * @param userId
     */
    public UpdateProfile(int userId) {
        initComponents();
        UpdateProfile.userId = userId;
        Labelpic();
        GetUserInformation();
        JFrameIcon();
    }

    //Setting an Icon for jFrame
    void JFrameIcon() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icons/password.png")));
    }

    void ProfilePicture() {
        try {
            DbClass.createCon2();
            String q = "select id from registration where Id = " + userId;
            DbClass.pst2 = DbClass.con2.prepareStatement(q);
            DbClass.rs2 = DbClass.pst2.executeQuery();

            if (DbClass.rs2.next()) {
                //getting User Id
                int userID = DbClass.rs2.getInt(1);
                //Deleting Image by full row from Profile Picture Table
                DbClass.createCon2();
                String deletePicture = "DELETE FROM PROFILEPICTURE WHERE Id = " + userID;
                DbClass.pst2 = DbClass.con2.prepareStatement(deletePicture);
                DbClass.pst2.execute();

                //Inserting New Picture
                DbClass.createCon3();
                String imuploadQ = "INSERT INTO PROFILEPICTURE VALUES(?,?)";
                DbClass.pst3 = DbClass.con3.prepareStatement(imuploadQ);
                DbClass.pst3.setInt(1, userId);
                DbClass.pst3.setBinaryStream(2, pic, pic.available());
                DbClass.pst3.executeUpdate();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "IOException | SQLException Error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void Labelpic() {
        try {
            DbClass.createCon();
            String query = "select Picture from PROFILEPICTURE where id = " + userId;
            DbClass.createSt();
            DbClass.rs = DbClass.st.executeQuery(query);
            while (DbClass.rs.next()) {
                try {
                    InputStream inst = DbClass.rs.getBinaryStream(1);
                    Image im = ImageIO.read(inst);
                    labelpic.setIcon(new ImageIcon(im.getScaledInstance(labelpic.getWidth(), labelpic.getHeight(), Image.SCALE_SMOOTH)));
                } catch (IOException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "Picture Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error in User", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void GetUserInformation() {
        try {
            DbClass.createCon2();
            String q = "select * from registration where Id = " + userId;
            DbClass.pst2 = DbClass.con2.prepareStatement(q);
            DbClass.rs2 = DbClass.pst2.executeQuery();

            if (DbClass.rs2.next()) {
                String name = DbClass.rs2.getString("Username");
                updateUsername.setText(name);

                String email = DbClass.rs2.getString("Email");
                updateEmailAddress.setText(email);

                String number = DbClass.rs2.getString("PhoneNumber");
                updatePhoneNumber.setText(number);
                
                String password = DbClass.rs2.getString("Password");
                txtPassword.setText(password);
                txtConfirmPassword.setText(password);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Username getting error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void RegistrationTableNameUpdate() {
        try {
            DbClass.createCon2();
            String query = "UPDATE registration SET Username = ? WHERE Id = " + userId;
            DbClass.pst2 = DbClass.con2.prepareStatement(query);

            if (Pattern.matches("^[a-zA-Z]+$", updateUsername.getText())) {
                //Aditta
                DbClass.pst2.setString(1, updateUsername.getText());
            } else if (Pattern.matches("^[a-zA-Z]+[ ]{1}+[a-zA-Z]+$", updateUsername.getText())) {
                //Aditta Chakraborty
                DbClass.pst2.setString(1, updateUsername.getText());
            } else if (Pattern.matches("^[a-zA-Z]+[ ]+[a-zA-Z]+[ ]{1}+[a-zA-Z]+$", updateUsername.getText())) {
                //AC Aditta Chakraborty
                DbClass.pst2.setString(1, updateUsername.getText());
            }

            DbClass.pst2.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Name Is Not Updated In Registration Table", "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        updatePhoneNumber = new javax.swing.JTextField();
        updateEmailAddress = new javax.swing.JTextField();
        labelpic = new javax.swing.JLabel();
        btnAttachImage = new javax.swing.JButton();
        btnCaptureImage = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnUpdateProfile = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        updateUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtConfirmPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Profile");
        setResizable(false);
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

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel6.setText("Email Address");

        jLabel7.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel7.setText("Phone Number");

        jLabel13.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel13.setText("Profile Picture");

        updatePhoneNumber.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        updatePhoneNumber.setActionCommand("<Not Set>");
        updatePhoneNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                updatePhoneNumberKeyTyped(evt);
            }
        });

        updateEmailAddress.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        labelpic.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelpic.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnAttachImage.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnAttachImage.setText("Attach Image");
        btnAttachImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAttachImageActionPerformed(evt);
            }
        });

        btnCaptureImage.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnCaptureImage.setText("Capture Image");
        btnCaptureImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaptureImageActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-recurring_appointment.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnUpdateProfile.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnUpdateProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-approve_and_update_1.png"))); // NOI18N
        btnUpdateProfile.setText("Update Profile");
        btnUpdateProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProfileActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel5.setText("Username");

        updateUsername.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel1.setText("Password");

        jLabel2.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel2.setText("Confirm Password");

        txtPassword.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtConfirmPassword.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(jLabel13)
                        .addComponent(jLabel5)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(btnReset))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btnUpdateProfile))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(updatePhoneNumber, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updateEmailAddress, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAttachImage)
                                .addGap(18, 18, 18)
                                .addComponent(btnCaptureImage))
                            .addComponent(updateUsername, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword)
                            .addComponent(txtConfirmPassword, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(25, 25, 25))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(labelpic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(labelpic, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAttachImage)
                    .addComponent(btnCaptureImage)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(updateUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(updateEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(updatePhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnUpdateProfile))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void updatePhoneNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_updatePhoneNumberKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_updatePhoneNumberKeyTyped

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        updateUsername.setText(null);
        updateEmailAddress.setText(null);
        updatePhoneNumber.setText(null);
        txtPassword.setText(null);
        txtConfirmPassword.setText(null);
        //labelpic.setIcon();
    }//GEN-LAST:event_btnResetActionPerformed
    FileInputStream pic;
    File fi = new File(System.getProperty("user.home") + "/Desktop/Profile Picture.png");

    int SelectAndCreate() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Select an Image");
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPG, JPEG and GIF Images", "png", "jpg", "jpeg", "gif");
        chooser.addChoosableFileFilter(filter);
        int i = chooser.showOpenDialog(this);
        if (i == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage bI;
                try {
                    bI = ImageIO.read(chooser.getSelectedFile());
                    BufferedImage newBI = new BufferedImage(bI.getWidth(), bI.getHeight(), bI.getType());
                    newBI.createGraphics().drawImage(bI, 0, 0, Color.WHITE, null);
                    try {
                        ImageIO.write(newBI, "png", new File(System.getProperty("user.home") + "/Desktop/Profile Picture.png"));
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
    boolean isSelected = false;
    private void btnAttachImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAttachImageActionPerformed
        int i = SelectAndCreate();
        if (i == JFileChooser.APPROVE_OPTION) {
            if (fi.isFile()) {
                filename = fi.getAbsolutePath();
                try {
                    pic = new FileInputStream(fi.getAbsolutePath());
                } catch (FileNotFoundException e) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Picture not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
                ImageIcon im = new ImageIcon(filename);
                Image iimage = im.getImage();
                Image newImage = iimage.getScaledInstance(labelpic.getWidth(), labelpic.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon imicon = new ImageIcon(newImage);
                labelpic.setIcon(imicon);
                isSelected = true;
                try {
                    File image = new File(filename);
                    FileInputStream fs = new FileInputStream(image);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    for (int readNum; (readNum = fs.read(buf)) != -1;) {
                        bos.write(buf, 0, readNum);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "IOException Error Attach Image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        ProfilePicture();
        fi.delete();
    }//GEN-LAST:event_btnAttachImageActionPerformed
    File capture = new File(System.getProperty("user.home") + "/Desktop/Profile Picture.png");

    void CaptureImage() {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();
        try {
            ImageIO.write(webcam.getImage(), "PNG", new File(System.getProperty("user.home") + "/Desktop/Profile Picture.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "IOExcdption Error In Capture Image", "Error", JOptionPane.ERROR_MESSAGE);
        }
        webcam.close();
    }
    private void btnCaptureImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaptureImageActionPerformed
        CaptureImage();
        if (capture.isFile()) {
            filename = capture.getAbsolutePath();
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
                JOptionPane.showMessageDialog(this, "IOException Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        ProfilePicture();
        capture.delete();
    }//GEN-LAST:event_btnCaptureImageActionPerformed
    int id = 0;
    int tempId = 0;
    private void btnUpdateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProfileActionPerformed
        if (labelpic.getIcon() == null || updateUsername.getText().equals("") || updateEmailAddress.getText().equals("") || updatePhoneNumber.getText().equals("") || txtPassword.getPassword().equals("") || txtConfirmPassword.getPassword().equals("")) {
            JOptionPane.showMessageDialog(this, "All fields are required");
        } else {
            try {
                DbClass.createCon2();
                String sql = "Select Email FROM registration WHERE Id = " + userId;
                DbClass.pst2 = DbClass.con2.prepareStatement(sql);
                DbClass.rs2 = DbClass.pst2.executeQuery();
                DbClass.rs2.next();
                String email = DbClass.rs2.getString("Email");
                if (updateEmailAddress.getText().equals(email)) {
                    userId = DbClass.rs2.getInt(1);
                    DbClass.createCon2();
                    String query2 = "UPDATE registration SET  Email = ?, PhoneNumber = ?, Password = ? WHERE Id = " + userId;
                    DbClass.pst2 = DbClass.con2.prepareStatement(query2);

                    DbClass.pst2.setString(1, updateEmailAddress.getText());
                    DbClass.pst2.setString(2, updatePhoneNumber.getText());

                    if (Arrays.equals(txtPassword.getPassword(), txtConfirmPassword.getPassword())) {
                        DbClass.pst2.setString(3, new String(txtPassword.getPassword()));
                    } else {
                        JOptionPane.showMessageDialog(this, "Password Didn't Matched", "", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    RegistrationTableNameUpdate();
                    DbClass.pst2.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Successfully Updated\nThe Software Need To Re-Open");
                    System.exit(0);
                } else {
                    DbClass.createCon2();
                    String query2 = "Select Email from registration Where Email = '" + updateEmailAddress.getText() + "'";
                    DbClass.pst2 = DbClass.con2.prepareStatement(query2);
                    DbClass.rs2 = DbClass.pst2.executeQuery();
                    if (DbClass.rs2.next()) {
                        JOptionPane.showMessageDialog(this, "Email Address already exists\nEnter Another Email Address", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        DbClass.createCon2();
                        String query3 = "UPDATE registration SET Email = ?, PhoneNumber = ?, Password = ? WHERE Id = " + userId;
                        DbClass.pst2 = DbClass.con2.prepareStatement(query3);

                        DbClass.pst2.setString(1, updateEmailAddress.getText());
                        DbClass.pst2.setString(2, updatePhoneNumber.getText());

                        if (Arrays.equals(txtPassword.getPassword(), txtConfirmPassword.getPassword())) {
                            DbClass.pst2.setString(3, new String(txtPassword.getPassword()));
                        } else {
                            JOptionPane.showMessageDialog(this, "Password Didn't Matched", "", JOptionPane.ERROR_MESSAGE);
                        }
                        
                        RegistrationTableNameUpdate();
                        int k = DbClass.pst2.executeUpdate();
                        if (k != 0) {
                            JOptionPane.showMessageDialog(this, "Successfully Updated\nThe Software Need To Re-Open");
                            System.exit(0);
                        } else {
                            JOptionPane.showMessageDialog(this, "Not Updated", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (HeadlessException | SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnUpdateProfileActionPerformed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_formMousePressed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new UpdateProfile(userId).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAttachImage;
    private javax.swing.JButton btnCaptureImage;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdateProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelpic;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField updateEmailAddress;
    private javax.swing.JTextField updatePhoneNumber;
    private javax.swing.JTextField updateUsername;
    // End of variables declaration//GEN-END:variables
}
