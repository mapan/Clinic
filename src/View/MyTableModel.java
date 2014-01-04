/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author panma
 */
public class MyTableModel extends AbstractTableModel {

    ArrayList<ArrayList<String>> data;
    String[] col;
    
    public MyTableModel(ArrayList result) {
        data = (ArrayList<ArrayList<String>>)result.get(0);
        col = (String[])result.get(1);
    }
    
    public void insert(ArrayList<String> row) {
        data.add(row);
        fireTableDataChanged();
    }
    
    public void delete(int id) {
        for(int i = 0;i < data.size();i++) {
            if(Integer.parseInt(data.get(i).get(0)) == id) {
                data.remove(i);
                break;
            }
        }
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return col.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return col[columnIndex];
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
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
    
}
