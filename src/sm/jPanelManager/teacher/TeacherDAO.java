/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.teacher;

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
public class TeacherDAO implements StudentManager<Teacher> {

    private Connection conn;

    public TeacherDAO() {
        conn = BConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection succeed!");
        } else {
            System.out.println("Connection failed!!!");
        }
    }

    @Override
    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            String sql = "{CALL readAllTeacher}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idTeacher = rs.getInt("idTeacher");
                String rollNumber = rs.getString("rollNumber");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getNString("address");

                Teacher teacher = new Teacher(idTeacher, rollNumber, firstName, lastName, gender, phone, email, address);
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teachers;
    }

    @Override
    public boolean save(Teacher teacher) {
        try {
            String sql = "{CALL insertTeacher(?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, teacher.getRollNumber());
            cs.setNString(2, teacher.getFirstName());
            cs.setNString(3, teacher.getLastName());
            cs.setByte(4, teacher.getGender());
            cs.setString(5, teacher.getPhone());
            cs.setString(6, teacher.getEmail());
            cs.setNString(7, teacher.getAddress());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Teacher teacher) {
        try {
            String sql = "{CALL updateTeacher(?,?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, teacher.getIdTeacher());
            cs.setString(2, teacher.getRollNumber());
            cs.setNString(3, teacher.getFirstName());
            cs.setNString(4, teacher.getLastName());
            cs.setByte(5, teacher.getGender());
            cs.setString(6, teacher.getPhone());
            cs.setString(7, teacher.getEmail());
            cs.setNString(8, teacher.getAddress());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        try {
            String sql = "DELETE FROM tblTeacher WHERE rollNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Teacher> search(String s) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = null;

        try {
            if (JPTeacherManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Roll number")) {
                sql = "{CALL searchTeacherByRollN(?)}";
            } else if (JPTeacherManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Name")) {
                sql = "{CALL searchTeacherByName(?)}";
            } else if (JPTeacherManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Phone")) {
                sql = "{CALL searchTeacherByPhone(?)}";
            } else if (JPTeacherManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Email")) {
                sql = "{CALL searchTeacherByEmail(?)}";
            } else if (JPTeacherManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Address")) {
                sql = "{CALL searchTeacherByAddress(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idTeacher = rs.getInt("idTeacher");
                String rollNumber = rs.getString("rollNumber");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getNString("address");

                Teacher teacher = new Teacher(idTeacher, rollNumber, firstName, lastName, gender, phone, email, address);
                teachers.add(teacher);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teachers;
    }

    @Override
    public int count() {
        try {
            int total = 0;

            String sql = "SELECT COUNT(*) AS totalTeacher FROM tblTeacher";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}
