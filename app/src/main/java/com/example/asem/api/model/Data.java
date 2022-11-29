package com.example.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("aset_id")
    @Expose
    private Integer asetId;
    @SerializedName("aset_name")
    @Expose
    private String asetName;
    @SerializedName("aset_tipe")
    @Expose
    private Integer asetTipe;
    @SerializedName("aset_jenis")
    @Expose
    private Integer asetJenis;
    @SerializedName("aset_kondisi")
    @Expose
    private Integer asetKondisi;
    @SerializedName("aset_sub_unit")
    @Expose
    private Integer asetSubUnit;
    @SerializedName("aset_kode")
    @Expose
    private Integer asetKode;
    @SerializedName("nomor_sap")
    @Expose
    private Integer nomorSap;
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
    private Integer asetLuas;
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
    private String fotoAsetQr;
    @SerializedName("status_posisi")
    @Expose
    private Integer statusPosisi;
    @SerializedName("unit_id")
    @Expose
    private Integer unitId;
    @SerializedName("afdeling_id")
    @Expose
    private Integer afdelingId;
    @SerializedName("user_input_id")
    @Expose
    private Integer userInputId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("ba_file")
    @Expose
    private String baFile;
    @SerializedName("umur_ekonomis_in_month")
    @Expose
    private Integer umurEkonomisInMonth;


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

    public Integer getAsetTipe() {
        return asetTipe;
    }

    public void setAsetTipe(Integer asetTipe) {
        this.asetTipe = asetTipe;
    }

    public Integer getAsetJenis() {
        return asetJenis;
    }

    public void setAsetJenis(Integer asetJenis) {
        this.asetJenis = asetJenis;
    }

    public Integer getAsetKondisi() {
        return asetKondisi;
    }

    public void setAsetKondisi(Integer asetKondisi) {
        this.asetKondisi = asetKondisi;
    }

    public Integer getAsetSubUnit() {
        return asetSubUnit;
    }

    public void setAsetSubUnit(Integer asetSubUnit) {
        this.asetSubUnit = asetSubUnit;
    }

    public Integer getAsetKode() {
        return asetKode;
    }

    public void setAsetKode(Integer asetKode) {
        this.asetKode = asetKode;
    }

    public Integer getNomorSap() {
        return nomorSap;
    }

    public void setNomorSap(Integer nomorSap) {
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

    public Integer getAsetLuas() {
        return asetLuas;
    }

    public void setAsetLuas(Integer asetLuas) {
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

    public String getFotoAsetQr() {
        return fotoAsetQr;
    }

    public void setFotoAsetQr(String fotoAsetQr) {
        this.fotoAsetQr = fotoAsetQr;
    }

    public Integer getStatusPosisi() {
        return statusPosisi;
    }

    public void setStatusPosisi(Integer statusPosisi) {
        this.statusPosisi = statusPosisi;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getAfdelingId() {
        return afdelingId;
    }

    public void setAfdelingId(Integer afdelingId) {
        this.afdelingId = afdelingId;
    }

    public Integer getUserInputId() {
        return userInputId;
    }

    public void setUserInputId(Integer userInputId) {
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

    public String getBaFile() {
        return baFile;
    }

    public void setBaFile(String baFile) {
        this.baFile = baFile;
    }

    public Integer getUmurEkonomisInMonth() {
        return umurEkonomisInMonth;
    }

    public void setUmurEkonomisInMonth(Integer umurEkonomisInMonth) {
        this.umurEkonomisInMonth = umurEkonomisInMonth;
    }

    public Data(Integer asetId, String asetName, Integer asetTipe, Integer asetJenis, Integer asetKondisi, Integer asetSubUnit, Integer asetKode, Integer nomorSap, String fotoAset1, String fotoAset2, String fotoAset3, String fotoAset4, String geoTag1, String geoTag2, String geoTag3, String geoTag4, Integer asetLuas, String tglInput, String tglOleh, Integer nilaiResidu, Integer nilaiOleh, String nomorBast, String masaSusut, String keterangan, String fotoQr, String noInv, String fotoAsetQr, Integer statusPosisi, Integer unitId, Integer afdelingId, Integer userInputId, String createdAt, String updatedAt, String baFile, Integer umurEkonomisInMonth) {
        this.asetId = asetId;
        this.asetName = asetName;
        this.asetTipe = asetTipe;
        this.asetJenis = asetJenis;
        this.asetKondisi = asetKondisi;
        this.asetSubUnit = asetSubUnit;
        this.asetKode = asetKode;
        this.nomorSap = nomorSap;
        this.fotoAset1 = fotoAset1;
        this.fotoAset2 = fotoAset2;
        this.fotoAset3 = fotoAset3;
        this.fotoAset4 = fotoAset4;
        this.geoTag1 = geoTag1;
        this.geoTag2 = geoTag2;
        this.geoTag3 = geoTag3;
        this.geoTag4 = geoTag4;
        this.asetLuas = asetLuas;
        this.tglInput = tglInput;
        this.tglOleh = tglOleh;
        this.nilaiResidu = nilaiResidu;
        this.nilaiOleh = nilaiOleh;
        this.nomorBast = nomorBast;
        this.masaSusut = masaSusut;
        this.keterangan = keterangan;
        this.fotoQr = fotoQr;
        this.noInv = noInv;
        this.fotoAsetQr = fotoAsetQr;
        this.statusPosisi = statusPosisi;
        this.unitId = unitId;
        this.afdelingId = afdelingId;
        this.userInputId = userInputId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.baFile = baFile;
        this.umurEkonomisInMonth = umurEkonomisInMonth;
    }
}
