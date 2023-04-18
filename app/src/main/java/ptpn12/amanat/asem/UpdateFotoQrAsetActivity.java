package ptpn12.amanat.asem;

import static ptpn12.amanat.asem.utils.utils.CurrencyToNumber;

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
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
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
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.Afdelling;
import ptpn12.amanat.asem.api.model.AfdellingModel;
import ptpn12.amanat.asem.api.model.AlatAngkut;
import ptpn12.amanat.asem.api.model.AllSpinner;
import ptpn12.amanat.asem.api.model.AsetJenis;
import ptpn12.amanat.asem.api.model.AsetJenisModel;
import ptpn12.amanat.asem.api.model.AsetKode2;
import ptpn12.amanat.asem.api.model.AsetKodeModel2;
import ptpn12.amanat.asem.api.model.AsetKondisi;
import ptpn12.amanat.asem.api.model.AsetModel;
import ptpn12.amanat.asem.api.model.AsetModel2;
import ptpn12.amanat.asem.api.model.AsetTipe;
import ptpn12.amanat.asem.api.model.Data;
import ptpn12.amanat.asem.api.model.DataAllSpinner;
import ptpn12.amanat.asem.api.model.Sap;
import ptpn12.amanat.asem.api.model.SistemTanam;
import ptpn12.amanat.asem.api.model.SubUnit;
import ptpn12.amanat.asem.api.model.SubUnitModel;
import ptpn12.amanat.asem.api.model.Unit;
import ptpn12.amanat.asem.api.model.UnitModel;
import ptpn12.amanat.asem.utils.GpsConverter;
import ptpn12.amanat.asem.utils.utils;

import com.google.android.gms.location.LocationCallback;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

public class UpdateFotoQrAsetActivity extends AppCompatActivity {
    ViewGroup addNewFotoAsetAndQr;
    ViewGroup addNewFotoAsetAndQr2;
    ImageView fotoasetqr;
    ImageView fotoasetqr2;
    Button inpSimpanFotoQr;
    File fotoAsetQrFile;
    File fotoAsetQrFile2;
    Data aset;
    Button inpBtnMap;
    Button btnFile;
    Button btnFileBAST;
    Button btnSubmit;
    Button btnYaKirim;
    Button btnTidakKirim;
    Button map1;
    Button map2;
    Button map3;
    Button map4;
    Button map5;
    Button downloadBa;
    Button downloadBAST;
    double longitudeValue = 0;
    double latitudeValue = 0;
    List<String> listSpinnerAlatAngkut = new ArrayList<>();
    List<String> listSpinnerSistemTanam = new ArrayList<>();
    Map<Integer,Integer> mapKodeSpinner = new HashMap();
    Map<Integer,Integer> mapSpinnerkode = new HashMap();
    Map<Long, Integer> mapSap = new HashMap();
    Map<Integer, Long> mapSpinnerSap = new HashMap();
    List<String> listSpinnerSap = new ArrayList<>();
    List<Sap> sapAll = new ArrayList<>();

    DataAllSpinner allSpinner;
    Map<Integer, Integer> mapAfdelingSpinner = new HashMap<Integer, Integer>();
    Map<Integer, Integer> mapSpinnerAfdeling = new HashMap<Integer, Integer>();
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
//    EditText inpJumlahPohon;
    TextView tvUploudBA;
    TextView tvUploadBAST;
    TextView tvKetReject;
    AsetModel asetModel;
    //    List<String> listSpinnerSap=new ArrayList<>();
    File source;
    private AsetInterface asetInterface;
    Spinner spinnerTipeAset;
    Spinner spinnerJenisAset;
    Spinner spinnerAsetKondisi;
    Spinner spinnerKodeAset;
    Spinner spinnerSubUnit;
    Spinner spinnerUnit;
    Spinner spinnerAfdeling;
    Spinner spinnerAlatAngkut;
    Spinner spinnerLuasSatuan;
    Spinner spinnerSistemTanam;

    EditText inpTglInput;
    EditText inpNamaAset;
    TextView inpNoSAP;
    TextView tvAlatAngkut;
    TextView tvSistemTanam;

    EditText inpLuasAset;
    EditText inpTahunTanam;
    EditText inpNilaiAsetSAP;
    EditText inpTglOleh;
    EditText inpMasaPenyusutan;
    EditText inpHGU;
    EditText inpNomorBAST;
    EditText inpNilaiResidu;
    EditText inpKeterangan;
    EditText inpUmrEkonomis;
    EditText inpPersenKondisi;
    EditText inpKetReject;
    EditText inpPopTotalPohonSaatIni;
    EditText inpPopTotalStdMaster;
    EditText inpPopPerHA;
    EditText inpPresentasePopPerHA;

    TextView tvPopTotalPohonSaatIni;
    TextView tvPopTotalStdMaster;
    TextView tvPopPerHA;
    TextView tvPresentasePopPerHA;

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
    String urlBa;
    String urlBAST;

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
    String spinnerIdSubUnit;
    String spinnerIdSistemTanam;

    Dialog customDialogEditAset;


    Integer id;



