package com.example.rohit.chatapp.services.model;
import com.google.gson.annotations.SerializedName;
/**
 * Created by rohit on 8/23/17.
 */

public class ActivityObject {
    private String mode;
    private String activityId;

    public ActivityObject(String mode, String activityId) {
        this.mode = mode;
        this.activityId = activityId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "ActivityObject{" +
                "mode='" + mode + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
    }
}
