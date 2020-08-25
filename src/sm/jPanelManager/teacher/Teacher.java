/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.teacher;

/**
 *
 * @author Ngoc Tuan
 */
public class Teacher {

    private int idTeacher;
    private String rollNumber;
    private String firstName;
    private String lastName;
    private byte gender;
    private String genderString;
    private String phone;
    private String email;
    private String address;

    public Teacher() {
    }

    public Teacher(int idTeacher, String rollNumber, String firstName, String lastName, byte gender, String phone, String email, String address) {
        this.idTeacher = idTeacher;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Teacher(int idTeacher, String rollNumber, String firstName, String lastName, String genderString, String phone, String email, String address) {
        this.idTeacher = idTeacher;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderString = genderString;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Teacher(String rollNumber, String firstName, String lastName, byte gender, String phone, String email, String address) {
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getIdTeacher() {
        return idTeacher;
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

    public byte getGender() {
        return gender;
    }

    public String getGenderString() {
        return genderString;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return this.firstName;
    }

}
