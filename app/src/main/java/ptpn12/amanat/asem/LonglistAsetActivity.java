package ptpn12.amanat.asem;

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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ptpn12.amanat.asem.adapter.Aset2Adapter;
import ptpn12.amanat.asem.adapter.AsetOfflineAdapter;
import ptpn12.amanat.asem.adapter.SearchAsetAdapter;
import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.Afdelling;
import ptpn12.amanat.asem.api.model.AlatAngkut;
import ptpn12.amanat.asem.api.model.AllSpinner;
import ptpn12.amanat.asem.api.model.AsetJenis;
import ptpn12.amanat.asem.api.model.AsetKode2;
import ptpn12.amanat.asem.api.model.AsetKondisi;
import ptpn12.amanat.asem.api.model.AsetTipe;
import ptpn12.amanat.asem.api.model.Data;
import ptpn12.amanat.asem.api.model.Data2;
import ptpn12.amanat.asem.api.model.DataAllSpinner;
import ptpn12.amanat.asem.api.model.Sap;
import ptpn12.amanat.asem.api.model.Search;
import ptpn12.amanat.asem.api.model.SubUnit;
import ptpn12.amanat.asem.api.model.Unit;
import ptpn12.amanat.asem.offline.AsetHelper;
import ptpn12.amanat.asem.offline.DatabaseHelper;
import ptpn12.amanat.asem.offline.MappingHelper;
import ptpn12.amanat.asem.offline.model.Aset;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LonglistAsetActivity extends AppCompatActivity  { //implements BottomNavigationView.OnItemSelectedListener


    DataAllSpinner allSpinner;
    Context context;
    Boolean offline;
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
    Aset2Adapter onlineAdapter;
    DatabaseHelper dbOffline;
    ArrayList<Aset> dataoffline;
    AsetHelper asetHelper;
    List<Data2> asetList = new ArrayList<>();

    SharedPreferences.Editor editor;
    LinearLayout addDataOffline;
    TextView tvAddDataOffline;

    ImageView wifiOFF;
    ImageView wifiON;
    ImageView btnSync;

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

    boolean isLoading;
    int dataApiSize;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longlist_aset);

        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);
        editor = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences(PREF_LOGIN,MODE_PRIVATE);
        dialog = new Dialog(LonglistAsetActivity.this,R.style.MyAlertDialogTheme);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        btnReport = findViewById(R.id.btnReport);
        btnFilter = findViewById(R.id.btnFilter);

//        manager = new LinearLayoutManager(this);
        rcAset = findViewById(R.id.asetAll);
        rcAset.setHasFixedSize(true);
