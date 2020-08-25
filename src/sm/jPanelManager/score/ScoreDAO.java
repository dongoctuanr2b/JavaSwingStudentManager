/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.score;

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
public class ScoreDAO implements StudentManager<Score> {

    private Connection conn;

    public ScoreDAO() {
        conn = BConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection succeed!");
        } else {
            System.out.println("Connection failed!!!");
        }
    }

    @Override
    public List<Score> getAll() {
        List<Score> scores = new ArrayList<>();

        try {
            String sql = "{CALL readAllScore}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idScore = rs.getInt("idScore");
                String rollNumber = rs.getString("rollNumber");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String subjectName = rs.getNString("subjectName");
                String className = rs.getNString("className");
                float scoreSQL = rs.getFloat("score");
                String typeOfScore = rs.getString("typeOfScore");
                int numberOfExams = rs.getInt("numberOfExams");
                String description = rs.getNString("description");

                Score score = new Score(idScore, rollNumber, firstName, lastName, subjectName, className, scoreSQL, typeOfScore, numberOfExams, description);
                scores.add(score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return scores;
    }

    public List<Score> getAllScoreTemp() {
        List<Score> scores = new ArrayList<>();

        try {
            String sql = "{CALL readAllScoreTemp}";
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idScore = rs.getInt("idScore");
                String rollNumber = rs.getString("rollNumber");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String subjectName = rs.getNString("subjectName");
                String className = rs.getNString("className");
                float scoreSQL = rs.getFloat("score");
                String typeOfScore = rs.getString("typeOfScore");
                int numberOfExams = rs.getInt("numberOfExams");
                String description = rs.getNString("description");

                Score score = new Score(idScore, rollNumber, firstName, lastName, subjectName, className, scoreSQL, typeOfScore, numberOfExams, description);
                scores.add(score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return scores;
    }

    @Override
    public boolean save(Score score) {
        try {
            String sql = "{CALL insertScore(?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, score.getIdStudent());
            cs.setInt(2, score.getIdSubject());
            cs.setInt(3, score.getIdClass());
            cs.setFloat(4, score.getScore());
            cs.setString(5, score.getTypeOfScore());
            cs.setInt(6, score.getNumberOfExams());
            cs.setNString(7, score.getDesc());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            System.out.println("not insert to tblScore");
            return false;
        }
    }

    public boolean saveScoreTemp(Score score) {
        try {
            String sql = "{CALL insertScoreTemp(?,?,?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, score.getRollNumber());
            cs.setNString(2, score.getFirstName());
            cs.setNString(3, score.getLastName());
            cs.setNString(4, score.getSubjectName());
            cs.setNString(5, score.getClassName());
            cs.setFloat(6, score.getScore());
            cs.setString(7, score.getTypeOfScore());
            cs.setInt(8, score.getNumberOfExams());
            cs.setNString(9, score.getDesc());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Score score) {
        try {
            String sql = "{CALL updateScore(?,?,?,?,?,?,?,?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, score.getIdScore());
            cs.setInt(2, score.getIdStudent());
            cs.setInt(3, score.getIdSubject());
            cs.setInt(4, score.getIdClass());
            cs.setFloat(5, score.getScore());
            cs.setString(6, score.getTypeOfScore());
            cs.setInt(7, score.getNumberOfExams());
            cs.setNString(8, score.getDesc());

            int row = cs.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        try {
            String sql = "DELETE FROM tblScore WHERE idScore = ?";
            PreparedStatement ps = conn.prepareCall(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteByID(String s) {
        try {
            String sql = "DELETE FROM scoreTemp WHERE idScore = ?";
            PreparedStatement ps = conn.prepareCall(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteByRollN(String s) {
        try {
            String sql = "DELETE FROM tblScore WHERE idStudent = ?";
            PreparedStatement ps = conn.prepareCall(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteByClass(String s) {
        try {
            String sql = "DELETE FROM tblScore WHERE idClass = ?";
            PreparedStatement ps = conn.prepareCall(sql);
            ps.setString(1, s);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Score> search(String s) {
        List<Score> scores = new ArrayList<>();
        String sql = null;

        try {
            if (JPScoreManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Roll number")) {
                sql = "{CALL searchScoreByRollN(?)}";
            } else if (JPScoreManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Name")) {
                sql = "{CALL searchScoreByName(?)}";
            } else if (JPScoreManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Description")) {
                sql = "{CALL searchScoreByDesc(?)}";
            } else if (JPScoreManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Score")) {
                sql = "{CALL searchScoreByScore(?)}";
            } else if (JPScoreManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Subject name")) {
                sql = "{CALL searchScoreBySubjectN(?)}";
            } else if (JPScoreManager.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Class name")) {
                sql = "{CALL searchScoreByClassN(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idScore = rs.getInt("idScore");
                String rollNumber = rs.getString("rollNumber");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String subjectName = rs.getNString("subjectName");
                String className = rs.getNString("className");
                float scoreSQL = rs.getFloat("score");
                String typeOfScore = rs.getString("typeOfScore");
                int numberOfExams = rs.getInt("numberOfExams");
                String description = rs.getNString("description");

                Score score = new Score(idScore, rollNumber, firstName, lastName, subjectName, className, scoreSQL, typeOfScore, numberOfExams, description);
                scores.add(score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return scores;
    }

    public List<Score> searchScoreTemp(String s) {
        List<Score> scores = new ArrayList<>();
        String sql = null;

        try {
            if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Roll number")) {
                sql = "{CALL searchScoreTempByRollN(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Name")) {
                sql = "{CALL searchScoreTempByName(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Description")) {
                sql = "{CALL searchScoreTempByDesc(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Score")) {
                sql = "{CALL searchScoreTempByScore(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Subject name")) {
                sql = "{CALL searchScoreTempBySubjectN(?)}";
            } else if (JPRecycle.jComboBoxSearch.getSelectedItem().toString().equalsIgnoreCase("Class name")) {
                sql = "{CALL searchScoreTempByClassN(?)}";
            }

            CallableStatement cs = conn.prepareCall(sql);
            cs.setNString(1, s);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                int idScore = rs.getInt("idScore");
                String rollNumber = rs.getString("rollNumber");
                String firstName = rs.getNString("firstName");
                String lastName = rs.getNString("lastName");
                String subjectName = rs.getNString("subjectName");
                String className = rs.getNString("className");
                float scoreSQL = rs.getFloat("score");
                String typeOfScore = rs.getString("typeOfScore");
                int numberOfExams = rs.getInt("numberOfExams");
                String description = rs.getNString("description");

                Score score = new Score(idScore, rollNumber, firstName, lastName, subjectName, className, scoreSQL, typeOfScore, numberOfExams, description);
                scores.add(score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return scores;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Score> avgScoreByStudents() {
        List<Score> listScores = new ArrayList<>();
        try {
            String sql = "{CALL avgScoreByStudents}";
            CallableStatement cs = conn.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Float score = rs.getFloat("score");
                String rollNumber = rs.getString("rollNumber");

                Score scores = new Score(score, rollNumber);
                listScores.add(scores);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listScores;
    }
}
