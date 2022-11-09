package com.example.asem.api;

import com.example.asem.api.model.Aset;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AsetInterface {


    @GET("aset/{id}/")
    Call<List<Aset>> getAset(@Path("id") int asetId);
}
