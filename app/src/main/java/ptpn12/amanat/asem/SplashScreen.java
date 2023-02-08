package ptpn12.amanat.asem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

import ptpn12.amanat.asem.R;
import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.VersionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String LOGIN_CREDENTIALS = "LOGIN_CREDENTIALS";
    SharedPreferences sharedPreferences;
    AsetInterface asetInterface;
    String versionName;
    Integer versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        versionName = BuildConfig.VERSION_NAME;
        versionCode = BuildConfig.VERSION_CODE;

        new Handler().postDelayed(this::running, 2000);
    }

    private void checkLogin() {
        String user_id = sharedPreferences.getString("user_id","-");
        Log.d("asetapix", user_id);

        if(user_id.equals("-")){
//            Toast.makeText(getApplicationContext(),"Anda sudah login",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            finish();
            startActivity(intent);
        } else {
//            Toast.makeText(getApplicationContext(),"Harap login terlebih daulu",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SplashScreen.this, Home.class);
            finish();
            startActivity(intent);
        }
    }

    //check android version
    private void running() {
        checkUpdate();
    }

    private void checkUpdate(){
        Call<VersionModel> call = asetInterface.checkUpdate(versionCode, versionName);
        call.enqueue(new Callback<VersionModel>() {
            @Override
            public void onResponse(@NonNull Call<VersionModel> call, @NonNull Response<VersionModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getSuccess()){
                        Boolean updateAvailable = response.body().getUpdateAvailable();
                        Boolean updateNeeded = response.body().getUpdateNeeded();
                        String aplikasiId = response.body().getAplikasiId();
                        String pesan1 = response.body().getPesan1();
                        String pesan2 = response.body().getPesan2();
                        isUpdate(updateAvailable, updateNeeded, aplikasiId, pesan1, pesan2);
                    }else{
                        AlertDialog dialog = new AlertDialog.Builder(SplashScreen.this)
                                .setTitle("Error[1]")
                                .setMessage("Response : "+response.message())
                                .setPositiveButton("Coba Lagi", (arg0, arg1) -> running())
                                .show();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                    }
                }else{
                    AlertDialog dialog = new AlertDialog.Builder(SplashScreen.this)
                            .setTitle("Error[2]")
                            .setMessage("Response : "+response.code()+" "+response.message())
                            .setPositiveButton("Coba Lagi", (arg0, arg1) -> running())
                            .show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VersionModel> call, @NonNull Throwable t) {

                String username = sharedPreferences.getString("nama","-");

                if (!username.equals("-")){
                    Toast.makeText(getApplicationContext(),"Anda dalam mode Offline",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreen.this, Home.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Anda belum login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void isUpdate(Boolean updateAvailable, Boolean updateNeeded, String aplikasiId, String pesan1, String pesan2){
        if (updateAvailable){
            AlertDialog dialog;
            if (updateNeeded){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                dialog = new AlertDialog.Builder(SplashScreen.this)
                        .setTitle("Update Aplikasi Tersedia")
                        .setMessage(pesan2)
                        .setNegativeButton("Update Aplikasi", (dialogInterface, i) -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + aplikasiId));
                            startActivity(intent);
                            finish();
                        }).show();
            }else{
                dialog = new AlertDialog.Builder(SplashScreen.this)
                        .setTitle("Update Aplikasi Tersedia")
                        .setMessage(pesan1)
                        .setNegativeButton("Update Sekarang", (dialogInterface, i) -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + aplikasiId));
                            startActivity(intent);
                            finish();
                        })
                        .setPositiveButton("Update Nanti", (arg0, arg1) -> {
                            if (sharedPreferences.contains(LOGIN_CREDENTIALS)) {
                                String loginToken = sharedPreferences.getString(LOGIN_CREDENTIALS, "");
                                if (loginToken.equals("")) {
                                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    checkLogin();
                                }
                            } else {
                                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }).show();
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }else{
            if(sharedPreferences.contains(LOGIN_CREDENTIALS)){
                String loginToken = sharedPreferences.getString(LOGIN_CREDENTIALS, "");
                if (loginToken.equals("")) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    checkLogin();
                }
            }else{
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

}