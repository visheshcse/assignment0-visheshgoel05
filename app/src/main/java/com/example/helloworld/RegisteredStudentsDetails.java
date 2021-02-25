package com.example.helloworld;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RegisteredStudentsDetails {
    private static RegisteredStudentsDetails registeredStudentsDetails;

    private List<Student> students;
    private ArrayList<String> studentNames = new ArrayList<String>(Arrays.asList(
            "Raman", "Rohit", "John", "Vishal", "Varun", "Smith" , "Watson", "Vikram", "Ravi", "Vicky",
            "Sarthak" ,"Priya", "Sandhya", "Glenn", "Usha", "William", "John", "Scott", "Raj", "Rajat",
            "Abhishek", "Tarun", "Sahil", "Mani", "Manik", "Ankur", "Gautam", "Suraj", "Vivek", "Aaastha"));

    public static RegisteredStudentsDetails get(Context context) {
        if (registeredStudentsDetails == null) {
            registeredStudentsDetails = new RegisteredStudentsDetails(context);
        }

        return registeredStudentsDetails;
    }

    private RegisteredStudentsDetails(Context context) {
        students = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Student student = new Student("10"+ Integer.toString(i),
                    studentNames.get(i), "CSE", studentNames.get(i).toLowerCase() + "@iiitd.ac.in");
            students.add(student);
        }
    }

    public List<Student> getStudents() {
        return students;
    }

//    public Student getStudent(String id) {
//        for (Student student : students) {
//            if (student.getRollNo().equals(id)) {
//                return student;
//            }
//        }
//
//        return null;
//    }
}
