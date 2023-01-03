package com.example.asem.db;

import static android.provider.BaseColumns._ID;

import static com.example.asem.db.DatabaseContract.NoteColumns.AFDELINGID;
import static com.example.asem.db.DatabaseContract.NoteColumns.ASETFOTOQRSTATUS;
import static com.example.asem.db.DatabaseContract.NoteColumns.ASETID;
import static com.example.asem.db.DatabaseContract.NoteColumns.ASETJENIS;
import static com.example.asem.db.DatabaseContract.NoteColumns.ASETKODE;
import static com.example.asem.db.DatabaseContract.NoteColumns.ASETKONDISI;
import static com.example.asem.db.DatabaseContract.NoteColumns.ASETLUAS;
import static com.example.asem.db.DatabaseContract.NoteColumns.ASETNAME;
import static com.example.asem.db.DatabaseContract.NoteColumns.ASETSUBUNIT;
import static com.example.asem.db.DatabaseContract.NoteColumns.ASETTIPE;
import static com.example.asem.db.DatabaseContract.NoteColumns.CREATEDAT;
import static com.example.asem.db.DatabaseContract.NoteColumns.FOTOASET1;
import static com.example.asem.db.DatabaseContract.NoteColumns.FOTOASET2;
import static com.example.asem.db.DatabaseContract.NoteColumns.FOTOASET3;
import static com.example.asem.db.DatabaseContract.NoteColumns.FOTOASET4;
import static com.example.asem.db.DatabaseContract.NoteColumns.FOTOASETQR;
import static com.example.asem.db.DatabaseContract.NoteColumns.FOTOQR;
import static com.example.asem.db.DatabaseContract.NoteColumns.GEOTAG1;
import static com.example.asem.db.DatabaseContract.NoteColumns.GEOTAG2;
import static com.example.asem.db.DatabaseContract.NoteColumns.GEOTAG3;
import static com.example.asem.db.DatabaseContract.NoteColumns.GEOTAG4;
import static com.example.asem.db.DatabaseContract.NoteColumns.HGU;
import static com.example.asem.db.DatabaseContract.NoteColumns.JUMLAHPOHON;
import static com.example.asem.db.DatabaseContract.NoteColumns.KETERANGAN;
import static com.example.asem.db.DatabaseContract.NoteColumns.KETREJECT;
import static com.example.asem.db.DatabaseContract.NoteColumns.MASASUSUT;
import static com.example.asem.db.DatabaseContract.NoteColumns.NILAIOLEH;
import static com.example.asem.db.DatabaseContract.NoteColumns.NILAIRESIDU;
import static com.example.asem.db.DatabaseContract.NoteColumns.NOINV;
import static com.example.asem.db.DatabaseContract.NoteColumns.NOMORBAST;
import static com.example.asem.db.DatabaseContract.NoteColumns.NOMORSAP;
import static com.example.asem.db.DatabaseContract.NoteColumns.PERSENKONDISI;
import static com.example.asem.db.DatabaseContract.NoteColumns.STATUSPOSISI;
import static com.example.asem.db.DatabaseContract.NoteColumns.STATUSPOSISIID;
import static com.example.asem.db.DatabaseContract.NoteColumns.STATUSREJECT;
import static com.example.asem.db.DatabaseContract.NoteColumns.TGLINPUT;
import static com.example.asem.db.DatabaseContract.NoteColumns.TGLOLEH;
import static com.example.asem.db.DatabaseContract.NoteColumns.UMUREKONOMISINMONTH;
import static com.example.asem.db.DatabaseContract.NoteColumns.UNITID;
import static com.example.asem.db.DatabaseContract.NoteColumns.UPDATEDAT;
import static com.example.asem.db.DatabaseContract.NoteColumns.USERINPUTID;

import android.database.Cursor;

import com.example.asem.db.model.Aset;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Aset> mapCursorToArrayList(Cursor asetCursor) {
        ArrayList<Aset> asetList = new ArrayList<>();

        while (asetCursor.moveToNext()) {
//            int id = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(_ID));
            Integer asetid = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(String.valueOf("aset_id")));
            String asetname = asetCursor.getString(asetCursor.getColumnIndexOrThrow(ASETNAME));
            String asettipe = asetCursor.getString(asetCursor.getColumnIndexOrThrow(ASETTIPE));
            String asetjenis = asetCursor.getString(asetCursor.getColumnIndexOrThrow(ASETJENIS));
            String asetkondisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(ASETKONDISI));
            String asetsubunit = asetCursor.getString(asetCursor.getColumnIndexOrThrow(ASETSUBUNIT));
            String asetkode = asetCursor.getString(asetCursor.getColumnIndexOrThrow(ASETKODE));
            String nomorsap = asetCursor.getString(asetCursor.getColumnIndexOrThrow(NOMORSAP));
            String fotoaset1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(FOTOASET1));
            String fotoaset2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(FOTOASET2));
            String fotoaset3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(FOTOASET3));
            String fotoaset4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(FOTOASET4));
            String geotag1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(GEOTAG1));
            String geotag2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(GEOTAG2));
            String geotag3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(GEOTAG3));
            String geotag4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(GEOTAG4));
            Double asetluas = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(ASETLUAS)));
            String tglinput = asetCursor.getString(asetCursor.getColumnIndexOrThrow(TGLINPUT));
            String tgloleh = asetCursor.getString(asetCursor.getColumnIndexOrThrow(TGLOLEH));
            Long nilairesidu = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(NILAIRESIDU)));
            Long nilaioleh = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(NILAIOLEH)));
            String nomorbast = asetCursor.getString(asetCursor.getColumnIndexOrThrow(NOMORBAST));
            String masasusut = asetCursor.getString(asetCursor.getColumnIndexOrThrow(MASASUSUT));
            String keterangan = asetCursor.getString(asetCursor.getColumnIndexOrThrow(KETERANGAN));
            String fotoqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(FOTOQR));
            String noinv = asetCursor.getString(asetCursor.getColumnIndexOrThrow(NOINV));
            String fotoasetqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(FOTOASETQR));
            String statusposisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(STATUSPOSISI));
            String unitid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(UNITID));
            String afdelingid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AFDELINGID));
            String userinputid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(USERINPUTID));
            String createdat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(CREATEDAT));
            String updatedat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(UPDATEDAT));
            Integer jumlahpohon = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(String.valueOf(JUMLAHPOHON)));
            String hgu = asetCursor.getString(asetCursor.getColumnIndexOrThrow(HGU));
            String asetfotoqrstatus = asetCursor.getString(asetCursor.getColumnIndexOrThrow(ASETFOTOQRSTATUS));
            Integer statusposisiid = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(String.valueOf(STATUSPOSISIID)));
            String ketreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(KETREJECT));
            String statusreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(STATUSREJECT));
            Double persenkondisi = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(PERSENKONDISI)));
            Integer umurekonomisinmonth = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(String.valueOf(UMUREKONOMISINMONTH)));
            asetList.add(new Aset(asetid, asetname,asettipe,asetjenis,asetkondisi,asetsubunit,asetkode,nomorsap,fotoaset1,fotoaset2,fotoaset3,fotoaset4,geotag1,geotag2,geotag3,geotag4,asetluas,tglinput,tgloleh,nilairesidu,nilaioleh,nomorbast,masasusut,keterangan,fotoqr,noinv,fotoasetqr,statusposisi,unitid,afdelingid,userinputid,createdat,updatedat,jumlahpohon,umurekonomisinmonth,persenkondisi,statusposisiid,statusreject,ketreject,asetfotoqrstatus));
        }
        return asetList;
    }
}
