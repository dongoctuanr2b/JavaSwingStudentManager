/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.timetable;

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
public class TimetableDAO implements StudentManager<TimeTable> {

    private Connection conn;

    public TimetableDAO() {
        conn = BConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection succeed!");
        } else {
            System.out.println("Connection failed!!!");
        }
    }

    @Override
    public List<TimeTable> getAll() {
        List<TimeTable> listTimeTable = new ArrayList<>();
        try {
            String sql = "{CALL readAllTimetable}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idTimetable = rs.getInt("idTimetable");
                String className = rs.getNString("className");
                String subjectName = rs.getNString("subjectName");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String date = rs.getString("date");
                String room = rs.getString("room");
                String teacher = rs.getString("teacher");
                String desc = rs.getString("description");

                TimeTable timeTable = new TimeTable(idTimetable, className, subjectName, startTime, endTime, date, room, teacher, desc);
                listTimeTable.add(timeTable);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTimeTable;
    }

    public List<TimeTable> getAllTemp() {
        List<TimeTable> listTimeTable = new ArrayList<>();
        try {
            String sql = "{CALL readAllTimetableTemp}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String className = rs.getNString("className");
                String subjectName = rs.getNString("subjectName");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String date = rs.getString("date");
                String room = rs.getString("roomName");
                String teacher = rs.getString("firstName");
                String desc = rs.getString("description");

                TimeTable timeTable = new TimeTable(className, subjectName, startTime, endTime, date, room, teacher, desc);
                listTimeTable.add(timeTable);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTimeTable;
    }

    @Override
    public boolean save(TimeTable timeTable) {
        try {
            String sql = "{CALL insertTimetable(?,?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, timeTable.getIdClass());
            cs.setInt(2, timeTable.getIdSubject());
            cs.setString(3, timeTable.getStartTime());
            cs.setString(4, timeTable.getEndTime());
            cs.setString(5, timeTable.getDate());
            cs.setInt(6, timeTable.getIdRoom());
            cs.setInt(7, timeTable.getIdTeacher());
            cs.setString(8, timeTable.getDesc());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean saveTimetableTemp(TimeTable timeTable) {
        try {
            String sql = "{CALL insertTimetableTemp(?,?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, timeTable.getClassName());
            cs.setString(2, timeTable.getSubject());
            cs.setString(3, timeTable.getStartTime());
            cs.setString(4, timeTable.getEndTime());
            cs.setString(5, timeTable.getDate());
            cs.setString(6, timeTable.getRoom());
            cs.setString(7, timeTable.getTeacher());
            cs.setString(8, timeTable.getDesc());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(TimeTable timeTable) {
        try {
            String sql = "{CALL updateTimetable(?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, timeTable.getIdTT());
            cs.setInt(2, timeTable.getIdClass());
            cs.setInt(3, timeTable.getIdSubject());
            cs.setString(4, timeTable.getStartTime());
            cs.setString(5, timeTable.getEndTime());
            cs.setString(6, timeTable.getDate());
            cs.setInt(7, timeTable.getIdRoom());
            cs.setInt(8, timeTable.getIdTeacher());
            cs.setString(9, timeTable.getDesc());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        try {
            String sql = "DELETE FROM tblTimetable WHERE idTimetable = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StudentManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(String className, String subjectName) {
        try {
            String sql = "DELETE FROM tblTimetableTemp WHERE className = ? and subjectName = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, className);
            ps.setString(2, subjectName);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(StudentManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<TimeTable> search(String s) {
        List<TimeTable> listTimeTable = new ArrayList<>();
        String sql = null;

        try {
            if (JPTimetableManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Class")) {
                sql = "{CALL searchTimetableByClass(?)}";
            } else if (JPTimetableManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Teacher")) {
                sql = "{CALL searchTimetableByTeacher(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idTimetable = rs.getInt("idTimetable");
                String className = rs.getNString("className");
                String subjectName = rs.getNString("subjectName");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String date = rs.getString("date");
                String room = rs.getString("roomName");
                String teacher = rs.getString("teacher");
                String desc = rs.getString("description");

                TimeTable timeTable = new TimeTable(idTimetable, className, subjectName, startTime, endTime, date, room, teacher, desc);
                listTimeTable.add(timeTable);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTimeTable;
    }

    public List<TimeTable> searchTemp(String s) {
        List<TimeTable> listTimeTable = new ArrayList<>();
        String sql = null;

        try {
            if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Class")) {
                sql = "{CALL searchTimetableByClassTemp(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Teacher")) {
                sql = "{CALL searchTimetableByTeacherTemp(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String className = rs.getNString("className");
                String subjectName = rs.getNString("subjectName");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String date = rs.getString("date");
                String room = rs.getString("roomName");
                String teacher = rs.getString("firstName");
                String desc = rs.getString("description");

                TimeTable timeTable = new TimeTable(className, subjectName, startTime, endTime, date, room, teacher, desc);
                listTimeTable.add(timeTable);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimetableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listTimeTable;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
