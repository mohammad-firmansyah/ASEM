package com.example.asem.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AsetModel {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    public Aset data;
    @SerializedName("code")
    private int code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public Aset getData() {
        return data;
    }

    public void setData(Aset data) {
        this.data = data;
    }

    public AsetModel(String status, int code, Aset data) {
        this.status =status;
        this.code = code;

        this.data = data;
    }
}
