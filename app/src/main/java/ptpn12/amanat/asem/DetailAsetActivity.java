package ptpn12.amanat.asem;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.Afdelling;
import ptpn12.amanat.asem.api.model.AllSpinner;
import ptpn12.amanat.asem.api.model.AsetApproveModel;
import ptpn12.amanat.asem.api.model.AsetJenis;
import ptpn12.amanat.asem.api.model.AsetKode2;
import ptpn12.amanat.asem.api.model.AsetKondisi;
import ptpn12.amanat.asem.api.model.AsetModel;
import ptpn12.amanat.asem.api.model.AsetTipe;
import ptpn12.amanat.asem.api.model.Data;
import ptpn12.amanat.asem.api.model.DataAllSpinner;
import ptpn12.amanat.asem.api.model.ReportModel;
import ptpn12.amanat.asem.api.model.Sap;
import ptpn12.amanat.asem.api.model.SistemTanam;
import ptpn12.amanat.asem.api.model.SubUnit;
import ptpn12.amanat.asem.api.model.Unit;
import ptpn12.amanat.asem.utils.utils;

import ptpn12.amanat.asem.*;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.location.FusedLocationProviderClient;

public class DetailAsetActivity extends AppCompatActivity {
    String url1 = "";
    String url2 = "";
    String url3 = "";
    String url4 = "";
    String url5 = "";

    Data aset;
    String urlfotoasetqr = "";
    String urlfotoasetqr2 = "";
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
    Button btnFileBAST;
    Button btnSubmit;
    Button map1;
    Button map2;
    Button map3;
    Button map4;
    Button map5;
    Button btnApprove;
    Button btnReject;
    Button inpSimpanFotoQr;
    Button downloadQr;
    Button downloadBa;
    Button downloadBAST;

    EditText inpNoInv;
    EditText inpHGU;
    ImageView fotoasetqr;
    ImageView fotoasetqr2;
    ViewGroup addNewFotoAsetAndQr;
    ViewGroup addNewFotoAsetAndQr2;
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

//    public static String baseUrl = "http://202.148.9.226:7710/mnj_aset_production/public/api/";
//    public String baseUrlImg = "http://202.148.9.226:7710/mnj_aset_production/public";
//    public static String baseUrl = "http://202.148.9.226:7710/mnj_aset_production/public/api/";
//public static String baseUrl = "https://amanat.ptpn12.com/api/";
    //    public static String baseUrlAset = "http://202.148.9.226:7710/mnj_aset_production/public";
//    public static String baseUrlImg = "https://amanat.ptpn12.com";
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    TextView tvUploudBA;
    TextView tvUploadFileBAST;
    TextView tvFotoAsetQR;
    TextView tvFotoAsetQR2;
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
    EditText spinnerSistemTanam;

    EditText spinnerLuasSatuan;
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
    EditText inpPopTotalPohonSaatIni;
    EditText inpPopTotalStdMaster;
    EditText inpPopPerHA;
    EditText inpTahunTanam;
    EditText inpPresentasePopPerHA;
    TextView tvPopTotalPohonSaatIni;
    TextView tvPopTotalStdMaster;
    TextView tvPopPerHA;
    TextView tvPresentasePopPerHA;
    EditText inpAlatAngkut;
    TextView tvAlatAngkut;



    ViewGroup foto1rl;
    ViewGroup foto2rl;
    ViewGroup foto3rl;
    ViewGroup foto4rl;
    ViewGroup foto5rl;
    ViewGroup listBtnMap;

    ImageView fotoimg1;
    ImageView fotoimg2;
    ImageView fotoimg3;
    ImageView fotoimg4;
    ImageView fotoimg5;
    ImageView qrDefault;


    String photoname1 = "foto1.png";
    String photoname2 = "foto2.png";
    String photoname3 = "foto3.png";
    String photoname4 = "foto4.png";


    String urlBa;
    String urlBAST;
    String geotag1;
    String geotag2;
    String geotag3;
    String geotag4;
    String geotag5;

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

