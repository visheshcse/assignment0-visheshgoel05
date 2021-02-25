package com.example.helloworld;

public class Student {

    private String rollNo;
    private String name;
    private String dept;
    private String emailId;

    public Student(String rollNo, String name, String dept, String emailId) {
        this.rollNo = rollNo;
        this.name = name;
        this.dept = dept;
        this.emailId = emailId;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
