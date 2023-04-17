package ptpn12.amanat.asem.api;

import ptpn12.amanat.asem.api.model.AfdellingModel;
import ptpn12.amanat.asem.api.model.AllSpinner;
import ptpn12.amanat.asem.api.model.AsetApproveModel;
import ptpn12.amanat.asem.api.model.AsetJenisModel;
import ptpn12.amanat.asem.api.model.AsetKodeModel2;
import ptpn12.amanat.asem.api.model.AsetKondisi;
import ptpn12.amanat.asem.api.model.AsetModel;
import ptpn12.amanat.asem.api.model.AsetModel2;
import ptpn12.amanat.asem.api.model.AsetTipe;
import ptpn12.amanat.asem.api.model.CheckVersionModel;
import ptpn12.amanat.asem.api.model.Data2;
import ptpn12.amanat.asem.api.model.DeleteModel;
import ptpn12.amanat.asem.api.model.Filter;
import ptpn12.amanat.asem.api.model.LoginModel;
import ptpn12.amanat.asem.api.model.ReportModel;
import ptpn12.amanat.asem.api.model.Search;
import ptpn12.amanat.asem.api.model.SubUnitModel;
import ptpn12.amanat.asem.api.model.UnitModel;

import java.util.List;

import okhttp3.MultipartBody;
import ptpn12.amanat.asem.api.model.VersionModel;
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

    @GET("check-version/{v}")
    Call<CheckVersionModel> checkVersion(@Path("v") Integer v);

    @GET("aset-kode/")
    Call<AsetKodeModel2> getAsetKode();

    @GET("sub-unit/")
    Call<SubUnitModel> getSubUnit();

    @GET("unit/")
    Call<UnitModel> getUnit();

    @GET("aset-all/{id}")
    Call<List<Data2>> getAllAset(@Path("id") Integer id);

    @GET("all-spinner/")
    Call<AllSpinner> getAllSpinner();

    @GET("aset-tipe/")
    Call<List<AsetTipe>> getAsetTipe();

    @GET("aset-kondisi/")
    Call<List<AsetKondisi>> getAsetKondisi();

    @GET("afdelling/")
    Call<AfdellingModel> getAfdeling();

    @POST("aset/edit")
    Call<AsetModel2> editAset(
            @Header("Content-Type") String contentType, @Body MultipartBody body
            );

    @GET("approve/{id}")
    Call<AsetApproveModel> approveAset(@Path("id") int id);

    @POST("reject/{id}")
    Call<AsetApproveModel> rejectAset(@Path("id") int id,
                                      @Header("Content-Type") String contentType,
                                      @Body MultipartBody body);

    @POST("aset/create")
    Call<AsetModel2> addAset(
            @Header("Content-Type") String contentType, @Body MultipartBody body
    );

    @POST("export")
    Call<ReportModel> downloadReport(
            @Header("Content-Type") String contentType, @Body MultipartBody body
    );

    @GET("download-qr/{id}")
    Call<ReportModel> downloadQr(
            @Path("id") int id
    );

    @POST("kirim-foto-asetqr/{id}")
    Call<AsetApproveModel> addFotoAsetQr(@Path("id") int id,
            @Header("Content-Type") String contentType, @Body MultipartBody body
    );

    @DELETE("aset/{id}")
    Call<DeleteModel> deleteReport(@Path("id") int asetId);

    @FormUrlEncoded
    @POST("login")Call<LoginModel> login(
            @Field("username") String username,
            @Field("user_pass") String user_pass
    );

    @GET("login")Call<LoginModel> getData();

    @GET("kirim/{id}/{user_id}")
    Call<AsetModel2> kirimDataAset(
            @Path("id") int id,
            @Path("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("search")
    Call<List<Search>> searchAset(
            @Field("search") String search,
            @Field("userId") int userID
    );

    @FormUrlEncoded
    @POST("filter")
    Call<List<Filter>> filterAset(

            @Field("userId") int userID
    );

    // ---------- Update Versi Aplikasi ---------- //

    @FormUrlEncoded
    @POST("check/update")
    Call<VersionModel> checkUpdate(
            @Field("versionCode") Integer versionCode,
            @Field("versionName") String versionName
    );



}
