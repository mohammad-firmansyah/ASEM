package com.example.asem;

import static com.example.asem.utils.utils.CurrencyToNumber;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Afdelling;
import com.example.asem.api.model.AfdellingModel;
import com.example.asem.api.model.AllSpinner;
import com.example.asem.api.model.AsetJenis;
import com.example.asem.api.model.AsetJenisModel;
import com.example.asem.api.model.AsetKode;
import com.example.asem.api.model.AsetKode2;
import com.example.asem.api.model.AsetKodeModel;
import com.example.asem.api.model.AsetKodeModel2;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetModel;
import com.example.asem.api.model.AsetModel2;
import com.example.asem.api.model.AsetTipe;
import com.example.asem.api.model.Data;
import com.example.asem.api.model.Data2;
import com.example.asem.api.model.DataAllSpinner;
import com.example.asem.api.model.Sap;
import com.example.asem.api.model.SubUnit;
import com.example.asem.api.model.SubUnitModel;
import com.example.asem.api.model.Unit;
import com.example.asem.api.model.UnitModel;
import com.example.asem.utils.GpsConverter;
import com.example.asem.utils.utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAsetActivity extends AppCompatActivity {
    Button inpBtnMap;
    Button btnFile;
    Button btnSubmit;
    Button map1;
    Button map2;
    Button map3;
    Button map4;
    Button btnYaKirim;
    Button btnTidakKirim;

    DataAllSpinner allSpinner;
    Context context;
    SharedPreferences sharedPreferences;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    Uri docUri;
    List<String> listSpinnerSap=new ArrayList<>();
    List<AsetKode2> asetKode2 = new ArrayList<>();
    List<Afdelling> afdeling = new ArrayList<>();


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

    public static String baseUrl = "http://202.148.9.226:7710/mnj_aset_production/public/api/";
    public String baseUrlImg = "http://202.148.9.226:7710/mnj_aset_production/public";
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
    Spinner spinnerAfdeling;
    Spinner spinnerSubUnit;
    Spinner spinnerUnit;
    Spinner spinnerKomoditi;

    EditText inpNamaAset;
    EditText inpHGU;
    TextView inpNoSAP;
    EditText inpLuasAset;
    EditText inpNilaiAsetSAP;
    EditText inpTglOleh;
    EditText inpMasaPenyusutan;
    EditText inpNomorBAST;
    EditText inpNilaiResidu;
    EditText inpKeterangan;
    EditText inpUmrEkonomis;
    EditText inpPersenKondisi;
    EditText inpAfdelingET;

    ViewGroup foto1rl;
    ViewGroup foto2rl;
    ViewGroup foto3rl;
    ViewGroup foto4rl;
    ViewGroup listBtnMap;

    ImageView fotoimg1;
    ImageView fotoimg2;
    ImageView fotoimg3;
    ImageView fotoimg4;

    Map<Integer, Integer> mapAfdelingSpinner = new HashMap<Integer, Integer>();
    Map<Integer, Integer> mapSpinnerAfdeling = new HashMap<Integer, Integer>();
    Map<Integer, String> mapAfdeling = new HashMap();
    Map<Integer, Integer> mapSap = new HashMap();

    Map<Integer,Integer> mapKodeSpinner = new HashMap();
    Map<Integer,Integer> mapSpinnerkode = new HashMap();
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

    Dialog customDialogAddAset;


    /**
     * Kode diambil dari jawaban irshad sheikh di stackoverflow
     * https://stackoverflow.com/a/65447195/18983047
     * @param context contect activity
     * @param uri uri file yang di pilih
     * @return file pada directory aplikasi yang bisa dipakai
     * @throws IOException ketika tidak dapat memuat file
     */
    public File getFile(Context context, Uri uri) throws IOException {
        // save file to directory Documents di package aplikasi
        File destinationFilename = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Toast.makeText(AddAsetActivity.this, "File tidak dapat dimuat", Toast.LENGTH_LONG).show();
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }

    // file yang dipilih akan di copy ke directory aplikasi
    public void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            assert data != null;
            Uri urifile = data.getData();
            try {
                bafile_file = getFile(this, urifile);
                String docPath = bafile_file.getAbsolutePath();
                Log.d("asetapix", "onActivityResult: path doc : "+docPath);
                Log.d("asetapix", "onActivityResult: masterpath : "+data.getData().getPath());
//                ExifInterface ei = new ExifInterface(bafile_file.getAbsolutePath());
                tvUploudBA.setText(bafile_file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("asetapix", "onActivityResult: "+ data.getData());
        }
    }


    ActivityResultLauncher<Intent> sActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        String path =  uri.getPath();
                        bafile_file = new File(Environment.getExternalStorageDirectory().getPath() + path);
                        Log.d("asetapix",Environment.getExternalStorageDirectory().getPath() + path);
                        tvUploudBA.setText(bafile_file.getAbsolutePath());
                    }
                }
            });

    public void openFileDialog() {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.addCategory(Intent.CATEGORY_OPENABLE);
        data.setType("*/*");
//        String[] mimetype = {"pdf/*"};
//        data.putExtra(Intent.EXTRA_MIME_TYPES,mimetype);
        data = Intent.createChooser(data,"Pilih Berita Acara");
        sActivityResultLauncher.launch(data);
    }
