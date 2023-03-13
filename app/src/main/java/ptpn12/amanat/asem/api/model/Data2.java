package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data2 {

    @SerializedName("aset_id")
    @Expose
    private Integer asetId;
    @SerializedName("aset_name")
    @Expose
    private String asetName;
    @SerializedName("aset_tipe")
    @Expose
    private String asetTipe;
    @SerializedName("aset_jenis")
    @Expose
    private String asetJenis;
    @SerializedName("aset_kondisi")
    @Expose
    private String asetKondisi;
    @SerializedName("aset_sub_unit")
    @Expose
    private String asetSubUnit;
    @SerializedName("aset_kode")
    @Expose
    private String asetKode;
    @SerializedName("nomor_sap")
    @Expose
    private String nomorSap;
    @SerializedName("foto_aset1")
    @Expose
    private String fotoAset1;
    @SerializedName("foto_aset2")
    @Expose
    private String fotoAset2;
    @SerializedName("foto_aset3")
    @Expose
    private String fotoAset3;
    @SerializedName("foto_aset4")
    @Expose
    private String fotoAset4;
    @SerializedName("foto_aset5")
    @Expose
    private String fotoAset5;
    @SerializedName("geo_tag1")
    @Expose
    private String geoTag1;
    @SerializedName("geo_tag2")
    @Expose
    private String geoTag2;
    @SerializedName("geo_tag3")
    @Expose
    private String geoTag3;
    @SerializedName("geo_tag4")
    @Expose
    private String geoTag4;
    @SerializedName("geo_tag5")
    @Expose
    private String geoTag5;
    @SerializedName("aset_luas")
    @Expose
    private Double asetLuas;
    @SerializedName("tgl_input")
    @Expose
    private String tglInput;
    @SerializedName("tgl_oleh")
    @Expose
    private String tglOleh;
    @SerializedName("nilai_residu")
    @Expose
    private Long nilaiResidu;
    @SerializedName("nilai_oleh")
    @Expose
    private Long nilaiOleh;
    @SerializedName("nomor_bast")
    @Expose
    private String nomorBast;
    @SerializedName("masa_susut")
    @Expose
    private String masaSusut;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("foto_qr")
    @Expose
    private Object fotoQr;
    @SerializedName("no_inv")
    @Expose
    private Object noInv;
    @SerializedName("foto_aset_qr")
    @Expose
    private Object fotoAsetQr;
    @SerializedName("foto_aset_qr_2")
    @Expose
    private Object fotoAsetQr2;

    @SerializedName("status_posisi")
    @Expose
    private String statusPosisi;
    @SerializedName("unit_id")
    @Expose
    private String unitId;
    @SerializedName("afdeling_id")
    @Expose
    private String afdelingId;
    @SerializedName("user_input_id")
    @Expose
    private Object userInputId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("jumlah_pohon")
    @Expose
    private Long jumlahPohon;

    public void setJumlahPohon(Long jumlahPohon) {
        this.jumlahPohon = jumlahPohon;
    }

    @SerializedName("umur_ekonomis_in_month")
    @Expose
    private Integer umurEkonomisInMonth;
    @SerializedName("persen_kondisi")
    @Expose

    private double persenKondisi;

    @SerializedName("status_posisi_id")
    @Expose
    private Integer statusPosisiID;

    @SerializedName("status_reject")
    @Expose
    private String statusReject;

    @SerializedName("ket_reject")
    @Expose
    private String ketReject;

    @SerializedName("aset_foto_qr_status")
    @Expose
    private String asetFotoQrStatus;

    @SerializedName("hgu")
    @Expose
    private String hgu;

    @SerializedName("alat_pengangkutan")
    @Expose
    private String alat_pengangkutan;

    @SerializedName("satuan_luas")
    @Expose
    private String satuan_luas;
    @SerializedName("berita_acara")
    @Expose
    private String beritaAcara;

    @SerializedName("file_bast")
    @Expose
    private String fileBAST;

    public String getBeritaAcara() {
        return beritaAcara;
    }

    public void setBeritaAcara(String beritaAcara) {
        this.beritaAcara = beritaAcara;
    }

    public String getFileBAST() {
        return fileBAST;
    }

    public void setFileBAST(String fileBAST) {
        this.fileBAST = fileBAST;
    }

    public String getSatuan_luas() {
        return satuan_luas;
    }

    public void setSatuan_luas(String satuan_luas) {
        this.satuan_luas = satuan_luas;
    }

    public String getAlat_pengangkutan() {
        return alat_pengangkutan;
    }

    public void setAlat_pengangkutan(String alat_pengangkutan) {
        this.alat_pengangkutan = alat_pengangkutan;
    }

    @SerializedName("pop_pohon_saat_ini")
    @Expose
    private Double popPohonSaatIni;

    @SerializedName("pop_standar")
    @Expose
    private Double popStandar;

    @SerializedName("pop_per_ha")
    @Expose
    private Double popPerHA;

    @SerializedName("presentase_pop_per_ha")
    @Expose
    private Double popPersentasePerHA;

    @SerializedName("tahun_tanam")
    @Expose
    private String tahunTanam;

    @SerializedName("sistem_tanam")
    @Expose
    private String sistemTanam;

    public String getSistemTanam() {
        return sistemTanam;
    }

    public void setSistemTanam(String sistemTanam) {
        this.sistemTanam = sistemTanam;
    }

    public String getTahunTanam() {
        return tahunTanam;
    }

    public void setTahunTanam(String tahunTanam) {
        this.tahunTanam = tahunTanam;
    }

    public Double getPopPohonSaatIni() {
        return popPohonSaatIni;
    }

    public void setPopPohonSaatIni(Double popPohonSaatIni) {
        this.popPohonSaatIni = popPohonSaatIni;
    }

    public Double getPopStandar() {
        return popStandar;
    }

    public void setPopStandar(Double popStandar) {
        this.popStandar = popStandar;
    }

    public Double getPopPerHA() {
        return popPerHA;
    }

    public void setPopPerHA(Double popPerHA) {
        this.popPerHA = popPerHA;
    }

    public Double getPopPersentasePerHA() {
        return popPersentasePerHA;
    }

    public void setPopPersentasePerHA(Double popPersentasePerHA) {
        this.popPersentasePerHA = popPersentasePerHA;
    }

    public String getHgu() {
        return hgu;
    }

    public void setHgu(String hgu) {
        this.hgu = hgu;
    }

    public String getAsetFotoQrStatus() {
        return asetFotoQrStatus;
    }

    public void setAsetFotoQrStatus(String asetFotoQrStatus) {
        this.asetFotoQrStatus = asetFotoQrStatus;
    }

    public String getStatusReject() { return statusReject; }

    public void setStatusReject(String statusReject) {
        this.statusReject = statusReject;
    }

    public String getKetReject() {
        return ketReject;
    }

    public void setKetReject(String ketReject) {
        this.ketReject = ketReject;
    }

    public double getPersenKondisi() {
        return persenKondisi;
    }

    public void setPersenKondisi(double persenKondisi) {
        this.persenKondisi = persenKondisi;
    }

    public Integer getStatusPosisiID() {
        return statusPosisiID;
    }

    public void setStatusPosisiID(Integer statusPosisiID) {
        this.statusPosisiID = statusPosisiID;
    }

    public Integer getAsetId() {
        return asetId;
    }

    public void setAsetId(Integer asetId) {
        this.asetId = asetId;
    }

    public String getAsetName() {
        return asetName;
    }

    public void setAsetName(String asetName) {
        this.asetName = asetName;
    }

    public String getAsetTipe() {
        return asetTipe;
    }

    public void setAsetTipe(String asetTipe) {
        this.asetTipe = asetTipe;
    }

    public String getAsetJenis() {
        return asetJenis;
    }

    public void setAsetJenis(String asetJenis) {
        this.asetJenis = asetJenis;
    }

    public String getAsetKondisi() {
        return asetKondisi;
    }

    public void setAsetKondisi(String asetKondisi) {
        this.asetKondisi = asetKondisi;
    }

    public String getAsetSubUnit() {
        return asetSubUnit;
    }

    public void setAsetSubUnit(String asetSubUnit) {
        this.asetSubUnit = asetSubUnit;
    }

    public String getAsetKode() {
        return asetKode;
    }

    public void setAsetKode(String asetKode) {
        this.asetKode = asetKode;
    }

    public String getNomorSap() {
        return nomorSap;
    }

    public void setNomorSap(String nomorSap) {
        this.nomorSap = nomorSap;
    }

    public String getFotoAset1() {
        return fotoAset1;
    }

    public void setFotoAset1(String fotoAset1) {
        this.fotoAset1 = fotoAset1;
    }

    public String getFotoAset2() {
        return fotoAset2;
    }

    public void setFotoAset2(String fotoAset2) {
        this.fotoAset2 = fotoAset2;
    }

    public String getFotoAset3() {
        return fotoAset3;
    }

    public void setFotoAset3(String fotoAset3) {
        this.fotoAset3 = fotoAset3;
    }

    public String getFotoAset4() {
        return fotoAset4;
    }

    public void setFotoAset4(String fotoAset4) {
        this.fotoAset4 = fotoAset4;
    }

    public String getFotoAset5() {
        return fotoAset5;
    }

    public void setFotoAset5(String fotoAset5) {
        this.fotoAset5 = fotoAset5;
    }

    public String getGeoTag5() {
        return geoTag5;
    }

    public void setGeoTag5(String geoTag5) {
        this.geoTag5 = geoTag5;
    }

    public String getGeoTag1() {
        return geoTag1;
    }

    public void setGeoTag1(String geoTag1) {
        this.geoTag1 = geoTag1;
    }

    public String getGeoTag2() {
        return geoTag2;
    }

    public void setGeoTag2(String geoTag2) {
        this.geoTag2 = geoTag2;
    }

    public String getGeoTag3() {
        return geoTag3;
    }

    public void setGeoTag3(String geoTag3) {
        this.geoTag3 = geoTag3;
    }

    public String getGeoTag4() {
        return geoTag4;
    }

    public void setGeoTag4(String geoTag4) {
        this.geoTag4 = geoTag4;
    }

    public Double getAsetLuas() {
        return asetLuas;
    }

    public void setAsetLuas(Double asetLuas) {
        this.asetLuas = asetLuas;
    }

    public String getTglInput() {
        return tglInput;
    }

    public void setTglInput(String tglInput) {
        this.tglInput = tglInput;
    }

    public String getTglOleh() {
        return tglOleh;
    }

    public void setTglOleh(String tglOleh) {
        this.tglOleh = tglOleh;
    }

    public Long getNilaiResidu() {
        return nilaiResidu;
    }

    public void setNilaiResidu(Long nilaiResidu) {
        this.nilaiResidu = nilaiResidu;
    }

    public Long getNilaiOleh() {
        return nilaiOleh;
    }

    public void setNilaiOleh(Long nilaiOleh) {
        this.nilaiOleh = nilaiOleh;
    }

    public String getNomorBast() {
        return nomorBast;
    }

    public void setNomorBast(String nomorBast) {
        this.nomorBast = nomorBast;
    }

    public String getMasaSusut() {
        return masaSusut;
    }

    public void setMasaSusut(String masaSusut) {
        this.masaSusut = masaSusut;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Object getFotoQr() {
        return fotoQr;
    }

    public void setFotoQr(Object fotoQr) {
        this.fotoQr = fotoQr;
    }

    public Object getNoInv() {
        return noInv;
    }

    public void setNoInv(Object noInv) {
        this.noInv = noInv;
    }

    public Object getFotoAsetQr() {
        return fotoAsetQr;
    }

    public void setFotoAsetQr(Object fotoAsetQr) {
        this.fotoAsetQr = fotoAsetQr;
    }

    public Object getFotoAsetQr2() {
        return fotoAsetQr2;
    }

    public void setFotoAsetQr2(Object fotoAsetQr2) {
        this.fotoAsetQr2 = fotoAsetQr2;
    }

    public String getStatusPosisi() {
        return statusPosisi;
    }

    public void setStatusPosisi(String statusPosisi) {
        this.statusPosisi = statusPosisi;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAfdelingId() {
        return afdelingId;
    }

    public void setAfdelingId(String afdelingId) {
        this.afdelingId = afdelingId;
    }

    public Object getUserInputId() {
        return userInputId;
    }

    public void setUserInputId(Object userInputId) {
        this.userInputId = userInputId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getJumlahPohon() {
        return jumlahPohon;
    }

//    public void setJumlahPohon(Object jumlahPohon) {
//        this.jumlahPohon = jumlahPohon;
//    }

    public Integer getUmurEkonomisInMonth() {
        return umurEkonomisInMonth;
    }

    public void setUmurEkonomisInMonth(Integer umurEkonomisInMonth) {
        this.umurEkonomisInMonth = umurEkonomisInMonth;
    }

}
