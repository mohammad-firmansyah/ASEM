package com.example.asem;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Afdelling;
import com.example.asem.api.model.AsetApproveModel;
import com.example.asem.api.model.AsetJenis;
import com.example.asem.api.model.AsetKode2;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetModel;
import com.example.asem.api.model.AsetTipe;
import com.example.asem.api.model.DataAllSpinner;
import com.example.asem.api.model.ReportModel;
import com.example.asem.api.model.Sap;
import com.example.asem.api.model.SubUnit;
import com.example.asem.api.model.Unit;
import com.example.asem.db.AsetHelper;
import com.example.asem.db.MappingHelper;
import com.example.asem.db.model.Aset;
import com.example.asem.utils.utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAsetOfflineActivity extends AppCompatActivity {
    private AsetHelper asetHelper;
    ListView listView;
    Dialog spinnerNoSap;
    Map<Integer, Integer> mapSpinnerAfdeling = new HashMap<Integer, Integer>();
    Map<Integer, String> mapAfdeling = new HashMap();
    String url1 = "";
    String url2 = "";
    String url3 = "";
    String url4 = "";
    String urlfotoasetqr = "";
    Map<Integer, Integer> mapAfdelingSpinner = new HashMap<Integer, Integer>();
    Map<Long, Integer> mapSap = new HashMap();
    Map<Integer, Long> mapSpinnerSap = new HashMap();
    List<String> listSpinnerSap = new ArrayList<>();
    Map<Integer,Integer> mapKodeSpinner = new HashMap();
    Map<Integer,Integer> mapSpinnerkode = new HashMap();
    File asetqrfoto;
    DataAllSpinner allSpinner;
    Dialog customDialogApprove;
    Dialog customDialogReject;
    Button inpBtnMap;
    Button btnFile;
    Button btnSubmit;
    Button map1;
    Button map2;
    Button map3;
    Button map4;
    Button btnApprove;
    Button btnReject;
    Button inpSimpanFotoQr;
    Button downloadQr;

    EditText inpNoInv;
    EditText inpHGU;
    ImageView fotoasetqr;
    ViewGroup addNewFotoAsetAndQr;
    LinearLayout fotoasetqrgroup;

    String qrurl;
    Integer statusPosisi;

    Integer id;
    double longitudeValue = 0;
    double latitudeValue = 0;

    SharedPreferences sharedPreferences;
    private static final String PREF_LOGIN = "LOGIN_PREF";


    List<AsetKode2> asetKode2 = new ArrayList<>();
    List<Afdelling> afdeling = new ArrayList<>();

    FusedLocationProviderClient mFusedLocationClient;

    private static final String[] PERMISSIONS_LOCATION_AND_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int LOCATION_PERMISSION_AND_STORAGE = 33;

    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    EditText inpJumlahPohon;
    TextView tvUploudBA;
    TextView tvFotoAsetQR;
    TextView tvKetReject;
    AsetModel asetModel;
    File source;

    private AsetInterface asetInterface;
    Spinner spinnerTipeAset;
    Spinner spinnerJenisAset;
    Spinner spinnerAsetKondisi;
    Spinner spinnerKodeAset;
    Spinner spinnerAfdeling;
    Spinner spinnerSubUnit;
    Spinner spinnerUnit;

    EditText inpTglInput;
    EditText inpNamaAset;
    EditText inpNoSAP;
    EditText inpLuasAset;
    EditText inpNilaiAsetSAP;
    EditText inpTglOleh;
    EditText inpMasaPenyusutan;
    EditText inpNomorBAST;
    EditText inpNilaiResidu;
    EditText inpKeterangan;
    EditText inpUmrEkonomis;
    EditText inpPersenKondisi;
    EditText inpKetReject;
    EditText  editTextSap;


    ViewGroup foto1rl;
    ViewGroup foto2rl;
    ViewGroup foto3rl;
    ViewGroup foto4rl;
    ViewGroup listBtnMap;

    ImageView fotoimg1;
    ImageView fotoimg2;
    ImageView fotoimg3;
    ImageView fotoimg4;
    ImageView qrDefault;


    String photoname1 = "foto1.png";
    String photoname2 = "foto2.png";
    String photoname3 = "foto3.png";
    String photoname4 = "foto4.png";



    String geotag1;
    String geotag2;
    String geotag3;
    String geotag4;

    File img1;
    File img2;
    File img3;
    File img4;
    File bafile_file;

    String spinnerIdTipeAsset;
    String spinnerIdJenisAset;
    String spinnerIdAsetKondisi;
    String spinnerIdKodeAset;

    String spinnerIdAfdeling;
    String spinnerIdSubUnit;
    String spinnerIdUnit;


    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_offline);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        asetHelper = AsetHelper.getInstance(getApplicationContext());

        sharedPreferences = DetailAsetOfflineActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);

        dialog = new Dialog(DetailAsetOfflineActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        dialog.show();
        spinnerNoSap = new Dialog(DetailAsetOfflineActivity.this);
        spinnerNoSap.dismiss();
        spinnerNoSap.setContentView(R.layout.searchable_spinner);
        spinnerNoSap.getWindow().setLayout(650,800);
        spinnerNoSap.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        spinnerNoSap.show();
        editTextSap=spinnerNoSap.findViewById(R.id.edit_text);
        listView=spinnerNoSap.findViewById(R.id.list_view);
        inpKetReject = findViewById(R.id.inpKetReject);
        inpKetReject.setEnabled(false);
        inpNoInv = findViewById(R.id.inpNoInv);
        inpNoInv.setEnabled(false);
        listBtnMap = findViewById(R.id.listMapButton);
        inpTglOleh = findViewById(R.id.inpTglMasukAset);
        inpTglOleh.setEnabled(false);
        tvUploudBA = findViewById(R.id.tvUploudBA);
        spinnerTipeAset = findViewById(R.id.inpTipeAset);
        spinnerTipeAset.setEnabled(false);
        spinnerJenisAset = findViewById(R.id.inpJenisAset);
        spinnerJenisAset.setEnabled(false);
        spinnerAsetKondisi = findViewById(R.id.inpKndsAset);
        spinnerAsetKondisi.setEnabled(false);
        spinnerKodeAset = findViewById(R.id.inpKodeAset);
        spinnerKodeAset.setEnabled(false);
        spinnerAfdeling = findViewById(R.id.inpAfdeling);
        spinnerAfdeling.setEnabled(false);
        spinnerSubUnit = findViewById(R.id.inpSubUnit);
        spinnerSubUnit.setEnabled(false);
        spinnerUnit = findViewById(R.id.inpUnit);
        spinnerUnit.setEnabled(false);
        fotoasetqr = findViewById(R.id.fotoasetqr);
        tvFotoAsetQR = findViewById(R.id.tvFotoAsetQR);
        tvKetReject = findViewById(R.id.tvKetReject);

        addNewFotoAsetAndQr = findViewById(R.id.addNewFotoAsetAndQr);
        inpTglInput = findViewById(R.id.inpTglInput);
        inpTglInput.setEnabled(false);
        inpUmrEkonomis = findViewById(R.id.inpUmrEkonomis);
        inpUmrEkonomis.setEnabled(false);
        inpNamaAset = findViewById(R.id.inpNamaAset);
        inpNamaAset.setEnabled(false);
        inpNoSAP = findViewById(R.id.inpNmrSAP);
        inpNoSAP.setEnabled(false);
        inpLuasAset = findViewById(R.id.inpLuasAset);
        inpLuasAset.setEnabled(false);
        inpNilaiAsetSAP = findViewById(R.id.inpNilaiAsetSAP);
        inpNilaiAsetSAP.setEnabled(false);
        inpMasaPenyusutan = findViewById(R.id.inpMasaPenyusutan);
        inpMasaPenyusutan.setEnabled(false);
        inpNomorBAST = findViewById(R.id.inpNmrBAST);
        inpNomorBAST.setEnabled(false);
        inpNilaiResidu = findViewById(R.id.inpNmrResidu);
        inpNilaiResidu.setEnabled(false);
        inpKeterangan = findViewById(R.id.inpKeterangan);
        inpKeterangan.setEnabled(false);
        inpJumlahPohon = findViewById(R.id.inpJmlhPohon);
        inpJumlahPohon.setEnabled(false);
        inpPersenKondisi = findViewById(R.id.inpPersenKondisi);
        inpPersenKondisi.setEnabled(false);
        qrDefault = findViewById(R.id.qrDefault);
        downloadQr = findViewById(R.id.downloadQr);
        inpSimpanFotoQr = findViewById(R.id.inpSimpanFotoQr);
        inpHGU = findViewById(R.id.inpHGU);
        inpHGU.setEnabled(false);

        map1 = findViewById(R.id.map1);
        map2 = findViewById(R.id.map2);
        map3 = findViewById(R.id.map3);
        map4 = findViewById(R.id.map4);

        foto1rl = findViewById(R.id.foto1);
        foto2rl = findViewById(R.id.foto2);
        foto3rl = findViewById(R.id.foto3);
        foto4rl = findViewById(R.id.foto4);

        fotoimg1 = findViewById(R.id.fotoimg1);
        fotoimg2 = findViewById(R.id.fotoimg2);
        fotoimg3 = findViewById(R.id.fotoimg3);
        fotoimg4 = findViewById(R.id.fotoimg4);
        inpBtnMap = findViewById(R.id.inpBtnMap);
        btnApprove = findViewById(R.id.btnApprove);
        btnReject = findViewById(R.id.btnReject);

        downloadQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qrurl != null) {
                    Call<ReportModel> call = asetInterface.downloadQr(id);
                    call.enqueue(new Callback<ReportModel>() {
                        @Override
                        public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
                            if (!response.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"download gagal "+String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                                return;
                            }


                        }

                        @Override
                        public void onFailure(Call<ReportModel> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"download gagal "+t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        inpSimpanFotoQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("asetapix","clicked data");
//                Toast.makeText(getApplicationContext(),"helo",Toast.LENGTH_LONG).show();
                addFotoQrAset();
            }
        });


        spinnerSubUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdSubUnit = String.valueOf(position);
                editVisibilityDynamic();
                setAfdelingAdapter();
                setValueInput();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                editVisibilityDynamic();
                setAfdelingAdapter();
                setValueInput();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerTipeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdTipeAsset = String.valueOf(position+1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerJenisAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdJenisAset = String.valueOf(position+1);
                editVisibilityDynamic();
                setAdapterAsetKode();
                setValueInput();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerKodeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdKodeAset = String.valueOf(position+1);
//                editVisibilityDynamic();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerAsetKondisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdAsetKondisi = String.valueOf(position+1);
                editVisibilityDynamic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogApprove.show();
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogReject.show();
            }
        });

        inpBtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag1));
                startActivity(intent);
            }
        });

        foto1rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAsetOfflineActivity.this,DetailImageActivity.class);
                intent.putExtra("url",url1);
                startActivity(intent);
            }
        });

        foto2rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAsetOfflineActivity.this,DetailImageActivity.class);
                intent.putExtra("url",url2);
                startActivity(intent);
            }
        });

        foto3rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAsetOfflineActivity.this,DetailImageActivity.class);
                intent.putExtra("url",url3);
                startActivity(intent);
            }
        });

        foto4rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAsetOfflineActivity.this,DetailImageActivity.class);
                intent.putExtra("url",url4);
                startActivity(intent);
            }
        });

        addNewFotoAsetAndQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAsetOfflineActivity.this,DetailImageActivity.class);
                intent.putExtra("url",urlfotoasetqr);
                startActivity(intent);
            }
        });


        inpNilaiAsetSAP.addTextChangedListener(new TextWatcher() {
            private String setEditText = inpNilaiAsetSAP.getText().toString().trim();
            private String setTextv;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals(setEditText)){
                    inpNilaiAsetSAP.removeTextChangedListener(this);
                    String replace = charSequence.toString().replaceAll("[Rp. ]","");
                    if (!replace.isEmpty()){
                        setEditText = formatrupiah(Double.parseDouble(replace));
                        setTextv = setEditText;

                    } else {
                        setEditText = "";
                        setTextv= "Hasil Input";
                    }

                    inpNilaiAsetSAP.setText(setEditText);
                    inpNilaiAsetSAP.setSelection(setEditText.length());
                    inpNilaiAsetSAP.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inpNilaiResidu.addTextChangedListener(new TextWatcher() {
            private String setEditText = inpNilaiResidu.getText().toString().trim();
            private String setTextv;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals(setEditText)){
                    inpNilaiResidu.removeTextChangedListener(this);
                    String replace = charSequence.toString().replaceAll("[Rp. ]","");
                    if (!replace.isEmpty()){
                        setEditText = formatrupiah(Double.parseDouble(replace));
                        setTextv = setEditText;

                    } else {
                        setEditText = "";
                        setTextv= "Hasil Input";
                    }

                    inpNilaiResidu.setText(setEditText);
                    inpNilaiResidu.setSelection(setEditText.length());
                    inpNilaiResidu.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnFile = findViewById(R.id.inpUploudBA);

        map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag1));
                startActivity(intent);
            }
        });

        map2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag2));
                startActivity(intent);
            }
        });

        map3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag3));
                startActivity(intent);
            }
        });

        map4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag4));
                startActivity(intent);
            }
        });

        initCalender();
        getAllSpinnerData();
