package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnitModel {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    public List<Unit> data;
    @SerializedName("code")
    private int code;

    public UnitModel(String status, List<Unit> data, int code) {
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

    public List<Unit> getData() {
        return data;
    }

    public void setData(List<Unit> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
