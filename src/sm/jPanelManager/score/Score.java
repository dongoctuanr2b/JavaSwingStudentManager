/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.score;

/**
 *
 * @author Ngoc Tuan
 */
public class Score {

    private int idScore;
    private int idStudent;
    private String rollNumber;
    private String firstName;
    private String lastName;
    private int idSubject;
    private String subjectName;
    private int idClass;
    private String className;
    private float score;
    private String typeOfScore;
    private int numberOfExams;
    private String desc;

    public Score() {
    }

    public Score(int idScore, int idStudent, int idSubject, int idClass, float score, String typeOfScore, int numberOfExams, String desc) {
        this.idScore = idScore;
        this.idStudent = idStudent;
        this.idSubject = idSubject;
        this.idClass = idClass;
        this.score = score;
        this.typeOfScore = typeOfScore;
        this.numberOfExams = numberOfExams;
        this.desc = desc;
    }

    public Score(int idStudent, int idSubject, int idClass, float score, String typeOfScore, int numberOfExams, String desc) {
        this.idStudent = idStudent;
        this.idSubject = idSubject;
        this.idClass = idClass;
        this.score = score;
        this.typeOfScore = typeOfScore;
        this.numberOfExams = numberOfExams;
        this.desc = desc;
    }

    public Score(String rollNumber, String firstName, String lastName, String subjectName, String className, float score, String typeOfScore, int numberOfExams, String desc) {
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjectName = subjectName;
        this.className = className;
        this.score = score;
        this.typeOfScore = typeOfScore;
        this.numberOfExams = numberOfExams;
        this.desc = desc;
    }

    public Score(int idScore, String rollNumber, String firstName, String lastName, String subjectName, String className, float score, String typeOfScore, int numberOfExams, String desc) {
        this.idScore = idScore;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjectName = subjectName;
        this.className = className;
        this.score = score;
        this.typeOfScore = typeOfScore;
        this.numberOfExams = numberOfExams;
        this.desc = desc;
    }

    public Score(float score, String rollNumber) {
        this.score = score;
        this.rollNumber = rollNumber;
    }

    public int getIdScore() {
        return idScore;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getIdClass() {
        return idClass;
    }

    public String getClassName() {
        return className;
    }

    public float getScore() {
        return score;
    }

    public String getTypeOfScore() {
        return typeOfScore;
    }

    public int getNumberOfExams() {
        return numberOfExams;
    }

    public String getDesc() {
        return desc;
    }

}
