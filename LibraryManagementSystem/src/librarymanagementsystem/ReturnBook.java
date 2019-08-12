/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author adittachakraborty
 */
public final class ReturnBook extends javax.swing.JFrame {

    int xMouse;
    int yMouse;
    String filename = null;

    /**
     * Creates new form AddBook
     *
     */
    public ReturnBook() {
        initComponents();
        table();
        remainingTime();
        JFrameIcon();
        returnBookTable.setRowHeight(35);
    }
    
    //Setting an Icon for jFrame
    private void JFrameIcon() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Image/icons8-book_shelf.png")));
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
        returnBookTable.getColumnModel().getColumn(6).setCellRenderer(tableCellRenderer);
    }

    public void remainingTime() {
        try {
            DBclass.createCon();
            //Getting RemainingTime of Book from Issue Book Table
            String query = "SELECT Id, ReturnDate FROM IssueBook";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                int id = DBclass.rs.getInt(1);

                java.sql.Date date = DBclass.rs.getDate(2);
                //Converting sql date to Util date format
                java.util.Date returnDate = new java.util.Date(date.getTime());

                //Getting current date in localdate format
                LocalDate localDate = LocalDate.now();
                //Converting localdate to Util date format
                java.util.Date currentDate = java.sql.Date.valueOf(localDate);

                //Getting difference between two Util days
                long difference = currentDate.getTime() - returnDate.getTime();
                //Calculating & formatinng in Integer
                int remainingDays = (int) (difference / (1000 * 60 * 60 * 24));

                if (remainingDays < 0) {
                    int positiveOfRemainningDays = Math.abs(remainingDays);
                    String daysRemaing = " Days Remaining";
                    String remain = String.valueOf(positiveOfRemainningDays);
                    String remainDays = remain + daysRemaing;

                    //It has previous value. So Updating real value in String after calculating remaining days
                    String query2 = "UPDATE IssueBook SET RemainingTime = ? WHERE id = " + id;
                    DBclass.pst = DBclass.con.prepareStatement(query2);
                    DBclass.pst.setString(1, remainDays);
                    DBclass.pst.executeUpdate();
                } else if (remainingDays > 0) {
                    String expired = "Expired ";
                    String text = " Days Ago";
                    String days = String.valueOf(remainingDays);
                    String finalDays = expired + days + text;

                    //It has previous value. So Updating real value in String after calculating remaining days
                    String query2 = "UPDATE IssueBook SET RemainingTime = ? WHERE id = " + id;
                    DBclass.pst = DBclass.con.prepareStatement(query2);
                    DBclass.pst.setString(1, finalDays);
                    DBclass.pst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * After returning the book, this method <br>
     * remove Issue Book fromm Issue table
     *
     * @param Id Issue Id
     */
    void returnIssuedBook(int Id) {
        int issueId = Id;
        try {
            DBclass.createCon();
            String query = "DELETE FROM IssueBook WHERE id = " + issueId;
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.pst.executeUpdate();
            table();
            JOptionPane.showMessageDialog(this, "Book Successfully Retured", "Information Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void table() {
        DefaultTableModel tableModel = new DefaultTableModel();
        String columnNames[] = {"Issue Id", "Student Id", "Student Name", "Book Id", "Book Name", "Writer Name", "Return date", "Remaining Time", "Department", "Roll", "Batch", "Phone Number", "Edition", "Return Book"};
        tableModel.setColumnIdentifiers(columnNames);

        try {
            DBclass.createCon();
            String query = "select IssueBook.Id, StudentId, StudentName, BookId, BookName, WriterName, ReturnDate, RemainingTime, Department, Roll, Batch, PhoneNumber, Edition from IssueBook, Student, Book WHERE Student.Registration = IssueBook.StudentId AND Book.Id = IssueBook.BookId";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                int id = DBclass.rs.getInt(1);
                int studentId = DBclass.rs.getInt(2);
                String studentName = DBclass.rs.getString(3);
                int bookId = DBclass.rs.getInt(4);
                String bookName = DBclass.rs.getString(5);
                String writerName = DBclass.rs.getString(6);
                Date returnDate = DBclass.rs.getDate(7);
                String remainingTime = DBclass.rs.getString(8);
                String department = DBclass.rs.getString(9);
                int roll = DBclass.rs.getInt(10);
                String batch = DBclass.rs.getString(11);
                String phoneNumber = DBclass.rs.getString(12);
                String edition = DBclass.rs.getString(13);

                Object[] row = new Object[14];
                row[0] = id;
                row[1] = studentId;
                row[2] = studentName;
                row[3] = bookId;
                row[4] = bookName;
                row[5] = writerName;
                row[6] = returnDate;
                row[7] = remainingTime;
                row[8] = department;
                row[9] = roll;
                row[10] = batch;
                row[11] = phoneNumber;
                row[12] = edition;
                row[13] = "Return";

                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        returnBookTable.setModel(tableModel);

        //For set the column width
        TableColumnModel columnModel = returnBookTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20); //Issue Id column
        columnModel.getColumn(3).setPreferredWidth(20); //Book Id column
        columnModel.getColumn(9).setPreferredWidth(20); //Roll column
        columnModel.getColumn(10).setPreferredWidth(20); //Batch column
        columnModel.getColumn(12).setPreferredWidth(20); //Edition column
        columnModel.getColumn(13).setPreferredWidth(40); //Remove column

        Action doSomething = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JTable table = (JTable) e.getSource();
                int row = Integer.valueOf(e.getActionCommand());
                returnBookTable.getSelectionModel().addSelectionInterval(row, row);
                DefaultTableModel model = (DefaultTableModel) returnBookTable.getModel();
                int rowIndex = returnBookTable.getSelectedRow();

                returnIssuedBook((int) model.getValueAt(rowIndex, 0)); //Issue Id;
            }
        };

        TableButton tableButton = new TableButton(returnBookTable, doSomething, 13);
        ChangeTableDateFormat();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        returnBookTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();

        pic2.setText("jLabel1");
        pic2.setPreferredSize(new java.awt.Dimension(200, 200));
        pic2.setSize(new java.awt.Dimension(200, 200));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Return Book");
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

        returnBookTable.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        returnBookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Issue Id", "Student Id", "Student Name", "Book Id", "Book Name", "Writer Name", "Return Date", "Remaining Time", "Department", "Roll", "Batch", "Phone Number", "Edition"
            }
        ));
        jScrollPane1.setViewportView(returnBookTable);

        jLabel9.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel9.setText("Search ");

        txtSearch.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnReset.setText("Reset");
        btnReset.setPreferredSize(new java.awt.Dimension(74, 30));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(431, 431, 431)
                        .addComponent(jLabel9)
                        .addGap(30, 30, 30)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                .addGap(20, 20, 20))
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

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearch.setText("");
        table();
    }//GEN-LAST:event_btnResetActionPerformed
    private void bookSearch(String search) {
        String text = search;
        if (!txtSearch.getText().isEmpty()) {
            DefaultTableModel tableModel = new DefaultTableModel();
            String columnnNames[] = {"Issue Id", "Student Id", "Student Name", "Book Id", "Book Name", "Writer Name", "Return date", "Remaining Time", "Department", "Roll", "Batch", "Phone Number", "Edition", "Return Book"};
            tableModel.setColumnIdentifiers(columnnNames);

            try {
                DBclass.createCon();
                String query = "select IssueBook.Id, StudentId, StudentName, BookId, BookName, WriterName, ReturnDate, "
                        + "RemainingTime, Department, Roll, Batch, PhoneNumber, Edition FROM IssueBook, Student, Book WHERE "
                        + "Student.Registration = IssueBook.StudentId AND Book.Id = IssueBook.BookId "
                        + "AND IssueBook.StudentId LIKE '%" + text + "%' OR Book.BookName LIKE '%"
                        + text + "%' OR Student.StudentName LIKE '%" + text + "%'";
                DBclass.pst = DBclass.con.prepareStatement(query);
                DBclass.rs = DBclass.pst.executeQuery();
                while (DBclass.rs.next()) {
                    int id = DBclass.rs.getInt(1);
                    int studentId = DBclass.rs.getInt(2);
                    String studentName = DBclass.rs.getString(3);
                    int bookId = DBclass.rs.getInt(4);
                    String bookName = DBclass.rs.getString(5);
                    String writerName = DBclass.rs.getString(6);
                    Date returnDate = DBclass.rs.getDate(7);
                    String remainingTime = DBclass.rs.getString(8);
                    String department = DBclass.rs.getString(9);
                    int roll = DBclass.rs.getInt(10);
                    String batch = DBclass.rs.getString(11);
                    String phoneNumber = DBclass.rs.getString(12);
                    String edition = DBclass.rs.getString(13);

                    Object[] row = new Object[14];
                    row[0] = id;
                    row[1] = studentId;
                    row[2] = studentName;
                    row[3] = bookId;
                    row[4] = bookName;
                    row[5] = writerName;
                    row[6] = returnDate;
                    row[7] = remainingTime;
                    row[8] = department;
                    row[9] = roll;
                    row[10] = batch;
                    row[11] = phoneNumber;
                    row[12] = edition;
                    row[13] = "Return";

                    tableModel.addRow(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            returnBookTable.setModel(tableModel);

            //For set the column width
            TableColumnModel columnModel = returnBookTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(20); //Issue Id column
            columnModel.getColumn(3).setPreferredWidth(20); //Book Id column
            columnModel.getColumn(9).setPreferredWidth(20); //Roll column
            columnModel.getColumn(10).setPreferredWidth(20); //Batch column
            columnModel.getColumn(12).setPreferredWidth(20); //Edition column
            columnModel.getColumn(13).setPreferredWidth(40); //Remove column

            Action doSomething = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //JTable table = (JTable) e.getSource();
                    int row = Integer.valueOf(e.getActionCommand());
                    returnBookTable.getSelectionModel().addSelectionInterval(row, row);
                    DefaultTableModel model = (DefaultTableModel) returnBookTable.getModel();
                    int rowIndex = returnBookTable.getSelectedRow();

                    returnIssuedBook((int) model.getValueAt(rowIndex, 0)); //Issue Id;
                }
            };

            TableButton tableButton = new TableButton(returnBookTable, doSomething, 13);
            ChangeTableDateFormat();
        } else {
            table();

            //For set the column width
            TableColumnModel columnModel = returnBookTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(20); //Issue Id column
            columnModel.getColumn(3).setPreferredWidth(20); //Book Id column
            columnModel.getColumn(9).setPreferredWidth(20); //Roll column
            columnModel.getColumn(10).setPreferredWidth(20); //Batch column
            columnModel.getColumn(12).setPreferredWidth(20); //Edition column
            columnModel.getColumn(13).setPreferredWidth(40); //Remove column
        }
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
            java.util.logging.Logger.getLogger(ReturnBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            Properties props = new Properties();
            props.put("logoString", "AEC");
            BernsteinLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ReturnBook().setVisible(true);
            //id, studentId, bookId, returnDate
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel pic2;
    private javax.swing.JTable returnBookTable;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
