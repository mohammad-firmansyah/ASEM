package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAsetKode {
    public static String TABLE_NAME = "aset_kode";
    public static final class AsetKodeColumns implements BaseColumns {
        public static String ASETKODEID  = "aset_tipe_id";
        public static String CREATEDAT = "created_at";
        public static String UPDATEDAT = "updated_at";
        public static String ASETCLASS = "aset_class";
        public static String ASETGROUP = "aset_group";
        public static String ASETDESC = "aset_desc";
        public static String ASETJENIS = "aset_jenis";
    }
}
