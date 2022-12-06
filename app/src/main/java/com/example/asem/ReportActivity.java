package com.example.asem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.NetworkErrorException;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.AsetJenisModel;
import com.example.asem.api.model.AsetKode;
import com.example.asem.api.model.AsetKode2;
import com.example.asem.api.model.AsetKodeModel;
import com.example.asem.api.model.AsetKodeModel2;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetModel;
import com.example.asem.api.model.AsetTipe;
import com.example.asem.api.model.Report;
import com.example.asem.api.model.ReportModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {
    public static String baseUrl = "http://202.148.9.226:7710/mnj_aset_repo/public/api/";
    public static String baseUrlAset = "http://202.148.9.226:7710/mnj_aset_repo/public";
    private AsetInterface asetInterface;
    final Calendar myCalendar= Calendar.getInstance();
    Spinner spinnerTipeAset;
    Spinner spinnerJenisAset;
    Spinner spinnerAsetKondisi;
    Spinner spinnerKodeAset;
    Spinner spinnerJenisReport;

    String spinnerIdTipeAsset;
    String spinnerIdJenisAset;
    String spinnerIdAsetKondisi;
    String spinnerIdKodeAset;
    String getSpinnerIdJenisReport;


    Button btnSubmit;
    ImageView imgBack;

    RadioGroup radioGroup;
    EditText inpTglInput1;
    EditText inpTglInput2;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dialog = new Dialog(ReportActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        dialog.show();

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





        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        imgBack.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        asetInterface = retrofit.create(AsetInterface.class);


        getSpinnerData();
        initCalender();

        btnSubmit.setOnClickListener(v -> apiDownloadReport());

        spinnerTipeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdTipeAsset = String.valueOf(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerJenisAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdJenisAset = String.valueOf(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerAsetKondisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdAsetKondisi = String.valueOf(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerKodeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdKodeAset = String.valueOf(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerJenisReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSpinnerIdJenisReport = String.valueOf(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

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

    public void initCalender(){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
//                updateLabel1();
            }
        };
        inpTglInput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReportActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                updateLabel1();
            }
        });
        inpTglInput2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReportActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                updateLabel2();
            }
        });
    }

    private void updateLabel1(){
        String myFormat="yyyy/MM/dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inpTglInput1.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateLabel2(){
        String myFormat="yyyy/MM/dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inpTglInput2.setText(dateFormat.format(myCalendar.getTime()));
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


    private void getSpinnerData(){
        getAsetKondisi();
        getKodeAset();
        getTipeAset();
        getAsetJenis();

//        set spinner jenis report
        List<String> listSpinner = new ArrayList<>();
        listSpinner.add("excel");
        listSpinner.add("pdf");
        // Set hasil result json ke dalam adapter spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisReport.setAdapter(adapter);

    }


    private void downloadReport(String url){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        String title = URLUtil.guessFileName(url,null,null);
        request.setTitle(title);
        request.setDescription("Downloading File Please Wait.....");
        String cookie = CookieManager.getInstance().getCookie(url);
        request.addRequestHeader("cookie",cookie);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

        Toast.makeText(this, "Downloading Started", Toast.LENGTH_SHORT).show();

    }

    private void apiDownloadReport(){

        dialog.show();
        if (inpTglInput1.getText().toString().matches("")) {
            dialog.hide();
            inpTglInput1.setError("Tgl 1 harus diisi");
            inpTglInput1.requestFocus();
            return;
        }
        if (inpTglInput2.getText().toString().matches("")) {
            dialog.hide();
            inpTglInput2.setError("Tgl 2 harus diisi");
            inpTglInput2.requestFocus();
            return;
        }


        try{

            RequestBody requestTipeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdTipeAsset));
            RequestBody requestJenisAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdJenisAset));
            RequestBody requestKondisiAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdAsetKondisi));
            RequestBody requestKodeAset = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerIdKodeAset));
            RequestBody requestJenisReport = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(spinnerJenisReport.getSelectedItem().toString()));
            RequestBody requestTglInput1 = RequestBody.create(MediaType.parse("text/plain"), inpTglInput1.getText()+" 00:00:00");
            RequestBody requestTglInput2 = RequestBody.create(MediaType.parse("text/plain"), inpTglInput2.getText()+" 00:00:00");
            Log.d("asetapix",String.valueOf(spinnerJenisReport.getSelectedItem().toString()));



            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.addPart(MultipartBody.Part.createFormData("aset_tipe",null,requestTipeAset));
            builder.addPart(MultipartBody.Part.createFormData("aset_jenis",null,requestJenisAset));
            builder.addPart(MultipartBody.Part.createFormData("aset_kondisi",null,requestKondisiAset));
            builder.addPart(MultipartBody.Part.createFormData("jenis_report",null,requestJenisReport));
            builder.addPart(MultipartBody.Part.createFormData("aset_kode",null,requestKodeAset));
            builder.addPart(MultipartBody.Part.createFormData("tgl_input1",null,requestTglInput1));
            builder.addPart(MultipartBody.Part.createFormData("tgl_input2",null,requestTglInput2));
            Log.d("asetapix",String.valueOf(inpTglInput1.getText()));

            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);

            if ("ya".equals(radioButton.getText().toString())) {
                RequestBody requestQrCode = RequestBody.create(MediaType.parse("text/plain"), "true");
                builder.addPart(MultipartBody.Part.createFormData("qrcode",null,requestQrCode));

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
                        dialog.hide();
                        Log.d("asetData",String.valueOf(response.body().getData()));
                        if ("laporan.pdf".equals(String.valueOf(response.body().getData()))) {

                            downloadReport(baseUrlAset + "/" + String.valueOf(response.body().getData()));

                            Log.d("asetdua",baseUrlAset + "/" + String.valueOf(response.body().getData()));
                        } else {
                            downloadReport(baseUrl+String.valueOf(response.body().getData()));
                        }
                        Log.d("asetdua",baseUrlAset + "/" + String.valueOf(response.body().getData()));
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                        return;
                    }

                    dialog.hide();

                    Log.d("asetapix",String.valueOf(response.errorBody()));
                    Log.d("asetapix",String.valueOf(call.request().body()));
                    Log.d("asetapix",String.valueOf(call.request().url()));
                    Log.d("asetapix",String.valueOf(response.code()));
//                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<ReportModel> call, Throwable t) {
                    dialog.hide();
                    Log.d("asetapix", "onError : "+t.getMessage());
                    Log.d("asetapix",String.valueOf(call.request().body()));
                    Log.d("asetapix",String.valueOf(call.request().url()));
                    Log.d("asetapix",String.valueOf(call.request().method()));
                    Toast.makeText(getApplicationContext(),"error " + t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
//        Call<ReportModel> ApiDownloadReport = asetInterface.downloadReport()
    } catch (Exception e) {
            e.printStackTrace();
        }

}

}