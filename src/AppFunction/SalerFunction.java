/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppFunction;

import Connect.DataConnection;
import View.JISalerManage;
import static com.sun.corba.se.impl.presentation.rmi.StubConnectImpl.connect;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.connect;
import static com.sun.jmx.remote.internal.IIOPHelper.connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.management.remote.JMXConnectorFactory.connect;
import static javax.management.remote.JMXConnectorFactory.connect;
import static javax.rmi.PortableRemoteObject.connect;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class SalerFunction {
    private static Connection conn = Connect.DataConnection.getConnection();
    
    public static String getNameById(String tablesql, int category_Id){
       String sql = "SELECT * FROM "+tablesql+" WHERE category_Id = ?";
       String namebyid = null;
       try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, category_Id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                namebyid = rs.getString("category_Name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
       return namebyid;
    }

    public static void getDataToCombobox(JComboBox combobox, String tabledata){
        String sql = "SELECT * FROM " + tabledata;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                combobox.addItem(rs.getString("category_Name"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void viewItemToTable(JTable table){
        //vector luu ten cot
        Vector cols = new Vector();
        cols.addElement("Item ID");
        cols.addElement("Item Name");
        cols.addElement("Category");
        cols.addElement("Quantity");
        cols.addElement("Price");
        //vector luu du lieu
        Vector data = new Vector();
        String sql = "SELECT * FROM Items";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vector item = new Vector();
                item.addElement(rs.getInt("item_Id"));
                item.addElement(rs.getString("item_Name"));
                item.addElement(getNameById("Categories",rs.getInt("category_Id")));
                item.addElement(rs.getFloat("item_Quantity"));
                item.addElement(rs.getDouble("item_Price"));
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       table.setModel(new DefaultTableModel(data, cols));
        
    }
    
    public static void searchItem(JTable table, String condition){
        //vector luu ten cot
        Vector cols = new Vector();
        cols.addElement("Item ID");
        cols.addElement("Item Name");
        cols.addElement("Category");
        cols.addElement("Quantity");
        cols.addElement("Price");
        //vector luu du lieu
        Vector data = new Vector();
        String sql = "SELECT * FROM Items" + condition;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vector item = new Vector();
                item.addElement(rs.getInt("item_Id"));
                item.addElement(rs.getString("item_Name"));
                item.addElement(getNameById("Categories",rs.getInt("category_Id")));
                item.addElement(rs.getFloat("item_Quantity"));
                item.addElement(rs.getDouble("item_Price"));
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setModel(new DefaultTableModel(data, cols));
    }
    
    public void addItemToBill(JTable outtable, JTable intable){
        DefaultTableModel model_outtable = (DefaultTableModel) outtable.getModel();
        DefaultTableModel model_intable = (DefaultTableModel) intable.getModel();
        int[] indexs = outtable.getSelectedRows();
        Object[] cols = new Object[5];
        int test = 0;
        for(int i=0; i<indexs.length; i++){
            cols[0] = model_outtable.getValueAt(indexs[i], 0);
            cols[1] = model_outtable.getValueAt(indexs[i], 1);
            cols[2] = model_outtable.getValueAt(indexs[i], 2);
            cols[3] = JOptionPane.showInputDialog("input quantity of "+model_outtable.getValueAt(indexs[i], 0)+" "+model_outtable.getValueAt(indexs[i], 1));
            cols[4] = Double.parseDouble(model_outtable.getValueAt(indexs[i], 4).toString()) * Double.parseDouble(cols[3].toString());
            for(int x=0; x<model_intable.getRowCount(); x++){
                if(cols[0].toString().equals(model_intable.getValueAt(x, 0).toString())){
                    int quantity = Integer.parseInt(model_intable.getValueAt(x, 3).toString())+Integer.parseInt(cols[3].toString());
                    model_intable.setValueAt(quantity,x, 3);
                    model_intable.setValueAt(quantity*Double.parseDouble(model_outtable.getValueAt(indexs[i], 4).toString()),x, 4);
                    test = 1;
                    break;
                } else {
                    test = 0;
                }
            }
            if(test == 0){
                model_intable.addRow(cols);
            }
        }
    }
    
    public double totalPrice(JTable table){
        int index = table.getRowCount();
        double tprice = 0;
        for(int i = 0; i<index; i++){
            tprice += Double.parseDouble(table.getValueAt(i, 4).toString());
        }
        return tprice;
    }
}
