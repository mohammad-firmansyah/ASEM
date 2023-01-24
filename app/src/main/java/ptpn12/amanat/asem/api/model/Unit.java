package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

public class Unit {

    @SerializedName("unit_id")
    private int unit_id;

    @SerializedName("unit_desc")
    private String unit_desc;

    public Unit(int unit_id, String unit_desc) {
        this.unit_id = unit_id;
        this.unit_desc = unit_desc;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public String getUnit_desc() {
        return unit_desc;
    }

    public void setUnit_desc(String unit_desc) {
        this.unit_desc = unit_desc;
    }
}
