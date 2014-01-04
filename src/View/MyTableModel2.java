/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author panma
 */
public class MyTableModel2 extends AbstractTableModel {
    
    CachedRowSet rs;
    ResultSetMetaData meta;
    int cols, rows;
    
    public MyTableModel2(CachedRowSet r) {
        rs = r;
        try {
            meta = rs.getMetaData();
            cols = meta.getColumnCount();
            rs.beforeFirst();
            rows = 0;
            while (rs.next()) {
                rows++;
            }
            rs.beforeFirst();
        } catch(SQLException e) {
            e.printStackTrace();
        }          
    }
    
    public void insert(String[] info) {
        try {
            rs.moveToInsertRow();
            for(int i = 0;i < cols;i++) {
                rs.updateString(i+1, info[i]);
            }
            rs.insertRow();
            rs.moveToCurrentRow();
            this.fireTableDataChanged();
        } catch(SQLException e) {
            e.printStackTrace();
        } 
    }
    
    public void delete(int id) {
        try {
            rs.beforeFirst();
            while(rs.next()) {
                if(Integer.parseInt(rs.getString(1))==id) {
                    rs.deleteRow();
                    break;
                }
            }
            this.fireTableDataChanged();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
            
    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return cols;
    }

    @Override
    public String getColumnName(int col) {
        try {
            return meta.getColumnLabel(col + 1);
        } catch (SQLException e) {
            return e.toString();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            if (!rs.absolute(rowIndex + 1)) {
                return null;
            }
            rs.absolute(rowIndex + 1);
            Object o = rs.getObject(columnIndex + 1);
            if (o == null) {
                return null;
            } else {
                return o.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
    
}
