package com.example.asem.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME = "data_aset";
    static final class NoteColumns implements BaseColumns {
        static Integer ASETID;
        static String ASETNAME;
        static String ASETTIPE;
        static String ASETJENIS;
        static String ASETKONDISI;
        static String ASETSUBUNIT;
        static String ASETKODE;
        static String NOMORSAP;
        static String FOTOASET1;
        static String FOTOASET2;
        static String FOTOASET3;
        static String FOTOASET4;
        static String GEOTAG1;
        static String GEOTAG2;
        static String GEOTAG3;
        static String GEOTAG4;
        static Double ASETLUAS;
        static String TGLINPUT;
        static String TGLOLEH;
        static Long NILAIRESIDU;
        static Long NILAIOLEH;
        static String NOMORBAST;
        static String MASASUSUT;
        static String KETERANGAN;
        static String FOTOQR;
        static String NOINV;
        static String FOTOASETQR;
        static String STATUSPOSISI;
        static String UNITID;
        static String AFDELINGID;
        static String USERINPUTID;
        static String CREATEDAT;
        static String UPDATEDAT;
        static Integer JUMLAHPOHON;
        static String HGU;
        static String ASETFOTOQRSTATUS;
        static Integer STATUSPOSISIID;
        static String KETREJECT;
        static String STATUSREJECT;
        static Double PERSENKONDISI;
        static Integer UMUREKONOMISINMONTH;

    }
}
