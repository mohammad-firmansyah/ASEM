package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataAllSpinner {

    @SerializedName("aset_tipe")
    @Expose
    private List<AsetTipe> asetTipe = null;
    @SerializedName("aset_jenis")
    @Expose
    private List<AsetJenis> asetJenis = null;

    @SerializedName("aset_kondisi")
    @Expose
    private List<AsetKondisi> asetKondisi = null;
    @SerializedName("aset_kode")
    @Expose
    private List<AsetKode2> asetKode = null;
    @SerializedName("unit")
    @Expose
    private List<Unit> unit = null;
    @SerializedName("sub_unit")
    @Expose
    private List<SubUnit> subUnit = null;
    @SerializedName("afdeling")
    @Expose
    private List<Afdelling> afdeling = null;

    @SerializedName("sap")
    @Expose
    private List<Sap> sap = null;

    @SerializedName("alat_pengangkutan")
    @Expose
    private List<AlatAngkut> alatAngkut = null;

    @SerializedName("sistem_tanam")
    @Expose
    private List<SistemTanam> sistemTanam = null;

    public List<SistemTanam> getSistemTanam() {
        return sistemTanam;
    }

    public void setSistemTanam(List<SistemTanam> sistemTanam) {
        this.sistemTanam = sistemTanam;
    }

    public List<AlatAngkut> getAlatAngkut() {
        return alatAngkut;
    }

    public void setAlatAngkut(List<AlatAngkut> alatAngkut) {
        this.alatAngkut = alatAngkut;
    }

    public List<Sap> getSap() {
        return sap;
    }

    public void setSap(List<Sap> sap) {
        this.sap = sap;
    }

    public List<AsetTipe> getAsetTipe() {
        return asetTipe;
    }

    public void setAsetTipe(List<AsetTipe> asetTipe) {
        this.asetTipe = asetTipe;
    }

    public List<AsetJenis> getAsetJenis() {
        return asetJenis;
    }

    public void setAsetJenis(List<AsetJenis> asetJenis) {
        this.asetJenis = asetJenis;
    }

    public List<AsetKondisi> getAsetKondisi() {
        return asetKondisi;
    }

    public void setAsetKondisi(List<AsetKondisi> asetKondisi) {
        this.asetKondisi = asetKondisi;
    }

    public List<AsetKode2> getAsetKode() {
        return asetKode;
    }

    public void setAsetKode(List<AsetKode2> asetKode) {
        this.asetKode = asetKode;
    }

    public List<SubUnit> getSubUnit() {
        return subUnit;
    }

    public void setSubUnit(List<SubUnit> subUnit) {
        this.subUnit = subUnit;
    }

    public List<Afdelling> getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(List<Afdelling> afdeling) {
        this.afdeling = afdeling;
    }

    public List<Unit> getUnit() {
        return unit;
    }

    public void setUnit(List<Unit> unit) {
        this.unit = unit;
    }
}
