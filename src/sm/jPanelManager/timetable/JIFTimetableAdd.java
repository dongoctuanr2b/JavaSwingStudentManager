/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.timetable;

import java.awt.Color;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sm.jPanelManager.classes.Classes;
import sm.jPanelManager.classes.ClassesDAO;
import sm.jPanelManager.room.Room;
import sm.jPanelManager.room.RoomDAO;
import sm.jPanelManager.subject.Subject;
import sm.jPanelManager.subject.SubjectDAO;
import sm.jPanelManager.teacher.Teacher;
import sm.jPanelManager.teacher.TeacherDAO;

/**
 *
 * @author Ngoc Tuan
 */
public class JIFTimetableAdd extends javax.swing.JInternalFrame {

    private TimetableDAO timetableDAO = new TimetableDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private ClassesDAO classesDAO = new ClassesDAO();
    private RoomDAO roomDAO = new RoomDAO();

    /**
     * Creates new form NewJInternalFrame
     */
    public JIFTimetableAdd() {
        initComponents();

        loadClass();
        loadRoom();
        loadSubject();
        loadTeacher();

        timePickerStart.setTimeToNow();
        timePickerEnd.setTimeToNow();
    }

    //fill data ra bảng
    public void fillData() {

        List<TimeTable> listTimeTable = timetableDAO.getAll();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("idTT");
        dtm.addColumn("Class");
        dtm.addColumn("Subject");
        dtm.addColumn("Start time");
        dtm.addColumn("End time");
        dtm.addColumn("Days");
        dtm.addColumn("Room");
        dtm.addColumn("Teacher");
        dtm.addColumn("Desc");

        for (TimeTable timeTable : listTimeTable) {
            Vector<Object> vector = new Vector();
            vector.add(timeTable.getIdTT());
            vector.add(timeTable.getClassName());
            vector.add(timeTable.getSubject());
            vector.add(timeTable.getStartTime());
            vector.add(timeTable.getEndTime());
            vector.add(timeTable.getDate());
            vector.add(timeTable.getRoom());
            vector.add(timeTable.getTeacher());
            vector.add(timeTable.getDesc());

            dtm.addRow(vector);
        }

        JPTimetableManager.jTableTimetable.setModel(dtm);

        JPTimetableManager.jTableTimetable.removeColumn(JPTimetableManager.jTableTimetable.getColumnModel().getColumn(0));
    }

    //load dữ liệu từ bảng tblClass vào jComboboxClass
    private void loadClass() {
        List<Classes> listClasses = classesDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Classes classes : listClasses) {
            dcbm.addElement(classes);
        }

