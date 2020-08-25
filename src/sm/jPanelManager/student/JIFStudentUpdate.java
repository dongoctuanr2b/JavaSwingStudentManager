/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.student;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sm.jPanelManager.classes.Classes;
import sm.jPanelManager.classes.ClassesDAO;

/**
 *
 * @author Ngoc Tuan
 */
public class JIFStudentUpdate extends javax.swing.JInternalFrame {

    private StudentDAO studentDAO = new StudentDAO();
    private ClassesDAO classesDAO = new ClassesDAO();

    /**
     * Creates new form JInternalFrameUpdate
     */
    public JIFStudentUpdate() {
        initComponents();

        loadClass();
    }

    //fill data ra bảng
    private void fillData() {
        List<Student> students = studentDAO.getAll();

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("RollNumber");
        dtm.addColumn("Class");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Gender");
        dtm.addColumn("BirthDate");
        dtm.addColumn("Phone");
        dtm.addColumn("Email");
        dtm.addColumn("Address");

        for (Student student : students) {
            Vector<Object> vector = new Vector();
            vector.add(student.getRollNumber());
            vector.add(student.getClassName());
            vector.add(student.getFirstName());
            vector.add(student.getLastName());
            vector.add(student.getGenderString());
            vector.add(student.getBirthDate());
            vector.add(student.getPhone());
            vector.add(student.getEmail());
            vector.add(student.getAddress());

            dtm.addRow(vector);
        }

        JPStudentManager.jTableStudent.setModel(dtm);
    }

    private void loadClass() {
        //jComboBoxClass.setSelectedItem("");
        //AutoCompleteDecorator.decorate(jComboBoxClass);

        ClassesDAO classesDAO = new ClassesDAO();
        List<Classes> listClasses = classesDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Classes classes : listClasses) {
            dcbm.addElement(classes.getClassName());
        }

        jComboBoxClass.setModel(dcbm);
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

    //get idStudent từ bảng
    private int idStudent() {
        int index = JPStudentManager.jTableStudent.getSelectedRow();
        TableModel model = JPStudentManager.jTableStudent.getModel();

        int idStudent = 0;
        String rollN = model.getValueAt(index, 0).toString();

        for (Student student : studentDAO.getAll()) {
            if (rollN.equalsIgnoreCase(student.getRollNumber())) {
                idStudent = student.getIdStudent();
            }
        }

        return idStudent;
    }

    //validate form update student
    private boolean validateJIFStudentUpdate() {
        boolean flag = true;

        //check roll number phải là duy nhất ko được trùng, chuyển flag = false
        List<Student> students = studentDAO.getAll();

        int index = JPStudentManager.jTableStudent.getSelectedRow();
        TableModel model = JPStudentManager.jTableStudent.getModel();

        if (flag) {
            for (Student student : students) {
                String getJTextRN = jTextFieldRollN.getText();
                String getRowClick = model.getValueAt(index, 0).toString();

                if ((getJTextRN.toLowerCase().equals(student.getRollNumber().toLowerCase()))
                        && (!getRowClick.equalsIgnoreCase(getJTextRN))) {
                    JOptionPane.showMessageDialog(rootPane, "RollNumber " + getJTextRN + " already exist!!!", "Warning", JOptionPane.OK_OPTION);
                    flag = false;
                }
            }
        }

        if (flag) {
            for (Student student : students) {
                String getJTextRN = jTextFieldRollN.getText();

                if ((getJTextRN.toLowerCase().equalsIgnoreCase(student.getRollNumber().toLowerCase()))) {
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
                    || jDateChooserBDate.getDate() == null || gender() == -1) {

                JOptionPane.showMessageDialog(rootPane, "One Or More Empty Field!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                return false;
            } else if (jDateChooserBDate.getDate().compareTo(new Date()) > 0) {

                JOptionPane.showMessageDialog(rootPane, "Your birthday is greater than the current date!!!", "Warning", JOptionPane.ERROR_MESSAGE);

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

    //get idClass từ bảng
    private int idClass() {
        int idClass = 0;

        List<Classes> listClass = classesDAO.getAll();
        for (Classes classes : listClass) {
            if (jComboBoxClass.getSelectedItem().toString().equalsIgnoreCase(classes.getClassName())) {
                idClass = classes.getIdClass();
            }
        }

        return idClass;
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
        jPanelU = new javax.swing.JPanel();
        jDateChooserBDate = new com.toedter.calendar.JDateChooser();
        jButtonU = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
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
        jTextFieldRollN = new javax.swing.JTextField();
        jLabelRollN = new javax.swing.JLabel();
        jLabelBDate = new javax.swing.JLabel();
        jLabelLName = new javax.swing.JLabel();
        jTextFieldLName = new javax.swing.JTextField();
        jComboBoxClass = new javax.swing.JComboBox<>();
        jLabelClass = new javax.swing.JLabel();
        jLabelStudentU = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setResizable(true);

        jPanelU.setBackground(new java.awt.Color(255, 255, 255));
        jPanelU.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelUMouseMoved(evt);
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
        jScrollPane3.setViewportView(jTextAreaAddress);

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

        jTextFieldRollN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRollNActionPerformed(evt);
            }
        });

        jLabelRollN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelRollN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelRollN.setText("Roll number:");

        jLabelBDate.setBackground(new java.awt.Color(255, 255, 255));
        jLabelBDate.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelBDate.setText("Birthdate:");

        jLabelLName.setBackground(new java.awt.Color(255, 255, 255));
        jLabelLName.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelLName.setText("Last name:");

        jTextFieldLName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLNameActionPerformed(evt);
            }
        });

        jComboBoxClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelClass.setBackground(new java.awt.Color(255, 255, 255));
        jLabelClass.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelClass.setText("Class:");

        jLabelStudentU.setBackground(new java.awt.Color(255, 255, 255));
        jLabelStudentU.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabelStudentU.setText("Update Student");

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

        javax.swing.GroupLayout jPanelULayout = new javax.swing.GroupLayout(jPanelU);
        jPanelU.setLayout(jPanelULayout);
        jPanelULayout.setHorizontalGroup(
            jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelULayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelULayout.createSequentialGroup()
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelULayout.createSequentialGroup()
                                    .addGap(103, 103, 103)
                                    .addComponent(jDateChooserBDate, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelULayout.createSequentialGroup()
                                    .addComponent(jLabelRollN)
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldFName, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelULayout.createSequentialGroup()
                                            .addComponent(jTextFieldRollN, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(93, 93, 93)))))
                            .addGroup(jPanelULayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelFName, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelGender, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelBDate, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelAddress, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelULayout.createSequentialGroup()
                                        .addComponent(jRadioButtonMale)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButtonFmale))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelLName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelClass, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelEmail, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jButtonClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jComboBoxClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldLName, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(jPanelULayout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(jLabelStudentU)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelULayout.setVerticalGroup(
            jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelULayout.createSequentialGroup()
                .addComponent(jLabelStudentU)
                .addGap(29, 29, 29)
                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelULayout.createSequentialGroup()
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelClass)
                            .addComponent(jComboBoxClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelLName)
                            .addComponent(jTextFieldLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPhone)
                            .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelEmail)))
                    .addGroup(jPanelULayout.createSequentialGroup()
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRollN)
                            .addComponent(jTextFieldRollN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFName)
                            .addComponent(jTextFieldFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelGender)
                            .addComponent(jRadioButtonFmale)
                            .addComponent(jRadioButtonMale))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelBDate)
                            .addComponent(jDateChooserBDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAddress))))
                .addGap(18, 18, 18)
                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            if (validateJIFStudentUpdate()) {
                //format date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Student student = new Student(
                        idStudent(),
                        idClass(),
                        jTextFieldRollN.getText(),
                        jTextFieldFName.getText(),
                        jTextFieldLName.getText(),
                        gender(),
                        sdf.format(jDateChooserBDate.getDate()),
                        jTextFieldPhone.getText(),
                        jTextFieldEmail.getText(),
                        jTextAreaAddress.getText()
                );

                boolean row = studentDAO.update(student);

                if (row) {

                    JOptionPane.showMessageDialog(rootPane, "Student Data Updated!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                    //khi Update thành công thì show data lên bảng
                    fillData();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error Update Student!!!", "Warning", JOptionPane.ERROR_MESSAGE);
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

    private void jTextFieldRollNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRollNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRollNActionPerformed

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
        jTextFieldRollN.setText(null);
        jTextFieldEmail.setText(null);
        jTextFieldFName.setText(null);
        jTextFieldLName.setText(null);
        jTextFieldPhone.setText(null);
        jTextAreaAddress.setText(null);
        jComboBoxClass.setSelectedIndex(0);
        jDateChooserBDate.setDate(null);
        buttonGroup1.clearSelection();
    }//GEN-LAST:event_jButtonClearMouseClicked

    private void jButtonClearMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonClearMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClearMouseReleased

    private void jPanelUMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelUMouseMoved
        // TODO add your handling code here:
        jButtonU.setBackground(new Color(53, 173, 164));
        jButtonU.setForeground(new Color(255, 255, 255));

        jButtonClear.setBackground(new Color(255, 51, 51));
        jButtonClear.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelUMouseMoved

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
    public static javax.swing.JComboBox<String> jComboBoxClass;
    public static com.toedter.calendar.JDateChooser jDateChooserBDate;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelBDate;
    private javax.swing.JLabel jLabelClass;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelFName;
    private javax.swing.JLabel jLabelGender;
    private javax.swing.JLabel jLabelLName;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelRollN;
    private javax.swing.JLabel jLabelStudentU;
    private javax.swing.JPanel jPanelU;
    public static javax.swing.JRadioButton jRadioButtonFmale;
    public static javax.swing.JRadioButton jRadioButtonMale;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTextArea jTextAreaAddress;
    public static javax.swing.JTextField jTextFieldEmail;
    public static javax.swing.JTextField jTextFieldFName;
    public static javax.swing.JTextField jTextFieldLName;
    public static javax.swing.JTextField jTextFieldPhone;
    public static javax.swing.JTextField jTextFieldRollN;
    // End of variables declaration//GEN-END:variables
}
