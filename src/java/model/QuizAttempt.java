/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
/**
 *
 * @author User
 */
public class QuizAttempt {
    private int id;
    private String studentId;
    private int quizId;
    private Timestamp startTime;
    private Timestamp endTime;
    private int timeTakenMinute;
    private double marks;

    //setters getters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public int getQuizId() {
        return quizId;
    }
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    public int getTimeTakenMinute() {
        return timeTakenMinute;
    }
    public void setTimeTakenMinute(int timeTakenMinute) {
        this.timeTakenMinute = timeTakenMinute;
    }
    public double getMarks() {
        return marks;
    }
    public void setMarks(double marks) {
        this.marks = marks;
    }
}
