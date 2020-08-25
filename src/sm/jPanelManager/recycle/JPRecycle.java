/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.recycle;

import java.awt.Color;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import sm.jPanelManager.classes.Classes;
import sm.jPanelManager.classes.ClassesDAO;
import sm.jPanelManager.classes.JPClassManager;
import sm.jPanelManager.room.Room;
import sm.jPanelManager.room.RoomDAO;
import sm.jPanelManager.score.JPScoreManager;
import sm.jPanelManager.score.Score;
import sm.jPanelManager.score.ScoreDAO;
import sm.jPanelManager.student.JPStudentManager;
import sm.jPanelManager.student.Student;
import sm.jPanelManager.student.StudentDAO;
import sm.jPanelManager.subject.Subject;
import sm.jPanelManager.subject.SubjectDAO;
import sm.jPanelManager.teacher.Teacher;
import sm.jPanelManager.teacher.TeacherDAO;
import sm.jPanelManager.timetable.TimeTable;
import sm.jPanelManager.timetable.TimetableDAO;
import sm.mainFrame.MainFrame;

/**
 *
 * @author Ngoc Tuan
 */
public class JPRecycle extends javax.swing.JPanel {

    private boolean flagGlobal = false;

    private ClassesDAO classesDAO = new ClassesDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private ScoreDAO scoreDAO = new ScoreDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private TimetableDAO timetableDAO = new TimetableDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();

    /**
     * Creates new form NewJPanel
     */
    public JPRecycle() {
        initComponents();

        setVisible(false);
        setBounds(0, 0, MainFrame.jPanelContent.getSize().width + 105, MainFrame.jPanelContent.getSize().height);

        jTableRecycle.setRowHeight(30);
        jTableRecycle.setShowGrid(true);
        jTableRecycle.setGridColor(new Color(102, 102, 102));
    }

    //Class
    private void fillDataClass() {
        List<Classes> listClasses = classesDAO.getAllTempClass();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("ID");
        dtm.addColumn("Class name");
        dtm.addColumn("Number of students");

        for (Classes classes : listClasses) {
            Vector<Object> vector = new Vector();
            vector.add(classes.getIdClass());
            vector.add(classes.getClassName());
            vector.add(classes.getNumberOfStudents());

            dtm.addRow(vector);
        }

        jTableRecycle.setModel(dtm);

        jTableRecycle.removeColumn(jTableRecycle.getColumnModel().getColumn(0));
    }

