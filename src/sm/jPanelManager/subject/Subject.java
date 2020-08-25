/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.subject;

/**
 *
 * @author Ngoc Tuan
 */
public class Subject {

    private int idSubject;
    private String subjectName;
    private float fee;

    public Subject() {
    }

    public Subject(int idSubject, String subjectName, float fee) {
        this.idSubject = idSubject;
        this.subjectName = subjectName;
        this.fee = fee;
    }

    public Subject(String subjectName, float fee) {
        this.subjectName = subjectName;
        this.fee = fee;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public float getFee() {
        return fee;
    }

    @Override
    public String toString() {
        return this.subjectName;
    }

}
