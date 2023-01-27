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
import android.os.Handler;
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

import ptpn12.amanat.asem.adapter.AsetOfflineAdapter;
import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.Afdelling;
import ptpn12.amanat.asem.api.model.AlatAngkut;
import ptpn12.amanat.asem.api.model.AsetJenis;
import ptpn12.amanat.asem.api.model.AsetKode2;
import ptpn12.amanat.asem.api.model.AsetKondisi;
import ptpn12.amanat.asem.api.model.AsetModel;
import ptpn12.amanat.asem.api.model.AsetTipe;
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
import java.lang.ref.WeakReference;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsetAddUpdateOfflineActivity extends AppCompatActivity  implements LoadNotesCallback  {
    private boolean isEdit = false;
    private int position;
    Aset aset;
    private AsetHelper asetHelper;
    private AsetOfflineAdapter adapter;
    Integer afdeling_id = 0;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    Integer id =0;
    SharedPreferences sharedPreferences;
    DataAllSpinner allSpinner;
    Button inpBtnMap;
    Button btnFile;
    Button btnSubmit;
    Button map1;
    Button map2;
    Button map3;
    Button map4;
    Button btnYaKirim;
    Button btnTidakKirim;

    Uri docUri;
    List<String> listSpinnerSap=new ArrayList<>();
    List<AsetKode2> asetKode2 = new ArrayList<>();
    List<Afdelling> afdeling2 = new ArrayList<>();
    List<Unit> unit = new ArrayList<>();
    List<SubUnit> subUnit = new ArrayList<>();


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
    Spinner spinnerAlatAngkut;
    Spinner spinnerLuasSatuan;


    TextView inpNoSAP;
    TextView tvAlatAngkut;
    TextView tvPopTotalIni;
    TextView tvPopTotalStd;
    TextView tvPopHektarIni;
    TextView tvPopHektarStd;

    EditText inpNamaAset;
    EditText inpHGU;
    EditText inpLuasAset;
    EditText inpNilaiAsetSAP;
    EditText inpTglOleh;
    EditText inpTglInput;
    EditText inpMasaPenyusutan;
    EditText inpNomorBAST;
    EditText inpNilaiResidu;
    EditText inpKeterangan;
    EditText inpUmrEkonomis;
    EditText inpPersenKondisi;
    EditText inpAfdelingET;
    EditText inpPopTotalIni;
    EditText inpPopTotalStd;
    EditText inpPopHektarIni;
    EditText inpPopHektarStd;

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
    Map<Long, Integer> mapSap = new HashMap();

    Map<Integer,Integer> mapKodeSpinner = new HashMap();
    Map<Integer,Integer> mapSpinnerkode = new HashMap();
    Map<String,Integer> mapSpinnerkodeCoba = new HashMap();
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
    Dialog customDialogUpdateAset;


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
//                            Intent data = activityResult.getData();

                            if (resultCode== Activity.RESULT_OK){
                                img1 = utils.savePictureResult(
                                        AsetAddUpdateOfflineActivity.this, photoname1, fotoimg1, true
                                );
                                fotoimg1.getLayoutParams().width = 200;
                                fotoimg1.getLayoutParams().height = 200;
                                setExifLocation(img1,1);

                            } else if (resultCode == Activity.RESULT_CANCELED){
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
                            if (resultCode== Activity.RESULT_OK){
                                img2 = utils.savePictureResult(
                                        AsetAddUpdateOfflineActivity.this, photoname2, fotoimg2, true
                                );
                                fotoimg2.getLayoutParams().width = 200;
                                fotoimg2.getLayoutParams().height = 200;
                                setExifLocation(img2,2);
                            } else if (resultCode == Activity.RESULT_CANCELED){
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
                            if (resultCode== Activity.RESULT_OK){
                                img3 = utils.savePictureResult(
                                        AsetAddUpdateOfflineActivity.this, photoname3, fotoimg3, true
                                );
                                fotoimg3.getLayoutParams().width = 200;
                                fotoimg3.getLayoutParams().height = 200;
                                setExifLocation(img3,3);
                            } else if (resultCode == Activity.RESULT_CANCELED){
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
                            if (resultCode== Activity.RESULT_OK){
                                img4 = utils.savePictureResult(
                                        AsetAddUpdateOfflineActivity.this, photoname4, fotoimg4, true
                                );
                                fotoimg4.getLayoutParams().width = 200;
                                fotoimg4.getLayoutParams().height = 200;
                                setExifLocation(img4,4);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(AsetAddUpdateOfflineActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );
    private Dialog dialog;
    ListView listView;
    EditText  editTextSap;
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

        id = getIntent().getIntExtra("id",0);
        if (id != 0) {
            isEdit = true;
        }


        sharedPreferences = AsetAddUpdateOfflineActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        afdeling_id = Integer.parseInt(sharedPreferences.getString("afdeling_id", "0"));

        dialog = new Dialog(AsetAddUpdateOfflineActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        spinnerNoSap = new Dialog(AsetAddUpdateOfflineActivity.this);

        getLastLocation(AsetAddUpdateOfflineActivity.this,getApplicationContext());

        listBtnMap = findViewById(R.id.listMapButton);
        inpBtnMap = findViewById(R.id.inpBtnMap);
        inpTglOleh = findViewById(R.id.inpTglMasukAset);
        inpTglInput = findViewById(R.id.inpTglInput);
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
        spinnerLuasSatuan = findViewById(R.id.inpLuasSatuan);

        tvPopTotalIni = findViewById(R.id.tvPopTotalIni);
        tvPopTotalStd = findViewById(R.id.tvPopTotalStd);
        tvPopHektarIni = findViewById(R.id.tvPopHektarIni);
        tvPopHektarStd = findViewById(R.id.tvPopHektarStd);

        inpPopTotalIni = findViewById(R.id.inpPopTotalIni);
        inpPopTotalStd = findViewById(R.id.inpPopTotalStd);
        inpPopHektarIni = findViewById(R.id.inpPopHektarIni);
        inpPopHektarStd = findViewById(R.id.inpPopHektarStd);

        List<String> listSpinnerSatuan = new ArrayList<>();
        listSpinnerSatuan.add("Ha");
        listSpinnerSatuan.add("m2");
        listSpinnerSatuan.add("Item");
        ArrayAdapter<String> adapterLuasSatuan =new ArrayAdapter<>(AsetAddUpdateOfflineActivity.this, android.R.layout.simple_list_item_1,listSpinnerSatuan);
        spinnerLuasSatuan.setAdapter(adapterLuasSatuan);
        spinnerAlatAngkut = findViewById(R.id.inpAlatAngkut);
        tvAlatAngkut = findViewById(R.id.tvAlatAngkut);

        foto1rl = findViewById(R.id.foto1);
        foto2rl = findViewById(R.id.foto2);
        foto3rl = findViewById(R.id.foto3);
        foto4rl = findViewById(R.id.foto4);

        map1 = findViewById(R.id.map1);
        map2 = findViewById(R.id.map2);
        map3 = findViewById(R.id.map3);
        map4 = findViewById(R.id.map4);



        fotoimg1 = findViewById(R.id.fotoimg1);
        fotoimg2 = findViewById(R.id.fotoimg2);
        fotoimg3 = findViewById(R.id.fotoimg3);
        fotoimg4 = findViewById(R.id.fotoimg4);
//        inpBtnMap = findViewById(R.id.inpBtnMap);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnFile = findViewById(R.id.inpUploudBA);


//        handler


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
                    setValueInput();
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

                if (spinnerKodeAset.getSelectedItem().equals("ZA08/Alat Pengangkutan")){
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

                ArrayAdapter<String> adapterSap =new ArrayAdapter<>(AsetAddUpdateOfflineActivity.this, android.R.layout.simple_list_item_1,listSpinnerSap);
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

    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inpTglOleh.setText(dateFormat.format(myCalendar.getTime()));
    }


    public void setAdapterAsetKode(){
        List<String> asetKode = new ArrayList<>();
        String aset_kode_temp;
        asetKode.add("Pilih Kode Aset");
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
    public void setAdapterAsetKodeEdit(){
        List<String> asetKode = new ArrayList<>();
        String aset_kode_temp;
        Integer i = 0;
        for (AsetKode2 a : asetKode2) {
            if (a.getAsetJenis() == spinnerJenisAset.getSelectedItemId()) {

                if ((a.getAsetJenis()) == 1 ) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
                } else if ((a.getAsetJenis()) == 2) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                } else {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                }

                mapKodeSpinner.put(a.getAsetKodeId(),i);
                Log.d("map-1", String.valueOf(mapKodeSpinner.get(i)));
                mapSpinnerkode.put(i,a.getAsetKodeId());
                mapSpinnerkodeCoba.put(aset_kode_temp,a.getAsetKodeId());
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

    public void setAfdelingAdapter(){
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
                new DatePickerDialog(AsetAddUpdateOfflineActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    private void setValueInput(){
            asetHelper.open();
            Cursor data = asetHelper.queryById(String.valueOf(id));
            aset = MappingHelper.mapCursorToArrayAset(data);
                if (aset.getBeritaAcara() != null ) {
                    tvUploudBA.setText(aset.getBeritaAcara());
                }

                inpTglInput.setText(aset.getTglInput());
                inpTglOleh.setText(aset.getTglOleh());
                inpNoSAP.setText(aset.getNomorSap());
                inpNamaAset.setText(aset.getAsetName());
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

            spinnerTipeAset.setSelection(Integer.parseInt(aset.getAsetTipe()));
            spinnerJenisAset.setSelection(Integer.parseInt(aset.getAsetJenis()));
            setAdapterAsetKodeEdit();
            spinnerAsetKondisi.setSelection(Integer.parseInt(aset.getAsetKondisi()));
            spinnerSubUnit.setSelection(Integer.parseInt(aset.getAsetSubUnit()));

            try{
                if (aset.getAfdelingId() != null) {

//                mapAfdelingSpinner.put(185,1);
                    spinnerAfdeling.setSelection(mapAfdelingSpinner.get(afdeling_id-2));

                }

//                Log.d("asetkode1", String.valueOf(aset.getAsetKode()));
//                Log.d("asetkode2", String.valueOf(mapKodeSpinner.get(Integer.parseInt(aset.getAsetKode()))));
//                Log.d("asetkode3", String.valueOf(mapKodeSpinner.size()));
                if(mapKodeSpinner.size() != 0){

                    Integer idspinner = getSpinnerKodeAset(Integer.parseInt(aset.getAsetJenis()),Integer.parseInt(aset.getAsetKode()));
                    spinnerKodeAset.setSelection(idspinner);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }



                String url1 = "",url2 = "",url3 = "",url4 = "";
                url1 = "file://" + aset.getFotoAset1();
                url2 = "file://" + aset.getFotoAset2();
                url3 = "file://" + aset.getFotoAset3();
                url4 = "file://" + aset.getFotoAset4();

                try {


                if (aset.getFotoAset1() == null ){
                    map1.setEnabled(false);
                    foto1rl.setEnabled(false);
                } else {
                    URL url = new URL(url1);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    fotoimg1.getLayoutParams().width = 200;
                    fotoimg1.getLayoutParams().height = 200;
                    fotoimg1.setImageBitmap(bmp);
                    map1.setEnabled(true);
                }

        if (aset.getFotoAset2() == null ){
            map2.setEnabled(false);
            foto2rl.setEnabled(false);
        } else {
            URL url = new URL(url2);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            fotoimg2.getLayoutParams().width = 200;
            fotoimg2.getLayoutParams().height = 200;
            fotoimg2.setImageBitmap(bmp);
            map2.setEnabled(true);
        }

        if (aset.getFotoAset3() == null ){
            map3.setEnabled(false);
            foto3rl.setEnabled(false);
        } else {
            URL url = new URL(url3);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            fotoimg3.getLayoutParams().width = 200;
            fotoimg3.getLayoutParams().height = 200;
            fotoimg3.setImageBitmap(bmp);
            map3.setEnabled(true);
        }

        if (aset.getFotoAset4() == null ){
            map4.setEnabled(false);
            foto4rl.setEnabled(false);
        } else {
            URL url = new URL(url4);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            fotoimg4.getLayoutParams().width = 200;
            fotoimg4.getLayoutParams().height = 200;
            fotoimg4.setImageBitmap(bmp);
            map4.setEnabled(true);
        }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                geotag1 = aset.getGeoTag1();
                geotag2 = aset.getGeoTag2();
                geotag3 = aset.getGeoTag3();
                geotag4 = aset.getGeoTag4();


//                set selection spinners



                editVisibilityDynamic();



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



    private void setExifLocation(File fileImage,int list){
        try {
            getLastLocation(AsetAddUpdateOfflineActivity.this,getApplicationContext());
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
//    public void editVisibilityDynamic(){
//        TextView tvBa = findViewById(R.id.tvBa);
//        TextView tvPohon = findViewById(R.id.tvPohon);
//        TextView tvBast = findViewById(R.id.tvBast);
//        TextView tvFoto = findViewById(R.id.tvFoto);
//        TextView tvAfdeling = findViewById(R.id.tvAfdeling);
//        Spinner inpAfdeling = findViewById(R.id.inpAfdeling);
//        TextView tvLuasTanaman = findViewById(R.id.luasTanaman);
//        TextView tvLuasNonTanaman = findViewById(R.id.luasNonTanaman);
//        TextView tvPersenKondisi = findViewById(R.id.tvPersenKondisi);
//
//        HorizontalScrollView scrollPartition = findViewById(R.id.scrollPartition);
////        Toast.makeText(getApplicationContext(),String.valueOf(spinnerSubUnit.getSelectedItemId()),Toast.LENGTH_LONG).show();
//        if (spinnerTipeAset.getSelectedItemId() != 0) {
//            inpNomorBAST.setVisibility(View.VISIBLE);
//            tvBast.setVisibility(View.VISIBLE);
//        } else {
//            inpNomorBAST.setVisibility(View.GONE);
//            tvBast.setVisibility(View.GONE);
//        }
//
//        if (spinnerSubUnit.getSelectedItemId() == 2){
//            inpAfdeling.setVisibility(View.VISIBLE);
//            tvAfdeling.setVisibility(View.VISIBLE);
////            Toast.makeText(getApplicationContext(),String.valueOf(spinnerSubUnit.getSelectedItemId()),Toast.LENGTH_LONG).show();
//        } else {
//            inpAfdeling.setVisibility(View.GONE);
//            tvAfdeling.setVisibility(View.GONE);
//        }
//
//        if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
////            listBtnMap.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.VISIBLE);
////            inpKomoditi.setVisibility(View.VISIBLE);
//
//            inpNomorBAST.setVisibility(View.VISIBLE);
//            tvBast.setVisibility(View.VISIBLE);
//            tvUploudBA.setVisibility(View.GONE);
//            tvBa.setVisibility(View.GONE);
////            inpBtnMap.setVisibility(View.GONE);
//            btnFile.setVisibility(View.GONE);
//
//            tvFoto.setVisibility(View.VISIBLE);
//            scrollPartition.setVisibility(View.VISIBLE);
//
//            tvLuasTanaman.setVisibility(View.VISIBLE);
//            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);
//
//            inpPersenKondisi.setVisibility(View.GONE);
//            tvPersenKondisi.setVisibility(View.GONE);
//
//        }
//
//        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
////            listBtnMap.setVisibility(View.VISIBLE);
//            tvBa.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.VISIBLE);
//
////            inpKomoditi.setVisibility(View.VISIBLE);
//            inpNomorBAST.setVisibility(View.GONE);
//            tvBast.setVisibility(View.GONE);
//            tvUploudBA.setVisibility(View.VISIBLE);
//            btnFile.setVisibility(View.VISIBLE);
//
////            inpBtnMap.setVisibility(View.GONE);
//
//            tvFoto.setVisibility(View.VISIBLE);
//            scrollPartition.setVisibility(View.VISIBLE);
//
//            tvLuasTanaman.setVisibility(View.VISIBLE);
//            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);
//
//            inpPersenKondisi.setVisibility(View.GONE);
//            tvPersenKondisi.setVisibility(View.GONE);
//
//        }
//
//
//        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
//            tvUploudBA.setVisibility(View.VISIBLE);
//            btnFile.setVisibility(View.VISIBLE);
//            tvBa.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.VISIBLE);
////            inpKomoditi.setVisibility(View.VISIBLE);
//
//            inpNomorBAST.setVisibility(View.GONE);
//            tvBast.setVisibility(View.GONE);
////            listBtnMap.setVisibility(View.GONE);
////            inpBtnMap.setVisibility(View.GONE);
//
//            tvFoto.setVisibility(View.GONE);
//            scrollPartition.setVisibility(View.GONE);
//
//            tvLuasTanaman.setVisibility(View.VISIBLE);
//            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);
//
//            inpPersenKondisi.setVisibility(View.GONE);
//            tvPersenKondisi.setVisibility(View.GONE);
//        }
//
//        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
////            listBtnMap.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.VISIBLE);
////            inpKomoditi.setVisibility(View.VISIBLE);
//            inpNomorBAST.setVisibility(View.VISIBLE);
//            tvBast.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.VISIBLE);
//
//            tvBa.setVisibility(View.GONE);
//            tvUploudBA.setVisibility(View.GONE);
////            inpBtnMap.setVisibility(View.GONE);
//            btnFile.setVisibility(View.GONE);
//
//            tvFoto.setVisibility(View.VISIBLE);
//            scrollPartition.setVisibility(View.VISIBLE);
//
//            tvLuasTanaman.setVisibility(View.VISIBLE);
//            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);
//
//            inpPersenKondisi.setVisibility(View.GONE);
//            tvPersenKondisi.setVisibility(View.GONE);
//
//        }
//
//        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
////            listBtnMap.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.VISIBLE);
//            tvBa.setVisibility(View.VISIBLE);
//            tvUploudBA.setVisibility(View.VISIBLE);
//            btnFile.setVisibility(View.VISIBLE);
////            inpKomoditi.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.VISIBLE);
//
//
//            inpNomorBAST.setVisibility(View.GONE);
//            tvBast.setVisibility(View.GONE);
////            inpBtnMap.setVisibility(View.GONE);
//
//            tvFoto.setVisibility(View.VISIBLE);
//            scrollPartition.setVisibility(View.VISIBLE);
//
//            tvLuasTanaman.setVisibility(View.VISIBLE);
//            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);
//
//            inpPersenKondisi.setVisibility(View.GONE);
//            tvPersenKondisi.setVisibility(View.GONE);
//
//        }
//
//        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
//            tvBa.setVisibility(View.VISIBLE);
//            btnFile.setVisibility(View.VISIBLE);
//            tvUploudBA.setVisibility(View.VISIBLE);
////            inpKomoditi.setVisibility(View.VISIBLE);
//
//            inpNomorBAST.setVisibility(View.GONE);
//            tvBast.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
////            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
////            inpBtnMap.setVisibility(View.GONE);
//
//            tvFoto.setVisibility(View.GONE);
//            scrollPartition.setVisibility(View.GONE);
//
//            tvLuasTanaman.setVisibility(View.VISIBLE);
//            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);
//
//            inpPersenKondisi.setVisibility(View.GONE);
//            tvPersenKondisi.setVisibility(View.GONE);
//
//        }
//
//        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
////            inpBtnMap.setVisibility(View.VISIBLE);
//            inpNomorBAST.setVisibility(View.VISIBLE);
//            tvBast.setVisibility(View.VISIBLE);
//
////            inpKomoditi.setVisibility(View.GONE);
//            tvUploudBA.setVisibility(View.GONE);
////            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
//            tvBa.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
//            btnFile.setVisibility(View.GONE);
//
//            tvFoto.setVisibility(View.VISIBLE);
//            scrollPartition.setVisibility(View.VISIBLE);
//
//            tvLuasTanaman.setVisibility(View.GONE);
//            tvLuasNonTanaman.setVisibility(View.VISIBLE);
//            inpLuasAset.setVisibility(View.VISIBLE);
//
//            inpPersenKondisi.setVisibility(View.VISIBLE);
//            tvPersenKondisi.setVisibility(View.VISIBLE);
//        }
//
//        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) &&"rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
//            tvBa.setVisibility(View.VISIBLE);
//            btnFile.setVisibility(View.VISIBLE);
//            tvUploudBA.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
////            inpBtnMap.setVisibility(View.VISIBLE);
//            inpNomorBAST.setVisibility(View.GONE);
//            tvBast.setVisibility(View.GONE);
//
//            tvFoto.setVisibility(View.VISIBLE);
//            scrollPartition.setVisibility(View.VISIBLE);
//
////            listBtnMap.setVisibility(View.GONE);
//            tvLuasTanaman.setVisibility(View.GONE);
//            tvLuasNonTanaman.setVisibility(View.VISIBLE);
//            inpLuasAset.setVisibility(View.VISIBLE);
//
//            inpPersenKondisi.setVisibility(View.VISIBLE);
//            tvPersenKondisi.setVisibility(View.VISIBLE);
//
//        }
//
//        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
//            tvBa.setVisibility(View.VISIBLE);
//            btnFile.setVisibility(View.VISIBLE);
//            tvUploudBA.setVisibility(View.VISIBLE);
//
////            inpKomoditi.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
////            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
////            inpBtnMap.setVisibility(View.GONE);
//            inpNomorBAST.setVisibility(View.GONE);
//            tvBast.setVisibility(View.GONE);
//
//            tvFoto.setVisibility(View.GONE);
//            scrollPartition.setVisibility(View.GONE);
//
//            tvLuasTanaman.setVisibility(View.GONE);
//            tvLuasNonTanaman.setVisibility(View.VISIBLE);
//            inpLuasAset.setVisibility(View.VISIBLE);
//
//            inpPersenKondisi.setVisibility(View.VISIBLE);
//            tvPersenKondisi.setVisibility(View.VISIBLE);
//
//        } else {
////            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
//            tvFoto.setVisibility(View.GONE);
//            scrollPartition.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
//            tvBa.setVisibility(View.GONE);
//            tvUploudBA.setVisibility(View.GONE);
////            inpBtnMap.setVisibility(View.GONE);
//            btnFile.setVisibility(View.GONE);
//
//            tvLuasTanaman.setVisibility(View.GONE);
//            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.GONE);
//
//            inpPersenKondisi.setVisibility(View.GONE);
//            tvPersenKondisi.setVisibility(View.GONE);
//        }
//    }

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
            spinnerLuasSatuan.setVisibility(View.GONE);
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
            spinnerLuasSatuan.setVisibility(View.GONE);

        }

        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);
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
            spinnerLuasSatuan.setVisibility(View.GONE);

        }


        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);
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
            spinnerLuasSatuan.setVisibility(View.GONE);
        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
            inpJumlahPohon.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvPohon.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);
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

            spinnerLuasSatuan.setVisibility(View.GONE);
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
            spinnerLuasSatuan.setVisibility(View.GONE);

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
            spinnerLuasSatuan.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);
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
            spinnerLuasSatuan.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            inpBtnMap.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);

            spinnerLuasSatuan.setVisibility(View.VISIBLE);
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
            spinnerLuasSatuan.setVisibility(View.VISIBLE);
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
            spinnerLuasSatuan.setVisibility(View.VISIBLE);
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
            spinnerLuasSatuan.setVisibility(View.GONE);
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

    @Override
    public void preExecute() {
        dialog.show();
    }
    @Override
    public void postExecute(ArrayList<Aset> aset) {
        dialog.dismiss();
        if (aset.size() > 0) {
            adapter.setListAset(aset);
        } else {
            adapter.setListAset(new ArrayList<Aset>());
//            utils.showSnackbarMessage(rvNotes,"Tidak ada data saat ini");
        }
    }

    public void getAllSpinnerData(){

        asetHelper.open();
        Cursor asetTipe = asetHelper.getAllAsetTipe();
        Cursor asetJenis = asetHelper.getAllAsetJenis();
        Cursor asetKondisi = asetHelper.getAllAsetKondisi();
        Cursor asetKodeCursor = asetHelper.getAllAsetKode();
        Cursor unit = asetHelper.getAllUnit();
        Cursor subUnit = asetHelper.getAllSubUnit();
        Cursor afdeling = asetHelper.getAllAfdeling();
        Cursor sap = asetHelper.getAllSap();
        Cursor alatAngkut = asetHelper.getAllAlatAngkut();
         DataAllSpinner dataAllSpinner = MappingHelper.mapCursorToArrayListSpinner(asetTipe,asetJenis,asetKondisi,asetKodeCursor,unit,subUnit,afdeling,sap,alatAngkut);

        List<String> listSpinnerTipe = new ArrayList<>();

        List<String> listSpinnerJenis = new ArrayList<>();

        List<String> listSpinnerKondisiAset = new ArrayList<>();


        List<String> listSpinnerUnit = new ArrayList<>();

        List<String> listSpinnerSubUnit = new ArrayList<>();

        List<String> listSpinnerAfdeling = new ArrayList<>();
        List<String> listSpinnerAlatAngkut = new ArrayList<>();



        listSpinnerTipe.add("Pilih Tipe Aset");
        listSpinnerJenis.add("Pilih Jenis Aset");
        listSpinnerKondisiAset.add("Pilih Kondisi Aset");
        listSpinnerSubUnit.add("Pilih Sub Unit ");
        listSpinnerAfdeling.add("Pilih Afdeling ");
        listSpinnerAlatAngkut.add("Pilih Alat Angkut ");

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

        // get alat angkut

        for (AlatAngkut at : dataAllSpinner.getAlatAngkut()){

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
                    listSpinnerSap.add(at.getSap_desc());
                }

            // set adapter unit
            ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, listSpinnerUnit);
            adapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUnit.setAdapter(adapterUnit);
            sharedPreferences = AsetAddUpdateOfflineActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
            try {

                Integer unit_id = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
                spinnerUnit.setSelection(unit_id-1);
            } catch(Exception e){}

//                // get afdeling
                Integer i=1;
                afdeling2 = dataAllSpinner.getAfdeling();
                for (Afdelling at : dataAllSpinner.getAfdeling()){
                    if (at.getUnit_id() == (spinnerUnit.getSelectedItemId()+1)) {
                        mapSpinnerAfdeling.put(i,at.getAfdelling_id());
                        mapAfdelingSpinner.put(at.getAfdelling_id(),i);
                        mapAfdeling.put(i, at.getAfdelling_desc());
                        listSpinnerAfdeling.add(at.getAfdelling_desc());
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

                try {
                    Integer sub_unit_id = Integer.valueOf(sharedPreferences.getString("sub_unit_id", "0"));
                    spinnerSubUnit.setSelection(sub_unit_id);
                } catch (Exception e){}

                spinnerAfdeling.setSelection(mapAfdelingSpinner.get(afdeling_id-2));

                asetHelper.close();

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
            customDialogAddAset.dismiss();
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
            customDialogAddAset.dismiss();
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
            customDialogAddAset.dismiss();
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
            customDialogAddAset.dismiss();
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
            customDialogAddAset.dismiss();
            return;
        }


    }

    public void addAset(){
        dialog.show();
        spinnerValidation();
        if (inpNamaAset.getText().toString().equals("")) {
            dialog.dismiss();
            customDialogAddAset.dismiss();
            inpNamaAset.setError("nama harus diisi");
            inpNamaAset.requestFocus();

            return;
        }

        if (inpNoSAP.getText().toString().equals("")) {
            dialog.dismiss();
            customDialogAddAset.dismiss();
            inpNoSAP.setError("nomor SAP harus diisi");
            inpNoSAP.requestFocus();

            return;
        }

        if (spinnerJenisAset.getSelectedItemId() == 2) {
            if (Integer.parseInt(inpPersenKondisi.getText().toString()) > 100 || Integer.parseInt(inpPersenKondisi.getText().toString()) < 0) {
                dialog.dismiss();
                customDialogAddAset.dismiss();
                inpPersenKondisi.setError("Isian Persen Kondisi Wajib Minimal 0 Maksimal 100");
                inpPersenKondisi.requestFocus();

                return;
            }
        }

        if (inpNilaiAsetSAP.getText().toString().equals("")) {
            dialog.dismiss();
            customDialogAddAset.dismiss();
            inpNilaiAsetSAP.setError("Nilai Perolehan Aset harus diisi");
            inpNilaiAsetSAP.requestFocus();

            return;
        }

        if (inpTglOleh.getText().toString().equals("")) {
            dialog.dismiss();
            customDialogAddAset.dismiss();
            inpTglOleh.setError("Tanggal Perolehan harus diisi");
            inpTglOleh.requestFocus();

            return;
        }

        if (inpMasaPenyusutan.getText().toString().equals("")) {
            dialog.dismiss();
            customDialogAddAset.dismiss();
            inpMasaPenyusutan.setError("Masa Penyusutan harus diisi");
            inpMasaPenyusutan.requestFocus();

            return;
        }

        if (inpNilaiResidu.getText().toString().equals("")) {
            dialog.dismiss();
            customDialogAddAset.dismiss();
            inpNilaiResidu.setError("Nilai Residu harus diisi");
            inpNilaiResidu.requestFocus();

            return;
        }

        if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))){
            if ("normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))){
                if (img1 == null || img2 == null || img3 == null || img4 == null){
                    Toast.makeText(getApplicationContext(), "Foto Wajib Diisi Lengkap!", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                    customDialogAddAset.dismiss();
                    return;
                }
            }
        }

        if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))){
            if ( "rusak".equals (String.valueOf(spinnerAsetKondisi.getSelectedItem()))){
                if (img1 == null || img2 == null || img3 == null || img4 == null){
                    Toast.makeText(getApplicationContext(), "Foto Wajib Diisi Lengkap!", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    customDialogAddAset.dismiss();
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
            Log.d("amanat18-idkodeaset",String.valueOf(idkodeaset));
            values.put("aset_kode", String.valueOf(idkodeaset));

            values.put("unit_id", String.valueOf(spinnerUnit.getSelectedItemId()+1));
            values.put("aset_sub_unit", String.valueOf(spinnerSubUnit.getSelectedItemId()));
            values.put("afdeling_id", String.valueOf(afdeling_id));
            values.put("aset_name", inpNamaAset.getText().toString().trim());

            // Get the internal files directory
            String namaAsetWithoutSpace = inpNamaAset.getText().toString().trim();
            namaAsetWithoutSpace = namaAsetWithoutSpace.replace(" ", "");

            // Create a new file in the internal files directory

            File newImg1 = new File(getFilesDir(),namaAsetWithoutSpace +"1.png");
            File newImg2 = new File(getFilesDir(),namaAsetWithoutSpace+"2.png");
            File newImg3 = new File(getFilesDir(),namaAsetWithoutSpace+"3.png");
            File newImg4 = new File(getFilesDir(),namaAsetWithoutSpace+"4.png");
            File ba = new File(getFilesDir(),namaAsetWithoutSpace+"-ba.pdf");

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

                values.put("foto_aset1",newImg1.getAbsolutePath());
                values.put("geo_tag1",geotag1);


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
                values.put("foto_aset2",newImg2.getAbsolutePath());
                values.put("geo_tag2",geotag2);


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
                values.put("foto_aset3",newImg3.getAbsolutePath());
                values.put("geo_tag3",geotag3);


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
                values.put("foto_aset4",newImg4.getAbsolutePath());
                values.put("geo_tag4",geotag4);

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
                values.put("berita_acara",ba.getAbsolutePath());

            }

            String nomor_aset_sap = inpNoSAP.getText().toString().trim();
            values.put("nomor_sap",nomor_aset_sap);

            values.put("aset_luas",inpLuasAset.getText().toString().trim());
            values.put("persen_kondisi",inpPersenKondisi.getText().toString().trim());
            values.put("hgu",inpHGU.getText().toString().trim());
            values.put("nilai_oleh",utils.CurrencyToNumber(inpNilaiAsetSAP.getText().toString().trim()));
            values.put("tgl_oleh",inpTglOleh.getText().toString().trim() + " 00:00:00");
            LocalDateTime currentTime = null;
            DateTimeFormatter formatter = null;
            String formattedDateTime = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentTime = LocalDateTime.now();
                 formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                 formattedDateTime = currentTime.format(formatter);
            }

            values.put("tgl_input", String.valueOf(formattedDateTime));
            values.put("masa_susut",inpMasaPenyusutan.getText().toString().trim());
            values.put("nilai_residu",utils.CurrencyToNumber(inpNilaiResidu.getText().toString().trim()));
            values.put("nomor_bast",inpNomorBAST.getText().toString().trim());
            values.put("jumlah_pohon",inpJumlahPohon.getText().toString().trim());
            values.put("keterangan",inpKeterangan.getText().toString().trim());

            asetHelper.open();
            asetHelper.insert(values);
            asetHelper.close();
            customDialogAddAset.dismiss();
            dialog.dismiss();
//            finish();
            Intent intent = new Intent(AsetAddUpdateOfflineActivity.this, LonglistAsetActivity.class);
            startActivity(intent);
//            saveImageInternal(img1,inpNamaAset.getText().toString().trim(),1);
        } catch(Exception e) {
            customDialogAddAset.dismiss();
            dialog.dismiss();
            e.printStackTrace();
        }




    }


    public void editAset(){
        dialog.show();
        spinnerValidation();
//        if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))){
//            if ("normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))){
//                if (img1 == null || img2 == null || img3 == null || img4 == null){
//                    Toast.makeText(getApplicationContext(), "Foto Wajib Diisi Lengkap!", Toast.LENGTH_SHORT).show();
//
//                    dialog.dismiss();
//                    return;
//                }
//            }
//        }

//        if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))){
//            if ( "rusak".equals (String.valueOf(spinnerAsetKondisi.getSelectedItem()))){
//                if (img1 == null || img2 == null || img3 == null || img4 == null){
//                    Toast.makeText(getApplicationContext(), "Foto Wajib Diisi Lengkap!", Toast.LENGTH_SHORT).show();
//
//                    dialog.dismiss();
//                    return;
//                }
//            }
//        }

        try {
            ContentValues values = new ContentValues();
            values.put("aset_tipe", String.valueOf(spinnerTipeAset.getSelectedItemId()));
            values.put("aset_jenis", String.valueOf(spinnerJenisAset.getSelectedItemId()));
            values.put("aset_kondisi", String.valueOf(spinnerAsetKondisi.getSelectedItemId()));

            Integer idkode = getAsetKodeId(Math.toIntExact(spinnerJenisAset.getSelectedItemId()),Math.toIntExact(spinnerKodeAset.getSelectedItemId()));
            values.put("aset_kode", String.valueOf(idkode+1));

            values.put("unit_id", String.valueOf(spinnerUnit.getSelectedItemId()+1));
            values.put("aset_sub_unit", String.valueOf(spinnerSubUnit.getSelectedItemId()));
            values.put("afdeling_id", String.valueOf(afdeling_id));

            values.put("aset_name", inpNamaAset.getText().toString().trim());

            // Get the internal files directory

            String namaAsetWithoutSpace = inpNamaAset.getText().toString().trim();
            namaAsetWithoutSpace = namaAsetWithoutSpace.replace(" ", "");

            // Create a new file in the internal files directory

            File newImg1 = new File(getFilesDir(),namaAsetWithoutSpace +"1.png");
            File newImg2 = new File(getFilesDir(),namaAsetWithoutSpace+"2.png");
            File newImg3 = new File(getFilesDir(),namaAsetWithoutSpace+"3.png");
            File newImg4 = new File(getFilesDir(),namaAsetWithoutSpace+"4.png");
            File ba = new File(getFilesDir(),namaAsetWithoutSpace+"-ba.pdf");

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

                values.put("foto_aset1",newImg1.getAbsolutePath());
                values.put("geo_tag1",geotag1);


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
                values.put("foto_aset2",newImg2.getAbsolutePath());
                values.put("geo_tag2",geotag2);


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
                values.put("foto_aset3",newImg3.getAbsolutePath());
                values.put("geo_tag3",geotag3);


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
                values.put("foto_aset4",newImg4.getAbsolutePath());
                values.put("geo_tag4",geotag4);

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
                values.put("berita_acara",ba.getAbsolutePath());

            }

            String nomor_aset_sap = inpNoSAP.getText().toString().trim();
            values.put("nomor_sap",nomor_aset_sap);
            values.put("aset_luas",inpLuasAset.getText().toString().trim());
            values.put("persen_kondisi",inpPersenKondisi.getText().toString().trim());
            values.put("hgu",inpHGU.getText().toString().trim());
            values.put("nilai_oleh",utils.CurrencyToNumber(inpNilaiAsetSAP.getText().toString().trim()));
            values.put("tgl_oleh",inpTglOleh.getText().toString().trim());
            LocalDateTime currentTime = null;
            DateTimeFormatter formatter = null;
            String formattedDateTime = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentTime = LocalDateTime.now();
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                formattedDateTime = currentTime.format(formatter);
            }

            values.put("tgl_input", String.valueOf(formattedDateTime));
            values.put("masa_susut",inpMasaPenyusutan.getText().toString().trim());
            values.put("nilai_residu",utils.CurrencyToNumber(inpNilaiResidu.getText().toString().trim()));
            values.put("nomor_bast",inpNomorBAST.getText().toString().trim());
            values.put("jumlah_pohon",inpJumlahPohon.getText().toString().trim());
            values.put("keterangan",inpKeterangan.getText().toString().trim());

            asetHelper.open();
            asetHelper.update(String.valueOf(id),values);
            asetHelper.close();
            customDialogUpdateAset.dismiss();
            dialog.dismiss();
//            finish();
            Intent intent = new Intent(this, LonglistAsetActivity.class);
//            intent.setAction("isChecked");
//                    new Intent(AsetAddUpdateOfflineActivity.this, LonglistAsetActivity.class);
            startActivity(intent);
//            saveImageInternal(img1,inpNamaAset.getText().toString().trim(),1);
        } catch(Exception e) {
            customDialogUpdateAset.dismiss();
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"error : " + e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }




    }

    private Integer getAsetKodeId(Integer asetJenis,Integer spinnerId) {
        Integer i = 0;
        for (AsetKode2 a : asetKode2){
            if (a.getAsetJenis() == asetJenis) {
                Log.d("aset-spinner", String.valueOf(spinnerId));
                Log.d("aset-i", String.valueOf(i));
                if (i == spinnerId) {
                    return a.getAsetKodeId()-1;
                }

                i++;
            }
        }

        return 0;
    }

    private Integer getSpinnerKodeAset(Integer asetJenis,Integer idkodeaset) {
        Integer i = 0;
        for (AsetKode2 a : asetKode2){
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

    private static class LoadNotesAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallback> weakCallback;
        private LoadNotesAsync(Context context, LoadNotesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }
        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            weakCallback.get().preExecute();
            executor.execute(() -> {
                Context context = weakContext.get();
                AsetHelper asetHelper = AsetHelper.getInstance(context);
                asetHelper.open();
                Cursor dataCursor = asetHelper.queryAll();
                ArrayList<Aset> aset = MappingHelper.mapCursorToArrayListAset(dataCursor);
                asetHelper.close();
                handler.post(() -> weakCallback.get().postExecute(aset));
            });
        }
    }




}

interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<Aset> notes);
}