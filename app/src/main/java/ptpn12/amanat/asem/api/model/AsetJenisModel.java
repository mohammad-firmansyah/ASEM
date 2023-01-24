package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AsetJenisModel {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    public List<AsetJenis> data;
    @SerializedName("code")
    private int code;

    public AsetJenisModel(String status, List<AsetJenis> data, int code) {
        this.status = status;
        this.data = data;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AsetJenis> getData() {
        return data;
    }

    public void setData(List<AsetJenis> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
