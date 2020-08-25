/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.timetable;

/**
 *
 * @author Ngoc Tuan
 */
public class TimeTable {

    private int idTT;
    private int idClass;
    private String className;
    private int idSubject;
    private String subject;
    private String startTime;
    private String endTime;
    private String date;
    private int idRoom;
    private String room;
    private int idTeacher;
    private String teacher;
    private String desc;

    public TimeTable() {
    }

    public TimeTable(int idTT, String className, String subject, String startTime, String endTime, String date, String room, String teacher, String desc) {
        this.idTT = idTT;
        this.className = className;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.room = room;
        this.teacher = teacher;
        this.desc = desc;
    }

    public TimeTable(int idTT, int idClass, int idSubject, String startTime, String endTime, String date, int idRoom, int idTeacher, String desc) {
        this.idTT = idTT;
        this.idClass = idClass;
        this.idSubject = idSubject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.idRoom = idRoom;
        this.idTeacher = idTeacher;
        this.desc = desc;
    }

    public TimeTable(int idClass, int idSubject, String startTime, String endTime, String date, int idRoom, int idTeacher, String desc) {
        this.idClass = idClass;
        this.idSubject = idSubject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.idRoom = idRoom;
        this.idTeacher = idTeacher;
        this.desc = desc;
    }

    public TimeTable(String className, String subject, String startTime, String endTime, String date, String room, String teacher, String desc) {
        this.className = className;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.room = room;
        this.teacher = teacher;
        this.desc = desc;
    }

    public int getIdTT() {
        return idTT;
    }

    public int getIdClass() {
        return idClass;
    }

    public String getClassName() {
        return className;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public String getSubject() {
        return subject;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }

    public String getDesc() {
        return desc;
    }

}
