package com.example.asem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    TextView tvUploudBA;
    AsetModel asetModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                .baseUrl("https://ef7c-202-148-12-130.ap.ngrok.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        asetInterface = retrofit.create(AsetInterface.class);

        initCalender();
        Button btnFile = findViewById(R.id.inpUploudBA);
        getTipeAset();
        getAsetJenis();
        getAsetKondisi();
        getTglInput();
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

            switch (requestCode) {
                case 102:
                    String d = path;
                    Toast.makeText(getApplicationContext(),d,Toast.LENGTH_SHORT).show();

                    tvUploudBA.setText(d);
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
                        AsetModel m = response.body();
                        Toast.makeText(getApplicationContext(), String.valueOf(asetModel.getData().getAset_tipe()),Toast.LENGTH_SHORT).show();
                        spinnerJenisAset.setSelection(m.getData().getAset_tipe()-1);
                    }

                    @Override
                    public void onFailure(Call<AsetModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                        return;
                    }
                });
            }

            @Override
            public void onFailure(Call<AsetJenisModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
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

                        if (!response.isSuccessful()){
                            //                    tvResult.setText("Code: "+response.code());
                            Toast.makeText(getApplicationContext(),"ga bisa",Toast.LENGTH_SHORT);
                            return;
                        }
                        AsetModel m = response.body();
                        Log.d("apiaset", String.valueOf(m.getData().getAset_tipe()));
                        asetModel = m;
                        Toast.makeText(getApplicationContext(), String.valueOf(asetModel.getData().getAset_tipe()),Toast.LENGTH_SHORT).show();
                        spinnerTipeAset.setSelection(asetModel.getData().getAset_tipe()-1);
                    }

                    @Override
                    public void onFailure(Call<AsetModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                        return;
                    }
                });

            }

            @Override
            public void onFailure(Call<List<AsetTipe>> call, Throwable t) {
                Log.d("apitipeaset",t.getMessage());
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

                        if (!response.isSuccessful()){
                            //                    tvResult.setText("Code: "+response.code());
                            Toast.makeText(getApplicationContext(),"ga bisa",Toast.LENGTH_SHORT);
                            return;
                        }
                        AsetModel m = response.body();
                        asetModel = m;
//                        Toast.makeText(getApplicationContext(), String.valueOf(asetModel.getData().getAset_tipe()),Toast.LENGTH_SHORT).show();
                        spinnerAsetKondisi.setSelection(asetModel.getData().getAset_kondisi()-1);
                    }

                    @Override
                    public void onFailure(Call<AsetModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                        return;
                    }
                });

            }

            @Override
            public void onFailure(Call<List<AsetKondisi>> call, Throwable t) {
                Log.d("apitipeaset",t.getMessage());
            }
        });
    }
    private void getTglInput(){
        Call<AsetModel> call = asetInterface.getAset(1);
        call.enqueue(new Callback<AsetModel>() {

            @Override
            public void onResponse(Call<AsetModel> call, Response<AsetModel> response) {

//                if (!response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
//                    return;
//                }

                inpTglInput.setText(response.body().getData().getTgl_input().split(" ")[0]);
                inpNoSAP.setText(String.valueOf(response.body().getData().getNomor_sap()));
                inpNamaAset.setText(response.body().getData().getAset_name());
                inpLuasAset.setText(String.valueOf(response.body().getData().getAset_luas()));
                inpNilaiAsetSAP.setText(String.valueOf(response.body().getData().getNilai_sap()));
                inpMasaPenyusutan.setText(String.valueOf(response.body().getData().getMasa_susut()));
                inpNomorBAST.setText(String.valueOf(response.body().getData().getNomor_bast()));
                inpNilaiResidu.setText(formatrupiah(Double.parseDouble(String.valueOf(response.body().getData().getNilai_residu()))));
                inpKeterangan.setText(response.body().getData().getKeterangan());
                inpUmrEkonomis.setText(String.valueOf(response.body().getData().getUmur_ekonomis_in_month()));
                inpNilaiAsetSAP.setText(formatrupiah(Double.parseDouble(String.valueOf(response.body().getData().getUmur_ekonomis_in_month()))));
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
                        Toast.makeText(getApplicationContext(), String.valueOf(asetModel.getData().getAset_tipe()),Toast.LENGTH_SHORT).show();
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



}