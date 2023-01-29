package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAsetKondisi {
    public static String TABLE_NAME = "aset_kondisi";
    public static final class AsetKondisiColumns implements BaseColumns {
        public static String ASETKONDISIID  = "aset_kondisi_id";
        public static String ASETKONDISIDESC = "aset_kondisi_desc";
    }
}
