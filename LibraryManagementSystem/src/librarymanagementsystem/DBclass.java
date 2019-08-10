/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adittachakraborty
 */
public class DBclass {
    
    public static Connection con = null;
    public static Connection con2 = null;
    public static PreparedStatement pst = null;
    public static PreparedStatement pst2 = null;
    public static ResultSet rs = null;
    public static ResultSet rs2 = null;
    public static Statement st = null;
    
    
    public static void createCon() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystemDB", "root", "Adittacse97@");
        } catch (SQLException e) {

        }
    }
    
    public static void createCon2() {
        try {
            con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystemDB", "root", "Adittacse97@");
        } catch (SQLException e) {

        }
    }
    
    public static void createSt() {
        try {
            st = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closeCon() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closeCon2() {
        try {
            con2.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBclass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