//    public void openfilechoser(){
////        Intent intent = null;
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
////            intent = new Intent(Intent.ACTION_VIEW, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
////        }
////        intent.addCategory(Intent.CATEGORY_OPENABLE);
////        intent.setType("*/*");
//
////        intent.addCategory(Intent.CATEGORY_OPENABLE);
////        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);
////        Intent chooseFileIntent = Intent.createChooser(intent, "Choose a file");
//
////        startActivity(intent);
////        Intent intent = new Intent(AddAsetActivity.this, FilePickerActivity.class);
////
////        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder().setCheckPermission(true).setShowFiles(true).setShowImages(false).setShowVideos(false).setMaxSelection(1).setSuffixes("txt","pdf","doc","docx").setSkipZeroSizeFiles(true).build());
////        startActivityForResult(intent,1);
////    Intent target = FileUtils.createGetContentIntent()
//
////        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
////
////        intent.addCategory(Intent.CATEGORY_OPENABLE);
////
////        intent.setType("application/pdf");
////
////        startActivityForResult(intent,1);
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");
//        resultLauncher.launch(intent);
//
//    }




    ActivityResultLauncher<Intent> activityCaptureFoto1 =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode== Activity.RESULT_OK){
                                img1 = utils.savePictureResult(
                                        AddAsetActivity.this, photoname1, fotoimg1, true
                                );
                                fotoimg1.getLayoutParams().width = 200;
                                fotoimg1.getLayoutParams().height = 200;
                                setExifLocation(img1,1);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(AddAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                                        AddAsetActivity.this, photoname2, fotoimg2, true
                                );
                                fotoimg2.getLayoutParams().width = 200;
                                fotoimg2.getLayoutParams().height = 200;
                                setExifLocation(img2,2);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(AddAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                                        AddAsetActivity.this, photoname3, fotoimg3, true
                                );
                                fotoimg3.getLayoutParams().width = 200;
                                fotoimg3.getLayoutParams().height = 200;
                                setExifLocation(img3,3);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(AddAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                                        AddAsetActivity.this, photoname4, fotoimg4, true
                                );
                                fotoimg4.getLayoutParams().width = 200;
                                fotoimg4.getLayoutParams().height = 200;
                                setExifLocation(img4,4);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(AddAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );
    private Dialog dialog;
    ListView listView;
    EditText  editTextSap;
    Dialog spinnerNoSap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = AddAsetActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        setContentView(R.layout.activity_add_aset);

        dialog = new Dialog(AddAsetActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();

        spinnerNoSap = new Dialog(AddAsetActivity.this);

        getLastLocation(AddAsetActivity.this,getApplicationContext());

        inpTglOleh = findViewById(R.id.inpTglMasukAset);
        tvUploudBA = findViewById(R.id.tvUploudBA);
        spinnerTipeAset = findViewById(R.id.inpTipeAset);
        spinnerJenisAset = findViewById(R.id.inpJenisAset);
        spinnerAsetKondisi = findViewById(R.id.inpKndsAset);
        spinnerKodeAset = findViewById(R.id.inpKodeAset);
        spinnerAfdeling = findViewById(R.id.inpAfdeling);
        spinnerAfdeling.setEnabled(false);
        spinnerSubUnit = findViewById(R.id.inpSubUnit);
        spinnerSubUnit.setEnabled(false);
        spinnerUnit = findViewById(R.id.inpUnit);
        spinnerUnit.setEnabled(false);
        inpNamaAset = findViewById(R.id.inpNamaAset);
        inpNoSAP = findViewById(R.id.inpNmrSAP);
        inpLuasAset = findViewById(R.id.inpLuasAset);
        inpNilaiAsetSAP = findViewById(R.id.inpNilaiAsetSAP);
        inpMasaPenyusutan = findViewById(R.id.inpMasaPenyusutan);
        inpNomorBAST = findViewById(R.id.inpNmrBAST);
        inpNilaiResidu = findViewById(R.id.inpNmrResidu);
        inpKeterangan = findViewById(R.id.inpKeterangan);
        inpJumlahPohon = findViewById(R.id.inpJmlhPohon);
        inpPersenKondisi = findViewById(R.id.inpPersenKondisi);
        inpHGU = findViewById(R.id.inpHGU);
//        spinnerKomoditi = findViewById(R.id.inpKomoditi);

//        List<String> listSpinner = new ArrayList<>();
//        listSpinner.add("excel");
//        listSpinner.add("pdf");
//        // Set hasil result json ke dalam adapter spinner
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
//                android.R.layout.simple_spinner_item, listSpinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerKomoditi.setAdapter(adapter);

        foto1rl = findViewById(R.id.foto1);
        foto2rl = findViewById(R.id.foto2);
        foto3rl = findViewById(R.id.foto3);
        foto4rl = findViewById(R.id.foto4);

        fotoimg1 = findViewById(R.id.fotoimg1);
        fotoimg2 = findViewById(R.id.fotoimg2);
        fotoimg3 = findViewById(R.id.fotoimg3);
        fotoimg4 = findViewById(R.id.fotoimg4);
//        inpBtnMap = findViewById(R.id.inpBtnMap);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnFile = findViewById(R.id.inpUploudBA);

//        handler


        btnSubmit.setOnClickListener(v -> initDialogAddAset());

        spinnerTipeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinnerIdTipeAsset = String.valueOf(position);
                    editVisibilityDynamic();

                }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerJenisAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdJenisAset = String.valueOf(position);

                editVisibilityDynamic();
                setAdapterAsetKode();
                Log.d("asetapix",spinnerIdJenisAset);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerKodeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdKodeAset = String.valueOf(position);
//                editVisibilityDynamic();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerAsetKondisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdAsetKondisi = String.valueOf(position);
                editVisibilityDynamic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerAfdeling.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdAfdeling = String.valueOf(position);
                editVisibilityDynamic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerSubUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdSubUnit = String.valueOf(position);
                editVisibilityDynamic();
//                setAfdelingAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setAfdelingAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        inpNoSAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerNoSap.setContentView(R.layout.searchable_spinner);
                spinnerNoSap.getWindow().setLayout(650,800);
                spinnerNoSap.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                spinnerNoSap.show();
                editTextSap=spinnerNoSap.findViewById(R.id.edit_text);
                listView=spinnerNoSap.findViewById(R.id.list_view);
//                spinnerNoSap.show();

//                if (listSpinnerSap.size() != 0){

                ArrayAdapter<String> adapterSap =new ArrayAdapter<>(AddAsetActivity.this, android.R.layout.simple_list_item_1,listSpinnerSap);
                listView.setAdapter(adapterSap);

                //                 Initialize array adapter

                // set adapter
                listView.setAdapter(adapterSap);

                    editTextSap.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            adapterSap.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // when item selected from list
                            // set selected item on textView
                            inpNoSAP.setText(adapterSap.getItem(position));

                            // Dismiss dialog
                            spinnerNoSap.dismiss();
                        }
                    });


