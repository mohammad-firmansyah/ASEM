package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

public class AsetKode {

    @SerializedName("aset_kode_id")
    private int aset_kode_id;

    @SerializedName("aset_kode_desc")
    private String aset_kode_desc;

    public AsetKode(int aset_kode_id, String aset_kode_desc) {
        this.aset_kode_id = aset_kode_id;
        this.aset_kode_desc = aset_kode_desc;
    }

    public int getAset_kode_id() {
        return aset_kode_id;
    }

    public void setAset_kode_id(int aset_kode_id) {
        this.aset_kode_id = aset_kode_id;
    }

    public String getAset_kode_desc() {
        return aset_kode_desc;
    }

    public void setAset_kode_desc(String aset_kode_desc) {
        this.aset_kode_desc = aset_kode_desc;
    }
}
