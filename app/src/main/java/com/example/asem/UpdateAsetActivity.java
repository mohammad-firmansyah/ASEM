package com.example.asem;

import static com.example.asem.utils.utils.CurrencyToNumber;
import static com.example.asem.utils.utils.latitudeValue;
import static com.example.asem.utils.utils.longitudeValue;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asem.api.AsetInterface;
import com.example.asem.api.MyErrorMessage;
import com.example.asem.api.model.Afdelling;
import com.example.asem.api.model.AfdellingModel;
import com.example.asem.api.model.Aset;
import com.example.asem.api.model.AsetJenis;
import com.example.asem.api.model.AsetJenisModel;
import com.example.asem.api.model.AsetKode;
import com.example.asem.api.model.AsetKodeModel;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetModel;
import com.example.asem.api.model.AsetTipe;
import com.example.asem.api.model.Data;
import com.example.asem.utils.GpsConverter;
import com.example.asem.utils.utils;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

//import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class UpdateAsetActivity extends AppCompatActivity {
    Data aset = new Data();
    Button inpBtnMap;
    Button btnFile;
    Button btnSubmit;
    Button map1;
    Button map2;
    Button map3;
    Button map4;
    double longitudeValue = 0;
    double latitudeValue = 0;

    FusedLocationProviderClient mFusedLocationClient;

    private static final String[] PERMISSIONS_LOCATION_AND_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int LOCATION_PERMISSION_AND_STORAGE = 33;

    public static String baseUrl = "http://202.148.9.226:9910/aset_mnj_repo/public/api/";
    public String baseUrlImg = "http://202.148.9.226:9910/aset_mnj_repo/public";
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    EditText inpJumlahPohon;
    TextView tvUploudBA;
    AsetModel asetModel;
    File source;
    private AsetInterface asetInterface;
    Spinner spinnerTipeAset;
    Spinner spinnerJenisAset;
    Spinner spinnerAsetKondisi;
    Spinner spinnerKodeAset;

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

    ViewGroup foto1rl;
    ViewGroup foto2rl;
    ViewGroup foto3rl;
    ViewGroup foto4rl;
    ViewGroup listBtnMap;

    ImageView fotoimg1;
    ImageView fotoimg2;
    ImageView fotoimg3;
    ImageView fotoimg4;


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


    public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

