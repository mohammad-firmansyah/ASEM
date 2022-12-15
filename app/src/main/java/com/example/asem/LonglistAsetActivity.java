package com.example.asem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.asem.adapter.Aset2Adapter;
import com.example.asem.adapter.AsetAdapter;
import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Data;
import com.example.asem.api.model.Data2;
import com.example.asem.api.model.Unit;
import com.example.asem.api.model.UnitModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LonglistAsetActivity extends AppCompatActivity {

    private static final String[] PERMISSIONS_LOCATION_AND_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final String PREF_LOGIN = "LOGIN_PREF";
    SharedPreferences sharedPreferences;

    Button btnReport;
    Button btnFilter;
    FloatingActionButton fab;
    RecyclerView rcAset;

    Integer user_id;

    public static String baseUrl = "http://202.148.9.226:7710/mnj_aset_repo/public/api/";
    private AsetInterface asetInterface;
    private Dialog dialog;
    Data[] allData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longlist_aset);
        dialog = new Dialog(LonglistAsetActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        rcAset = findViewById(R.id.asetAll);
        rcAset.setHasFixedSize(true);
        rcAset.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.addAset);
        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String hak_akses_id = sharedPreferences.getString("hak_akses_id", "-");
        if (hak_akses_id.equals("7")){
            fab.setVisibility(View.VISIBLE);
        }else {
            fab.setVisibility(View.GONE);
        }

        user_id = Integer.parseInt(sharedPreferences.getString("user_id", "-"));


        //fab.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(10, 50, 50)));

        btnReport = findViewById(R.id.btnReport);
        btnFilter = findViewById(R.id.btnFilter);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LonglistAsetActivity.this, "masuk filter", Toast.LENGTH_SHORT).show();
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LonglistAsetActivity.this,ReportActivity.class));

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LonglistAsetActivity.this, AddAsetActivity.class));
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        asetInterface = retrofit.create(AsetInterface.class);
        allData = new Data[]{};
        AsetAdapter adapter = new AsetAdapter(allData,LonglistAsetActivity.this);
        rcAset.setAdapter(adapter);


        getAllAset();
    }

    private void getAllAset(){
        dialog.show();
        Call<List<Data2>> call = asetInterface.getAllAset(user_id);
        call.enqueue(new Callback<List<Data2>>() {
            @Override
            public void onResponse(Call<List<Data2>> call, Response<List<Data2>> response) {
                dialog.dismiss();
                if (!response.isSuccessful() && response.body() == null){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<Data2> datas = response.body();
//
//                Data2 allData [] = {};
//                for (int i = 0; i <= datas.size(); i++) {
//                    allData[i] = datas.get(i);
////                    Log.d("asetapix","h");
//                }


//                Data[] allData2 = new Data[]{
//                        new Data(1,23,"dsa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
//                        new Data(2,22,"as",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
//                        new Data(3,21,"dsasa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
//                        new Data(4,24,"dsasa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
//                        new Data(5,26,"dsasa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
//                        new Data(6,27,"dsasa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22)
//                };
                Aset2Adapter adapter = new Aset2Adapter(datas,LonglistAsetActivity.this);
                rcAset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Data2>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    //getdata sorting list trhdp status posisi masing-masing

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Keluar");
        builder.setMessage("Apa anda yakin keluar aplikasi?")
                .setPositiveButton("Iya", (dialog, id) ->finishAffinity())
                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

}