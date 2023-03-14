package ptpn12.amanat.asem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import ptpn12.amanat.asem.R;
import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.AsetModel2;
import ptpn12.amanat.asem.api.model.CheckVersionModel;
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

        AsetInterface asetInterface;
        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);
        Call<CheckVersionModel> call = asetInterface.checkVersion(versionCode);


        call.enqueue(new Callback<CheckVersionModel>() {
            @Override
            public void onResponse(Call<CheckVersionModel> call, Response<CheckVersionModel> response) {
                if (!response.isSuccessful() && response.body() == null) {
                    Toast.makeText(getApplicationContext(),"Error " + String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                } else {
                    android.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashScreen.this);
                    if (!response.body().getData()) {

                        // set title dialog
                        alertDialogBuilder.setTitle("Update Versi Terbaru");

                        // set pesan dari dialog
                        alertDialogBuilder
                                .setMessage("Update versi aplikasi anda yang terbaru")
                                .setCancelable(false)
                                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=ptpn12.amanat.asem"));
                                        startActivity(browserIntent);

                                    }
                                });
                        alertDialogBuilder.show();
//                        new Handler().postDelayed(this::checkLogin, 2000);
                        return;
                    } else {
                        new Handler().postDelayed(this::checkLogin, 2000);
//                        Toast.makeText(getApplicationContext(),"masuk",Toast.LENGTH_LONG).show();
                    }
                }
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

            @Override
            public void onFailure(Call<CheckVersionModel> call, Throwable t) {

            }
        });
    }

}