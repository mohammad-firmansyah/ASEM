package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

public class Afdelling {
    @SerializedName("afdeling_id")
    private int afdeling_id;
    @SerializedName("afdeling_desc")
    private String afdeling_desc;
    @SerializedName("unit_id")
    private int unit_id;

    public Afdelling(int afdelling_id, String afdelling_desc, int unit_id) {
        this.afdeling_id = afdelling_id;
        this.afdeling_desc = afdelling_desc;
        this.unit_id = unit_id;
    }

    public int getAfdelling_id() {
        return afdeling_id;
    }

    public void setAfdelling_id(int afdelling_id) {
        this.afdeling_id = afdelling_id;
    }

    public String getAfdelling_desc() {
        return afdeling_desc;
    }

    public void setAfdelling_desc(String afdelling_desc) {
        this.afdeling_desc = afdelling_desc;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }
}
