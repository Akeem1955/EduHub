package com.akeem.student.parser;

import java.util.HashMap;
import java.util.List;

public class Student {
    private String name;
    private String matric_no;
    private String gender;
    private HashMap<String,Integer> assignment_score;
    private HashMap<String,Integer> total_score;
    private HashMap<String,Integer> test_score;
    private HashMap<String, Integer> attendance;
    public Student() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatric_no() {
        return matric_no;
    }

    public void setMatric_no(String matric_no) {
        this.matric_no = matric_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public HashMap<String, Integer> getAssignment_score() {
        return assignment_score;
    }

    public void setAssignment_score(HashMap<String, Integer> assignment_score) {
        this.assignment_score = assignment_score;
    }

    public HashMap<String, Integer> getTotal_score() {
        return total_score;
    }

    public void setTotal_score(HashMap<String, Integer> total_score) {
        this.total_score = total_score;
    }

    public HashMap<String, Integer> getTest_score() {
        return test_score;
    }

    public void setTest_score(HashMap<String, Integer> test_score) {
        this.test_score = test_score;
    }


    public HashMap<String, Integer> getAttendance() {
        return attendance;
    }

    public void setAttendance(HashMap<String, Integer> attendance) {
        this.attendance = attendance;
    }
}