    public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null ) {

            Uri uri = data.getData();
            String path =  uri.getPath();
            bafile_file = new File(path);
            tvUploudBA.setText(bafile_file.getName());
            Toast.makeText(getApplicationContext(),"sukses unggah berita acara",Toast.LENGTH_LONG).show();
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                fotoAsetQrFile = getFile(this,uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                fotoasetqr.getLayoutParams().height = 350;
                fotoasetqr.getLayoutParams().width = 400;
                fotoasetqr.setImageBitmap(bitmapImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(),"sukses unggah foto 1",Toast.LENGTH_LONG).show();
        }  else if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                fotoAsetQrFile2 = getFile(this,uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                fotoasetqr2.getLayoutParams().height = 350;
                fotoasetqr2.getLayoutParams().width = 400;
                fotoasetqr2.setImageBitmap(bitmapImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(),"sukses unggah foto 2",Toast.LENGTH_LONG).show();
        }
        else if (requestCode == 4 && resultCode == Activity.RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                file_bast = getFile(this, uri);
                tvUploadBAST.setText(file_bast.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void openfilechoser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,1);
    }

    public void openfilechoserBAST(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,4);
    }


    public void openfilechoserImg(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                2);
    }

    public void openfilechoserImg2(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                3);
    }

    public File getFile(Context context, Uri uri) throws IOException {
        // save file to directory Documents di package aplikasi
        File destinationFilename = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Toast.makeText(UpdateFotoQrAsetActivity.this, "File tidak dapat dimuat", Toast.LENGTH_LONG).show();
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
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




    ActivityResultLauncher<Intent> activityCaptureFoto1 =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode== Activity.RESULT_OK){
                                img1 = utils.savePictureResult(
                                        UpdateFotoQrAsetActivity.this, photoname1, fotoimg1, true
                                );
                                fotoimg1.getLayoutParams().width = 200;
                                fotoimg1.getLayoutParams().height = 200;
                                setExifLocation(img1,1);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateFotoQrAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                                        UpdateFotoQrAsetActivity.this, photoname2, fotoimg2, true
                                );
                                fotoimg2.getLayoutParams().width = 200;
                                fotoimg2.getLayoutParams().height = 200;
                                setExifLocation(img2,2);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateFotoQrAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                                        UpdateFotoQrAsetActivity.this, photoname3, fotoimg3, true
                                );
                                fotoimg3.getLayoutParams().width = 200;
                                fotoimg3.getLayoutParams().height = 200;
                                setExifLocation(img3,3);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateFotoQrAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                                        UpdateFotoQrAsetActivity.this, photoname4, fotoimg4, true
                                );
                                fotoimg4.getLayoutParams().width = 200;
                                fotoimg4.getLayoutParams().height = 200;
                                setExifLocation(img4,4);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateFotoQrAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
                            if (resultCode== Activity.RESULT_OK){
                                img5 = utils.savePictureResult(
                                        UpdateFotoQrAsetActivity.this, photoname5, fotoimg5, true
                                );
                                fotoimg5.getLayoutParams().width = 200;
                                fotoimg5.getLayoutParams().height = 200;
                                setExifLocation(img5,5);
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateFotoQrAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );

    // foto aset + qr
    ActivityResultLauncher<Intent> activityCaptureFotoAsetQr =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode== Activity.RESULT_OK){
                                fotoAsetQrFile = utils.savePictureResult(
                                        UpdateFotoQrAsetActivity.this, "fotoasetqr.png", fotoasetqr, true
                                );
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateFotoQrAsetActivity.this, "input foto aset batal ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
            );

    ActivityResultLauncher<Intent> activityCaptureFotoAsetQr2 =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int resultCode = activityResult.getResultCode();
                            if (resultCode== Activity.RESULT_OK){
                                fotoAsetQrFile2 = utils.savePictureResult(
                                        UpdateFotoQrAsetActivity.this, "fotoasetqr2.png", fotoasetqr2, true
                                );
                            } else if (resultCode == Activity.RESULT_CANCELED){
                                android.widget.Toast.makeText(UpdateFotoQrAsetActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_update_foto_qr_aset);

        sharedPreferences = UpdateFotoQrAsetActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);

        spinnerNoSap = new Dialog(UpdateFotoQrAsetActivity.this);

        dialog = new Dialog(UpdateFotoQrAsetActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();

//        progress = new ProgressBar(this);
//        progress.setMessage("Wait while loading...");
//        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();

        getLastLocation(UpdateFotoQrAsetActivity.this,getApplicationContext());

        downloadBa = findViewById(R.id.inpDownloadBa);
        downloadBAST = findViewById(R.id.inpDownloadBAST);
        id = getIntent().getIntExtra("id",0);
        listBtnMap = findViewById(R.id.listMapButton);
        inpTglOleh = findViewById(R.id.inpTglMasukAset);
        inpTglOleh.setEnabled(false);
        tvUploudBA = findViewById(R.id.tvUploudBA);
        tvUploadBAST = findViewById(R.id.tvUploadFileBAST);
        spinnerTipeAset = findViewById(R.id.inpTipeAset);
        spinnerJenisAset = findViewById(R.id.inpJenisAset);
        spinnerAsetKondisi = findViewById(R.id.inpKndsAset);
        spinnerKodeAset = findViewById(R.id.inpKodeAset);
        inpTahunTanam = findViewById(R.id.inpTahunTanam);
        inpTahunTanam.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        spinnerUnit = findViewById(R.id.inpUnit);
        spinnerUnit.setEnabled(false);
        spinnerSubUnit = findViewById(R.id.inpSubUnit);
        spinnerSubUnit.setEnabled(false);
        spinnerAfdeling = findViewById(R.id.inpAfdeling);
        spinnerAfdeling.setEnabled(false);
        inpTglInput = findViewById(R.id.inpTglInput);
        inpTglInput.setEnabled(false);
        inpUmrEkonomis = findViewById(R.id.inpUmrEkonomis);
        inpUmrEkonomis.setEnabled(false);
        inpNamaAset = findViewById(R.id.inpNamaAset);
        inpNoSAP = findViewById(R.id.inpNmrSAP);
        inpLuasAset = findViewById(R.id.inpLuasAset);
        inpNilaiAsetSAP = findViewById(R.id.inpNilaiAsetSAP);
        inpNilaiAsetSAP.setEnabled(false);
        inpMasaPenyusutan = findViewById(R.id.inpMasaPenyusutan);
        inpMasaPenyusutan.setEnabled(false);
        inpNomorBAST = findViewById(R.id.inpNmrBAST);
        inpNilaiResidu = findViewById(R.id.inpNmrResidu);
        inpNilaiResidu.setEnabled(false);
        inpKeterangan = findViewById(R.id.inpKeterangan);
//        inpJumlahPohon = findViewById(R.id.inpJmlhPohon);
        inpPersenKondisi = findViewById(R.id.inpPersenKondisi);
        inpHGU = findViewById(R.id.inpHGU);
        inpKetReject = findViewById(R.id.inpKetReject);
        tvKetReject = findViewById(R.id.tvKetReject);
        inpPopTotalPohonSaatIni = findViewById(R.id.inpPopTotalIni);
        inpPopTotalStdMaster = findViewById(R.id.inpPopTotalStd);
        inpPopPerHA = findViewById(R.id.inpPopHektarIni);
        inpPopPerHA.setEnabled(false);
        inpPresentasePopPerHA = findViewById(R.id.inpPopHektarStd);
        inpPresentasePopPerHA.setEnabled(false);
        tvPopTotalPohonSaatIni = findViewById(R.id.popTotalIni);
        tvPopTotalStdMaster = findViewById(R.id.popTotalStd);
        tvPopPerHA = findViewById(R.id.popHektarIni);
        tvPresentasePopPerHA = findViewById(R.id.popHektarStd);

        spinnerLuasSatuan = findViewById(R.id.inpLuasSatuan);
        List<String> listSpinnerSatuan = new ArrayList<>();
        listSpinnerSatuan.add("Ha");
        listSpinnerSatuan.add("m2");
        listSpinnerSatuan.add("Item");
        ArrayAdapter<String> adapterLuasSatuan =new ArrayAdapter<>(UpdateFotoQrAsetActivity.this, android.R.layout.simple_list_item_1,listSpinnerSatuan);
        spinnerLuasSatuan.setAdapter(adapterLuasSatuan);
        spinnerAlatAngkut = findViewById(R.id.inpAlatAngkut);
        tvAlatAngkut = findViewById(R.id.tvAlatAngkut);
        spinnerSistemTanam = findViewById(R.id.inpSistemTanam);
        tvSistemTanam = findViewById(R.id.tvSistemTanam);

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

        btnFile = findViewById(R.id.inpUploudBA);
        btnFileBAST = findViewById(R.id.inpUploadBAST);
//        btnSubmit = findViewById(R.id.btnSubmit);

        inpSimpanFotoQr = findViewById(R.id.inpSimpanFotoQr);
        fotoasetqr = findViewById(R.id.fotoasetqr);
        fotoasetqr2 = findViewById(R.id.fotoasetqr2);
        addNewFotoAsetAndQr = findViewById(R.id.addNewFotoAsetAndQr);
        addNewFotoAsetAndQr2 = findViewById(R.id.addNewFotoAsetAndQr2);

        // globally
        TextView tvTitle = findViewById(R.id.tvTitle);
        //in your OnCreate() method
        tvTitle.setText("EDIT DATA");

        downloadBAST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadBASTFunc();
            }
        });
        downloadBa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadBaFunc();
            }
        });
        addNewFotoAsetAndQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                captureFotoQcLoses("fotoasetqr.png",activityCaptureFotoAsetQr);
                openfilechoserImg();

            }
        });
        addNewFotoAsetAndQr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                captureFotoQcLoses("fotoasetqr2.png",activityCaptureFotoAsetQr2);
                openfilechoserImg2();
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

                ArrayAdapter<String> adapterSap =new ArrayAdapter<>(UpdateFotoQrAsetActivity.this, android.R.layout.simple_list_item_1,listSpinnerSap);
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
                        setValueSap(adapterSap.getItem(position));

                        // Dismiss dialog
                        spinnerNoSap.dismiss();
                    }
                });
