/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppFunction;

import View.JFrameMain;
import View.JIFrameUserLogin;
import View.JISalerManage;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author Administrator
 */
public class ExFunction {
    private Connection conn = Connect.DataConnection.getConnection();
    
    public static String encryptMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean userLogin(String username, String password){
        boolean login = false;
        String sql = "SELECT * FROM Users WHERE user_Name = ? AND user_Pass = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Login Success!");
                login = true;
            } else {
                JOptionPane.showMessageDialog(null, "Login Error!");
                login = false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ExFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return login;
    }
    
    public JInternalFrame userRole(String username, String password){
        JInternalFrame userframe = null;
        String sql = "SELECT * FROM Users WHERE user_Name = ? AND user_Pass = ?";
        String role = null;
        try {
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                role = rs.getString("user_Role");
            }
            if(role.equals("saler")){
                userframe = new JISalerManage();
                userframe.setVisible(true);
            } else if (role.equals("salma")) {
                userframe = new JISalerManage();
                userframe.setVisible(true);
            } else if (role.equals("invma")) {
                userframe = new JISalerManage();
                userframe.setVisible(true);
            } else if (role.equals("admin")) {
                userframe = new JISalerManage();
                userframe.setVisible(true);
            }
            
            //thiet lap khong di chuyen frame
            BasicInternalFrameUI ui = (BasicInternalFrameUI)userframe.getUI();
            Component north = ui.getNorthPane();
            MouseMotionListener[] actions = (MouseMotionListener[])north.getListeners(MouseMotionListener.class);
            for (int i = 0; i < actions.length; i++){
                north.removeMouseMotionListener( actions[i] );
            }
            //--end--khong di chuyen
            
            userframe.setSize(getMaxWidth(),getMaxHeight());
              
        } catch (SQLException ex) {
            Logger.getLogger(ExFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userframe;
    }
    
    public int getMaxWidth() {
          return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
     }
     public int getMaxHeight() {
         return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
     }
}

    