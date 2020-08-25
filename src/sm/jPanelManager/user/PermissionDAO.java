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
 * @author Ngoc Tuan
 */
public class PermissionDAO implements StudentManager<Permission> {

    private Connection conn;

    public PermissionDAO() {
        conn = BConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection succeed!");
        } else {
            System.out.println("Connection failed!!!");
        }
    }

    @Override
    public List<Permission> getAll() {
        List<Permission> permissions = new ArrayList<>();
        try {
            String sql = "{CALL getAllPerDetail}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idPermissionDetail = rs.getInt("idPermissionDetail");
                int idPermission = rs.getInt("idPermission");
                String permissionName = rs.getString("permissionName");
                String actionName = rs.getString("actionName");
                String checkAction = rs.getString("checkAction");

                Permission permission = new Permission(idPermissionDetail, idPermission, permissionName, actionName, checkAction);
                permissions.add(permission);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return permissions;
    }

    public List<Permission> getAllPer() {
        List<Permission> permissions = new ArrayList<>();
        try {
            String sql = "{CALL getAllPer}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idPermission = rs.getInt("idPermission");
                String permissionName = rs.getString("permissionName");

                Permission permission = new Permission(idPermission, permissionName);
                permissions.add(permission);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return permissions;
    }

    @Override
    public boolean save(Permission permission) {
        try {
            String sql = "{CALL insertPermission(?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, permission.getPermissionName());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean savePerDetail(Permission permissionDetail) {
        try {
            String sql = "{CALL insertPermissionDetail(?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, permissionDetail.getIdPermission());
            cs.setString(2, permissionDetail.getActionName());
            cs.setByte(3, permissionDetail.getCheckAction());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updatePerDetail(Permission permissionDetail) {
        try {
            String sql = "{CALL updatePermissionDetail(?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, permissionDetail.getIdPermissionDetail());
            cs.setInt(2, permissionDetail.getIdPermission());
            cs.setString(3, permissionDetail.getActionName());
            cs.setByte(4, permissionDetail.getCheckAction());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Permission permission) {
        try {
            String sql = "{CALL updatePermission(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, permission.getIdPermission());
            cs.setString(2, permission.getPermissionName());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        try {
            String sql = "DELETE FROM tblPermission WHERE idPermission = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Permission> search(String s) {
        List<Permission> permissions = new ArrayList<>();
        String sql = null;

        try {
            if (JPPerManager.jComboBoxSearch.getSelectedItem().toString().equals("Permission name")) {
                sql = "{CALL searchPerDetailByPer(?)}";
            } else if (JPPerManager.jComboBoxSearch.getSelectedItem().toString().equals("Action name")) {
                sql = "{CALL searchPerDetailByActionN(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, s);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idPermissionDetail = rs.getInt("idPermissionDetail");
                int idPermission = rs.getInt("idPermission");
                String permissionName = rs.getString("permissionName");
                String actionName = rs.getString("actionName");
                String checkAction = rs.getString("checkAction");

                Permission permission = new Permission(idPermissionDetail, idPermission, permissionName, actionName, checkAction);
                permissions.add(permission);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return permissions;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int idPerMax() {
        try {
            int total = 0;

            String sql = "SELECT TOP 1 idPermission FROM tblPermission order by idPermission desc";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(PermissionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
