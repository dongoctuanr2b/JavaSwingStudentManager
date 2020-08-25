/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.teacher;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sm.mainFrame.MainFrame;

/**
 *
 * @author Ngoc Tuan
 */
public class JIFTeacherAdd extends javax.swing.JInternalFrame {

    private TeacherDAO teacherDAO = new TeacherDAO();

    /**
     * Creates new form NewJInternalFrame
     */
    public JIFTeacherAdd() {
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

    //validate email
    private boolean isEmailValid() {
        //validate email: ^([\w]+)@([\w]+)\.([\w]+)$
        Pattern pmail = Pattern.compile("^([\\w]+)@([\\w]+)\\.([\\w]+)$");//định nghĩa chuỗi hợp lệ
        Matcher mMail = pmail.matcher(jTextFieldEmail.getText()); //get text ở email để match với biến pmail 
        boolean isEmailValid = mMail.matches(); //tạo biến boolean, nếu match is true, not match is false

        return isEmailValid;
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

    //validate form add student
    private boolean validateTeacherAdd() {
        boolean flag = true;

        //check roll number phải là duy nhất ko được trùng, chuyển flag = false
        List<Teacher> teachers = teacherDAO.getAll();
        for (Teacher teacher : teachers) {
            String getJTextRN = jTextFieldRollN.getText();
            String getRN = teacher.getRollNumber();
            if (getJTextRN.toLowerCase().equals(getRN.toLowerCase())) {
                JOptionPane.showMessageDialog(rootPane, "RollNumber " + getJTextRN + " already exist!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                flag = false;
                break;
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
        jButtonSave = new javax.swing.JLabel();
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
        jLabelTeacherAdd = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JLabel();
        jTextFieldRollN = new javax.swing.JTextField();
        jLabelRollN = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setBorder(null);
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

        jLabelTeacherAdd.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTeacherAdd.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabelTeacherAdd.setText("Add Teacher");

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

        jTextFieldRollN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRollNActionPerformed(evt);
            }
        });

        jLabelRollN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelRollN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelRollN.setText("Roll number:");

        javax.swing.GroupLayout jPanelAddLayout = new javax.swing.GroupLayout(jPanelAdd);
        jPanelAdd.setLayout(jPanelAddLayout);
        jPanelAddLayout.setHorizontalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelPhone)
                            .addComponent(jLabelLName)
                            .addGroup(jPanelAddLayout.createSequentialGroup()
                                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                                        .addComponent(jLabelAddress)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextFieldRollN, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanelAddLayout.createSequentialGroup()
                                                .addComponent(jLabelRollN)
                                                .addGap(160, 160, 160))
                                            .addGroup(jPanelAddLayout.createSequentialGroup()
                                                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabelGender)
                                                    .addComponent(jLabelFName))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextFieldFName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanelAddLayout.createSequentialGroup()
                                                        .addComponent(jRadioButtonMale)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jRadioButtonFmale)))))
                                        .addGap(83, 83, 83)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(jLabelEmail)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldLName, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addGap(227, 227, 227)
                .addComponent(jLabelTeacherAdd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelAddLayout.setVerticalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addComponent(jLabelTeacherAdd)
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
                    .addComponent(jTextFieldFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFName))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButtonFmale)
                        .addComponent(jRadioButtonMale))
                    .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelEmail)
                        .addComponent(jLabelGender)))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
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
        if (validateTeacherAdd()) {
            //format date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Teacher teacher = new Teacher(
                    jTextFieldRollN.getText(),
                    jTextFieldFName.getText(),
                    jTextFieldLName.getText(),
                    gender(),
                    jTextFieldPhone.getText(),
                    jTextFieldEmail.getText(),
                    jTextAreaAddress.getText()
            );

            boolean row = teacherDAO.save(teacher);

            if (row) {
                JOptionPane.showMessageDialog(rootPane, "New Teacher Added!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                //count số teacher
                int totalTeacher = teacherDAO.count();
                MainFrame.jLabelCountTeacher.setText("" + totalTeacher);

                fillData();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error add Teacher!!!", "Warning", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonSaveMouseClicked

    private void jButtonSaveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSaveMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSaveMouseReleased

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
        jButtonSave.setBackground(new Color(53, 173, 164));
        jButtonSave.setForeground(new Color(255, 255, 255));

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
    public static javax.swing.JLabel jButtonSave;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelFName;
    private javax.swing.JLabel jLabelGender;
    private javax.swing.JLabel jLabelLName;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelRollN;
    private javax.swing.JLabel jLabelTeacherAdd;
    private javax.swing.JPanel jPanelAdd;
    private javax.swing.JRadioButton jRadioButtonFmale;
    private javax.swing.JRadioButton jRadioButtonMale;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTextArea jTextAreaAddress;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFName;
    private javax.swing.JTextField jTextFieldLName;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldRollN;
    // End of variables declaration//GEN-END:variables
}