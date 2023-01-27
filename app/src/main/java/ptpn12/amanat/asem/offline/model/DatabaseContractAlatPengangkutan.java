package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAlatPengangkutan {
    static String TABLE_NAME = "alat_pengangkutan";
    static final class AlatPengangkutanColumns implements BaseColumns {
        static String APID  = "ap_id";
        static String APDESC = "ap_desc";
}
}
