package com.example.asem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.asem.adapter.Aset2Adapter;
import com.example.asem.adapter.AsetOfflineAdapter;
import com.example.asem.adapter.SearchAsetAdapter;
import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Data;
import com.example.asem.api.model.Data2;
import com.example.asem.api.model.Search;
import com.example.asem.api.model.SearchModel;
import com.example.asem.db.AsetHelper;
import com.example.asem.db.DatabaseHelper;
import com.example.asem.db.model.Aset;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LonglistAsetActivity extends AppCompatActivity  { //implements BottomNavigationView.OnItemSelectedListener




    private static final String[] PERMISSIONS_LOCATION_AND_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final String PREF_LOGIN = "LOGIN_PREF";
    SharedPreferences sharedPreferences;
    AsetOfflineAdapter offlineAdapter;
    DatabaseHelper dbOffline;
    ArrayList<Aset> dataoffline;

    Button btnReport;
    Button btnFilter;
    FloatingActionButton fab;

    RecyclerView rcAset; //untuk aset utama
    RecyclerView rcAset2; // untuk search dan filter

    SwipeRefreshLayout srlonglist;
    SearchView searchView;
    SwitchCompat switch_offline;

    Integer user_id;

    private AsetInterface asetInterface;
    private Dialog dialog;
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
        rcAset2 = findViewById(R.id.recView2);
        rcAset2.setHasFixedSize(true);
        rcAset2.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.addAset);
        srlonglist = findViewById(R.id.srlonglist);
        searchView = findViewById(R.id.svSearch);
        switch_offline = findViewById(R.id.switchoffline);

        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String hak_akses_id = sharedPreferences.getString("hak_akses_id", "-");
        if (hak_akses_id.equals("7")){
            fab.setVisibility(View.VISIBLE);
        }else {
            fab.setVisibility(View.GONE);
        }

        user_id = Integer.parseInt(sharedPreferences.getString("user_id", "0"));


        //fab.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(10, 50, 50)));

        //search function dibawah ini
        //bisa serj no sap, no inventaris, nama aset
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            SharedPreferences.Editor editor = getSharedPreferences(PREF_LOGIN,MODE_PRIVATE).edit();
            @Override
            public boolean onQueryTextSubmit(String search) {
//                Toast.makeText(rcAset2.get,"halo",Toast.LENGTH_SHORT).show();
                sharedPreferences = getApplicationContext().getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
                int userId = Integer.parseInt(sharedPreferences.getString("user_id", "-"));
                getData(search, userId);
                rcAset.setVisibility(View.GONE);
                rcAset2.setVisibility(View.VISIBLE);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    Intent intent = new Intent(LonglistAsetActivity.this, LonglistAsetActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
//
//        dbOffline = new DatabaseHelper(this);
//        dataoffline = new ArrayList<>();
//        if (hak_akses_id.equals("7")){
//            dataoffline = ;
//        }

//        Boolean switchState = switch_offline.isChecked();
        switch_offline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dialog.show();
                srlonglist.setEnabled(false);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LonglistAsetActivity.this, AddAsetActivity.class));
                    }
                });
                if(isChecked){
                    //aktifkan longlist offline
                    if (switch_offline.isEmojiCompatEnabled()){
                        dialog.dismiss();
                        switch_offline.setChecked(true);
                        rcAset.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rcAset.setAdapter(offlineAdapter);
                        getDataOffline();
                        btnReport.setOnClickListener(view -> Toast.makeText(getApplicationContext(),"Fitur Tidak Tersedia Dalam Mode Offline",Toast.LENGTH_SHORT).show());
                        btnFilter.setOnClickListener(view -> Toast.makeText(getApplicationContext(),"Fitur Tidak Tersedia Dalam Mode Offline",Toast.LENGTH_SHORT).show());
                        searchView.setVisibility(View.GONE);
                        switch_offline.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.MediumBlue)));
                        switch_offline.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.Blue2)));
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(LonglistAsetActivity.this, AsetAddUpdateOfflineActivity.class));

                            }
                        });
                    }
                }else {
                    dialog.dismiss();
                    srlonglist.setEnabled(true);
                    getAllAset();
                    btnReport.setVisibility(View.VISIBLE);
                    btnFilter.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    switch_offline.setTrackTintList(ColorStateList.valueOf(Color.GRAY));
                    switch_offline.setThumbTintList(ColorStateList.valueOf(Color.WHITE));
                }
            }
        });

        btnReport = findViewById(R.id.btnReport);
        btnFilter = findViewById(R.id.btnFilter);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LonglistAsetActivity.this, "Filter Belum Tersedia", Toast.LENGTH_SHORT).show();
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LonglistAsetActivity.this,ReportActivity.class));

            }
        });

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(LonglistAsetActivity.this, AddAsetActivity.class));
//                startActivity(new Intent(LonglistAsetActivity.this, AddAsetActivity.class));
//
//            }
//        });

        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);
