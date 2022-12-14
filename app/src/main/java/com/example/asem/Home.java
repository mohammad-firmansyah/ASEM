package com.example.asem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    private static final String PREF_LOGIN = "LOGIN_PREF";
    SharedPreferences sharedPreferences;
    CardView cvHome;
    TextView tvWelcome, tvNama, tvUserPosisi, tvBagian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvBagian = findViewById(R.id.tvBagian);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvNama = findViewById(R.id.tvNama);
        tvUserPosisi = findViewById(R.id.tvUserPosisi);

        getHome();
    }

    public void getHome(){
        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String user_fullname = sharedPreferences.getString("nama", "-");
        String jabatan = sharedPreferences.getString("user_jabatan","-");

        tvNama.setText(user_fullname);
        tvUserPosisi.setText(jabatan);

    }
}