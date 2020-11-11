package com.kominfo.anaksehat.models;

public class ApiStatus {
    private String status;
    private String message;

    public ApiStatus(){}

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
