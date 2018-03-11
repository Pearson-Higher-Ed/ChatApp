package com.example.rohit.chatapp.services.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rohit on 8/16/17.
 */



public class SentMessage {

    private String source;
    private String dialogEntryPoint;
    private String learningContextId;
    private String sessionId;
    private String[] message;
    private String conversationId;
    private String event;
    private String learningObjectiveId;
    @SerializedName("correlation-id")
    private String correlationId;
    private String userId;
    private String goalFrameworkId;

    public SentMessage(String[] mList, String goalFrameworkId, String userId,
                       String correlationId, String learningObjectiveId, String event,
                       String source, String dialogEntryPoint, String learningContextId,
                       String sessionId, String conversationId) {
        this.goalFrameworkId = goalFrameworkId;
        this.userId = userId;
        this.correlationId = correlationId;
        this.learningObjectiveId = learningObjectiveId;
        this.event = event;
        this.source = source;
        this.dialogEntryPoint = dialogEntryPoint;
        this.learningContextId = learningContextId;
        this.sessionId = sessionId;
        message = mList;
        this.conversationId = conversationId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDialogEntryPoint() {
        return dialogEntryPoint;
    }

    public void setDialogEntryPoint(String dialogEntryPoint) {
        this.dialogEntryPoint = dialogEntryPoint;
    }

    public String getLearningContextId() {
        return learningContextId;
    }

    public void setLearningContextId(String learningContextId) {
        this.learningContextId = learningContextId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getLearningObjectiveId() {
        return learningObjectiveId;
    }

    public void setLearningObjectiveId(String learningObjectiveId) {
        this.learningObjectiveId = learningObjectiveId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoalFrameworkId() {
        return goalFrameworkId;
    }

    public void setGoalFrameworkId(String goalFrameworkId) {
        this.goalFrameworkId = goalFrameworkId;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "source='" + source + '\'' +
                ", dialogEntryPoint='" + dialogEntryPoint + '\'' +
                ", learningContextId='" + learningContextId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", message=" + message +
                ", conversationId='" + conversationId + '\'' +
                ", event='" + event + '\'' +
                ", learningObjectiveId='" + learningObjectiveId + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", userId='" + userId + '\'' +
                ", goalFrameworkId='" + goalFrameworkId + '\'' +
                '}';
    }
}
