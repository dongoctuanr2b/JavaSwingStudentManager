/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.score;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import sm.jPanelManager.classes.ClassesDAO;
import sm.jPanelManager.recycle.JPRecycle;
import sm.jPanelManager.student.StudentDAO;
import sm.jPanelManager.subject.SubjectDAO;
import sm.mainFrame.MainFrame;

/**
 *
 * @author Ngoc Tuan
 */
public class JPScoreManager extends javax.swing.JPanel {

    private JIFScoreAdd jIFScoreAdd;
    private JIFScoreUpdate jIFScoreUpdate;

    private ScoreDAO scoreDAO = new ScoreDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private ClassesDAO classesDAO = new ClassesDAO();

    /**
     * Creates new form JPanelStudent
     */
    public JPScoreManager() {
        initComponents();

        setVisible(false);
        setBounds(0, 0, MainFrame.jPanelContent.getSize().width + 105, MainFrame.jPanelContent.getSize().height);

        jTableScore.setRowHeight(30);
        jTableScore.setShowGrid(true);
        jTableScore.setGridColor(Color.gray);

        fillData();
    }

    private void setData() {
        List<Score> scores = scoreDAO.getAll();

        if (scores.size() > 0) {
            int index = jTableScore.getSelectedRow();
            TableModel model = jTableScore.getModel();
            if (jIFScoreUpdate != null) {
                for (int i = 0; i < JIFScoreUpdate.jComboBoxRollN.getItemCount(); i++) {
                    if (JIFScoreUpdate.jComboBoxRollN.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 1).toString())) {
                        JIFScoreUpdate.jComboBoxRollN.setSelectedIndex(i);
                    }
                }

                for (int i = 0; i < JIFScoreUpdate.jComboBoxSubjectN.getItemCount(); i++) {
                    if (JIFScoreUpdate.jComboBoxSubjectN.getItemAt(i).toString()
                            .equalsIgnoreCase(model.getValueAt(index, 4).toString())) {
                        JIFScoreUpdate.jComboBoxSubjectN.setSelectedIndex(i);
                    }
                }

                for (int i = 0; i < JIFScoreUpdate.jComboBoxClass.getItemCount(); i++) {
                    if (JIFScoreUpdate.jComboBoxClass.getItemAt(i).toString()
                            .equalsIgnoreCase(model.getValueAt(index, 5).toString())) {
                        JIFScoreUpdate.jComboBoxClass.setSelectedIndex(i);
                    }
                }

                for (int i = 0; i < JIFScoreUpdate.jComboBoxTOScore.getItemCount(); i++) {
                    if (JIFScoreUpdate.jComboBoxTOScore.getItemAt(i).toString()
                            .equalsIgnoreCase(model.getValueAt(index, 7).toString())) {
                        JIFScoreUpdate.jComboBoxTOScore.setSelectedIndex(i);
                    }
                }

                JIFScoreUpdate.jTextFieldFName.setText(model.getValueAt(index, 2).toString());
                JIFScoreUpdate.jTextFieldLName.setText(model.getValueAt(index, 3).toString());
                JIFScoreUpdate.jTextFieldScore.setText(model.getValueAt(index, 6).toString());
                JIFScoreUpdate.jTextFieldNOExam.setText(model.getValueAt(index, 8).toString());
                JIFScoreUpdate.jTextAreaDesc.setText(model.getValueAt(index, 9).toString());
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

    //get idScore từ bảng
    private String idScore() {
        int index = jTableScore.getSelectedRow();
        TableModel model = jTableScore.getModel();

        return model.getValueAt(index, 0).toString();
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

    private void insertTempScore() {
        int index = jTableScore.getSelectedRow();
        TableModel model = jTableScore.getModel();

        Score score = new Score(
                model.getValueAt(index, 1).toString(),
                model.getValueAt(index, 2).toString(),
                model.getValueAt(index, 3).toString(),
                model.getValueAt(index, 4).toString(),
                model.getValueAt(index, 5).toString(),
                Float.valueOf(model.getValueAt(index, 6).toString()),
                model.getValueAt(index, 7).toString(),
                Integer.valueOf(model.getValueAt(index, 8).toString()),
                model.getValueAt(index, 9).toString()
        );

        boolean row = scoreDAO.saveScoreTemp(score);

        if (row) {
            System.out.println("insert score temp success");
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
        jTableScore = new javax.swing.JTable();
        jPanelTop = new javax.swing.JPanel();
        jLabelScore = new javax.swing.JLabel();

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
        jComboBoxSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Roll number", "Name", "Description", "Score", "Subject name", "Class name" }));
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
        jLabelRecycle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/delete1.png"))); // NOI18N
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
                .addComponent(jLabelRecycle, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
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
                    .addGroup(jPanelMidLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldSearch)
                            .addComponent(jComboBoxSearch)
                            .addComponent(jLabelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabelRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelRecycle, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTableScore.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTableScore.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableScore.setFillsViewportHeight(true);
        jTableScore.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTableScoreMouseMoved(evt);
            }
        });
        jTableScore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTableScoreMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableScoreMouseReleased(evt);
            }
        });
        jTableScore.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableScoreKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableScore);

        jPanelTop.setBackground(new java.awt.Color(153, 153, 153));
        jPanelTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelTopMouseMoved(evt);
            }
        });

        jLabelScore.setFont(new java.awt.Font("Century Gothic", 1, 22)); // NOI18N
        jLabelScore.setForeground(new java.awt.Color(255, 255, 255));
        jLabelScore.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Score</div></html> ");

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelScore)
                .addContainerGap())
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelScore, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
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
                .addComponent(jPanelMid, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
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

    private void jTableScoreMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableScoreMouseMoved
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
    }//GEN-LAST:event_jTableScoreMouseMoved

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
            if (jIFScoreAdd == null) {
                jIFScoreAdd = new JIFScoreAdd();
                jTableScore.add(jIFScoreAdd);
                jIFScoreAdd.setBounds(0, 0, jTableScore.getSize().width / 2 + 170, 430);
                jIFScoreAdd.setVisible(true);

                if (jIFScoreUpdate != null) {
                    jIFScoreUpdate.setVisible(false);
                }
            }
            jIFScoreAdd.setVisible(true);

            if (jIFScoreUpdate != null) {
                jIFScoreUpdate.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableScore.getSize();
            Dimension sizejInternalFrameAdd = jIFScoreAdd.getSize();
            jIFScoreAdd.setLocation((sizejTableStudent.width - sizejInternalFrameAdd.width) / 2, (sizejTableStudent.height - sizejInternalFrameAdd.height) / 2);
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
            if (jIFScoreUpdate == null) {
                jIFScoreUpdate = new JIFScoreUpdate();
                jTableScore.add(jIFScoreUpdate);
                jIFScoreUpdate.setBounds(0, 0, jTableScore.getSize().width / 2 + 190, 430);
                jIFScoreUpdate.setVisible(true);

                if (jIFScoreAdd != null) {
                    jIFScoreAdd.setVisible(false);
                }

                try {
                    setData();
                } catch (Exception e) {
                }
            }
            jIFScoreUpdate.setVisible(true);

            if (jIFScoreAdd != null) {
                jIFScoreAdd.setVisible(false);
            }

            //set Location của jIternalFrame ở giữa jTableStudent
            Dimension sizejTableStudent = jTableScore.getSize();
            Dimension sizejInternalFrameU = jIFScoreUpdate.getSize();
            jIFScoreUpdate.setLocation((sizejTableStudent.width - sizejInternalFrameU.width) / 2, (sizejTableStudent.height - sizejInternalFrameU.height) / 2);
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
                int position = jTableScore.getSelectedRow();
                if (position >= 0) {
                    int yes = JOptionPane.showConfirmDialog(this, "Are you sure want to this score delete?", "Warning", JOptionPane.YES_NO_OPTION);

                    if (yes == JOptionPane.YES_OPTION) {
                        insertTempScore();
                        boolean row = scoreDAO.delete(idScore());

                        if (row) {
                            JOptionPane.showMessageDialog(this, "Score Data Deleted!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                            createChartScore();

                            //khi delete thành công thì show data lên bảng
                            fillData();
                        } else {
                            JOptionPane.showMessageDialog(this, "Error Delete Score!!!", "Warning", JOptionPane.ERROR_MESSAGE);
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

    private void jComboBoxSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBoxSearchFocusGained
        // TODO add your handling code here:      
    }//GEN-LAST:event_jComboBoxSearchFocusGained

    private void jComboBoxSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBoxSearchFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxSearchFocusLost

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

    private void jTableScoreMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableScoreMouseReleased
        // TODO add your handling code here:
        List<Score> scores = scoreDAO.getAll();

        if (scores.size() > 0) {
            int index = jTableScore.getSelectedRow();
            TableModel model = jTableScore.getModel();
            if (jIFScoreUpdate != null) {
                for (int i = 0; i < JIFScoreUpdate.jComboBoxRollN.getItemCount(); i++) {
                    if (JIFScoreUpdate.jComboBoxRollN.getItemAt(i).toString().
                            equalsIgnoreCase(model.getValueAt(index, 1).toString())) {
                        JIFScoreUpdate.jComboBoxRollN.setSelectedIndex(i);
                    }
                }

                for (int i = 0; i < JIFScoreUpdate.jComboBoxSubjectN.getItemCount(); i++) {
                    if (JIFScoreUpdate.jComboBoxSubjectN.getItemAt(i).toString()
                            .equalsIgnoreCase(model.getValueAt(index, 4).toString())) {
                        JIFScoreUpdate.jComboBoxSubjectN.setSelectedIndex(i);
                    }
                }

                for (int i = 0; i < JIFScoreUpdate.jComboBoxClass.getItemCount(); i++) {
                    if (JIFScoreUpdate.jComboBoxClass.getItemAt(i).toString()
                            .equalsIgnoreCase(model.getValueAt(index, 5).toString())) {
                        JIFScoreUpdate.jComboBoxClass.setSelectedIndex(i);
                    }
                }

                for (int i = 0; i < JIFScoreUpdate.jComboBoxTOScore.getItemCount(); i++) {
                    if (JIFScoreUpdate.jComboBoxTOScore.getItemAt(i).toString()
                            .equalsIgnoreCase(model.getValueAt(index, 7).toString())) {
                        JIFScoreUpdate.jComboBoxTOScore.setSelectedIndex(i);
                    }
                }

                JIFScoreUpdate.jTextFieldFName.setText(model.getValueAt(index, 2).toString());
                JIFScoreUpdate.jTextFieldLName.setText(model.getValueAt(index, 3).toString());
                JIFScoreUpdate.jTextFieldScore.setText(model.getValueAt(index, 6).toString());
                JIFScoreUpdate.jTextFieldNOExam.setText(model.getValueAt(index, 8).toString());
                JIFScoreUpdate.jTextAreaDesc.setText(model.getValueAt(index, 9).toString());
            }
        }
    }//GEN-LAST:event_jTableScoreMouseReleased

    private void jTableScoreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableScoreMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableScoreMouseEntered

    private void jTableScoreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableScoreKeyReleased
        // TODO add your handling code here:
        List<Score> scores = scoreDAO.getAll();

        //get key up and key down để fill dữ liệu ra form add
        if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
            if (scores.size() > 0) {
                int index = jTableScore.getSelectedRow();
                TableModel model = jTableScore.getModel();
                if (jIFScoreUpdate != null) {
                    for (int i = 0; i < JIFScoreUpdate.jComboBoxRollN.getItemCount(); i++) {
                        if (JIFScoreUpdate.jComboBoxRollN.getItemAt(i).toString().
                                equalsIgnoreCase(model.getValueAt(index, 1).toString())) {
                            JIFScoreUpdate.jComboBoxRollN.setSelectedIndex(i);
                        }
                    }

                    for (int i = 0; i < JIFScoreUpdate.jComboBoxSubjectN.getItemCount(); i++) {
                        if (JIFScoreUpdate.jComboBoxSubjectN.getItemAt(i).toString()
                                .equalsIgnoreCase(model.getValueAt(index, 4).toString())) {
                            JIFScoreUpdate.jComboBoxSubjectN.setSelectedIndex(i);
                        }
                    }

                    for (int i = 0; i < JIFScoreUpdate.jComboBoxClass.getItemCount(); i++) {
                        if (JIFScoreUpdate.jComboBoxClass.getItemAt(i).toString()
                                .equalsIgnoreCase(model.getValueAt(index, 5).toString())) {
                            JIFScoreUpdate.jComboBoxClass.setSelectedIndex(i);
                        }
                    }

                    for (int i = 0; i < JIFScoreUpdate.jComboBoxTOScore.getItemCount(); i++) {
                        if (JIFScoreUpdate.jComboBoxTOScore.getItemAt(i).toString()
                                .equalsIgnoreCase(model.getValueAt(index, 7).toString())) {
                            JIFScoreUpdate.jComboBoxTOScore.setSelectedIndex(i);
                        }
                    }

                    JIFScoreUpdate.jTextFieldFName.setText(model.getValueAt(index, 2).toString());
                    JIFScoreUpdate.jTextFieldLName.setText(model.getValueAt(index, 3).toString());
                    JIFScoreUpdate.jTextFieldScore.setText(model.getValueAt(index, 6).toString());
                    JIFScoreUpdate.jTextFieldNOExam.setText(model.getValueAt(index, 8).toString());
                    JIFScoreUpdate.jTextAreaDesc.setText(model.getValueAt(index, 9).toString());
                }
            }
        }
    }//GEN-LAST:event_jTableScoreKeyReleased

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        List<Score> scores = scoreDAO.search(jTextFieldSearch.getText());

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

        List<Score> scores = scoreDAO.getAllScoreTemp();

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

        JPRecycle.jTableRecycle.setModel(dtm);
        JPRecycle.jTableRecycle.removeColumn(JPRecycle.jTableRecycle.getColumnModel().getColumn(0));

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();

        dcbm.addElement("Roll number");
        dcbm.addElement("Name");
        dcbm.addElement("Description");
        dcbm.addElement("Score");
        dcbm.addElement("Subject name");
        dcbm.addElement("Class name");

        JPRecycle.jComboBoxSearch.setModel(dcbm);
    }//GEN-LAST:event_jLabelRecycleMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> jComboBoxSearch;
    public static javax.swing.JLabel jLabelAdd;
    public static javax.swing.JLabel jLabelDelete;
    public static javax.swing.JLabel jLabelRecycle;
    private javax.swing.JLabel jLabelRefresh;
    private javax.swing.JLabel jLabelScore;
    private javax.swing.JLabel jLabelSearch;
    public static javax.swing.JLabel jLabelUpdate;
    private javax.swing.JPanel jPanelMid;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableScore;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
