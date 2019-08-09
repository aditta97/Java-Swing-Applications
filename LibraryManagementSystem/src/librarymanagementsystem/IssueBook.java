/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adittachakraborty
 */
public final class IssueBook extends javax.swing.JFrame {

    int xMouse;
    int yMouse;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String filename = null;

    /**
     * Creates new form AddBook
     */
    public IssueBook() {
        initComponents();
        table();
        issueBookTable.setRowHeight(35);
    }

    private void remainingTime(String bookName, String writerName, String edition, String publisher, int pages) {
        try {
            DBclass.createCon();
            //Getting Id of Book from Book Table
            String query = "SELECT Id FROM Book WHERE BookName = '" + bookName + "' AND WriterName = '" + writerName + "' AND Edition = '" + edition + "' AND Publisher = '" + publisher + "' AND Pages = " + pages;
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                int bookId = DBclass.rs.getInt(1);

                long issueD = issueDateChooser.getDate().getTime();
                java.sql.Date iDate = new java.sql.Date(issueD);
                String i = iDate.toString();

                long returnD = returnDateChooser.getDate().getTime();
                java.sql.Date rDate = new java.sql.Date(returnD);
                String r = rDate.toString();

                //Getting Id of IssueBook from IssueBook Table
                DBclass.createCon2();
                String query1 = "SELECT Id FROM IssueBook WHERE BookId = " + bookId + " AND StudentId = '" + txtRegistration.getText() + "' AND IssueDate = '" + i + "' AND ReturnDate = '" + r + "'";
                DBclass.pst2 = DBclass.con2.prepareStatement(query1);
                DBclass.rs2 = DBclass.pst2.executeQuery();

                while (DBclass.rs2.next()) {
                    int issueId = DBclass.rs2.getInt(1);

                    //Getting current date in localdate format
                    LocalDate localDate = LocalDate.now();
                    //Converting localdate to Util date format
                    java.util.Date currentDate = java.sql.Date.valueOf(localDate);

                    //Getting returning date in sql date format from GUI
                    java.sql.Date sqlDate = new java.sql.Date(returnDateChooser.getDate().getTime());
                    //Converting sql date to Util date format
                    java.util.Date returnDate = new java.util.Date(sqlDate.getTime());

                    //Getting difference between two Util days
                    long difference = currentDate.getTime() - returnDate.getTime();
                    //Calculating & formatinng in Integer
                    int remainingDays = (int) (difference / (1000 * 60 * 60 * 24));
                    
                    if (remainingDays < 0) {
                        int positiveOfRemainningDays = Math.abs(remainingDays);
                        String daysRemaing = " Days Remaining";
                        String remain = String.valueOf(positiveOfRemainningDays);
                        String remainDays = remain + daysRemaing;
                        System.out.println(remainDays);
                        
                        //It's NULL value. So Updating real value in String after calculating remaining days
                        String query2 = "UPDATE IssueBook SET RemainingTime = ? WHERE id = " + issueId;
                        DBclass.pst2 = DBclass.con2.prepareStatement(query2);
                        DBclass.pst2.setString(1, remainDays);
                        DBclass.pst2.executeUpdate();
                    } else if (remainingDays > 0) {
                        String expired = "Expired ";
                        String text = " Days Ago";
                        String days = String.valueOf(remainingDays);
                        String finalDays = expired + days + text;
                        System.out.println(finalDays);
                        
                        //It's NULL value. So Updating real value in String after calculating remaining days
                        String query2 = "UPDATE IssueBook SET RemainingTime = ? WHERE id = " + issueId;
                        DBclass.pst2 = DBclass.con2.prepareStatement(query2);
                        DBclass.pst2.setString(1, finalDays);
                        DBclass.pst2.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void issueBookStudent(int Id, String bookName, String writerName, String edition, String publisher, int pages) {
        String studentId = txtRegistration.getText();

        int bookId = Id;

        long issueD = issueDateChooser.getDate().getTime();
        java.sql.Date issueDate = new java.sql.Date(issueD);

        long returnD = returnDateChooser.getDate().getTime();
        java.sql.Date returnDate = new java.sql.Date(returnD);

        try {
            DBclass.createCon();
            String query = "INSERT INTO IssueBook(StudentId, BookId, IssueDate, ReturnDate) VALUES(?, ?, ?, ?)";
            DBclass.pst = DBclass.con.prepareStatement(query);

            DBclass.pst.setString(1, studentId);
            DBclass.pst.setInt(2, bookId);
            DBclass.pst.setDate(3, issueDate);
            DBclass.pst.setDate(4, returnDate);

            DBclass.pst.executeUpdate();
            remainingTime(bookName, writerName, edition, publisher, pages);
            JOptionPane.showMessageDialog(this, "Book Issued Successfully", "Information Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void table() {
        DefaultTableModel tableModel = new DefaultTableModel();
        String columnnNames[] = {"Book Id", "Book Name", "Writer Name", "Edition", "Publisher", "Pages", "Add Book"};
        tableModel.setColumnIdentifiers(columnnNames);

        try {
            DBclass.createCon();
            String query = "select Id, BookName, WriterName, Edition, Publisher, Pages from Book";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                int id = DBclass.rs.getInt(1);
                String name = DBclass.rs.getString(2);
                String writerName = DBclass.rs.getString(3);
                String edition = DBclass.rs.getString(4);
                String publisher = DBclass.rs.getString(5);
                int pages = DBclass.rs.getInt(6);

                Object[] row = new Object[7];
                row[0] = id;
                row[1] = name;
                row[2] = writerName;
                row[3] = edition;
                row[4] = publisher;
                row[5] = pages;
                row[6] = "Add";

                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        issueBookTable.setModel(tableModel);

        Action doSomething = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtRegistration.getText().equals("") || issueDateChooser.getDate() == null || returnDateChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "All Fields Are Required");
                } else {
                    //JTable table = (JTable) e.getSource();
                    int row = Integer.valueOf(e.getActionCommand());
                    issueBookTable.getSelectionModel().addSelectionInterval(row, row);
                    DefaultTableModel model = (DefaultTableModel) issueBookTable.getModel();
                    int rowIndex = issueBookTable.getSelectedRow();

                    issueBookStudent((int) model.getValueAt(rowIndex, 0), //Book Id
                            (String) model.getValueAt(row, 1), //Book Name
                            (String) model.getValueAt(row, 2), //Writer Name
                            (String) model.getValueAt(row, 3), //Edition
                            (String) model.getValueAt(row, 4), //Publisher
                            (int) model.getValueAt(row, 5)); //Pages
                }
            }
        };

        TableButton tableButton = new TableButton(issueBookTable, doSomething, 6);
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
        txtRegistration = new javax.swing.JTextField();
        txtStudentName = new javax.swing.JTextField();
        txtDepartment = new javax.swing.JTextField();
        txtRoll = new javax.swing.JTextField();
        txtBatch = new javax.swing.JTextField();
        txtSession = new javax.swing.JTextField();
        txtPhoneNumber = new javax.swing.JTextField();
        labelpic = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        issueBookTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        issueDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        returnDateChooser = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Issue Book");
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
        jLabel1.setText("Student");

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

        txtRegistration.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtRegistration.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRegistrationKeyReleased(evt);
            }
        });

        txtStudentName.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtStudentName.setEnabled(false);

        txtDepartment.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtDepartment.setEnabled(false);

        txtRoll.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtRoll.setEnabled(false);
        txtRoll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRollKeyTyped(evt);
            }
        });

        txtBatch.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtBatch.setEnabled(false);

        txtSession.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtSession.setEnabled(false);

        txtPhoneNumber.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtPhoneNumber.setEnabled(false);
        txtPhoneNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneNumberKeyTyped(evt);
            }
        });

        labelpic.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelpic.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        issueBookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(issueBookTable);

        jLabel9.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel9.setText("Search Book Name");

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

        jLabel10.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel10.setText("Student Name");

        jLabel8.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel8.setText("Issue Date");

        issueDateChooser.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel11.setText("Return Date");

        returnDateChooser.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(labelpic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(returnDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtRegistration, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .addComponent(txtPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .addComponent(txtSession, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .addComponent(txtBatch, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .addComponent(txtRoll, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .addComponent(txtDepartment, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .addComponent(txtStudentName, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .addComponent(issueDateChooser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(30, 30, 30)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnReset)
                        .addGap(0, 253, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(btnReset))
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelpic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRoll, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBatch))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSession, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(issueDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(returnDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );

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

    private void txtRollKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRollKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtRollKeyTyped

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        table();
        txtRegistration.setText("");
        txtStudentName.setText("");
        txtDepartment.setText("");
        txtRoll.setText("");
        txtBatch.setText("");
        txtSession.setText("");
        txtPhoneNumber.setText("");
        issueDateChooser.setDate(null);
        returnDateChooser.setDate(null);
    }//GEN-LAST:event_btnResetActionPerformed
    //For Search button (Searching by Registration)
    public void Searchfetch(String registration) {
        String search = registration;
        try {
            DBclass.createCon();
            String query = "SELECT ProfilePicture, StudentName, Department, Roll, Batch, Session, PhoneNumber FROM Student where Registration = '" + search + "'";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            if (DBclass.rs.next()) {
                try {
                    InputStream is = DBclass.rs.getBinaryStream(1);
                    Image im = ImageIO.read(is);
                    Image img2 = im.getScaledInstance(labelpic.getWidth(), labelpic.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon i = new ImageIcon(img2);
                    labelpic.setIcon(i);
                } catch (IOException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "Picture Not Found", "Error To Get Picture", JOptionPane.ERROR_MESSAGE);
                }

                String name = DBclass.rs.getString(2);
                txtStudentName.setText(name);

                String department = DBclass.rs.getString(3);
                txtDepartment.setText(department);

                int roll = DBclass.rs.getInt(4);
                txtRoll.setText(String.valueOf(roll));

                String batch = DBclass.rs.getString(5);
                txtBatch.setText(batch);

                String session = DBclass.rs.getString(6);
                txtSession.setText(session);

                String phoneNumber = DBclass.rs.getString(7);
                txtPhoneNumber.setText(phoneNumber);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error In Search Fetch", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void txtRegistrationKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRegistrationKeyReleased
        String search = txtRegistration.getText();
        Searchfetch(search);
    }//GEN-LAST:event_txtRegistrationKeyReleased
    private void bookSearch(String bookSearh) {
        DefaultTableModel tableModel = new DefaultTableModel();
        String columnnNames[] = {"Book Id", "Book Name", "Writer Name", "Edition", "Publisher", "Pages", "Add Book"};
        tableModel.setColumnIdentifiers(columnnNames);

        try {
            DBclass.createCon();
            String query = "select Id, BookName, WriterName, Edition, Publisher, Pages from Book WHERE BookName = '" + bookSearh + "'";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                int id = DBclass.rs.getInt(1);
                String name = DBclass.rs.getString(2);
                String writerName = DBclass.rs.getString(3);
                String edition = DBclass.rs.getString(4);
                String publisher = DBclass.rs.getString(5);
                int pages = DBclass.rs.getInt(6);

                Object[] row = new Object[7];
                row[0] = id;
                row[1] = name;
                row[2] = writerName;
                row[3] = edition;
                row[4] = publisher;
                row[5] = pages;
                row[6] = "Add";

                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        issueBookTable.setModel(tableModel);

        Action doSomething = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtRegistration.getText().equals("") || issueDateChooser.getDate() == null || returnDateChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "All Fields Are Required");
                } else {
                    //JTable table = (JTable) e.getSource();
                    int row = Integer.valueOf(e.getActionCommand());
                    issueBookTable.getSelectionModel().addSelectionInterval(row, row);
                    DefaultTableModel model = (DefaultTableModel) issueBookTable.getModel();
                    int rowIndex = issueBookTable.getSelectedRow();

                    issueBookStudent((int) model.getValueAt(rowIndex, 0), //Book Id
                            (String) model.getValueAt(row, 1), //Book Name
                            (String) model.getValueAt(row, 2), //Writer Name
                            (String) model.getValueAt(row, 3), //Edition
                            (String) model.getValueAt(row, 4), //Publisher
                            (int) model.getValueAt(row, 5)); //Pages
                }
            }
        };

        TableButton tableButton = new TableButton(issueBookTable, doSomething, 6);
    }
    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String search = txtSearch.getText();
        bookSearch(search);
    }//GEN-LAST:event_txtSearchKeyReleased

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
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new IssueBook().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JTable issueBookTable;
    private com.toedter.calendar.JDateChooser issueDateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private com.toedter.calendar.JDateChooser returnDateChooser;
    private javax.swing.JTextField txtBatch;
    private javax.swing.JTextField txtDepartment;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtRegistration;
    private javax.swing.JTextField txtRoll;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSession;
    private javax.swing.JTextField txtStudentName;
    // End of variables declaration//GEN-END:variables
}
