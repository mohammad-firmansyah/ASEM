package com.example.asem.db;

import static android.provider.BaseColumns._ID;
import static com.example.asem.db.DatabaseContract.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AsetHelper {
    private static AsetHelper INSTANCE;
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static SQLiteDatabase database;

    private AsetHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static AsetHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = new AsetHelper(context);
                }
            }

        }

        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
//        database.execSQL("DROP TABLE data_aset");
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAllAfdeling() {
        return database.query(
                "afdeling",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAsetJenis() {
        return database.query(
                "aset_jenis",
                null,
                null,
                null,
                null,
                null,
                null);
    }


    public Cursor getAsetTipe() {
        return database.query(
                "aset_tipe",
                null,
                null,
                null,
                null,
                null,
                null);
    }


    public Cursor getAsetKondisi() {
        return database.query(
                "aset_kondisi",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAsetKode() {
        return database.query(
                "aset_kode",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getHakAkses() {
        return database.query(
                "hak_akses",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getSap() {
        return database.query(
                "sap",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getStatusPosisi() {
        return database.query(
                "status_posisi",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getSubUnit() {
        return database.query(
                "sub_unit",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getUnit() {
        return database.query(
                "sub_unit",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getUsers() {
        return database.query(
                "users",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = " + id, null);
    }
}
