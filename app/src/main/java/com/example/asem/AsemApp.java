package com.example.asem;

import android.content.SharedPreferences;

public class AsemApp {
    public static String BASE_URL_API = "http://202.148.9.226:7710/mnj_aset_production/public/api/";
    public static String BASE_URL_ASSET = "http://202.148.9.226:7710/mnj_aset_production/public";
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String LOGIN_CREDENTIALS = "LOGIN_CREDENTIALS";

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
