package ptpn12.amanat.asem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ptpn12.amanat.asem.R;

import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.Afdelling;
import ptpn12.amanat.asem.api.model.AlatAngkut;
import ptpn12.amanat.asem.api.model.AllSpinner;
import ptpn12.amanat.asem.api.model.AsetJenis;
import ptpn12.amanat.asem.api.model.AsetJenisModel;
import ptpn12.amanat.asem.api.model.AsetKode2;
import ptpn12.amanat.asem.api.model.AsetKodeModel2;
import ptpn12.amanat.asem.api.model.AsetKondisi;
import ptpn12.amanat.asem.api.model.AsetTipe;
import ptpn12.amanat.asem.api.model.DataAllSpinner;
import ptpn12.amanat.asem.api.model.ReportModel;

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
import ptpn12.amanat.asem.api.model.Sap;
import ptpn12.amanat.asem.api.model.SubUnit;
import ptpn12.amanat.asem.api.model.Unit;
import ptpn12.amanat.asem.api.model.UnitModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnDateSetListener date2;

    private static final String[] PERMISSIONS_LOCATION_AND_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int LOCATION_PERMISSION_AND_STORAGE = 33;

    Integer unit_id = 0;
    Integer user_id = 0;
    ListView listView;
    DataAllSpinner allSpinner;
    Map<Integer, Integer> mapAfdelingSpinner = new HashMap<Integer, Integer>();
    Map<Integer, Integer> mapSpinnerAfdeling = new HashMap<Integer, Integer>();
    Map<Integer, String> mapAfdeling = new HashMap();
    Map<Long, Integer> mapSap = new HashMap();

    Map<Integer,Integer> mapKodeSpinner = new HashMap();
    Map<Integer,Integer> mapSpinnerkode = new HashMap();

    List<String> listSpinnerSap=new ArrayList<>();
    List<AsetKode2> asetKode2 = new ArrayList<>();
    List<Afdelling> afdeling = new ArrayList<>();

    private AsetInterface asetInterface;
    final Calendar myCalendar1= Calendar.getInstance();
    final Calendar myCalendar2= Calendar.getInstance();
    Spinner spinnerTipeAset;
    SharedPreferences sharedPreferences;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    Spinner spinnerJenisAset;
    Spinner spinnerAsetKondisi;
    Spinner spinnerKodeAset;
    Spinner spinnerJenisReport;
    Spinner spinnerUnit;

    String spinnerIdTipeAsset;
    String spinnerIdJenisAset;
    String spinnerIdAsetKondisi;
    String spinnerIdKodeAset;
    String getSpinnerIdJenisReport;


    Button btnSubmit;
    ImageView imgBack;
    String urlReportv3;
    TextView tvUnit;
    RadioGroup radioGroup;
    EditText inpTglInput1;
    EditText inpTglInput2;
    EditText inpHGU;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        sharedPreferences = ReportActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);

        unit_id = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
        user_id = Integer.valueOf(sharedPreferences.getString("user_id", "0"));
        Log.d("amanat19", String.valueOf(unit_id));
        dialog = new Dialog(ReportActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        dialog.show();

        tvUnit = findViewById(R.id.tvUnit);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        spinnerJenisReport = findViewById(R.id.spinnerReport);
        spinnerTipeAset = findViewById(R.id.inpTipeAset);
        spinnerJenisAset = findViewById(R.id.inpJenisAset);
        spinnerAsetKondisi = findViewById(R.id.inpKndsAset);
        spinnerKodeAset = findViewById(R.id.inpKodeAset);
        inpTglInput1 = findViewById(R.id.inpTglInput);
        inpTglInput2 = findViewById(R.id.inpTglInput2);
        btnSubmit = findViewById(R.id.btnSubmit);
        radioGroup = findViewById(R.id.qrcode);
        imgBack = findViewById(R.id.imgBack);

        inpTglInput1 = findViewById(R.id.inpTglInput);
        inpTglInput2 = findViewById(R.id.inpTglInput2);
        inpHGU = findViewById(R.id.inpHGU);


        // globally
        TextView tvTitle = findViewById(R.id.tvTitle);
        //in your OnCreate() method
        tvTitle.setText("REPORT DATA");



        imgBack.setVisibility(View.GONE);



//        getAllSpinnerData();
        getSpinnerData();
        verifyStorageAndLocationPermissions(this);
        initCalender();
        initCalender2();

        btnSubmit.setOnClickListener(v -> apiDownloadReport());

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

        spinnerKodeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdKodeAset = String.valueOf(position);
                editVisibilityDynamic();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerJenisReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSpinnerIdJenisReport = String.valueOf(position);
                editVisibilityDynamic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        inpTglInput2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(ReportActivity.this,date,myCalendar2.get(Calendar.YEAR),myCalendar2.get(Calendar.MONTH),myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
//                initCalender2();
//                updateLabel2();
//            }
//        });

//        editVisibilityDynamic();
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
                listSpinner.add("Semua");

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
                listSpinner.add("Semua");
                for (int i=0;i<response.body().size();i++){
                    Log.d("asetapi2",response.body().get(i).getAset_kondisi_desc());
                    listSpinner.add(response.body().get(i).getAset_kondisi_desc());
                }

                // Set hasil result json ke dalam adapter spinner

                try{

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAsetKondisi.setAdapter(adapter);
                }
                catch(Exception e ) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<AsetKondisi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inpTglInput1.setText(dateFormat.format(myCalendar1.getTime()));
    }

    public void initCalender(){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH,month);
                myCalendar1.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        inpTglInput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReportActivity.this,date,myCalendar1.get(Calendar.YEAR),myCalendar1.get(Calendar.MONTH),myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel2(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inpTglInput2.setText(dateFormat.format(myCalendar2.getTime()));
    }

    public void initCalender2(){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH,month);
                myCalendar2.set(Calendar.DAY_OF_MONTH,day);
                updateLabel2();
            }
        };
        inpTglInput2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReportActivity.this,date,myCalendar2.get(Calendar.YEAR),myCalendar2.get(Calendar.MONTH),myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
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
                listSpinner.add("Semua");
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
                listSpinner.add("Semua");


                for ( AsetKode2 a : asetKode ){

                    String aset_kode_temp = "";
                    if (a.getAsetJenis() == 2 ) {
                        aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
                    } else if (a.getAsetJenis() == 1) {
                        aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                    } else {
                        aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                    }
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

    private void getUnit(){
        Call<UnitModel> call = asetInterface.getUnit();
        call.enqueue(new Callback<UnitModel>() {
            @Override
            public void onResponse(Call<UnitModel> call, Response<UnitModel> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<Unit> unit = response.body().getData();
                List<String> listSpinner = new ArrayList<>();
                listSpinner.add("Semua");


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



    private void getSpinnerData(){
        dialog.show();
        getAsetKondisi();
        getKodeAset();
        getTipeAset();
        getAsetJenis();
        getUnit();

//        set spinner jenis report
        List<String> listSpinner = new ArrayList<>();
        listSpinner.add("excel");
        listSpinner.add("pdf");
        // Set hasil result json ke dalam adapter spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisReport.setAdapter(adapter);

        dialog.dismiss();
    }

    public void getAllSpinnerData(){
        dialog.show();
        Call<AllSpinner> call = asetInterface.getAllSpinner();
        try {

            call.enqueue(new Callback<AllSpinner>() {
                @Override
                public void onResponse(Call<AllSpinner> call, Response<AllSpinner> response) {
                    try {


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

                        listSpinnerTipe.add("Semua");
                        listSpinnerJenis.add("Semua");
                        listSpinnerKondisiAset.add("Semua");
                        listSpinnerKodeAset.add("Semua");
                        listSpinnerUnit.add("Semua");

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

                        List<String> listSpinner = new ArrayList<>();
                        listSpinner.add("excel");
                        listSpinner.add("pdf");
                        // Set hasil result json ke dalam adapter spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerJenisReport.setAdapter(adapter);

                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    editVisibilityDynamic();
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<AllSpinner> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void downloadReport(String url){

        Log.d("amanat20-url",url);

        try {
            String title = URLUtil.guessFileName(url,null,null);
//        request.setTitle(title);
//        request.setDescription("Downloading File Please Wait.....");
//        String cookie = CookieManager.getInstance().getCookie(url);
//        request.addRequestHeader("cookie",cookie);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//
//        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//        downloadManager.enqueue(request);
//
//        Toast.makeText(this, "Berhasil Mengunduh", Toast.LENGTH_SHORT).show();



            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.allowScanningByMediaScanner();
            DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);

            Toast.makeText(this, "Download Dimulai" , Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            e.printStackTrace();
        }
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));


    }

    private void apiDownloadReport(){

        dialog.show();
        if (inpTglInput1.getText().toString().matches("")) {
            dialog.dismiss();
            inpTglInput1.setError("Tgl 1 harus diisi");
            inpTglInput1.requestFocus();
            return;
        }
        if (inpTglInput2.getText().toString().matches("")) {
            dialog.dismiss();
            inpTglInput2.setError("Tgl 2 harus diisi");
            inpTglInput2.requestFocus();
            return;
        }


        try{

            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            sharedPreferences = ReportActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

            urlReportv3 = AsemApp.BASE_URL + "report3?tipe_aset=" + String.valueOf(spinnerTipeAset.getSelectedItemId()) +
                    "&jenis_aset=" + String.valueOf(spinnerJenisAset.getSelectedItemId()) +
                    "&kondisi_aset=" + String.valueOf(spinnerAsetKondisi.getSelectedItemId()) +
                    "&kode_aset=" + String.valueOf(spinnerKodeAset.getSelectedItemId()) +
                    "&hak_akses_id=" + String.valueOf(sharedPreferences.getString("hak_akses_id", "-")) +
                    "&unit=" + String.valueOf(sharedPreferences.getString("unit_id", "-")) +
                    "&sub_unit=" + String.valueOf(sharedPreferences.getString("sub_unit_id","-")) +
                    "&afdeling=" + String.valueOf(sharedPreferences.getString("afdeling_id","-")) +
                    "&tgl_input1=" +String.valueOf(inpTglInput1.getText()) +
                    "&tgl_input2=" + String.valueOf(inpTglInput2.getText()) +
                    "&jenis_report=" + spinnerJenisReport.getSelectedItem() +
                    "&qrcode=" + radioButton.getText().toString();

//            urlReportv3 = AsemApp.BASE_URL + Uri.encode(query); untuk encode url by android

            RequestBody requestTipeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdTipeAsset));
            RequestBody requestJenisAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdJenisAset));
            RequestBody requestKondisiAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdAsetKondisi));
            RequestBody requestKodeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdKodeAset));
            RequestBody requestJenisReport = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerJenisReport.getSelectedItem().toString()));
            RequestBody requestTglInput1 = RequestBody.create(MediaType.parse("text/plain"), inpTglInput1.getText()+" 00:00:00");
            RequestBody requestTglInput2 = RequestBody.create(MediaType.parse("text/plain"), inpTglInput2.getText()+" 00:00:00");




            RequestBody requestUnit = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(unit_id));
            RequestBody requestUnitKasi = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerUnit.getSelectedItemId()));
            RequestBody requestUserId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(user_id));
            RequestBody requestHGU = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inpHGU.getText().toString().trim()));



            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.addPart(MultipartBody.Part.createFormData("aset_tipe",null,requestTipeAset));
            builder.addPart(MultipartBody.Part.createFormData("aset_jenis",null,requestJenisAset));
            builder.addPart(MultipartBody.Part.createFormData("aset_kondisi",null,requestKondisiAset));
            builder.addPart(MultipartBody.Part.createFormData("jenis_report",null,requestJenisReport));
            builder.addPart(MultipartBody.Part.createFormData("aset_kode",null,requestKodeAset));
            builder.addPart(MultipartBody.Part.createFormData("tgl_input1",null,requestTglInput1));
            builder.addPart(MultipartBody.Part.createFormData("tgl_input2",null,requestTglInput2));
            builder.addPart(MultipartBody.Part.createFormData("unit_id",null,requestUnit));
            builder.addPart(MultipartBody.Part.createFormData("user_id",null,requestUserId));
            builder.addPart(MultipartBody.Part.createFormData("unit_id_kasi",null,requestUnitKasi));
