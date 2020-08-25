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
import javax.swing.table.TableModel;
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
public class JIFTimetableUpdate extends javax.swing.JInternalFrame {

    private TimetableDAO timetableDAO = new TimetableDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private ClassesDAO classesDAO = new ClassesDAO();
    private RoomDAO roomDAO = new RoomDAO();

    /**
     * Creates new form JInternalFrameUpdate
     */
    public JIFTimetableUpdate() {
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
            dcbm.addElement(classes.getClassName());
        }

        jComboBoxClass.setModel(dcbm);
    }

    //load dữ liệu từ bảng tblSubject vào jComboboxSubject
    private void loadSubject() {
        List<Subject> subjects = subjectDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Subject subject : subjects) {
            dcbm.addElement(subject.getSubjectName());
        }

        jComboBoxSubject.setModel(dcbm);
    }

    //load dữ liệu từ bảng tblSubject vào jComboboxSubject
    private void loadTeacher() {
        List<Teacher> teachers = teacherDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Teacher teacher : teachers) {
            dcbm.addElement(teacher.getFirstName());
        }

        jComboBoxTeacher.setModel(dcbm);
    }

    //load dữ liệu từ bảng tblSubject vào jComboboxSubject
    private void loadRoom() {
        List<Room> rooms = roomDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Room room : rooms) {
            dcbm.addElement(room.getRoomName());
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
    private boolean validateTTUpdate() {
        boolean flag = true;

        //check subject name and class name phải là duy nhất ko được trùng, chuyển flag = false
        List<TimeTable> listTimeTable = timetableDAO.getAll();

        int index = JPTimetableManager.jTableTimetable.getSelectedRow();
        TableModel model = JPTimetableManager.jTableTimetable.getModel();

        if (flag) {
            for (TimeTable timeTable : listTimeTable) {
                String getCBS = jComboBoxSubject.getSelectedItem().toString(); //get Course name khi user chọn
                String getRowClickS = model.getValueAt(index, 2).toString();
                String getS = timeTable.getSubject(); //get course name trong từ bảng

                String getCBClN = jComboBoxClass.getSelectedItem().toString();
                String getRowClickClN = model.getValueAt(index, 1).toString();
                String getClN = timeTable.getClassName();

                boolean checkS = getCBS.equalsIgnoreCase(getS);
                boolean checkCBS = getRowClickS.equalsIgnoreCase(getCBS);
                boolean checkClN = getCBClN.equalsIgnoreCase(getClN);
                boolean checkCBClN = getRowClickClN.equalsIgnoreCase(getCBClN);

                if (((checkS && !checkCBS) && (checkClN && !checkCBClN))
                        || ((checkS && !checkCBS) && (checkClN && checkCBClN))
                        || ((checkS && checkCBS) && (checkClN && !checkCBClN))) {
                    JOptionPane.showMessageDialog(rootPane, "Class " + getClN
                            + " has a subject " + getCBS.toUpperCase() + " time table!!!", "Warning", JOptionPane.OK_OPTION);

                    flag = false;
                }
            }
        }

        if (flag) {
            for (TimeTable timeTable : listTimeTable) {
                String getCBS = jComboBoxSubject.getSelectedItem().toString(); //get Course name khi user chọn
                String getS = timeTable.getSubject(); //get course name trong từ bảng

                String getCBCLN = jComboBoxClass.getSelectedItem().toString();
                String getCLN = timeTable.getClassName();

                if ((getCBCLN.equalsIgnoreCase(getCLN)) && (getCBS.equalsIgnoreCase(getS))) {
                    int yes = JOptionPane.showConfirmDialog(rootPane, "Class " + getCLN
                            + " has a subject " + getCBS.toUpperCase() + " time table!!!"
                            + "Do you want to change?", "Warning", JOptionPane.YES_NO_OPTION);

                    if (yes == JOptionPane.YES_OPTION) {
                        flag = false;
                    } else {
                        flag = true;
                    }
                    break;
                }
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

    //get idStudent từ bảng
    private int idTimetable() {
        int index = JPTimetableManager.jTableTimetable.getSelectedRow();
        TableModel model = JPTimetableManager.jTableTimetable.getModel();

        return Integer.valueOf(model.getValueAt(index, 0).toString());
    }

    //get idStudent từ bảng
    private int idClass() {
        int idClass = 0;

        List<Classes> listClasses = classesDAO.getAll();
        for (Classes classes : listClasses) {
            if (jComboBoxClass.getSelectedItem().toString().equalsIgnoreCase(classes.getClassName())) {
                idClass = classes.getIdClass();
            }
        }

        return idClass;
    }

    //get idStudent từ bảng
    private int idSubject() {
        int idSubject = 0;

        List<Subject> subjects = subjectDAO.getAll();
        for (Subject subject : subjects) {
            if (jComboBoxSubject.getSelectedItem().toString().equalsIgnoreCase(subject.getSubjectName())) {
                idSubject = subject.getIdSubject();
            }
        }

        return idSubject;
    }

    //get idStudent từ bảng
    private int idRoom() {
        int idRoom = 0;

        List<Room> rooms = roomDAO.getAll();
        for (Room room : rooms) {
            if (jComboBoxRoom.getSelectedItem().toString().equalsIgnoreCase(room.getRoomName())) {
                idRoom = room.getIdRoom();
            }
        }

        return idRoom;
    }

    //get idStudent từ bảng
    private int idTeacher() {
        int idTeacher = 0;

        List<Teacher> teachers = teacherDAO.getAll();
        for (Teacher teacher : teachers) {
            if (jComboBoxTeacher.getSelectedItem().toString().equalsIgnoreCase(teacher.getFirstName())) {
                idTeacher = teacher.getIdTeacher();
            }
        }

        return idTeacher;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelU = new javax.swing.JPanel();
        jButtonUpdate = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
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
        jLabelTimetableU = new javax.swing.JLabel();
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
        setBorder(null);

        jPanelU.setBackground(new java.awt.Color(255, 255, 255));
        jPanelU.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelUMouseMoved(evt);
            }
        });

        jButtonUpdate.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;Update</div></html> ");
        jButtonUpdate.setBackground(new java.awt.Color(53, 173, 164));
        jButtonUpdate.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(53, 173, 164), 1, true));
        jButtonUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonUpdate.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonUpdate.setOpaque(true);
        jButtonUpdate.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButtonUpdateMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButtonUpdateMouseMoved(evt);
            }
        });
        jButtonUpdate.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jButtonUpdateMouseWheelMoved(evt);
            }
        });
        jButtonUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonUpdateMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonUpdateMouseReleased(evt);
            }
        });

        jTextAreaDesc.setColumns(20);
        jTextAreaDesc.setRows(5);
        jScrollPane.setViewportView(jTextAreaDesc);

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

        jLabelTimetableU.setText("Update Timetable");
        jLabelTimetableU.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTimetableU.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N

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

        jComboBoxClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxClassActionPerformed(evt);
            }
        });

        jComboBoxRoom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxTeacher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCheckBoxMo.setText("Monday");

        jCheckBoxTu.setText("Tuesday");

        jCheckBoxWed.setText("Wednesday");

        jCheckBoxThu.setText("Thursday");

        jCheckBoxFr.setText("Friday");

        jCheckBoxSat.setText("Saturday");

        javax.swing.GroupLayout jPanelULayout = new javax.swing.GroupLayout(jPanelU);
        jPanelU.setLayout(jPanelULayout);
        jPanelULayout.setHorizontalGroup(
            jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelULayout.createSequentialGroup()
                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelULayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabeClass, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDesc)
                            .addComponent(jLabelDate))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelULayout.createSequentialGroup()
                                .addComponent(jCheckBoxWed)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBoxThu))
                            .addGroup(jPanelULayout.createSequentialGroup()
                                .addComponent(jCheckBoxFr)
                                .addGap(18, 18, 18)
                                .addComponent(jCheckBoxSat))
                            .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboBoxClass, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelULayout.createSequentialGroup()
                                    .addComponent(jCheckBoxMo)
                                    .addGap(18, 18, 18)
                                    .addComponent(jCheckBoxTu))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelSubject)
                            .addComponent(jLabeEndTime)
                            .addComponent(jLabelLStartTime)
                            .addComponent(jLabelRoom)
                            .addComponent(jLabeTeacher))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxRoom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxTeacher, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelULayout.createSequentialGroup()
                                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(timePickerStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(timePickerEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57))
                            .addComponent(jComboBoxSubject, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelULayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
            .addGroup(jPanelULayout.createSequentialGroup()
                .addGap(255, 255, 255)
                .addComponent(jLabelTimetableU)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelULayout.setVerticalGroup(
            jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelULayout.createSequentialGroup()
                .addComponent(jLabelTimetableU)
                .addGap(29, 29, 29)
                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelULayout.createSequentialGroup()
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabeClass)
                            .addComponent(jComboBoxClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDate)
                            .addComponent(jCheckBoxMo)
                            .addComponent(jCheckBoxTu))
                        .addGap(15, 15, 15)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxWed)
                            .addComponent(jCheckBoxThu))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxFr)
                            .addComponent(jCheckBoxSat))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDesc)))
                    .addGroup(jPanelULayout.createSequentialGroup()
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSubject)
                            .addComponent(jComboBoxSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelLStartTime)
                            .addComponent(timePickerStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabeEndTime)
                            .addComponent(timePickerEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRoom)
                            .addComponent(jComboBoxRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabeTeacher)
                            .addComponent(jComboBoxTeacher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanelULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void jButtonUpdateMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUpdateMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUpdateMouseDragged

    private void jButtonUpdateMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUpdateMouseMoved
        // TODO add your handling code here:
        jButtonUpdate.setBackground(new Color(255, 255, 255));
        jButtonUpdate.setForeground(new Color(53, 173, 164));
    }//GEN-LAST:event_jButtonUpdateMouseMoved

    private void jButtonUpdateMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jButtonUpdateMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUpdateMouseWheelMoved

    private void jButtonUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUpdateMouseClicked
        // TODO add your handling code here:
        //Check form add student
        try {
            if (validateTTUpdate()) {

                TimeTable timeTable = new TimeTable(
                        idTimetable(),
                        idClass(),
                        idSubject(),
                        String.valueOf(timePickerStart.getTime()),
                        String.valueOf(timePickerEnd.getTime()),
                        days(),
                        idRoom(),
                        idTeacher(),
                        jTextAreaDesc.getText()
                );

                boolean row = timetableDAO.update(timeTable);

                if (row) {
                    JOptionPane.showMessageDialog(rootPane, "TimeTable Data Updated!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                    //khi Update thành công thì show data lên bảng
                    fillData();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error Update TimeTable!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButtonUpdateMouseClicked

    private void jButtonUpdateMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUpdateMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUpdateMouseReleased

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

    private void jPanelUMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelUMouseMoved
        // TODO add your handling code here:
        jButtonUpdate.setBackground(new Color(53, 173, 164));
        jButtonUpdate.setForeground(new Color(255, 255, 255));

        jButtonClear.setBackground(new Color(255, 51, 51));
        jButtonClear.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelUMouseMoved

    private void jComboBoxClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxClassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxClassActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel jButtonClear;
    public static javax.swing.JLabel jButtonUpdate;
    public static javax.swing.JCheckBox jCheckBoxFr;
    public static javax.swing.JCheckBox jCheckBoxMo;
    public static javax.swing.JCheckBox jCheckBoxSat;
    public static javax.swing.JCheckBox jCheckBoxThu;
    public static javax.swing.JCheckBox jCheckBoxTu;
    public static javax.swing.JCheckBox jCheckBoxWed;
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
    private javax.swing.JLabel jLabelTimetableU;
    private javax.swing.JPanel jPanelU;
    private javax.swing.JScrollPane jScrollPane;
    public static javax.swing.JTextArea jTextAreaDesc;
    public static com.github.lgooddatepicker.components.TimePicker timePickerEnd;
    public static com.github.lgooddatepicker.components.TimePicker timePickerStart;
    // End of variables declaration//GEN-END:variables
}
