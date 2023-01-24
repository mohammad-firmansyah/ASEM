package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

public class SubUnit {

    @SerializedName("sub_unit_id")
    private int sub_unit_id;
    @SerializedName("sub_unit_desc")
    private String sub_unit_desc;

    public SubUnit(int sub_unit_id, String sub_unit_desc) {
        this.sub_unit_id = sub_unit_id;
        this.sub_unit_desc = sub_unit_desc;
    }

    public int getSub_unit_id() {
        return sub_unit_id;
    }

    public void setSub_unit_id(int sub_unit_id) {
        this.sub_unit_id = sub_unit_id;
    }

    public String getSub_unit_desc() {
        return sub_unit_desc;
    }

    public void setSub_unit_desc(String sub_unit_desc) {
        this.sub_unit_desc = sub_unit_desc;
    }
}
