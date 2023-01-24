package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.SerializedName;

public class Aset {

    public Aset(String aset_name, int aset_tipe, int aset_jenis, int aset_kondisi, int aset_sub_unit, int aset_kode, int nomor_sap, String foto_aset1, String foto_aset2, String foto_aset3, String foto_aset4, String geo_tag1, String geo_tag2, String geo_tag3, String geo_tag4, int aset_luas, String tgl_input, String tgl_oleh, int nilai_residu, int nilai_sap, int nomor_bast, int masa_susut, String keterangan, String foto_qr, String no_inv, String foto_aset_qr, String status_posisi, int unit_id, int afdelling_id, int user_input_id, int umur_ekonomis, String ba_file) {
        this.aset_name = aset_name;
        this.aset_tipe = aset_tipe;
        this.aset_jenis = aset_jenis;
        this.aset_kondisi = aset_kondisi;
        this.aset_sub_unit = aset_sub_unit;
        this.aset_kode = aset_kode;
        this.nomor_sap = nomor_sap;
        this.foto_aset1 = foto_aset1;
        this.foto_aset2 = foto_aset2;
        this.foto_aset3 = foto_aset3;
        this.foto_aset4 = foto_aset4;
        this.geo_tag1 = geo_tag1;
        this.geo_tag2 = geo_tag2;
        this.geo_tag3 = geo_tag3;
        this.geo_tag4 = geo_tag4;
        this.aset_luas = aset_luas;
        this.tgl_input = tgl_input;
        this.tgl_oleh = tgl_oleh;
        this.nilai_residu = nilai_residu;
        this.nilai_sap = nilai_sap;
        this.nomor_bast = nomor_bast;
        this.masa_susut = masa_susut;
        this.keterangan = keterangan;
        this.foto_qr = foto_qr;
        this.no_inv = no_inv;
        this.foto_aset_qr = foto_aset_qr;
        this.status_posisi = status_posisi;
        this.unit_id = unit_id;
        this.afdelling_id = afdelling_id;
        this.user_input_id = user_input_id;
        this.umur_ekonomis_in_month = umur_ekonomis;
        this.ba_file = ba_file;
    }

    @SerializedName("aset_name")
    private String aset_name;
    @SerializedName("aset_tipe")
    private int aset_tipe;
    @SerializedName("aset_jenis")
    private int aset_jenis;
    @SerializedName("aset_kondisi")
    private int aset_kondisi;
    @SerializedName("aset_sub_unit")
    private int aset_sub_unit;
    @SerializedName("aset_kode")
    private int aset_kode;
    @SerializedName("nomor_sap")
    private int nomor_sap;
    @SerializedName("foto_aset1")
    private String foto_aset1;
    @SerializedName("foto_aset2")
    private String foto_aset2;
    @SerializedName("foto_aset3")
    private String foto_aset3;
    @SerializedName("foto_aset4")
    private String foto_aset4;

    @SerializedName("geo_tag1")
    private String geo_tag1;
    @SerializedName("geo_tag2")
    private String geo_tag2;
    @SerializedName("geo_tag3")
    private String geo_tag3;
    @SerializedName("geo_tag4")
    private String geo_tag4;
    @SerializedName("aset_luas")
    private int aset_luas;
    @SerializedName("tgl_input")
    private String tgl_input;
    @SerializedName("tgl_oleh")
    private String tgl_oleh;
    @SerializedName("nilai_residu")
    private int nilai_residu;
    @SerializedName("nilai_sap")
    private int nilai_sap;
    @SerializedName("nomor_bast")
    private int nomor_bast;
    @SerializedName("masa_susut")
    private int masa_susut;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("foto_qr")
    private String foto_qr;

    @SerializedName("no_inv")
    private String no_inv;
    @SerializedName("foto_aset_qr")
    private String foto_aset_qr;
    @SerializedName("status_posisi")
    private String status_posisi;
    @SerializedName("unit_id")
    private int unit_id;
    @SerializedName("afdelling_id")
    private int afdelling_id;
    @SerializedName("user_input_id")
    private int user_input_id;
    @SerializedName("umur_ekonomis_in_month")
    private int umur_ekonomis_in_month;
    @SerializedName("ba_file")
    private String ba_file;

    public String getBa_file() {
        return ba_file;
    }

    public void setBa_file(String ba_file) {
        this.ba_file = ba_file;
    }

    public int getNilai_sap() {
        return nilai_sap;
    }

    public void setNilai_sap(int nilai_sap) {
        this.nilai_sap = nilai_sap;
    }

    public int getUmur_ekonomis_in_month() {
        return umur_ekonomis_in_month;
    }

