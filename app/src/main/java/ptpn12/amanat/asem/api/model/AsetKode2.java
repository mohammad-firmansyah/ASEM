package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AsetKode2 {
    @SerializedName("aset_kode_id")
    @Expose
    private Integer asetKodeId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("aset_class")
    @Expose
    private String asetClass;
    @SerializedName("aset_group")
    @Expose
    private String asetGroup;
    @SerializedName("aset_desc")
    @Expose
    private String asetDesc;
    @SerializedName("aset_jenis")
    @Expose
    private Integer asetJenis;

    public Integer getAsetKodeId() {
        return asetKodeId;
    }

    public void setAsetKodeId(Integer asetKodeId) {
        this.asetKodeId = asetKodeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAsetClass() {
        return asetClass;
    }

    public void setAsetClass(String asetClass) {
        this.asetClass = asetClass;
    }

    public String getAsetGroup() {
        return asetGroup;
    }

    public void setAsetGroup(String asetGroup) {
        this.asetGroup = asetGroup;
    }

    public String getAsetDesc() {
        return asetDesc;
    }

    public void setAsetDesc(String asetDesc) {
        this.asetDesc = asetDesc;
    }

    public Integer getAsetJenis() {
        return asetJenis;
    }

    public void setAsetJenis(Integer asetJenis) {
        this.asetJenis = asetJenis;
    }

    public AsetKode2(Integer asetKodeId, String createdAt, String updatedAt, String asetClass, String asetGroup, String asetDesc, Integer asetJenis) {
        this.asetKodeId = asetKodeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.asetClass = asetClass;
        this.asetGroup = asetGroup;
        this.asetDesc = asetDesc;
        this.asetJenis = asetJenis;
    }
}
