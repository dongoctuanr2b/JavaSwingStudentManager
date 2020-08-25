/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.user;

import java.awt.Color;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Ngoc Tuan
 */
public class JIFPerUpdate extends javax.swing.JInternalFrame {

    private PermissionDAO permissionDAO = new PermissionDAO();

    /**
     * Creates new form JIFPerUpdate
     */
    public JIFPerUpdate() {
        initComponents();
    }

    //fill data ra bảng
    public void fillData() {

        List<Permission> permissions = permissionDAO.getAll();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("idPermissionDetail");
        dtm.addColumn("idPermission");
        dtm.addColumn("Permission name");
        dtm.addColumn("Action name");
        dtm.addColumn("Check action");

        for (Permission permission : permissions) {
            Vector<Object> vector = new Vector();
            vector.add(permission.getIdPermissionDetail());
            vector.add(permission.getIdPermission());
            vector.add(permission.getPermissionName());
            vector.add(permission.getActionName());
            vector.add(permission.getCheckActionString());

            dtm.addRow(vector);
        }

        JPPerManager.jTablePer.setModel(dtm);

        JPPerManager.jTablePer.removeColumn(JPPerManager.jTablePer.getColumnModel().getColumn(1));
        JPPerManager.jTablePer.removeColumn(JPPerManager.jTablePer.getColumnModel().getColumn(0));
    }

