package com.example.asem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asem.adapter.Aset2Adapter;
import com.example.asem.adapter.SearchAsetAdapter;
import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Data;
import com.example.asem.api.model.Data2;
import com.example.asem.api.model.Search;
import com.example.asem.api.model.SearchModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    Button btnReport;
    Button btnFilter;
    FloatingActionButton fab;

    RecyclerView rcAset; //untuk aset utama
    RecyclerView rcAset2; // untuk search dan filter

    SwipeRefreshLayout srlonglist;
    SearchView searchView;

    Integer user_id;

    public static String baseUrl = "http://202.148.9.226:7710/mnj_aset_production/public/api/";
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
        rcAset2 = findViewById(R.id.recView2);
        rcAset2.setHasFixedSize(true);
        rcAset2.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.addAset);
        srlonglist = findViewById(R.id.srlonglist);
        searchView = findViewById(R.id.svSearch);

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
            public boolean onQueryTextSubmit(String query) {
                getDataSearch(query);
                rcAset.setVisibility(View.GONE);
                rcAset2.setVisibility(View.VISIBLE);
//                Toast.makeText(rcAset2.get,"halo",Toast.LENGTH_SHORT).show();
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

    private void getDataSearch(String query){
        dialog.show();
        Integer id = null;
        Search search = new Search();
        SharedPreferences.Editor editor = getSharedPreferences(PREF_LOGIN,MODE_PRIVATE).edit();
        Call<SearchModel> call = asetInterface.searchAset(String.valueOf(search),query);
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                dialog.dismiss();
                editor.putString("-",query);

                SearchModel dataS = response.body();
                SearchAsetAdapter adapter2 = new SearchAsetAdapter(dataS, LonglistAsetActivity.this);
                rcAset2.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
                srlonglist.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "knp ya", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
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