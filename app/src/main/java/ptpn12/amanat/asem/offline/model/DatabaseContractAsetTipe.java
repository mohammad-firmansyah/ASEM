package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAsetTipe {

    static String TABLE_NAME = "aset_tipe";
    static final class AsetTipeColumns implements BaseColumns {
        static String ASETTIPEID  = "aset_tipe_id";
        static String ASETTIPEDESC = "aset_tipe_desc";
    }
}
