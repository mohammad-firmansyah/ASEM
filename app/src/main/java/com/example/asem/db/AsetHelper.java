package com.example.asem.db;

import static android.provider.BaseColumns._ID;
import static com.example.asem.db.DatabaseContractAset.*;
import static com.example.asem.db.DatabaseContractAfdeling.*;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_AFDELING;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_ALAT_PENGANGKUTAN;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_ASET;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_ASET_JENIS;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_ASET_KODE;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_ASET_KONDISI;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_ASET_TIPE;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_HAK_AKSES;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_SAP;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_STATUS_POSISI;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_SUB_UNIT;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_UNIT;
import static com.example.asem.db.DatabaseHelper.SQL_CREATE_TABLE_USERS;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_AFDELING;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_ASET_JENIS;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_ASET_KODE;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_ASET_KONDISI;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_ASET_TIPE;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_HAK_AKSES;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_STATUS_POSISI;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_SUB_UNIT;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_UNIT;
import static com.example.asem.db.DatabaseHelper.SQL_INSERT_TABLE_USERS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.asem.api.model.Data;

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

//    public Cursor getAsetById(String id) {
//        return database.rawQuery("SELECT * FROM " + DatabaseContractAset.TABLE_NAME + "WHERE aset_id = ?", new String[]{String.valueOf("aset_id"),null});
//
//    }

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
    public Cursor getAllAlatAngkut() {
        return database.query(
                "alat_pengangkutan",
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
                "aset_id" + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insert(ContentValues values) {
        return database.insert(DatabaseContractAset.TABLE_NAME, null, values);
    }


    public void insertAsetTpe(ContentValues values) {
        database.insert(DatabaseContractAsetTipe.TABLE_NAME, null, values);
    }
    public void insertAlatAngkut(ContentValues values) {
        database.insert(DatabaseContractAlatPengangkutan.TABLE_NAME, null, values);
    }

    public void deleteAsetTpe() {
        database.delete(DatabaseContractAsetTipe.TABLE_NAME,null,null);
    }

    public void insertAfdeling(ContentValues values) {
        database.insert(DatabaseContractAfdeling.TABLE_NAME, null, values);
    }
    public void deleteAfdeling() {
        database.delete(DatabaseContractAfdeling.TABLE_NAME,null,null);
    }

    public void insertUnit(ContentValues values) {
        database.insert(DatabaseContractUnit.TABLE_NAME, null, values);
    }

    public void deleteUnit() {
        database.delete(DatabaseContractUnit.TABLE_NAME,null,null);
    }

    public void insertSubUnit(ContentValues values) {
        database.insert(DatabaseContractSubUnit.TABLE_NAME, null, values);
    }
    public void deleteSubUnit() {
        database.delete(DatabaseContractSubUnit.TABLE_NAME,null,null);
    }

    public void insertAsetKode(ContentValues values) {
        database.insert(DatabaseContractAsetKode.TABLE_NAME, null, values);
    }

    public void deleteAsetKode() {
        database.delete(DatabaseContractAsetKode.TABLE_NAME,null,null);
    }

    public void insertAsetJenis(ContentValues values) {
        database.insert(DatabaseContractAsetJenis.TABLE_NAME, null, values);
    }

    public void deleteAsetJenis() {
        database.delete(DatabaseContractAsetJenis.TABLE_NAME,null,null);
    }

    public void insertAsetKondisi(ContentValues values) {

        database.insert(DatabaseContractAsetKondisi.TABLE_NAME, null, values);
    }

    public void deleteAsetKondisi() {

        database.delete(DatabaseContractAsetKondisi.TABLE_NAME,null,null);
    }
    public void insertSap(ContentValues values) {
        database.insert(DatabaseContractSap.TABLE_NAME, null, values);
    }
    public void deleteSap() {
        database.delete(DatabaseContractSap.TABLE_NAME,null,null);
    }

    public int update(String id, ContentValues values) {
        return database.update(DatabaseContractAset.TABLE_NAME, values, "aset_id" + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return database.delete(DatabaseContractAset.TABLE_NAME, "aset_id" + " = " + id, null);
    }

    public int truncate() {
         database.execSQL("DROP TABLE if exists afdeling");
        database.execSQL("DROP TABLE if exists unit");
        database.execSQL("DROP TABLE if exists sub_unit");
        database.execSQL("DROP TABLE if exists hak_akses");
        database.execSQL("DROP TABLE if exists aset_jenis");
        database.execSQL("DROP TABLE if exists aset_kode");
        database.execSQL("DROP TABLE if exists aset_kondisi");
        database.execSQL("DROP TABLE if exists status_posisi");
        database.execSQL("DROP TABLE if exists aset_tipe");
        database.execSQL("DROP TABLE if exists users");
        database.execSQL("DROP TABLE  if exists sap");
        database.execSQL("DROP TABLE  if exists alat_pengangkutan");

//
        database.execSQL(SQL_CREATE_TABLE_AFDELING);

        database.execSQL(SQL_CREATE_TABLE_ALAT_PENGANGKUTAN);

        database.execSQL(SQL_CREATE_TABLE_ASET_JENIS);

        database.execSQL(SQL_CREATE_TABLE_ASET_KODE);

        database.execSQL(SQL_CREATE_TABLE_ASET_KONDISI);

        database.execSQL(SQL_CREATE_TABLE_STATUS_POSISI);

        database.execSQL(SQL_CREATE_TABLE_SUB_UNIT);

        database.execSQL(SQL_CREATE_TABLE_ASET_TIPE);

        database.execSQL(SQL_CREATE_TABLE_UNIT);

        database.execSQL(SQL_CREATE_TABLE_USERS);

        database.execSQL(SQL_CREATE_TABLE_SAP);

        database.execSQL(SQL_CREATE_TABLE_HAK_AKSES);

//        DatabaseHelper.DATABASE_VERSION += 1;
        return 1;

    }


}