    String spinnerIdSistemTanam;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aset);

        Intent intent = getIntent();

        sharedPreferences = DetailAsetActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);

        id = intent.getIntExtra("id",0);
        dialog = new Dialog(DetailAsetActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();

        inpKetReject = findViewById(R.id.inpKetReject);
        inpKetReject.setEnabled(false);
        inpNoInv = findViewById(R.id.inpNoInv);
        inpNoInv.setEnabled(false);
        listBtnMap = findViewById(R.id.listMapButton);
        inpTglOleh = findViewById(R.id.inpTglMasukAset);
        inpTglOleh.setEnabled(false);
        inpTahunTanam = findViewById(R.id.inpTahunTanam);
        inpTahunTanam.setEnabled(false);
        tvUploudBA = findViewById(R.id.tvUploudBA);
        tvUploadFileBAST = findViewById(R.id.tvUploadFileBAST);
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
        spinnerSistemTanam = findViewById(R.id.inpSistemTanam);
        spinnerSistemTanam.setEnabled(false);
        fotoasetqr = findViewById(R.id.fotoasetqr);
        tvFotoAsetQR = findViewById(R.id.tvFotoAsetQR);
        fotoasetqr2 = findViewById(R.id.fotoasetqr2);
        tvFotoAsetQR2 = findViewById(R.id.tvFotoAsetQR2);
        tvKetReject = findViewById(R.id.tvKetReject);
        spinnerLuasSatuan = findViewById(R.id.inpLuasSatuan);
        spinnerLuasSatuan.setEnabled(false);
        tvAlatAngkut = findViewById(R.id.tvAlatAngkut);
        inpAlatAngkut = findViewById(R.id.inpAlatAngkut);
        inpAlatAngkut.setEnabled(false);

        downloadBa = findViewById(R.id.inpDownloadBa);
        downloadBAST = findViewById(R.id.inpDownloadBAST);
        addNewFotoAsetAndQr = findViewById(R.id.addNewFotoAsetAndQr);
        addNewFotoAsetAndQr2 = findViewById(R.id.addNewFotoAsetAndQr2);
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
        inpPersenKondisi = findViewById(R.id.inpPersenKondisi);
        inpPersenKondisi.setEnabled(false);
        qrDefault = findViewById(R.id.qrDefault);
        downloadQr = findViewById(R.id.downloadQr);
        inpSimpanFotoQr = findViewById(R.id.inpSimpanFotoQr);
        inpHGU = findViewById(R.id.inpHGU);
        inpHGU.setEnabled(false);
        inpPopTotalPohonSaatIni = findViewById(R.id.inpPopTotalIni);
        inpPopTotalPohonSaatIni.setEnabled(false);
        inpPopTotalStdMaster = findViewById(R.id.inpPopTotalStd);
        inpPopTotalStdMaster.setEnabled(false);
        inpPopPerHA = findViewById(R.id.inpPopHektarIni);
        inpPopPerHA.setEnabled(false);
        inpPresentasePopPerHA = findViewById(R.id.inpPopHektarStd);
        inpPresentasePopPerHA.setEnabled(false);
        tvPopTotalPohonSaatIni = findViewById(R.id.popTotalIni);
        tvPopTotalStdMaster = findViewById(R.id.popTotalStd);
        tvPopPerHA = findViewById(R.id.popHektarIni);
        tvPresentasePopPerHA = findViewById(R.id.popHektarStd);

        map1 = findViewById(R.id.map1);
        map2 = findViewById(R.id.map2);
        map3 = findViewById(R.id.map3);
        map4 = findViewById(R.id.map4);
        map5 = findViewById(R.id.map5);

        foto1rl = findViewById(R.id.foto1);
        foto2rl = findViewById(R.id.foto2);
        foto3rl = findViewById(R.id.foto3);
        foto4rl = findViewById(R.id.foto4);
        foto5rl = findViewById(R.id.foto5);

        fotoimg1 = findViewById(R.id.fotoimg1);
        fotoimg2 = findViewById(R.id.fotoimg2);
        fotoimg3 = findViewById(R.id.fotoimg3);
        fotoimg4 = findViewById(R.id.fotoimg4);
        fotoimg5 = findViewById(R.id.fotoimg5);
        inpBtnMap = findViewById(R.id.inpBtnMap);
        btnApprove = findViewById(R.id.btnApprove);
        btnReject = findViewById(R.id.btnReject);
        btnFile = findViewById(R.id.inpUploudBA);
        btnFileBAST = findViewById(R.id.inpUploadBAST);

        // globally
        TextView tvTitle = findViewById(R.id.tvTitle);
        //in your OnCreate() method
        tvTitle.setText("DETAIL DATA");

        downloadBa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadBaFunc();
            }
        });

        downloadBAST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadBASTFunc();
            }
        });

