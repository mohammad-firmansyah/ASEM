package ptpn12.amanat.asem.offline.model;

import android.provider.BaseColumns;

public class DatabaseContractAfdeling {
    public static String TABLE_NAME = "afdeling";
    public static final class AfdelingColumns implements BaseColumns {
        public static String AFDELINGID = "afdeling_id";
        public static String AFDELINGDESC = "afdeling_desc";
        public static String UNITID = "unit_id";
    }
}
