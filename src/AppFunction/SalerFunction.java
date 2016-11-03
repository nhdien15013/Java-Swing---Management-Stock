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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.management.remote.JMXConnectorFactory.connect;
import static javax.management.remote.JMXConnectorFactory.connect;
import static javax.rmi.PortableRemoteObject.connect;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class SalerFunction {
    private static Connection conn = Connect.DataConnection.getConnection();
    
    public int getIdentityId(String table){
        int Id = 0;
        String sql = "SELECT IDENT_CURRENT('"+table+"')";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Id;
    }
    
    public static String getNameById(String tablesql, String idcol_name ,int Id){
       String sql = "SELECT * FROM "+tablesql+" WHERE "+idcol_name+" = ?";
       String namebyid = null;
       try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                namebyid = rs.getString(2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
       return namebyid;
    }
    
    public static String getNameById(String tablesql, String idcol_name ,int Id, String datacol_name, String datacol_type){
       String sql = "SELECT * FROM "+tablesql+" WHERE "+idcol_name+" = ?";
       String namebyid = null;
       try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                if(datacol_type == "String"){
                    namebyid = rs.getString(datacol_name);
                } else if(datacol_type == "int"){
                    namebyid = String.valueOf(rs.getInt(datacol_name));
                } else if(datacol_type == "double"){
                    namebyid = String.valueOf(rs.getDouble(datacol_name));
                }
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
       return namebyid;
    }
    
    public static int getIdByName(String tablesql, String namecol_name ,String name){
       String sql = "SELECT * FROM "+tablesql+" WHERE "+namecol_name+" = ?";
       int idbyname = 0;
       try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                idbyname = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
       return idbyname;
    }
    
    public static void setDataToCombobox(JComboBox combobox, String tabledata, int type){
        String sql = "SELECT * FROM " + tabledata;
        if(type == 0){
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    combobox.addItem(rs.getString(2));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
            } 
        } else if(type == 1){
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    combobox.addItem(rs.getString(1)+"/"+rs.getString(2));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
    }
    
    //Produce Bill
    
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
                item.addElement(getNameById("Categories", "category_Id", rs.getInt("category_Id")));
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
                item.addElement(getNameById("Categories", "category_Id", rs.getInt("category_Id")));
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
        Scanner input = new Scanner(System.in);
        DefaultTableModel model_outtable = (DefaultTableModel) outtable.getModel();
        DefaultTableModel model_intable = (DefaultTableModel) intable.getModel();
        int[] indexs = outtable.getSelectedRows();
        Object[] cols = new Object[5];
        int test = 0;
        for(int i=0; i<indexs.length; i++){
            cols[0] = model_outtable.getValueAt(indexs[i], 0);
            cols[1] = model_outtable.getValueAt(indexs[i], 1);
            cols[2] = model_outtable.getValueAt(indexs[i], 2);
            try{
                cols[3] = Integer.parseInt(JOptionPane.showInputDialog("input quantity of "+model_outtable.getValueAt(indexs[i], 0)+" "+model_outtable.getValueAt(indexs[i], 1)));
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
            } catch (NumberFormatException numex){
                JOptionPane.showMessageDialog(null, "Please input number!");
            }
        }
    }
    
    public void delRowInTable(JTable deltable){
        DefaultTableModel model_intable = (DefaultTableModel) deltable.getModel();
        int[] indexs = deltable.getSelectedRows();
        int del = 1;
        for(int i=0; i<indexs.length; i++){
            model_intable.removeRow(indexs[i]);
            if(i<indexs.length-1){
                indexs[i+1] = indexs[i+1]-del;
                del = del+1;
            }
        }
    }
    
    public double totalPrice(JTable table,  int indexPricecol){
        int index = table.getRowCount();
        double tprice = 0;
        for(int i = 0; i<index; i++){
            tprice += Double.parseDouble(table.getValueAt(i, indexPricecol).toString());
        }
        return tprice;
    }
    
    public void submitBill(int user_id, int cust_id, Date bill_Date, String bill_Type){
        String sql = "INSERT INTO Bills(user_Id,cust_Id,bill_Date,bill_Status,bill_Type)"
                + " VALUES(?,?,?,'waiting',?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            stmt.setInt(2, cust_id);
            stmt.setDate(3, bill_Date);
            stmt.setString(4, bill_Type);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void submitBillDetail(JTable billtable, int billid){
        DefaultTableModel model_billtable = (DefaultTableModel) billtable.getModel();
        String sql = "INSERT INTO Bill_Details(bill_Id,item_Id,bill_I_Quantity)"
                + " VALUES(?,?,?,0)";
            for(int i=0; i<model_billtable.getRowCount(); i++){
                int _itemid = Integer.parseInt(model_billtable.getValueAt(i, 0).toString());
                int _quantity = Integer.parseInt(model_billtable.getValueAt(i, 3).toString());
                try {
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, billid);
                    stmt.setInt(2, _itemid);
                    stmt.setInt(3, _quantity);
                    stmt.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            JOptionPane.showMessageDialog(null, "Create bill success!");
    }
    
    
    //Customers
    public static void viewCust(JTable table){
        //vector luu ten cot
        Vector cols = new Vector();
        cols.addElement("ID");
        cols.addElement("Name");
        cols.addElement("Gender");
        cols.addElement("Tel");
        cols.addElement("Adress");
        cols.addElement("Purchase");
        //vector luu du lieu
        Vector data = new Vector();
        String sql = "SELECT * FROM Customers";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vector item = new Vector();
                item.addElement(rs.getInt("cust_Id"));
                item.addElement(rs.getString("cust_Name"));
                item.addElement(rs.getString("cust_Gender"));
                item.addElement(rs.getString("cust_Tel"));
                item.addElement(rs.getString("cust_Adress"));
                item.addElement(rs.getInt("cust_Purchase"));
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setModel(new DefaultTableModel(data, cols));
    }
    
    public static void searchCust(JTable table, String condition){
        //vector luu ten cot
        Vector cols = new Vector();
        cols.addElement("ID");
        cols.addElement("Name");
        cols.addElement("Gender");
        cols.addElement("Tel");
        cols.addElement("Adress");
        cols.addElement("Purchase");
        //vector luu du lieu
        Vector data = new Vector();
        String sql = "SELECT * FROM Customers" + condition;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vector item = new Vector();
                item.addElement(rs.getInt("cust_Id"));
                item.addElement(rs.getString("cust_Name"));
                item.addElement(rs.getString("cust_Gender"));
                item.addElement(rs.getString("cust_Tel"));
                item.addElement(rs.getString("cust_Adress"));
                item.addElement(rs.getInt("cust_Purchase"));
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setModel(new DefaultTableModel(data, cols));
    }
    
    public static String getTabledata(JTable tabledata, int colvalue){
        DefaultTableModel modeltable  = (DefaultTableModel) tabledata.getModel();
        int index = tabledata.getSelectedRow();
        String data = modeltable.getValueAt(index, colvalue).toString();
        return data;
    }
    
    public static void getTableToTextField(JTable tabledata, int colvalue, JTextField settextfield){
        DefaultTableModel modeltable  = (DefaultTableModel) tabledata.getModel();
        int index = tabledata.getSelectedRow();
        settextfield.setText(modeltable.getValueAt(index, colvalue).toString());
    }
    
    public static void getTableToTextField(JTable tabledata, int colvalue, JTextArea settextfield){
        DefaultTableModel modeltable  = (DefaultTableModel) tabledata.getModel();
        int index = tabledata.getSelectedRow();
        settextfield.setText(modeltable.getValueAt(index, colvalue).toString());
    }
    
    public static void getTableToCombobox(JTable tabledata, int colvalue, JComboBox setcombobox){
        DefaultTableModel modeltable  = (DefaultTableModel) tabledata.getModel();
        int index = tabledata.getSelectedRow();
        setcombobox.setSelectedItem(modeltable.getValueAt(index, colvalue).toString());
    }
    
    public static void editCust(int custid, String custname, String custgender, String custtel, String custadress){
        String sql = "UPDATE Customers SET "
                + "cust_Name=?,"
                + "cust_Gender=?,"
                + "cust_Tel=?,"
                + "cust_Adress=?"
                + " WHERE cust_Id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, custname);
            stmt.setString(2, custgender);
            stmt.setString(3, custtel);
            stmt.setString(4, custadress);
            stmt.setInt(5, custid);
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Update Customer info success!");
    }
    
    //Bill List
    public static void viewBillList(JTable table){
        //vector luu ten cot
        Vector cols = new Vector();
        cols.addElement("Bill ID");
        cols.addElement("Saler");
        cols.addElement("Customer");
        cols.addElement("Date");
        cols.addElement("Status");
        cols.addElement("Type");
        //vector luu du lieu
        Vector data = new Vector();
        String sql = "SELECT * FROM Bills";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vector item = new Vector();
                item.addElement(rs.getInt("bill_Id"));
                item.addElement(getNameById("Users","user_Id", rs.getInt("user_Id")));
                item.addElement(getNameById("Customers","cust_Id", rs.getInt("cust_Id")));
                item.addElement(rs.getDate("bill_Date"));
                item.addElement(rs.getString("bill_Status"));
                item.addElement(rs.getString("bill_Type"));
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setModel(new DefaultTableModel(data, cols));
    }
    
    public static void searchBillList(JTable table, String condition){
        //vector luu ten cot
        Vector cols = new Vector();
        cols.addElement("Bill ID");
        cols.addElement("Saler");
        cols.addElement("Customer");
        cols.addElement("Date");
        cols.addElement("Status");
        cols.addElement("Type");
        //vector luu du lieu
        Vector data = new Vector();
        String sql = "SELECT * FROM Bills"+condition;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vector item = new Vector();
                item.addElement(rs.getInt("bill_Id"));
                item.addElement(getNameById("Users","user_Id", rs.getInt("user_Id")));
                item.addElement(getNameById("Customers","cust_Id", rs.getInt("cust_Id")));
                item.addElement(rs.getDate("bill_Date"));
                item.addElement(rs.getString("bill_Status"));
                item.addElement(rs.getString("bill_Type"));
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setModel(new DefaultTableModel(data, cols));
    }
    
    public static void viewItemsByBillId(JTable table, int billid){
        //vector luu ten cot
        Vector cols = new Vector();
        cols.addElement("Bill ID");
        cols.addElement("Item ID");
        cols.addElement("Item Name");
        cols.addElement("Quantity");
        cols.addElement("Price");
        cols.addElement("Cancel");
        //vector luu du lieu
        Vector data = new Vector();
        String sql = "SELECT * FROM Bill_Details WHERE bill_Id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, billid);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vector item = new Vector();
                Double itemprice = Double.parseDouble(getNameById("Items", "item_Id", rs.getInt("item_Id"), "item_Price", "double"));
                item.addElement(rs.getInt("bill_Id"));
                item.addElement(rs.getInt("item_Id"));
                item.addElement(getNameById("Items","item_Id", rs.getInt("item_Id")));
                item.addElement(rs.getDouble("bill_I_Quantity"));
                item.addElement(itemprice*rs.getInt("bill_I_Quantity"));
                item.addElement((rs.getBoolean("bill_I_Cancel"))?"Cancel":"");
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalerFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setModel(new DefaultTableModel(data, cols));
    }
}

