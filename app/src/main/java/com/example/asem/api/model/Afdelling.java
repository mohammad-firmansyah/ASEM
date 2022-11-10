package com.example.asem.api.model;

import com.google.gson.annotations.SerializedName;

public class Afdelling {
    @SerializedName("afdelling_id")
    private int afdelling_id;
    @SerializedName("afdelling_desc")
    private String afdelling_desc;
    @SerializedName("unit_id")
    private int unit_id;

    public Afdelling(int afdelling_id, String afdelling_desc, int unit_id) {
        this.afdelling_id = afdelling_id;
        this.afdelling_desc = afdelling_desc;
        this.unit_id = unit_id;
    }

    public int getAfdelling_id() {
        return afdelling_id;
    }

    public void setAfdelling_id(int afdelling_id) {
        this.afdelling_id = afdelling_id;
    }

    public String getAfdelling_desc() {
        return afdelling_desc;
    }

    public void setAfdelling_desc(String afdelling_desc) {
        this.afdelling_desc = afdelling_desc;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }
}
