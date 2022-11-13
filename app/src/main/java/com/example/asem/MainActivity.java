package com.example.asem;

import static com.example.asem.utils.utils.latitudeValue;
import static com.example.asem.utils.utils.longitudeValue;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asem.api.AsetInterface;
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
import com.example.asem.utils.GpsConverter;
import com.example.asem.utils.utils;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class MainActivity extends AppCompatActivity {
    Button inpBtnMap;
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

    private String baseUrl = "https://3d25-202-148-9-238.ap.ngrok.io/api/";
    private String baseUrlImg = "https://3d25-202-148-9-238.ap.ngrok.io";
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
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

    ActivityResultLauncher<Intent> activityCaptureFoto1 =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode== Activity.RESULT_OK){
                                File fotoTunggak = utils.savePictureResult(
                                        MainActivity.this, photoname1, fotoimg1, true
                                );
                                setExifLocation(fotoTunggak);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                                File fotoTunggak = utils.savePictureResult(
                                        MainActivity.this, photoname2, fotoimg2, true
                                );
                                setExifLocation(fotoTunggak);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                                File fotoTunggak = utils.savePictureResult(
                                        MainActivity.this, photoname3, fotoimg3, true
                                );
                                setExifLocation(fotoTunggak);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                                File fotoTunggak = utils.savePictureResult(
                                        MainActivity.this, photoname4, fotoimg4, true
                                );
                                setExifLocation(fotoTunggak);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLastLocation(MainActivity.this,getApplicationContext());

        editText = findViewById(R.id.inpTglMasukAset);
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

        foto1rl = findViewById(R.id.foto1);
        foto2rl = findViewById(R.id.foto2);
        foto3rl = findViewById(R.id.foto3);
        foto4rl = findViewById(R.id.foto4);

        fotoimg1 = findViewById(R.id.fotoimg1);
        fotoimg2 = findViewById(R.id.fotoimg2);
        fotoimg3 = findViewById(R.id.fotoimg3);
        fotoimg4 = findViewById(R.id.fotoimg4);
        inpBtnMap = findViewById(R.id.inpBtnMap);

        File img1;
        File img2;
        File img3;
        File img4;

        inpBtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(MainActivity.this,getApplicationContext());
                Log.d("mapsku",String.valueOf(latitudeValue) + " " + String.valueOf(longitudeValue));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag1));
                startActivity(intent);
            }
        });

