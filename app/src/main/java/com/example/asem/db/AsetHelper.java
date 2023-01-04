package com.example.asem.db;

import static android.provider.BaseColumns._ID;
import static com.example.asem.db.DatabaseContractAset.*;
import static com.example.asem.db.DatabaseContractAfdeling.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AsetHelper {
    private static AsetHelper INSTANCE;
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
                DatabaseContractAset.TABLE_NAME,
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

    public Cursor getAllAsetJenis() {
        return database.query(
                "aset_jenis",
                null,
                null,
                null,
                null,
                null,
                null);
    }


    public Cursor getAllAsetTipe() {
        return database.query(
                "aset_tipe",
                null,
                null,
                null,
                null,
                null,
                null);
    }


    public Cursor getAllAsetKondisi() {
        return database.query(
                "aset_kondisi",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAllAsetKode() {
        return database.query(
                "aset_kode",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAllHakAkses() {
        return database.query(
                "hak_akses",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAllSap() {
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

    public Cursor getAllSubUnit() {
        return database.query(
                "sub_unit",
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getAllUnit() {
        return database.query(
                "unit",
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
                DatabaseContractAset.TABLE_NAME,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(DatabaseContractAset.TABLE_NAME, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(DatabaseContractAset.TABLE_NAME, values, "aset_id" + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DatabaseContractAset.TABLE_NAME, "aset_id" + " = " + id, null);
    }
}