//        if (resultCode == RESULT_OK && data != null ) {
//            ArrayList<MediaFile> mediaFiles = data.getParcelableArrayListExtra(
//                    FilePickerActivity.MEDIA_FILES
//            );
//            String path =  mediaFiles.get(0).getPath();
//            bafile_file = new File(path);
//
//            switch (requestCode) {
//                case 102:
//                    Toast.makeText(getApplicationContext(),"success uploud berita acara",Toast.LENGTH_LONG).show();
//                    tvUploudBA.setText(bafile_file.getName());
//                    break;
//
//            }
//        }

        if (requestCode == 1 && resultCode == RESULT_OK && data != null ) {

            Uri uri = data.getData();
            String path =  uri.getPath();
            bafile_file = new File(path);
            Toast.makeText(getApplicationContext(),"sukses unggah berita acara",Toast.LENGTH_LONG).show();
            tvUploudBA.setText(bafile_file.getName());
        } else {
            Toast.makeText(UpdateAsetActivity.this,"gagal unggah file",Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void openfilechoser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(".pdf -> application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,1);
    }


    ActivityResultLauncher<Intent> activityCaptureFoto1 =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode== Activity.RESULT_OK){
                                img1 = utils.savePictureResult(
                                        UpdateAsetActivity.this, photoname1, fotoimg1, true
                                );
                                setExifLocation(img1,1);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );

    ActivityResultLauncher<Intent> activityCaptureFoto2 =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode== Activity.RESULT_OK){
                                img2 = utils.savePictureResult(
                                        UpdateAsetActivity.this, photoname2, fotoimg2, true
                                );
                                setExifLocation(img2,2);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );

    ActivityResultLauncher<Intent> activityCaptureFoto3 =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode== Activity.RESULT_OK){
                                img3 = utils.savePictureResult(
                                        UpdateAsetActivity.this, photoname3, fotoimg3, true
                                );
                                setExifLocation(img3,3);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );

    ActivityResultLauncher<Intent> activityCaptureFoto4 =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode== Activity.RESULT_OK){
                                img4 = utils.savePictureResult(
                                        UpdateAsetActivity.this, photoname4, fotoimg4, true
                                );
                                setExifLocation(img4,4);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(UpdateAsetActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();

//        progress = new ProgressBar(this);
//        progress.setMessage("Wait while loading...");
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();

        getLastLocation(UpdateAsetActivity.this,getApplicationContext());

        listBtnMap = findViewById(R.id.listMapButton);
        inpTglOleh = findViewById(R.id.inpTglMasukAset);
        tvUploudBA = findViewById(R.id.tvUploudBA);
        spinnerTipeAset = findViewById(R.id.inpTipeAset);
        spinnerJenisAset = findViewById(R.id.inpJenisAset);
        spinnerAsetKondisi = findViewById(R.id.inpKndsAset);
        spinnerKodeAset = findViewById(R.id.inpKodeAset);

        inpTglInput = findViewById(R.id.inpTglInput);
        inpTglInput.setEnabled(false);
        inpUmrEkonomis = findViewById(R.id.inpUmrEkonomis);
        inpUmrEkonomis.setEnabled(false);
        inpNamaAset = findViewById(R.id.inpNamaAset);
        inpNoSAP = findViewById(R.id.inpNmrSAP);
        inpLuasAset = findViewById(R.id.inpLuasAset);
        inpNilaiAsetSAP = findViewById(R.id.inpNilaiAsetSAP);
        inpMasaPenyusutan = findViewById(R.id.inpMasaPenyusutan);
        inpNomorBAST = findViewById(R.id.inpNmrBAST);
        inpNilaiResidu = findViewById(R.id.inpNmrResidu);
        inpKeterangan = findViewById(R.id.inpKeterangan);
        inpJumlahPohon = findViewById(R.id.inpJmlhPohon);

        foto1rl = findViewById(R.id.foto1);
        foto2rl = findViewById(R.id.foto2);
        foto3rl = findViewById(R.id.foto3);
        foto4rl = findViewById(R.id.foto4);

        fotoimg1 = findViewById(R.id.fotoimg1);
        fotoimg2 = findViewById(R.id.fotoimg2);
        fotoimg3 = findViewById(R.id.fotoimg3);
        fotoimg4 = findViewById(R.id.fotoimg4);
        inpBtnMap = findViewById(R.id.inpBtnMap);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> editAset());
        inpBtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(UpdateAsetActivity.this,getApplicationContext());
                Log.d("asetapix",String.valueOf(latitudeValue) + " " + String.valueOf(longitudeValue));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag1));
                startActivity(intent);
            }
        });

