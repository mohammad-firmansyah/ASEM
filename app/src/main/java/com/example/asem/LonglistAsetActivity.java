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
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asem.adapter.Aset2Adapter;
import com.example.asem.adapter.AsetOfflineAdapter;
import com.example.asem.adapter.SearchAsetAdapter;
import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Afdelling;
import com.example.asem.api.model.AllSpinner;
import com.example.asem.api.model.AsetJenis;
import com.example.asem.api.model.AsetKode;
import com.example.asem.api.model.AsetKode2;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetTipe;
import com.example.asem.api.model.Data;
import com.example.asem.api.model.Data2;
import com.example.asem.api.model.DataAllSpinner;
import com.example.asem.api.model.Sap;
import com.example.asem.api.model.Search;
import com.example.asem.api.model.SearchModel;
import com.example.asem.api.model.SubUnit;
import com.example.asem.api.model.Unit;
import com.example.asem.db.AsetHelper;
import com.example.asem.db.DatabaseHelper;
import com.example.asem.db.MappingHelper;
import com.example.asem.db.model.Aset;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LonglistAsetActivity extends AppCompatActivity  { //implements BottomNavigationView.OnItemSelectedListener


    DataAllSpinner allSpinner;
    Context context;
    List<String> listSpinnerSap=new ArrayList<>();
    List<AsetKode2> asetKode2 = new ArrayList<>();
    List<Afdelling> afdeling = new ArrayList<>();
    Map<Integer, Integer> mapAfdelingSpinner = new HashMap<Integer, Integer>();
    Map<Integer, Integer> mapSpinnerAfdeling = new HashMap<Integer, Integer>();
    Map<Integer, String> mapAfdeling = new HashMap();
    Map<Integer, Integer> mapSap = new HashMap();

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
    AsetHelper asetHelper;
    List<Data2> asetList = new ArrayList<>();

    Button btnReport;
    Button btnFilter;
    Button btnSync;
    Button btnSyncReset;
    ViewGroup vwSync;
    FloatingActionButton fab;

    RecyclerView rcAset; //untuk aset utama
    RecyclerView rcAset2; // untuk search dan filter

    SwipeRefreshLayout srlonglist;
    SearchView searchView;
    SwitchCompat switch_offline;

    Integer user_id;

    private AsetInterface asetInterface;
    private Dialog dialog;

    boolean isLoading;

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
        vwSync = findViewById(R.id.btnsync);
        btnSync = findViewById(R.id.btnSyncSpinner);
        btnSyncReset = findViewById(R.id.btnSyncSpinnerDelete);
        fab = findViewById(R.id.addAset);
        srlonglist = findViewById(R.id.srlonglist);
        searchView = findViewById(R.id.svSearch);
        switch_offline = findViewById(R.id.switchoffline);

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllSpinnerData();
            }
        });

        btnSyncReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                AsetHelper asetHelper = AsetHelper.getInstance(getApplicationContext());
                asetHelper.open();
                asetHelper.deleteAsetJenis();
                asetHelper.deleteAsetKode();
                asetHelper.deleteAsetTpe();
                asetHelper.deleteAsetKondisi();
                asetHelper.deleteSap();
                asetHelper.deleteAfdeling();
                asetHelper.deleteUnit();
                asetHelper.deleteSubUnit();
                asetHelper.close();
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"success reset data spinner",Toast.LENGTH_LONG).show();
            }
        });

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LonglistAsetActivity.this, AddAsetActivity.class));
                startActivity(new Intent(LonglistAsetActivity.this, AddAsetActivity.class));

            }
        });

        //-------fungsi offline dibawah ini-------//
//        dbTebu = new DatabaseTebu.DataTmaTebuOfflineDbHelper(this);
//        datamandor = new ArrayList<>();
//
//        if (sharedPreferences.getString("jabatan","-").equals("MANDOR")){
//            datamandor = dbTebu.readDataTmaTebu();
//            adapterOffline = new AdapterLonglistOffline(LonglistTebu.this,datamandor);
//        }
//
//        List<Data2> datas = response.body();
//        Aset2Adapter adapter = new Aset2Adapter(datas,LonglistAsetActivity.this);
//        rcAset.setAdapter(adapter);

//        dbOffline = new DatabaseHelper(this);



//        Boolean switchState = switch_offline.isChecked();
        switch_offline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                srlonglist.setEnabled(false);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LonglistAsetActivity.this, AddAsetActivity.class));
                    }
                });
                if(isChecked){
                    dialog.show();
                    //aktifkan longlist offline
                    if (switch_offline.isEmojiCompatEnabled()){
                        vwSync.setVisibility(View.VISIBLE);

                        dialog.dismiss();
                        switch_offline.setChecked(true);
                        rcAset.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rcAset.setAdapter(offlineAdapter);
                        getDataOffline();
                        searchView.setVisibility(View.GONE);
                        btnReport.setVisibility(View.GONE);
                        btnFilter.setVisibility(View.GONE);
                        switch_offline.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.MediumBlue)));
                        switch_offline.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(LonglistAsetActivity.this, AsetAddUpdateOfflineActivity.class));

                            }
                        });
                        asetHelper = AsetHelper.getInstance(getApplicationContext());
                        asetHelper.open();
                        Cursor dataoffline = asetHelper.queryAll();
                        if (hak_akses_id.equals("7")){
                            ArrayList<Aset> listAset = MappingHelper.mapCursorToArrayListAset(dataoffline);
                            offlineAdapter = new AsetOfflineAdapter(LonglistAsetActivity.this, listAset);
                                rcAset.setAdapter(offlineAdapter);
                        }
                        asetHelper.close();
                    }
                }else {
                    dialog.dismiss();
                    vwSync.setVisibility(View.GONE);
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
//        initScrollListener();
    }

    // untuk load recycler 10 data dulu
