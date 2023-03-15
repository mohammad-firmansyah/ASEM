package ptpn12.amanat.asem;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AsemApp {
    public static String BASE_URL_API = "https://amanat.ptpn12.com/api/";
    public static String BASE_URL_ASSET = "https://amanat.ptpn12.com";
    public static String BASE_URL = "https://amanat.ptpn12.com/";
//    public static String BASE_URL_API = "http://202.148.9.226:7710/mnj_aset_production/public/api/";
//    public static String BASE_URL_ASSET = "http://202.148.9.226:7710/mnj_aset_production/public";
//    public static String BASE_URL = "http://202.148.9.226:7710/mnj_aset_production/";
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String LOGIN_CREDENTIALS = "LOGIN_CREDENTIALS";

    @NonNull
    public static Retrofit getApiClient() {

        // Used to log the request and response.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .callTimeout(0, TimeUnit.MINUTES)
                .connectTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        // Returning the retrofit object.
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //TEMPAT UNTUK initiate LOGIN,SHAREDPREFERENCES

    public static class UserPreferences{
        public static final String USER_ID = "user_id";
        public static final String USERNAME = "username";
        public static final String USER_NIP = "user_nip";
        public static final String USER_FULLNAME = "user_fullname";
        public static final String USER_EMAIL = "user_email";
        public static final String USER_JABATAN = "user_jabatan";
        public static final String HAK_AKSES_ID = "hak_akses_id";
        public static final String UNIT_ID = "unit_id";
        SharedPreferences sharedPreferences;

        public SharedPreferences getSharedPreferences() {
            return sharedPreferences;
        }
    }
}
