/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.subject;

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
public class SubjectDAO implements StudentManager<Subject> {

    private Connection conn;

    public SubjectDAO() {
        conn = BConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection succeed!");
        } else {
            System.out.println("Connection failed!!!");
        }
    }

    @Override
    public List<Subject> getAll() {
        List<Subject> subjects = new ArrayList<>();
        try {
            String sql = "{CALL getAllSubject}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idSubject = rs.getInt("idSubject");
                String subjectName = rs.getNString("subjectName");
                float fee = rs.getFloat("fee");

                Subject subject = new Subject(idSubject, subjectName, fee);
                subjects.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return subjects;
    }

    @Override
    public boolean save(Subject subject) {
        try {
            String sql = "{CALL insertSubject(?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, subject.getSubjectName());
            cs.setFloat(2, subject.getFee());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Subject subject) {
        try {
            String sql = "{CALL updateSubject(?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, subject.getIdSubject());
            cs.setNString(2, subject.getSubjectName());
            cs.setFloat(3, subject.getFee());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        try {
            String sql = "DELETE FROM tblSubject WHERE idSubject = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setNString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Subject> search(String s) {
        List<Subject> subjects = new ArrayList<>();
        String sql = null;

        try {
            if (JPSubjectManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Subject name")) {
                sql = "{CALL searchSubjectByName(?)}";
            } else if (JPSubjectManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Fee")) {
                sql = "{CALL searchSubjectByFee(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idSubject = rs.getInt("idSubject");
                String subjectName = rs.getNString("subjectName");
                float fee = rs.getFloat("fee");

                Subject subject = new Subject(idSubject, subjectName, fee);
                subjects.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return subjects;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
