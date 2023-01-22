package com.example.asem.db;

import static com.example.asem.db.DatabaseContractAset.AsetColumns;
import static com.example.asem.db.DatabaseContractAsetTipe.AsetTipeColumns;
import static com.example.asem.db.DatabaseContractAsetJenis.AsetJenisColumns;
import static com.example.asem.db.DatabaseContractAsetKode.AsetKodeColumns;
import static com.example.asem.db.DatabaseContractAsetKondisi.AsetKondisiColumns;
import static com.example.asem.db.DatabaseContractUnit.UnitColumns;
import static com.example.asem.db.DatabaseContractSubUnit.SubUnitColumns;
import static com.example.asem.db.DatabaseContractAfdeling.AfdelingColumns;
import static com.example.asem.db.DatabaseContractSap.SapColumns;
import android.database.Cursor;

import com.example.asem.api.model.Afdelling;
import com.example.asem.api.model.AlatAngkut;
import com.example.asem.api.model.AsetJenis;
import com.example.asem.api.model.AsetKode2;
import com.example.asem.api.model.AsetKondisi;
import com.example.asem.api.model.AsetTipe;
import com.example.asem.api.model.Sap;
import com.example.asem.api.model.SubUnit;
import com.example.asem.api.model.Unit;
import com.example.asem.db.model.Aset;
import com.example.asem.db.model.DataAllSpinner;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Aset> mapCursorToArrayListAset(Cursor asetCursor) {
        ArrayList<Aset> asetList = new ArrayList<>();

        while (asetCursor.moveToNext()) {
            Integer asetid = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETID));
            String asetname = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETNAME));
            String asettipe = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETTIPE));
            String asetjenis = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETJENIS));
            String asetkondisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETKONDISI));
            String asetsubunit = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETSUBUNIT));
            String asetkode = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETKODE));
            String nomorsap = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.NOMORSAP));
            String fotoaset1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASET1));
            String fotoaset2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASET2));
            String fotoaset3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASET3));
            String fotoaset4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASET4));
            String geotag1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.GEOTAG1));
            String geotag2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.GEOTAG2));
            String geotag3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.GEOTAG3));
            String geotag4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.GEOTAG4));
            Double asetluas = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.ASETLUAS)));
            String tglinput = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.TGLINPUT));
            String tgloleh = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.TGLOLEH));
            Long nilairesidu = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.NILAIRESIDU)));
            Long nilaioleh = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.NILAIOLEH)));
            String nomorbast = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.NOMORBAST));
            String masasusut = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.MASASUSUT));
            String keterangan = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.KETERANGAN));
            String fotoqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOQR));
            String noinv = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.NOINV));
            String fotoasetqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASETQR));
            String statusposisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.STATUSPOSISI));
            String unitid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.UNITID));
            String afdelingid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.AFDELINGID));
            String userinputid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.USERINPUTID));
            String createdat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.CREATEDAT));
            String updatedat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.UPDATEDAT));
            Integer jumlahpohon = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.JUMLAHPOHON)));
            String hgu = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.HGU));
            String asetfotoqrstatus = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETFOTOQRSTATUS));
            String ketreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.KETREJECT));
            String statusreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.STATUSREJECT));
            Double persenkondisi = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.PERSENKONDISI)));
            String beritaAcara = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.BERITAACARA));
            asetList.add(new Aset(asetid, asetname,asettipe,asetjenis,asetkondisi,asetsubunit,asetkode,nomorsap,fotoaset1,fotoaset2,fotoaset3,fotoaset4,geotag1,geotag2,geotag3,geotag4,asetluas,tglinput,tgloleh,nilairesidu,nilaioleh,nomorbast,masasusut,keterangan,fotoqr,noinv,fotoasetqr,statusposisi,unitid,afdelingid,userinputid,createdat,updatedat,jumlahpohon,persenkondisi,statusreject,ketreject,asetfotoqrstatus,hgu,beritaAcara));
        }
        return asetList;
    }

    public static DataAllSpinner mapCursorToArrayListSpinner(Cursor asetTipe,Cursor asetJenis, Cursor asetKondisi,Cursor asetKode,Cursor unit,Cursor subUnit,Cursor afdeling, Cursor sap,Cursor alatAngkut) {
        DataAllSpinner dataAllSpinner = new DataAllSpinner();
        ArrayList<AsetTipe> listAsetTipe = new ArrayList<>();
        ArrayList<AsetJenis> listAsetJenis = new ArrayList<>();
        ArrayList<AsetKondisi> listAsetKondisi = new ArrayList<>();
        ArrayList<AsetKode2> listAsetKode = new ArrayList<>();
        ArrayList<Unit> listUnit = new ArrayList<>();
        ArrayList<SubUnit> listSubUnit = new ArrayList<>();
        ArrayList<Afdelling> listAfdeling = new ArrayList<>();
        ArrayList<Sap> listSap = new ArrayList<>();
        ArrayList<AlatAngkut> listAlatAngkut = new ArrayList<>();

        while (asetTipe.moveToNext()) {
            Integer asetTipeId = asetTipe.getInt(asetTipe.getColumnIndexOrThrow(AsetTipeColumns.ASETTIPEID));
            String asetTipeDesc = asetTipe.getString(asetTipe.getColumnIndexOrThrow(AsetTipeColumns.ASETTIPEDESC));
            listAsetTipe.add(new AsetTipe(asetTipeId,asetTipeDesc));
        }

        while (asetJenis.moveToNext()) {
            Integer asetJenisId = asetJenis.getInt(asetJenis.getColumnIndexOrThrow(AsetJenisColumns.ASETJENISID));
            String asetJenisDesc = asetJenis.getString(asetJenis.getColumnIndexOrThrow(AsetJenisColumns.ASETJENISDESC));
            listAsetJenis.add(new AsetJenis(asetJenisId,asetJenisDesc));
        }

        while (asetKondisi.moveToNext()) {
            Integer asetKondisiId = asetKondisi.getInt(asetKondisi.getColumnIndexOrThrow(AsetKondisiColumns.ASETKONDISIID));
            String asetKondisiDesc = asetKondisi.getString(asetKondisi.getColumnIndexOrThrow(AsetKondisiColumns.ASETKONDISIDESC));
            listAsetKondisi.add(new AsetKondisi(asetKondisiId,asetKondisiDesc));
        }
        while (asetKode.moveToNext()) {
            Integer asetKodeid = asetKode.getInt(asetTipe.getColumnIndexOrThrow(AsetKodeColumns.ASETKODEID));
            String asetkodeCreatedat = asetKode.getString(asetKode.getColumnIndexOrThrow(AsetKodeColumns.CREATEDAT));
            String asetkodeUpdatedAt = asetKode.getString(asetKode.getColumnIndexOrThrow(AsetKodeColumns.UPDATEDAT));
            String asetkodeClass = asetKode.getString(asetKode.getColumnIndexOrThrow(AsetKodeColumns.ASETCLASS));
            String asetKodeGroup = asetKode.getString(asetKode.getColumnIndexOrThrow(AsetKodeColumns.ASETGROUP));
            String asetKodeDesc = asetKode.getString(asetKode.getColumnIndexOrThrow(AsetKodeColumns.ASETDESC));
            Integer asetKodeJenis = asetKode.getInt(asetKode.getColumnIndexOrThrow(AsetKodeColumns.ASETJENIS));
            listAsetKode.add(new AsetKode2(asetKodeid,asetkodeCreatedat,asetkodeUpdatedAt,asetkodeClass,asetKodeGroup,asetKodeDesc,asetKodeJenis));
        }
        while (unit.moveToNext()) {
            Integer unitId = unit.getInt(unit.getColumnIndexOrThrow(UnitColumns.UNITID));
            String unitDesc = unit.getString(unit.getColumnIndexOrThrow(UnitColumns.UNITDESC));
            listUnit.add(new Unit(unitId,unitDesc));
        }
        while (subUnit.moveToNext()) {
            Integer subUnitId = subUnit.getInt(subUnit.getColumnIndexOrThrow(SubUnitColumns.SUBUNITID));
            String subUnitDesc = subUnit.getString(subUnit.getColumnIndexOrThrow(SubUnitColumns.SUBUNITDESC));
            listSubUnit.add(new SubUnit(subUnitId,subUnitDesc));
        }
        while (afdeling.moveToNext()) {
            Integer afdelingId = afdeling.getInt(afdeling.getColumnIndexOrThrow(AfdelingColumns.AFDELINGID));
            String afdelingDesc = afdeling.getString(afdeling.getColumnIndexOrThrow(AfdelingColumns.AFDELINGDESC));
            Integer afdelingUnit = afdeling.getInt(afdeling.getColumnIndexOrThrow(AfdelingColumns.UNITID));
            listAfdeling.add(new Afdelling(afdelingId,afdelingDesc,afdelingUnit));
        }
        while (sap.moveToNext()) {
            Integer sapId = sap.getInt(sap.getColumnIndexOrThrow(SapColumns.SAPID));
            String sapDesc = sap.getString(sap.getColumnIndexOrThrow(SapColumns.SAPDESC));
//            String sapName = sap.getString(sap.getColumnIndexOrThrow(SapColumns.SAPNAME));
//            Integer unitId = sap.getInt(sap.getColumnIndexOrThrow(SapColumns.UNITID));
            listSap.add(new Sap(sapId,sapDesc));
        }

        while (alatAngkut.moveToNext()) {
            Integer apId = alatAngkut.getInt(alatAngkut.getColumnIndexOrThrow(DatabaseContractAlatPengangkutan.AlatPengangkutanColumns.APID));
            String apDesc = alatAngkut.getString(alatAngkut.getColumnIndexOrThrow(DatabaseContractAlatPengangkutan.AlatPengangkutanColumns.APDESC));
            listAlatAngkut.add(new AlatAngkut(apId,apDesc));
        }



        dataAllSpinner.setAsetTipe(listAsetTipe);
        dataAllSpinner.setAsetJenis(listAsetJenis);
        dataAllSpinner.setAsetKondisi(listAsetKondisi);
        dataAllSpinner.setAsetKode(listAsetKode);
        dataAllSpinner.setUnit(listUnit);
        dataAllSpinner.setSubUnit(listSubUnit);
        dataAllSpinner.setAfdeling(listAfdeling);
        dataAllSpinner.setSap(listSap);
        dataAllSpinner.setAlatAngkut(listAlatAngkut);

        return dataAllSpinner;
    }

    public static Aset mapCursorToArrayAset(Cursor asetCursor) {
        Aset asetList = new Aset();

        while (asetCursor.moveToNext()) {
            Integer asetid = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETID));
            String asetname = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETNAME));
            String asettipe = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETTIPE));
            String asetjenis = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETJENIS));
            String asetkondisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETKONDISI));
            String asetsubunit = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETSUBUNIT));
            String asetkode = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETKODE));
            String nomorsap = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.NOMORSAP));
            String fotoaset1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASET1));
            String fotoaset2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASET2));
            String fotoaset3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASET3));
            String fotoaset4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASET4));
            String geotag1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.GEOTAG1));
            String geotag2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.GEOTAG2));
            String geotag3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.GEOTAG3));
            String geotag4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.GEOTAG4));
            Double asetluas = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.ASETLUAS)));
            String tglinput = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.TGLINPUT));
            String tgloleh = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.TGLOLEH));
            Long nilairesidu = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.NILAIRESIDU)));
            Long nilaioleh = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.NILAIOLEH)));
            String nomorbast = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.NOMORBAST));
            String masasusut = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.MASASUSUT));
            String keterangan = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.KETERANGAN));
            String fotoqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOQR));
            String noinv = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.NOINV));
            String fotoasetqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.FOTOASETQR));
            String statusposisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.STATUSPOSISI));
            String unitid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.UNITID));
            String afdelingid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.AFDELINGID));
            String userinputid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.USERINPUTID));
            String createdat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.CREATEDAT));
            String updatedat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.UPDATEDAT));
            Integer jumlahpohon = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.JUMLAHPOHON)));
            String hgu = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.HGU));
            String asetfotoqrstatus = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.ASETFOTOQRSTATUS));
            String ketreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.KETREJECT));
            String statusreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(AsetColumns.STATUSREJECT));
            Double persenkondisi = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.PERSENKONDISI)));
            String beritaAcara = asetCursor.getString(asetCursor.getColumnIndexOrThrow(String.valueOf(AsetColumns.BERITAACARA)));
            asetList = new Aset(asetid, asetname,asettipe,asetjenis,asetkondisi,asetsubunit,asetkode,nomorsap,fotoaset1,fotoaset2,fotoaset3,fotoaset4,geotag1,geotag2,geotag3,geotag4,asetluas,tglinput,tgloleh,nilairesidu,nilaioleh,nomorbast,masasusut,keterangan,fotoqr,noinv,fotoasetqr,statusposisi,unitid,afdelingid,userinputid,createdat,updatedat,jumlahpohon,persenkondisi,statusreject,ketreject,asetfotoqrstatus,hgu,beritaAcara);
        }
        return asetList;
    }
}
