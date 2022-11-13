package com.example.asem.api;

import com.example.asem.api.model.AfdellingModel;
import com.example.asem.api.model.Aset;
import com.example.asem.api.model.AsetJenisModel;
import com.example.asem.api.model.AsetKodeModel;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetModel;
import com.example.asem.api.model.AsetTipe;
import com.example.asem.api.model.SubUnitModel;
import com.example.asem.api.model.UnitModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AsetInterface {


    @GET("aset/{id}/")
    Call<AsetModel> getAset(@Path("id") int asetId);

    @GET("afdelling/")
    Call<AfdellingModel> getAfdelling();

    @GET("aset-jenis/")
    Call<AsetJenisModel> getAsetJenis();

    @GET("aset-kode/")
    Call<AsetKodeModel> getAsetKode();

    @GET("sub-unit/")
    Call<SubUnitModel> getSubUnit();

    @GET("unit/")
    Call<UnitModel> getUnit();

    @GET("aset-tipe/")
    Call<List<AsetTipe>> getAsetTipe();

    @GET("aset-kondisi/")
    Call<List<AsetKondisi>> getAsetKondisi();


    @Multipart
    @POST("aset/{id}")
    Call<Aset> editAset(
            @Path("id") int asetId,
            @Part("id") int id,
            @Part("aset_name") RequestBody aset_name,
            @Part("aset_tipe") RequestBody aset_tipe,
            @Part("aset_jenis") RequestBody aset_jenis,
            @Part("aset_kondisi") RequestBody aset_kondisi,
            @Part("aset_sub_unit") RequestBody aset_sub_unit,
            @Part("aset_kode") RequestBody aset_kode,
            @Part("nomor_sap") RequestBody nomor_sap,
            @Part MultipartBody.Part foto_aset1,
            @Part MultipartBody.Part foto_aset2,
            @Part MultipartBody.Part foto_aset3,
            @Part MultipartBody.Part foto_aset4,
            @Part("geo_tag1") RequestBody geo_tag1,
            @Part("geo_tag2") RequestBody geo_tag2,
            @Part("geo_tag3") RequestBody geo_tag3,
            @Part("geo_tag4") RequestBody geo_tag4,
            @Part("aset_luas") RequestBody aset_luas,
            @Part("tgl_input") RequestBody tgl_input,
            @Part("tgl_oleh") RequestBody tgl_oleh,
            @Part("nilai_residu") RequestBody nilai_residu,
            @Part("nilai_oleh") RequestBody nilai_oleh,
            @Part("nomor_bast") RequestBody nomor_bast,
            @Part("masa_susut") RequestBody masa_susut,
            @Part("keterangan") RequestBody keterangan,
            @Part MultipartBody.Part berita_acara
    );
}
