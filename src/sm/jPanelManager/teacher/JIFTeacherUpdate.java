/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.teacher;

import java.awt.Color;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Ngoc Tuan
 */
public class JIFTeacherUpdate extends javax.swing.JInternalFrame {

    private TeacherDAO teacherDAO = new TeacherDAO();

    /**
     * Creates new form JInternalFrameUpdate
     */
    public JIFTeacherUpdate() {
        initComponents();
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

        JPTeacherManager.jTableTeacher.setModel(dtm);

        JPTeacherManager.jTableTeacher.removeColumn(JPTeacherManager.jTableTeacher.getColumnModel().getColumn(0));
    }

    //Xử lý radio button của gender
    private byte gender() {
        if (jRadioButtonMale.isSelected()) {
            return 1;
        } else if (jRadioButtonFmale.isSelected()) {

            return 0;
        } else {
            return -1;
        }
    }

    //validate email
    private boolean isEmailValid() {
        //validate email: ^([\w]+)@([\w]+)\.([\w]+)$
        Pattern pmail = Pattern.compile("^([\\w]+)@([\\w]+)\\.([\\w]+)$");//định nghĩa chuỗi hợp lệ
        Matcher mMail = pmail.matcher(jTextFieldEmail.getText()); //get text ở email để match với biến pmail 
        boolean isEmailValid = mMail.matches(); //tạo biến boolean, nếu match is true, not match is false

        return isEmailValid;
    }

    //get idTEacher từ bảng
    private int idTeacher() {
        int index = JPTeacherManager.jTableTeacher.getSelectedRow();
        TableModel model = JPTeacherManager.jTableTeacher.getModel();

        int idTeacher = Integer.valueOf(model.getValueAt(index, 0).toString());

        return idTeacher;
    }

