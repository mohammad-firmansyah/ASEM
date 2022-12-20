package com.example.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchModel {

    public SearchModel(boolean status, String message, Data2 data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private Data2 data;

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

        public Data2 getData() {
            return data;
        }

        public void setData(Data2 data) {
            this.data = data;
        }
}