//                }










            }
        });
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

        btnFile = findViewById(R.id.inpUploudBA);

        btnFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                openfilechoser();
//                openFileDialog();
//                Toast.makeText(getApplicationContext(),"hello from hell",Toast.LENGTH_LONG).show();

                Intent pickFile = new Intent(Intent.ACTION_GET_CONTENT);
                pickFile.addCategory(Intent.CATEGORY_OPENABLE);
                pickFile.setType("*/*");
                // Optionally, specify a URI for the file that should appear in the
                // system file picker when it loads.

                pickFile.putExtra(DocumentsContract.EXTRA_INITIAL_URI, docUri);
                startActivityForResult(pickFile, 1);
            }
        });


//        handler


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        asetInterface = retrofit.create(AsetInterface.class);





        initCalender();
        getAllSpinnerData();
//        setAfdelingAdapter();

    }

    void initDialogAddAset(){
        customDialogAddAset = new Dialog(AddAsetActivity.this, R.style.MyAlertDialogTheme);
        customDialogAddAset.setContentView(R.layout.dialog_addaset);
        customDialogAddAset.setCanceledOnTouchOutside(false);
        customDialogAddAset.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        btnYaKirim = customDialogAddAset.findViewById(R.id.btnYaKirim);
        btnTidakKirim = customDialogAddAset.findViewById(R.id.btnTidakKirim);
        customDialogAddAset.show();

        btnYaKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAset();

                //Toast.makeText(UpdateAsetActivity.this,"masuk", Toast.LENGTH_SHORT).show();
            }
        });

        btnTidakKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogAddAset.dismiss();
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
                new DatePickerDialog(AddAsetActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                android.app.AlertDialog.Builder windowAlert = new android.app.AlertDialog.Builder(AddAsetActivity.this);
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
            getLastLocation(AddAsetActivity.this,getApplicationContext());
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
                    Log.d("aseturl","urlku" + geotag1);
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


    public void spinnerValidation(){

        if (spinnerTipeAset.getSelectedItemId()== 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Tipet Aset Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
            dialog.dismiss();
            return;
        }


        if (spinnerJenisAset.getSelectedItemId()== 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Jenis Aset Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
            dialog.dismiss();
            return;
        }
        if (spinnerAsetKondisi.getSelectedItemId()== 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Kondisi Aset Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
            dialog.dismiss();
            return;
        }
        if (spinnerKodeAset.getSelectedItemId()== 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Kode Aset Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
            dialog.dismiss();
            return;
        }
        if (spinnerSubUnit.getSelectedItemId()== 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Sub Unit Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
            dialog.dismiss();
            return;
        }


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
        if (spinnerTipeAset.getSelectedItemId() != 0) {
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
        } else {
            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
        }

        if (spinnerSubUnit.getSelectedItemId() == 2){
            inpAfdeling.setVisibility(View.VISIBLE);
            tvAfdeling.setVisibility(View.VISIBLE);
//            Toast.makeText(getApplicationContext(),String.valueOf(spinnerSubUnit.getSelectedItemId()),Toast.LENGTH_LONG).show();
        } else {
            inpAfdeling.setVisibility(View.GONE);
            tvAfdeling.setVisibility(View.GONE);
        }

        if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
//            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);

            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
//            inpBtnMap.setVisibility(View.GONE);
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
//            listBtnMap.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);

//            inpKomoditi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);

//            inpBtnMap.setVisibility(View.GONE);

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
            tvPohon.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);

            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
//            listBtnMap.setVisibility(View.GONE);
//            inpBtnMap.setVisibility(View.GONE);

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);
        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