//        downloadQr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (qrurl != null) {
//                    Call<ReportModel> call = asetInterface.downloadQr(id);
//                    call.enqueue(new Callback<ReportModel>() {
//                        @Override
//                        public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
//                            if (!response.isSuccessful()){
//                                Toast.makeText(getApplicationContext(),"download apa "+String.valueOf(response.code()),Toast.LENGTH_LONG).show();
//                                return;
//                            }
//
//                            downloadQrImage(AsemApp.BASE_URL_ASSET+"/qrcode.pdf");
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<ReportModel> call, Throwable t) {
//                            Toast.makeText(getApplicationContext(),"download gagal "+t.getMessage(),Toast.LENGTH_LONG).show();
//                            Log.d("qrgagal",t.getMessage());
//                        }
//                    });
//                }
//            }
//        });



            downloadQr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://amanat.ptpn12.com/api/download-qr/" + id));
//            String title = URLUtil.guessFileName(url,null,null);
//            request.setTitle(title);
//            request.setDescription("Sedang Mendownload Mohon Tunggu...");
//            String cookie = CookieManager.getInstance().getCookie(url);
//            request.addRequestHeader("cookie",cookie);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "qrcode.pdf");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.allowScanningByMediaScanner();
                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);

                    Toast.makeText(getApplicationContext(), "Download Dimulai" , Toast.LENGTH_SHORT).show();
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
                editVisibilityDynamic();
                if (spinnerKodeAset.getSelectedItem().equals("ZA08/Alat Pengangkutan")){
                    inpAlatAngkut.setVisibility(View.VISIBLE);
                    tvAlatAngkut.setVisibility(View.VISIBLE);
                } else {
                    inpAlatAngkut.setVisibility(View.GONE);
                    tvAlatAngkut.setVisibility(View.GONE);
                }

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
//
//        spinnerSistemTanam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                spinnerIdSistemTanam = String.valueOf(position+1);
//                editVisibilityDynamic();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//        });

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
                Intent intent = new Intent(DetailAsetActivity.this,DetailImageActivity.class);
                intent.putExtra("url",url1);
                startActivity(intent);
            }
        });

        foto2rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAsetActivity.this,DetailImageActivity.class);
                intent.putExtra("url",url2);
                startActivity(intent);
            }
        });

        foto3rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAsetActivity.this,DetailImageActivity.class);
                intent.putExtra("url",url3);
                startActivity(intent);
            }
        });

        foto4rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAsetActivity.this,DetailImageActivity.class);
                intent.putExtra("url",url4);
                startActivity(intent);
            }
        });

        foto5rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailAsetActivity.this,DetailImageActivity.class);
                intent.putExtra("url",url5);
                startActivity(intent);
            }
        });

        addNewFotoAsetAndQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAsetActivity.this,DetailImageActivity.class);
                intent.putExtra("url",urlfotoasetqr);
                startActivity(intent);
            }
        });
        addNewFotoAsetAndQr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAsetActivity.this,DetailImageActivity.class);
                intent.putExtra("url",urlfotoasetqr2);
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

        map5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag5));
                startActivity(intent);
            }
        });

        initCalender();
        getAllSpinnerData();
        setDataAset();
        setValueInput();
        initCustomDialog();

    }

    private void downloadQrImage(String url) {

        Log.d("qrgagal2",url);
//        Log.d("qrgagal3",String.valueOf(request));

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//            String title = URLUtil.guessFileName(url,null,null);
//            request.setTitle(title);
//            request.setDescription("Sedang Mendownload Mohon Tunggu...");
//            String cookie = CookieManager.getInstance().getCookie(url);
//            request.addRequestHeader("cookie",cookie);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"qrcode.pdf");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.allowScanningByMediaScanner();
            DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);

            Toast.makeText(this, "Download Dimulai" , Toast.LENGTH_SHORT).show();

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
                new DatePickerDialog(DetailAsetActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    private void setDataAset(){
        Call<AsetModel> call = asetInterface.getAset(id);

        call.enqueue(new Callback<AsetModel>() {

            @Override
            public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {

                if (!response.isSuccessful() && response.body() == null){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                aset = response.body().getData();

            }

            @Override
            public void onFailure(Call<AsetModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }

        });
    }

    private String showPopulasi(String pop) {
        String[] first  = pop.split("[.]");

        Log.d("amanat20", String.valueOf(first[0]));
        if (first.length <= 1 ) {
            return pop;
        }
        String second = first[first.length - 1];


        String comma = "";
        if (second.length() < 3) {
            comma  = second;

        } else {
            comma  = second.substring(0,3);

        }


        String result = first[0] + "." +comma +" %";

        return result;
    }

    private String showPopulasiWithoutPercentage(String pop) {
        String[] first  = pop.split("[.]");

        if (first.length <= 1 ) {
            return pop;
        }
        String second = first[first.length - 1];

        Log.d("amanat29", String.valueOf(second));

        String comma = "";
        if (second.length() < 3) {
            comma  = second;

        } else {
            comma  = second.substring(0,3);

        }

        String result = first[0] + "." + comma;

        return result;
    }



    @SuppressLint("SetTextI18n")
    private void setValueInput(){

        try {
            inpNoSAP.setText(aset.getNomorSap());
            inpNamaAset.setText(aset.getAsetName());
            inpLuasAset.setText(String.valueOf(aset.getAsetLuas()));
            inpMasaPenyusutan.setText(String.valueOf(aset.getMasaSusut()));
            inpNomorBAST.setText(String.valueOf(aset.getNomorBast()));

            inpKeterangan.setText(aset.getKeterangan());
            inpUmrEkonomis.setText(utils.MonthToYear(aset.getUmurEkonomisInMonth()));
            inpPersenKondisi.setText(String.valueOf(aset.getPersenKondisi()));
            inpHGU.setText(String.valueOf(aset.getHgu()));
            inpPopTotalPohonSaatIni.setText(String.valueOf(aset.getPopPohonSaatIni()));
            inpPopTotalStdMaster.setText(String.valueOf(aset.getPopStandar()));

            if (aset.getAsetJenis() != 2){
                if (aset.getSistemTanam() != null) {

                    if (!aset.getSistemTanam().equals("")) {
                        spinnerSistemTanam.setText(aset.getSistemTanam());

                        if (aset.getSistemTanam().equals("Mono")) {
                            inpPopPerHA.setText(showPopulasiWithoutPercentage(String.valueOf(aset.getPopPerHa())));
                            inpPresentasePopPerHA.setText(showPopulasi(String.valueOf(aset.getPresentasePopPerHa())));
                        }
                    }

                }
            } else {
                if (aset.getAlat_pengangkutan() != null) {

                    if (!aset.getAlat_pengangkutan().equals("")) {
                        inpAlatAngkut.setText(aset.getAlat_pengangkutan());
                    }
                }

                if (aset.getSatuan_luas() != null) {

                    if (!aset.getSatuan_luas().equals("")) {
                        spinnerLuasSatuan.setText(aset.getSatuan_luas());
                    }
                }
            }

            inpTahunTanam.setText(String.valueOf(aset.getTahunTanam()));

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


            statusPosisi = aset.getStatusPosisi();
            id = aset.getAsetId();

            if (aset.getNoInv() != null) {
                inpNoInv.setText(String.valueOf(aset.getNoInv()));
            };

            qrurl = AsemApp.BASE_URL+"storage/app/public/qrcode/"+aset.getFotoQr();
            if (aset.getFotoQr() != null) {
                qrDefault.getLayoutParams().height = 346;
                Picasso.get().load(qrurl).resize(400,400).centerCrop().into(qrDefault);
            }

//           /storage/aset/GEo0wT85Y44MrYta0qRvDRXsCRjdgB90wZYYclKr.jpg
//          /storage/fdvbtkkUlu3ktZCotWrHPwtWOkAAPsRCtS9tQwDE.jpg


            url1 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset1();
            url2 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset2();
            url3 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset3();
            url4 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset4();
            url5 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset5();

//            if (aset.getFotoAset1().split("/").length > 1) {
//                url1 = AsemApp.BASE_URL_ASSET+aset.getFotoAset1();
//                url2 = AsemApp.BASE_URL_ASSET+aset.getFotoAset2();
//                url3 = AsemApp.BASE_URL_ASSET+aset.getFotoAset3();
//                url4 = AsemApp.BASE_URL_ASSET+aset.getFotoAset4();
//                url5 = AsemApp.BASE_URL_ASSET+aset.getFotoAset5();
//            }

            urlfotoasetqr = AsemApp.BASE_URL_ASSET+"/storage/app/public/qrcode-foto/"+aset.getFotoAsetQr();
            urlfotoasetqr2 = AsemApp.BASE_URL_ASSET+"/storage/app/public/qrcode-foto/"+aset.getFotoAsetQr2();

            if (aset.getFotoAset1() == null ){
                map1.setEnabled(false);
                foto1rl.setEnabled(false);
            } else {
                map1.setEnabled(true);
                fotoimg1.getLayoutParams().width = 200;
                fotoimg1.getLayoutParams().height = 200;
                Picasso.get().load(url1).resize(200,200).centerCrop().into(fotoimg1);
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

            if (aset.getFotoAset5() == null ){
                    map5.setEnabled(false);
                    foto5rl.setEnabled(false);
                } else {
                    map5.setEnabled(true);
                    fotoimg5.getLayoutParams().width = 200;
                    fotoimg5.getLayoutParams().height = 200;
                    Picasso.get().load(url5).resize(200,200).centerCrop().into(fotoimg5);
                }

//                Log.d("asetapix", response.body().getData().getFotoAsetQr());

            if (aset.getFotoAsetQr()!=null && aset.getFotoAsetQr2()!=null){
//                    fotoasetqrgroup.setVisibility(View.VISIBLE);
                String url = AsemApp.BASE_URL + aset.getFotoAsetQr();
                Log.d("amanat-url",url);
                fotoasetqr.getLayoutParams().width = 300;
                fotoasetqr.getLayoutParams().height = 300;
                Picasso.get().load(urlfotoasetqr).resize(300,300).centerCrop().into(fotoasetqr);

                String url2 = AsemApp.BASE_URL + aset.getFotoAsetQr2();
                Log.d("amanat-url",url2);
                fotoasetqr2.getLayoutParams().width = 300;
                fotoasetqr2.getLayoutParams().height = 300;
                Picasso.get().load(urlfotoasetqr2).resize(300,300).centerCrop().into(fotoasetqr2);
            }
            else{
                tvFotoAsetQR.setVisibility(View.GONE);
                tvFotoAsetQR2.setVisibility(View.GONE);
                addNewFotoAsetAndQr.setVisibility(View.GONE);
                addNewFotoAsetAndQr2.setVisibility(View.GONE);
            }

            urlBa = aset.getBeritaAcara();
            if (urlBa != null ) {
                tvUploudBA.setText(urlBa.substring(0,10)+"...");
            }

            urlBAST = aset.getFileBAST();
            if (urlBAST != null ) {
                tvUploadFileBAST.setText(urlBAST.substring(0,10)+"...");
            }

            geotag1 = aset.getGeoTag1();

            geotag2 = aset.getGeoTag2();

            geotag3 = aset.getGeoTag3();

            geotag4 = aset.getGeoTag4();

            geotag5 = aset.getGeoTag5();


//                set selection spinners
            spinnerTipeAset.setSelection(aset.getAsetTipe()-1);
            spinnerJenisAset.setSelection(aset.getAsetJenis()-1);

            spinnerAsetKondisi.setSelection(aset.getAsetKondisi()-1);

            spinnerSubUnit.setSelection(aset.getAsetSubUnit()-1);
//
//            spinnerSistemTanam.setSelection(aset.getSistemTanam()-1);


            inpTglInput.setText(aset.getTglInput().split(" ")[0]);
            inpTglOleh.setText(aset.getTglOleh().split(" ")[0]);
            inpNilaiAsetSAP.setText(formatrupiah(Double.valueOf((aset.getNilaiOleh() != null) ? aset.getNilaiOleh() : 0 )));
            inpNilaiResidu.setText(formatrupiah(Double.valueOf((aset.getNilaiResidu() != null) ? aset.getNilaiResidu() : 0 )));

            try {

                if (aset.getAfdelingId() != 0) {
                        Log.d("afdelingg", String.valueOf(aset.getAfdelingId())+'\n'+mapAfdelingSpinner.get(aset.getAfdelingId()));

                    spinnerAfdeling.setSelection(mapAfdelingSpinner.get(aset.getAfdelingId()));

                }

                if (mapKodeSpinner.get(aset.getAsetKode()) != null) {

                    spinnerKodeAset.setSelection(mapKodeSpinner.get(aset.getAsetKode()));
                }
            } catch (Exception e){
            }


            checkApproved();
            checkQrCode();
            editVisibilityDynamic();
        } catch (Exception e) {
            e.printStackTrace();
        }


            }

    public String formatrupiah(Double number){
            Locale localeID = new Locale("IND","ID");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
            String formatRupiah =  numberFormat.format(number);
            String[] split = formatRupiah.split(",");
            int length = split[0].length();
            return split[0].substring(0,2)+". "+split[0].substring(2,length);
        }
    public void downloadBaFunc(){

                String title = URLUtil.guessFileName(AsemApp.BASE_URL_ASSET + "/" + urlBa,null,null);

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse( AsemApp.BASE_URL_ASSET + "/" + urlBa));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.allowScanningByMediaScanner();
                DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

                Toast.makeText(this, "Download Dimulai" , Toast.LENGTH_SHORT).show();

        }
    public void downloadBASTFunc(){

                String title = URLUtil.guessFileName(AsemApp.BASE_URL_ASSET + "/" + urlBAST,null,null);

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse( AsemApp.BASE_URL_ASSET + "/" + urlBAST));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.allowScanningByMediaScanner();
                DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

                Toast.makeText(this, "Download Dimulai" , Toast.LENGTH_SHORT).show();

        }

    public void editVisibilityDynamic(){
        TextView tvBa = findViewById(R.id.tvBa);
//        TextView tvPohon = findViewById(R.id.tvPohon);
        TextView tvBast = findViewById(R.id.tvBast);
        TextView tvFoto = findViewById(R.id.tvFoto);
        TextView tvAfdeling = findViewById(R.id.tvAfdeling);
        Spinner inpAfdeling = findViewById(R.id.inpAfdeling);
        TextView tvLuasTanaman = findViewById(R.id.luasTanaman);
        TextView tvLuasNonTanaman = findViewById(R.id.luasNonTanaman);
        TextView tvPersenKondisi = findViewById(R.id.tvPersenKondisi);
        TextView tvFileBAST = findViewById(R.id.tvUploadFileBAST);
        TextView tvUploadBAST = findViewById(R.id.tvUploadBAST);
        TextView tvTahunTanam = findViewById(R.id.tvTahunTanam);
        TextView tvSistemTanam = findViewById(R.id.tvSistemTanam);

        HorizontalScrollView scrollPartition = findViewById(R.id.scrollPartition);
//        Toast.makeText(getApplicationContext(),String.valueOf(spinnerSubUnit.getSelectedItemId()),Toast.LENGTH_LONG).show();
        if (spinnerTipeAset.getSelectedItemId() != 0) {
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
        } else {
            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
        }

        if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))) {

            tvTahunTanam.setVisibility(View.VISIBLE);
            inpTahunTanam.setVisibility(View.VISIBLE);
            tvSistemTanam.setVisibility(View.VISIBLE);
            spinnerSistemTanam.setVisibility(View.VISIBLE);

            tvPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
            tvPopTotalStdMaster.setVisibility(View.VISIBLE);
            tvPopPerHA.setVisibility(View.GONE);
            tvPresentasePopPerHA.setVisibility(View.GONE);
            inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
            inpPopTotalStdMaster.setVisibility(View.VISIBLE);
            inpPopPerHA.setVisibility(View.GONE);
            inpPresentasePopPerHA.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);