//            builder.addPart(MultipartBody.Part.createFormData("hgu",null,requestHGU));

            if ("QRcode".equals(radioButton.getText().toString())) {
                RequestBody requestQrCode = RequestBody.create(MediaType.parse("text/plain"), "true");
                builder.addPart(MultipartBody.Part.createFormData("qrcode",null,requestQrCode));
            } else if("Foto+QRcode".equals(radioButton.getText().toString())) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlReportv3));
                startActivity(browserIntent);
                dialog.dismiss();
                return;
            } else {
                RequestBody requestQrCode = RequestBody.create(MediaType.parse("text/plain"), "false");
                builder.addPart(MultipartBody.Part.createFormData("qrcode",null,requestQrCode));
            }

            MultipartBody multipartBody = builder
                    .build();
            String contentType = "multipart/form-data; charset=utf-8; boundary=" + multipartBody.boundary();


            Call<ReportModel> call = asetInterface.downloadReport(contentType,multipartBody);

            call.enqueue(new Callback<ReportModel>(){

                @Override
                public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
                    if (response.isSuccessful() && response.body() != null){
                        dialog.dismiss();
                        downloadReport(AsemApp.BASE_URL_ASSET +"/"+response.body().getData());
                        return;
                    }

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"error " + response.code(),Toast.LENGTH_LONG).show();
                    return;
                }

                @Override
                public void onFailure(Call<ReportModel> call, Throwable t) {

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"error " + t.getMessage(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Data SAP Tidak Ditemukan.\n" ,Toast.LENGTH_SHORT).show();
                }
            });
//        Call<ReportModel> ApiDownloadReport = asetInterface.downloadReport()
    } catch (Exception e) {
            e.printStackTrace();
        }

}

public void editVisibilityDynamic(){

        TextView tvHgu = findViewById(R.id.tvHGU);
        tvHgu.setVisibility(View.GONE);


        if (unit_id == 20) {
            spinnerUnit.setVisibility(View.VISIBLE);
            tvUnit.setVisibility(View.VISIBLE);
        } else {
            spinnerUnit.setVisibility(View.GONE);
            tvUnit.setVisibility(View.GONE);
        }
//
//        if (spinnerJenisAset.getSelectedItemId() != 0 || spinnerKodeAset.getSelectedItemId() != 0 || spinnerAsetKondisi.getSelectedItemId() != 0 || spinnerTipeAset.getSelectedItemId() != 0) {
//           inpHGU.setEnabled(false);
//           tvHgu.setEnabled(false);
//
////           inpTglInput1.setEnabled(false);
////           inpTglInput2.setEnabled(false);
//        }
    }
}