/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.timetable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sm.jPanelManager.classes.Classes;
import sm.jPanelManager.classes.ClassesDAO;
import sm.jPanelManager.recycle.JPRecycle;
import sm.jPanelManager.room.Room;
import sm.jPanelManager.room.RoomDAO;
import sm.jPanelManager.subject.Subject;
import sm.jPanelManager.subject.SubjectDAO;
import sm.jPanelManager.teacher.Teacher;
import sm.jPanelManager.teacher.TeacherDAO;
import sm.mainFrame.MainFrame;

/**
 *
 * @author Ngoc Tuan
 */
public class JPTimetableManager extends javax.swing.JPanel {

    private JIFTimetableAdd jIFTimetableAdd;
    private JIFTimetableUpdate jIFTimetableUpdate;

    private TimetableDAO timetableDAO = new TimetableDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private ClassesDAO classesDAO = new ClassesDAO();
    private RoomDAO roomDAO = new RoomDAO();

    private static String classN;
    private static String subjectN;

    /**
     * Creates new form JPanelStudent
     */
    public JPTimetableManager() {
        initComponents();

        setVisible(false);
        setBounds(0, 0, MainFrame.jPanelContent.getSize().width + 105, MainFrame.jPanelContent.getSize().height);

        jTableTimetable.setRowHeight(30);
        jTableTimetable.setShowGrid(true);
        jTableTimetable.setGridColor(new Color(102, 102, 102));

        fillData();
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

        jTableTimetable.setModel(dtm);

        jTableTimetable.removeColumn(jTableTimetable.getColumnModel().getColumn(0));
    }

