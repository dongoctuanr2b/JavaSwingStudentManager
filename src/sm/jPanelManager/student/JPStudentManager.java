/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.student;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import sm.jPanelManager.classes.ClassesDAO;
import sm.jPanelManager.recycle.JPRecycle;
import sm.jPanelManager.score.Score;
import sm.jPanelManager.score.ScoreDAO;
import sm.mainFrame.MainFrame;

/**
 *
 * @author Ngoc Tuan
 */
public class JPStudentManager extends javax.swing.JPanel {

    private JIFStudentAdd jIFStudentAdd;
    private JIFStudentUpdate jIFStudentUpdate;
    private StudentDAO studentDAO = new StudentDAO();
    private ClassesDAO classesDAO = new ClassesDAO();
    private ScoreDAO scoreDAO = new ScoreDAO();

    /**
     * Creates new form JPanelStudent
     */
    public JPStudentManager() {
        initComponents();

        setVisible(false);
        setBounds(0, 0, MainFrame.jPanelContent.getSize().width + 105, MainFrame.jPanelContent.getSize().height);

        jTableStudent.setRowHeight(30);
        jTableStudent.setShowGrid(true);
        jTableStudent.setGridColor(new Color(102, 102, 102));

        fillData();
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

        jTableStudent.setModel(dtm);
    }

    //chuyển string thành Date
    private Date birthDate() throws ParseException {
        int index = jTableStudent.getSelectedRow();
        TableModel model = jTableStudent.getModel();
        Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(index, 5).toString());

