/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sm.jPanelManager.student.StudentDAO;
import sm.jPanelManager.recycle.JPRecycle;
import sm.jPanelManager.score.Score;
import sm.jPanelManager.score.ScoreDAO;
import sm.jPanelManager.student.Student;
import sm.jPanelManager.subject.SubjectDAO;
import sm.mainFrame.MainFrame;

/**
 *
 * @author Ngoc Tuan
 */
public class JPClassManager extends javax.swing.JPanel {

    private JIFClassAdd jIFClassAdd;
    private JIFClassUpdate jIFClassUpdate;

    private ClassesDAO classesDAO = new ClassesDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private ScoreDAO scoreDAO = new ScoreDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();

    /**
     * Creates new form JPanelStudent
     */
    public JPClassManager() {
        initComponents();

        setVisible(false);
        setBounds(0, 0, MainFrame.jPanelContent.getSize().width + 105, MainFrame.jPanelContent.getSize().height);

        jTableClass.setRowHeight(30);
        jTableClass.setShowGrid(true);
        jTableClass.setGridColor(new Color(102, 102, 102));

        fillData();
    }

    private void setData() {
        List<Classes> listClass = classesDAO.getAll();

        if (listClass.size() > 0) {
            int index = jTableClass.getSelectedRow();
            TableModel model = jTableClass.getModel();
            if (jIFClassUpdate != null) {//tránh lỗi NULLpointerEx do chưa khởi tạo IFAddStudentForm mà đã click vào bảng
                JIFClassUpdate.jTextFieldClassN.setText(model.getValueAt(index, 1).toString());
                JIFClassUpdate.jTextFieldNOS.setText(model.getValueAt(index, 2).toString());
            }
        }
    }

    //fill data ra bảng
    private void fillData() {
        List<Classes> listClasses = classesDAO.getAll();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("ID");
        dtm.addColumn("Class Name");
        dtm.addColumn("Number of students");

        for (Classes classes : listClasses) {
            Vector<Object> vector = new Vector();
            vector.add(classes.getIdClass());
            vector.add(classes.getClassName());
            vector.add(classes.getNumberOfStudents());

            dtm.addRow(vector);
        }

        jTableClass.setModel(dtm);
    }

    private void insertTempClass() {
        int index = jTableClass.getSelectedRow();
        TableModel model = jTableClass.getModel();

        String className = model.getValueAt(index, 1).toString();
        int NOStudents = Integer.valueOf(model.getValueAt(index, 2).toString());

        Classes classes = new Classes(
                className,
                NOStudents
        );

        boolean row = classesDAO.saveTempClass(classes);

        if (row) {
            System.out.println("insert class temp success");
        } else {
            System.out.println("failed");
        }
    }

    private void insertTempScore() {
        int index = jTableClass.getSelectedRow();
        TableModel model = jTableClass.getModel();

        String className = String.valueOf(model.getValueAt(index, 1));

        List<Score> scores = scoreDAO.getAll();

        for (Score score : scores) {
            if (score.getClassName().equalsIgnoreCase(className)) {
                Score score1 = new Score(
                        score.getRollNumber(),
                        score.getFirstName(),
                        score.getLastName(),
                        score.getSubjectName(),
                        score.getClassName(),
                        score.getScore(),
                        score.getTypeOfScore(),
                        score.getNumberOfExams(),
                        score.getDesc()
                );
                boolean row = scoreDAO.saveScoreTemp(score1);
                if (row) {
                    System.out.println("insert score temp success");
                } else {
                    System.out.println("failed");
                }
            }
        }
    }

