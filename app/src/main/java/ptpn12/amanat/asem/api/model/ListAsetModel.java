package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListAsetModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("data")
        @Expose
        private List<Data> data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

    public ListAsetModel(String status, Integer code, List<Data> data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }
}
