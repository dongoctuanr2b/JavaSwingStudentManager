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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import sm.jPanelManager.classes.Classes;
import sm.jPanelManager.classes.ClassesDAO;
import static sm.jPanelManager.score.JPScoreManager.jTableScore;
import sm.jPanelManager.student.Student;
import sm.jPanelManager.student.StudentDAO;
import sm.jPanelManager.subject.Subject;
import sm.jPanelManager.subject.SubjectDAO;
import sm.mainFrame.MainFrame;

/**
 *
 * @author Ngoc Tuan
 */
public class JIFScoreAdd extends javax.swing.JInternalFrame {

    private ScoreDAO scoreDAO = new ScoreDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private ClassesDAO classesDAO = new ClassesDAO();

    /**
     * Creates new form NewJInternalFrame
     */
    public JIFScoreAdd() {
        initComponents();

        loadClass();
        loadRollNumber();
        loadSubject();

        setName();
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
            dcbm.addElement(student);
        }

        jComboBoxRollN.setModel(dcbm);
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

    //set jComboBoxSubject
    private void loadSubject() {
        List<Subject> subjects = subjectDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Subject subject : subjects) {
            dcbm.addElement(subject);
        }

        jComboBoxSubjectN.setModel(dcbm);
    }

    //set jComboBoxClass
    private void loadClass() {
        List<Classes> listClass = classesDAO.getAll();

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (Classes classes : listClass) {
            dcbm.addElement(classes);
        }

        jComboBoxClass.setModel(dcbm);
    }

    //validate form add score
    private boolean validateScoreAdd() {
        boolean flag = true;

        //check course name phải là duy nhất ko được trùng, chuyển flag = false
        List<Score> listScore = scoreDAO.getAll();
        for (Score score : listScore) {
            String getCBR = jComboBoxRollN.getSelectedItem().toString(); // get rollNumber ở comboBox
            String getCBS = jComboBoxSubjectN.getSelectedItem().toString();// get courseName ở comboBox
            String getR = score.getRollNumber(); // get rollNumber từ listScore
            String getS = score.getSubjectName();// get courseName từ listScore

            if ((getCBR.equalsIgnoreCase(getR)) && (getCBS.equalsIgnoreCase(getS))) {
                JOptionPane.showMessageDialog(rootPane, "Roll Nummber " + score.getRollNumber()
                        + " (learning " + score.getSubjectName().toUpperCase() + ") already exists the score!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                flag = false;
                break;
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

    private void createChartScore() {
        ScoreDAO scoreDAO = new ScoreDAO();
        List<Score> scores = scoreDAO.avgScoreByStudents();

        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();//Constructs a new dataset, initially empty.

        for (Score score : scores) {// get score và rollNumber từ listScore
            dataSet.setValue(score.getScore(), "Score", score.getRollNumber());
        }

        //Creates a new chart with the given title and plot.
        JFreeChart chart = ChartFactory.createBarChart("Statistics average score", "Roll number", "Score", dataSet, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);//Constructs a panel that displays the specified chart.

        MainFrame.jPanelStatisticsAvgScore.removeAll();
        MainFrame.jPanelStatisticsAvgScore.add(chartPanel);
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
        jLabelScoreAdd = new javax.swing.JLabel();
        jButtonClear = new javax.swing.JLabel();
        jTextFieldScore = new javax.swing.JTextField();
        jLabelSubjectN = new javax.swing.JLabel();
        jComboBoxRollN = new javax.swing.JComboBox<>();
        jComboBoxTOScore = new javax.swing.JComboBox<>();
        jComboBoxSubjectN = new javax.swing.JComboBox<>();

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

        jLabelScoreAdd.setBackground(new java.awt.Color(255, 255, 255));
        jLabelScoreAdd.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabelScoreAdd.setText("Add Score");

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

        jComboBoxRollN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxRollN.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxRollNItemStateChanged(evt);
            }
        });
        jComboBoxRollN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxRollNMouseClicked(evt);
            }
        });

        jComboBoxTOScore.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theory", "Practical" }));

        jComboBoxSubjectN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanelAddLayout = new javax.swing.GroupLayout(jPanelAdd);
        jPanelAdd.setLayout(jPanelAddLayout);
        jPanelAddLayout.setHorizontalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelRollN)
                            .addComponent(jLabelFName)
                            .addComponent(jLabelScore))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldScore, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(jTextFieldFName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(jComboBoxRollN, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jLabelSubjectN)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxSubjectN, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelTOScore)
                            .addComponent(jLabelLName)
                            .addComponent(jLabelClass)
                            .addComponent(jLabelNOExam))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldNOExam, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(jComboBoxClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldLName, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(jComboBoxTOScore, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelScoreAdd)
                .addGap(297, 297, 297))
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabelDesc)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelAddLayout.setVerticalGroup(
            jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddLayout.createSequentialGroup()
                .addComponent(jLabelScoreAdd)
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
                .addGroup(jPanelAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabelDesc))
                    .addGroup(jPanelAddLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
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
        //Check form add course
        if (validateScoreAdd()) {
            Score score = new Score(
                    ((Student) jComboBoxRollN.getSelectedItem()).getIdStudent(),
                    ((Subject) jComboBoxSubjectN.getSelectedItem()).getIdSubject(),
                    ((Classes) jComboBoxClass.getSelectedItem()).getIdClass(),
                    Float.valueOf(jTextFieldScore.getText()),
                    jComboBoxTOScore.getSelectedItem().toString(),
                    Integer.valueOf(jTextFieldNOExam.getText()),
                    jTextAreaDesc.getText()
            );

            boolean row = scoreDAO.save(score);

            if (row) {
                JOptionPane.showMessageDialog(rootPane, "New Score Added!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                createChartScore();

                fillData();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error Add Score!!!", "Warning", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonSaveMouseClicked

    private void jButtonSaveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSaveMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSaveMouseReleased

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
        jButtonSave.setBackground(new Color(53, 173, 164));
        jButtonSave.setForeground(new Color(255, 255, 255));

        jButtonClear.setBackground(new Color(255, 51, 51));
        jButtonClear.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelAddMouseMoved

    private void jTextFieldScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldScoreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldScoreActionPerformed

    private void jComboBoxRollNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxRollNMouseClicked
        // TODO add your handling code here:  
    }//GEN-LAST:event_jComboBoxRollNMouseClicked

    private void jComboBoxRollNItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxRollNItemStateChanged
        // TODO add your handling code here:
        setName();
    }//GEN-LAST:event_jComboBoxRollNItemStateChanged

    private void jTextFieldScoreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldScoreKeyTyped
        // TODO add your handling code here:
        if (!Character.isDigit(evt.getKeyChar()) && !(evt.getKeyChar() == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldScoreKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel jButtonClear;
    public static javax.swing.JLabel jButtonSave;
    public static javax.swing.JComboBox<String> jComboBoxClass;
    public static javax.swing.JComboBox<String> jComboBoxRollN;
    public static javax.swing.JComboBox<String> jComboBoxSubjectN;
    private javax.swing.JComboBox<String> jComboBoxTOScore;
    private javax.swing.JLabel jLabelClass;
    private javax.swing.JLabel jLabelDesc;
    private javax.swing.JLabel jLabelFName;
    private javax.swing.JLabel jLabelLName;
    private javax.swing.JLabel jLabelNOExam;
    private javax.swing.JLabel jLabelRollN;
    private javax.swing.JLabel jLabelScore;
    private javax.swing.JLabel jLabelScoreAdd;
    private javax.swing.JLabel jLabelSubjectN;
    private javax.swing.JLabel jLabelTOScore;
    private javax.swing.JPanel jPanelAdd;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaDesc;
    private javax.swing.JTextField jTextFieldFName;
    private javax.swing.JTextField jTextFieldLName;
    private javax.swing.JTextField jTextFieldNOExam;
    private javax.swing.JTextField jTextFieldScore;
    // End of variables declaration//GEN-END:variables
}
