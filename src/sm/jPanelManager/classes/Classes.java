/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.classes;

/**
 *
 * @author Ngoc Tuan
 */
public class Classes {

    private int idClass;
    private String className;
    private int numberOfStudents;

    public Classes() {
    }

    public Classes(int idClass, String className, int numberOfStudents) {
        this.idClass = idClass;
        this.className = className;
        this.numberOfStudents = numberOfStudents;
    }

    public Classes(String className, int numberOfStudents) {
        this.className = className;
        this.numberOfStudents = numberOfStudents;
    }

    public int getIdClass() {
        return idClass;
    }

    public String getClassName() {
        return className;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    @Override
    public String toString() {
        return this.className;
    }

}
