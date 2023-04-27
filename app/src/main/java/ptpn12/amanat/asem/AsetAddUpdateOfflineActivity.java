package ptpn12.amanat.asem;

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
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
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

import ptpn12.amanat.asem.api.model.Afdelling;
import ptpn12.amanat.asem.api.model.AlatAngkut;
import ptpn12.amanat.asem.api.model.AsetJenis;
import ptpn12.amanat.asem.api.model.AsetKode2;
import ptpn12.amanat.asem.api.model.AsetKondisi;
import ptpn12.amanat.asem.api.model.AsetTipe;
import ptpn12.amanat.asem.api.model.SistemTanam;
import ptpn12.amanat.asem.offline.model.DataAllSpinner;
import ptpn12.amanat.asem.api.model.Sap;
import ptpn12.amanat.asem.api.model.SubUnit;
import ptpn12.amanat.asem.api.model.Unit;
import ptpn12.amanat.asem.offline.AsetHelper;
import ptpn12.amanat.asem.offline.MappingHelper;
import ptpn12.amanat.asem.offline.model.Aset;
import ptpn12.amanat.asem.utils.GpsConverter;
import ptpn12.amanat.asem.utils.utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AsetAddUpdateOfflineActivity extends AppCompatActivity {
    private boolean isEdit = false;
    ViewGroup vwBast;
    Aset aset;
    List<Sap> sapAll = new ArrayList<>();
    List<SistemTanam> listSistemTanam = new ArrayList<>();
    private AsetHelper asetHelper;
    Integer afdeling_id = 0;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    Integer id = 0;
    SharedPreferences sharedPreferences;
    List<String> listSpinnerAlatAngkut = new ArrayList<>();
    Button inpBtnMap;
    Button btnFile;
    Button btnFileBAST;
    Button btnSubmit;
    Button map1;
    Button map2;
    Button map3;
    Button map4;
    Button map5;
    Button btnYaKirim;
    Button btnTidakKirim;

    Uri docUri;
    List<String> listSpinnerSap = new ArrayList<>();
    List<AsetKode2> asetKode2 = new ArrayList<>();
    List<Afdelling> afdeling2 = new ArrayList<>();


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

    final Calendar myCalendar = Calendar.getInstance();
    TextView tvUploudBA;
    TextView tvUploadFileBAST;
    Spinner spinnerTipeAset;
    Spinner spinnerJenisAset;
    Spinner spinnerAsetKondisi;
    Spinner spinnerKodeAset;
    Spinner spinnerAfdeling;
    Spinner spinnerSubUnit;
    Spinner spinnerUnit;
    Spinner spinnerAlatAngkut;
    Spinner spinnerLuasSatuan;

    Spinner spinnerSistemTanam;

    TextView inpNoSAP;
    TextView tvAlatAngkut;
    TextView tvPopTotalPohonSaatIni;
    TextView tvPopTotalStdMaster;
    TextView tvPopPerHA;
    TextView tvPresentasePopPerHA;

    EditText inpNamaAset;
    EditText inpHGU;
    EditText inpLuasAset;
    EditText inpNilaiAsetSAP;
    EditText inpTglOleh;
    EditText inpTglInput;

    EditText inpTahunTanam;
    EditText inpMasaPenyusutan;
    EditText inpNomorBAST;
    EditText inpNilaiResidu;
    EditText inpKeterangan;
    EditText inpPersenKondisi;
    EditText inpPopTotalPohonSaatIni;
    EditText inpPopTotalStdMaster;
    EditText inpPopPerHA;
    EditText inpPresentasePopPerHA;

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

    Map<Integer, Integer> mapAfdelingSpinner = new HashMap<>();
    Map<Integer, Integer> mapSpinnerAfdeling = new HashMap<>();
    Map<Integer, String> mapAfdeling = new HashMap();
    Map<Long, Integer> mapSap = new HashMap();

    Map<Integer, Integer> mapKodeSpinner = new HashMap();
    Map<Integer, Integer> mapSpinnerkode = new HashMap();
    Map<String, Integer> mapSpinnerkodeCoba = new HashMap();
    String photoname1 = "foto1.png";
    String photoname2 = "foto2.png";
    String photoname3 = "foto3.png";
    String photoname4 = "foto4.png";
    String photoname5 = "foto5.png";


    String geotag1;
    String geotag2;
    String geotag3;
    String geotag4;
    String geotag5;

    File img1;
    File img2;
    File img3;
    File img4;
    File img5;
    File bafile_file;
    File file_bast;

    String spinnerIdTipeAsset;
    String spinnerIdJenisAset;
    String spinnerIdAsetKondisi;
    String spinnerIdKodeAset;
    String spinnerIdAfdeling;
    String spinnerIdSubUnit;
    String spinnerIdUnit;

    Dialog customDialogAddAset;
    Dialog customDialogUpdateAset;


    /**
     * Kode diambil dari jawaban irshad sheikh di stackoverflow
     * https://stackoverflow.com/a/65447195/18983047
     *
     * @param context contect activity
     * @param uri     uri file yang di pilih
     * @return file pada directory aplikasi yang bisa dipakai
     * @throws IOException ketika tidak dapat memuat file
     */
    public File getFile(Context context, Uri uri) throws IOException {
        // save file to directory Documents di package aplikasi
        File destinationFilename = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Toast.makeText(AsetAddUpdateOfflineActivity.this, "File tidak dapat dimuat", Toast.LENGTH_LONG).show();
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

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Uri urifile = data.getData();
            try {
                bafile_file = getFile(this, urifile);
//                file_bast = getFile(this, urifile);
                String docPath = bafile_file.getAbsolutePath();
                Log.d("asetapix", "onActivityResult: path doc : " + docPath);
                Log.d("asetapix", "onActivityResult: masterpath : " + data.getData().getPath());
//                ExifInterface ei = new ExifInterface(bafile_file.getAbsolutePath());
                tvUploudBA.setText(bafile_file.getAbsolutePath());
//                tvUploadFileBAST.setText(file_bast.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("asetapix", "onActivityResult: " + data.getData());
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Uri urifile = data.getData();
            try {
                file_bast = getFile(this, urifile);
                tvUploadFileBAST.setText(file_bast.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
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

                        String path = uri.getPath();
                        bafile_file = new File(Environment.getExternalStorageDirectory().getPath() + path);
                        Log.d("asetapix", Environment.getExternalStorageDirectory().getPath() + path);
                        tvUploudBA.setText(bafile_file.getAbsolutePath());
                    }
                }
            });
    ActivityResultLauncher<Intent> BASTActivityResultLauncher = registerForActivityResult(
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

                        String path = uri.getPath();
                        file_bast = new File(Environment.getExternalStorageDirectory().getPath() + path);
                        Log.d("asetapix", Environment.getExternalStorageDirectory().getPath() + path);
                        tvUploadFileBAST.setText(file_bast.getAbsolutePath());
                    }
                }
            });

    public void openFileDialog() {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.addCategory(Intent.CATEGORY_OPENABLE);
        data.setType("*/*");
//        String[] mimetype = {"pdf/*"};
//        data.putExtra(Intent.EXTRA_MIME_TYPES,mimetype);
        data = Intent.createChooser(data, "Pilih Berita Acara");
        sActivityResultLauncher.launch(data);
        BASTActivityResultLauncher.launch(data);
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
//                            Intent data = activityResult.getData();

                            if (resultCode == Activity.RESULT_OK) {
                                img1 = utils.savePictureResult(
                                        AsetAddUpdateOfflineActivity.this, photoname1, fotoimg1, true
                                );
                                fotoimg1.getLayoutParams().width = 200;
                                fotoimg1.getLayoutParams().height = 200;
                                setExifLocation(img1, 1);

                            } else if (resultCode == Activity.RESULT_CANCELED) {
                                android.widget.Toast.makeText(AsetAddUpdateOfflineActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                            if (resultCode == Activity.RESULT_OK) {
                                img2 = utils.savePictureResult(
                                        AsetAddUpdateOfflineActivity.this, photoname2, fotoimg2, true
                                );
                                fotoimg2.getLayoutParams().width = 200;
                                fotoimg2.getLayoutParams().height = 200;
                                setExifLocation(img2, 2);
                            } else if (resultCode == Activity.RESULT_CANCELED) {
                                android.widget.Toast.makeText(AsetAddUpdateOfflineActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                            if (resultCode == Activity.RESULT_OK) {
                                img3 = utils.savePictureResult(
                                        AsetAddUpdateOfflineActivity.this, photoname3, fotoimg3, true
                                );
                                fotoimg3.getLayoutParams().width = 200;
                                fotoimg3.getLayoutParams().height = 200;
                                setExifLocation(img3, 3);
                            } else if (resultCode == Activity.RESULT_CANCELED) {
                                android.widget.Toast.makeText(AsetAddUpdateOfflineActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                            if (resultCode == Activity.RESULT_OK) {
                                img4 = utils.savePictureResult(
                                        AsetAddUpdateOfflineActivity.this, photoname4, fotoimg4, true
                                );
                                fotoimg4.getLayoutParams().width = 200;
                                fotoimg4.getLayoutParams().height = 200;
                                setExifLocation(img4, 4);
                            } else if (resultCode == Activity.RESULT_CANCELED) {
                                android.widget.Toast.makeText(AsetAddUpdateOfflineActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );


    ActivityResultLauncher<Intent> activityCaptureFoto5 =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode == Activity.RESULT_OK) {
                                img5 = utils.savePictureResult(
                                        AsetAddUpdateOfflineActivity.this, photoname5, fotoimg5, true
                                );
                                fotoimg5.getLayoutParams().width = 200;
                                fotoimg5.getLayoutParams().height = 200;
                                setExifLocation(img5, 5);
                            } else if (resultCode == Activity.RESULT_CANCELED) {
                                android.widget.Toast.makeText(AsetAddUpdateOfflineActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );
    private Dialog dialog;
    ListView listView;
    EditText editTextSap;
    Dialog spinnerNoSap;
    public static final String EXTRA_ASET = "extra_aset";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aset_add_update_offline);


        asetHelper = AsetHelper.getInstance(getApplicationContext());

        TextView tvTitle = findViewById(R.id.tvTitle);
        // globally
        //in your OnCreate() method
        tvTitle.setText("TAMBAH DATA OFFLINE");

        id = getIntent().getIntExtra("id", 0);
        if (id != 0) {
            isEdit = true;

            // globally
            //in your OnCreate() method
            tvTitle.setText("EDIT DATA OFFLINE");
        }


        sharedPreferences = AsetAddUpdateOfflineActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        afdeling_id = Integer.parseInt(sharedPreferences.getString("afdeling_id", "0"));

        dialog = new Dialog(AsetAddUpdateOfflineActivity.this, R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        spinnerNoSap = new Dialog(AsetAddUpdateOfflineActivity.this);

        getLastLocation(AsetAddUpdateOfflineActivity.this, getApplicationContext());



        vwBast = findViewById(R.id.vwBast);
        listBtnMap = findViewById(R.id.listMapButton);
        inpBtnMap = findViewById(R.id.inpBtnMap);
        inpTglOleh = findViewById(R.id.inpTglMasukAset);
        inpTglOleh.setEnabled(false);

        inpTglInput = findViewById(R.id.inpTglInput);
        tvUploudBA = findViewById(R.id.tvUploudBA);
        tvUploadFileBAST = findViewById(R.id.tvUploadFileBAST);
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
        inpMasaPenyusutan.setEnabled(false);
        inpNomorBAST = findViewById(R.id.inpNmrBAST);
        inpNilaiResidu = findViewById(R.id.inpNmrResidu);
        inpKeterangan = findViewById(R.id.inpKeterangan);
//        inpJumlahPohon = findViewById(R.id.inpJmlhPohon);
        inpPersenKondisi = findViewById(R.id.inpPersenKondisi);
        inpHGU = findViewById(R.id.inpHGU);
        spinnerLuasSatuan = findViewById(R.id.inpLuasSatuan);
        spinnerSistemTanam = findViewById(R.id.inpSistemTanam);

        tvPopTotalPohonSaatIni = findViewById(R.id.tvPopTotalIni);
        tvPopTotalStdMaster = findViewById(R.id.tvPopTotalStd);
        tvPopPerHA = findViewById(R.id.tvPopHektarIni);
        tvPresentasePopPerHA = findViewById(R.id.tvPopHektarStd);

        inpPopTotalPohonSaatIni = findViewById(R.id.inpPopTotalIni);
        inpPopTotalStdMaster = findViewById(R.id.inpPopTotalStd);

        inpPopPerHA = findViewById(R.id.inpPopHektarIni);
        inpPopPerHA.setEnabled(false);

        inpPresentasePopPerHA = findViewById(R.id.inpPopHektarStd);
        inpPresentasePopPerHA.setEnabled(false);

        inpTahunTanam = findViewById(R.id.inpTahunTanam);
        inpTahunTanam.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        List<String> listSpinnerSatuan = new ArrayList<>();

        listSpinnerSatuan.add("Ha");
        listSpinnerSatuan.add("m2");
        listSpinnerSatuan.add("Item");
        ArrayAdapter<String> adapterLuasSatuan = new ArrayAdapter<>(AsetAddUpdateOfflineActivity.this, android.R.layout.simple_list_item_1, listSpinnerSatuan);

        spinnerLuasSatuan.setAdapter(adapterLuasSatuan);
        spinnerAlatAngkut = findViewById(R.id.inpAlatAngkut);
        tvAlatAngkut = findViewById(R.id.tvAlatAngkut);

        foto1rl = findViewById(R.id.foto1);
        foto2rl = findViewById(R.id.foto2);
        foto3rl = findViewById(R.id.foto3);
        foto4rl = findViewById(R.id.foto4);
        foto5rl = findViewById(R.id.foto5);

        map1 = findViewById(R.id.map1);
        map2 = findViewById(R.id.map2);
        map3 = findViewById(R.id.map3);
        map4 = findViewById(R.id.map4);
        map5 = findViewById(R.id.map5);


        fotoimg1 = findViewById(R.id.fotoimg1);
        fotoimg2 = findViewById(R.id.fotoimg2);
        fotoimg3 = findViewById(R.id.fotoimg3);
        fotoimg4 = findViewById(R.id.fotoimg4);
        fotoimg5 = findViewById(R.id.fotoimg5);
        inpBtnMap = findViewById(R.id.inpBtnMap);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnFile = findViewById(R.id.inpUploudBA);
        btnFileBAST = findViewById(R.id.inpUploadBAST);


//        handler


        inpPopTotalPohonSaatIni.addTextChangedListener( new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!inpPopTotalPohonSaatIni.getText().toString().equals("") && !inpLuasAset.getText().toString().equals("")){

                    try{


                        Double popPerHa =  Double.parseDouble((inpPopTotalPohonSaatIni.getText().toString() != null || !inpPopTotalPohonSaatIni.getText().toString().equals("") ) ? String.valueOf(inpPopTotalPohonSaatIni.getText().toString()) : "0" ) / Double.parseDouble((inpLuasAset.getText().toString().trim().equals("")) ? "0" : inpLuasAset.getText().toString().trim());

                        if (Double.isNaN(popPerHa)) {
                            popPerHa = 0.0;
                        }

                        if (Double.isInfinite(popPerHa)) {
                            popPerHa = 0.0;

                        }



                        inpPopPerHA.setText(showPopulasiWithoutPercentage(String.valueOf(popPerHa)));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        inpPopTotalStdMaster.addTextChangedListener( new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!inpPopTotalStdMaster.getText().toString().equals("")){
                    try {


                        Double popPerHa =  Double.parseDouble((inpPopTotalPohonSaatIni.getText().toString() != null || !inpPopTotalPohonSaatIni.getText().toString().equals("") ) ? String.valueOf(inpPopTotalPohonSaatIni.getText().toString()) : "0" ) / Double.parseDouble((inpLuasAset.getText().toString().trim().equals("")) ? "0" : inpLuasAset.getText().toString().trim());
                        Double presentase = popPerHa / Double.parseDouble((inpPopTotalStdMaster.getText().toString() != null || inpPopTotalStdMaster.getText().toString().equals("") ) ? String.valueOf(inpPopTotalStdMaster.getText().toString()) : "0"  ) * 100;

                        if (Double.isNaN(presentase)) {
                            presentase = 0.0;
                        }

                        if (Double.isInfinite(presentase)){
                            presentase = 0.0;
                        }


                        inpPresentasePopPerHA.setText(showPopulasi(String.valueOf(presentase)));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        inpLuasAset.addTextChangedListener( new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!inpPopTotalStdMaster.getText().toString().equals("")){
                    try {

                        Double popPerHa =  Double.parseDouble((inpPopTotalPohonSaatIni.getText().toString() != null || !inpPopTotalPohonSaatIni.getText().toString().equals("") ) ? String.valueOf(inpPopTotalPohonSaatIni.getText().toString()) : "0" ) / Double.parseDouble((inpLuasAset.getText().toString().trim().equals("")) ? "0" : inpLuasAset.getText().toString().trim());
                        Double presentase = popPerHa / Double.parseDouble((inpPopTotalStdMaster.getText().toString() != null || inpPopTotalStdMaster.getText().toString().equals("") ) ? String.valueOf(inpPopTotalStdMaster.getText().toString()) : "0"  ) * 100;

                        if (Double.isNaN(presentase)) {
                            presentase = 0.0;
                        }

                        if (Double.isInfinite(presentase)){
                            presentase = 0.0;
                        }

                        if (Double.isNaN(popPerHa)) {
                            popPerHa = 0.0;
                        }

                        if (Double.isInfinite(popPerHa)) {
                            popPerHa = 0.0;

                        }

                        inpPresentasePopPerHA.setText(showPopulasi(String.valueOf(presentase)));
                        inpPopPerHA.setText(showPopulasiWithoutPercentage(String.valueOf(popPerHa)));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    initDialogUpdateAset();
                } else {
                    initDialogAddAset();
                }
            }
        });

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
                editVisibilityDynamic();
                setAdapterAsetKode();
                if (isEdit) {
                    setAdapterAsetKodeEdit();
//                    setValueInput();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerKodeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdKodeAset = String.valueOf(position);
                editVisibilityDynamic();
                if (spinnerKodeAset.getSelectedItem().equals("ZA08/Alat Pengangkutan")) {
                    spinnerAlatAngkut.setVisibility(View.VISIBLE);
                    tvAlatAngkut.setVisibility(View.VISIBLE);
                } else {
                    spinnerAlatAngkut.setVisibility(View.GONE);
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
                setAfdelingAdapter();
                if (isEdit) {
                    setValueInput();
                }
//                getAllSpinnerData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editVisibilityDynamic();
//                setAfdelingAdapter();
                if (isEdit) {

                    setValueInput();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerSistemTanam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editVisibilityDynamic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inpNoSAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerNoSap.setContentView(R.layout.searchable_spinner);
                spinnerNoSap.getWindow().setLayout(650, 800);
                spinnerNoSap.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                spinnerNoSap.show();
                editTextSap = spinnerNoSap.findViewById(R.id.edit_text);
                listView = spinnerNoSap.findViewById(R.id.list_view);
//                spinnerNoSap.show();

//                if (listSpinnerSap.size() != 0){

                ArrayAdapter<String> adapterSap = new ArrayAdapter<>(AsetAddUpdateOfflineActivity.this, android.R.layout.simple_list_item_1, listSpinnerSap);
                listView.setAdapter(adapterSap);

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
                        setValueSap(adapterSap.getItem(position));

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
                captureFotoQcLoses(photoname1, activityCaptureFoto1);

            }
        });

        foto2rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFotoQcLoses(photoname2, activityCaptureFoto2);

            }
        });
        foto3rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFotoQcLoses(photoname3, activityCaptureFoto3);

            }
        });

        foto4rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFotoQcLoses(photoname4, activityCaptureFoto4);

            }
        });

        foto5rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFotoQcLoses(photoname5, activityCaptureFoto5);

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
                if (!charSequence.toString().equals(setEditText)) {
                    inpNilaiAsetSAP.removeTextChangedListener(this);
                    String replace = charSequence.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()) {
                        setEditText = formatrupiah(Double.parseDouble(replace));
                        setTextv = setEditText;

                    } else {
                        setEditText = "";
                        setTextv = "Hasil Input";
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
                if (!charSequence.toString().equals(setEditText)) {
                    inpNilaiResidu.removeTextChangedListener(this);
                    String replace = charSequence.toString().replaceAll("[Rp. ]", "");
                    if (!replace.isEmpty()) {
                        setEditText = formatrupiah(Double.parseDouble(replace));
                        setTextv = setEditText;

                    } else {
                        setEditText = "";
                        setTextv = "Hasil Input";
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

        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openfilechoser();
//                openFileDialog();
//                Toast.makeText(getApplicationContext(),"hello from hell",Toast.LENGTH_LONG).show();

                Intent pickFile = new Intent(Intent.ACTION_GET_CONTENT);
                pickFile.addCategory(Intent.CATEGORY_OPENABLE);
                pickFile.setType("application/pdf");
                // Optionally, specify a URI for the file that should appear in the
                // system file picker when it loads.

                pickFile.putExtra(DocumentsContract.EXTRA_INITIAL_URI, docUri);
                startActivityForResult(pickFile, 1);
            }
        });

        btnFileBAST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openfilechoser();
//                openFileDialog();
//                Toast.makeText(getApplicationContext(),"hello from hell",Toast.LENGTH_LONG).show();

                Intent pickFile = new Intent(Intent.ACTION_GET_CONTENT);
                pickFile.addCategory(Intent.CATEGORY_OPENABLE);
                pickFile.setType("application/pdf");
                // Optionally, specify a URI for the file that should appear in the
                // system file picker when it loads.

                pickFile.putExtra(DocumentsContract.EXTRA_INITIAL_URI, docUri);
                startActivityForResult(pickFile, 2);
            }
        });


        getAllSpinnerData();
        initCalender();
        if (isEdit) {
            TextView tvTglInput = findViewById(R.id.tvTglInput);
            tvTglInput.setVisibility(View.VISIBLE);
            inpTglInput.setVisibility(View.VISIBLE);
            inpTglInput.setEnabled(false);
            setValueInput();
        }
    }

    void initDialogAddAset() {
        customDialogAddAset = new Dialog(AsetAddUpdateOfflineActivity.this, R.style.MyAlertDialogTheme);
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
            }
        });

        btnTidakKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogAddAset.dismiss();
            }
        });
    }

    void initDialogUpdateAset() {
        customDialogUpdateAset = new Dialog(AsetAddUpdateOfflineActivity.this, R.style.MyAlertDialogTheme);
        customDialogUpdateAset.setContentView(R.layout.dialog_submitupdate);
        customDialogUpdateAset.setCanceledOnTouchOutside(false);
        customDialogUpdateAset.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        btnYaKirim = customDialogUpdateAset.findViewById(R.id.btnYaKirim);
        btnTidakKirim = customDialogUpdateAset.findViewById(R.id.btnTidakKirim);
        customDialogUpdateAset.show();

        btnYaKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAset();
            }
        });

        btnTidakKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogUpdateAset.dismiss();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        inpTglOleh.setText(dateFormat.format(myCalendar.getTime()));
    }


    public void setAdapterAsetKode() {
        List<String> asetKode = new ArrayList<>();
        String aset_kode_temp;
        asetKode.add("Pilih Kode Aset");
        Integer i = 1;
        for (AsetKode2 a : asetKode2) {
            if (a.getAsetJenis() == spinnerJenisAset.getSelectedItemId()) {

                if (a.getAsetJenis() == 2) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
                } else if (a.getAsetJenis() == 1) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                } else {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                }

                mapKodeSpinner.put(a.getAsetKodeId(), i);
                mapSpinnerkode.put(i, a.getAsetKodeId());

                i++;
                asetKode.add(aset_kode_temp);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, asetKode);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKodeAset.setAdapter(adapter);

    }

    public void setAdapterAsetKodeEdit() {
        List<String> asetKode = new ArrayList<>();
        String aset_kode_temp;
        Integer i = 0;
        for (AsetKode2 a : asetKode2) {
            if (a.getAsetJenis() == spinnerJenisAset.getSelectedItemId()) {

                if ((a.getAsetJenis()) == 2) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
                } else if ((a.getAsetJenis()) == 1) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                } else {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                }

                mapKodeSpinner.put(a.getAsetKodeId(), i);
                Log.d("map-1", String.valueOf(mapKodeSpinner.get(i)));
                mapSpinnerkode.put(i, a.getAsetKodeId());
                mapSpinnerkodeCoba.put(aset_kode_temp, a.getAsetKodeId());
                Log.d("map-2", String.valueOf(mapSpinnerkode.get(i)));

                i++;
                asetKode.add(aset_kode_temp);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, asetKode);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKodeAset.setAdapter(adapter);

    }

    public void setAfdelingAdapter() {
//        List<String> afdelings = new ArrayList<>();
//        afdelings.add("pilih afdeling");
//        Integer i = 1;
//        for (Afdelling a:afdeling2) {
//            if (a.getUnit_id() == (spinnerUnit.getSelectedItemId()+1)) {
//                afdelings.add(a.getAfdelling_desc());
//                mapAfdelingSpinner.put(a.getAfdelling_id(),i);
//                i++;
//            }
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
//                android.R.layout.simple_spinner_item, afdelings);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerAfdeling.setAdapter(adapter);


    }


    public void initCalender() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        inpTglOleh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AsetAddUpdateOfflineActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    private Integer getAlatAngkut(String alatAngkut) {
        Integer i = 0;
        for (String a : listSpinnerAlatAngkut) {
            if (alatAngkut.equals(a)) {

                return i;
            }
            i++;
        }
        return 0;
    }


    private void setValueInput() {
        asetHelper.open();
        Cursor data = asetHelper.queryById(String.valueOf(id));
        aset = MappingHelper.mapCursorToArrayAset(data);



        if (aset.getBeritaAcara() != null) {
            tvUploudBA.setText(aset.getBeritaAcara());
        }

        if (aset.getFileBAST() != null) {
            tvUploadFileBAST.setText(aset.getFileBAST());
        }

        if (aset.getAlat_pengangkutan() != null) {
            spinnerAlatAngkut.setSelection(getAlatAngkut(aset.getAlat_pengangkutan()));
        }

        if (aset.getSatuan_luas() != null) {
            if (aset.getSatuan_luas().equals("Ha")) {
                spinnerLuasSatuan.setSelection(0);
            } else if (aset.getSatuan_luas().equals("m2")) {
                spinnerLuasSatuan.setSelection(1);
            } else {
                spinnerLuasSatuan.setSelection(2);
            }
        }

        if (aset.getAsetJenis().equals("1") || aset.getAsetJenis().equals("3") ) {
            inpPopTotalPohonSaatIni.setText(String.valueOf(aset.getPop_total_ini()));
            inpPopTotalStdMaster.setText(String.valueOf(aset.getPop_total_std()));
            inpPopPerHA.setText(String.valueOf(aset.getPop_per_ha()));
            inpPresentasePopPerHA.setText(showPopulasi(String.valueOf(aset.getPresentase_pop_per_ha())));
            spinnerSistemTanam.setSelection(getSistemTanamByName(aset.getSistem_tanam()));
            inpTahunTanam.setText(String.valueOf(aset.getTahun_tanam()));
        }


        inpTglInput.setText(aset.getTglInput());
        inpTglOleh.setText(aset.getTglOleh().split(" ")[0]);
        inpNoSAP.setText(aset.getNomorSap());
        inpNamaAset.setText(aset.getAsetName());
        inpLuasAset.setText(String.valueOf(aset.getAsetLuas()));
        inpNilaiAsetSAP.setText(String.valueOf(aset.getNilaiOleh()));

        inpMasaPenyusutan.setText(String.valueOf(aset.getMasaSusut()));
        inpMasaPenyusutan.setEnabled(false);


        inpNomorBAST.setText(String.valueOf(aset.getNomorBast()));
        inpNilaiResidu.setText(formatrupiah(Double.parseDouble(String.valueOf(aset.getNilaiResidu()))));
        inpKeterangan.setText(aset.getKeterangan());
//                inpUmrEkonomis.setText(utils.MonthToYear(aset.getUmurEkonomisInMonth()));
        inpNilaiAsetSAP.setText(formatrupiah(Double.parseDouble(String.valueOf(aset.getNilaiOleh()))));
        inpPersenKondisi.setText(String.valueOf(aset.getPersenKondisi()));
//        inpJumlahPohon.setText(String.valueOf(aset.getJumlahPohon()));
        inpHGU.setText(String.valueOf(aset.getHgu()));
        String ket_reject = aset.getKetReject();

        spinnerTipeAset.setSelection(Integer.parseInt(aset.getAsetTipe()));
        spinnerJenisAset.setSelection(Integer.parseInt(aset.getAsetJenis()));
        setAdapterAsetKodeEdit();
        spinnerAsetKondisi.setSelection(Integer.parseInt(aset.getAsetKondisi()));
        spinnerSubUnit.setSelection(Integer.parseInt(aset.getAsetSubUnit()));

        try {

            if (mapKodeSpinner.size() != 0) {

                Integer idspinner = getSpinnerKodeAset(Integer.parseInt(aset.getAsetJenis()), Integer.parseInt(aset.getAsetKode()));
                spinnerKodeAset.setSelection(idspinner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        String url1 = "", url2 = "", url3 = "", url4 = "", url5 = "";
        url1 = "file://" + aset.getFotoAset1();
        url2 = "file://" + aset.getFotoAset2();
        url3 = "file://" + aset.getFotoAset3();
        url4 = "file://" + aset.getFotoAset4();
        url5 = "file://" + aset.getFotoAset5();

        try {


            if (aset.getFotoAset1() == null) {
                map1.setEnabled(false);
//                foto1rl.setEnabled(false);
            } else {
                URL url = new URL(url1);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                fotoimg1.getLayoutParams().width = 200;
                fotoimg1.getLayoutParams().height = 200;
                fotoimg1.setImageBitmap(bmp);
                map1.setEnabled(true);
            }

            if (aset.getFotoAset2() == null) {
                map2.setEnabled(false);
//                foto2rl.setEnabled(false);
            } else {
                URL url = new URL(url2);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                fotoimg2.getLayoutParams().width = 200;
                fotoimg2.getLayoutParams().height = 200;
                fotoimg2.setImageBitmap(bmp);
                map2.setEnabled(true);
            }

            if (aset.getFotoAset3() == null) {
                map3.setEnabled(false);
//                foto3rl.setEnabled(false);
            } else {
                URL url = new URL(url3);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                fotoimg3.getLayoutParams().width = 200;
                fotoimg3.getLayoutParams().height = 200;
                fotoimg3.setImageBitmap(bmp);
                map3.setEnabled(true);
            }

            if (aset.getFotoAset4() == null) {
                map4.setEnabled(false);
//                foto4rl.setEnabled(false);
            } else {
                URL url = new URL(url4);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                fotoimg4.getLayoutParams().width = 200;
                fotoimg4.getLayoutParams().height = 200;
                fotoimg4.setImageBitmap(bmp);
                map4.setEnabled(true);
            }

            if (aset.getFotoAset5() == null) {
                map5.setEnabled(false);
//                foto5rl.setEnabled(false);
            } else {
                URL url = new URL(url5);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                fotoimg5.getLayoutParams().width = 200;
                fotoimg5.getLayoutParams().height = 200;
                fotoimg5.setImageBitmap(bmp);
                map5.setEnabled(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        geotag1 = aset.getGeoTag1();
        geotag2 = aset.getGeoTag2();
        geotag3 = aset.getGeoTag3();
        geotag4 = aset.getGeoTag4();
        geotag5 = aset.getGeoTag5();


//                set selection spinners


        editVisibilityDynamic();


    }

    public String formatrupiah(Double number) {
        Locale localeID = new Locale("IND", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String formatRupiah = numberFormat.format(number);
        String[] split = formatRupiah.split(",");
        int length = split[0].length();
        return split[0].substring(0, 2) + ". " + split[0].substring(2, length);
    }

    private void captureFotoQcLoses(String imageName, ActivityResultLauncher<Intent> activityLauncherName) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File fileImage = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "/" + imageName);
        if (fileImage.exists()) {
            fileImage.delete();
            Log.d("captImg", "captureImage: " + fileImage.exists());
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
                android.app.AlertDialog.Builder windowAlert = new android.app.AlertDialog.Builder(AsetAddUpdateOfflineActivity.this);
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


    private void setExifLocation(File fileImage, int list) {
        try {
            getLastLocation(AsetAddUpdateOfflineActivity.this, getApplicationContext());
            ExifInterface exif = new ExifInterface(fileImage.getAbsoluteFile().getAbsolutePath());
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, GpsConverter.convert(latitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, GpsConverter.latitudeRef(latitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, GpsConverter.convert(longitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, GpsConverter.latitudeRef(longitudeValue));
            exif.saveAttributes();
            String url = "https://www.google.com/maps/search/?api=1&query=" + String.valueOf(latitudeValue) + "%2C" + String.valueOf(longitudeValue);
            if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))) {
                if (list == 1) {

                    geotag1 = url;
                    Log.d("aseturl", "urlku" + geotag1);
                } else if (list == 2) {

                    geotag2 = url;
                } else if (list == 3) {

                    geotag3 = url;
                } else if (list == 4) {

                    geotag4 = url;
                } else if (list == 5) {

                    geotag5 = url;
                }

            } else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))) {
                if (list == 1) {

                    geotag1 = url;
                } else if (list == 2) {

                    geotag2 = url;
                } else if (list == 3) {

                    geotag3 = url;
                } else if (list == 4) {

                    geotag4 = url;
                }
            } else {
                geotag1 = url;
            }



        } catch (Exception e) {
            Toast.makeText(this, "Error when set Exif Location", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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

    public void editVisibilityDynamic() {
        TextView tvBa = findViewById(R.id.tvBa);
        TextView tvBast = findViewById(R.id.tvBast);
        TextView tvFoto = findViewById(R.id.tvFoto);
        TextView tvAfdeling = findViewById(R.id.tvAfdeling);
        Spinner inpAfdeling = findViewById(R.id.inpAfdeling);
        TextView tvLuasTanaman = findViewById(R.id.luasTanaman);
        TextView tvLuasNonTanaman = findViewById(R.id.luasNonTanaman);
        TextView tvPersenKondisi = findViewById(R.id.tvPersenKondisi);
        TextView tvUploadBAST = findViewById(R.id.tvUploadBAST);
        TextView tvFileBAST = findViewById(R.id.tvUploadFileBAST);
        TextView tvTahunTanam = findViewById(R.id.tvTahunTanam);
        TextView tvSistemTanam = findViewById(R.id.tvSistemTanam);

        HorizontalScrollView scrollPartition = findViewById(R.id.scrollPartition);


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


            if("Mono".equals(String.valueOf(spinnerSistemTanam.getSelectedItem()))){
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
            }

            if("ZC06/S001/Tebu".equals(String.valueOf(spinnerKodeAset.getSelectedItem()))){
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

            }
        }
        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))) {

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

            if("Mono".equals(String.valueOf(spinnerSistemTanam.getSelectedItem()))){
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
        }




        if ("baru".equals(String.valueOf(spinnerTipeAset.getSelectedItem()))) {
            //input PDF ba bast
            btnFileBAST.setVisibility(View.VISIBLE);
            tvFileBAST.setVisibility(View.VISIBLE);
            tvUploadBAST.setVisibility(View.VISIBLE);
        } else {
            //input PDF ba bast
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
        }

        if (spinnerSubUnit.getSelectedItemId() == 2) {
            inpAfdeling.setVisibility(View.VISIBLE);
            tvAfdeling.setVisibility(View.VISIBLE);
        } else {
            inpAfdeling.setVisibility(View.GONE);
            tvAfdeling.setVisibility(View.GONE);
        }

        if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            listBtnMap.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);


            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);

            foto5rl.setVisibility(View.VISIBLE);



        } else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            tvFoto.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            inpNomorBAST.setVisibility(View.VISIBLE);
            inpBtnMap.setVisibility(View.GONE);
            inpPersenKondisi.setVisibility(View.GONE);

            spinnerLuasSatuan.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);

            listBtnMap.setVisibility(View.GONE);

            btnFile.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);

            scrollPartition.setVisibility(View.VISIBLE);

            foto5rl.setVisibility(View.VISIBLE);

        } else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvUploudBA.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            tvFoto.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            inpNomorBAST.setVisibility(View.VISIBLE);
            inpBtnMap.setVisibility(View.GONE);
            inpPersenKondisi.setVisibility(View.GONE);

            btnFile.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);

            spinnerLuasSatuan.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);

            listBtnMap.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);
            foto5rl.setVisibility(View.VISIBLE);

        } else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBast.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            tvFoto.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            inpBtnMap.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            inpPersenKondisi.setVisibility(View.GONE);

            btnFile.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.VISIBLE);
            foto5rl.setVisibility(View.GONE);

            spinnerLuasSatuan.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);



        } else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.VISIBLE);
            tvFoto.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);


            btnFile.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);

            listBtnMap.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.VISIBLE);
            foto5rl.setVisibility(View.GONE);

            spinnerLuasSatuan.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);


            inpNomorBAST.setVisibility(View.VISIBLE);
            inpBtnMap.setVisibility(View.GONE);
            inpPersenKondisi.setVisibility(View.GONE);





        } else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            tvFoto.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            spinnerLuasSatuan.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);

            inpNomorBAST.setVisibility(View.VISIBLE);
            inpBtnMap.setVisibility(View.GONE);
            inpPersenKondisi.setVisibility(View.GONE);

            btnFile.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);

            scrollPartition.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);
            foto5rl.setVisibility(View.GONE);




        } else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBast.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.GONE);
            tvFoto.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.GONE);
            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);

            inpLuasAset.setVisibility(View.VISIBLE);
            inpPersenKondisi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            inpBtnMap.setVisibility(View.GONE);

            listBtnMap.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.VISIBLE);

            btnFile.setVisibility(View.GONE);
            foto5rl.setVisibility(View.GONE);



        } else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.VISIBLE);
            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);

            listBtnMap.setVisibility(View.GONE);
            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);

            foto5rl.setVisibility(View.GONE);


        } else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            tvFoto.setVisibility(View.GONE);
            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);

            spinnerLuasSatuan.setVisibility(View.VISIBLE);
            listBtnMap.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);
            foto5rl.setVisibility(View.GONE);

            inpBtnMap.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);
            inpPersenKondisi.setVisibility(View.VISIBLE);

            btnFile.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);




        } else {
            tvFoto.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);
            tvPopTotalPohonSaatIni.setVisibility(View.GONE);
            tvPopTotalStdMaster.setVisibility(View.GONE);
            tvPresentasePopPerHA.setVisibility(View.GONE);
            tvPopPerHA.setVisibility(View.GONE);


            listBtnMap.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);

            inpBtnMap.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.GONE);
            inpPersenKondisi.setVisibility(View.GONE);
            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
            inpPopTotalStdMaster.setVisibility(View.GONE);
            inpPopPerHA.setVisibility(View.GONE);
            inpPresentasePopPerHA.setVisibility(View.GONE);

            btnFile.setVisibility(View.GONE);
            btnFileBAST.setVisibility(View.GONE);

            foto5rl.setVisibility(View.GONE);
        }

        if (!isEdit){
            inpBtnMap.setVisibility(View.GONE);
        } else {
            inpBtnMap.setVisibility(View.GONE);
        }
    }


    public void getAllSpinnerData() {

        asetHelper.open();
        sharedPreferences = AsetAddUpdateOfflineActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        Cursor asetTipe = asetHelper.getAllAsetTipe();
        Cursor asetJenis = asetHelper.getAllAsetJenis();
        Cursor asetKondisi = asetHelper.getAllAsetKondisi();
        Cursor asetKodeCursor = asetHelper.getAllAsetKode();
        Cursor unit = asetHelper.getAllUnit();
        Cursor subUnit = asetHelper.getAllSubUnit();
        Cursor afdeling = asetHelper.getAllAfdeling();
        Cursor sap = asetHelper.getAllSap();
        Cursor alatAngkut = asetHelper.getAllAlatAngkut();
        Cursor sistemTanam = asetHelper.getAllSistemTanam();
        DataAllSpinner dataAllSpinner = MappingHelper.mapCursorToArrayListSpinner(asetTipe, asetJenis, asetKondisi, asetKodeCursor, unit, subUnit, afdeling, sap, alatAngkut,sistemTanam);

        List<String> listSpinnerTipe = new ArrayList<>();

        List<String> listSpinnerJenis = new ArrayList<>();

        List<String> listSpinnerKondisiAset = new ArrayList<>();


        List<String> listSpinnerUnit = new ArrayList<>();

        List<String> listSpinnerSubUnit = new ArrayList<>();

        List<String> listSpinnerAfdeling = new ArrayList<>();

        List<String> listSpinnerSistemTanam = new ArrayList<>();

        listSpinnerAlatAngkut = new ArrayList<>();


        listSpinnerTipe.add("Pilih Tipe Aset");
        listSpinnerJenis.add("Pilih Jenis Aset");
        listSpinnerKondisiAset.add("Pilih Kondisi Aset");
        listSpinnerSubUnit.add("Pilih Sub Unit ");
        listSpinnerAfdeling.add("Pilih Afdeling ");
        listSpinnerSistemTanam.add("Pilih Sistem Tanam ");
        listSpinnerAlatAngkut.add("Pilih Alat Angkut ");

        // get data tipe aset

        for (AsetTipe at : dataAllSpinner.getAsetTipe()) {

            listSpinnerTipe.add(at.getAset_tipe_desc());

        }

        // get data sistem tanam

        listSistemTanam = dataAllSpinner.getSistemTanam();


        for (SistemTanam at : dataAllSpinner.getSistemTanam()) {
            listSpinnerSistemTanam.add(at.getSt_desc());
        }

        // get data jenis

        for (AsetJenis at : dataAllSpinner.getAsetJenis()) {

            listSpinnerJenis.add(at.getAset_jenis_desc());

        }


        // get kondisi aset

        for (AsetKondisi at : dataAllSpinner.getAsetKondisi()) {

            listSpinnerKondisiAset.add(at.getAset_kondisi_desc());

        }

        // get alat angkut

        for (AlatAngkut at : dataAllSpinner.getAlatAngkut()) {

            listSpinnerAlatAngkut.add(at.getAp_desc());

        }


        // get kode aset

        asetKode2 = dataAllSpinner.getAsetKode();

        if (!isEdit) {
            setAdapterAsetKode();

        } else {
            setAdapterAsetKodeEdit();
        }

        // get unit

        for (Unit at : dataAllSpinner.getUnit()) {
            listSpinnerUnit.add(at.getUnit_desc());

        }

        // get sub unit
        for (SubUnit at : dataAllSpinner.getSubUnit()) {
            listSpinnerSubUnit.add(at.getSub_unit_desc());
        }

        // get sap

        Integer unit_id = Integer.parseInt(sharedPreferences.getString("unit_id","0"));
        for (Sap at : dataAllSpinner.getSap()) {
            if (at.getUnit_id().equals(unit_id)) {
                mapSap.put(Long.parseLong(at.getSap_desc()), at.getSap_id());
                listSpinnerSap.add(at.getSap_desc());
                sapAll.add(at);
            }
        }

        // set adapter unit
        ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerUnit);
        adapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(adapterUnit);

        try {

            unit_id = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
            spinnerUnit.setSelection(unit_id - 1);
        } catch (Exception e) {
        }

