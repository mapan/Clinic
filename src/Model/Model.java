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
import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * @author panma
 */
public class Model {
    
    static String url = "jdbc:derby:Clinic";
            //"jdbc:derby:/Users/panma/Library/"
              //          + "Application Support/NetBeans/7.4/derby/Clinic";
    
    public static Comparator<String> int_cmp = new Comparator<String>() {
        public int compare(String s1, String s2) {
            int a = Integer.parseInt(s1);
            int b = Integer.parseInt(s2);
            return a - b;
        }
    };
    public static Comparator<String> double_cmp = new Comparator<String>() {
        public int compare(String s1, String s2) {
            Double a = Double.parseDouble(s1);
            Double b = Double.parseDouble(s2);
            return a.compareTo(b);
        }
    };
    
    public static HashMap<String,String> map = new HashMap<String,String>();
    
    public static void map() {
        map.put("ID", "ID");
        map.put("NAME", "名字");
        map.put("SEX", "性别");
        map.put("AGE", "年龄");
        map.put("CELL", "手机");
        map.put("PLACE", "产地");
        map.put("UNIT", "规格");
        map.put("SELL_PRICE", "售价");
        map.put("CONSUME", "消耗量");
        map.put("STOCK", "库存");
        map.put("DATE", "日期");
        map.put("PROBLEM", "症状");
        map.put("PRICE", "价格");
        map.put("DOSE", "剂量");
    }
    
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
    
    public static void update_patient(String[] info,int id) {
        Connection con = null;
        PreparedStatement st = null;
        String update = "UPDATE PATIENTS SET name=?,sex=?,age=?,weight=?,cell=?,phone=?,"
                + "address=?,allergy=?,history=? WHERE id="+id;
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
    
    // get newly added medicine info
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
    
    public static void update_stock(int mid,int dose) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT consume,stock from MEDICINE WHERE id="+mid;
        String update = "UPDATE MEDICINE SET consume=?,stock=? WHERE id="+mid;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            int consume = 0,stock = 0;
            if(rs.next()) {
                consume = Integer.parseInt(rs.getString(1))+dose;
                stock = Integer.parseInt(rs.getString(2))-dose;
            }
            st = con.prepareStatement(update);
            st.setString(1, consume+"");
            st.setString(2, stock+"");
            st.executeUpdate();
        } catch(SQLException err) {
            err.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
        
    // prescription table
    public static ArrayList pre_table(int rid) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList result = new ArrayList();
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("SELECT name,place,unit,dose,price FROM "
                    + "(SELECT * FROM PRESCRIPTION WHERE rid = ?)AS P "
                    + "INNER JOIN MEDICINE ON P.mid = MEDICINE.id");
            st.setInt(1, rid);
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
    
    public static int insert_record(String[] info,int pid) {
        String table = "RECORDS(pid,date,problem,price)";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("INSERT INTO " + table + " VALUES (?, ?, ?, ?)");
            st.setInt(1, pid);
            for (int i = 0; i < info.length; i++) {
                st.setString(i+2, info[i]);
            }
            st.executeUpdate();
            st = con.prepareStatement("SELECT id FROM RECORDS WHERE pid=? and date=? and problem=?");
            st.setInt(1, pid);
            for (int i = 0; i < info.length - 1; i++) {
                st.setString(i+2, info[i]);
            }
            rs = st.executeQuery();
            if(rs.next()) 
                id = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,rs);
        }
        return id;
    }
    
    public static void insert_prescription(int rid,int mid,String dose,String price) {
        String table = "PRESCRIPTION(rid,mid,dose,price)";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("INSERT INTO " + table + " VALUES (?, ?, ?, ?)");
            st.setInt(1, rid);
            st.setInt(2, mid);
            st.setString(3, dose);
            st.setString(4, price);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
    public static ArrayList record_table(int pid) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList result = new ArrayList();
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("SELECT id,date,problem,price FROM RECORDS WHERE pid = " + pid);
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
    
    public static void update_record_price(int rid,String price) {
        String update = "UPDATE RECORDS SET price=? WHERE id="+rid;
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement(update);
            st.setString(1, price);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
    public static void update_record(String[] info,int rid) {
        String update = "UPDATE RECORDS SET date=?,problem=?,price=? WHERE id="+rid;
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement(update);
            st.setString(1, info[0]);
            st.setString(2, info[1]);
            st.setString(3, info[2]);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
    public static String[] get_record_info(int id) {
        String[] info = new String[3];
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement("SELECT date,problem,price FROM RECORDS WHERE id = " + id);
            rs = st.executeQuery();
            if(rs.next()) {
                for(int i = 0;i < info.length;i++) {
                    info[i] = rs.getString(i+1);
                }
            }
        } catch(SQLException err) {
            err.printStackTrace();
        } finally {
            close(con,st,rs);
        }
        return info;
    }
    
    public static void delete_re_pre(int pid) {
        String update = "DELETE FROM (SELECT * FROM ("
                + "(SELECT id FROM RECORDS WHERE pid="+pid
                + ") AS R INNER JOIN PRESCRIPTION ON R.id=PRESCRIPTION.rid))";
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement(update);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
    public static void delete() {
        String update = "delete from records";
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = DriverManager.getConnection(url);
            st = con.prepareStatement(update);
            st.executeUpdate();
            update = "delete from prescription";
            st = con.prepareStatement(update);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con,st,null);
        }
    }
    
}
