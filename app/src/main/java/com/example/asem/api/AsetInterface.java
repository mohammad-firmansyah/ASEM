package com.example.asem.api;

import com.example.asem.MainActivity;
import com.example.asem.api.model.AfdellingModel;
import com.example.asem.api.model.Aset;
import com.example.asem.api.model.AsetJenisModel;
import com.example.asem.api.model.AsetKodeModel;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetModel;
import com.example.asem.api.model.AsetTipe;
import com.example.asem.api.model.Data;
import com.example.asem.api.model.SubUnitModel;
import com.example.asem.api.model.UnitModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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


//    @Multipart
//    @POST("aset/edit")
//    Call<AsetModel> editAset(
//
//            @Part("aset_name") RequestBody requestAsetName,
//            @Part("aset_tipe") RequestBody requestTipeAset,
//            @Part("aset_jenis") RequestBody requestJenisAset,
//            @Part("aset_kondisi") RequestBody requestKondisiAset,
//            @Part("aset_sub_unit") RequestBody requestSubUnit,
//            @Part("aset_kode") RequestBody requestKodeAset,
//            @Part("nomor_sap") RequestBody requestNomorAsetSAP,
//            @Part MultipartBody.Part foto_aset1,
//            @Part MultipartBody.Part foto_aset2,
//            @Part MultipartBody.Part foto_aset3,
//            @Part MultipartBody.Part foto_aset4,
//            @Part("geo_tag1") RequestBody geo_tag1,
//            @Part("geo_tag2") RequestBody geo_tag2,
//            @Part("geo_tag3") RequestBody geo_tag3,
//            @Part("geo_tag4") RequestBody geo_tag4,
//            @Part("aset_luas") RequestBody aset_luas,
//            @Part("tgl_input") RequestBody tgl_input,
//            @Part("tgl_oleh") RequestBody tgl_oleh,
//            @Part("nomor_bast") RequestBody nomor_bast,
//            @Part("masa_susut") RequestBody masa_susut,
//            @Part("keterangan") RequestBody keterangan,
//            @Part MultipartBody.Part foto_qr,
//            @Part("no_inv") RequestBody requestNoInv,
//            @Part MultipartBody.Part foto_aset_qr,
//            @Part("status_posisi") RequestBody requestStatusPosisi,
//            @Part("nilai_residu") RequestBody nilai_residu,
//            @Part("afdeling_id") RequestBody requestAfdellingId,
//            @Part("user_input_id") RequestBody requestUserInputId,
//            @Part("nilai_oleh") RequestBody requestNilaiOleh,
//            @Part("unit_id") RequestBody requestUnitId
//            );
//
//
//    @Multipart
    @POST("aset/edit")
    Call<AsetModel> editAset(
            @Header("Content-Type") String contentType, @Body MultipartBody body
            );

    @POST("aset/")
    Call<AsetModel> addAset(
            @Header("Content-Type") String contentType, @Body MultipartBody body
    );
        //    @Multipart
        //    @POST("aset/{id}")
        //    Call<AsetModel> editAset(int i, RequestBody requestNamaAset, RequestBody requestTipeAset, RequestBody requestJenisAset, RequestBody requestKondisiAset, RequestBody requestKodeAset, RequestBody requestNomorAsetSAP, MultipartBody.Part img1Part, MultipartBody.Part img2Part, MultipartBody.Part img3Part, MultipartBody.Part img4Part, RequestBody requestGeoTag1, RequestBody requestGeoTag2, RequestBody requestGeoTag3, RequestBody requestGeoTag4, RequestBody requestGeoTag41, RequestBody requestLuasAset, RequestBody requestTglOleh, RequestBody requestNilaiResidu, RequestBody requestNilaiAsetSAP, RequestBody requestNomorBAST, RequestBody requestMasaSusut, RequestBody requestKeterangan, String ba_file);
}
