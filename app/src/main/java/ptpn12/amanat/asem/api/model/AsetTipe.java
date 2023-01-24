package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

public class AsetTipe {

    @SerializedName("aset_tipe_id")
    private int aset_tipe_id;

    @SerializedName("aset_tipe_desc")
    private String aset_tipe_desc;

    public AsetTipe(Integer aset_tipe_id, String aset_tipe_desc) {
        this.aset_tipe_id = aset_tipe_id;
        this.aset_tipe_desc = aset_tipe_desc;
    }

    public int getAset_tipe_id() {
        return aset_tipe_id;
    }

    public void setAset_tipe_id(int aset_tipe_id) {
        this.aset_tipe_id = aset_tipe_id;
    }

    public String getAset_tipe_desc() {
        return aset_tipe_desc;
    }

    public void setAset_tipe_desc(String aset_tipe_desc) {
        this.aset_tipe_desc = aset_tipe_desc;
    }
}