//        listener spinner
        spinnerTipeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id = spinnerTipeAset.get(position).getId();
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

        initCalender();
        Button btnFile = findViewById(R.id.inpUploudBA);
        getTipeAset();
        getAsetJenis();
        getAsetKondisi();
        getDataAset();
        getKodeAset();

        btnFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder().setCheckPermission(true).setShowFiles(true).setShowImages(false).setShowVideos(false).setMaxSelection(1).setSuffixes("txt","pdf","doc","docx").setSkipZeroSizeFiles(true).build());
                startActivityForResult(intent,102);

            }


        });
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void initCalender(){
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == RESULT_OK && data != null ) {
            ArrayList<MediaFile> mediaFiles = data.getParcelableArrayListExtra(
                    FilePickerActivity.MEDIA_FILES
            );
            String path =  mediaFiles.get(0).getPath();
            source = new File(path);

            switch (requestCode) {
                case 102:
                    Toast.makeText(getApplicationContext(),"success uploud file",Toast.LENGTH_SHORT).show();
                    tvUploudBA.setText(source.getName());
                    break;

            }
        }
    }

    private void getAset(){
            Call<AsetModel> call = asetInterface.getAset(1);
            call.enqueue(new Callback<AsetModel>() {

                @Override
                public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {

                    if (!response.isSuccessful()){
    //                    tvResult.setText("Code: "+response.code());
                        Toast.makeText(getApplicationContext(),"ga bisa",Toast.LENGTH_SHORT);
                        return;
                    }
                    AsetModel m = response.body();
                    Log.d("apiaset", String.valueOf(m.getData().getAset_tipe()));
                    asetModel = m;
                    Toast.makeText(getApplicationContext(), String.valueOf(asetModel.getData().getAset_tipe()),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<AsetModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }
            });
        }

    private void getAsetJenis(){
        Call<AsetJenisModel> call = asetInterface.getAsetJenis();
        call.enqueue(new Callback<AsetJenisModel>() {
            @Override
            public void onResponse(Call<AsetJenisModel> call, Response<AsetJenisModel> response) {

                List<String> listSpinner = new ArrayList<>();

                for (int i = 0; i < response.body().getData().size();i++){
                    listSpinner.add(response.body().getData().get(i).getAset_jenis_desc());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerJenisAset.setAdapter(adapter);


                Call<AsetModel> call2 = asetInterface.getAset(1);
                call2.enqueue(new Callback<AsetModel>() {

                    @Override
                    public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {

                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_SHORT);
                            return;
                        }
//                        Toast.makeText(getApplicationContext(), String.valueOf(asetModel.getData().getAset_tipe()),Toast.LENGTH_SHORT).show();
                        spinnerJenisAset.setSelection(response.body().getData().getAset_tipe()-1);
                    }

                    @Override
                    public void onFailure(Call<AsetModel> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
//                        return;
                    }
                });
            }

            @Override
            public void onFailure(Call<AsetJenisModel> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
//                return;
            }
        });
    }

    private void getTipeAset(){
        Call<List<AsetTipe>> call = asetInterface.getAsetTipe();
        call.enqueue(new Callback<List<AsetTipe>>() {
            @Override
            public void onResponse(Call<List<AsetTipe>> call, Response<List<AsetTipe>> response) {
                Toast.makeText(getApplicationContext(),"bisa",Toast.LENGTH_LONG).show();
//                List<AsetTipe> allTipe = response.body();
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

//                spinnerTipeAset.setSelection(1);
//                if (asetModel != null){
//                    int spinnerPos = adapter.getPosition(asetModel.getData().getAset_tipe())
//                    spinnerTipeAset.setSelection();
//                }

//                Log.d("asetapi2",String.valueOf(asetModel.getData().getAset_tipe()));

                Call<AsetModel> call2 = asetInterface.getAset(1);
                call2.enqueue(new Callback<AsetModel>() {

                    @Override
                    public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {

//                        if (!response.isSuccessful()){
                            //                    tvResult.setText("Code: "+response.code());
//                            Toast.makeText(getApplicationContext(),"ga bisa",Toast.LENGTH_SHORT);
//                            return;
//                        }
                        AsetModel m = response.body();
                        Log.d("apiaset", String.valueOf(m.getData().getAset_tipe()));
                        asetModel = m;
                        Toast.makeText(getApplicationContext(), String.valueOf(asetModel.getData().getAset_tipe()),Toast.LENGTH_SHORT).show();
                        spinnerTipeAset.setSelection(asetModel.getData().getAset_tipe()-1);
                    }

                    @Override
                    public void onFailure(Call<AsetModel> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
//                        return;
                    }
                });

            }

            @Override
            public void onFailure(Call<List<AsetTipe>> call, Throwable t) {
//                Log.d("apitipeaset",t.getMessage());
            }
        });
    }

    private void getAsetKondisi(){
        Call<List<AsetKondisi>> call = asetInterface.getAsetKondisi();
        call.enqueue(new Callback<List<AsetKondisi>>() {
            @Override
            public void onResponse(Call<List<AsetKondisi>> call, Response<List<AsetKondisi>> response) {
                Toast.makeText(getApplicationContext(),"bisa",Toast.LENGTH_LONG).show();
//                List<AsetTipe> allTipe = response.body();
                List<String> listSpinner = new ArrayList<>();

                for (int i=0;i<response.body().size();i++){
                    listSpinner.add(response.body().get(i).getAset_kondisi_desc());
                }
//                Log.d("asetapi", listSpinner.get(0));

                // Set hasil result json ke dalam adapter spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAsetKondisi.setAdapter(adapter);

//                spinnerTipeAset.setSelection(1);
//                if (asetModel != null){
//                    int spinnerPos = adapter.getPosition(asetModel.getData().getAset_tipe())
//                    spinnerTipeAset.setSelection();
//                }

//                Log.d("asetapi2",String.valueOf(asetModel.getData().getAset_tipe()));

                Call<AsetModel> call2 = asetInterface.getAset(1);
                call2.enqueue(new Callback<AsetModel>() {

                    @Override
                    public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {

//                        if (!response.isSuccessful()){
//                            //                    tvResult.setText("Code: "+response.code());
//                            Toast.makeText(getApplicationContext(),"ga bisa",Toast.LENGTH_SHORT);
//                            return;
//                        }
                        AsetModel m = response.body();
                        asetModel = m;
//                        Toast.makeText(getApplicationContext(), String.valueOf(asetModel.getData().getAset_tipe()),Toast.LENGTH_SHORT).show();
                        spinnerAsetKondisi.setSelection(asetModel.getData().getAset_kondisi()-1);
                    }

                    @Override
                    public void onFailure(Call<AsetModel> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
//                        return;
                    }
                });

            }

            @Override
            public void onFailure(Call<List<AsetKondisi>> call, Throwable t) {
//                Log.d("apitipeaset",t.getMessage());
            }
        });
    }
    private void getDataAset(){
        Call<AsetModel> call = asetInterface.getAset(1);
        call.enqueue(new Callback<AsetModel>() {

            @Override
            public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {

//                if (!response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
//                    return;
//                }

                tvUploudBA.setText(response.body().getData().getBa_file());
                inpTglInput.setText(response.body().getData().getTgl_input().split(" ")[0]);
                editText.setText(response.body().getData().getTgl_input().split(" ")[0]);
                inpNoSAP.setText(String.valueOf(response.body().getData().getNomor_sap()));
                inpNamaAset.setText(response.body().getData().getAset_name());
                inpLuasAset.setText(String.valueOf(response.body().getData().getAset_luas()));
                inpNilaiAsetSAP.setText(String.valueOf(response.body().getData().getNilai_sap()));
                inpMasaPenyusutan.setText(String.valueOf(response.body().getData().getMasa_susut()));
                inpNomorBAST.setText(String.valueOf(response.body().getData().getNomor_bast()));
                inpNilaiResidu.setText(formatrupiah(Double.parseDouble(String.valueOf(response.body().getData().getNilai_residu()))));
                inpKeterangan.setText(response.body().getData().getKeterangan());
                inpUmrEkonomis.setText(utils.MonthToYear(response.body().getData().getUmur_ekonomis_in_month()));
                inpNilaiAsetSAP.setText(formatrupiah(Double.parseDouble(String.valueOf(response.body().getData().getUmur_ekonomis_in_month()))));
                String url1 = baseUrlImg+response.body().getData().getFoto_aset1();
                String url2 = baseUrlImg+response.body().getData().getFoto_aset2();
                String url3 = baseUrlImg+response.body().getData().getFoto_aset3();
                String url4 = baseUrlImg+response.body().getData().getFoto_aset4();
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


                geotag1 = response.body().getData().getGeo_tag1();
                geotag2 = response.body().getData().getGeo_tag2();
                geotag3 = response.body().getData().getGeo_tag3();
                geotag4 = response.body().getData().getGeo_tag4();


            }

            @Override
            public void onFailure(Call<AsetModel> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
//                return;
            }
        });
    }

    private void getKodeAset(){
        Call<AsetKodeModel> call = asetInterface.getAsetKode();
        call.enqueue(new Callback<AsetKodeModel>() {
            @Override
            public void onResponse(Call<AsetKodeModel> call, Response<AsetKodeModel> response) {
//                if (!response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
//                    return;
//                }

                List<AsetKode> asetKode = response.body().getData();
                List<String> listSpinner = new ArrayList<>();


                for ( AsetKode a : asetKode ){
//                    Log.d("dalemloop",listSpinner.get(i));
                    listSpinner.add(a.getAset_kode_desc());
                    Log.d("asetapi3",String.valueOf(a));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKodeAset.setAdapter(adapter);


                Call<AsetModel> call2 = asetInterface.getAset(1);
                call2.enqueue(new Callback<AsetModel>() {
                    @Override
                    public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {

//                        if (!response.isSuccessful()){
//                            Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
//                            return;
//                        }

                        AsetModel m = response.body();
//                        Toast.makeText(getApplicationContext(), String.valueOf(asetModel.getData().getAset_tipe()),Toast.LENGTH_SHORT).show();
                        spinnerKodeAset.setSelection(m.getData().getAset_kode());
                    }

                    @Override
                    public void onFailure(Call<AsetModel> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
//                        return;
                    }
                });
            }

            @Override
            public void onFailure(Call<AsetKodeModel> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
//                return;
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

    // https://www.geeksforgeeks.org/how-to-get-user-location-in-android/
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
                android.app.AlertDialog.Builder windowAlert = new android.app.AlertDialog.Builder(MainActivity.this);
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


    private void setExifLocation(File fileImage){
        try {
            getLastLocation(MainActivity.this,getApplicationContext());
            ExifInterface exif = new ExifInterface(fileImage.getAbsoluteFile().getAbsolutePath());
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, GpsConverter.convert(latitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, GpsConverter.latitudeRef(latitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, GpsConverter.convert(longitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, GpsConverter.latitudeRef(longitudeValue));
            exif.saveAttributes();
            String url = "https://www.google.com/maps/search/?api=1&query="+String.valueOf(latitudeValue)+"%2C"+String.valueOf(longitudeValue);
            geotag1 = url;
            Log.d("exifcek",String.valueOf(latitudeValue) + String.valueOf(utils.latitudeValue));
        } catch (Exception e){
            Toast.makeText(this, "Error when set Exif Location", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void editAset(){

            String tipe_aset = spinnerTipeAset.getSelectedItem().toString();
            String jenis_aset = spinnerJenisAset.getSelectedItem().toString();
            String kondisi_aset = spinnerAsetKondisi.getSelectedItem().toString();
            String tgl_input = inpTglInput.getText().toString().trim();
            String kode_aset = spinnerKodeAset.getSelectedItem().toString();
            String nama_aset = inpNamaAset.getText().toString().trim();
            String nomor_aset_sap = inpNoSAP.getText().toString().trim();
            String ba_file = tvUploudBA.getText().toString().trim();



            if (ba_file != "isi.pdf"){
                RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), source);
                MultipartBody.Part partBaFile = MultipartBody.Part.createFormData("foto_tunggak", source.getName(), requestBaFile);
            }

//            if (ba_file != "isi.pdf"){
//                RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), source);
//                MultipartBody.Part partBaFile = MultipartBody.Part.createFormData("foto_tunggak", source.getName(), requestBaFile);
//            }



            try {
                RequestBody requestTipeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tipe_aset));
                RequestBody requestAsetJenis = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jenis_aset));
                RequestBody requestAsetKondisi = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(kondisi_aset));
                RequestBody requestTglInput = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tgl_input));
                RequestBody requestKodeAset = RequestBody.create(MediaType.parse("text/plain"), tgl_input);
                // Request Foto
                RequestBody requestToken = RequestBody.create(MediaType.parse("text/plain"), OssPlanters.AuthOSS.getTokenUser(this));
                RequestBody requestBeratTunggak = RequestBody.create(MediaType.parse("text/plain"), sBeratTunggak);
                RequestBody requestBeratPucukan = RequestBody.create(MediaType.parse("text/plain"), sBeratPucukan);
                RequestBody requestBeratBrondolan = RequestBody.create(MediaType.parse("text/plain"), sBeratBrondolan);
                RequestBody requestPanjangJuring = RequestBody.create(MediaType.parse("text/plain"), sPanjangJuring);
                RequestBody requestLuasSample = RequestBody.create(MediaType.parse("text/plain"), sLuasSample);
                RequestBody requestPotensiLose = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jumlahPotensiLoses));

                RequestBody requestTanggalWaktu = RequestBody.create(MediaType.parse("text/plain"), sTanggalWaktu);
                RequestBody requestNamaPTA = RequestBody.create(MediaType.parse("text/plain"), sNamaPTA);
                RequestBody requestNamaRenteng = RequestBody.create(MediaType.parse("text/plain"), sNamaRenteng);

                Call<Model> call = apiTebuQualityInterface.editQcLoses(
                        OssPlanters.AuthOSS.getAuthAPI(this),
                        requestToken,
                        losesID,
                        requestAfdelID,
                        requestPetakID,
                        requestBeratTunggak,
                        requestBeratPucukan,
                        requestBeratBrondolan,
                        requestPanjangJuring,
                        requestLuasSample,
                        requestPotensiLose,
                        requestTanggalWaktu,
                        requestPtaID,
                        requestNamaPTA,
                        requestRentengID,
                        requestNamaRenteng
                );

                if (fotoTunggak != null && fotoPucukan != null && fotoBrondolan != null){
                    RequestBody requestFotoTunggak = RequestBody.create(MediaType.parse("multipart/form-file"), fotoTunggak);
                    MultipartBody.Part partFotoTunggak = MultipartBody.Part.createFormData("foto_tunggak", fotoTunggak.getName(), requestFotoTunggak);

                    RequestBody requestFotoPucukan = RequestBody.create(MediaType.parse("multipart/form-file"), fotoPucukan);
                    MultipartBody.Part partFotoPucukan = MultipartBody.Part.createFormData("foto_pucukan", fotoPucukan.getName(), requestFotoPucukan);

                    RequestBody requestFotoBrondolan = RequestBody.create(MediaType.parse("multipart/form-file"), fotoBrondolan);
                    MultipartBody.Part partFotoBrondolan = MultipartBody.Part.createFormData("foto_brondolan", fotoBrondolan.getName(), requestFotoBrondolan);

                    call = apiTebuQualityInterface.editQcLoses(
                            OssPlanters.AuthOSS.getAuthAPI(this),
                            requestToken,
                            losesID,
                            requestAfdelID,
                            requestPetakID,
                            requestBeratTunggak,
                            requestBeratPucukan,
                            requestBeratBrondolan,
                            requestPanjangJuring,
                            requestLuasSample,
                            requestPotensiLose,
                            requestTanggalWaktu,
                            requestPtaID,
                            requestNamaPTA,
                            requestRentengID,
                            requestNamaRenteng,
                            partFotoTunggak,
                            partFotoPucukan,
                            partFotoBrondolan
                    );

                } else if (fotoTunggak != null && fotoPucukan != null) {
                    RequestBody requestFotoTunggak = RequestBody.create(MediaType.parse("multipart/form-file"), fotoTunggak);
                    MultipartBody.Part partFotoTunggak = MultipartBody.Part.createFormData("foto_tunggak", fotoTunggak.getName(), requestFotoTunggak);
                    RequestBody requestFotoPucukan = RequestBody.create(MediaType.parse("multipart/form-file"), fotoPucukan);
                    MultipartBody.Part partFotoPucukan = MultipartBody.Part.createFormData("foto_pucukan", fotoPucukan.getName(), requestFotoPucukan);

                    call = apiTebuQualityInterface.editQcLosesDuaFoto(
                            OssPlanters.AuthOSS.getAuthAPI(this),
                            requestToken,
                            losesID,
                            requestAfdelID,
                            requestPetakID,
                            requestBeratTunggak,
                            requestBeratPucukan,
                            requestBeratBrondolan,
                            requestPanjangJuring,
                            requestLuasSample,
                            requestPotensiLose,
                            requestTanggalWaktu,
                            requestPtaID,
                            requestNamaPTA,
                            requestRentengID,
                            requestNamaRenteng,
                            partFotoTunggak,
                            partFotoPucukan
                    );
                } else if (fotoTunggak != null && fotoBrondolan != null) {
                    RequestBody requestFotoTunggak = RequestBody.create(MediaType.parse("multipart/form-file"), fotoTunggak);
                    MultipartBody.Part partFotoTunggak = MultipartBody.Part.createFormData("foto_tunggak", fotoTunggak.getName(), requestFotoTunggak);
                    RequestBody requestFotoBrondolan = RequestBody.create(MediaType.parse("multipart/form-file"), fotoBrondolan);
                    MultipartBody.Part partFotoBrondolan = MultipartBody.Part.createFormData("foto_brondolan", fotoBrondolan.getName(), requestFotoBrondolan);

                    call = apiTebuQualityInterface.editQcLosesDuaFoto(
                            OssPlanters.AuthOSS.getAuthAPI(this),
                            requestToken,
                            losesID,
                            requestAfdelID,
                            requestPetakID,
                            requestBeratTunggak,
                            requestBeratPucukan,
                            requestBeratBrondolan,
                            requestPanjangJuring,
                            requestLuasSample,
                            requestPotensiLose,
                            requestTanggalWaktu,
                            requestPtaID,
                            requestNamaPTA,
                            requestRentengID,
                            requestNamaRenteng,
                            partFotoTunggak,
                            partFotoBrondolan
                    );
                } else if (fotoPucukan != null && fotoBrondolan != null) {
                    RequestBody requestFotoPucukan = RequestBody.create(MediaType.parse("multipart/form-file"), fotoPucukan);
                    MultipartBody.Part partFotoPucukan = MultipartBody.Part.createFormData("foto_pucukan", fotoPucukan.getName(), requestFotoPucukan);
                    RequestBody requestFotoBrondolan = RequestBody.create(MediaType.parse("multipart/form-file"), fotoBrondolan);
                    MultipartBody.Part partFotoBrondolan = MultipartBody.Part.createFormData("foto_brondolan", fotoBrondolan.getName(), requestFotoBrondolan);

                    call = apiTebuQualityInterface.editQcLosesDuaFoto(
                            OssPlanters.AuthOSS.getAuthAPI(this),
                            requestToken,
                            losesID,
                            requestAfdelID,
                            requestPetakID,
                            requestBeratTunggak,
                            requestBeratPucukan,
                            requestBeratBrondolan,
                            requestPanjangJuring,
                            requestLuasSample,
                            requestPotensiLose,
                            requestTanggalWaktu,
                            requestPtaID,
                            requestNamaPTA,
                            requestRentengID,
                            requestNamaRenteng,
                            partFotoPucukan,
                            partFotoBrondolan
                    );
                } else if (fotoTunggak != null) {
                    RequestBody requestFotoTunggak = RequestBody.create(MediaType.parse("multipart/form-file"), fotoTunggak);
                    MultipartBody.Part partFotoTunggak = MultipartBody.Part.createFormData("foto_tunggak", fotoTunggak.getName(), requestFotoTunggak);
                    call = apiTebuQualityInterface.editQcLosesSatuFoto(
                            OssPlanters.AuthOSS.getAuthAPI(this),
                            requestToken,
                            losesID,
                            requestAfdelID,
                            requestPetakID,
                            requestBeratTunggak,
                            requestBeratPucukan,
                            requestBeratBrondolan,
                            requestPanjangJuring,
                            requestLuasSample,
                            requestPotensiLose,
                            requestTanggalWaktu,
                            requestPtaID,
                            requestNamaPTA,
                            requestRentengID,
                            requestNamaRenteng,
                            partFotoTunggak
                    );
                } else if (fotoPucukan != null) {
                    RequestBody requestFotoPucukan = RequestBody.create(MediaType.parse("multipart/form-file"), fotoPucukan);
                    MultipartBody.Part partFotoPucukan = MultipartBody.Part.createFormData("foto_pucukan", fotoPucukan.getName(), requestFotoPucukan);
                    call = apiTebuQualityInterface.editQcLosesSatuFoto(
                            OssPlanters.AuthOSS.getAuthAPI(this),
                            requestToken,
                            losesID,
                            requestAfdelID,
                            requestPetakID,
                            requestBeratTunggak,
                            requestBeratPucukan,
                            requestBeratBrondolan,
                            requestPanjangJuring,
                            requestLuasSample,
                            requestPotensiLose,
                            requestTanggalWaktu,
                            requestPtaID,
                            requestNamaPTA,
                            requestRentengID,
                            requestNamaRenteng,
                            partFotoPucukan
                    );
                } else if (fotoBrondolan != null) {
                    RequestBody requestFotoBrondolan = RequestBody.create(MediaType.parse("multipart/form-file"), fotoBrondolan);
                    MultipartBody.Part partFotoBrondolan = MultipartBody.Part.createFormData("foto_brondolan", fotoBrondolan.getName(), requestFotoBrondolan);
                    call = apiTebuQualityInterface.editQcLosesSatuFoto(
                            OssPlanters.AuthOSS.getAuthAPI(this),
                            requestToken,
                            losesID,
                            requestAfdelID,
                            requestPetakID,
                            requestBeratTunggak,
                            requestBeratPucukan,
                            requestBeratBrondolan,
                            requestPanjangJuring,
                            requestLuasSample,
                            requestPotensiLose,
                            requestTanggalWaktu,
                            requestPtaID,
                            requestNamaPTA,
                            requestRentengID,
                            requestNamaRenteng,
                            partFotoBrondolan
                    );
                }

                call.enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(@NotNull Call<Model> call, @NotNull Response<Model> response) {
                        if (response.isSuccessful() && response.body() != null){
                            loading.dismiss();
                            if (!response.body().getSuccess()){
                                Log.d(TAG, "onResponse: edit MBS : "+response.body().getMessage());
                                TmaTebuUtility.displayAlertDialogOK(
                                        context, OssPlanters.ERROR_RESPONSE_ERROR,
                                        "Error response edit qc loses \n"+response.body().getMessage()
                                );
                                return;
                            }
                            TmaTebuUtility.Ngetoast(context, response.body().getMessage());
                            Intent intent = new Intent(context, LonglistQcLoses.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            TmaTebuUtility.displayAlertDialogOK(
                                    context,
                                    OssPlanters.ERROR_RESPONSE_ERROR,
                                    "Error saat edit loses : "+response.code()
                            );
                        }
                        loading.dismiss();
                    }

                    @Override
                    public void onFailure(@NotNull Call<Model> call, @NotNull Throwable t) {
                        loading.dismiss();
                        TmaTebuUtility.displayAlertDialogOK(
                                context,
                                OssPlanters.ERROR_RETROFIT_FAIL,
                                "Input Loses Gagal Terhubung Server : \n"+t.getMessage()
                        );
                    }
                });


            } catch (Exception e ){
                loading.dismiss();
                TmaTebuUtility.displayAlertDialogOK(this, OssPlanters.ERROR_OSS_EXCEPTION, "Error input loses : \n"+e.getMessage());
                Log.d(TAG, "inputQcLoses: "+e.getMessage());
                e.printStackTrace();
            }


    }

}