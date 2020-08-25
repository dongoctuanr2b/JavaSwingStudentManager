/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.teacher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sm.mainFrame.MainFrame;

/**
 *
 * @author Ngoc Tuan
 */
public class JPTeacherManager extends javax.swing.JPanel {

    private JIFTeacherAdd jIFTeacherAdd;
    private JIFTeacherUpdate jIFTeacherUpdate;

    private TeacherDAO teacherDAO = new TeacherDAO();

    /**
     * Creates new form JPanelStudent
     */
    public JPTeacherManager() {
        initComponents();

        setVisible(false);
        setBounds(0, 0, MainFrame.jPanelContent.getSize().width + 105, MainFrame.jPanelContent.getSize().height);

        jTableTeacher.setRowHeight(30);
        jTableTeacher.setShowGrid(true);
        jTableTeacher.setGridColor(new Color(102, 102, 102));

        fillData();
    }

    //fill data ra bảng
    private void fillData() {
        List<Teacher> teachers = teacherDAO.getAll();

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("idTeacher");
        dtm.addColumn("Roll number");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Gender");
        dtm.addColumn("Phone");
        dtm.addColumn("Email");
        dtm.addColumn("Address");

        for (Teacher teacher : teachers) {
            Vector<Object> vector = new Vector();
            vector.add(teacher.getIdTeacher());
            vector.add(teacher.getRollNumber());
            vector.add(teacher.getFirstName());
            vector.add(teacher.getLastName());
            vector.add(teacher.getGenderString());
            vector.add(teacher.getPhone());
            vector.add(teacher.getEmail());
            vector.add(teacher.getAddress());

            dtm.addRow(vector);
        }

        jTableTeacher.setModel(dtm);

        jTableTeacher.removeColumn(jTableTeacher.getColumnModel().getColumn(0));
    }

