package ptpn12.amanat.asem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import ptpn12.amanat.asem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    private static final String PREF_LOGIN = "LOGIN_PREF";
    SharedPreferences sharedPreferences;
    CardView cvHome;
    TextView tvWelcome, tvNama, tvHakAkses, tvBagian;
    MenuItem nav_beranda, nav_profil, nav_longlist;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        dialog = new Dialog(Home.this,R.style.MyAlertDialogTheme);
//        dialog.setContentView(R.layout.loading);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        dialog.show();

        tvBagian = findViewById(R.id.tvBagian);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvNama = findViewById(R.id.tvNama);
        tvHakAkses = findViewById(R.id.tvHakAkses);

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
        String hak_akses  = sharedPreferences.getString("hak_akses_desc","-");
        String bagian = sharedPreferences.getString("unit_desc","-");

        tvNama.setText(user_fullname);
        tvHakAkses.setText(hak_akses);
        tvBagian.setText(bagian);


    }

    public void onBackPressed() {

        startActivity(new Intent(Home.this,ProfilActivity.class));
        finish();
    }
}