package com.example.asem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.AsetJenisModel;
import com.example.asem.api.model.AsetKode;
import com.example.asem.api.model.AsetKodeModel;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetTipe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {
    public static String baseUrl = "http://202.148.9.226:9910/aset_mnj_repo/public/api/";
    public String baseUrlImg = "http://202.148.9.226:9910/aset_mnj_repo/public";
    private AsetInterface asetInterface;
    final Calendar myCalendar= Calendar.getInstance();
    Spinner spinnerTipeAset;
    Spinner spinnerJenisAset;
    Spinner spinnerAsetKondisi;
    Spinner spinnerKodeAset;
    Spinner spinnerJenisReport;

    EditText inpTglInput1;
    EditText inpTglInput2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        spinnerJenisReport = findViewById(R.id.spinnerReport);
        inpTglInput1 = findViewById(R.id.inpTglInput);
        inpTglInput2 = findViewById(R.id.inpTglInput2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        asetInterface = retrofit.create(AsetInterface.class);


        getSpinnerData();
        initCalender();

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
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inpTglInput1.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateLabel2(){
        String myFormat="yyyy-MM-dd";
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


    private void getSpinnerData(){
        getAsetKondisi();
        getKodeAset();
        getTipeAset();
        getAsetJenis();

//        set spinner jenis report
        List<String> listSpinner = new ArrayList<>();
        listSpinner.add("EXCEL");
        listSpinner.add("PDF");
        // Set hasil result json ke dalam adapter spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisReport.setAdapter(adapter);

    }

}