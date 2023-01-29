package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAlatPengangkutan {
    public static String TABLE_NAME = "alat_pengangkutan";
    public static final class AlatPengangkutanColumns implements BaseColumns {
        public static String APID  = "ap_id";
        public static String APDESC = "ap_desc";
}
}
