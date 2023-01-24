package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AfdellingModel {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    public List<Afdelling> data;
    @SerializedName("code")
    private int code;

    public AfdellingModel(String status, List<Afdelling> data, int code) {
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

    public List<Afdelling> getData() {
        return data;
    }

    public void setData(List<Afdelling> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
