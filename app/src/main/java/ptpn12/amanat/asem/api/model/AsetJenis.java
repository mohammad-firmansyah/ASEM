package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

public class AsetJenis {

    @SerializedName("aset_jenis_id")
    private int aset_jenis_id;

    @SerializedName("aset_jenis_desc")
    private String aset_jenis_desc;

    public AsetJenis(int aset_jenis_id, String aset_jenis_desc) {
        this.aset_jenis_id = aset_jenis_id;
        this.aset_jenis_desc = aset_jenis_desc;
    }

    public int getAset_jenis_id() {
        return aset_jenis_id;
    }

    public void setAset_jenis_id(int aset_jenis_id) {
        this.aset_jenis_id = aset_jenis_id;
    }

    public String getAset_jenis_desc() {
        return aset_jenis_desc;
    }

    public void setAset_jenis_desc(String aset_jenis_desc) {
        this.aset_jenis_desc = aset_jenis_desc;
    }
}