    public void setUmur_ekonomis_in_month(int umur_ekonomis) {
        this.umur_ekonomis_in_month = umur_ekonomis;
    }

    public String getAset_name() {
        return aset_name;
    }

    public void setAset_name(String aset_name) {
        this.aset_name = aset_name;
    }

    public int getAset_tipe() {
        return aset_tipe;
    }

    public void setAset_tipe(int aset_tipe) {
        this.aset_tipe = aset_tipe;
    }

    public int getAset_jenis() {
        return aset_jenis;
    }

    public void setAset_jenis(int aset_jenis) {
        this.aset_jenis = aset_jenis;
    }

    public int getAset_kondisi() {
        return aset_kondisi;
    }

    public void setAset_kondisi(int aset_kondisi) {
        this.aset_kondisi = aset_kondisi;
    }

    public int getAset_sub_unit() {
        return aset_sub_unit;
    }

    public void setAset_sub_unit(int aset_sub_unit) {
        this.aset_sub_unit = aset_sub_unit;
    }

    public int getAset_kode() {
        return aset_kode;
    }

    public void setAset_kode(int aset_kode) {
        this.aset_kode = aset_kode;
    }

    public int getNomor_sap() {
        return nomor_sap;
    }

    public void setNomor_sap(int nomor_sap) {
        this.nomor_sap = nomor_sap;
    }

    public String getFoto_aset1() {
        return foto_aset1;
    }

    public void setFoto_aset1(String foto_aset1) {
        this.foto_aset1 = foto_aset1;
    }

    public String getFoto_aset2() {
        return foto_aset2;
    }

    public void setFoto_aset2(String foto_aset2) {
        this.foto_aset2 = foto_aset2;
    }

    public String getFoto_aset3() {
        return foto_aset3;
    }

    public void setFoto_aset3(String foto_aset3) {
        this.foto_aset3 = foto_aset3;
    }

    public String getFoto_aset4() {
        return foto_aset4;
    }

    public void setFoto_aset4(String foto_aset4) {
        this.foto_aset4 = foto_aset4;
    }

    public String getGeo_tag1() {
        return geo_tag1;
    }

    public void setGeo_tag1(String geo_tag1) {
        this.geo_tag1 = geo_tag1;
    }

    public String getGeo_tag2() {
        return geo_tag2;
    }

    public void setGeo_tag2(String geo_tag2) {
        this.geo_tag2 = geo_tag2;
    }

    public String getGeo_tag3() {
        return geo_tag3;
    }

    public void setGeo_tag3(String geo_tag3) {
        this.geo_tag3 = geo_tag3;
    }

    public String getGeo_tag4() {
        return geo_tag4;
    }

    public void setGeo_tag4(String geo_tag4) {
        this.geo_tag4 = geo_tag4;
    }

    public int getAset_luas() {
        return aset_luas;
    }

    public void setAset_luas(int aset_luas) {
        this.aset_luas = aset_luas;
    }

    public String getTgl_input() {
        return tgl_input;
    }

    public void setTgl_input(String tgl_input) {
        this.tgl_input = tgl_input;
    }

    public String getTgl_oleh() {
        return tgl_oleh;
    }

    public void setTgl_oleh(String tgl_oleh) {
        this.tgl_oleh = tgl_oleh;
    }

    public int getNilai_residu() {
        return nilai_residu;
    }

    public void setNilai_residu(int nilai_residu) {
        this.nilai_residu = nilai_residu;
    }


    public int getNomor_bast() {
        return nomor_bast;
    }

    public void setNomor_bast(int nomor_bast) {
        this.nomor_bast = nomor_bast;
    }

    public int getMasa_susut() {
        return masa_susut;
    }

    public void setMasa_susut(int masa_susut) {
        this.masa_susut = masa_susut;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFoto_qr() {
        return foto_qr;
    }

    public void setFoto_qr(String foto_qr) {
        this.foto_qr = foto_qr;
    }

    public String getNo_inv() {
        return no_inv;
    }

    public void setNo_inv(String no_inv) {
        this.no_inv = no_inv;
    }

    public String getFoto_aset_qr() {
        return foto_aset_qr;
    }

    public void setFoto_aset_qr(String foto_aset_qr) {
        this.foto_aset_qr = foto_aset_qr;
    }

    public String getStatus_posisi() {
        return status_posisi;
    }

    public void setStatus_posisi(String status_posisi) {
        this.status_posisi = status_posisi;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public int getAfdelling_id() {
        return afdelling_id;
    }

    public void setAfdelling_id(int afdelling_id) {
        this.afdelling_id = afdelling_id;
    }

    public int getUser_input_id() {
        return user_input_id;
    }

    public void setUser_input_id(int user_input_id) {
        this.user_input_id = user_input_id;
    }

}
