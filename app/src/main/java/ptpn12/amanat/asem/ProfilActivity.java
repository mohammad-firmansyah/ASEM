package ptpn12.amanat.asem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ptpn12.amanat.asem.R;
import ptpn12.amanat.asem.offline.AsetHelper;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfilActivity extends AppCompatActivity {

    private static final String PREF_LOGIN = "LOGIN_PREF";

    AsetHelper asetHelper;
    TextView tvNIP,tvNama,tvHakAkses,tvJabatan,tvBagian,tvEmail, tvVersionName, tvVersionCode;
    CardView resetPass, logOut;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        asetHelper = AsetHelper.getInstance(getApplicationContext());
        tvNIP = findViewById(R.id.tvNIP);
        tvNama = findViewById(R.id.tvNama);
        tvHakAkses = findViewById(R.id.tvHakAkses);
        tvJabatan = findViewById(R.id.tvJabatan);
        tvBagian = findViewById(R.id.tvBagian);
        tvEmail = findViewById(R.id.tvEmail);

        resetPass = findViewById(R.id.resetPass);
        logOut = findViewById(R.id.logOut);

        // globally
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        //in your OnCreate() method
        tvTitle.setText(R.string.profil);

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        // Set the version to a TextView
        tvVersionCode = findViewById(R.id.tvVersionCode);
        tvVersionCode.setText("Version : "+(versionCode));

        tvVersionName = findViewById(R.id.tvVersionName);
        tvVersionName.setText(" || v"+versionName);


        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AsemApp.BASE_URL + "reset-password/"));
//                startActivity(browserIntent);
                Toast.makeText(ProfilActivity.this, "Fitur Belum Berfungsi", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);

        bottomNavigationView.setSelectedItemId(R.id.nav_profil);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_beranda:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profil:
                        return true;
                    case R.id.nav_longlist:
                        startActivity(new Intent(getApplicationContext(),LonglistAsetActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        getUser();
    }

    private void getUser(){

        sharedPreferences = getSharedPreferences(PREF_LOGIN,MODE_PRIVATE);
        String user_fullname = sharedPreferences.getString("nama", "-");
        String nip = sharedPreferences.getString("user_nip", "-");
        String email = sharedPreferences.getString("user_email", "-");
        String jabatan = sharedPreferences.getString("user_jabatan", "-");
        String bagian = sharedPreferences.getString("unit_desc","-");
        String hak_akses = sharedPreferences.getString("hak_akses_desc","-");

        //Log.d("asetapix",user_fullname);
        tvNama.setText(user_fullname);
        tvHakAkses.setText(hak_akses);
        tvNIP.setText(nip);
        tvEmail.setText(email);
        tvJabatan.setText(jabatan);
        tvBagian.setText(bagian);
        //tvJabatan.setText(String.valueOf("Staf Keuangan dan Akuntansi"));
        //tvBagian.setText(String.valueOf("Bagian Keuangan dan Akuntansi"));
    }

    //fungsi layout menu

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Keluar");
        builder.setMessage("Apa Anda Yakin Ingin Logout?")
                .setPositiveButton("Iya", (dialog, id) -> logoutAct())
                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
//        startActivity(new Intent(ProfilActivity.this, MainActivity.class));
//        finish();
    }

    public void logoutAct(){
        SharedPreferences.Editor editor = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit().clear();
        editor.clear().apply();
        finishAffinity();
        asetHelper.open();
        asetHelper.deleteAllAset();
        asetHelper.close();
        startActivity(new Intent(ProfilActivity.this, SplashScreen.class));
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Keluar");
        builder.setMessage("Apa anda yakin keluar aplikasi?")
                .setPositiveButton("Iya", (dialog, id) ->finishAffinity())
                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
}