package com.example.asem.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "asetmnj_prod";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE = "create table `data_aset` (`aset_id` int (11),`aset_name` varchar (765),`aset_tipe` int (11),`aset_jenis` int (11),`aset_kondisi` int (11),`aset_sub_unit` int (11),`aset_kode` int (11),`nomor_sap` varchar (765),`foto_aset1` varchar (765),`foto_aset2` varchar (765),`foto_aset3` varchar (765),`foto_aset4` varchar (765),`geo_tag1` varchar (765),`geo_tag2` varchar (765),`geo_tag3` varchar (765),`geo_tag4` varchar (765),\n\t`aset_luas` double ,\n\t`tgl_input` datetime ,\n\t`tgl_oleh` datetime ,`nilai_residu` bigint (20),`nilai_oleh` bigint (20),`nomor_bast` varchar (765),`masa_susut` varchar (765),`keterangan` varchar (765),`foto_qr` varchar (765),`no_inv` varchar (765),`foto_aset_qr` varchar (765),`status_posisi` int (11),`unit_id` int (11),`afdeling_id` int (11),`user_input_id` int (11),`created_at` timestamp ,`updated_at` datetime ,`jumlah_pohon` int (11),`persen_kondisi` double ,`berita_acara` varchar (765),`status_reject` varchar (765),`ket_reject` varchar (765),`aset_foto_qr_status` varchar (765),`hgu` varchar (765))";
//            DatabaseContract.TABLE_NAME,
//            DatabaseContract.NoteColumns._ID,
//            DatabaseContract.NoteColumns.ASETID,
//            DatabaseContract.NoteColumns.AFDELINGID,
//            DatabaseContract.NoteColumns.ASETJENIS,
//            DatabaseContract.NoteColumns.ASETID,
//            DatabaseContract.NoteColumns.ASETNAME,
//            DatabaseContract.NoteColumns.ASETTIPE,
//            DatabaseContract.NoteColumns.ASETJENIS,
//            DatabaseContract.NoteColumns.ASETKONDISI,
//            DatabaseContract.NoteColumns.ASETSUB_UNIT,
//            DatabaseContract.NoteColumns.ASETKODE,
//            DatabaseContract.NoteColumns.NOMORSAP,
//            DatabaseContract.NoteColumns.FOTOASET1,
//            DatabaseContract.NoteColumns.FOTOASET2,
//            DatabaseContract.NoteColumns.FOTOASET3,
//            DatabaseContract.NoteColumns.FOTOASET4,
//            DatabaseContract.NoteColumns.GEOTAG1,
//            DatabaseContract.NoteColumns.GEOTAG2,
//            DatabaseContract.NoteColumns.GEOTAG3,
//            DatabaseContract.NoteColumns.GEOTAG4,
//            DatabaseContract.NoteColumns.ASETLUAS,
//            DatabaseContract.NoteColumns.TGLINPUT,
//            DatabaseContract.NoteColumns.TGLOLEH,
//            DatabaseContract.NoteColumns.NILAIRESIDU,
//            DatabaseContract.NoteColumns.NILAIOLEH,
//            DatabaseContract.NoteColumns.NOMORBAST,
//            DatabaseContract.NoteColumns.MASASUSUT,
//            DatabaseContract.NoteColumns.KETERANGAN,
//            DatabaseContract.NoteColumns.FOTOQR,
//            DatabaseContract.NoteColumns.NOINV,
//            DatabaseContract.NoteColumns.FOTOASETQR,
//            DatabaseContract.NoteColumns.STATUSPOSISI,
//            DatabaseContract.NoteColumns.UNITID,
//            DatabaseContract.NoteColumns.AFDELINGID,
//            DatabaseContract.NoteColumns.USERINPUTID,
//            DatabaseContract.NoteColumns.CREATEDAT,
//            DatabaseContract.NoteColumns.UPDATEDAT,
//            DatabaseContract.NoteColumns.JUMLAHPOHON
//    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
