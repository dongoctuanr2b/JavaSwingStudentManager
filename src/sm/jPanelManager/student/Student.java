/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jPanelManager.student;

/**
 *
 * @author Ngoc Tuan
 */
public class Student {

    private int idStudent;
    private int idClass;
    private String className;
    private String rollNumber;
    private String firstName;
    private String lastName;
    private byte gender;
    private String genderString;
    private String birthDate;
    private String phone;
    private String email;
    private String address;

    public Student() {
    }

    public Student(int idStudent, String className) {
        this.idStudent = idStudent;
        this.className = className;
    }

    public Student(int idClass, String rollNumber, String firstName, String lastName, byte gender, String birthDate, String phone, String email, String address) {
        this.idClass = idClass;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Student(int idStudent, int idClass, String className, String rollNumber, String firstName, String lastName, String genderString, String birthDate, String phone, String email, String address) {
        this.idStudent = idStudent;
        this.idClass = idClass;
        this.className = className;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderString = genderString;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Student(String className, String rollNumber, String firstName, String lastName, String genderString, String birthDate, String phone, String email, String address) {
        this.className = className;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderString = genderString;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Student(int idStudent, String className, String rollNumber, String firstName, String lastName, String genderString, String birthDate, String phone, String email, String address) {
        this.idStudent = idStudent;
        this.className = className;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderString = genderString;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Student(String className, String rollNumber, String firstName, String lastName, byte gender, String birthDate, String phone, String email, String address) {
        this.className = className;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Student(int idStudent, int idClass, String rollNumber, String firstName, String lastName, byte gender, String birthDate, String phone, String email, String address) {
        this.idStudent = idStudent;
        this.idClass = idClass;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
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

    public byte getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
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

    public String getGenderString() {
        return genderString;
    }

    public int getIdClass() {
        return idClass;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return this.rollNumber;
    }
}
