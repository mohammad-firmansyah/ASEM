package ptpn12.amanat.asem.db;

import android.provider.BaseColumns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatabaseContractAset {
    static String TABLE_NAME = "data_aset";
    static final class AsetColumns implements BaseColumns {
        static String ASETID = "aset_id";
        static String ASETNAME = "aset_name";
        static String ASETTIPE = "aset_tipe";
        static String ASETJENIS = "aset_jenis";
        static String ASETKONDISI = "aset_kondisi";
        static String ASETSUBUNIT = "aset_sub_unit";
        static String ASETKODE = "aset_kode";
        static String NOMORSAP = "nomor_sap";
        static String FOTOASET1 = "foto_aset1";
        static String FOTOASET2 = "foto_aset2";
        static String FOTOASET3 = "foto_aset3";
        static String FOTOASET4 = "foto_aset4";
        static String GEOTAG1 = "geo_tag1";
        static String GEOTAG2 = "geo_tag2";
        static String GEOTAG3 = "geo_tag3";
        static String GEOTAG4 = "geo_tag4";
        static String ASETLUAS = "aset_luas";
        static String TGLINPUT = "tgl_input";
        static String TGLOLEH = "tgl_oleh";
        static String NILAIRESIDU = "nilai_residu";
        static String NILAIOLEH = "nilai_oleh";
        static String NOMORBAST = "nomor_bast";
        static String MASASUSUT = "masa_susut";
        static String KETERANGAN = "keterangan";
        static String FOTOQR = "foto_qr";
        static String NOINV = "no_inv";
        static String FOTOASETQR = "foto_aset_qr";
        static String STATUSPOSISI = "status_posisi";
        static String UNITID = "unit_id";
        static String AFDELINGID = "afdeling_id";
        static String USERINPUTID = "user_input_id";
        static String CREATEDAT = "created_at";
        static String UPDATEDAT = "updated_at";
        static String JUMLAHPOHON = "jumlah_pohon";
        static String HGU = "hgu";
        static String ASETFOTOQRSTATUS = "aset_foto_qr_status";
        static String KETREJECT = "ket_reject";
        static String STATUSREJECT = "status_reject";
        static String PERSENKONDISI = "persen_kondisi";
        static String UMUREKONOMISINMONTH = "umur_ekonomis_in_month";
        static String BERITAACARA = "berita_acara";
        private String ALATPENGANGKUTAN = "alat_pengangkutan";
        private String SATUANLUAS = "satuan_luas";
        private String POPTOTALINI = "pop_total_ini";
        private String POPTOTALSTD = "pop_total_std";
        private String POPHEKTARINI = "pop_hektar_ini";
        private String POPHEKTARSTD = "pop_hektar_std";


    }
}
