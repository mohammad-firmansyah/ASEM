package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAsetKondisi {
    static String TABLE_NAME = "aset_kondisi";
    static final class AsetKondisiColumns implements BaseColumns {
        static String ASETKONDISIID  = "aset_kondisi_id";
        static String ASETKONDISIDESC = "aset_kondisi_desc";
    }
}
