package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AsetKodeModel2 {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("data")
        @Expose
        private List<AsetKode2> data = null;

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

        public List<AsetKode2> getData() {
            return data;
        }

        public void setData(List<AsetKode2> data) {
            this.data = data;
        }

    }
