/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author panma
 */
public class Model {
    
    static String url = "jdbc:derby:/Users/panma/Library/"
                        + "Application Support/NetBeans/7.4/derby/Clinic";
    
    public static void close(Connection con,PreparedStatement st,ResultSet rs) {
        try {
            if(con != null) con.close();
            if(st != null) st.close();
            if(rs != null) rs.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    // fill table in Home screen
    public static ArrayList patient_table() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList result = new ArrayList();
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("SELECT id,name,sex,age,cell FROM PATIENTS");
            rs = st.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            String[] col = new String[meta.getColumnCount()];
            for(int i = 0;i < col.length;i++)
                col[i] = meta.getColumnLabel(i+1);
            ArrayList<String> row;
            while(rs.next()) {
                row = new ArrayList<String>();
                for(int i = 0;i < col.length;i++)
                    row.add(rs.getString(i+1));
                data.add(row);
            }
            result.add(data);
            result.add(col);
            
        } catch(SQLException err) {
            err.printStackTrace();
        } finally {
            close(con,st,rs);
        }
        return result;
    }
    
    // insert new patient info
    public static void insert_patient(String[] info) {
        String table = "PATIENTS(name,sex,age,weight,cell,phone,address,allergy,history)";
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("INSERT INTO " + table + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < info.length; i++) {
                st.setString(i+1, info[i]);
            }
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
    // insert newly added patient into the jtable
    public static ArrayList<String> get_new_patient(String[] in) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<String> row = new ArrayList<String>();
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("SELECT id,name,sex,age,cell FROM PATIENTS WHERE name = '" + in[0] + "' and"
                    + " sex = '" + in[1] + "' and age = '" + in[2]+ "'");
            rs = st.executeQuery();
            if(rs.next()) {
                for(int i = 0;i < 5;i++) {
                    row.add(rs.getString(i+1));
                }
            }
        } catch(SQLException err) {
            err.printStackTrace();
        } finally {
            close(con,st,rs);
        }
        return row;
    }
    
    // delete a patient info
    public static void delete_patient(int id) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("DELETE FROM PATIENTS WHERE id = " + id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
    // populate forms when double click
    public static String[] get_patient_info(int id) {
        String[] info = new String[9];
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("SELECT * FROM PATIENTS WHERE id = " + id);
            rs = st.executeQuery();
            if(rs.next()) {
                for(int i = 0;i < 9;i++) {
                    info[i] = rs.getString(i+2);
                }
            }
        } catch(SQLException err) {
            err.printStackTrace();
        } finally {
            close(con,st,rs);
        }
        return info;
    }
    
    public static ArrayList med_table() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList result = new ArrayList();
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("SELECT id,name,place,unit,sell_price,consume,stock FROM MEDICINE");
            rs = st.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            String[] col = new String[meta.getColumnCount()];
            for(int i = 0;i < col.length;i++)
                col[i] = meta.getColumnLabel(i+1);
            ArrayList<String> row;
            while(rs.next()) {
                row = new ArrayList<String>();
                for(int i = 0;i < col.length;i++)
                    row.add(rs.getString(i+1));
                data.add(row);
            }
            result.add(data);
            result.add(col);
            
        } catch(SQLException err) {
            err.printStackTrace();
        } finally {
            close(con,st,rs);
        }
        return result;
    }
    
    public static void insert_medicine(String[] info) {
        String table = "MEDICINE(name,place,unit,raw_price,sell_price,purchase,consume,stock,expire,date)";
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("INSERT INTO " + table + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (int i = 0; i < info.length; i++) {
                st.setString(i+1, info[i]);
            }
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
    // insert newly added medicine into the jtable
    public static ArrayList<String> get_new_medicine(String[] in) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<String> row = new ArrayList<String>();
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("SELECT id,name,place,unit,sell_price,consume,stock FROM MEDICINE WHERE name = '" + in[0] + "' and"
                    + " place = '" + in[1] + "' and unit = '" + in[2]+ "'");
            rs = st.executeQuery();
            if(rs.next()) {
                for(int i = 0;i < rs.getMetaData().getColumnCount();i++) {
                    row.add(rs.getString(i+1));
                }
            }
        } catch(SQLException err) {
            err.printStackTrace();
        } finally {
            close(con,st,rs);
        }
        return row;
    }
    
    public static void delete_medicine(int id) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("DELETE FROM MEDICINE WHERE id = " + id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
    // populate forms when double click
    public static String[] get_medicine_info(int id) {
        String[] info = new String[10];
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("SELECT * FROM MEDICINE WHERE id = " + id);
            rs = st.executeQuery();
            if(rs.next()) {
                for(int i = 0;i < info.length;i++) {
                    info[i] = rs.getString(i+2);
                }
            }
        } catch(SQLException err) {
            err.printStackTrace();
        } finally {
            close(con,st,rs);
        }
        return info;
    }
    
    // update med table
    public static void update_medicine(String[] info,int id) {
        Connection con = null;
        PreparedStatement st = null;
        String update = "UPDATE MEDICINE SET name=?,place=?,unit=?,raw_price=?,sell_price=?,purchase=?,"
                + "consume=?,stock=?,expire=?,date=? WHERE id="+id;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement(update);
            for (int i = 0; i < info.length; i++) {
                st.setString(i+1, info[i]);
            }
            st.executeUpdate();
        } catch(SQLException err) {
            err.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
}
