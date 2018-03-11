package com.example.rohit.chatapp.services.model;
import com.google.gson.annotations.SerializedName;
/**
 * Created by rohit on 8/23/17.
 */

public class AssessmentObject {
    private String description;
    private String goalframework;
    private String learningObjectiveId;
    public AssessmentObject(String description, String goalframework, String learningObjectiveId) {
        this.description = description;
        this.goalframework = goalframework;
        this.learningObjectiveId = learningObjectiveId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoalframework() {
        return goalframework;
    }

    public void setGoalframework(String goalframework) {
        this.goalframework = goalframework;
    }

    public String getLearningObjectiveId() {
        return learningObjectiveId;
    }

    public void setLearningObjectiveId(String learningObjectiveId) {
        this.learningObjectiveId = learningObjectiveId;
    }

    @Override
    public String toString() {
        return "AssessmentObject{" +
                "description='" + description + '\'' +
                ", goalframework='" + goalframework + '\'' +
                ", learningObjectiveId='" + learningObjectiveId + '\'' +
                '}';
    }
}

