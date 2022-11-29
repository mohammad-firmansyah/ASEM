package com.example.asem.api.model;

public class ReportModel {
    private boolean status;
    private String message;
    private Report url;

    public ReportModel(boolean status, String message, Report url) {
        this.status = status;
        this.message = message;
        this.url = url;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Report getUrl() {
        return url;
    }

    public void setUrl(Report url) {
        this.url = url;
    }
}