    private void insertTempStudent() {
        int index = jTableClass.getSelectedRow();
        TableModel model = jTableClass.getModel();

        String className = String.valueOf(model.getValueAt(index, 1));

        List<Student> students = studentDAO.getAll();

        for (Student student : students) {
            if (student.getClassName().equalsIgnoreCase(className)) {
                Student student1 = new Student(
                        student.getClassName(),
                        student.getRollNumber(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getGender(),
                        student.getBirthDate(),
                        student.getPhone(),
                        student.getEmail(),
                        student.getAddress()
                );
                boolean row = studentDAO.saveStudentTemp(student1);
                if (row) {
                    System.out.println("insert student temp success");
                } else {
                    System.out.println("failed");
                }
            }
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
        jTableClass = new javax.swing.JTable();
        jPanelTop = new javax.swing.JPanel();
        jLabelClass = new javax.swing.JLabel();

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelUpdateMouseEntered(evt);
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
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Class name", "Number of students" }));
        jComboBoxSearch.setOpaque(false);
        jComboBoxSearch.setPreferredSize(new java.awt.Dimension(200, 26));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
        jPanelMidLayout.setVerticalGroup(
            jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMidLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSearch)
                    .addComponent(jComboBoxSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanelMidLayout.createSequentialGroup()
                .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelRecycle, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTableClass.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTableClass.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableClass.setFillsViewportHeight(true);
        jTableClass.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTableClassMouseMoved(evt);
            }
        });
        jTableClass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableClassMouseReleased(evt);
            }
        });
        jTableClass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableClassKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableClass);

        jPanelTop.setBackground(new java.awt.Color(153, 153, 153));
        jPanelTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelTopMouseMoved(evt);
            }
        });

        jLabelClass.setFont(new java.awt.Font("Century Gothic", 1, 22)); // NOI18N
        jLabelClass.setForeground(new java.awt.Color(255, 255, 255));
        jLabelClass.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Classes</div></html> ");

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelClass)
                .addContainerGap())
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelClass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
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

        jLabelRecycle.setBackground(new Color(204, 204, 204));
        jLabelRecycle.setForeground(new Color(0, 0, 0));

        jLabelRefresh.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_jPanelMidMouseMoved

    private void jTableClassMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClassMouseMoved
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
    }//GEN-LAST:event_jTableClassMouseMoved

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
            if (jIFClassAdd == null) {
                jIFClassAdd = new JIFClassAdd();
                jTableClass.add(jIFClassAdd);
                jIFClassAdd.setBounds(0, 0, jTableClass.getSize().width / 2 + 100, 400);
                jIFClassAdd.setVisible(true);

                if (jIFClassUpdate != null) {
                    jIFClassUpdate.setVisible(false);
                }
            }
            jIFClassAdd.setVisible(true);

            if (jIFClassUpdate != null) {
                jIFClassUpdate.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableClass.getSize();
            Dimension sizejInternalFrameAdd = jIFClassAdd.getSize();
            jIFClassAdd.setLocation((sizejTableStudent.width - sizejInternalFrameAdd.width) / 2, (sizejTableStudent.height - sizejInternalFrameAdd.height) / 2);
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
            if (jIFClassUpdate == null) {
                jIFClassUpdate = new JIFClassUpdate();
                jTableClass.add(jIFClassUpdate);
                jIFClassUpdate.setBounds(0, 0, jTableClass.getSize().width / 2 + 100, 400);
                jIFClassUpdate.setVisible(true);

                if (jIFClassAdd != null) {
                    jIFClassAdd.setVisible(false);
                }

                try {
                    setData();
                } catch (Exception e) {
                }
            }
            jIFClassUpdate.setVisible(true);

            if (jIFClassAdd != null) {
                jIFClassAdd.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableClass.getSize();
            Dimension sizejInternalFrameU = jIFClassUpdate.getSize();
            jIFClassUpdate.setLocation((sizejTableStudent.width - sizejInternalFrameU.width) / 2, (sizejTableStudent.height - sizejInternalFrameU.height) / 2);
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
                int index = jTableClass.getSelectedRow();
                TableModel model = jTableClass.getModel();

                String idClass = model.getValueAt(index, 0).toString();
                String className = model.getValueAt(index, 1).toString();

                int yes = JOptionPane.showConfirmDialog(this, "All students and scores of "
                        + className + " class will be also deleted."
                        + "Do you want to delete?", "Warning", JOptionPane.YES_NO_OPTION);

                if (yes == JOptionPane.YES_OPTION) {
                    insertTempClass();
                    insertTempScore();
                    insertTempStudent();

                    scoreDAO.deleteByClass(idClass);
                    studentDAO.deleteByIdClass(idClass);
                    boolean row = classesDAO.delete(idClass);

                    if (row) {
                        JOptionPane.showMessageDialog(this, "Delete Successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                        //count số class
                        int totalClasses = classesDAO.count();
                        MainFrame.jLabelCountClass.setText("" + totalClasses);

                        //khi delete thành công thì show data lên bảng
                        fillData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error Delete !!!", "Notification", JOptionPane.ERROR_MESSAGE);
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
        // TODO add your handling code here  
    }//GEN-LAST:event_jComboBoxSearchFocusLost

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        List<Classes> listClass = classesDAO.search(jTextFieldSearch.getText());

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Class Name");
        dtm.addColumn("Number of students");

        for (Classes classes : listClass) {
            Vector<Object> vector = new Vector();
            vector.add(classes.getIdClass());
            vector.add(classes.getClassName());
            vector.add(classes.getNumberOfStudents());

            dtm.addRow(vector);
        }

        jTableClass.setModel(dtm);
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void jTableClassMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClassMouseReleased
        // TODO add your handling code here:
        List<Classes> listClass = classesDAO.getAll();

        if (listClass.size() > 0) {
            int index = jTableClass.getSelectedRow();
            TableModel model = jTableClass.getModel();
            if (jIFClassUpdate != null) {//tránh lỗi NULLpointerEx do chưa khởi tạo IFAddStudentForm mà đã click vào bảng
                JIFClassUpdate.jTextFieldClassN.setText(model.getValueAt(index, 1).toString());
                JIFClassUpdate.jTextFieldNOS.setText(model.getValueAt(index, 2).toString());
            }
        }
    }//GEN-LAST:event_jTableClassMouseReleased

    private void jTableClassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableClassKeyReleased
        // TODO add your handling code here:
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
            List<Classes> listClass = classesDAO.getAll();

            if (listClass.size() > 0) {
                int index = jTableClass.getSelectedRow();
                TableModel model = jTableClass.getModel();
                if (jIFClassUpdate != null) {//tránh lỗi NULLpointerEx do chưa khởi tạo IFAddStudentForm mà đã click vào bảng
                    JIFClassUpdate.jTextFieldClassN.setText(model.getValueAt(index, 1).toString());
                    JIFClassUpdate.jTextFieldNOS.setText(model.getValueAt(index, 2).toString());
                }
            }
        }
    }//GEN-LAST:event_jTableClassKeyReleased

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

    private void jLabelUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelUpdateMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelUpdateMouseEntered

    private void jLabelRecycleMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecycleMouseMoved
        // TODO add your handling code here:
        jLabelRecycle.setBackground(new Color(255, 255, 255));
        jLabelRecycle.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_jLabelRecycleMouseMoved

    private void jLabelRecycleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecycleMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);

        MainFrame.getjPRecycle().setVisible(true);

        List<Classes> listClasses = classesDAO.getAllTempClass();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("ID");
        dtm.addColumn("Class Name");
        dtm.addColumn("Number of students");

        for (Classes classes : listClasses) {
            Vector<Object> vector = new Vector();
            vector.add(classes.getIdClass());
            vector.add(classes.getClassName());
            vector.add(classes.getNumberOfStudents());

            dtm.addRow(vector);
        }

        JPRecycle.jTableRecycle.setModel(dtm);
        JPRecycle.jTableRecycle.removeColumn(JPRecycle.jTableRecycle.getColumnModel().getColumn(0));

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        dcbm.addElement("Class name");
        dcbm.addElement("Number of students");

        JPRecycle.jComboBoxSearch.setModel(dcbm);
    }//GEN-LAST:event_jLabelRecycleMouseClicked

    private void jLabelRecycleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecycleMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelRecycleMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> jComboBoxSearch;
    public static javax.swing.JLabel jLabelAdd;
    private javax.swing.JLabel jLabelClass;
    public static javax.swing.JLabel jLabelDelete;
    private javax.swing.JLabel jLabelRecycle;
    private javax.swing.JLabel jLabelRefresh;
    private javax.swing.JLabel jLabelSearch;
    public static javax.swing.JLabel jLabelUpdate;
    private javax.swing.JPanel jPanelMid;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableClass;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