//            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);

            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
//            inpBtnMap.setVisibility(View.GONE);
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
//            listBtnMap.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);


            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
//            inpBtnMap.setVisibility(View.GONE);

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
//            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
//            inpBtnMap.setVisibility(View.GONE);

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
//            inpBtnMap.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);

//            inpKomoditi.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
//            listBtnMap.setVisibility(View.GONE);
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
//            inpBtnMap.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);

//            listBtnMap.setVisibility(View.GONE);
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
//            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
//            inpBtnMap.setVisibility(View.GONE);
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
//            listBtnMap.setVisibility(View.GONE);
            inpJumlahPohon.setVisibility(View.GONE);
            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);
            tvPohon.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
//            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.GONE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);
        }
    }
    public void addAset(){
        dialog.show();
        spinnerValidation();
//        Toast.makeText(getApplicationContext(), "hello im clicked", Toast.LENGTH_SHORT).show();
        if (inpNamaAset.getText().toString().equals("")) {
            dialog.dismiss();
            inpNamaAset.setError("nama harus diisi");
            inpNamaAset.requestFocus();
            return;
        }

        if (inpNoSAP.getText().toString().equals("")) {
            dialog.dismiss();
            inpNoSAP.setError("nomor SAP harus diisi");
            inpNoSAP.requestFocus();
            return;
        }

        if (spinnerJenisAset.getSelectedItemId() == 2) {
            if (Integer.parseInt(inpPersenKondisi.getText().toString()) > 100 || Integer.parseInt(inpPersenKondisi.getText().toString()) < 0) {
                dialog.dismiss();
                inpPersenKondisi.setError("Isian Persen Kondisi Wajib Minimal 0 Maksimal 100");
                inpPersenKondisi.requestFocus();
                return;
            }
        }

        if (inpNilaiAsetSAP.getText().toString().equals("")) {
            inpNilaiAsetSAP.setError("Nilai Perolehan Aset harus diisi");
            inpNilaiAsetSAP.requestFocus();
            dialog.dismiss();
            return;
        }

        if (inpTglOleh.getText().toString().equals("")) {
            inpTglOleh.setError("Tanggal Perolehan harus diisi");
            inpTglOleh.requestFocus();
            dialog.dismiss();
            return;
        }

        if (inpMasaPenyusutan.getText().toString().equals("")) {
            inpMasaPenyusutan.setError("Masa Penyusutan harus diisi");
            inpMasaPenyusutan.requestFocus();
            dialog.dismiss();
            return;
        }

        if (inpNilaiResidu.getText().toString().equals("")) {
            inpNilaiResidu.setError("Nilai Residu harus diisi");
            inpNilaiResidu.requestFocus();
            dialog.dismiss();
            return;
        }

        if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))){
            if ("normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))){
                if (img1 == null || img2 == null || img3 == null || img4 == null){
                    Toast.makeText(getApplicationContext(), "Foto Wajib Diisi Lengkap!", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                    return;
                }
            }
        }

        if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))){
            if ( "rusak".equals (String.valueOf(spinnerAsetKondisi.getSelectedItem()))){
                if (img1 == null || img2 == null || img3 == null || img4 == null){
                    Toast.makeText(getApplicationContext(), "Foto Wajib Diisi Lengkap!", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                    return;
                }
            }
        }


//        if (spinnerJenisAset.getSelectedItemId() == 1 || spinnerJenisAset.getSelectedItemId() == 3 ) {

            //          if (inpJumlahPohon.getText().toString().equals("")) {
            //            inpJumlahPohon.setError("Jumlah Pohon harus diisi");
            //          inpJumlahPohon.requestFocus();
            //        dialog.dismiss();
            //      return;
            // }
            //}


            String nama_aset = inpNamaAset.getText().toString().trim();
            Integer nomor_aset_sap = mapSap.get(Integer.parseInt(inpNoSAP.getText().toString().trim()));
            String ba_file = tvUploudBA.getText().toString().trim();
//        String luas_aset = inpLuasAset.getText().toString().trim();
            String luas_aset = String.valueOf(Double.parseDouble(inpLuasAset.getText().toString().trim()));
            String nilai_aset = String.valueOf(CurrencyToNumber(inpNilaiAsetSAP.getText().toString().trim()));


            String tgl_oleh = String.valueOf(inpTglOleh.getText().toString().trim() + " 00:00:00");
            String masa_susut = String.valueOf(inpMasaPenyusutan.getText().toString().trim());
            String nomor_bast = String.valueOf(inpNomorBAST.getText().toString().trim());
            String nilai_residu = String.valueOf(CurrencyToNumber(inpNilaiResidu.getText().toString().trim()));
            String keterangan = String.valueOf(inpKeterangan.getText().toString().trim());


            MultipartBody.Part img1Part = null, img2Part = null, img3Part = null, img4Part = null, partBaFile = null;


            try {

                RequestBody requestTipeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdTipeAsset));
                RequestBody requestJenisAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdJenisAset));
                RequestBody requestKondisiAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdAsetKondisi));
                RequestBody requestKodeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mapSpinnerkode.get(Integer.parseInt(String.valueOf(spinnerKodeAset.getSelectedItemId())))));
