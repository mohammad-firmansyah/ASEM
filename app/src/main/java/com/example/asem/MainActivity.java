package com.example.asem;

import static android.app.PendingIntent.getActivity;
import static com.example.asem.LonglistAsetActivity.baseUrl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Login;
import com.example.asem.api.model.LoginModel;
import com.google.gson.JsonObject;

//import butterknife.BindView;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //AlertDialog loading;
    //GlobalFunction global = new GlobalFunction();
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String LOGIN_CREDENTIALS = "LOGIN_CREDENTIALS";
    private static final String TAG = "LoginTAG";
    SharedPreferences sharedPreferences;
    private Dialog dialog;
    private AsetInterface asetInterface;

    Button btnLogin;
    EditText etNIP;
    EditText etPass;

    String username, user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(MainActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        etNIP = findViewById(R.id.etNIP);
        etPass = findViewById(R.id.etPassword);

        //String username = sharedPreferences.getString("username", "-");
//        Log.d("tessp", username);


        btnLogin = findViewById(R.id.btnLogin);
/*
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LonglistAsetActivity.class));
            }
        });
*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        asetInterface = retrofit.create(AsetInterface.class);

        btnLogin.setOnClickListener(v -> loginProses());

    }


    public void loginProses(){
        username = etNIP.getText().toString();
        user_pass = etPass.getText().toString();

        if(username.isEmpty()){
            etNIP.setError("Username wajib diisi!");
            etNIP.requestFocus();
        }
        if(user_pass.isEmpty()){
            etPass.setError("Password wajib diisi!");
            etPass.requestFocus();
        }else{
//            startActivity(new Intent(MainActivity.this,LonglistAsetActivity.class));
            dialog.show();

            Call<LoginModel> call = asetInterface.login(username,user_pass);    
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.isSuccessful() && response.body() != null){
                        if (response.body().getSuccess()){
                            Login login = response.body().getData();
                            Log.d(TAG, "onResponse: teslog : "+login.getUsername());
                            SharedPreferences.Editor editor = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();
                            //editor.putString(LOGIN_CREDENTIALS, response.body().getToken());
                            sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
                            String username = sharedPreferences.getString("username", "-");
                            String user_pass = sharedPreferences.getString("user_pass", "-");
                            String user_email = sharedPreferences.getString("user_email", "-");
                            String user_nip = sharedPreferences.getString("user_nip", "-");
                            String user_fullname = sharedPreferences.getString("user_fullname", "-");
                            String user_jabatan = sharedPreferences.getString("user_jabatan", "-");
                            String user_id = sharedPreferences.getString("user_id", "-");
                            String hak_akses_id = sharedPreferences.getString("hak_akses_id", "-");
                            String unit_id = sharedPreferences.getString("unit_id", "-");

                            editor.putString("username", username);
                            editor.putString("user_nip", String.valueOf(login.getUserNip()));
                            editor.putString("user_id", String.valueOf(login.getUserId()));
                            //editor.putString("user_id", user_id);
                            editor.putString("user_email", String.valueOf(login.getUserEmail()));
                            editor.putString("nama", String.valueOf(login.getUserFullname()));
                            editor.putString("user_jabatan", String.valueOf(login.getUserJabatan()));
                            editor.putString("unit_id", String.valueOf(login.getUnitId()));
                            editor.putString("hak_akses_id", String.valueOf(login.getHakAksesId()));
                            editor.putString("unit_desc", String.valueOf(login.getUnitDesc()));
                            editor.putString("hak_akses_desc", String.valueOf(login.getHakAksesDesc()));
                            editor.putString("sub_unit_id", String.valueOf(login.getSubUnitId()));
                            if (login.getSubUnitId() == 2) {

                                editor.putString("afdeling_id", String.valueOf(login.getAfdelingId()));
                            }  else  {

                                editor.putString("afdeling_id", String.valueOf(0));
                            }
                            editor.putBoolean("onSyncDone", false); // saat login awal dia kondisi belum sync
                            editor.apply();
                            //startActivity(new Intent(MainActivity.this, LonglistAsetActivity.class));
                            startActivity(new Intent(MainActivity.this, Home.class));
                        }else{
                            dialog.dismiss();
                            Log.d("taglogin", "onResponse: Login bre");
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();}
                    }else{
                        dialog.dismiss();
                        if (response.toString().contains("code=500")){
                            Toast.makeText(MainActivity.this,"Username atau Password anda salah", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this,response.toString(), Toast.LENGTH_SHORT).show();
                        }
                }}

                @Override
                public void onFailure( @NonNull Call<LoginModel> call, @NonNull Throwable t) {
                        Log.d("fail", "onFailure: "+t);
                        Toast.makeText(MainActivity.this, "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Login Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
            });

        } dialog.dismiss();
    }

//    public void onBackPressed(){
//        AlertDialog.Builder builder =new AlertDialog.Builder(this);
//        builder.setTitle("Keluar");
//        builder.setMessage("Apakah Anda Yakin Ingin Keluar?")
//                .setPositiveButton("Iya", (dialog, id) -> finishAffinity() )
//                .setNegativeButton("Tidak",(dialog, id) -> dialog.cancel());
//        AlertDialog alertDialog = builder.create();
//        alertDialog.setCanceledOnTouchOutside(true);
//        alertDialog.show();
//    }




        /**
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
    }*/

}