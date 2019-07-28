/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storeaccountpassword;

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
public class DbClass {
    
    public static Connection con = null;
    public static Connection con2 = null;
    public static Connection con3 = null;
    
    public static PreparedStatement pst = null;
    public static PreparedStatement pst2 = null;
    public static PreparedStatement pst3 = null;
    
    public static ResultSet rs = null;
    public static ResultSet rs2 = null;
    
    public static Statement st = null;
    
    public static void createCon() {
        try {

            try {
                //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/StoreAccountPassword", "root", "Adittacse97@");
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:SAP.db", "", "");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DbClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException e) {

        }
    }
    
    public static void createCon2() {
        try {

            try {
                //con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/StoreAccountPassword", "root", "Adittacse97@");
                Class.forName("org.sqlite.JDBC");
                con2 = DriverManager.getConnection("jdbc:sqlite:SAP.db", "", "");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DbClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException e) {

        }
    }
    
    public static void createCon3() {
        try {

            try {
                //con3 = DriverManager.getConnection("jdbc:mysql://localhost:3306/StoreAccountPassword", "root", "Adittacse97@");
                Class.forName("org.sqlite.JDBC");
                con3 = DriverManager.getConnection("jdbc:sqlite:SAP.db", "", "");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DbClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException e) {

        }
    }
    
    public static void createSt() {
        try {
            st = con.createStatement();
        } catch (SQLException ex) {
            
        }
    }
    
    public static void closeCon() {
        try {
            con.close();
        } catch (SQLException ex) {
            
        }
    }
}
