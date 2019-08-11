/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author adittachakraborty
 */
public final class AddBook extends javax.swing.JFrame {

    int xMouse;
    int yMouse;
    String filename = null;

    /**
     * Creates new form AddBook
     */
    public AddBook() {
        initComponents();
        table();
        bookTable.setRowHeight(35);
    }

    private void selectBook(int id) {
        int bookId = id;
        try {
            DBclass.createCon();
            String query = "SELECT * FROM Book WHERE ID = " + bookId;
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                try {
                    InputStream is = DBclass.rs.getBinaryStream("BookImage");
                    Image im = ImageIO.read(is);
                    Image img2 = im.getScaledInstance(labelpic.getWidth(), labelpic.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon i = new ImageIcon(img2);
                    labelpic.setIcon(i);
                } catch (IOException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "Picture Not Found", "Error To Get Picture", JOptionPane.ERROR_MESSAGE);
                }
                txtBookName.setText(DBclass.rs.getString("BookName"));
                txtWriterName.setText(DBclass.rs.getString("WriterName"));
                txtEdition.setText(DBclass.rs.getString("Edition"));
                txtQuantity.setText(String.valueOf(DBclass.rs.getInt("Quantity")));
                txtPublisher.setText(DBclass.rs.getString("Publisher"));
                txtPages.setText(String.valueOf(DBclass.rs.getInt("Pages")));
                txtPrice.setText(String.valueOf(DBclass.rs.getInt("Price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void table() {
        DefaultTableModel tableModel = new DefaultTableModel();
        String columnNames[] = {"Book Id", "Book Name", "Writer Name", "Edition", "Quantity", "Publisher", "Pages", "Price", "Select Book"};
        tableModel.setColumnIdentifiers(columnNames);

        try {
            DBclass.createCon();
            String query = "select Id, BookName, WriterName, Edition, Quantity, Publisher, Pages, Price from Book";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                int id = DBclass.rs.getInt(1);
                String name = DBclass.rs.getString(2);
                String writerName = DBclass.rs.getString(3);
                String edition = DBclass.rs.getString(4);
                int quantity = DBclass.rs.getInt(5);
                String publisher = DBclass.rs.getString(6);
                int pages = DBclass.rs.getInt(7);
                int price = DBclass.rs.getInt(8);

                Object[] row = new Object[9];
                row[0] = id;
                row[1] = name;
                row[2] = writerName;
                row[3] = edition;
                row[4] = quantity;
                row[5] = publisher;
                row[6] = pages;
                row[7] = price;
                row[8] = "Select";

                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        bookTable.setModel(tableModel);

        Action doSomething = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JTable table = (JTable) e.getSource();
                int row = Integer.valueOf(e.getActionCommand());
                bookTable.getSelectionModel().addSelectionInterval(row, row);
                DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
                int rowIndex = bookTable.getSelectedRow();

                selectBook((int) model.getValueAt(rowIndex, 0)); //Book Id
            }
        };

        TableButton tableButton = new TableButton(bookTable, doSomething, 8);
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
        txtBookName = new javax.swing.JTextField();
        txtWriterName = new javax.swing.JTextField();
        txtEdition = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        txtPublisher = new javax.swing.JTextField();
        txtPages = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        labelpic = new javax.swing.JLabel();
        btnAttachImage = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Book");
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

        jLabel1.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel1.setText("Book name");

        jLabel2.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel2.setText("Writer Name");

        jLabel3.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel3.setText("Edition");

        jLabel4.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel4.setText("Quantity");

        jLabel5.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel5.setText("Publisher");

        jLabel6.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel6.setText("Price");

        jLabel7.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel7.setText("Pages");

        txtBookName.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtWriterName.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtEdition.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtQuantity.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtPublisher.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N

        txtPages.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtPages.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagesKeyTyped(evt);
            }
        });

        txtPrice.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        txtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPriceKeyTyped(evt);
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

        bookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book Id", "Book Name", "Writer Name", "Edition", "Quantity", "Publisher", "Pages", "Price"
            }
        ));
        jScrollPane1.setViewportView(bookTable);

        jLabel9.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        jLabel9.setText("Search Book");

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
        btnSave.setText("Save Book");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Monaco", 0, 14)); // NOI18N
        btnUpdate.setText("Update Book");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(jLabel8))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPages, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEdition, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtWriterName, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBookName, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAttachImage)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(labelpic, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(btnSave)
                        .addGap(60, 60, 60)
                        .addComponent(btnUpdate)))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(30, 30, 30)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnReset)
                        .addGap(301, 301, 301))
                    .addComponent(jScrollPane1))
                .addGap(20, 20, 20))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSave, btnUpdate});

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
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBookName))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtWriterName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEdition))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPublisher))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPages, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnUpdate)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1)))
                .addGap(25, 25, 25))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnSave, btnUpdate});

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

    private void txtPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtPriceKeyTyped
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (labelpic.getIcon() == null || txtBookName.getText().equals("") || txtWriterName.getText().equals("") || txtEdition.getText().equals("") || txtQuantity.getText().equals("") || txtPublisher.getText().equals("") || txtPages.getText().equals("") || txtPrice.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All Fields Are Required", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String bookName = txtBookName.getText();
                String writername = txtWriterName.getText();
                String edition = txtEdition.getText();

                DBclass.createCon();
                String query = "SELECT Id FROM Book WHERE Bookname = '" + bookName + "' AND WriterName = '" + writername + "' AND Edition = '" + edition + "'";
                DBclass.pst = DBclass.con.prepareStatement(query);
                DBclass.rs = DBclass.pst.executeQuery();
                if (DBclass.rs.next()) {
                    JOptionPane.showMessageDialog(this, "The Book Is Already Entered", "Information Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    DBclass.createCon();
                    String studentInsert = "INSERT INTO Book (BookImage, BookName, WriterName, Edition, Quantity, Publisher, Pages, Price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    DBclass.pst = DBclass.con.prepareStatement(studentInsert);

                    try {
                        DBclass.pst.setBinaryStream(1, pic, pic.available());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Picture Didn't Entered", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    DBclass.pst.setString(2, txtBookName.getText());
                    DBclass.pst.setString(3, txtWriterName.getText());
                    DBclass.pst.setString(4, txtEdition.getText());
                    int quantity = Integer.valueOf(txtQuantity.getText());
                    DBclass.pst.setInt(5, quantity);
                    DBclass.pst.setString(6, txtPublisher.getText());
                    int pages = Integer.valueOf(txtPages.getText());
                    DBclass.pst.setInt(7, pages);
                    int price = Integer.valueOf(txtPrice.getText());
                    DBclass.pst.setInt(8, price);

                    DBclass.pst.executeUpdate();
                    table();
                    JOptionPane.showMessageDialog(this, "Book Successfully Entered", "Information Message", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (labelpic.getIcon() == null || txtBookName.getText().equals("") || txtWriterName.getText().equals("") || txtEdition.getText().equals("") || txtQuantity.getText().equals("") || txtPublisher.getText().equals("") || txtPages.getText().equals("") || txtPrice.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "All Fields Are Required", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String bookName = txtBookName.getText();
                String writername = txtWriterName.getText();
                String edition = txtEdition.getText();

                DBclass.createCon();
                String query = "SELECT Id FROM Book WHERE BookName = '" + bookName + "' AND WriterName = '" + writername + "' AND Edition = '" + edition + "'";
                DBclass.pst = DBclass.con.prepareStatement(query);
                DBclass.rs = DBclass.pst.executeQuery();
                if (DBclass.rs.next()) {
                    int id = DBclass.rs.getInt(1);
                    String updateBook = "UPDATE Book SET BookName = ?, WriterName = ?, Edition = ?, Quantity = ?, Publisher = ?, pages = ?, price = ? WHERE Id = " + id;
                    DBclass.pst = DBclass.con.prepareStatement(updateBook);

                    DBclass.pst.setString(1, txtBookName.getText());
                    DBclass.pst.setString(2, txtWriterName.getText());
                    DBclass.pst.setString(3, txtEdition.getText());
                    int quantity = Integer.valueOf(txtQuantity.getText());
                    DBclass.pst.setInt(4, quantity);
                    DBclass.pst.setString(5, txtPublisher.getText());
                    int pages = Integer.valueOf(txtPages.getText());
                    DBclass.pst.setInt(6, pages);
                    int price = Integer.valueOf(txtPrice.getText());
                    DBclass.pst.setInt(7, price);

                    int k = DBclass.pst.executeUpdate();
                    if (k != 0) {
                        table();
                        JOptionPane.showMessageDialog(this, "Successfully Updated");
                    } else {
                        JOptionPane.showMessageDialog(this, "Not Updated", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Book Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearch.setText("");
        txtBookName.setText("");
        txtWriterName.setText("");
        txtEdition.setText("");
        txtQuantity.setText("");
        txtPublisher.setText("");
        txtPages.setText("");
        txtPrice.setText("");
        table();
    }//GEN-LAST:event_btnResetActionPerformed
    private void bookSearch(String bookSearch) {
        String book = bookSearch;
        DefaultTableModel tableModel = new DefaultTableModel();
        String columnnNames[] = {"Book Id", "Book Name", "Writer Name", "Edition", "Quantity", "Publisher", "Pages", "Price", "Select Book"};
        tableModel.setColumnIdentifiers(columnnNames);

        try {
            DBclass.createCon();
            String query = "select Id, BookName, WriterName, Edition, Quantity, Publisher, Pages, Price from Book WHERE BookName LIKE '%"
                    + book + "%' OR WriterName LIKE '%" + book + "%' OR Publisher LIKE '%" + book + "%'";
            DBclass.pst = DBclass.con.prepareStatement(query);
            DBclass.rs = DBclass.pst.executeQuery();
            while (DBclass.rs.next()) {
                int id = DBclass.rs.getInt(1);
                String name = DBclass.rs.getString(2);
                String writerName = DBclass.rs.getString(3);
                String edition = DBclass.rs.getString(4);
                int quantity = DBclass.rs.getInt(5);
                String publisher = DBclass.rs.getString(6);
                int pages = DBclass.rs.getInt(7);
                int price = DBclass.rs.getInt(8);

                Object[] row = new Object[9];
                row[0] = id;
                row[1] = name;
                row[2] = writerName;
                row[3] = edition;
                row[4] = quantity;
                row[5] = publisher;
                row[6] = pages;
                row[7] = price;
                row[8] = "Select";

                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        bookTable.setModel(tableModel);

        Action doSomething = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JTable table = (JTable) e.getSource();
                int row = Integer.valueOf(e.getActionCommand());
                bookTable.getSelectionModel().addSelectionInterval(row, row);
                DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
                int rowIndex = bookTable.getSelectedRow();

                selectBook((int) model.getValueAt(rowIndex, 0)); //Book Id
            }

        };

        TableButton tableButton = new TableButton(bookTable, doSomething, 8);
    }
    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String search = txtSearch.getText();
        bookSearch(search);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtPagesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagesKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtPagesKeyTyped

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
            java.util.logging.Logger.getLogger(AddBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AddBook().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bookTable;
    private javax.swing.JButton btnAttachImage;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTextField txtBookName;
    private javax.swing.JTextField txtEdition;
    private javax.swing.JTextField txtPages;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtPublisher;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtWriterName;
    // End of variables declaration//GEN-END:variables
}
