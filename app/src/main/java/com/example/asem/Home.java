package com.example.asem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    private static final String PREF_LOGIN = "LOGIN_PREF";
    SharedPreferences sharedPreferences;
    CardView cvHome;
    TextView tvWelcome, tvNama, tvUserPosisi, tvBagian;
    MenuItem nav_beranda, nav_profil, nav_longlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvBagian = findViewById(R.id.tvBagian);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvNama = findViewById(R.id.tvNama);
        tvUserPosisi = findViewById(R.id.tvUserPosisi);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);

        bottomNavigationView.setSelectedItemId(R.id.nav_beranda);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_longlist:
                        startActivity(new Intent(getApplicationContext(),LonglistAsetActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_beranda:
                        return true;
                    case R.id.nav_profil:
                        startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        getHome();
    }

    public void getHome(){
        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        String user_fullname = sharedPreferences.getString("nama", "-");
        String jabatan = sharedPreferences.getString("user_jabatan","-");

        tvNama.setText(user_fullname);
        tvUserPosisi.setText(jabatan);

    }

    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Keluar");
//        builder.setMessage("Apa anda yakin keluar aplikasi?")
//                .setPositiveButton("Iya", (dialog, id) ->finishAffinity())
//                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
//        AlertDialog alert = builder.create();
//        alert.setCanceledOnTouchOutside(true);
//        alert.show();
        startActivity(new Intent(Home.this,ProfilActivity.class));
        finish();
    }
}