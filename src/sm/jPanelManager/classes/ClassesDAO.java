/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.classes;

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
import sm.jPanelManager.recycle.JPRecycle;

/**
 *
 * @author Ngoc Tuan
 */
public class ClassesDAO implements StudentManager<Classes> {

    private Connection conn;

    public ClassesDAO() {
        conn = BConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection succeed!");
        } else {
            System.out.println("Connection failed!!!");
        }
    }

    @Override
    public List<Classes> getAll() {
        List<Classes> listClasses = new ArrayList<>();

        try {
            String sql = "{CALL readAllClasses}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idClass = rs.getInt("idClass");
                String className = rs.getNString("className");
                int numberOfStudents = rs.getInt("numberOfStudents");

                Classes classes = new Classes(idClass, className, numberOfStudents);
                listClasses.add(classes);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listClasses;
    }

    public List<Classes> getAllTempClass() {
        List<Classes> listClasses = new ArrayList<>();

        try {
            String sql = "{CALL readAllClassTemp}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idClass = rs.getInt("idClass");
                String className = rs.getNString("className");
                int numberOfStudents = rs.getInt("numberOfStudents");

                Classes classes = new Classes(idClass, className, numberOfStudents);
                listClasses.add(classes);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listClasses;
    }

    @Override
    public boolean save(Classes classes) {
        try {
            String sql = "{CALL insertClass(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, classes.getClassName());
            cs.setInt(2, classes.getNumberOfStudents());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean saveTempClass(Classes classes) {
        try {
            String sql = "{CALL insertClassTemp(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, classes.getClassName());
            cs.setInt(2, classes.getNumberOfStudents());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Classes classes) {
        try {
            String sql = "{CALL updateClass(?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, classes.getIdClass());
            cs.setNString(2, classes.getClassName());
            cs.setInt(3, classes.getNumberOfStudents());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        try {
            String sql = "DELETE FROM tblClasses WHERE idClass = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteTempClass(String s) {
        try {
            String sql = "DELETE FROM classesTemp WHERE idClass = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Classes> search(String s) {
        List<Classes> listClasses = new ArrayList<>();
        String sql = null;

        try {
            if (JPClassManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Class name")) {
                sql = "{CALL searchClassByName(?)}";
            } else if (JPClassManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Number of students")) {
                sql = "{CALL searchClassByNOStudents(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idClass = rs.getInt("idClass");
                String className = rs.getNString("className");
                int numberOfStudents = rs.getInt("numberOfStudents");

                Classes classes = new Classes(idClass, className, numberOfStudents);
                listClasses.add(classes);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listClasses;
    }

    public List<Classes> searchTempClass(String s) {
        List<Classes> listClasses = new ArrayList<>();
        String sql = null;

        try {
            if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Class name")) {
                sql = "{CALL searchTempClassByName(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Number of students")) {
                sql = "{CALL searchTempClassByNOStudents(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String className = rs.getNString("className");
                int numberOfStudents = rs.getInt("numberOfStudents");

                Classes classes = new Classes(className, numberOfStudents);
                listClasses.add(classes);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listClasses;
    }

    @Override
    public int count() {
        try {
            int total = 0;

            String sql = "SELECT COUNT(*) AS totalClass FROM tblClasses";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(ClassesDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
