package com.example.rohit.chatapp.services.model;

/**
 * Created by rohit on 8/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageList {
    @SerializedName("conversationId")
    @Expose
    private String conversationId;
    @SerializedName("attemptNumber")
    @Expose
    private Integer attemptNumber;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("chats")
    @Expose
    private List<Chat> chats = null;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public void reformatChats(){

    }

    @Override
    public String toString() {
        return "MessageList{" +
                "conversationId='" + conversationId + '\'' +
                ", attemptNumber=" + attemptNumber +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", chats=" + chats +
                '}';
    }
}