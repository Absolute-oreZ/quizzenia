package model;

public class Question {
    private int id;
    private int quizId;
    private String questionText;
    private byte[] questionPicture;

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getQuizId() {
        return quizId;
    }
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public byte[] getQuestionPicture() {
        return questionPicture;
    }
    public void setQuestionPicture(byte[] questionPicture) {
        this.questionPicture = questionPicture;
    }
}