//            RequestBody requestKodeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf( Integer.parseInt(String.valueOf(spinnerKodeAset.getSelectedItemId())) + 1));
                RequestBody requestNamaAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nama_aset));
                RequestBody requestNomorAsetSAP = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nomor_aset_sap));
                Log.d("amanat14", String.valueOf(spinnerKodeAset.getSelectedItemId()));
                Integer kode = Math.toIntExact(spinnerKodeAset.getSelectedItemId());
                Log.d("amanat14", String.valueOf(kode));
                Log.d("amanat14", String.valueOf(mapSpinnerkode.get(Integer.parseInt(String.valueOf(spinnerKodeAset.getSelectedItemId() + 1)))));

                RequestBody requestLuasAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(luas_aset));
                RequestBody requestNilaiAsetSAP = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nilai_aset));
                RequestBody requestTglOleh = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(tgl_oleh));
                RequestBody requestMasaSusut = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(masa_susut));
                RequestBody requestNomorBAST = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nomor_bast));
                RequestBody requestNilaiResidu = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nilai_residu));
                RequestBody requestHGU = RequestBody.create(MediaType.parse("text/plain"), inpHGU.getText().toString().trim());

                RequestBody requestSubUnit = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdSubUnit));
                RequestBody requestUnit = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Integer.parseInt(String.valueOf(spinnerUnit.getSelectedItemId())) + 1));
                Integer afdeling_id = Integer.valueOf(sharedPreferences.getString("afdeling_id", "0"));
                RequestBody requestAfdeling = RequestBody.create(MediaType.parse("text/plain"), String.valueOf((afdeling_id)));


                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.addPart(MultipartBody.Part.createFormData("aset_name", null, requestNamaAset));
                builder.addPart(MultipartBody.Part.createFormData("aset_tipe", null, requestTipeAset));
                builder.addPart(MultipartBody.Part.createFormData("aset_jenis", null, requestJenisAset));
                builder.addPart(MultipartBody.Part.createFormData("aset_kondisi", null, requestKondisiAset));
                builder.addPart(MultipartBody.Part.createFormData("aset_kode", null, requestKodeAset));
                builder.addPart(MultipartBody.Part.createFormData("nomor_sap", null, requestNomorAsetSAP));
                builder.addPart(MultipartBody.Part.createFormData("hgu", null, requestHGU));

                builder.addPart(MultipartBody.Part.createFormData("aset_luas", null, requestLuasAset));
                builder.addPart(MultipartBody.Part.createFormData("tgl_oleh", null, requestTglOleh));
                builder.addPart(MultipartBody.Part.createFormData("nilai_residu", null, requestNilaiResidu));
                builder.addPart(MultipartBody.Part.createFormData("nilai_oleh", null, requestNilaiAsetSAP));
                builder.addPart(MultipartBody.Part.createFormData("nomor_bast", null, requestNomorBAST));
                builder.addPart(MultipartBody.Part.createFormData("masa_susut", null, requestMasaSusut));
                builder.addPart(MultipartBody.Part.createFormData("aset_sub_unit", null, requestSubUnit));
                builder.addPart(MultipartBody.Part.createFormData("unit_id", null, requestUnit));

                if (spinnerSubUnit.getSelectedItemId() == 2) {
                    builder.addPart(MultipartBody.Part.createFormData("afdeling_id", null, requestAfdeling));
                }

                if (spinnerJenisAset.getSelectedItemId() == 1 || spinnerJenisAset.getSelectedItemId() == 3) {
                    RequestBody requestJumlahPohon = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inpJumlahPohon.getText().toString()));
                    builder.addPart(MultipartBody.Part.createFormData("jumlah_pohon", null, requestJumlahPohon));
                }

                if (spinnerJenisAset.getSelectedItemId() == 2) {
                    RequestBody requestPersenKondisi = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inpPersenKondisi.getText().toString()));
                    builder.addPart(MultipartBody.Part.createFormData("persen_kondisi", null, requestPersenKondisi));
                }

                if (img1 != null) {
                    RequestBody requestGeoTag1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(geotag1));
                    builder.addPart(MultipartBody.Part.createFormData("foto_aset1", img1.getName(), RequestBody.create(MediaType.parse("image/*"), img1)));
                    builder.addPart(MultipartBody.Part.createFormData("geo_tag1", null, requestGeoTag1));
                }

                if (img2 != null) {
                    RequestBody requestGeoTag2 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(geotag2));
                    builder.addPart(MultipartBody.Part.createFormData("foto_aset2", img2.getName(), RequestBody.create(MediaType.parse("image/*"), img2)));
                    builder.addPart(MultipartBody.Part.createFormData("geo_tag2", null, requestGeoTag2));
                }

                if (img3 != null) {
                    RequestBody requestGeoTag3 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(geotag3));
                    builder.addPart(MultipartBody.Part.createFormData("foto_aset3", img3.getName(), RequestBody.create(MediaType.parse("image/*"), img3)));
                    builder.addPart(MultipartBody.Part.createFormData("geo_tag3", null, requestGeoTag3));
                }

                if (img4 != null) {
                    RequestBody requestGeoTag4 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(geotag4));
                    builder.addPart(MultipartBody.Part.createFormData("foto_aset4", img4.getName(), RequestBody.create(MediaType.parse("image/*"), img4)));
                    builder.addPart(MultipartBody.Part.createFormData("geo_tag4", null, requestGeoTag4));
                }


                if (bafile_file != null) {
                    RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), bafile_file);
                    partBaFile = MultipartBody.Part.createFormData("ba_file", bafile_file.getName(), requestBaFile);
                    builder.addPart(partBaFile);
                }

                if (inpKeterangan != null) {
                    RequestBody requestKeterangan = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(keterangan));
                    builder.addPart(MultipartBody.Part.createFormData("keterangan", null, requestKeterangan));
                    Log.d("asetapix", String.valueOf(requestKeterangan));
                }


                MultipartBody multipartBody = builder
                        .build();
                String contentType = "multipart/form-data; charset=utf-8; boundary=" + multipartBody.boundary();


                Call<AsetModel2> call = asetInterface.addAset(contentType, multipartBody);


                call.enqueue(new Callback<AsetModel2>() {

                    @Override
                    public void onResponse(Call<AsetModel2> call, Response<AsetModel2> response) {
                        if (!response.isSuccessful() && response.body() == null) {
                            dialog.dismiss();
                            customDialogAddAset.dismiss();
                            inpNoSAP.setError("Nomor SAP sudah ada");
                            inpNoSAP.requestFocus();
                            return;
                        }
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddAsetActivity.this, LonglistAsetActivity.class));
                        return;

                    }

                    @Override
                    public void onFailure(Call<AsetModel2> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "error " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "error " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
//            startActivity(new Intent(AddAsetActivity.this,LonglistAsetActivity.class));
            }