    private void setData() {
        List<TimeTable> timeTables = timetableDAO.getAll();

        if (timeTables.size() > 0) {
            int index = jTableTimetable.getSelectedRow();
            TableModel model = jTableTimetable.getModel();

            if (jIFTimetableUpdate != null) {//tránh lỗi NULLpointerEx do chưa khởi tạo IFAddStudentForm mà đã click vào bảng
                //jcombobox class name
                for (int i = 0; i < JIFTimetableUpdate.jComboBoxClass.getItemCount(); i++) {
                    if (JIFTimetableUpdate.jComboBoxClass.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 1).toString())) {
                        JIFTimetableUpdate.jComboBoxClass.setSelectedIndex(i);
                    }
                }

                //jcombobox subject name
                for (int i = 0; i < JIFTimetableUpdate.jComboBoxSubject.getItemCount(); i++) {
                    if (JIFTimetableUpdate.jComboBoxSubject.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 2).toString())) {
                        JIFTimetableUpdate.jComboBoxSubject.setSelectedIndex(i);
                    }
                }

                //jcombobox teacher name
                for (int i = 0; i < JIFTimetableUpdate.jComboBoxTeacher.getItemCount(); i++) {
                    if (JIFTimetableUpdate.jComboBoxTeacher.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 7).toString())) {
                        JIFTimetableUpdate.jComboBoxTeacher.setSelectedIndex(i);
                    }
                }

                //jcombobox room name
                for (int i = 0; i < JIFTimetableUpdate.jComboBoxRoom.getItemCount(); i++) {
                    if (JIFTimetableUpdate.jComboBoxRoom.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 6).toString())) {
                        JIFTimetableUpdate.jComboBoxRoom.setSelectedIndex(i);
                    }
                }

                //start Time
                JIFTimetableUpdate.timePickerStart.setText(model.getValueAt(index, 3).toString());
                //end Time
                JIFTimetableUpdate.timePickerEnd.setText(model.getValueAt(index, 4).toString());

                //days
                JIFTimetableUpdate.jCheckBoxMo.setSelected(false);//bỏ tích tất cả checkbox trước khi user click row mới 
                JIFTimetableUpdate.jCheckBoxTu.setSelected(false);
                JIFTimetableUpdate.jCheckBoxWed.setSelected(false);
                JIFTimetableUpdate.jCheckBoxThu.setSelected(false);
                JIFTimetableUpdate.jCheckBoxFr.setSelected(false);
                JIFTimetableUpdate.jCheckBoxSat.setSelected(false);
                String[] days = model.getValueAt(index, 5).toString().split("-"); //bỏ dấu gạch ngang trong chuỗi rồi chuyển thàng mảng
                for (String day : days) {
                    if (JIFTimetableUpdate.jCheckBoxMo.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxMo.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxTu.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxTu.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxWed.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxWed.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxThu.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxThu.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxFr.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxFr.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxSat.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxSat.setSelected(true);
                    }
                }

                JIFTimetableUpdate.jTextAreaDesc.setText(model.getValueAt(index, 8).toString());
            }
        }
    }

    //get idTimetable từ bảng
    private String idTimetable() {
        int index = jTableTimetable.getSelectedRow();
        TableModel model = jTableTimetable.getModel();

        return model.getValueAt(index, 0).toString();
    }

    private void insertTempTimetable() {
        int index = jTableTimetable.getSelectedRow();
        TableModel model = jTableTimetable.getModel();

        TimeTable timeTable = new TimeTable(
                model.getValueAt(index, 1).toString(),
                model.getValueAt(index, 2).toString(),
                model.getValueAt(index, 3).toString(),
                model.getValueAt(index, 4).toString(),
                model.getValueAt(index, 5).toString(),
                model.getValueAt(index, 6).toString(),
                model.getValueAt(index, 7).toString(),
                model.getValueAt(index, 8).toString()
        );

        boolean row = timetableDAO.saveTimetableTemp(timeTable);

        if (row) {
            System.out.println("insert timetable temp success");
        } else {
            System.out.println("failed");
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
        jLabelRecycle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTimetable = new javax.swing.JTable();
        jPanelTop = new javax.swing.JPanel();
        jLabelStudent = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(900, 509));
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
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Teacher", "Class" }));
        jComboBoxSearch.setOpaque(false);

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

        jLabelRecycle.setBackground(new java.awt.Color(204, 204, 204));
        jLabelRecycle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelRecycle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/delete.png"))); // NOI18N
        jLabelRecycle.setText("<html><div style='text-align: right;'>&emsp;Recycle bin</div></html> ");
        jLabelRecycle.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabelRecycle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelRecycle.setOpaque(true);
        jLabelRecycle.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelRecycleMouseMoved(evt);
            }
        });
        jLabelRecycle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRecycleMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelRecycleMouseReleased(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelRecycle, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
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
                        .addComponent(jLabelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelRecycle, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelMidLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSearch)
                    .addComponent(jComboBoxSearch)
                    .addComponent(jLabelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTableTimetable.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTableTimetable.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableTimetable.setFillsViewportHeight(true);
        jTableTimetable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTableTimetableMouseMoved(evt);
            }
        });
        jTableTimetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableTimetableMouseReleased(evt);
            }
        });
        jTableTimetable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableTimetableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableTimetable);

        jPanelTop.setBackground(new java.awt.Color(153, 153, 153));
        jPanelTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelTopMouseMoved(evt);
            }
        });

        jLabelStudent.setFont(new java.awt.Font("Century Gothic", 1, 22)); // NOI18N
        jLabelStudent.setForeground(new java.awt.Color(255, 255, 255));
        jLabelStudent.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Timetable</div></html> ");

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelStudent)
                .addContainerGap())
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelStudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
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

        jLabelRecycle.setBackground(new Color(204, 204, 204));
        jLabelRecycle.setForeground(new Color(0, 0, 0));

        jLabelRefresh.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_jPanelMidMouseMoved

    private void jTableTimetableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTimetableMouseMoved
        // TODO add your handling code here:
        jLabelAdd.setBackground(new Color(204, 204, 204));
        jLabelAdd.setForeground(new Color(0, 0, 0));

        jLabelDelete.setBackground(new Color(204, 204, 204));
        jLabelDelete.setForeground(new Color(0, 0, 0));

        jLabelUpdate.setBackground(new Color(204, 204, 204));
        jLabelUpdate.setForeground(new Color(0, 0, 0));

        jLabelRecycle.setBackground(new Color(204, 204, 204));
        jLabelRecycle.setForeground(new Color(0, 0, 0));

        jLabelRefresh.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_jTableTimetableMouseMoved

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        jLabelAdd.setBackground(new Color(204, 204, 204));
        jLabelAdd.setForeground(new Color(0, 0, 0));

        jLabelDelete.setBackground(new Color(204, 204, 204));
        jLabelDelete.setForeground(new Color(0, 0, 0));

        jLabelUpdate.setBackground(new Color(204, 204, 204));
        jLabelUpdate.setForeground(new Color(0, 0, 0));

        jLabelRecycle.setBackground(new Color(204, 204, 204));
        jLabelRecycle.setForeground(new Color(0, 0, 0));

        jLabelRefresh.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_formMouseMoved

    private void jPanelTopMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelTopMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelTopMouseMoved

    private void jLabelAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelAddMouseClicked
        // TODO add your handling code here:
        if (jLabelAdd.isEnabled()) {
            if (jIFTimetableAdd == null) {
                jIFTimetableAdd = new JIFTimetableAdd();
                jTableTimetable.add(jIFTimetableAdd);
                jIFTimetableAdd.setBounds(0, 0, jTableTimetable.getSize().width / 2 + 200, 420);
                jIFTimetableAdd.setVisible(true);

                if (jIFTimetableUpdate != null) {
                    jIFTimetableUpdate.setVisible(false);
                }
            }
            jIFTimetableAdd.setVisible(true);

            if (jIFTimetableUpdate != null) {
                jIFTimetableUpdate.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableTimetable.getSize();
            Dimension sizejInternalFrameAdd = jIFTimetableAdd.getSize();
            jIFTimetableAdd.setLocation((sizejTableStudent.width - sizejInternalFrameAdd.width) / 2, (sizejTableStudent.height - sizejInternalFrameAdd.height) / 2);
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
            if (jIFTimetableUpdate == null) {
                jIFTimetableUpdate = new JIFTimetableUpdate();
                jTableTimetable.add(jIFTimetableUpdate);
                jIFTimetableUpdate.setBounds(0, 0, jTableTimetable.getSize().width / 2 + 200, 420);
                jIFTimetableUpdate.setVisible(true);

                if (jIFTimetableAdd != null) {
                    jIFTimetableAdd.setVisible(false);
                }

                try {
                    setData();
                } catch (Exception e) {
                }
            }
            jIFTimetableUpdate.setVisible(true);

            if (jIFTimetableAdd != null) {
                jIFTimetableAdd.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableTimetable.getSize();
            Dimension sizejInternalFrameU = jIFTimetableUpdate.getSize();
            jIFTimetableUpdate.setLocation((sizejTableStudent.width - sizejInternalFrameU.width) / 2, (sizejTableStudent.height - sizejInternalFrameU.height) / 2);
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
                int position = jTableTimetable.getSelectedRow();

                if (position >= 0) {
                    int yes = JOptionPane.showConfirmDialog(this, "Are you sure want to this TimeTable delete?", "Warning", JOptionPane.YES_NO_OPTION);

                    if (yes == JOptionPane.YES_OPTION) {
                        insertTempTimetable();

                        boolean row = timetableDAO.delete(idTimetable());

                        if (row) {
                            JOptionPane.showMessageDialog(this, "TimeTable Data Deleted!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                            //khi delete thành công thì show data lên bảng
                            fillData();
                        } else {
                            JOptionPane.showMessageDialog(this, "Error Delete TimeTable!!!", "Warning", JOptionPane.ERROR_MESSAGE);
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

    private void jTableTimetableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTimetableMouseReleased
        // TODO add your handling code here:
        List<TimeTable> timeTables = timetableDAO.getAll();

        if (timeTables.size() > 0) {
            int index = jTableTimetable.getSelectedRow();
            TableModel model = jTableTimetable.getModel();

            if (jIFTimetableUpdate != null) {//tránh lỗi NULLpointerEx do chưa khởi tạo IFAddStudentForm mà đã click vào bảng
                //jcombobox class name
                for (int i = 0; i < JIFTimetableUpdate.jComboBoxClass.getItemCount(); i++) {
                    if (JIFTimetableUpdate.jComboBoxClass.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 1).toString())) {
                        JIFTimetableUpdate.jComboBoxClass.setSelectedIndex(i);
                    }
                }

                //jcombobox subject name
                for (int i = 0; i < JIFTimetableUpdate.jComboBoxSubject.getItemCount(); i++) {
                    if (JIFTimetableUpdate.jComboBoxSubject.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 2).toString())) {
                        JIFTimetableUpdate.jComboBoxSubject.setSelectedIndex(i);
                    }
                }

                //jcombobox teacher name
                for (int i = 0; i < JIFTimetableUpdate.jComboBoxTeacher.getItemCount(); i++) {
                    if (JIFTimetableUpdate.jComboBoxTeacher.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 7).toString())) {
                        JIFTimetableUpdate.jComboBoxTeacher.setSelectedIndex(i);
                    }
                }

                //jcombobox room name
                for (int i = 0; i < JIFTimetableUpdate.jComboBoxRoom.getItemCount(); i++) {
                    if (JIFTimetableUpdate.jComboBoxRoom.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 6).toString())) {
                        JIFTimetableUpdate.jComboBoxRoom.setSelectedIndex(i);
                    }
                }

                //start Time
                JIFTimetableUpdate.timePickerStart.setText(model.getValueAt(index, 3).toString());
                //end Time
                JIFTimetableUpdate.timePickerEnd.setText(model.getValueAt(index, 4).toString());

                //days
                JIFTimetableUpdate.jCheckBoxMo.setSelected(false);//bỏ tích tất cả checkbox trước khi user click row mới 
                JIFTimetableUpdate.jCheckBoxTu.setSelected(false);
                JIFTimetableUpdate.jCheckBoxWed.setSelected(false);
                JIFTimetableUpdate.jCheckBoxThu.setSelected(false);
                JIFTimetableUpdate.jCheckBoxFr.setSelected(false);
                JIFTimetableUpdate.jCheckBoxSat.setSelected(false);
                String[] days = model.getValueAt(index, 5).toString().split("-"); //bỏ dấu gạch ngang trong chuỗi rồi chuyển thàng mảng
                for (String day : days) {
                    if (JIFTimetableUpdate.jCheckBoxMo.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxMo.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxTu.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxTu.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxWed.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxWed.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxThu.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxThu.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxFr.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxFr.setSelected(true);
                    } else if (JIFTimetableUpdate.jCheckBoxSat.getText().equals(day)) {
                        JIFTimetableUpdate.jCheckBoxSat.setSelected(true);
                    }
                }

                JIFTimetableUpdate.jTextAreaDesc.setText(model.getValueAt(index, 8).toString());
            }
        }
    }//GEN-LAST:event_jTableTimetableMouseReleased

    private void jTableTimetableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableTimetableKeyReleased
        // TODO add your handling code here:
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
            List<TimeTable> timeTables = timetableDAO.getAll();

            if (timeTables.size() > 0) {
                int index = jTableTimetable.getSelectedRow();
                TableModel model = jTableTimetable.getModel();

                if (jIFTimetableUpdate != null) {//tránh lỗi NULLpointerEx do chưa khởi tạo IFAddStudentForm mà đã click vào bảng
                    //jcombobox class name
                    for (int i = 0; i < JIFTimetableUpdate.jComboBoxClass.getItemCount(); i++) {
                        if (JIFTimetableUpdate.jComboBoxClass.getItemAt(i).toString().
                                equalsIgnoreCase(model.getValueAt(index, 1).toString())) {
                            JIFTimetableUpdate.jComboBoxClass.setSelectedIndex(i);
                        }
                    }

                    //jcombobox subject name
                    for (int i = 0; i < JIFTimetableUpdate.jComboBoxSubject.getItemCount(); i++) {
                        if (JIFTimetableUpdate.jComboBoxSubject.getItemAt(i).toString().
                                equalsIgnoreCase(model.getValueAt(index, 2).toString())) {
                            JIFTimetableUpdate.jComboBoxSubject.setSelectedIndex(i);
                        }
                    }

                    //jcombobox teacher name
                    for (int i = 0; i < JIFTimetableUpdate.jComboBoxTeacher.getItemCount(); i++) {
                        if (JIFTimetableUpdate.jComboBoxTeacher.getItemAt(i).toString().
                                equalsIgnoreCase(model.getValueAt(index, 7).toString())) {
                            JIFTimetableUpdate.jComboBoxTeacher.setSelectedIndex(i);
                        }
                    }

                    //jcombobox room name
                    for (int i = 0; i < JIFTimetableUpdate.jComboBoxRoom.getItemCount(); i++) {
                        if (JIFTimetableUpdate.jComboBoxRoom.getItemAt(i).toString().
                                equalsIgnoreCase(model.getValueAt(index, 6).toString())) {
                            JIFTimetableUpdate.jComboBoxRoom.setSelectedIndex(i);
                        }
                    }

                    //start Time
                    JIFTimetableUpdate.timePickerStart.setText(model.getValueAt(index, 3).toString());
                    //end Time
                    JIFTimetableUpdate.timePickerEnd.setText(model.getValueAt(index, 4).toString());

                    //days
                    JIFTimetableUpdate.jCheckBoxMo.setSelected(false);//bỏ tích tất cả checkbox trước khi user click row mới 
                    JIFTimetableUpdate.jCheckBoxTu.setSelected(false);
                    JIFTimetableUpdate.jCheckBoxWed.setSelected(false);
                    JIFTimetableUpdate.jCheckBoxThu.setSelected(false);
                    JIFTimetableUpdate.jCheckBoxFr.setSelected(false);
                    JIFTimetableUpdate.jCheckBoxSat.setSelected(false);
                    String[] days = model.getValueAt(index, 5).toString().split("-"); //bỏ dấu gạch ngang trong chuỗi rồi chuyển thàng mảng
                    for (String day : days) {
                        if (JIFTimetableUpdate.jCheckBoxMo.getText().equals(day)) {
                            JIFTimetableUpdate.jCheckBoxMo.setSelected(true);
                        } else if (JIFTimetableUpdate.jCheckBoxTu.getText().equals(day)) {
                            JIFTimetableUpdate.jCheckBoxTu.setSelected(true);
                        } else if (JIFTimetableUpdate.jCheckBoxWed.getText().equals(day)) {
                            JIFTimetableUpdate.jCheckBoxWed.setSelected(true);
                        } else if (JIFTimetableUpdate.jCheckBoxThu.getText().equals(day)) {
                            JIFTimetableUpdate.jCheckBoxThu.setSelected(true);
                        } else if (JIFTimetableUpdate.jCheckBoxFr.getText().equals(day)) {
                            JIFTimetableUpdate.jCheckBoxFr.setSelected(true);
                        } else if (JIFTimetableUpdate.jCheckBoxSat.getText().equals(day)) {
                            JIFTimetableUpdate.jCheckBoxSat.setSelected(true);
                        }
                    }

                    JIFTimetableUpdate.jTextAreaDesc.setText(model.getValueAt(index, 8).toString());
                }
            }
        }
    }//GEN-LAST:event_jTableTimetableKeyReleased

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        List<TimeTable> timeTables = timetableDAO.search(jTextFieldSearch.getText());

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

        for (TimeTable timeTable : timeTables) {
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

        jTableTimetable.setModel(dtm);

        jTableTimetable.removeColumn(jTableTimetable.getColumnModel().getColumn(0));
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void jLabelRecycleMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecycleMouseMoved
        // TODO add your handling code here:
        jLabelRecycle.setBackground(new Color(255, 255, 255));
        jLabelRecycle.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_jLabelRecycleMouseMoved

    private void jLabelRecycleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecycleMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);

        MainFrame.getjPRecycle().setVisible(true);

        List<TimeTable> timeTables = timetableDAO.getAllTemp();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("Class");
        dtm.addColumn("Subject");
        dtm.addColumn("Start time");
        dtm.addColumn("End time");
        dtm.addColumn("Days");
        dtm.addColumn("Room");
        dtm.addColumn("Teacher");
        dtm.addColumn("Desc");

        for (TimeTable timeTable : timeTables) {
            Vector<Object> vector = new Vector();
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

        JPRecycle.jTableRecycle.setModel(dtm);

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        dcbm.addElement("Teacher");
        dcbm.addElement("Class");

        JPRecycle.jComboBoxSearch.setModel(dcbm);
    }//GEN-LAST:event_jLabelRecycleMouseClicked

    private void jLabelRecycleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecycleMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelRecycleMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> jComboBoxSearch;
    public static javax.swing.JLabel jLabelAdd;
    public static javax.swing.JLabel jLabelDelete;
    private javax.swing.JLabel jLabelRecycle;
    private javax.swing.JLabel jLabelRefresh;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JLabel jLabelStudent;
    public static javax.swing.JLabel jLabelUpdate;
    private javax.swing.JPanel jPanelMid;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableTimetable;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
