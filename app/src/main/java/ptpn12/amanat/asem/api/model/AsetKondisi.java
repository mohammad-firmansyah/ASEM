package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

public class AsetKondisi {

    @SerializedName("aset_kondisi_id")
    private int aset_kondisi_id;

    @SerializedName("aset_kondisi_desc")
    private String aset_kondisi_desc;

    public AsetKondisi(int aset_kondisi_id, String aset_kondisi_desc) {
        this.aset_kondisi_id = aset_kondisi_id;
        this.aset_kondisi_desc = aset_kondisi_desc;
    }

    public int getAset_kondisi_id() {
        return aset_kondisi_id;
    }

    public void setAset_kondisi_id(int aset_kondisi_id) {
        this.aset_kondisi_id = aset_kondisi_id;
    }

    public String getAset_kondisi_desc() {
        return aset_kondisi_desc;
    }

    public void setAset_kondisi_desc(String aset_kondisi_desc) {
        this.aset_kondisi_desc = aset_kondisi_desc;
    }
}

