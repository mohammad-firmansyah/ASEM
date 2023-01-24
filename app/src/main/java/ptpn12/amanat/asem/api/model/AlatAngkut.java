package ptpn12.amanat.asem.api.model;

public class AlatAngkut {
    private Integer ap_id;
    private String ap_desc;

    public Integer getAp_id() {
        return ap_id;
    }

    public void setAp_id(Integer ap_id) {
        this.ap_id = ap_id;
    }

    public String getAp_desc() {
        return ap_desc;
    }

    public void setAp_desc(String ap_desc) {
        this.ap_desc = ap_desc;
    }

    public AlatAngkut(Integer ap_id, String ap_desc) {
        this.ap_id = ap_id;
        this.ap_desc = ap_desc;
    }
}
