/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.student;

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
public class StudentDAO implements StudentManager<Student> {

    private Connection conn;

    public StudentDAO() {
        conn = BConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection succeed!");
        } else {
            System.out.println("Connection failed!!!");
        }
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try {
            String sql = "{CALL readAllStudent}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idStudent = rs.getInt("idStudent");
                String rollNumber = rs.getString("rollNumber");
                int idClass = rs.getInt("idClass");
                String className = rs.getNString("className");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String gender = rs.getString("gender");
                String birthDate = rs.getString("birthDate");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getNString("address");

                Student student = new Student(idStudent, idClass, className, rollNumber, firstName, lastName, gender, birthDate, phone, email, address);
                students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return students;
    }

    public List<Student> getAllStudentTemp() {
        List<Student> students = new ArrayList<>();
        try {
            String sql = "{CALL readAllStudentTemp}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idStudent = rs.getInt("idStudent");
                String rollNumber = rs.getString("rollNumber");
                String className = rs.getNString("className");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String gender = rs.getString("gender");
                String birthDate = rs.getString("birthDate");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getNString("address");

                Student student = new Student(idStudent, className, rollNumber, firstName, lastName, gender, birthDate, phone, email, address);
                students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return students;
    }

    @Override
    public boolean save(Student student) {
        try {
            String sql = "{CALL insertStudent(?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, student.getIdClass());
            cs.setString(2, student.getRollNumber());
            cs.setNString(3, student.getFirstName());
            cs.setNString(4, student.getLastName());
            cs.setByte(5, student.getGender());
            cs.setString(6, student.getBirthDate());
            cs.setString(7, student.getPhone());
            cs.setString(8, student.getEmail());
            cs.setNString(9, student.getAddress());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            System.out.println("not insert to tblStudent");
            return false;
        }
    }

    public boolean saveStudentTemp(Student student) {
        try {
            String sql = "{CALL insertStudentTemp(?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, student.getClassName());
            cs.setString(2, student.getRollNumber());
            cs.setNString(3, student.getFirstName());
            cs.setNString(4, student.getLastName());
            cs.setByte(5, student.getGender());
            cs.setString(6, student.getBirthDate());
            cs.setString(7, student.getPhone());
            cs.setString(8, student.getEmail());
            cs.setNString(9, student.getAddress());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Student student) {
        try {
            String sql = "{CALL updateStudent(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, student.getIdStudent());
            cs.setInt(2, student.getIdClass());
            cs.setString(3, student.getRollNumber());
            cs.setNString(4, student.getFirstName());
            cs.setNString(5, student.getLastName());
            cs.setByte(6, student.getGender());
            cs.setString(7, student.getBirthDate());
            cs.setString(8, student.getPhone());
            cs.setString(9, student.getEmail());
            cs.setNString(10, student.getAddress());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        try {
            String sql = "DELETE FROM tblStudent WHERE rollNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteByIDStudent(String s) {
        try {
            String sql = "DELETE FROM studentTemp WHERE idStudent = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteByIdClass(String s) {
        try {
            String sql = "DELETE FROM tblStudent WHERE idClass = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Student> search(String s) {
        List<Student> studentsSearch = new ArrayList<>();
        String sql = null;

        try {
            if (JPStudentManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Roll number")) {
                sql = "{CALL searchStudentByRollN(?)}";
            } else if (JPStudentManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Name")) {
                sql = "{CALL searchStudentByName(?)}";
            } else if (JPStudentManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Class")) {
                sql = "{CALL searchStudentByClass(?)}";
            } else if (JPStudentManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Phone")) {
                sql = "{CALL searchStudentByPhone(?)}";
            } else if (JPStudentManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Email")) {
                sql = "{CALL searchStudentByEmail(?)}";
            } else if (JPStudentManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Address")) {
                sql = "{CALL searchStudentByAddress(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String rollNumber = rs.getString("rollNumber");
                String className = rs.getNString("className");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String gender = rs.getString("gender");
                String birthDate = rs.getString("birthDate");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getNString("address");

                Student student = new Student(className, rollNumber, firstName, lastName, gender, birthDate, phone, email, address);
                studentsSearch.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return studentsSearch;
    }

    public List<Student> searchTemp(String s) {
        List<Student> studentsSearch = new ArrayList<>();
        String sql = null;

        try {
            if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Roll number")) {
                sql = "{CALL searchStudentTempByRollN(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Name")) {
                sql = "{CALL searchStudentTempByName(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Class")) {
                sql = "{CALL searchStudentTempByClass(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Phone")) {
                sql = "{CALL searchStudentTempByPhone(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Email")) {
                sql = "{CALL searchStudentTempByEmail(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Address")) {
                sql = "{CALL searchStudentTempByAddress(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String rollNumber = rs.getString("rollNumber");
                String className = rs.getNString("className");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String gender = rs.getString("gender");
                String birthDate = rs.getString("birthDate");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getNString("address");

                Student student = new Student(className, rollNumber, firstName, lastName, gender, birthDate, phone, email, address);
                studentsSearch.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return studentsSearch;
    }

    @Override
    public int count() {
        try {
            int total = 0;

            String sql = "SELECT COUNT(DISTINCT rollNumber) AS totalStudent FROM tblStudent";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int countStudentByMale(int male) {
        try {
            int total = 0;

            String sql = "SELECT COUNT(DISTINCT rollNumber) AS totalMale FROM tblStudent WHERE gender = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, male);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int countStudentByFemale(int female) {
        try {
            int total = 0;

            String sql = "SELECT COUNT(DISTINCT rollNumber) AS totalMale FROM tblStudent WHERE gender = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, female);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
