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

/**
 *
 * @author Ngoc Tuan
 */
public class JIFPerAdd extends javax.swing.JInternalFrame {

    private PermissionDAO permissionDAO = new PermissionDAO();

    /**
     * Creates new form JIFPerAdd
     */
    public JIFPerAdd() {
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

    private boolean validatePerAdd() {
        boolean flag = true;

        //check subject name and class name phải là duy nhất ko được trùng, chuyển flag = false
        List<Permission> permissions = permissionDAO.getAllPer();
        for (Permission permission : permissions) {
            if (jTextFieldPerN.getText().equalsIgnoreCase(permission.getPermissionName())) {
                JOptionPane.showMessageDialog(this, "This Permission name already exist!", "Warning", JOptionPane.ERROR_MESSAGE);
                flag = false;
            }
        }
        if (flag) {
            if (jTextFieldPerN.getText().isEmpty() || (!jCheckBoxAdd.isSelected() && !jCheckBoxDelete.isSelected()
                    && !jCheckBoxUpdate.isSelected() && !jCheckBoxRead.isSelected())) {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelAdd = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JLabel();
        jLabelPass = new javax.swing.JLabel();
        jLabelLPerN = new javax.swing.JLabel();
        jLabelPerAdd = new javax.swing.JLabel();
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

        jButtonSave.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;Save</div></html> ");
        jButtonSave.setBackground(new java.awt.Color(53, 173, 164));
        jButtonSave.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(53, 173, 164), 1, true));
        jButtonSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSave.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSave.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSave.setOpaque(true);
        jButtonSave.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButtonSaveMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButtonSaveMouseMoved(evt);
            }
        });
        jButtonSave.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jButtonSaveMouseWheelMoved(evt);
            }
        });
        jButtonSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSaveMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonSaveMouseReleased(evt);
            }
        });

        jLabelPass.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPass.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPass.setText("Action name:");

        jLabelLPerN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelLPerN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelLPerN.setText("Permission name:");

        jLabelPerAdd.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPerAdd.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabelPerAdd.setText("Add Permission");

        jButtonClear.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;Clear</div></html> ");
        jButtonClear.setBackground(new java.awt.Color(255, 51, 51));
        jButtonClear.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 1, true));
        jButtonClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonClear.setForeground(new java.awt.Color(255, 255, 255));
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
                    .addComponent(jLabelPass))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jCheckBoxDelete)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxRead))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jCheckBoxAdd)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxUpdate))
                    .addComponent(jTextFieldPerN, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelPerAdd)
                .addGap(172, 172, 172))
        );
        jPanelAddLayout.setVerticalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addComponent(jLabelPerAdd)
                .addGap(66, 66, 66)
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
                .addGap(59, 59, 59)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
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

    private void jButtonSaveMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSaveMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSaveMouseDragged

    private void jButtonSaveMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSaveMouseMoved
        // TODO add your handling code here:
        jButtonSave.setBackground(new Color(255, 255, 255));
        jButtonSave.setForeground(new Color(53, 173, 164));
    }//GEN-LAST:event_jButtonSaveMouseMoved

    private void jButtonSaveMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jButtonSaveMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSaveMouseWheelMoved

    private void jButtonSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSaveMouseClicked
        // TODO add your handling code here:
        //Check form add student
        if (validatePerAdd()) {

            Permission permission = new Permission(
                    jTextFieldPerN.getText()
            );

            boolean rowPermission = permissionDAO.save(permission);

            //insert tblUserPermission
            int idPer = permissionDAO.idPerMax();

            Permission permissionDetailAdd = new Permission(
                    idPer,
                    jCheckBoxAdd.getText(),
                    checkActionAdd()
            );
            Permission permissionDetailUpdate = new Permission(
                    idPer,
                    jCheckBoxUpdate.getText(),
                    checkActionUpdate()
            );
            Permission permissionDetailDelete = new Permission(
                    idPer,
                    jCheckBoxDelete.getText(),
                    checkActionDelete()
            );
            Permission permissionDetailRead = new Permission(
                    idPer,
                    jCheckBoxRead.getText(),
                    checkActionRead()
            );

            boolean rowPermissionDetailAdd = permissionDAO.savePerDetail(permissionDetailAdd);
            boolean rowPermissionDetailUpdate = permissionDAO.savePerDetail(permissionDetailUpdate);
            boolean rowPermissionDetailDelete = permissionDAO.savePerDetail(permissionDetailDelete);
            boolean rowPermissionDetailRead = permissionDAO.savePerDetail(permissionDetailRead);

            if (rowPermission && rowPermissionDetailAdd && rowPermissionDetailUpdate && rowPermissionDetailDelete && rowPermissionDetailRead) {
                JOptionPane.showMessageDialog(rootPane, "New Permission Added!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                try {
                    fillData();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error add Permission!!!", "Warning", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonSaveMouseClicked

    private void jButtonSaveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSaveMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSaveMouseReleased

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

    private void jPanelAddMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelAddMouseMoved
        // TODO add your handling code here:
        jButtonSave.setBackground(new Color(53, 173, 164));
        jButtonSave.setForeground(new Color(255, 255, 255));

        jButtonClear.setBackground(new Color(255, 51, 51));
        jButtonClear.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelAddMouseMoved

    private void jCheckBoxAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel jButtonClear;
    public static javax.swing.JLabel jButtonSave;
    private javax.swing.JCheckBox jCheckBoxAdd;
    private javax.swing.JCheckBox jCheckBoxDelete;
    private javax.swing.JCheckBox jCheckBoxRead;
    private javax.swing.JCheckBox jCheckBoxUpdate;
    private javax.swing.JLabel jLabelLPerN;
    private javax.swing.JLabel jLabelPass;
    private javax.swing.JLabel jLabelPerAdd;
    private javax.swing.JPanel jPanelAdd;
    private javax.swing.JTextField jTextFieldPerN;
    // End of variables declaration//GEN-END:variables
}
