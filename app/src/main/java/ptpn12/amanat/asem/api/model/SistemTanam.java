package ptpn12.amanat.asem.api.model;

public class SistemTanam {

    private Integer st_id;
    private String st_desc;

    public SistemTanam(Integer st_id, String st_desc) {
        this.st_id = st_id;
        this.st_desc = st_desc;
    }

    public Integer getSt_id() {
        return st_id;
    }

    public void setSt_id(Integer st_id) {
        this.st_id = st_id;
    }

    public String getSt_desc() {
        return st_desc;
    }

    public void setSt_desc(String st_desc) {
        this.st_desc = st_desc;
    }
}