    private void deleteClass() {
        int index = jTableRecycle.getSelectedRow();
        TableModel model = jTableRecycle.getModel();

        String idClass = String.valueOf(model.getValueAt(index, 0));

        int yes = JOptionPane.showConfirmDialog(this, "Do you want to delete?", "Warning", JOptionPane.YES_NO_OPTION);
        if (yes == JOptionPane.YES_OPTION) {
            boolean row = classesDAO.deleteTempClass(idClass);

            if (row) {
                JOptionPane.showMessageDialog(this, "Delete Successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                //khi delete thành công thì show data lên bảng
                fillDataClass();
            } else {
                JOptionPane.showMessageDialog(this, "Error Delete !!!", "Notification", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchClass() {
        List<Classes> listClass = classesDAO.searchTempClass(jTextFieldSearch.getText());

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Class Name");
        dtm.addColumn("Number of students");

        for (Classes classes : listClass) {
            Vector<Object> vector = new Vector();
            vector.add(classes.getIdClass());
            vector.add(classes.getClassName());
            vector.add(classes.getNumberOfStudents());

            dtm.addRow(vector);
        }

        jTableRecycle.setModel(dtm);

        jTableRecycle.removeColumn(jTableRecycle.getColumnModel().getColumn(0));
    }

    //validate form add class
    private boolean validateClassAdd() {
        boolean flag = true;

        int index = jTableRecycle.getSelectedRow();
        TableModel model = jTableRecycle.getModel();

        String className = model.getValueAt(index, 1).toString();

        //check classname phải là duy nhất ko được trùng
        List<Classes> listClasses = classesDAO.getAll();
        for (Classes classes : listClasses) {
            if ((className.equalsIgnoreCase(classes.getClassName()))) {
                JOptionPane.showMessageDialog(this, "Class name " + classes.getClassName() + " already exist. Cannot restore!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                flag = false;
            }
        }

        //nếu className không bị trùng thì flag vẫn = true rồi check tiếp
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    private void restoreClass() {
        //Check form add classes
        if (validateClassAdd()) {
            int index = jTableRecycle.getSelectedRow();
            TableModel model = jTableRecycle.getModel();

            String className = model.getValueAt(index, 1).toString();
            int NOStudents = Integer.valueOf(model.getValueAt(index, 2).toString());

            Classes classes = new Classes(
                    className,
                    NOStudents
            );

            boolean row = classesDAO.save(classes);

            if (row) {

                JOptionPane.showMessageDialog(this, "Restore successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                //count số class
                int totalClass = classesDAO.count();
                MainFrame.jLabelCountClass.setText("" + totalClass);

                List<Classes> listClasses = classesDAO.getAll();

                DefaultTableModel dtm = new DefaultTableModel();

                dtm.addColumn("ID");
                dtm.addColumn("Class Name");
                dtm.addColumn("Number of students");

                for (Classes classes1 : listClasses) {
                    Vector<Object> vector = new Vector();
                    vector.add(classes1.getIdClass());
                    vector.add(classes1.getClassName());
                    vector.add(classes1.getNumberOfStudents());

                    dtm.addRow(vector);
                }

                JPClassManager.jTableClass.setModel(dtm);

                flagGlobal = true;
            } else {
                JOptionPane.showMessageDialog(this, "Error restore Class!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                flagGlobal = false;
            }
        }
    }

    //Student
    private void fillDataStudent() {
        List<Student> students = studentDAO.getAllStudentTemp();

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("RollNumber");
        dtm.addColumn("Class");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Gender");
        dtm.addColumn("BirthDate");
        dtm.addColumn("Phone");
        dtm.addColumn("Email");
        dtm.addColumn("Address");

        for (Student student : students) {
            Vector<Object> vector = new Vector();
            vector.add(student.getIdStudent());
            vector.add(student.getRollNumber());
            vector.add(student.getClassName());
            vector.add(student.getFirstName());
            vector.add(student.getLastName());
            vector.add(student.getGenderString());
            vector.add(student.getBirthDate());
            vector.add(student.getPhone());
            vector.add(student.getEmail());
            vector.add(student.getAddress());

            dtm.addRow(vector);
        }

        jTableRecycle.setModel(dtm);

        jTableRecycle.removeColumn(jTableRecycle.getColumnModel().getColumn(0));
    }

    private void deleteStudent() {
        int index = jTableRecycle.getSelectedRow();
        TableModel model = jTableRecycle.getModel();

        String idStudent = model.getValueAt(index, 0).toString();

        int yes = JOptionPane.showConfirmDialog(this, "Are you sure want to this student delete?", "Warning", JOptionPane.YES_NO_OPTION);
        if (yes == JOptionPane.YES_OPTION) {
            boolean row = studentDAO.deleteByIDStudent(idStudent);

            if (row) {
                JOptionPane.showMessageDialog(this, "Student Data Deleted!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                //khi delete thành công thì show data lên bảng
                fillDataStudent();
            } else {
                JOptionPane.showMessageDialog(this, "Error Delete Student!!!", "Warning", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    private void searchStudent() {
        List<Student> students = studentDAO.searchTemp(jTextFieldSearch.getText());

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("RollNumber");
        dtm.addColumn("Class");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Gender");
        dtm.addColumn("BirthDate");
        dtm.addColumn("Phone");
        dtm.addColumn("Email");
        dtm.addColumn("Address");

        for (Student student : students) {
            Vector<Object> vector = new Vector();
            vector.add(student.getIdStudent());
            vector.add(student.getRollNumber());
            vector.add(student.getClassName());
            vector.add(student.getFirstName());
            vector.add(student.getLastName());
            vector.add(student.getGenderString());
            vector.add(student.getBirthDate());
            vector.add(student.getPhone());
            vector.add(student.getEmail());
            vector.add(student.getAddress());

            dtm.addRow(vector);
        }

        jTableRecycle.setModel(dtm);

        jTableRecycle.removeColumn(jTableRecycle.getColumnModel().getColumn(0));
    }

    //validate form add class
    private boolean validateStudentAdd() {
        boolean flag = true;

        int index = jTableRecycle.getSelectedRow();
        TableModel model = jTableRecycle.getModel();

        String rollNumber = model.getValueAt(index, 1).toString();
        //check roll number phải là duy nhất ko được trùng, chuyển flag = false
        List<Student> students = studentDAO.getAll();
        for (Student student : students) {
            String getRN = student.getRollNumber();
            if (rollNumber.toLowerCase().equals(getRN.toLowerCase())) {
                JOptionPane.showMessageDialog(this, "RollNumber " + rollNumber + " already exist. Cannot restore!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                flag = false;
                break;
            }
        }

        //nếu roll number không bị trùng thì flag vẫn = true rồi check tiếp
        if (flag) {
            return true;

        } else {
            return false;
        }
    }

    //Xử lý radio button của gender
    private byte gender() {
        int index = jTableRecycle.getSelectedRow();
        TableModel model = jTableRecycle.getModel();

        if (model.getValueAt(index, 5).toString().equals("Male")) {
            return 1;
        } else if (model.getValueAt(index, 5).toString().equals("Female")) {
            return 0;
        } else {
            return -1;
        }
    }

    private void restoreStudent() {
        //Check form add student 
        if (validateStudentAdd()) {
            List<Classes> listClass = classesDAO.getAll();

            int index = jTableRecycle.getSelectedRow();
            TableModel model = jTableRecycle.getModel();

            String className = model.getValueAt(index, 2).toString();

            int idClass = 0;
            for (Classes classes : listClass) {
                if (classes.getClassName().equalsIgnoreCase(className)) {
                    idClass = classes.getIdClass();
                }
            }

            Student student1 = new Student(
                    idClass,
                    model.getValueAt(index, 1).toString(),
                    model.getValueAt(index, 3).toString(),
                    model.getValueAt(index, 4).toString(),
                    gender(),
                    model.getValueAt(index, 6).toString(),
                    model.getValueAt(index, 7).toString(),
                    model.getValueAt(index, 8).toString(),
                    model.getValueAt(index, 9).toString()
            );

            boolean row = studentDAO.save(student1);

            if (row) {
                JOptionPane.showMessageDialog(this, "Restore successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                //count số student
                int totalStudents = studentDAO.count();
                MainFrame.jLabelCountStudent.setText("" + totalStudents);

                List<Student> students = studentDAO.getAll();

                DefaultTableModel dtm = new DefaultTableModel();
                dtm.addColumn("RollNumber");
                dtm.addColumn("Class");
                dtm.addColumn("First Name");
                dtm.addColumn("Last Name");
                dtm.addColumn("Gender");
                dtm.addColumn("BirthDate");
                dtm.addColumn("Phone");
                dtm.addColumn("Email");
                dtm.addColumn("Address");

                for (Student student2 : students) {
                    Vector<Object> vector = new Vector();
                    vector.add(student2.getRollNumber());
                    vector.add(student2.getClassName());
                    vector.add(student2.getFirstName());
                    vector.add(student2.getLastName());
                    vector.add(student2.getGenderString());
                    vector.add(student2.getBirthDate());
                    vector.add(student2.getPhone());
                    vector.add(student2.getEmail());
                    vector.add(student2.getAddress());

                    dtm.addRow(vector);
                }

                JPStudentManager.jTableStudent.setModel(dtm);

                flagGlobal = true;
            } else {
                JOptionPane.showMessageDialog(this, "Error restore Student.\n"
                        + "Cannot restore because class " + className
                        + " does not exist!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                flagGlobal = false;
            }
        }
    }

    //Score
    private void fillDataScore() {
        List<Score> scores = scoreDAO.getAllScoreTemp();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("idScore");
        dtm.addColumn("RollNumber");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Subject");
        dtm.addColumn("Class");
        dtm.addColumn("Score");
        dtm.addColumn("Type of score");
        dtm.addColumn("Number of exams");
        dtm.addColumn("Description");

        for (Score score : scores) {
            Vector<Object> vector = new Vector();
            vector.add(score.getIdScore());
            vector.add(score.getRollNumber());
            vector.add(score.getFirstName());
            vector.add(score.getLastName());
            vector.add(score.getSubjectName());
            vector.add(score.getClassName());
            vector.add(score.getScore());
            vector.add(score.getTypeOfScore());
            vector.add(score.getNumberOfExams());
            vector.add(score.getDesc());

            dtm.addRow(vector);
        }

        jTableRecycle.setModel(dtm);
        jTableRecycle.removeColumn(jTableRecycle.getColumnModel().getColumn(0));
    }

    private void deleteScore() {
        int index = jTableRecycle.getSelectedRow();
        TableModel model = jTableRecycle.getModel();

        String idScore = String.valueOf(model.getValueAt(index, 0));

        int yes = JOptionPane.showConfirmDialog(this, "Do you want to delete?", "Warning", JOptionPane.YES_NO_OPTION);
        if (yes == JOptionPane.YES_OPTION) {
            boolean row = scoreDAO.deleteByID(idScore);

            if (row) {
                JOptionPane.showMessageDialog(this, "Delete Successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                //khi delete thành công thì show data lên bảng
                fillDataScore();
            } else {
                JOptionPane.showMessageDialog(this, "Error Delete !!!", "Notification", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchScore() {
        List<Score> scores = scoreDAO.searchScoreTemp(jTextFieldSearch.getText());

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("idScore");
        dtm.addColumn("RollNumber");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Subject");
        dtm.addColumn("Class");
        dtm.addColumn("Score");
        dtm.addColumn("Type of score");
        dtm.addColumn("Number of exams");
        dtm.addColumn("Description");

        for (Score score : scores) {
            Vector<Object> vector = new Vector();
            vector.add(score.getIdScore());
            vector.add(score.getRollNumber());
            vector.add(score.getFirstName());
            vector.add(score.getLastName());
            vector.add(score.getSubjectName());
            vector.add(score.getClassName());
            vector.add(score.getScore());
            vector.add(score.getTypeOfScore());
            vector.add(score.getNumberOfExams());
            vector.add(score.getDesc());

            dtm.addRow(vector);
        }

        jTableRecycle.setModel(dtm);
        jTableRecycle.removeColumn(jTableRecycle.getColumnModel().getColumn(0));
    }

    //validate form add class
    private boolean validateScoreAdd() {
        boolean flag = true;

        //check course name phải là duy nhất ko được trùng, chuyển flag = false
        List<Score> listScore = scoreDAO.getAll();

        int index = jTableRecycle.getSelectedRow();
        TableModel model = jTableRecycle.getModel();

        for (Score score : listScore) {
            String getCBR = model.getValueAt(index, 1).toString(); // get rollNumber ở comboBox
            String getCBS = model.getValueAt(index, 4).toString();// get courseName ở comboBox
            String getR = score.getRollNumber(); // get rollNumber từ listScore
            String getS = score.getSubjectName();// get courseName từ listScore

            if ((getCBR.equalsIgnoreCase(getR)) && (getCBS.equalsIgnoreCase(getS))) {
                JOptionPane.showMessageDialog(this, "Roll Nummber " + score.getRollNumber()
                        + " (learning " + score.getSubjectName().toUpperCase() + ") already exists the score. Cannot restore", "Warning", JOptionPane.ERROR_MESSAGE);

                flag = false;
                break;
            }
        }

        //nếu course name and rollNumber không bị trùng thì flag vẫn = true rồi check tiếp
        if (flag) {
            return true;
        } else {
            return false;
        }
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

        MainFrame.jPanelStatisticsAvgScore.removeAll();
        MainFrame.jPanelStatisticsAvgScore.add(chartPanel);
    }

    private void restoreScore() {
        //Check form add course
        if (validateScoreAdd()) {
            List<Student> students = studentDAO.getAll();
            List<Subject> subjects = subjectDAO.getAll();
            List<Classes> listClass = classesDAO.getAll();

            int index = jTableRecycle.getSelectedRow();
            TableModel model = jTableRecycle.getModel();

            String rollN = model.getValueAt(index, 1).toString();
            String className = model.getValueAt(index, 5).toString();
            String subjectName = model.getValueAt(index, 4).toString();

            int idStudent = 0;
            for (Student student : students) {
                if (rollN.equalsIgnoreCase(student.getRollNumber())) {
                    idStudent = student.getIdStudent();
                }
            }

            int idSubject = 0;
            for (Subject subject : subjects) {
                if (subjectName.equalsIgnoreCase(subject.getSubjectName())) {
                    idSubject = subject.getIdSubject();
                }
            }

            int idClass = 0;
            for (Classes classes : listClass) {
                if (className.equalsIgnoreCase(classes.getClassName())) {
                    idClass = classes.getIdClass();
                }
            }

            Score score = new Score(
                    idStudent,
                    idSubject,
                    idClass,
                    Float.valueOf(model.getValueAt(index, 6).toString()),
                    model.getValueAt(index, 7).toString(),
                    Integer.valueOf(model.getValueAt(index, 8).toString()),
                    model.getValueAt(index, 9).toString()
            );

            boolean row = scoreDAO.save(score);
            if (row) {
                JOptionPane.showMessageDialog(this, "Restore successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                createChartScore();

                List<Score> scores = scoreDAO.getAll();

                DefaultTableModel dtm = new DefaultTableModel();

                dtm.addColumn("idScore");
                dtm.addColumn("RollNumber");
                dtm.addColumn("First Name");
                dtm.addColumn("Last Name");
                dtm.addColumn("Subject");
                dtm.addColumn("Class");
                dtm.addColumn("Score");
                dtm.addColumn("Type of score");
                dtm.addColumn("Number of exams");
                dtm.addColumn("Description");

                for (Score score1 : scores) {
                    Vector<Object> vector = new Vector();
                    vector.add(score1.getIdScore());
                    vector.add(score1.getRollNumber());
                    vector.add(score1.getFirstName());
                    vector.add(score1.getLastName());
                    vector.add(score1.getSubjectName());
                    vector.add(score1.getClassName());
                    vector.add(score1.getScore());
                    vector.add(score1.getTypeOfScore());
                    vector.add(score1.getNumberOfExams());
                    vector.add(score1.getDesc());

                    dtm.addRow(vector);
                }

                JPScoreManager.jTableScore.setModel(dtm);

                JPScoreManager.jTableScore.removeColumn(JPScoreManager.jTableScore.getColumnModel().getColumn(0));
                flagGlobal = true;
            } else {
                JOptionPane.showMessageDialog(this, "Error restore score.\n"
                        + "Cannot restore because class " + className
                        + " or roll number " + rollN + " or subject " + subjectName
                        + " does not exist!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                flagGlobal = false;
            }
        }
    }

    //Timetable
    private void fillDataTimetable() {
        List<TimeTable> timeTables = timetableDAO.getAllTemp();

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("Class");
        dtm.addColumn("Subject");
        dtm.addColumn("Start time");
        dtm.addColumn("End time");
        dtm.addColumn("Days");
        dtm.addColumn("Room");
        dtm.addColumn("Teacher");
        dtm.addColumn("Desc");

        for (TimeTable timeTable : timeTables) {
            Vector<Object> vector = new Vector();
            vector.add(timeTable.getIdTT());
            vector.add(timeTable.getClassName());
            vector.add(timeTable.getSubject());
            vector.add(timeTable.getStartTime());
            vector.add(timeTable.getEndTime());
            vector.add(timeTable.getDate());
            vector.add(timeTable.getRoom());
            vector.add(timeTable.getTeacher());
            vector.add(timeTable.getDesc());

            dtm.addRow(vector);
        }

        jTableRecycle.setModel(dtm);
    }

    private void deleteTimetable() {
        int index = jTableRecycle.getSelectedRow();
        TableModel model = jTableRecycle.getModel();

        String className = model.getValueAt(index, 0).toString();
        String subjectName = model.getValueAt(index, 1).toString();

        int yes = JOptionPane.showConfirmDialog(this, "Do you want to delete?", "Warning", JOptionPane.YES_NO_OPTION);
        if (yes == JOptionPane.YES_OPTION) {
            boolean row = timetableDAO.delete(className, subjectName);

            if (row) {
                JOptionPane.showMessageDialog(this, "Delete Successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                //khi delete thành công thì show data lên bảng
                fillDataTimetable();
            } else {
                JOptionPane.showMessageDialog(this, "Error Delete !!!", "Notification", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchTimetable() {
        List<TimeTable> timeTables = timetableDAO.searchTemp(jTextFieldSearch.getText());

        DefaultTableModel dtm = new DefaultTableModel();

        dtm.addColumn("Class");
        dtm.addColumn("Subject");
        dtm.addColumn("Start time");
        dtm.addColumn("End time");
        dtm.addColumn("Days");
        dtm.addColumn("Room");
        dtm.addColumn("Teacher");
        dtm.addColumn("Desc");

        for (TimeTable timeTable : timeTables) {
            Vector<Object> vector = new Vector();
            vector.add(timeTable.getClassName());
            vector.add(timeTable.getSubject());
            vector.add(timeTable.getStartTime());
            vector.add(timeTable.getEndTime());
            vector.add(timeTable.getDate());
            vector.add(timeTable.getRoom());
            vector.add(timeTable.getTeacher());
            vector.add(timeTable.getDesc());

            dtm.addRow(vector);
        }

        jTableRecycle.setModel(dtm);
    }

    //validate form add timetable
    private boolean validateTTAdd() {
        boolean flag = true;

        //check subject name and class name phải là duy nhất ko được trùng, chuyển flag = false
        List<TimeTable> listTimeTable = timetableDAO.getAll();

        int index = jTableRecycle.getSelectedRow();
        TableModel model = jTableRecycle.getModel();

        for (TimeTable timeTable : listTimeTable) {
            String getSTemp = model.getValueAt(index, 1).toString();//get Course name khi user chọn
            String getS = timeTable.getSubject(); //get course name trong từ bảng
            String getClTemp = model.getValueAt(index, 0).toString();
            String getCLN = timeTable.getClassName();
            if (getClTemp.toLowerCase().equals(getCLN.toLowerCase()) && getSTemp.equals(getS)) {
                JOptionPane.showMessageDialog(this, "Class " + getCLN
                        + " has a subject " + getSTemp.toUpperCase() + " time table!!!", "Warning", JOptionPane.ERROR_MESSAGE);

                flag = false;
                break;
            }
        }
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    private void restoreTimetable() {
        //Check form add course
        if (validateTTAdd()) {
            List<TimeTable> timeTables = timetableDAO.getAll();

            List<Teacher> teachers = teacherDAO.getAll();
            List<Room> rooms = roomDAO.getAll();
            List<Subject> subjects = subjectDAO.getAll();
            List<Classes> listClass = classesDAO.getAll();

            int index = jTableRecycle.getSelectedRow();
            TableModel model = jTableRecycle.getModel();

            String className = model.getValueAt(index, 0).toString();
            String subjectName = model.getValueAt(index, 1).toString();
            String roomName = model.getValueAt(index, 5).toString();
            String teacherName = model.getValueAt(index, 6).toString();

            int idClass = 0;
            for (Classes classes : listClass) {
                if (className.equalsIgnoreCase(classes.getClassName())) {
                    idClass = classes.getIdClass();
                }
            }

            int idSubject = 0;
            for (Subject subject : subjects) {
                if (subjectName.equalsIgnoreCase(subject.getSubjectName())) {
                    idSubject = subject.getIdSubject();
                }
            }

            int idRoom = 0;
            for (Room room : rooms) {
                if (className.equalsIgnoreCase(room.getRoomName())) {
                    idRoom = room.getIdRoom();
                }
            }

            int idTeacher = 0;
            for (Teacher teacher : teachers) {
                if (teacherName.equalsIgnoreCase(teacher.getFirstName())) {
                    idTeacher = teacher.getIdTeacher();
                }
            }

            TimeTable timeTable = new TimeTable(
                    idClass,
                    idSubject,
                    model.getValueAt(index, 2).toString(),
                    model.getValueAt(index, 3).toString(),
                    model.getValueAt(index, 4).toString(),
                    idRoom,
                    idTeacher,
                    model.getValueAt(index, 7).toString()
            );

            boolean row = timetableDAO.save(timeTable);
            if (row) {
                JOptionPane.showMessageDialog(this, "Restore successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);

                flagGlobal = true;
            } else {
                JOptionPane.showMessageDialog(this, "Error restore score.\n"
                        + "Cannot restore because class " + className
                        + " or subject " + subjectName + " or room " + roomName
                        + " or teacher " + teacherName
                        + " does not exist!!!", "Warning", JOptionPane.ERROR_MESSAGE);
                flagGlobal = false;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMid = new javax.swing.JPanel();
        jLabelBack = new javax.swing.JLabel();
        jLabelDelete = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jComboBoxSearch = new javax.swing.JComboBox<>();
        jLabelSearch = new javax.swing.JLabel();
        jLabelRefresh = new javax.swing.JLabel();
        jLabelRestore = new javax.swing.JLabel();
        jPanelTop = new javax.swing.JPanel();
        jLabelStudent = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRecycle = new javax.swing.JTable();

        jPanelMid.setBackground(new java.awt.Color(204, 204, 204));
        jPanelMid.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelMidMouseMoved(evt);
            }
        });

        jLabelBack.setBackground(new java.awt.Color(204, 204, 204));
        jLabelBack.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/back.png"))); // NOI18N
        jLabelBack.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabelBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelBack.setOpaque(true);
        jLabelBack.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelBackMouseMoved(evt);
            }
        });
        jLabelBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelBackMouseClicked(evt);
            }
        });

        jLabelDelete.setBackground(new java.awt.Color(204, 204, 204));
        jLabelDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/delete1.png"))); // NOI18N
        jLabelDelete.setText("<html><div style='text-align: right;'>&emsp;Delete</div></html> ");
        jLabelDelete.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabelDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelDelete.setOpaque(true);
        jLabelDelete.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelDeleteMouseMoved(evt);
            }
        });
        jLabelDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDeleteMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelDeleteMouseReleased(evt);
            }
        });

        jTextFieldSearch.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextFieldSearch.setForeground(new java.awt.Color(153, 153, 153));
        jTextFieldSearch.setText("Search...");
        jTextFieldSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldSearchFocusLost(evt);
            }
        });
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });

        jComboBoxSearch.setBackground(new java.awt.Color(240, 240, 240));
        jComboBoxSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBoxSearch.setOpaque(false);

        jLabelSearch.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelSearch.setText("Search By:");

        jLabelRefresh.setBackground(new java.awt.Color(204, 204, 204));
        jLabelRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/refresh1.png"))); // NOI18N
        jLabelRefresh.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabelRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelRefresh.setOpaque(true);
        jLabelRefresh.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelRefreshMouseMoved(evt);
            }
        });
        jLabelRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRefreshMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelRefreshMouseReleased(evt);
            }
        });

        jLabelRestore.setBackground(new java.awt.Color(204, 204, 204));
        jLabelRestore.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelRestore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sm/image/delete.png"))); // NOI18N
        jLabelRestore.setText("<html><div style='text-align: right;'>&emsp;Restore</div></html> ");
        jLabelRestore.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jLabelRestore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelRestore.setOpaque(true);
        jLabelRestore.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabelRestoreMouseMoved(evt);
            }
        });
        jLabelRestore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRestoreMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabelRestoreMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanelMidLayout = new javax.swing.GroupLayout(jPanelMid);
        jPanelMid.setLayout(jPanelMidLayout);
        jPanelMidLayout.setHorizontalGroup(
            jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMidLayout.createSequentialGroup()
                .addComponent(jLabelBack, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                .addComponent(jLabelSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
        jPanelMidLayout.setVerticalGroup(
            jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMidLayout.createSequentialGroup()
                .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBack, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelMidLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSearch)
                    .addComponent(jComboBoxSearch)
                    .addComponent(jLabelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMidLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelTop.setBackground(new java.awt.Color(153, 153, 153));
        jPanelTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanelTopMouseMoved(evt);
            }
        });

        jLabelStudent.setFont(new java.awt.Font("Century Gothic", 1, 22)); // NOI18N
        jLabelStudent.setForeground(new java.awt.Color(255, 255, 255));
        jLabelStudent.setText("<html><div style='text-align: right;'>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Recycle bin</div></html> ");

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelStudent)
                .addContainerGap())
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelStudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        jTableRecycle.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTableRecycle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableRecycle.setFillsViewportHeight(true);
        jTableRecycle.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTableRecycleMouseMoved(evt);
            }
        });
        jTableRecycle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRecycleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTableRecycleMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableRecycleMouseReleased(evt);
            }
        });
        jTableRecycle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableRecycleKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTableRecycle);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanelMid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelMid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelBackMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelBackMouseMoved
        // TODO add your handling code here:
        jLabelBack.setBackground(new Color(255, 255, 255));
        jLabelBack.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_jLabelBackMouseMoved

    private void jLabelBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelBackMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);

        if (MainFrame.jLabelLineClasses.isVisible()) {
            MainFrame.getjPClassManager().setVisible(true);
        } else if (MainFrame.jLabelLineStudent.isVisible()) {
            MainFrame.getjPStudentManager().setVisible(true);
        } else if (MainFrame.jLabelLineScore.isVisible()) {
            MainFrame.getjPScoreManager().setVisible(true);
        } else if (MainFrame.jLabelLineTimetable.isVisible()) {
            MainFrame.getjPTimetableManager().setVisible(true);
        }
    }//GEN-LAST:event_jLabelBackMouseClicked

    private void jLabelDeleteMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDeleteMouseMoved
        // TODO add your handling code here:
        jLabelDelete.setBackground(new Color(255, 255, 255));
        jLabelDelete.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_jLabelDeleteMouseMoved

    private void jLabelDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDeleteMouseClicked
        // TODO add your handling code here:
        try {
            if (MainFrame.jLabelLineClasses.isVisible()) {
                deleteClass();
            } else if (MainFrame.jLabelLineStudent.isVisible()) {
                deleteStudent();
            } else if (MainFrame.jLabelLineScore.isVisible()) {
                deleteScore();
            } else if (MainFrame.jLabelLineTimetable.isVisible()) {
                deleteTimetable();
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jLabelDeleteMouseClicked

    private void jLabelDeleteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDeleteMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelDeleteMouseReleased

    private void jTextFieldSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSearchFocusGained
        // TODO add your handling code here:
        if (jTextFieldSearch.getText().equals("Search...")) {
            jTextFieldSearch.setText("");
            jTextFieldSearch.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jTextFieldSearchFocusGained

    private void jTextFieldSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldSearchFocusLost
        // TODO add your handling code here:
        //nếu jTextField trống thì lại setText thành username
        if (jTextFieldSearch.getText().isEmpty()) {
            jTextFieldSearch.setText("Search...");
            jTextFieldSearch.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextFieldSearchFocusLost

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        if (MainFrame.jLabelLineClasses.isVisible()) {
            searchClass();
        } else if (MainFrame.jLabelLineStudent.isVisible()) {
            searchStudent();
        } else if (MainFrame.jLabelLineScore.isVisible()) {
            searchScore();
        } else if (MainFrame.jLabelLineTimetable.isVisible()) {
            searchTimetable();
        }
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void jLabelRefreshMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRefreshMouseMoved
        // TODO add your handling code here:
        jLabelRefresh.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jLabelRefreshMouseMoved

    private void jLabelRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRefreshMouseClicked
        // TODO add your handling code here:
        if (MainFrame.jLabelLineClasses.isVisible()) {
            fillDataClass();
        } else if (MainFrame.jLabelLineStudent.isVisible()) {
            fillDataStudent();
        } else if (MainFrame.jLabelLineScore.isVisible()) {
            fillDataScore();
        } else if (MainFrame.jLabelLineTimetable.isVisible()) {
            fillDataTimetable();
        }

    }//GEN-LAST:event_jLabelRefreshMouseClicked

    private void jLabelRefreshMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRefreshMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelRefreshMouseReleased

    private void jLabelRestoreMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRestoreMouseMoved
        // TODO add your handling code here:
        jLabelRestore.setBackground(new Color(255, 255, 255));
        jLabelRestore.setForeground(new Color(0, 0, 0));
    }//GEN-LAST:event_jLabelRestoreMouseMoved

    private void jLabelRestoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRestoreMouseClicked
        // TODO add your handling code here:
        try {
            int index = jTableRecycle.getSelectedRow();
            TableModel model = jTableRecycle.getModel();

            if (MainFrame.jLabelLineClasses.isVisible()) {
                restoreClass();

                if (flagGlobal) {
                    //restore xong thi delete trong bang tam
                    String idClass = String.valueOf(model.getValueAt(index, 0));

                    classesDAO.deleteTempClass(idClass);

                    fillDataClass();

                    flagGlobal = false;
                }
            } else if (MainFrame.jLabelLineStudent.isVisible()) {
                restoreStudent();

                if (flagGlobal) {
                    //restore xong thi delete trong bang tam
                    String idStudent = model.getValueAt(index, 0).toString();

                    studentDAO.deleteByIDStudent(idStudent);

                    fillDataStudent();

                    flagGlobal = false;
                }
            } else if (MainFrame.jLabelLineScore.isVisible()) {
                restoreScore();
                if (flagGlobal) {
                    //restore xong thi delete trong bang tam
                    String idScore = String.valueOf(model.getValueAt(index, 0));

                    scoreDAO.deleteByID(idScore);

                    fillDataScore();

                    flagGlobal = false;
                }
            } else if (MainFrame.jLabelLineTimetable.isVisible()) {
                restoreTimetable();

                if (flagGlobal) {
                    //restore xong thi delete trong bang tam
                    String className = model.getValueAt(index, 0).toString();
                    String subjectName = model.getValueAt(index, 1).toString();

                    timetableDAO.delete(className, subjectName);

                    fillDataTimetable();

                    flagGlobal = false;
                }
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jLabelRestoreMouseClicked

    private void jLabelRestoreMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRestoreMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelRestoreMouseReleased

    private void jPanelMidMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMidMouseMoved
        // TODO add your handling code here:
        jLabelBack.setBackground(new Color(204, 204, 204));
        jLabelBack.setForeground(new Color(0, 0, 0));

        jLabelDelete.setBackground(new Color(204, 204, 204));
        jLabelDelete.setForeground(new Color(0, 0, 0));

        jLabelRestore.setBackground(new Color(204, 204, 204));
        jLabelRestore.setForeground(new Color(0, 0, 0));

        jLabelRefresh.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_jPanelMidMouseMoved

    private void jPanelTopMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelTopMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanelTopMouseMoved

    private void jTableRecycleMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRecycleMouseMoved
        // TODO add your handling code here:
        jLabelBack.setBackground(new Color(204, 204, 204));
        jLabelBack.setForeground(new Color(0, 0, 0));

        jLabelDelete.setBackground(new Color(204, 204, 204));
        jLabelDelete.setForeground(new Color(0, 0, 0));

        jLabelRestore.setBackground(new Color(204, 204, 204));
        jLabelRestore.setForeground(new Color(0, 0, 0));

        jLabelRefresh.setBackground(new Color(204, 204, 204));
    }//GEN-LAST:event_jTableRecycleMouseMoved

    private void jTableRecycleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRecycleMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableRecycleMouseClicked

    private void jTableRecycleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRecycleMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableRecycleMouseEntered

    private void jTableRecycleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRecycleMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableRecycleMouseReleased

    private void jTableRecycleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableRecycleKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableRecycleKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> jComboBoxSearch;
    public static javax.swing.JLabel jLabelBack;
    public static javax.swing.JLabel jLabelDelete;
    private javax.swing.JLabel jLabelRefresh;
    public static javax.swing.JLabel jLabelRestore;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JLabel jLabelStudent;
    private javax.swing.JPanel jPanelMid;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableRecycle;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
