/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.user;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sm.connection.BConnection;
import sm.interfaceSM.StudentManager;

/**
 *
 * @author admin
 */
public class UserDAO implements StudentManager<User> {

    private Connection conn;

    public UserDAO() {
        conn = BConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection succeed!");
        } else {
            System.out.println("Connection failed!!!");
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "{CALL getAllUserPer}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idUser = rs.getInt("idUser");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String permissionName = rs.getNString("permissionName");

                User user = new User(idUser, username, password, permissionName);
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    @Override
    public boolean save(User user) {
        try {
            String sql = "{CALL insertUser(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, user.getUsername());
            cs.setString(2, user.getPassword());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean saveUserPer(User userPermission) {
        try {
            String sql = "{CALL insertUserPermission(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, userPermission.getIdPermission());
            cs.setInt(2, userPermission.getIdUser());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updateUserPer(User userPermission) {
        try {
            String sql = "{CALL updateUserPer(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, userPermission.getIdUser());
            cs.setInt(2, userPermission.getIdPermission());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            String sql = "{CALL updateUser(?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, user.getIdUser());
            cs.setString(2, user.getUsername());
            cs.setString(3, user.getPassword());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        try {
            String sql = "DELETE FROM tblUser WHERE idUser = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<User> search(String s) {
        List<User> users = new ArrayList<>();
        String sql = null;

        try {
            if (JPUserManager.jComboBoxSearch.getSelectedItem().toString().equals("Permission")) {
                sql = "{CALL searchUserPerByPer(?)}";
            } else if (JPUserManager.jComboBoxSearch.getSelectedItem().toString().equals("Username")) {
                sql = "{CALL searchUserPerByUserN(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, s);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idUser = rs.getInt("idUser");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String permissionName = rs.getString("permissionName");

                User user = new User(idUser, username, password, permissionName);
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    @Override
    public int count() {
        try {
            int total = 0;

            String sql = "SELECT COUNT(*) AS totalUser FROM tblUser";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int idUserMax() {
        try {
            int total = 0;

            String sql = "SELECT TOP 1 idUser FROM tblUser order by idUser desc";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