    //validate form update student
    private boolean validateTeacherUpdate() {
        boolean flag = true;

        //check roll number phải là duy nhất ko được trùng, chuyển flag = false
        List<Teacher> teachers = teacherDAO.getAll();

        int index = JPTeacherManager.jTableTeacher.getSelectedRow();
        TableModel model = JPTeacherManager.jTableTeacher.getModel();

        if (flag) {
            for (Teacher teacher : teachers) {
                String getJTextRN = jTextFieldRollN.getText();
                String getRowClick = model.getValueAt(index, 1).toString();

                if ((getJTextRN.toLowerCase().equals(teacher.getRollNumber().toLowerCase()))
                        && (!getRowClick.equalsIgnoreCase(getJTextRN))) {
                    JOptionPane.showMessageDialog(rootPane, "RollNumber " + getJTextRN + " already exist!!!", "Warning", JOptionPane.OK_OPTION);

                    flag = false;
                }
            }
        }

        if (flag) {
            for (Teacher teacher : teachers) {
                String getJTextRN = jTextFieldRollN.getText();
                String getRN = teacher.getRollNumber();
                if (getJTextRN.toLowerCase().equals(getRN.toLowerCase())) {
                    int yes = JOptionPane.showConfirmDialog(rootPane, "RollNumber " + getJTextRN + " already exist!!! Do you want to change?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (yes == JOptionPane.YES_OPTION) {
                        flag = false;
                    } else {
                        flag = true;
                    }
                }
            }
        }

        //nếu roll number không bị trùng thì flag vẫn = true rồi check tiếp
        if (flag) {
            if (jTextFieldRollN.getText().isEmpty() || jTextFieldFName.getText().isEmpty()
                    || jTextFieldLName.getText().isEmpty() || jTextFieldPhone.getText().isEmpty()
                    || jTextFieldEmail.getText().isEmpty() || jTextAreaAddress.getText().isEmpty()
                    || gender() == -1) {

                JOptionPane.showMessageDialog(rootPane, "One Or More Empty Field!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                return false;
            } else if (!isEmailValid()) {

                JOptionPane.showMessageDialog(rootPane, "Your Email is not valid!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                return false;
            } else {
                return true;
            }
        } else {
            return false;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanelAdd = new javax.swing.JPanel();
        jButtonU = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        jTextAreaAddress = new javax.swing.JTextArea();
        jLabelAddress = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jLabelEmail = new javax.swing.JLabel();
        jTextFieldPhone = new javax.swing.JTextField();
        jLabelPhone = new javax.swing.JLabel();
        jRadioButtonFmale = new javax.swing.JRadioButton();
        jRadioButtonMale = new javax.swing.JRadioButton();
        jLabelGender = new javax.swing.JLabel();
        jLabelFName = new javax.swing.JLabel();
        jTextFieldFName = new javax.swing.JTextField();
        jLabelLName = new javax.swing.JLabel();
        jTextFieldLName = new javax.swing.JTextField();
        jLabelTeacherU = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JLabel();
        jLabelRollN = new javax.swing.JLabel();
        jTextFieldRollN = new javax.swing.JTextField();

        setBorder(null);
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

        jTextAreaAddress.setColumns(20);
        jTextAreaAddress.setRows(5);
        jScrollPane.setViewportView(jTextAreaAddress);

        jLabelAddress.setBackground(new java.awt.Color(255, 255, 255));
        jLabelAddress.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelAddress.setText("Address:");

        jTextFieldEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmailActionPerformed(evt);
            }
        });

        jLabelEmail.setBackground(new java.awt.Color(255, 255, 255));
        jLabelEmail.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelEmail.setText("Email:");

        jTextFieldPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPhoneActionPerformed(evt);
            }
        });
        jTextFieldPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPhoneKeyTyped(evt);
            }
        });

        jLabelPhone.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPhone.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelPhone.setText("Phone:");

        jRadioButtonFmale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButtonFmale);
        jRadioButtonFmale.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jRadioButtonFmale.setText("Female");

        jRadioButtonMale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButtonMale);
        jRadioButtonMale.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jRadioButtonMale.setText("Male");
        jRadioButtonMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMaleActionPerformed(evt);
            }
        });

        jLabelGender.setBackground(new java.awt.Color(255, 255, 255));
        jLabelGender.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelGender.setText("Gender:");

        jLabelFName.setBackground(new java.awt.Color(255, 255, 255));
        jLabelFName.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelFName.setText("First name:");

        jTextFieldFName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFNameActionPerformed(evt);
            }
        });

        jLabelLName.setBackground(new java.awt.Color(255, 255, 255));
        jLabelLName.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelLName.setText("Last name:");

        jTextFieldLName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLNameActionPerformed(evt);
            }
        });

        jLabelTeacherU.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTeacherU.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabelTeacherU.setText("Update Teacher");

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

        jLabelRollN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelRollN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelRollN.setText("Roll number:");

        jTextFieldRollN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRollNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAddLayout = new javax.swing.GroupLayout(jPanelAdd);
        jPanelAdd.setLayout(jPanelAddLayout);
        jPanelAddLayout.setHorizontalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelLName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanelAddLayout.createSequentialGroup()
                                        .addComponent(jLabelAddress)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelAddLayout.createSequentialGroup()
                                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelAddLayout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addComponent(jLabelGender)
                                                .addGap(18, 18, 18)
                                                .addComponent(jRadioButtonMale)
                                                .addGap(18, 18, 18)
                                                .addComponent(jRadioButtonFmale))
                                            .addGroup(jPanelAddLayout.createSequentialGroup()
                                                .addComponent(jLabelRollN)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextFieldRollN, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanelAddLayout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabelFName)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextFieldFName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(83, 83, 83)))
                                .addGap(35, 35, 35)
                                .addComponent(jLabelEmail)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldLName, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addContainerGap(310, Short.MAX_VALUE)
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(jLabelTeacherU)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelAddLayout.setVerticalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addComponent(jLabelTeacherU)
                .addGap(47, 47, 47)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLName)
                    .addComponent(jTextFieldLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelRollN)
                    .addComponent(jTextFieldRollN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPhone)
                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFName)
                    .addComponent(jTextFieldFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelGender)
                            .addComponent(jRadioButtonFmale)
                            .addComponent(jRadioButtonMale)))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelEmail))))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
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
            if (validateTeacherUpdate()) {
                Teacher teacher = new Teacher(
                        idTeacher(),
                        jTextFieldRollN.getText(),
                        jTextFieldFName.getText(),
                        jTextFieldLName.getText(),
                        gender(),
                        jTextFieldPhone.getText(),
                        jTextFieldEmail.getText(),
                        jTextAreaAddress.getText()
                );

                boolean row = teacherDAO.update(teacher);

                if (row) {

                    JOptionPane.showMessageDialog(rootPane, "Teacher Data Updated!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                    //khi Update thành công thì show data lên bảng
                    fillData();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error Update Teacher!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButtonUMouseClicked

    private void jButtonUMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUMouseReleased

    private void jTextFieldEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmailActionPerformed

    private void jTextFieldPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhoneActionPerformed

    private void jRadioButtonMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMaleActionPerformed

    private void jTextFieldFNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFNameActionPerformed

    private void jTextFieldLNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLNameActionPerformed

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
        jTextFieldEmail.setText(null);
        jTextFieldRollN.setText(null);
        jTextFieldFName.setText(null);
        jTextFieldLName.setText(null);
        jTextFieldPhone.setText(null);
        jTextAreaAddress.setText(null);
        buttonGroup1.clearSelection();
    }//GEN-LAST:event_jButtonClearMouseClicked

    private void jButtonClearMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClearMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClearMouseReleased

    private void jPanelAddMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelAddMouseMoved
        // TODO add your handling code here:
        jButtonU.setBackground(new Color(53, 173, 164));
        jButtonU.setForeground(new Color(255, 255, 255));

        jButtonClear.setBackground(new Color(255, 51, 51));
        jButtonClear.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelAddMouseMoved

    private void jTextFieldRollNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRollNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRollNActionPerformed

    private void jTextFieldPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPhoneKeyTyped
        // TODO add your handling code here:
        //Allow only numbers
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldPhoneKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JLabel jButtonClear;
    public static javax.swing.JLabel jButtonU;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelFName;
    private javax.swing.JLabel jLabelGender;
    private javax.swing.JLabel jLabelLName;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelRollN;
    private javax.swing.JLabel jLabelTeacherU;
    private javax.swing.JPanel jPanelAdd;
    public static javax.swing.JRadioButton jRadioButtonFmale;
    public static javax.swing.JRadioButton jRadioButtonMale;
    private javax.swing.JScrollPane jScrollPane;
    public static javax.swing.JTextArea jTextAreaAddress;
    public static javax.swing.JTextField jTextFieldEmail;
    public static javax.swing.JTextField jTextFieldFName;
    public static javax.swing.JTextField jTextFieldLName;
    public static javax.swing.JTextField jTextFieldPhone;
    public static javax.swing.JTextField jTextFieldRollN;
    // End of variables declaration//GEN-END:variables
}