//            spinnerSistemTanam.setEnabled(true);
//            spinnerSistemTanam.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.rounded_spinner));

            Log.d("tanam", String.valueOf(spinnerSistemTanam.getText()));

            if("Mono".equals(String.valueOf(spinnerSistemTanam.getText()))){
                tvTahunTanam.setVisibility(View.VISIBLE);
                inpTahunTanam.setVisibility(View.VISIBLE);
                tvSistemTanam.setVisibility(View.VISIBLE);
                spinnerSistemTanam.setVisibility(View.VISIBLE);

                tvPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
                tvPopTotalStdMaster.setVisibility(View.VISIBLE);
                tvPopPerHA.setVisibility(View.VISIBLE);
                tvPresentasePopPerHA.setVisibility(View.VISIBLE);
                inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
                inpPopTotalStdMaster.setVisibility(View.VISIBLE);
                inpPopPerHA.setVisibility(View.VISIBLE);
                inpPresentasePopPerHA.setVisibility(View.VISIBLE);

                tvLuasTanaman.setVisibility(View.VISIBLE);
                inpLuasAset.setVisibility(View.VISIBLE);
                spinnerLuasSatuan.setVisibility(View.VISIBLE);
            }
//            else{
////                tvTahunTanam.setVisibility(View.GONE);
////                inpTahunTanam.setVisibility(View.GONE);
////                tvSistemTanam.setVisibility(View.GONE);
////                spinnerSistemTanam.setVisibility(View.GONE);
//
//                tvPopTotalPohonSaatIni.setVisibility(View.GONE);
//                tvPopTotalStdMaster.setVisibility(View.GONE);
//                tvPopPerHA.setVisibility(View.GONE);
//                tvPresentasePopPerHA.setVisibility(View.GONE);
//                inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//                inpPopTotalStdMaster.setVisibility(View.GONE);
//                inpPopPerHA.setVisibility(View.GONE);
//                inpPresentasePopPerHA.setVisibility(View.GONE);
//
////                tvLuasTanaman.setVisibility(View.GONE);
////                inpLuasAset.setVisibility(View.GONE);
//
//            }

            if("ZC06/S001/Tebu".equals(String.valueOf(spinnerKodeAset.getSelectedItem()))) {
                tvTahunTanam.setVisibility(View.VISIBLE);
                inpTahunTanam.setVisibility(View.VISIBLE);
                tvSistemTanam.setVisibility(View.GONE);
                spinnerSistemTanam.setVisibility(View.GONE);

                tvPopTotalPohonSaatIni.setVisibility(View.GONE);
                tvPopTotalStdMaster.setVisibility(View.GONE);
                tvPopPerHA.setVisibility(View.GONE);
                tvPresentasePopPerHA.setVisibility(View.GONE);
                inpPopTotalPohonSaatIni.setVisibility(View.GONE);
                inpPopTotalStdMaster.setVisibility(View.GONE);
                inpPopPerHA.setVisibility(View.GONE);
                inpPresentasePopPerHA.setVisibility(View.GONE);

                tvLuasTanaman.setVisibility(View.VISIBLE);
                inpLuasAset.setVisibility(View.VISIBLE);
                spinnerLuasSatuan.setVisibility(View.VISIBLE);

//                spinnerSistemTanam.setEnabled(false);
//                spinnerSistemTanam.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_not_clickable_spinner));
//                spinnerSistemTanam.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.bg_not_clickable_spinner));

            }