    private boolean validatePerU() {
        boolean flag = true;

        //check subject name and class name phải là duy nhất ko được trùng, chuyển flag = false
        List<Permission> permissions = permissionDAO.getAllPer();

        int index = JPPerManager.jTablePer.getSelectedRow();
        TableModel model = JPPerManager.jTablePer.getModel();

        if (flag) {
            for (Permission permission : permissions) {
                String getRowClick = model.getValueAt(index, 2).toString();
                String getJTextPerN = jTextFieldPerN.getText();

                if ((getJTextPerN.toLowerCase().equals(permission.getPermissionName().toLowerCase()))
                        && (!getRowClick.equalsIgnoreCase(getJTextPerN))) {
                    JOptionPane.showMessageDialog(rootPane, "Permission name " + getJTextPerN + " already exist!!!", "Warning", JOptionPane.OK_OPTION);

                    flag = false;
                }
            }
        }

        if (flag) {
            for (Permission permission : permissions) {
                if (jTextFieldPerN.getText().toLowerCase().equals(permission.getPermissionName().toLowerCase())) {
                    int yes = JOptionPane.showConfirmDialog(rootPane, "Permission name " + jTextFieldPerN.getText() + " already exist!!! Do you want to change?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (yes == JOptionPane.YES_OPTION) {
                        flag = false;
                        break;
                    } else {
                        flag = true;
                        break;
                    }
                }
            }
        }

        if (flag) {
            if (jTextFieldPerN.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "One Or More Empty Field!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //Xử lý radio button của gender
    private byte checkActionAdd() {
        if (jCheckBoxAdd.isSelected()) {
            return 1;
        }

        return 0;
    }

    //Xử lý radio button của gender
    private byte checkActionUpdate() {
        if (jCheckBoxUpdate.isSelected()) {
            return 1;
        }

        return 0;
    }

    //Xử lý radio button của gender
    private byte checkActionDelete() {
        if (jCheckBoxDelete.isSelected()) {
            return 1;
        }

        return 0;
    }

    //Xử lý radio button của gender
    private byte checkActionRead() {
        if (jCheckBoxRead.isSelected()) {
            return 1;
        }

        return 0;
    }

    //get idStudent từ bảng
    private int idPermission() {
        int index = JPPerManager.jTablePer.getSelectedRow();
        TableModel model = JPPerManager.jTablePer.getModel();

        return Integer.valueOf(model.getValueAt(index, 1).toString());
    }

    //get idStudent từ bảng
    private int idPermissionDetail() {
        int index = JPPerManager.jTablePer.getSelectedRow();
        TableModel model = JPPerManager.jTablePer.getModel();

        return Integer.valueOf(model.getValueAt(index, 0).toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelAdd = new javax.swing.JPanel();
        jButtonU = new javax.swing.JLabel();
        jLabelPass = new javax.swing.JLabel();
        jLabelLPerN = new javax.swing.JLabel();
        jLabelPerU = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JLabel();
        jTextFieldPerN = new javax.swing.JTextField();
        jCheckBoxAdd = new javax.swing.JCheckBox();
        jCheckBoxUpdate = new javax.swing.JCheckBox();
        jCheckBoxDelete = new javax.swing.JCheckBox();
        jCheckBoxRead = new javax.swing.JCheckBox();

        setClosable(true);
        setResizable(true);

        jPanelAdd.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAdd.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelAddMouseMoved(evt);
            }
        });

        jButtonU.setBackground(new java.awt.Color(53, 173, 164));
        jButtonU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonU.setForeground(new java.awt.Color(255, 255, 255));
        jButtonU.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;Update</div></html> ");
        jButtonU.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(53, 173, 164), 1, true));
        jButtonU.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonU.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonU.setOpaque(true);
        jButtonU.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButtonUMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButtonUMouseMoved(evt);
            }
        });
        jButtonU.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jButtonUMouseWheelMoved(evt);
            }
        });
        jButtonU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonUMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonUMouseReleased(evt);
            }
        });

        jLabelPass.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPass.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPass.setText("Action name:");

        jLabelLPerN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelLPerN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelLPerN.setText("Permission name:");

        jLabelPerU.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPerU.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabelPerU.setText("Update Permission");

        jButtonClear.setBackground(new java.awt.Color(255, 51, 51));
        jButtonClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonClear.setForeground(new java.awt.Color(255, 255, 255));
        jButtonClear.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;Clear</div></html> ");
        jButtonClear.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jButtonClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonClear.setOpaque(true);
        jButtonClear.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButtonClearMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButtonClearMouseMoved(evt);
            }
        });
        jButtonClear.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jButtonClearMouseWheelMoved(evt);
            }
        });
        jButtonClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonClearMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonClearMouseReleased(evt);
            }
        });

        jCheckBoxAdd.setText("Add");
        jCheckBoxAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAddActionPerformed(evt);
            }
        });

        jCheckBoxUpdate.setText("Update");

        jCheckBoxDelete.setText("Delete");

        jCheckBoxRead.setText("Read");

        javax.swing.GroupLayout jPanelAddLayout = new javax.swing.GroupLayout(jPanelAdd);
        jPanelAdd.setLayout(jPanelAddLayout);
        jPanelAddLayout.setHorizontalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelLPerN)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jLabelPass)
                        .addGap(31, 31, 31)))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jCheckBoxDelete)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxRead))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jCheckBoxAdd)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxUpdate))
                    .addComponent(jTextFieldPerN, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelPerU)
                .addGap(140, 140, 140))
        );
        jPanelAddLayout.setVerticalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPerU)
                .addGap(55, 55, 55)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLPerN)
                    .addComponent(jTextFieldPerN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPass)
                    .addComponent(jCheckBoxAdd)
                    .addComponent(jCheckBoxUpdate))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxDelete)
                    .addComponent(jCheckBoxRead))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonUMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUMouseDragged

    private void jButtonUMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUMouseMoved
        // TODO add your handling code here:
        jButtonU.setBackground(new Color(255, 255, 255));
        jButtonU.setForeground(new Color(53, 173, 164));
    }//GEN-LAST:event_jButtonUMouseMoved

    private void jButtonUMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jButtonUMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUMouseWheelMoved

    private void jButtonUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUMouseClicked
        // TODO add your handling code here:
        //Check form add student
        try {
            if (validatePerU()) {

                Permission permission = new Permission(
                        idPermission(),
                        jTextFieldPerN.getText()
                );

                boolean rowPermission = permissionDAO.update(permission);

                int index = JPPerManager.jTablePer.getSelectedRow();
                TableModel model = JPPerManager.jTablePer.getModel();

                if (model.getValueAt(index, 3).toString().equalsIgnoreCase(jCheckBoxAdd.getText())) {
                    Permission permissionDetailAdd = new Permission(
                            idPermissionDetail(),
                            idPermission(),
                            jCheckBoxAdd.getText(),
                            checkActionAdd()
                    );

                    boolean rowPermissionDetailAdd = permissionDAO.updatePerDetail(permissionDetailAdd);

                    if (rowPermissionDetailAdd) {
                        System.out.println("Successfully");
                    } else {
                        System.out.println("Unsuccessfully");
                    }
                } else if (model.getValueAt(index, 3).toString().equalsIgnoreCase(jCheckBoxUpdate.getText())) {
                    Permission permissionDetailUpdate = new Permission(
                            idPermissionDetail(),
                            idPermission(),
                            jCheckBoxUpdate.getText(),
                            checkActionUpdate()
                    );
                    boolean rowPermissionDetailUpdate = permissionDAO.updatePerDetail(permissionDetailUpdate);

                    if (rowPermissionDetailUpdate) {
                        System.out.println("Successfully");
                    } else {
                        System.out.println("Unsuccessfully");
                    }
                } else if (model.getValueAt(index, 3).toString().equalsIgnoreCase(jCheckBoxDelete.getText())) {
                    Permission permissionDetailDelete = new Permission(
                            idPermissionDetail(),
                            idPermission(),
                            jCheckBoxDelete.getText(),
                            checkActionDelete()
                    );
                    boolean rowPermissionDetailDelete = permissionDAO.updatePerDetail(permissionDetailDelete);

                    if (rowPermissionDetailDelete) {
                        System.out.println("Successfully");
                    } else {
                        System.out.println("Unsuccessfully");
                    }
                } else if (model.getValueAt(index, 3).toString().equalsIgnoreCase(jCheckBoxRead.getText())) {
                    Permission permissionDetailRead = new Permission(
                            idPermissionDetail(),
                            idPermission(),
                            jCheckBoxRead.getText(),
                            checkActionRead()
                    );
                    boolean rowPermissionDetailRead = permissionDAO.updatePerDetail(permissionDetailRead);

                    if (rowPermissionDetailRead) {
                        System.out.println("Successfully");
                    } else {
                        System.out.println("Unsuccessfully");
                    }
                }

                if (rowPermission) {
                    JOptionPane.showMessageDialog(rootPane, "Permission Data Updated!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                    //khi Update thành công thì show data lên bảng
                    fillData();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error Update Permission!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButtonUMouseClicked

    private void jButtonUMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUMouseReleased

    private void jButtonClearMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClearMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClearMouseDragged

    private void jButtonClearMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClearMouseMoved
        // TODO add your handling code here:
        jButtonClear.setBackground(new Color(255, 255, 255));
        jButtonClear.setForeground(new Color(255, 51, 51));
    }//GEN-LAST:event_jButtonClearMouseMoved

    private void jButtonClearMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jButtonClearMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClearMouseWheelMoved

    private void jButtonClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClearMouseClicked
        // TODO add your handling code here:
        jTextFieldPerN.setText(null);
        jCheckBoxAdd.setSelected(false);
        jCheckBoxUpdate.setSelected(false);
        jCheckBoxDelete.setSelected(false);
        jCheckBoxRead.setSelected(false);
    }//GEN-LAST:event_jButtonClearMouseClicked

    private void jButtonClearMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClearMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClearMouseReleased

    private void jCheckBoxAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxAddActionPerformed

    private void jPanelAddMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelAddMouseMoved
        // TODO add your handling code here:
        jButtonU.setBackground(new Color(53, 173, 164));
        jButtonU.setForeground(new Color(255, 255, 255));

        jButtonClear.setBackground(new Color(255, 51, 51));
        jButtonClear.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelAddMouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel jButtonClear;
    public static javax.swing.JLabel jButtonU;
    public static javax.swing.JCheckBox jCheckBoxAdd;
    public static javax.swing.JCheckBox jCheckBoxDelete;
    public static javax.swing.JCheckBox jCheckBoxRead;
    public static javax.swing.JCheckBox jCheckBoxUpdate;
    private javax.swing.JLabel jLabelLPerN;
    private javax.swing.JLabel jLabelPass;
    private javax.swing.JLabel jLabelPerU;
    private javax.swing.JPanel jPanelAdd;
    public static javax.swing.JTextField jTextFieldPerN;
    // End of variables declaration//GEN-END:variables
}
