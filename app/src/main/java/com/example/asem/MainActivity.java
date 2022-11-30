package com.example.asem;

import static android.app.PendingIntent.getActivity;
import static com.example.asem.utils.utils.CurrencyToNumber;
import static com.example.asem.utils.utils.latitudeValue;
import static com.example.asem.utils.utils.longitudeValue;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asem.adapter.AsetAdapter;
import com.example.asem.api.AsetInterface;
import com.example.asem.api.MyErrorMessage;
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
import com.example.asem.api.model.Data;
import com.example.asem.api.model.ListAsetModel;
import com.example.asem.utils.GpsConverter;
import com.example.asem.utils.utils;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnReport;
    Button btnFilter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rcAset = findViewById(R.id.asetAll);
        rcAset.setHasFixedSize(true);
        rcAset.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.addAset);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255, 50, 50)));
        Data[] allData = new Data[]{
        new Data(1,"dsa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
        new Data(2,"as",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
        new Data(3,"dsasa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
        new Data(4,"dsasa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
        new Data(5,"dsasa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22),
        new Data(6,"dsasa",1,1,1,1,1,21212,"fotoaset1","fotoaset2","fotoaset3","foto4","geo2","geo3","geo4","geotag4",12000,"12/12/12 00:00:00","12/12/12 00:00:00",120000,210000,"212212","2","keterangan","foorqr","112121","asdasd",1,1,1,21, "12/12/12 00:00:00","12/12/12 00:00:00","dssdja",22)
        };

        btnReport = findViewById(R.id.btnReport);
        btnFilter = findViewById(R.id.btnFilter);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "masuk filter", Toast.LENGTH_SHORT).show();
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ReportActivity.class));

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddAsetActivity.class));
            }
        });

        AsetAdapter adapter = new AsetAdapter(allData,MainActivity.this);
        Log.d("barusantag",String.valueOf(adapter.getItemCount()));
        rcAset.setAdapter(adapter);
    }
}