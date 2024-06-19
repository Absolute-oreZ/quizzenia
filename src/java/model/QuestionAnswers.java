/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public class QuestionAnswers {
    private int id;
    private int questionId;
    private int answerId;
    private boolean isCorrect;

    //setters getters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public boolean isIsCorrect() {
        return isCorrect;
    }
    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