//            else{
////                tvTahunTanam.setVisibility(View.GONE);
////                inpTahunTanam.setVisibility(View.GONE);
////                tvSistemTanam.setVisibility(View.GONE);
////                spinnerSistemTanam.setVisibility(View.GONE);
//
//                tvPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//                tvPopTotalStdMaster.setVisibility(View.VISIBLE);
//                tvPopPerHA.setVisibility(View.VISIBLE);
//                tvPresentasePopPerHA.setVisibility(View.VISIBLE);
//                inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//                inpPopTotalStdMaster.setVisibility(View.VISIBLE);
//                inpPopPerHA.setVisibility(View.VISIBLE);
//                inpPresentasePopPerHA.setVisibility(View.VISIBLE);
//
////                tvLuasTanaman.setVisibility(View.GONE);
////                inpLuasAset.setVisibility(View.GONE);
//
//                spinnerSistemTanam.setEnabled(true);
////                spinnerSistemTanam.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.rounded_spinner));
//            }
        }
        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))) {

            tvTahunTanam.setVisibility(View.VISIBLE);
            inpTahunTanam.setVisibility(View.VISIBLE);
            tvSistemTanam.setVisibility(View.VISIBLE);
            spinnerSistemTanam.setVisibility(View.VISIBLE);

            tvPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
            tvPopTotalStdMaster.setVisibility(View.VISIBLE);
            tvPopPerHA.setVisibility(View.VISIBLE);
            tvPresentasePopPerHA.setVisibility(View.VISIBLE);
            inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
            inpPopTotalStdMaster.setVisibility(View.VISIBLE);
            inpPopPerHA.setVisibility(View.VISIBLE);
            inpPresentasePopPerHA.setVisibility(View.VISIBLE);

            tvLuasTanaman.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.VISIBLE);

            if(!"Mono".equals(String.valueOf(spinnerSistemTanam.getText()))){
                tvTahunTanam.setVisibility(View.VISIBLE);
                inpTahunTanam.setVisibility(View.VISIBLE);
                tvSistemTanam.setVisibility(View.VISIBLE);
                spinnerSistemTanam.setVisibility(View.VISIBLE);

                tvPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
                tvPopTotalStdMaster.setVisibility(View.VISIBLE);
                tvPopPerHA.setVisibility(View.GONE);
                tvPresentasePopPerHA.setVisibility(View.GONE);
                inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
                inpPopTotalStdMaster.setVisibility(View.VISIBLE);
                inpPopPerHA.setVisibility(View.GONE);
                inpPresentasePopPerHA.setVisibility(View.GONE);

                tvLuasTanaman.setVisibility(View.GONE);
                inpLuasAset.setVisibility(View.GONE);
                spinnerLuasSatuan.setVisibility(View.GONE);
            }

        }
        else {

            tvTahunTanam.setVisibility(View.GONE);
            inpTahunTanam.setVisibility(View.GONE);
            tvSistemTanam.setVisibility(View.GONE);
            spinnerSistemTanam.setVisibility(View.GONE);

            tvPopTotalPohonSaatIni.setVisibility(View.GONE);
            tvPopTotalStdMaster.setVisibility(View.GONE);
            tvPopPerHA.setVisibility(View.GONE);
            tvPresentasePopPerHA.setVisibility(View.GONE);
            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
            inpPopTotalStdMaster.setVisibility(View.GONE);
            inpPopPerHA.setVisibility(View.GONE);
            inpPresentasePopPerHA.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);
        }

