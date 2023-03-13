package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAset {
    public static String TABLE_NAME = "data_aset";
    public static final class AsetColumns implements BaseColumns {
        public static String ASETID = "aset_id";
        public static String ASETNAME = "aset_name";
        public static String ASETTIPE = "aset_tipe";
        public static String ASETJENIS = "aset_jenis";
        public static String ASETKONDISI = "aset_kondisi";
        public static String ASETSUBUNIT = "aset_sub_unit";
        public static String ASETKODE = "aset_kode";
        public static String NOMORSAP = "nomor_sap";
        public static String FOTOASET1 = "foto_aset1";
        public static String FOTOASET2 = "foto_aset2";
        public static String FOTOASET3 = "foto_aset3";
        public static String FOTOASET4 = "foto_aset4";
        public static String FOTOASET5 = "foto_aset5";
        public static String GEOTAG1 = "geo_tag1";
        public static String GEOTAG2 = "geo_tag2";
        public static String GEOTAG3 = "geo_tag3";
        public static String GEOTAG4 = "geo_tag4";
        public static String GEOTAG5 = "geo_tag5";
        public static String ASETLUAS = "aset_luas";
        public static String TGLINPUT = "tgl_input";
        public static String TGLOLEH = "tgl_oleh";
        public static String NILAIRESIDU = "nilai_residu";
        public static String NILAIOLEH = "nilai_oleh";
        public static String NOMORBAST = "nomor_bast";
        public static String MASASUSUT = "masa_susut";
        public static String KETERANGAN = "keterangan";
        public static String FOTOQR = "foto_qr";
        public static String NOINV = "no_inv";
        public static String FOTOASETQR = "foto_aset_qr";
        public static String STATUSPOSISI = "status_posisi";
        public static String UNITID = "unit_id";
        public static String AFDELINGID = "afdeling_id";
        public static String USERINPUTID = "user_input_id";
        public static String CREATEDAT = "created_at";
        public static String UPDATEDAT = "updated_at";
        public static String JUMLAHPOHON = "jumlah_pohon";
        public static String HGU = "hgu";
        public static String ASETFOTOQRSTATUS = "aset_foto_qr_status";
        public static String KETREJECT = "ket_reject";
        public static String STATUSREJECT = "status_reject";
        public static String PERSENKONDISI = "persen_kondisi";
        public static String UMUREKONOMISINMONTH = "umur_ekonomis_in_month";
        public static String BERITAACARA = "berita_acara";
        public static String FILEBAST = "file_bast";
        public static String ALATPENGANGKUTAN = "alat_pengangkutan";
        public static String SATUANLUAS = "satuan_luas";
        public static String POPTOTALINI = "pop_pohon_saat_ini";
        public static String POPTOTALSTD = "pop_standar";
        public static String POPHEKTARINI = "pop_per_ha";
        public static String POPHEKTARSTD = "presentase_pop_per_ha";
        public static String TAHUNTANAM = "tahun_tanam";
        public static String SISTEMTANAM = "sistem_tanam";


    }
}
