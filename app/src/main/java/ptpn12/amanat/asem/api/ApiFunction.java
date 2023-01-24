package ptpn12.amanat.asem.api;

import android.util.Log;

import ptpn12.amanat.asem.api.model.AsetModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFunction {

    public static String BASE_URL = "http://202.148.9.226:9910/aset_mnj_production/public/api/";

//    public Object initAPI(Object apiData){
//        Object apiInterface;
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        apiInterface = retrofit.create(apiData.getClass());
//
//        return apiInterface;
//    }

    private static Retrofit retrofit = null;
    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private void getAset(AsetInterface asetInterface){
        Call<AsetModel> call = asetInterface.getAset(1);
        call.enqueue(new Callback<AsetModel>() {

            @Override
            public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {

                if (!response.isSuccessful()){
//                    tvResult.setText("Code: "+response.code());
//                    Toast.makeText(getApplicationContext(),"ga bisa",Toast.LENGTH_SHORT);
                    Log.d("apiaset","gabisa konek tapi sukses response");
                    return;
                }
                AsetModel m = response.body();
//                Log.d("apiaset", m.getData().getTgl_input());
//                data = m;
//                return m;
            }

            @Override
            public void onFailure(Call<AsetModel> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