//            if (ba_file != "isi.pdf"){
//                RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), source);
//                MultipartBody.Part partBaFile = MultipartBody.Part.createFormData("foto_tunggak", source.getName(), requestBaFile);
//            }

        }

    public void getAllSpinnerData(){

        Call<AllSpinner> call = asetInterface.getAllSpinner();

        call.enqueue(new Callback<AllSpinner>() {
            @Override
            public void onResponse(Call<AllSpinner> call, Response<AllSpinner> response) {
                if (!response.isSuccessful() && response.body().getData() == null) {
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    return;
                }
                dialog.dismiss();
                allSpinner = response.body().getData();

                DataAllSpinner dataAllSpinner = response.body().getData();
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
                    mapSap.put(Integer.parseInt(at.getSap_desc()),at.getSap_id());
                    listSpinnerSap.add(at.getSap_desc());
                }

                // get afdeling
                Integer i=0;
                afdeling = dataAllSpinner.getAfdeling();
                for (Afdelling at : dataAllSpinner.getAfdeling()){
                    if (at.getUnit_id() == (spinnerUnit.getSelectedItemId()+1)) {
                        mapSpinnerAfdeling.put(at.getAfdelling_id(), i);
                        mapAfdeling.put(i, at.getAfdelling_desc());
                        listSpinnerAfdeling.add(at.getAfdelling_desc());
                        i++;
                    }
                }

                Integer afdeling_id = Integer.valueOf(sharedPreferences.getString("afdeling_id", "0"));

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

                    if (listView != null) {

                        ArrayAdapter<String> adapterSap = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, listSpinnerSap);
                        adapterSap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        listView.setAdapter(adapterSap);





                    }
                } catch(Exception e){
                    e.printStackTrace();
                }

//


                // set adapter unit
                ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinnerUnit);
                adapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnit.setAdapter(adapterUnit);
                sharedPreferences = AddAsetActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
                try {

                    Integer unit_id = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
                    spinnerUnit.setSelection(unit_id-1);
                } catch(Exception e){}



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
                ArrayAdapter<String> adapterAfdeling = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinnerAfdeling);
                adapterAfdeling.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAfdeling.setAdapter(adapterAfdeling);
                try {

                    if (listSpinnerAfdeling.size() != 0) {

                        spinnerAfdeling.setSelection(mapAfdelingSpinner.get(afdeling_id));

                    }
                } catch (Exception e){
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



    public void setAdapterAsetKode(){
        List<String> asetKode = new ArrayList<>();
        String aset_kode_temp = "";

        asetKode.add("pilih aset kode");
        Integer i = 1;
        for (AsetKode2 a : asetKode2) {


            if (a.getAsetJenis() == spinnerJenisAset.getSelectedItemId()) {

                if (a.getAsetJenis() == 2 ) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
                } else if (a.getAsetJenis() == 1) {
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


    public void setAfdelingAdapter(){
        List<String> afdelings = new ArrayList<>();
        Integer i = 1;
        for (Afdelling a:afdeling) {
            if (a.getUnit_id() == (spinnerUnit.getSelectedItemId()+1)) {
                mapAfdelingSpinner.put(a.getAfdelling_id(),i);
                mapSpinnerAfdeling.put(i,a.getAfdelling_id());
                afdelings.add(a.getAfdelling_desc());
                i++;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, afdelings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAfdeling.setAdapter(adapter);
    }
}