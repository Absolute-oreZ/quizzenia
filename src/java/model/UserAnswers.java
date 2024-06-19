/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public class UserAnswers {
    private int id;
    private int quizAttemtId;
    private int questionId;
    private int answerId;
    
    //setters getters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getQuizAttemtId() {
        return quizAttemtId;
    }
    public void setQuizAttemtId(int quizAttemtId) {
        this.quizAttemtId = quizAttemtId;
    }
    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    public int getAnswerId() {
        return answerId;
    }
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
