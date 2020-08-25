/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.login;

import sm.jPanelManager.user.User;
import sm.jPanelManager.user.UserDAO;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Ngoc Tuan
 */
public class JPanelSU extends javax.swing.JPanel {

    private Point initialClick;
    private JFrame parent;
    private UserDAO userDAO = new UserDAO();

    /**
     * Creates new form JPanelSU
     */
    public JPanelSU(final JFrame parent) {
        initComponents();

        setBounds(0, 0, 386, 395);
        setBackground(Color.WHITE);

        this.parent = parent;

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                // get location of Window
                int thisX = parent.getLocation().x;
                int thisY = parent.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                parent.setLocation(X, Y);

            }
        });
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
        jLabelSU = new javax.swing.JLabel();
        jLabelUS1 = new javax.swing.JLabel();
        jButtonSU = new javax.swing.JLabel();
        jTextFieldSU = new javax.swing.JTextField();
        jLabelPass1 = new javax.swing.JLabel();
        jLabelCPass2 = new javax.swing.JLabel();
        jLabelLine = new javax.swing.JLabel();
        jPasswordSU = new javax.swing.JPasswordField();
        jPasswordSU1 = new javax.swing.JPasswordField();
        jLabelEyeSU = new javax.swing.JLabel();
        jLabelEyeSU1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setOpaque(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelSU.setBackground(new java.awt.Color(255, 255, 255));
        jLabelSU.setFont(new java.awt.Font("Segoe UI", 0, 27)); // NOI18N
        jLabelSU.setForeground(new java.awt.Color(102, 102, 102));
        jLabelSU.setText("Sign Up For Student");
        add(jLabelSU, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 240, -1));

        jLabelUS1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/username.png"))); // NOI18N
        add(jLabelUS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        jButtonSU.setBackground(new java.awt.Color(102, 102, 102));
        jButtonSU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSU.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSU.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;SIGN UP</div></html> ");
        jButtonSU.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
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
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonSUMouseReleased(evt);
            }
        });
        add(jButtonSU, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 143, 39));

        jTextFieldSU.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextFieldSU.setForeground(new java.awt.Color(153, 153, 153));
        jTextFieldSU.setText("username");
        jTextFieldSU.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldSUFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldSUFocusLost(evt);
            }
        });
        jTextFieldSU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSUActionPerformed(evt);
            }
        });
        add(jTextFieldSU, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 233, 32));

        jLabelPass1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/pass.png"))); // NOI18N
        add(jLabelPass1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, -1));

        jLabelCPass2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/pass.png"))); // NOI18N
        add(jLabelCPass2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));

        jLabelLine.setBackground(new java.awt.Color(255, 255, 255));
        jLabelLine.setForeground(new java.awt.Color(255, 255, 255));
        jLabelLine.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabelLine.setOpaque(true);
        add(jLabelLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 240, -1));

        jPasswordSU.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPasswordSU.setForeground(new java.awt.Color(153, 153, 153));
        jPasswordSU.setText("password");
        jPasswordSU.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordSUFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordSUFocusLost(evt);
            }
        });
        add(jPasswordSU, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 233, 32));

        jPasswordSU1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPasswordSU1.setForeground(new java.awt.Color(153, 153, 153));
        jPasswordSU1.setText("password");
        jPasswordSU1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPasswordSU1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPasswordSU1FocusLost(evt);
            }
        });
        add(jPasswordSU1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 233, 32));

        jLabelEyeSU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png"))); // NOI18N
        jLabelEyeSU.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelEyeSU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEyeSUMouseClicked(evt);
            }
        });
        add(jLabelEyeSU, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, -1, 30));

        jLabelEyeSU1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png"))); // NOI18N
        jLabelEyeSU1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelEyeSU1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelEyeSU1MouseClicked(evt);
            }
        });
        add(jLabelEyeSU1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, -1, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSUMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSUMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSUMouseDragged

    private void jButtonSUMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSUMouseMoved
        // TODO add your handling code here:
        jButtonSU.setBackground(new Color(255, 255, 255));
        jButtonSU.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_jButtonSUMouseMoved

    private void jButtonSUMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jButtonSUMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSUMouseWheelMoved


    private void jButtonSUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSUMouseClicked
        // TODO add your handling code here:
        String username = jTextFieldSU.getText();
        String pass = String.valueOf(jPasswordSU.getPassword());
        String cfpass = String.valueOf(jPasswordSU1.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Username, please!", "Notification", JOptionPane.INFORMATION_MESSAGE);
        } else {
            boolean flag = true;

            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.getAll();
            for (User sameUser : users) {
                if (username.equals(sameUser.getUsername())) {
                    JOptionPane.showMessageDialog(this, "This Username already exist!", "Warning", JOptionPane.ERROR_MESSAGE);
                    flag = false;
                }
            }
            if (flag) {
                if (pass.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter Password, please!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                } else if (cfpass.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter a Confirm Password, please!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                } else if (!pass.equals(cfpass)) {
                    JOptionPane.showMessageDialog(this, "Incorrect Confirm Password or Password!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                } else {
                    User user = new User(
                            jTextFieldSU.getText(),
                            String.valueOf(jPasswordSU.getPassword())
                    );
                    boolean rowUser = userDAO.save(user);

                    //insert tblUserPermission
                    int idUser = userDAO.idUserMax();

                    User userPermission = new User(
                            Login.getIdPermission(),
                            idUser
                    );

                    boolean rowUserPermission = userDAO.saveUserPer(userPermission);

                    if (rowUser && rowUserPermission) {
                        JOptionPane.showMessageDialog(null, "Register successfully!");

                        //show dialog, check user click ok thì chuyển sang form login
                        int ok = JOptionPane.showConfirmDialog(this, "Would You Like To Comeback Login?", "Notification", JOptionPane.OK_CANCEL_OPTION);
                        if (ok == JOptionPane.OK_OPTION) {
                            Login login = new Login();
                            login.jTextFieldSI.setText(jTextFieldSU.getText());//lấy username vừa đăng kí bên register sang login
                            login.jTextFieldSI.setForeground(Color.BLACK);
                            login.setVisible(true);
                            login.setLocationRelativeTo(null);
                            this.setVisible(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Register failed!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_jButtonSUMouseClicked

    private void jButtonSUMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSUMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSUMouseReleased

    private void jTextFieldSUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSUActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextFieldSUActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        jButtonSU.setForeground(new Color(255, 255, 255));
        jButtonSU.setBackground(new Color(102, 102, 102));

        Login.jPopupMenuSU.setBorder(BorderFactory.createLineBorder(new Color(102, 102, 102)));
        Login.jMenuItemT.setBackground(Color.WHITE);
        Login.jMenuItemT.setForeground(new Color(102, 102, 102));
        Login.jMenuItemS.setBackground(Color.WHITE);
        Login.jMenuItemS.setForeground(new Color(102, 102, 102));
    }//GEN-LAST:event_formMouseMoved

    private void jLabelEyeSUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEyeSUMouseClicked
        // TODO add your handling code here:
        if (jPasswordSU.echoCharIsSet()) {
            jPasswordSU.setEchoChar((char) 0);
            jLabelEyeSU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes1.png")));
        } else {
            jPasswordSU.setEchoChar('*');
            jLabelEyeSU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));
        }
    }//GEN-LAST:event_jLabelEyeSUMouseClicked

    private void jLabelEyeSU1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelEyeSU1MouseClicked
        // TODO add your handling code here:
        if (jPasswordSU1.echoCharIsSet()) {
            jPasswordSU1.setEchoChar((char) 0);
            jLabelEyeSU1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes1.png")));
        } else {
            jPasswordSU1.setEchoChar('*');
            jLabelEyeSU1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/eyes.png")));
        }
    }//GEN-LAST:event_jLabelEyeSU1MouseClicked

    private void jTextFieldSUFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSUFocusGained
        // TODO add your handling code here:
        //xóa text nếu jtextfieldUsername on focus gained
        if (jTextFieldSU.getText().equals("username")) {
            jTextFieldSU.setText("");
            jTextFieldSU.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextFieldSUFocusGained

    private void jTextFieldSUFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSUFocusLost
        // TODO add your handling code here:
        if (jTextFieldSU.getText().isEmpty()) {
            jTextFieldSU.setText("username");
            jTextFieldSU.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextFieldSUFocusLost

    private void jPasswordSUFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordSUFocusGained
        // TODO add your handling code here:
        String pass = String.valueOf(jPasswordSU.getPassword());
        if (pass.equals("password")) {
            jPasswordSU.setText("");
            jPasswordSU.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jPasswordSUFocusGained

    private void jPasswordSUFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordSUFocusLost
        // TODO add your handling code here:
        String pass = String.valueOf(jPasswordSU.getPassword());
        if (pass.isEmpty()) {
            jPasswordSU.setText("password");
            jPasswordSU.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jPasswordSUFocusLost

    private void jPasswordSU1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordSU1FocusGained
        // TODO add your handling code here:
        String pass = String.valueOf(jPasswordSU1.getPassword());
        if (pass.equals("password")) {
            jPasswordSU1.setText("");
            jPasswordSU1.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jPasswordSU1FocusGained

    private void jPasswordSU1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordSU1FocusLost
        // TODO add your handling code here:
        String pass = String.valueOf(jPasswordSU1.getPassword());
        if (pass.isEmpty()) {
            jPasswordSU1.setText("password");
            jPasswordSU1.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jPasswordSU1FocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jButtonSU;
    private javax.swing.JLabel jLabelCPass2;
    public static javax.swing.JLabel jLabelEyeSU;
    public static javax.swing.JLabel jLabelEyeSU1;
    private javax.swing.JLabel jLabelLine;
    private javax.swing.JLabel jLabelPass1;
    public static javax.swing.JLabel jLabelSU;
    private javax.swing.JLabel jLabelUS1;
    public static javax.swing.JPasswordField jPasswordSU;
    public static javax.swing.JPasswordField jPasswordSU1;
    private javax.swing.JTextField jTextFieldSU;
    // End of variables declaration//GEN-END:variables
}