//        allData = new Data[]{};
//        AsetAdapter adapter = new AsetAdapter(allData,LonglistAsetActivity.this);
//        rcAset.setAdapter(adapter);



        srlonglist.setOnRefreshListener(() ->{
            srlonglist.setRefreshing(false);
            Intent intent = new Intent(LonglistAsetActivity.this, LonglistAsetActivity.class);
            startActivity(intent);
            finish();
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);

        bottomNavigationView.setSelectedItemId(R.id.nav_longlist);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_beranda:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_longlist:
                        return true;
                    case R.id.nav_profil:
                        startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        getAllAset();
    }

    private void getDataOffline(){
        Data[] allData = new Data[]{
                new Data(1,"tesoff",1,2,1,2,1,"6","fotoaset1","fotoaset2","fotoaset3","fotoaset4","geo","null","null","null", 999.0,"2023-01-03 10:11:23","2023-01-03 00:00:00",9,666,"nnn","1","-",null,null,null,1,16,185,null,"2023-01-03T03:11:23.000000Z","2023-01-03T03:11:23.000000Z",null,99,null,null,null,null,"jjj")
        };
        AsetOfflineAdapter asetOfflineAdapter = new AsetOfflineAdapter(allData,LonglistAsetActivity.this);
        rcAset.setAdapter(asetOfflineAdapter);
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


    //fungsi search ini masih api, yg fungsi get nya belum
//    public void getSearch{}

    public void getData(String search, int userId) {

        getDataApiSearch(asetInterface.searchAset(search, userId));
//        Log.d("ceksearch","bisa search :"+search);
        dialog.show();
    }

    private void getDataApiSearch(@NotNull Call<List<Search>> call){
//        call = asetInterface.searchAset();
        call.enqueue(new Callback<List<Search>>() {
                @Override
                public void onResponse(Call<List<Search>> call, Response<List<Search>> response) {
                    if(!response.isSuccessful() && response.body() == null){
//                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                    List<Search> dataS = response.body();
                    SearchAsetAdapter adapter = new SearchAsetAdapter(dataS, LonglistAsetActivity.this);
                    rcAset2.setAdapter(adapter);
//                    Log.d("ceksearch","bisa search :" +search);
//                Toast.makeText(getApplicationContext(),"ini masuk kan", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    srlonglist.setRefreshing(false);

                    dialog.dismiss();
                }
            public void onFailure(Call<List<Search>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error :"+t.getMessage(),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                srlonglist.setRefreshing(false);
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

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId())
//        {
//            case R.id.nav_beranda:
//                startActivity(new Intent(getApplicationContext(),Home.class));
//                overridePendingTransition(0,0);
//                return true;
//            case R.id.nav_longlist:
//                return true;
//            case R.id.nav_profil:
//                startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
//                overridePendingTransition(0,0);
//                return true;
//        }
//        return false;
//    }
}