//        listener spinner

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
//        listener spinner


        foto1rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFotoQcLoses(photoname1,activityCaptureFoto1);

            }
        });

        foto2rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFotoQcLoses(photoname2,activityCaptureFoto2);

            }
        });
        foto3rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFotoQcLoses(photoname3,activityCaptureFoto3);

            }
        });

        foto4rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFotoQcLoses(photoname4,activityCaptureFoto4);

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        asetInterface = retrofit.create(AsetInterface.class);

        btnFile = findViewById(R.id.inpUploudBA);

        initCalender();
        getSpinnerData();
        setValueInput();

        btnFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openfilechoser();
            }


        });
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
                new DatePickerDialog(UpdateAsetActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    private void getAsetJenis(){
        Call<AsetJenisModel> call = asetInterface.getAsetJenis();
        call.enqueue(new Callback<AsetJenisModel>() {
            @Override
            public void onResponse(Call<AsetJenisModel> call, Response<AsetJenisModel> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<String> listSpinner = new ArrayList<>();

                for (int i = 0; i < response.body().getData().size();i++){
                    listSpinner.add(response.body().getData().get(i).getAset_jenis_desc());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerJenisAset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AsetJenisModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTipeAset(){
        Call<List<AsetTipe>> call = asetInterface.getAsetTipe();
        call.enqueue(new Callback<List<AsetTipe>>() {
            @Override
            public void onResponse(Call<List<AsetTipe>> call, Response<List<AsetTipe>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
//                    utils.Ngetoast(getApplicationContext(),);
                    return;
                }
                List<String> listSpinner = new ArrayList<>();

                for (int i=0;i<response.body().size();i++){
                    listSpinner.add(response.body().get(i).getAset_tipe_desc());
                }
                Log.d("asetapi", listSpinner.get(0));

                // Set hasil result json ke dalam adapter spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTipeAset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<AsetTipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAsetKondisi(){
        Call<List<AsetKondisi>> call = asetInterface.getAsetKondisi();
        call.enqueue(new Callback<List<AsetKondisi>>() {
            @Override
            public void onResponse(Call<List<AsetKondisi>> call, Response<List<AsetKondisi>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<String> listSpinner = new ArrayList<>();
                for (int i=0;i<response.body().size();i++){
                    listSpinner.add(response.body().get(i).getAset_kondisi_desc());
                }

                // Set hasil result json ke dalam adapter spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAsetKondisi.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<AsetKondisi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
    private void setValueInput(){
        Call<AsetModel> call = asetInterface.getAset(1);
        call.enqueue(new Callback<AsetModel>() {

            @Override
            public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {
                dialog.hide();
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }



                aset = response.body().getData();
                tvUploudBA.setText(response.body().getData().getBaFile());
                inpTglInput.setText(response.body().getData().getTglInput().split(" ")[0]);
                inpTglOleh.setText(response.body().getData().getTglInput().split(" ")[0]);
                inpNoSAP.setText(String.valueOf(response.body().getData().getNomorSap()));
                inpNamaAset.setText(response.body().getData().getAsetName());
                inpLuasAset.setText(String.valueOf(response.body().getData().getAsetLuas()));
                inpNilaiAsetSAP.setText(String.valueOf(response.body().getData().getNilaiOleh()));
                inpMasaPenyusutan.setText(String.valueOf(response.body().getData().getMasaSusut()));
                inpNomorBAST.setText(String.valueOf(response.body().getData().getNomorBast()));
                inpNilaiResidu.setText(formatrupiah(Double.parseDouble(String.valueOf(response.body().getData().getNilaiResidu()))));
                inpKeterangan.setText(response.body().getData().getKeterangan());
                inpUmrEkonomis.setText(utils.MonthToYear(response.body().getData().getUmurEkonomisInMonth()));
                inpNilaiAsetSAP.setText(formatrupiah(Double.parseDouble(String.valueOf(response.body().getData().getUmurEkonomisInMonth()))));
                String url1 = baseUrlImg+response.body().getData().getFotoAset1();
                String url2 = baseUrlImg+response.body().getData().getFotoAset2();
                String url3 = baseUrlImg+response.body().getData().getFotoAset3();
                String url4 = baseUrlImg+response.body().getData().getFotoAset4();
                fotoimg1.getLayoutParams().width = 200;
                fotoimg1.getLayoutParams().height = 200;
                Picasso.get().load(url1).resize(200,200).centerCrop().into(fotoimg1);

                fotoimg2.getLayoutParams().width = 200;
                fotoimg2.getLayoutParams().height = 200;
                Picasso.get().load(url2).resize(200,200).centerCrop().into(fotoimg2);

                fotoimg3.getLayoutParams().width = 200;
                fotoimg3.getLayoutParams().height = 200;
                Picasso.get().load(url3).resize(200,200).centerCrop().into(fotoimg3);

                fotoimg4.getLayoutParams().width = 200;
                fotoimg4.getLayoutParams().height = 200;
                Picasso.get().load(url4).resize(200,200).centerCrop().into(fotoimg4);


                geotag1 = response.body().getData().getGeoTag1();
                geotag2 = response.body().getData().getGeoTag2();
                geotag3 = response.body().getData().getGeoTag3();
                geotag4 = response.body().getData().getGeoTag4();


//                set selection spinners
                spinnerTipeAset.setSelection(response.body().getData().getAsetTipe()-1);
                spinnerJenisAset.setSelection(response.body().getData().getAsetTipe()-1);
                spinnerAsetKondisi.setSelection(response.body().getData().getAsetKondisi()-1);
                spinnerKodeAset.setSelection(response.body().getData().getAsetKode()-1);
                editVisibilityDynamic();
//                Log.d("asetapix", spinnerJenisAset.getSelectedItem().toString());
//                if (spinnerJenisAset.getSelectedItem().toString() == "tanaman" && spinnerAsetKondisi.getSelectedItem().toString() == "normal") {
//                    listBtnMap.setVisibility(View.VISIBLE);
//                    inpJumlahPohon.setVisibility(View.VISIBLE);
//
//                    tvUploudBA.setVisibility(View.GONE);
//                    inpBtnMap.setVisibility(View.GONE);
//                    btnFile.setVisibility(View.GONE);
//
//                }
//
//                if (spinnerJenisAset.getSelectedItem().toString() == "tanaman" && spinnerAsetKondisi.getSelectedItem().toString() == "rusak") {
//                    listBtnMap.setVisibility(View.VISIBLE);
//                    inpJumlahPohon.setVisibility(View.VISIBLE);
//                    tvUploudBA.setVisibility(View.VISIBLE);
//                    btnFile.setVisibility(View.VISIBLE);
//
//                    inpBtnMap.setVisibility(View.GONE);
//
//                }
//
//
//                if (spinnerJenisAset.getSelectedItem().toString() == "tanaman" && spinnerAsetKondisi.getSelectedItem().toString() == "hilang") {
//                    tvUploudBA.setVisibility(View.VISIBLE);
//                    btnFile.setVisibility(View.VISIBLE);
//
//                    inpJumlahPohon.setVisibility(View.GONE);
//                    listBtnMap.setVisibility(View.GONE);
//                    inpJumlahPohon.setVisibility(View.GONE);
//                    inpBtnMap.setVisibility(View.GONE);
//
//                }
//
//                if (spinnerJenisAset.getSelectedItem().toString() == "kayu" && spinnerAsetKondisi.getSelectedItem().toString() == "normal") {
//                    listBtnMap.setVisibility(View.VISIBLE);
//                    inpJumlahPohon.setVisibility(View.VISIBLE);
//
//                    tvUploudBA.setVisibility(View.GONE);
//                    inpBtnMap.setVisibility(View.GONE);
//                    btnFile.setVisibility(View.GONE);
//
//                }
//
//                if (spinnerJenisAset.getSelectedItem().toString() == "kayu" && spinnerAsetKondisi.getSelectedItem().toString() == "rusak") {
//                    listBtnMap.setVisibility(View.VISIBLE);
//                    inpJumlahPohon.setVisibility(View.VISIBLE);
//                    tvUploudBA.setVisibility(View.VISIBLE);
//                    btnFile.setVisibility(View.VISIBLE);
//
//                    inpBtnMap.setVisibility(View.GONE);
//
//                }
//
//                if (spinnerJenisAset.getSelectedItem().toString() == "kayu" && spinnerAsetKondisi.getSelectedItem().toString() == "hilang") {
//                    tvUploudBA.setVisibility(View.VISIBLE);
//                    btnFile.setVisibility(View.VISIBLE);
//
//                    inpJumlahPohon.setVisibility(View.GONE);
//                    listBtnMap.setVisibility(View.GONE);
//                    inpJumlahPohon.setVisibility(View.GONE);
//                    inpBtnMap.setVisibility(View.GONE);
//
//                }
//
//                if (spinnerJenisAset.getSelectedItem().toString() == "non-tanaman" && spinnerAsetKondisi.getSelectedItem().toString() == "normal") {
//                    inpBtnMap.setVisibility(View.VISIBLE);
//
//                    listBtnMap.setVisibility(View.GONE);
//                    inpJumlahPohon.setVisibility(View.GONE);
//                    tvUploudBA.setVisibility(View.GONE);
//                    btnFile.setVisibility(View.GONE);
//
//                }
//
//                if (spinnerJenisAset.getSelectedItem().toString() == "non-tanaman" && spinnerAsetKondisi.getSelectedItem().toString() == "rusak") {
//                    listBtnMap.setVisibility(View.VISIBLE);
//                    tvUploudBA.setVisibility(View.VISIBLE);
//                    btnFile.setVisibility(View.VISIBLE);
//
//                    inpJumlahPohon.setVisibility(View.GONE);
//                    inpBtnMap.setVisibility(View.GONE);
//
//                }
//
//                if (spinnerJenisAset.getSelectedItem().toString() == "non-tanaman" && spinnerAsetKondisi.getSelectedItem().toString() == "hilang") {
//                    tvUploudBA.setVisibility(View.VISIBLE);
//                    btnFile.setVisibility(View.VISIBLE);
//
//                    inpJumlahPohon.setVisibility(View.GONE);
//                    listBtnMap.setVisibility(View.GONE);
//                    inpJumlahPohon.setVisibility(View.GONE);
//                    inpBtnMap.setVisibility(View.GONE);
//
//                }


            }

            @Override
            public void onFailure(Call<AsetModel> call, Throwable t) {
                dialog.hide();
                Log.d("asetapix",t.getMessage());
            }
        });
    }

    private void getKodeAset(){
        Call<AsetKodeModel> call = asetInterface.getAsetKode();
        call.enqueue(new Callback<AsetKodeModel>() {
            @Override
            public void onResponse(Call<AsetKodeModel> call, Response<AsetKodeModel> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<AsetKode> asetKode = response.body().getData();
                List<String> listSpinner = new ArrayList<>();


                for ( AsetKode a : asetKode ){
                    listSpinner.add(a.getAset_kode_desc());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKodeAset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AsetKodeModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
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

    private void captureFotoQcLoses(String imageName, ActivityResultLauncher<Intent> activityLauncherName){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File fileImage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "/"+imageName);
        if (fileImage.exists()){
            fileImage.delete();
            Log.d("captImg", "captureImage: "+fileImage.exists());
            fileImage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageName);
        }
        Uri uriFile = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".provider", fileImage);
        Log.d("captimg",String.valueOf(uriFile));
//
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile);
        activityLauncherName.launch(takePictureIntent);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(Activity activity, Context context) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        latitudeValue = location.getLatitude();
                        longitudeValue = location.getLongitude();
                    }
                });
            } else {
                android.app.AlertDialog.Builder windowAlert = new android.app.AlertDialog.Builder(UpdateAsetActivity.this);
                windowAlert.setTitle("Lokasi Not Found");
                windowAlert.setMessage("Silahkan aktifkan lokasi terlebih dahulu");
                windowAlert.setNegativeButton("Ok", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    dialog.cancel();
                });
                windowAlert.show();
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            verifyStorageAndLocationPermissions(this);
        }
        Log.d("mapsku", "onCreate: latlong val : "+latitudeValue+"-"+longitudeValue);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        com.google.android.gms.location.LocationRequest mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    // get location callback
    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            longitudeValue = mLastLocation.getLongitude();
            latitudeValue = mLastLocation.getLatitude();
        }
    };


    public boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }



    public void verifyStorageAndLocationPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED || !checkPermissions()) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_LOCATION_AND_STORAGE,
                    LOCATION_PERMISSION_AND_STORAGE
            );
        }
    }


    private void setExifLocation(File fileImage,int list){
        try {
            getLastLocation(UpdateAsetActivity.this,getApplicationContext());
            ExifInterface exif = new ExifInterface(fileImage.getAbsoluteFile().getAbsolutePath());
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, GpsConverter.convert(latitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, GpsConverter.latitudeRef(latitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, GpsConverter.convert(longitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, GpsConverter.latitudeRef(longitudeValue));
            exif.saveAttributes();
            String url = "https://www.google.com/maps/search/?api=1&query="+String.valueOf(latitudeValue)+"%2C"+String.valueOf(longitudeValue);
            if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))) {
                if (list == 1) {

                    geotag1 = url;
                } else if (list == 2) {

                    geotag2 = url;
                } else if (list == 3) {

                    geotag3 = url;
                } else if (list == 4) {

                    geotag4 = url;
                }

            }else if("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))) {
                if (list == 1) {

                    geotag1 = url;
                } else if (list == 2) {

                    geotag2 = url;
                } else if (list == 3) {

                    geotag3 = url;
                } else if (list == 4) {

                    geotag4 = url;
                }
            }

            else {
                geotag1 = url;
            }

        } catch (Exception e){
            Toast.makeText(this, "Error when set Exif Location", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void editVisibilityDynamic(){
        if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);

            tvUploudBA.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            Toast.makeText(this, "tanaman", Toast.LENGTH_SHORT).show();

        }

        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);

            inpBtnMap.setVisibility(View.GONE);

        }


        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);

            inpJumlahPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);

            tvUploudBA.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);

            inpBtnMap.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);

            inpJumlahPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            inpBtnMap.setVisibility(View.VISIBLE);

            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) &&"rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            listBtnMap.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);

            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);

            inpJumlahPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);

        }
    }
    public void editAset(){
        dialog.show();
        Log.d("halo",String.valueOf(spinnerIdTipeAsset));
        String tgl_input = inpTglInput.getText().toString().trim() + " 00:00:00";

        String nama_aset = inpNamaAset.getText().toString().trim();
        String nomor_aset_sap = inpNoSAP.getText().toString().trim();
        String ba_file = tvUploudBA.getText().toString().trim();
        String luas_aset = inpLuasAset.getText().toString().trim();
        String nilai_aset = String.valueOf(CurrencyToNumber(inpNilaiAsetSAP.getText().toString().trim()));


        String tgl_oleh = String.valueOf(inpTglOleh.getText().toString().trim()) + " 00:00:00";
        String masa_susut = String.valueOf(inpMasaPenyusutan.getText().toString().trim());
        String nomor_bast = String.valueOf(inpMasaPenyusutan.getText().toString().trim());
        String nilai_residu = String.valueOf(CurrencyToNumber(inpNilaiResidu.getText().toString().trim()));
        String keterangan = String.valueOf(inpKeterangan.getText().toString().trim());
        String asetId = String.valueOf(aset.getAsetId());

        Log.d("asetapix","tanggal : " + tgl_input + " - " + tgl_oleh);


//            if (Integer.valueOf(spinnerIdTipeAsset) == 1) {
//                geotag2 = geotag1;
//                geotag3 = geotag1;
//                geotag4 = geotag1;
//            }

        MultipartBody.Part img1Part = null,img2Part = null,img3Part=null,img4Part=null,partBaFile=null;

        if (bafile_file != null){
            RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), bafile_file);
            partBaFile = MultipartBody.Part.createFormData("ba_file", bafile_file.getName(), requestBaFile);
        }

        if (img1 != null) {
            img1Part = MultipartBody.Part.createFormData("img1",img1.getName(),RequestBody.create(MediaType.parse("image/*"),img1));
        }

        if (img2 != null) {
            img2Part = MultipartBody.Part.createFormData("img2",img2.getName(),RequestBody.create(MediaType.parse("image/*"),img2));
        }

        if (img3 != null) {
            img3Part = MultipartBody.Part.createFormData("img3",img3.getName(),RequestBody.create(MediaType.parse("image/*"),img3));
        }

        if (img4 != null) {
            img4Part = MultipartBody.Part.createFormData("img4",img4.getName(),RequestBody.create(MediaType.parse("image/*"),img4));
        }


        try{

            RequestBody requestAsetId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(asetId));
            RequestBody requestTipeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdTipeAsset));
            RequestBody requestJenisAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdJenisAset));
            RequestBody requestKondisiAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdAsetKondisi));
            RequestBody requestKodeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdKodeAset));
            RequestBody requestNamaAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nama_aset));
            RequestBody requestNomorAsetSAP = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nomor_aset_sap));

            RequestBody requestGeoTag1 = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(geotag1));
            RequestBody requestGeoTag2 = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(geotag2));
            RequestBody requestGeoTag3 = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(geotag3));
            RequestBody requestGeoTag4 = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(geotag4));

            RequestBody requestLuasAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(luas_aset));
            RequestBody requestNilaiAsetSAP = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nilai_aset));
            RequestBody requestTglOleh = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tgl_oleh));
            RequestBody requestTglInput = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tgl_input));
            RequestBody requestMasaSusut = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(masa_susut));
            RequestBody requestNomorBAST = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nomor_bast));
            RequestBody requestNilaiResidu = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nilai_residu));
            RequestBody requestKeterangan = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(keterangan));



            if (bafile_file != null){
                RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), bafile_file);
                partBaFile = MultipartBody.Part.createFormData("ba_file", bafile_file.getName(), requestBaFile);
            }

            if (img1 != null) {
                img1Part = MultipartBody.Part.createFormData("foto_aset1",img1.getName(),RequestBody.create(MediaType.parse("image/*"),img1));
            }

            if (img2 != null) {
                img2Part = MultipartBody.Part.createFormData("foto_aset2",img2.getName(),RequestBody.create(MediaType.parse("image/*"),img2));
            }

            if (img3 != null) {
                img3Part = MultipartBody.Part.createFormData("foto_aset3",img3.getName(),RequestBody.create(MediaType.parse("image/*"),img3));
            }

            if (img4 != null) {
                img4Part = MultipartBody.Part.createFormData("foto_aset4",img4.getName(),RequestBody.create(MediaType.parse("image/*"),img4));
            }


