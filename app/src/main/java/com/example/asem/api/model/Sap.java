package com.example.asem.api.model;

public class Sap {
    private Integer sap_id;
    private String sap_desc;

    public Sap(Integer sap_id, String sap_desc) {
        this.sap_id = sap_id;
        this.sap_desc = sap_desc;
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
