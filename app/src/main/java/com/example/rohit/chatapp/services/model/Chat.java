package com.example.rohit.chatapp.services.model;

/**
 * Created by rohit on 8/10/17.
 */

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("chatText")
    @Expose
    private String chatText;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "sender='" + sender + '\'' +
                ", chatText='" + chatText + '\'' +
                '}';
    }

    public String getReformatText() {
        String str = "\\\\\"";
        String str1 = "\"";
        String reformat = chatText;
        if (reformat.charAt(0) == '['
                && reformat.charAt(1) == '\"'
                && reformat.charAt(reformat.length()-1) == ']'
                && reformat.charAt(reformat.length()-2) == '\"') {
            reformat = reformat.substring(2,reformat.length()-2);
        }
        reformat = reformat.replaceAll("\",\" \",\"","\n");
        reformat = reformat.replaceAll("\",\"","\n");
        reformat = reformat.replaceAll(str, str1);
        if (reformat.charAt(0) == '\n'){
            reformat = reformat.substring(1);
        }
        if (reformat.charAt(reformat.length()-1) == '\n') {
            reformat = reformat.substring(0,reformat.length()-2);
        }
        return reformat;
    }
}