//                Call<AsetModel> call = asetInterface.editAset(
//                        1,
//                        requestNamaAset,
//                        requestTipeAset,
//                        requestJenisAset,
//                        requestKondisiAset,
//                        requestKodeAset,
//                        requestNomorAsetSAP,
//                        img1Part,
//                        img2Part,
//                        img3Part,
//                        img4Part,
//                        requestGeoTag1,
//                        requestGeoTag2,
//                        requestGeoTag3,
//                        requestGeoTag4,
//                        requestGeoTag4,
//                        requestLuasAset,
//                        requestTglOleh,
//                        requestNilaiResidu,
//                        requestNilaiAsetSAP,
//                        requestNomorBAST,
//                        requestMasaSusut,
//                        requestKeterangan,
//                        ba_file
//                );

//            Data data = new Data();
//            data.setAsetId(1);
//            data.setAsetName("asetnama");
//            data.setAsetTipe(1);
//            data.setAsetKondisi(1);
//            data.setTglInput("08-09-22");
//            data.setTglOleh("08-09-22");
//            data.setAsetKode(1);
//            data.setNomorSap(111);
//            data.setAsetLuas(111);
//            data.setNilaiOleh(1111);
//            data.setMasaSusut("11");
//            data.setNomorBast("11");
//            data.setNilaiResidu(11000);
//            data.setKeterangan("ini dia");
//
//            Gson mGson = new Gson();
//            RequestBody dataBody = RequestBody.create(MultipartBody.FORM, mGson.toJson(data));
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.addPart(MultipartBody.Part.createFormData("aset_id",null,requestAsetId));
            builder.addPart(MultipartBody.Part.createFormData("aset_name",null,requestNamaAset));
            builder.addPart(MultipartBody.Part.createFormData("aset_tipe",null,requestTipeAset));
            builder.addPart(MultipartBody.Part.createFormData("aset_jenis",null,requestJenisAset));
            builder.addPart(MultipartBody.Part.createFormData("aset_kondisi",null,requestKondisiAset));
            builder.addPart(MultipartBody.Part.createFormData("aset_kode",null,requestKodeAset));
            builder.addPart(MultipartBody.Part.createFormData("nomor_sap",null,requestNomorAsetSAP));

            builder.addPart(MultipartBody.Part.createFormData("aset_luas",null,requestLuasAset));
            builder.addPart(MultipartBody.Part.createFormData("tgl_oleh",null,requestTglOleh));
            builder.addPart(MultipartBody.Part.createFormData("tgl_input",null,requestTglInput));
            builder.addPart(MultipartBody.Part.createFormData("nilai_residu",null,requestNilaiResidu));
            builder.addPart(MultipartBody.Part.createFormData("nilai_oleh",null,requestNilaiAsetSAP));
            builder.addPart(MultipartBody.Part.createFormData("nomor_bast",null,requestNomorBAST));
            builder.addPart(MultipartBody.Part.createFormData("masa_susut",null,requestMasaSusut));
            builder.addPart(MultipartBody.Part.createFormData("keterangan",null,requestKeterangan));

            if (bafile_file != null){
                RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), bafile_file);
                partBaFile = MultipartBody.Part.createFormData("ba_file", bafile_file.getName(), requestBaFile);
                builder.addPart(partBaFile);
            }

            if (img1 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset1", img1.getName(), RequestBody.create(MediaType.parse("image/*"), img1)));
                builder.addPart(MultipartBody.Part.createFormData("geo_tag1",null,requestGeoTag1));
                builder.addPart(MultipartBody.Part.createFormData("geo_tag2",null,requestGeoTag2));
                builder.addPart(MultipartBody.Part.createFormData("geo_tag3",null,requestGeoTag3));
                builder.addPart(MultipartBody.Part.createFormData("geo_tag4",null,requestGeoTag4));
            }

            if (img2 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset2",img2.getName(),RequestBody.create(MediaType.parse("image/*"),img2)));
            }

            if (img3 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset3",img3.getName(),RequestBody.create(MediaType.parse("image/*"),img3)));
            }

            if (img4 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset4",img4.getName(),RequestBody.create(MediaType.parse("image/*"),img4)));
            }

            MultipartBody multipartBody = builder
                    .build();



            String contentType = "multipart/form-data; charset=utf-8; boundary=" + multipartBody.boundary();



            Call<AsetModel> call = asetInterface.editAset(contentType,multipartBody);

            call.enqueue(new Callback<AsetModel>(){

                @Override
                public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {
                    if (response.isSuccessful() && response.body() != null){
                        Log.d("asetapix", "onResponse aset tipe: "+ response.body().getData().getAsetTipe());
                        Log.d("asetapix", "onResponse aset jenis: "+ response.body().getData().getAsetJenis());
                        Log.d("asetapix", "onResponse aset kondisi: "+ response.body().getData().getAsetKondisi());
                        Log.d("asetapix", "onResponse tgl input: "+ response.body().getData().getTglInput());
                        Log.d("asetapix", "onResponse tgl oleh: "+ response.body().getData().getTglOleh());
                        Log.d("asetapix", "onResponse asetKode: "+ response.body().getData().getAsetKode());
                        Log.d("asetapix", "onResponse aset Nma: "+ response.body().getData().getAsetName());
                        Log.d("asetapix", "onResponse nomor sap: "+ response.body().getData().getNomorSap());
                        Log.d("asetapix", "onResponse ba file: "+ response.body().getData().getBaFile());
//                                Log.d("asetapix", "onResponse foto aset 1: "+ response.body().getData().getFotoAset1());
//                                Log.d("asetapix", "onResponse foto aset 2: "+ response.body().getData().getFotoAset2());
//                                Log.d("asetapix", "onResponse foto aset 3: "+ response.body().getData().getFotoAset3());
//                                Log.d("asetapix", "onResponse foto aset 4: "+ response.body().getData().getFotoAset4());
                        Log.d("asetapix", "onResponse aset luas: "+ response.body().getData().getAsetLuas());
                        Log.d("asetapix", "onResponse nilai oleh: "+ response.body().getData().getNilaiOleh());
                        Log.d("asetapix", "onResponse masa susut: "+ response.body().getData().getMasaSusut());
                        Log.d("asetapix", "onResponse bast : "+ response.body().getData().getNomorBast());
                        Log.d("asetapix", "onResponse residu: "+ response.body().getData().getNilaiResidu());
                        Log.d("asetapix", "onResponse kterangan: "+ response.body().getData().getKeterangan());
                    }


                    Log.d("asetapix",String.valueOf(response.errorBody()));
                    Log.d("asetapix",String.valueOf(call.request().body()));
                    Log.d("asetapix",String.valueOf(call.request().url()));
                    Log.d("asetapix",String.valueOf(response.code()));

                }

                @Override
                public void onFailure(Call<AsetModel> call, Throwable t) {
                    Log.d("asetapix", "onError : "+t.getMessage());
                    Log.d("asetapix",String.valueOf(call.request().body()));
                    Log.d("asetapix",String.valueOf(call.request().url()));
                    Log.d("asetapix",String.valueOf(call.request().method()));
                }
            });
        }
        catch (Exception e ){

            Log.d("errorapi", "edit aset: "+e.getMessage());
            e.printStackTrace();
        }



//            if (ba_file != "isi.pdf"){
//                RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), source);
//                MultipartBody.Part partBaFile = MultipartBody.Part.createFormData("foto_tunggak", source.getName(), requestBaFile);
//            }

    }

    public void getSpinnerData(){
        getTipeAset();
        getKodeAset();
        getAsetJenis();
        getAsetKondisi();
    }
}