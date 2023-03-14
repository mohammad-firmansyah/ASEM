package ptpn12.amanat.asem.api.model;

public class Sap {
    private Integer sap_id;
    private String sap_desc;
    private String sap_name;
    private Integer unit_id;
    private Long nilai_oleh;
    private Long nilai_residu;
    private Integer masa_susut;
    private String tgl_oleh;

    public Sap(Integer sap_id, String sap_desc, String sap_name, Integer unit_id, Long nilai_oleh, Long nilai_residu, Integer masa_susut, String tgl_oleh) {
        this.sap_id = sap_id;
        this.sap_desc = sap_desc;
        this.sap_name = sap_name;
        this.unit_id = unit_id;
        this.nilai_oleh = nilai_oleh;
        this.nilai_residu = nilai_residu;
        this.masa_susut = masa_susut;
        this.tgl_oleh = tgl_oleh;
    }

    public Integer getMasa_susut() {
        return masa_susut;
    }

    public void setMasa_susut(Integer masa_susut) {
        this.masa_susut = masa_susut;
    }

    public String getTgl_oleh() {
        return tgl_oleh;
    }

    public void setTgl_oleh(String tgl_oleh) {
        this.tgl_oleh = tgl_oleh;
    }

    public Long getNilai_oleh() {
        return nilai_oleh;
    }

    public void setNilai_oleh(Long nilai_oleh) {
        this.nilai_oleh = nilai_oleh;
    }

    public Long getNilai_residu() {
        return nilai_residu;
    }

    public void setNilai_residu(Long nilai_residu) {
        this.nilai_residu = nilai_residu;
    }

    public String getSap_name() {
        return sap_name;
    }

    public void setSap_name(String sap_name) {
        this.sap_name = sap_name;
    }

    public Integer getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Integer unit_id) {
        this.unit_id = unit_id;
    }

    public Integer getSap_id() {
        return sap_id;
    }

    public void setSap_id(Integer sap_id) {
        this.sap_id = sap_id;
    }

    public String getSap_desc() {
        return sap_desc;
    }

    public void setSap_desc(String sap_desc) {
        this.sap_desc = sap_desc;
    }
}
