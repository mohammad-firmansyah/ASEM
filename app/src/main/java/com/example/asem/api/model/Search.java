package com.example.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<Search> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Search> getData() {
        return data;
    }

    public void setData(List<Search> data) {
        this.data = data;
    }

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
    private Integer nilaiResidu;
    @SerializedName("nilai_oleh")
    @Expose
    private Integer nilaiOleh;
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
    private String fotoQr;
    @SerializedName("no_inv")
    @Expose
    private String noInv;
    @SerializedName("foto_aset_qr")
    @Expose
    private Object fotoAsetQr;
    @SerializedName("status_posisi")
    @Expose
    private String statusPosisi;
    @SerializedName("unit_id")
    @Expose
    private String unitId;
    @SerializedName("afdeling_id")
    @Expose
    private Object afdelingId;
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
    private Object jumlahPohon;
    @SerializedName("persen_kondisi")
    @Expose
    private Integer persenKondisi;
    @SerializedName("berita_acara")
    @Expose
    private Object beritaAcara;
    @SerializedName("status_reject")
    @Expose
    private Object statusReject;
    @SerializedName("ket_reject")
    @Expose
    private Object ketReject;
    @SerializedName("umur_ekonomis_in_month")
    @Expose
    private Integer umurEkonomisInMonth;

    @SerializedName("status_posisi_id")
    @Expose
    private Integer statusPosisiID;

    @SerializedName("aset_foto_qr_status")
    @Expose
    private String asetFotoQrStatus;

    public String getAsetFotoQrStatus() {
        return asetFotoQrStatus;
    }

    public void setAsetFotoQrStatus(String asetFotoQrStatus) {
        this.asetFotoQrStatus = asetFotoQrStatus;
    }

    public Integer getStatusPosisiID() {
        return statusPosisiID;
    }

    public void setStatusPosisiID(Integer statusPosisiID) {
        this.statusPosisiID = statusPosisiID;
    }

    public void setKetReject(Object ketReject) {
        this.ketReject = ketReject;
    }

    public Integer getUmurEkonomisInMonth() {
        return umurEkonomisInMonth;
    }

    public void setUmurEkonomisInMonth(Integer umurEkonomis) {
        this.umurEkonomisInMonth = umurEkonomis;
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

    public Integer getNilaiResidu() {
        return nilaiResidu;
    }

    public void setNilaiResidu(Integer nilaiResidu) {
        this.nilaiResidu = nilaiResidu;
    }

    public Integer getNilaiOleh() {
        return nilaiOleh;
    }

    public void setNilaiOleh(Integer nilaiOleh) {
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

    public String getFotoQr() {
        return fotoQr;
    }

    public void setFotoQr(String fotoQr) {
        this.fotoQr = fotoQr;
    }

    public String getNoInv() {
        return noInv;
    }

    public void setNoInv(String noInv) {
        this.noInv = noInv;
    }

    public Object getFotoAsetQr() {
        return fotoAsetQr;
    }

    public void setFotoAsetQr(Object fotoAsetQr) {
        this.fotoAsetQr = fotoAsetQr;
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

    public Object getAfdelingId() {
        return afdelingId;
    }

    public void setAfdelingId(Object afdelingId) {
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

    public Object getJumlahPohon() {
        return jumlahPohon;
    }

    public void setJumlahPohon(Object jumlahPohon) {
        this.jumlahPohon = jumlahPohon;
    }

    public Integer getPersenKondisi() {
        return persenKondisi;
    }

    public void setPersenKondisi(Integer persenKondisi) {
        this.persenKondisi = persenKondisi;
    }

    public Object getBeritaAcara() {
        return beritaAcara;
    }

    public void setBeritaAcara(Object beritaAcara) {
        this.beritaAcara = beritaAcara;
    }

    public Object getStatusReject() {
        return statusReject;
    }

    public void setStatusReject(Object statusReject) {
        this.statusReject = statusReject;
    }

    public Object getKetReject() {
        return ketReject;
    }

    public void setKetRejec(Object ketReject) {
        this.ketReject = ketReject;
    }

}