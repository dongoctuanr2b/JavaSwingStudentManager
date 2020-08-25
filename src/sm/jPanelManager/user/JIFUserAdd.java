/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.user;

import java.awt.Color;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ngoc Tuan
 */
public class JIFUserAdd extends javax.swing.JInternalFrame {

    private UserDAO userDAO = new UserDAO();
    private PermissionDAO permissionDAO = new PermissionDAO();

    /**
     * Creates new form JIFUserAdd
     */
    public JIFUserAdd() {
        initComponents();

        loadPermission();
    }

    //fill data ra bảng
    public void fillData() {

        List<User> users = userDAO.getAll();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("idUser");
        dtm.addColumn("Username");
        dtm.addColumn("Password");
        dtm.addColumn("Permission");

        for (User user : users) {
            Vector<Object> vector = new Vector();
            vector.add(user.getIdUser());
            vector.add(user.getUsername());
            vector.add(user.getPassword());
            vector.add(user.getPermissionName());

            dtm.addRow(vector);
        }

        JPUserManager.jTableUser.setModel(dtm);

        JPUserManager.jTableUser.removeColumn(JPUserManager.jTableUser.getColumnModel().getColumn(2));
        JPUserManager.jTableUser.removeColumn(JPUserManager.jTableUser.getColumnModel().getColumn(0));
    }

    private boolean validateUserAdd() {
        boolean flag = true;

        //check subject name and class name phải là duy nhất ko được trùng, chuyển flag = false
        List<User> users = userDAO.getAll();
        for (User user : users) {
            if (jTextFieldUserN.getText().equalsIgnoreCase(user.getUsername())) {
                JOptionPane.showMessageDialog(this, "This Username already exist!", "Warning", JOptionPane.ERROR_MESSAGE);
                flag = false;
                break;
            }
        }
        if (flag) {
            if (jTextFieldUserN.getText().isEmpty() || String.valueOf(jPasswordField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "One Or More Empty Field!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void loadPermission() {
        // TODO add your handling code here:
        List<Permission> permissions = permissionDAO.getAllPer();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Permission permission : permissions) {
            dcbm.addElement(permission);
        }

        jComboBoxPer.setModel(dcbm);
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
        jLabePer = new javax.swing.JLabel();
        jLabelPass = new javax.swing.JLabel();
        jLabelLUserN = new javax.swing.JLabel();
        jLabelUserAdd = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JLabel();
        jComboBoxPer = new javax.swing.JComboBox<>();
        jTextFieldUserN = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();

        setClosable(true);
        setResizable(true);

        jPanelAdd.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAdd.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelAddMouseMoved(evt);
            }
        });

        jButtonSave.setBackground(new java.awt.Color(53, 173, 164));
        jButtonSave.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSave.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSave.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;Save</div></html> ");
        jButtonSave.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(53, 173, 164), 1, true));
        jButtonSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jLabePer.setBackground(new java.awt.Color(255, 255, 255));
        jLabePer.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabePer.setText("Permission:");

        jLabelPass.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPass.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPass.setText("Password:");

        jLabelLUserN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelLUserN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelLUserN.setText("Username:");

        jLabelUserAdd.setBackground(new java.awt.Color(255, 255, 255));
        jLabelUserAdd.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabelUserAdd.setText("Add User");

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

        jComboBoxPer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanelAddLayout = new javax.swing.GroupLayout(jPanelAdd);
        jPanelAdd.setLayout(jPanelAddLayout);
        jPanelAddLayout.setHorizontalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelUserAdd)
                .addGap(209, 209, 209))
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabePer)
                    .addComponent(jLabelPass)
                    .addComponent(jLabelLUserN))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPasswordField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jComboBoxPer, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldUserN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanelAddLayout.setVerticalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelUserAdd)
                .addGap(33, 33, 33)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLUserN)
                    .addComponent(jTextFieldUserN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPass)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabePer)
                    .addComponent(jComboBoxPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        if (validateUserAdd()) {

            User user = new User(
                    jTextFieldUserN.getText(),
                    String.valueOf(jPasswordField.getPassword())
            );

            boolean rowUser = userDAO.save(user);

            //insert tblUserPermission
            int idUser = userDAO.idUserMax();

            User userPermission = new User(
                    ((Permission) jComboBoxPer.getSelectedItem()).getIdPermission(),
                    idUser
            );

            boolean rowUserPermission = userDAO.saveUserPer(userPermission);

            if (rowUser && rowUserPermission) {
                JOptionPane.showMessageDialog(rootPane, "New User Added!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                try {
                    fillData();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error add User!!!", "Warning", JOptionPane.ERROR_MESSAGE);
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
        jTextFieldUserN.setText(null);
        jPasswordField.setText(null);
        jComboBoxPer.setSelectedIndex(0);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel jButtonClear;
    public static javax.swing.JLabel jButtonSave;
    public static javax.swing.JComboBox<String> jComboBoxPer;
    private javax.swing.JLabel jLabePer;
    private javax.swing.JLabel jLabelLUserN;
    private javax.swing.JLabel jLabelPass;
    private javax.swing.JLabel jLabelUserAdd;
    private javax.swing.JPanel jPanelAdd;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextFieldUserN;
    // End of variables declaration//GEN-END:variables
}