//        if (spinnerTipeAset.getSelectedItemId() == 1 ) {
        if ("baru".equals(String.valueOf(spinnerTipeAset.getSelectedItem())) ) {
            //input PDF ba bast
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.VISIBLE);
            tvUploadBAST.setVisibility(View.VISIBLE);
            downloadBAST.setVisibility(View.VISIBLE);
        } else {
            //input PDF ba bast
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);
        }

        if (spinnerSubUnit.getSelectedItemId() == 1){
            inpAfdeling.setVisibility(View.VISIBLE);
            tvAfdeling.setVisibility(View.VISIBLE);
//            Toast.makeText(getApplicationContext(),String.valueOf(spinnerSubUnit.getSelectedItemId()),Toast.LENGTH_LONG).show();
        } else {
            inpAfdeling.setVisibility(View.GONE);
            tvAfdeling.setVisibility(View.GONE);
        }

        if (spinnerJenisAset.getSelectedItem().equals("non tanaman")) {
            tvTahunTanam.setVisibility(View.GONE);
            inpTahunTanam.setVisibility(View.GONE);
        } else {
            tvTahunTanam.setVisibility(View.VISIBLE);
            inpTahunTanam.setVisibility(View.VISIBLE);
        }


        if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            listBtnMap.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
            downloadBa.setVisibility(View.GONE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);

            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            if ("baru".equals(String.valueOf(spinnerTipeAset.getSelectedItem())) ) {
                //input PDF ba bast
                downloadBAST.setVisibility(View.VISIBLE);
                btnFileBAST.setVisibility(View.GONE);
                tvFileBAST.setVisibility(View.VISIBLE);
                tvUploadBAST.setVisibility(View.VISIBLE);
            }


            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

//            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            foto5rl.setVisibility(View.VISIBLE);
            map5.setVisibility(View.VISIBLE);

//            tvPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//            tvPopTotalStdMaster.setVisibility(View.VISIBLE);
//            tvPopPerHA.setVisibility(View.VISIBLE);
//            tvPresentasePopPerHA.setVisibility(View.VISIBLE);
//            inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//            inpPopTotalStdMaster.setVisibility(View.VISIBLE);
//            inpPopPerHA.setVisibility(View.VISIBLE);
//            inpPresentasePopPerHA.setVisibility(View.VISIBLE);

        }

        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
            downloadBa.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.GONE);
            tvBa.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);

//            inpKomoditi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);

            inpBtnMap.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

//            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            foto5rl.setVisibility(View.VISIBLE);
            map5.setVisibility(View.VISIBLE);

//            tvPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//            tvPopTotalStdMaster.setVisibility(View.VISIBLE);
//            tvPopPerHA.setVisibility(View.VISIBLE);
//            tvPresentasePopPerHA.setVisibility(View.VISIBLE);
//            inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//            inpPopTotalStdMaster.setVisibility(View.VISIBLE);
//            inpPopPerHA.setVisibility(View.VISIBLE);
//            inpPresentasePopPerHA.setVisibility(View.VISIBLE);

        }


        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.GONE);
            tvBa.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.GONE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

//            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            foto5rl.setVisibility(View.VISIBLE);
            map5.setVisibility(View.VISIBLE);

//            tvPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//            tvPopTotalStdMaster.setVisibility(View.VISIBLE);
//            tvPopPerHA.setVisibility(View.VISIBLE);
//            tvPresentasePopPerHA.setVisibility(View.VISIBLE);
//            inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//            inpPopTotalStdMaster.setVisibility(View.VISIBLE);
//            inpPopPerHA.setVisibility(View.VISIBLE);
//            inpPresentasePopPerHA.setVisibility(View.VISIBLE);
        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.GONE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.GONE);
            downloadBa.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);

            if ("baru".equals(String.valueOf(spinnerTipeAset.getSelectedItem())) ) {
                //input PDF ba bast
                downloadBAST.setVisibility(View.VISIBLE);
                btnFileBAST.setVisibility(View.GONE);
                tvFileBAST.setVisibility(View.VISIBLE);
                tvUploadBAST.setVisibility(View.VISIBLE);
            }


            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

//            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            foto5rl.setVisibility(View.GONE);
            map5.setVisibility(View.GONE);

