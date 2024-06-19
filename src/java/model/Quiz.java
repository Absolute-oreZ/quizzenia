package model;

import java.sql.Timestamp;

public class Quiz {

    private int id;
    private String name;
    private String description;
    private int classId;
    private int noOfQuestion;
    private Timestamp startTime;
    private Timestamp endTime;
    private int durationMinutes;
    private int attempts;
    private String status;
    private Timestamp createdAt;
    private Timestamp lastEditedAt;

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getNoOfQuestion() {
        return noOfQuestion;
    }
    public void setNoOfQuestion(int noOfQuestion) {
        this.noOfQuestion = noOfQuestion;
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

    public int getAttempts() {
        return attempts;
    }
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLastEditedAt() {
        return lastEditedAt;
    }
    public void setLastEditedAt(Timestamp lastEditedAt) {
        this.lastEditedAt = lastEditedAt;
    }
}
