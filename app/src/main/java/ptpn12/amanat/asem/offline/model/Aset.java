package ptpn12.amanat.asem.offline.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Aset implements Parcelable {

    @SerializedName("aset_id")
    @Expose
    private Integer asetId;
    @SerializedName("aset_name")
    @Expose
    private String asetName;
    @SerializedName("berita_acara")
    @Expose
    private String beritaAcara;
    @SerializedName("file_bast")
    @Expose
    private String fileBAST;

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
    private Integer jumlahPohon;

    @SerializedName("tahun_tanam")
    @Expose
    private Integer tahun_tanam;

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
    @SerializedName("pop_pohon_saat_ini")
    @Expose
    private String pop_total_ini;
    @SerializedName("pop_master")
    @Expose
    private String pop_total_std;
    @SerializedName("pop_per_ha")
    @Expose
    private String pop_per_ha;
    @SerializedName("presentase_pop_per_ha")
    @Expose
    private String presentase_pop_per_ha;

    @SerializedName("sistem_tanam")
    @Expose
    private String sistem_tanam;

    public String getSistem_tanam() {
        return sistem_tanam;
    }

    public String getPop_per_ha() {
        return pop_per_ha;
    }

    public void setPop_per_ha(String pop_per_ha) {
        this.pop_per_ha = pop_per_ha;
    }

    public String getPresentase_pop_per_ha() {
        return presentase_pop_per_ha;
    }

    public void setPresentase_pop_per_ha(String presentase_pop_per_ha) {
        this.presentase_pop_per_ha = presentase_pop_per_ha;
    }

    public void setSistem_tanam(String sistem_tanam) {
        this.sistem_tanam = sistem_tanam;
    }

    public void setJumlahPohon(Integer jumlahPohon) {
        this.jumlahPohon = jumlahPohon;
    }

    public Integer getTahun_tanam() {
        return tahun_tanam;
    }

    public void setTahun_tanam(Integer tahun_tanam) {
        this.tahun_tanam = tahun_tanam;
    }

    public String getAlat_pengangkutan() {
        return alat_pengangkutan;
    }

    public void setAlat_pengangkutan(String alat_pengangkutan) {
        this.alat_pengangkutan = alat_pengangkutan;
    }

    public String getSatuan_luas() {
        return satuan_luas;
    }

    public void setSatuan_luas(String satuan_luas) {
        this.satuan_luas = satuan_luas;
    }

    public String getPop_total_ini() {
        return pop_total_ini;
    }

    public void setPop_total_ini(String pop_total_ini) {
        this.pop_total_ini = pop_total_ini;
    }

    public String getPop_total_std() {
        return pop_total_std;
    }

    public void setPop_total_std(String pop_total_std) {
        this.pop_total_std = pop_total_std;
    }


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

    public String getGeoTag5() {
        return geoTag5;
    }

    public void setGeoTag5(String geoTag5) {
        this.geoTag5 = geoTag5;
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

    public Integer getJumlahPohon() {
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

    public Aset(Integer asetId, String asetName, String asetTipe, String asetJenis, String asetKondisi, String asetSubUnit, String asetKode, String nomorSap, String fotoAset1, String fotoAset2, String fotoAset3, String fotoAset4,String fotoAset5, String geoTag1, String geoTag2, String geoTag3, String geoTag4, String geoTag5, Double asetLuas, String tglInput, String tglOleh, Long nilaiResidu, Long nilaiOleh, String nomorBast, String masaSusut, String keterangan, String fotoQr, String noInv, String fotoAsetQr, String statusPosisi, String unitId, String afdelingId, String userInputId, String createdAt, String updatedAt, Integer jumlahPohon, double persenKondisi, String statusReject, String ketReject, String asetFotoQrStatus,String hgu,String berita_acara,String fileBAST,String alat_pengangkutan,String satuan_luas, String popTotalIni,String popTotalStd, String popHektarIni,String popHektarStd, Integer tahun_tanam, String sistem_tanam) {
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
        this.fotoAset5 = fotoAset5;
        this.geoTag1 = geoTag1;
        this.geoTag2 = geoTag2;
        this.geoTag3 = geoTag3;
        this.geoTag4 = geoTag4;
        this.geoTag5 = geoTag5;
        this.asetLuas = asetLuas;
        this.tglInput = tglInput;
        this.tglOleh = tglOleh;
        this.nilaiResidu = nilaiResidu;
        this.nilaiOleh = nilaiOleh;
        this.nomorBast = nomorBast;
        this.masaSusut = masaSusut;
        this.keterangan = keterangan;
        this.fotoQr = fotoQr;
        this.hgu = hgu;
        this.noInv = noInv;
        this.fotoAsetQr = fotoAsetQr;
        this.statusPosisi = statusPosisi;
        this.unitId = unitId;
        this.afdelingId = afdelingId;
        this.userInputId = userInputId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.jumlahPohon = jumlahPohon;
        this.umurEkonomisInMonth = umurEkonomisInMonth;
        this.persenKondisi = persenKondisi;
        this.statusPosisiID = statusPosisiID;
        this.statusReject = statusReject;
        this.ketReject = ketReject;
        this.asetFotoQrStatus = asetFotoQrStatus;
        this.beritaAcara = berita_acara ;
        this.alat_pengangkutan = alat_pengangkutan ;
        this.satuan_luas = satuan_luas ;
        this.pop_total_ini = popTotalIni ;
        this.pop_total_std = popTotalStd ;
        this.pop_per_ha = popHektarIni ;
        this.presentase_pop_per_ha = popHektarStd ;
        this.fileBAST = fileBAST;
        this.tahun_tanam = tahun_tanam;
        this.sistem_tanam = sistem_tanam;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.asetId);
        dest.writeString(this.asetTipe);
        dest.writeString(this.asetJenis);
        dest.writeString(this.tglInput);
        dest.writeString(this.asetKode);
        dest.writeString(this.asetKondisi);
        dest.writeString(this.unitId);
        dest.writeString(this.asetSubUnit);
        dest.writeString(this.afdelingId);
        dest.writeString(this.asetName);
        dest.writeString(this.nomorSap);
        dest.writeString(this.fotoAset1);
        dest.writeString(this.fotoAset2);
        dest.writeString(this.fotoAset3);
        dest.writeString(this.fotoAset4);
        dest.writeString(this.fotoAset5);
        dest.writeString(this.geoTag1);
        dest.writeString(this.geoTag2);
        dest.writeString(this.geoTag3);
        dest.writeString(this.geoTag4);
        dest.writeString(this.geoTag5);
        dest.writeString(this.hgu);
        dest.writeDouble(this.persenKondisi);
        dest.writeDouble(this.asetLuas);
        dest.writeLong(this.nilaiOleh);
        dest.writeString(this.tglOleh);
        dest.writeString(this.masaSusut);
        dest.writeString(this.nomorBast);
        dest.writeLong(this.nilaiResidu);
        dest.writeString(this.keterangan);
        dest.writeString(String.valueOf(this.tahun_tanam));
    }
    public Aset() {
    }
    private Aset(Parcel in) {
        this.asetId = in.readInt();
        this.asetTipe = in.readString();
        this.asetKondisi = in.readString();
        this.asetJenis = in.readString();
        this.tglInput = in.readString();
        this.asetKode = in.readString();
        this.unitId = in.readString();
        this.asetSubUnit = in.readString();
        this.afdelingId = in.readString();
        this.afdelingId = in.readString();

        this.asetName = in.readString();
        this.nomorSap = in.readString();
        this.fotoAset1 = in.readString();
        this.fotoAset2 = in.readString();
        this.fotoAset3 = in.readString();
        this.fotoAset4 = in.readString();
        this.fotoAset5 = in.readString();
        this.geoTag1 = in.readString();
        this.geoTag2 = in.readString();
        this.geoTag3 = in.readString();
        this.geoTag4 = in.readString();
        this.geoTag5 = in.readString();
        this.hgu = in.readString();
        this.persenKondisi = in.readInt();
        this.asetLuas = in.readDouble();
        this.nilaiOleh = in.readLong();
        this.tglOleh = in.readString();
        this.masaSusut = in.readString();
        this.nomorBast = in.readString();
        this.nilaiResidu = in.readLong();
        this.umurEkonomisInMonth = in.readInt();
        this.keterangan = in.readString();
    }
    public static final Parcelable.Creator<Aset> CREATOR = new Parcelable.Creator<Aset>() {
        @Override
        public Aset createFromParcel(Parcel source) {
            return new Aset(source);
        }
        @Override
        public Aset[] newArray(int size) {
            return new Aset[size];
        }
    };
}