//        getSpinnerData();
        setValueInput();

    }

    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inpTglOleh.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void initCalender(){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        inpTglOleh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailAsetOfflineActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setValueInput(){
        try {


//        Call<AsetModel> call = asetInterface.getAset(id);
            asetHelper.open();
            Cursor cursor= asetHelper.queryById(String.valueOf(id));
            Aset aset = MappingHelper.mapCursorToArrayAset(cursor);

            inpNamaAset.setText(aset.getAsetName());
            if (aset.getBeritaAcara() != null ) {

                tvUploudBA.setText(aset.getBeritaAcara());

            }
            inpTglOleh.setText(aset.getTglOleh());
            inpTglInput.setText(aset.getTglInput());
            inpNoSAP.setText(String.valueOf(mapSpinnerSap.get(Integer.parseInt(aset.getNomorSap()))));
                inpLuasAset.setText(String.valueOf(aset.getAsetLuas()));
                inpNilaiAsetSAP.setText(String.valueOf(aset.getNilaiOleh()));
                inpMasaPenyusutan.setText(String.valueOf(aset.getMasaSusut()));
                inpNomorBAST.setText(String.valueOf(aset.getNomorBast()));
                inpNilaiResidu.setText(formatrupiah(Double.parseDouble(String.valueOf(aset.getNilaiResidu()))));
                inpKeterangan.setText(aset.getKeterangan());
//                inpUmrEkonomis.setText(utils.MonthToYear(aset.getUmurEkonomisInMonth()));
                inpNilaiAsetSAP.setText(formatrupiah(Double.parseDouble(String.valueOf(aset.getNilaiOleh()))));
                inpPersenKondisi.setText(String.valueOf(aset.getPersenKondisi()));
                inpJumlahPohon.setText(String.valueOf(aset.getJumlahPohon()));
                inpHGU.setText(String.valueOf(aset.getHgu()));
                String ket_reject = aset.getKetReject();
                if (ket_reject != null){
                    inpKetReject.setVisibility(View.VISIBLE);
                    inpKetReject.setEnabled(false);
                    tvKetReject.setVisibility(View.VISIBLE);
                    inpKetReject.setText(ket_reject);
                } else {
                    inpKetReject.setVisibility(View.GONE);
                    tvKetReject.setVisibility(View.GONE);
                }

//                statusPosisi = Integer.valueOf(aset.getStatusPosisi());
                id = aset.getAsetId();

                if (aset.getNoInv() != null) {
                    inpNoInv.setText(String.valueOf(aset.getNoInv()));
                };

                qrurl = AsemApp.BASE_URL_API+"/"+aset.getFotoQr();
                if (aset.getFotoQr() != null) {
                    qrDefault.getLayoutParams().height = 346;
                    Picasso.get().load(qrurl).resize(400,400).centerCrop().into(qrDefault);
                }

                url1 = "file://" + aset.getFotoAset1();
                url2 = AsemApp.BASE_URL_ASSET+aset.getFotoAset2();
                url3 = AsemApp.BASE_URL_ASSET+aset.getFotoAset3();
                url4 = AsemApp.BASE_URL_ASSET+aset.getFotoAset4();
                urlfotoasetqr = AsemApp.BASE_URL_ASSET+aset.getFotoAsetQr();

                if (aset.getFotoAset1() == null ){
                    map1.setEnabled(false);
                    foto1rl.setEnabled(false);
                } else {
                    Log.d("amanat17-img",url1);
                    URL url = new URL(url1);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    fotoimg1.getLayoutParams().width = 200;
                    fotoimg1.getLayoutParams().height = 200;
                    fotoimg1.setImageBitmap(bmp);
                    map1.setEnabled(true);
//                    fotoimg1.getLayoutParams().width = 200;
//                    fotoimg1.getLayoutParams().height = 200;
//                    Picasso.get().load(url1).resize(200,200).centerCrop().into(fotoimg1);

                }

                if (aset.getFotoAset2() == null ){
                    map2.setEnabled(false);
                    foto2rl.setEnabled(false);
                } else {
                    map2.setEnabled(true);
                    fotoimg2.getLayoutParams().width = 200;
                    fotoimg2.getLayoutParams().height = 200;
                    Picasso.get().load(url2).resize(200,200).centerCrop().into(fotoimg2);
                }

                if (aset.getFotoAset3() == null ){
                    map3.setEnabled(false);
                    foto3rl.setEnabled(false);
                } else {
                    map3.setEnabled(true);
                    fotoimg3.getLayoutParams().width = 200;
                    fotoimg3.getLayoutParams().height = 200;
                    Picasso.get().load(url3).resize(200,200).centerCrop().into(fotoimg3);
                }

                if (aset.getFotoAset4() == null ){
                    map4.setEnabled(false);
                    foto4rl.setEnabled(false);
                } else {
                    map4.setEnabled(true);
                    fotoimg4.getLayoutParams().width = 200;
                    fotoimg4.getLayoutParams().height = 200;
                    Picasso.get().load(url4).resize(200,200).centerCrop().into(fotoimg4);
                }

//                Log.d("asetapix", aset.getFotoAsetQr());

                if (aset.getFotoAsetQr()!=null ){
//                    fotoasetqrgroup.setVisibility(View.VISIBLE);
                    String url = AsemApp.BASE_URL_ASSET + aset.getFotoAsetQr();
                    fotoasetqr.getLayoutParams().width = 300;
                    fotoasetqr.getLayoutParams().height = 300;
                    Picasso.get().load(urlfotoasetqr).resize(300,300).centerCrop().into(fotoasetqr);
                }
                else{
                    tvFotoAsetQR.setVisibility(View.GONE);
                    addNewFotoAsetAndQr.setVisibility(View.GONE);
                }

                geotag1 = aset.getGeoTag1();
                geotag2 = aset.getGeoTag2();
                geotag3 = aset.getGeoTag3();
                geotag4 = aset.getGeoTag4();


//                set selection spinners
                spinnerTipeAset.setSelection(Integer.valueOf(aset.getAsetTipe()));
                spinnerJenisAset.setSelection(Integer.valueOf(aset.getAsetJenis()));
                spinnerAsetKondisi.setSelection(Integer.valueOf(aset.getAsetKondisi()));
                spinnerSubUnit.setSelection(Integer.valueOf(aset.getAsetSubUnit()));



                try {

                    if (aset.getAfdelingId() != null) {
//                        Log.d("afdeling_id", String.valueOf(aset.getAfdelingId()));

                        spinnerAfdeling.setSelection(mapAfdelingSpinner.get(aset.getAfdelingId()));

                    }

                    if (mapKodeSpinner.get(aset.getAsetKode()) != null) {

                        spinnerKodeAset.setSelection(mapKodeSpinner.get(aset.getAsetKode()));

//                        Log.d("amanat12", String.valueOf(spinnerKodeAset.getSelectedItemId()));
//                        Log.d("amanat12", String.valueOf(mapKodeSpinner.get(aset.getAsetKode()-1)));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

        }
        catch (Exception e){
            e.printStackTrace();
        }

//                editVisibilityDynamic();



            }




    public void editVisibilityDynamic(){
        TextView tvBa = findViewById(R.id.tvBa);
        TextView tvPohon = findViewById(R.id.tvPohon);
        TextView tvBast = findViewById(R.id.tvBast);
        TextView tvFoto = findViewById(R.id.tvFoto);
        TextView tvAfdeling = findViewById(R.id.tvAfdeling);
        Spinner inpAfdeling = findViewById(R.id.inpAfdeling);
        TextView tvLuasTanaman = findViewById(R.id.luasTanaman);
        TextView tvLuasNonTanaman = findViewById(R.id.luasNonTanaman);
        TextView tvPersenKondisi = findViewById(R.id.tvPersenKondisi);

        HorizontalScrollView scrollPartition = findViewById(R.id.scrollPartition);
//        Toast.makeText(getApplicationContext(),String.valueOf(spinnerSubUnit.getSelectedItemId()),Toast.LENGTH_LONG).show();


        if (spinnerSubUnit.getSelectedItemId() == 2){
            inpAfdeling.setVisibility(View.VISIBLE);
            tvAfdeling.setVisibility(View.VISIBLE);
//            Toast.makeText(getApplicationContext(),String.valueOf(spinnerSubUnit.getSelectedItemId()),Toast.LENGTH_LONG).show();
        } else {
            inpAfdeling.setVisibility(View.GONE);
            tvAfdeling.setVisibility(View.GONE);
        }

        if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);

            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

        }

        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);

//            inpKomoditi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);

            inpBtnMap.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

        }


        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);

            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
            tvPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);
        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);

            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);


            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);

            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
            tvPohon.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            inpBtnMap.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);

