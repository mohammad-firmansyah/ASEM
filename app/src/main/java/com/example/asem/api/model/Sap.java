package com.example.asem.api.model;

public class Sap {
    private Integer sap_id;
    private String sap_desc;
    private String sap_name;
    private Integer unit_id;

    public Sap(Integer sap_id, String sap_desc) {
        this.sap_id = sap_id;
        this.sap_desc = sap_desc;
//        this.sap_name = sap_name;
//        this.unit_id = unit_id;
    }

    public String getSap_name() {
        return sap_name;
    }

    public void setSap_name(String sap_name) {
        this.sap_name = sap_name;
    }

    public Integer getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Integer unit_id) {
        this.unit_id = unit_id;
    }

    public Integer getSap_id() {
        return sap_id;
    }

    public void setSap_id(Integer sap_id) {
        this.sap_id = sap_id;
    }

    public String getSap_desc() {
        return sap_desc;
    }

    public void setSap_desc(String sap_desc) {
        this.sap_desc = sap_desc;
    }
}