//            tvPopTotalPohonSaatIni.setVisibility(View.GONE);
//            tvPopTotalStdMaster.setVisibility(View.GONE);
//            tvPopPerHA.setVisibility(View.GONE);
//            tvPresentasePopPerHA.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            tvBa.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.GONE);
//            inpKomoditi.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);

            downloadBa.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            inpBtnMap.setVisibility(View.GONE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

//            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            foto5rl.setVisibility(View.GONE);
            map5.setVisibility(View.GONE);

//            tvPopTotalPohonSaatIni.setVisibility(View.GONE);
//            tvPopTotalStdMaster.setVisibility(View.GONE);
//            tvPopPerHA.setVisibility(View.GONE);
//            tvPresentasePopPerHA.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

//            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            foto5rl.setVisibility(View.GONE);
            map5.setVisibility(View.GONE);

//            tvPopTotalPohonSaatIni.setVisibility(View.GONE);
//            tvPopTotalStdMaster.setVisibility(View.GONE);
//            tvPopPerHA.setVisibility(View.GONE);
//            tvPresentasePopPerHA.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            inpBtnMap.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.GONE);
//            inpKomoditi.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            spinnerLuasSatuan.setVisibility(View.VISIBLE);

            if ("baru".equals(String.valueOf(spinnerTipeAset.getSelectedItem())) ) {
                //input PDF ba bast
                downloadBAST.setVisibility(View.VISIBLE);
                btnFileBAST.setVisibility(View.GONE);
                tvFileBAST.setVisibility(View.VISIBLE);
                tvUploadBAST.setVisibility(View.VISIBLE);
            }

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);

            foto5rl.setVisibility(View.GONE);
            map5.setVisibility(View.GONE);

//            tvPopTotalPohonSaatIni.setVisibility(View.GONE);
//            tvPopTotalStdMaster.setVisibility(View.GONE);
//            tvPopPerHA.setVisibility(View.GONE);
//            tvPresentasePopPerHA.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);
        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) &&"rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.VISIBLE);
            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.VISIBLE);

            listBtnMap.setVisibility(View.GONE);
            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);

            foto5rl.setVisibility(View.GONE);
            map5.setVisibility(View.GONE);

//            tvPopTotalPohonSaatIni.setVisibility(View.GONE);
//            tvPopTotalStdMaster.setVisibility(View.GONE);
//            tvPopPerHA.setVisibility(View.GONE);
//            tvPresentasePopPerHA.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);

            foto5rl.setVisibility(View.GONE);
            map5.setVisibility(View.GONE);