    private void setData() {
        List<Teacher> teachers = teacherDAO.getAll();

        if (teachers.size() > 0) {
            int index = jTableTeacher.getSelectedRow();
            TableModel model = jTableTeacher.getModel();

            JIFTeacherUpdate.jTextFieldRollN.setText(model.getValueAt(index, 1).toString());
            JIFTeacherUpdate.jTextFieldFName.setText(model.getValueAt(index, 2).toString());
            JIFTeacherUpdate.jTextFieldLName.setText(model.getValueAt(index, 3).toString());

            //gender
            if (model.getValueAt(index, 4).toString().equals("Male")) {
                JIFTeacherUpdate.jRadioButtonMale.setSelected(true);
                JIFTeacherUpdate.jRadioButtonFmale.setSelected(false);
            } else if (model.getValueAt(index, 4).toString().equals("Female")) {
                JIFTeacherUpdate.jRadioButtonFmale.setSelected(true);
                JIFTeacherUpdate.jRadioButtonMale.setSelected(false);
            }

            JIFTeacherUpdate.jTextFieldPhone.setText(model.getValueAt(index, 5).toString());
            JIFTeacherUpdate.jTextFieldEmail.setText(model.getValueAt(index, 6).toString());
            JIFTeacherUpdate.jTextAreaAddress.setText(model.getValueAt(index, 7).toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMid = new javax.swing.JPanel();
        jLabelAdd = new javax.swing.JLabel();
        jLabelDelete = new javax.swing.JLabel();
        jLabelUpdate = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jComboBoxSearch = new javax.swing.JComboBox<>();
        jLabelSearch = new javax.swing.JLabel();
        jLabelRefresh = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTeacher = new javax.swing.JTable();
        jPanelTop = new javax.swing.JPanel();
        jLabelTeacher = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(859, 531));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jPanelMid.setBackground(new java.awt.Color(204, 204, 204));
        jPanelMid.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelMidMouseMoved(evt);
            }
        });

        jLabelAdd.setBackground(new java.awt.Color(204, 204, 204));
        jLabelAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/add.png"))); // NOI18N
        jLabelAdd.setText("<html><div style='text-align: right;'>&emsp;Add</div></html> ");
        jLabelAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabelAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelAdd.setOpaque(true);
        jLabelAdd.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelAddMouseMoved(evt);
            }
        });
        jLabelAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelAddMouseClicked(evt);
            }
        });

        jLabelDelete.setBackground(new java.awt.Color(204, 204, 204));
        jLabelDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/delete1.png"))); // NOI18N
        jLabelDelete.setText("<html><div style='text-align: right;'>&emsp;Delete</div></html> ");
        jLabelDelete.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabelDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelDelete.setOpaque(true);
        jLabelDelete.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelDeleteMouseMoved(evt);
            }
        });
        jLabelDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDeleteMouseClicked(evt);
            }
        });

        jLabelUpdate.setBackground(new java.awt.Color(204, 204, 204));
        jLabelUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/update.png"))); // NOI18N
        jLabelUpdate.setText("<html><div style='text-align: right;'>&emsp;Update</div></html> ");
        jLabelUpdate.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabelUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelUpdate.setOpaque(true);
        jLabelUpdate.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelUpdateMouseMoved(evt);
            }
        });
        jLabelUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelUpdateMouseClicked(evt);
            }
        });

        jTextFieldSearch.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextFieldSearch.setForeground(new java.awt.Color(153, 153, 153));
        jTextFieldSearch.setText("Search...");
        jTextFieldSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldSearchFocusLost(evt);
            }
        });
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });

        jComboBoxSearch.setBackground(new java.awt.Color(240, 240, 240));
        jComboBoxSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Roll number", "Name", "Phone", "Email", "Address" }));
        jComboBoxSearch.setOpaque(false);
        jComboBoxSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBoxSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBoxSearchFocusLost(evt);
            }
        });

        jLabelSearch.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelSearch.setText("Search By:");

        jLabelRefresh.setBackground(new java.awt.Color(204, 204, 204));
        jLabelRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/refresh1.png"))); // NOI18N
        jLabelRefresh.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabelRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelRefresh.setOpaque(true);
        jLabelRefresh.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelRefreshMouseMoved(evt);
            }
        });
        jLabelRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRefreshMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelMidLayout = new javax.swing.GroupLayout(jPanelMid);
        jPanelMid.setLayout(jPanelMidLayout);
        jPanelMidLayout.setHorizontalGroup(
            jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMidLayout.createSequentialGroup()
                .addComponent(jLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jLabelSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
        jPanelMidLayout.setVerticalGroup(
            jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMidLayout.createSequentialGroup()
                .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelMidLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSearch)
                    .addComponent(jComboBoxSearch)
                    .addComponent(jLabelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTableTeacher.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTableTeacher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableTeacher.setFillsViewportHeight(true);
        jTableTeacher.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTableTeacherMouseMoved(evt);
            }
        });
        jTableTeacher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableTeacherMouseReleased(evt);
            }
        });
        jTableTeacher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableTeacherKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableTeacher);

        jPanelTop.setBackground(new java.awt.Color(153, 153, 153));
        jPanelTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelTopMouseMoved(evt);
            }
        });

        jLabelTeacher.setFont(new java.awt.Font("Century Gothic", 1, 22)); // NOI18N
        jLabelTeacher.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTeacher.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Teacher</div></html> ");

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTeacher)
                .addContainerGap())
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTeacher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanelMid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jPanelMidMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMidMouseMoved
        // TODO add your handling code here:
        jLabelAdd.setBackground(new Color(204, 204, 204));
        jLabelAdd.setForeground(new Color(0, 0, 0));

        jLabelDelete.setBackground(new Color(204, 204, 204));
        jLabelDelete.setForeground(new Color(0, 0, 0));

        jLabelUpdate.setBackground(new Color(204, 204, 204));
        jLabelUpdate.setForeground(new Color(0, 0, 0));

        jLabelRefresh.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_jPanelMidMouseMoved

    private void jTableTeacherMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTeacherMouseMoved
        // TODO add your handling code here:
        jLabelAdd.setBackground(new Color(204, 204, 204));
        jLabelAdd.setForeground(new Color(0, 0, 0));

        jLabelDelete.setBackground(new Color(204, 204, 204));
        jLabelDelete.setForeground(new Color(0, 0, 0));

        jLabelUpdate.setBackground(new Color(204, 204, 204));
        jLabelUpdate.setForeground(new Color(0, 0, 0));

        jLabelRefresh.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_jTableTeacherMouseMoved

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        jLabelAdd.setBackground(new Color(204, 204, 204));
        jLabelAdd.setForeground(new Color(0, 0, 0));

        jLabelDelete.setBackground(new Color(204, 204, 204));
        jLabelDelete.setForeground(new Color(0, 0, 0));

        jLabelUpdate.setBackground(new Color(204, 204, 204));
        jLabelUpdate.setForeground(new Color(0, 0, 0));

        jLabelRefresh.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_formMouseMoved

    private void jPanelTopMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelTopMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelTopMouseMoved

    private void jLabelAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelAddMouseClicked
        // TODO add your handling code here:
        if (jLabelAdd.isEnabled()) {
            if (jIFTeacherAdd == null) {
                jIFTeacherAdd = new JIFTeacherAdd();
                jTableTeacher.add(jIFTeacherAdd);
                jIFTeacherAdd.setBounds(0, 0, jTableTeacher.getSize().width / 2 + 100, 420);
                jIFTeacherAdd.setVisible(true);

                if (jIFTeacherUpdate != null) {
                    jIFTeacherUpdate.setVisible(false);
                }
            }
            jIFTeacherAdd.setVisible(true);

            if (jIFTeacherUpdate != null) {
                jIFTeacherUpdate.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableTeacher.getSize();
            Dimension sizejInternalFrameAdd = jIFTeacherAdd.getSize();
            jIFTeacherAdd.setLocation((sizejTableStudent.width - sizejInternalFrameAdd.width) / 2, (sizejTableStudent.height - sizejInternalFrameAdd.height) / 2);
        }

    }//GEN-LAST:event_jLabelAddMouseClicked

    private void jLabelAddMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelAddMouseMoved
        // TODO add your handling code here:
        jLabelAdd.setBackground(new Color(255, 255, 255));
        jLabelAdd.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_jLabelAddMouseMoved

    private void jLabelUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateMouseClicked
        // TODO add your handling code here:
        if (jLabelUpdate.isEnabled()) {
            if (jIFTeacherUpdate == null) {
                jIFTeacherUpdate = new JIFTeacherUpdate();
                jTableTeacher.add(jIFTeacherUpdate);
                jIFTeacherUpdate.setBounds(0, 0, jTableTeacher.getSize().width / 2 + 100, 420);
                jIFTeacherUpdate.setVisible(true);

                if (jIFTeacherAdd != null) {
                    jIFTeacherAdd.setVisible(false);
                }

                try {
                    setData();
                } catch (Exception e) {
                }
            }
            jIFTeacherUpdate.setVisible(true);

            if (jIFTeacherAdd != null) {
                jIFTeacherAdd.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableTeacher.getSize();
            Dimension sizejInternalFrameU = jIFTeacherUpdate.getSize();
            jIFTeacherUpdate.setLocation((sizejTableStudent.width - sizejInternalFrameU.width) / 2, (sizejTableStudent.height - sizejInternalFrameU.height) / 2);
        }

    }//GEN-LAST:event_jLabelUpdateMouseClicked

    private void jLabelUpdateMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateMouseMoved
        // TODO add your handling code here:
        jLabelUpdate.setBackground(new Color(255, 255, 255));
        jLabelUpdate.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_jLabelUpdateMouseMoved

    private void jLabelDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDeleteMouseClicked
        // TODO add your handling code here:
        try {
            if (jLabelDelete.isEnabled()) {
                int position = jTableTeacher.getSelectedRow();
                if (position >= 0) {
                    String rollNumber = jTableTeacher.getModel().getValueAt(position, 1).toString();
                    int yes = JOptionPane.showConfirmDialog(this, "Are you sure want to this teacher delete?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (yes == JOptionPane.YES_OPTION) {
                        boolean row = teacherDAO.delete(rollNumber);

                        if (row) {
                            JOptionPane.showMessageDialog(this, "Teacher Data Deleted!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                            //count số student
                            int totalTeacher = teacherDAO.count();
                            MainFrame.jLabelCountTeacher.setText("" + totalTeacher);

                            //khi delete thành công thì show data lên bảng
                            fillData();
                        } else {
                            JOptionPane.showMessageDialog(this, "Error Delete Teacher!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jLabelDeleteMouseClicked

    private void jLabelDeleteMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDeleteMouseMoved
        // TODO add your handling code here:
        jLabelDelete.setBackground(new Color(255, 255, 255));
        jLabelDelete.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_jLabelDeleteMouseMoved

    private void jLabelRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRefreshMouseClicked
        // TODO add your handling code here:
        fillData();
    }//GEN-LAST:event_jLabelRefreshMouseClicked

    private void jLabelRefreshMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRefreshMouseMoved
        // TODO add your handling code here:
        jLabelRefresh.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jLabelRefreshMouseMoved

    private void jComboBoxSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBoxSearchFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSearchFocusGained

    private void jComboBoxSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBoxSearchFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSearchFocusLost

    private void jTextFieldSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSearchFocusGained
        // TODO add your handling code here:
        if (jTextFieldSearch.getText().equals("Search...")) {
            jTextFieldSearch.setText("");
            jTextFieldSearch.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextFieldSearchFocusGained

    private void jTextFieldSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSearchFocusLost
        // TODO add your handling code here:
        //nếu jTextField trống thì lại setText thành username
        if (jTextFieldSearch.getText().isEmpty()) {
            jTextFieldSearch.setText("Search...");
            jTextFieldSearch.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextFieldSearchFocusLost

    private void jTableTeacherMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTeacherMouseReleased
        // TODO add your handling code here:
        List<Teacher> teachers = teacherDAO.getAll();

        if (teachers.size() > 0) {
            int index = jTableTeacher.getSelectedRow();
            TableModel model = jTableTeacher.getModel();
            if (jIFTeacherUpdate != null) {
                JIFTeacherUpdate.jTextFieldRollN.setText(model.getValueAt(index, 1).toString());
                JIFTeacherUpdate.jTextFieldFName.setText(model.getValueAt(index, 2).toString());
                JIFTeacherUpdate.jTextFieldLName.setText(model.getValueAt(index, 3).toString());

                //gender
                if (model.getValueAt(index, 4).toString().equals("Male")) {
                    JIFTeacherUpdate.jRadioButtonMale.setSelected(true);
                    JIFTeacherUpdate.jRadioButtonFmale.setSelected(false);
                } else if (model.getValueAt(index, 4).toString().equals("Female")) {
                    JIFTeacherUpdate.jRadioButtonFmale.setSelected(true);
                    JIFTeacherUpdate.jRadioButtonMale.setSelected(false);
                }

                JIFTeacherUpdate.jTextFieldPhone.setText(model.getValueAt(index, 5).toString());
                JIFTeacherUpdate.jTextFieldEmail.setText(model.getValueAt(index, 6).toString());
                JIFTeacherUpdate.jTextAreaAddress.setText(model.getValueAt(index, 7).toString());
            }
        }
    }//GEN-LAST:event_jTableTeacherMouseReleased

    private void jTableTeacherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableTeacherKeyReleased
        // TODO add your handling code here:
        List<Teacher> teachers = teacherDAO.getAll();

        //get key up and key down để fill dữ liệu ra form add
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
            if (teachers.size() > 0) {
                int index = jTableTeacher.getSelectedRow();
                TableModel model = jTableTeacher.getModel();

                if (jIFTeacherUpdate != null) {
                    JIFTeacherUpdate.jTextFieldRollN.setText(model.getValueAt(index, 1).toString());
                    JIFTeacherUpdate.jTextFieldFName.setText(model.getValueAt(index, 2).toString());
                    JIFTeacherUpdate.jTextFieldLName.setText(model.getValueAt(index, 3).toString());

                    //gender
                    if (model.getValueAt(index, 4).toString().equals("Male")) {
                        JIFTeacherUpdate.jRadioButtonMale.setSelected(true);
                        JIFTeacherUpdate.jRadioButtonFmale.setSelected(false);
                    } else if (model.getValueAt(index, 4).toString().equals("Female")) {
                        JIFTeacherUpdate.jRadioButtonFmale.setSelected(true);
                        JIFTeacherUpdate.jRadioButtonMale.setSelected(false);
                    }

                    JIFTeacherUpdate.jTextFieldPhone.setText(model.getValueAt(index, 5).toString());
                    JIFTeacherUpdate.jTextFieldEmail.setText(model.getValueAt(index, 6).toString());
                    JIFTeacherUpdate.jTextAreaAddress.setText(model.getValueAt(index, 7).toString());
                }
            }
        }
    }//GEN-LAST:event_jTableTeacherKeyReleased

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        List<Teacher> teachers = teacherDAO.search(jTextFieldSearch.getText());

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("idTeacher");
        dtm.addColumn("Roll number");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Gender");
        dtm.addColumn("Phone");
        dtm.addColumn("Email");
        dtm.addColumn("Address");

        for (Teacher teacher : teachers) {
            Vector<Object> vector = new Vector();
            vector.add(teacher.getIdTeacher());
            vector.add(teacher.getRollNumber());
            vector.add(teacher.getFirstName());
            vector.add(teacher.getLastName());
            vector.add(teacher.getGenderString());
            vector.add(teacher.getPhone());
            vector.add(teacher.getEmail());
            vector.add(teacher.getAddress());

            dtm.addRow(vector);
        }

        JPTeacherManager.jTableTeacher.setModel(dtm);

        JPTeacherManager.jTableTeacher.removeColumn(JPTeacherManager.jTableTeacher.getColumnModel().getColumn(0));
    }//GEN-LAST:event_jTextFieldSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> jComboBoxSearch;
    public static javax.swing.JLabel jLabelAdd;
    public static javax.swing.JLabel jLabelDelete;
    private javax.swing.JLabel jLabelRefresh;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JLabel jLabelTeacher;
    public static javax.swing.JLabel jLabelUpdate;
    private javax.swing.JPanel jPanelMid;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableTeacher;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