//        onlineAdapter = new Aset2Adapter(asetList, this);
//        rcAset.setAdapter(onlineAdapter);
//        rcAset.setLayoutManager(manager);
        rcAset2 = findViewById(R.id.recView2);
        rcAset2.setHasFixedSize(true);
        rcAset2.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.addAset);
        srlonglist = findViewById(R.id.srlonglist);
        searchView = findViewById(R.id.svSearch);
        switch_offline = findViewById(R.id.switchoffline);
        addDataOffline = findViewById(R.id.addDataOffline);
        tvAddDataOffline = findViewById(R.id.tvAddDataOffline);
        wifiOFF = findViewById(R.id.imgWifiOFF);
        wifiON = findViewById(R.id.imgWifiON);
        btnSync = findViewById(R.id.btnSync);

        // globally
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        //in your OnCreate() method
        tvTitle.setText("LIST DATA");

        Intent intent = getIntent();
         offline = intent.getBooleanExtra("offline",false);
        if(offline) {
            switch_offline.setChecked(true);
        } else {
            switch_offline.setChecked(false);
        }
        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String hak_akses_id = sharedPreferences.getString("hak_akses_id", "-");
        if (hak_akses_id.equals("7")){
            fab.setVisibility(View.VISIBLE);
        }else {
            fab.setVisibility(View.GONE);
        }

        user_id = Integer.parseInt(sharedPreferences.getString("user_id", "0"));

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offline) {
                    String hak_akses_id = sharedPreferences.getString("hak_akses_id", "-");
                    if (hak_akses_id.equals("7")){
                        AsetHelper asetHelper = AsetHelper.getInstance(getApplicationContext());
                        asetHelper.open();
                        asetHelper.truncate();
                        asetHelper.close();
                        editor.putBoolean("sync",true);
                        editor.apply();
                        getAllSpinnerData();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Hanya tersedia saat offline",Toast.LENGTH_LONG).show();

                }
            }
        });



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
                offline = true;
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LonglistAsetActivity.this, AddAsetActivity.class));
                    }
                });

                if(switch_offline.isChecked()){
                    dialog.show();

                    btnSync.setVisibility(View.VISIBLE);
                    //aktifkan longlist offline
                    addDataOffline.setVisibility(View.VISIBLE);

                    dialog.dismiss();
                    rcAset.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rcAset.setAdapter(offlineAdapter);
                    searchView.setVisibility(View.GONE);
                    btnReport.setVisibility(View.GONE);
                    btnFilter.setVisibility(View.GONE);
                    switch_offline.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.MediumBlue)));
                    switch_offline.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Boolean sync = sharedPreferences.getBoolean("sync",false);
                            if (sync){

                                startActivity(new Intent(LonglistAsetActivity.this, AsetAddUpdateOfflineActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(),"Sinkronkan Data Terlebih Dahulu",Toast.LENGTH_LONG).show();
                            }

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

                }else {
                    dialog.dismiss();
                    btnSync.setVisibility(View.GONE);
                    addDataOffline.setVisibility(View.GONE);
                    srlonglist.setEnabled(true);
                    getAllAset();
//                    Aset2Adapter adapter = new Aset2Adapter(datas,LonglistAsetActivity.this);
//                    rcAset.setAdapter(adapter);
                    rcAset.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rcAset.setAdapter(onlineAdapter);
                    btnReport.setVisibility(View.VISIBLE);
                    btnFilter.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                    switch_offline.setTrackTintList(ColorStateList.valueOf(Color.GRAY));
                    switch_offline.setThumbTintList(ColorStateList.valueOf(Color.WHITE));
                }

            }
        });

        if(switch_offline.isChecked()){
            dialog.show();
            btnSync.setVisibility(View.VISIBLE);
            //aktifkan longlist offline
            addDataOffline.setVisibility(View.VISIBLE);

            dialog.dismiss();
            switch_offline.setChecked(true);
            rcAset.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rcAset.setAdapter(offlineAdapter);
            searchView.setVisibility(View.GONE);
            btnReport.setVisibility(View.GONE);
            btnFilter.setVisibility(View.GONE);
            switch_offline.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.MediumBlue)));
            switch_offline.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean sync = sharedPreferences.getBoolean("sync",false);
                    if (sync){

                        startActivity(new Intent(LonglistAsetActivity.this, AsetAddUpdateOfflineActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(),"Sinkronkan Data Terlebih Dahulu",Toast.LENGTH_LONG).show();
                    }

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

        }else {
            dialog.dismiss();
            btnSync.setVisibility(View.GONE);
            addDataOffline.setVisibility(View.GONE);
            srlonglist.setEnabled(true);
            getAllAset();
            rcAset.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rcAset.setAdapter(onlineAdapter);
            btnReport.setVisibility(View.VISIBLE);
            btnFilter.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
            switch_offline.setTrackTintList(ColorStateList.valueOf(Color.GRAY));
            switch_offline.setThumbTintList(ColorStateList.valueOf(Color.WHITE));
        }

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

        srlonglist.setOnRefreshListener(() ->{
            srlonglist.setRefreshing(false);
            Intent intentToLonglist = new Intent(LonglistAsetActivity.this, LonglistAsetActivity.class);
            startActivity(intentToLonglist);
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


//        initScrollListener();
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

                wifiOFF.setVisibility(View.GONE);
                wifiON.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<Data2>> call, Throwable t) {
                dialog.dismiss();
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Internet tidak terdeteksi.\nSilahkan switch ke Offline!",Toast.LENGTH_LONG).show();

                // fungsi icon wifi
                wifiOFF.setVisibility(View.VISIBLE);
                wifiON.setVisibility(View.GONE);

                return;
            }
        });
    }


    //fungsi search dibawah ini

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
                    dialog.dismiss();
                    if(!response.isSuccessful() && response.body() == null){
//                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
                        return;
                    }
                    List<Search> dataS = response.body();
                    SearchAsetAdapter adapter = new SearchAsetAdapter(dataS, LonglistAsetActivity.this);
                    rcAset2.setAdapter(adapter);