//            tvPopTotalPohonSaatIni.setVisibility(View.GONE);
//            tvPopTotalStdMaster.setVisibility(View.GONE);
//            tvPopPerHA.setVisibility(View.GONE);
//            tvPresentasePopPerHA.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        } else {
            spinnerLuasSatuan.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);
            downloadBa.setVisibility(View.GONE);
            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            foto5rl.setVisibility(View.GONE);
            map5.setVisibility(View.GONE);

            tvPopTotalPohonSaatIni.setVisibility(View.GONE);
            tvPopTotalStdMaster.setVisibility(View.GONE);
            tvPopPerHA.setVisibility(View.GONE);
            tvPresentasePopPerHA.setVisibility(View.GONE);
            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
            inpPopTotalStdMaster.setVisibility(View.GONE);
            inpPopPerHA.setVisibility(View.GONE);
            inpPresentasePopPerHA.setVisibility(View.GONE);

            tvTahunTanam.setVisibility(View.GONE);
            inpTahunTanam.setVisibility(View.GONE);
            tvSistemTanam.setVisibility(View.GONE);
            spinnerSistemTanam.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);
        }
    }

    public void setAdapterAsetKode(){
        List<String> asetKode = new ArrayList<>();
        String aset_kode_temp;
        Integer i = 0;
        for (AsetKode2 a : asetKode2) {
            if (a.getAsetJenis()-1 == spinnerJenisAset.getSelectedItemId()) {

                if ((a.getAsetJenis()) == 2 ) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
                } else if ((a.getAsetJenis()) == 1) {
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
    public void checkApproved(){
        ViewGroup ln = findViewById(R.id.approveGroup);

        Integer hak_akses_id = Integer.valueOf(sharedPreferences.getString("hak_akses_id", "0"));
        if (statusPosisi == 2 || statusPosisi == 4 || statusPosisi == 6 || statusPosisi == 8 || statusPosisi == 10  ) {
            if (statusPosisi == 2 && hak_akses_id == 6 ) ln.setVisibility(View.VISIBLE);
            else if (statusPosisi == 4 && hak_akses_id == 5 ) ln.setVisibility(View.VISIBLE);
            else if (statusPosisi == 6 && hak_akses_id == 4 ) ln.setVisibility(View.VISIBLE);
            else if (statusPosisi == 8 && hak_akses_id == 3 ) ln.setVisibility(View.VISIBLE);
            else if (statusPosisi == 10 && hak_akses_id == 2 ) ln.setVisibility(View.VISIBLE);
            else {
                ln.setVisibility(View.GONE);
            }
        } else {
            ln.setVisibility(View.GONE);
        }
    }

    public void checkQrCode(){
        ViewGroup ln = findViewById(R.id.qrGroup);
        ViewGroup ln2 = findViewById(R.id.uploadFotoGroup);
        Integer hak_akses_id = Integer.valueOf(sharedPreferences.getString("hak_akses_id", "0"));
        ln2.setVisibility(View.VISIBLE);
        ln.setVisibility(View.VISIBLE);
        if (statusPosisi < 5) {
            ln.setVisibility(View.GONE);
            ln2.setVisibility(View.GONE);
        }


    }

    public void getFotoAsetUrl(String url){
        String[] parts = url.split("/");
        if (parts.length == 3) {

        } else if (parts.length == 2 ) {

        } else {

        }

    }
    public void getAllSpinnerData(){
        Call<AllSpinner> call = asetInterface.getAllSpinner();

        call.enqueue(new Callback<AllSpinner>() {
            @Override
            public void onResponse(Call<AllSpinner> call, Response<AllSpinner> response) {
                try {
                    if (!response.isSuccessful() && response.body().getData() == null) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
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
                    List<String> listSpinnerAfdeling = new ArrayList<>();
                    List<String> listSpinnerSistemTanam = new ArrayList<>();

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
                    setAdapterAsetKode();


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

                    // get afdeling
                    afdeling = dataAllSpinner.getAfdeling();
                    for (Afdelling at : dataAllSpinner.getAfdeling()){
                        listSpinnerUnit.add(at.getAfdelling_desc());
                    }

                    // get sistem tanam
                    for (SistemTanam at : dataAllSpinner.getSistemTanam()){
                        listSpinnerSistemTanam.add(at.getSt_desc());
                    }

                    setAfdelingAdapter();




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

//                    // set adapter sistem tanaman
//                    ArrayAdapter<String> adapterSistemTanaman = new ArrayAdapter<String>(getApplicationContext(),
//                            android.R.layout.simple_spinner_item, listSpinnerSistemTanam);
//                    adapterSistemTanaman.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerSistemTanam.setAdapter(adapterSistemTanaman);

//                // set adapter kode aset
//                ArrayAdapter<String> adapterKodeAset = new ArrayAdapter<String>(getApplicationContext(),
//                        android.R.layout.simple_spinner_item, listSpinnerKodeAset);
//                adapterKodeAset.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerKodeAset.setAdapter(adapterKodeAset);

                    // set adapter unit
                    ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, listSpinnerUnit);
                    adapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUnit.setAdapter(adapterUnit);
//                    Integer unit_id = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
                    spinnerUnit.setSelection(aset.getUnitId()-1);

                    // set adapter sub unit

                    ArrayAdapter<String> adapterSubUnit = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, listSpinnerSubUnit);
                    adapterSubUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubUnit.setAdapter(adapterSubUnit);

                    // set adapter afedeling
                    ArrayAdapter<String> adapterAfdeling = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, listSpinnerAfdeling);
                    adapterAfdeling.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAfdeling.setAdapter(adapterAfdeling);
                    dialog.dismiss();
                }catch(Exception e){
                    e.printStackTrace();
                }





            }

            @Override
            public void onFailure(Call<AllSpinner> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });

    }

    private void initCustomDialog(){

        customDialogApprove = new Dialog(DetailAsetActivity.this,R.style.MyAlertDialogTheme);
        customDialogApprove.setContentView(R.layout.dialog_approve);
        customDialogApprove.setCanceledOnTouchOutside(false);
        customDialogApprove.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        customDialogReject = new Dialog(DetailAsetActivity.this,R.style.MyAlertDialogTheme);
        customDialogReject.setContentView(R.layout.dialog_reject);
        customDialogReject.setCanceledOnTouchOutside(false);
        customDialogReject.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        customDialog.show();

        Button btnYa = customDialogApprove.findViewById(R.id.btnYaKirim);
        Button btnTidak = customDialogApprove.findViewById(R.id.btnTidakKirim);

        Button btnYa2 = customDialogReject.findViewById(R.id.btnYaKirim);
        Button btnTidak2 = customDialogReject.findViewById(R.id.btnTidakKirim);

        btnTidak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogReject.dismiss();
            }
        });

        btnYa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ketReject = customDialogReject.findViewById(R.id.inpRejctMessages);
                if ("".equals(ketReject.getText().toString().trim())) {
                    ketReject.setError("Wajib Diisi");
                    ketReject.requestFocus();
                    return;
                }

                Log.d("asetapix2",String.valueOf(ketReject.getText()));
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.addPart(MultipartBody.Part.createFormData("ket_reject",null,RequestBody.create(MediaType.parse("text/plain"), String.valueOf(ketReject.getText()))));
                MultipartBody multipartBody = builder
                        .build();
                String contentType = "multipart/form-data; charset=utf-8; boundary=" + multipartBody.boundary();
                Call<AsetApproveModel> call = asetInterface.rejectAset(id,contentType,multipartBody);


                call.enqueue(new Callback<AsetApproveModel>() {
                    @Override
                    public void onResponse(Call<AsetApproveModel> call, Response<AsetApproveModel> response) {
                        if (response.isSuccessful() && response.body() != null) {


                            startActivity(new Intent(DetailAsetActivity.this,LonglistAsetActivity.class));
                            customDialogReject.dismiss();
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(),"Kesalahan jaringan mohon coba lagi",Toast.LENGTH_LONG).show();
                            customDialogReject.dismiss();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<AsetApproveModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Kesalahan : " + t.getMessage() ,Toast.LENGTH_LONG).show();
                        customDialogReject.dismiss();
                        return;
                    }
                });
                customDialogReject.dismiss();
            }
        });

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogApprove.dismiss();
            }
        });

        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<AsetApproveModel> call = asetInterface.approveAset(id);

                call.enqueue(new Callback<AsetApproveModel>() {
                    @Override
                    public void onResponse(Call<AsetApproveModel> call, Response<AsetApproveModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            btnYa.setEnabled(false);
                            startActivity(new Intent(DetailAsetActivity.this,LonglistAsetActivity.class));

                            customDialogApprove.dismiss();
                            btnYa.setEnabled(true);
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(),"Kesalahan jaringan mohon coba lagi",Toast.LENGTH_LONG).show();
                            customDialogApprove.dismiss();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<AsetApproveModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Kesalahan : " + t.getMessage() ,Toast.LENGTH_LONG).show();
                        customDialogApprove.dismiss();
                        return;
                    }
                });
                customDialogApprove.dismiss();
            }
        });
    }

    private void addFotoQrAset(){
        dialog.show();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addPart(MultipartBody.Part.createFormData("foto_aset_qr",asetqrfoto.getName(),RequestBody.create(MediaType.parse("image/*"),asetqrfoto)));


        MultipartBody multipartBody = builder
                .build();
        String contentType = "multipart/form-data; charset=utf-8; boundary=" + multipartBody.boundary();



        Call<AsetApproveModel> call = asetInterface.addFotoAsetQr(id,contentType,multipartBody);

        call.enqueue(new Callback<AsetApproveModel>() {
            @Override
            public void onResponse(Call<AsetApproveModel> call, Response<AsetApproveModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dialog.dismiss();
                    startActivity(new Intent(DetailAsetActivity.this,LonglistAsetActivity.class));
                    finish();
                    return;
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Error : mohon coba lagi",Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<AsetApproveModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error : " + t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
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