package com.dhruv.studentdatabase.database;

public class Student {
    private int ID;
    private String firstName;
    private String lastName;
    private int marks;

    public Student() {
    }

    public Student(int ID) {
        this.ID = ID;
    }

    public Student(String firstName, String lastName, int marks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.marks = marks;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "ID=" + ID + "\nFirst Name=" + firstName + "\nLast Name=" + lastName + "\nMarks=" + marks + "\n";
    }
}
