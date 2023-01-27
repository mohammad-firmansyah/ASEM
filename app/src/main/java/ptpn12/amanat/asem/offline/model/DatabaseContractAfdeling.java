package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAfdeling {
    static String TABLE_NAME = "afdeling";
    static final class AfdelingColumns implements BaseColumns {
        static String AFDELINGID = "afdeling_id";
        static String AFDELINGDESC = "afdeling_desc";
        static String UNITID = "unit_id";
    }
}
