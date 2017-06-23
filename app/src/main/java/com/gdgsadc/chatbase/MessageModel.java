package com.gdgsadc.chatbase;

/**
 * Created by dan on 23/06/17.
 */

public class MessageModel {

    private String timestamp, userId, postId, message;

    // Empty Constructor Required
    public MessageModel(){}

    public MessageModel(String timestamp, String userId, String postId, String message) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.postId = postId;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

