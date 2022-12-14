package com.example.asem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asem.api.AsetInterface;
import com.example.asem.api.model.Login;
import com.example.asem.api.model.LoginModel;

import java.util.List;
import java.util.PrimitiveIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {

    private static final String PREF_LOGIN = "LOGIN_PREF";

    TextView tvNIP,tvNama,tvUserPosisi,tvJabatan,tvBagian,tvEmail;
    CardView resetPass, logOut;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tvNIP = findViewById(R.id.tvNIP);
        tvNama = findViewById(R.id.tvNama);
        tvUserPosisi = findViewById(R.id.tvUserPosisi);
        tvJabatan = findViewById(R.id.tvJabatan);
        tvBagian = findViewById(R.id.tvBagian);
        tvEmail = findViewById(R.id.tvEmail);

        resetPass = findViewById(R.id.resetPass);
        logOut = findViewById(R.id.logOut);
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfilActivity.this, "Fitur belum berfungsi", Toast.LENGTH_SHORT).show();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        getUser();
    }

    private void getUser(){

        sharedPreferences = getSharedPreferences(PREF_LOGIN,MODE_PRIVATE);
        String user_fullname = sharedPreferences.getString("nama", "-");
        String jabatan = sharedPreferences.getString("user_jabatan","-");
        String nip = sharedPreferences.getString("user_nip", "-");
        String email = sharedPreferences.getString("user_email", "-");

        //Log.d("asetapix",user_fullname);
        tvNama.setText(user_fullname);
        tvJabatan.setText(jabatan);
        tvNIP.setText(nip);
        tvEmail.setText(email);
    }

    //fungsi layout menu

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Keluar");
        builder.setMessage("Apa Anda Yakin Ingin Logout?")
                .setPositiveButton("Iya", (dialog, id) -> finishAffinity())
                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
        startActivity(new Intent(ProfilActivity.this, MainActivity.class));
        finish();
    }
}