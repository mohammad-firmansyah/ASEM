package ptpn12.amanat.asem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import ptpn12.amanat.asem.R;

public class SplashScreen extends AppCompatActivity {

    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String LOGIN_CREDENTIALS = "LOGIN_CREDENTIALS";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        new Handler().postDelayed(this::checkLogin, 2000);
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
}