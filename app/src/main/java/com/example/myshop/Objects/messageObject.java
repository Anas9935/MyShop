package com.example.myshop.Objects;

public class messageObject {
    private String uid;
    private int type;
    private String message;
    private long timestamp;

    public messageObject(String uid, int type, String message, long timestamp) {
        this.uid = uid;
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