//    private void populateData() {
//        int i = 0;
//        dataApiSize = Math.min(dataList.size(), 10);
//        Log.d(TAG, "populateData: "+ dataApiSize);
//        while (i < dataApiSize) {
//            rowsArrayList.add(dataList.get(i));
//            i++;
//        }
//    }

    private void getDataOffline(){
        Data[] allData = new Data[]{
//                new Data(1,"tesoff",1,2,1,2,1,"6","fotoaset1","fotoaset2","fotoaset3","fotoaset4","geo","null","null","null", 999.0,"2023-01-03 10:11:23","2023-01-03 00:00:00",9,666,"nnn","1","-",null,null,null,1,16,185,null,"2023-01-03T03:11:23.000000Z","2023-01-03T03:11:23.000000Z",null,99,null,null,null,null,"jjj")
        };
//
//        AsetOfflineAdapter asetOfflineAdapter = new AsetOfflineAdapter(listAset,LonglistAsetActivity.this);
//        rcAset.setAdapter(asetOfflineAdapter);
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
public void getAllSpinnerData(){
        dialog.show();

    Call<AllSpinner> call = asetInterface.getAllSpinner();

    call.enqueue(new Callback<AllSpinner>() {
        @Override
        public void onResponse(Call<AllSpinner> call, Response<AllSpinner> response) {
            if (!response.isSuccessful() && response.body().getData() == null) {
                Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }

            allSpinner = response.body().getData();

            DataAllSpinner dataAllSpinner = response.body().getData();
            List<String> listSpinnerTipe = new ArrayList<>();
            List<String> listSpinnerJenis = new ArrayList<>();
            List<String> listSpinnerKondisiAset = new ArrayList<>();
            List<String> listSpinnerKodeAset = new ArrayList<>();
            List<String> listSpinnerUnit = new ArrayList<>();
            List<String> listSpinnerSubUnit = new ArrayList<>();
            List<String> listSpinnerAfdeling =   new ArrayList<>();
            // get data tipe aset
            AsetHelper asetHelper = AsetHelper.getInstance(getApplicationContext());
            asetHelper.open();
            for (AsetTipe at : dataAllSpinner.getAsetTipe()){
//                asetHelper.deleteAsetTpe();
                ContentValues values = new ContentValues();
                values.put("aset_tipe_desc",at.getAset_tipe_desc());
                asetHelper.insertAsetTpe(values);
            }

            // get data jenis
            for (AsetJenis at : dataAllSpinner.getAsetJenis()){
//                asetHelper.deleteAsetJenis();
                ContentValues values = new ContentValues();
                values.put("aset_jenis_desc",at.getAset_jenis_desc());
                asetHelper.insertAsetJenis(values);
            }

            // get kondisi aset
            for (AsetKondisi at : dataAllSpinner.getAsetKondisi()){
//                asetHelper.deleteAsetKondisi();
                ContentValues values = new ContentValues();
                values.put("aset_kondisi_desc",at.getAset_kondisi_desc());
                asetHelper.insertAsetKondisi(values);
            }

            // get kode aset
            for (AsetKode2 at : dataAllSpinner.getAsetKode()){
//                asetHelper.deleteAsetKode();
                ContentValues values = new ContentValues();
                values.put("aset_class",at.getAsetClass());
                values.put("aset_group",at.getAsetGroup());
                values.put("aset_desc",at.getAsetDesc());
                values.put("aset_jenis",at.getAsetJenis());
                values.put("created_at",at.getCreatedAt());
                values.put("updated_at",at.getUpdatedAt());
                asetHelper.insertAsetKode(values);
            }

            // get unit
            for (Unit at : dataAllSpinner.getUnit()){
//                asetHelper.deleteUnit();
                ContentValues values = new ContentValues();
                values.put("unit_desc",at.getUnit_desc());
                asetHelper.insertUnit(values);
            }

            // get sub unit
            for (SubUnit at : dataAllSpinner.getSubUnit()){
//                asetHelper.deleteSubUnit();
                ContentValues values = new ContentValues();
                values.put("sub_unit_desc",at.getSub_unit_desc());
                asetHelper.insertSubUnit(values);
            }



            // get afdeling
            for (Afdelling at : dataAllSpinner.getAfdeling()){
//                asetHelper.deleteAfdeling();
                ContentValues values = new ContentValues();
                values.put("afdeling_desc",at.getAfdelling_desc());
                values.put("unit_id",at.getUnit_id());
                asetHelper.insertAfdeling(values);
            }

            // get sap
            for (Sap at : dataAllSpinner.getSap()){
//                asetHelper.deleteSap();
                ContentValues values = new ContentValues();
                values.put("sap_desc",at.getSap_desc());
                asetHelper.insertSap(values);
            }

            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"sikronasi data sukses",Toast.LENGTH_SHORT).show();
            return;

        }

        @Override
        public void onFailure(Call<AllSpinner> call, Throwable t) {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            return;
        }
    });

    }

//    //load data 10 per 10
//    //thanks to https://www.digitalocean.com/community/tutorials/android-recyclerview-load-more-endless-scrolling
//
//    private void initScrollListener(){
//        rcAset.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                if (!isLoading){
////                    List<Data2> rowsArrayList = response.body();
//                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == asetList.size() - 1) {
//                        //bottom of list!
//                        if (datalist.size() > 10){
//                            loadMore();
//                        } else{
//                            Log.d("dataload","end scroll");
//                        }
//                        isLoading = true;
//                    }
//                }
//            }
//        });
//    }
//
//    private void loadMore(){}
}