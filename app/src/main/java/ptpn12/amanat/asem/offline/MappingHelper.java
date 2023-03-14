package ptpn12.amanat.asem.offline;

import android.database.Cursor;

import ptpn12.amanat.asem.api.model.Afdelling;
import ptpn12.amanat.asem.api.model.AlatAngkut;
import ptpn12.amanat.asem.api.model.AsetJenis;
import ptpn12.amanat.asem.api.model.AsetKode2;
import ptpn12.amanat.asem.api.model.AsetKondisi;
import ptpn12.amanat.asem.api.model.AsetTipe;
import ptpn12.amanat.asem.api.model.Sap;
import ptpn12.amanat.asem.api.model.SistemTanam;
import ptpn12.amanat.asem.api.model.SubUnit;
import ptpn12.amanat.asem.api.model.Unit;
import ptpn12.amanat.asem.offline.model.Aset;
import ptpn12.amanat.asem.offline.model.DataAllSpinner;
import ptpn12.amanat.asem.offline.model.DatabaseContractAfdeling;
import ptpn12.amanat.asem.offline.model.DatabaseContractAlatPengangkutan;
import ptpn12.amanat.asem.offline.model.DatabaseContractAset;
import ptpn12.amanat.asem.offline.model.DatabaseContractAsetJenis;
import ptpn12.amanat.asem.offline.model.DatabaseContractAsetKode;
import ptpn12.amanat.asem.offline.model.DatabaseContractAsetKondisi;
import ptpn12.amanat.asem.offline.model.DatabaseContractAsetTipe;
import ptpn12.amanat.asem.offline.model.DatabaseContractSap;
import ptpn12.amanat.asem.offline.model.DatabaseContractSistemTanam;
import ptpn12.amanat.asem.offline.model.DatabaseContractSubUnit;
import ptpn12.amanat.asem.offline.model.DatabaseContractUnit;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Aset> mapCursorToArrayListAset(Cursor asetCursor) {
        ArrayList<Aset> asetList = new ArrayList<>();

        while (asetCursor.moveToNext()) {
            Integer asetid = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETID));
            String asetname = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETNAME));
            String asettipe = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETTIPE));
            String asetjenis = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETJENIS));
            String asetkondisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETKONDISI));
            String asetsubunit = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETSUBUNIT));
            String asetkode = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETKODE));
            String nomorsap = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.NOMORSAP));
            String fotoaset1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET1));
            String fotoaset2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET2));
            String fotoaset3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET3));
            String fotoaset4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET4));
            String fotoaset5 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET5));
            String geotag1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG1));
            String geotag2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG2));
            String geotag3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG3));
            String geotag4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG4));
            String geotag5 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG5));
            Double asetluas = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.ASETLUAS)));
            String tglinput = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.TGLINPUT));
            String tgloleh = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.TGLOLEH));
            Long nilairesidu = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.NILAIRESIDU)));
            Long nilaioleh = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.NILAIOLEH)));
            String nomorbast = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.NOMORBAST));
            String masasusut = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.MASASUSUT));
            String keterangan = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.KETERANGAN));
            String fotoqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOQR));
            String noinv = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.NOINV));
            String fotoasetqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASETQR));
            String statusposisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.STATUSPOSISI));
            String unitid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.UNITID));
            String afdelingid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.AFDELINGID));
            String userinputid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.USERINPUTID));
            String createdat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.CREATEDAT));
            String updatedat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.UPDATEDAT));
            Integer jumlahpohon = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.JUMLAHPOHON)));
            String hgu = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.HGU));
            String asetfotoqrstatus = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETFOTOQRSTATUS));
            String ketreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.KETREJECT));
            String statusreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.STATUSREJECT));
            Double persenkondisi = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.PERSENKONDISI)));
            String beritaAcara = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.BERITAACARA));
            String fileBAST = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FILEBAST));
            String alat_pengangkutan = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ALATPENGANGKUTAN));
            String satuan_luas = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.SATUANLUAS));
            String pop_total_ini = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.POPTOTALINI));
            String pop_total_std = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.POPTOTALSTD));
            String pop_hektar_ini = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.POPHEKTARINI));
            String pop_hektar_std = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.POPHEKTARSTD));
            Integer tahun_tanam = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.TAHUNTANAM));
            String sistem_tanam = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.SISTEMTANAM));
            asetList.add(new Aset(asetid, asetname,asettipe,asetjenis,asetkondisi,asetsubunit,asetkode,nomorsap,fotoaset1,fotoaset2,
                    fotoaset3,fotoaset4,fotoaset5,geotag1,geotag2,geotag3,geotag4,geotag5,asetluas,tglinput,tgloleh,nilairesidu,nilaioleh,nomorbast,
                    masasusut,keterangan,fotoqr,noinv,fotoasetqr,statusposisi,unitid,afdelingid,userinputid,createdat,updatedat,
                    jumlahpohon,persenkondisi,statusreject,ketreject,asetfotoqrstatus,hgu,beritaAcara,fileBAST,alat_pengangkutan,satuan_luas,
                    pop_total_ini,pop_total_std,pop_hektar_ini,pop_hektar_std,tahun_tanam,sistem_tanam));
        }
        return asetList;
    }

    public static DataAllSpinner mapCursorToArrayListSpinner(Cursor asetTipe, Cursor asetJenis, Cursor asetKondisi, Cursor asetKode, Cursor unit, Cursor subUnit, Cursor afdeling, Cursor sap, Cursor alatAngkut,Cursor sistemTanam) {
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
        ArrayList<SistemTanam> listSistemTanam = new ArrayList<>();

        while (asetTipe.moveToNext()) {
            Integer asetTipeId = asetTipe.getInt(asetTipe.getColumnIndexOrThrow(DatabaseContractAsetTipe.AsetTipeColumns.ASETTIPEID));
            String asetTipeDesc = asetTipe.getString(asetTipe.getColumnIndexOrThrow(DatabaseContractAsetTipe.AsetTipeColumns.ASETTIPEDESC));
            listAsetTipe.add(new AsetTipe(asetTipeId,asetTipeDesc));
        }

        while (asetJenis.moveToNext()) {
            Integer asetJenisId = asetJenis.getInt(asetJenis.getColumnIndexOrThrow(DatabaseContractAsetJenis.AsetJenisColumns.ASETJENISID));
            String asetJenisDesc = asetJenis.getString(asetJenis.getColumnIndexOrThrow(DatabaseContractAsetJenis.AsetJenisColumns.ASETJENISDESC));
            listAsetJenis.add(new AsetJenis(asetJenisId,asetJenisDesc));
        }

        while (asetKondisi.moveToNext()) {
            Integer asetKondisiId = asetKondisi.getInt(asetKondisi.getColumnIndexOrThrow(DatabaseContractAsetKondisi.AsetKondisiColumns.ASETKONDISIID));
            String asetKondisiDesc = asetKondisi.getString(asetKondisi.getColumnIndexOrThrow(DatabaseContractAsetKondisi.AsetKondisiColumns.ASETKONDISIDESC));
            listAsetKondisi.add(new AsetKondisi(asetKondisiId,asetKondisiDesc));
        }
        while (asetKode.moveToNext()) {
            Integer asetKodeid = asetKode.getInt(asetTipe.getColumnIndexOrThrow(DatabaseContractAsetKode.AsetKodeColumns.ASETKODEID));
            String asetkodeCreatedat = asetKode.getString(asetKode.getColumnIndexOrThrow(DatabaseContractAsetKode.AsetKodeColumns.CREATEDAT));
            String asetkodeUpdatedAt = asetKode.getString(asetKode.getColumnIndexOrThrow(DatabaseContractAsetKode.AsetKodeColumns.UPDATEDAT));
            String asetkodeClass = asetKode.getString(asetKode.getColumnIndexOrThrow(DatabaseContractAsetKode.AsetKodeColumns.ASETCLASS));
            String asetKodeGroup = asetKode.getString(asetKode.getColumnIndexOrThrow(DatabaseContractAsetKode.AsetKodeColumns.ASETGROUP));
            String asetKodeDesc = asetKode.getString(asetKode.getColumnIndexOrThrow(DatabaseContractAsetKode.AsetKodeColumns.ASETDESC));
            Integer asetKodeJenis = asetKode.getInt(asetKode.getColumnIndexOrThrow(DatabaseContractAsetKode.AsetKodeColumns.ASETJENIS));
            listAsetKode.add(new AsetKode2(asetKodeid,asetkodeCreatedat,asetkodeUpdatedAt,asetkodeClass,asetKodeGroup,asetKodeDesc,asetKodeJenis));
        }
        while (unit.moveToNext()) {
            Integer unitId = unit.getInt(unit.getColumnIndexOrThrow(DatabaseContractUnit.UnitColumns.UNITID));
            String unitDesc = unit.getString(unit.getColumnIndexOrThrow(DatabaseContractUnit.UnitColumns.UNITDESC));
            listUnit.add(new Unit(unitId,unitDesc));
        }
        while (subUnit.moveToNext()) {
            Integer subUnitId = subUnit.getInt(subUnit.getColumnIndexOrThrow(DatabaseContractSubUnit.SubUnitColumns.SUBUNITID));
            String subUnitDesc = subUnit.getString(subUnit.getColumnIndexOrThrow(DatabaseContractSubUnit.SubUnitColumns.SUBUNITDESC));
            listSubUnit.add(new SubUnit(subUnitId,subUnitDesc));
        }
        while (afdeling.moveToNext()) {
            Integer afdelingId = afdeling.getInt(afdeling.getColumnIndexOrThrow(DatabaseContractAfdeling.AfdelingColumns.AFDELINGID));
            String afdelingDesc = afdeling.getString(afdeling.getColumnIndexOrThrow(DatabaseContractAfdeling.AfdelingColumns.AFDELINGDESC));
            Integer afdelingUnit = afdeling.getInt(afdeling.getColumnIndexOrThrow(DatabaseContractAfdeling.AfdelingColumns.UNITID));
            listAfdeling.add(new Afdelling(afdelingId,afdelingDesc,afdelingUnit));
        }
        while (sap.moveToNext()) {
            Integer sapId = sap.getInt(sap.getColumnIndexOrThrow(DatabaseContractSap.SapColumns.SAPID));
            String sapDesc = sap.getString(sap.getColumnIndexOrThrow(DatabaseContractSap.SapColumns.SAPDESC));
            String sapName = sap.getString(sap.getColumnIndexOrThrow(DatabaseContractSap.SapColumns.SAPNAME));
            Integer unitId = sap.getInt(sap.getColumnIndexOrThrow(DatabaseContractSap.SapColumns.UNITID));
            Long nilaiOleh = sap.getLong(sap.getColumnIndexOrThrow(DatabaseContractSap.SapColumns.NILAIOLEH));
            Long nilaiResidu = sap.getLong(sap.getColumnIndexOrThrow(DatabaseContractSap.SapColumns.NILAIRESIDU));
            Integer masaSusut = sap.getInt(sap.getColumnIndexOrThrow(DatabaseContractSap.SapColumns.MASASUSUT));
            String tglOleh = sap.getString(sap.getColumnIndexOrThrow(DatabaseContractSap.SapColumns.TGLOLEH));
            listSap.add(new Sap(sapId,sapDesc,sapName,unitId,nilaiOleh,nilaiResidu,masaSusut,tglOleh));
        }

        while (alatAngkut.moveToNext()) {
            Integer apId = alatAngkut.getInt(alatAngkut.getColumnIndexOrThrow(DatabaseContractAlatPengangkutan.AlatPengangkutanColumns.APID));
            String apDesc = alatAngkut.getString(alatAngkut.getColumnIndexOrThrow(DatabaseContractAlatPengangkutan.AlatPengangkutanColumns.APDESC));
            listAlatAngkut.add(new AlatAngkut(apId,apDesc));
        }

        while (sistemTanam.moveToNext()) {
            Integer stId = sistemTanam.getInt(sistemTanam.getColumnIndexOrThrow(DatabaseContractSistemTanam.SistemTanamColumns.SISTEMTANAMID));
            String stDesc = sistemTanam.getString(sistemTanam.getColumnIndexOrThrow(DatabaseContractSistemTanam.SistemTanamColumns.SISTEMTANAMDESC));
            listSistemTanam.add(new SistemTanam(stId,stDesc));
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
        dataAllSpinner.setSistemTanam(listSistemTanam);

        return dataAllSpinner;
    }

    public static Aset mapCursorToArrayAset(Cursor asetCursor) {
        Aset asetList = new Aset();

        while (asetCursor.moveToNext()) {
            Integer asetid = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETID));
            String asetname = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETNAME));
            String asettipe = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETTIPE));
            String asetjenis = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETJENIS));
            String asetkondisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETKONDISI));
            String asetsubunit = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETSUBUNIT));
            String asetkode = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETKODE));
            String nomorsap = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.NOMORSAP));
            String fotoaset1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET1));
            String fotoaset2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET2));
            String fotoaset3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET3));
            String fotoaset4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET4));
            String fotoaset5 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASET5));
            String geotag1 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG1));
            String geotag2 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG2));
            String geotag3 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG3));
            String geotag4 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG4));
            String geotag5 = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.GEOTAG5));
            Double asetluas = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.ASETLUAS)));
            String tglinput = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.TGLINPUT));
            String tgloleh = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.TGLOLEH));
            Long nilairesidu = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.NILAIRESIDU)));
            Long nilaioleh = asetCursor.getLong(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.NILAIOLEH)));
            String nomorbast = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.NOMORBAST));
            String masasusut = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.MASASUSUT));
            String keterangan = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.KETERANGAN));
            String fotoqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOQR));
            String noinv = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.NOINV));
            String fotoasetqr = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.FOTOASETQR));
            String statusposisi = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.STATUSPOSISI));
            String unitid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.UNITID));
            String afdelingid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.AFDELINGID));
            String userinputid = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.USERINPUTID));
            String createdat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.CREATEDAT));
            String updatedat = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.UPDATEDAT));
            Integer jumlahpohon = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.JUMLAHPOHON)));
            String hgu = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.HGU));
            String asetfotoqrstatus = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ASETFOTOQRSTATUS));
            String ketreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.KETREJECT));
            String statusreject = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.STATUSREJECT));
            Double persenkondisi = asetCursor.getDouble(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.PERSENKONDISI)));
            String beritaAcara = asetCursor.getString(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.BERITAACARA)));
            String fileBAST = asetCursor.getString(asetCursor.getColumnIndexOrThrow(String.valueOf(DatabaseContractAset.AsetColumns.FILEBAST)));
            String alat_pengangkutan = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.ALATPENGANGKUTAN));
            String satuan_luas = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.SATUANLUAS));
            String pop_total_ini = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.POPTOTALINI));
            String pop_total_std = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.POPTOTALSTD));
            String pop_hektar_ini = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.POPHEKTARINI));
            String pop_hektar_std = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.POPHEKTARSTD));
            Integer tahun_tanam = asetCursor.getInt(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.TAHUNTANAM));
            String sistem_tanam = asetCursor.getString(asetCursor.getColumnIndexOrThrow(DatabaseContractAset.AsetColumns.SISTEMTANAM));

            asetList = new Aset(asetid, asetname,asettipe,asetjenis,asetkondisi,asetsubunit,asetkode,nomorsap,fotoaset1,fotoaset2,
                    fotoaset3,fotoaset4,fotoaset5,geotag1,geotag2,geotag3,geotag4,geotag5,asetluas,tglinput,tgloleh,nilairesidu,nilaioleh,nomorbast,
                    masasusut,keterangan,fotoqr,noinv,fotoasetqr,statusposisi,unitid,afdelingid,userinputid,createdat,updatedat,
                    jumlahpohon,persenkondisi,statusreject,ketreject,asetfotoqrstatus,hgu,beritaAcara,fileBAST,alat_pengangkutan,satuan_luas,
                    pop_total_ini,pop_total_std,pop_hektar_ini,pop_hektar_std,tahun_tanam,sistem_tanam);
        }
        return asetList;
    }
}