//            inpKomoditi.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            tvPohon.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);
        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) &&"rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

            listBtnMap.setVisibility(View.GONE);
            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);

//            inpKomoditi.setVisibility(View.GONE);
            tvPohon.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);

        } else {
            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);
            tvPohon.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.GONE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);
        }
    }


    private void addFotoQrAset() {
        dialog.show();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addPart(MultipartBody.Part.createFormData("foto_aset_qr", asetqrfoto.getName(), RequestBody.create(MediaType.parse("image/*"), asetqrfoto)));


        MultipartBody multipartBody = builder
                .build();
        String contentType = "multipart/form-data; charset=utf-8; boundary=" + multipartBody.boundary();


        Call<AsetApproveModel> call = asetInterface.addFotoAsetQr(id, contentType, multipartBody);

        call.enqueue(new Callback<AsetApproveModel>() {
            @Override
            public void onResponse(Call<AsetApproveModel> call, Response<AsetApproveModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dialog.dismiss();
                    startActivity(new Intent(DetailAsetOfflineActivity.this, LonglistAsetActivity.class));
                    finish();
                    return;
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error : mohon coba lagi", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<AsetApproveModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });

    }

    public String formatrupiah(Double number){
        Locale localeID = new Locale("IND","ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String formatRupiah =  numberFormat.format(number);
        String[] split = formatRupiah.split(",");
        int length = split[0].length();
        return split[0].substring(0,2)+". "+split[0].substring(2,length);
    }

    public void setAdapterAsetKode(){
        List<String> asetKode = new ArrayList<>();
        String aset_kode_temp;
        Integer i = 0;
        for (AsetKode2 a : asetKode2) {
            if (a.getAsetJenis()-1 == spinnerJenisAset.getSelectedItemId()) {

                if ((a.getAsetJenis()) == 1 ) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
                } else if ((a.getAsetJenis()) == 2) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                } else {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                }

                mapKodeSpinner.put(a.getAsetKodeId(),i);
                mapSpinnerkode.put(i,a.getAsetKodeId());

                i++;
                asetKode.add(aset_kode_temp);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, asetKode);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKodeAset.setAdapter(adapter);
    }

    public void getAllSpinnerData(){

        asetHelper.open();
        Cursor asetTipe = asetHelper.getAllAsetTipe();
        Cursor asetJenis = asetHelper.getAllAsetJenis();
        Cursor asetKondisi = asetHelper.getAllAsetKondisi();
        Cursor asetKode = asetHelper.getAllAsetKode();
        Cursor unit = asetHelper.getAllUnit();
        Cursor subUnit = asetHelper.getAllSubUnit();
        Cursor afdeling = asetHelper.getAllAfdeling();
        Cursor sap = asetHelper.getAllSap();
        com.example.asem.db.model.DataAllSpinner dataAllSpinner = MappingHelper.mapCursorToArrayListSpinner(asetTipe,asetJenis,asetKondisi,asetKode,unit,subUnit,afdeling,sap);

        List<String> listSpinnerTipe = new ArrayList<>();

        List<String> listSpinnerJenis = new ArrayList<>();

        List<String> listSpinnerKondisiAset = new ArrayList<>();

        List<String> listSpinnerKodeAset = new ArrayList<>();

        List<String> listSpinnerUnit = new ArrayList<>();

        List<String> listSpinnerSubUnit = new ArrayList<>();

        List<String> listSpinnerAfdeling = new ArrayList<>();



        listSpinnerTipe.add("Pilih Tipe Aset");

        listSpinnerJenis.add("Pilih Jenis Aset");

        listSpinnerKondisiAset.add("Pilih Kondisi Aset");

        listSpinnerKodeAset.add("Pilih Kode Aset");

        listSpinnerSubUnit.add("Pilih Sub Unit ");

        listSpinnerAfdeling.add("Pilih Afdeling Aset");
        // get data tipe aset

        for (AsetTipe at : dataAllSpinner.getAsetTipe()){

            listSpinnerTipe.add(at.getAset_tipe_desc());

        }

        // get data jenis

        for (AsetJenis at : dataAllSpinner.getAsetJenis()){

            listSpinnerJenis.add(at.getAset_jenis_desc());

        }


        // get kondisi aset

        for (AsetKondisi at : dataAllSpinner.getAsetKondisi()){

            listSpinnerKondisiAset.add(at.getAset_kondisi_desc());

        }


        // get kode aset

        asetKode2 = dataAllSpinner.getAsetKode();



        // get unit

        for (Unit at : dataAllSpinner.getUnit()){
            listSpinnerUnit.add(at.getUnit_desc());

        }

        // get sub unit
        for (SubUnit at : dataAllSpinner.getSubUnit()){
            listSpinnerSubUnit.add(at.getSub_unit_desc());
        }

        // get sap
        for (Sap at : dataAllSpinner.getSap()){
            mapSap.put(Long.parseLong(at.getSap_desc()),at.getSap_id());
            mapSpinnerSap.put(at.getSap_id(),Long.parseLong(at.getSap_desc()));
            listSpinnerSap.add(at.getSap_desc());
        }

        // set adapter unit
        ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerUnit);
        adapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(adapterUnit);
        sharedPreferences = DetailAsetOfflineActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        try {
            Integer unit_id = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
            spinnerUnit.setSelection(unit_id-1);
        } catch(Exception e){}

        // get afdeling
        Integer i=0;
            for (Afdelling at : dataAllSpinner.getAfdeling()){
            if (at.getUnit_id() == (spinnerUnit.getSelectedItemId()+1)) {
                mapSpinnerAfdeling.put(at.getAfdelling_id(), i);
                mapAfdeling.put(i, at.getAfdelling_desc());
                listSpinnerAfdeling.add(at.getAfdelling_desc());
                Log.d("amanat15",listSpinnerAfdeling.get(i));
                i++;
            }
        }



        // set adapter tipe
        ArrayAdapter<String> adapterTipe = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerTipe);
        adapterTipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipeAset.setAdapter(adapterTipe);

        // set adapter jenis
        ArrayAdapter<String> adapterJenis = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerJenis);
        adapterJenis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisAset.setAdapter(adapterJenis);

        // set adapter kondisi aset
        ArrayAdapter<String> adapterKondisiAset = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerKondisiAset);
        adapterKondisiAset.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAsetKondisi.setAdapter(adapterKondisiAset);

        // set adapter kode aset
        ArrayAdapter<String> adapterKodeAset = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerKodeAset);
        adapterKodeAset.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKodeAset.setAdapter(adapterKodeAset);

        // set adapter sap aset

        try{

            if (    listView != null) {

                ArrayAdapter<String> adapterSap = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinnerSap);
                adapterSap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listView.setAdapter(adapterSap);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

//


//                // set adapter unit
//                ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(getApplicationContext(),
//                        android.R.layout.simple_spinner_item, listSpinnerUnit);
//                adapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerUnit.setAdapter(adapterUnit);
//                sharedPreferences = AsetAddUpdateOfflineActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
//                try {
//
//                    Integer unit_id = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
//                    spinnerUnit.setSelection(unit_id-1);
//                } catch(Exception e){}



        // set adapter sub unit
        ArrayAdapter<String> adapterSubUnit = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerSubUnit);
        adapterSubUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubUnit.setAdapter(adapterSubUnit);
        try {
            Integer sub_unit_id = Integer.valueOf(sharedPreferences.getString("sub_unit_id", "0"));
            spinnerSubUnit.setSelection(sub_unit_id);
        } catch (Exception e){}

        // set adapter afedeling
        Integer afdeling_id = Integer.parseInt(sharedPreferences.getString("afdeling_id", "0"));
        ArrayAdapter<String> adapterAfdeling = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerAfdeling);
        adapterAfdeling.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAfdeling.setAdapter(adapterAfdeling);
        try {

            if (listSpinnerAfdeling.size() != 0) {

                Log.d("amanat14", String.valueOf(mapAfdelingSpinner.get(afdeling_id)));
                spinnerAfdeling.setSelection(mapAfdelingSpinner.get(afdeling_id));


            }
        } catch (Exception e){
        }

        asetHelper.close();

    }
    public void setAfdelingAdapter(){
        List<String> afdelings = new ArrayList<>();
        afdelings.add("pilih afdeling");
        Integer i = 1;
        for (Afdelling a:afdeling) {
            if (a.getUnit_id() == (spinnerUnit.getSelectedItemId()+1)) {
                afdelings.add(a.getAfdelling_desc());
                mapAfdelingSpinner.put(a.getAfdelling_id(),i);
                i++;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, afdelings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAfdeling.setAdapter(adapter);
        Log.d("asetapix", String.valueOf(spinnerIdAfdeling));
    }
}