        return birthDate;
    }

    private void setData() {
        List<Student> students = studentDAO.getAll();

        if (students.size() > 0) {
            int index = jTableStudent.getSelectedRow();
            TableModel model = jTableStudent.getModel();
            if (jIFStudentUpdate != null) {//tránh lỗi NULLpointerEx do chưa khởi tạo IFAddStudentForm mà đã click vào bảng
                JIFStudentUpdate.jTextFieldRollN.setText(model.getValueAt(index, 0).toString());
                JIFStudentUpdate.jTextFieldFName.setText(model.getValueAt(index, 2).toString());
                JIFStudentUpdate.jTextFieldLName.setText(model.getValueAt(index, 3).toString());

                //jcombobox class name
                for (int i = 0; i < JIFStudentUpdate.jComboBoxClass.getItemCount(); i++) {
                    if (JIFStudentUpdate.jComboBoxClass.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 1).toString())) {
                        JIFStudentUpdate.jComboBoxClass.setSelectedIndex(i);
                    }
                }

                //gender
                if (model.getValueAt(index, 4).toString().equals("Male")) {
                    JIFStudentUpdate.jRadioButtonMale.setSelected(true);
                    JIFStudentUpdate.jRadioButtonFmale.setSelected(false);
                } else if (model.getValueAt(index, 4).toString().equals("Female")) {
                    JIFStudentUpdate.jRadioButtonFmale.setSelected(true);
                    JIFStudentUpdate.jRadioButtonMale.setSelected(false);
                }

                //birth date
                try {
                    JIFStudentUpdate.jDateChooserBDate.setDate(birthDate());
                } catch (ParseException ex) {
                    Logger.getLogger(JPStudentManager.class.getName()).log(Level.SEVERE, null, ex);
                }

                JIFStudentUpdate.jTextFieldPhone.setText(model.getValueAt(index, 6).toString());
                JIFStudentUpdate.jTextFieldEmail.setText(model.getValueAt(index, 7).toString());
                JIFStudentUpdate.jTextAreaAddress.setText(model.getValueAt(index, 8).toString());
            }
        }
    }

    private void createChartGender() {
        int totalMale = studentDAO.countStudentByMale(1);
        int totalFemale = studentDAO.countStudentByFemale(0);
        int totalStudents = studentDAO.count();

        DefaultPieDataset pieDataset = new DefaultPieDataset(); //Constructs a new dataset, initially empty.

        pieDataset.setValue("Male", (float) totalMale / totalStudents * 100); //tính % số student male so với totalStudents
        pieDataset.setValue("Female", (float) totalFemale / totalStudents * 100);//tính % số student female so với totalStudents

        //Creates a new chart with the given title and plot.
        JFreeChart chart = ChartFactory.createPieChart3D("Statistics gender", pieDataset, true, true, true);

        ChartPanel chartPanel = new ChartPanel(chart);//Constructs a panel that displays the specified chart.

        MainFrame.jPanelStatisticsGender.removeAll();
        MainFrame.jPanelStatisticsGender.add(chartPanel);

    }

    //Xử lý radio button của gender
    private byte gender() {
        int index = jTableStudent.getSelectedRow();
        TableModel model = jTableStudent.getModel();

        if (model.getValueAt(index, 4).toString().equals("Male")) {
            return 1;
        } else if (model.getValueAt(index, 4).toString().equals("Female")) {
            return 0;
        } else {
            return -1;
        }
    }

    private void insertTempStudent() {
        int index = jTableStudent.getSelectedRow();
        TableModel model = jTableStudent.getModel();

        Student student = new Student(
                model.getValueAt(index, 1).toString(),
                model.getValueAt(index, 0).toString(),
                model.getValueAt(index, 2).toString(),
                model.getValueAt(index, 3).toString(),
                gender(),
                model.getValueAt(index, 5).toString(),
                model.getValueAt(index, 6).toString(),
                model.getValueAt(index, 7).toString(),
                model.getValueAt(index, 8).toString()
        );

        boolean row = studentDAO.saveStudentTemp(student);

        if (row) {
            System.out.println("insert student temp success");
        } else {
            System.out.println("failed");
        }
    }

    private void insertTempScore() {
        int index = jTableStudent.getSelectedRow();
        TableModel model = jTableStudent.getModel();

        String rollN = String.valueOf(model.getValueAt(index, 0));

        List<Score> scores = scoreDAO.getAll();

        for (Score score : scores) {
            if (score.getRollNumber().equalsIgnoreCase(rollN)) {
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

    //get idStudent từ bảng
    private int idStudent() {
        int index = jTableStudent.getSelectedRow();
        TableModel model = jTableStudent.getModel();

        int idStudent = 0;
        String rollN = model.getValueAt(index, 0).toString();

        for (Student student : studentDAO.getAll()) {
            if (rollN.equalsIgnoreCase(student.getRollNumber())) {
                idStudent = student.getIdStudent();
            }
        }

        return idStudent;
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
        jTableStudent = new javax.swing.JTable();
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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelDeleteMouseReleased(evt);
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
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Roll number", "Name", "Class", "Phone", "Email", "Address" }));
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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelRefreshMouseReleased(evt);
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

        jTableStudent.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTableStudent.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableStudent.setFillsViewportHeight(true);
        jTableStudent.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTableStudentMouseMoved(evt);
            }
        });
        jTableStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableStudentMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTableStudentMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableStudentMouseReleased(evt);
            }
        });
        jTableStudent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableStudentKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableStudent);

        jPanelTop.setBackground(new java.awt.Color(153, 153, 153));
        jPanelTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelTopMouseMoved(evt);
            }
        });

        jLabelStudent.setFont(new java.awt.Font("Century Gothic", 1, 22)); // NOI18N
        jLabelStudent.setForeground(new java.awt.Color(255, 255, 255));
        jLabelStudent.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Student</div></html> ");

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

    private void jTableStudentMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableStudentMouseMoved
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
    }//GEN-LAST:event_jTableStudentMouseMoved

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
            if (jIFStudentAdd == null) {
                jIFStudentAdd = new JIFStudentAdd();
                jTableStudent.add(jIFStudentAdd);
                jIFStudentAdd.setBounds(0, 0, jTableStudent.getSize().width / 2 + 100, 430);
                jIFStudentAdd.setVisible(true);

                if (jIFStudentUpdate != null) {
                    jIFStudentUpdate.setVisible(false);
                }
            }
            jIFStudentAdd.setVisible(true);

            if (jIFStudentUpdate != null) {
                jIFStudentUpdate.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableStudent.getSize();
            Dimension sizejInternalFrameAdd = jIFStudentAdd.getSize();
            jIFStudentAdd.setLocation((sizejTableStudent.width - sizejInternalFrameAdd.width) / 2, (sizejTableStudent.height - sizejInternalFrameAdd.height) / 2);
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
            if (jIFStudentUpdate == null) {
                jIFStudentUpdate = new JIFStudentUpdate();
                jTableStudent.add(jIFStudentUpdate);
                jIFStudentUpdate.setBounds(0, 0, jTableStudent.getSize().width / 2 + 100, 430);
                jIFStudentUpdate.setVisible(true);

                if (jIFStudentAdd != null) {
                    jIFStudentAdd.setVisible(false);
                }

                try {
                    setData();
                } catch (Exception e) {
                }

            }
            jIFStudentUpdate.setVisible(true);

            if (jIFStudentAdd != null) {
                jIFStudentAdd.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableStudent.getSize();
            Dimension sizejInternalFrameU = jIFStudentUpdate.getSize();
            jIFStudentUpdate.setLocation((sizejTableStudent.width - sizejInternalFrameU.width) / 2, (sizejTableStudent.height - sizejInternalFrameU.height) / 2);
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
                int index = jTableStudent.getSelectedRow();
                String rollNumber = jTableStudent.getModel().getValueAt(index, 0).toString();
                int yes = JOptionPane.showConfirmDialog(this, "The Scores will be also deleted."
                        + "Are you sure want to this student delete?", "Warning", JOptionPane.YES_NO_OPTION);

                if (yes == JOptionPane.YES_OPTION) {
                    insertTempStudent();
                    insertTempScore();

                    scoreDAO.deleteByRollN(String.valueOf(idStudent()));
                    boolean row = studentDAO.delete(rollNumber);

                    if (row) {
                        JOptionPane.showMessageDialog(this, "Student Data Deleted!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                        //count số student
                        int totalStudents = studentDAO.count();
                        MainFrame.jLabelCountStudent.setText("" + totalStudents);

                        createChartGender();

                        //khi delete thành công thì show data lên bảng
                        fillData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error Delete Student!!!", "Warning", JOptionPane.ERROR_MESSAGE);
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

    private void jTableStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableStudentMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableStudentMouseClicked

    private void jTableStudentMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableStudentMouseReleased
        // TODO add your handling code here:
        List<Student> students = studentDAO.getAll();

        if (students.size() > 0) {
            int index = jTableStudent.getSelectedRow();
            TableModel model = jTableStudent.getModel();
            if (jIFStudentUpdate != null) {//tránh lỗi NULLpointerEx do chưa khởi tạo IFAddStudentForm mà đã click vào bảng
                JIFStudentUpdate.jTextFieldRollN.setText(model.getValueAt(index, 0).toString());
                JIFStudentUpdate.jTextFieldFName.setText(model.getValueAt(index, 2).toString());
                JIFStudentUpdate.jTextFieldLName.setText(model.getValueAt(index, 3).toString());

                //jcombobox class name
                for (int i = 0; i < JIFStudentUpdate.jComboBoxClass.getItemCount(); i++) {
                    if (JIFStudentUpdate.jComboBoxClass.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 1).toString())) {
                        JIFStudentUpdate.jComboBoxClass.setSelectedIndex(i);
                    }
                }

                //gender
                if (model.getValueAt(index, 4).toString().equals("Male")) {
                    JIFStudentUpdate.jRadioButtonMale.setSelected(true);
                    JIFStudentUpdate.jRadioButtonFmale.setSelected(false);
                } else if (model.getValueAt(index, 4).toString().equals("Female")) {
                    JIFStudentUpdate.jRadioButtonFmale.setSelected(true);
                    JIFStudentUpdate.jRadioButtonMale.setSelected(false);
                }

                //birth date
                try {
                    JIFStudentUpdate.jDateChooserBDate.setDate(birthDate());
                } catch (ParseException ex) {
                    Logger.getLogger(JPStudentManager.class.getName()).log(Level.SEVERE, null, ex);
                }

                JIFStudentUpdate.jTextFieldPhone.setText(model.getValueAt(index, 6).toString());
                JIFStudentUpdate.jTextFieldEmail.setText(model.getValueAt(index, 7).toString());
                JIFStudentUpdate.jTextAreaAddress.setText(model.getValueAt(index, 8).toString());
            }
        }
    }//GEN-LAST:event_jTableStudentMouseReleased

    private void jTableStudentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableStudentKeyReleased
        // TODO add your handling code here:
        List<Student> students = studentDAO.getAll();

        //get key up and key down để fill dữ liệu ra form add
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
            if (students.size() > 0) {
                int index = jTableStudent.getSelectedRow();
                TableModel model = jTableStudent.getModel();
                if (jIFStudentUpdate != null) {//tránh lỗi NULLpointerEx do chưa khởi tạo IFAddStudentForm mà đã click vào bảng
                    JIFStudentUpdate.jTextFieldRollN.setText(model.getValueAt(index, 0).toString());
                    JIFStudentUpdate.jTextFieldFName.setText(model.getValueAt(index, 2).toString());
                    JIFStudentUpdate.jTextFieldLName.setText(model.getValueAt(index, 3).toString());

                    //jcombobox class name
                    for (int i = 0; i < JIFStudentUpdate.jComboBoxClass.getItemCount(); i++) {
                        if (JIFStudentUpdate.jComboBoxClass.getItemAt(i).toString().
                                equalsIgnoreCase(model.getValueAt(index, 1).toString())) {
                            JIFStudentUpdate.jComboBoxClass.setSelectedIndex(i);
                        }
                    }

                    //gender
                    if (model.getValueAt(index, 4).toString().equals("Male")) {
                        JIFStudentUpdate.jRadioButtonMale.setSelected(true);
                        JIFStudentUpdate.jRadioButtonFmale.setSelected(false);
                    } else if (model.getValueAt(index, 4).toString().equals("Female")) {
                        JIFStudentUpdate.jRadioButtonFmale.setSelected(true);
                        JIFStudentUpdate.jRadioButtonMale.setSelected(false);
                    }

                    //birth date
                    try {
                        JIFStudentUpdate.jDateChooserBDate.setDate(birthDate());
                    } catch (ParseException ex) {
                        Logger.getLogger(JPStudentManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    JIFStudentUpdate.jTextFieldPhone.setText(model.getValueAt(index, 6).toString());
                    JIFStudentUpdate.jTextFieldEmail.setText(model.getValueAt(index, 7).toString());
                    JIFStudentUpdate.jTextAreaAddress.setText(model.getValueAt(index, 8).toString());
                }
            }
        }
    }//GEN-LAST:event_jTableStudentKeyReleased

    private void jLabelRefreshMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRefreshMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelRefreshMouseReleased

    private void jTableStudentMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableStudentMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableStudentMouseEntered

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

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        List<Student> students = studentDAO.search(jTextFieldSearch.getText());

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

        jTableStudent.setModel(dtm);
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void jLabelDeleteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDeleteMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelDeleteMouseReleased

    private void jLabelRecycleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecycleMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelRecycleMouseReleased

    private void jLabelRecycleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecycleMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        MainFrame.getjPRecycle().setVisible(true);

        List<Student> students = studentDAO.getAllStudentTemp();

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
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
            vector.add(student.getIdStudent());
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

        JPRecycle.jTableRecycle.setModel(dtm);
        JPRecycle.jTableRecycle.removeColumn(JPRecycle.jTableRecycle.getColumnModel().getColumn(0));

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        dcbm.addElement("Roll number");
        dcbm.addElement("Name");
        dcbm.addElement("Class");
        dcbm.addElement("Phone");
        dcbm.addElement("Email");
        dcbm.addElement("Address");

        JPRecycle.jComboBoxSearch.setModel(dcbm);
    }//GEN-LAST:event_jLabelRecycleMouseClicked

    private void jLabelRecycleMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRecycleMouseMoved
        // TODO add your handling code here:
        jLabelRecycle.setBackground(new Color(255, 255, 255));
        jLabelRecycle.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_jLabelRecycleMouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> jComboBoxSearch;
    public static javax.swing.JLabel jLabelAdd;
    public static javax.swing.JLabel jLabelDelete;
    public static javax.swing.JLabel jLabelRecycle;
    private javax.swing.JLabel jLabelRefresh;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JLabel jLabelStudent;
    public static javax.swing.JLabel jLabelUpdate;
    private javax.swing.JPanel jPanelMid;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableStudent;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
