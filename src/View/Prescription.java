/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Model.Model;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author panma
 */
public class Prescription extends javax.swing.JFrame {
    
    MyTableModel med_tm,pre_tm,record_tm,m_tm;
    TableRowSorter<MyTableModel> sorter;
    int pid,rid;
    double total_price = 0;
    
    /**
     * Creates new form Prescription
     */
    public Prescription(int rid) {
        med_tm = new MyTableModel(Model.med_table());
        pre_tm = new MyTableModel(Model.pre_table(rid));
        this.rid = rid;
        // UI
        initComponents();
        // get date
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String d = dateFormat.format(new Date());
        date.setText(d);
        // set table property
        set_med_table();
        set_pre_table();
    }
    
    private void set_pre_table() {
        pre_tm.addTableModelListener(
                new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        pre_table.setModel(pre_tm);
                    }
                });
    }
    
    private void set_med_table() {
        sorter = new TableRowSorter<MyTableModel>(med_tm);
        sorter.setComparator(4, Model.double_cmp);
        sorter.setComparator(5, Model.int_cmp);
        sorter.setComparator(6, Model.int_cmp);
        med_table.setRowSorter(sorter);
        find.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        filter();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        filter();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        filter();
                    }
                });
        // double click to add dose
        med_table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    Point p = me.getPoint();
                    int row = med_table.rowAtPoint(p);
                    int mid = Integer.parseInt((String) med_table.getValueAt(row, 0));
                    String name = (String) med_table.getValueAt(row, 1);
                    String stock = (String) med_table.getValueAt(row, 6);
                    String consume = (String) med_table.getValueAt(row, 5);
                    double sell_price = Double.parseDouble((String)med_table.getValueAt(row, 4));
                    String dose = JOptionPane.showInputDialog(null, "输入处方剂量: (库存:"+stock+")",
                            name,JOptionPane.PLAIN_MESSAGE);
                    if ((dose != null) && (dose.length() > 0)) {
                        // if it's the first prescription, then create new record first
                        if(rid == 0) {
                            String[] info = new String[] {date.getText(),problem.getText(),""};
                            rid = Model.insert_record(info, pid);
                        }
                        // insert new prescription
                        double price = sell_price*(Integer.parseInt(dose));
                        total_price += price;
                        // update total price label
                        total.setText(total_price+"");
                        Model.insert_prescription(rid, mid, dose, price+"");
                        // get newly added prescription info and add into pre table
                        String[] info = Model.get_medicine_info(mid);
                        ArrayList<String> data = new ArrayList<String>();
                        data.add(info[0]); data.add(info[1]); data.add(info[2]);
                        data.add(dose); data.add(price+"");
                        pre_tm.insert(data);
                        // update med stock and consume in db
                        Model.update_stock(mid, Integer.parseInt(dose));
                        // update stock in the med table
                        int c = Integer.parseInt(consume)+Integer.parseInt(dose);
                        int s = Integer.parseInt(stock)-Integer.parseInt(dose);
                        med_tm.setValueAt(c+"", row, 5);
                        med_tm.setValueAt(s+"", row, 6);
                        m_tm.setValueAt(c+"", row, 5);
                        m_tm.setValueAt(s+"", row, 6);
                    }
                }
            }
        });
    }
    
    private void filter() {
        RowFilter<MyTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(find.getText(), 1);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
    
    public void populate(String date,String problem,String price) {
        this.date.setText(date); 
        this.problem.setText(problem);
        this.total.setText(price);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label = new javax.swing.JLabel();
        l1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        problem = new javax.swing.JTextArea();
        date3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pre_table = new javax.swing.JTable();
        find = new javax.swing.JTextField();
        date4 = new javax.swing.JLabel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        med_table = new javax.swing.JTable();
        date = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("新建处方");

        label.setText("日期:");

        l1.setText("症状:");

        problem.setColumns(20);
        problem.setRows(5);
        jScrollPane1.setViewportView(problem);

        date3.setText("药品:");

        pre_table.setModel(pre_tm);
        jScrollPane2.setViewportView(pre_table);

        find.setToolTipText("输入药品名字");

        date4.setText("处方:");

        save.setText("保存");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        cancel.setText("取消");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        med_table.setModel(med_tm);
        jScrollPane4.setViewportView(med_table);

        jLabel1.setText("总价:");

        total.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(l1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(date4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(16, 16, 16))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(date3)
                                    .addGap(56, 56, 56)
                                    .addComponent(find, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                .addComponent(jScrollPane2)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(322, 322, 322)
                            .addComponent(save)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancel))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(l1))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(find, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(date4)
                    .addComponent(jLabel1)
                    .addComponent(total))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(save)
                    .addComponent(cancel))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // TODO add your handling code here:
        if(this.getTitle().equals("新建处方")) {
            // update total price in record table
            Model.update_record_price(rid, total_price+"");
            // insert into record table model
            ArrayList<String> row = new ArrayList<String>();
            row.add(rid + "");
            row.add(date.getText());
            row.add(problem.getText());
            row.add(total_price + "");
            record_tm.insert(row);
        }
        else {
            String[] cur = new String[] {date.getText(),problem.getText(),total_price+""};
            String[] old = Model.get_record_info(rid);
            if(!Arrays.equals(cur, old)) {
                Model.update_record(cur, rid);
                ArrayList<String> a = new ArrayList<String>();
                a.add(rid+"");
                a.add(cur[0]); a.add(cur[1]); a.add(cur[2]);
                record_tm.update(rid, a);
            }
        }
        dispose();
    }//GEN-LAST:event_saveActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_cancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JTextField date;
    private javax.swing.JLabel date3;
    private javax.swing.JLabel date4;
    private javax.swing.JTextField find;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel l1;
    private javax.swing.JLabel label;
    private javax.swing.JTable med_table;
    private javax.swing.JTable pre_table;
    private javax.swing.JTextArea problem;
    private javax.swing.JButton save;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
