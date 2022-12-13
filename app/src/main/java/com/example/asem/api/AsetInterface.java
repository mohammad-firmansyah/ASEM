package com.example.asem.api;

import com.example.asem.api.model.AfdellingModel;
import com.example.asem.api.model.AsetApproveModel;
import com.example.asem.api.model.AsetJenisModel;
import com.example.asem.api.model.AsetKodeModel2;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetModel;
import com.example.asem.api.model.AsetModel2;
import com.example.asem.api.model.AsetTipe;
import com.example.asem.api.model.Data2;
import com.example.asem.api.model.DeleteModel;
import com.example.asem.api.model.LoginModel;
import com.example.asem.api.model.ReportModel;
import com.example.asem.api.model.SubUnitModel;
import com.example.asem.api.model.UnitModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AsetInterface {


    @GET("aset/{id}/")
    Call<AsetModel> getAset(@Path("id") int asetId);

    @GET("aset-jenis/")
    Call<AsetJenisModel> getAsetJenis();

    @GET("aset-kode/")
    Call<AsetKodeModel2> getAsetKode();

    @GET("sub-unit/")
    Call<SubUnitModel> getSubUnit();

    @GET("unit/")
    Call<UnitModel> getUnit();

    @GET("aset/")
    Call<List<Data2>> getAllAset();

    @GET("aset-tipe/")
    Call<List<AsetTipe>> getAsetTipe();

    @GET("aset-kondisi/")
    Call<List<AsetKondisi>> getAsetKondisi();

    @GET("afdelling/")
    Call<AfdellingModel> getAfdeling();

    @POST("aset/edit")
    Call<AsetModel> editAset(
            @Header("Content-Type") String contentType, @Body MultipartBody body
            );

    @GET("approve/{id}")
    Call<AsetApproveModel> approveAset(@Path("id") int id);

    @POST("reject")
    Call<AsetApproveModel> rejectAset(@Path("id") int id, @Header("Content-Type") String contentType, @Body MultipartBody body);

    @POST("aset/create")
    Call<AsetModel2> addAset(
            @Header("Content-Type") String contentType, @Body MultipartBody body
    );

    @POST("export")
    Call<ReportModel> downloadReport(
            @Header("Content-Type") String contentType, @Body MultipartBody body
    );

    @DELETE("aset/{id}")
    Call<DeleteModel> deleteReport(@Path("id") int asetId);

    @FormUrlEncoded
    @POST("login")Call<LoginModel> login(
            @Field("username") String username,
            @Field("user_pass") String user_pass
    );

    @FormUrlEncoded
    @POST("kirimDataAset")
    Call<AsetModel2> kirimDataAset(
//            @Field("aset_sub_unit") String aset_sub_unit,
//            @Field("status_posisi_id") String status_posisi_id,
            @Field("id") int id,
            @Field("message") String kirim);


}
