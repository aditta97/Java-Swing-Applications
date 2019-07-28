/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storeaccountpassword;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author adittachakraborty
 */
public class InternetConnection {
    
    public static boolean InternetConnection(){
        Socket sock = new Socket();
        InetSocketAddress addr = new InetSocketAddress("www.google.com", 80);
        try {
            sock.connect(addr);
            //JOptionPane.showMessageDialog(null,"You are connected!");
            return true;
        } catch (IOException e) {
            //JOptionPane.showMessageDialog(null,"Please check your Internet Connection!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Socket Didn't Closed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
