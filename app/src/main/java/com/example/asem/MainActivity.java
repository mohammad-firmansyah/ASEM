package com.example.asem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Aset;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

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

public class MainActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText;
    TextView tvUploudBA;
    private AsetInterface asetInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.inpTglMasukAset);
        tvUploudBA = findViewById(R.id.tvUploudBA);
//        initCalender();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://90cf-112-215-173-37.ap.ngrok.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        asetInterface = retrofit.create(AsetInterface.class);

        getAset();

       Button btnFile = findViewById(R.id.inpUploudBA);

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

    public  void initCalender(){
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
        Call<List<Aset>> call = asetInterface.getAset(1);
        call.enqueue(new Callback<List<Aset>>() {

            @Override
            public void onResponse(Call<List<Aset>> call, Response<List<Aset>> response) {
                Log.d("api",response.body().toString());
                Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<List<Aset>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}