        jComboBoxClass.setModel(dcbm);
    }

    //load dữ liệu từ bảng tblSubject vào jComboboxSubject
    private void loadSubject() {
        List<Subject> subjects = subjectDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Subject subject : subjects) {
            dcbm.addElement(subject);
        }

        jComboBoxSubject.setModel(dcbm);
    }

    //load dữ liệu từ bảng tblSubject vào jComboboxSubject
    private void loadTeacher() {
        List<Teacher> teachers = teacherDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Teacher teacher : teachers) {
            dcbm.addElement(teacher);
        }

        jComboBoxTeacher.setModel(dcbm);
    }

    //load dữ liệu từ bảng tblSubject vào jComboboxSubject
    private void loadRoom() {
        List<Room> rooms = roomDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Room room : rooms) {
            dcbm.addElement(room);
        }

        jComboBoxRoom.setModel(dcbm);
    }

    //Xử lý radio button của gender
    private String days() {
        String days = "";
        if (jCheckBoxMo.isSelected()) {
            days += jCheckBoxMo.getText() + "-";
        }
        if (jCheckBoxTu.isSelected()) {
            days += jCheckBoxTu.getText() + "-";
        }
        if (jCheckBoxWed.isSelected()) {
            days += jCheckBoxWed.getText() + "-";
        }
        if (jCheckBoxThu.isSelected()) {
            days += jCheckBoxThu.getText() + "-";
        }
        if (jCheckBoxFr.isSelected()) {
            days += jCheckBoxFr.getText() + "-";
        }
        if (jCheckBoxSat.isSelected()) {
            days += jCheckBoxSat.getText() + "-";
        }

        return days.substring(0, days.length() - 1);
    }

    //validate form add timetable
    private boolean validateTTAdd() {
        boolean flag = true;

        //check subject name and class name phải là duy nhất ko được trùng, chuyển flag = false
        List<TimeTable> listTimeTable = timetableDAO.getAll();
        for (TimeTable timeTable : listTimeTable) {
            String getCBS = jComboBoxSubject.getSelectedItem().toString(); //get Course name khi user chọn
            String getS = timeTable.getSubject(); //get course name trong từ bảng
            String getCBCLN = jComboBoxClass.getSelectedItem().toString();
            String getCLN = timeTable.getClassName();
            if (getCBCLN.toLowerCase().equals(getCLN.toLowerCase()) && getCBS.equals(getS)) {
                JOptionPane.showMessageDialog(rootPane, "Class " + getCLN
                        + " has a subject " + getCBS.toUpperCase() + " time table!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                flag = false;
                break;
            }
        }
        if (flag) {
            if (jTextAreaDesc.getText().isEmpty() || timePickerEnd.getTime() == null || timePickerStart.getTime() == null
                    || (!jCheckBoxMo.isSelected() && !jCheckBoxTu.isSelected() && !jCheckBoxWed.isSelected()
                    && !jCheckBoxThu.isSelected() && !jCheckBoxFr.isSelected() && !jCheckBoxSat.isSelected())) {

                JOptionPane.showMessageDialog(rootPane, "One Or More Empty Field!!!", "Warning", JOptionPane.ERROR_MESSAGE);

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

        jPanelAdd = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaDesc = new javax.swing.JTextArea();
        jLabelDesc = new javax.swing.JLabel();
        jLabeTeacher = new javax.swing.JLabel();
        jLabelRoom = new javax.swing.JLabel();
        jLabeEndTime = new javax.swing.JLabel();
        jLabeClass = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jLabelLStartTime = new javax.swing.JLabel();
        jComboBoxSubject = new javax.swing.JComboBox<>();
        jLabelSubject = new javax.swing.JLabel();
        jLabelTimetableAdd = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JLabel();
        jComboBoxClass = new javax.swing.JComboBox<>();
        jComboBoxRoom = new javax.swing.JComboBox<>();
        jComboBoxTeacher = new javax.swing.JComboBox<>();
        timePickerEnd = new com.github.lgooddatepicker.components.TimePicker();
        timePickerStart = new com.github.lgooddatepicker.components.TimePicker();
        jCheckBoxMo = new javax.swing.JCheckBox();
        jCheckBoxTu = new javax.swing.JCheckBox();
        jCheckBoxWed = new javax.swing.JCheckBox();
        jCheckBoxThu = new javax.swing.JCheckBox();
        jCheckBoxFr = new javax.swing.JCheckBox();
        jCheckBoxSat = new javax.swing.JCheckBox();

        setClosable(true);
        setResizable(true);
        setBackground(new java.awt.Color(204, 204, 204));
        setBorder(null);

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

        jTextAreaDesc.setColumns(20);
        jTextAreaDesc.setRows(5);
        jScrollPane3.setViewportView(jTextAreaDesc);

        jLabelDesc.setText("Description:");
        jLabelDesc.setBackground(new java.awt.Color(255, 255, 255));
        jLabelDesc.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabeTeacher.setText("Teacher:");
        jLabeTeacher.setBackground(new java.awt.Color(255, 255, 255));
        jLabeTeacher.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabelRoom.setText("Room:");
        jLabelRoom.setBackground(new java.awt.Color(255, 255, 255));
        jLabelRoom.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabeEndTime.setText("End time:");
        jLabeEndTime.setBackground(new java.awt.Color(255, 255, 255));
        jLabeEndTime.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabeClass.setText("Class:");
        jLabeClass.setBackground(new java.awt.Color(255, 255, 255));
        jLabeClass.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabelDate.setText("Date:");
        jLabelDate.setBackground(new java.awt.Color(255, 255, 255));
        jLabelDate.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabelLStartTime.setText("Start time:");
        jLabelLStartTime.setBackground(new java.awt.Color(255, 255, 255));
        jLabelLStartTime.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jComboBoxSubject.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelSubject.setText("Subject:");
        jLabelSubject.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSubject.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N

        jLabelTimetableAdd.setText("Add Timetable");
        jLabelTimetableAdd.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTimetableAdd.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N

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

        jComboBoxClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxRoom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxTeacher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCheckBoxMo.setText("Monday");

        jCheckBoxTu.setText("Tuesday");

        jCheckBoxWed.setText("Wednesday");

        jCheckBoxThu.setText("Thursday");

        jCheckBoxFr.setText("Friday");

        jCheckBoxSat.setText("Saturday");

        javax.swing.GroupLayout jPanelAddLayout = new javax.swing.GroupLayout(jPanelAdd);
        jPanelAdd.setLayout(jPanelAddLayout);
        jPanelAddLayout.setHorizontalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabeClass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDesc)
                            .addComponent(jLabelDate))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAddLayout.createSequentialGroup()
                                .addComponent(jCheckBoxWed)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBoxThu))
                            .addGroup(jPanelAddLayout.createSequentialGroup()
                                .addComponent(jCheckBoxFr)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBoxSat))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboBoxClass, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelAddLayout.createSequentialGroup()
                                    .addComponent(jCheckBoxMo)
                                    .addGap(18, 18, 18)
                                    .addComponent(jCheckBoxTu))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabeEndTime, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelLStartTime, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelRoom, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabeTeacher, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelSubject, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxSubject, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelAddLayout.createSequentialGroup()
                                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(timePickerStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(timePickerEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57))
                            .addComponent(jComboBoxRoom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxTeacher, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelTimetableAdd)
                .addGap(251, 251, 251))
        );
        jPanelAddLayout.setVerticalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addComponent(jLabelTimetableAdd)
                .addGap(29, 29, 29)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabeClass)
                            .addComponent(jComboBoxClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDate)
                            .addComponent(jCheckBoxMo)
                            .addComponent(jCheckBoxTu))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxWed)
                            .addComponent(jCheckBoxThu))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxFr)
                            .addComponent(jCheckBoxSat))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDesc)))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSubject)
                            .addComponent(jComboBoxSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelLStartTime)
                            .addComponent(timePickerStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabeEndTime)
                            .addComponent(timePickerEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRoom)
                            .addComponent(jComboBoxRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabeTeacher)
                            .addComponent(jComboBoxTeacher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
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
        if (validateTTAdd()) {

            TimeTable timeTable = new TimeTable(
                    ((Classes) jComboBoxClass.getSelectedItem()).getIdClass(),
                    ((Subject) jComboBoxSubject.getSelectedItem()).getIdSubject(),
                    String.valueOf(timePickerStart.getTime()),
                    String.valueOf(timePickerEnd.getTime()),
                    days(),
                    ((Room) jComboBoxRoom.getSelectedItem()).getIdRoom(),
                    ((Teacher) jComboBoxTeacher.getSelectedItem()).getIdTeacher(),
                    jTextAreaDesc.getText()
            );

            boolean row = timetableDAO.save(timeTable);

            if (row) {
                JOptionPane.showMessageDialog(rootPane, "New TimeTable Added!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                try {
                    fillData();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error add TimeTable!!!", "Warning", JOptionPane.ERROR_MESSAGE);
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
        jTextAreaDesc.setText(null);
        jComboBoxClass.setSelectedIndex(0);
        jComboBoxRoom.setSelectedIndex(0);
        jComboBoxSubject.setSelectedIndex(0);
        jComboBoxTeacher.setSelectedIndex(0);
        timePickerStart.setTimeToNow();
        timePickerEnd.setTimeToNow();
        jCheckBoxFr.setSelected(false);
        jCheckBoxMo.setSelected(false);
        jCheckBoxSat.setSelected(false);
        jCheckBoxThu.setSelected(false);
        jCheckBoxTu.setSelected(false);
        jCheckBoxWed.setSelected(false);
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
    private javax.swing.JCheckBox jCheckBoxFr;
    private javax.swing.JCheckBox jCheckBoxMo;
    private javax.swing.JCheckBox jCheckBoxSat;
    private javax.swing.JCheckBox jCheckBoxThu;
    private javax.swing.JCheckBox jCheckBoxTu;
    private javax.swing.JCheckBox jCheckBoxWed;
    public static javax.swing.JComboBox<String> jComboBoxClass;
    public static javax.swing.JComboBox<String> jComboBoxRoom;
    public static javax.swing.JComboBox<String> jComboBoxSubject;
    public static javax.swing.JComboBox<String> jComboBoxTeacher;
    private javax.swing.JLabel jLabeClass;
    private javax.swing.JLabel jLabeEndTime;
    private javax.swing.JLabel jLabeTeacher;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelDesc;
    private javax.swing.JLabel jLabelLStartTime;
    private javax.swing.JLabel jLabelRoom;
    private javax.swing.JLabel jLabelSubject;
    private javax.swing.JLabel jLabelTimetableAdd;
    private javax.swing.JPanel jPanelAdd;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaDesc;
    private com.github.lgooddatepicker.components.TimePicker timePickerEnd;
    private com.github.lgooddatepicker.components.TimePicker timePickerStart;
    // End of variables declaration//GEN-END:variables
}
