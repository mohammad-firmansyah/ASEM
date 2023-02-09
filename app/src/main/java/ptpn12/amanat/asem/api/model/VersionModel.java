package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionModel {
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;
    @Expose
    @SerializedName("updateAvailable") private Boolean updateAvailable;
    @Expose
    @SerializedName("updateNeeded") private Boolean updateNeeded;
    @Expose
    @SerializedName("aplikasiId") private String aplikasiId;
    @Expose
    @SerializedName("pesan1") private String pesan1;
    @Expose
    @SerializedName("pesan2") private String pesan2;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(Boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }

    public Boolean getUpdateNeeded() {
        return updateNeeded;
    }

    public void setUpdateNeeded(Boolean updateNeeded) {
        this.updateNeeded = updateNeeded;
    }

    public String getAplikasiId() {
        return aplikasiId;
    }

    public void setAplikasiId(String aplikasiId) {
        this.aplikasiId = aplikasiId;
    }

    public String getPesan1() {
        return pesan1;
    }

    public void setPesan1(String pesan1) {
        this.pesan1 = pesan1;
    }

    public String getPesan2() {
        return pesan2;
    }

    public void setPesan2(String pesan2) {
        this.pesan2 = pesan2;
    }
}