//                }

            }
        });

        spinnerSistemTanam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdSistemTanam = String.valueOf(position);
                editVisibilityDynamic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


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

        inpSimpanFotoQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityCaptureFotoAsetQr==null && activityCaptureFotoAsetQr2==null){
                    Toast.makeText(getApplicationContext(),"Mohon Lengkapi Foto Aset + QR!", Toast.LENGTH_SHORT).show();
                }else{
                    initDialogEditAset();
                }
            }
        });

        inpBtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(UpdateFotoQrAsetActivity.this,getApplicationContext());
                Log.d("asetapix",String.valueOf(latitudeValue) + " " + String.valueOf(longitudeValue));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag1));
                startActivity(intent);
            }
        });


//        listener spinner

        map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(UpdateFotoQrAsetActivity.this,getApplicationContext());
                Log.d("asetapix",String.valueOf(latitudeValue) + " " + String.valueOf(longitudeValue));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag1));
                startActivity(intent);
            }
        });

        map2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(UpdateFotoQrAsetActivity.this,getApplicationContext());
                Log.d("asetapix",String.valueOf(latitudeValue) + " " + String.valueOf(longitudeValue));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag2));
                startActivity(intent);
            }
        });

        map3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(UpdateFotoQrAsetActivity.this,getApplicationContext());
                Log.d("asetapix",String.valueOf(latitudeValue) + " " + String.valueOf(longitudeValue));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag3));
                startActivity(intent);
            }
        });

        map4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(UpdateFotoQrAsetActivity.this,getApplicationContext());
                Log.d("asetapix",String.valueOf(latitudeValue) + " " + String.valueOf(longitudeValue));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag4));
                startActivity(intent);
            }
        });

        map5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation(UpdateFotoQrAsetActivity.this,getApplicationContext());
                Log.d("asetapix",String.valueOf(latitudeValue) + " " + String.valueOf(longitudeValue));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(geotag5));
                startActivity(intent);
            }
        });

        spinnerSubUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdSubUnit = String.valueOf(position+1);
                editVisibilityDynamic();
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
                fotoimg1.getLayoutParams().width = 200;
                fotoimg1.getLayoutParams().height = 200;

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

        foto5rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureFotoQcLoses(photoname5,activityCaptureFoto5);

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
        getAllSpinnerData();
        initCalender();
        setValueInput();

        btnFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openfilechoserBAST();
            }


        });

        btnFileBAST.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { openfilechoser(); }
        });
    }

    public void initDialogEditAset(){
        customDialogEditAset = new Dialog(UpdateFotoQrAsetActivity.this, R.style.MyAlertDialogTheme);
        customDialogEditAset.setContentView(R.layout.dialog_submitupdate);
        customDialogEditAset.setCanceledOnTouchOutside(false);
        customDialogEditAset.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        btnYaKirim = customDialogEditAset.findViewById(R.id.btnYaKirim);
        btnTidakKirim = customDialogEditAset.findViewById(R.id.btnTidakKirim);
        customDialogEditAset.show();

        btnYaKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAset();
                //Toast.makeText(UpdateFotoQrAsetActivity.this,"masuk", Toast.LENGTH_SHORT).show();
            }
        });

        btnTidakKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogEditAset.dismiss();
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
                new DatePickerDialog(UpdateFotoQrAsetActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
//                listSpinner.add("pilih aset jenis");
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

    private Integer getAlatAngkut(String alatAngkut) {
        Integer i = 0;
        for (String a : listSpinnerAlatAngkut) {
            if (alatAngkut.equals(a)){

                return i;
            }
            i++;
        }
        return 0;
    }

    private Integer getSistemTanam(String sistemTanam) {
        Integer i = 0;
        for (String a : listSpinnerSistemTanam) {
            if (sistemTanam.equals(a)){

                return i;
            }
            i++;
        }
        return 0;
    }
    private void setValueInput(){
        Call<AsetModel> call = asetInterface.getAset(id);

        call.enqueue(new Callback<AsetModel>() {

                         @Override
                         public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {
                             Handler handler = new Handler();
                             handler.postDelayed(new Runnable() {
                                 public void run() {
                                     dialog.dismiss();
                                 }
                             }, 1500);
                             if (!response.isSuccessful() && response.body() == null){
                                 Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                                 return;
                             }



                             aset = response.body().getData();

                             urlBa = aset.getBeritaAcara();
                             if (urlBa != null ) {
                                 tvUploudBA.setText(urlBa.substring(0,10)+"...");
                             }
                             urlBAST = aset.getFileBAST();
                             if (urlBAST != null ) {
                                 tvUploadBAST.setText(urlBAST.substring(0,10)+"...");
                             }

                             if(aset.getAlat_pengangkutan() != null){
                                 spinnerAlatAngkut.setSelection(getAlatAngkut(aset.getAlat_pengangkutan()));
                             }

                             if(aset.getSistemTanam() != null){
                                 spinnerSistemTanam.setSelection(getSistemTanam(aset.getSistemTanam()));
                             }

                             if(aset.getTahunTanam() != null){
                                 inpTahunTanam.setText(aset.getTahunTanam());
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

                             inpTglInput.setText(aset.getTglInput().split(" ")[0]);
                             inpTglOleh.setText(aset.getTglOleh().split(" ")[0]);
                             inpNoSAP.setText(aset.getNomorSap());
                             inpNamaAset.setText(aset.getAsetName());
                             inpLuasAset.setText(String.valueOf(Double.parseDouble(String.valueOf(aset.getAsetLuas()))));
                             inpNilaiAsetSAP.setText(formatrupiah(Double.valueOf((aset.getNilaiOleh() != null) ? aset.getNilaiOleh() : 0 )));
                             inpMasaPenyusutan.setText(String.valueOf(aset.getMasaSusut()));
                             inpNomorBAST.setText(String.valueOf(aset.getNomorBast()));
                             inpNilaiResidu.setText(formatrupiah(Double.valueOf((aset.getNilaiResidu() != null) ? aset.getNilaiResidu() : 0 )));
                             inpKeterangan.setText(aset.getKeterangan());
                             inpUmrEkonomis.setText(utils.MonthToYear(aset.getUmurEkonomisInMonth()));
                             inpPersenKondisi.setText(String.valueOf(aset.getPersenKondisi()));
                             inpHGU.setText(String.valueOf(aset.getHgu()));


                             if(aset.getPopPohonSaatIni() != null && aset.getPopStandar() != null){
                                 inpPopTotalPohonSaatIni.setText(String.valueOf(aset.getPopPohonSaatIni()));
                                 inpPopTotalStdMaster.setText(String.valueOf(aset.getPopStandar()));
                             }

                             if(aset.getPopPerHa() != null && aset.getPresentasePopPerHa() != null){
                                 inpPopPerHA.setText(showPopulasiWithoutPercentage(String.valueOf(aset.getPopPerHa())));
                                 inpPresentasePopPerHA.setText(showPopulasi(String.valueOf(aset.getPresentasePopPerHa())));
                             }


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
                             String url1 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset1();
                             String url2 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset2();
                             String url3 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset3();
                             String url4 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset4();
                             String url5 = AsemApp.BASE_URL_ASSET+"/storage/app/public/aset/"+aset.getFotoAset5();

                             if (aset.getFotoAset1() == null ){
                                 map1.setEnabled(false);
                             } else {
                                 map1.setEnabled(true);
                                 fotoimg1.getLayoutParams().width = 200;
                                 fotoimg1.getLayoutParams().height = 200;
                                 Picasso.get().load(url1).resize(200,200).centerCrop().into(fotoimg1);
                             }

                             if (aset.getFotoAset2() == null ){
                                 map2.setEnabled(false);
                             } else {
                                 map2.setEnabled(true);
                                 fotoimg2.getLayoutParams().width = 200;
                                 fotoimg2.getLayoutParams().height = 200;
                                 Picasso.get().load(url2).resize(200,200).centerCrop().into(fotoimg2);
                             }

                             if (aset.getFotoAset3() == null ){
                                 map3.setEnabled(false);
                             } else {
                                 map3.setEnabled(true);
                                 fotoimg3.getLayoutParams().width = 200;
                                 fotoimg3.getLayoutParams().height = 200;
                                 Picasso.get().load(url3).resize(200,200).centerCrop().into(fotoimg3);
                             }

                             if (aset.getFotoAset4() == null ){
                                 map4.setEnabled(false);
                             } else {
                                 map4.setEnabled(true);
                                 fotoimg4.getLayoutParams().width = 200;
                                 fotoimg4.getLayoutParams().height = 200;
                                 Picasso.get().load(url4).resize(200,200).centerCrop().into(fotoimg4);
                             }

                             if (aset.getFotoAset5() == null ){
                                 map5.setEnabled(false);
                             } else {
                                 map5.setEnabled(true);
                                 fotoimg5.getLayoutParams().width = 200;
                                 fotoimg5.getLayoutParams().height = 200;
                                 Picasso.get().load(url5).resize(200,200).centerCrop().into(fotoimg5);
                             }

                             if (aset.getFotoAsetQr()!=null ){
                                 String url =  AsemApp.BASE_URL_ASSET+"/storage/app/public/qrcode-foto/"+aset.getFotoAsetQr();
                                 Log.d("amanat-url",url);
                                 fotoasetqr.getLayoutParams().width = 300;
                                 fotoasetqr.getLayoutParams().height = 300;
                                 Picasso.get().load(url).resize(300,300).centerCrop().into(fotoasetqr);
                             }
                             if (aset.getFotoAsetQr2()!=null ){
                                 String url =  AsemApp.BASE_URL_ASSET+"/storage/app/public/qrcode-foto/"+aset.getFotoAsetQr2();
                                 Log.d("amanat-url",url);
                                 fotoasetqr2.getLayoutParams().width = 300;
                                 fotoasetqr2.getLayoutParams().height = 300;
                                 Picasso.get().load(url).resize(300,300).centerCrop().into(fotoasetqr2);
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
//                spinnerKodeAset.setSelection(aset.getAsetKode()-1);
                             spinnerSubUnit.setSelection(aset.getAsetSubUnit()-1);

                             try {

                                 if (aset.getAfdelingId() != 0) {

                                     spinnerAfdeling.setSelection(mapAfdelingSpinner.get(aset.getAfdelingId()));

                                 }

                                 if (mapKodeSpinner.get(aset.getAsetKode()) != null) {

                                     spinnerKodeAset.setSelection(mapKodeSpinner.get(aset.getAsetKode()));

                                     Log.d("amanat12", String.valueOf(spinnerKodeAset.getSelectedItemId()));
                                     Log.d("amanat12", String.valueOf(mapKodeSpinner.get(aset.getAsetKode())));
                                 }
                             } catch (Exception e){
                             }
                             editVisibilityDynamic();


                         }

                         @Override
                         public void onFailure(Call<AsetModel> call, Throwable t) {
                             Handler handler = new Handler();
                             handler.postDelayed(new Runnable() {
                                 public void run() {
                                     dialog.dismiss();
                                 }
                             }, 1500);
                             Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                         }

                     }
        );
    }





    private void getKodeAset(){
        Call<AsetKodeModel2> call = asetInterface.getAsetKode();
        call.enqueue(new Callback<AsetKodeModel2>() {
            @Override
            public void onResponse(Call<AsetKodeModel2> call, Response<AsetKodeModel2> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<AsetKode2> asetKode = response.body().getData();
                List<String> listSpinner = new ArrayList<>();

                asetKode2 = asetKode;

                for ( AsetKode2 a : asetKode ){

                    String aset_kode_temp = "";

                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
//                    if ((a.getAsetJenis()+1) == 1 ) {
//                    } else if ((a.getAsetJenis()+1)  == 2) {
//                        aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
//                    } else {
//                        aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
//                    }

                    listSpinner.add(aset_kode_temp);

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKodeAset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AsetKodeModel2> call, Throwable t) {
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
                android.app.AlertDialog.Builder windowAlert = new android.app.AlertDialog.Builder(UpdateFotoQrAsetActivity.this);
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

    private void getAfdeling(){
        Call<AfdellingModel> call = asetInterface.getAfdeling();
        call.enqueue(new Callback<AfdellingModel>() {
            @Override
            public void onResponse(Call<AfdellingModel> call, Response<AfdellingModel> response) {

                if (!response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
//                    utils.Ngetoast(getApplicationContext(),);
                    return;
                }
                List<String> listSpinner = new ArrayList<>();
                listSpinner.add("Pilih Afdeling");
                afdeling = response.body().getData();

                for (int i=0;i<response.body().getData().size();i++){
//                    if (response.body().getData())
                    listSpinner.add(response.body().getData().get(i).getAfdelling_desc());

                }

                // Set hasil result json ke dalam adapter spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAfdeling.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AfdellingModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    private void getSubUnit(){
        Call<SubUnitModel> call = asetInterface.getSubUnit();
        call.enqueue(new Callback<SubUnitModel>() {
            @Override
            public void onResponse(Call<SubUnitModel> call, Response<SubUnitModel> response) {
                if (!response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<SubUnit> subUnit = response.body().getData();
                List<String> listSpinner = new ArrayList<>();

                for ( SubUnit a : subUnit ){
                    listSpinner.add(a.getSub_unit_desc());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubUnit.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<SubUnitModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    private void getUnit(){
        Call<UnitModel> call = asetInterface.getUnit();
        call.enqueue(new Callback<UnitModel>() {
            @Override
            public void onResponse(Call<UnitModel> call, Response<UnitModel> response) {
                if (!response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<Unit> unit = response.body().getData();
                List<String> listSpinner = new ArrayList<>();


                for ( Unit a : unit ){
                    listSpinner.add(a.getUnit_desc());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnit.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<UnitModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
    }


    private void setExifLocation(File fileImage,int list){
        try {
            getLastLocation(UpdateFotoQrAsetActivity.this,getApplicationContext());
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
                }else if (list == 5) {

                    geotag5 = url;
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

        HorizontalScrollView scrollPartition = findViewById(R.id.scrollPartition);
//        Toast.makeText(getApplicationContext(),String.valueOf(spinnerSubUnit.getSelectedItemId()),Toast.LENGTH_LONG).show();
        if (spinnerTipeAset.getSelectedItemId() != 0) {
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
        } else {
            inpNomorBAST.setVisibility(View.GONE);
            tvBast.setVisibility(View.GONE);
        }

        Log.d("jenis", String.valueOf("kayu".equals(String.valueOf(spinnerKodeAset.getSelectedItem()))));

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

            Log.d("tanam", String.valueOf(spinnerSistemTanam.getSelectedItem()));

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

//            if(spinnerKodeAset.getSelectedItemId() == 22
            if("ZC06/S001/Tebu".equals(String.valueOf(spinnerKodeAset.getSelectedItem()))
//                    && "Mono".equals(String.valueOf(spinnerSistemTanam.getSelectedItem()))
            ){
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

            if(!"Mono".equals(String.valueOf(spinnerSistemTanam.getSelectedItem()))){
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
            btnFileBAST.setVisibility(View.VISIBLE);
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

        if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            listBtnMap.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
//            inpKomoditi.setVisibility(View.VISIBLE);

            downloadBa.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.GONE);

            tvUploudBA.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            if ("baru".equals(String.valueOf(spinnerTipeAset.getSelectedItem())) ) {
                //input PDF ba bast
                btnFileBAST.setVisibility(View.VISIBLE);
                tvFileBAST.setVisibility(View.VISIBLE);
                tvUploadBAST.setVisibility(View.VISIBLE);
                downloadBAST.setVisibility(View.VISIBLE);
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

//            tvPopTotalSaatIni.setVisibility(View.VISIBLE);
//            tvPopTotalStandar.setVisibility(View.VISIBLE);
//            tvPopHektarSaatIni.setVisibility(View.VISIBLE);
//            tvPopHektarStandar.setVisibility(View.VISIBLE);
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
            spinnerLuasSatuan.setVisibility(View.GONE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);

            tvBa.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
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

//            tvPopTotalSaatIni.setVisibility(View.VISIBLE);
//            tvPopTotalStandar.setVisibility(View.VISIBLE);
//            tvPopHektarSaatIni.setVisibility(View.VISIBLE);
//            tvPopHektarStandar.setVisibility(View.VISIBLE);
//            inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//            inpPopTotalStdMaster.setVisibility(View.VISIBLE);
//            inpPopPerHA.setVisibility(View.VISIBLE);
//            inpPresentasePopPerHA.setVisibility(View.VISIBLE);

        }


        else if ("tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.GONE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

//            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            foto5rl.setVisibility(View.VISIBLE);
            map5.setVisibility(View.VISIBLE);

//            tvPopTotalSaatIni.setVisibility(View.VISIBLE);
//            tvPopTotalStandar.setVisibility(View.VISIBLE);
//            tvPopHektarSaatIni.setVisibility(View.VISIBLE);
//            tvPopHektarStandar.setVisibility(View.VISIBLE);
//            inpPopTotalPohonSaatIni.setVisibility(View.VISIBLE);
//            inpPopTotalStdMaster.setVisibility(View.VISIBLE);
//            inpPopPerHA.setVisibility(View.VISIBLE);
//            inpPresentasePopPerHA.setVisibility(View.VISIBLE);
        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            downloadBa.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            if ("baru".equals(String.valueOf(spinnerTipeAset.getSelectedItem())) ) {
                //input PDF ba bast
                btnFileBAST.setVisibility(View.VISIBLE);
                tvFileBAST.setVisibility(View.VISIBLE);
                tvUploadBAST.setVisibility(View.VISIBLE);
                downloadBAST.setVisibility(View.VISIBLE);
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

//            tvPopTotalSaatIni.setVisibility(View.GONE);
//            tvPopTotalStandar.setVisibility(View.GONE);
//            tvPopHektarSaatIni.setVisibility(View.GONE);
//            tvPopHektarStandar.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem()))  && "rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem())) ) {
            listBtnMap.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.VISIBLE);
//            inpJumlahPohon.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.VISIBLE);
            tvBa.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);

//            tvPohon.setVisibility(View.VISIBLE);

            spinnerLuasSatuan.setVisibility(View.GONE);
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

//            tvPopTotalSaatIni.setVisibility(View.GONE);
//            tvPopTotalStandar.setVisibility(View.GONE);
//            tvPopHektarSaatIni.setVisibility(View.GONE);
//            tvPopHektarStandar.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        }

        else if ("kayu".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.VISIBLE);
//            inpKomoditi.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.GONE);
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

            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);

//            tvLuasTanaman.setVisibility(View.VISIBLE);
            tvLuasNonTanaman.setVisibility(View.GONE);
//            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.GONE);
            tvPersenKondisi.setVisibility(View.GONE);

            foto5rl.setVisibility(View.GONE);
            map5.setVisibility(View.GONE);

//            tvPopTotalSaatIni.setVisibility(View.GONE);
//            tvPopTotalStandar.setVisibility(View.GONE);
//            tvPopHektarSaatIni.setVisibility(View.GONE);
//            tvPopHektarStandar.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "normal".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            inpBtnMap.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.GONE);
            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);

            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);

            if ("baru".equals(String.valueOf(spinnerTipeAset.getSelectedItem())) ) {
                //input PDF ba bast
                btnFileBAST.setVisibility(View.VISIBLE);
                tvFileBAST.setVisibility(View.VISIBLE);
                tvUploadBAST.setVisibility(View.VISIBLE);
                downloadBAST.setVisibility(View.VISIBLE);
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

//            tvPopTotalSaatIni.setVisibility(View.GONE);
//            tvPopTotalStandar.setVisibility(View.GONE);
//            tvPopHektarSaatIni.setVisibility(View.GONE);
//            tvPopHektarStandar.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);
        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) &&"rusak".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.VISIBLE);
//            tvPohon.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.VISIBLE);
            inpNomorBAST.setVisibility(View.VISIBLE);
            tvBast.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.VISIBLE);
            tvFoto.setVisibility(View.VISIBLE);
            scrollPartition.setVisibility(View.VISIBLE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);

            listBtnMap.setVisibility(View.GONE);
            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.VISIBLE);
            inpLuasAset.setVisibility(View.VISIBLE);

            inpPersenKondisi.setVisibility(View.VISIBLE);
            tvPersenKondisi.setVisibility(View.VISIBLE);

            foto5rl.setVisibility(View.GONE);
            map5.setVisibility(View.GONE);

//            tvPopTotalSaatIni.setVisibility(View.GONE);
//            tvPopTotalStandar.setVisibility(View.GONE);
//            tvPopHektarSaatIni.setVisibility(View.GONE);
//            tvPopHektarStandar.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        }

        else if ("non tanaman".equals(String.valueOf(spinnerJenisAset.getSelectedItem())) && "hilang".equals(String.valueOf(spinnerAsetKondisi.getSelectedItem()))) {
            tvBa.setVisibility(View.VISIBLE);
            btnFile.setVisibility(View.VISIBLE);
            tvUploudBA.setVisibility(View.VISIBLE);
            downloadBa.setVisibility(View.VISIBLE);
            spinnerLuasSatuan.setVisibility(View.VISIBLE);
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

//            tvPopTotalSaatIni.setVisibility(View.GONE);
//            tvPopTotalStandar.setVisibility(View.GONE);
//            tvPopHektarSaatIni.setVisibility(View.GONE);
//            tvPopHektarStandar.setVisibility(View.GONE);
//            inpPopTotalPohonSaatIni.setVisibility(View.GONE);
//            inpPopTotalStdMaster.setVisibility(View.GONE);
//            inpPopPerHA.setVisibility(View.GONE);
//            inpPresentasePopPerHA.setVisibility(View.GONE);

        } else {
            listBtnMap.setVisibility(View.GONE);
//            inpJumlahPohon.setVisibility(View.GONE);
            tvFoto.setVisibility(View.GONE);
            scrollPartition.setVisibility(View.GONE);
//            tvPohon.setVisibility(View.GONE);
            tvBa.setVisibility(View.GONE);
            tvUploudBA.setVisibility(View.GONE);
            inpBtnMap.setVisibility(View.GONE);
            btnFile.setVisibility(View.GONE);
            spinnerLuasSatuan.setVisibility(View.GONE);
            tvLuasTanaman.setVisibility(View.GONE);
            tvLuasNonTanaman.setVisibility(View.GONE);
            downloadBa.setVisibility(View.GONE);
            inpLuasAset.setVisibility(View.GONE);
            btnFileBAST.setVisibility(View.GONE);
            tvFileBAST.setVisibility(View.GONE);
            tvUploadBAST.setVisibility(View.GONE);
            downloadBAST.setVisibility(View.GONE);
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
    public void editAset(){
        dialog.show();

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
//
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

        try{

            String nama_aset = inpNamaAset.getText().toString().trim();
            String nomor_aset_sap = inpNoSAP.getText().toString().trim();
            String luas_aset = String.valueOf(Double.parseDouble((inpLuasAset.getText().toString().trim().equals("")) ? "0" : inpLuasAset.getText().toString().trim()));
            String nilai_aset = String.valueOf(CurrencyToNumber(inpNilaiAsetSAP.getText().toString().trim()));
            String tgl_oleh = inpTglOleh.getText().toString().trim();
            String masa_susut = inpMasaPenyusutan.getText().toString().trim();
            String nomor_bast = inpNomorBAST.getText().toString().trim();
            String nilai_residu = String.valueOf(CurrencyToNumber(inpNilaiResidu.getText().toString().trim()));
            String keterangan = inpKeterangan.getText().toString().trim();
            String asetId = String.valueOf(aset.getAsetId());

            MultipartBody.Part partBaFile = null, partBASTFile = null;


            RequestBody requestAsetId = RequestBody.create(MediaType.parse("text/plain"), asetId);
            RequestBody requestTipeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdTipeAsset));
            RequestBody requestJenisAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdJenisAset));
            RequestBody requestKondisiAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdAsetKondisi));
            RequestBody requestKodeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mapSpinnerkode.get(Integer.parseInt(String.valueOf(spinnerKodeAset.getSelectedItemId())))));
            RequestBody requestNamaAset = RequestBody.create(MediaType.parse("text/plain"), nama_aset);
            RequestBody requestNomorAsetSAP = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nomor_aset_sap));

            RequestBody requestGeoTag1 = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(geotag1));
            RequestBody requestGeoTag2 = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(geotag2));
            RequestBody requestGeoTag3 = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(geotag3));
            RequestBody requestGeoTag4 = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(geotag4));
            RequestBody requestGeoTag5 = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(geotag5));

            RequestBody requestLuasAset = RequestBody.create(MediaType.parse("text/plain"), luas_aset);
            RequestBody requestNilaiAsetSAP = RequestBody.create(MediaType.parse("text/plain"), nilai_aset);
            RequestBody requestTglOleh = RequestBody.create(MediaType.parse("text/plain"), tgl_oleh);
            RequestBody requestMasaSusut = RequestBody.create(MediaType.parse("text/plain"), masa_susut);
            RequestBody requestNomorBAST = RequestBody.create(MediaType.parse("text/plain"), nomor_bast);
            RequestBody requestNilaiResidu = RequestBody.create(MediaType.parse("text/plain"), nilai_residu);
            RequestBody requestKeterangan = RequestBody.create(MediaType.parse("text/plain"), keterangan);
            RequestBody requestSubUnit = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(aset.getAsetSubUnit()));

            RequestBody requestAfdeling = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(0));

            if (aset.getAsetSubUnit() == 2) {
                int afdelingId = (int) aset.getAfdelingId();
                if (afdelingId != 0){
                    requestAfdeling = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(afdelingId));
                } else {
                    requestAfdeling = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(0));
                }
            }

            RequestBody requestUnit = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Integer.parseInt(String.valueOf(spinnerUnit.getSelectedItemId())) +1));
            RequestBody requestHGU = RequestBody.create(MediaType.parse("text/plain"), inpHGU.getText().toString().trim());

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
            builder.addPart(MultipartBody.Part.createFormData("nilai_residu",null,requestNilaiResidu));
            builder.addPart(MultipartBody.Part.createFormData("nilai_oleh",null,requestNilaiAsetSAP));
            builder.addPart(MultipartBody.Part.createFormData("masa_susut",null,requestMasaSusut));
            builder.addPart(MultipartBody.Part.createFormData("keterangan",null,requestKeterangan));

            builder.addPart(MultipartBody.Part.createFormData("aset_sub_unit",null,requestSubUnit));
            builder.addPart(MultipartBody.Part.createFormData("nomor_bast",null,requestNomorBAST));
            builder.addPart(MultipartBody.Part.createFormData("afdeling_id",null,requestAfdeling));
            builder.addPart(MultipartBody.Part.createFormData("unit_id",null,requestUnit));
            builder.addPart(MultipartBody.Part.createFormData("hgu",null,requestHGU));


            Log.d("tebu", String.valueOf(spinnerKodeAset.getSelectedItem().equals("ZC06/S001/Tebu")));
//            if ((spinnerJenisAset.getSelectedItemId() == 1 && !spinnerKodeAset.getSelectedItem().equals("ZC06/S001/Tebu")) || spinnerJenisAset.getSelectedItemId() == 3 ) {
//                RequestBody requestPopulasiPohonSaatIni = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble(inpPopTotalPohonSaatIni.getText().toString().trim().equals("") ? "0" : inpPopTotalPohonSaatIni.getText().toString().trim() )));
//                RequestBody requestPopulasiStandar = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble(inpPopTotalStdMaster.getText().toString().trim().equals("") ? "0" : inpPopTotalStdMaster.getText().toString().trim())));
//                builder.addPart(MultipartBody.Part.createFormData("pop_pohon_saat_ini", null, requestPopulasiPohonSaatIni));
//                builder.addPart(MultipartBody.Part.createFormData("pop_standar", null, requestPopulasiStandar));
//
//                if (!inpPopPerHA.getText().toString().equals("")) {
//                    Double popPerHa =  Double.parseDouble((inpPopTotalPohonSaatIni.getText().toString() != null || !inpPopTotalPohonSaatIni.getText().toString() .equals("") ) ? String.valueOf(inpPopTotalPohonSaatIni.getText().toString()) : "0" ) / Double.parseDouble((inpLuasAset.getText().toString() != null || !inpLuasAset.getText().toString() .equals("") ) ? String.valueOf(inpLuasAset.getText().toString()) : "0");
//                    Double presentase = popPerHa / Double.parseDouble((inpPopTotalStdMaster.getText().toString() != null || inpPopTotalStdMaster.getText().toString().equals("") ) ? String.valueOf(inpPopTotalStdMaster.getText().toString()) : "0"  ) * 100;
//                    RequestBody requestPopulasiPerHA = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(popPerHa));
//                    RequestBody requestPresentasePopulasiPerHA = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(presentase));
//                    builder.addPart(MultipartBody.Part.createFormData("pop_per_ha", null, requestPopulasiPerHA));
//                    builder.addPart(MultipartBody.Part.createFormData("presentase_pop_per_ha", null, requestPresentasePopulasiPerHA));
//                }
//
//            }
//            if ((spinnerJenisAset.getSelectedItemId() == 1 && !spinnerKodeAset.getSelectedItem().equals("ZC06/S001/Tebu")) || spinnerJenisAset.getSelectedItemId() == 3 ){
//                RequestBody requestSistemTanam = RequestBody.create(MediaType.parse("text/plain"), spinnerSistemTanam.getSelectedItem().toString().trim());
//                builder.addPart(MultipartBody.Part.createFormData("sistem_tanam", null, requestSistemTanam));
//
//            }else if (spinnerKodeAset.getSelectedItem().equals("ZC06/S001/Tebu")){
//                RequestBody requestTanamMono = RequestBody.create(MediaType.parse("text/plain"), "Mono");
//                builder.addPart(MultipartBody.Part.createFormData("sistem_tanam",null,requestTanamMono));
//            }

            if (inpPopTotalPohonSaatIni != null){
                RequestBody requestPopulasiPohonSaatIni = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble((inpPopTotalPohonSaatIni.getText().toString().trim().equals("")) ? "0" : inpPopTotalPohonSaatIni.getText().toString().trim())));
                builder.addPart(MultipartBody.Part.createFormData("pop_pohon_saat_ini", null, requestPopulasiPohonSaatIni));
            }

            if (inpPopTotalStdMaster != null){
                RequestBody requestPopulasiStandar = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Double.parseDouble((inpPopTotalStdMaster.getText().toString().trim().equals("")) ? "0" : inpPopTotalStdMaster.getText().toString().trim())));
                builder.addPart(MultipartBody.Part.createFormData("pop_standar", null, requestPopulasiStandar));
            }

            if (!inpPopPerHA.getText().toString().isEmpty() || !inpPresentasePopPerHA.getText().toString().isEmpty()){
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

                RequestBody requestPopulasiPerHA = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(popPerHa));
                RequestBody requestPresentasePopulasiPerHA = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(presentase));
                builder.addPart(MultipartBody.Part.createFormData("pop_per_ha", null, requestPopulasiPerHA));
                builder.addPart(MultipartBody.Part.createFormData("presentase_pop_per_ha", null, requestPresentasePopulasiPerHA));
            }

            if (inpTahunTanam != null){
                RequestBody requestTahunTanam = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inpTahunTanam.getText().toString()));
                builder.addPart(MultipartBody.Part.createFormData("tahun_tanam", null, requestTahunTanam));
            }

