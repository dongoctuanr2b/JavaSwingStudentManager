/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.login;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import sm.jPanelManager.classes.ClassesDAO;
import sm.jPanelManager.classes.JPClassManager;
import sm.jPanelManager.room.JPRoomManager;
import sm.jPanelManager.score.JPScoreManager;
import sm.jPanelManager.score.Score;
import sm.jPanelManager.score.ScoreDAO;
import sm.jPanelManager.student.JPStudentManager;
import sm.jPanelManager.student.StudentDAO;
import sm.jPanelManager.subject.JPSubjectManager;
import sm.jPanelManager.teacher.JPTeacherManager;
import sm.jPanelManager.teacher.TeacherDAO;
import sm.jPanelManager.timetable.JPTimetableManager;
import sm.jPanelManager.user.User;
import sm.jPanelManager.user.UserDAO;
import sm.mainFrame.MainFrame;

/**
 *
 * @author Ngoc Tuan
 */
public class Login extends javax.swing.JFrame {

    private JPanelSU jPanelSU;
    private MainFrame mainFrame;
    private static int idPermission = 0;

    private UserDAO userDAO = new UserDAO();

    public static int getIdPermission() {
        return idPermission;
    }

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();

        this.setLocationRelativeTo(null);
    }

    private void permission() {
        List<User> users = userDAO.getAll();

        for (User user : users) {
            if (jTextFieldSI.getText().equalsIgnoreCase(user.getUsername())) {
                switch (user.getPermissionName()) {
                    case "Teacher":
                        MainFrame.jLabelUser.setEnabled(false);

                        JPTeacherManager.jLabelAdd.setEnabled(false);
                        JPTeacherManager.jLabelUpdate.setEnabled(false);
                        JPTeacherManager.jLabelDelete.setEnabled(false);

                        JPClassManager.jLabelAdd.setEnabled(false);
                        JPClassManager.jLabelUpdate.setEnabled(false);
                        JPClassManager.jLabelDelete.setEnabled(false);

                        JPRoomManager.jLabelAdd.setEnabled(false);
                        JPRoomManager.jLabelUpdate.setEnabled(false);
                        JPRoomManager.jLabelDelete.setEnabled(false);

                        JPSubjectManager.jLabelAdd.setEnabled(false);
                        JPSubjectManager.jLabelUpdate.setEnabled(false);
                        JPSubjectManager.jLabelDelete.setEnabled(false);

                        JPTimetableManager.jLabelAdd.setEnabled(false);
                        JPTimetableManager.jLabelUpdate.setEnabled(false);
                        JPTimetableManager.jLabelDelete.setEnabled(false);

                        break;
                    case "Student":
                        MainFrame.jLabelUser.setEnabled(false);

                        JPTeacherManager.jLabelAdd.setEnabled(false);
                        JPTeacherManager.jLabelUpdate.setEnabled(false);
                        JPTeacherManager.jLabelDelete.setEnabled(false);

                        JPClassManager.jLabelAdd.setEnabled(false);
                        JPClassManager.jLabelUpdate.setEnabled(false);
                        JPClassManager.jLabelDelete.setEnabled(false);

                        JPRoomManager.jLabelAdd.setEnabled(false);
                        JPRoomManager.jLabelUpdate.setEnabled(false);
                        JPRoomManager.jLabelDelete.setEnabled(false);

                        JPStudentManager.jLabelAdd.setEnabled(false);
                        JPStudentManager.jLabelUpdate.setEnabled(false);
                        JPStudentManager.jLabelDelete.setEnabled(false);

                        JPTimetableManager.jLabelAdd.setEnabled(false);
                        JPTimetableManager.jLabelUpdate.setEnabled(false);
                        JPTimetableManager.jLabelDelete.setEnabled(false);

                        JPSubjectManager.jLabelAdd.setEnabled(false);
                        JPSubjectManager.jLabelUpdate.setEnabled(false);
                        JPSubjectManager.jLabelDelete.setEnabled(false);

                        JPScoreManager.jLabelAdd.setEnabled(false);
                        JPScoreManager.jLabelUpdate.setEnabled(false);
                        JPScoreManager.jLabelDelete.setEnabled(false);

                        break;
                    default:
                        break;
                }
            }
        }

    }

    private void createChartGender() {
        StudentDAO studentDAO = new StudentDAO();
        int totalMale = studentDAO.countStudentByMale(1);
        int totalFemale = studentDAO.countStudentByFemale(0);
        int totalStudents = studentDAO.count();

        DefaultPieDataset pieDataset = new DefaultPieDataset(); //Constructs a new dataset, initially empty.

        pieDataset.setValue("Male", (float) totalMale / totalStudents * 100); //tính % số student male so với totalStudents
        pieDataset.setValue("Female", (float) totalFemale / totalStudents * 100);//tính % số student female so với totalStudents

        //Creates a new chart with the given title and plot.
        JFreeChart chart = ChartFactory.createPieChart3D("Statistics gender", pieDataset, true, true, true);

        ChartPanel chartPanel = new ChartPanel(chart);//Constructs a panel that displays the specified chart.

        MainFrame.jPanelStatisticsGender.add(chartPanel);

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

        jPopupMenuSU = new javax.swing.JPopupMenu();
        jMenuItemS = new javax.swing.JMenuItem();
        jMenuItemT = new javax.swing.JMenuItem();
        jPanelcontent = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonSI = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonSU = new javax.swing.JLabel();
        jPanelSISU = new javax.swing.JPanel();
        jLabelExit = new javax.swing.JLabel();
        jPanelSI = new javax.swing.JPanel();
        jLabelSI = new javax.swing.JLabel();
        jTextFieldSI = new javax.swing.JTextField();
        jLabelUS = new javax.swing.JLabel();
        jLabelPass = new javax.swing.JLabel();
        jButtonSI2 = new javax.swing.JLabel();
        jLabelLine = new javax.swing.JLabel();
        jPasswordSI = new javax.swing.JPasswordField();
        jLabelEyeSI = new javax.swing.JLabel();

        jPopupMenuSU.setForeground(new java.awt.Color(240, 240, 240));
        jPopupMenuSU.setOpaque(false);

        jMenuItemS.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItemS.setForeground(new java.awt.Color(53, 173, 164));
        jMenuItemS.setText("jMenuItem1");
        jMenuItemS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItemS.setOpaque(true);
        jMenuItemS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jMenuItemSMouseMoved(evt);
            }
        });
        jMenuItemS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItemSMouseClicked(evt);
            }
        });
        jMenuItemS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSActionPerformed(evt);
            }
        });
        jMenuItemS.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jMenuItemSPropertyChange(evt);
            }
        });
        jPopupMenuSU.add(jMenuItemS);

        jMenuItemT.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItemT.setForeground(new java.awt.Color(53, 173, 164));
        jMenuItemT.setText("jMenuItem2");
        jMenuItemT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItemT.setOpaque(true);
        jMenuItemT.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jMenuItemTMouseMoved(evt);
            }
        });
        jMenuItemT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTActionPerformed(evt);
            }
        });
        jPopupMenuSU.add(jMenuItemT);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanelcontent.setBackground(new java.awt.Color(255, 255, 255));
        jPanelcontent.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanelcontentMouseDragged(evt);
            }
        });
        jPanelcontent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanelcontentMousePressed(evt);
            }
        });

        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });
        jPanel1.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                jPanel1VetoableChange(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Welcome Back");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("<html><div style='text-align: center;'>Lorem Ipsum is simply dummy text of <br/> the printing and typesetting industry. <br/> Lorem Ipsum has been the industry's <br/> standard dummy</div></html> ");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButtonSI.setBackground(new java.awt.Color(102, 102, 102));
        jButtonSI.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSI.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSI.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;SIGN IN</div></html> ");
        jButtonSI.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jButtonSI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSI.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSI.setOpaque(true);
        jButtonSI.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButtonSIMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButtonSIMouseMoved(evt);
            }
        });
        jButtonSI.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jButtonSIMouseWheelMoved(evt);
            }
        });
        jButtonSI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSIMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonSIMouseReleased(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/iconfinder_Student_1170240.png"))); // NOI18N

        jButtonSU.setBackground(new java.awt.Color(102, 102, 102));
        jButtonSU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSU.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSU.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;SIGN UP</div></html> ");
        jButtonSU.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jButtonSU.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSU.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSU.setOpaque(true);
        jButtonSU.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButtonSUMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButtonSUMouseMoved(evt);
            }
        });
        jButtonSU.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jButtonSUMouseWheelMoved(evt);
            }
        });
        jButtonSU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSUMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonSUMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonSUMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jButtonSI, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSU, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSI, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSU, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );

        jPanelSISU.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSISU.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanelSISUMouseDragged(evt);
            }
        });
        jPanelSISU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanelSISUMousePressed(evt);
            }
        });

        jLabelExit.setBackground(new java.awt.Color(255, 255, 255));
        jLabelExit.setForeground(new java.awt.Color(53, 173, 164));
        jLabelExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/exitLogin.png"))); // NOI18N
        jLabelExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelExit.setOpaque(true);
        jLabelExit.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelExitMouseMoved(evt);
            }
        });
        jLabelExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelExitMouseClicked(evt);
            }
        });

        jPanelSI.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSI.setOpaque(false);
        jPanelSI.setPreferredSize(new java.awt.Dimension(400, 373));
        jPanelSI.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanelSIformMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelSIformMouseMoved(evt);
            }
        });
        jPanelSI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanelSIformMousePressed(evt);
            }
        });
        jPanelSI.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelSI.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSI.setFont(new java.awt.Font("Segoe UI", 0, 27)); // NOI18N
        jLabelSI.setForeground(new java.awt.Color(102, 102, 102));
        jLabelSI.setText("Sign In");
        jPanelSI.add(jLabelSI, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, -1, -1));

        jTextFieldSI.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextFieldSI.setForeground(new java.awt.Color(153, 153, 153));
        jTextFieldSI.setText("username");
        jTextFieldSI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldSIFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldSIFocusLost(evt);
            }
        });
        jTextFieldSI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSIActionPerformed(evt);
            }
        });
        jPanelSI.add(jTextFieldSI, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 233, 32));

        jLabelUS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/username.png"))); // NOI18N
        jPanelSI.add(jLabelUS, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));

        jLabelPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/pass.png"))); // NOI18N
        jPanelSI.add(jLabelPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jButtonSI2.setBackground(new java.awt.Color(102, 102, 102));
        jButtonSI2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSI2.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSI2.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;SIGN IN</div></html> ");
        jButtonSI2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jButtonSI2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSI2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSI2.setOpaque(true);
        jButtonSI2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButtonSI2MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButtonSI2MouseMoved(evt);
            }
        });
        jButtonSI2.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jButtonSI2MouseWheelMoved(evt);
            }
        });
        jButtonSI2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSI2MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonSI2MouseReleased(evt);
            }
        });
        jPanelSI.add(jButtonSI2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 143, 39));

        jLabelLine.setBackground(new java.awt.Color(0, 0, 0));
        jLabelLine.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLine.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanelSI.add(jLabelLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 240, -1));

        jPasswordSI.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPasswordSI.setForeground(new java.awt.Color(153, 153, 153));
        jPasswordSI.setText("password");
        jPasswordSI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordSIFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordSIFocusLost(evt);
            }
        });
        jPanelSI.add(jPasswordSI, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 233, 32));

        jLabelEyeSI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png"))); // NOI18N
        jLabelEyeSI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelEyeSI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEyeSIMouseClicked(evt);
            }
        });
        jPanelSI.add(jLabelEyeSI, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 30, 30));

        javax.swing.GroupLayout jPanelSISULayout = new javax.swing.GroupLayout(jPanelSISU);
        jPanelSISU.setLayout(jPanelSISULayout);
        jPanelSISULayout.setHorizontalGroup(
            jPanelSISULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSISULayout.createSequentialGroup()
                .addGap(0, 356, Short.MAX_VALUE)
                .addComponent(jLabelExit))
            .addGroup(jPanelSISULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelSI, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
        );
        jPanelSISULayout.setVerticalGroup(
            jPanelSISULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSISULayout.createSequentialGroup()
                .addComponent(jLabelExit)
                .addGap(0, 365, Short.MAX_VALUE))
            .addGroup(jPanelSISULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelSI, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelcontentLayout = new javax.swing.GroupLayout(jPanelcontent);
        jPanelcontent.setLayout(jPanelcontentLayout);
        jPanelcontentLayout.setHorizontalGroup(
            jPanelcontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 745, Short.MAX_VALUE)
            .addGroup(jPanelcontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelcontentLayout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 406, Short.MAX_VALUE)))
            .addGroup(jPanelcontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelcontentLayout.createSequentialGroup()
                    .addContainerGap(347, Short.MAX_VALUE)
                    .addComponent(jPanelSISU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanelcontentLayout.setVerticalGroup(
            jPanelcontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
            .addGroup(jPanelcontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelcontentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelcontentLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanelSISU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelcontent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelcontent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSIMouseClicked
        // TODO add your handling code here:
        try {
            JPanelSU.jPasswordSU.setEchoChar('*');
            JPanelSU.jLabelEyeSU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));
            JPanelSU.jPasswordSU1.setEchoChar('*');
            JPanelSU.jLabelEyeSU1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));

            if (jPanelSI != null) {
                jPanelSI.setVisible(true);
                jPanelSU.setVisible(false);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButtonSIMouseClicked

    private void jButtonSIMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSIMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jButtonSIMouseReleased

    private void jButtonSIMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSIMouseMoved
        // TODO add your handling code here:
        jButtonSI.setBackground(new Color(255, 255, 255));
        jButtonSI.setForeground(new Color(102, 102, 102));

    }//GEN-LAST:event_jButtonSIMouseMoved

    private void jButtonSIMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSIMouseDragged
        // TODO add your ihandling code here:
    }//GEN-LAST:event_jButtonSIMouseDragged

    private void jPanel1VetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_jPanel1VetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1VetoableChange

    private void jButtonSIMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jButtonSIMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSIMouseWheelMoved

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        // TODO add your handling code here:
        jButtonSI.setForeground(new Color(255, 255, 255));
        jButtonSI.setBackground(new Color(102, 102, 102));
        jButtonSU.setForeground(new Color(255, 255, 255));
        jButtonSU.setBackground(new Color(102, 102, 102));

        //
        jPopupMenuSU.setBorder(BorderFactory.createLineBorder(new Color(102, 102, 102)));
        jMenuItemT.setBackground(Color.WHITE);
        jMenuItemT.setForeground(new Color(102, 102, 102));
        jMenuItemS.setBackground(Color.WHITE);
        jMenuItemS.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_jPanel1MouseMoved


    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:

    }//GEN-LAST:event_formMouseDragged

    private void jButtonSUMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSUMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSUMouseDragged

    private void jButtonSUMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSUMouseMoved
        // TODO add your handling code here:
        jButtonSU.setBackground(new Color(255, 255, 255));
        jButtonSU.setForeground(new Color(102, 102, 102));

        jPopupMenuSU.setBorder(BorderFactory.createLineBorder(new Color(102, 102, 102)));
        jMenuItemT.setBackground(Color.WHITE);
        jMenuItemT.setForeground(new Color(102, 102, 102));
        jMenuItemS.setBackground(Color.WHITE);
        jMenuItemS.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_jButtonSUMouseMoved

    private void jButtonSUMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jButtonSUMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSUMouseWheelMoved

    private void jButtonSUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSUMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSUMouseClicked

    private void jButtonSUMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSUMouseReleased
        // TODO add your handling code here:
        jMenuItemS.setText("For student");
        jMenuItemT.setText("For teacher");
        jPopupMenuSU.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        if (evt.getButton() == 1) { // 1-left click, 2-middle, 3-right button
            jPopupMenuSU.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jButtonSUMouseReleased


    private void jPanelcontentMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelcontentMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelcontentMousePressed

    private void jPanelcontentMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelcontentMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelcontentMouseDragged

    static int xx, yy;
    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // TODO add your handling code here:
        xx = evt.getX();
        yy = evt.getY();
    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // TODO add your handling code here:   
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - yy);
    }//GEN-LAST:event_jPanel1MouseDragged


    private void jPanelSISUMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSISUMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanelSISUMousePressed

    private void jPanelSISUMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSISUMouseDragged
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanelSISUMouseDragged

    private void jLabelExitMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelExitMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelExitMouseMoved

    private void jLabelExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelExitMouseClicked
        // TODO add your handling code here:
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jLabelExitMouseClicked

    private void jButtonSUMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSUMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSUMousePressed

    private void jMenuItemSMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItemSMouseMoved
        // TODO add your handling code here:
        jMenuItemS.setForeground(Color.WHITE);
        jMenuItemT.setBackground(Color.WHITE);
        jMenuItemT.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_jMenuItemSMouseMoved

    private void jMenuItemTMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItemTMouseMoved
        // TODO add your handling code here:
        jMenuItemT.setForeground(Color.WHITE);
        jMenuItemS.setBackground(Color.WHITE);
        jMenuItemS.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_jMenuItemTMouseMoved

    private void jMenuItemSPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jMenuItemSPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemSPropertyChange

    private void jMenuItemSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItemSMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemSMouseClicked

    private void jMenuItemSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSActionPerformed
        // TODO add your handling code here:
        jPasswordSI.setEchoChar('*');
        jLabelEyeSI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));

        if (jPanelSU == null) {
            jPanelSU = new JPanelSU(this);
            jPanelSISU.add(jPanelSU);
            jPanelSU.setVisible(true);
            jPanelSI.setVisible(false);
            JPanelSU.jLabelSU.setText("Sign Up For Student");
        }
        jPanelSU.setVisible(true);
        jPanelSI.setVisible(false);
        JPanelSU.jLabelSU.setText("Sign Up For Student");

        JPanelSU.jPasswordSU.setEchoChar('*');
        JPanelSU.jLabelEyeSU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));
        JPanelSU.jPasswordSU1.setEchoChar('*');
        JPanelSU.jLabelEyeSU1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));

        idPermission = 2;
    }//GEN-LAST:event_jMenuItemSActionPerformed

    private void jMenuItemTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTActionPerformed
        // TODO add your handling code here:
        jPasswordSI.setEchoChar('*');
        jLabelEyeSI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));

        if (jPanelSU == null) {
            jPanelSU = new JPanelSU(this);
            jPanelSISU.add(jPanelSU);
            jPanelSU.setVisible(true);
            jPanelSI.setVisible(false);
            JPanelSU.jLabelSU.setText("Sign Up For Teacher");
        }
        jPanelSU.setVisible(true);
        jPanelSI.setVisible(false);
        JPanelSU.jLabelSU.setText("Sign Up For Teacher");

        JPanelSU.jPasswordSU.setEchoChar('*');
        JPanelSU.jLabelEyeSU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));
        JPanelSU.jPasswordSU1.setEchoChar('*');
        JPanelSU.jLabelEyeSU1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));

        idPermission = 3;
    }//GEN-LAST:event_jMenuItemTActionPerformed

    private void jTextFieldSIFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSIFocusGained
        // TODO add your handling code here:
        //xóa text nếu jtextfieldUsername on focus gained
        if (jTextFieldSI.getText().equals("username")) {
            jTextFieldSI.setText("");
            jTextFieldSI.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextFieldSIFocusGained

    private void jTextFieldSIFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSIFocusLost
        // TODO add your handling code here:
        //nếu jTextField trống thì lại setText thành username
        if (jTextFieldSI.getText().isEmpty()) {
            jTextFieldSI.setText("username");
            jTextFieldSI.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextFieldSIFocusLost

    private void jTextFieldSIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSIActionPerformed

    private void jButtonSI2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSI2MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSI2MouseDragged

    private void jButtonSI2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSI2MouseMoved
        // TODO add your handling code here:
        jButtonSI2.setBackground(new Color(255, 255, 255));
        jButtonSI2.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_jButtonSI2MouseMoved

    private void jButtonSI2MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jButtonSI2MouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSI2MouseWheelMoved

    private void jButtonSI2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSI2MouseClicked
        // TODO add your handling code here:
        String username = jTextFieldSI.getText();
        String pass = String.valueOf(jPasswordSI.getPassword());

        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAll();

        if (username.isEmpty() || jTextFieldSI.getForeground().equals(new Color(153, 153, 153))) {
            JOptionPane.showMessageDialog(rootPane, "Enter Username, please!", "Notification", JOptionPane.INFORMATION_MESSAGE);
        } else if (!username.isEmpty() || jTextFieldSI.getForeground().equals(new Color(153, 153, 153))) {
            boolean flag = false;
            for (User user : users) {
                if (username.equals(user.getUsername()) && pass.equals(user.getPassword())) {
                    JOptionPane.showMessageDialog(null, "Login successfully!");
                    mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                    mainFrame.pack();
                    mainFrame.setLocationRelativeTo(null);

                    createChartGender();
                    createChartScore();

                    //welcome đến tên user
                    mainFrame.jLabelWelcome.setText("Welcome: " + username);

                    //welcome đến tên user
                    mainFrame.jLabelRole.setText("Role: " + user.getPermissionName());

                    //count số student
                    StudentDAO studentDAO = new StudentDAO();
                    int totalStudents = studentDAO.count();
                    mainFrame.jLabelCountStudent.setText("" + totalStudents);

                    //count số class
                    ClassesDAO classesDAO = new ClassesDAO();
                    int totalClasses = classesDAO.count();
                    mainFrame.jLabelCountClass.setText("" + totalClasses);

                    //count số teacher
                    TeacherDAO teacherDAO = new TeacherDAO();
                    int totalTeacher = teacherDAO.count();
                    mainFrame.jLabelCountTeacher.setText("" + totalTeacher);

                    permission();

                    flag = true;

                    this.dispose();
                }
            }

            if (!flag) {
                if (pass.isEmpty() || jPasswordSI.getForeground().equals(new Color(153, 153, 153))) {
                    JOptionPane.showMessageDialog(rootPane, "Enter Password, please!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                } else if (!pass.isEmpty() || jPasswordSI.getForeground().equals(Color.WHITE)) {
                    JOptionPane.showMessageDialog(rootPane, "Incorrect Username or Password!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jButtonSI2MouseClicked

    private void jButtonSI2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSI2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSI2MouseReleased

    private void jPasswordSIFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordSIFocusGained
        // TODO add your handling code here:
        String pass = String.valueOf(jPasswordSI.getPassword());
        if (pass.equals("password")) {
            jPasswordSI.setText("");
            jPasswordSI.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jPasswordSIFocusGained

    private void jPasswordSIFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordSIFocusLost
        // TODO add your handling code here:
        String pass = String.valueOf(jPasswordSI.getPassword());
        if (pass.isEmpty()) {
            jPasswordSI.setText("password");
            jPasswordSI.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jPasswordSIFocusLost

    private void jLabelEyeSIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEyeSIMouseClicked
        // TODO add your handling code here:
        if (jPasswordSI.echoCharIsSet()) {
            jPasswordSI.setEchoChar((char) 0);
            jLabelEyeSI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes1.png")));
        } else {
            jPasswordSI.setEchoChar('*');
            jLabelEyeSI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));
        }
    }//GEN-LAST:event_jLabelEyeSIMouseClicked

    private void jPanelSIformMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSIformMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelSIformMouseDragged

    private void jPanelSIformMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSIformMouseMoved
        // TODO add your handling code here:
        jButtonSI2.setForeground(new Color(255, 255, 255));
        jButtonSI2.setBackground(new Color(102, 102, 102));

        jPopupMenuSU.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        jMenuItemT.setBackground(Color.WHITE);
        jMenuItemT.setForeground(new Color(102, 102, 102));
        jMenuItemS.setBackground(Color.WHITE);
        jMenuItemS.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_jPanelSIformMouseMoved

    private void jPanelSIformMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSIformMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelSIformMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jButtonSI;
    public static javax.swing.JLabel jButtonSI2;
    private javax.swing.JLabel jButtonSU;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public static javax.swing.JLabel jLabelExit;
    public static javax.swing.JLabel jLabelEyeSI;
    private javax.swing.JLabel jLabelLine;
    private javax.swing.JLabel jLabelPass;
    private javax.swing.JLabel jLabelSI;
    private javax.swing.JLabel jLabelUS;
    public static javax.swing.JMenuItem jMenuItemS;
    public static javax.swing.JMenuItem jMenuItemT;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelSI;
    public static javax.swing.JPanel jPanelSISU;
    private javax.swing.JPanel jPanelcontent;
    public static javax.swing.JPasswordField jPasswordSI;
    protected static javax.swing.JPopupMenu jPopupMenuSU;
    public static javax.swing.JTextField jTextFieldSI;
    // End of variables declaration//GEN-END:variables
}
