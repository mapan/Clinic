/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Model.Model;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class Home extends javax.swing.JFrame {
    
    MyTableModel patient_tm,med_tm;
    TableRowSorter<MyTableModel> patient_sorter,med_sorter;

    /**
     * Creates new form Home
     */
    public Home() {
        Model.map();
        // set table model
        patient_tm = new MyTableModel(Model.patient_table());
        med_tm = new MyTableModel(Model.med_table());
        // UI
        initComponents();
        // set up two tables
        set_patient_table();
        set_med_table();
    }
    
    // set table model property
    private void set_patient_table() {
        patient_tm.addTableModelListener(
                new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        patient_table.setModel(patient_tm);
                    }
                });
        // set filter for table
        patient_sorter = new TableRowSorter<MyTableModel>(patient_tm);
        patient_table.setRowSorter(patient_sorter);
        find_patient.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        filter(true);
                    }

                    public void insertUpdate(DocumentEvent e) {
                        filter(true);
                    }

                    public void removeUpdate(DocumentEvent e) {
                        filter(true);
                    }
                });
        // double click to show the details
        patient_table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    Point p = me.getPoint();
                    int row = patient_table.rowAtPoint(p);
                    String id = (String) patient_table.getValueAt(row, 0);
                    NewRecord detail = new NewRecord(Integer.parseInt(id));
                    detail.setTitle("病人详细信息");
                    detail.yes.setText("确定");
                    detail.patient_tm = patient_tm;
                    detail.m_tm = med_tm;
                    detail.populate(Model.get_patient_info(Integer.parseInt(id)));
                    detail.setVisible(true);
                }
            }
        });
    }
    
    private void set_med_table() {
        med_tm.addTableModelListener(
                new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        med_table.setModel(med_tm);
                    }
                });
        // set filter for table
        med_sorter = new TableRowSorter<MyTableModel>(med_tm);
        med_table.setRowSorter(med_sorter);
        find_med.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        filter(false);
                    }

                    public void insertUpdate(DocumentEvent e) {
                        filter(false);
                    }

                    public void removeUpdate(DocumentEvent e) {
                        filter(false);
                    }
                });
        // double click to show the details
        med_table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    Point p = me.getPoint();
                    int row = med_table.rowAtPoint(p);
                    String id = (String) med_table.getValueAt(row, 0);
                    NewMedicine detail = new NewMedicine();
                    detail.setTitle("药品详细信息");
                    detail.save.setText("确定");
                    detail.id = Integer.parseInt(id);
                    detail.tm = med_tm;
                    detail.populate(Model.get_medicine_info(Integer.parseInt(id)));
                    detail.setVisible(true);
                }
            }
        });
    }
    
    private void filter(boolean b) {
        RowFilter<MyTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter((b?find_patient:find_med).getText(), 1);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        (b?patient_sorter:med_sorter).setRowFilter(rf);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        find_patient = new javax.swing.JTextField();
        clear = new javax.swing.JButton();
        create = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        patient_table = new javax.swing.JTable();
        delete = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        find_med = new javax.swing.JTextField();
        new_med = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        med_table = new javax.swing.JTable();
        delete_med = new javax.swing.JButton();
        clear1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("诊所管理");

        tabbedPane.setToolTipText("");

        jPanel2.setToolTipText("");

        find_patient.setToolTipText("输入姓名");

        clear.setText("清空");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        create.setText("新建");
        create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createActionPerformed(evt);
            }
        });

        patient_table.setModel(patient_tm);
        jScrollPane1.setViewportView(patient_table);

        delete.setText("删除");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(find_patient, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(create)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(find_patient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clear)
                    .addComponent(create))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delete)
                        .addGap(284, 284, 284))))
        );

        find_patient.getAccessibleContext().setAccessibleDescription("");

        tabbedPane.addTab("就诊记录", jPanel2);

        find_med.setToolTipText("输入药品名");

        new_med.setText("新建");
        new_med.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_medActionPerformed(evt);
            }
        });

        med_table.setModel(med_tm);
        jScrollPane2.setViewportView(med_table);

        delete_med.setText("删除");
        delete_med.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_medActionPerformed(evt);
            }
        });

        clear1.setText("清空");
        clear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete_med))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(find_med, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clear1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(new_med)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(find_med, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clear1)
                    .addComponent(new_med))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delete_med)
                        .addGap(284, 284, 284))))
        );

        tabbedPane.addTab("药品信息", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
        );

        tabbedPane.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewRecord nr = new NewRecord(0);
                nr.patient_tm = patient_tm;
                nr.m_tm = med_tm;
                nr.setVisible(true);
            }
        });
    }//GEN-LAST:event_createActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:
        int row = patient_table.getSelectedRow();
        int id = Integer.parseInt((String)patient_table.getValueAt(row, 0));
        String name = (String)patient_table.getValueAt(row, 1);
        int reply = JOptionPane.showConfirmDialog(
            null,
            "确定删除 "+ name + " 的信息?",
            "删除",
            JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            Model.delete_patient(id); // update db first
            patient_tm.delete(id);
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        // TODO add your handling code here:
        find_patient.setText("");
    }//GEN-LAST:event_clearActionPerformed

    private void new_medActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_medActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewMedicine nm = new NewMedicine();
                nm.tm = med_tm;
                nm.consume.setEnabled(false);
                nm.stock.setEnabled(false);
                nm.add.setEnabled(false);
                nm.setVisible(true);
            }
        });
    }//GEN-LAST:event_new_medActionPerformed

    private void delete_medActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_medActionPerformed
        // TODO add your handling code here:
        int row = med_table.getSelectedRow();
        int id = Integer.parseInt((String)med_table.getValueAt(row, 0));
        String name = (String)med_table.getValueAt(row, 1);
        int reply = JOptionPane.showConfirmDialog(
            null,
            "确定删除 "+ name + " 的信息?",
            "删除",
            JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            Model.delete_medicine(id); // update db first
            Model.delete_re_pre(id);
            med_tm.delete(id);
        }
    }//GEN-LAST:event_delete_medActionPerformed

    private void clear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear1ActionPerformed
        // TODO add your handling code here:
        find_med.setText("");
    }//GEN-LAST:event_clear1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clear;
    private javax.swing.JButton clear1;
    private javax.swing.JButton create;
    private javax.swing.JButton delete;
    private javax.swing.JButton delete_med;
    private javax.swing.JTextField find_med;
    private javax.swing.JTextField find_patient;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable med_table;
    private javax.swing.JButton new_med;
    private javax.swing.JTable patient_table;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables

}