//                // get afdeling
        Integer i = 1;
        afdeling2 = dataAllSpinner.getAfdeling();
        for (Afdelling at : dataAllSpinner.getAfdeling()) {
                mapSpinnerAfdeling.put(i, at.getAfdelling_id());
                mapAfdelingSpinner.put(at.getAfdelling_id(), i);
                Log.d("amanat98", String.valueOf(at.getAfdelling_desc()));
                mapAfdeling.put(i, at.getAfdelling_desc());
                listSpinnerAfdeling.add(at.getAfdelling_desc());
                i++;
        }


        // set adapter tipe
        ArrayAdapter<String> adapterTipe = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerTipe);
        adapterTipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipeAset.setAdapter(adapterTipe);

        // set adapter sistem tanam
        ArrayAdapter<String> adapterSistemTanam = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerSistemTanam);
        adapterSistemTanam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSistemTanam.setAdapter(adapterSistemTanam);

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


        // set adapter sap aset

        try {

            if (listView != null) {

                ArrayAdapter<String> adapterSap = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinnerSap);
                adapterSap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listView.setAdapter(adapterSap);
            }
        } catch (Exception e) {
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


        // alat angkut
        ArrayAdapter<String> adapterAlatAngkut = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerAlatAngkut);
        adapterAlatAngkut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlatAngkut.setAdapter(adapterAlatAngkut);

        // set adapter afedeling

        ArrayAdapter<String> adapterAfdeling = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinnerAfdeling);
        adapterAfdeling.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAfdeling.setAdapter(adapterAfdeling);

        Integer sub_unit_id = Integer.valueOf(sharedPreferences.getString("sub_unit_id", "0"));
        try {
            spinnerSubUnit.setSelection(sub_unit_id);
        } catch (Exception e) {
        }

        if (sub_unit_id == 2) {
            spinnerAfdeling.setSelection(mapAfdelingSpinner.get(afdeling_id));
        }

        asetHelper.close();

    }

    public Boolean spinnerValidation() {


        if (spinnerTipeAset.getSelectedItemId() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Tipet Aset Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
            dialog.dismiss();
            customDialogAddAset.dismiss();
            return false;
        }


        if (spinnerJenisAset.getSelectedItemId() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Jenis Aset Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
            dialog.dismiss();
            customDialogAddAset.dismiss();
            return false;
        }
        if (spinnerAsetKondisi.getSelectedItemId() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Kondisi Aset Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
            dialog.dismiss();
            customDialogAddAset.dismiss();
            return false;
        }
        if (spinnerKodeAset.getSelectedItemId() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Kode Aset Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            dialog.dismiss();
            customDialogAddAset.dismiss();
            return false;
        }
        if (spinnerSubUnit.getSelectedItemId() == 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title dialog
            alertDialogBuilder.setTitle("Error!");

            // set pesan dari dialog
            alertDialogBuilder
                    .setMessage("Sub Unit Harus Dipilih")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // jika tombol diklik, maka akan menutup activity ini
                            dialog.cancel();
                        }
                    });


            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();

            // menampilkan alert dialog
            alertDialog.show();
            dialog.dismiss();
            customDialogAddAset.dismiss();
            return false;
        }
        if (spinnerJenisAset.getSelectedItemId() == 1 || spinnerJenisAset.getSelectedItemId() == 3) {
            if (spinnerSistemTanam.getSelectedItemId() == 0 && !spinnerKodeAset.getSelectedItem().equals("ZC06/S001/Tebu")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                // set title dialog
                alertDialogBuilder.setTitle("Error!");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Sistem Tanam Harus Dipilih")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // jika tombol diklik, maka akan menutup activity ini
                                dialog.cancel();
                            }
                        });


                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                dialog.dismiss();
                customDialogAddAset.dismiss();
                return false;
            }
        }else {

            if (spinnerKodeAset.getSelectedItem().equals("ZA08/Alat Pengangkutan")) {
                if (spinnerAlatAngkut.getSelectedItemId() == 0) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            this);

                    // set title dialog
                    alertDialogBuilder.setTitle("Error!");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Alat Angkut Harus Dipilih")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // jika tombol diklik, maka akan menutup activity ini
                                    dialog.cancel();
                                }
                            });


                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    dialog.dismiss();
                    customDialogAddAset.dismiss();
                    return false;
                }
            }

        }



        return true;
    }

    public void addAset() {
        dialog.show();
        Boolean spinnerValidaton = spinnerValidation();

        if (!spinnerValidaton) {
            return;
        }



        if (inpNamaAset.getText().toString().equals("")) {
            dialog.dismiss();
            customDialogAddAset.dismiss();
            inpNamaAset.setError("nama harus diisi");
            inpNamaAset.requestFocus();

            return;
        }

        if (inpNoSAP.getText().toString().equals("Pilih Nomor SAP")) {
            dialog.dismiss();
            customDialogAddAset.dismiss();
            Toast.makeText(getApplicationContext(), "Nomor SAP Wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (spinnerJenisAset.getSelectedItemId() == 2) {
            if ("normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))){
                if (img1 == null || img2 == null || img3 == null || img4 == null){
                    customDialogAddAset.dismiss();
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Foto Wajib Diisi Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if ( "rusak".equals (String.valueOf(spinnerAsetKondisi.getSelectedItem()))){
                if (img1 == null || img2 == null || img3 == null || img4 == null){
                    dialog.dismiss();
                    customDialogAddAset.dismiss();
                    Toast.makeText(getApplicationContext(), "Foto Wajib Diisi Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if(inpPersenKondisi.getText().toString().equals("")){
                customDialogAddAset.dismiss();
                dialog.dismiss();
                inpPersenKondisi.setError("Persen kondisi wajib diisi");
                inpPersenKondisi.requestFocus();
                return;
            }

            if(inpLuasAset.getText().toString().equals("")){
                customDialogAddAset.dismiss();
                dialog.dismiss();
                inpLuasAset.setError("Luas Aset wajib diisi");
                inpLuasAset.requestFocus();
                return;
            }

            if (Integer.parseInt(inpPersenKondisi.getText().toString()) > 100 || Integer.parseInt(inpPersenKondisi.getText().toString()) < 0) {
                customDialogAddAset.dismiss();
                dialog.dismiss();
                inpPersenKondisi.setError("Isian Persen Kondisi Wajib Minimal 0 Maksimal 100");
                inpPersenKondisi.requestFocus();
                return;
            }
        } else {
            if(inpPopTotalPohonSaatIni.getText().toString().equals("")){
                dialog.dismiss();
                customDialogAddAset.dismiss();
                inpPopTotalPohonSaatIni.setError("Populasi harus diisi");
                inpPopTotalPohonSaatIni.requestFocus();
                return;
            }

            if(inpPopTotalStdMaster.getText().toString().equals("")){
                dialog.dismiss();
                customDialogAddAset.dismiss();
                inpPopTotalStdMaster.setError("Populasi harus diisi");
                inpPopTotalStdMaster.requestFocus();
                return;
            }

            if (spinnerSistemTanam.getSelectedItemId() == 1 || spinnerKodeAset.getSelectedItem().equals("ZC06/S001/Tebu")) {
                if(inpLuasAset.getText().toString().equals("")){
                    customDialogAddAset.dismiss();
                    dialog.dismiss();
                    inpLuasAset.setError("Luas Aset wajib diisi");
                    inpLuasAset.requestFocus();
                    return;
                }
            }

            if (inpTahunTanam.getText().toString().equals("")) {
                dialog.dismiss();
                customDialogAddAset.dismiss();
                inpTahunTanam.setError("Tahun Tanam harus diisi");
                inpTahunTanam.requestFocus();
                return;
            }

            if (spinnerSistemTanam.getSelectedItemId() == 1 || spinnerKodeAset.getSelectedItem().equals("ZC06/S001/Tebu")) {
                if(inpLuasAset.getText().toString().equals("")){
                    customDialogAddAset.dismiss();
                    dialog.dismiss();
                    inpLuasAset.setError("Luas Aset wajib diisi");
                    inpLuasAset.requestFocus();
                    return;
                }
            }
        }

        try {
            ContentValues values = new ContentValues();
            values.put("aset_tipe", String.valueOf(spinnerTipeAset.getSelectedItemId()));
            values.put("aset_jenis", String.valueOf(spinnerJenisAset.getSelectedItemId()));
            values.put("aset_kondisi", String.valueOf(spinnerAsetKondisi.getSelectedItemId()));

            Integer idkodeaset = getAsetKodeId(Math.toIntExact(spinnerJenisAset.getSelectedItemId()), Math.toIntExact(spinnerKodeAset.getSelectedItemId()));
            values.put("aset_kode", String.valueOf(idkodeaset));

            values.put("unit_id", String.valueOf(spinnerUnit.getSelectedItemId() + 1));
            values.put("aset_sub_unit", String.valueOf(spinnerSubUnit.getSelectedItemId()));
            values.put("afdeling_id", String.valueOf(afdeling_id));
            values.put("aset_name", inpNamaAset.getText().toString().trim());


            // Get the internal files directory
            String namaAsetWithoutSpace = inpNamaAset.getText().toString().trim();
            namaAsetWithoutSpace = namaAsetWithoutSpace.replace(" ", "");

            // Create a new file in the internal files directory

            File newImg1 = new File(getFilesDir(), namaAsetWithoutSpace + "1.png");
            File newImg2 = new File(getFilesDir(), namaAsetWithoutSpace + "2.png");
            File newImg3 = new File(getFilesDir(), namaAsetWithoutSpace + "3.png");
            File newImg4 = new File(getFilesDir(), namaAsetWithoutSpace + "4.png");
            File newImg5 = new File(getFilesDir(), namaAsetWithoutSpace + "5.png");
            File ba = new File(getFilesDir(), namaAsetWithoutSpace + "-ba.pdf");
            File BAST = new File(getFilesDir(), namaAsetWithoutSpace + "-bast.pdf");

            if (img1 != null) {
                FileInputStream in = new FileInputStream(img1);
                FileOutputStream out = new FileOutputStream(newImg1);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img1.delete();

                values.put("foto_aset1", newImg1.getAbsolutePath());
                values.put("geo_tag1", geotag1);


            }

            if (img2 != null) {
                FileInputStream in = new FileInputStream(img2);
                FileOutputStream out = new FileOutputStream(newImg2);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img2.delete();
                values.put("foto_aset2", newImg2.getAbsolutePath());
                values.put("geo_tag2", geotag2);


            }

            if (img3 != null) {
                FileInputStream in = new FileInputStream(img3);
                FileOutputStream out = new FileOutputStream(newImg3);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img3.delete();
                values.put("foto_aset3", newImg3.getAbsolutePath());
                values.put("geo_tag3", geotag3);


            }

            if (img4 != null) {
                FileInputStream in = new FileInputStream(img4);
                FileOutputStream out = new FileOutputStream(newImg4);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img4.delete();
                values.put("foto_aset4", newImg4.getAbsolutePath());
                values.put("geo_tag4", geotag4);

            }

            if (img5 != null) {
                FileInputStream in = new FileInputStream(img5);
                FileOutputStream out = new FileOutputStream(newImg5);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img5.delete();
                values.put("foto_aset5", newImg5.getAbsolutePath());
                values.put("geo_tag5", geotag5);

            }

            if (bafile_file != null) {

                FileInputStream in = new FileInputStream(bafile_file);
                FileOutputStream out = new FileOutputStream(ba);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                bafile_file.delete();
                values.put("berita_acara", ba.getAbsolutePath());

            }

            if (file_bast != null) {

                FileInputStream in = new FileInputStream(file_bast);
                FileOutputStream out = new FileOutputStream(BAST);

                // Copy the file
                byte[] buffer = new byte[9999];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                file_bast.delete();
                values.put("file_bast", BAST.getAbsolutePath());

            }

            String nomor_aset_sap = inpNoSAP.getText().toString().trim();
            values.put("nomor_sap", nomor_aset_sap);

            values.put("aset_luas", inpLuasAset.getText().toString().trim());
            values.put("persen_kondisi", inpPersenKondisi.getText().toString().trim());
            values.put("hgu", inpHGU.getText().toString().trim());
            values.put("nilai_oleh", utils.CurrencyToNumber(inpNilaiAsetSAP.getText().toString().trim()));
            values.put("tgl_oleh", inpTglOleh.getText().toString().trim());



            if (spinnerAlatAngkut.getSelectedItem() != null) {
                values.put("alat_pengangkutan", spinnerAlatAngkut.getSelectedItem().toString().trim());
            }

            if (spinnerLuasSatuan.getSelectedItem() != null) {
                values.put("satuan_luas", spinnerLuasSatuan.getSelectedItem().toString().trim());
            }

            if (spinnerJenisAset.getSelectedItem().equals("tanaman") || spinnerJenisAset.getSelectedItem().equals("kayu") ) {

                values.put("tahun_tanam", inpTahunTanam.getText().toString().trim());


                if (!"ZC06/S001/Tebu".equals(spinnerKodeAset.getSelectedItem())){
                    values.put("sistem_tanam", String.valueOf(spinnerSistemTanam.getSelectedItem()));

                    Double popPerHa =  Double.parseDouble((inpPopTotalPohonSaatIni.getText().toString() != null || !inpPopTotalPohonSaatIni.getText().toString().equals("") ) ? String.valueOf(inpPopTotalPohonSaatIni.getText().toString()) : "0" ) / Double.parseDouble((inpLuasAset.getText().toString().trim().equals("")) ? "0" : inpLuasAset.getText().toString().trim());
                    Double presentase = popPerHa / Double.parseDouble((inpPopTotalStdMaster.getText().toString() != null || inpPopTotalStdMaster.getText().toString().equals("") ) ? String.valueOf(inpPopTotalStdMaster.getText().toString()) : "0"  ) * 100;

                    if (Double.isNaN(presentase)) {
                        presentase = 0.0;
                    }

                    if (Double.isInfinite(presentase)){
                        presentase = 0.0;
                    }

                    if (Double.isNaN(popPerHa)) {
                        popPerHa = 0.0;
                    }

                    if (Double.isInfinite(popPerHa)) {
                        popPerHa = 0.0;

                    }

                    values.put("pop_pohon_saat_ini", inpPopTotalPohonSaatIni.getText().toString().trim());
                    values.put("pop_standar", inpPopTotalStdMaster.getText().toString().trim());
                    values.put("pop_per_ha", popPerHa);
                    values.put("presentase_pop_per_ha", presentase);

                } else {
                    values.put("sistem_tanam", "Mono");
                    values.put("pop_pohon_saat_ini", inpPopTotalPohonSaatIni.getText().toString().trim());
                    values.put("pop_standar", inpPopTotalStdMaster.getText().toString().trim());
                }

            }

            LocalDateTime currentTime = null;
            DateTimeFormatter formatter = null;
            String formattedDateTime = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentTime = LocalDateTime.now();
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                formattedDateTime = currentTime.format(formatter);
            }


            values.put("tgl_input", String.valueOf(formattedDateTime));
            values.put("masa_susut", inpMasaPenyusutan.getText().toString().trim());
            values.put("nilai_residu", utils.CurrencyToNumber(inpNilaiResidu.getText().toString().trim()));
            values.put("nomor_bast", inpNomorBAST.getText().toString().trim());
            values.put("keterangan", inpKeterangan.getText().toString().trim());

            asetHelper.open();
            asetHelper.insert(values);
            asetHelper.close();
            customDialogAddAset.dismiss();
            dialog.dismiss();
//            finish();
            Intent intent = new Intent(AsetAddUpdateOfflineActivity.this, LonglistAsetActivity.class);
            intent.putExtra("offline", true);
            startActivity(intent);
//            saveImageInternal(img1,inpNamaAset.getText().toString().trim(),1);
        } catch (Exception e) {
            customDialogAddAset.dismiss();
            dialog.dismiss();
            e.printStackTrace();
        }


    }


    public void editAset() {
        dialog.show();



        try {
            ContentValues values = new ContentValues();
            values.put("aset_tipe", String.valueOf(spinnerTipeAset.getSelectedItemId()));
            values.put("aset_jenis", String.valueOf(spinnerJenisAset.getSelectedItemId()));
            values.put("aset_kondisi", String.valueOf(spinnerAsetKondisi.getSelectedItemId()));

            Integer idkode = getAsetKodeId(Math.toIntExact(spinnerJenisAset.getSelectedItemId()), Math.toIntExact(spinnerKodeAset.getSelectedItemId()));
            values.put("aset_kode", String.valueOf(idkode + 1));

            values.put("unit_id", String.valueOf(spinnerUnit.getSelectedItemId() + 1));
            values.put("aset_sub_unit", String.valueOf(spinnerSubUnit.getSelectedItemId()));
            values.put("afdeling_id", String.valueOf(afdeling_id));

            values.put("aset_name", inpNamaAset.getText().toString().trim());


            // Get the internal files directory

            String namaAsetWithoutSpace = inpNamaAset.getText().toString().trim();
            namaAsetWithoutSpace = namaAsetWithoutSpace.replace(" ", "");

            // Create a new file in the internal files directory

            File newImg1 = new File(getFilesDir(), namaAsetWithoutSpace + "1.png");
            File newImg2 = new File(getFilesDir(), namaAsetWithoutSpace + "2.png");
            File newImg3 = new File(getFilesDir(), namaAsetWithoutSpace + "3.png");
            File newImg4 = new File(getFilesDir(), namaAsetWithoutSpace + "4.png");
            File newImg5 = new File(getFilesDir(), namaAsetWithoutSpace + "5.png");
            File ba = new File(getFilesDir(), namaAsetWithoutSpace + "-ba.pdf");
            File BAST = new File(getFilesDir(), namaAsetWithoutSpace + "-bast.pdf");

            if (img1 != null) {
                FileInputStream in = new FileInputStream(img1);
                FileOutputStream out = new FileOutputStream(newImg1);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img1.delete();

                values.put("foto_aset1", newImg1.getAbsolutePath());
                values.put("geo_tag1", geotag1);


            }

            if (img2 != null) {
                FileInputStream in = new FileInputStream(img2);
                FileOutputStream out = new FileOutputStream(newImg2);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img2.delete();
                values.put("foto_aset2", newImg2.getAbsolutePath());
                values.put("geo_tag2", geotag2);


            }

            if (img3 != null) {
                FileInputStream in = new FileInputStream(img3);
                FileOutputStream out = new FileOutputStream(newImg3);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img3.delete();
                values.put("foto_aset3", newImg3.getAbsolutePath());
                values.put("geo_tag3", geotag3);


            }

            if (img4 != null) {
                FileInputStream in = new FileInputStream(img4);
                FileOutputStream out = new FileOutputStream(newImg4);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img4.delete();
                values.put("foto_aset4", newImg4.getAbsolutePath());
                values.put("geo_tag4", geotag4);

            }

            if (img5 != null) {
                FileInputStream in = new FileInputStream(img5);
                FileOutputStream out = new FileOutputStream(newImg5);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                img5.delete();
                values.put("foto_aset5", newImg5.getAbsolutePath());
                values.put("geo_tag5", geotag5);

            }

            if (bafile_file != null) {

                FileInputStream in = new FileInputStream(bafile_file);
                FileOutputStream out = new FileOutputStream(ba);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                bafile_file.delete();
                values.put("berita_acara", ba.getAbsolutePath());

            }

            if (file_bast != null) {

                FileInputStream in = new FileInputStream(file_bast);
                FileOutputStream out = new FileOutputStream(BAST);

                // Copy the file
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                // Close the streams
                in.close();
                out.flush();
                out.close();

                // Delete the original file
                file_bast.delete();
                values.put("file_bast", BAST.getAbsolutePath());

            }

            String nomor_aset_sap = inpNoSAP.getText().toString().trim();
            values.put("nomor_sap", nomor_aset_sap);
            values.put("aset_luas", inpLuasAset.getText().toString().trim());
            values.put("persen_kondisi", inpPersenKondisi.getText().toString().trim());
            values.put("hgu", inpHGU.getText().toString().trim());
            values.put("nilai_oleh", utils.CurrencyToNumber(inpNilaiAsetSAP.getText().toString().trim()));
            values.put("tgl_oleh", inpTglOleh.getText().toString().trim());
            LocalDateTime currentTime = null;
            DateTimeFormatter formatter = null;
            String formattedDateTime = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentTime = LocalDateTime.now();
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                formattedDateTime = currentTime.format(formatter);
            }

            values.put("tgl_input", String.valueOf(formattedDateTime));
            values.put("masa_susut", inpMasaPenyusutan.getText().toString().trim());
            values.put("nilai_residu", utils.CurrencyToNumber(inpNilaiResidu.getText().toString().trim()));
            values.put("nomor_bast", inpNomorBAST.getText().toString().trim());
            values.put("keterangan", inpKeterangan.getText().toString().trim());

            if (spinnerJenisAset.getSelectedItem().equals("tanaman")) {
                Double popPerHa =  Double.parseDouble((inpPopTotalPohonSaatIni.getText().toString() != null || !inpPopTotalPohonSaatIni.getText().toString().equals("") ) ? String.valueOf(inpPopTotalPohonSaatIni.getText().toString()) : "0" ) / Double.parseDouble((inpLuasAset.getText().toString().trim().equals("")) ? "0" : inpLuasAset.getText().toString().trim());
                Double presentase = popPerHa / Double.parseDouble((inpPopTotalStdMaster.getText().toString() != null || inpPopTotalStdMaster.getText().toString().equals("") ) ? String.valueOf(inpPopTotalStdMaster.getText().toString()) : "0"  ) * 100;

                if (Double.isNaN(presentase)) {
                    presentase = 0.0;
                }

                if (Double.isInfinite(presentase)){
                    presentase = 0.0;
                }

                if (Double.isNaN(popPerHa)) {
                    popPerHa = 0.0;
                }

                if (Double.isInfinite(popPerHa)) {
                    popPerHa = 0.0;

                }
                values.put("pop_pohon_saat_ini", inpPopTotalPohonSaatIni.getText().toString().trim());
                values.put("pop_standar", inpPopTotalStdMaster.getText().toString().trim());
                values.put("pop_per_ha", popPerHa);
                values.put("presentase_pop_per_ha", presentase);
                values.put("tahun_tanam", inpTahunTanam.getText().toString().trim());
            }

            if (spinnerAlatAngkut.getSelectedItem() != null) {
                values.put("alat_pengangkutan", spinnerAlatAngkut.getSelectedItem().toString().trim());

            }

            if (spinnerLuasSatuan.getSelectedItem() != null) {
                values.put("satuan_luas", spinnerLuasSatuan.getSelectedItem().toString().trim());
            }



            asetHelper.open();
            asetHelper.update(String.valueOf(id), values);
            asetHelper.close();

            dialog.dismiss();
            customDialogUpdateAset.dismiss();
//            finish();

//            Intent intent = new Intent(this, LonglistAsetActivity.class);
//            startActivity(intent);

            Intent intent = new Intent(AsetAddUpdateOfflineActivity.this, LonglistAsetActivity.class);
            intent.putExtra("offline", true);
            startActivity(intent);

        } catch (Exception e) {
            dialog.dismiss();
            customDialogUpdateAset.dismiss();
            Toast.makeText(getApplicationContext(), "error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    private Integer getAsetKodeId(Integer asetJenis, Integer spinnerId) {
        Integer i = 0;
        for (AsetKode2 a : asetKode2) {
            if (a.getAsetJenis() == asetJenis) {
                if (i == spinnerId) {
                    return a.getAsetKodeId() - 1;
                }

                i++;
            }
        }

        return 0;
    }

    private Integer getSpinnerKodeAset(Integer asetJenis, Integer idkodeaset) {
        Integer i = 0;
        for (AsetKode2 a : asetKode2) {
            if (a.getAsetJenis() == asetJenis) {
                Log.d("aset-spinner", String.valueOf(idkodeaset));
                Log.d("aset-i", String.valueOf(a.getAsetKodeId()));
                if (a.getAsetKodeId() == idkodeaset) {
                    return i;
                }

                i++;
            }
        }

        return 0;
    }

    private void setValueSap(String sap) {

        for (Sap it : sapAll) {

            if (it.getSap_desc().equals(sap)){
                inpNilaiAsetSAP.setText(formatrupiah(Double.valueOf(it.getNilai_oleh())));
                inpNilaiResidu.setText(formatrupiah(Double.valueOf(it.getNilai_residu())));
                inpMasaPenyusutan.setText(String.valueOf(it.getMasa_susut()));
                inpTglOleh.setText(it.getTgl_oleh());
            }
        }
    }

    private Integer getSistemTanamByName(String sistem_tanam) {
        for(SistemTanam it : listSistemTanam) {
            if (sistem_tanam.equals(it.getSt_desc())){
                return it.getSt_id();
            }
        }
        return 0;
    }

}