//            if (spinnerJenisAset.getSelectedItemId() == 1 || spinnerJenisAset.getSelectedItemId() == 3){
//                RequestBody requestSistemTanam = RequestBody.create(MediaType.parse("text/plain"), spinnerSistemTanam.getSelectedItem().toString().trim());
//                builder.addPart(MultipartBody.Part.createFormData("sistem_tanam", null, requestSistemTanam));
//            }

            if (spinnerSistemTanam.getSelectedItem() != null){
                RequestBody requestSistemTanam = RequestBody.create(MediaType.parse("text/plain"), spinnerSistemTanam.getSelectedItem().toString().trim());
                builder.addPart(MultipartBody.Part.createFormData("sistem_tanam", null, requestSistemTanam));
            }
            
            if (bafile_file != null){
                RequestBody requestBaFile = RequestBody.create(MediaType.parse("multipart/form-file"), bafile_file);
                partBaFile = MultipartBody.Part.createFormData("ba_file", bafile_file.getName(), requestBaFile);
                builder.addPart(partBaFile);
            }
            if (file_bast != null){
                RequestBody requestBASTFile = RequestBody.create(MediaType.parse("multipart/form-file"), file_bast);
                partBASTFile = MultipartBody.Part.createFormData("file_bast", file_bast.getName(), requestBASTFile);
                builder.addPart(partBASTFile);
            }

            if (spinnerLuasSatuan.getSelectedItem() != null) {
                RequestBody requestSatuan = RequestBody.create(MediaType.parse("text/plain"), spinnerLuasSatuan.getSelectedItem().toString().trim());
                builder.addPart(MultipartBody.Part.createFormData("satuan_luas", null, requestSatuan));
            }

            if (spinnerKodeAset.getSelectedItem().equals("ZA08/-/Alat Pengangkutan")){
                RequestBody requestAlatAngkut = RequestBody.create(MediaType.parse("text/plain"), spinnerAlatAngkut.getSelectedItem().toString().trim());
                builder.addPart(MultipartBody.Part.createFormData("alat_angkut", null, requestAlatAngkut));
            }

            if (img1 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset1", img1.getName(), RequestBody.create(MediaType.parse("image/*"), img1)));
                builder.addPart(MultipartBody.Part.createFormData("geo_tag1",null,requestGeoTag1));
            }

            if (fotoAsetQrFile != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset_qr", fotoAsetQrFile.getName(), RequestBody.create(MediaType.parse("image/*"), fotoAsetQrFile)));
            }
            if (fotoAsetQrFile2 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset_qr_2", fotoAsetQrFile2.getName(), RequestBody.create(MediaType.parse("image/*"), fotoAsetQrFile2)));
            }

            if (img2 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset2",img2.getName(),RequestBody.create(MediaType.parse("image/*"),img2)));
                builder.addPart(MultipartBody.Part.createFormData("geo_tag2",null,requestGeoTag2));
            }

            if (img3 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset3",img3.getName(),RequestBody.create(MediaType.parse("image/*"),img3)));
                builder.addPart(MultipartBody.Part.createFormData("geo_tag3",null,requestGeoTag3));
            }

            if (img4 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset4",img4.getName(),RequestBody.create(MediaType.parse("image/*"),img4)));
                builder.addPart(MultipartBody.Part.createFormData("geo_tag4",null,requestGeoTag4));
            }

            if (img5 != null) {
                builder.addPart(MultipartBody.Part.createFormData("foto_aset5",img5.getName(),RequestBody.create(MediaType.parse("image/*"),img5)));
                builder.addPart(MultipartBody.Part.createFormData("geo_tag5",null,requestGeoTag5));
            }

            MultipartBody multipartBody = builder
                    .build();



            String contentType = "multipart/form-data; charset=utf-8; boundary=" + multipartBody.boundary();


            Call<AsetModel2> call = asetInterface.editAset(contentType,multipartBody);

            call.enqueue(new Callback<AsetModel2>(){

                @Override
                public void onResponse(Call<AsetModel2> call, Response<AsetModel2> response) {
                    if (response.isSuccessful() && response.body() != null){
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"succeed edit aset",Toast.LENGTH_LONG).show();
                        setValueInput();
                        startActivity(new Intent(UpdateFotoQrAsetActivity.this,LonglistAsetActivity.class));
                        finish();

                        return;
                    }if (response.code() >= 400 && response.code() < 500){
                        Toast.makeText(getApplicationContext(), "Nomor SAP sudah ada", Toast.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                    Log.d("asetapix",String.valueOf(response.errorBody()));
                    Log.d("asetapix",String.valueOf(call.request().body()));
                    Log.d("asetapix",String.valueOf(call.request().url()));
                    Log.d("asetapix",String.valueOf(response.code()));
                    return;
                }

                @Override
                public void onFailure(Call<AsetModel2> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"edit aset" + t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e ){
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"gagal edit aset "+e.getMessage(),Toast.LENGTH_LONG).show();
            Log.d("error-amant18",e.getMessage());
            e.printStackTrace();
        }




    }
    public void setAdapterAsetKode(){
        List<String> asetKode = new ArrayList<>();
        String aset_kode_temp;
        Integer i = 0;
        for (AsetKode2 a : asetKode2) {
            if (a.getAsetJenis()-1 == spinnerJenisAset.getSelectedItemId()) {
                Log.d("asetapix22",  "aset jenis dari spinner "+ String.valueOf(spinnerJenisAset.getSelectedItemId()));

                if ((a.getAsetJenis()) == 2 ) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
                } else if ((a.getAsetJenis()) == 1) {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                } else {
                    aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                }

                mapKodeSpinner.put(a.getAsetKodeId(),i);
                mapSpinnerkode.put(i,a.getAsetKodeId());

                Log.d("amanat17-kodespinner", String.valueOf(mapKodeSpinner.get(i)));

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
        afdelings.add("pilih afdeling");
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

                allSpinner = response.body().getData();

                DataAllSpinner dataAllSpinner = response.body().getData();
                List<String> listSpinnerTipe = new ArrayList<>();
                List<String> listSpinnerJenis = new ArrayList<>();
                List<String> listSpinnerKondisiAset = new ArrayList<>();
                List<String> listSpinnerKodeAset = new ArrayList<>();
                List<String> listSpinnerUnit = new ArrayList<>();
                List<String> listSpinnerSubUnit = new ArrayList<>();
                List<String> listSpinnerAfdeling = new ArrayList<>();
                listSpinnerAlatAngkut = new ArrayList<>();
                listSpinnerSistemTanam = new ArrayList<>();

                // get data tipe aset
                for (AsetTipe at : dataAllSpinner.getAsetTipe()){
                    listSpinnerTipe.add(at.getAset_tipe_desc());
                }

                // get alat angkut
                for (AlatAngkut at : dataAllSpinner.getAlatAngkut()){
                    listSpinnerAlatAngkut.add(at.getAp_desc());
                }

                // get sistem tanam
                for (SistemTanam at : dataAllSpinner.getSistemTanam()){
                    listSpinnerSistemTanam.add(at.getSt_desc());
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
                    sapAll.add(at);
                }

                // get afdeling
                afdeling = dataAllSpinner.getAfdeling();
                for (Afdelling at : dataAllSpinner.getAfdeling()){
                    listSpinnerUnit.add(at.getAfdelling_desc());
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

                // set adapter kode aset
                ArrayAdapter<String> adapterKodeAset = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinnerKodeAset);
                adapterKodeAset.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKodeAset.setAdapter(adapterKodeAset);

                // set adapter unit
                ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinnerUnit);
                adapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnit.setAdapter(adapterUnit);
                Integer unit_id = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
                spinnerUnit.setSelection(aset.getUnitId()-1);

                // set adapter alat angkut
                ArrayAdapter<String> adapterAlatAngkut = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinnerAlatAngkut);
                adapterAlatAngkut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAlatAngkut.setAdapter(adapterAlatAngkut);

                // set adapter sistem tanam
                ArrayAdapter<String> adapterSistemTanam = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinnerSistemTanam);
                adapterSistemTanam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSistemTanam.setAdapter(adapterSistemTanam);

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
            }

            @Override
            public void onFailure(Call<AllSpinner> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });

    }


    public void getSpinnerData(){
        getTipeAset();
        getKodeAset();
        getAsetJenis();
        getAsetKondisi();
        getAfdeling();
        getSubUnit();
        getUnit();
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

    private void setNilaiAsetSAP (String sap){
        for (Sap it : sapAll) {

            Log.d("sap",sap);
            Log.d("sap2",it.getSap_id()+ "=" + it.getNilai_oleh());

            if (it.getSap_desc().equals(sap)){

                Log.d("sapdes", String.valueOf(it.getNilai_oleh()));

                inpNilaiAsetSAP.setText(String.valueOf((it.getNilai_oleh() != null) ? it.getNilai_oleh() : 0 ));
                inpNilaiResidu.setText(String.valueOf((it.getNilai_residu() != null) ? it.getNilai_residu() : 0 ));
            }
        }
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

}