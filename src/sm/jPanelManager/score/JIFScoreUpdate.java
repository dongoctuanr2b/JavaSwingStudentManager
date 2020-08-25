/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.score;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sm.jPanelManager.classes.Classes;
import sm.jPanelManager.classes.ClassesDAO;
import static sm.jPanelManager.score.JPScoreManager.jTableScore;
import sm.jPanelManager.student.Student;
import sm.jPanelManager.student.StudentDAO;
import sm.jPanelManager.subject.Subject;
import sm.jPanelManager.subject.SubjectDAO;

/**
 *
 * @author Ngoc Tuan
 */
public class JIFScoreUpdate extends javax.swing.JInternalFrame {

    private ScoreDAO scoreDAO = new ScoreDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private ClassesDAO classesDAO = new ClassesDAO();

    /**
     * Creates new form JInternalFrameUpdate
     */
    public JIFScoreUpdate() {
        initComponents();

        loadClass();
        loadRollNumber();
        loadSubject();

        setName();
    }

    //set jComboBoxRollNumber
    private void setName() {
        List<Student> students = studentDAO.getAll();

        for (Student student : students) {
            if (jComboBoxRollN.getSelectedItem().toString().equalsIgnoreCase(student.getRollNumber())) {
                jTextFieldFName.setText(student.getFirstName());
                jTextFieldLName.setText(student.getLastName());
                jComboBoxClass.getModel().setSelectedItem(student.getClassName());
            }
        }
    }

    //fill data ra bảng
    private void fillData() {
        List<Score> scores = scoreDAO.getAll();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("idScore");
        dtm.addColumn("RollNumber");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Subject");
        dtm.addColumn("Class");
        dtm.addColumn("Score");
        dtm.addColumn("Type of score");
        dtm.addColumn("Number of exams");
        dtm.addColumn("Description");

        for (Score score : scores) {
            Vector<Object> vector = new Vector();
            vector.add(score.getIdScore());
            vector.add(score.getRollNumber());
            vector.add(score.getFirstName());
            vector.add(score.getLastName());
            vector.add(score.getSubjectName());
            vector.add(score.getClassName());
            vector.add(score.getScore());
            vector.add(score.getTypeOfScore());
            vector.add(score.getNumberOfExams());
            vector.add(score.getDesc());

            dtm.addRow(vector);
        }

        jTableScore.setModel(dtm);

        jTableScore.removeColumn(jTableScore.getColumnModel().getColumn(0));
    }

    //set jComboBoxRollNumber
    private void loadRollNumber() {
        List<Student> students = studentDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Student student : students) {
            dcbm.addElement(student.getRollNumber());
        }

