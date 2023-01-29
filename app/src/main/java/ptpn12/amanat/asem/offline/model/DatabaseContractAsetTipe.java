package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAsetTipe {

    public static String TABLE_NAME = "aset_tipe";
    public static final class AsetTipeColumns implements BaseColumns {
        public static String ASETTIPEID  = "aset_tipe_id";
        public static String ASETTIPEDESC = "aset_tipe_desc";
    }
}
