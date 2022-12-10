package com.example.asem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilActivity extends AppCompatActivity {

    private static final String PREF_LOGIN = "LOGIN_PREF";

    TextView tvNIP,tvNama,tvUser,tvJabatan,tvBagian,tvEmail;
    CardView resetPass, logOut;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tvNIP = findViewById(R.id.tvNIP);
        tvNama = findViewById(R.id.tvNama);
        tvUser = findViewById(R.id.tvUser);
        tvJabatan = findViewById(R.id.tvJabatan);
        tvBagian = findViewById(R.id.tvBagian);
        tvEmail = findViewById(R.id.tvEmail);

        getUser();

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
    }

    private void getUser(){
        //fungsi retrofit getdata user
        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "-");
        String user_pass = sharedPreferences.getString("user_pass", "-");

        //tvNama.setText(getString("username"));

    }

    private void logout(){
        startActivity(new Intent(ProfilActivity.this, MainActivity.class));
        finish();
    }
}