        jComboBoxRollN.setModel(dcbm);
    }

    //set jComboBoxSubject
    private void loadSubject() {
        List<Subject> subjects = subjectDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Subject subject : subjects) {
            dcbm.addElement(subject.getSubjectName());
        }

        jComboBoxSubjectN.setModel(dcbm);
    }

    //set jComboBoxClass
    private void loadClass() {
        List<Classes> listClass = classesDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Classes classes : listClass) {
            dcbm.addElement(classes.getClassName());
        }

        jComboBoxClass.setModel(dcbm);
    }

    //validate form update course
    private boolean validateScoreUpdate() {
        boolean flag = true;

        //check course name phải là duy nhất ko được trùng, chuyển flag = false
        List<Score> listScore = scoreDAO.getAll();

        int index = JPScoreManager.jTableScore.getSelectedRow();
        TableModel model = JPScoreManager.jTableScore.getModel();

        if (flag) {
            for (Score score : listScore) {
                String getCBR = jComboBoxRollN.getSelectedItem().toString(); // get rollNumber ở comboBox
                String getRowClickR = model.getValueAt(index, 1).toString();
                String getR = score.getRollNumber();

                String getCBS = jComboBoxSubjectN.getSelectedItem().toString();// get courseName ở comboBox
                String getRowClickS = model.getValueAt(index, 4).toString();
                String getS = score.getSubjectName();

                boolean checkRN = getCBR.equalsIgnoreCase(getR);
                boolean checkCBR = getRowClickR.equalsIgnoreCase(getCBR);
                boolean checkS = getCBS.equalsIgnoreCase(getS);
                boolean checkCBS = getRowClickS.equalsIgnoreCase(getCBS);

                if (((checkRN && !checkCBR) && (checkS && !checkCBS))
                        || ((checkRN && !checkCBR) && (checkS && checkCBS))
                        || ((checkRN && checkCBR) && (checkS && !checkCBS))) {
                    JOptionPane.showMessageDialog(rootPane, "Roll Nummber " + getR
                            + " (learning " + getS.toUpperCase() + ") already exists the score!!!", "Warning", JOptionPane.OK_OPTION);

                    flag = false;
                }
            }
        }

        if (flag) {
            for (Score score : listScore) {
                String getCBR = jComboBoxRollN.getSelectedItem().toString(); // get rollNumber ở comboBox
                String getCBS = jComboBoxSubjectN.getSelectedItem().toString();// get courseName ở comboBox
                String getR = score.getRollNumber(); // get rollNumber từ listScore
                String getS = score.getSubjectName();// get courseName từ listScore

                if ((getCBR.equalsIgnoreCase(getR)) && (getCBS.equalsIgnoreCase(getS))) {
                    int yes = JOptionPane.showConfirmDialog(rootPane, "Roll Nummber " + getR
                            + " (learning " + getS.toUpperCase() + ") already exists the score!!! "
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

        //nếu course name and rollNumber không bị trùng thì flag vẫn = true rồi check tiếp
        if (flag) {
            if (jTextFieldFName.getText().isEmpty() || jTextFieldLName.getText().isEmpty()
                    || jTextFieldNOExam.getText().isEmpty() || jTextAreaDesc.getText().isEmpty()
                    || jTextFieldScore.getText().isEmpty()) {

                JOptionPane.showMessageDialog(rootPane, "One Or More Empty Field!!!", "Warning", JOptionPane.ERROR_MESSAGE);

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

    //get idSubject từ bảng
    private int idSubject() {
        int idSubject = 0;

        List<Subject> subjects = subjectDAO.getAll();
        for (Subject subject : subjects) {
            if (jComboBoxSubjectN.getSelectedItem().toString().equalsIgnoreCase(subject.getSubjectName())) {
                idSubject = subject.getIdSubject();
            }
        }

        return idSubject;
    }

    //get idStudent từ bảng
    private int idStudent() {
        int idStudent = 0;

        List<Student> students = studentDAO.getAll();
        for (Student student : students) {
            if (jComboBoxRollN.getSelectedItem().toString().equalsIgnoreCase(student.getRollNumber())) {
                idStudent = student.getIdStudent();
            }
        }

        return idStudent;
    }

    //get idStudent từ bảng
    private int idScore() {
        int index = JPScoreManager.jTableScore.getSelectedRow();
        TableModel model = JPScoreManager.jTableScore.getModel();

        return Integer.valueOf(model.getValueAt(index, 0).toString());
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
        jButtonU = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaDesc = new javax.swing.JTextArea();
        jLabelDesc = new javax.swing.JLabel();
        jTextFieldNOExam = new javax.swing.JTextField();
        jLabelNOExam = new javax.swing.JLabel();
        jLabelTOScore = new javax.swing.JLabel();
        jLabelFName = new javax.swing.JLabel();
        jTextFieldFName = new javax.swing.JTextField();
        jLabelRollN = new javax.swing.JLabel();
        jLabelScore = new javax.swing.JLabel();
        jLabelLName = new javax.swing.JLabel();
        jTextFieldLName = new javax.swing.JTextField();
        jComboBoxClass = new javax.swing.JComboBox<>();
        jLabelClass = new javax.swing.JLabel();
        jLabelScoreU = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JLabel();
        jTextFieldScore = new javax.swing.JTextField();
        jLabelSubjectN = new javax.swing.JLabel();
        jComboBoxSubjectN = new javax.swing.JComboBox<>();
        jComboBoxRollN = new javax.swing.JComboBox<>();
        jComboBoxTOScore = new javax.swing.JComboBox<>();

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

        jTextAreaDesc.setColumns(20);
        jTextAreaDesc.setRows(5);
        jScrollPane3.setViewportView(jTextAreaDesc);

        jLabelDesc.setBackground(new java.awt.Color(255, 255, 255));
        jLabelDesc.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelDesc.setText("Description:");

        jTextFieldNOExam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNOExamActionPerformed(evt);
            }
        });

        jLabelNOExam.setBackground(new java.awt.Color(255, 255, 255));
        jLabelNOExam.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelNOExam.setText("Number of exams:");

        jLabelTOScore.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTOScore.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelTOScore.setText("Type of score:");

        jLabelFName.setBackground(new java.awt.Color(255, 255, 255));
        jLabelFName.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelFName.setText("First name:");

        jTextFieldFName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFNameActionPerformed(evt);
            }
        });

        jLabelRollN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelRollN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelRollN.setText("Roll number:");

        jLabelScore.setBackground(new java.awt.Color(255, 255, 255));
        jLabelScore.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelScore.setText("Score:");

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

        jLabelScoreU.setBackground(new java.awt.Color(255, 255, 255));
        jLabelScoreU.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabelScoreU.setText("Update Score");

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

        jTextFieldScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldScoreActionPerformed(evt);
            }
        });
        jTextFieldScore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldScoreKeyTyped(evt);
            }
        });

        jLabelSubjectN.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSubjectN.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabelSubjectN.setText("Subject name:");

        jComboBoxSubjectN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxRollN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxRollN.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxRollNItemStateChanged(evt);
            }
        });

        jComboBoxTOScore.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theory", "Practical" }));

        javax.swing.GroupLayout jPanelAddLayout = new javax.swing.GroupLayout(jPanelAdd);
        jPanelAdd.setLayout(jPanelAddLayout);
        jPanelAddLayout.setHorizontalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelScore, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelFName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelSubjectN, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelDesc, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelRollN, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanelAddLayout.createSequentialGroup()
                            .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelAddLayout.createSequentialGroup()
                            .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldScore, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextFieldFName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                .addComponent(jComboBoxSubjectN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoxRollN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(129, 129, 129)
                            .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelTOScore, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabelLName, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabelClass, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabelNOExam, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldNOExam, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                .addComponent(jComboBoxClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldLName, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                .addComponent(jComboBoxTOScore, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(47, 47, 47))
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(jLabelScoreU)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelAddLayout.setVerticalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addComponent(jLabelScoreU)
                .addGap(29, 29, 29)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelClass)
                            .addComponent(jComboBoxClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelLName)
                            .addComponent(jTextFieldLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTOScore)
                            .addComponent(jComboBoxTOScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNOExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNOExam)))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRollN)
                            .addComponent(jComboBoxRollN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFName)
                            .addComponent(jTextFieldFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelScore))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSubjectN)
                            .addComponent(jComboBoxSubjectN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDesc)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonU, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        //Check form add course
        try {
            if (validateScoreUpdate()) {
                Score score = new Score(
                        idScore(),
                        idStudent(),
                        idSubject(),
                        idClass(),
                        Float.valueOf(jTextFieldScore.getText()),
                        jComboBoxTOScore.getSelectedItem().toString(),
                        Integer.valueOf(jTextFieldNOExam.getText()),
                        jTextAreaDesc.getText()
                );

                boolean row = scoreDAO.update(score);

                if (row) {
                    JOptionPane.showMessageDialog(rootPane, "Score Data Updated!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                    fillData();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error Update Score!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButtonUMouseClicked

    private void jButtonUMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonUMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUMouseReleased

    private void jTextFieldNOExamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNOExamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNOExamActionPerformed

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
        jComboBoxRollN.setSelectedIndex(0);
        jComboBoxClass.setSelectedIndex(0);
        jComboBoxSubjectN.setSelectedIndex(0);
        jComboBoxTOScore.setSelectedIndex(0);
        jTextAreaDesc.setText(null);
        jTextFieldFName.setText(null);
        jTextFieldLName.setText(null);
        jTextFieldNOExam.setText(null);
        jTextFieldScore.setText(null);
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

    private void jTextFieldScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldScoreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldScoreActionPerformed

    private void jTextFieldScoreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldScoreKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldScoreKeyTyped

    private void jComboBoxRollNItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxRollNItemStateChanged
        // TODO add your handling code here:
        setName();
    }//GEN-LAST:event_jComboBoxRollNItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel jButtonClear;
    public static javax.swing.JLabel jButtonU;
    public static javax.swing.JComboBox<String> jComboBoxClass;
    public static javax.swing.JComboBox<String> jComboBoxRollN;
    public static javax.swing.JComboBox<String> jComboBoxSubjectN;
    public static javax.swing.JComboBox<String> jComboBoxTOScore;
    private javax.swing.JLabel jLabelClass;
    private javax.swing.JLabel jLabelDesc;
    private javax.swing.JLabel jLabelFName;
    private javax.swing.JLabel jLabelLName;
    private javax.swing.JLabel jLabelNOExam;
    private javax.swing.JLabel jLabelRollN;
    private javax.swing.JLabel jLabelScore;
    private javax.swing.JLabel jLabelScoreU;
    private javax.swing.JLabel jLabelSubjectN;
    private javax.swing.JLabel jLabelTOScore;
    private javax.swing.JPanel jPanelAdd;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTextArea jTextAreaDesc;
    public static javax.swing.JTextField jTextFieldFName;
    public static javax.swing.JTextField jTextFieldLName;
    public static javax.swing.JTextField jTextFieldNOExam;
    public static javax.swing.JTextField jTextFieldScore;
    // End of variables declaration//GEN-END:variables
}