//                    Log.d("ceksearch","bisa search :" +search);
//                Toast.makeText(getApplicationContext(),"ini masuk kan", Toast.LENGTH_SHORT).show();
//                    adapter.notifyDataSetChanged();
//                    srlonglist.setRefreshing(false);
//
//                    dialog.dismiss();
                }
            public void onFailure(Call<List<Search>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error :"+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("search", "error message: "+t.getMessage());
//                srlonglist.setRefreshing(false);
                return;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sync:
                if (offline) {
                    String hak_akses_id = sharedPreferences.getString("hak_akses_id", "-");
                    if (hak_akses_id.equals("7")){
                        AsetHelper asetHelper = AsetHelper.getInstance(getApplicationContext());
                        asetHelper.open();
                        asetHelper.truncate(); ;
                        asetHelper.close();
                        editor.putBoolean("sync",true);
                        editor.apply();
                        getAllSpinnerData();
                        return true;
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Hanya tersedia saat offline",Toast.LENGTH_LONG).show();
                    break;
                }
        }
        return true;
    }
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


public void getAllSpinnerData(){
        dialog.show();
        dialog.setCancelable(false);

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

            // get alat pengangkutan
            for (AlatAngkut at : dataAllSpinner.getAlatAngkut()){
                ContentValues values = new ContentValues();
                values.put("ap_desc",at.getAp_desc());
                asetHelper.insertAlatAngkut(values);
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

    // untuk load recycler 10 data dulu
//    private void populateData() {
//        int i = 0;
//        dataApiSize = Math.min(dataList.size(), 10);
//        Log.d("populate", "populateData: "+ dataApiSize);
//        while (i < dataApiSize) {
//            rowsArrayList.add(dataList.get(i));
//            i++;
//        }
//    }

//    //load data 10 per 10 (lazyloading)
//    //thanks to https://www.digitalocean.com/community/tutorials/android-recyclerview-load-more-endless-scrolling
//
//    int currentItems, totalItems, scrollOutItems;
//    boolean isScrolling = false;
//    private void initScrollListener(){
//        rcAset.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                    isScrolling = true;
//                }
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                currentItems = manager.getChildCount();
//                totalItems = manager.getItemCount();
//                scrollOutItems = manager.findFirstCompletelyVisibleItemPosition();
//
//                if (isScrolling && (currentItems + scrollOutItems == totalItems)){
//                    //load more data
//                    isScrolling = false;
//                    loadMore();
//                }
//
////                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
////                if (!isLoading){
//////                    List<Data2> rowsArrayList = response.body();
////                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == asetList.size() - 1) {
////                        //bottom of list!
////                        if (datalist.size() > 10){
////                            loadMore();
////                        } else{
////                            Log.d("dataload","end scroll");
////                        }
////                        isLoading = true;
////                    }
////                }
//            }
//        });
//    }
//
////    //load more data
//    private void loadMore(){
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=0;i<5;i++){
//                    asetList.add(Math.floor(Math.random()*100) + "");
////                    Math.min(asetList.size(), 10);
//
//;                    onlineAdapter.notifyDataSetChanged();
//                }
//            }
//        }, 5000);

//        rowsArrayList.add(null);
//        adapter.notifyItemInserted(rowsArrayList.size() - 1);
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dataApiSize = Math.min(dataList.size(), 10);
//                rowsArrayList.remove(rowsArrayList.size() - 1);
//                int scrollPosition = rowsArrayList.size();
//                adapter.notifyItemRemoved(scrollPosition);
//                int currentSize = scrollPosition;
//                int nextLimit = currentSize + dataApiSize;
//
//                try {
//                    while (currentSize - 1 < nextLimit) {
//                        rowsArrayList.add(dataList.get(currentSize));
//                        sizerowrray = rowsArrayList.size();
//                        currentSize++;
//                        if (currentSize >= dataList.size()){
//                            Toast.makeText(LonglistTebu.this, "Data Sudah yang paling Akhir", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                } catch (Exception e){
//                    Toast.makeText(LonglistTebu.this, "error", Toast.LENGTH_LONG).show();
//                }
//                Log.d("crsize", String.valueOf(currentSize));
//                Log.d("crsize", String.valueOf(rowsArrayList.size()));
//                Log.d("limit", String.valueOf(nextLimit));
//                Log.d("datalist data", String.valueOf(dataList.size()));
//
////                if ( rowsArrayList.add(dataList.get(currentSize).)
//
//                adapter.notifyDataSetChanged();
//                isLoading = false;
//            }
//        }, 2000);


//    }
}